package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Entity
public class SolarSystem {

    @Id
    private String name;

    @ManyToOne
    private Constellation constellation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
}
