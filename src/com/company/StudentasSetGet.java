package com.company;

public class StudentasSetGet {

    private int id;
    private String vardas;
    private String pavarde;
    private String grupe;
    private int pazymys;


    public Integer getId(){return id;}
    public String getVardas(){return vardas;}
    public String getPavarde(){return pavarde;}
    public String getGrupe(){return grupe;}
    public int getPazymys(){return pazymys;}

    public void setId(int id) {this.id = id;}
    public void setVardas(String vardas) {this.vardas = vardas;}
    public void setPavarde(String pavarde) {this.pavarde = pavarde;}
    public void setGrupe(String grupe) {this.grupe = grupe;}
    public void setPazymys(int pazymys) {this.pazymys = pazymys;}
}
