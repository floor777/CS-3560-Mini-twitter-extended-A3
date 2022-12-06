package com.company;

import java.util.ArrayList;
import java.util.HashSet;

public class IDValidationVisitorImplementation implements Visitor{
    private boolean unique = true;

    HashSet IDSet = new HashSet();
    @Override
    public void visit(User user) {
        if(IDSet.contains(user)) {
            unique = false;
        }
        IDSet.add(IDSet);



    }

    @Override
    public void visit(Group group) {
        IDSet.add(group);

    }

    public boolean isUnique() {
        return this.unique;
    }
}
