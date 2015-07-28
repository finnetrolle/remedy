package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Entity
public class PrimaryGoal {

    private String name;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Campaign campaign;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Primary{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", campaign=" + campaign +
                '}';
    }
}
