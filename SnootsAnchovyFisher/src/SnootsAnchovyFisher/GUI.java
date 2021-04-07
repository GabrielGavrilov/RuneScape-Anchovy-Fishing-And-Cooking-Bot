package SnootsAnchovyFisher;

import org.osbot.JF;
import org.osbot.JP;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class GUI {
    public void run(Main main) {

        ////////////////////////////////////////////////////////////////
        //                    MAIN FRAME PROPERTIES                  //
        //////////////////////////////////////////////////////////////

        //Creates the main frame
        JFrame mainFrame = new JFrame("Snoots' Anchovy Fisher & Cooker");

        //Sets the size and properties of the main frame
        mainFrame.setSize(300, 250);
        mainFrame.setResizable(false);

        //Creates the options panel
        JPanel optionsPanel = new JPanel();

        //Creates the panel title and sets its properties
        TitledBorder panelTitle = BorderFactory.createTitledBorder("Options");
        panelTitle.setTitleJustification(TitledBorder.LEFT);

        //Sets the options panel properties
        optionsPanel.setBorder(panelTitle);
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(2, 5, 280, 180);

        //Adds the options panel to the main frame
        mainFrame.add(optionsPanel);

        //Creates the start panel and sets its properties
        JPanel startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setBounds(5, 350, 70, 20);

        //Adds the start panel to the main frame
        mainFrame.add(startPanel);

        ////////////////////////////////////////////////////////////////
        //                      MAIN FRAME CONTENT                   //
        //////////////////////////////////////////////////////////////

        //Creates a new string label and sets its properties
        JLabel optionLabel = new JLabel("Choose option:");
        optionLabel.setBounds(10, 40, 95, 20);
        optionsPanel.add(optionLabel);

        //Creates the combo box
        JComboBox<String> optionList = new JComboBox<String>(new String[] {
                "",
                "Fish Anchovies",
                "Cook Anchovies"
        });

        //Sets the combo box properties
        optionList.addActionListener(e -> main.option = (String) optionList.getSelectedItem());
        optionList.setBounds(160, 40, 110, 20);
        optionsPanel.add(optionList);

        //Makes it so the bot wont run until the GUI closes
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            synchronized (main.lock) {
                main.lock.notify();
            }
            mainFrame.setVisible(false);
        });

        //Sets the start button properties
        startButton.setBounds(5, 185, 70, 20);
        startPanel.add(startButton);

        //Sets the main frame visible
        mainFrame.setVisible(true);
    }
}
