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
        DataHandler lh=new DataHandler();
        GUI gui=new GUI(lh);
        lh.gui=gui;
//        lh.evaluateCase();
        // call Similarity so it's initialized (for developement purposes)
//        Similarity.compute(false, null, null);
    }
}
