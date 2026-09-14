import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.plugin.frame.RoiManager;

public class generalRoiCreation_menu {
    
//explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    




    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //Strings
    public static String generalRoiCreationMenu_default_file_path_string = new String("/home/username/file");

    //ImagePlus
    public static ImagePlus mainImage = new ImagePlus();

    //integers
    public static int mainImage_id;

    //flag to know if the frame is initialized
    public static boolean generalRoiCreation_menu_frame_initialized = false;

    //ArrayList for the second section button's classes
    public static ArrayList<Method> generalRoiCreation_menu_Second_Section_Button_Method_arraylist = new ArrayList<Method>();

    //flags to know if a roi type is initialized
    public static boolean PointRois_initialized = false;
    public static boolean OtherRois_initialized = false;
    
    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//
    
    //JFrames
    public static JFrame generalRoiCreation_menu_frame = new JFrame("General ROI Creation Menu");

    //JPanels
    public static JPanel thirdSectionJPanel = new JPanel();

    //SpringLayouts
    public static SpringLayout generalRoiCreation_menu_springLayout = new SpringLayout();

        //Third section's JPanel spring layout
    public static SpringLayout thirdSectionJPanel_spring_layout = new SpringLayout();
    

    //declare the content pane for the frame
    public static Container generalRoiCreation_menu_contentPane = generalRoiCreation_menu_frame.getContentPane();

    //JLabels
    public static JLabel generalRoiCreation_menu_file_path_label = new JLabel("Main File Path: ");
        
        //from the third section
    public static JLabel pointRois_file_path_label = new JLabel("Rois File Path: ");
        

    //JTextFields
    public static JTextField generalRoiCreation_menu_file_path_textField = new JTextField(20);
    
        //from the third section
    public static JTextField pointRois_Rois_file_path_text_field = new JTextField(20);


    //JButtons
    public static JButton generalRoiCreation_menu_file_path_select_button = new JButton("Select File");
    public static JButton generalRoiCreation_menu_file_path_open_button = new JButton("Open");

    public static JButton generalRoiCreation_menu_button_create_point_rois = new JButton("Point ROIs");
    public static JButton generalRoiCreation_menu_button_create_other_rois = new JButton("Other ROIs");

        //from the third section
    public static JButton pointRois_file_path_select_button = new JButton("Select ROIs File");
    public static JButton pointRois_file_path_open_button = new JButton("Open ROIs File");

    //Mouse Listeners
    public static MouseListener generalRoiCreation_original_mainImage_mouse_listener;

    //JSeparator
    public static JSeparator generalRoiCreation_menu_First_Second_separator = new JSeparator();

    public static JSeparator generalRoiCreation_menu_Second_Third_separator = new JSeparator(JSeparator.VERTICAL);

    //declare the new JButton ArraList
    public static ArrayList<JButton> generalRoiCreation_menu_buttonArrayList = new ArrayList<JButton>();
    public static ArrayList<JButton> generalRoiCreation_menu_Second_Section_Button_arraylist = new ArrayList<JButton>();

    //JCHeckboxes
        //from the third section
    public static JCheckBox pointRois_create_new_checkbox = new JCheckBox("Create new rois");

    //end of the region
    //#endregion

    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////

    //method to initialize the frame
    public static void initializeFrame(){

        //log that it has been initialized
        IJ.log("Initializing the General ROI Creation Menu");

        //add the components to the frame
        addComponentsToFrame();

        //set the frame's listener
        setMainFrameListeners();
        
        //set the frame flag to initialized
        generalRoiCreation_menu_frame_initialized = true;

    }

