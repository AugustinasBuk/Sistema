package com.company;

public class Pazymiai {

    private int id;
    private String mokytojas;
    private String studentas;
    private String grupe;
    private String dalykas;
    private int pazymys;


    public int getId() {return id;}
    public int getPazymys() {return pazymys;}
    public String getMokytojas() {return mokytojas;}
    public String getGrupe() {return grupe;}
    public String getStudentas() {return studentas;}
    public String getDalykas() {return dalykas;}

    public void setId(int id) {this.id = id;}
    public void setPazymys(int pazymys) {this.pazymys = pazymys;}
    public void setGrupe(String grupe) {this.grupe = grupe;}
    public void setMokytojas(String mokytojas) {this.mokytojas = mokytojas;}
    public void setStudentas(String studentas) {this.studentas = studentas;}
    public void setDalykas(String dalykas) {this.dalykas = dalykas;}
}
