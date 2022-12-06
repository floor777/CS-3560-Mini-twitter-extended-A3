package com.company;

public class lastUpdatedUserVisitorImplementation implements Visitor{

    private long lastUpdatedTime = 0;
    private User userToReturn;
    @Override
    public void visit(User user) {
        if(user.getLastUpdateTime() == -1 ) {



        }
        else{
            if(user.getLastUpdateTime() > lastUpdatedTime) {
                lastUpdatedTime = user.getLastUpdateTime();
                userToReturn = user;
            }


        }

    }

    @Override
    public void visit(Group group) {

    }

    public User getUserToReturn() {
        return userToReturn;
    }

    public void setUserToReturn(User userToReturn) {
        this.userToReturn = userToReturn;
    }
}
