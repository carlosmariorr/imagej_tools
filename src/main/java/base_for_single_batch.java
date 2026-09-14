//#region ############################################### DESCRIPTION #################################################

/* This is the template for making a menu that will be instantiated once and then,
 * the corresponding button in the main menu will be deactivated.
 * 
 * To create a new menu from this class, you need to:
 *  1) Copy this file and rename it to the name of the menu you want to create
 *  2) Change the name of the class to the name of the menu you want to create 
 *  3) Modify the mainMenu class to add a new button with the name of the menu you want to create (Follow instructions on the mainMenu class)
 *  4) Change the global variable "current Class" to fit the newly named class
 *  5) Change the global variable "frameName" to fit the newly named class
 *  6) Change the global variable "generalMenuTemplate_frame" to fit the newly named class. Replace all "generalMenuTemplate_frame" with the new name of the frame
 *  7) Make your actual program. You can initialize the frame wherever you want or do anything you desire. Get your heart content. 
 */

//#endregion ############################################ DESCRIPTION #################################################


//#region ############################################### IMPORTS #####################################################

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

//#endregion ############################################ IMPORTS #####################################################


//start of the class
public class base_for_single_batch {

    
    //#region ############################################### GLOBALS #####################################################

    //the current class
    static Class<?> currentClass = projectionMenu.class;

    //the frame name
    public static String frameName = "Projection Menu";

    //the boolean to know if the frame is initialized
    public static boolean isInitialized = false;

    //the menu width and the menu height
    public static int menuWidth = 300;
    public static int menuHeight = 200;

    //#region ############################################### JAVA SWING ELEMENTS #########################################

    //the frame
    public static JFrame projectionMenu_frame = new JFrame(frameName);

    //the frame's content pane
    public static Container projectionMenu_frame_contentPane = projectionMenu_frame.getContentPane();

    //the spring layout for the frame
    public static SpringLayout projectionMenu_frame_springLayout = new SpringLayout();

    //JButtons 
    public static JButton singleButton = new JButton("Single");
    public static JButton batchButton = new JButton("Batch");

    //The button list
    public static ArrayList<JButton> mainButtonsArray = new ArrayList<JButton>();

    //JSeparator
    public static JSeparator mainButtons_separator = new JSeparator(JSeparator.HORIZONTAL);

    //projection panel
    public static JPanel projectionPanel = new JPanel();

    //projection panel layout
    public static SpringLayout projectionPanel_springLayout = new SpringLayout();



    //#endregion ############################################ JAVA SWING ELEMENTS #########################################

    //#endregion ############################################ GLOBALS #####################################################

    //#region ############################################### METHODS #####################################################

    //method to initialize the frame
    public static void initializeFrame() {

        //reset the main componentss
        resetMainComponents();

        //add the components to the frame
        addComponentsToFrame();

        //set the main restraints
        setMainComponentsRestraints();

        //add the mainComponentsListeners
        addMainComponentsListeners();

        //set the boolean to true
        isInitialized = true;

    }

    //method to reset the main components
    public static void resetMainComponents() {

        //start the new jframe
        projectionMenu_frame = new JFrame(frameName);

        //start a new projectionmenu content pane
        projectionMenu_frame_contentPane = projectionMenu_frame.getContentPane();

        //start the new spring layout
        projectionMenu_frame_springLayout = new SpringLayout();

        //the single button
        singleButton = new JButton("Single");

        //the batch button
        batchButton = new JButton("Batch");

        //the main buttons array
        mainButtonsArray = new ArrayList<JButton>();

        //the main buttons separator
        mainButtons_separator = new JSeparator(JSeparator.HORIZONTAL);

        //projection panel
        projectionPanel = new JPanel();

        //projection panel layout
        projectionPanel_springLayout = new SpringLayout();

    }

