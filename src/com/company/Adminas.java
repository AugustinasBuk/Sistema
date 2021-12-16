package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Adminas implements ActionListener {

    JFrame frame = new JFrame();

    JLabel helloLabel = new JLabel("Vartotojų registravimas");

    JButton addButton = new JButton("Pridėti mokytoją");
    JButton addButton2 = new JButton("Pridėti studentą");
    JButton editStudentButton = new JButton("Redaguoti studentus");
    JButton editTeacherButton = new JButton("Redaguoti mokytojus");
    JButton logoutButton = new JButton("Atsijungti");


    JLabel nameLabel = new JLabel("Vardas:");
    JLabel nameLabel2 = new JLabel("Vardas:");

    JTextField nameText = new JTextField();
    JTextField nameText2 = new JTextField();

    JLabel surnameLabel = new JLabel("Pavarde:");
    JLabel surnameLabel2 = new JLabel("Pavarde:");

    JTextField surnameText = new JTextField();
    JTextField surnameText2 = new JTextField();

    JLabel subjectLabel = new JLabel("Subject:");
    JLabel groupLabel = new JLabel("Grupė:");
    JTextField groupText = new JTextField();
    JTextField subjectText = new JTextField();
    JLabel messageLabel = new JLabel("");

    Adminas(){


        helloLabel.setBounds(120,15,190,40);

        nameLabel.setBounds(20, 60, 60, 25);
        nameLabel2.setBounds(200,60,60,25);

        nameText.setBounds(70, 60, 120, 25);
        nameText2.setBounds(250,60,120,25);


        surnameLabel.setBounds(16, 90, 60, 25);
        surnameText.setBounds(70,90,120,25);

        surnameLabel2.setBounds(195,90,120,25);
        surnameText2.setBounds(250,90,120,25);

        subjectLabel.setBounds(18,120, 60,25 );
        groupLabel.setBounds(205,120,60,25);

        subjectText.setBounds(70,120,120,25);
        groupText.setBounds(250,120,120,25);

        addButton.setBounds(50,155,140,25);
        addButton2.setBounds(230,155,140,25);

        messageLabel.setBounds(30,195, 200, 25);


        logoutButton.setBounds(10,25,90,25);
        editStudentButton.setBounds(210, 250, 160, 30);
        editTeacherButton.setBounds(30, 250, 160, 30);
        editStudentButton.addActionListener(this);
        editTeacherButton.addActionListener(this);


        addButton.addActionListener(this);
        addButton2.addActionListener(this);
        logoutButton.addActionListener(this);


        frame.add(helloLabel);

        frame.add(nameLabel);
        frame.add(nameLabel2);

        frame.add(nameText);
        frame.add(nameText2);

        frame.add(surnameLabel);
        frame.add(surnameLabel2);

        frame.add(surnameText);
        frame.add(surnameText2);

        frame.add(subjectLabel);
        frame.add(groupLabel);

        frame.add(subjectText);
        frame.add(groupText);

        frame.add(addButton);
        frame.add(addButton2);

        frame.add(editStudentButton);
        frame.add(editTeacherButton);
        frame.add(logoutButton);

        frame.add(messageLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);

        helloLabel.setFont(new Font("Impact", Font.BOLD, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            String vardas = nameText.getText();
            String pavarde = surnameText.getText();
            String dalykas = subjectText.getText();

            if (vardas.isEmpty() || pavarde.isEmpty() || dalykas.isEmpty()) {
                System.out.println("Bloga Įvestis ");
            } else {
                DuomBaze duomBaze = new DuomBaze();
                boolean tikrinti = duomBaze.pridetiMokytoja(vardas, pavarde, dalykas);
                if (tikrinti == false) {
                    System.out.println("Bloga Įvestis ");
                } else if (tikrinti == true) {
                    System.out.println("Mokytojas pridėtas ");
                }
            }
        }

            if (e.getSource() == addButton2) {
                String vardas = nameText2.getText();
                String pavarde = surnameText2.getText();
                String grupe = groupText.getText();

                if (vardas.isEmpty() || pavarde.isEmpty() || grupe.isEmpty()) {
                    System.out.println("Bloga Įvestis ");
                } else {
                    DuomBaze duomBaze = new DuomBaze();
                    boolean tikrinti = duomBaze.pridetiStudenta(vardas, pavarde, grupe);
                    if (tikrinti == false) {
                        System.out.println("Bloga Įvestis ");
                    } else if (tikrinti == true) {
                        System.out.println("Studentas pridėtas");
                    }
                }
            }
        if (e.getSource() == logoutButton){
            Prisijungimas prisijungimas = new Prisijungimas();
            frame.dispose();
        }
        if (e.getSource() == editStudentButton){
            AdminStudentas adminStudentas = new AdminStudentas();
            frame.dispose();
        }
        if (e.getSource() == editTeacherButton){
            AdminMokytojas adminMokytojas = new AdminMokytojas();
            frame.dispose();

            }
        }
    }

