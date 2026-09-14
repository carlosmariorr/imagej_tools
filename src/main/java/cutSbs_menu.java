import java.awt.Container;
import java.awt.Dimension;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageWindow;
import ij.gui.PointRoi;
import ij.gui.Roi;
import ij.plugin.Duplicator;
import ij.plugin.frame.RoiManager;



public class cutSbs_menu {
    
    //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    

    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //flags to check if the frame has been initialized
    public static boolean cutSbs_Initialized = false;

    //the current class
    static Class<?> currentClass = cutSbs_menu.class;

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
    static int batchMenuWidth = 600;

    //set the batch menu height
    static int batchMenuHeight = 400;

    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//

    //end of the swing elements region
    //#endregion

    //JFrames
    public static JFrame cutSbs_frame = new JFrame("Cut sbs Menu");

    //Containers
    public static Container cutSbs_frame_contentPane = cutSbs_frame.getContentPane();

    //JPanels
    public static JPanel cutSbsPanel = new JPanel();

    //SpringLayout
    public static SpringLayout cutSbs_menu_springLayout = new SpringLayout();
    public static SpringLayout cutSbsPanel_springLayout = new SpringLayout();

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
    public static JLabel single_square_size_label = new JLabel("Square Size: ");
    public static JLabel single_save_label = new JLabel("Roi save file: ");
    public static JLabel single_suffix_label = new JLabel("Suffix: ");
    public static JLabel single_start_status_label = new JLabel("Status: ");
    public static JLabel single_advance_sbs_step_label = new JLabel(" - Cut Sbs step - ");
    public static JLabel single_all_z_sections_label = new JLabel("All Z Sections: ");
    public static JLabel confirm_rectangle_label = new JLabel("Confirm Rectangle: ");
    public static JLabel single_botton_z_label = new JLabel("Bottom Z: ");
    public static JLabel single_top_z_label = new JLabel("Top Z: ");

    //JTextFields
    public static JTextField single_file_textfield = new JTextField("");
    public static JTextField single_square_size_textfield = new JTextField("100");
    public static JTextField single_save_textfield = new JTextField("");
    public static JTextField single_suffix_text_field = new JTextField("sbs");

    //JButtons
    public static JButton single_file_select_button = new JButton("Select");
    public static JButton single_save_select_button = new JButton("Select");
    public static JButton single_start_button = new JButton("Start Cutting");
    public static JButton single_cut_sbs_button = new JButton("Cut sbs");
    public static JButton single_advance_sbs_step_button = new JButton(" Advance Step ");
    public static JButton single_file_open_button = new JButton("Open");

    //JCheckBoxes
    public static JCheckBox single_all_z_sections_checkbox = new JCheckBox();
    public static JCheckBox single_confirm_rectangle_checkbox = new JCheckBox();

    //JSliders 
    public static JSlider single_bottom_z_slider = new JSlider();
    public static JSlider single_top_z_slider = new JSlider();

    //JSpinner
    public static JSpinner single_bottom_z_spinner = new JSpinner();
    public static JSpinner single_top_z_spinner = new JSpinner();

    //#endregion #################################

    //the log object
    public static logObject currentLog = new logObject("none", "none","none", false, false);

    //the roi manager
    public static RoiManager roiManager = new RoiManager();

    //the cutting image
    public static ImagePlus cuttingImage = null;

    //the image id
    public static int cuttingImageID = 0;

    //set the coordsRoi
    public static Roi coordsRoi = null;

    public static Point[] currentPointArray = new Point[1];

    //a bool for the selected z flag
    public static boolean selectedZFlag = false;

    //make a state for the current sbs
    public static int currentSbs_state = -1;

    //make a current sbs counter
    public static int currentSbsCounter = 0;

    //make a tuple with the advance step buttons text
    public static String[] advanceStepButtonText = {" Confirm Rectangle ", " Confirm lower Z ", " Confirm upper Z "};

    //make the cuttingLowerz and cuttingUpperz ints
    public static int cuttingLowerz = 0;
    public static int cuttingUpperz = 0;

    //make an int for the number of slices
    public static int number_of_slices = 0;

    //get the current file path
    public static String currentFilePath = "";

    //get the hasmap
    public static Map<Integer, Map<int[],Roi>> pointMap = new HashMap<Integer, Map<int[],Roi>>();

    //int for the previous rotation
    public static int previousRotation = 0;

    //get the rotation slices
    public static int rotationSlices = 0;

    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    
    //method to initialize the frame
    public static void initializeFrame() {

        //start a new cutSbs_menu jframe
        cutSbs_frame = new JFrame("Cut sbs Menu");

        //get this contenta pane
        cutSbs_frame_contentPane = cutSbs_frame.getContentPane();

        //add the components to the frame
        addComponentsToFrame();

        //set the components constraints
        setMenuComponentsConstraints();

        //set the components listeners for the first section
        setCutSbsMenuListeners();

        //set the frame's listener
        setFrameListeners();
        
        //pack the frame
        cutSbs_frame.pack();

        //set the frame visible
        cutSbs_frame.setVisible(true);

        //set the frame flag to initialized
        cutSbs_Initialized = true;

    }
    
