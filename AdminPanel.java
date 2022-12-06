package com.company;

public class AdminPanel extends GUI {

    // Creating a static instance of the class
    private static volatile AdminPanel AP  = null;

    // Private constructor
    private AdminPanel() {

    }

    // static getInstance method to make sure there is one and only one instance
    public static AdminPanel getInstance()
    {
        if (AP == null)
        {
            // Protect thread
            synchronized (AdminPanel.class)
            {
                // Make sure only one thread is reaching here after the previous check
                if (AP == null)
                    AP = new AdminPanel();
            }
        }
        return AP;
    }
}
