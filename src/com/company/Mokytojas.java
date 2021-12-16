package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.lang.*;

public class Mokytojas implements ActionListener {
    Connection con = null;
    public static Pazymiai pazymiaiBook = new Pazymiai();

    JFrame frame = new JFrame();
    String name = null;
    String teacherSurname = null;
    JButton logoutButton = new JButton("Atsijungti");
    JLabel helloLabel = new JLabel("");
    JLabel idLabel = new JLabel("ID");
    JLabel surnameLabel = new JLabel("Pavardė");
    JLabel groupLabel = new JLabel("Grupė");
    JLabel subjectLabel = new JLabel("Dalykas");
    JLabel gradeLabel = new JLabel("Pažymys");
    JLabel messageLabel = new JLabel("");
    JTextField idText = new JTextField();
    JTextField surnameText = new JTextField();
    JTextField gruopText = new JTextField();
    String choice = "";
    JComboBox choiceList = new JComboBox();
    JTextField gradeText = new JTextField();
    DefaultListModel listModel = new DefaultListModel();
    JButton addButton = new JButton("Prideti pažymį");
    JButton updateButton = new JButton("Atnaujinti pažymį");

    Mokytojas(String name1, String surname){
        name = name1;
        teacherSurname= surname;
        logoutButton.setBounds(130,470,150,40);
        helloLabel.setBounds(50,45,250,25);

        idLabel.setBounds(10,365,20,25);
        idText.setBounds(10,395, 20, 25);
        surnameLabel.setBounds(55, 365, 100, 25);
        surnameText.setBounds(35,395, 100, 25 );
        groupLabel.setBounds(265, 365, 50, 25);
        gruopText.setBounds(265, 395, 50, 25);
        subjectLabel.setBounds(160, 365, 75, 25);
        gradeLabel.setBounds(320, 365, 60, 25);
        gradeText.setBounds(320,395, 50, 25);

        addButton.setBounds(360, 150,130,30);
        updateButton.setBounds(360, 200, 130, 30);
        messageLabel.setBounds(15, 475, 200, 30);


        DuomBaze duomBaze = new DuomBaze();
        choice = duomBaze.pirmasDalykas(surname); // Parenka subjecta kad atitiktu comboBox
        logoutButton.setBackground(Color.red);


        try {
            String query = "SELECT ID,studentas,studentogr,dalykas,pazymys FROM pažymiai WHERE mokytojas='"+surname+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String student = rs.getString("studentas");
                String group = rs.getString("studentogr");
                String subject = rs.getString("dalykas");
                int grade = rs.getInt("pazymys");

                pazymiaiBook.setId(id);
                pazymiaiBook.setStudentas(student);
                pazymiaiBook.setGrupe(group);
                pazymiaiBook.setDalykas(subject);
                pazymiaiBook.setPazymys(grade);

                String info = String.format("%-1d %-25.15s %-10.10s %-20.18s %-1d ", pazymiaiBook.getId(), pazymiaiBook.getStudentas(), pazymiaiBook.getDalykas(), pazymiaiBook.getGrupe(), pazymiaiBook.getPazymys());
                listModel.addElement(info);
            }
            JList list = new JList(listModel);
            list.setBounds(10,105, 350,200);
            frame.add(list);
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
        try {
            String query = "SELECT Dalykas FROM dalykas WHERE Pavardė='"+surname+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String subject = rs.getString("Dalykas");
                choiceList.addItem(subject);
            }
            choiceList.setBounds(140,395,110,25);
            choiceList.addActionListener(this);
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

        logoutButton.addActionListener(this);
        addButton.addActionListener(this);
        updateButton.addActionListener(this);

        frame.add(logoutButton);
        frame.add(helloLabel);
        frame.add(idLabel);
        frame.add(idText);
        frame.add(surnameLabel);
        frame.add(surnameText);
        frame.add(groupLabel);
        frame.add(gruopText);
        frame.add(subjectLabel);
        frame.add(choiceList);
        frame.add(gradeLabel);
        frame.add(gradeText);
        frame.add(addButton);
        frame.add(messageLabel);
        frame.add(updateButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == choiceList) {
            JComboBox cb = (JComboBox) e.getSource();
            choice = (String) cb.getSelectedItem();
        }
        if (e.getSource() == logoutButton) {
            Prisijungimas prisijungimas = new Prisijungimas();
            frame.dispose();
        }
        if (e.getSource() == addButton) {
            String surname = surnameText.getText();
            String group = gruopText.getText();
            String grade = gradeText.getText();
            DuomBaze duomBaze = new DuomBaze();
            int ats = duomBaze.patikrinti(surname, group);

            if (ats == 0) {
                System.out.println ("Tokio studento nera ");
            } else if (ats == 1 && !grade.isEmpty()) {
                try {
                    int totalGrade = Integer.parseInt(grade);
                    if (totalGrade <= 10) {
                        int ats1 = duomBaze.ivestiPazymi(teacherSurname, surname, group, choice, grade);
                        System.out.print("");

                        if (ats1 == 0) {
                            System.out.println("Bloga Įvestis ");
                        } else if (ats1 == 1) {
                            System.out.println("");
                            Mokytojas teacher = new Mokytojas(name, teacherSurname);
                            frame.dispose();
                        }
                    } else {
                        System.out.println("Pažymys turi būti tarp (1-10) ");}
                }catch (Exception ed){
                    System.out.println("Bloga Įvestis ");}
            } else if (grade.isEmpty()) {
                System.out.println("Pažymys turi buti tarp (1-10) ");}
        }


        if (e.getSource() == updateButton) {
            String id = idText.getText();
            String surname = surnameText.getText();
            String group = gruopText.getText();
            String grade = gradeText.getText();
            DuomBaze duomBaze = new DuomBaze();
            if (!id.isEmpty()) {
                int ats = duomBaze.patikrinti(surname, group);
                if (ats == 0) {
                    System.out.println("Toks studentas neegzistuoja ");

                } else if (ats == 1 && !grade.isEmpty()) {
                    try {
                        int totalGrade = Integer.parseInt(grade);
                        if (totalGrade <= 10) {
                            try {
                                int id1 = Integer.parseInt(id);
                                int ats1 = duomBaze.atnaujintiPazymi(id1, grade);
                                System.out.println("");
                                if (ats1 == 0) {
                                    System.out.println("Bloga Įvestis ");

                                } else if (ats1 == 1) {
                                    System.out.println("");
                                    Mokytojas teacher = new Mokytojas(name, teacherSurname);
                                    frame.dispose();
                                }
                            } catch (Exception ex) {
                                System.out.println("Bloga įvestis ");}
                        } else {
                            System.out.println("Pažymys turi buti tarp 1 ir 10 ");}
                    } catch (Exception ec) {
                        System.out.println("Bloga įvestis ");}
                } else if (grade.isEmpty()) {
                    System.out.println("Pažymys turi buti tarp 1 ir 10 ");}
            } else {
                System.out.println("ID laukas tuscias ");}
        }

    }
}