    //method to add the components to the frame
    public static void addComponentsToFrame() {

        //set the layout
        cutSbs_frame_contentPane.setLayout(cutSbs_menu_springLayout);

        //set the frame's minimum size
        cutSbs_frame.setMinimumSize(new Dimension(batchMenuWidth, batchMenuHeight));

        //set the size
        cutSbs_frame.setSize(batchMenuWidth, batchMenuHeight);

        //populate the first section buttons array list
        populatesingleBatchButtonsArrayList();

        //Add the buttons from the first section to the frame
        for (JButton button : singleBatchButtonsArray) {

            //add the button to the frame
            cutSbs_frame_contentPane.add(button);

            //set the button to enabled
            button.setEnabled(true);

        }

        //add the vertical separator
        cutSbs_frame_contentPane.add(singleBatch_separator);

        //set the cutSbsPanel to new
        cutSbsPanel = new JPanel();

        //add the second section panel to the frame
        cutSbs_frame_contentPane.add(cutSbsPanel);

        //set the cutSbsPanel's layout to new
        cutSbsPanel_springLayout = new SpringLayout();

        //add the second section panel's layout
        cutSbsPanel.setLayout(cutSbsPanel_springLayout);

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
        cutSbs_menu_springLayout.putConstraint(SpringLayout.NORTH, singleButton, 5, SpringLayout.NORTH, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.WEST, singleButton, 5, SpringLayout.WEST, cutSbs_frame_contentPane);

        //set the constraints for the batch button
        cutSbs_menu_springLayout.putConstraint(SpringLayout.NORTH, batchButton, 5, SpringLayout.NORTH, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.WEST, batchButton, 5, SpringLayout.EAST, singleButton);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.SOUTH, batchButton, 0, SpringLayout.SOUTH, singleButton);

