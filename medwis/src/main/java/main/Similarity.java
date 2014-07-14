package main;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Martin Zurowietz
 */
public class Similarity {

   /**
    * Maps every category to its similarity computing type.
    */
   private static HashMap<String, String> types;
   
   /**
    * Maps every category to its weight.
    */
   private static HashMap<String, Float> weights;

   /**
    * Maps every category of type "gauss" to its male standard deviation.
    */
   private static HashMap<String, Float> standardDeviationsMale;

   /**
    * Maps every category of type "gauss" to its female standard deviation.
    */
   private static HashMap<String, Float> standardDeviationsFemale;

   /**
    * Maps every category of type "matrix" to its male similarity matrix.
    */
   private static HashMap<String, Similarity.Matrix> matricesMale;

   /**
    * Maps every category of type "matrix" to its female similarity matrix.
    */
   private static HashMap<String, Similarity.Matrix> matricesFemale;

   static {
      Similarity.types = new HashMap<>();
      Similarity.weights = new HashMap<>();
      Similarity.standardDeviationsFemale = new HashMap<>();
      Similarity.standardDeviationsMale = new HashMap<>();
      Similarity.matricesFemale = new HashMap<>();
      Similarity.matricesMale = new HashMap<>();

      try {
         Similarity.init();
      } catch (IOException ex) {
         GUI.console.append("Initializing of the similarity computing class failed!" + "\n");
         GUI.console.append(ex.getMessage() + "\n");
      }
   }

   private Similarity() {
   }

   /**
    * Computes the similarity of two cases.
    *
    * @param isFemale Specifies whether the input case is female.
    * @param inputCase Case as provided by the user.
    * @param storedCase Case from the data-basis to compare.
    * @return Similarity of the cases.
    */
   public static float compute(boolean isFemale, HashMap<String, String> inputCase, HashMap<String, String> storedCase) 
   throws IllegalArgumentException {
      float sumWeights = 0;
      float result = 0;

      Iterator<Entry<String, String>> iterator = inputCase.entrySet().iterator();

      Entry<String, String> inputCategory;
      String categoryName;
      float weight;
      
      while (iterator.hasNext()) {
         inputCategory = iterator.next();
         categoryName = inputCategory.getKey();
         weight = Similarity.weights.get(categoryName);
         
         if(inputCategory.getValue().isEmpty()){
             inputCategory.setValue("0");
         }
         
         try {
            result += weight * Similarity.getSimilarityOf(
                    isFemale, categoryName, inputCategory.getValue(), storedCase.get(categoryName)
            );
         } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invlaid value '"
                    + inputCategory.getValue() + "' for category '" + categoryName + "'.");
         }

