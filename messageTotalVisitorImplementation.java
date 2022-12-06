package com.company;

import javax.swing.*;

public class messageTotalVisitorImplementation implements Visitor{

    // Stores the count of messages by the end of the call
    private int messageCount = 0;
    // Gets the user's messageListModel and gets the size of it because it is equal to the amount of messages that are
    // stored in the user instance
    public void visit(User user) {
        DefaultListModel messages = user.getMessageListModel();
        setMessageCount(getMessageCount() + messages.getSize());

    }

    // If a group is visited, don't change the count
    @Override
    public void visit(Group group) {
        setMessageCount(getMessageCount());

    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}
