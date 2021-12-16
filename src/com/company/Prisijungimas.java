package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Prisijungimas implements ActionListener {

    String vartotojas = "Studentas";

    JFrame frame = new JFrame();
    String[] naudSarasasString = {"Studentas","Adminas", "Mokytojas"," "};
    JComboBox sarasas = new JComboBox(naudSarasasString);

    JTextField SlapyvardisField = new JTextField("");
    JPasswordField SlaptazodisField = new JPasswordField("");

    JLabel slapyvardisLabel = new JLabel("Slapyvardis(vardas): ");
    JLabel slaptazodisLabel = new JLabel("Slaptažodis(pavardė): ");
    JLabel messageLabel = new JLabel("");
    JLabel tekstasLabel = new JLabel("Prisijungimas");


    JButton loginButton = new JButton("Prisijungti");

    Prisijungimas(){

        slapyvardisLabel.setBounds(7,100,300,25);
        slaptazodisLabel.setBounds(0,150,140,25);
        messageLabel.setBounds(125,250,175,25);
        tekstasLabel.setBounds(130,50,200,30);

        SlapyvardisField.setBounds(125,100,200,25);
        SlaptazodisField.setBounds(125,150,200,25);


        sarasas.setSelectedIndex(3);
        sarasas.setBounds(150,180,100,25);
        sarasas.addActionListener(this);

        loginButton.setBounds(125,260,200,50);
        loginButton.addActionListener(this);

        frame.add(loginButton);
        frame.add(sarasas);
        frame.add(messageLabel);
        frame.add(SlapyvardisField);
        frame.add(slapyvardisLabel);
        frame.add(SlaptazodisField);
        frame.add(slaptazodisLabel);
        frame.add(tekstasLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);

        tekstasLabel.setFont(new Font("Impact", Font.BOLD, 28));
        loginButton.setBackground(Color.yellow);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sarasas){
            JComboBox cb = (JComboBox)e.getSource();
            vartotojas = (String)cb.getSelectedItem();
        }

        if (e.getSource() == loginButton){
            if(vartotojas.equals("Studentas")){
                String slapyvardis = SlapyvardisField.getText();
                String slaptazodis = String.valueOf(SlaptazodisField.getPassword());
                if(slapyvardis.isEmpty() || slaptazodis.isEmpty()){
                    System.out.println("Bloga Įvestis");
                }else {
                DuomBaze duomBaze = new DuomBaze();
                boolean tikrinti = duomBaze.studentoPrisijungimas(slapyvardis, slaptazodis);
                    if(tikrinti == true){
                        PazymiuKnygele pazymiuKnygele = new PazymiuKnygele(slapyvardis);
                        frame.dispose();
                    }else if(tikrinti == false){
                        System.out.println("Bloga Įvestis");
                    }
                }
            }

            if(vartotojas.equals("Mokytojas")) {
                String slapyvardis = SlapyvardisField.getText();
                String slaptazodis = String.valueOf(SlaptazodisField.getPassword());
                if (slapyvardis.isEmpty() || slaptazodis.isEmpty()) {
                    System.out.println("Bloga Įvestis");
                }else{
                    DuomBaze duomBaze = new DuomBaze();
                    boolean tikrinti = duomBaze.mokytojoPrisijungimas(slapyvardis, slaptazodis);
                    if(tikrinti == true){
                        Mokytojas mokytojas = new Mokytojas(slapyvardis, slaptazodis);
                        frame.dispose();
                    }else if(tikrinti == false){
                        System.out.println("Bloga Įvestis");
                    }
                }
            }

            if(vartotojas.equals("Adminas")) {
                String slapyvardis = SlapyvardisField.getText();
                String slaptazodis = String.valueOf(SlaptazodisField.getPassword());
                if (slapyvardis.isEmpty() || slaptazodis.isEmpty()) {
                    System.out.println("Bloga Įvestis");

                }else if (slapyvardis.equals("admin") && slaptazodis.equals("admin")){
                    Adminas adminas = new Adminas();
                    frame.dispose();
                }
            }
        }

    }
}
