/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Tim Pontzen
 */
public class GUI {
    public JFrame frame;
    public JTextField lucenefield;
    public ArrayList<JComponent> inputfields;
    public GUI(LuceneHandler lh){
        frame=new JFrame("CBR");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel lucenep=new JPanel(new GridLayout(1, 2));
        lucenefield = new JTextField();
        lucenep.add(lucenefield);
        JButton b=new JButton("create Index");
        b.addActionListener(lh);
        lucenep.add(b);
        
       c.gridx=0;
       c.gridy=0;
       frame.add(lucenep, c);
       
       frame.setVisible(true);
       frame.pack();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
