package com.company;

import javax.swing.*;

public interface Visitor {
    void visit(User user);
    void visit(Group group);

}
