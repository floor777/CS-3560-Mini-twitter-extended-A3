package com.company;

import java.util.ArrayList;

public class positivePercentageVisitorImplementation implements Visitor{

    private double positiveWordOccurence = 0;
    // Setting the positive word to search for as "meow"
    private String positiveWord = "meow";

    @Override
    public void visit(User user) {
        // If a user is visited, iterate through its messageArrayList and check if the message at index i contains
        // the positive word and if it does, iterate the count by 1
        ArrayList<String> messages = user.getMessageArrayList();
        for(int i = 0; i < messages.size(); i++) {
            if(messages.get(i).contains(positiveWord)) {
                positiveWordOccurence++;
            }
        }
    }
    @Override
    public void visit(Group group) {
        // If a group is visited, don't change the value
        setPositiveWordOccurence(getPositiveWordOccurence());

    }

    public double getPositiveWordOccurence() {
        return positiveWordOccurence;
    }

    public void setPositiveWordOccurence(double positivePercentage) {
        this.positiveWordOccurence = positivePercentage;
    }




}
