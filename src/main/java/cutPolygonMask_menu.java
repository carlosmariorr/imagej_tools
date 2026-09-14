import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.JList;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;



public class cutPolygonMask_menu {
    
    //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    

    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //flags to check if the frame has been initialized
    public static boolean cutPolygonMask_Initialized = false;

    //the current class
    static Class<?> currentClass = cutPolygonMask_menu.class;

    //the image id label
    static String imageIDLabel = "Image ID:[ ]";

    //the main image id
    static int single_main_image_id = 0;

    //the background image id
    static int single_background_image_id = 0;

    //the substracted image id
    static int single_substracted_image_id = 0;

    //the parent directory
    static String parentDirectory = "";
    
    //set the single menu sizes
    static int singleMenuWidth = 600;

    //set the single menu height
    static int singleMenuHeight = 370;

    //set the batch menu width
    static int batchMenuWidth = 800;

    //set the batch menu height
    static int batchMenuHeight = 620;

    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//

    //end of the swing elements region
    //#endregion

    //JFrames
    public static JFrame cutPolygonMask_frame = new JFrame("Cut Polygon Mask");

    //Containers
    public static Container cutPolygonMask_frame_contentPane = cutPolygonMask_frame.getContentPane();

    //JPanels
    public static JPanel cutPolygonMaskPanel = new JPanel();

    //SpringLayout
    public static SpringLayout cutPolygonMask_menu_springLayout = new SpringLayout();
    public static SpringLayout cutPolygonMaskPanel_springLayout = new SpringLayout();

    //JButtons
    public static JButton singleButton = new JButton("Single");
    public static JButton batchButton = new JButton("Batch");

    //JButtons ArraLists
    public static ArrayList<JButton> singleBatchButtonsArray = new ArrayList<JButton>();


    //JSeparator
    public static JSeparator singleBatch_separator = new JSeparator(JSeparator.HORIZONTAL);


    //#region ######################################### FOR THE SINGLE SECTION #########################################
    //declare the components, add them and set the constraints
    //JLabels
    public static JLabel single_file_label = new JLabel("File: ");
    public static JLabel single_polygon_label = new JLabel("Polygon File: ");
    public static JLabel single_cut_file_label = new JLabel("Cut File: ");
    public static JLabel single_list_label = new JLabel("List");
    public static JLabel single_z_start_label = new JLabel("Z start");
    public static JLabel single_z_end_label = new JLabel("Z end");
    public static JLabel single_channels_label = new JLabel("Channels: ");
    public static JLabel single_suffix_label = new JLabel("Suffix: ");
    public static JLabel single_cut_from_file_label = new JLabel("Cut from file ");
    public static JLabel single_saving_label = new JLabel("Cut Image: Not Saved");
    public static JLabel single_current_z_label = new JLabel("Current Z: ");
    public static JLabel single_z_bottom_label = new JLabel("Z Bottom: ");
    public static JLabel single_z_top_label = new JLabel("Z Top: ");

    //JTextFields
    public static JTextField single_file_textfield = new JTextField("");
    public static JTextField single_polygon_textfield = new JTextField("");
    public static JTextField single_cut_file_textfield = new JTextField("");
    public static JTextField single_suffix_text_field = new JTextField("cut");

    //JButtons
    public static JButton single_file_select_button = new JButton("Select");
    public static JButton single_file_open_button = new JButton("Open");
    public static JButton single_polygon_select_button = new JButton("Select");
    public static JButton single_cut_file_select_button = new JButton("Select");
    public static JButton single_add_polygon_button = new JButton("Add Polygon");
    public static JButton single_delete_polygon_button = new JButton("Delete Polygon");
    public static JButton single_save_polygon_button = new JButton("Save Polygon");
    public static JButton single_cut_file_button = new JButton("Cut File");
    public static JButton single_save_cut_file_button = new JButton("Save Cut File");
    public static JButton single_reset_menu = new JButton("Reset Menu");

    //JCheckBoxes
    public static ArrayList<JCheckBox> single_channels_checkbox_list = new ArrayList<JCheckBox>();
    public static JCheckBox single_cut_from_file_checkbox = new JCheckBox("");

    //Jlists
    public static JList<String> single_polygon_list = new JList<String>();

    //JListModel
    public static DefaultListModel<String> single_polygon_list_model = new DefaultListModel<String>();

    //JSliders
    public static JSlider single_z_start_slider = new JSlider();
    public static JSlider single_z_end_slider = new JSlider();

    //JSpinners
    public static JSpinner single_z_start_spinner = new JSpinner();
    public static JSpinner single_z_end_spinner = new JSpinner();

    //#endregion #################################

    //the log object
    public static logObject currentLog = new logObject("none", "none","none", false, false);

    //the roi manager
    public static RoiManager roiManager = new RoiManager();

    //the sbs image
    public static ImagePlus currentCuttingImage = null;

    //the sbs image id
    public static int currentCuttingImageID = 0;

    //the image slices 
    public static int currentImageSlices = 0;

    //the image channels
    public static int currentImageChannels = 0;

    //the ArrayList of channel names
    public static ArrayList<String> currentImageChannelsNames = new ArrayList<String>();

    //the array with the stain to slide split
    public static String[] stainToSlideSplit = new String[2];

    //the hasmap for the rois in the z sections
    public static Map<Integer, Roi> roiMap = new HashMap<Integer, Roi>();

    //the current image width
    public static int currentImageWidth = 0;

    //the current image height
    public static int currentImageHeight = 0;

    //the normal roi for this file
    public static Roi normalRoi = null;

    //the normal roi points
    public static Point[] normalRoiPoints = null;

    //boolean to get from the inside
    public static boolean roiFromInside = false;

    //get the cut image
    public static ImagePlus cutImage = null;

    //get the wheel direction 
    public static int wheelDirection = 0;

    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    
    //method to initialize the frame
    public static void initializeFrame() {

        //start a new cutPolygonMask_menu jframe
        cutPolygonMask_frame = new JFrame("Cut sbs Menu");

        //get this contenta pane
        cutPolygonMask_frame_contentPane = cutPolygonMask_frame.getContentPane();

        //add the components to the frame
        addComponentsToFrame();

        //set the components constraints
        setMenuComponentsConstraints();

        //set the components listeners for the first section
        setcutPolygonMaskMenuListeners();

        //set the frame's listener
        setFrameListeners();
        
        //pack the frame
        cutPolygonMask_frame.pack();

        //set the frame visible
        cutPolygonMask_frame.setVisible(true);

        //set the frame flag to initialized
        cutPolygonMask_Initialized = true;

    }
    
