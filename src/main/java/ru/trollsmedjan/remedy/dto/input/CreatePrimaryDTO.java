package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 29.07.2015.
 */
public class CreatePrimaryDTO extends BaseData {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreatePrimaryDTO{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
