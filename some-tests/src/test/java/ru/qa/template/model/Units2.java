package ru.qa.template.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Units2 extends ForwardingSet<Unit2Data> {
    private Set<Unit2Data> delegate;

    public Units2(Units2 units2) {
        this.delegate = new HashSet<>(units2.delegate);
    }

    public Units2() {
        this.delegate = new HashSet<>();
    }

    public Units2(Collection<Unit2Data> units2) {
        this.delegate = new HashSet<>(units2);
    }

    @Override
    protected Set<Unit2Data> delegate() {
        return delegate;
    }

    public Units2 withAdded(Unit2Data unit2) {
        Units2 units2 = new Units2(this);
        units2.add(unit2);

        return units2;
    }

    public Units2 without(Unit2Data unit2) {
        Units2 units2 = new Units2(this);
        units2.remove(unit2);

        return units2;
    }
}