    //method to add the components to the frame
    public static void addComponentsToFrame() {

        //set the layout
        cutPolygonMask_frame_contentPane.setLayout(cutPolygonMask_menu_springLayout);

        //set the frame's minimum size
        cutPolygonMask_frame.setMinimumSize(new Dimension(batchMenuWidth, batchMenuHeight));

        //set the size
        cutPolygonMask_frame.setSize(batchMenuWidth, batchMenuHeight);

        //populate the first section buttons array list
        populatesingleBatchButtonsArrayList();

        //Add the buttons from the first section to the frame
        for (JButton button : singleBatchButtonsArray) {

            //add the button to the frame
            cutPolygonMask_frame_contentPane.add(button);

            //set the button to enabled
            button.setEnabled(true);

        }

        //add the vertical separator
        cutPolygonMask_frame_contentPane.add(singleBatch_separator);

        //set the cutPolygonMaskPanel to new
        cutPolygonMaskPanel = new JPanel();

        //add the second section panel to the frame
        cutPolygonMask_frame_contentPane.add(cutPolygonMaskPanel);

        //set the cutPolygonMaskPanel's layout to new
        cutPolygonMaskPanel_springLayout = new SpringLayout();

        //add the second section panel's layout
        cutPolygonMaskPanel.setLayout(cutPolygonMaskPanel_springLayout);

    }

    //populate the JButtons ArrayLists
    public static void populatesingleBatchButtonsArrayList() {

        //add the single and batch buttons to the first section buttons
        singleBatchButtonsArray.add(singleButton);
        singleBatchButtonsArray.add(batchButton);

    }

    //method to set the components constraints
    public static void setMenuComponentsConstraints() {

        //set the constraints for the single button
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.NORTH, singleButton, 5, SpringLayout.NORTH, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.WEST, singleButton, 5, SpringLayout.WEST, cutPolygonMask_frame_contentPane);

