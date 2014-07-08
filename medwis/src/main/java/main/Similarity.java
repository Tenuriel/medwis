/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Martin Zurowietz
 */
public class Similarity {
   
   private static LinkedHashMap<String, LinkedHashMap<String, Object>> categories;
   
   private static final Yaml YAML = new Yaml();
   
   static {
      Similarity.categories = Similarity.loadCategories();
      Similarity.cacheMatrices();
   }
   
   private Similarity() {}
   
   public static float compute(HashMap<String, String> inputCase, HashMap<String, String> storedCase) {
      return 0;
   }
   
   private static float getSimilarityOf(String categoryName, String inputValue, String storedValue) {
      LinkedHashMap<String, Object> category = Similarity.categories.get(categoryName);
      String type = (String) category.get("type");
      float similarity = 0;
      
      if (type.equals("gauss")) {
         similarity = Similarity.getGaussSimilarity(category, inputValue, storedValue);
      } else if (type.equals("matrix")) {
         similarity = Similarity.getMatrixSimilarity(category, inputValue, storedValue);
      } else {
         similarity = Similarity.getBinarySimilarity(inputValue, storedValue);
      }
      
      return similarity;
   }
   
   private static float getGaussSimilarity(LinkedHashMap<String, Object> category, String inputValue, String storedValue) {
      return 0;
   }
   
   private static float getMatrixSimilarity(LinkedHashMap<String, Object> category, String inputValue, String storedValue) {
      return 0;
   }
   
   private static float getBinarySimilarity(String inputValue, String storedValue) {
      if (inputValue.equals(storedValue)) return 1;
      return 0;
   }
   
   private static LinkedHashMap<String, LinkedHashMap<String, Object>> loadCategories() {
      InputStream input = null;
      LinkedHashMap<String, LinkedHashMap<String, Object>> output;
      try {
         input = new FileInputStream(new File("categories.yml"));
      } catch (FileNotFoundException ex) {
         Logger.getLogger(Similarity.class.getName()).log(Level.SEVERE, null, ex);
      }
      output = (LinkedHashMap<String, LinkedHashMap<String, Object>>) YAML.load(input);
      return output;
   }
   
   private static void cacheMatrices() {
      //TODO
   }
   
}
