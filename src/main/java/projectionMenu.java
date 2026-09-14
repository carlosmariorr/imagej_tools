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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;

//#endregion ############################################ IMPORTS #####################################################


//start of the class
public class projectionMenu {

    
    //#region ############################################### GLOBALS #####################################################

    //the current class
    static Class<?> currentClass = projectionMenu.class;

    //the frame name
    public static String frameName = "Projection Menu";

    //the boolean to know if the frame is initialized
    public static boolean isInitialized = false;

    //the menu width and the menu height
    public static int menuWidth = 1200;
    public static int menuHeight = 370;

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

    //#region ############################################### SINGLE MENU #################################################

    //JLabels
    public static JLabel singleMenu_file_label = new JLabel("File path:");
    public static JLabel singleMenu_projection_type_label = new JLabel("Type:");
    public static JLabel singleMenu_channels_label = new JLabel("Channels:");
    public static JLabel singleMenu_time_point_label = new JLabel("Time Points:");
    public static JLabel singleMenu_top_slider_label = new JLabel("Top:");
    public static JLabel singleMenu_bottom_slider_label = new JLabel("Bottom:");
    public static JLabel singleMenu_save_label = new JLabel("Save path:");

    //JTextFields
    public static JTextField singleMenu_file_textField = new JTextField();
    public static JTextField singleMenu_channels_textField = new JTextField();
    public static JTextField singleMenu_time_point_textField = new JTextField();
    public static JTextField singleMenu_save_textField = new JTextField();

    //JButtons
    public static JButton singleMenu_select_file_button = new JButton("Select");
    public static JButton singleMenu_open_file_button = new JButton("Open");
    public static JButton singleMenu_add_image_button = new JButton("Add Image From Window");
    public static JButton singleMenu_unlist_image_button = new JButton("Unlist Image");
    public static JButton singleMenu_show_image_button = new JButton("Show Image");
    public static JButton singleMenu_make_projection_button = new JButton("Make Projection");
    public static JButton singleMenu_select_projection_window_button = new JButton("Select Window");
    public static JButton singleMenu_select_save_button = new JButton("Select");
    public static JButton singleMenu_save_button = new JButton("Save");

    //JPanels
    public static JPanel singleMenu_channels_panel = new JPanel();
    public static JPanel singleMenu_time_point_panel = new JPanel();

    //the pannel layouts
    public static SpringLayout singleMenu_channels_panel_springLayout = new SpringLayout();
    public static SpringLayout singleMenu_time_point_panel_springLayout = new SpringLayout();

    //JCheckboxes
    public static JCheckBox singleMenu_auto_project_checkbox = new JCheckBox("Auto Project");
    public static JCheckBox singleMenu_all_slices_checkbox = new JCheckBox("All Slices");

    //JCheckboxes arraylist
    public static ArrayList<JCheckBox> singleMenu_channels_checkbox_arraylist = new ArrayList<JCheckBox>();
    public static ArrayList<JCheckBox> singleMenu_time_point_checkbox_arraylist = new ArrayList<JCheckBox>();

    //Jlists
    public static JList<String> singleMenu_image_list = new JList<String>();

    //JListModel
    public static DefaultListModel<String>singleMenu_jlist_model = new DefaultListModel<String>();

    //JScrollPane
    public static JScrollPane singleMenu_image_list_scrollPane = new JScrollPane(singleMenu_image_list);

    //JComboBox
    public static JComboBox<String> singleMenu_projection_type_choice = new JComboBox<String>();
    
    //JSliders
    public static JSlider singleMenu_top_slider = new JSlider();
    public static JSlider singleMenu_bottom_slider = new JSlider();

    //JSpinners
    public static JSpinner singleMenu_top_spinner = new JSpinner();
    public static JSpinner singleMenu_bottom_spinner = new JSpinner();

    //List of types of projections
    public static String[] projection_types = {"Max Intensity", "Sum", "Min Intensity", "Mean", "Median", "Standard Deviation"};

    //List of the projectionObjects
    public static ArrayList<projectionObject> projectionObjectsList = new ArrayList<projectionObject>();

    //a bool to trigger from combo box
    public static boolean triggerFromSingleComoBox = true;

    //a bool to trigger form single JList
    public static boolean triggerFromSingleJList = true;

    //A bool to trigger from single auto project checkbox
    public static boolean triggerFromSingleAutoProjectCheckbox = true;

    //a bool to trigger from the top slider
    public static boolean triggerFromSingleTopSlider = true;

    //a bool to trigger from the bottom slider
    public static boolean triggerFromSingleBottomSlider = true;

    //a bool to trigger from the top spinner
    public static boolean triggerFromSingleTopSpinner = true;

    //a bool to trigger from the bottom spinner
    public static boolean triggerFromSingleBottomSpinner = true;

    //the array list for the projected images and the corresponding parameters


    //#endregion ############################################ SINGLE MENU #################################################


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

        //pack the frame
        projectionMenu_frame.pack();

        //set the frame to visible
        projectionMenu_frame.setVisible(true);

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

                //call the method to initialize single
                startSingleMenu();

