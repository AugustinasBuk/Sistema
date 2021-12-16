package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminMokytojas implements ActionListener {
    JFrame frame = new JFrame();
    Connection con = null;
    public static MokytojasSetGet teacher = new MokytojasSetGet();

    JButton backButton = new JButton("Back");
    JLabel nameLabel = new JLabel("Vardas");
    JTextField nameText = new JTextField();
    JLabel surnameLabel = new JLabel("Pavardė");
    JTextField surnameText = new JTextField();
    JLabel subjectLabel = new JLabel("Dalykas");
    JTextField subjectText = new JTextField();
    JLabel messageLabel = new JLabel("");

    JButton addSubjectButton = new JButton("Pridėti dalyką");
    JButton deleteSubjectButton = new JButton("Ištrinti dalyką");
    JButton deleteTeacher = new JButton("Ištrinti Destytoją");

    DefaultListModel listModel = new DefaultListModel();

    AdminMokytojas(){
        backButton.setBounds(315,25,75,25);
        nameLabel.setBounds(10,285, 120,25);
        nameText.setBounds(10,310,120,25);
        surnameLabel.setBounds(135, 285, 120,25);
        surnameText.setBounds(135,310,120,25);
        subjectLabel.setBounds(260,285,120,25);
        subjectText.setBounds(260,310,120,25);
        addSubjectButton.setBounds(295,80,120,25);
        deleteSubjectButton.setBounds(295,105,120,25);
        deleteTeacher.setBounds(295,170,120,25);
        messageLabel.setBounds(10,375,200,25);


        try {
            String query = "SELECT Vardas,Pavardė FROM mokytojas";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String vardas = rs.getString("Vardas");
                String pavarde = rs.getString("Pavardė");
                teacher.setSurname(pavarde);
                try {
                    String query1 = "SELECT Dalykas FROM dalykas WHERE Pavardė='"+teacher.getSurname()+"'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery(query1);
                    while (rs1.next()) {
                        String dalykas = rs1.getString("Dalykas");
                        teacher.setName(vardas);
                        teacher.setSurname(pavarde);
                        teacher.setSubject(dalykas);
                        String info = String.format("%-20.15s %-20.15s %-15.10s ",
                                teacher.getName(),
                                teacher.getSurname(),
                                teacher.getSubject());

                        listModel.addElement(info);
                    }
                    JList list = new JList(listModel);
                    list.setBounds(10,25, 280,180);
                    frame.add(list);
                    st1.close();
                } catch (SQLException ex) {
                    System.out.println("Nesujungta");
                } finally {
                    try {
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException e) {

                    }
                }
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("Nesujungta");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {

            }
        }

        backButton.addActionListener(this);
        addSubjectButton.addActionListener(this);
        deleteSubjectButton.addActionListener(this);
        deleteTeacher.addActionListener(this);

        frame.add(backButton);
        frame.add(addSubjectButton);
        frame.add(nameLabel);
        frame.add(nameText);
        frame.add(surnameLabel);
        frame.add(surnameText);
        frame.add(subjectLabel);
        frame.add(subjectText);
        frame.add(messageLabel);
        frame.add(deleteSubjectButton);
        frame.add(deleteTeacher);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 460);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            Adminas adminas = new Adminas();
            frame.dispose();
        }
        if (e.getSource() == addSubjectButton){
            String name = nameText.getText();
            String surname = surnameText.getText();
            String subject = subjectText.getText();
            if (name.isEmpty() || surname.isEmpty() || subject.isEmpty()){
                System.out.println("Bloga įvestis");
            }else{
                DuomBaze duomBaze = new DuomBaze();
                boolean tikrinti = duomBaze.mokytojoPrisijungimas(name,surname);
                if (tikrinti == false){
                    System.out.println("Toks mokytojas neegzistuoja");
                }if (tikrinti == true){
                    boolean tikrinti2 = duomBaze.pridetiDalyka(surname,subject);
                    if (tikrinti2 == false){
                        System.out.println("Bloga įvestis");
                    }else if (tikrinti2 == true){
                        AdminMokytojas adminMokytojas = new AdminMokytojas();
                        frame.dispose();
                    }
                }
            }
        }
        if (e.getSource() == deleteSubjectButton){
            String name = nameText.getText();
            String surname = surnameText.getText();
            String subject = subjectText.getText();
            if (name.isEmpty() || surname.isEmpty() || subject.isEmpty()){
                System.out.println("Bloga įvestis ");
            }else{
                DuomBaze duomBaze = new DuomBaze();
                boolean tikrinti = duomBaze.mokytojoPrisijungimas(name,surname);
                if (tikrinti == false){
                    System.out.println("Toks mokytojas neegzistuoja ");
                }if (tikrinti == true){
                    boolean tikrinti2 = duomBaze.istrintiMokytojoDalyka(surname,subject);
                    if (tikrinti2 == false){
                        System.out.println("Bloga įvestis ");
                    }else if (tikrinti2 == true){
                        AdminMokytojas adminMokytojas = new AdminMokytojas();
                        frame.dispose();
                    }
                }
            }
        }
        if (e.getSource() == deleteTeacher){
            String vardas = nameText.getText();
            String pavarde = surnameText.getText();
            if (vardas.isEmpty() || pavarde.isEmpty()){
                System.out.println("Bloga įvestis ");
            }else{
                DuomBaze duomBaze = new DuomBaze();
                boolean tikrinti = duomBaze.mokytojoPrisijungimas(vardas,pavarde);
                if (tikrinti == false){
                    System.out.println("Toks mokytojas neegzistuoja ");
                }if (tikrinti == true){
                    boolean tikrinti2 = duomBaze.istrintiMokytoja(vardas, pavarde);
                    if (tikrinti2 == false){
                        System.out.println("Bloga įvestis ");
                    }else if (tikrinti2 == true){
                        AdminMokytojas adminMokytojas = new AdminMokytojas();
                        frame.dispose();
                    }
                }
            }
        }
    }
}
