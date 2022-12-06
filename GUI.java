package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GUI extends JPanel {
    private JTree tree;
    private JTextField userIDTextField, groupIDTextField;

    private JButton addUserButton, addGroupButton, openUserViewButton, showUserTotalButton, verifyIDsButton;
    private JButton showGroupTotalButton, showMessagesTotalButton, showPositivePercentageButton, lastUpdatedUser;

    // Stores the users as they are made. Used to find which user's userView should be opened
    private ArrayList<User> userArrayList;
    private ArrayList<String> userNameArrayList;
    private boolean uniqueIDs = true;

    public GUI() {super(new GridLayout(1, 0));

        userArrayList = new ArrayList<>(10);
        userNameArrayList = new ArrayList<>(10);


        // Creating the root node
        Group top = new Group("root");

        //Creating the treeModel used for the tree. Used TreeModel to have the tree auto-refresh on changes
        DefaultTreeModel treeModel = new DefaultTreeModel(top);

        // Create tree that allows one selection at a time
        tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Create components for rightPanel
        userIDTextField = new JTextField();
        addUserButton = new JButton("Add User");
        groupIDTextField = new JTextField();
        addGroupButton = new JButton("Add Group");
        openUserViewButton = new JButton("Open User View");
        showUserTotalButton = new JButton("Show User Total");
        showGroupTotalButton = new JButton("Show Group Total");
        showMessagesTotalButton = new JButton("Show Messages Total");
        showPositivePercentageButton = new JButton("Show Positive Percentage Total");
        verifyIDsButton = new JButton("Validate ID");
        lastUpdatedUser = new JButton("Find last updated user");



        //Create the treeView scroll pane with the Jtree instance as an argument then pass it to the container
        JScrollPane treeView = new JScrollPane(tree);
        add(treeView);


        //Create the parent rightPanel and the button and text field panels
        JPanel rightPanel = new JPanel(new GridLayout(4, 0, 0, 50));
        // Made it 2x2 to fit the 2 pairs of 2 buttos and their text fields
        JPanel upperRightPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        JPanel middleRightPanel = new JPanel();
        // Made it 2x2 to fit the 4 buttons
        JPanel lowerRightPanel = new JPanel(new GridLayout(3, 2, 10, 5));



        //Create the scroll pane to add the text fields and buttons to and do so
        JScrollPane buttonView = new JScrollPane(rightPanel);
        upperRightPanel.add(userIDTextField);
        upperRightPanel.add(addUserButton);
        upperRightPanel.add(groupIDTextField);
        upperRightPanel.add(addGroupButton);
        middleRightPanel.add(openUserViewButton);
        lowerRightPanel.add(showUserTotalButton);
        lowerRightPanel.add(showGroupTotalButton);
        lowerRightPanel.add(showMessagesTotalButton);
        lowerRightPanel.add(showPositivePercentageButton);
        lowerRightPanel.add(verifyIDsButton);
        lowerRightPanel.add(lastUpdatedUser);



        // Adding the individual panels to the parent rightPanel panel
        rightPanel.add(upperRightPanel);
        rightPanel.add(middleRightPanel);
        rightPanel.add(lowerRightPanel);

        verifyIDsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IDValidationVisitorImplementation IDVisitor = new IDValidationVisitorImplementation();
                top.accept(IDVisitor);
                boolean isUnique = IDVisitor.isUnique();
                System.out.println(isUnique);


            }
        });
        lastUpdatedUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastUpdatedUserVisitorImplementation lastUpdatedUserVisitor = new lastUpdatedUserVisitorImplementation();
                top.accept(lastUpdatedUserVisitor);
                User user = lastUpdatedUserVisitor.getUserToReturn();
                System.out.println("last updated user: " + user.getUserName());

            }
        });


        // addUserButton action listener added. I preferred the creation of the actionPerformed method
        // at the same location instead of separate to keep the code more concise
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser(top, userIDTextField.getText());
                treeModel.reload();

            }
        });

        // addGroupButton action listener added
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup(top, groupIDTextField.getText());
                treeModel.reload();
            }
        });

        // openUserViewButton action listener added
        openUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Creating a node to get the last selected user. If it was a user, open
                // the chosen user's user view. Otherwise, alert the client that they tried to perform the action on a
                // group node
                DefaultMutableTreeNode selectedUser  = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(!selectedUser.getAllowsChildren()) {
                    createAndShowUserGUI((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
                }
                else {
                    JOptionPane.showMessageDialog(null,
                             "Error: A group was selected",
                            "Error ", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // showUserTotalButton action listener added
        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creating a new visitor for user and passing it into the accept method called on the root node
                // visitor pattern used here
                VisitorImplementation userVisitor = new VisitorImplementation();
                top.accept(userVisitor);
                JOptionPane.showMessageDialog(null,
                        "User count: " + userVisitor.getUserCount(),
                        "User Count", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // showGroupTotalButton action listener added
        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creating a new visitor for group and passing it into the accept method called on the root node
                // visitor pattern used here
                groupVisitorImplementation groupVisitor = new groupVisitorImplementation();
                top.accept(groupVisitor);
                JOptionPane.showMessageDialog(null,
                        "Group count: " + groupVisitor.getGroupCount(),
                        "Group Count", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listener to the showMessagesTotalButton
        showMessagesTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creates a message Visitor and passes it to the accept method called on the root node
                messageTotalVisitorImplementation messageVisitor = new messageTotalVisitorImplementation();

                top.accept(messageVisitor);

                // Saving the value of the getCount call to display it
                int messageCount = messageVisitor.getMessageCount();

                JOptionPane.showMessageDialog(null,
                        "Message count: " + messageCount,
                        "Message Count", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // showPositivePercentageButton action listener added
        showPositivePercentageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Creatng a new messageVisitor to pass it into the accept call on root and saving the value of the
                // getCount() call
                messageTotalVisitorImplementation messageVisitor = new messageTotalVisitorImplementation();

                top.accept(messageVisitor);
                int messageCount = messageVisitor.getMessageCount();

                // Create a new positivePercentageVisitor instance and pass it into the accept method called on root
                // Save the value of the getPositiveWordOccurence call into a variable
                positivePercentageVisitorImplementation positivePercentageVisitor = new positivePercentageVisitorImplementation();
                top.accept(positivePercentageVisitor);
                double positivePercentage = positivePercentageVisitor.getPositiveWordOccurence();
                // Calls the calculator of the positive percentage and stores it in a variable
                positivePercentage = calculatePositivePercentage(positivePercentage, messageCount);

                // Displays it
                JOptionPane.showMessageDialog(null,
                        "Positive messages percentage: %" + positivePercentage,
                        "Positive messages percentage", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        //Add the scroll panes to a new horizontally-split split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(buttonView);

        // Setting the dimensions of the two views and the splitPane used to contain both of them
        Dimension minimumSize = new Dimension(100, 100);
        Dimension maximumSize = new Dimension(500, 500);
        buttonView.setMinimumSize(minimumSize);
        buttonView.setMaximumSize(maximumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(200);
        splitPane.setPreferredSize(new Dimension(750, 300));

        //Add the split pane to the parent container
        add(splitPane);
    }

    private void createUser(DefaultMutableTreeNode top, String userName) {


        // Creates and stores a new user for searching purposes
        User newUserNode = new User(userName);

        userNameArrayList.add(userName);
        userArrayList.add(newUserNode);

        // Creates a group instance of root
        Group group = (Group) top;
        // user is the node to be added to the tree. Parent is the eventual parent node(group) in which it will
        // be placed in
        DefaultMutableTreeNode user;
        DefaultMutableTreeNode parent;
        // Getting the selected elements path to see if it is null. If it is null, set root as parent.
        // Otherwise, set the group node that was selected as the parent.
        TreePath parentPath = tree.getSelectionPath();

        // allowsChildren set to false prevents any future user node from accepting children which avoids
        // the issue of a user node from being given child nodes
        user = new DefaultMutableTreeNode(userName, false);
        // If nothing is selected, set parent to root. Otherwise, set it to the currently-selected node
        if(parentPath == null) {
            parent = top;
        }
        else {
            parent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        }

        // Add the new node to the tree
        parent.add(user);
        // Add the user to root's composite list
        ArrayList<Composite> topCompositeList = group.getCompositeList();
        topCompositeList.add((Composite) newUserNode);


    }

    private void createGroup(DefaultMutableTreeNode top, String groupName) {
        // group is the node to be added to the tree. Parent is the eventual parent node(group) in which it will
        // be placed in
        DefaultMutableTreeNode group;
        DefaultMutableTreeNode parent;
        // Getting the selected elements path to see if it is null. If it is null, set root as parent.
        // Otherwise, set the group node that was selected as the parent.
        TreePath parentPath = tree.getSelectionPath();
        group = new DefaultMutableTreeNode(groupName, true);
        if(parentPath == null) {
            parent = top;
        }
        else {
            parent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        }

        // Creating a new group and giving it a new composite list on creation by setting its list to the new list
        Group newGroup = new Group(groupName);
        ArrayList<Composite> newList = new ArrayList<>();
        newGroup.setList(newList);
        parent.add(group);

        // Creating the group that is root casted as a group and getting its list then adding the new to it
        Group test = (Group) top;
        ArrayList<Composite> topCompositeList = test.getCompositeList();
        topCompositeList.add((Composite) newGroup);
    }



    void createAndShowUserGUI(DefaultMutableTreeNode user) {

        JFrame userViewFrame = new JFrame(user.toString());

        // Getting the name of the user object to later find the respective user object
        String userFrameName = user.toString();

        // Getting the currently selected user's User object by finding the index of the name in
        // userNameArrayList then returning the object stored at that index in userArrayList
        int currentUserIndex = userNameArrayList.indexOf(userFrameName);
        User currentUser = userArrayList.get(currentUserIndex);

        // Creating the parent panel and 4 respective child panels to be used to create the whole user view
        JPanel rightPanel = new JPanel(new GridLayout(4, 0, 0, 50));
        JPanel firstUserPanel = new JPanel(new GridLayout(1, 2, 10, 5));
        JPanel secondUserPanel = new JPanel();
        JPanel thirdUserPanel = new JPanel(new GridLayout(1, 2, 10, 5));
        JPanel fourthUserPanel = new JPanel();


        // Gets the DefaultListModel from the current user node so that it does not get wiped when the
        // window is reopened.
        DefaultListModel listModel = currentUser.getUserListModel();


        // Creating the components of firstUserPanel and adding them to it
        JTextField userViewUserIDTextField = new JTextField();
        JButton followUserButton = new JButton("Follow User");
        firstUserPanel.add(userViewUserIDTextField);
        firstUserPanel.add(followUserButton);

        // Creating the list and scrollpane for the followingList and adding it to the secondUserPanel
        JList<String> followingListDisplay = new JList<String>(listModel);
        JScrollPane listPane = new JScrollPane(followingListDisplay);
        secondUserPanel.add(listPane);
        // Set to this size because the scroll bar was not appearing with my previous set size
        listPane.setPreferredSize(new Dimension(500, 300));



        // Getting the user stored messageListModel to have it be stored on reopening of the window
        DefaultListModel messageModel = currentUser.getMessageListModel();

        // Creating the textfield and button for the post tweet section(third user panel)
        JTextField tweetMessageTextField = new JTextField("");
        JButton postTweetButton = new JButton("Post Tweet");
        // Adding both components to the third user panel
        thirdUserPanel.add(tweetMessageTextField);
        thirdUserPanel.add(postTweetButton);


        // Getting the JTextArea from the user so that the newsFeed of the user in userView is not wiped on
        // the reopening of the window
        JTextArea currentUserNewsFeedTextField = currentUser.getNewsFeedTextField();

        // Adding that JTextArea to a JScrollPane and adding it to the fourth user panel
        JScrollPane messagePane = new JScrollPane(currentUserNewsFeedTextField);
        fourthUserPanel.add(messagePane);
        messagePane.setPreferredSize(new Dimension(500, 100));

        // Putting all of the panels into rightPanel in order
        rightPanel.add(firstUserPanel);
        rightPanel.add(secondUserPanel);
        rightPanel.add(thirdUserPanel);
        rightPanel.add(fourthUserPanel);
        rightPanel.setPreferredSize(new Dimension(500, 500));

        userViewFrame.add(rightPanel);
        userViewFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        userViewFrame.pack();
        userViewFrame.setVisible(true);
        System.out.println("creation time of current user " + currentUser.getCreationTime()); // number 2 of hw 3

        // Action listener for the followUserButton
        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the currentUser's following list
                ArrayList<String> currentUserFollowingList = currentUser.getFollowingList();


                // Get the inputted userName
                String textFieldInput = userViewUserIDTextField.getText();

                // Get the user that you wish to follow by using the inputted userName
                int userToFollowIndex = userNameArrayList.indexOf(textFieldInput);
                User userToFollow = userArrayList.get(userToFollowIndex);

                // Checking to see if userNameArrayList contains the user you wish to follow and the current user
                // to validate that they both exist
                if(userNameArrayList.contains(textFieldInput)
                        && userNameArrayList.contains(userFrameName)) {


                    currentUserFollowingList.add(userToFollow.getUserName());
                    // Refreshing the listModel
                    listModel.clear();
                    listModel.addElement(currentUserFollowingList);
                    // Attaching the currentUser to userToFollow as an observer
                    userToFollow.attach(currentUser);
                }
            }
        });

        // Adding an action listener to postTweetButton
        postTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Getting the timestamp for the message
                String textFieldInput = getFormattedDate() + " " + currentUser.getUserName() + ": " +  tweetMessageTextField.getText();

                // Setting the text of the currentUser's text field and adding the textfield to the messageModel
                currentUserNewsFeedTextField.setText(currentUserNewsFeedTextField.getText() + "\n" + textFieldInput);
                messageModel.addElement(currentUserNewsFeedTextField);
                // Notifying all the observers(followers)
                currentUser.setLastUpdateTime(System.currentTimeMillis()); // 3 for homework 3

                currentUser.notifyObservers();
                // Adding the new tweet to the messageArrayList
                currentUser.getMessageArrayList().add(tweetMessageTextField.getText());

            }
        });


    }

    // Get the formatted timestamp for tweets
    public String getFormattedDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = date.format(dateFormat);
        return formattedDate;

    }

    // Method to calculate the positive percentage
    private double calculatePositivePercentage(double positiveWordOccurence, int getMessageCount) {
        //Calculates the percentage of positive messages
        positiveWordOccurence = positiveWordOccurence / (double) getMessageCount;
        positiveWordOccurence *= 100;
        return positiveWordOccurence;
    };

    // Method to create the GUI
    void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Mini-Twitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add an instance of GUI to the window which opens up the admin panel
        frame.add(new GUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    }