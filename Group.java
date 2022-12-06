package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.UUID;

public class Group extends DefaultMutableTreeNode implements Composite {
    // ArrayList of type Composite to store the user and group nodes added to the group
    private ArrayList<Composite> compositeList = new ArrayList<>();
    private String groupID;
    private String groupName;
    private long creationTime = System.currentTimeMillis();

    public Group(String groupName) {
        this.groupID = UUID.randomUUID().toString();
        this.groupName = groupName;
    }

    public ArrayList<Composite> getCompositeList() {
        return compositeList;
    }
    public void setList(ArrayList<Composite> compositeArrayList) {
        this.compositeList = compositeArrayList;
    }


    // Visitor pattern accept method for visitors. Unlike users, it passes in each component of the group's component
    // array list into the accept method. Groups will be recursively called while users will simply be accepted
    // This makes use of the composite design pattern
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for(Composite C : compositeList) {
            C.accept(visitor);
        }

    }


    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }
}