    //method to add the components to the frame
    public static void addComponentsToFrame() {

        //set the layout
        projectionMenu_frame_contentPane.setLayout(projectionMenu_frame_springLayout);

        //set the minimum size
        projectionMenu_frame.setMinimumSize(new Dimension(menuWidth, menuHeight));

        //populate the button array
        populateButtonArray();

        //add the buttons to the frame
        for (JButton button : mainButtonsArray) {

            //add the button to the frame
            projectionMenu_frame_contentPane.add(button);

            //set the button to enabled
            button.setEnabled(true);

        }

        //add the vertical separator
        projectionMenu_frame_contentPane.add(mainButtons_separator);

        //add the projection panel
        projectionMenu_frame_contentPane.add(projectionPanel);

        //set the panel's layout
        projectionPanel.setLayout(projectionPanel_springLayout);

    }

    //method to populate the button array
    public static void populateButtonArray() {

        //add the single button
        mainButtonsArray.add(singleButton);

        //add the batch button
        mainButtonsArray.add(batchButton);

    }

    //method to set the main components restraints
    public static void setMainComponentsRestraints() {

        //populate the buttons

        //start the button counter
        int buttonCounter = 0;

        //loop through the number of buttons and add the constraints
        for (int i = 0; i < mainButtonsArray.size(); i++) {

            //get the button
            JButton button = mainButtonsArray.get(i);

            //for the west
            //if the buttonCounter is 0, then set the west to the projectionMenu_frame_contentPane's west
            if (buttonCounter == 0) {

                //set the west
                projectionMenu_frame_springLayout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.WEST, projectionMenu_frame_contentPane);

            } else {

                //get the previous button 
                JButton previousButton = mainButtonsArray.get(buttonCounter - 1);

                //set the west of the button to 5 units the previous button's east
                projectionMenu_frame_springLayout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.EAST, previousButton);

            }

            //for the north
            projectionMenu_frame_springLayout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, projectionMenu_frame_contentPane);

            //increment the button counter
            buttonCounter++;

        }
        
        //set the separator's
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.WEST, mainButtons_separator, 5, SpringLayout.WEST, projectionMenu_frame_contentPane);
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.EAST, mainButtons_separator, -5, SpringLayout.EAST, projectionMenu_frame_contentPane);
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.NORTH, mainButtons_separator, 5, SpringLayout.SOUTH, mainButtonsArray.get(0));

        //set the projection panel's
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.WEST, projectionPanel, 5, SpringLayout.WEST, projectionMenu_frame_contentPane);
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.EAST, projectionPanel, -5, SpringLayout.EAST, projectionMenu_frame_contentPane);
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.NORTH, projectionPanel, 5, SpringLayout.SOUTH, mainButtons_separator);
        projectionMenu_frame_springLayout.putConstraint(SpringLayout.SOUTH, projectionPanel, -5, SpringLayout.SOUTH, projectionMenu_frame_contentPane);

    }

    //method to add the main components listeners
    public static void addMainComponentsListeners() {

        //set the single button listener
        singleButton.addActionListener(new ActionListener() {

            //add the listener to the single button
            public void actionPerformed(ActionEvent actionEvent) {

                //run the method to reset the single panel
                //resetSinglePanel();

                //call the method to initialize single
                //startSingleMenu();

                //set the button to disabled
                singleButton.setEnabled(false);

            }

        });

        //set the batch button listener
        batchButton.addActionListener(new ActionListener() {

            //add the listener to the batch button
            public void actionPerformed(ActionEvent actionEvent) {

                //run the method to reset the batch panel
                //resetBatchPanel();

                //call the method to initialize batch
                //startBatchMenu();

                //set the button to disabled
                batchButton.setEnabled(false);

            }

        });

        //set the main frame listener
        projectionMenu_frame.addWindowListener(new java.awt.event.WindowAdapter() {

            //add the listener to the main frame
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                //run the method to close the menu
                closeMenu();

            }

        });

    }

    //method to close the menu
    public static void closeMenu() {

        //initialize the frame
        initializeFrame();

        //close the frame
        projectionMenu_frame.dispose();

        //run the method in the mainMenu to reset the button
        mainMenu.resetOpenedMenuButton(currentClass);

        //set the boolean to false
        isInitialized = false;

    }

    //method to know if it is initialized
    public static boolean isInitialized() {

        //return the boolean
        return isInitialized;

    }

    //#endregion ############################################ METHODS #####################################################


    //end of the main class
}

