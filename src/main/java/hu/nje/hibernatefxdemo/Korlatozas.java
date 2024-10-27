package hu.nje.hibernatefxdemo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "korlatozasok")
public class Korlatozas {
    @Id
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

    @Column(name = "megnevid")
    private Integer megnevid;

    @Column(name = "mertekid")
    private Integer mertekid;

    @Column(name = "sebesseg")
    private Integer sebesseg;

    public Korlatozas() {
    }


    public Korlatozas(Integer utszam, Float kezdet, Float veg, String telepules, String mettol, String meddig, Integer megnevid, Integer mertekid, Integer sebesseg) {
        this.utszam = utszam;
        this.kezdet = kezdet;
        this.veg = veg;
        this.telepules = telepules;
        this.mettol = mettol;
        this.meddig = meddig;
        this.megnevid = megnevid;
        this.mertekid = mertekid;
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

    public Integer getMegnevid() {
        return megnevid;
    }

    public void setMegnevid(Integer megnevid) {
        this.megnevid = megnevid;
    }

    public Integer getMertekid() {
        return mertekid;
    }

    public void setMertekid(Integer mertekid) {
        this.mertekid = mertekid;
    }

    public Integer getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(Integer sebesseg) {
        this.sebesseg = sebesseg;
    }
}
