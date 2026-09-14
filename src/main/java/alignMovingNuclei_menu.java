//#region Imports

// the java imports
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
//the javax imports
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JCheckBox;

//the IJ imports
import ij.IJ;
import ij.WindowManager;
import ij.ImagePlus;
import ij.gui.PointRoi;
import ij.gui.Roi;
import ij.process.ImageProcessor;


//#endregion Imports


public class alignMovingNuclei_menu {
    
    //#region Variables

    //#start the java swing elements
    
    //general variables
    static int alignMovingNuclei_menu_width = 600;
    static int alignMovingNuclei_menu_height = 400;

    public static boolean alignMovingNuclei_Initialized = false;

    //set the working image
    public static ImagePlus workingImage = null;

    //Frames
    public static JFrame alignMovingNucleiFrame = new JFrame("Align Moving Nuclei");

    //Containers
    public static Container alignMovingNuclei_frame_contentPane;

    //The spring layouts
    public static SpringLayout alignMovingNuclei_menu_springLayout = new SpringLayout();

    //Labels
    public static JLabel alignMovingNuclei_menu_file_label = new JLabel("File: ");
    public static JLabel alignMovingNuclei_menu_movements_label = new JLabel("Movements: ");
    public static JLabel alignMovingNuclei_menu_individual_label = new JLabel("Individual");
    public static JLabel alignMovingNuclei_menu_saving_dir_label = new JLabel("Saving path: ");
    public static JLabel alignMovingNuclei_menu_save_movements_label = new JLabel("Save Movements: ");

    //Text fields
    public static JLabel alignMovingNuclei_menu_file_textField = new JLabel("open_file");
    public static JTextField alignMovingNuclei_menu_saving_dir_textField = new JTextField("select saving path");
    public static JTextField alignMovingNuclei_menu_save_movements_textField = new JTextField("select saving path");

    //The JButton
    public static JButton alignMovingNuclei_menu_open_button = new JButton("Open");
    public static JButton alignMovingNuclei_menu_select_current_image_button = new JButton("Select Current");
    public static JButton alignMovingNuclei_menu_select_base_file = new JButton("Select Base File");
    public static JButton alignMovingNuclei_menu_save_image_button = new JButton("Save File");
    public static JButton alignMovingNuclei_menu_select_movements_base_file = new JButton("Select Base File");
    public static JButton alignMovingNuclei_menu_save_movements_button = new JButton("Save Movements");

    //The checkboxes
    public static JCheckBox alignMovingNuclei_menu_individual_checkbox = new JCheckBox("Individual");

    //The confirm movement button
    public static JButton alignMovingNuclei_menu_confirm_movement_button = new JButton("Confirm Movement");

    //make an empty hashmap of the movements
    public static HashMap<Integer, HashMap<String, ArrayList<Integer>>> movements_history = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();

    //#endregion Variables


    //#region Methods

    //method to inizialize the frame
    public static void initializeFrame() {

        //start the alignMovingNuclei frame
        alignMovingNucleiFrame = new JFrame("Align Moving Nuclei");

        //get the content pane
        alignMovingNuclei_frame_contentPane = alignMovingNucleiFrame.getContentPane();

        //add the components to the frame
        addComponentsToFrame();

        //add the action listeners
        addActionListeners();

        //pack the frame
        alignMovingNucleiFrame.pack();

        //set the frame to visible
        alignMovingNucleiFrame.setVisible(true);

        //set the initialized to true
        alignMovingNuclei_Initialized = true;


    }

    //method to add the components to the frame
    public static void addComponentsToFrame() {

        //add the sprint layout to the content pane
        alignMovingNuclei_frame_contentPane.setLayout(alignMovingNuclei_menu_springLayout);

        //set the minimum size
        alignMovingNucleiFrame.setMinimumSize(new Dimension(400, 400));

        //set the size
        alignMovingNucleiFrame.setSize(alignMovingNuclei_menu_width, alignMovingNuclei_menu_height);

        //add the file label to the contenta pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_file_label);

        //add the text field to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_file_textField);

        //add the open button to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_open_button);

        //add the select current image button to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_select_current_image_button);

        //add the saving dir label to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_saving_dir_label);

        //add the saving dir text field to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_saving_dir_textField);

        //add the select base file button to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_select_base_file);

        //add the save image button to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_save_image_button);

        //add the movements label to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_movements_label);

        //add the save movements text field
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_save_movements_textField);

