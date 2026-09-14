import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import ij.IJ;

public class wekaBackgroundSubstraction_menu {
    
    //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    

    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //flags to check if the frame has been initialized
    public static boolean zBackground_Substraction_Initialized = false;

    public static Class<?> currentClass = wekaBackgroundSubstraction_menu.class;
    
    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//

    //end of the swing elements region
    //#endregion

    //JFrames
    public static JFrame zBackground_frame = new JFrame("Weka Background Substraction Menu");

    //Containers
    public static Container zBackground_frame_contentPane = zBackground_frame.getContentPane();

    //JPanels
    public static JPanel secondSectionPanel = new JPanel();

    //SpringLayout
    public static SpringLayout zBackground_menu_springLayout = new SpringLayout();

    //JButtons
    public static JButton singleButton = new JButton("Single");
    public static JButton batchButton = new JButton("Batch");

    //JButtons ArraLists
    public static ArrayList<JButton> firstSectionButtons = new ArrayList<JButton>();

    //JSeparator
    public static JSeparator First_Second_sections_separator = new JSeparator(JSeparator.VERTICAL);

    ///////////// region for the single section ///////////////////
    
    //JButtons
    public static JButton single_Select_Opened = new JButton("Select Image");

    //JLabels
    public static JLabel single_Selected_Image_Label = new JLabel("Selected Image: ");



    //end of the region
    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    
    //method to initialize the frame
    public static void initializeFrame() {

        //log that it has been initialized
        IJ.log("Initializing the Weka Substraction Menu");

        //add the components to the frame
        addComponentsToFrame();

        //set the components constraints
        setFirstSectionComponentsConstraints();

        //set the components listeners for the first section
        setFirstSectionComponentsListeners();

        //set the frame's listener
        setFrameListeners();
        
        //pack the frame
        zBackground_frame.pack();

        //set the frame visible
        zBackground_frame.setVisible(true);

        //set the frame flag to initialized
        zBackground_Substraction_Initialized = true;

    }
    
    //method to add the components to the frame
    public static void addComponentsToFrame() {

        //set the layout
        zBackground_frame_contentPane.setLayout(zBackground_menu_springLayout);

        //set the frame's minimum size
        zBackground_frame.setMinimumSize(new Dimension(400, 400));

        //populate the first section buttons array list
        populateFirstSectionButtonsArrayList();

        //Add the buttons from the first section to the frame
        for (JButton button : firstSectionButtons) {

            //add the button to the frame
            zBackground_frame_contentPane.add(button);

        }

        //add the vertical separator
        zBackground_frame_contentPane.add(First_Second_sections_separator);

        //add the second section panel to the frame
        zBackground_frame_contentPane.add(secondSectionPanel);

    }

    //populate the JButtons ArrayLists
    public static void populateFirstSectionButtonsArrayList() {

        //add the single and batch buttons to the first section buttons
        firstSectionButtons.add(singleButton);
        firstSectionButtons.add(batchButton);

    }

