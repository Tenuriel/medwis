/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Tim Pontzen
 */
public class LuceneHandler implements ActionListener{
    public static String DELIMETER = "lucenedeli";
    public GUI gui;
    private IndexReader ir;
    private final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
    private IndexSearcher iS;
    private IndexWriter iw;
    
    public LuceneHandler(){
    }
    /**
     * load the index.
     * @throws IOException If there is no Index or a Readerror. 
     */
    public void loadIndex() throws IOException{
            ir=DirectoryReader.open(FSDirectory.open(new File("./Index")));
            iS=new IndexSearcher(ir);
    }
    
    /**
     * creates an Index from medical Case-Files.
     */
    public void createIndex(){
        try {
            Directory dir = FSDirectory.open(new File("Index"));
            IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
            conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(dir, conf);
            Scanner scan = new Scanner(Paths.get("abstract_clean.txt"));
            String[] line;
            Document doc;
        } catch (IOException ex) {
            GUI.console.append(ex.getMessage()+"\n");
        }
    }
    
    /**
     * Returns a string enclosed in delimeters.
     *
     * @param s
     * @return
     */
    public static String delimeterString(String s) {
        return DELIMETER + " " + s + " " + DELIMETER;
    }
    
    public HashMap<String,String> getInput(){
        HashMap<String,String> map=new HashMap<>();
        for(JComponent c:gui.inputfields){
            if(c instanceof JTextField){
                map.put(c.getName(), ((JTextField)c).getText());
            }else if(c instanceof JRadioButton && ((JRadioButton)c).isSelected()){
                map.put(c.getName(), ((JRadioButton)c).getText());
            }
        }
        return map;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
