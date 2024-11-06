package hu.nje.hibernatefxdemo;

import javax.persistence.*;

@Entity
@Table(name = "korlatozasok")
public class Korlatozas {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "id")
    private int id;

    @Column(name = "utszam")
    private Integer utszam;

    @Column(name = "kezdet")
    private Float kezdet;

    @Column(name = "veg")
    private Float veg;

    @Column(name = "telepules")
    private String telepules;

    @Column(name = "mettol")
    private String mettol;

    @Column(name = "meddig")
    private String meddig;

    @OneToOne
    @JoinColumn(name = "mertekid")
    private Mertek mertek;

    @ManyToOne
    @JoinColumn(name = "megnevid")
    private Megnevezes megnevezes;

    @Column(name = "sebesseg")
    private Integer sebesseg;

    public Korlatozas() {
    }

    public Korlatozas(Integer utszam, Float kezdet, Float veg, String telepules, String mettol, String meddig,  Mertek mertek, Megnevezes megnevezes, Integer sebesseg) {
        this.utszam = utszam;
        this.kezdet = kezdet;
        this.veg = veg;
        this.telepules = telepules;
        this.mettol = mettol;
        this.meddig = meddig;
        this.mertek = mertek;
        this.megnevezes = megnevezes;
        this.sebesseg = sebesseg;
    }


    public Integer getUtszam() {
        return utszam;
    }

    public void setUtszam(Integer utszam) {
        this.utszam = utszam;
    }

    public Float getKezdet() {
        return kezdet;
    }

    public void setKezdet(Float kezdet) {
        this.kezdet = kezdet;
    }

    public Float getVeg() {
        return veg;
    }

    public void setVeg(Float veg) {
        this.veg = veg;
    }

    public String getTelepules() {
        return telepules;
    }

    public void setTelepules(String telepules) {
        this.telepules = telepules;
    }

    public String getMettol() {
        return mettol;
    }

    public void setMettol(String mettol) {
        this.mettol = mettol;
    }

    public String getMeddig() {
        return meddig;
    }

    public void setMeddig(String meddig) {
        this.meddig = meddig;
    }

    public Integer getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(Integer sebesseg) {
        this.sebesseg = sebesseg;
    }

    public Mertek getMertek() {
        return mertek;
    }

    public void setMertek(Mertek mertek) {
        this.mertek = mertek;
    }

    public Megnevezes getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(Megnevezes megnevezes) {
        this.megnevezes = megnevezes;
    }

    @Override
    public String toString(){
        return this.getUtszam().toString() + " " + this.getTelepules() + " " + this.getMegnevezes() + " " + this.getMertek() + " " + this.getSebesseg();
    }

}