    //method to set the components constraints
    public static void setFirstSectionComponentsConstraints() {

        //set the constraints for the single button
        zBackground_menu_springLayout.putConstraint(SpringLayout.NORTH, 
                                                                    singleButton,
                                                                    5,
                                                                    SpringLayout.NORTH,
                                                                    zBackground_frame_contentPane);
        zBackground_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                                    singleButton,
                                                                    5,
                                                                    SpringLayout.WEST,
                                                                    zBackground_frame_contentPane);
        //set the constraints for the batch button
        zBackground_menu_springLayout.putConstraint(SpringLayout.NORTH, 
                                                                    batchButton,
                                                                    5,
                                                                    SpringLayout.SOUTH,
                                                                    singleButton);
        zBackground_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                                    batchButton,
                                                                    5,
                                                                    SpringLayout.WEST,
                                                                    zBackground_frame_contentPane);

        //set the constraints for the vertical separator
        zBackground_menu_springLayout.putConstraint(SpringLayout.NORTH, 
                                                                    First_Second_sections_separator,
                                                                    5,
                                                                    SpringLayout.NORTH,
                                                                    zBackground_frame_contentPane);
        zBackground_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                                    First_Second_sections_separator,
                                                                    5,
                                                                    SpringLayout.EAST,
                                                                    singleButton);
        zBackground_menu_springLayout.putConstraint(SpringLayout.SOUTH,
                                                                    First_Second_sections_separator,
                                                                    -5,
                                                                    SpringLayout.SOUTH,
                                                                    zBackground_frame_contentPane);

        //set the constraints for the second section panel
        zBackground_menu_springLayout.putConstraint(SpringLayout.NORTH, 
                                                                    secondSectionPanel,
                                                                    5,
                                                                    SpringLayout.NORTH,
                                                                    zBackground_frame_contentPane);
        zBackground_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                                    secondSectionPanel,
                                                                    5,
                                                                    SpringLayout.EAST,
                                                                    First_Second_sections_separator);
        zBackground_menu_springLayout.putConstraint(SpringLayout.SOUTH,
                                                                    secondSectionPanel,
                                                                    -5,
                                                                    SpringLayout.SOUTH,
                                                                    zBackground_frame_contentPane);
        zBackground_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                                    secondSectionPanel,
                                                                    -5,
                                                                    SpringLayout.EAST,
                                                                    zBackground_frame_contentPane);


    }

    //method to set the first section listeners
    public static void setFirstSectionComponentsListeners() {

        //set the single button listener
        singleButton.addActionListener(new ActionListener() {

            //method to perform the action
            public void actionPerformed(ActionEvent actionEvent) {

                //call the method to initialize single
                

            }

        });

    }

    //method to initialize the single menu
    public static void startSingleMenu() {

        //run the method to reset the second section panel
        resetSecondSectionPanel();

        

    }

    //method to reset the second section panel
    public static void resetSecondSectionPanel() {

        //get the initialized buttons
        ArrayList<JButton> initializedButtons = getInitializedFirstSectionButtons();

        //reset the corresponding second section components to their initial state
        resetInitializedSecondSectionComponents(initializedButtons);

        //remove all the components from the panel
        secondSectionPanel.removeAll();

        
        //pack the frame
        zBackground_frame.pack();

        //repaint the frame
        zBackground_frame.repaint();

    }

    //method to get the initialized first section buttons
    public static ArrayList<JButton> getInitializedFirstSectionButtons() {

        //create the array list
        ArrayList<JButton> initializedButtons = new ArrayList<JButton>();

        //loop through the first section buttons
        for (JButton button : firstSectionButtons) {

            //check if the button is disabled
            if (!button.isEnabled()) {

                //add the button to the array list
                initializedButtons.add(button);

            }

        }

        //return the array list
        return initializedButtons;

    }

    //method to reset the second section components that are initialized
    public static void resetInitializedSecondSectionComponents(ArrayList<JButton> initializedButtons) {

        //loop through the initialized buttons
        for (JButton button : initializedButtons) {

            //get the button label
            String buttonLabel = button.getText();

            //add the string _reset to the button label
            String resetButtonLabel = buttonLabel + "_resetComponents";

            //get the method with the reset button label
            try {
                Method reset_method = currentClass.getMethod(resetButtonLabel);
                //run the method
                reset_method.invoke(null);
                
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            

        }

    }

    //method to reset the single components
    public static void Single_resetComponents() {

        

    }

    //method to reset the batch components
    public static void Batch_resetComponents() {

        

    }

    //method to set the frame's listeners
    public static void setFrameListeners() {

        //get the frame's listeners into a list
        WindowListener[] frameListeners = zBackground_frame.getWindowListeners();

        //loop through the list and remove them
        for (WindowListener windowListener : frameListeners) {

            //remove the listener
            zBackground_frame.removeWindowListener(windowListener);

        }

        //add the new listener
        zBackground_frame.addWindowListener(new WindowAdapter() {

            //method to close the frame
            public void windowClosing(WindowEvent windowEvent) {

                //close the frame
                closeMenu();

            }

        });


    }

    //method to reset the jframe and the layouts
    public static void resetMenu() {

        
    
    }
        
    //method to close the menu
    public static void closeMenu() {

        //close the frame
        zBackground_frame.dispose();

        //reset the manu
        resetMenu();

        //run the method in the mainMenu to reset the button
        mainMenu.resetOpenedMenuButton(currentClass);

        //set the boolean to false
        zBackground_Substraction_Initialized = false;

    }

    //method to reopen the full menu
    public static void reopenMenu() {

        //get the menu's position
        int x = zBackground_frame.getX();
        int y = zBackground_frame.getY();

        //close the frame
        zBackground_frame.dispose();

        //reset the menu
        resetMenu();

        //initialize the frame
        initializeFrame();

        //set the mainMenu button to disabled
        mainMenu.setButtonToDisabledFromClass(currentClass);

        //set the location
        zBackground_frame.setLocation(x, y);

    }

    //method to retrieve if the frame is initialized
    public static boolean isInitialized() {

        //return the boolean
        return zBackground_Substraction_Initialized;

    }

    //#endregion end of methods region




}