        //set the constraints for the batch button
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.NORTH, batchButton, 5, SpringLayout.NORTH, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.WEST, batchButton, 5, SpringLayout.EAST, singleButton);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.SOUTH, batchButton, 0, SpringLayout.SOUTH, singleButton);

        //set the constraints for the separator
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.NORTH, singleBatch_separator, 5, SpringLayout.SOUTH, singleButton);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.WEST, singleBatch_separator, 5, SpringLayout.WEST, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.EAST, singleBatch_separator, -5, SpringLayout.EAST, cutPolygonMask_frame_contentPane);

        //set the constraints for the second section panel
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.NORTH, cutPolygonMaskPanel, 5, SpringLayout.SOUTH, singleBatch_separator);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.WEST, cutPolygonMaskPanel, 5, SpringLayout.WEST, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.SOUTH, cutPolygonMaskPanel, -5, SpringLayout.SOUTH, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.EAST, cutPolygonMaskPanel, -5, SpringLayout.EAST, cutPolygonMask_frame_contentPane);

    }

    //method to set the first section listeners
    public static void setcutPolygonMaskMenuListeners() {

        //set the single button listener
        singleButton.addActionListener(new ActionListener() {

            //method to perform the action
            public void actionPerformed(ActionEvent actionEvent) {

                //run the method to reset the second section panel
                resetcutPolygonMaskPanel();

                //call the method to initialize single
                startSingleMenu();

                //set the button to disabled
                singleButton.setEnabled(false);

            }

        });

        batchButton.addActionListener(new ActionListener() {

            //method to perform the action
            public void actionPerformed(ActionEvent actionEvent) {

                //run the method to reset the second section panel
                resetcutPolygonMaskPanel();

                //call the method to initialize batch
                startBatchMenu();

                //set the button to disabled
                batchButton.setEnabled(false);

            }

        });



    }

    //method to initialize the single menu
    public static void startSingleMenu() {

        //reset the single menu
        resetSingleMenu();

        //revalidate the frame
        cutPolygonMask_frame.revalidate();

        //repaint the frame
        cutPolygonMask_frame.repaint();

        //add the components
        addSingleMenuComponents();

        //set the components listeners
        // setSingleMenuComponentsListeners();

        //pack the frame
        cutPolygonMask_frame.pack();

        //update the draw
        cutPolygonMask_frame.repaint();

    }

    //method to add the components to the single menu
    public static void addSingleMenuComponents() {
        
        //JLabels
        cutPolygonMaskPanel.add(single_file_label);
        cutPolygonMaskPanel.add(single_suffix_label);
        cutPolygonMaskPanel.add(single_polygon_label);
        cutPolygonMaskPanel.add(single_cut_file_label);
        cutPolygonMaskPanel.add(single_list_label);
        cutPolygonMaskPanel.add(single_z_start_label);
        cutPolygonMaskPanel.add(single_z_end_label);
        cutPolygonMaskPanel.add(single_channels_label);
        cutPolygonMaskPanel.add(single_cut_from_file_label);
        cutPolygonMaskPanel.add(single_saving_label);
        cutPolygonMaskPanel.add(single_current_z_label);
        cutPolygonMaskPanel.add(single_z_bottom_label);
        cutPolygonMaskPanel.add(single_z_top_label);


        //JTextFields
        cutPolygonMaskPanel.add(single_file_textfield);
        cutPolygonMaskPanel.add(single_suffix_text_field);
        cutPolygonMaskPanel.add(single_polygon_textfield);
        cutPolygonMaskPanel.add(single_cut_file_textfield);

        //JButtons
        cutPolygonMaskPanel.add(single_file_select_button);
        cutPolygonMaskPanel.add(single_file_open_button);
        cutPolygonMaskPanel.add(single_polygon_select_button);
        cutPolygonMaskPanel.add(single_cut_file_select_button);
        cutPolygonMaskPanel.add(single_add_polygon_button);
        cutPolygonMaskPanel.add(single_delete_polygon_button);
        cutPolygonMaskPanel.add(single_save_polygon_button);
        cutPolygonMaskPanel.add(single_cut_file_button);
        cutPolygonMaskPanel.add(single_save_cut_file_button);
        cutPolygonMaskPanel.add(single_reset_menu);
    
        //JCheckBoxes
        cutPolygonMaskPanel.add(single_cut_from_file_checkbox);

        //JLists
        cutPolygonMaskPanel.add(single_polygon_list);

        //JSliders
        cutPolygonMaskPanel.add(single_z_start_slider);
        single_z_start_slider.setMajorTickSpacing(1);
        single_z_start_slider.setPaintTicks(true);
        single_z_start_slider.setSnapToTicks(true);

        cutPolygonMaskPanel.add(single_z_end_slider);
        single_z_end_slider.setMajorTickSpacing(1);
        single_z_end_slider.setPaintTicks(true);
        single_z_end_slider.setSnapToTicks(true);

        //JSpinners
        cutPolygonMaskPanel.add(single_z_start_spinner);
        single_z_start_spinner.setModel(new SpinnerNumberModel(1, 1, 2, 1));
        cutPolygonMaskPanel.add(single_z_end_spinner);
        single_z_end_spinner.setModel(new SpinnerNumberModel(2, 1, 2, 1));

        //setting the constraints
        
        //for the file open button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_open_button, 5, SpringLayout.NORTH, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_open_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        
        //for the file label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_label, 5, SpringLayout.NORTH, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_file_label, 5, SpringLayout.WEST, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_file_label, 0, SpringLayout.SOUTH, single_file_open_button);

        //for the select button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_select_button, 5, SpringLayout.NORTH, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_select_button, -5, SpringLayout.WEST, single_file_open_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_file_select_button, 0, SpringLayout.SOUTH, single_file_open_button);

        //for the text field
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_textfield, 5, SpringLayout.NORTH, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_file_textfield, 5, SpringLayout.EAST, single_file_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_textfield, -5, SpringLayout.WEST, single_file_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_file_textfield, 0, SpringLayout.SOUTH, single_file_open_button);

        //for the suffix label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_suffix_label, 5, SpringLayout.SOUTH, single_file_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_suffix_label, 5, SpringLayout.WEST, cutPolygonMaskPanel);

        //for the suffix text field
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_suffix_text_field, 5, SpringLayout.SOUTH, single_file_textfield);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_suffix_text_field, 5, SpringLayout.EAST, single_suffix_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_suffix_text_field, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the suffix label south
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_suffix_label, 0, SpringLayout.SOUTH, single_suffix_text_field);

        //for the polygon label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_polygon_label, 5, SpringLayout.SOUTH, single_suffix_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_polygon_label, 5, SpringLayout.WEST, cutPolygonMaskPanel);

        //for the polygon select button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_polygon_select_button, 5, SpringLayout.SOUTH, single_suffix_text_field);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_polygon_select_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        
        //for the south of the polygon label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_polygon_label, 0, SpringLayout.SOUTH, single_polygon_select_button);

        //for the polygon text field
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_polygon_textfield, 5, SpringLayout.SOUTH, single_suffix_text_field);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_polygon_textfield, 5, SpringLayout.EAST, single_polygon_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_polygon_textfield, -5, SpringLayout.WEST, single_polygon_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_polygon_textfield, 0, SpringLayout.SOUTH, single_polygon_select_button);

        //for the cut file select button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_file_select_button, 5, SpringLayout.SOUTH, single_polygon_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_cut_file_select_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the cut file label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_file_label, 5, SpringLayout.SOUTH, single_polygon_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_file_label, 5, SpringLayout.WEST, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_cut_file_label, 0, SpringLayout.SOUTH, single_cut_file_select_button);

        //for the cut file text field
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_file_textfield, 5, SpringLayout.SOUTH, single_polygon_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_file_textfield, 5, SpringLayout.EAST, single_cut_file_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_cut_file_textfield, -5, SpringLayout.WEST, single_cut_file_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_cut_file_textfield, 0, SpringLayout.SOUTH, single_cut_file_select_button);

        //for the list label 
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_list_label, 5, SpringLayout.SOUTH, single_cut_file_select_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_list_label, 5, SpringLayout.WEST, cutPolygonMaskPanel);

        //for the list
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_polygon_list, 5, SpringLayout.SOUTH, single_list_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_polygon_list, 5, SpringLayout.WEST, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_polygon_list, -5, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_polygon_list, -5, SpringLayout.SOUTH, cutPolygonMaskPanel);

        //for the add polygon button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_add_polygon_button, 0, SpringLayout.NORTH, single_polygon_list);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_add_polygon_button, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_add_polygon_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the delete polygon button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_delete_polygon_button, 5, SpringLayout.SOUTH, single_add_polygon_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_delete_polygon_button, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_delete_polygon_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the start label slider
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_start_label, 5, SpringLayout.SOUTH, single_delete_polygon_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_z_start_label, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);

        //for the JSpinner
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_start_spinner, 0, SpringLayout.NORTH, single_z_start_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_start_spinner, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the single slider
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_start_slider, 0, SpringLayout.NORTH, single_z_start_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_z_start_slider, 5, SpringLayout.EAST, single_z_start_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_start_slider, -5, SpringLayout.WEST, single_z_start_spinner);

        //for the end label slider
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_end_label, 8, SpringLayout.SOUTH, single_z_start_slider);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_z_end_label, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);

        //for the end spinner
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_end_spinner, 0, SpringLayout.NORTH, single_z_end_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_end_spinner, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the single slider
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_end_slider, 0, SpringLayout.NORTH, single_z_end_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_z_end_slider, 0, SpringLayout.WEST, single_z_start_slider);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_end_slider, 5, SpringLayout.WEST, single_z_end_spinner);
        
        //for the channels label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_channels_label, 8, SpringLayout.SOUTH, single_z_end_slider);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_channels_label, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);

        //for the save polygon button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_save_polygon_button, 5, SpringLayout.SOUTH, single_channels_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_save_polygon_button, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_save_polygon_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the cut from file label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_from_file_label, 5, SpringLayout.SOUTH, single_save_polygon_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_from_file_label, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);

        //for the cut from file checkbox
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_from_file_checkbox, 5, SpringLayout.SOUTH, single_save_polygon_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_from_file_checkbox, 5, SpringLayout.EAST, single_cut_from_file_label);

        //for the cut file button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_file_button, 5, SpringLayout.SOUTH, single_cut_from_file_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_file_button, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_cut_file_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the save cut file button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_save_cut_file_button, 5, SpringLayout.SOUTH, single_cut_file_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_save_cut_file_button, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_save_cut_file_button, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the reset menu button
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_reset_menu, 5, SpringLayout.SOUTH, single_save_cut_file_button);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, single_reset_menu, 2, SpringLayout.HORIZONTAL_CENTER, cutPolygonMaskPanel);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_reset_menu, -5, SpringLayout.EAST, cutPolygonMaskPanel);

        //for the saving label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_saving_label, 5, SpringLayout.SOUTH, single_reset_menu);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_saving_label, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        single_saving_label.setFont(new Font("Arial", Font.PLAIN, 14));

        //for the current z
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_current_z_label, 5, SpringLayout.SOUTH, single_saving_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_current_z_label, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        single_current_z_label.setFont(new Font("Arial", Font.PLAIN, 14));

        //for the bottom label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_bottom_label, 5, SpringLayout.SOUTH, single_current_z_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_bottom_label, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        single_z_bottom_label.setFont(new Font("Arial", Font.PLAIN, 14));

        //for the top label
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, single_z_top_label, 5, SpringLayout.SOUTH, single_z_bottom_label);
        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.EAST, single_z_top_label, -5, SpringLayout.EAST, cutPolygonMaskPanel);
        single_z_top_label.setFont(new Font("Arial", Font.PLAIN, 14));

        //set the initial states
        
        //for the add polygon button
        single_add_polygon_button.setEnabled(false);

        //for the delete polygon button
        single_delete_polygon_button.setEnabled(false);

        //for the slider start
        single_z_start_slider.setMinimum(1);
        single_z_start_slider.setMaximum(2);
        single_z_start_slider.setValue(1);
        single_z_start_slider.setEnabled(false);

        //for the spinner start
        single_z_start_spinner.setEnabled(false);

        //for the slider end
        single_z_end_slider.setMinimum(1);
        single_z_end_slider.setMaximum(2);
        single_z_end_slider.setValue(2);
        single_z_end_slider.setEnabled(false);

        //for the spinner end
        single_z_end_spinner.setEnabled(false);

        //for the save polygon button
        single_save_polygon_button.setEnabled(false);

        //for the cut from file checkbox
        single_cut_from_file_checkbox.setEnabled(false);

        //for the cut file button
        single_cut_file_button.setEnabled(false);

        //for the save cut file button
        single_save_cut_file_button.setEnabled(false);

        //set the panel constraints
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.NORTH, cutPolygonMaskPanel, 5, SpringLayout.SOUTH, singleBatch_separator);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.WEST, cutPolygonMaskPanel, 5, SpringLayout.WEST, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.SOUTH, cutPolygonMaskPanel, -5, SpringLayout.SOUTH, cutPolygonMask_frame_contentPane);
        cutPolygonMask_menu_springLayout.putConstraint(SpringLayout.EAST, cutPolygonMaskPanel, -5, SpringLayout.EAST, cutPolygonMask_frame_contentPane);

        //set action listener for select file button
        //remove the button listeners
        removeListenersFromButton(single_file_select_button);
        //add the new listener
        single_file_select_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the suffix
                String suffix = single_suffix_text_field.getText();
                
                String selectedFilePath = mainMenu.getFileFromChooser("image");

                //get the path without extension
                String selectedFilePathNameWithoutExtension = selectedFilePath.substring(0, selectedFilePath.lastIndexOf("."));

                //get the rois zip file path
                String polygonFilePath = selectedFilePathNameWithoutExtension + "_polygon.zip";
                
                //set the text field to the selected file path
                single_file_textfield.setText(selectedFilePath);

                //set the save text field to the rois zip file path
                single_polygon_textfield.setText(polygonFilePath);

                //get the cut file path
                String cutFilePath = selectedFilePathNameWithoutExtension + "_" + suffix + ".tif";

                //set the cut file text field
                single_cut_file_textfield.setText(cutFilePath);
                
            }
        });

        //for the open button
        //remove the button listeners
        removeListenersFromButton(single_file_open_button);
        //add the new listener
        single_file_open_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the file path
                String filePath = single_file_textfield.getText();

                //verify that the file exists
                File file = new File(filePath);

                //if the file does not exist
                if (!file.exists()) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "The file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    //return
                    return ;

                }

                //open the file
                currentCuttingImage = IJ.openImage(filePath);

                //show the image
                currentCuttingImage.show();

                //add a window listener to the image
                //add the action listener to the substraction image
                currentCuttingImage.getWindow().addWindowListener(new WindowAdapter() {
                    
                    public void windowClosing(WindowEvent e) {
                        
                        //reset the single menu

                        resetcutPolygonMaskPanel();

                        startSingleMenu();

                    }
                });

                //get the image id
                currentCuttingImageID = currentCuttingImage.getID();

                //get the image slices
                currentImageSlices = currentCuttingImage.getNSlices();

                //get the image widht and height
                currentImageWidth = currentCuttingImage.getWidth();

                //get the image height
                currentImageHeight = currentCuttingImage.getHeight();

                //get the normal roi as a square roi with the current widht and height
                normalRoi = new Roi(0, 0, currentImageWidth, currentImageHeight);

                //get the roi points
                normalRoiPoints = normalRoi.getContainedPoints();

                //populate the roiMap by looping through the number of slices
                for (int i = 0; i < currentImageSlices; i++) {

                    //get a new roi that is a copy of the normalRoi
                    Roi normalRoiCopy = new Roi(0,0,currentImageWidth,currentImageHeight);

                    //add the roi points to the roiMap
                    roiMap.put(i + 1, normalRoiCopy);

                }

                //set the slider values based on the current image slices
                single_z_start_slider.setMinimum(1);
                single_z_start_slider.setMaximum(currentImageSlices);
                single_z_start_slider.setValue(1);
                //set the slider ticks
                single_z_start_slider.setMajorTickSpacing(1);
                single_z_start_slider.setPaintTicks(true);

                //set the start spinner to enabled
                single_z_start_spinner.setEnabled(true);
                //set the spinner model
                single_z_start_spinner.setModel(new SpinnerNumberModel(1, 1, currentImageSlices, 1));

                single_z_end_slider.setMinimum(1);
                single_z_end_slider.setMaximum(currentImageSlices);
                single_z_end_slider.setValue(currentImageSlices);
                //set the slider ticks
                single_z_end_slider.setMajorTickSpacing(1);
                single_z_end_slider.setPaintTicks(true);

                //set the end spinner to enabled
                single_z_end_spinner.setEnabled(true);
                //set the spinner model
                single_z_end_spinner.setModel(new SpinnerNumberModel(currentImageSlices, 1, currentImageSlices, 1));

                //get the image channels
                currentImageChannels = currentCuttingImage.getNChannels();

                //make an array list of JCheckboxes
                single_channels_checkbox_list = new ArrayList<JCheckBox>();
                
                //get the file name without the parent directory
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);

                //get the contains _stain bool
                boolean contains_stain = fileName.contains("_stain");

                //get the contains _slide bool
                boolean contains_slide = fileName.contains("_slide");

                //if both bools are true, log it
                if (contains_stain && contains_slide) {

                    //initiate the channel names array
                    currentImageChannelsNames = new ArrayList<String>();

                    //get from _stain to slide
                    String stainToSlide = fileName.substring(fileName.indexOf("_stain") + 7, fileName.indexOf("_slide"));

                    //separate them by _
                    stainToSlideSplit = stainToSlide.split("_");

                }

                //get a JCheckbox for each channel into the list
                for (int i = 0; i < currentImageChannels; i++) {
                    
                    //get the checkbox name
                    String checkBoxName = "Channel " + (i + 1);

                    //if both bools are true, set the name to the stain name
                    if (contains_stain && contains_slide) {

                        //get the stain name
                        String stainName = stainToSlideSplit[i];

                        //set the checkbox name to the stain name
                        checkBoxName = stainName;

                        //add the stain name to the list
                        currentImageChannelsNames.add(stainName);

                    }

                    //make a new JCheckBox
                    JCheckBox checkBox = new JCheckBox(checkBoxName);

                    //add the checkbox to the list
                    single_channels_checkbox_list.add(checkBox);

                    //add the checkbox to the panel
                    cutPolygonMaskPanel.add(checkBox);

                    //set the checkbox to enabled
                    checkBox.setEnabled(true);

                    //set the checkbox to selected
                    checkBox.setSelected(true);

                }

                //set the constraints for the checkboxes
                for (int i = 0; i < single_channels_checkbox_list.size(); i++) {

                    //get the checkbox
                    JCheckBox checkBox = single_channels_checkbox_list.get(i);
                    
                    //if it is the first checkbox
                    if (i == 0) {

                        //set the constraints
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, checkBox, 0, SpringLayout.NORTH, single_channels_label);
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, checkBox, 5, SpringLayout.EAST, single_channels_label);
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, checkBox, 0, SpringLayout.SOUTH, single_channels_label);

                    } else {

                        //get the previous checkbox
                        JCheckBox previousCheckBox = single_channels_checkbox_list.get(i - 1);

                        //set the constraints
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.NORTH, checkBox, 0, SpringLayout.NORTH, previousCheckBox);
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.WEST, checkBox, 5, SpringLayout.EAST, previousCheckBox);
                        cutPolygonMaskPanel_springLayout.putConstraint(SpringLayout.SOUTH, checkBox, 0, SpringLayout.SOUTH, previousCheckBox);

                    }

                }

                //set the add polygon to enabled
                single_add_polygon_button.setEnabled(true);

                //set the delete polygon to enabled
                single_delete_polygon_button.setEnabled(false);

                //set the slider start to enabled
                single_z_start_slider.setEnabled(true);

                //set the slider end to enabled
                single_z_end_slider.setEnabled(true);

                //set the save polygon button to enabled
                single_save_polygon_button.setEnabled(false);

                //set the cut from file checkbox to enabled
                single_cut_from_file_checkbox.setEnabled(true);

                //set the cut file button to enabled
                single_cut_file_button.setEnabled(true);

                //set the save cut file button to enabled
                single_save_cut_file_button.setEnabled(true);

                //revalidate the panel
                cutPolygonMaskPanel.revalidate();

                //repaint the panel
                cutPolygonMaskPanel.repaint();

                //show the image
                currentCuttingImage.show();

                //set the image listeners
                setImageListeners();
                
            }

        });

        //for the select polygon button
        //remove the button listeners
        removeListenersFromButton(single_polygon_select_button);
        //add the new listener
        single_polygon_select_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //look for the polygon file
                String selectedFilePath = mainMenu.getFileFromChooser("polygon");

                //set the polygon textfield to the selected file path
                single_polygon_textfield.setText(selectedFilePath);

            }

        });

        //for the select cut file button
        //remove the button listeners
        removeListenersFromButton(single_cut_file_select_button);
        //add the new listener
        single_cut_file_select_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //look for the cut file
                String selectedFilePath = mainMenu.getFileFromChooser("image");

                //set the cut file textfield to the selected file path
                single_cut_file_textfield.setText(selectedFilePath);

            }

        });

        //for the sliders
        //remove the slider listeners
        removeListenersFromSlider(single_z_start_slider);
        //for the start slider
        single_z_start_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the slider value
                int sliderValue = single_z_start_slider.getValue();

                //get the end slider value
                int endSliderValue = single_z_end_slider.getValue();

                //if the slider value is greater than the end slider value
                if (sliderValue > endSliderValue) {

                    //set the end slider value to the slider value
                    single_z_end_slider.setValue(sliderValue);

                }

                //set the correspoinding spinner value
                single_z_start_spinner.setValue(sliderValue);

                //set the bottom z label to the slider value
                single_z_bottom_label.setText("Bottom Z: " + sliderValue);

            }

        });

        //for the end slider
        //remove the slider listeners
        removeListenersFromSlider(single_z_end_slider);
        //for the end slider
        single_z_end_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the slider value
                int sliderValue = single_z_end_slider.getValue();

                //get the start slider value
                int startSliderValue = single_z_start_slider.getValue();

                //if the slider value is greater than the end slider value
                if (sliderValue < startSliderValue) {

                    //set the end slider value to the slider value
                    single_z_start_slider.setValue(sliderValue);

                }

                //set the correspoinding spinner value
                single_z_end_spinner.setValue(sliderValue);

                //set the top z label to the slider value
                single_z_top_label.setText("Top Z: " + sliderValue);

            }

        });

        //for the start spinner
        //remove the spinner listeners
        removeListenersFromSpinner(single_z_start_spinner);
        //for the start spinner
        single_z_start_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the spinner value
                int spinnerValue = (int) single_z_start_spinner.getValue();

                //get the end spinner value
                int endSpinnerValue = (int) single_z_end_spinner.getValue();

                //if the spinner value is greater than the end spinner value
                if (spinnerValue > endSpinnerValue) {

                    //set the end spinner value to the spinner value
                    single_z_end_spinner.setValue(spinnerValue);

                }

                //set the correspoinding slider value
                single_z_start_slider.setValue(spinnerValue);

            }

        });

        //for the end spinner
        //remove the spinner listeners
        removeListenersFromSpinner(single_z_end_spinner);
        //for the end spinner
        single_z_end_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the spinner value
                int spinnerValue = (int) single_z_end_spinner.getValue();

                //get the start spinner value
                int startSpinnerValue = (int) single_z_start_spinner.getValue();

                //if the spinner value is greater than the end spinner value
                if (spinnerValue < startSpinnerValue) {

                    //set the end spinner value to the spinner value
                    single_z_start_spinner.setValue(spinnerValue);

                }

                //set the correspoinding slider value
                single_z_end_slider.setValue(spinnerValue);

            }

        });

        //for the add polygon button
        //remove the button listeners
        removeListenersFromButton(single_add_polygon_button);
        //add the new listener
        single_add_polygon_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                //set the save polygon button to enabled
                single_save_polygon_button.setEnabled(true);

                //get the roi from the image
                Roi roi = currentCuttingImage.getRoi();

                //if the roi is null
                if (roi == null) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "No roi selected", "Error", JOptionPane.ERROR_MESSAGE);

                    //return
                    return ;

                }

                //get the current zstart
                int zstart = single_z_start_slider.getValue();

                //get the current zend
                int zend = single_z_end_slider.getValue();

                //get the channel 
                int imageChanneling = currentCuttingImage.getC();

                //loop from the zstart to the zend
                for (int a = zstart; a <= zend; a++) {
                    
                    //set the roi hyperstack position
                    roi.setPosition(imageChanneling, a, 1);

                    //set the roi to the map
                    roiMap.put(a, roi);

                }

                //update the roi manager instance
                update_rois_in_manager();

                //update the JList
                update_JList();

                //set the slider to the zend + 1
                single_z_start_slider.setValue(zend + 1);

                //set the slider to the zend + 2
                single_z_end_slider.setValue(zend + 2);

                //set the slice to the zend + 1
                currentCuttingImage.setZ(zend + 1);

                //select none
                currentCuttingImage.resetRoi();

            }

        });

        //the delete polygon button
        //remove the button listeners
        removeListenersFromButton(single_delete_polygon_button);
        //add the new listener
        single_delete_polygon_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the selected index from the list
                int selectedIndex = single_polygon_list.getSelectedIndex();

                //if the selected index is -1
                if (selectedIndex == -1) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "No list entry selected", "Error", JOptionPane.ERROR_MESSAGE);

                    //return
                    return ;

                }

                //get the string of the selected index
                String selectedString = single_polygon_list.getSelectedValue();

                //split the string by the -
                String[] selectedStringSplit = selectedString.split("-");

                //loop through the string[] and replace the z with nothing
                for (int i = 0; i < selectedStringSplit.length; i++) {
                    
                    //get the string
                    String string = selectedStringSplit[i];

                    //replace the z with nothing
                    string = string.replace("z", "");

                    //set the string to the string[]
                    selectedStringSplit[i] = string;

                }

                //get the zstart
                int zstart = Integer.parseInt(selectedStringSplit[0]);

                //get the zend
                int zend = Integer.parseInt(selectedStringSplit[1]);

                //loop from the zstart to the zend
                for (int a = zstart; a <= zend; a++) {
                    
                    //get the deletedRoi copy from normal roi
                    Roi deletedRoi = new Roi(0,0,currentImageWidth,currentImageHeight);
                    
                    //set the roi to the map
                    roiMap.put(a, deletedRoi);

                }

                //update the roi manager instance
                update_rois_in_manager();

                //update the JList
                update_JList();

                //set the slider to the zstart
                single_z_start_slider.setValue(zstart);

                //set the end slider to zstar +1
                single_z_end_slider.setValue(zstart + 1);

                //set the z to the zstart
                currentCuttingImage.setZ(zstart);

            }

        });

        //for the save polygon
        //remove the button listeners
        removeListenersFromButton(single_save_polygon_button);
        //add the new listener
        single_save_polygon_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the polygon file path
                String polygonFilePath = single_polygon_textfield.getText();

                //if the file exists, ask for overwrite
                File polygonFile = new File(polygonFilePath);

                //if the file exists
                if (polygonFile.exists()) {

                    //ask for overwrite
                    int overwrite = JOptionPane.showConfirmDialog(null, "The file already exists. Overwrite?", "Overwrite", JOptionPane.YES_NO_OPTION);

                    //if the user does not want to overwrite
                    if (overwrite == JOptionPane.NO_OPTION) {

                        //return
                        return ;

                    }

                }

                //get the roi manager instance
                roiManager = RoiManager.getInstance();

                //save the roi manager
                roiManager.runCommand("Save", polygonFilePath);

            }

        });

        //for the cut file button
        //remove the button listeners
        removeListenersFromButton(single_cut_file_button);
        //add the new listener
        single_cut_file_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //set the button to disabled
                single_cut_file_button.setEnabled(false);

                //select nothing on the image
                currentCuttingImage.resetRoi();

                //duplicate the image
                cutImage = currentCuttingImage.duplicate();

                //make it 32-bit
                IJ.run(cutImage, "32-bit", "");

                //set the z to 1
                cutImage.setZ(1);

                //loop through the number of slices
                for (int slices_here = 0; slices_here < currentImageSlices; slices_here++) {
                    
                    //set the slice
                    cutImage.setZ(slices_here + 1);

                    //get the corresponding roi
                    Roi cuttingRoi = roiMap.get(slices_here + 1);

                    //get the cutting roi points
                    Point[] cuttingRoiPoints = cuttingRoi.getContainedPoints();

                    //find out if they're the same as the normal roi points
                    boolean equalsNormal = Arrays.equals(cuttingRoiPoints, normalRoiPoints);

                    //if they are not equal, set the roi
                    if (equalsNormal == false) {

                        //set the roi
                        cutImage.setRoi(cuttingRoi);

                        //make inverse
                        IJ.run(cutImage, "Make Inverse", "");

                        //loop through the channels
                        for (int channels_here = 0; channels_here < currentImageChannels; channels_here++) {
                            
                            //set the channel
                            cutImage.setC(channels_here + 1);

                            //set the value to NaN
                            IJ.run(cutImage, "Set...", "value=0");

                        }

                    }

                    //select nothing
                    cutImage.resetRoi();

                }

                //set it to channel 1
                cutImage.setC(1);

                //set it to slice 1
                cutImage.setZ(1);

                //show the image
                cutImage.show();

                //add the window closing listener to the cutImage
                cutImage.getWindow().addWindowListener(new WindowAdapter() {
                    
                    public void windowClosing(WindowEvent e) {
                        
                        //set the cut button to enabled
                        single_cut_file_button.setEnabled(true);
                        

                    }
                });

            }
        
        });

        //for the save cut file button
        //remove the button listeners
        removeListenersFromButton(single_save_cut_file_button);
        //add the new listener
        single_save_cut_file_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the cut file path
                String cutFilePath = single_cut_file_textfield.getText();

                //if the file exists, ask for overwrite
                File cutFile = new File(cutFilePath);

                //if the file exists
                if (cutFile.exists()) {

                    //ask for overwrite
                    int overwrite = JOptionPane.showConfirmDialog(null, "The file already exists. Overwrite?", "Overwrite", JOptionPane.YES_NO_OPTION);

                    //if the user does not want to overwrite
                    if (overwrite == JOptionPane.NO_OPTION) {

                        //return
                        return ;

                    }

                }

                //save the cut file
                IJ.saveAs(cutImage, "Tiff", cutFilePath);

                //set the save label to saved
                single_saving_label.setText("Saved");

            }

        });


        //for single_reset_menu
        //remove the button listeners
        removeListenersFromButton(single_reset_menu);
        //add the new listener
        single_reset_menu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //close the cutImage if it exists
                if (cutImage != null) {

                    //close the cutImage
                    cutImage.close();

                }

                //close the currentCuttingImage if it exists
                if (currentCuttingImage != null) {

                    //close the currentCuttingImage
                    currentCuttingImage.close();

                }
                
                //run the method to reset the second section panel
                resetcutPolygonMaskPanel();

                //call the method to initialize single
                startSingleMenu();

            }

        });

    }

    //method to remove listeners from button
    public static void removeListenersFromButton(JButton button) {

        //get the listeners from the button
        ActionListener[] button_listeners = button.getActionListeners();

        //remove the listeners
        for (ActionListener buttonListener : button_listeners) {
            button.removeActionListener(buttonListener);
        }

    }

    //method to remove the listeners from the sliders
    public static void removeListenersFromSlider(JSlider slider) {

        //get the listeners from the slider
        ChangeListener[] slider_listeners = slider.getChangeListeners();

        //remove the listeners
        for (ChangeListener sliderListener : slider_listeners) {
            slider.removeChangeListener(sliderListener);
        }

    }

    //method to remove the listeners from the spinners
    public static void removeListenersFromSpinner(JSpinner spinner) {

        //get the listeners from the spinner
        ChangeListener[] spinner_listeners = spinner.getChangeListeners();

        //remove the listeners
        for (ChangeListener spinnerListener : spinner_listeners) {
            spinner.removeChangeListener(spinnerListener);
        }

    }

    //method to update the rois in the manager
    public static void update_rois_in_manager() {

        //get the roiManager reference
        roiManager = RoiManager.getInstance();

        //reset the roi manager
        roiManager.reset();

        //loop through the number of z sections
        for (int i = 1; i <= currentImageSlices; i++) {
            
            //get the roi
            Roi roi = roiMap.get(i);

            //if the roi is null
            if (roi == null) {

                //show an error message
                JOptionPane.showMessageDialog(null, "No roi selected for z section " + i, "Error", JOptionPane.ERROR_MESSAGE);

                //return
                return ;

            }

            //add the roi to the roi manager
            roiManager.addRoi(roi);

            //select the roi
            roiManager.select(i - 1);

            //change the name to the corresponding z section
            roiManager.rename(i - 1, "z" + i);

            //update the roi manager
            roiManager.runCommand("Update");

        }

        //select none
        currentCuttingImage.resetRoi();

        //get the current slice
        int this_slice = currentCuttingImage.getZ();

        //get the correpsonding roi
        Roi this_roi = roiMap.get(this_slice);

        //get the roi points
        Point[] this_roi_points = this_roi.getContainedPoints();

        //if the roi poitns are different from the normal roi points
        if (!Arrays.equals(this_roi_points, normalRoiPoints)) {

            //set the roi to the normal roi
            currentCuttingImage.setRoi(this_roi);

        }

    }

    //method to update the JList
    public static void update_JList() {

        //clear the list model
        single_polygon_list_model.clear();

        //set the start bool
        boolean zstar_found = false;

        //set the zstart to 0
        int zstart = 0;

        //set the zend found to 0
        boolean zend_found = false;

        //set the zend to 0
        int zend = 0;

        //get a znextstart variable
        int znextstart = 0;

        //get a znextstart found variable
        boolean znextstart_found = false;

        //get an arraylist of strings
        ArrayList<String> string_list = new ArrayList<String>();

        //loop through the number of slices and log it
        for (int zloop = 1; zloop <= currentImageSlices; zloop++) {
            
            //set the znextstart found to false
            znextstart_found = false;

            //set the znextstart to 0
            znextstart = 0;

            //get the roi
            Roi roi = roiMap.get(zloop);

            //get the roi points
            Point[] roi_points = roi.getContainedPoints();

            //get an equals normal flag
            boolean equals_normal = Arrays.equals(roi_points, normalRoiPoints);

            //if the roi points are different from the normal roi points
            if (equals_normal == false) {

                //if the zloop is one
                if (zloop == 1) {

                    //set the zstart to 1
                    zstart = 1;

                    //set the zstart found to true
                    zstar_found = true;

                }

                //if the zloop is not one
                if (zloop > 1) {

                    //get the previous roi
                    Roi previous_roi = roiMap.get(zloop - 1);

                    //get the previous roi points
                    Point[] previous_roi_points = previous_roi.getContainedPoints();

                    //find out if they're the same
                    boolean previous_equals_points = Arrays.equals(previous_roi_points, roi_points);

                    //if they're not the same
                    if (previous_equals_points == false) {

                        //if the start found is true
                        if (zstar_found == true) {

                            //set the zend to the zloop
                            zend = zloop-1;

                            //set the zend found to true
                            zend_found = true;

                            //set the znextstart found to true
                            znextstart_found = true;

                            //set the znextstart to the zloop
                            znextstart = zloop;

                        }

                        //if the start found is false
                        if (zstar_found == false) {

                            //set the zstart to the zloop
                            zstart = zloop;

                            //set the zstart found to true
                            zstar_found = true;

                        }


                    }

                    //if they're the same
                    if (previous_equals_points == true) {

                        //if the zloop is the last slice
                        if (zloop == currentImageSlices) {

                            //set the zend to the zloop
                            zend = zloop;

                            //set the zend found to true
                            zend_found = true;

                        }


                    }

                }

            }

            //if the roi points are the same as the normal roi points
            if (equals_normal == true) {

                //if the zstart found is true
                if (zstar_found == true) {

                    //if the zloop is more than 1
                    if (zloop > 1) {

                        //get the previous roi
                        Roi previous_roi = roiMap.get(zloop - 1);

                        //get the previous roi points
                        Point[] previous_roi_points = previous_roi.getContainedPoints();

                        //find out if they're the same
                        boolean previous_equals_points = Arrays.equals(previous_roi_points, roi_points);

                        //if they're not the same
                        if (previous_equals_points == false) {                      

                            //set the zstart to the zloop
                            zend = zloop - 1;

                            //set the zstart found to true
                            zend_found = true;

                        }

                    }

                }

                //if the zstart found is false, then don't do anything

            }

            //if the zend found is true
            if (zend_found == true) {

                //get the adding string
                String adding_string = "z" + zstart + "-z" + zend;

                //add the string to the string list
                string_list.add(adding_string);

                //set the zstart found to false
                zstar_found = false;

                //set the zend fount to false
                zend_found = false;

                //set the zstart to 0
                zstart = 0;

                //set the zend to 0
                zend = 0;

                //if the znextstart found is true
                if (znextstart_found == true) {

                    //set the zstart to the znextstart
                    zstart = znextstart;

                    //set the zend found to false
                    zstar_found = true;

                }

            }

        }

        //loop through the string list
        for (String string : string_list) {

            //add the string to the list model
            single_polygon_list_model.addElement(string);

        }

        //set the list model to the list
        single_polygon_list.setModel(single_polygon_list_model);

        //repaint the list
        single_polygon_list.repaint();

        //if the string list is more than 0
        if (string_list.size() > 0) {

            //set the delete polygon button to enabled
            single_delete_polygon_button.setEnabled(true);

        }

        //if the string list is less than 1
        if (string_list.size() < 1) {

            //set the delete polygon button to disabled
            single_delete_polygon_button.setEnabled(false);

        }
        
    }

    //method to set the image listeners
    public static void setImageListeners() {

        //get the image canvas
        ImageCanvas imageCanvas = currentCuttingImage.getCanvas();

        //if you scroll over the image with the mouse, the image will scroll
        imageCanvas.addMouseWheelListener(new MouseWheelListener() {
            
            public void mouseWheelMoved(MouseWheelEvent e) {

                //get the current roi
                Roi currentWheelRoi = currentCuttingImage.getRoi();

                //get the wheel rotation
                int wheelRotation = e.getWheelRotation();                

                //get the current slice
                int currentSlice = currentCuttingImage.getZ();

                //get the new slice
                int newSlice = currentSlice + wheelRotation;

                //if the new slice is less than 1
                if (newSlice < 1) {

                    //set the new slice to 1
                    newSlice = 1;

                }

                //if the new slice is greater than the number of slices
                if (newSlice > currentImageSlices) {

                    //set the new slice to the number of slices
                    newSlice = currentImageSlices;

                }

                //set the new slice
                currentCuttingImage.setZ(newSlice);

                //if the sign of the wheel rotation and wheel direction are different
                if (Math.signum(wheelRotation) != Math.signum(wheelDirection)) {

                    //if the wheel rotation is positive
                    if (wheelRotation > 0) {

                        //set the start slider to the current slice
                        single_z_start_slider.setValue(newSlice);

                    }

                    //if the wheel rotation is negative
                    if (wheelRotation < 0) {

                        //set the end slider to the current slice
                        single_z_end_slider.setValue(newSlice);

                    }

                }

                //set the wheel direction to the wheel rotation
                wheelDirection = wheelRotation;

                //if the new slice is the bottom
                if (newSlice == 1) {

                    //set the start slider to the current slice
                    single_z_start_slider.setValue(newSlice);

                }

                //if the new slice is the top
                if (newSlice == currentImageSlices) {

                    //set the end slider to the current slice
                    single_z_end_slider.setValue(newSlice);

                }

                //set the current slice label to
                single_current_z_label.setText("Current Z: " + newSlice);

                //get an instance of the roi manager
                roiManager = RoiManager.getInstance();

                //get the roi count
                int roiCount = roiManager.getCount();

                //if the roi count is more than 0
                if (roiCount > 0) {
    
                    //get the current roi
                    Roi currentCorrespondingRoi = roiManager.getRoi(currentSlice-1);

                    //get the points for this current roi
                    Point[] currentCorrespondingRoiPoints = currentCorrespondingRoi.getContainedPoints();

                    //if the points are different from the normal points
                    if (!Arrays.equals(currentCorrespondingRoiPoints, normalRoiPoints)) {

                        //set the bool to true
                        roiFromInside = true;

                    }

                    //if the points are the same as the normal points
                    if (Arrays.equals(currentCorrespondingRoiPoints, normalRoiPoints)) {

                        //set the bool to false
                        roiFromInside = false;

                    }

                    //get the corresponding roi from the roi manager
                    Roi displayRoi = roiManager.getRoi(newSlice - 1);

                    //get the roi contained points
                    Point[] displayRoiPoints = displayRoi.getContainedPoints();

                    //if the points are different from the normal points
                    if (!Arrays.equals(displayRoiPoints, normalRoiPoints)) {

                        //set the roi to the display roi
                        currentCuttingImage.setRoi(displayRoi);

                    }

                    //if the points are the same as the normal points
                    if (Arrays.equals(displayRoiPoints, normalRoiPoints)) {

                        //if you're coming form  the inside
                        if (roiFromInside == true) {

                            //reset the roi
                            currentCuttingImage.resetRoi();

                        }

                        //if you're not coming from the inside
                        if (roiFromInside == false) {

                            //set the roi to the normal roi
                            currentCuttingImage.setRoi(currentWheelRoi);

                        }

                    }

                }

                //if the roi count is less than 1
                if (roiCount < 1) {

                    //if the current wheel roi is not null
                    if (currentWheelRoi != null) {

                        //set the roi to the current wheel roi
                        currentCuttingImage.setRoi(currentWheelRoi);

                    }

                }

            }

        });

    }

    //method to reset the second section panel
    public static void resetcutPolygonMaskPanel() {

        //remove all the components from the panel
        cutPolygonMaskPanel.removeAll();

        //set the second section panel's layout to null
        cutPolygonMaskPanel.setLayout(null);

        //restart the panel's spring layout
        cutPolygonMaskPanel_springLayout = new SpringLayout();

        //add the new spring layout to the panel
        cutPolygonMaskPanel.setLayout(cutPolygonMaskPanel_springLayout);

        //set the single and batch buttons to enabled
        for (JButton button : singleBatchButtonsArray) {

            //add the button to the frame
            button.setEnabled(true);

        }

        //repaint the panel
        cutPolygonMaskPanel.repaint();

    }

    //method to reset the single menu
    public static void resetSingleMenu() {

        //#region ######################################### FOR THE SINGLE SECTION #########################################
        //declare the components, add them and set the constraints
        //JLabels
        single_file_label = new JLabel("File: ");
        single_polygon_label = new JLabel("Polygon File: ");
        single_cut_file_label = new JLabel("Cut File: ");
        single_list_label = new JLabel("List");
        single_z_start_label = new JLabel("Z start");
        single_z_end_label = new JLabel("Z end");
        single_channels_label = new JLabel("Channels: ");
        single_suffix_label = new JLabel("Suffix: ");
        single_cut_from_file_label = new JLabel("Cut from file ");
        single_saving_label = new JLabel("Cut Image: Not Saved");
        single_current_z_label = new JLabel("Current Z: ");
        single_z_bottom_label = new JLabel("Z Bottom: ");
        single_z_top_label = new JLabel("Z Top: ");

        //JTextFields
        single_file_textfield = new JTextField("");
        single_polygon_textfield = new JTextField("");
        single_cut_file_textfield = new JTextField("");
        single_suffix_text_field = new JTextField("cut");

        //JButtons
        single_file_select_button = new JButton("Select");
        single_file_open_button = new JButton("Open");
        single_polygon_select_button = new JButton("Select");
        single_cut_file_select_button = new JButton("Select");
        single_add_polygon_button = new JButton("Add Polygon");
        single_delete_polygon_button = new JButton("Delete Polygon");
        single_save_polygon_button = new JButton("Save Polygon");
        single_cut_file_button = new JButton("Cut File");
        single_save_cut_file_button = new JButton("Save Cut File");
        single_reset_menu = new JButton("Reset Menu");

        //JCheckBoxes
        single_channels_checkbox_list = new ArrayList<JCheckBox>();
        single_cut_from_file_checkbox = new JCheckBox("");

        //Jlists
        single_polygon_list = new JList<String>();

        //Jlist model
        single_polygon_list_model = new DefaultListModel<String>();

        //JSliders
        single_z_start_slider = new JSlider();
        single_z_end_slider = new JSlider();

        //#endregion #################################

        //the log object
        currentLog = new logObject("none", "none","none", false, false);

        //the roi manager
        roiManager = new RoiManager();

        //the sbs image
        currentCuttingImage = null;

        //the sbs image id
        currentCuttingImageID = 0;

        //the image slices 
        currentImageSlices = 0;

        //the image channels
        currentImageChannels = 0;

        //the ArrayList of channel names
        currentImageChannelsNames = new ArrayList<String>();

        //the array with the stain to slide split
        stainToSlideSplit = new String[2];

        //the roimap
        roiMap = new HashMap<Integer, Roi>();

        //the current image width
        currentImageWidth = 0;

        //the current image height
        currentImageHeight = 0;

        //the normal roi
        normalRoi = null;

        //the points
        normalRoiPoints = null;

        //the roi from inside
        roiFromInside = false;

        //reset the cut image
        cutImage = null;

        //get the roi manager instance and reset it
        roiManager = RoiManager.getInstance();

        //reset the roi manager
        roiManager.reset();

        //set the wheel direction
        wheelDirection = 0;
    
    }

    //start the batch menu
    public static void startBatchMenu() {

        //set the 


    }

    //method to reset the batch components
    public static void Batch_resetComponents() {

        return;

    }

    //method to set the frame's listeners
    public static void setFrameListeners() {

        //get the frame's listeners into a list
        WindowListener[] frameListeners = cutPolygonMask_frame.getWindowListeners();

        //loop through the list and remove them
        for (WindowListener windowListener : frameListeners) {

            //remove the listener
            cutPolygonMask_frame.removeWindowListener(windowListener);

        }

        //add the new listener
        cutPolygonMask_frame.addWindowListener(new WindowAdapter() {

            //method to close the frame
            public void windowClosing(WindowEvent windowEvent) {

                //close the frame
                closeMenu();

            }

        });


    }
        
    //method to close the menu
    public static void closeMenu() {

        //initialize the frame
        initializeFrame();

        //close the frame
        cutPolygonMask_frame.dispose();

        //run the method in the mainMenu to reset the button
        mainMenu.resetOpenedMenuButton(currentClass);

        //set the boolean to false
        cutPolygonMask_Initialized = false;

    }

    //method to retrieve if the frame is initialized
    public static boolean isInitialized() {

        //return the boolean
        return cutPolygonMask_Initialized;

    }

    //#endregion end of methods region

}
