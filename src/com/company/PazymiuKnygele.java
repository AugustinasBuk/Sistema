package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PazymiuKnygele implements ActionListener {

    Connection con = null;

    public static Pazymiai pazymiaiBook = new Pazymiai();

    String vardas = null;
    String pavarde = null;
    String grupe = null;

    JFrame frame = new JFrame();
    JButton atsijungtiButton = new JButton("Atsijungti");
    JLabel knygLabel = new JLabel("Pažymiu knygele");


    DefaultListModel listModel = new DefaultListModel();

    JList sarasas = new JList(listModel);



    PazymiuKnygele(String vardas1){
        vardas = vardas1 ;
        //System.out.println(name);
        atsijungtiButton.setBounds(100,250,120,30);
        knygLabel.setBounds(100,80,140, 20 );
        sarasas.setBounds(10, 105, 320, 100);

        knygLabel.setFont(new Font("Impact", Font.BOLD, 20));
        atsijungtiButton.setBackground(Color.red);



        try{
            String query = "SELECT Pavardė,Grupė FROM studentas WHERE Vardas='" +vardas+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                pavarde = rs.getString("Pavardė");
                grupe = rs.getString("Grupė");
            }
            st.close();
        }catch (Exception e){
            System.out.println("Nesujungta");
        }finally {
            try{
                if (con != null){
                    con.close();
                }
            }catch (SQLException ex) {}
        }

        try {
            String query1 = "SELECT mokytojas,dalykas,pazymys FROM pažymiai WHERE studentas='"+pavarde+"' AND studentogr='"+grupe+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);

            while (rs.next()) {
                String teacher = rs.getString("mokytojas");
                int grade = rs.getInt("pazymys");
                String subject = rs.getString("dalykas");

                pazymiaiBook.setMokytojas(teacher);
                pazymiaiBook.setDalykas(subject);
                pazymiaiBook.setPazymys(grade);


                 String info = String.format("%-10.25s %-10.16s %-1d", pazymiaiBook.getMokytojas(), pazymiaiBook.getDalykas(), pazymiaiBook.getPazymys() );

                listModel.addElement(info);

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
        atsijungtiButton.addActionListener(this);

        frame.add(atsijungtiButton);
        frame.add(knygLabel);
        frame.add(sarasas);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== atsijungtiButton){
            Prisijungimas prisijungimas = new Prisijungimas();
            frame.dispose();

        }
    }
}