                //set the button to disabled
                singleButton.setEnabled(false);

            }

        });

        //set the batch button listener
        batchButton.addActionListener(new ActionListener() {

            //add the listener to the batch button
            public void actionPerformed(ActionEvent actionEvent) {

                //call the method to initialize batch
                startBatchMenu();

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

    //method to start the single menu
    public static void startSingleMenu() {

        //reset the single panel
        resetSinglePanel();

        //add the components to the panel
        addComponentsToSinglePanel();

        //set the restraints
        setSinglePanelConstraints();

        //add the listeners
        addSinglePanelListeners();

        //loop through the button list
        for (JButton button : mainButtonsArray) {

            //if the button is not the single button, then set it to enabled
            if (button != singleButton) {

                //set the button to disabled
                button.setEnabled(true);

            }

        }

        //redraw the frame
        projectionMenu_frame.revalidate();

        //redraw the panel
        projectionPanel.repaint();

    }

    //method to reset the single panel
    public static void resetSinglePanel() {

        //clear the panel
        projectionPanel.removeAll();

        //make a new layout
        projectionPanel_springLayout = new SpringLayout();

        //set the layout
        projectionPanel.setLayout(projectionPanel_springLayout);

        //redraw the frame
        projectionMenu_frame.revalidate();

        //redraw the panel
        projectionPanel.repaint();

        //JLabels
        singleMenu_file_label = new JLabel("File path:");
        singleMenu_projection_type_label = new JLabel("Type:");
        singleMenu_channels_label = new JLabel("Channels:");
        singleMenu_time_point_label = new JLabel("Time Points:");
        singleMenu_top_slider_label = new JLabel("Top:");
        singleMenu_bottom_slider_label = new JLabel("Bottom:");
        singleMenu_save_label = new JLabel("Save path:");

        //JTextFields
        singleMenu_file_textField = new JTextField();
        singleMenu_channels_textField = new JTextField();
        singleMenu_time_point_textField = new JTextField();
        singleMenu_save_textField = new JTextField();

        //JButtons
        singleMenu_select_file_button = new JButton("Select");
        singleMenu_open_file_button = new JButton("Open");
        singleMenu_add_image_button = new JButton("Add Image From Window");
        singleMenu_unlist_image_button = new JButton("Unlist Image");
        singleMenu_show_image_button = new JButton("Show Image");
        singleMenu_make_projection_button = new JButton("Make Projection");
        singleMenu_select_projection_window_button = new JButton("Select Window");
        singleMenu_select_save_button = new JButton("Select");
        singleMenu_save_button = new JButton("Save");

        //JPanels
        singleMenu_channels_panel = new JPanel();
        singleMenu_time_point_panel = new JPanel();


        //the pannel layouts
        singleMenu_channels_panel_springLayout = new SpringLayout();
        singleMenu_time_point_panel_springLayout = new SpringLayout();

        //JCheckboxes
        singleMenu_auto_project_checkbox = new JCheckBox("Auto Project");
        singleMenu_all_slices_checkbox = new JCheckBox("All Slices");

        //JCheckboxes arraylist
        singleMenu_channels_checkbox_arraylist = new ArrayList<JCheckBox>();
        singleMenu_time_point_checkbox_arraylist = new ArrayList<JCheckBox>();

        //Jlists
        singleMenu_image_list = new JList<String>();

        //JListModel
        singleMenu_jlist_model = new DefaultListModel<String>();

        //JScrollPane
        singleMenu_image_list_scrollPane = new JScrollPane(singleMenu_image_list);

        //JComboBox
        singleMenu_projection_type_choice = new JComboBox<String>();

        //JSliders
        singleMenu_top_slider = new JSlider();
        singleMenu_bottom_slider = new JSlider();

        //JSpinners
        singleMenu_top_spinner = new JSpinner();
        singleMenu_bottom_spinner = new JSpinner();

        //List of types of projections
        projection_types = new String[]{"Max Intensity", "Sum", "Min Intensity", "Mean", "Median", "Standard Deviation"};

        //List of the projectionObjects
        projectionObjectsList = new ArrayList<projectionObject>();

        //a bool to trigger from combo box
        triggerFromSingleComoBox = true;

        //a bool to trigger form single JList
        triggerFromSingleJList = true;

        //A bool to trigger from single auto project checkbox
        triggerFromSingleAutoProjectCheckbox = true;

        //a bool to trigger from the top slider
        triggerFromSingleTopSlider = true;

        //a bool to trigger from the bottom slider
        triggerFromSingleBottomSlider = true;

        //a bool to trigger from the top spinner
        triggerFromSingleTopSpinner = true;

        //a bool to trigger from the bottom spinner
        triggerFromSingleBottomSpinner = true;

    }

    //method to add the components to the single panel
    public static void addComponentsToSinglePanel() {

        //add the components to the panel
        
        //Labels
        projectionPanel.add(singleMenu_file_label);
        projectionPanel.add(singleMenu_projection_type_label);
        projectionPanel.add(singleMenu_channels_label);
        projectionPanel.add(singleMenu_time_point_label);
        projectionPanel.add(singleMenu_top_slider_label);
        projectionPanel.add(singleMenu_bottom_slider_label);
        projectionPanel.add(singleMenu_save_label);

        //TextFields
        projectionPanel.add(singleMenu_file_textField);
        projectionPanel.add(singleMenu_save_textField);

        //Buttons
        projectionPanel.add(singleMenu_select_file_button);
        projectionPanel.add(singleMenu_open_file_button);
        projectionPanel.add(singleMenu_add_image_button);
        projectionPanel.add(singleMenu_unlist_image_button);
        projectionPanel.add(singleMenu_show_image_button);
        projectionPanel.add(singleMenu_make_projection_button);
        projectionPanel.add(singleMenu_select_projection_window_button);
        projectionPanel.add(singleMenu_select_save_button);
        projectionPanel.add(singleMenu_save_button);

        //add the layouts to the channels and time point panels
        singleMenu_channels_panel.setLayout(singleMenu_channels_panel_springLayout);
        singleMenu_time_point_panel.setLayout(singleMenu_time_point_panel_springLayout);

        //Panels
        projectionPanel.add(singleMenu_channels_panel);
        projectionPanel.add(singleMenu_time_point_panel);

        //Checkboxes
        projectionPanel.add(singleMenu_auto_project_checkbox);
        projectionPanel.add(singleMenu_all_slices_checkbox);

        //add the default list model to the list and then the list to the pane
        singleMenu_image_list.setModel(singleMenu_jlist_model);
        singleMenu_image_list_scrollPane = new JScrollPane(singleMenu_image_list);

        //JLists
        projectionPanel.add(singleMenu_image_list_scrollPane);

        //JComboBox
        singleMenu_projection_type_choice = new JComboBox<String>(projection_types);
        projectionPanel.add(singleMenu_projection_type_choice);

        //JSliders
        projectionPanel.add(singleMenu_top_slider);
        projectionPanel.add(singleMenu_bottom_slider);

        //set the slider ticks
        singleMenu_top_slider.setMajorTickSpacing(1);
        singleMenu_top_slider.setPaintTicks(true);

        singleMenu_bottom_slider.setMajorTickSpacing(1);
        singleMenu_bottom_slider.setPaintTicks(true);
        
        //JSpinners
        projectionPanel.add(singleMenu_top_spinner);
        projectionPanel.add(singleMenu_bottom_spinner);

    }

    //method to set the restraints
    public static void setSinglePanelConstraints() {

        //for the file label
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_file_label, 5, SpringLayout.WEST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_file_label, 5, SpringLayout.NORTH, projectionPanel);

        //for the file open button
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_open_file_button, -5, SpringLayout.EAST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_open_file_button, 0, SpringLayout.NORTH, singleMenu_file_label);

        //for the file select button
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_select_file_button, -5, SpringLayout.WEST, singleMenu_open_file_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_select_file_button, 0, SpringLayout.NORTH, singleMenu_file_label);

        //for the file text field
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_file_textField, 5, SpringLayout.EAST, singleMenu_file_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_file_textField, -5, SpringLayout.WEST, singleMenu_select_file_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_file_textField, 0, SpringLayout.NORTH, singleMenu_file_label);

        //for the save label
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_save_label, 5, SpringLayout.WEST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_save_label, -5, SpringLayout.SOUTH, projectionPanel);

        //for the save button
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_save_button, -5, SpringLayout.EAST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_save_button, 0, SpringLayout.SOUTH, singleMenu_save_label);

        //for the select button
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_select_save_button, -5, SpringLayout.WEST, singleMenu_save_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_select_save_button, 0, SpringLayout.SOUTH, singleMenu_save_label);

        //for the save text field
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_save_textField, 5, SpringLayout.EAST, singleMenu_save_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_save_textField, -5, SpringLayout.WEST, singleMenu_select_save_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_save_textField, 0, SpringLayout.SOUTH, singleMenu_save_label);
    
        //for the scrollpane
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_image_list_scrollPane, 5, SpringLayout.WEST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_image_list_scrollPane, -5, SpringLayout.HORIZONTAL_CENTER, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_image_list_scrollPane, 5, SpringLayout.SOUTH, singleMenu_file_textField);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_image_list_scrollPane, -5, SpringLayout.NORTH, singleMenu_save_textField);

        //for the add image button
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_add_image_button, 0, SpringLayout.NORTH, singleMenu_image_list_scrollPane);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_add_image_button, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //for the unlist image button
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_unlist_image_button, 0, SpringLayout.NORTH, singleMenu_add_image_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_unlist_image_button, 5, SpringLayout.EAST, singleMenu_add_image_button);

        //for the show image button
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_show_image_button, 0, SpringLayout.NORTH, singleMenu_add_image_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_show_image_button, 5, SpringLayout.EAST, singleMenu_unlist_image_button);

        //for the projection type label
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_projection_type_label, 5, SpringLayout.SOUTH, singleMenu_add_image_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_projection_type_label, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //for the projection type choice
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_projection_type_choice, 0, SpringLayout.NORTH, singleMenu_projection_type_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_projection_type_choice, 5, SpringLayout.EAST, singleMenu_projection_type_label);

        //for the make projection button
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_make_projection_button, 0, SpringLayout.NORTH, singleMenu_projection_type_choice);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_make_projection_button, 5, SpringLayout.EAST, singleMenu_projection_type_choice);

        //for the select projection window button
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_select_projection_window_button, 0, SpringLayout.NORTH, singleMenu_make_projection_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_select_projection_window_button, 5, SpringLayout.EAST, singleMenu_make_projection_button);

        //for the auto project checkbox
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_auto_project_checkbox, 0, SpringLayout.NORTH, singleMenu_select_projection_window_button);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_auto_project_checkbox, 5, SpringLayout.EAST, singleMenu_select_projection_window_button);

        //for the channels label
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_channels_label, 5, SpringLayout.SOUTH, singleMenu_projection_type_choice);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_channels_label, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //for the channels panel
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_channels_panel, 0, SpringLayout.NORTH, singleMenu_channels_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_channels_panel, 5, SpringLayout.EAST, singleMenu_channels_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_channels_panel, -5, SpringLayout.EAST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_channels_panel, 5, SpringLayout.SOUTH, singleMenu_channels_label);

        //for the time point label 
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_time_point_label, 5, SpringLayout.SOUTH, singleMenu_channels_panel);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_time_point_label, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //for the time point panel
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_time_point_panel, 0, SpringLayout.NORTH, singleMenu_time_point_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_time_point_panel, 5, SpringLayout.EAST, singleMenu_time_point_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_time_point_panel, -5, SpringLayout.EAST, projectionPanel);
        projectionPanel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_time_point_panel, 5, SpringLayout.SOUTH, singleMenu_time_point_label);

        //for the all slices checkbox
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_all_slices_checkbox, 5, SpringLayout.SOUTH, singleMenu_time_point_panel);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_all_slices_checkbox, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //for the bottom slider label
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_bottom_slider_label, 5, SpringLayout.SOUTH, singleMenu_all_slices_checkbox);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_bottom_slider_label, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //the bottom spinner
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_bottom_spinner, 0, SpringLayout.NORTH, singleMenu_bottom_slider_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_bottom_spinner, -5, SpringLayout.EAST, projectionPanel);

        //the bottom slider
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_bottom_slider, 0, SpringLayout.NORTH, singleMenu_bottom_slider_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_bottom_slider, 5, SpringLayout.EAST, singleMenu_bottom_slider_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_bottom_slider, -5, SpringLayout.WEST, singleMenu_bottom_spinner);

        //for the top slider label
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_top_slider_label, 5, SpringLayout.SOUTH, singleMenu_bottom_slider);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_top_slider_label, 5, SpringLayout.EAST, singleMenu_image_list_scrollPane);

        //the top spinner
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_top_spinner, 0, SpringLayout.NORTH, singleMenu_top_slider_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_top_spinner, -5, SpringLayout.EAST, projectionPanel);

        //the top slider
        projectionPanel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_top_slider, 0, SpringLayout.NORTH, singleMenu_top_slider_label);
        projectionPanel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_top_slider, 0, SpringLayout.WEST, singleMenu_bottom_slider);
        projectionPanel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_top_slider, -5, SpringLayout.WEST, singleMenu_top_spinner);

        //set the initial states
        
        //for the auto project checkbox
        singleMenu_auto_project_checkbox.setSelected(true);

        //for the all slices checkbox
        singleMenu_all_slices_checkbox.setSelected(true);

        //for the projection slider
        singleMenu_top_slider.setMinimum(0);
        singleMenu_top_slider.setMaximum(1);
        singleMenu_top_slider.setValue(1);

        //set enabled to false
        singleMenu_top_slider.setEnabled(false);

        //for the bottom slider
        singleMenu_bottom_slider.setMinimum(0);
        singleMenu_bottom_slider.setMaximum(1);
        singleMenu_bottom_slider.setValue(0);

        //set enabled to false
        singleMenu_bottom_slider.setEnabled(false);

        //for the bottom spinner
        singleMenu_bottom_spinner.setModel(new SpinnerNumberModel(0, 0, 1, 1));

        //set enabled to false
        singleMenu_bottom_spinner.setEnabled(false);

        //for the top spinner
        singleMenu_top_spinner.setModel(new SpinnerNumberModel(1, 0, 1, 1));

        //set enabled to false
        singleMenu_top_spinner.setEnabled(false);

        

    }

    //method to add the listeners
    public static void addSinglePanelListeners() {

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_select_file_button);
        //add the new listener
        singleMenu_select_file_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //set all triggers to false
                triggerFromSingleComoBox = false;

                //run the method to deselect for new image
                singleMenuDeselectForNewImage();

                //set all triggers to true
                triggerFromSingleComoBox = true;

                //get the selected file path
                String selectedFilePath = mainMenu.getFileFromChooser("image");

                //set the file text field to it
                singleMenu_file_textField.setText(selectedFilePath);

            }
        
        });

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_open_file_button);
        //add the new listener
        singleMenu_open_file_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the selected file path
                String filePath = singleMenu_file_textField.getText();

                //make sure that the file path is not empty and that the file exists
                if (filePath == "" || !new File(filePath).exists()) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "The file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    //return
                    return ;

                }

                //get the projection type save string
                String projectionTypeString = getProjectionTypeSaveString();

                //get the projection type
                String projectionType = (String) singleMenu_projection_type_choice.getSelectedItem();

                //get the parent directory
                String parentDirString = filePath.substring(0, filePath.lastIndexOf(File.separator));

                //if the parent directory doesn't end with a file separator, then add it
                if (!parentDirString.endsWith(File.separator)) {

                    //add the file separator
                    parentDirString = parentDirString + File.separator;

                }

                //get the file name without extension
                String fileNameWithoutExtension = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf("."));

                //get the file name with extension
                String fileNameWithExtension = filePath.substring(filePath.lastIndexOf(File.separator) + 1);

                //get the saveFilename
                String saveFilename = parentDirString + fileNameWithoutExtension + "_" + projectionTypeString + ".tif";

                //set the save text field to it
                singleMenu_save_textField.setText(saveFilename);

                //run the method to open the image
                ImagePlus addingImage = IJ.openImage(filePath);

                //show the image
                addingImage.show();

                //get the window id
                int projectingID = addingImage.getID();

                //get the projectionObject
                projectionObject addingProjectionObject = new projectionObject(addingImage);

                //get the number of channels
                int numberOfChannels = addingImage.getNChannels();

                //get the number of time points
                int numberOfTimePoints = addingImage.getNFrames();

                //get the number of slices
                int numberOfSlices = addingImage.getNSlices();

                //set the available parameters in the object
                //the projected image id
                addingProjectionObject.setProjectingImageID(projectingID);
                //the projecting path
                addingProjectionObject.setProjectingPath(filePath);
                //the projecting name
                addingProjectionObject.setProjectingName(fileNameWithExtension);
                //the type
                addingProjectionObject.setProjectionType(projectionType);
                //the projecting channel number
                addingProjectionObject.setChannels(numberOfChannels);
                //the projecting time point number
                addingProjectionObject.setFrames(numberOfTimePoints);
                //the projecting slice number
                addingProjectionObject.setSlices(numberOfSlices);

                //initiate the adding channel[]
                int[] addingChannels = new int[numberOfChannels];
                //loop through the channels and add them
                for (int i = 0; i < numberOfChannels; i++) {

                    //add the channel
                    addingChannels[i] = i + 1;

                }
                //set the channels
                addingProjectionObject.setProjectingChannels(addingChannels);
                
                //initiate the adding time points[]
                int[] addingTimePoints = new int[numberOfTimePoints];
                //loop through the time points and add them
                for (int i = 0; i < numberOfTimePoints; i++) {

                    //add the time point
                    addingTimePoints[i] = i + 1;

                }
                //set the time points
                addingProjectionObject.setProjectingFrames(addingTimePoints);

                //initiate the adding slices[]
                int[] addingSlices = new int[numberOfSlices];
                //loop through the slices and add them
                for (int i = 0; i < numberOfSlices; i++) {

                    //add the slice
                    addingSlices[i] = i + 1;

                }
                //set the slices
                addingProjectionObject.setProjectingSlices(addingSlices);
                
                //add the projectionObject to the list
                projectionObjectsList.add(addingProjectionObject);

                //update the JList
                updateJList("open button");

                //get the index of the addingProjectionObject name in the list model
                int addingProjectionObjectIndex = singleMenu_jlist_model.indexOf(addingProjectionObject.getProjectingName());

                //select the index in the list
                singleMenu_image_list.setSelectedIndex(addingProjectionObjectIndex);

            }
        
        });

        //remove the listeners from the JList
        commonSwingMethods.removeJListListeners(singleMenu_image_list);
        //add the new listener
        singleMenu_image_list.addListSelectionListener(new ListSelectionListener() {
            
            public void valueChanged(ListSelectionEvent e) {

                //if the trigger is true
                if (triggerFromSingleJList) {

                    //set all triggers to false
                    setAllSingleTriggers(false);

                    triggerFromSingleJList = true;

                }

                //find out if the value is adjusting
                boolean isAdjusting = e.getValueIsAdjusting();

                //if the value is adjusting, return
                if (isAdjusting) {

                    //return
                    return ;

                }

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //deselect for new image
                    singleMenuDeselectForNewImage();

                    //return
                    return ;

                }

                //get the projection object by the indx
                projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                //get the image path
                String imagePath = selectedProjectionObject.getProjectingPath();

                //set the file text field to it
                singleMenu_file_textField.setText(imagePath);

                //get the path without extension
                String pathWithoutExtension = imagePath.substring(0, imagePath.lastIndexOf("."));

                //get the projection type
                String savingProjectionType = getProjectionTypeSaveString();

                //get the save path
                String savePath = pathWithoutExtension + "_" + savingProjectionType + ".tif";

                //set the save text field to it
                singleMenu_save_textField.setText(savePath);

                //set the object's projecting path to it
                selectedProjectionObject.setProjectedPath(savePath);

                //set the trigger from combo box to false
                triggerFromSingleComoBox = false;

                //set the projection type choice to the object's projection type               
                singleMenu_projection_type_choice.setSelectedItem(selectedProjectionObject.getProjectionType());

                //set the trigger from combo box to true
                triggerFromSingleComoBox = true;

                //get the projectingChannels
                int[] projectingChannels = selectedProjectionObject.getProjectingChannels();

                //get the number of channels
                int numberOfChannels = selectedProjectionObject.getChannels();

                //clear the channels panlel
                singleMenu_channels_panel.removeAll();

                //reset the channels checkbox arraylist
                singleMenu_channels_checkbox_arraylist = new ArrayList<JCheckBox>();

                //if the number of channels is less that 5
                if (numberOfChannels < 5) {

                    //get a new checkbox for each channel and add it to the channels array list
                    for (int i = 0; i < numberOfChannels; i++) {

                        //make a new checkbox
                        JCheckBox addingCheckbox = new JCheckBox("Channel " + (i + 1));

                        //add the checkbox to the arraylist
                        singleMenu_channels_checkbox_arraylist.add(addingCheckbox);

                        //add the checkbox to the channels panel
                        singleMenu_channels_panel.add(addingCheckbox);

                        //set to deselected
                        addingCheckbox.setSelected(false);

                        //loop through the projecting channels, if i+1 is there, set the checkbox to selected
                        for (int projectingChannel : projectingChannels) {

                            //if the projecting channel is the same as i+1
                            if (projectingChannel == (i + 1)) {

                                //set the checkbox to selected
                                addingCheckbox.setSelected(true);

                            }

                        }

                        //if it's the first checkbox
                        if (i == 0) {

                            //set the constraints
                            singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.WEST, addingCheckbox, 0, SpringLayout.WEST, singleMenu_channels_panel);
                            singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.NORTH, addingCheckbox, 0, SpringLayout.NORTH, singleMenu_channels_panel);

                        } else {

                            //get the previous checkbox
                            JCheckBox previousCheckbox = singleMenu_channels_checkbox_arraylist.get(i - 1);

                            //set the constraints
                            singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.WEST, addingCheckbox, 5, SpringLayout.EAST, previousCheckbox);
                            singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.NORTH, addingCheckbox, 0, SpringLayout.NORTH, previousCheckbox);

                        }

                        //redraw the frame and repaint
                        projectionMenu_frame.revalidate();

                        //repaint the frame
                        projectionMenu_frame.repaint();

                        //add checkbox listener so that when it is selected, change the projecting Object
                        addingCheckbox.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {

                                //get the selected index
                                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                                //if the selected index is -1
                                if (selectedIndex == -1) {

                                    //return
                                    return ;

                                }

                                //get the projection object by the indx
                                projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                                //get the number of channels
                                int numberOfChannels = selectedProjectionObject.getChannels();

                                //start an array list of integers 
                                ArrayList<Integer> projectingChannelsArrayList = new ArrayList<Integer>();

                                //loop through the number of channels, get the checkbox and if it is selected, add the index+1 to the arraylist
                                for (int i = 0; i < numberOfChannels; i++) {

                                    //get the checkbox
                                    JCheckBox gettingCheckbox = singleMenu_channels_checkbox_arraylist.get(i);

                                    //if the checkbox is selected
                                    if (gettingCheckbox.isSelected()) {

                                        //add the index+1 to the arraylist
                                        projectingChannelsArrayList.add(i + 1);

                                    }

                                }

                                //start the int[] with the number of entries in the arraylist
                                int[] projectingChannels = new int[projectingChannelsArrayList.size()];

                                //set them
                                for (int i = 0; i < projectingChannelsArrayList.size(); i++) {

                                    //set the projecting channel
                                    projectingChannels[i] = projectingChannelsArrayList.get(i);

                                }

                                //set the projecting channels in the object
                                selectedProjectionObject.setProjectingChannels(projectingChannels);

                                //update the JList
                                updateJList("from channel checkbox");

                                //handle the auto
                                // handleAutoProject();

                                //handle the all slices
                                // handleAllSlices();

                                //set the selection 
                                singleMenu_image_list.setSelectedIndex(selectedIndex);

                            }
                            
                        });

                    }

                } else {

                    //add the channels text field to the channels panel
                    singleMenu_channels_panel.add(singleMenu_channels_textField);

                    //set the constraints
                    singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_channels_textField, 0, SpringLayout.WEST, singleMenu_channels_panel);
                    singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_channels_textField, 0, SpringLayout.NORTH, singleMenu_channels_panel);
                    singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_channels_textField, 0, SpringLayout.SOUTH, singleMenu_channels_panel);
                    singleMenu_channels_panel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_channels_textField, 0, SpringLayout.EAST, singleMenu_channels_panel);

                }

                //get the projectingFrames
                int[] projectingFrames = selectedProjectionObject.getProjectingFrames();

                //get the number of time points
                int numberOfTimePoints = selectedProjectionObject.getFrames();

                //clear the time point panel
                singleMenu_time_point_panel.removeAll();

                //start a new arraylist of checkboxes
                singleMenu_time_point_checkbox_arraylist = new ArrayList<JCheckBox>();

                //if the number of time points is less that 5
                if (numberOfTimePoints < 5) {

                    //get a checkbox for each time point
                    for (int i = 0; i < numberOfTimePoints; i++) {

                        //make a new checkbox
                        JCheckBox addingCheckbox = new JCheckBox("Time Point " + (i + 1));

                        //add the checkbox to the arraylist
                        singleMenu_time_point_checkbox_arraylist.add(addingCheckbox);

                        //add the checkbox to the time point panel
                        singleMenu_time_point_panel.add(addingCheckbox);

                        //set to deselected
                        addingCheckbox.setSelected(false);

                        //loop through the projecting frames, if i+1 is there, set the checkbox to selected
                        for (int projectingFrame : projectingFrames) {

                            //if the projecting frame is the same as i+1
                            if (projectingFrame == (i + 1)) {

                                //set the checkbox to selected
                                addingCheckbox.setSelected(true);

                            }

                        }

                        //if it's the first checkbox
                        if (i == 0) {

                            //set the constraints
                            singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.WEST, addingCheckbox, 0, SpringLayout.WEST, singleMenu_time_point_panel);
                            singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.NORTH, addingCheckbox, 0, SpringLayout.NORTH, singleMenu_time_point_panel);

                        } else {

                            //set the constraints
                            singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.WEST, addingCheckbox, 5, SpringLayout.EAST, singleMenu_time_point_checkbox_arraylist.get(i - 1));
                            singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.NORTH, addingCheckbox, 0, SpringLayout.NORTH, singleMenu_time_point_checkbox_arraylist.get(i - 1));

                        }

                        //redraw the frame and repaint
                        projectionMenu_frame.revalidate();

                        //repaint the frame
                        projectionMenu_frame.repaint();

                        //add checkbox listeners here
                        addingCheckbox.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {

                                //get the selected index
                                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                                //if the selected index is -1
                                if (selectedIndex == -1) {

                                    //return
                                    return ;

                                }

                                //get the projection object by the indx
                                projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                                //get the number of time points
                                int numberOfTimePoints = selectedProjectionObject.getFrames();

                                //start an array list of integers 
                                ArrayList<Integer> projectingFramesArrayList = new ArrayList<Integer>();

                                //loop through the number of time points, get the checkbox and if it is selected, add the index+1 to the arraylist
                                for (int i = 0; i < numberOfTimePoints; i++) {

                                    //get the checkbox
                                    JCheckBox gettingCheckbox = singleMenu_time_point_checkbox_arraylist.get(i);

                                    //if the checkbox is selected
                                    if (gettingCheckbox.isSelected()) {

                                        //add the index+1 to the arraylist
                                        projectingFramesArrayList.add(i + 1);

                                    }

                                }

                                //start the int[] with the number of entries in the arraylist
                                int[] projectingFrames = new int[projectingFramesArrayList.size()];

                                //set them
                                for (int i = 0; i < projectingFramesArrayList.size(); i++) {

                                    //set the projecting frame
                                    projectingFrames[i] = projectingFramesArrayList.get(i);

                                }

                                //set the projecting frames in the object
                                selectedProjectionObject.setProjectingFrames(projectingFrames);

                                //update the JList
                                updateJList("from time poitn checkbox");

                                //handle the auto
                                // handleAutoProject();

                                //handle the all slices
                                // handleAllSlices();

                                //set the selection 
                                singleMenu_image_list.setSelectedIndex(selectedIndex);

                            }
                            
                        });

                    }

                }
                else {

                    //add the time point text field to the time point panel
                    singleMenu_time_point_panel.add(singleMenu_time_point_textField);

                    //set the constraints
                    singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.WEST, singleMenu_time_point_textField, 0, SpringLayout.WEST, singleMenu_time_point_panel);
                    singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.NORTH, singleMenu_time_point_textField, 0, SpringLayout.NORTH, singleMenu_time_point_panel);
                    singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.SOUTH, singleMenu_time_point_textField, 0, SpringLayout.SOUTH, singleMenu_time_point_panel);
                    singleMenu_time_point_panel_springLayout.putConstraint(SpringLayout.EAST, singleMenu_time_point_textField, 0, SpringLayout.EAST, singleMenu_time_point_panel);

                }

                //get the number of slices
                int numberOfSlices = selectedProjectionObject.getSlices();

                //set the top slider max and min
                singleMenu_top_slider.setMinimum(1);
                singleMenu_top_slider.setMaximum(numberOfSlices);

                //set the bottom slider to maxna and min
                singleMenu_bottom_slider.setMinimum(1);
                singleMenu_bottom_slider.setMaximum(numberOfSlices);

                //set the spinner max and min
                singleMenu_top_spinner.setModel(new SpinnerNumberModel(1, 1, numberOfSlices, 1));
                singleMenu_bottom_spinner.setModel(new SpinnerNumberModel(1, 1, numberOfSlices, 1));

                //set the top slider to the max
                singleMenu_top_slider.setValue(numberOfSlices);

                //set the bottom slider to the min
                singleMenu_bottom_slider.setValue(1);

                //set the top spinner to the max
                singleMenu_top_spinner.setValue(numberOfSlices);

                //set the bottom spinner to the min
                singleMenu_bottom_spinner.setValue(1);

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

                //if the trigger from single jlist is true
                if (triggerFromSingleJList) {

                    //set all triggers to true
                    setAllSingleTriggers(true);

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from: the JList" );

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }

                }    
            }

        });

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_make_projection_button);
        //add the new listener
        singleMenu_make_projection_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //return
                    return ;

                }

                //log that the projection is being made
                IJ.log(frameName + " - Making projection from make projection button");

                //run the method to make the projection
                makeProjection(selectedIndex);

            }
        
        });

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_unlist_image_button);
        //add the new listener
        singleMenu_unlist_image_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //return
                    return ;

                }

                //remove the projectionObject from the list
                projectionObjectsList.remove(selectedIndex);

                //update the JList
                updateJList("from the unlist button");

            }
        
        });

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_add_image_button);
        //add the new listener that just prints: adding image
        singleMenu_add_image_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the current image
                ImagePlus currentImage = WindowManager.getCurrentImage();

                //get the current image id
                int currentImageID = currentImage.getID();

                //get the current image path
                String currentImagePath = currentImage.getOriginalFileInfo().directory + currentImage.getOriginalFileInfo().fileName;

                //get the current image name
                String currentImageName = currentImage.getOriginalFileInfo().fileName;

                //get the current projection type
                String currentProjectionType = (String) singleMenu_projection_type_choice.getSelectedItem();

                //get the current channels
                int currentChannels = currentImage.getNChannels();

                //get the current time points
                int currentTimePoints = currentImage.getNFrames();

                //get the current slices
                int currentSlices = currentImage.getNSlices();

                //loop through the current image channels to get a list of them
                int[] currentChannelsArray = new int[currentChannels];

                //loop through the channels and add them
                for (int i = 0; i < currentChannels; i++) {

                    //add the channel
                    currentChannelsArray[i] = i + 1;

                }

                //loop through the current image time points to get a list of them
                int[] currentTimePointsArray = new int[currentTimePoints];

                //loop through the time points and add them
                for (int i = 0; i < currentTimePoints; i++) {

                    //add the time point
                    currentTimePointsArray[i] = i + 1;

                }

                //loop through the current image slices to get a list of them
                int[] currentSlicesArray = new int[currentSlices];

                //loop through the slices and add them
                for (int i = 0; i < currentSlices; i++) {

                    //add the slice
                    currentSlicesArray[i] = i + 1;

                }

                //make a new projectionObject
                projectionObject addingProjectionObject = new projectionObject(currentImage);

                //add the path
                addingProjectionObject.setProjectingPath(currentImagePath);

                //add the projecting image id
                addingProjectionObject.setProjectingImageID(currentImageID);

                //add the name
                addingProjectionObject.setProjectingName(currentImageName);

                //add the projection type
                addingProjectionObject.setProjectionType(currentProjectionType);

                //add the channels
                addingProjectionObject.setChannels(currentChannels);

                //add the time points
                addingProjectionObject.setFrames(currentTimePoints);

                //add the slices
                addingProjectionObject.setSlices(currentSlices);

                //add the channels array
                addingProjectionObject.setProjectingChannels(currentChannelsArray);

                //add the time points array
                addingProjectionObject.setProjectingFrames(currentTimePointsArray);

                //add the slices array
                addingProjectionObject.setProjectingSlices(currentSlicesArray);

                //add the projectionObject to the list
                projectionObjectsList.add(addingProjectionObject);

                //update the JList
                updateJList("from the add image button");

                //get the index of the addingProjectionObject name in the list model
                int addingProjectionObjectIndex = singleMenu_jlist_model.indexOf(addingProjectionObject.getProjectingName());

                //select it
                singleMenu_image_list.setSelectedIndex(addingProjectionObjectIndex);

            }
        
        });

        //remove the listeners from the button
        commonSwingMethods.removeButtonListeners(singleMenu_show_image_button);
        //add the new listener
        singleMenu_show_image_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //return
                    return ;

                }

                //get the projectionObject
                projectionObject showingProjectionObject = projectionObjectsList.get(selectedIndex);

                //get the projecting image id
                int projectingImageID = showingProjectionObject.getProjectingImage().getID();

                //get the image
                ImagePlus showingImage = WindowManager.getImage(projectingImageID);

                //show the image
                showingImage.show();

            }
        
        });

        //remove the listeners from the combo box
        commonSwingMethods.removeJComboBoxListeners(singleMenu_projection_type_choice);
        //add the new listener
        singleMenu_projection_type_choice.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //return
                    return ;

                }

                //get the projectionObject
                projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                //get the projection type
                String projectionType = (String) singleMenu_projection_type_choice.getSelectedItem();

                //set the projection type in the object
                selectedProjectionObject.setProjectionType(projectionType);

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

                //if the trigger from combo box is true
                if (triggerFromSingleComoBox) {

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from projection type choice");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }

                }

            }
        
        });

        //remove the listeners from the auto project checkbox
        commonSwingMethods.removeJCkeckBoxListeners(singleMenu_auto_project_checkbox);
        //add the new listener so that it prints the state
        singleMenu_auto_project_checkbox.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                //get the satate
                boolean state = singleMenu_auto_project_checkbox.isSelected();

                //if it is true
                if (state) {

                    //disable the make projection button
                    singleMenu_make_projection_button.setEnabled(false);

                }

                //if it is false
                else {

                    //enable the make projection button
                    singleMenu_make_projection_button.setEnabled(true);

                }

                //get the selected index
                int selectedIndex = singleMenu_image_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //return
                    return ;

                }

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

                //if the run from auto project checkbox is selected
                if (triggerFromSingleAutoProjectCheckbox) {

                    //if the state is ture
                    if (state) {

                        //log that the projection is being made
                        IJ.log(frameName + " - Running projection from auto project checkbox");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }
                }
            }
        });

        //remove the listeners from the all slices checkbox
        commonSwingMethods.removeJCkeckBoxListeners(singleMenu_all_slices_checkbox);
        //add the new listener so that it prints the state
        singleMenu_all_slices_checkbox.addChangeListener(new ChangeListener() {
            
            public void stateChanged( ChangeEvent e ) {

                //get the state
                boolean state = singleMenu_all_slices_checkbox.isSelected();

                //if the state is true
                if (state) {

                    //disable the top slider
                    singleMenu_top_slider.setEnabled(false);

                    //disable the bottom slider
                    singleMenu_bottom_slider.setEnabled(false);

                    //disable the top spinner
                    singleMenu_top_spinner.setEnabled(false);

                    //disable the bottom spinner
                    singleMenu_bottom_spinner.setEnabled(false);

                    //get the selected index
                    int selectedIndex = singleMenu_image_list.getSelectedIndex();

                    //get the object
                    projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                    //geet the number of slices
                    int numberOfSlices = selectedProjectionObject.getSlices();

                    //set the triggers to false
                    setAllSingleTriggers(false);

                    //set the bot slider
                    singleMenu_bottom_slider.setValue(1);

                    //set the bot spinner
                    singleMenu_bottom_spinner.setValue(1);

                    //set the top slider
                    singleMenu_top_slider.setValue(numberOfSlices);

                    //set the top spinner
                    singleMenu_top_spinner.setValue(numberOfSlices);

                    //get the int[] of slices
                    int[] projectingSlices = new int[numberOfSlices];

                    //loop through the projecting slices
                    for (int i = 0; i < projectingSlices.length; i++) {

                        //set the projecting slice
                        projectingSlices[i] = i + 1;

                    }

                    //set the projecting slices
                    selectedProjectionObject.setProjectingSlices(projectingSlices);

                    //update the jlist
                    updateJList("from the all slices checkbox");

                    //set the selected index
                    singleMenu_image_list.setSelectedIndex(selectedIndex);

                    //set the triggers to true
                    setAllSingleTriggers(true);

                }

                //if the state is false
                else {

                    //enable the top slider
                    singleMenu_top_slider.setEnabled(true);

                    //enable the bottom slider
                    singleMenu_bottom_slider.setEnabled(true);

                    //enable the top spinner
                    singleMenu_top_spinner.setEnabled(true);

                    //enable the bottom spinner
                    singleMenu_bottom_spinner.setEnabled(true);

                }

            }

        });

        //remove the listeners from the bottom slider
        commonSwingMethods.removeJSliderListeners(singleMenu_bottom_slider);
        //add the new listener
        singleMenu_bottom_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged (ChangeEvent e) {

                //if the value is adjusting, return
                if (singleMenu_bottom_slider.getValueIsAdjusting()) {

                    //return
                    return ;

                }

                //if the trigger from slider is true
                if (triggerFromSingleBottomSlider) {

                    //set the other sliders and spinners to flase
                    triggerFromSingleTopSlider = false;
                    triggerFromSingleTopSpinner = false;
                    triggerFromSingleBottomSpinner = false;

                }

                //get the value
                int value = singleMenu_bottom_slider.getValue();

                //get the value from the top slider
                int topValue = singleMenu_top_slider.getValue();

                //if the value is greater than the top value
                if (value > topValue) {

                    //set the top slider to the value
                    singleMenu_top_slider.setValue(value);

                }

                //set the spinner to the value
                singleMenu_bottom_spinner.setValue(value);

                //if the trigger from slider is true
                if (triggerFromSingleBottomSlider) {

                    //set the other sliders and spinners to true
                    triggerFromSingleTopSlider = true;
                    triggerFromSingleTopSpinner = true;
                    triggerFromSingleBottomSpinner = true;

                    //get the top slice
                    int topSlice = singleMenu_top_slider.getValue();

                    //get the bottom slice
                    int bottomSlice = singleMenu_bottom_slider.getValue();

                    //get the int[] of slices
                    int[] projectingSlices = new int[(topSlice - bottomSlice) + 1];

                    //loop through the projecting slices
                    for (int i = 0; i < projectingSlices.length; i++) {

                        //set the projecting slice
                        projectingSlices[i] = bottomSlice + i;

                    }
                    
                    //get the selected index
                    int selectedIndex = singleMenu_image_list.getSelectedIndex();

                    //get the object
                    projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                    //set the projecting slices
                    selectedProjectionObject.setProjectingSlices(projectingSlices);

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from bottom slider");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }

                }

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

            }


        });

        //remove the listeners from the top slider
        commonSwingMethods.removeJSliderListeners(singleMenu_top_slider);
        //add the new listener
        singleMenu_top_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged ( ChangeEvent e ) {

                //if the value is adjusting, return
                if (singleMenu_top_slider.getValueIsAdjusting()) {

                    //return
                    return ;

                }

                //if the trigger from slider is true
                if (triggerFromSingleTopSlider) {

                    //set the other sliders and spinners to flase
                    triggerFromSingleBottomSlider = false;
                    triggerFromSingleTopSpinner = false;
                    triggerFromSingleBottomSpinner = false;

                }

                //get the value
                int value = singleMenu_top_slider.getValue();

                //get the value from the bottom slider
                int bottomValue = singleMenu_bottom_slider.getValue();

                //if the value is less than the bottom value
                if (value < bottomValue) {

                    //set the bottom slider to the value
                    singleMenu_bottom_slider.setValue(value);

                }

                //set the spinner to the value
                singleMenu_top_spinner.setValue(value);

                //if the trigger from slider is true
                if (triggerFromSingleTopSlider) {

                    //set the other sliders and spinners to true
                    triggerFromSingleBottomSlider = true;
                    triggerFromSingleTopSpinner = true;
                    triggerFromSingleBottomSpinner = true;

                    //get the top slice
                    int topSlice = singleMenu_top_slider.getValue();

                    //get the bottom slice
                    int bottomSlice = singleMenu_bottom_slider.getValue();

                    //get the int[] of slices
                    int[] projectingSlices = new int[(topSlice - bottomSlice) + 1];

                    //loop through the projecting slices
                    for (int i = 0; i < projectingSlices.length; i++) {

                        //set the projecting slice
                        projectingSlices[i] = bottomSlice + i;

                    }
                    
                    //get the selected index
                    int selectedIndex = singleMenu_image_list.getSelectedIndex();

                    //get the object
                    projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                    //set the projecting slices
                    selectedProjectionObject.setProjectingSlices(projectingSlices);

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from top slider");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }

                }




            }


        });

        //remove the listeners from the bottom spinner
        commonSwingMethods.removeJSpinnerListeners(singleMenu_bottom_spinner);
        //add the new listener
        singleMenu_bottom_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged ( ChangeEvent e ) {

                //if the trigger from spinner is true
                if (triggerFromSingleBottomSpinner) {

                    //set the other sliders and spinners to flase
                    triggerFromSingleTopSlider = false;
                    triggerFromSingleBottomSlider = false;
                    triggerFromSingleTopSpinner = false;

                }

                //get the value
                int value = (Integer) singleMenu_bottom_spinner.getValue();

                //get the value from the top spinner
                int topValue = (Integer) singleMenu_top_spinner.getValue();

                //if the value is greater than the top value
                if (value > topValue) {

                    //set the top spinnder to the value
                    singleMenu_top_spinner.setValue(value);

                }

                //set the slider to the value
                singleMenu_bottom_slider.setValue(value);

                //if the trigger from the spinner is true
                if (triggerFromSingleBottomSpinner) {

                    //set the other sliders and spinners to true
                    triggerFromSingleTopSlider = true;
                    triggerFromSingleBottomSlider = true;
                    triggerFromSingleTopSpinner = true;

                    //get the top slice
                    int topSlice = singleMenu_top_slider.getValue();

                    //get the bottom slice
                    int bottomSlice = singleMenu_bottom_slider.getValue();

                    //get the int[] of slices
                    int[] projectingSlices = new int[(topSlice - bottomSlice) + 1];

                    //loop through the projecting slices
                    for (int i = 0; i < projectingSlices.length; i++) {

                        //set the projecting slice
                        projectingSlices[i] = bottomSlice + i;

                    }

                    //get the selected index
                    int selectedIndex = singleMenu_image_list.getSelectedIndex();

                    //get the object
                    projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                    //set the projecting slices
                    selectedProjectionObject.setProjectingSlices(projectingSlices);

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from bottom spinner");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }
                
                }

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

            }


        });

        //remove the listeners from the top spinner
        commonSwingMethods.removeJSpinnerListeners(singleMenu_top_spinner);
        //add the new listener
        singleMenu_top_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged (ChangeEvent e) {

                //if the trigger from spinner is true
                if (triggerFromSingleTopSpinner) {

                    //set the other sliders and spinners to flase
                    triggerFromSingleTopSlider = false;
                    triggerFromSingleBottomSlider = false;
                    triggerFromSingleBottomSpinner = false;

                }

                //get the value
                int value = (Integer) singleMenu_top_spinner.getValue();

                //get the value from the bottom spinner
                int bottomValue = (Integer) singleMenu_bottom_spinner.getValue();

                //if the value is less than the bottom value
                if (value < bottomValue) {

                    //set the bottom spinnder to the value
                    singleMenu_bottom_spinner.setValue(value);

                }

                //set the slider to the value
                singleMenu_top_slider.setValue(value);

                //if the trigger from the spinner is true
                if (triggerFromSingleTopSpinner) {

                    //set the others back to true
                    triggerFromSingleTopSlider = true;
                    triggerFromSingleBottomSlider = true;
                    triggerFromSingleBottomSpinner = true;

                    //get the top slice
                    int topSlice = singleMenu_top_slider.getValue();

                    //get the bottom slice
                    int bottomSlice = singleMenu_bottom_slider.getValue();

                    //get the int[] of slices
                    int[] projectingSlices = new int[(topSlice - bottomSlice) + 1];

                    //loop through the projecting slices
                    for (int i = 0; i < projectingSlices.length; i++) {

                        //set the projecting slice
                        projectingSlices[i] = bottomSlice + i;

                    }

                    //get the selected index
                    int selectedIndex = singleMenu_image_list.getSelectedIndex();

                    //get the object
                    projectionObject selectedProjectionObject = projectionObjectsList.get(selectedIndex);

                    //set the projecting slices
                    selectedProjectionObject.setProjectingSlices(projectingSlices);

                    //if the auto project checkbox is selected
                    if (singleMenu_auto_project_checkbox.isSelected()) {

                        //log where you're running the projection from
                        IJ.log(frameName + " - Running projection from top spinner");

                        //run the method to make the projection
                        makeProjection(selectedIndex);

                    }

                }

                //handle the auto
                // handleAutoProject();

                //handle the all slices
                // handleAllSlices();

            } 

        });

    }

    //method to set all single triggers
    public static void setAllSingleTriggers(boolean state) {

        //set the trigger from single jlist
        triggerFromSingleJList = state;

        //set the trigger from single combo box
        triggerFromSingleComoBox = state;

        //set the trigger from single auto project checkbox
        triggerFromSingleAutoProjectCheckbox = state;

        //set the trigger from single top slider
        triggerFromSingleTopSlider = state;

        //set the trigger from single bottom slider
        triggerFromSingleBottomSlider = state;

        //set the trigger from single top spinner
        triggerFromSingleTopSpinner = state;

        //set the trigger from single bottom spinner
        triggerFromSingleBottomSpinner = state;

    }

    //method to deselect for new image
    public static void singleMenuDeselectForNewImage() {

        //if there's a selection on the list, select nothing
        if (singleMenu_image_list.getSelectedIndex() != -1) {

            //sets the selection to nothing
            singleMenu_image_list.setSelectedIndex(-1);

        }

        //clear the channels panel
        singleMenu_channels_panel.removeAll();

        //clear the time point panel
        singleMenu_time_point_panel.removeAll();

        //reset the file text fields
        singleMenu_file_textField.setText("");
        singleMenu_channels_textField.setText("");
        singleMenu_time_point_textField.setText("");
        singleMenu_save_textField.setText("");

        //set the sliders to 0 and 1
        singleMenu_top_slider.setMinimum(0);
        singleMenu_top_slider.setMaximum(1);
        singleMenu_top_slider.setValue(1);
        singleMenu_bottom_slider.setMinimum(0);
        singleMenu_bottom_slider.setMaximum(1);
        singleMenu_bottom_slider.setValue(0);

        //set the spinners to 0 and 1
        singleMenu_top_spinner.setValue(1);
        singleMenu_bottom_spinner.setValue(0);

        //revalidate the frame
        projectionMenu_frame.revalidate();

        //repaint the frame
        projectionMenu_frame.repaint();

    }

    //method to get the projection type save string
    public static String getProjectionTypeSaveString() {

        //get the selection type from the combo box
        String selectionType = (String) singleMenu_projection_type_choice.getSelectedItem();

        //if the selection type is Max Intensity
        if (selectionType.equals("Max Intensity")) {

            selectionType  = "max_int";

        }

        //if the selection type is Sum
        else if (selectionType.equals("Sum")) {

            selectionType = "sum";

        }

        //if the selection type is Min Intensity
        else if (selectionType.equals("Min Intensity")) {

            selectionType = "min_int";

        }

        //if the selection type is Mean
        else if (selectionType.equals("Mean")) {

            selectionType = "avg";

        }

        //if the selection type is Median
        else if (selectionType.equals("Median")) {

            selectionType = "median";

        }

        //if the selection type is Standard Deviation
        else if (selectionType.equals("Standard Deviation")) {

            selectionType = "std_dev";

        }

        //make all lower cases
        selectionType = selectionType.toLowerCase();

        //if there's spaces, replace with underscores
        selectionType = selectionType.replace(" ", "_");

        //add _proj to the selection type
        selectionType = selectionType + "_proj";

        

        //return the selection type
        return selectionType;

    }

    //method to update the JList
    public static void updateJList(String where_from) {

        //log that the JList is being updated and where from
        IJ.log(frameName + " - Updating the JList from : " + where_from);

        //clear the list model
        singleMenu_jlist_model.clear();

        //loop through the projectionObjectsList
        for (projectionObject projectionObject : projectionObjectsList) {

            //get the projectionObject name
            String projectionObjectName = projectionObject.getProjectingName();

            //add the name to the list model
            singleMenu_jlist_model.addElement(projectionObjectName);

        }

        //set the scroll pane to the max right side
        singleMenu_image_list_scrollPane.getHorizontalScrollBar().setValue(singleMenu_image_list_scrollPane.getHorizontalScrollBar().getMaximum());

        //revalidate the frame
        projectionMenu_frame.revalidate();

        //repaint the frame
        projectionMenu_frame.repaint();

    }

    //method to start the batch menu
    public static void startBatchMenu() {

        //reset the batch panel
        resetBatchPanel();

        //loop through the buttons
        for (JButton button : mainButtonsArray) {

            //if the button is not the batch button, then set it to enabled
            if (button != batchButton) {

                //set the button to disabled
                button.setEnabled(true);

            }

        }

    }

    //method to reset the batch panel
    public static void resetBatchPanel() {

        //clear the panel
        projectionPanel.removeAll();

        //make a new layout
        projectionPanel_springLayout = new SpringLayout();

        //set the layout
        projectionPanel.setLayout(projectionPanel_springLayout);

        //revalidate the frame
        projectionMenu_frame.revalidate();

        //repaint the panel
        projectionPanel.repaint();

    }

    //method to make the projection
    public static void makeProjection(int objectIndex) {

        //get the projectionObject
        projectionObject projectingObject = projectionObjectsList.get(objectIndex);

        //if the projecting object has an image projected
        if (projectingObject.getProjectedImage() != null) {

            //the projection exists, closing it 
            IJ.log(frameName + " - Closing the projection");

            //close the image without saving
            projectingObject.getProjectedImage().changes = false;

            //close the image
            projectingObject.getProjectedImage().close();

        }

        //if the slices, time frame or the channels are empty [], then return
        if (projectingObject.getProjectingSlices().length == 0 || projectingObject.getProjectingFrames().length == 0 || projectingObject.getProjectingChannels().length == 0) {

            //log that some of these don't have lenghth
            IJ.log(frameName + " - Some of the slices, time points or channels are empty");

            //return
            return ;

        }

        //get the projection type
        String projectionType = singleMenu_projection_type_choice.getSelectedItem().toString();

        //set the projection type to the correct string
        projectingObject.setProjectionType(projectionType);

        //"Max Intensity", "Sum", "Min Intensity", "Mean", "Median", "Standard Deviation"

        //if the projection type is Max Intensity
        if (projectionType.equals("Max Intensity")) {

            projectionType  = "max_int";

        }
        //if the projection type is Sum
        else if (projectionType.equals("Sum")) {

            projectionType = "sum";

        }
        //if the projection type is Min Intensity
        else if (projectionType.equals("Min Intensity")) {

            projectionType = "min_int";

        }
        //if the projection type is Mean
        else if (projectionType.equals("Mean")) {

            projectionType = "avg";

        }
        //if the projection type is Median
        else if (projectionType.equals("Median")) {

            projectionType = "median";

        }
        //if the projection type is Standard Deviation
        else if (projectionType.equals("Standard Deviation")) {

            projectionType = "std_dev";

        }

        //hide the projecting image
        projectingObject.getProjectingImage().hide();

        //make the projection 
        ImagePlus projected_image = projectionManager.makeProjection(projectingObject.getProjectingImage(), projectingObject.getProjectingSlices(), projectingObject.getProjectingChannels(), projectingObject.getProjectingFrames(), projectionType);

        //loop through the channels of the projected_image
        for (int i = 0; i < projected_image.getNChannels(); i++) {

            //set the channel
            projected_image.setC(i + 1);

            //set the saturation
            IJ.run(projected_image, "Enhance Contrast", "saturated=0.35");

        }

        //set the projected image
        projectingObject.setProjectedImage(projected_image);

        //get the projected image id
        int projected_image_id = projected_image.getID();

        //set the projected image id
        projectingObject.setProjectedImageID(projected_image_id);

        //get the projected path
        String projected_path = singleMenu_save_textField.getText();

        //get the projected name
        String projected_name = projected_path.substring(projected_path.lastIndexOf(File.separator) + 1);

        //set the in the object
        projectingObject.setProjectedName(projected_name);

        //set the projected path
        projectingObject.setProjectedPath(projected_path);

        projectingObject.getProjectedImage().show();

        projectingObject.getProjectingImage().show();



    }

    //method to handle the auto project
    public static void handleAutoProject() {

        //if the auto project checkbox is selected
        if (singleMenu_auto_project_checkbox.isSelected()) {

            //set the make projection button to disabled
            singleMenu_make_projection_button.setEnabled(false);           

        }
        //else, set it to enabled
        else {

            //set the make projection button to enabled
            singleMenu_make_projection_button.setEnabled(true);

        }

    }

    //method to handle the all slices
    public static void handleAllSlices() {

        //if the all slices checkbox is selected
        if (singleMenu_all_slices_checkbox.isSelected()) {

            //set all triggers to disabled
            setAllSingleTriggers(false);

            //set the top slider to max
            singleMenu_top_slider.setValue(singleMenu_top_slider.getMaximum());

            //set the bottom slider to min
            singleMenu_bottom_slider.setValue(singleMenu_bottom_slider.getMinimum());

            //set the top spinner to max
            singleMenu_top_spinner.setValue(singleMenu_top_slider.getMaximum());

            //set the bottom spinner to min
            singleMenu_bottom_spinner.setValue(singleMenu_bottom_slider.getMinimum());

            //set the top slider to disabled
            singleMenu_top_slider.setEnabled(false);

            //set the bottom slider to disabled
            singleMenu_bottom_slider.setEnabled(false);

            //set the top spinner to disabled
            singleMenu_top_spinner.setEnabled(false);

            //set the bottom spinner to disabled
            singleMenu_bottom_spinner.setEnabled(false);

            //set all triggers to true
            setAllSingleTriggers(true);

        }

        //if not
        else {

            //set the sliders to enabled
            singleMenu_top_slider.setEnabled(true);
            singleMenu_bottom_slider.setEnabled(true);

            //set the spinners to enabled
            singleMenu_top_spinner.setEnabled(true);
            singleMenu_bottom_spinner.setEnabled(true);

        }


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

