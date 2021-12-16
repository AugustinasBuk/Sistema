package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminStudentas implements ActionListener {

    Connection con = null;

    public static StudentasSetGet studentasSetGet = new StudentasSetGet();

    JFrame frame = new JFrame();
    JButton backButton = new JButton("Atgal");
    JButton updateButton = new JButton("Atnaujinti");
    JButton deleteButton = new JButton("Ištrinti");

    JLabel idLabel = new JLabel(" ID");
    JTextField idText = new JTextField();
    JLabel groupLabel = new JLabel(" Grupė");
    JTextField groupText = new JTextField();
    JLabel surnameLabel = new JLabel("Pavardė");
    JTextField surnameText = new JTextField();

    JLabel messageLabel = new JLabel("");

    DefaultListModel listModel = new DefaultListModel();

    AdminStudentas(){
        backButton.setBounds(300,25,90,25);
        idLabel.setBounds(10,285,30,25);
        idText.setBounds(10,310, 30, 25);
        groupLabel.setBounds(60, 285, 50, 25);
        groupText.setBounds(50, 310, 50, 25);
        surnameLabel.setBounds(60, 285, 75, 25);
        surnameText.setBounds(50,310,75,25);
        updateButton.setBounds(300, 160, 90, 25);
        deleteButton.setBounds(300,185, 90, 25 );
        messageLabel.setBounds(30, 340, 200, 25 );



        try {
            String query = "SELECT ID,Vardas,Pavardė,Grupė FROM studentas";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String vardas = rs.getString("Vardas");
                String pavarde = rs.getString("Pavardė");
                String grupe = rs.getString("Grupė");

                studentasSetGet.setId(id);
                studentasSetGet.setVardas(vardas);
                studentasSetGet.setPavarde(pavarde);
                studentasSetGet.setGrupe(grupe);

                String info = String.format("%-2s %-25.15s %-25.15s %-10.5s",
                        studentasSetGet.getId(),
                        studentasSetGet.getVardas(),
                        studentasSetGet.getPavarde(),
                        studentasSetGet.getGrupe());

                listModel.addElement(info);
            }
            JList list = new JList(listModel);
            list.setBounds(10,25, 280,200);
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

        backButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        frame.add(backButton);
        frame.add(idLabel);
        frame.add(idText);
        frame.add(groupLabel);
        frame.add(groupText);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(messageLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            Adminas adminas = new Adminas();
            frame.dispose();
        }
        if (e.getSource() == updateButton){
            String id = idText.getText();
            String group = groupText.getText();
            if (id.isEmpty() || group.isEmpty()) {
                messageLabel.setText("Wrong input");
            }else {
                try {
                    int id1 = Integer.parseInt(id);
                    DuomBaze duomBaze = new DuomBaze();
                    boolean tikrinti = duomBaze.atnaujintiStudenta(id1, group);
                    if (tikrinti == false) {
                        messageLabel.setText("Wrong input");
                    }

                    else if (tikrinti == true) {
                        boolean tikrinti2 = duomBaze.atnaujintiStudentoPazymius(id, group);


                        if (tikrinti2 == false){
                            messageLabel.setText("Wrong input");

                        }else if (tikrinti2 == true) {
                            AdminStudentas adminStudentas = new AdminStudentas();
                            frame.dispose();
                        }
                    }

                } catch (Exception ex) {
                    messageLabel.setText("Wrong input");
                }
            }
        }

        if (e.getSource() == deleteButton){
            String id = idText.getText();
            String surname = surnameText.getText();
            if (id.isEmpty()) {
                messageLabel.setText("Wrong input");
            }else {
                try {
                    int id1 = Integer.parseInt(id);
                    DuomBaze duomBaze = new DuomBaze();
                   boolean tikrinti = duomBaze.istrintiStudenta(id1);
                    if (tikrinti == false) {
                        messageLabel.setText("Wrong input");
                    } else if (tikrinti == true) {
                        boolean tikrinti2 = duomBaze.istrinti(surname);
                        if (tikrinti2 == false){
                            messageLabel.setText("Wrong input");
                        }else if (tikrinti2 == true) {
                            AdminStudentas adminStudentas = new AdminStudentas();
                            frame.dispose();
                        }
                    }
                } catch (Exception ex) {
                    messageLabel.setText("Wrong input");
                }
            }
        }
    }
}
