package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Entity
public class Constellation {

    @Id
    private String name;

    @OneToMany(mappedBy = "constellation")
    private Collection<SolarSystem> solarSystems;

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Constellation)) return false;

        Constellation that = (Constellation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    public void setSolarSystems(Collection<SolarSystem> solarSystems) {
        this.solarSystems = solarSystems;
    }
}
