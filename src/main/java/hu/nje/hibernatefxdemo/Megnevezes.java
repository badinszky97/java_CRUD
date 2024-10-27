package hu.nje.hibernatefxdemo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "megnevezes")
public class Megnevezes {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nev")
    private String nev;

    public Megnevezes() {
    }

    public Megnevezes(String nev) {
        this.nev = nev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
}