        //add the select movements base file button
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_select_movements_base_file);

        //add the save movements button
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_save_movements_button);

        //add the individual checkbox to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_individual_checkbox);

        //add the add movement button to the content pane
        alignMovingNuclei_frame_contentPane.add(alignMovingNuclei_menu_confirm_movement_button);

        //set the constraints for the file label
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_file_label, 10, SpringLayout.WEST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_file_label, 10, SpringLayout.NORTH, alignMovingNuclei_frame_contentPane);

        //set the constraints for the open button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_open_button, -10, SpringLayout.EAST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_open_button, 10, SpringLayout.NORTH, alignMovingNuclei_frame_contentPane);

        //set the constraints for the select current image button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_select_current_image_button, -10, SpringLayout.WEST, alignMovingNuclei_menu_open_button);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_select_current_image_button, 10, SpringLayout.NORTH, alignMovingNuclei_frame_contentPane);

        //set the constraints for the file text field
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_file_textField, 10, SpringLayout.EAST, alignMovingNuclei_menu_file_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_file_textField, 0, SpringLayout.NORTH, alignMovingNuclei_menu_file_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_file_textField, -10, SpringLayout.WEST, alignMovingNuclei_menu_select_current_image_button);

        //set the constraints for the saving dir label
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_saving_dir_label, 10, SpringLayout.WEST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_saving_dir_label, 10, SpringLayout.SOUTH, alignMovingNuclei_menu_file_label);
        
        //set the constraints for the save image button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_save_image_button, -10, SpringLayout.EAST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_save_image_button, 0, SpringLayout.NORTH, alignMovingNuclei_menu_saving_dir_label);

        //set the constraints for the select base file button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_select_base_file, -10, SpringLayout.WEST, alignMovingNuclei_menu_save_image_button);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_select_base_file, 0, SpringLayout.NORTH, alignMovingNuclei_menu_saving_dir_label);

        //set the constraints for the saving dir text field
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_saving_dir_textField, 10, SpringLayout.EAST, alignMovingNuclei_menu_saving_dir_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_saving_dir_textField, 0, SpringLayout.NORTH, alignMovingNuclei_menu_saving_dir_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_saving_dir_textField, -10, SpringLayout.WEST, alignMovingNuclei_menu_select_base_file);

        //set the constraints for the movements label
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_movements_label, 10, SpringLayout.WEST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_movements_label, 10, SpringLayout.SOUTH, alignMovingNuclei_menu_save_image_button);

        //set the constraints for the save movements button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_save_movements_button, -10, SpringLayout.EAST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_save_movements_button, 0, SpringLayout.NORTH, alignMovingNuclei_menu_movements_label);

        //set the constraints for the select movements base file button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_select_movements_base_file, -10, SpringLayout.WEST, alignMovingNuclei_menu_save_movements_button);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_select_movements_base_file, 0, SpringLayout.NORTH, alignMovingNuclei_menu_movements_label);

        //set the constraints for the save movements text field
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_save_movements_textField, 10, SpringLayout.EAST, alignMovingNuclei_menu_movements_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_save_movements_textField, 0, SpringLayout.NORTH, alignMovingNuclei_menu_movements_label);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.EAST, alignMovingNuclei_menu_save_movements_textField, -10, SpringLayout.WEST, alignMovingNuclei_menu_select_movements_base_file);

        //set the constraints for the individual checkbox
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_individual_checkbox, 10, SpringLayout.WEST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_individual_checkbox, 10, SpringLayout.SOUTH, alignMovingNuclei_menu_movements_label);

        //set the constraints for the confirm movement button
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.WEST, alignMovingNuclei_menu_confirm_movement_button, 10, SpringLayout.WEST, alignMovingNuclei_frame_contentPane);
        alignMovingNuclei_menu_springLayout.putConstraint(SpringLayout.NORTH, alignMovingNuclei_menu_confirm_movement_button, 10, SpringLayout.SOUTH, alignMovingNuclei_menu_individual_checkbox);

    }

    public static void addActionListeners() {

        //add the action listener to the open button
        alignMovingNuclei_menu_open_button.addActionListener(e -> {

            //open the file
            openFile();

        });

        //add the action listener to the select current image button
        alignMovingNuclei_menu_select_current_image_button.addActionListener(e -> {

            //select the current image
            selectCurrentImage();

        });

        //add the action listener to the confirm movement button
        alignMovingNuclei_menu_confirm_movement_button.addActionListener(e -> {

            //confirm the movement
            confirmMovement();

        });

        //add the action listener to the select base file button
        alignMovingNuclei_menu_select_base_file.addActionListener(e -> {

            //select the base file
            selectBaseFile();

        });

        //add the action listener to the save image button
        alignMovingNuclei_menu_save_image_button.addActionListener(e -> {

            //save the image
            saveImage();

        });

        //add the action listener to the select movements base file button
        alignMovingNuclei_menu_select_movements_base_file.addActionListener(e -> {

            //select the movements base file
            selectMovementsBaseFile();

        });

        //add the action listener to the save movements button
        alignMovingNuclei_menu_save_movements_button.addActionListener(e -> {

            //save the movements
            saveMovements();

        }

        );

    }

    //mehotd to open the file
    public static void openFile() {

        //if the working image is not null
        if (workingImage != null) {

            //ask if the user wants to close the current image
            boolean close_current_image = IJ.showMessageWithCancel("Close current image", "Do you want to close the current image?");

            //if the user does not want to close the current image
            if (!close_current_image) {

                //return
                return;

            }

            //close the current image
            workingImage.close();

        }

        //set the saving path to ""
        String saving_path = "";

        //get the file path
        String file_path = IJ.getFilePath("Select the file");

        //if the file path is null
        if (file_path == null) {

            //return
            return;

        }

        //get the file to later get the parent directory
        File file_open_object = new File(file_path);

        //get the parend directory of the file
        String parent_directory = file_open_object.getParent();
        
        //get the opening file name
        String opening_file_name = file_open_object.getName();

        //set the opening file name without the extension
        String opening_file_name_no_ext = opening_file_name.substring(0, opening_file_name.lastIndexOf("."));

        //if the parent directory is not null, add it to the saving path
        if (parent_directory != null) {

            //set the saving path
            saving_path = parent_directory;

        }

        //get the saving_file_name_suggestion
        String saving_file_name_suggestion = opening_file_name_no_ext + "_aligned.tif";

        //get the suggested movements file name
        String suggested_movements_file_name = opening_file_name_no_ext + "_align_movements.txt";
        
        //add the saving file name suggestion to the saving path
        saving_path = saving_path + "/" + saving_file_name_suggestion;

        //set the text field
        alignMovingNuclei_menu_file_textField.setText(opening_file_name);

        //set the saving path
        alignMovingNuclei_menu_saving_dir_textField.setText(saving_path);

        //set the movements save path
        alignMovingNuclei_menu_save_movements_textField.setText(parent_directory + "/" + suggested_movements_file_name);

        //run the bioformats importer
        IJ.run("Bio-Formats", "open=" + file_path);

        //set the working image
        workingImage = WindowManager.getCurrentImage();

        //reset the movements history
        movements_history = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();

        //initialize the movements history
        initializeMovementHistory();

    }

    //method to select the current image
    public static void selectCurrentImage() {

        //if the working image is not null
        if (workingImage != null) {

            //ask if the user wants to close the current image
            boolean close_current_image = IJ.showMessageWithCancel("Close current image", "Do you want to close the current image?");

            //if the user does not want to close the current image
            if (!close_current_image) {

                //return
                return;

            }

            //close the current image
            workingImage.close();

        }

        //set the current image
        workingImage = WindowManager.getCurrentImage();

        //if the working image is null
        if (workingImage == null) {

            //print the error
            IJ.error("No image is selected");

            //return
            return;

        }

        //set the text field
        alignMovingNuclei_menu_file_textField.setText(workingImage.getTitle());

        //reset the movements history
        movements_history = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();

        //initialize the movements history
        initializeMovementHistory();

    }

    //method to get moved image
    public static ImageProcessor getMovedImage(ImageProcessor moving_image, int moving_x, int moving_y) {

        //get a copy of the moving image
        ImageProcessor returning_image = moving_image.duplicate();

        //if the x is not 0
        if (moving_x != 0) {

            //move the image
            returning_image.translate(moving_x, 0);

        }

        //if the y is not 0
        if (moving_y != 0) {

            //move the image
            returning_image.translate(0, moving_y);

        }

        //return the image
        return returning_image;

    }

    //method to initialize movements history
    public static void initializeMovementHistory() {

        //get the number of images on the stack
        int number_of_images = workingImage.getStackSize();

        //loop through the images
        for (int a = 1; a <= number_of_images; a++) {

            //make a new hashmap
            HashMap<String, ArrayList<Integer>> new_hashmap = new HashMap<String, ArrayList<Integer>>();

            //add the x and y movements
            new_hashmap.put("x", new ArrayList<Integer>());
            new_hashmap.put("y", new ArrayList<Integer>());

            //add the hashmap to the movements history
            movements_history.put(a, new_hashmap);

        }

    }

    //method to confirm the movement
    public static void confirmMovement() {

        //get the current roi
        Roi current_roi = workingImage.getRoi();

        //check if the roi is null
        if (current_roi == null) {

            //print the error
            IJ.error("No ROI selected");

            //return
            return;

        }

        //get the roi type
        int roi_type = current_roi.getType();

        //if the roi type is not 10
        if (roi_type != 10) {

            //print the error
            IJ.error("Please select a point ROI");

            //return
            return;

        }

        //get the current roi coordinates
        float[] current_roi_x_coordinates = current_roi.getFloatPolygon().xpoints;
        float[] current_roi_y_coordinates = current_roi.getFloatPolygon().ypoints;

        //print the moving points
        IJ.log("Point 1: " + current_roi_x_coordinates[0] + ", " + current_roi_y_coordinates[0]);
        IJ.log("Point 2: " + current_roi_x_coordinates[1] + ", " + current_roi_y_coordinates[1]);

        //get the number of points
        int number_of_points = current_roi_x_coordinates.length;

        //if the number of points is not 2
        if (number_of_points != 2) {

            //print the error
            IJ.error("Please select only two points");

            //return
            return;

        }

        //cast it into pointroi
        PointRoi current_point_roi = (PointRoi) current_roi;

        //get the status of the checkbox
        boolean individual_checkbox_state = alignMovingNuclei_menu_individual_checkbox.isSelected();

        //get the x difference
        float x_difference = current_roi_x_coordinates[1] - current_roi_x_coordinates[0];
        //get the y difference
        float y_difference = current_roi_y_coordinates[1] - current_roi_y_coordinates[0];

        //make them integers by rounding
        int x_difference_int = Math.round(x_difference);
        int y_difference_int = Math.round(y_difference);

        //get the position 1
        int position_1 = current_point_roi.getPointPosition(0);

        //if the checkbox is selected
        if (individual_checkbox_state) {

            //go to the position 1
            workingImage.setPosition(position_1);

            //get the image processor
            ImageProcessor current_image_processor = workingImage.getProcessor();

            //get the moved image
            ImageProcessor moved_image = getMovedImage(current_image_processor, x_difference_int, y_difference_int);

            //set the image processor
            workingImage.setProcessor(moved_image);

            //add the movements to the movements history
            movements_history.get(position_1).get("x").add(x_difference_int);
            movements_history.get(position_1).get("y").add(y_difference_int);

        }

        else {

            //go to the position 1
            workingImage.setPosition(position_1);

            //get the current z position
            int current_z_position = workingImage.getZ();

            //get the current t position
            int current_t_position = workingImage.getT();

            //get the current c position
            int current_c_position = workingImage.getC();

            //get the total number of t positions
            int total_t_positions = workingImage.getNFrames();

            //get the total number of c positions
            int total_c_positions = workingImage.getNChannels();

            //loop through the time points
            for (int a = 1; a <= total_t_positions; a++) {

                //set the T position
                workingImage.setT(a);

                //loop through the channels
                for (int b = 1; b <= total_c_positions; b++) {

                    //set the C position
                    workingImage.setC(b);

                    //loop through the z positions
                    for (int c = 1; c <= current_z_position; c++) {

                        //set the Z position
                        workingImage.setZ(c);

                        //get the stack position for the movement
                        int stack_position = workingImage.getStackIndex(b, c, a);

                        //get the image processor
                        ImageProcessor current_image_processor = workingImage.getProcessor();

                        //get the moved image
                        ImageProcessor moved_image = getMovedImage(current_image_processor, x_difference_int, y_difference_int);

                        //set the image processor
                        workingImage.setProcessor(moved_image);

                        //add the movements to the movements history
                        movements_history.get(stack_position).get("x").add(x_difference_int);
                        movements_history.get(stack_position).get("y").add(y_difference_int);

                    }

                }

            }

            //set it back to the original position
            workingImage.setT(current_t_position);
            workingImage.setC(current_c_position);
            workingImage.setZ(current_z_position);

            //clear the rois from the image
            workingImage.killRoi();

        }
        
    }

    //method to select the base file
    public static void selectBaseFile() {

        //get the file path
        String file_path = IJ.getFilePath("Select the base file");

        //if the file path is null
        if (file_path == null) {

            //return
            return;

        }

        //get the file name
        File file_object = new File(file_path);

        //get the file name
        String file_name = file_object.getName();

        //get the parent directory
        String parent_directory = file_object.getParent();

        //get the file name without the extension
        String file_name_no_ext = file_name.substring(0, file_name.lastIndexOf("."));

        //get the saving path
        String saving_path = parent_directory + "/" + file_name_no_ext + "_aligned.tif";

        //set the text field
        alignMovingNuclei_menu_saving_dir_textField.setText(saving_path);

        //get the suggested movements file name
        String suggested_movements_file_name = file_name_no_ext + "_align_movements.txt";

        //get the movements_save_path
        String movements_save_path = parent_directory + "/" + suggested_movements_file_name;

        //set the text field
        alignMovingNuclei_menu_save_movements_textField.setText(movements_save_path);

    }

    //method to select the movements base file
    public static void selectMovementsBaseFile() {

        //get the file path
        String file_path = IJ.getFilePath("Select the base file");

        //if the file path is null
        if (file_path == null) {

            //return
            return;

        }

        //get the file name
        File file_object = new File(file_path);

        //get the file name
        String file_name = file_object.getName();

        //get the parent directory
        String parent_directory = file_object.getParent();

        //get the file name without the extension
        String file_name_no_ext = file_name.substring(0, file_name.lastIndexOf("."));

        //get the saving path
        String saving_path = parent_directory + "/" + file_name_no_ext + "_movements.txt";

        //set the text field
        alignMovingNuclei_menu_save_movements_textField.setText(saving_path);

    }

    //method to save the image
    public static void saveImage() {

        //get the saving path
        String saving_path = alignMovingNuclei_menu_saving_dir_textField.getText();

        //if the saving path is null
        if (saving_path.equals("")) {

            //print the error
            IJ.error("Please select a saving path");

            //return
            return;

        }

        //check if the saving path already exists, if it does, ask the user if they want to overwrite it
        File saving_file = new File(saving_path);

        //if the file exists
        if (saving_file.exists()) {

            //ask the user if they want to overwrite the file
            boolean overwrite_file = IJ.showMessageWithCancel("File already exists", "The file already exists, do you want to overwrite it?");

            //if the user does not want to overwrite the file
            if (!overwrite_file) {

                //return
                return;

            }

        }

        //save the image
        IJ.saveAs(workingImage, "Tiff", saving_path);

    }

    //method to save the movements
    public static void saveMovements() {

        //get the saving path
        String saving_path = alignMovingNuclei_menu_save_movements_textField.getText();

        //if the saving path is null
        if (saving_path.equals("")) {

            //print the error
            IJ.error("Please select a saving path");

            //return
            return;

        }

        //check if the saving path already exists, if it does, ask the user if they want to overwrite it
        File saving_file = new File(saving_path);

        //if the file exists
        if (saving_file.exists()) {

            //ask the user if they want to overwrite the file
            boolean overwrite_file = IJ.showMessageWithCancel("File already exists", "The file already exists, do you want to overwrite it?");

            //if the user does not want to overwrite the file
            if (!overwrite_file) {

                //return
                return;

            }

        }

        //start the saving text
        String saving_text = "";

        //loop through the movements history
        for (int a = 1; a <= movements_history.size(); a++) {

            //get the x movements
            ArrayList<Integer> x_movements = movements_history.get(a).get("x");
            
            //if the length of the x movements is 0
            if (x_movements.size() == 0) {

                //continue
                continue;

            }

            //get the y movements
            ArrayList<Integer> y_movements = movements_history.get(a).get("y");

            //add the movements to the saving text
            saving_text = saving_text + "Position" + a + "\n";

            //loop through the x movements
            for (int b = 0; b < x_movements.size(); b++) {

                //add the x movement
                saving_text = saving_text + "X:" + x_movements.get(b) + ",Y:" + y_movements.get(b) + "\n";

            }

        }

        //if the saving text is empty
        if (saving_text.equals("")) {

            //print the error
            IJ.error("No movements to save");

            //return
            return;

        }

        //save the text
        IJ.saveString(saving_text, saving_path);

    }    

    //#endregion MEthods

}
