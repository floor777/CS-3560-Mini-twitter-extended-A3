package com.company;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;


public abstract class Subject extends DefaultMutableTreeNode{
    // Saving a list of observrs in each Subject

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    // Method to attach an observer to the observer ArrayList of the subject instance
    public void attach(Observer observer) {
        observers.add(observer);
    }


    // Notify observers to update with the subject instance passed in
    public void notifyObservers() {
        for(Observer o: observers) {
            o.update(this);

        }

    }

    public ArrayList<Observer> getObservers() {
        return this.observers;
    }










}