        //set the constraints for the separator
        cutSbs_menu_springLayout.putConstraint(SpringLayout.NORTH, singleBatch_separator, 5, SpringLayout.SOUTH, singleButton);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.WEST, singleBatch_separator, 5, SpringLayout.WEST, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.EAST, singleBatch_separator, -5, SpringLayout.EAST, cutSbs_frame_contentPane);

        //set the constraints for the second section panel
        cutSbs_menu_springLayout.putConstraint(SpringLayout.NORTH, cutSbsPanel, 5, SpringLayout.SOUTH, singleBatch_separator);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.WEST, cutSbsPanel, 5, SpringLayout.WEST, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.SOUTH, cutSbsPanel, -5, SpringLayout.SOUTH, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.EAST, cutSbsPanel, -5, SpringLayout.EAST, cutSbs_frame_contentPane);

    }

    //method to set the first section listeners
    public static void setCutSbsMenuListeners() {

        //set the single button listener
        singleButton.addActionListener(new ActionListener() {

            //method to perform the action
            public void actionPerformed(ActionEvent actionEvent) {

                //run the method to reset the second section panel
                resetCutSbsPanel();

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
                resetCutSbsPanel();

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
        cutSbs_frame.revalidate();

        //repaint the frame
        cutSbs_frame.repaint();

        //add the components
        addSingleMenuComponents();

        //set the components listeners
        // setSingleMenuComponentsListeners();

        //pack the frame
        cutSbs_frame.pack();

        //update the draw
        cutSbs_frame.repaint();

    }

    //method to add the components to the single menu
    public static void addSingleMenuComponents() {
        
        //JLabels
        cutSbsPanel.add(single_file_label);
        cutSbsPanel.add(single_square_size_label);
        cutSbsPanel.add(single_save_label);
        cutSbsPanel.add(single_suffix_label);
        cutSbsPanel.add(single_start_status_label);
        cutSbsPanel.add(single_advance_sbs_step_label);
        cutSbsPanel.add(single_all_z_sections_label);
        cutSbsPanel.add(single_botton_z_label);
        cutSbsPanel.add(single_top_z_label);

        //JTextFields
        cutSbsPanel.add(single_file_textfield);
        cutSbsPanel.add(single_square_size_textfield);  
        cutSbsPanel.add(single_save_textfield);
        cutSbsPanel.add(single_suffix_text_field);

        //JButtons
        cutSbsPanel.add(single_file_select_button);
        cutSbsPanel.add(single_save_select_button);
        cutSbsPanel.add(single_start_button);
        cutSbsPanel.add(single_cut_sbs_button);
        cutSbsPanel.add(single_advance_sbs_step_button);
        cutSbsPanel.add(single_file_open_button);

        //JCheckBoxes
        cutSbsPanel.add(single_all_z_sections_checkbox);

        //JSliders
        cutSbsPanel.add(single_bottom_z_slider);
        single_bottom_z_slider.setMajorTickSpacing(1);
        single_bottom_z_slider.setSnapToTicks(true);
        single_bottom_z_slider.setPaintTicks(true);
        cutSbsPanel.add(single_top_z_slider);
        single_top_z_slider.setMajorTickSpacing(1);
        single_top_z_slider.setSnapToTicks(true);
        single_top_z_slider.setPaintTicks(true);

        //JSpinners
        cutSbsPanel.add(single_bottom_z_spinner);
        cutSbsPanel.add(single_top_z_spinner);

        //setting the constraints
        //for the file label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_label, 5, SpringLayout.NORTH, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_file_label, 5, SpringLayout.WEST, cutSbsPanel);
        
        //for the open button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_open_button, 5, SpringLayout.NORTH, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_open_button, -5, SpringLayout.EAST, cutSbsPanel);

        //for the file select button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_select_button, 5, SpringLayout.NORTH, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_select_button, -5, SpringLayout.WEST,single_file_open_button);

        //for the single file text field
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_file_textfield, 5, SpringLayout.NORTH, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_file_textfield, 5, SpringLayout.EAST, single_file_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_file_textfield, -5, SpringLayout.WEST, single_file_select_button);

        //for the square size label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_square_size_label, 5, SpringLayout.SOUTH, single_file_textfield);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_square_size_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the square size text field
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_square_size_textfield, 5, SpringLayout.SOUTH, single_file_textfield);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_square_size_textfield, 5, SpringLayout.EAST, single_square_size_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_square_size_textfield, -5, SpringLayout.EAST, cutSbsPanel);

        //for the all z sections label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_all_z_sections_label, 5, SpringLayout.SOUTH, single_square_size_textfield);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_all_z_sections_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the all z sections checkbox
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_all_z_sections_checkbox, 5, SpringLayout.SOUTH, single_square_size_textfield);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_all_z_sections_checkbox, 5, SpringLayout.EAST, single_all_z_sections_label);

        //for the bottom z label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_botton_z_label, 5, SpringLayout.SOUTH, single_all_z_sections_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_botton_z_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the bottom z spiner
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_bottom_z_spinner, 5, SpringLayout.SOUTH, single_all_z_sections_checkbox);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_bottom_z_spinner, -5, SpringLayout.EAST, cutSbsPanel);

        //for the bottom z slider
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_bottom_z_slider, 5, SpringLayout.SOUTH, single_all_z_sections_checkbox);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_bottom_z_slider, 5, SpringLayout.EAST, single_botton_z_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_bottom_z_slider, -5, SpringLayout.WEST, single_bottom_z_spinner);

        //for the top z label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_top_z_label, 5, SpringLayout.SOUTH, single_bottom_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_top_z_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the top z spiner
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_top_z_spinner, 5, SpringLayout.SOUTH, single_bottom_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_top_z_spinner, -5, SpringLayout.EAST, cutSbsPanel);

        //for the top z slider
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_top_z_slider, 5, SpringLayout.SOUTH, single_bottom_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_top_z_slider, 0, SpringLayout.WEST, single_bottom_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_top_z_slider, -5, SpringLayout.WEST, single_top_z_spinner);

        //for the save label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_save_label, 5, SpringLayout.SOUTH, single_top_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_save_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the save select button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_save_select_button, 5, SpringLayout.SOUTH, single_top_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_save_select_button, -5, SpringLayout.EAST, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_save_select_button, 0, SpringLayout.SOUTH, single_save_label);

        //for the save text field
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_save_textfield, 5, SpringLayout.SOUTH, single_top_z_slider);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_save_textfield, 5, SpringLayout.EAST, single_save_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_save_textfield, -5, SpringLayout.WEST, single_save_select_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_save_textfield, 0, SpringLayout.SOUTH, single_save_label);

        //for the suffix label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_suffix_label, 5, SpringLayout.SOUTH, single_save_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_suffix_label, 5, SpringLayout.WEST, cutSbsPanel);

        //for the suffix text field
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_suffix_text_field, 5, SpringLayout.SOUTH, single_save_textfield);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_suffix_text_field, 5, SpringLayout.EAST, single_suffix_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_suffix_text_field, -5, SpringLayout.EAST, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_suffix_text_field, 0, SpringLayout.SOUTH, single_suffix_label);
        
        //for the start button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_start_button, 5, SpringLayout.SOUTH, single_suffix_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_start_button, -5, SpringLayout.EAST, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_start_button, 0, SpringLayout.WEST, single_file_select_button);

        //for the start status label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_start_status_label, 5, SpringLayout.SOUTH, single_suffix_label);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_start_status_label, -5, SpringLayout.WEST, single_start_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_start_status_label, 0, SpringLayout.SOUTH, single_start_button);

        //for the cut sbs button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_cut_sbs_button, 5, SpringLayout.SOUTH, single_start_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_cut_sbs_button, -5, SpringLayout.EAST, cutSbsPanel);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.WEST, single_cut_sbs_button, 0, SpringLayout.WEST, single_advance_sbs_step_button);

        //for the select z button
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_advance_sbs_step_button, 5, SpringLayout.SOUTH, single_cut_sbs_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_advance_sbs_step_button, -5, SpringLayout.EAST, cutSbsPanel);
        
        //for the current z label
        cutSbsPanel_springLayout.putConstraint(SpringLayout.NORTH, single_advance_sbs_step_label, 5, SpringLayout.SOUTH, single_cut_sbs_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.EAST, single_advance_sbs_step_label, -5, SpringLayout.WEST, single_advance_sbs_step_button);
        cutSbsPanel_springLayout.putConstraint(SpringLayout.SOUTH, single_advance_sbs_step_label, 0, SpringLayout.SOUTH, single_advance_sbs_step_button);
        
        //set the initial states
        
        //for the start button
        single_start_button.setEnabled(false);

        //for the cut sbs button
        single_cut_sbs_button.setEnabled(false);

        //for the select z button
        single_advance_sbs_step_button.setEnabled(false);

        //add the panel to the frame
        cutSbs_frame_contentPane.add(cutSbsPanel);

        //set the panel constraints
        cutSbs_menu_springLayout.putConstraint(SpringLayout.NORTH, cutSbsPanel, 5, SpringLayout.SOUTH, singleBatch_separator);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.WEST, cutSbsPanel, 5, SpringLayout.WEST, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.SOUTH, cutSbsPanel, -5, SpringLayout.SOUTH, cutSbs_frame_contentPane);
        cutSbs_menu_springLayout.putConstraint(SpringLayout.EAST, cutSbsPanel, -5, SpringLayout.EAST, cutSbs_frame_contentPane);

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
                String roisZipFilePath = selectedFilePathNameWithoutExtension + "_" + suffix + "_rois.zip";
                
                //set the text field to the selected file path
                single_file_textfield.setText(selectedFilePath);

                //set the save text field to the rois zip file path
                single_save_textfield.setText(roisZipFilePath);

                

                //set the start button to enabled
                single_start_button.setEnabled(true);
                
            }
        });

        //for the file open button
        //remove the button listeners
        removeListenersFromButton(single_file_open_button);
        //add the new listener
        single_file_open_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                //get the file path
                currentFilePath = single_file_textfield.getText();

                //verify that the file exists
                File file = new File(currentFilePath);

                //if the file does not exist
                if (!file.exists()) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "The file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    //return
                    return ;

                }

                //set the textfield as disabled
                single_file_textfield.setEditable(false);

                //open the file using imagej
                cuttingImage = IJ.openImage(currentFilePath);

                //get the image id
                cuttingImageID = cuttingImage.getID();

                //show the image
                cuttingImage.show();

                //get the rotation slices
                rotationSlices = cuttingImage.getNSlices();

                //add the closing window listener
                cuttingImage.getWindow().addWindowListener(new WindowAdapter() {

                    //method to perform the action
                    public void windowClosing(WindowEvent windowEvent) {

                        //run the method to reset the second section panel
                        resetCutSbsPanel();

                        //call the method to initialize single
                        startSingleMenu();

                        //set the button to disabled
                        singleButton.setEnabled(false);

                        //get the roi manager instance
                        RoiManager roiManager = RoiManager.getInstance();

                        //reset the roi manager
                        roiManager.reset();

                    }

                });

                //get the number of slices
                number_of_slices = cuttingImage.getNSlices();
                
                //set the slider and spinner limits
                single_bottom_z_slider.setMinimum(1);
                single_bottom_z_slider.setMaximum(number_of_slices);
                single_bottom_z_spinner.setModel(new SpinnerNumberModel(1, 1, number_of_slices, 1));
                single_top_z_slider.setMinimum(1);
                single_top_z_slider.setMaximum(number_of_slices);
                single_top_z_spinner.setModel(new SpinnerNumberModel(number_of_slices, 1, number_of_slices, 1));

                //set the tool to point rois
                IJ.setTool("multipoint");

                //select the image id
                IJ.selectWindow(cuttingImageID);   
                
                //change the status label to opened
                single_start_status_label.setText("Status: Selecting nuclei with multipoint tool");

                //set the open button and select buttons to disabled
                single_file_open_button.setEnabled(false);
                single_file_select_button.setEnabled(false);

            }
        });

        //set the action listener for the select save button
        //remove the button listeners
        removeListenersFromButton(single_save_select_button);
        //add the new listener
        single_save_select_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                String selectedFilePath = mainMenu.getFileFromChooser("image");

                //get the path without extension
                String selectedFilePathNameWithoutExtension = selectedFilePath.substring(0, selectedFilePath.lastIndexOf("."));

                //get the rois zip file path
                String roisZipFilePath = selectedFilePathNameWithoutExtension + "_rois.zip";
                
                //set the text field to the selected file path
                single_save_textfield.setText(roisZipFilePath);

            }

        });

        //set the action listener for the start button
        //remove the button listeners
        removeListenersFromButton(single_start_button);
        //add the new listener
        single_start_button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                //if the image is null
                if (cuttingImage == null) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "The image is null", "Error", JOptionPane.ERROR_MESSAGE);

                    //return
                    return;

                }

                //get the current roi
                Roi currentRoi = cuttingImage.getRoi();

                //if the roi is null, print the error
                if (currentRoi == null) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "Select the nuclei before cutting", "Error", JOptionPane.ERROR_MESSAGE);

                    //return
                    return;

                }

                //get the image window
                ImageWindow imageWindow = cuttingImage.getWindow();

                //add the mouse scroll wheel listener
                imageWindow.addMouseWheelListener(new MouseWheelListener() {

                    //method to perform the action
                    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

                        //get the current slice
                        int currentWindowSlice = cuttingImage.getZ();

                        //get the wheel rotation
                        int wheelRotation = mouseWheelEvent.getWheelRotation();

                        //if the sign of the wheel rotation and wheel direction are different
                        if (Math.signum(wheelRotation) != Math.signum(previousRotation)) {

                            //if the wheel rotation is positive
                            if (wheelRotation > 0) {

                                //setting slice
                                int settingSlice = currentWindowSlice-1;

                                //if the setting slice is less than 1
                                if (settingSlice < 1) {

                                    //set the setting slice to 1
                                    settingSlice = 1;

                                }

                                //set the start slider to the current slice
                                single_bottom_z_slider.setValue(settingSlice);

                            }

                            //if the wheel rotation is negative
                            if (wheelRotation < 0) {

                                //setting slice
                                int settingSlice = currentWindowSlice+1;

                                //if the setting slice is greater than the number of slices
                                if (settingSlice > rotationSlices) {

                                    //set the setting slice to the number of slices
                                    settingSlice = rotationSlices;

                                }

                                //set the end slider to the current slice
                                single_top_z_slider.setValue(settingSlice);

                            }

                        }

                        //set the wheel direction to the wheel rotation
                        previousRotation = wheelRotation;

                    }

                });

                //get the save path from the text field
                String savePath = single_save_textfield.getText();

                //get the file name without extension
                String fileName_not_ext = currentFilePath.substring(currentFilePath.lastIndexOf(File.separator) + 1, currentFilePath.lastIndexOf("."));
                
                //get the script name
                String scriptName = "cutSbs";

                //get the year, month, day, hour, minute and second
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; //january is 0
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);

                //get the log name
                String logName = fileName_not_ext + "_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second + "_" + scriptName + ".cutsbslog";

                //get the parent of the save path
                String saveDir = savePath.substring(0, savePath.lastIndexOf(File.separator)+1);

                //create the log object
                currentLog = new logObject(saveDir, logName, scriptName, true, true);

                //start the log
                currentLog.startLog();

                //initialize the square size
                int squareSize = 0;

                //try to parse the square size
                try {

                    //get the square size
                    squareSize = Integer.parseInt(single_square_size_textfield.getText());

                } catch (Exception exception) {

                    //show an error message
                    JOptionPane.showMessageDialog(null, "The square size is not a number", "Error", JOptionPane.ERROR_MESSAGE);

                    //return
                    return;

                }

                //set the square size text field as disabled
                single_square_size_textfield.setEditable(false);
                
                //find out if the file exists
                File saveFile = new File(savePath);

                //if the file exists, ask for confirmation to overwrite
                if (saveFile.exists()) {

                    //show a confirmation dialog
                    int confirmation = JOptionPane.showConfirmDialog(null, "The file already exists. Do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION);

                    //if the user did not confirm
                    if (confirmation == JOptionPane.NO_OPTION) {

                        //return
                        return;

                    }

                }

                //set the save text field as disabled
                single_save_textfield.setEditable(false);

                //get the suffix
                String suffix = single_suffix_text_field.getText();

                //set the suffix text field as disabled
                single_suffix_text_field.setEditable(false);

                //get the sbs base name
                String sbsBaseName = savePath.substring(0, savePath.lastIndexOf(".")) + "_" + suffix;

                //set the finish button to enabled
                single_cut_sbs_button.setEnabled(true);

                //set the start button to disabled
                single_start_button.setEnabled(false);

                //run the method to start the cut sbs
                startCutSbs(currentFilePath, squareSize, savePath, suffix, sbsBaseName, single_advance_sbs_step_button, single_advance_sbs_step_label);

            }
        });

        //set the listener for the cut sbs button
        //remove the button listeners
        removeListenersFromButton(single_cut_sbs_button);
        //add the new listener
        single_cut_sbs_button.addActionListener(new ActionListener() {
            
            public void actionPerformed (ActionEvent e) {

                //cut the sbs
                cutCurrentSbs();

            }

        });

        //remove the slider listeners
        removeListenersFromSlider(single_bottom_z_slider);
        //add the bottom slider listener
        //for the start slider
        single_bottom_z_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the slider value
                int sliderValue = single_bottom_z_slider.getValue();

                //get the end slider value
                int endSliderValue = single_top_z_slider.getValue();

                //if the slider value is greater than the end slider value
                if (sliderValue > endSliderValue) {

                    //set the end slider value to the slider value
                    single_top_z_slider.setValue(sliderValue);

                }

                //set the correspoinding spinner value
                single_bottom_z_spinner.setValue(sliderValue);

            }

        });

        //remove the slider listeners
        removeListenersFromSlider(single_top_z_slider);
        //add the top slider listener
        //for the start slider
        single_top_z_slider.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the slider value
                int sliderValue = single_top_z_slider.getValue();

                //get the end slider value
                int startSliderValue = single_bottom_z_slider.getValue();

                //if the slider value is greater than the end slider value
                if (sliderValue < startSliderValue) {

                    //set the end slider value to the slider value
                    single_bottom_z_slider.setValue(sliderValue);

                }

                //set the correspoinding spinner value
                single_top_z_spinner.setValue(sliderValue);

            }

        });

        //remove the spinner listeners
        removeListenersFromSpinner(single_bottom_z_spinner);
        //add the bottom spinner listener
        single_bottom_z_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the spinner value
                int spinnerValue = (int) single_bottom_z_spinner.getValue();

                //get the end spinner value
                int endSpinnerValue = (int) single_top_z_spinner.getValue();

                //if the spinner value is greater than the end spinner value
                if (spinnerValue > endSpinnerValue) {

                    //set the end spinner value to the spinner value
                    single_top_z_spinner.setValue(spinnerValue);

                }

                //set the correspoinding slider value
                single_bottom_z_slider.setValue(spinnerValue);

            }

        });

        //remove the spinner listeners
        removeListenersFromSpinner(single_top_z_spinner);
        //add the top spinner listener
        single_top_z_spinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                //get the spinner value
                int spinnerValue = (int) single_top_z_spinner.getValue();

                //get the end spinner value
                int startSpinnerValue = (int) single_bottom_z_spinner.getValue();

                //if the spinner value is greater than the end spinner value
                if (spinnerValue < startSpinnerValue) {

                    //set the end spinner value to the spinner value
                    single_bottom_z_spinner.setValue(spinnerValue);

                }

                //set the correspoinding slider value
                single_top_z_slider.setValue(spinnerValue);

            }

        });
    
    }

    //method to start the cut sbs
    public static void startCutSbs(String filePath, int squareSize, String savePath, String suffix, String sbsBaseName, JButton selectZButton, JLabel currentZLabel) {

        //log that the roi manager is being reset
        currentLog.addLog("Resetting the roi manager");
        
        //construct a roi manager
        roiManager = new RoiManager();

        //reset the roi manager
        roiManager.reset();

        //log that the roi manager was reset
        currentLog.addLog("The roi manager was reset");

        //log that the image will be opened
        currentLog.addLog("Working with image: "+currentFilePath);

        //select the image id
        IJ.selectWindow(cuttingImageID);   

        //get the number of channels
        int numberOfChannels = cuttingImage.getNChannels();
        
        //change the status label to opened
        single_start_status_label.setText("Status: Cutting Sbs");

        //log that the square size is being obtained
        currentLog.addLog("Getting the square size");

        //getting the square size from the text field
        squareSize = Integer.parseInt(single_square_size_textfield.getText());

        //log the square size obtained
        currentLog.addLog("The square size is: "+squareSize);

        //enable the Cut sbs button
        single_cut_sbs_button.setEnabled(true);

        //log that the point roi is being obtained
        currentLog.addLog("Getting the point roi");

        //get the roi in the image
        Roi currentRoi = cuttingImage.getRoi();

        //log that the roi was obtained
        currentLog.addLog("The roi was obtained");

        //log that the name is being set up to sbs_coords
        currentLog.addLog("Setting the roi name to sbs_coords");

        //set the roi name to sbs_coords
        currentRoi.setName("sbs_coords");

        //log that it will be added to the roi manager
        currentLog.addLog("Adding the roi to the roi manager");

        //get the roi manager instance
        roiManager = RoiManager.getInstance();

        //reset the roi manager
        roiManager.reset();

        //add the roi to the roi manager
        roiManager.addRoi(currentRoi);

        //log that it was added
        currentLog.addLog("The roi was added to the roi manager");

        //select nothing
        cuttingImage.resetRoi();

        //get the points in the roi
        Point[] currentPointArray = currentRoi.getContainedPoints();

        //get the point roi
        PointRoi currentPointRoi = (PointRoi) currentRoi;

        //get the number of points
        int numberOfPoints = currentPointArray.length;

        //set the current sbs counter to 0
        currentSbsCounter = 0;

        //make reset the hashmap
        pointMap = new HashMap<Integer, Map<int[], Roi>>();

        //loop through the nummber of points and add an empty entry to the map
        for (int i = 0; i < numberOfPoints; i++) {

            //get the y and x values
            int xValue = (int) currentPointArray[i].getX();
            int yValue = (int) currentPointArray[i].getY();

            //get the position of the point
            int pointPosition = currentPointRoi.getPointPosition(i);

            //get the dividing int that will hold the previous z stacks
            int dividingInt = pointPosition-1;

            //get the z stack by dividing the point position by the number of channels and adding 1
            int zValue = (dividingInt / numberOfChannels) + 1;

            //get the x and y coordinates
            int[] xyzCoordinates = new int[]{xValue,yValue,zValue};

            //get the square roi 
            Roi addingSquareRoi = new Roi(xValue - (squareSize / 2), yValue - (squareSize / 2), squareSize, squareSize);

            //add the x and y points to the map with an empty roi
            pointMap.put(i, new HashMap<int[], Roi>());

            //add the integer array and the roi to the map
            pointMap.get(i).put(xyzCoordinates, addingSquareRoi);

        }

        //log that the cutting system is being initialized
        currentLog.addLog("Initializing the cutting system");
        
        //update the map with the method
        updateMap();

    }

    //method to advance the cut sbs step
    public static void cutCurrentSbs () {

        //log that the sbs will be cut
        currentLog.addLog("Cutting the sbs: sbs"+(currentSbsCounter+1));

        //get the number of channels
        int numberOfChannels = cuttingImage.getNChannels();

        //get the square roi
        Roi toAddSquareRoi = cuttingImage.getRoi();

        //if the roi is null
        if (toAddSquareRoi == null) {

            //show an error message
            JOptionPane.showMessageDialog(null, "Select square roi before cutting", "Error", JOptionPane.ERROR_MESSAGE);

            //return
            return;

        }

        //add the roi to the roi manager
        //get the roi manager instance
        roiManager = RoiManager.getInstance();

        //get the top and bottom z values from the sliders
        int bottomZValue = single_bottom_z_slider.getValue();

        //get the top and bottom z values from the sliders
        int topZValue = single_top_z_slider.getValue();

        //get the zstring
        String zString = "z" + bottomZValue + "-z" + topZValue;

        //get the roi name
        String roiName = "sbs" + (currentSbsCounter + 1) + "_" + zString;

        //log that the square roi is being added to the roi manager
        currentLog.addLog("Adding the square roi to the roi manager");

        //log the square roi name
        currentLog.addLog("The square roi name is: " + roiName);

        //set the name of the roi
        toAddSquareRoi.setName(roiName);

        //add the roi to the roi manager
        roiManager.addRoi(toAddSquareRoi);

        //log that it was added
        currentLog.addLog("The square roi was added to the roi manager");

        //log that the image is being cut
        currentLog.addLog("Cutting the image");

        //cut the image
        ImagePlus cutImage = new Duplicator().run(cuttingImage, 1, numberOfChannels, bottomZValue, topZValue, 1, 1);

        //log that the image was cut
        currentLog.addLog("The image was cut");

        //get the save path
        String savePath = single_save_textfield.getText();

        //get the parent of the save path
        String saveDir = savePath.substring(0, savePath.lastIndexOf(File.separator)+1);

        //get the suffix
        String suffix = single_suffix_text_field.getText();

        //get the file name
        String cuttingFileString = currentFilePath.substring(currentFilePath.lastIndexOf(File.separator) + 1, currentFilePath.lastIndexOf(".")) + "_"+ suffix + (currentSbsCounter + 1) + ".tif";

        //log the saveint total path
        currentLog.addLog("The saving path is: " + saveDir + cuttingFileString);

        //save the image
        IJ.saveAs(cutImage, "Tiff", saveDir + cuttingFileString);

        //log that the image was saved
        currentLog.addLog("The image was saved");

        //release the image from memory
        cutImage.close();

        //add the counter
        currentSbsCounter++;

        //update the map
        updateMap();

    }

    //method to update the map
    public static void updateMap() {

        //get the length of the map
        int mapLength = pointMap.size();

        //if the current sbs counter is greater than the map length
        if (currentSbsCounter >= mapLength) {

            //log that the cutting is finished
            currentLog.addLog("The cutting is finished");

            //log that the roiManager is being saved
            currentLog.addLog("Saving the roi manager");

            //get the save path
            String savePath = single_save_textfield.getText();

            //save the roi manager
            roiManager.runCommand("Save", savePath);

            //log that the roi manager was saved to
            currentLog.addLog("The roi manager was saved to: " + savePath);

            //reset the rois in the cutting image
            cuttingImage.resetRoi();

            //show a message
            JOptionPane.showMessageDialog(null, "The cutting is finished", "Finished", JOptionPane.INFORMATION_MESSAGE);

            //return
            return;

        }

        //add to the logobject the working sbs
        currentLog.addLog("Working with sbs: " + (currentSbsCounter + 1));

        //reset the roi
        cuttingImage.resetRoi();

        //get the key from the map
        int[] currentKey = (int[]) pointMap.get(currentSbsCounter).keySet().toArray()[0];

        //get the z position
        int zPosition = currentKey[2];

        //set the slice to the z position
        cuttingImage.setZ(zPosition);

        //log the position of the roi
        currentLog.addLog("The position of the roi is: " + zPosition);

        //get the roi from the map
        Roi squaringRoi = pointMap.get(currentSbsCounter).get(currentKey);

        //add the roi to the image
        cuttingImage.setRoi(squaringRoi);

        //log that the square roi is being verified
        currentLog.addLog("Verifying the square roi");

    }

    //method to cut all sbs now
    public static void cutAllSbsNow() {

        return;

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

    //method to remove listeners from sliders
    public static void removeListenersFromSlider(JSlider slider) {

        //get the listeners from the slider
        ChangeListener[] slider_listeners = slider.getChangeListeners();

        //remove the listeners
        for (ChangeListener sliderListener : slider_listeners) {
            slider.removeChangeListener(sliderListener);
        }

    }

    //method to remove listeners from spinners
    public static void removeListenersFromSpinner(JSpinner spinner) {

        //get the listeners from the spinner
        ChangeListener[] spinner_listeners = spinner.getChangeListeners();

        //remove the listeners
        for (ChangeListener spinnerListener : spinner_listeners) {
            spinner.removeChangeListener(spinnerListener);
        }

    }

    //method to reset the second section panel
    public static void resetCutSbsPanel() {

        //remove all the components from the panel
        cutSbsPanel.removeAll();

        //set the second section panel's layout to null
        cutSbsPanel.setLayout(null);

        //restart the panel's spring layout
        cutSbsPanel_springLayout = new SpringLayout();

        //add the new spring layout to the panel
        cutSbsPanel.setLayout(cutSbsPanel_springLayout);

        //set the single and batch buttons to enabled
        for (JButton button : singleBatchButtonsArray) {

            //add the button to the frame
            button.setEnabled(true);

        }

        //repaint the panel
        cutSbsPanel.repaint();

    }

    //method to reset the single menu
    public static void resetSingleMenu() {

        //#region ######################################### FOR THE SINGLE SECTION #########################################
        //declare the components, add them and set the constraints
        //JLabels
        single_file_label = new JLabel("File: ");
        single_square_size_label = new JLabel("Square Size: ");
        single_save_label = new JLabel("Roi save file: ");
        single_suffix_label = new JLabel("Suffix: ");
        single_start_status_label = new JLabel("Status: ");
        single_advance_sbs_step_label = new JLabel(" - Cut Sbs step - ");
        single_all_z_sections_label = new JLabel("All Z Sections: ");
        confirm_rectangle_label = new JLabel("Confirm Rectangle: ");
        single_botton_z_label = new JLabel("Bottom Z: ");
        single_top_z_label = new JLabel("Top Z: ");

        //JTextFields
        single_file_textfield = new JTextField("");
        single_square_size_textfield = new JTextField("100");
        single_save_textfield = new JTextField("");
        single_suffix_text_field = new JTextField("sbs");

        //JButtons
        single_file_select_button = new JButton("Select");
        single_save_select_button = new JButton("Select");
        single_start_button = new JButton("Start Cutting");
        single_cut_sbs_button = new JButton("Cut sbs");
        single_advance_sbs_step_button = new JButton(" Advance Step ");
        single_file_open_button = new JButton("Open");


        //JCheckBoxes
        single_all_z_sections_checkbox = new JCheckBox();
        single_confirm_rectangle_checkbox = new JCheckBox();

        //JSliders 
        single_bottom_z_slider = new JSlider();
        single_top_z_slider = new JSlider();

        //set the JSlider values
        single_bottom_z_slider.setMinimum(1);
        single_bottom_z_slider.setMaximum(2);
        single_bottom_z_slider.setValue(1);

        single_top_z_slider.setMinimum(1);
        single_top_z_slider.setMaximum(2);
        single_top_z_slider.setValue(2);

        //JSpinner
        single_bottom_z_spinner = new JSpinner();
        single_top_z_spinner = new JSpinner();

        //set the spinner max and min
        single_bottom_z_spinner.setModel(new SpinnerNumberModel(1, 1, 2, 1));
        single_top_z_spinner.setModel(new SpinnerNumberModel(2,1,2,1));
        single_bottom_z_spinner.setValue(1);
        single_top_z_spinner.setValue(2);


        //#endregion #################################

        //the log object
        currentLog = new logObject("none", "none","none", false, false);

        //the roi manager
        roiManager = new RoiManager();

        //the cutting image
        cuttingImage = null;

        //the image id
        cuttingImageID = 0;

        //set the coordsRoi
        coordsRoi = null;

        currentPointArray = new Point[1];

        //a bool for the selected z flag
        selectedZFlag = false;

        //make a state for the current sbs
        currentSbs_state = -1;

        //make a current sbs counter
        currentSbsCounter = 0;

        //make the cuttingLowerz and cuttingUpperz ints
        cuttingLowerz = 0;
        cuttingUpperz = 0;
        
        //for the number of slices
        number_of_slices = 0;

        //set the file path again
        currentFilePath = "";

        //set the has map again
        pointMap = new HashMap<Integer, Map<int[],Roi>>();

        //for the previous rotation
        previousRotation = 0;

        rotationSlices = 0;

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
        WindowListener[] frameListeners = cutSbs_frame.getWindowListeners();

        //loop through the list and remove them
        for (WindowListener windowListener : frameListeners) {

            //remove the listener
            cutSbs_frame.removeWindowListener(windowListener);

        }

        //add the new listener
        cutSbs_frame.addWindowListener(new WindowAdapter() {

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
        cutSbs_frame.dispose();

        //run the method in the mainMenu to reset the button
        mainMenu.resetOpenedMenuButton(currentClass);

        //set the boolean to false
        cutSbs_Initialized = false;

    }

    //method to retrieve if the frame is initialized
    public static boolean isInitialized() {

        //return the boolean
        return cutSbs_Initialized;

    }

    //#endregion end of methods region

}