    //method to populate the second section array list
    public static void populateSecondSectionButtonArrayList() {

        //get this file's class
        Class<?> thisClass = generalRoiCreation_menu.class;
        //add the create point rois button
        generalRoiCreation_menu_Second_Section_Button_arraylist.add(generalRoiCreation_menu_button_create_point_rois);
        //set the width of this first button
        generalRoiCreation_menu_Second_Section_Button_arraylist.get(0).setPreferredSize(new Dimension(150, 25));
        //get the startPointRois method to add it later
        Method startPointRois;
        try {
            
            //look for the method
            startPointRois = thisClass.getMethod("startPointRois");
            //add the method to the array list
            generalRoiCreation_menu_Second_Section_Button_Method_arraylist.add(startPointRois);

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        //add the other rois button
        generalRoiCreation_menu_Second_Section_Button_arraylist.add(generalRoiCreation_menu_button_create_other_rois);
        //add the class to the button array list
        //get the method
        Method startOtherRois;
        try {
            
            //look for the method
            startOtherRois = thisClass.getMethod("startOtherRois");
            //add the method to the array list
            generalRoiCreation_menu_Second_Section_Button_Method_arraylist.add(startOtherRois); 

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }

    //method to add the components
    public static void addComponentsToFrame(){

        //start with the general layout of the pane
        //set the layout
        generalRoiCreation_menu_contentPane.setLayout(generalRoiCreation_menu_springLayout);

        //set the frame's minimum size
        generalRoiCreation_menu_frame.setMinimumSize(new Dimension(600, 300));

        //start with the main file section
        //add the file path label
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_file_path_label);

        //add the file path textField
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_file_path_textField);

        //add the file path select button
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_file_path_select_button);

