import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;

public class zBackgroundSubstraction_menu {
    
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

    //the current class
    static Class<?> currentClass = zBackgroundSubstraction_menu.class;

    //the image id label
    static String imageIDLabel = "Image ID:[ ]";

    //the main image id
    static int single_main_image_id = 0;

    //the background image id
    static int single_background_image_id = 0;

    //the substracted image id
    static int single_substracted_image_id = 0;
    
    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//

    //end of the swing elements region
    //#endregion

    //JFrames
    public static JFrame zBackground_frame = new JFrame("Z-Background Substraction Menu");

    //Containers
    public static Container zBackground_frame_contentPane = zBackground_frame.getContentPane();

    //JPanels
    public static JPanel secondSectionPanel = new JPanel();

    //SpringLayout
    public static SpringLayout zBackground_menu_springLayout = new SpringLayout();
    public static SpringLayout secondSection_springLayout = new SpringLayout();

    //JButtons
    public static JButton singleButton = new JButton("Single");
    public static JButton batchButton = new JButton("Batch");

    //JButtons ArraLists
    public static ArrayList<JButton> firstSectionButtons = new ArrayList<JButton>();

    //JSeparator
    public static JSeparator First_Second_sections_separator = new JSeparator(JSeparator.VERTICAL);

    ///////////// region for the single section ///////////////////
    
    //#region single section region

    //JSeparators
    public static JSeparator single_Second_Third_Separator = new JSeparator(JSeparator.HORIZONTAL);
    public static JSeparator single_Third_Fourth_Separator = new JSeparator(JSeparator.HORIZONTAL);
    public static JSeparator single_Fourth_Fifth_Separator = new JSeparator(JSeparator.HORIZONTAL);

    //main image section
    //JLabels
    public static JLabel single_main_image_label = new JLabel("Main image");
    public static JLabel single_main_image_textfield_label = new JLabel("Image Name:");
    public static JLabel single_main_image_textfield_id_label = new JLabel(imageIDLabel);

    //JButtons
    public static JButton single_Open_Main_Image_button = new JButton("Open");
    public static JButton single_Select_Current_Window_Main_Image_button = new JButton("Select Current Window");

    //JTextFields
    public static JTextField single_main_image_textfield = new JTextField();

    //background image section
    //JLabels
    public static JLabel single_background_image_label = new JLabel("Background image");
    public static JLabel single_background_image_textfield_label = new JLabel("Image Name:");
    public static JLabel single_background_image_textfield_id_label = new JLabel(imageIDLabel);

    //JButtons
    public static JButton single_Open_Background_Image_button = new JButton("Open");
    public static JButton single_Select_Current_Window_Background_Image_button = new JButton("Select Current Window");

    //JTextFields
    public static JTextField single_background_image_textfield = new JTextField();



    //substracted image section
    //JLabels
    public static JLabel single_substracted_image_label = new JLabel("Substracted image");
    public static JLabel single_substracted_image_textfield_label = new JLabel("Image Name:");
    public static JLabel single_substracted_image_textfield_id_label = new JLabel(imageIDLabel);

    //JButtons
    public static JButton single_Start_Substraction_button = new JButton("Start Substraction");
    public static JButton single_Close_Substraction_button = new JButton("Close Substraction");

    //JTextFields
    public static JTextField single_substracted_image_textfield = new JTextField();




    //save section
    //Jlabels
    public static JLabel single_save_label = new JLabel("Save Image");
    public static JLabel single_save_textfield_label = new JLabel("Save Path: ");

    //JTextFields
    public static JTextField single_save_textfield = new JTextField();

    //JButtons
    public static JButton single_Save_select_Path_button = new JButton("Select Base Image");
    public static JButton single_Save_button = new JButton("Save");

    //#endregion
    



