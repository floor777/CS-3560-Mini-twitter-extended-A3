package com.company;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;

public class VisitorImplementation implements Visitor {
    // Class to get the count of visitors
    private int userCount = 0;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    // If a visitor is visited, iterate the count by 1
    public void visit(User user) {
        setUserCount(userCount + 1);
    }

    // If a group is visited, don't change the value
    @Override
    public void visit(Group group) {
       setUserCount(userCount);
    }
}