         sumWeights += weight;
      }
      
      // don't divide by zero
      if (sumWeights == 0) {
         return 0;
      }
      
      return result / sumWeights;
   }

   /**
    * Calculates the similarity of two values of a given category of two cases.
    *
    * @param isFemale Is the input case female? Else male.
    * @param categoryName The category of the values to compare.
    * @param inputValue The value of the input case.
    * @param storedValue The value of the case from the data-basis.
    * @return The similarity of the two values.
    */
   private static float getSimilarityOf(boolean isFemale, String categoryName, String inputValue, String storedValue)
      throws NumberFormatException, ArrayIndexOutOfBoundsException {
      String type = Similarity.types.get(categoryName);
      float similarity = 0;

      if (type.equals("gauss")) {
         similarity = Similarity.getGaussSimilarity(isFemale, categoryName, inputValue, storedValue);
      } else if (type.equals("matrix")) {
         similarity = Similarity.getMatrixSimilarity(isFemale, categoryName, inputValue, storedValue);
      } else {
         similarity = Similarity.getBinarySimilarity(inputValue, storedValue);
      }

      return similarity;
   }

   /**
    * Calculates the similarity of two values of a category by the gaussian
    * method.
    *
    * @param isFemale Is the input case female? Else male.
    * @param categoryName The category of the values to compare.
    * @param inputValue The value of the input case.
    * @param storedValue The value of the case from the data-basis.
    * @return The similarity of the two values.
    */
   private static float getGaussSimilarity(boolean isFemale, String categoryName, String inputValue, String storedValue)
      throws NumberFormatException {
      float deviation = (isFemale)
         ? Similarity.standardDeviationsFemale.get(categoryName)
         : Similarity.standardDeviationsMale.get(categoryName);
      return (float) Math.exp(
              -Math.pow( Float.parseFloat(inputValue) - Float.parseFloat(storedValue), 2 ) /
              ( 2 * Math.pow(deviation, 2) )
      );
   }

   /**
    * Calculates the similarity of two values of a category by the matrix
    * method.
    *
    * @param isFemale Is the input case female? Else male.
    * @param categoryName The category of the values to compare.
    * @param inputValue The value of the input case.
    * @param storedValue The value of the case from the data-basis.
    * @return The similarity of the two values.
    */
   private static float getMatrixSimilarity(boolean isFemale, String categoryName, String inputValue, String storedValue)
      throws ArrayIndexOutOfBoundsException {
      return (isFemale)
              ? Similarity.matricesFemale.get(categoryName).get(inputValue, storedValue)
              : Similarity.matricesMale.get(categoryName).get(inputValue, storedValue);
   }

   /**
    * Calculates the similarity of two values of a category by the binary
    * method.
    *
    * @param inputValue The value of the input case.
    * @param storedValue The value of the case from the data-basis.
    * @return The similarity of the two values.
    */
   private static float getBinarySimilarity(String inputValue, String storedValue) {
      return (inputValue.equals(storedValue)) ? 1 : 0;
   }

   private static void init() throws IOException {
      InputStream input = null;
      Yaml yaml = new Yaml();

      input = new FileInputStream(new File("categories.yml"));

      // temporary internal representation of the categories Yaml file
      LinkedHashMap<String, LinkedHashMap<String, Object>> categories
              = (LinkedHashMap<String, LinkedHashMap<String, Object>>) yaml.load(input);

      Iterator<Entry<String, LinkedHashMap<String, Object>>> iterator
              = categories.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<String, LinkedHashMap<String, Object>> entry = iterator.next();
         LinkedHashMap<String, Object> category = entry.getValue();
         String categoryName = entry.getKey();

         String type = (String) category.get("type");
         String valueFemale = (String) category.get("f_value");
         String valueMale = (String) category.get("m_value");

         Similarity.types.put(categoryName, type);
         Similarity.weights.put(categoryName, new Float((String) category.get("weight")));

         if (type.equals("gauss")) {
            Similarity.standardDeviationsFemale.put(categoryName, new Float(valueFemale));
            Similarity.standardDeviationsMale.put(categoryName, new Float(valueMale));
         } else if (type.equals("matrix")) {
            Similarity.matricesFemale.put(categoryName, new Similarity.Matrix(valueFemale));
            Similarity.matricesMale.put(categoryName, new Similarity.Matrix(valueMale));
         }
      }
   }

   /**
    * Class to represent a similarity-matrix.
    */
   private static class Matrix {

      /**
       * The categories (labels) of the rows and columns (rows and columns are
       * labeled the same).
       */
      private ArrayList<String> categories;
      /**
       * The array of similarity values.
       */
      private float[][] values;

      public Matrix(String path) throws IOException {
         CSVReader csv = null;
         String[] line = null;
         int lineNumber = 0;

         // read tab-separated file
         csv = new CSVReader(new FileReader(path), '\t');
         line = csv.readNext();
         int dimension = line.length;

         this.values = new float[dimension][dimension];

         this.categories = new ArrayList(Arrays.asList(line));

         while ((line = csv.readNext()) != null) {
            for (int i = 0; i < dimension; i++) {
               this.values[lineNumber][i] = Float.parseFloat(line[i]);
            }
            lineNumber++;
         }
      }

      /**
       * Returns the similarity of firstCategory and secondCategory as given by
       * this similarity matrix. Does no error-checks for performance reasons.
       * So make sure the categories are valid!
       *
       * @param firstCategory
       * @param secondCategory
       * @return The similarity of the two categories as given by this
       * similarity matrix.
       */
      public float get(String firstCategory, String secondCategory) throws ArrayIndexOutOfBoundsException {
         return this.values[this.categories.indexOf(firstCategory)][this.categories.indexOf(secondCategory)];
      }
   }
}
