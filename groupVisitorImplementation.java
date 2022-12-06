package com.company;

public class groupVisitorImplementation implements  Visitor{
    // Setting a count to store the total group count
    private int groupCount = 0;

    @Override
    // If a user is passed, don't change the value since we want to get the count of groups
    public void visit(User user) {
        setGroupCount(groupCount);
    }

    @Override
    // If a group is passed, increment the value of groupCount by 1
    public void visit(Group group) {
        setGroupCount(groupCount + 1);

    }

    // I subtracted 1 here because I am not counting the root group toward the total group count because it is not added
    // by the client
    public int getGroupCount() {
        return groupCount - 1;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

}
