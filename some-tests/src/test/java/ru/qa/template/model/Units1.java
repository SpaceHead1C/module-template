package ru.qa.template.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Units1 extends ForwardingSet<Unit1Data> {
    private Set<Unit1Data> delegate;

    public Units1(Units1 units1) {
        this.delegate = new HashSet<>(units1.delegate);
    }

    public Units1() {
        this.delegate = new HashSet<>();
    }

    public Units1(Collection<Unit1Data> units1) {
        this.delegate = new HashSet<>(units1);
    }

    @Override
    protected Set<Unit1Data> delegate() {
        return delegate;
    }

    public Units1 withAdded(Unit1Data unit1) {
        Units1 units1 = new Units1(this);
        units1.add(unit1);

        return units1;
    }

    public Units1 without(Unit1Data unit1) {
        Units1 contacts = new Units1(this);
        contacts.remove(unit1);

        return contacts;
    }
}