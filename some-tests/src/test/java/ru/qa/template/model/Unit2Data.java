package ru.qa.template.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("group")
@Entity
@Table(name = "table_2")
public class Unit2Data {
    @XStreamOmitField
    @Id
    @Column(name = "table2_id")
    private int id = Integer.MAX_VALUE;
    @Expose
    @Column(name = "text_field")
    @Type(type = "text")
    private String textField;
    @ManyToMany(mappedBy = "units2")
    private Set<Unit1Data> units1 = new HashSet<>();

    public String getTextField() {
        return textField;
    }

    public int getId() {
        return id;
    }

    public Units1 getUnits1() {
        return new Units1(units1);
    }

    public Unit2Data withTextField(String textField) {
        this.textField = textField;

        return this;
    }

    public Unit2Data withId(int id) {
        this.id = id;

        return this;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "id='" + id + '\'' +
                ", textField='" + textField + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit2Data groupData = (Unit2Data) o;

        if (id != groupData.id) return false;
        return textField != null ? !textField.equals(groupData.textField) : groupData.textField == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (textField != null ? textField.hashCode() : 0);
        return result;
    }
}
