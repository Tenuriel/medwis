/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Tim Pontzen
 */
public class GUI {

    /**
     * the dimension of every inputfield.
     */
    public static final Dimension FIELD_SIZE = new Dimension(120, 30);
    /**
     * the number of elements in one gridrow of the core-panel.
     */
    public static final int ELEMENTS_ROW = 4;
    public static JTextArea console;
    public JFrame frame;
    public JTextField lucenefield;
    public ArrayList<JComponent> inputfields;

    public GUI(LuceneHandler lh) {
        inputfields = new ArrayList<>();
        frame = new JFrame("CBR");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel lucenep = new JPanel(new GridLayout(1, 2));
        lucenefield = new JTextField();
        lucenefield.setPreferredSize(FIELD_SIZE);
        lucenep.add(lucenefield);
        JButton b = new JButton("create Index");
        b.addActionListener(lh);
        lucenep.add(b);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = ELEMENTS_ROW;
        frame.add(lucenep, c);

        //input fields and radio buttons
        JPanel core = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Geschlecht");
        c.gridwidth = 1;
        label.setPreferredSize(FIELD_SIZE);
        core.add(label, c);

        ButtonGroup group = new ButtonGroup();
        JPanel radContainer=new JPanel(new GridLayout(1, 2));
        radContainer.setPreferredSize(FIELD_SIZE);
        JRadioButton radbut = new JRadioButton("m",true);
        radbut.setName("Geschlecht");
        inputfields.add(radbut);
        group.add(radbut);
        radContainer.add(radbut);
        radbut = new JRadioButton("w");
        radbut.setName("Geschlecht");
        group.add(radbut);
        inputfields.add(radbut);
        radContainer.add(radbut);

        c.gridx = 1;
        core.add(radContainer, c);
        
        //herz
        label=new JLabel("Herz");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=1;
        core.add(label,c);
        JTextField inputfield=new JTextField();
        inputfield.setName("Herz");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx=1;
        core.add(inputfield,c);
        
        //blutdruck
        label=new JLabel("Blutdruck");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=0;
        core.add(label,c);
        inputfield=new JTextField();
        inputfield.setName("Blutdruck");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Rhythmus
        label = new JLabel("Rhythmus regelm.");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=1;
        core.add(label, c);

        group = new ButtonGroup();
        radContainer=new JPanel(new GridLayout(1, 2));
        radContainer.setPreferredSize(FIELD_SIZE);
        radbut = new JRadioButton("ja",true);
        radbut.setName("Rhythmus regelm.");
        inputfields.add(radbut);
        group.add(radbut);
        radContainer.add(radbut);
        radbut = new JRadioButton("nein");
        radbut.setName("Rhythmus regelm.");
        group.add(radbut);
        inputfields.add(radbut);
        radContainer.add(radbut);
        c.gridx++;
        core.add(radContainer, c);
        
        //Leutozythen
        label=new JLabel("Leutozythen");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=2;
        core.add(label,c);
        inputfield=new JTextField();
        inputfield.setName("Leutozythen");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Hämatokrit
        label=new JLabel("Hämatokrit");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=2;
        core.add(label,c);
        inputfield=new JTextField();
        inputfield.setName("Hämatokrit");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Harnsäure
        label=new JLabel("Harnsäure");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=3;
        core.add(label,c);
        inputfield=new JTextField();
        inputfield.setName("Harnsäure");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Schmerz
        label=new JLabel("Schmerz");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=3;
        core.add(label,c);
        inputfield=new JTextField("Ort");
        inputfield.setName("Schmerz");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Juckreiz
        label=new JLabel("Juckreiz");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=4;
        core.add(label,c);
        inputfield=new JTextField("Ort");
        inputfield.setName("Juckreiz");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Übelkeit
        label = new JLabel("Übelkeit");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=4;
        core.add(label, c);

        group = new ButtonGroup();
        radContainer=new JPanel(new GridLayout(1, 2));
        radContainer.setPreferredSize(FIELD_SIZE);
        radbut = new JRadioButton("ja", true);
        radbut.setName("Übelkeit");
        inputfields.add(radbut);
        group.add(radbut);
        radContainer.add(radbut);
        radbut = new JRadioButton("nein");
        radbut.setName("Übelkeit");
        group.add(radbut);
        inputfields.add(radbut);
        radContainer.add(radbut);
        c.gridx++;
        core.add(radContainer, c);
        
        //Druchfall
        label = new JLabel("Durchfall");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=5;
        core.add(label, c);

        group = new ButtonGroup();
        radContainer=new JPanel(new GridLayout(1, 2));
        radContainer.setPreferredSize(FIELD_SIZE);
        radbut = new JRadioButton("ja", true);
        radbut.setName("Durchfall");
        inputfields.add(radbut);
        group.add(radbut);
        radContainer.add(radbut);
        radbut = new JRadioButton("nein");
        group.add(radbut);
        radbut.setName("Durchfall");
        inputfields.add(radbut);
        radContainer.add(radbut);
        c.gridx++;
        core.add(radContainer, c);
        
        //Verstopfung
        label = new JLabel("Verstopfung");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=5;
        core.add(label, c);

        group = new ButtonGroup();
        radContainer=new JPanel(new GridLayout(1, 2));
        radContainer.setPreferredSize(FIELD_SIZE);
        radbut = new JRadioButton("ja", true);
        radbut.setName("Verstopfung");
        inputfields.add(radbut);
        group.add(radbut);
        radContainer.add(radbut);
        radbut = new JRadioButton("nein");
        radbut.setName("Verstopfung");
        group.add(radbut);
        inputfields.add(radbut);
        radContainer.add(radbut);
        c.gridx++;
        core.add(radContainer, c);
        
             
        //Temperatur
        label=new JLabel("Temperatur (°C)");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=6;
        core.add(label,c);
        inputfield=new JTextField("");
        inputfield.setName("Temeratur");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Gewicht
        label=new JLabel("Gewicht (kg/km)");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=2;
        c.gridy=6;
        core.add(label,c);
        inputfield=new JTextField("");
        inputfield.setName("Gewicht");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        //Alter
        label=new JLabel("Alter");
        label.setPreferredSize(FIELD_SIZE);
        c.gridx=0;
        c.gridy=7;
        core.add(label,c);
        inputfield=new JTextField("");
        inputfield.setName("Alter");
        inputfield.setPreferredSize(FIELD_SIZE);
        inputfields.add(inputfield);
        c.gridx++;
        core.add(inputfield,c);
        
        
        c.gridx = 0;
        c.gridy = 1;
        frame.add(core, c);
        //output field
        console = new JTextArea("Programmausgabe :\n");
        console.setEditable(false);
        JScrollPane conPane = new JScrollPane(console);
        conPane.setPreferredSize(new Dimension(FIELD_SIZE.width * ELEMENTS_ROW, 80));
        c.gridx = 0;
        c.gridwidth = ELEMENTS_ROW;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        frame.add(conPane, c);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
