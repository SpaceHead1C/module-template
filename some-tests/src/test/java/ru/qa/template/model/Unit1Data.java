package ru.qa.template.model;

import org.hibernate.annotations.Type;
import ru.qa.template.tests.TestBase;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "table_1")
public class Unit1Data {
    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;
    @Column(name = "text_field")
    private String textField;
    @Transient
    private String bigTextField;
    @Column(name = "photo")
    @Type(type = "text")
    private String photo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "unit1_in_units2",
            joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "table2_id"))
    private Set<Unit2Data> units2 = new HashSet<>();

    public String getTextField() {
        return textField;
    }

    public String getBigTextField() {
        return bigTextField;
    }

    public int getId() {
        return id;
    }

    public File getPhoto() {
        return new File(photo);
    }

    public Units2 getUnits2() {
        return new Units2(units2);
    }

    public Unit1Data withPhoto(File photo) {
        this.photo = photo.getPath();

        return this;
    }

    public Unit1Data withTextField(String textField) {
        this.textField = textField;

        return this;
    }

    public Unit1Data withBigTextField(String bigTextField) {
        this.bigTextField = bigTextField;

        return this;
    }

    public Unit1Data withId(int id) {
        this.id = id;

        return this;
    }

    public Unit1Data inUnit2(Unit2Data unit2) {
        units2.add(unit2);

        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", textField='" + textField + '\'' +
                ", bigTextField='" + bigTextField + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit1Data contactData = (Unit1Data) o;

        if (id != contactData.id) return false;
        return textField != null ? textField.equals(contactData.textField) : contactData.textField == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (textField != null ? textField.hashCode() : 0);
        return result;
    }
}