    //end of the region
    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    
    //method to initialize the frame
    public static void initializeFrame() {

        //log that it has been initialized
        IJ.log("Initializing the zBackground Substraction Menu");

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
        zBackground_frame.setMinimumSize(new Dimension(600, 530));

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

        //add the second section panel's layout
        secondSectionPanel.setLayout(secondSection_springLayout);

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

                //run the method to reset the second section panel
                resetSecondSectionPanel();

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
                resetSecondSectionPanel();

                //call the method to initialize batch
                //startBatchMenu();

                //set the button to disabled
                batchButton.setEnabled(false);

            }

        });



    }

    //method to initialize the single menu
    public static void startSingleMenu() {

        //add the components
        addSingleMenuComponents();

        //set the components constraints
        setSingleMenuComponentsConstraints();

        //method to set the initial state of the components
        setSingleMenuComponentsInitialState();

        //set the components listeners
        setSingleMenuComponentsListeners();

        //pack the frame
        zBackground_frame.pack();

        //update the draw
        zBackground_frame.repaint();

    }

    //method to add the components to the single menu
    public static void addSingleMenuComponents() {

        //add the components to the second section panel
        
        //add the JSeparators
        secondSectionPanel.add(single_Second_Third_Separator);
        secondSectionPanel.add(single_Third_Fourth_Separator);
        secondSectionPanel.add(single_Fourth_Fifth_Separator);
      
        
        //add the single main image labels
        secondSectionPanel.add(single_main_image_label);
            //set the label to bold
        single_main_image_label.setFont(single_main_image_label.getFont().deriveFont(Font.BOLD));
        secondSectionPanel.add(single_main_image_textfield_label);
        secondSectionPanel.add(single_main_image_textfield_id_label);

        //add the single main image buttons
        secondSectionPanel.add(single_Open_Main_Image_button);
        secondSectionPanel.add(single_Select_Current_Window_Main_Image_button);

        //add the single main image textfields
        secondSectionPanel.add(single_main_image_textfield);

        //add the background image labels
        secondSectionPanel.add(single_background_image_label);
            //set the label to bold
        single_background_image_label.setFont(single_background_image_label.getFont().deriveFont(Font.BOLD));
        secondSectionPanel.add(single_background_image_textfield_label);
        secondSectionPanel.add(single_background_image_textfield_id_label);

        //add the background image buttons
        secondSectionPanel.add(single_Open_Background_Image_button);
        secondSectionPanel.add(single_Select_Current_Window_Background_Image_button);

        //add the background image textfields
        secondSectionPanel.add(single_background_image_textfield);

        //add the substracted image labels
        secondSectionPanel.add(single_substracted_image_label);
            //set the label to bold
        single_substracted_image_label.setFont(single_substracted_image_label.getFont().deriveFont(Font.BOLD));
        secondSectionPanel.add(single_substracted_image_textfield_label);
        secondSectionPanel.add(single_substracted_image_textfield_id_label);

        //add the substracted image buttons
        secondSectionPanel.add(single_Start_Substraction_button);
        secondSectionPanel.add(single_Close_Substraction_button);

        //add the substracted image textfields
        secondSectionPanel.add(single_substracted_image_textfield);

        //add the save image labels
        secondSectionPanel.add(single_save_label);
            //set the label to bold
        single_save_label.setFont(single_save_label.getFont().deriveFont(Font.BOLD));
        secondSectionPanel.add(single_save_textfield_label);
        
        //add the save image buttons
        secondSectionPanel.add(single_Save_select_Path_button);
        secondSectionPanel.add(single_Save_button);

        //add the save image textfields
        secondSectionPanel.add(single_save_textfield);


    }

    //method to set he components constraints for the single menu
    public static void setSingleMenuComponentsConstraints() {

        //get an int to be the separator for the subsections
        int sub_section_space = 25;

        //set the constraints for the single main image label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_main_image_label,
                                                5,
                                                SpringLayout.NORTH,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_main_image_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        //now for the open button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Open_Main_Image_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_main_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Open_Main_Image_button,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        //now for the select current window button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Select_Current_Window_Main_Image_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_main_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Select_Current_Window_Main_Image_button,
                                                5,
                                                SpringLayout.EAST,
                                                single_Open_Main_Image_button);

        //now for the textfield label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_main_image_textfield_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Main_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_main_image_textfield_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_main_image_textfield_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_main_image_textfield);

        //now for the text field
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_main_image_textfield,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Main_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_main_image_textfield,
                                                5,
                                                SpringLayout.EAST,
                                                single_main_image_textfield_label);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_main_image_textfield,
                                                -5,
                                                SpringLayout.WEST,
                                                single_main_image_textfield_id_label);

        //now for the textfield id label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_main_image_textfield_id_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Main_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_main_image_textfield_id_label,
                                                -5,
                                                SpringLayout.EAST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_main_image_textfield_id_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_main_image_textfield);

        //now for the second third separator
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Second_Third_Separator,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_main_image_textfield);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Second_Third_Separator,
                                                0,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_Second_Third_Separator,
                                                0,
                                                SpringLayout.EAST,
                                                secondSectionPanel);

        //now for the background image label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_background_image_label,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_Second_Third_Separator);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_background_image_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        //now for the open button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Open_Background_Image_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_background_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Open_Background_Image_button,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        //now for the select current window button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Select_Current_Window_Background_Image_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_background_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Select_Current_Window_Background_Image_button,
                                                5,
                                                SpringLayout.EAST,
                                                single_Open_Background_Image_button);

        //now for the textfield label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_background_image_textfield_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Background_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_background_image_textfield_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_background_image_textfield_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_background_image_textfield);

        //now for the text field
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_background_image_textfield,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Background_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_background_image_textfield,
                                                5,
                                                SpringLayout.EAST,
                                                single_background_image_textfield_label);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_background_image_textfield,
                                                -5,
                                                SpringLayout.WEST,
                                                single_background_image_textfield_id_label);

        //now for the textfield id label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_background_image_textfield_id_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Open_Background_Image_button);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_background_image_textfield_id_label,
                                                -5,
                                                SpringLayout.EAST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_background_image_textfield_id_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_background_image_textfield);
        
        //now for the third fourth separator
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Third_Fourth_Separator,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_background_image_textfield);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Third_Fourth_Separator,
                                                0,
                                                SpringLayout.WEST,
                                                secondSectionPanel);    
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_Third_Fourth_Separator,
                                                0,
                                                SpringLayout.EAST,
                                                secondSectionPanel);

        //now for the substracted image label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_substracted_image_label,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_Third_Fourth_Separator);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_substracted_image_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        
        //now for the start substracting button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Start_Substraction_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_substracted_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Start_Substraction_button,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);

        //now for the close button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Close_Substraction_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_substracted_image_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Close_Substraction_button,
                                                5,
                                                SpringLayout.EAST,
                                                single_Start_Substraction_button);

        //now for the substracted image textfield label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_substracted_image_textfield_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Start_Substraction_button); 
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_substracted_image_textfield_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_substracted_image_textfield_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_substracted_image_textfield);
                                                
        //now for the substracted image textfield
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_substracted_image_textfield,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Start_Substraction_button);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_substracted_image_textfield,
                                                5,
                                                SpringLayout.EAST,
                                                single_substracted_image_textfield_label);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_substracted_image_textfield,
                                                -5,
                                                SpringLayout.WEST,
                                                single_substracted_image_textfield_id_label);

        //now for the substracted image textfield id label
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_substracted_image_textfield_id_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_Start_Substraction_button);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_substracted_image_textfield_id_label,
                                                -5,
                                                SpringLayout.EAST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_substracted_image_textfield_id_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_substracted_image_textfield);

        //now for the fourth fifth separator
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Fourth_Fifth_Separator,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_substracted_image_textfield);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_Fourth_Fifth_Separator,
                                                0,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_Fourth_Fifth_Separator,
                                                0,
                                                SpringLayout.EAST,
                                                secondSectionPanel);

        //now for the save image label 
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_save_label,
                                                sub_section_space,
                                                SpringLayout.SOUTH,
                                                single_Fourth_Fifth_Separator);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_save_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);

        //now for the save path label  
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_save_textfield_label,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_save_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_save_textfield_label,
                                                5,
                                                SpringLayout.WEST,
                                                secondSectionPanel);
        secondSection_springLayout.putConstraint(SpringLayout.SOUTH,
                                                single_save_textfield_label,
                                                0,
                                                SpringLayout.SOUTH,
                                                single_save_textfield);
                                                
        //now for the save path textfield
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_save_textfield,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_save_label);
        secondSection_springLayout.putConstraint(SpringLayout.WEST,
                                                single_save_textfield,
                                                5,
                                                SpringLayout.EAST,
                                                single_save_textfield_label);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_save_textfield,
                                                -5,
                                                SpringLayout.EAST,
                                                secondSectionPanel);

        //now for the save button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH, 
                                                single_Save_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_save_textfield);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_Save_button,
                                                -5,
                                                SpringLayout.EAST,
                                                secondSectionPanel);

        //now for the select button
        secondSection_springLayout.putConstraint(SpringLayout.NORTH,
                                                single_Save_select_Path_button,
                                                5,
                                                SpringLayout.SOUTH,
                                                single_save_textfield);
        secondSection_springLayout.putConstraint(SpringLayout.EAST,
                                                single_Save_select_Path_button,
                                                -5,
                                                SpringLayout.WEST,
                                                single_Save_button);


    }

    //method to set the initial state of the single components
    public static void setSingleMenuComponentsInitialState() {

        //reset the single main image components
        resetSingleMainImageComponents();

        //reset the single background substraction components
        resetSingleBackgroundImageComponents();

        //reset the single substracted image components
        resetSingleSubstractedImageComponents();

        //disable the substracted image components
        disableSingleSubstractedImageComponents();

        //reset the single save image components
        resetSingleSaveImageComponents();

        //disable the save image components
        disableSingleSaveImageComponents();       

    }

    //method to reset the single main image components
    public static void resetSingleMainImageComponents() {

        //set the text on the single_main_image_textfield_id_label to normal
        single_main_image_textfield_id_label.setText(imageIDLabel);
    
        //set the initial button states
        single_Open_Main_Image_button.setEnabled(true);
        single_Select_Current_Window_Main_Image_button.setEnabled(true);
    
        //add the single main image textfields
        single_main_image_textfield.setText("");
        single_main_image_textfield.setEditable(false);

    }

    //method to set the single main image components to image loaded
    public static void setSingleMainImageComponentsToMainImageLoaded() {

        //set the text on the id label to the image id
        single_main_image_textfield_id_label.setText("Image ID:["+single_main_image_id+"]");

        //set the buttons to their state
        single_Open_Main_Image_button.setEnabled(false);
        single_Select_Current_Window_Main_Image_button.setEnabled(false);
    
        //add the single main image textfields
        single_main_image_textfield.setText(WindowManager.getImage(single_main_image_id).getTitle());
        single_main_image_textfield.setEditable(false);

    }

    //method to reset the single background image components
    public static void resetSingleBackgroundImageComponents() {

        //set the id back to Image ID:[]
        single_background_image_textfield_id_label.setText(imageIDLabel);

        //add the background image buttons
        single_Open_Background_Image_button.setEnabled(true);
        single_Select_Current_Window_Background_Image_button.setEnabled(true);

        //add the background image textfields
        single_background_image_textfield.setText("");
        single_background_image_textfield.setEditable(false);

    }

    //method to set the single background image components to image loaded
    public static void setSingleBackgroundImageComponentsToBackgroundImageLoaded() {

        //set the text on the id label to the image id
        single_background_image_textfield_id_label.setText("Image ID:["+single_background_image_id+"]");

        //set the buttons to their state
        single_Open_Background_Image_button.setEnabled(false);
        single_Select_Current_Window_Background_Image_button.setEnabled(false);

        //add the background image textfields
        single_background_image_textfield.setText(WindowManager.getImage(single_background_image_id).getTitle());
        single_background_image_textfield.setEditable(false);

    }

    //method to reset the single substracted image components
    public static void resetSingleSubstractedImageComponents() {

        //reset the image id label
        single_substracted_image_textfield_id_label.setText(imageIDLabel);
    
        //add the substracted image buttons
        single_Start_Substraction_button.setEnabled(true);
        single_Close_Substraction_button.setEnabled(false);

        //add the substracted image textfields
        single_substracted_image_textfield.setText("");
        single_substracted_image_textfield.setEditable(false);

    }

    //method to disable the single substracted image components
    public static void disableSingleSubstractedImageComponents() {

        //disable the buttons
        single_Start_Substraction_button.setEnabled(false);
        single_Close_Substraction_button.setEnabled(false);

        //add the substracted image textfields
        single_substracted_image_textfield.setEditable(false);

    }

    //method to set the substracted image components to image processed
    public static void setSingleSubstractedImageComponentsToImageProcessed() {

        //set the text on the id label to the image id
        single_substracted_image_textfield_id_label.setText("Image ID:["+single_substracted_image_id+"]");

        //disable the buttons
        single_Start_Substraction_button.setEnabled(false);
        single_Close_Substraction_button.setEnabled(true);

        //add the substracted image textfields
        single_substracted_image_textfield.setEditable(false);
        //get the substraction image name using the id
        single_substracted_image_textfield.setText(WindowManager.getImage(single_substracted_image_id).getTitle());

    }

    //method to reset the save image components
    public static void resetSingleSaveImageComponents() {
            
        //add the save image buttons
        single_Save_select_Path_button.setEnabled(true);
        single_Save_button.setEnabled(true);
    
        //reset the textfield
        single_save_textfield.setText("");
        single_save_textfield.setEditable(true);

    }

    //method to disable the save image components
    public static void disableSingleSaveImageComponents() {

        //disable the buttons
        single_Save_select_Path_button.setEnabled(false);
        single_Save_button.setEnabled(false);

        //add the save image textfields
        single_save_textfield.setText("");
        single_save_textfield.setEditable(false);

    }

    //method to set the single listeners
    public static void setSingleMenuComponentsListeners() {

        //start by setting the listeners for the main image components

        ///open image button main image
        //get the listeners from the button
        ActionListener[] openMainImageListeners = single_Open_Main_Image_button.getActionListeners();
        //remove the listeners
        for (ActionListener openMainImageListener : openMainImageListeners) {
            single_Open_Main_Image_button.removeActionListener(openMainImageListener);
        }
        //add the new listener
        single_Open_Main_Image_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //get a file chooser
                JFileChooser fileChooser = new JFileChooser();

                //set the file chooser to open files
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                //set the file chooser to open only image files
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "tif", "tiff", "jpg", "jpeg", "png", "gif", "bmp", "dv", "czi", "decon"));

                //open the file chooser
                int returnVal = fileChooser.showOpenDialog(null);

                //if the user selected a file
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    //get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    //open the image in a new imageplus
                    ImagePlus mainImage = IJ.openImage(selectedFile.getAbsolutePath());

                    //show the image
                    mainImage.show();

                    //if the image is not null, set the image id to the image id
                    if (mainImage != null) {
                        
                        single_main_image_id = mainImage.getID();

                    }

                    //add the new listener
                    mainImage.getWindow().addWindowListener(new WindowAdapter() {
                        
                        public void windowClosed(WindowEvent e) {

                            //set the main image id to 0
                            single_main_image_id = 0;
                            
                            //reset the main image components
                            resetSingleMainImageComponents();

                            //check if the images are loaded
                            checkIfBothImagesAreLoaded();
                        }

                    });

                    //if the image id is different to 0, set the components to image loaded
                    if (single_main_image_id != 0) {
                        setSingleMainImageComponentsToMainImageLoaded();
                    }

                }

                
                //check if the images are loaded
                checkIfBothImagesAreLoaded();

            }
        });

        //select current image button main image
        //get the listeners from the button
        ActionListener[] selectCurrentMainImageListeners = single_Select_Current_Window_Main_Image_button.getActionListeners();
        //remove the listeners
        for (ActionListener selectCurrentMainImageListener : selectCurrentMainImageListeners) {
            single_Select_Current_Window_Main_Image_button.removeActionListener(selectCurrentMainImageListener);
        }
        //add the new listener
        single_Select_Current_Window_Main_Image_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //get the current image
                ImagePlus currentImage = WindowManager.getCurrentImage();

                //if the image is not null, set the image id to the image id
                if (currentImage != null) {
                    
                    //set the image id
                    single_main_image_id = currentImage.getID();

                    //add the new listener
                    currentImage.getWindow().addWindowListener(new WindowAdapter() {
                        
                        public void windowClosed(WindowEvent e) {
                            
                            //set the main image id to 0
                            single_main_image_id = 0;

                            //reset the main image components
                            resetSingleMainImageComponents();

                            //check if the images are loaded
                            checkIfBothImagesAreLoaded();


                        }

                    });

                    //if the image id is different to 0, set the components to image loaded
                    if (single_main_image_id != 0) {
                        setSingleMainImageComponentsToMainImageLoaded();
                    }

                
                }

                //if the image is null, then show a message dialog
                else {
                    JOptionPane.showMessageDialog(null, "No image is currently open.", "No image open", JOptionPane.ERROR_MESSAGE);
                }

                //check if the images are loaded
                checkIfBothImagesAreLoaded();

            }
        });

        //open image button background image
        //get the listeners from the button
        ActionListener[] openBackgroundImageListeners = single_Open_Background_Image_button.getActionListeners();
        //remove the listeners
        for (ActionListener openBackgroundImageListener : openBackgroundImageListeners) {
            single_Open_Background_Image_button.removeActionListener(openBackgroundImageListener);
        }
        //add the new listener
        single_Open_Background_Image_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //get a file chooser
                JFileChooser fileChooser = new JFileChooser();

                //set the file chooser to open files
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                //set the file chooser to open only image files
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "tif", "tiff", "jpg", "jpeg", "png", "gif", "bmp", "dv", "czi", "decon"));

                //open the file chooser
                int returnVal = fileChooser.showOpenDialog(null);

                //if the user selected a file
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    //get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    //open the image in a new imageplus
                    ImagePlus backgroundImage = IJ.openImage(selectedFile.getAbsolutePath());

                    //show the image
                    backgroundImage.show();

                    //if the image is not null, set the image id to the image id
                    if (backgroundImage != null) {
                        
                        single_background_image_id = backgroundImage.getID();

                    }

                    //add the new listener
                    backgroundImage.getWindow().addWindowListener(new WindowAdapter() {
                        
                        public void windowClosed(WindowEvent e) {

                            //set the background image id to 0
                            single_background_image_id = 0;
                            
                            //reset the background image components
                            resetSingleBackgroundImageComponents();

                            //check if the images are loaded
                            checkIfBothImagesAreLoaded();

                        }

                    });

                    //if the image id is different to 0, set the components to image loaded
                    if (single_background_image_id != 0) {
                        setSingleBackgroundImageComponentsToBackgroundImageLoaded();
                    }

                }

                //check if the images are loaded
                checkIfBothImagesAreLoaded();
            
            }
        });

        //select current image button background image
        //get the listeners from the button
        ActionListener[] selectCurrentBackgroundImageListeners = single_Select_Current_Window_Background_Image_button.getActionListeners();
        //remove the listeners
        for (ActionListener selectCurrentBackgroundImageListener : selectCurrentBackgroundImageListeners) {
            single_Select_Current_Window_Background_Image_button.removeActionListener(selectCurrentBackgroundImageListener);
        }
        //add the new listener
        single_Select_Current_Window_Background_Image_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //get the current image
                ImagePlus currentImage = WindowManager.getCurrentImage();

                //if the image is not null, set the image id to the image id
                if (currentImage != null) {
                    
                    //set the image id
                    single_background_image_id = currentImage.getID();

                    //add the new listener
                    currentImage.getWindow().addWindowListener(new WindowAdapter() {
                        
                        public void windowClosed(WindowEvent e) {
                            
                            //set the main image id to 0
                            single_background_image_id = 0;

                            //reset the main image components
                            resetSingleBackgroundImageComponents();

                            //check if both images are open
                            checkIfBothImagesAreLoaded();

                        }

                    });

                    //if the image id is different to 0, set the components to image loaded
                    if (single_background_image_id != 0) {
                        setSingleBackgroundImageComponentsToBackgroundImageLoaded();
                    }

                
                }

                //if the image is null, then show a message dialog
                else {
                    JOptionPane.showMessageDialog(null, "No image is currently open.", "No image open", JOptionPane.ERROR_MESSAGE);
                }

                //check if the images are loaded
                checkIfBothImagesAreLoaded();

                

            }
        });;
        
        //for the substraction button
        //get the listeners from the button
        ActionListener[] substractionButtonListeners = single_Start_Substraction_button.getActionListeners();
        //remove the listeners
        for (ActionListener substractionButtonListener : substractionButtonListeners) {
            single_Start_Substraction_button.removeActionListener(substractionButtonListener);
        }
        //add the new listener
        single_Start_Substraction_button.addActionListener(new ActionListener() {
            
            //when the button is pressed
            public void actionPerformed(ActionEvent e) {

                //run the method to get the substraction Image
                getSubstractionImage();

                //check if the substraction image is loaded by looking at the substraction image id
                if (single_substracted_image_id != 0) {

                    //set the substraction image components to substraction image loaded
                    setSingleSubstractedImageComponentsToImageProcessed();

                    //set the save image components to substraction image loaded
                    resetSingleSaveImageComponents();

                }

            }

        } );

        //for the close substraction image button
        //get the listeners from the button
        ActionListener[] closeSubstractionImageListeners = single_Close_Substraction_button.getActionListeners();
        //remove the listeners
        for (ActionListener closeSubstractionImageListener : closeSubstractionImageListeners) {
            single_Close_Substraction_button.removeActionListener(closeSubstractionImageListener);
        }
        //add the new listener
        single_Close_Substraction_button.addActionListener(new ActionListener() {
            
            //when the button is pressed
            public void actionPerformed(ActionEvent e) {

                //close the substraction image
                WindowManager.getImage(single_substracted_image_id).close();

                //set the substraction image id to 0
                single_substracted_image_id = 0;

                //reset the substraction image components
                resetSingleSubstractedImageComponents();

                //reset the save image components
                resetSingleSaveImageComponents();

                //disable the save components
                disableSingleSaveImageComponents();

            }

        } );

        //for the select base image button
        //get the listeners from the button
        ActionListener[] selectBaseImageListeners = single_Save_select_Path_button.getActionListeners();
        //remove the listeners
        for (ActionListener selectBaseImageListener : selectBaseImageListeners) {
            single_Save_select_Path_button.removeActionListener(selectBaseImageListener);
        }
        //add the new listener
        single_Save_select_Path_button.addActionListener(new ActionListener() {
            
            //when the button is pressed
            public void actionPerformed(ActionEvent e) {

                //create the file chooser
                JFileChooser fileChooser = new JFileChooser();

                //set the file chooser filter
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "tif", "tiff", "jpg", "jpeg", "png", "gif", "bmp", "dv", "czi", "decon"));

                //open the file chooser
                int returnValue = fileChooser.showOpenDialog(null);

                //if the file is selected
                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    //get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    //get the path of the selected file
                    String selectedFilePath = selectedFile.getAbsolutePath();

                    //get the path without the extension
                    String selectedFilePathWithoutExtension = selectedFilePath.substring(0, selectedFilePath.lastIndexOf("."));

                    //add the termination _background_substracted.tif to the path
                    String selectedFilePathWithTermination = selectedFilePathWithoutExtension + "_background_substracted.tif";

                    //check if the file exists
                    if (new File(selectedFilePathWithTermination).exists()) {

                        //log that the file exists and you have added numbering
                        IJ.log("The file " + selectedFilePathWithTermination + " already exists. The file will be saved with a numbering.");

                        //get the date and time
                        LocalDateTime now = LocalDateTime.now();

                        //get the date and time as a string
                        String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

                        //add the date and time to the path
                        selectedFilePathWithTermination = selectedFilePathWithoutExtension + "_background_substracted_" + dateTime + ".tif";

                    }

                    //set the path to the text field
                    single_save_textfield.setText(selectedFilePathWithTermination);

                }


            }

        } );

        //for the save image button
        //get the listeners from the button
        ActionListener[] saveImageListeners = single_Save_button.getActionListeners();
        //remove the listeners
        for (ActionListener saveImageListener : saveImageListeners) {
            single_Save_button.removeActionListener(saveImageListener);
        }
        //add the new listener
        single_Save_button.addActionListener(new ActionListener() {
            
            //when the button is pressed
            public void actionPerformed(ActionEvent e) {

                //get the substraction image
                ImagePlus substractionImage = WindowManager.getImage(single_substracted_image_id);

                //get the path from the text field
                String path = single_save_textfield.getText();

                //save the image
                IJ.saveAs(substractionImage, "Tiff", path);

                //log that the image was saved
                IJ.log("The image was saved as " + path);

            }

        } );

    }

    //method to check if both, main image and background image are loaded
    public static void checkIfBothImagesAreLoaded() {

        //if both images are loaded, set the components to initialized in the substraction window
        if ((single_main_image_id != 0 && single_background_image_id != 0) && (single_main_image_id != single_background_image_id)) {

            //get both images
            ImagePlus mainImage = WindowManager.getImage(single_main_image_id);
            ImagePlus backgroundImage = WindowManager.getImage(single_background_image_id);

            //get the number of channels of both images
            int mainImageChannels = mainImage.getNChannels();
            int backgroundImageChannels = backgroundImage.getNChannels();

            //get the z sections of both images
            int mainImageZSections = mainImage.getNSlices();
            int backgroundImageZSections = backgroundImage.getNSlices();

            //get the time points of both images
            int mainImageTimePoints = mainImage.getNFrames();
            int backgroundImageTimePoints = backgroundImage.getNFrames();

            //if the number of channels, z sections and time points are the same, set the components to initialized
            if (mainImageChannels == backgroundImageChannels && mainImageZSections == backgroundImageZSections && mainImageTimePoints == backgroundImageTimePoints) {
                resetSingleSubstractedImageComponents();
            }

            //else, show a message dialog
            else {
                JOptionPane.showMessageDialog(null, "The number of channels, z sections and time points of the main image and the background image are not the same.", "Images not compatible", JOptionPane.ERROR_MESSAGE);

                //remove the last window listener from the main image and the background image
                mainImage.getWindow().removeWindowListener(mainImage.getWindow().getWindowListeners()[mainImage.getWindow().getWindowListeners().length - 1]);
                backgroundImage.getWindow().removeWindowListener(backgroundImage.getWindow().getWindowListeners()[backgroundImage.getWindow().getWindowListeners().length - 1]);

                //reset the image ids
                single_main_image_id = 0;
                single_background_image_id = 0;

                //reset the components of the main image and the background image
                resetSingleMainImageComponents();
                resetSingleBackgroundImageComponents();
            
            }
            

        }
        //else, set the components to not initialized
        else {
            
            //reset the substracted image components
            resetSingleSubstractedImageComponents();

            //disable them now
            disableSingleSubstractedImageComponents();

            //reset the save image components
            resetSingleSaveImageComponents();

            //disable the save image components
            disableSingleSaveImageComponents();

        }

        //if the images have the same id, show a message dialog
        if ((single_main_image_id == single_background_image_id) && single_main_image_id != 0) {
            JOptionPane.showMessageDialog(null, "The main image and the background image are the same.", "Images not compatible, this breaks the program somehow... gotta debug later...", JOptionPane.ERROR_MESSAGE);

            //remove the last window listener from the main image and the background image
            WindowManager.getImage(single_main_image_id).getWindow().removeWindowListener(WindowManager.getImage(single_main_image_id).getWindow().getWindowListeners()[WindowManager.getImage(single_main_image_id).getWindow().getWindowListeners().length - 1]);
            WindowManager.getImage(single_background_image_id).getWindow().removeWindowListener(WindowManager.getImage(single_background_image_id).getWindow().getWindowListeners()[WindowManager.getImage(single_background_image_id).getWindow().getWindowListeners().length - 1]);

            //reset the image ids
            single_main_image_id = 0;
            single_background_image_id = 0;

            //reset the components of the main image and the background image
            resetSingleMainImageComponents();
            resetSingleBackgroundImageComponents();

        }



    }

    //method to get the substraction image
    public static void getSubstractionImage() {

        //get both the images from the main image and the background image
        ImagePlus mainImage = WindowManager.getImage(single_main_image_id);
        ImagePlus backgroundImage = WindowManager.getImage(single_background_image_id);

        //make a new image plus to be the substraction image
        ImagePlus substractionImage = new ImagePlus();
        //copy the main image to the substraction image
        substractionImage = mainImage.duplicate();
        //show the substraction image
        substractionImage.show();

        //change the name of the substraction image to "Substraction Image"
        substractionImage.setTitle("Substraction Image");

        //if the type is not 32 bits, change it to 32 bits
        if (substractionImage.getType() != ImagePlus.GRAY32) {
            
            //change the type to 32 bits
            IJ.run(substractionImage, "32-bit", "");

        }
        
        //get the number of channels of the substractions image
        int substractionImageChannels = substractionImage.getNChannels();

        //get the number of z sections of the substraction image
        int substractionImageZSections = substractionImage.getNSlices();

        //get the number of time points of the substraction image
        int substractionImageTimePoints = substractionImage.getNFrames();

        //log the number of time points
        IJ.log("Number of time points: " + substractionImageTimePoints);

        //loop through the time points
        for (int tp_index = 0; tp_index < substractionImageTimePoints; tp_index++) {
            
            //loop through the number of channels
            for (int ch_index = 0; ch_index < substractionImageChannels; ch_index++) {
                
                //loop through the number of z sections
                for (int z_index = 0; z_index < substractionImageZSections; z_index++) {
                    
                    //set the position of the background image
                    backgroundImage.setPosition(ch_index + 1, z_index + 1, tp_index + 1);

                    //get the processor of the background image
                    ImageProcessor backgroundImageProcessor = backgroundImage.getProcessor();

                    //get the statistics of the processor
                    ImageStatistics backgroundImageStatistics = backgroundImageProcessor.getStatistics();

                    //get the mean of the background image
                    double backgroundImageMean = backgroundImageStatistics.mean;

                    //set the position of the substraction image
                    substractionImage.setPosition(ch_index + 1, z_index + 1, tp_index + 1);

                    //get the processor of the substraction image
                    ImageProcessor substractionImageProcessor = substractionImage.getProcessor();

                    //substract the mean from the substraction image processor
                    substractionImageProcessor.subtract(backgroundImageMean);

                }

            }

        }

        //set all negative values to 0
        //loop through the time points
        for (int tp_index = 0; tp_index < substractionImageTimePoints; tp_index++) {
            
            //loop through the number of channels
            for (int ch_index = 0; ch_index < substractionImageChannels; ch_index++) {
                
                //loop through the number of z sections
                for (int z_index = 0; z_index < substractionImageZSections; z_index++) {
                    
                    //set the position of the substraction image
                    substractionImage.setPosition(ch_index + 1, z_index + 1, tp_index + 1);

                    //get the processor of the substraction image
                    ImageProcessor currentProcessor_zeros = substractionImage.getProcessor();

                    //get the width and heigh
                    int width = currentProcessor_zeros.getWidth();

                    int height = currentProcessor_zeros.getHeight();

                    //loop through the width
                    for (int x = 0; x < width; x++) {
                        
                        //loop through the height
                        for (int y = 0; y < height; y++) {
                            
                            //if the pixel value is negative, set it to 0
                            if (currentProcessor_zeros.getPixelValue(x, y) < 0) {
                                
                                //set the pixel value to 0
                                currentProcessor_zeros.set(x, y, 0);

                            }

                        }

                    }


                }

            }

        }

        /////////// comment this just in case
        /*
        //set a threshold for the image substracted
        IJ.setThreshold(substractionImage, 0.0000001, 1000000);
        IJ.run(substractionImage, "NaN Background", "stack");

        */

        //set the substraction image id
        single_substracted_image_id = substractionImage.getID();

        //add the action listener to the substraction image
        substractionImage.getWindow().addWindowListener(new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                
                //reset the substraction image components
                resetSingleSubstractedImageComponents();

                //reset the save image components
                resetSingleSaveImageComponents();

                //disable the save image components
                disableSingleSaveImageComponents();

                //reset the image id
                single_substracted_image_id = 0;

            }
        });

    }

    //method to reset the second section panel
    public static void resetSecondSectionPanel() {

        //get the initialized buttons
        ArrayList<JButton> initializedButtons = getInitializedFirstSectionButtons();

        //reset the corresponding second section components to their initial state
        resetInitializedSecondSectionComponents(initializedButtons);

        //remove all the components from the panel
        secondSectionPanel.removeAll();

        //set the second section panel's layout to null
        secondSectionPanel.setLayout(null);

        //restart the panel's spring layout
        secondSection_springLayout = new SpringLayout();

        //add the new spring layout to the panel
        secondSectionPanel.setLayout(secondSection_springLayout);

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

            //get the button text
            String buttonText = button.getText();

            //log that this button was initialized
            IJ.log(buttonText + " was initialized");

            //get the button label
            String buttonLabel = button.getText();

            //add the string _reset to the button label
            String resetButtonLabel = buttonLabel + "_resetComponents";

            //get the method with the reset button label
            try {
                Method reset_method = currentClass.getMethod(resetButtonLabel);
                //run the method
                reset_method.invoke(null);
                
                //reset the button to enabled
                button.setEnabled(true);
                
                
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

        //close the images if they exist
        //if the main image id is not 0, close the image
        if (single_main_image_id != 0) {
    
            //get the image
            ImagePlus image = WindowManager.getImage(single_main_image_id);

            //close the image
            image.close();

        }

        //if the background image id is not 0, close the image
        if (single_background_image_id != 0) {

            //get the image
            ImagePlus image = WindowManager.getImage(single_background_image_id);

            //close the image
            image.close();

        }

        //if the substracted image id is not 0, close it
        if (single_substracted_image_id != 0) {

            //get the image
            ImagePlus image = WindowManager.getImage(single_substracted_image_id);

            //close the image
            image.close();

        }

        //JSeparators
        single_Second_Third_Separator = new JSeparator(JSeparator.HORIZONTAL);
        single_Third_Fourth_Separator = new JSeparator(JSeparator.HORIZONTAL);
        single_Fourth_Fifth_Separator = new JSeparator(JSeparator.HORIZONTAL);

        //main image section
        //JLabels
        single_main_image_label = new JLabel("Main image");
        single_main_image_textfield_label = new JLabel("Image Name:");
        single_main_image_textfield_id_label = new JLabel(imageIDLabel);

        //JButtons
        single_Open_Main_Image_button = new JButton("Open");
        single_Select_Current_Window_Main_Image_button = new JButton("Select Current Window");

        //JTextFields
        single_main_image_textfield = new JTextField();



        //background image section
        //JLabels
        single_background_image_label = new JLabel("Background image");
        single_background_image_textfield_label = new JLabel("Image Name:");
        single_background_image_textfield_id_label = new JLabel(imageIDLabel);

        //JButtons
        single_Open_Background_Image_button = new JButton("Open");
        single_Select_Current_Window_Background_Image_button = new JButton("Select Current Window");

        //JTextFields
        single_background_image_textfield = new JTextField();

        //substracted image section
        //JLabels
        single_substracted_image_label = new JLabel("Substracted image");
        single_substracted_image_textfield_label = new JLabel("Image Name:");
        single_substracted_image_textfield_id_label = new JLabel(imageIDLabel);

        //JButtons
        single_Start_Substraction_button = new JButton("Start Substraction");
        single_Close_Substraction_button = new JButton("Close Substraction");

        //JTextFields
        single_substracted_image_textfield = new JTextField();

        //save section
        //Jlabels
        single_save_label = new JLabel("Save Image");
        single_save_textfield_label = new JLabel("Save Path: ");

        //JTextFields
        single_save_textfield = new JTextField();

        //JButtons
        single_Save_select_Path_button = new JButton("Select Base Image");
        single_Save_button = new JButton("Save");

        //set the image ids to 0
        single_main_image_id = 0;
        single_background_image_id = 0;
        single_substracted_image_id = 0;

        //set the button to enabled
        singleButton.setEnabled(true);

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

                //get the initialized buttons
                ArrayList<JButton> initialized_buttons = getInitializedFirstSectionButtons();

                //loop through the initialized buttons and run close them
                resetInitializedSecondSectionComponents(initialized_buttons);

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
