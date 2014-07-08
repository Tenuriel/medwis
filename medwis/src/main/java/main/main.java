/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Tim Pontzen
 */
public class main {
    public static void main(String args[]){
        LuceneHandler lh=new LuceneHandler();
        GUI gui=new GUI(lh);
        lh.gui=gui;
        // call Similarity so it's initialized (for developement purposes)
        Similarity.compute(null, null);
    }
}
