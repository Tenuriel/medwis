/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Tim Pontzen
 */
public class DataHandler implements ActionListener {

    /**
     * number of categories to skip while reading "categories.yml".
     */
    public static final int skipCat = 3;
    /**
     * the max number of results to be returned by evaluateCase().
     */
    public static final int resultNumber = 10;
    public GUI gui;

    public DataHandler() {
    }

    /**
     * collects the input and returns a hashmap with <Criteria,Value>.
     *
     * @return hashmap with <Criteria,Value>.
     */
    public HashMap<String, String> getInput() {
        HashMap<String, String> map = new HashMap<>();
        for (JComponent c : gui.inputfields) {
            if (c instanceof JTextField) {
                if (c.getName().equals("Blutdruck")) {
                    String[] tmp = ((JTextField) c).getText().split("/");
                    map.put("Blutdruck_systolisch", tmp[0]);
                    map.put("Blutdruck_diastolisch", tmp[1]);
                } else {
                    map.put(c.getName(), ((JTextField) c).getText());
                }
            } else if (c instanceof JRadioButton && ((JRadioButton) c).isSelected()) {
                map.put(c.getName(), ((JRadioButton) c).getText());
            }
        }
        return map;
    }

    /**
     * searches for the best matching case for the input case.
     *
     * @return best possible matches
     */
    public ArrayList<HashMap<String, String>> evaluateCase() {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> inputCase = getInput();
        HashMap<String, String> dataCase = new HashMap<>();
        String geschlecht = inputCase.remove("Geschlecht");
        boolean isFemale = false;
        if (geschlecht.equals("w")) {
            isFemale = true;
        }

        try {
            //read Categories
            Scanner scan = new Scanner(Paths.get("./categories.yml"));
            ArrayList<String> categories = new ArrayList<>();
            String cat;
            while (scan.hasNextLine()) {
                cat = scan.nextLine();
                if (!(cat.startsWith(" ") || cat.startsWith("#"))) {
                    categories.add(cat.split(":")[0]);
                }
            }
            scan.close();
            //read file line by line
            scan = new Scanner(Paths.get("../data/mwiss2014_100k.tsv"));
            String[] line;
            String[] tmp;
            int counter = skipCat;
            while (scan.hasNextLine()) {
                line = scan.nextLine().split("\t");
                for (String s : line) {
                    if (s.equals("w") || s.equals("m")) {
                        continue;
                    } else if (s.contains("/")) {
                        tmp = s.split("/");
                        dataCase.put("Blutdruck_systolisch", tmp[0]);
                        dataCase.put("Blutdruck_diastolisch", tmp[1]);
                        continue;
                    }
                    dataCase.put(categories.get(counter), s);
                    counter++;
                    if(counter==categories.size()){
                        break;
                    }
                }
                counter = skipCat;
                dataCase.put("score", String.valueOf(Similarity.compute(isFemale, inputCase, dataCase)));
                if (result.isEmpty()
                        || Float.valueOf(result.get(result.size() - 1).get("score")) < Float.valueOf(dataCase.get("score"))) {
                    placeElement(result, dataCase);
                }
                dataCase = new HashMap<>();
            }
        } catch (IOException ex) {
            GUI.console.append("Error while reading File: " + ex.getMessage());
        }

        return result;
    }

    /**
     * inserts an element at the right place.
     *
     * @param result the list to be sortet
     * @param datase the element to be insertert
     */
    private void placeElement(ArrayList<HashMap<String, String>> result, HashMap<String, String> dataCase) {
        if (result.isEmpty()) {
            result.add(dataCase);
        } else {
            for (HashMap<String, String> e : result) {
                if (Float.valueOf(e.get("score")) < Float.valueOf(dataCase.get("score"))) {
                    result.add(result.indexOf(e), dataCase);
                    if (result.size() >= resultNumber) {
                        result.remove(result.size() - 1);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