        //add the file path open button
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_file_path_open_button);

        //add the separator 
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_First_Second_separator);

        //set the first section's contstraints
        setMainFileSectionConstraints();

        //set the action listeners to the buttons of the first section
        setFirstSectionActionListeners();

        //populate the second section buttons
        populateSecondSectionButtonArrayList();

        //add the second section buttons
        addSecondSectionButtons();

        //set the second section's constraints
        setSecondSectionConstraints();

        //add the secodSection's action listeners
        addSecondSectionActionListeners();

        //add the third section JPanel
        generalRoiCreation_menu_contentPane.add(thirdSectionJPanel);

        //set the third section's constraints
        setThirdSectionConstraints();

        //pack the frame
        generalRoiCreation_menu_frame.pack();

        //show the frame
        generalRoiCreation_menu_frame.setVisible(true);    

    }

    //method to set the constraints for the main file section
    public static void setMainFileSectionConstraints() {

        //set the constraints for the file path label
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                            generalRoiCreation_menu_file_path_label,
                                                            5,
                                                            SpringLayout.WEST,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_file_path_label,
                                                            5,
                                                            SpringLayout.NORTH,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_file_path_label,
                                                            5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_file_path_textField);

        //set the constraints for the file path textField
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                            generalRoiCreation_menu_file_path_textField,
                                                            5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_file_path_label);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_file_path_textField,
                                                            5,
                                                            SpringLayout.NORTH,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                            generalRoiCreation_menu_file_path_textField,
                                                            -5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_contentPane);

        //set the constraints for the file path open button
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                            generalRoiCreation_menu_file_path_open_button,
                                                            -5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_file_path_open_button,
                                                            5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_file_path_textField);                                                                

        //set the constraints for the file path select button
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                            generalRoiCreation_menu_file_path_select_button,
                                                            -5,
                                                            SpringLayout.WEST,
                                                            generalRoiCreation_menu_file_path_open_button);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_file_path_select_button,
                                                            0,
                                                            SpringLayout.NORTH,
                                                            generalRoiCreation_menu_file_path_open_button);

        //set the constraints for the jseparator
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                            generalRoiCreation_menu_First_Second_separator,
                                                            0,
                                                            SpringLayout.WEST,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                            generalRoiCreation_menu_First_Second_separator,
                                                            0,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_First_Second_separator,
                                                            5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_file_path_open_button);


    }

    //method to set the loaded components of the first section
    public static void setFirstSectionComponentsToLoaded() {

        //set the file path text field to disabled
        generalRoiCreation_menu_file_path_textField.setEnabled(false);
        
        //set the file path select button to disabled
        generalRoiCreation_menu_file_path_select_button.setEnabled(false);

        //set the file path open button to disabled
        generalRoiCreation_menu_file_path_open_button.setEnabled(false);

        //loop through the second section buttons and set them to enabled
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {

            //set the button to enabled
            generalRoiCreation_menu_Second_Section_Button_arraylist.get(i).setEnabled(true);

        }

    }

    //method to reset the state of the first section components
    public static void resetFirstSectionComponents() {

        //set the file path text field to disabled
        generalRoiCreation_menu_file_path_textField.setEnabled(true);
        //set the text field to the default
        generalRoiCreation_menu_file_path_textField.setText(generalRoiCreationMenu_default_file_path_string);
        //repaint it
        generalRoiCreation_menu_file_path_textField.repaint();

        //set the file path select button to disabled
        generalRoiCreation_menu_file_path_select_button.setEnabled(true);

        //set the file path open button to disabled
        generalRoiCreation_menu_file_path_open_button.setEnabled(true);

    }

    //method to set the action listeners to the first section
    public static void setFirstSectionActionListeners() {

        //set the action listener to the file path select button
        //get the action listeners
        ActionListener[] actionListeners = generalRoiCreation_menu_file_path_select_button.getActionListeners();
        //remove the action listeners
        for (ActionListener actionListener : actionListeners) {
            generalRoiCreation_menu_file_path_select_button.removeActionListener(actionListener);
        }

        //add the new action listener to the button
        generalRoiCreation_menu_file_path_select_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get a new JFile chooser
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.showOpenDialog(generalRoiCreation_menu_frame);

                //get the selected file
                File selectedFile = fileChooser.getSelectedFile();

                //get the path
                String path = selectedFile.getAbsolutePath();

                //set the text field to the path
                generalRoiCreation_menu_file_path_textField.setText(path);
                //redraw the JText field
                generalRoiCreation_menu_file_path_textField.repaint();

            }

        });

        //set the action listener to the file path open button
        //get the action listeners
        actionListeners = generalRoiCreation_menu_file_path_open_button.getActionListeners();
        //remove the action listeners
        for (ActionListener actionListener : actionListeners) {
            generalRoiCreation_menu_file_path_open_button.removeActionListener(actionListener);
        }

        //add the new action listener to the button
        generalRoiCreation_menu_file_path_open_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the path
                String path = generalRoiCreation_menu_file_path_textField.getText();

                //check if it ends with .tif, .dv or .decon
                if (path.endsWith(".tif") || path.endsWith(".dv") || path.endsWith(".decon")) {
                    
                    //open the file using IJ
                    mainImage = IJ.openImage(path);

                    //show the image
                    mainImage.show();

                    //get the image id and set it
                    mainImage_id = mainImage.getID();

                    //set the first section to image loaded
                    setFirstSectionComponentsToLoaded();

                    //backup the original mouse listener
                    backupOriginalMouseListener();

                    //get a new listener for the main image
                    WindowAdapter mainImagWindowAdapter = getNewMainImageWindowListener();

                    //get the image window
                    ImageWindow imageWindow = mainImage.getWindow();

                    //add the listener to the imageWindow
                    imageWindow.addWindowListener(mainImagWindowAdapter);

                    //set the image as not interatable
                    setImageNotInteractable(mainImage_id);

                }

            }

        });

    }

    //method to set the listeners to the main frame
    public static void setMainFrameListeners(){

        //get the window listeners from the frame
        WindowListener[] windowListeners = generalRoiCreation_menu_frame.getWindowListeners();
        //remove the window listeners
        for (WindowListener windowListener : windowListeners) {
            generalRoiCreation_menu_frame.removeWindowListener(windowListener);
        }

        //add the new window listener
        generalRoiCreation_menu_frame.addWindowListener(new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                
                //check if the main image is not null
                if (mainImage != null) {
                    
                    //close the main image
                    mainImage.close();

                }

                //close the frame
                generalRoiCreation_menu_frame.dispose();

                //reset the manu
                resetMenu();

                //run the method in the mainMenu to reset the button
                mainMenu.resetGeneralRoiCreationMenuButton();

                //set the boolean to false
                generalRoiCreation_menu_frame_initialized = false;

                
            }

        });



    }

    //method to get a new window adapter for the main image 
    public static WindowAdapter getNewMainImageWindowListener() {

        //get a new window adapter
        WindowAdapter windowAdapter = new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                
                reopenMenu();

            }

        };

        //return the window adapter
        return windowAdapter;

    }

    //method to get the original mouse listener for the main image
    public static void backupOriginalMouseListener() {

        //get the main image
        ImagePlus mainImage_to_backup_mouse_listener = WindowManager.getImage(mainImage_id);

        //get the canvas
        ImageCanvas canvas = mainImage_to_backup_mouse_listener.getCanvas();

        //get the mouse listeners for the canvas
        MouseListener[] mouseListeners = canvas.getMouseListeners();

        //set the variable to it
        generalRoiCreation_original_mainImage_mouse_listener = mouseListeners[0];     

    }

    //method to reset the original mouse listener
    public static void resetOriginalMouseListener() {

        //get the main image
        ImagePlus mainImage_to_reset_mouse_listener = WindowManager.getImage(mainImage_id);

        //get the canvas
        ImageCanvas canvas = mainImage_to_reset_mouse_listener.getCanvas();

        //get the mouse listeners for the canvas
        MouseListener[] mouseListeners = canvas.getMouseListeners();

        //remove all the listeners
        for (MouseListener mouseListener : mouseListeners) {
            canvas.removeMouseListener(mouseListener);
        }

        //add the original mouse listener
        canvas.addMouseListener(generalRoiCreation_original_mainImage_mouse_listener);

    }

    //method to set the image as not interactable
    public static void setImageNotInteractable(int making_not_interactable_image_id) {

        //get the image
        ImagePlus image_to_set_not_interactable = WindowManager.getImage(making_not_interactable_image_id);

        //get the canvas
        ImageCanvas canvas = image_to_set_not_interactable.getCanvas();

        //get the mouse listeners for the canvas
        MouseListener[] mouseListeners = canvas.getMouseListeners();

        //remove all the listeners
        for (MouseListener mouseListener : mouseListeners) {
            canvas.removeMouseListener(mouseListener);
        }

    }

    //method to set the main image as interactable
    public static void setImageInteractable(int making_interactable_image_id) {

        //get the image
        ImagePlus image_to_set_interactable = WindowManager.getImage(making_interactable_image_id);

        //get the canvas
        ImageCanvas canvas = image_to_set_interactable.getCanvas();

        //get the mouse listeners for the canvas
        MouseListener[] mouseListeners = canvas.getMouseListeners();

        //remove all the listeners
        for (MouseListener mouseListener : mouseListeners) {
            canvas.removeMouseListener(mouseListener);
        }

        //add the original mouse listener
        canvas.addMouseListener(generalRoiCreation_original_mainImage_mouse_listener);

    }

    //method to add the second section buttons
    public static void addSecondSectionButtons() {

        //loop through the second section buttons
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {

            //get the button
            JButton button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(i); 

            //add the button to the panel
            generalRoiCreation_menu_contentPane.add(button);

            //set the JButton to disabled
            button.setEnabled(false);

        }

        //add the vertical separator 
        generalRoiCreation_menu_contentPane.add(generalRoiCreation_menu_Second_Third_separator);


    }

    //method to set the second section contraints
    public static void setSecondSectionConstraints() {

        //loop through the second section buttons array list
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {

            //get the current button
            JButton current_button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(i);

            //get the first button
            JButton first_button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(0);

            //if the i is 0
            if (i == 0) {
            
                //set the constraints for the first button
                generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                                    current_button,
                                                                    10,
                                                                    SpringLayout.SOUTH,
                                                                    generalRoiCreation_menu_First_Second_separator);

            }

            //if the i is more than 0
            if (i > 0) {

                //get the previous button
                JButton previous_button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(i - 1);

                //set the constraints for the current button
                generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                                    current_button,
                                                                    5,
                                                                    SpringLayout.SOUTH,
                                                                    previous_button);

                //set the east constraints
                generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                                    current_button,
                                                                    0,
                                                                    SpringLayout.EAST,
                                                                    first_button);

            }

            //set the west constraints
            generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                                current_button,
                                                                5,
                                                                SpringLayout.WEST,
                                                                generalRoiCreation_menu_contentPane);

        }

        //add the JSeparator constraints
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            generalRoiCreation_menu_Second_Third_separator,
                                                            5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_First_Second_separator);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_Second_Third_separator,
                                                            -5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                            generalRoiCreation_menu_Second_Third_separator,
                                                            5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_Second_Section_Button_arraylist.get(0));

    }

    //method to add the second section action listeners
    public static void addSecondSectionActionListeners() {

        //loop through the second section buttons
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {

            //get the button
            JButton button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(i);

            //get the action listeners from the button
            ActionListener[] actionListeners = button.getActionListeners();
            //remove the action listeners
            for (ActionListener actionListener : actionListeners) {
                button.removeActionListener(actionListener);
            }
            
            //add the action listener
            button.addActionListener(new ActionListener() {
               
                public void actionPerformed(ActionEvent e) {

                    //reset any initizalized third section
                    resetThirdSectionInitialized();

                    //reset the third section components
                    resetThirdSectionComponents();
                    
                    try {
                        //get the index of the button
                        int button_index = generalRoiCreation_menu_Second_Section_Button_arraylist.indexOf(e.getSource());

                        //set the source to disabled
                        ((JButton) e.getSource()).setEnabled(false);

                        //get the method from the list based on the index
                        Method method = generalRoiCreation_menu_Second_Section_Button_Method_arraylist.get(button_index);

                        //start the method
                        method.invoke(null);

                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InvocationTargetException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            });
            
        }

    }

    //method to reset the second section
    public static void resetSecondSectionComponents() {

        //loop through the second section buttons
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {

            //get the button
            JButton button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(i);

            //set the button to enabled
            button.setEnabled(false);

            //get the button listeners
            ActionListener[] actionListeners = button.getActionListeners();

            //remove the listeners
            for (ActionListener actionListener : actionListeners) {
                button.removeActionListener(actionListener);
            }

        }

        addSecondSectionActionListeners();

    }

    //method to set the third section constraints
    public static void setThirdSectionConstraints() {

        //set the constraints for the third section panel
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.NORTH,
                                                            thirdSectionJPanel,
                                                            10,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_First_Second_separator);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.EAST,
                                                            thirdSectionJPanel,
                                                            -5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_contentPane);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.WEST,
                                                            thirdSectionJPanel,
                                                            5,
                                                            SpringLayout.EAST,
                                                            generalRoiCreation_menu_Second_Third_separator);
        generalRoiCreation_menu_springLayout.putConstraint(SpringLayout.SOUTH,
                                                            thirdSectionJPanel,
                                                            -5,
                                                            SpringLayout.SOUTH,
                                                            generalRoiCreation_menu_contentPane);


    }

    //method to reset the third section
    public static void resetThirdSectionComponents() {

        //reset the third section initialized components if they are initialized
        resetThirdSectionInitialized();

        //remove all the components from the third section panel
        thirdSectionJPanel.removeAll();

        //get a new spring layout for the third section panel
        thirdSectionJPanel_spring_layout = new SpringLayout();

        //set the panel spring layout
        thirdSectionJPanel.setLayout(thirdSectionJPanel_spring_layout);

        //revalidate the frame
        generalRoiCreation_menu_frame.revalidate();

        //repaint the frame
        generalRoiCreation_menu_frame.repaint();

    }

    //method for the Point Rois
    public static void startPointRois(){

        //log that the point rois is initialized
        IJ.log("Point Rois is initialized");

        //set the variable to initialized
        PointRois_initialized = true;

        //run the method to add the pointRois UI
        addPointRoisUI();

        //set the constraints for the pointRois
        setPointRoisConstraints();

        //revalidate the frame
        generalRoiCreation_menu_frame.revalidate();

        //repaint the frame
        generalRoiCreation_menu_frame.repaint();
        
    }

    //method for the _ifInitializedReset of the startPointRois
    public static void startPointRois_ifInitializedReset() {

        //find out if it is initialized
        if (PointRois_initialized) {

            //set the components to new components
            pointRois_file_path_label = new JLabel("File Path:");

            //set the text field to a new text field
            pointRois_Rois_file_path_text_field = new JTextField(20);

            //set the flag to false
            PointRois_initialized = false;

            //get the method from the class
            Method method = null;

            try {
                method = generalRoiCreation_menu.class.getMethod("startPointRois");

                //if you get it, find the index of the method in the array list
                int method_index = generalRoiCreation_menu_Second_Section_Button_Method_arraylist.indexOf(method);

                //get the button from the array list
                JButton button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(method_index);

                //set the button to enabled
                button.setEnabled(true);
                
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    //method to add the points roi ui
    public static void addPointRoisUI() {

        //empty the third section panel just in case there's anything there
        thirdSectionJPanel.removeAll();

        //get a new spring layout for the third section panel
        thirdSectionJPanel_spring_layout = new SpringLayout();

        //set the panel spring layout
        thirdSectionJPanel.setLayout(thirdSectionJPanel_spring_layout);

        //add the JLabel to the jpanel
        thirdSectionJPanel.add(pointRois_file_path_label);

        //add the JTextField to the content pane
        thirdSectionJPanel.add(pointRois_Rois_file_path_text_field);

        //add the JButton for select
        thirdSectionJPanel.add(pointRois_file_path_select_button);

        //add the open button
        thirdSectionJPanel.add(pointRois_file_path_open_button);

        //add the JCheckbox
        thirdSectionJPanel.add(pointRois_create_new_checkbox);

    }

    //method to set the constraints of the point roi
    public static void setPointRoisConstraints() {

        //set the constraints for the JLabel
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.NORTH,
                                                        pointRois_file_path_label,
                                                        5,
                                                        SpringLayout.NORTH,
                                                        thirdSectionJPanel);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.WEST,
                                                        pointRois_file_path_label,
                                                        5,
                                                        SpringLayout.WEST,
                                                        thirdSectionJPanel);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.SOUTH,
                                                        pointRois_file_path_label,
                                                        0,
                                                        SpringLayout.SOUTH,
                                                        pointRois_Rois_file_path_text_field);
        
        //set the constraints for the JTextField
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.NORTH,
                                                        pointRois_Rois_file_path_text_field,
                                                        5,
                                                        SpringLayout.NORTH,
                                                        thirdSectionJPanel);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.WEST,
                                                        pointRois_Rois_file_path_text_field,
                                                        5,
                                                        SpringLayout.EAST,
                                                        pointRois_file_path_label);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.EAST,
                                                        pointRois_Rois_file_path_text_field,
                                                        -5,
                                                        SpringLayout.EAST,
                                                        thirdSectionJPanel);

        //set the constraints for the open button
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.NORTH,
                                                        pointRois_file_path_open_button,
                                                        5,
                                                        SpringLayout.SOUTH,
                                                        pointRois_Rois_file_path_text_field);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.EAST,
                                                        pointRois_file_path_open_button,
                                                        -5,
                                                        SpringLayout.EAST,
                                                        thirdSectionJPanel);
        
        //set the constraints for the select button
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.NORTH,
                                                        pointRois_file_path_select_button,
                                                        0,
                                                        SpringLayout.NORTH,
                                                        pointRois_file_path_open_button);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.EAST,
                                                        pointRois_file_path_select_button,
                                                        -5,
                                                        SpringLayout.WEST,
                                                        pointRois_file_path_open_button);

        //set the constraints for the JCheckbox
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.NORTH,
                                                        pointRois_create_new_checkbox,
                                                        0,
                                                        SpringLayout.NORTH,
                                                        pointRois_file_path_select_button);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.EAST,
                                                        pointRois_create_new_checkbox,
                                                        -5,
                                                        SpringLayout.WEST,
                                                        pointRois_file_path_select_button);
        thirdSectionJPanel_spring_layout.putConstraint(SpringLayout.SOUTH,
                                                        pointRois_create_new_checkbox,
                                                        0,
                                                        SpringLayout.SOUTH,
                                                        pointRois_file_path_select_button);

    }

    //method to add the pointRois action listeners
    public static void addPointRoisActionListeners() {

        //#region add the action listener for the open button
        //set the action listener for the open button
        //get the action listener for the open button
        ActionListener[] action_listeners = pointRois_file_path_open_button.getActionListeners();
        //remove the action listeners
        for (ActionListener action_listener : action_listeners) {

            pointRois_file_path_open_button.removeActionListener(action_listener);

        }
        //add the new listener
        pointRois_file_path_open_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the file path from the text field
                String file_path = pointRois_Rois_file_path_text_field.getText();

                //get the file
                File opening_roi_file = new File(file_path);

                //check if it actually exists, if it is a .roi file or a .zip file
                if (opening_roi_file.exists() && (file_path.endsWith(".roi") || file_path.endsWith(".zip"))) {

                    //get the roi manager
                    RoiManager roi_manager = RoiManager.getInstance();

                    //reset it
                    roi_manager.reset();

                    //open the file
                    roi_manager.runCommand("Open", file_path);

                } else {

                    //if it doesn't exist, tell the user
                    IJ.error("The file does not exist or is not a .roi or .zip file");

                }
                
            }

        });
        //end region
        //#endregion

        //#region add the action listener for the select button
        //get the action listeners for the select button
        action_listeners = pointRois_file_path_select_button.getActionListeners();
        //remove the action listeners
        for (ActionListener action_listener : action_listeners) {

            pointRois_file_path_select_button.removeActionListener(action_listener);

        }
        //add the new listener to the select button
        //add the new listener
        pointRois_file_path_open_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get a file chooser
                JFileChooser file_chooser = new JFileChooser();

                //set the file filter
                file_chooser.setFileFilter(new FileNameExtensionFilter("Roi files", "roi", "zip"));

                //set the file selection mode
                file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                //set the dialog title
                file_chooser.setDialogTitle("Select a .roi or .zip file");

                //show the dialog
                int return_value = file_chooser.showOpenDialog(null);

                //if the user selected a file
                if (return_value == JFileChooser.APPROVE_OPTION) {

                    //get the file
                    File selected_file = file_chooser.getSelectedFile();

                    //get the file path
                    String file_path = selected_file.getAbsolutePath();

                    //set the text field to the file path
                    pointRois_Rois_file_path_text_field.setText(file_path);

                }
                
            }

        });

        //#endregion


    }

    //method for the Other Rois
    public static void startOtherRois(){ 
    
        //set the flag to true
        OtherRois_initialized = true;

        //log that it has been initialized
        IJ.log("Other Rois is initialized");
    
    }

    //method for the _ifInitializedReset of the startPointRois
    public static void startOtherRois_ifInitializedReset() {

        //find out if it is initialized
        if (OtherRois_initialized) {

            //set the flag to false
            OtherRois_initialized = false;

            //get the method from the class
            Method method = null;

            try {
                method = generalRoiCreation_menu.class.getMethod("startOtherRois");

                //if you get it, find the index of the method in the array list
                int method_index = generalRoiCreation_menu_Second_Section_Button_Method_arraylist.indexOf(method);

                //get the button from the array list
                JButton button = generalRoiCreation_menu_Second_Section_Button_arraylist.get(method_index);

                //set the button to enabled
                button.setEnabled(true);
                
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    //method to add the points roi ui
    public static void addtOtherRoisUI() {



    }

    //method to reset the third section initialized components
    public static void resetThirdSectionInitialized() {

        //loop through the number of buttons of the second section
        for (int i = 0; i < generalRoiCreation_menu_Second_Section_Button_arraylist.size(); i++) {
            
            //get the method name
            String method_name = generalRoiCreation_menu_Second_Section_Button_Method_arraylist.get(i).getName();

            //add the ending _isInitialized to the method name
            method_name = method_name + "_ifInitializedReset";

            //get the new method based on the string
            Method method = null;

            //try to get the method
            try {

                //get the method
                method = generalRoiCreation_menu.class.getMethod(method_name);

                //try to invoke the method
                try {

                    //invoke the method
                    method.invoke(null);

                } catch (IllegalAccessException  e) {

                    //log the error
                    IJ.log("Error invoking the method: " + method_name);

                    //print the stack trace
                    e.printStackTrace();

                } catch (IllegalArgumentException  e) {

                    //log the error
                    IJ.log("Error invoking the method: " + method_name);

                    //print the stack trace
                    e.printStackTrace();

                } catch (InvocationTargetException  e) {

                    //log the error
                    IJ.log("Error invoking the method: " + method_name);

                    //print the stack trace
                    e.printStackTrace();

                }

            } catch (NoSuchMethodException  e) {

                //log the error
                IJ.log("Error getting the method: " + method_name);

                //print the stack trace
                e.printStackTrace();

            } catch (SecurityException  e) {

                //log the error
                IJ.log("Error getting the method: " + method_name);

                //print the stack trace
                e.printStackTrace();

            } 

        }



    }

    //method to know if the frame is initialized
    public static boolean isInitialized() {

        //return the flag
        return generalRoiCreation_menu_frame_initialized;

    }

    //method to reset the jframe and the layouts
    public static void resetMenu() {

    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //Strings
    generalRoiCreationMenu_default_file_path_string = new String("/home/username/file");

    //ImagePlus
    mainImage = new ImagePlus();

    //integers
    mainImage_id = 0;

    //flag to know if the frame is initialized
    generalRoiCreation_menu_frame_initialized = false;

    //ArrayList for the second section button's classes
    generalRoiCreation_menu_Second_Section_Button_Method_arraylist = new ArrayList<Method>();

    //flags to know if a roi type is initialized
    PointRois_initialized = false;
    OtherRois_initialized = false;
    
    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//
    
    //JFrames
    generalRoiCreation_menu_frame = new JFrame("General ROI Creation Menu");

    //JPanels
    thirdSectionJPanel = new JPanel();

    //SpringLayouts
    generalRoiCreation_menu_springLayout = new SpringLayout();

        //Third section's JPanel spring layout
    thirdSectionJPanel_spring_layout = new SpringLayout();
    

    //declare the content pane for the frame
    generalRoiCreation_menu_contentPane = generalRoiCreation_menu_frame.getContentPane();

    //JLabels
    generalRoiCreation_menu_file_path_label = new JLabel("Main File Path: ");
        
        //from the third section
    pointRois_file_path_label = new JLabel("File Path: ");
        

    //JTextFields
    generalRoiCreation_menu_file_path_textField = new JTextField(20);
    
        //from the third section
    pointRois_Rois_file_path_text_field = new JTextField(20);


    //JButtons
    generalRoiCreation_menu_file_path_select_button = new JButton("Select File");
    generalRoiCreation_menu_file_path_open_button = new JButton("Open");

    generalRoiCreation_menu_button_create_point_rois = new JButton("Point ROIs");
    generalRoiCreation_menu_button_create_other_rois = new JButton("Other ROIs");

    //JSeparator
    generalRoiCreation_menu_First_Second_separator = new JSeparator();

    generalRoiCreation_menu_Second_Third_separator = new JSeparator(JSeparator.VERTICAL);

    //declare the new JButton ArraList
    generalRoiCreation_menu_buttonArrayList = new ArrayList<JButton>();
    generalRoiCreation_menu_Second_Section_Button_arraylist = new ArrayList<JButton>();


    //end of the region
    //#endregion
    
    //end of region
    //#endregion

    }
    
    //method to close the menu
    public static void closeMenu() {

        //close the frame
        generalRoiCreation_menu_frame.dispose();

        //reset the manu
        resetMenu();

        //run the method in the mainMenu to reset the button
        mainMenu.resetGeneralRoiCreationMenuButton();

        //set the boolean to false
        generalRoiCreation_menu_frame_initialized = false;

    }

    //method to reopen the full menu
    public static void reopenMenu() {

        //get the JFrame location
        Point generalRoiCreation_menu_frame_location = generalRoiCreation_menu_frame.getLocation();

        //reset the menu
        closeMenu();

        //run the method to initialize the menu
        initializeFrame();

        //set the frame's location again
        generalRoiCreation_menu_frame.setLocation(generalRoiCreation_menu_frame_location);

    }

    //end of the region
    //#endregion

}

