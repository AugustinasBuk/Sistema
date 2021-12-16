package com.company;

import java.sql.*;


public class DuomBaze {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean studentoPrisijungimas(String pavarde, String slaptazodis) {
        boolean s = false;
        try {
            String query = "SELECT * FROM `studentas` WHERE Vardas=? and Pavardė=?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, pavarde);
            pst.setString(2, slaptazodis);
            rs = pst.executeQuery();

            if (rs.next()){
                s = true;
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return s;

    }


    public boolean mokytojoPrisijungimas(String username, String password) {
        boolean t = false;
        try {
            String query = "SELECT * FROM `mokytojas` WHERE Vardas=? and Pavardė =?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()){
                t = true;
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return t;

    }

    public Integer patikrinti(String surname, String group) {
        int sk = 0;
        try {
            String query = "SELECT * FROM `studentas` WHERE Pavardė=? and Grupė =?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, surname);
            pst.setString(2, group);
            rs = pst.executeQuery();

            if (rs.next()){
                sk = 1;
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return sk;

    }

    public Integer ivestiPazymi(String mokytojas, String studentas, String studentogr, String dalykas, String pazymys){
        int sk = 0;
        try {
            String query = "INSERT INTO pažymiai (mokytojas,studentas,studentogr,dalykas,pazymys) VALUES('" + mokytojas + "','" + studentas + "','" + studentogr + "','" + dalykas + "','" + pazymys + "')";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            sk = 1;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return sk;
    }


    public Integer atnaujintiPazymi(int id, String pazymys){
        int sk = 0;
        try {
            String query = "UPDATE pažymiai set pazymys='"+pazymys+"' where ID='"+id+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            sk = 1;

        } catch (SQLException ex) {
            sk = 0;
            System.out.println(ex);
        }
        return sk;
    }


    public boolean atnaujintiStudenta(int id, String grupe){
        boolean u = false;
        try {
            String query = "UPDATE studentas set Grupė='"+grupe+"' where ID='"+id+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            u = true;
        } catch (SQLException ex) {
            u = false;
            System.out.println(ex);
        }
        return u;
    }


    public boolean istrintiStudenta(int id){
       boolean d = false;
        try {
            String query = "delete from studentas where ID='"+id+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            d = true;;
        } catch (SQLException ex) {
            d = false;
            System.out.println(ex);
        }
        return d;
    }

    public boolean atnaujintiStudentoPazymius(String id, String grupe){
       boolean b = false;
        try {
            String query = "UPDATE pažymiai set studentogr='"+grupe+"' where ID='"+id+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            b = true;
        } catch (SQLException ex) {
            b = false;
            System.out.println(ex);
        }
        return b;
    }


    public boolean istrinti(String pavarde){
        boolean d2 = false;
        try {
            String query = "delete from pažymiai where studentas='"+pavarde+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            d2 = true;
        } catch (SQLException ex) {
            d2 = false;
            System.out.println(ex);
        }
        return d2;
    }

    public String pirmasDalykas(String pavarde) {
        String dalykas = null;
        try {
            String query = "SELECT Dalykas FROM `dalykas` WHERE Pavardė='"+pavarde+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema", "root", "");
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            if (rs.next()){
                dalykas = rs.getString("dalykas");
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return dalykas;
    }

    public boolean pridetiStudenta(String vardas, String pavarde, String grupe){
        boolean ps = false;
        try {
            String query = "INSERT INTO studentas (Vardas,Pavardė,Grupė) VALUES('" + vardas + "','" + pavarde + "','" + grupe + "')";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            ps = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return ps;
    }

    public boolean pridetiDalyka(String pavarde, String dalykas){
        boolean i = false;
        try {
            String query = "INSERT INTO dalykas (Pavardė,Dalykas) VALUES('" + pavarde + "','" + dalykas + "')";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            i = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return i;
    }

    public boolean istrintiMokytojoDalyka(String pavarde, String dalykas){
        boolean d = false;
        try {
            String query = "delete from dalykas where Pavardė='"+pavarde+"' AND Dalykas='"+dalykas+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            d = true;
        } catch (SQLException ex) {
            d = false;
            System.out.println(ex);
        }
        return d;
    }

    public boolean istrintiMokytoja(String vardas, String pavarde){
        boolean d = false;
        try {
            String query = "delete from mokytojas where Vardas='"+vardas+"' AND  Pavardė='"+pavarde+"'";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();

            try {
                String query1 = "delete from dalykas where Pavardė='" + pavarde + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                        "", "root", "");
                Statement st1 = con.createStatement();
                st1.executeUpdate(query1);
                con.close();
            }catch (SQLException e){
                System.out.println(e);
            }


            d = true;
        } catch (SQLException ex) {
            d = false;
            System.out.println(ex);
        }
        return d;
    }

    public boolean pridetiMokytoja(String vardas, String pavarde, String dalykas){
        boolean it = false;
        try {
            String query = "INSERT INTO mokytojas (Vardas, Pavardė) VALUES('" + vardas + "','" + pavarde + "')";
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                    "", "root", "");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            con.close();
            try {
                String query1 = "INSERT INTO dalykas (Pavardė, Dalykas) VALUES('" + pavarde + "','" + dalykas + "')";
                con = DriverManager.getConnection("jdbc:mysql://localhost/sistema" +
                        "", "root", "");
                Statement st1 = con.createStatement();
                st1.executeUpdate(query1);
                con.close();
            }catch (SQLException e){
                System.out.println(e);
            }
            it = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return it;
    }

}
