import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
//import ij.WindowManager;
import ij.plugin.ZProjector;
import ij.gui.Roi;
import ij.gui.RoiListener;
import ij.gui.ImageCanvas;
import ij.gui.Overlay;
import ij.gui.PointRoi;
//import ij.ImageListener;
import ij.plugin.frame.RoiManager;
import ij.process.FloatPolygon;
import ij.gui.YesNoCancelDialog;
import ij.Prefs;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Vector;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

import java.io.File;



public class classifier implements PlugIn {

	//#region Initiation variables and so on

	//declare the current image 
	ImagePlus current_image = new ImagePlus();

	//declare the projected image
	ImagePlus projected_image = new ImagePlus();

	//declaring the roi array that will hold the rois for the make sbs menu
	ArrayList<Roi> make_sbs_roi_list = new ArrayList<Roi>();

	//get the dimensions of the screen
	Dimension screensizes = IJ.getScreenSize();

	//creating the listener
	ImageListenerFlush new_image_listener = new ImageListenerFlush();

	//get the variable to hold the sbs_coords mouse listeners
	MouseListener sbs_coords_mouse_listener;

	/*
	//make a new roi listener for the make sbs image
	RoiListenerFlush make_sbs_coords_roi_listener = new RoiListenerFlush();

	*/

	//set up a variable to know if there is an image open
	boolean image_opened = false;

	//declaring the roi list from the make_sbs section
	ArrayList<Roi> roi_list = new ArrayList<Roi>();

	//getting the variable that holds the name of the point list in the roi manager
	String x_y_coordinates_roi_string = new String("sbs_coordinates");
	String x_y_coordinates_type_string = new String("Point");

	//getting the width of the screen / 2
	int JFrame_initial_width = 500;
	//getting the height of the screen / 2
	int JFrame_initial_height = 500;

	//setting up the projection image positions
	int projection_image_x_position = 500;
	int projection_image_y_position = 500;

	//get the number of properties per sbs roi
	int number_of_properties_per_sbs_roi = 3;

	//get the number of properties per sbs roi in a string array
	String[] number_of_properties_per_sbs_roi_string_array = {"square", "z1", "z2"};

	////////////////////Here i will show the roi properties structure 
	//////////   "squares:true/false" 
	//////////	 "z1:int"
	//////////   "z2:int"

	//set up a string to hold the working image path string field
	String working_image_path = new String("/home/user/path-to-file.tif");

	//creating flags to know if the gui have already been created... don't want to
	//figure out how to do this without this xD I'm 100% sure there is a better way
	//but I'm not sure how to do it
	boolean make_gui_created = false;
	boolean make_projection_gui_created = false;
	boolean make_sbs_menu_created = false;
	

	//setup a string to store the working image path even after you change the jtextbox
	String default_working_image_path_string = new String("/home/user/path-to-file.tif");
	String working_image_path_string = new String("/home/user/path-to-file.tif");


	//set up a string to hold the working image parent path
	String working_image_parent_path = new String("/home/user/");

	//setting up a variable to hold the projected image title
	String projected_image_title = new String("projection.tif"); 

	//the working window id for the make sbs window
	int Make_sbs_window_id = 0;



	//////////////////////////////////////////////////////////////////
	//#region Java swings elements

	//declare the jframe for the main window
    JFrame main_frame = new JFrame("Subset Classifier");
	//declare a JFrame for the projection menu
	JFrame projection_menu = new JFrame("Projection menu");
	//declare the JFrame for the cut sbs menu
	JFrame make_sbs_menu = new JFrame("SBS maker menu");

	//get an array with all of the JFrames that are not the main_frame
	JFrame[] all_jframes_except_main = {projection_menu, make_sbs_menu};

	//get an error frame
	//make a new jframe
	JFrame error_frame = new JFrame("Error");

	//declare a variable to hold the main image id
	int main_image_id = 0;

	//delcaring a variable to hold the projection image id
	int projected_image_id = 0;

	//declare a variable to hold the making sbs working image
	int make_sbs_working_image_id = 0;

    //get the content pane
    Container contentPane = main_frame.getContentPane();

	//get the content pane for the cut sbs menu
	Container make_sbs_menu_content_pane = make_sbs_menu.getContentPane();

    //set up the layout
    SpringLayout layout = new SpringLayout();

	//delcaring the buttons
	JButton Select_button = new JButton("Select");
	JButton Open_button = new JButton("Open");
	JButton Save_Projection_button = new JButton("Save Projection");  

	JButton make_sbs_roimanager_select_button = new JButton("Select Rois");
	JButton make_sbs_roimanager_save_button = new JButton("Save Rois");
	JButton make_sbs_roimanager_delete_sbs_button = new JButton("Delete SBS");
	JButton make_sbs_roimanager_cut_selected_sbs_button = new JButton("Cut SBS");
	JButton make_sbs_roimanager_cut_all_sbs_button = new JButton("Cut All SBS");
	JButton Make_sbs_roimanager_make_square_roi_button = new JButton("Make Square");
	JButton Make_sbs_roimanager_make_square_all_button = new JButton("Make all squares");
	JButton Make_sbs_roimanager_deselect_sbs_button = new JButton("Deselect SBS");
	

	//declaring the checkboxes
	JCheckBox Make_Projection_checkbox = new JCheckBox("Make Projection");
	JCheckBox Classify_checkbox = new JCheckBox("Classify SBS");
	JCheckBox Make_sbs_checkbox = new JCheckBox("SBS Maker");
	JCheckBox Project_all_checkbox = new JCheckBox("Project All");
	JCheckBox Make_sbs_show_in_all_slices_checkbox = new JCheckBox("Show in all slices");

	JCheckBox make_sbs_roimanager_get_from_current_image_checkbox = new JCheckBox("New from current image");
	JCheckBox Make_sbs_add_new_SBS_checkbox = new JCheckBox("Add SBS");
	JCheckBox Make_sbs_cut_entire_z_checkbox = new JCheckBox("Cut entire Z");
	JCheckBox Make_sbs_ask_for_zs_checkbox = new JCheckBox("Ask for Zs");
	JCheckBox Make_sbs_use_projection_checkbox = new JCheckBox("Use projection");
	JCheckBox Make_sbs_show_all_points_as_overlay_checkbox = new JCheckBox("Show all points as overlay");

	//declaring the labels 
	JLabel file_path_label = new JLabel("File Path: ");
	JLabel cut_sbs_roimanager_label = new JLabel("RoiManager file");

	JLabel Make_projection_save_path_label = new JLabel("Saving path: ");
	JLabel Make_projection_type_label = new JLabel("Projection type: ");

	JLabel Make_sbs_current_point_label = new JLabel("Working point: ");
	JLabel Makes_sbs_roisize_label = new JLabel("Square ROI side:");
	JLabel Make_sbs_cut_sbs_file_path_label = new JLabel("Saving SBS file path: ");

	//declaring the JText fields
	JTextField file_path_text_field = new JTextField(5);
	JTextField make_sbs_roimanager_text_field = new JTextField(5);
	JTextField Make_sbs_cut_sbs_file_path_textfield = new JTextField(5);

	JTextField Make_projection_save_textfield = new JTextField(5);

	//declaring the JSpinners
	JSpinner Make_Projection_low_spinner = new JSpinner();
	JSpinner Make_Projection_high_spinner = new JSpinner();

	JSpinner Make_sbs_square_roi_size_spinner = new JSpinner();

	//declaring the Jsliders
	JSlider Make_Projection_low_slider = new JSlider();
	JSlider Make_Projection_high_slider = new JSlider();

	//declare the string for the combo box
	String[] projection_types = {"max", "sum", "avg", "min","sd","median"};
	//declare the JComboBox
	JComboBox<String> Make_Projection_combobox = new JComboBox<String>(projection_types);

	//delcaring the Jlists
	JList<String> Make_sbs_working_rois_list = new JList<String>();
	
	//declaring the JScrollPane
	JScrollPane Make_sbs_working_rois_scrollpane = new JScrollPane(Make_sbs_working_rois_list);
		
	//#endregion
	/////////////////////////////////////////////////////////////////

	//#endregion


	//#region For the main guis
	/////////////////////////////////method to make the gui
	//creating a class to make the gui
	public void make_gui() {

		//setting up the minimum size of the jframe
		main_frame.setMinimumSize(new Dimension(500,100));
		//setting up the jframe size
		main_frame.setSize(500,100);

		//get the content pane
		contentPane = main_frame.getContentPane();

		//set the layout
		contentPane.setLayout(layout);

		//add them to the content pane
        contentPane.add(file_path_label);
        contentPane.add(file_path_text_field);
        contentPane.add(Select_button);
        contentPane.add(Open_button);

        contentPane.add(Classify_checkbox);
        contentPane.add(Make_sbs_checkbox);
		contentPane.add(Make_Projection_checkbox);

		//adjust the constraints of the label
        layout.putConstraint(SpringLayout.WEST, file_path_label, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, file_path_label, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, file_path_label, 0, SpringLayout.SOUTH, Select_button);
    
        //adjust the constraints of the text field
        layout.putConstraint(SpringLayout.WEST, file_path_text_field, 5, SpringLayout.EAST, file_path_label);
        layout.putConstraint(SpringLayout.NORTH, file_path_text_field, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, file_path_text_field, -5, SpringLayout.WEST, Select_button);
        layout.putConstraint(SpringLayout.SOUTH, file_path_text_field, 1, SpringLayout.SOUTH, Select_button);

        //adjust the constraints of the buttons
        //select
        layout.putConstraint(SpringLayout.EAST, Select_button, -5, SpringLayout.WEST, Open_button);
        layout.putConstraint(SpringLayout.NORTH, Select_button, 5, SpringLayout.NORTH, contentPane);
        
        //adjust the constraints of the checkboxes
        layout.putConstraint(SpringLayout.WEST, Classify_checkbox, 5, SpringLayout.EAST, Make_Projection_checkbox);
        layout.putConstraint(SpringLayout.NORTH, Classify_checkbox, 5, SpringLayout.SOUTH, Select_button);

        layout.putConstraint(SpringLayout.WEST, Make_sbs_checkbox, 5, SpringLayout.EAST, Classify_checkbox);
        layout.putConstraint(SpringLayout.NORTH, Make_sbs_checkbox, 5, SpringLayout.SOUTH, Select_button);

        //open
        layout.putConstraint(SpringLayout.NORTH, Open_button, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, Open_button, -5, SpringLayout.EAST, contentPane);


        //project
		layout.putConstraint(SpringLayout.WEST, Make_Projection_checkbox, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, Make_Projection_checkbox, 5, SpringLayout.SOUTH, Select_button);
    
		//set the dispose
		main_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//////////////////////////////////////add the listeners
		//get the window listeners and remove them
		WindowListener[] listeners = main_frame.getWindowListeners();
		for (int i = 0; i < listeners.length; i++) {
			main_frame.removeWindowListener(listeners[i]);
		}

		//add a lsitener to the jframe
		main_frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				
				//close all windows
				//get the titles
				String[] titles = WindowManager.getImageTitles();
				
				//close all the images
				for (int i = 0; i < titles.length; i++) {
					ImagePlus imp = WindowManager.getImage(titles[i]);
					if (imp != null) {
						imp.close();
					}
				}
				
				//close all the JFrames
				for (int i = 0; i < all_jframes_except_main.length; i++) {
					JFrame closing_frame = all_jframes_except_main[i];
					if (closing_frame != null) {
						closing_frame.dispose();
					}
						
				}
				
			}
		});

		//get and remove the listeners from the open button
		ActionListener[] open_button_listeners = Open_button.getActionListeners();
		for (int i = 0; i < open_button_listeners.length; i++) {
			Open_button.removeActionListener(open_button_listeners[i]);
		}
		////////////////////////////////// add the listeners
		//open image button
		Open_button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				//remove listeners
				remove_listeners();

				//add the listener
				ImagePlus.addImageListener(new_image_listener);

				//set the flush to false
				new_image_listener.setIgnoreFlush(false);

				//getting the path
				String opening_image_path = file_path_text_field.getText();
				
				//get the path into the global variable
				working_image_path_string = opening_image_path;

				//get the file (to work easy with the parents and so on)
				File open_file = new File(opening_image_path);

				//if the file exists, then proceed
				if (open_file.exists()) {
					
					//get the current image
					current_image = IJ.openImage(opening_image_path);
					
					//get the id
					main_image_id = current_image.getID();

					//show the image				
					current_image.show();
	
					//set the buttons on the panel to enabled
					Make_Projection_checkbox.setEnabled(true);
	
					//set the checkboxes to enables
					Make_Projection_checkbox.setEnabled(true);
					Classify_checkbox.setEnabled(true);
					Make_sbs_checkbox.setEnabled(true);
	
					//set the open image button to disables
					Open_button.setEnabled(false);	
					
					//set the JTextfield to disabled
					file_path_text_field.setEnabled(false);

					//set the select button to disabled
					Select_button.setEnabled(false);

				}
				else {
					IJ.log("The file does not exist, try again with a valid file.");
				}

			}

		});

		//get and remove the listeners form the select button
		ActionListener[] select_button_listeners = Select_button.getActionListeners();
		for (int i = 0; i < select_button_listeners.length; i++) {
			Select_button.removeActionListener(select_button_listeners[i]);
		}

		//add the action listeners to the buttons
		//Select button
		Select_button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				//create a file chooser
				JFileChooser file_chooser = new JFileChooser();
				
				//set the file chooser to only accept .tif files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TIF files", "tif");
				file_chooser.setFileFilter(filter);

				//open the file chooser
				int return_value = file_chooser.showOpenDialog(main_frame);
				
				//if the user selects a file
				if (return_value == JFileChooser.APPROVE_OPTION) {
					
					//get the file
					File selected_file = file_chooser.getSelectedFile();
					
					//get the file path
					String captured_path = selected_file.getAbsolutePath();
					
					//set the text field to the file path
					file_path_text_field.setText(captured_path);
				
				}
			}
		});

		//get and remove the listeners from the make projection checkbox
		ActionListener[] make_projection_checkbox_listeners = Make_Projection_checkbox.getActionListeners();
		for (int i = 0; i < make_projection_checkbox_listeners.length; i++) {
			Make_Projection_checkbox.removeActionListener(make_projection_checkbox_listeners[i]);
		}
		//make projection checkbox
		//get the make projection checkbox listener
		Make_Projection_checkbox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//if the checkbox is selected
				if (Make_Projection_checkbox.isSelected()) {

					//get the first and last slices
					int slice_number = current_image.getNSlices();	

					//make the gui
					make_projection_gui();

					//add a lsitener to the jframe
					projection_menu.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {

							//close the projected image
							if(projected_image != null){
								//set the ignore flush to true
								new_image_listener.setIgnoreFlush(false);
								projected_image.close();
							};
							
							//set the checkbox to fale
							Make_Projection_checkbox.setSelected(false);

						}
					});

					//get the new combo box listener
					Make_Projection_combobox_action_listener projection_combobox_listener = new Make_Projection_combobox_action_listener();

					//remove the action listeners from the combobox
					for (ActionListener al : Make_Projection_combobox.getActionListeners()) {
						Make_Projection_combobox.removeActionListener(al);
					}

					// add the listener to the combobox
					Make_Projection_combobox.addActionListener(projection_combobox_listener);

					//set the listener to project
					projection_combobox_listener.make_projection(true);

					///////////make the projection
					//get the selected item
					String selected_item_combobox = (String) Make_Projection_combobox.getSelectedItem();

					//make the projection image
					projected_image = ZProjector.run(current_image, selected_item_combobox, 1, slice_number);

					//get the image id
					projected_image_id = projected_image.getID();

					//show the projected image
					projected_image.show();

					//set the location
					projected_image.getWindow().setLocation(500,500);

					//now, create the spinner model
					SpinnerNumberModel spinner_model_low = new SpinnerNumberModel(1, 1, slice_number, 1);
					SpinnerNumberModel spinner_model_high = new SpinnerNumberModel(slice_number, 1, slice_number, 1);
					
					//now set the JSpinners to the model
					Make_Projection_low_spinner.setModel(spinner_model_low);
					Make_Projection_high_spinner.setModel(spinner_model_high);

					//make the models for the sliders
					Make_Projection_low_slider.setMinimum(1);
					Make_Projection_low_slider.setMaximum(slice_number);
					Make_Projection_low_slider.setValue(1);
					Make_Projection_low_slider.setMajorTickSpacing(1);
					Make_Projection_low_slider.setPaintTicks(true);
					Make_Projection_high_slider.setMinimum(1);
					Make_Projection_high_slider.setMaximum(slice_number);
					Make_Projection_high_slider.setValue(slice_number);
					Make_Projection_high_slider.setMajorTickSpacing(1);
					Make_Projection_high_slider.setPaintTicks(true);

					//adding the change listeners to the spinners
					//make the spinner low listener
					Projection_Spinner_low_listener low_spinner_listener = new Projection_Spinner_low_listener();
					//make the spinner high listener
					Projection_Spinner_high_listener high_spinner_listener = new Projection_Spinner_high_listener();
					
					//remove the previous listeners
					for (ChangeListener cl : Make_Projection_low_spinner.getChangeListeners()) {
						Make_Projection_low_spinner.removeChangeListener(cl);
					}
					for (ChangeListener cl : Make_Projection_high_spinner.getChangeListeners()) {
						Make_Projection_high_spinner.removeChangeListener(cl);
					}
					//add the low listener
					Make_Projection_low_spinner.addChangeListener(low_spinner_listener);
					//add the high listener
					Make_Projection_high_spinner.addChangeListener(high_spinner_listener);

					//now add the listener to the sliders
					//mke the low listener
					Projection_Slider_low_listener low_slider_listener = new Projection_Slider_low_listener();
					//make the high listener
					Projection_Slider_high_listener high_slider_listener = new Projection_Slider_high_listener();
					//remove the previous listeners
					for (ChangeListener cl : Make_Projection_low_slider.getChangeListeners()) {
						Make_Projection_low_slider.removeChangeListener(cl);
					}
					for (ChangeListener cl : Make_Projection_high_slider.getChangeListeners()) {
						Make_Projection_high_slider.removeChangeListener(cl);
					}					
					//add the low listener
					Make_Projection_low_slider.addChangeListener(low_slider_listener);
					//add the high listener
					Make_Projection_high_slider.addChangeListener(high_slider_listener);

					//create the new projection listener
					Projection_Checkbox_listener projection_checkbox_listener = new Projection_Checkbox_listener(slice_number);
					//remove previous listeners
					for (ActionListener al : Project_all_checkbox.getActionListeners()) {
						Project_all_checkbox.removeActionListener(al);
					}
					//add a listener to the checkbox
					Project_all_checkbox.addActionListener(projection_checkbox_listener);

					//enable the save projection button
					Save_Projection_button.setEnabled(true);

					//set the save path of the projection
					set_save_path_projection();

				}
				if (!Make_Projection_checkbox.isSelected()) {

					//close the projected image
					if(projected_image != null){
						
						//set the ignore flush to false
						new_image_listener.setIgnoreFlush(false);

						projected_image.close();

					};
					
					//dispose of the projection menu
					projection_menu.dispose();

					//get the combobox listener
					Make_Projection_combobox_action_listener projection_combobox_listener = new Make_Projection_combobox_action_listener();
					
					//remove the action listeners from the combobox
					for (ActionListener al : Make_Projection_combobox.getActionListeners()) {
						Make_Projection_combobox.removeActionListener(al);
					}

					//get it into the combobox
					Make_Projection_combobox.addActionListener(projection_combobox_listener);

					//set to not project
					projection_combobox_listener.make_projection(false);

					//reset the combo box
					Make_Projection_combobox.setSelectedIndex(0);

					//set the projection to true
					projection_combobox_listener.make_projection(true);

				}
			}
		});

		//get and remove the listeners from the Make_sbs_checkbox
		for (ActionListener al : Make_sbs_checkbox.getActionListeners()) {
			Make_sbs_checkbox.removeActionListener(al);
		}
		//add the listener to the cut checkbox
		Make_sbs_checkbox.addActionListener(new ActionListener() {
			
			//add the action event
			public void actionPerformed (ActionEvent e) {

				//if the checkbox is selected
				if (Make_sbs_checkbox.isSelected()) {

					//update the JList					

					//if it is selected, then restart the roi manager
					RoiManager roi_manager = new RoiManager();

					//get the instance of the roi manager
					roi_manager = RoiManager.getInstance();

					//reset the roi manager
					roi_manager.reset();

					//set multipoint tool 
					IJ.setTool("multipoint");

					//make the cut sbs menu
					make_make_sbs_menu_gui();

					//set the text field to empty
					make_sbs_roimanager_text_field.setText("");

					//get the listeners from the checkbox and remove them
					ActionListener[] listeners = make_sbs_roimanager_get_from_current_image_checkbox.getActionListeners();
					for (ActionListener listener : listeners) {
						make_sbs_roimanager_get_from_current_image_checkbox.removeActionListener(listener);
					}

					//add the listener to the cut sbs checkbox
					make_sbs_roimanager_get_from_current_image_checkbox.addActionListener(new ActionListener() {
						
						public void actionPerformed (ActionEvent e) {

							//reset the rois list
							//make_sbs_roi_list = new ArrayList<Roi>();

							//update the Jlist
							//get a new default model
							//DefaultListModel<String> loaded_list_model = new DefaultListModel<String>();
							
							//adding it to the JList
							//Make_sbs_working_rois_list.setModel(loaded_list_model);

							//log that a new model has started
							IJ.log("new model started");
							
							//if the checkbox is selected
							if (make_sbs_roimanager_get_from_current_image_checkbox.isSelected()) {
								
								//get the title of the roi manager save file
								String main_image_to_save_path = file_path_text_field.getText();

								//get the file (to work easy with the parents and so on)
								File main_image_to_save_file = new File(main_image_to_save_path);

								//get the no extension name of the file
								String main_image_to_save_file_no_extension = main_image_to_save_file.getName().substring(0, main_image_to_save_file.getName().lastIndexOf('.'));

								//get the parent
								String main_image_to_save_parent = main_image_to_save_file.getParent();

								//get the path to the roi manager save file
								String roi_manager_save_file_path = main_image_to_save_parent + "/" + main_image_to_save_file_no_extension + "_rois.zip";

								//set the text field
								make_sbs_roimanager_text_field.setText(roi_manager_save_file_path);

								//disable the load button
								make_sbs_roimanager_select_button.setEnabled(false);	
								
								//Check if the file exists
								File roi_manager_save_file = new File(roi_manager_save_file_path);

								//if the file exists
								if (roi_manager_save_file.exists()) {

									//print log to say that it already exists
									IJ.log("The roi manager save file already exists. Initiating a new one with a different filename.");

									//getting the date and time as strings
									DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

									//get the current date and time
									LocalDateTime now = LocalDateTime.now();

									//get the date and time as a string
									String date_and_time = dtf.format(now);
									
									//after getting the date and time, add it to the path
									roi_manager_save_file_path = main_image_to_save_parent + "/" + main_image_to_save_file_no_extension + "_date_and_time_" + date_and_time + "_rois.zip";

									//update the text field
									make_sbs_roimanager_text_field.setText(roi_manager_save_file_path);
									
								}

								//get the make sbs working image id
								//get the use projection checkbox state
								boolean use_projection = Make_sbs_use_projection_checkbox.isSelected();

								//log the use projection state
								IJ.log("use projection state: " + use_projection);

								//if it is not selected, get the current image id
								if (!use_projection) {

									//get the current image id
									int make_sbs_working_image_id = main_image_id;

								}
								//if it is selected, set the working image id to the projection
								else {

									//find out if the projected image exists
									boolean projection_exists = WindowManager.getImage(projected_image_id) != null;

									//if the projection exists, get the id
									if (projection_exists) {

										//get the current image id
										int make_sbs_working_image_id = projected_image_id;

									}

									else {

										//start the error method
										make_error_window("The projection image does not exist. Please make a projection first.");
										
										//deselect the add new 
										make_sbs_roimanager_get_from_current_image_checkbox.setSelected(false);

									}

								}
								

								//reset the roi list
								//make_sbs_prepare_rois_and_listeners();

								//get the image canvas from the main image
								//ImageCanvas main_image_canvas = main_image.getCanvas();
								
								//set the enabled to the gui
								Make_sbs_menu_enable_loaded_gui();

							}

							//if the checkbox is not selected
							if (!make_sbs_roimanager_get_from_current_image_checkbox.isSelected()) {

								//set the text field
								make_sbs_roimanager_text_field.setText("");

								//enable the load button
								make_sbs_roimanager_select_button.setEnabled(true);

								//set the enabled disabled to gui
								Make_sbs_menu_reset_gui();

							}

							
						}


					});
										
					//set the checkbox to selected
					make_sbs_roimanager_get_from_current_image_checkbox.setSelected(false);

					//remove action listeners from the load button
					for (ActionListener al : make_sbs_roimanager_select_button.getActionListeners()) {
						make_sbs_roimanager_select_button.removeActionListener(al);
					}

					//add the listener to the load button
					make_sbs_roimanager_select_button.addActionListener(new ActionListener() {
						
						public void actionPerformed (ActionEvent e) {

							//if it is selected, then restart the roi manager
							RoiManager roi_manager = new RoiManager();

							//get the isntance of the roi manager
							roi_manager = RoiManager.getInstance();

							//reset the roi manager
							roi_manager.reset();

							//if the roi list is more than 0, then reset it
							if (roi_list.size() > 0) {

								//reset the roi list
								roi_list.clear();

							}

							//create a file chooser
							JFileChooser file_chooser = new JFileChooser();

							//open the file chooser
							int return_value = file_chooser.showOpenDialog(make_sbs_menu);
							
							//if the user selects a file
							if (return_value == JFileChooser.APPROVE_OPTION) {
								
								//set the add from current checkbox to false
								make_sbs_roimanager_get_from_current_image_checkbox.setSelected(false);

								//get the file
								File selected_file = file_chooser.getSelectedFile();
								
								//get the file path
								String captured_path = selected_file.getAbsolutePath();
								
								//find out if the capture path is .zip
								String captured_path_extension = captured_path.substring(captured_path.lastIndexOf('.') + 1, captured_path.length());

								//if the extension is not zip then replace the extension with zip
								if (!captured_path_extension.equals("zip")) {
									
									//replace the extension with zip
									captured_path = captured_path.substring(0, captured_path.lastIndexOf('.')) + "_rois.zip";
									
								}

								//set the text field to the file path
								make_sbs_roimanager_text_field.setText(captured_path);

								//load the roi manager
								boolean roimanager_file_exists = make_sbs_load_roi_manager();

								//if the file was loaded, then validate it
								if (roimanager_file_exists) {

									//validate the roi manager
									boolean roi_coordinate_validation = make_sbs_validate_roi_manager();

									//print the validation
									IJ.log("Roi manager validation: " + roi_coordinate_validation);

									//if the validation is correct, then get the coordinates
									if (roi_coordinate_validation) {
										
										//get the working window
										Make_sbs_window_id = Make_sbs_get_working_image_id();

										//load the roi manager data into the roi_list
										//loop through the roi manager
										for (int i = 0; i < roi_manager.getCount(); i++) {

											//get the roi from the roi manager
											Roi roi = roi_manager.getRoi(i);

											//add the roi to the roi list
											roi_list.add(roi);

										}

										//update the jlist
										Make_sbs_update_jlist();

										//display the rois in the image
										Make_sbs_display_rois_in_image();

										//set the enabled
										Make_sbs_menu_enable_loaded_gui();

										//lock the image
										Make_sbs_roi_window_not_interactable(Make_sbs_window_id);

										//hide the roi manager
										roi_manager.setVisible(true);

									}
									
									//if it is not validated, get an error window with the error message
									else {
										
										//get the error message
										String error_message = "The roi manager file is not valid. Please check the file and try again or make a new file.";
										
										//get the error window
										make_error_window(error_message);

										//reset the roi manager
										roi_manager.reset();

										//reset the text field
										make_sbs_roimanager_text_field.setText("");

										//get gui to disabled
										Make_sbs_menu_reset_gui();
										
									}

									
								}


								
							
							}

						}

					});

				}

				//if the checkbox is not selected
				if (!Make_sbs_checkbox.isSelected()) {

					//close the frame
					make_sbs_menu.dispose();

					//disable the checkboxes
					Make_sbs_menu_reset_gui();
				

				}

			}

		});
		


        //display
        main_frame.pack();
        main_frame.setVisible(true);

	}
	
	//create a make projection menu gui
	public void make_projection_gui() {
		
		//set the minimum size
		projection_menu.setMinimumSize(new Dimension(300,300));

		//set the jfame size
		projection_menu.setSize(new Dimension(300,300));
		
		//declare a container
		Container projection_menu_content_pane = projection_menu.getContentPane();

		//set the layout
		SpringLayout projection_menu_layout = new SpringLayout();

		//set the container layout
		projection_menu_content_pane.setLayout(projection_menu_layout);

		//add the checkbox
		projection_menu_content_pane.add(Project_all_checkbox);

		//add the two Jspinners
		projection_menu_content_pane.add(Make_Projection_low_spinner);
		projection_menu_content_pane.add(Make_Projection_high_spinner);	

		//add the two sliders
		projection_menu_content_pane.add(Make_Projection_low_slider);
		projection_menu_content_pane.add(Make_Projection_high_slider);

		//add the JComboBox
		projection_menu_content_pane.add(Make_Projection_combobox);

		//add the JButtons
		//the save projection button
		projection_menu_content_pane.add(Save_Projection_button);

		//add the text fields
		//the save projection text field
		projection_menu_content_pane.add(Make_projection_save_textfield);

		//add the Jlabels
		//the save projection label
		projection_menu_content_pane.add(Make_projection_save_path_label);
		//the type label
		projection_menu_content_pane.add(Make_projection_type_label);

		//set the layout constraints
		//for the checkbox
		projection_menu_layout.putConstraint(SpringLayout.WEST, 
											Project_all_checkbox,
											5, 
											SpringLayout.WEST, 
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Project_all_checkbox,
											5,
											SpringLayout.NORTH,
											projection_menu_content_pane);
		//for the low spiner and slider
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_Projection_low_spinner,
											5,
											SpringLayout.WEST,
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_Projection_low_spinner,
											5,
											SpringLayout.SOUTH,
											Project_all_checkbox);
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_Projection_low_slider,
											5,
											SpringLayout.EAST,
											Make_Projection_low_spinner);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_Projection_low_slider,
											5,
											SpringLayout.SOUTH,
											Project_all_checkbox);
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Make_Projection_low_slider,
											-5,
											SpringLayout.WEST,
											Make_Projection_high_spinner);

		//for the high spiner and slider
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Make_Projection_high_spinner,
											-5,
											SpringLayout.EAST,
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_Projection_high_spinner,
											5,
											SpringLayout.SOUTH,
											Make_Projection_low_slider);	
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Make_Projection_high_slider,
											5,
											SpringLayout.EAST,
											Make_Projection_low_slider);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_Projection_high_slider,
											5,
											SpringLayout.SOUTH,
											Make_Projection_low_slider);
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_Projection_high_slider,
											5,
											SpringLayout.EAST,
											Make_Projection_low_spinner);
		
		//set the combobox
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_Projection_combobox,
											5,
											SpringLayout.EAST,
											Make_projection_type_label);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_Projection_combobox,
											5,
											SpringLayout.SOUTH,
											Make_Projection_high_slider);
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Make_Projection_combobox,
											-5,
											SpringLayout.EAST,
											projection_menu_content_pane);
		
		/////////////////////////////////for the JTextFields
		//for the save projection text field
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_projection_save_textfield,
											5,
											SpringLayout.EAST,
											Make_projection_save_path_label);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_projection_save_textfield,
											5,
											SpringLayout.SOUTH,
											Make_Projection_combobox);
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Make_projection_save_textfield,
											-5,
											SpringLayout.EAST,
											projection_menu_content_pane);

		//////////////////////////////////for the JButtons
		//for the save projection button
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Save_Projection_button,
											5,
											SpringLayout.WEST,
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Save_Projection_button,
											5,
											SpringLayout.SOUTH,
											Make_projection_save_textfield);
		projection_menu_layout.putConstraint(SpringLayout.EAST,
											Save_Projection_button,
											-5,
											SpringLayout.EAST,
											projection_menu_content_pane);

		//////////////////////////////////for the Jlabels
		//for the save projection label
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_projection_save_path_label,
											5,
											SpringLayout.WEST,
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_projection_save_path_label,
											5,
											SpringLayout.SOUTH,
											Make_Projection_combobox);
		//for the type label
		projection_menu_layout.putConstraint(SpringLayout.WEST,
											Make_projection_type_label,
											5,
											SpringLayout.WEST,
											projection_menu_content_pane);
		projection_menu_layout.putConstraint(SpringLayout.NORTH,
											Make_projection_type_label,
											5,
											SpringLayout.SOUTH,
											Make_Projection_high_slider);
		projection_menu_layout.putConstraint(SpringLayout.SOUTH,
											Make_projection_type_label,
											0,
											SpringLayout.SOUTH,
											Make_Projection_combobox);

		//////////////////////////////add liustener to component combobox
		//get the listener for the combobox
		Make_Projection_combobox_action_listener combobox_action_listener = new Make_Projection_combobox_action_listener();

		//set the make projection to true
		combobox_action_listener.make_projection(true);

		//set the save path
		set_save_path_projection();

		//set the dispose
		projection_menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/////////////////////////////////// add the listeners
		//Save projection button
		Save_Projection_button.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {

				//get the save path
				String saving_image_path = Make_projection_save_textfield.getText();

				//get the file
				File saving_file = new File(saving_image_path);

				//if the file exists
				if(saving_file.exists()) {

					//ask if the user wants to overwrite
					int overwrite = JOptionPane.showConfirmDialog(projection_menu, "The file already exists. Do you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_OPTION);

					//if the user does not want to overwrite
					if(overwrite == JOptionPane.NO_OPTION) {

						//return
						return;

					}

					//if the user wants to overwrite
					else {

						//delete the file
						saving_file.delete();

						//save the image
						IJ.saveAs(projected_image, "Tiff", saving_image_path);

					}

				}

				//if the file doesn't exist
				else {

					//save the image
					IJ.saveAs(projected_image, "Tiff", saving_image_path);

				}
		

			}

		});

		//pack
		projection_menu.pack();
		projection_menu.setVisible(true);

	}

	//create a cut sbs menu gui
	public void make_make_sbs_menu_gui() {

		//set the minimum size
		make_sbs_menu.setMinimumSize(new Dimension(600,450));

		//set the jfame size
		make_sbs_menu.setSize(new Dimension(600,450));

		//declare the new layout
		SpringLayout make_sbs_menu_layout = new SpringLayout();

		//set the content pane layout
		make_sbs_menu_content_pane.setLayout(make_sbs_menu_layout);

		//add the label
		make_sbs_menu_content_pane.add(cut_sbs_roimanager_label);
		make_sbs_menu_content_pane.add(Makes_sbs_roisize_label);
		make_sbs_menu_content_pane.add(Make_sbs_cut_sbs_file_path_label);

		//add the JTextField
		make_sbs_menu_content_pane.add(make_sbs_roimanager_text_field);
		make_sbs_menu_content_pane.add(Make_sbs_cut_sbs_file_path_textfield);

		//add the JButtons
		make_sbs_menu_content_pane.add(make_sbs_roimanager_select_button);
		make_sbs_menu_content_pane.add(make_sbs_roimanager_save_button);
		make_sbs_menu_content_pane.add(make_sbs_roimanager_delete_sbs_button);
		make_sbs_menu_content_pane.add(make_sbs_roimanager_cut_selected_sbs_button);
		make_sbs_menu_content_pane.add(make_sbs_roimanager_cut_all_sbs_button);
		make_sbs_menu_content_pane.add(Make_sbs_roimanager_make_square_roi_button);
		make_sbs_menu_content_pane.add(Make_sbs_roimanager_make_square_all_button);
		make_sbs_menu_content_pane.add(Make_sbs_roimanager_deselect_sbs_button);	
		make_sbs_menu_content_pane.add(Make_sbs_show_in_all_slices_checkbox);

		//add the checkbox
		make_sbs_menu_content_pane.add(make_sbs_roimanager_get_from_current_image_checkbox);
		make_sbs_menu_content_pane.add(Make_sbs_add_new_SBS_checkbox);
		make_sbs_menu_content_pane.add(Make_sbs_cut_entire_z_checkbox);
		make_sbs_menu_content_pane.add(Make_sbs_ask_for_zs_checkbox);
		make_sbs_menu_content_pane.add(Make_sbs_use_projection_checkbox);
		make_sbs_menu_content_pane.add(Make_sbs_show_all_points_as_overlay_checkbox);

		//adding the JList
		//make_sbs_menu_content_pane.add(Make_sbs_working_rois_list);

		//adding the JSpinner
		make_sbs_menu_content_pane.add(Make_sbs_square_roi_size_spinner);

		//adding the scrollpane
		make_sbs_menu_content_pane.add(Make_sbs_working_rois_scrollpane);

		//get the current image
		ImagePlus imp = WindowManager.getCurrentImage();
	
		//get the image size
		int image_width = imp.getWidth();
		int image_height = imp.getHeight();

		//get the max of those two
		int max = Math.max(image_width, image_height);

		//get a new model for the spinner
		SpinnerNumberModel roi_size_model = new SpinnerNumberModel(100, 1, max, 1);

		//add the spinner model
		Make_sbs_square_roi_size_spinner.setModel(roi_size_model);

		//set the ask for zs to false
		Make_sbs_ask_for_zs_checkbox.setSelected(false);

		//set the enabled to false
		Make_sbs_ask_for_zs_checkbox.setEnabled(false);

		//reset the gui
		Make_sbs_menu_reset_gui();

		//add the layout constraints
		//for the label
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										cut_sbs_roimanager_label,
										5,
										SpringLayout.WEST,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										cut_sbs_roimanager_label,
										5,
										SpringLayout.NORTH,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										cut_sbs_roimanager_label,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_text_field);
		//now for the label of the defualt size
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Makes_sbs_roisize_label,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Makes_sbs_roisize_label,
										5,
										SpringLayout.SOUTH,
										Make_sbs_add_new_SBS_checkbox);
		//for the cut sbs file path label
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_cut_sbs_file_path_label,
										5,
										SpringLayout.WEST,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										Make_sbs_cut_sbs_file_path_label,
										-5,
										SpringLayout.SOUTH,
										make_sbs_menu_content_pane);
		/////////text fields
		//for the text field
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_text_field,
										5,
										SpringLayout.EAST,
										cut_sbs_roimanager_label);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_text_field,
										5,
										SpringLayout.NORTH,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_text_field,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the text field of the cut path
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_cut_sbs_file_path_textfield,
										5,
										SpringLayout.EAST,
										Make_sbs_cut_sbs_file_path_label);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										Make_sbs_cut_sbs_file_path_textfield,
										-5,
										SpringLayout.SOUTH,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_cut_sbs_file_path_textfield,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
	

		//for the checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_get_from_current_image_checkbox,
										5,
										SpringLayout.WEST,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_get_from_current_image_checkbox,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_text_field);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										make_sbs_roimanager_get_from_current_image_checkbox,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_select_button);
		
		//for the load button
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_select_button,
										-5,
										SpringLayout.WEST,
										make_sbs_roimanager_save_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_select_button,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_text_field);
		//for the save button
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_save_button,
										-5,
										SpringLayout.WEST,
										Make_sbs_use_projection_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_save_button,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_text_field);
		//for the delete sbs button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_delete_sbs_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_delete_sbs_button,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_cut_all_sbs_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_delete_sbs_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the make square button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_roimanager_make_square_roi_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_roimanager_make_square_roi_button,			
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_delete_sbs_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_roimanager_make_square_roi_button,		
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the make all square button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_roimanager_make_square_all_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_roimanager_make_square_all_button,
										5,
										SpringLayout.SOUTH,
										Make_sbs_roimanager_make_square_roi_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_roimanager_make_square_all_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the cut all button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_cut_all_sbs_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_cut_all_sbs_button,
										5,
										SpringLayout.SOUTH,
										Make_sbs_roimanager_make_square_roi_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_cut_all_sbs_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		
										//for the current selected cut button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_cut_selected_sbs_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_cut_selected_sbs_button,
										5,
										SpringLayout.SOUTH,
										Make_sbs_cut_entire_z_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_cut_selected_sbs_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the cut all button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										make_sbs_roimanager_cut_all_sbs_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										make_sbs_roimanager_cut_all_sbs_button,
										5,
										SpringLayout.SOUTH,
										make_sbs_roimanager_cut_selected_sbs_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										make_sbs_roimanager_cut_all_sbs_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the deselect button
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_roimanager_deselect_sbs_button,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_roimanager_deselect_sbs_button,
										5,
										SpringLayout.SOUTH,
										Make_sbs_roimanager_make_square_all_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_roimanager_deselect_sbs_button,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);

		//for the JScroll pane
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_working_rois_scrollpane,
										5,
										SpringLayout.WEST,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_working_rois_scrollpane,
										20,
										SpringLayout.SOUTH,
										make_sbs_roimanager_save_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane,
										0,
										SpringLayout.EAST,
										make_sbs_roimanager_select_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										Make_sbs_working_rois_scrollpane,
										-5,
										SpringLayout.NORTH,
										Make_sbs_cut_sbs_file_path_textfield);
		//////////////checkboxes
		//for the checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_add_new_SBS_checkbox,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_add_new_SBS_checkbox,
										5,
										SpringLayout.SOUTH,
										Make_sbs_show_all_points_as_overlay_checkbox);
		//for the cut entire checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_cut_entire_z_checkbox,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_cut_entire_z_checkbox,
										5,
										SpringLayout.SOUTH,
										Make_sbs_square_roi_size_spinner);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_cut_entire_z_checkbox,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the get zs checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_ask_for_zs_checkbox,
										5,
										SpringLayout.EAST,
										Make_sbs_add_new_SBS_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_ask_for_zs_checkbox,
										0,
										SpringLayout.NORTH,
										Make_sbs_add_new_SBS_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										Make_sbs_ask_for_zs_checkbox,
										0,
										SpringLayout.SOUTH,
										Make_sbs_add_new_SBS_checkbox);
		// for the use projection checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_use_projection_checkbox,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_use_projection_checkbox,
										0,
										SpringLayout.NORTH,
										make_sbs_roimanager_save_button);
		make_sbs_menu_layout.putConstraint(SpringLayout.SOUTH,
										Make_sbs_use_projection_checkbox,
										0,
										SpringLayout.SOUTH,
										make_sbs_roimanager_save_button);
		//for the show in all slices checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_show_in_all_slices_checkbox,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_show_in_all_slices_checkbox,
										0,
										SpringLayout.NORTH,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,	
										Make_sbs_show_in_all_slices_checkbox,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);
		//for the overlay all points checkbox
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_show_all_points_as_overlay_checkbox,
										5,
										SpringLayout.EAST,
										Make_sbs_working_rois_scrollpane);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,
										Make_sbs_show_all_points_as_overlay_checkbox,
										5,
										SpringLayout.SOUTH,
										Make_sbs_show_in_all_slices_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_show_all_points_as_overlay_checkbox,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);

		//set the spinner position
		make_sbs_menu_layout.putConstraint(SpringLayout.WEST,
										Make_sbs_square_roi_size_spinner,
										5,
										SpringLayout.EAST,
										Makes_sbs_roisize_label);
		make_sbs_menu_layout.putConstraint(SpringLayout.NORTH,	
										Make_sbs_square_roi_size_spinner,
										5,
										SpringLayout.SOUTH,
										Make_sbs_add_new_SBS_checkbox);
		make_sbs_menu_layout.putConstraint(SpringLayout.EAST,
										Make_sbs_square_roi_size_spinner,
										-5,
										SpringLayout.EAST,
										make_sbs_menu_content_pane);


		//add and remove the make_sbs_menu listners
		//get the listeners
		WindowListener[] make_sbs_menu_listeners = make_sbs_menu.getWindowListeners();
		//remove the listeners
		for (int i = 0; i < make_sbs_menu_listeners.length; i++) {
			make_sbs_menu.removeWindowListener(make_sbs_menu_listeners[i]);
		}
		/////////////////////////////////////listeners
		//add an action listener to the frame
		make_sbs_menu.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//if the frame is closed, then unselect the checkbox
				Make_sbs_checkbox.setSelected(false);
				//also, set the Make_sbs_add_new
				Make_sbs_add_new_SBS_checkbox.setSelected(false);
				//also reset the Jlist
				//get a new list model
				DefaultListModel<String> reset_list_model = new DefaultListModel<String>();
				//set the list model
				Make_sbs_working_rois_list.setModel(reset_list_model);
				//reset the rois list
				roi_list = new ArrayList<Roi>();

				//reset the ui
				Make_sbs_menu_reset_gui();

			}
		});
		
		//get the listeners for the Make_sbs_add_new_SBS_checkbox
		ActionListener[] Make_sbs_add_new_SBS_checkbox_listeners = Make_sbs_add_new_SBS_checkbox.getActionListeners();
		//remove the listeners
		for (int i = 0; i < Make_sbs_add_new_SBS_checkbox_listeners.length; i++) {
			Make_sbs_add_new_SBS_checkbox.removeActionListener(Make_sbs_add_new_SBS_checkbox_listeners[i]);
		}
		//for the add new sbs checkbox
		Make_sbs_add_new_SBS_checkbox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				//if the checkbox is selected, set the variable to true
				if (Make_sbs_add_new_SBS_checkbox.isSelected()) {
					
					//reset the selection on the JList
					Make_sbs_working_rois_list.clearSelection();

					//set the ask for zs checkbox to enabled
					Make_sbs_ask_for_zs_checkbox.setEnabled(true);

					//get the working image id
					int working_image_id = Make_sbs_get_working_image_id();

					//set the image to interactable
					Make_sbs_roi_window_interactable(working_image_id);

					//update the image with the rios needed
					Make_sbs_display_rois_in_image();

					//run a method to make the window listener to add the points to the list
					AddWindowListenerNewSBS();

				}
				//if the checkbox is not selected, set the variable to false
				else {

					//set the ask for zs checkbox to disabled
					Make_sbs_ask_for_zs_checkbox.setEnabled(false);

					//set the ask for zs checkbox to false
					Make_sbs_ask_for_zs_checkbox.setSelected(false);
				
				}
			}
		});

		//get the listeners for the Jlist 
		ListSelectionListener[] Make_sbs_working_rois_list_listeners = Make_sbs_working_rois_list.getListSelectionListeners();
		//remove the listeners
		for (int i = 0; i < Make_sbs_working_rois_list_listeners.length; i++) {
			Make_sbs_working_rois_list.removeListSelectionListener(Make_sbs_working_rois_list_listeners[i]);
		}

		//make a new list selection listener
		Make_sbs_list_selection_listener Make_sbs_working_rois_list_selection_listener = new Make_sbs_list_selection_listener();

		//add a list selection listener
		Make_sbs_working_rois_list.addListSelectionListener(Make_sbs_working_rois_list_selection_listener);

		//set the dispose
		make_sbs_menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//get the listener for the show all points checkbox
		ActionListener[] Make_sbs_show_in_all_slices_checkbox_listeners = Make_sbs_show_in_all_slices_checkbox.getActionListeners(); 
		//remove the listeners
		for (int i = 0; i < Make_sbs_show_in_all_slices_checkbox_listeners.length; i++) {
			Make_sbs_show_in_all_slices_checkbox.removeActionListener(Make_sbs_show_in_all_slices_checkbox_listeners[i]);
		}
		//add a new action listener
		Make_sbs_show_in_all_slices_checkbox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//get the variable to pass it later
				boolean show_all_points = Make_sbs_show_in_all_slices_checkbox.isSelected();
				
				//set the variable using the method
				setShowAllSlices(show_all_points);

			}
		});

		//get the listeners for the Make sbs overlay
		ActionListener[] Make_sbs_show_all_points_as_overlay_listeners = Make_sbs_show_all_points_as_overlay_checkbox.getActionListeners();
		//remove the listeners
		for (int i = 0; i < Make_sbs_show_all_points_as_overlay_listeners.length; i++) {
			Make_sbs_show_all_points_as_overlay_checkbox.removeActionListener(Make_sbs_show_all_points_as_overlay_listeners[i]);
		}
		//add a new action listener
		Make_sbs_show_all_points_as_overlay_checkbox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				//display the images, it will take care of it in the method.
				Make_sbs_display_rois_in_image();

			}

		});


		//pack
		make_sbs_menu.pack();
		make_sbs_menu.setVisible(true);

	}

	//#endregion
	
	
	//#region methods

	//making the load roi manager method
	public boolean make_sbs_load_roi_manager() {

		//set the variable of answer
		boolean roi_manager_loaded = false;

		//first, reset the roi manager
		RoiManager roi_manager = RoiManager.getInstance();
		roi_manager.reset();

		//first, get the path from the text field
		String roi_manager_path = make_sbs_roimanager_text_field.getText();

		//find out if the file exists
		File roi_manager_file = new File(roi_manager_path);

		//if the file exists, load it
		if (roi_manager_file.exists()) {

			//open the file
			roi_manager.runCommand("Open", roi_manager_path);

			//set the roi manager loaded flag to true
			roi_manager_loaded = true;

		} else {

			//if the file doesn't exist, tell the user
			IJ.showMessage("The roi manager you selected doesn't exist");
			//print that a new one will be initiated
			IJ.log("A new roi manager will be initiated");

			//sett he flag to false
			roi_manager_loaded = false;

		}

		//return the flag
		return roi_manager_loaded;

	}

	//validate the roi manager
	public boolean make_sbs_validate_roi_manager() {

		//get the roi manager count
		RoiManager roi_manager = RoiManager.getInstance();
		
		//get the number of rois
		int roi_manager_count = roi_manager.getCount();

		//make a boolean to return
		boolean roi_manager_valid = false;

		//get the return type
		//ArrayList<float[]> roi_coordinates = new ArrayList<float[]>();

		//if the roi manager count is more than 0, then get the first roi 
		//and get the roi type
		if (roi_manager_count > 1) {

			//get the title of the first roi
			String first_roi_title = roi_manager.getRoi(0).getName();

			//get the roitype of the first roi
			int first_roi_type = roi_manager.getRoi(0).getType();

			//if it is of type point
			if (first_roi_type == Roi.POINT) {
				
				//compare it to the sbs_coordinates string
				if (first_roi_title.equals("sbs_coordinates")) {

					//if it's the same, then it's a valid roi manager
					roi_manager_valid = true;

				} else {

					//if it's not the same, then it's not a valid roi manager
					roi_manager_valid = false;

				}

		 	} 			
					
		} 
		
		//if the roi manager is 1 or less, then return false
		if (roi_manager_count <= 1) {
			
			roi_manager_valid = false;
			
		}

		return roi_manager_valid;


	}

	//get the coordinates from the roi manager
	public ArrayList<float[]> make_sbs_get_roi_coordinates() {

		//get the roi manager instance
		RoiManager roi_manager = RoiManager.getInstance();

		//get the coordinates from the first roi
		float[] roi_x_coordinates = roi_manager.getRoi(0).getFloatPolygon().xpoints;
		float[] roi_y_coordinates = roi_manager.getRoi(0).getFloatPolygon().ypoints;

		//get the array list with x and y coordinates
		ArrayList<float[]> roi_coordinates = new ArrayList<float[]>();
		roi_coordinates.add(roi_x_coordinates);
		roi_coordinates.add(roi_y_coordinates);

		//retunr the array list
		return roi_coordinates;

	}

	//making the class to populate the make sbs jlist
	public DefaultListModel<String> make_sbs_populate_jlist(ArrayList<Roi> roi_list) {

		//making a new DefaultListModel
		DefaultListModel<String> roi_list_model = new DefaultListModel<String>();

		//looping through the roi list to get the roi names
		for (int i = 0; i < roi_list.size(); i++) {

			//get the roi name
			String roi_name = roi_list.get(i).getName();

			//add the roi name to the model
			roi_list_model.addElement(roi_name);

		}

		//returning the model
		return roi_list_model;

	}

	//get a new class to hold the image listeners
	public class ImageListenerFlush implements ImageListener {

		private boolean ignore_flush = false;

		public void setIgnoreFlush(boolean ignore_flush) {
			this.ignore_flush = ignore_flush;
		}

		public void imageOpened(ImagePlus imp) {
			IJ.log("imageOpened: " + imp.getTitle());
		}

		public void imageClosed(ImagePlus imp) {
			
			if (ignore_flush) {
				return;
			}
			if (!ignore_flush) {
				
				//get the image id
				int image_id = imp.getID();

				//if the title is the same as the projected image
				if(image_id == projected_image_id){

					//enable the make projection button
					Make_Projection_checkbox.setSelected(false);

					//print that you closed the image
					IJ.log("You have closed the projected image");

					//dispose of the JFrame 
					projection_menu.dispose();		

				};
		
				//if the title is the same as the main image
				if(image_id == main_image_id){
					
					IJ.log("You have closed the main image");
					//set the open image button to enables
					Open_button.setEnabled(true);
					//set the make projection button to disabled
					Make_Projection_checkbox.setEnabled(false);
					//deselect it
					Make_Projection_checkbox.setSelected(false);
					//set the save projection button to disabled
					Save_Projection_button.setEnabled(false);
					//set the cut sbs checkbox to disabled
					Make_sbs_checkbox.setEnabled(false);
					//deselect it
					Make_sbs_checkbox.setSelected(false);
					//set the classify sbs checkbox to disabled
					Classify_checkbox.setEnabled(false);
					//deselect it
					Classify_checkbox.setSelected(false);
					//set the text field to the default
					file_path_text_field.setText(default_working_image_path_string);
					image_opened = false;

					//set the text field to enabled
					file_path_text_field.setEnabled(true);

					//set the select file button to enabled
					Select_button.setEnabled(true);

					//close all the JFrames
					for (int i = 0; i < all_jframes_except_main.length; i++) {
						JFrame closing_frame = all_jframes_except_main[i];
						if (closing_frame != null) {
							closing_frame.dispose();
						}
							
					}
				
				};
				
			}

			
		}

			
		public void imageUpdated(ImagePlus imp) {
			return;
		}




	}

	/*
	//get a new class to make a new roilistener
	public class RoiListenerFlush implements RoiListener {

		private boolean ignore_modification = false;

		public void setIgnoreFlush(boolean ignore_modification) {
			this.ignore_modification = ignore_modification;
		}

		public void roiModified(ImagePlus imp, int id) {
			

			
		}

		public void roiRemoved(ImagePlus imp, int id) {
						//log that a roi has been removed
						IJ.log("roiRemoved: " + imp.getTitle());
		}

		public void roiAdded(ImagePlus imp, int id) {
			//log that a roi has been added
			IJ.log("roiAdded: " + imp.getTitle());
		}

		public void roiSelected(ImagePlus imp, int id) {
			//log that is has been selected
			IJ.log("roiSelected: " + imp.getTitle());
		}

	}

	*/

	//make a class to hold roi properties for this implementation of making sbs
	public class SBS_ROI_Properties {

		//set a square present flag
		private boolean square_present = false;

		//set a z1 position integer
		private int z1_position = 0;

		//set a z2 position integer
		private int z2_position = 0;

		//set up a new list to hold the bool and int values
		private ArrayList<Object> roi_properties = new ArrayList<Object>();

		//now get a method to get a property array and transform it into the variables
		public void read_roi_properties(String[] roi_properties) {
			
			//split a string after a specific character
			String[] split_square_string = roi_properties[0].split(":");

			//cast the second string to an integer
			this.square_present = Boolean.parseBoolean(split_square_string[1]);
			this.roi_properties.add(square_present);

			//split a string after a specific character
			String[] split_z1 = roi_properties[1].split(":");

			//cast the second string to an integer
			this.z1_position = Integer.parseInt(split_z1[1]);
			this.roi_properties.add(z1_position);


			//split a string after a specific character
			String[] split_z2 = roi_properties[2].split(":");

			//cast the second string to an integer
			this.z2_position = Integer.parseInt(split_z2[1]);
			this.roi_properties.add(z2_position);

		}

		//make the getters and setters for variables square_present, z1_position, z2_position
		public boolean isSquare_present() {
			return square_present;
		}

		public void setSquare_present(boolean square_present) {
			this.square_present = square_present;
		}

		public int getZ1_position() {
			return z1_position;
		}

		public void setZ1_position(int z1_position) {
			this.z1_position = z1_position;
		}

		public int getZ2_position() {
			return z2_position;
		}

		public void setZ2_position(int z2_position) {
			this.z2_position = z2_position;
		}

		public Object getProperty(int index) {

			return this.roi_properties.get(index);

		}


	}

	////////////////////////method to remove listeners
	//class to remove listeners
	public void remove_listeners() {
		
		//remove the 


		Vector listeners = ImagePlus.getListeners();		
		
		//get the size of the listeners
		int listen_size = listeners.size();
		
		if(listen_size > 0){
			
			IJ.log("The size of the listeners is: " + listen_size);
		
			for (int i = 0; i < listen_size; i++) {
		
				ImageListener current_listen = (ImageListener) listeners.get(i);
		
				ImagePlus.removeImageListener(current_listen);
		
			}
			
		};
		
	}

	//////////////////////// make an action listener for the checkbox
	public class Projection_Checkbox_listener implements ActionListener {

		private int slicesNumber;

		public Projection_Checkbox_listener(int slicesNumber) {
			this.slicesNumber = slicesNumber;
		}

		public void actionPerformed(ActionEvent e) {
			
			//get the source
			Object source = e.getSource();
			
			//if the source is the project all checkbox
			if(source == Project_all_checkbox){
			
				//if the checkbox is selected
				if(Project_all_checkbox.isSelected()){
			
					//set the max projection slider to the max value
					Make_Projection_high_slider.setValue(slicesNumber);
			
					//set the max projection spinner to the max value
					Make_Projection_high_spinner.setValue(slicesNumber);
			
					//disable the slider
					Make_Projection_high_slider.setEnabled(false);
			
					//disable the spinner
					Make_Projection_high_spinner.setEnabled(false);
			
					//set the min projection slider to the min value
					Make_Projection_low_slider.setValue(1);
			
					//set the min projection spinner to the min value
					Make_Projection_low_spinner.setValue(1);
			
					//disable the min projection slider
					Make_Projection_low_slider.setEnabled(false);
			
					//disable the min projection spinner
					Make_Projection_low_spinner.setEnabled(false);

					//get the selected item
					String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();
					
					

					//if the projected image exists
					if(projected_image != null){
					
						//set the ignore flush to true
						new_image_listener.setIgnoreFlush(true);
					
						//close the projected image
						projected_image.close();
					
						new_image_listener.setIgnoreFlush(false);

					}

					//make the projection image
					projected_image = ZProjector.run(current_image, combobox_selected_item);
				
					//get the image id
					projected_image_id = projected_image.getID();
				
					//show the projected image
					projected_image.show();
				
					//set the location
					projected_image.getWindow().setLocation(500,500);
					
					//print the ij log
					IJ.log("You have projected all the slices");

					//set the save path of the projection
					set_save_path_projection();

				}else{
					//enable the slider
					Make_Projection_high_slider.setEnabled(true);
					//enable the spinner
					Make_Projection_high_spinner.setEnabled(true);
					//enable the min projection slider
					Make_Projection_low_slider.setEnabled(true);
					//enable the min projection spinner
					Make_Projection_low_spinner.setEnabled(true);
					//set the save path of the projection
					set_save_path_projection();
				};
			};
		};


	}

	//////////////////////// make an action listener for the sliders
	public class Projection_Slider_high_listener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			
			//get the source
			JSlider this_slider = (JSlider) e.getSource();

			if(!this_slider.getValueIsAdjusting()) {

				//get the value of the slider
				int high_slider_value = this_slider.getValue();
				//get the value from the low_slider
				int low_slider_value = Make_Projection_low_slider.getValue();		

				//get the spinner values 
				int high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				int low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();

				//print the values right now
				IJ.log("The high slider value is " + high_slider_value);
				IJ.log("The low slider value is " + low_slider_value);
				IJ.log("The high spinner value is " + high_spinner_value);
				IJ.log("The low spinner value is " + low_spinner_value);
				
				//if the higher slider is lower than the lower slider
				if(high_slider_value < low_slider_value){
					//print the log
					IJ.log("The high slider is lower than the low slider");
					//set the lower slider to the higher slider value
					Make_Projection_low_slider.setValue(high_slider_value);
					//print the log
					IJ.log("The low slider is now set to:"+high_slider_value);
					//print that this will activate the low slider listener
					IJ.log("This will activate the low slider listener");
				};
				
				//get the source slider numbers
				high_slider_value = Make_Projection_high_slider.getValue();
				low_slider_value = Make_Projection_low_slider.getValue();

				//get the spinner values 
				high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();

				//set the spinner values
				if (high_slider_value != high_spinner_value) {
					//print hte log
					IJ.log("The high slider value is not equal to the high spinner value");
					//set the high spinner to the high slider value
					Make_Projection_high_spinner.setValue(high_slider_value);
					//print the log
					IJ.log("The high spinner is now set to:"+high_slider_value);
					//print that this will activate the high spinner listener
					IJ.log("This will activate the high spinner listener");
				}
				if (low_slider_value != low_spinner_value) {
					//print the log
					IJ.log("The low slider value is not equal to the low spinner value");
					//set the low spinner to the low slider value
					Make_Projection_low_spinner.setValue(low_slider_value);
					//print the log
					IJ.log("The low spinner is now set to:"+low_slider_value);
					//print that this will activate the low spinner listener
					IJ.log("This will activate the low spinner listener");
				}

				//get the source slider numbers
				high_slider_value = Make_Projection_high_slider.getValue();
				low_slider_value = Make_Projection_low_slider.getValue();

				//get the spinner values 
				high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();
				
				//close the projected image
				if(projected_image != null){
					
					//set to ignore flush
					new_image_listener.setIgnoreFlush(true);
			
					projected_image.close();

					//set to not ignore the flush
					new_image_listener.setIgnoreFlush(false);
				};
				
				//get the selected item
				String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();

				//make the projection image
				projected_image = ZProjector.run(current_image, combobox_selected_item, low_spinner_value, high_spinner_value);
				//get the image id
				projected_image_id = projected_image.getID();
				//show the projected image
				projected_image.show();
				//set the location
				projected_image.getWindow().setLocation(500,500);

				//set the save path of the projection
				set_save_path_projection();

			}

		};

	}

	//////////////////////// make an action listener for the sliders
	public class Projection_Slider_low_listener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			
			//print that this is coming from the high slider
			IJ.log("This is coming from the low slider");
			//pring space
			IJ.log(" ");

			//get the source
			JSlider this_slider = (JSlider) e.getSource();

			if(!this_slider.getValueIsAdjusting()) {

				//get the value of the slider
				int high_slider_value = this_slider.getValue();
				//get the value from the low_slider
				int low_slider_value = Make_Projection_low_slider.getValue();		

				//get the spinner values 
				int high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				int low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();

				//print the values right now
				IJ.log("The high slider value is " + high_slider_value);
				IJ.log("The low slider value is " + low_slider_value);
				IJ.log("The high spinner value is " + high_spinner_value);
				IJ.log("The low spinner value is " + low_spinner_value);
				
				//if the higher slider is lower than the lower slider
				if(low_slider_value > high_slider_value){
					//print the log
					IJ.log("The high slider is lower than the low slider");
					//set the lower slider to the higher slider value
					Make_Projection_high_slider.setValue(low_slider_value);
					//print the log
					IJ.log("The high slider is now set to:"+low_slider_value);
					//print that this will activate the low slider listener
					IJ.log("This will activate the high slider listener");
				};
				
				//get the source slider numbers
				high_slider_value = Make_Projection_high_slider.getValue();
				low_slider_value = Make_Projection_low_slider.getValue();

				//get the spinner values 
				high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();

				//print the values right now
				IJ.log("The high slider value is " + high_slider_value);
				IJ.log("The low slider value is " + low_slider_value);
				IJ.log("The high spinner value is " + high_spinner_value);
				IJ.log("The low spinner value is " + low_spinner_value);

				//set the spinner values
				if (high_slider_value != high_spinner_value) {
					//print the log
					IJ.log("The high slider value is not equal to the high spinner value");
					//set the high spinner to the high slider value
					Make_Projection_high_spinner.setValue(high_slider_value);
					//print the log
					IJ.log("The high spinner is now set to:"+high_slider_value);
					//print that this will activate the high spinner listener
					IJ.log("This will activate the high spinner listener");
				}
				if (low_slider_value != low_spinner_value) {
					//print he log
					IJ.log("The low slider value is not equal to the low spinner value");
					//set the low spinner to the low slider value
					Make_Projection_low_spinner.setValue(low_slider_value);					
					//print the log
					IJ.log("The low spinner is now set to:"+low_slider_value);
					//print that this will activate the low spinner listener
					IJ.log("This will activate the low spinner listener");
				}

				//get the source slider numbers
				high_slider_value = Make_Projection_high_slider.getValue();
				low_slider_value = Make_Projection_low_slider.getValue();

				//get the spinner values 
				high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
				low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();
				
				//close the projected image
				if(projected_image != null){
					
					//set to ignore flush
					new_image_listener.setIgnoreFlush(true);
					
					projected_image.close();

					//set to not ignore the flush
					new_image_listener.setIgnoreFlush(false);
				};
				
				//get the selected item
				String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();

				//make the projection image
				projected_image = ZProjector.run(current_image, combobox_selected_item, low_spinner_value, high_spinner_value);
				//get the image id
				projected_image_id = projected_image.getID();
				//show the projected image
				projected_image.show();
				//set the location
				projected_image.getWindow().setLocation(500,500);

				//set the save path of the projection
				set_save_path_projection();

			}

		};

	}

	/////////////////////////make an action listener for the spinners
	public class Projection_Spinner_low_listener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {

			//get the source slider numbers
			int high_slider_value = Make_Projection_high_slider.getValue();
			int low_slider_value = Make_Projection_low_slider.getValue();

			//get the spinner values 
			int high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
			int low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();		

			//print the values right now
			IJ.log("The high slider value is " + high_slider_value);
			IJ.log("The low slider value is " + low_slider_value);
			IJ.log("The high spinner value is " + high_spinner_value);
			IJ.log("The low spinner value is " + low_spinner_value);

			//if the higher slider is lower than the lower slider
			if(low_spinner_value > high_spinner_value){
				//print the log
				IJ.log("The lowh spinner is higher than the high spinner");
				//set the lower spinner to the higher value
				Make_Projection_high_spinner.setValue(low_spinner_value);
				//print the log
				IJ.log("The high spinner is now set to:"+low_spinner_value);
				//print that this will activate the low spinner listener
				IJ.log("This will activate the high spinner listener");
			};

			//get the source slider numbers
			high_slider_value = Make_Projection_high_slider.getValue();
			low_slider_value = Make_Projection_low_slider.getValue();

			//get the spinner values 
			high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
			low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();		

			//print the values right now
			IJ.log("The high slider value is " + high_slider_value);
			IJ.log("The low slider value is " + low_slider_value);
			IJ.log("The high spinner value is " + high_spinner_value);
			IJ.log("The low spinner value is " + low_spinner_value);
			
			//if the high slider is different from the high spinner
			if (high_slider_value != high_spinner_value) {
				//print the log
				IJ.log("The high slider value is not equal to the high spinner value");
				//set the high slider to the high spinner value
				Make_Projection_high_slider.setValue(high_spinner_value);
				//print the log
				IJ.log("The high slider is now set to:"+high_spinner_value);
				//print that this will activate the high slider listener
				IJ.log("This will activate the high slider listener");
			};
			//if the low slider is different from the low spinner
			if (low_slider_value != low_spinner_value) {
				//print the log
				IJ.log("The low slider value is not equal to the low spinner value");
				//set the low slider to the low spinner value
				Make_Projection_low_slider.setValue(low_spinner_value);
				//print the log
				IJ.log("The low slider is now set to:"+low_spinner_value);
				//print that this will activate the low slider listener
				IJ.log("This will activate the low slider listener");
			};

			//close the projected image
			if(projected_image != null){
				//set to ignore flush
				new_image_listener.setIgnoreFlush(true);
				
				projected_image.close();

				//set to not ignore the flush
				new_image_listener.setIgnoreFlush(false);
			};

			//get the selected item
			String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();

			//make the projection image
			projected_image = ZProjector.run(current_image, combobox_selected_item, low_spinner_value, high_spinner_value);
			//get the image id
			projected_image_id = projected_image.getID();
			//show the projected image
			projected_image.show();
			//set the location
			projected_image.getWindow().setLocation(500,500);

			//set the save path of the projection
			set_save_path_projection();
		};

	};

	/////////////////////////make an action listener for the spinners
	public class Projection_Spinner_high_listener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {

			//get the source slider numbers
			int high_slider_value = Make_Projection_high_slider.getValue();
			int low_slider_value = Make_Projection_low_slider.getValue();

			//get the spinner values 
			int high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
			int low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();		

			//print the values right now
			IJ.log("The high slider value is " + high_slider_value);
			IJ.log("The low slider value is " + low_slider_value);
			IJ.log("The high spinner value is " + high_spinner_value);
			IJ.log("The low spinner value is " + low_spinner_value);

			//if the higher slider is lower than the lower slider
			if(high_slider_value < low_spinner_value){
				//print the log
				IJ.log("The high spinner is lower than the low spinner");
				//set the lower spinner to the higher value
				Make_Projection_low_spinner.setValue(high_spinner_value);
				//print the log
				IJ.log("The low spinner is now set to:"+high_spinner_value);
				//print that this will activate the low spinner listener
				IJ.log("This will activate the low spinner listener");
			};

			//get the source slider numbers
			high_slider_value = Make_Projection_high_slider.getValue();
			low_slider_value = Make_Projection_low_slider.getValue();

			//get the spinner values 
			high_spinner_value = (Integer) Make_Projection_high_spinner.getValue();
			low_spinner_value = (Integer) Make_Projection_low_spinner.getValue();		

			//print the values right now
			IJ.log("The high slider value is " + high_slider_value);
			IJ.log("The low slider value is " + low_slider_value);
			IJ.log("The high spinner value is " + high_spinner_value);
			IJ.log("The low spinner value is " + low_spinner_value);
			
			//if the high slider is different from the high spinner
			if (high_slider_value != high_spinner_value) {
				//print the log
				IJ.log("The high slider value is not equal to the high spinner value");
				//set the high slider to the high spinner value
				Make_Projection_high_slider.setValue(high_spinner_value);
				//print the log
				IJ.log("The high slider is now set to:"+high_spinner_value);
				//print that this will activate the high slider listener
				IJ.log("This will activate the high slider listener");
			};
			//if the low slider is different from the low spinner
			if (low_slider_value != low_spinner_value) {
				//print the log
				IJ.log("The low slider value is not equal to the low spinner value");
				//set the low slider to the low spinner value
				Make_Projection_low_slider.setValue(low_spinner_value);
				//print the log
				IJ.log("The low slider is now set to:"+low_spinner_value);
				//print that this will activate the low slider listener
				IJ.log("This will activate the low slider listener");
			};

			//close the projected image
			if(projected_image != null){
				
				//set to ignore flush
				new_image_listener.setIgnoreFlush(true);
				
				projected_image.close();

				//set to not ignore the flush
				new_image_listener.setIgnoreFlush(false);
			};

			//get the selected item
			String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();

			//make the projection image
			projected_image = ZProjector.run(current_image, combobox_selected_item, low_spinner_value, high_spinner_value);
			//get the image id
			projected_image_id = projected_image.getID();
			//show the projected image
			projected_image.show();
			//set the location
			projected_image.getWindow().setLocation(500,500);

			//set the save path of the projection
			set_save_path_projection();
			

		};

	};

	//////////////////////////make an action listener for the projection combobox
	public class Make_Projection_combobox_action_listener implements ActionListener {

		boolean project_here = true;

		public void make_projection(boolean project_here) {

			this.project_here = project_here;

		} 

		public void actionPerformed(ActionEvent e) {

			if (project_here) {
				
				//get the selected item
				String selected_item = (String) Make_Projection_combobox.getSelectedItem();

				//make the projection based on the selection
				//get the low and high spinners
				int low_spinner_value = Integer.parseInt( Make_Projection_low_spinner.getValue().toString());
				int high_spinner_value = Integer.parseInt( Make_Projection_high_spinner.getValue().toString());

				//close the image
				if(projected_image != null){
					
					//set the ignore flush to true
					new_image_listener.setIgnoreFlush(true);
					
					projected_image.close();
					
					//set the ignore flush to false
					new_image_listener.setIgnoreFlush(false);
				};

				//make the projection
				projected_image = ZProjector.run(current_image, selected_item, low_spinner_value, high_spinner_value);

				//get the image id
				projected_image_id = projected_image.getID();
				//show the projected image
				projected_image.show();

				//log where this is coming from
				IJ.log("ZProjection from the Make Projection Combobox");

				//set the location
				projected_image.getWindow().setLocation(500,500);

				//set the save path of the projection
				set_save_path_projection();

			}
				
		}
		
	}

	//////////////////////////make the list selection listener
	public class Make_sbs_list_selection_listener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {

			//update the display image
			Make_sbs_display_rois_in_image();

		}

	}
	
	//////////////////////////get the save path of projection method
	public void set_save_path_projection() {

		//get the text from the path text field
		String path_text = file_path_text_field.getText();

		//get the z sections of the projection
		int low_limit = Integer.parseInt(Make_Projection_low_spinner.getValue().toString());
		int high_limit = Integer.parseInt(Make_Projection_high_spinner.getValue().toString());
		
		//get the file (to work easy with the parents and so on)
		File main_to_save_file = new File(path_text);

		//get the parent path from the opening image path
		String main_to_save_parent_path = main_to_save_file.getParent();

		//get the name of the file
		String file_name = main_to_save_file.getName();

		//get the name of the file without the extension
		String file_name_no_extension = file_name.substring(0, file_name.lastIndexOf('.'));

		//get the selected item
		String combobox_selected_item = (String) Make_Projection_combobox.getSelectedItem();

		//get the final path to save the image
		String saving_image_path = main_to_save_parent_path + "/" + file_name_no_extension + "_z"+low_limit+"-z"+high_limit+"_"+combobox_selected_item+"_projection.tif";

		//set the text field to the path
		Make_projection_save_textfield.setText(saving_image_path);

	}

	/////////////////// error window method
	public void make_error_window(String error_message) {

		//set the size
		error_frame.setSize(300, 100);
		//set the location
		error_frame.setLocation(500, 500);
		//set the layout
		error_frame.setLayout(new FlowLayout());
		//make a new jlabel
		JLabel error_label = new JLabel(error_message);
		//add the label to the frame
		error_frame.add(error_label);
		//make a new jbutton
		JButton error_button = new JButton("OK");
		//add the button to the frame
		error_frame.add(error_button);
		//make a new action listener for the button
		ActionListener error_button_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//close the frame
				error_frame.dispose();
			}
		};
		//add the action listener to the button
		error_button.addActionListener(error_button_listener);
		//set the frame visible
		error_frame.setVisible(true);
	}

	///////////////////////prepare the roi array and window listener
	public void make_sbs_prepare_rois_and_listeners(int making_sbs_image_id) {

		//get the roi manager instance
		RoiManager roi_manager = RoiManager.getInstance();

		//reset the roi manager
		roi_manager.reset();

		//reset the roi list
		roi_list = new ArrayList<Roi>();

		//add a new emtpy point roi to the list
		roi_list.add(new PointRoi());

		//set the name of the first roi
		roi_list.get(0).setName("sbs_coordinates");

		//get the roi to the roi manager
		roi_manager.addRoi(roi_list.get(0));

		//get the title of the image
		String image_title = WindowManager.getImage(making_sbs_image_id).getTitle();

		//log the image title
		IJ.log("Making sbs from image title: " + image_title);




	}

	///////////////////making a method to get the working image
	public int Make_sbs_get_working_image() {

		//get the use projection state
		boolean use_projection_state = Make_sbs_use_projection_checkbox.isSelected();

		//initiate the using image id
		int using_image_id = 0;

		//if it is selected
		if(use_projection_state == true) {

			//get the image id
			using_image_id = projected_image_id;

		} else {

			//get the image id
			using_image_id = main_image_id;

		}

		//return the using image id 
		return using_image_id;

	}
	
	//////////////////////////////////make a method to validate the roi properties
	public boolean Make_sbs_validate_roi_properties(Roi roi_to_get_properties) {
		
		//returning boolean declaration
		boolean roi_properties_valid = false;

		//also, make a pass boolean array with three values set to 0
		boolean[] pass_boolean_array = new boolean[number_of_properties_per_sbs_roi];

		//if the roi has properties, then get them
		if (roi_to_get_properties.getProperties() != null) {

			//get the properties
			String roi_properties = roi_to_get_properties.getProperties();

			//now separate them into an array
			String[] roi_properties_array = roi_properties.split("\n");

			//get the length of the array
			int roi_properties_array_length = roi_properties_array.length;		

			//if the properties length is the same as the expected number of properties
			if (roi_properties_array_length == number_of_properties_per_sbs_roi) {
				
				//then loop and get the properties titles by splitting the strings
				for (int index = 0; index < roi_properties_array_length; index++) {

					//get the property title based on the array
					String property_title = number_of_properties_per_sbs_roi_string_array[index];
					
					//get a string for the property value
					String property_value = "NO_VALUE";

					//try to get the property value based on the title
					try {
						
						//get the property
						property_value = roi_to_get_properties.getProperty(property_title);

					} catch (Exception e) {
						//keeop the original string
						property_value = "NO_VALUE";

					}

					//if the property is different from NO_VALUE, then set the pass_boolean_array to true
					if (property_value != "NO_VALUE") {

						//set the pass_boolean_array to true
						pass_boolean_array[index] = true;

					} else {

						//set the pass_boolean_array to false
						pass_boolean_array[index] = false;

					}			

				}

				//now check if all the pass_boolean_array values are true
				if (pass_boolean_array[0] == true && pass_boolean_array[1] == true && pass_boolean_array[2] == true) {

					//if all the values are true, then set the roi_properties_valid to true
					roi_properties_valid = true;

				} else {

					//if not, then set the roi_properties_valid to false
					roi_properties_valid = false;

				}


			}


		}

		//return the roi_properties valid
		return roi_properties_valid;

	}

	public String[] Make_sbs_get_current_update_roi_properties(Roi roi_go_get_properties) {

		//initiate the properties array
		String[] caught_properties_array = new String[number_of_properties_per_sbs_roi];

		//loop through the number of properties
		for (int index = 0; index < number_of_properties_per_sbs_roi; index++) {

			//get the property title based on the array
			String property_title = number_of_properties_per_sbs_roi_string_array[index];

			//log which property title is being checked
			IJ.log("Checking property title: " + property_title);

			//get a string for the property value
			String property_value = "NO_VALUE";

			//try to get the property value based on the title
			try {

				//get the property
				property_value = roi_go_get_properties.getProperty(property_title);

				//log the result
				IJ.log("Property value: " + property_value);

			} catch (Exception e) {
				//keeop the original string
				property_value = "NO_VALUE";

			}

			//set the property value to the array
			caught_properties_array[index] = property_title+"="+property_value;

		}

		//return the array
		return caught_properties_array;

	}

	//////////////////////////// prepare the point sbs from the list
	public void Make_sbs_prepare_point_sbs_rois_from_list() {

		//get the first roi in the list
		PointRoi first_roi = (PointRoi) roi_list.get(0);

		//get the float polygon
		FloatPolygon first_roi_polygon = first_roi.getFloatPolygon();

		//get the number of points
		int points_length = first_roi_polygon.xpoints.length;

		//loop through the points
		for (int index = 0; index < points_length; index++) {

			//get the x, y and slice coordinates
			double x_coord = first_roi_polygon.xpoints[index];
			double y_coord = first_roi_polygon.ypoints[index];
			int z_coord = first_roi.getPointPosition(index);

			//log the new point being created
			IJ.log("Creating new point at x: " + x_coord + " y: " + y_coord + " z: "+first_roi.getPointPosition(index));

			//make a new point roi with the x and y points
			PointRoi point_roi = new PointRoi();

			//add the point
			point_roi.addPoint(x_coord, y_coord, z_coord);

			//set the name of the point roi
			point_roi.setName("sbs" + (index+1) + "_coordinate");

			//log the name of the point roi
			IJ.log("The name of the point roi is: " + point_roi.getName());

			//loop throuhg the possible properties
			for (int index2 = 0; index2 < number_of_properties_per_sbs_roi; index2++) {

				//get the property title based on the array
				String property_title = number_of_properties_per_sbs_roi_string_array[index2];

				//set the property to the roi
				point_roi.setProperty(property_title, "empty");

			}

			//add the point to the roi list
			roi_list.add(point_roi);

		}

		//log that this will fire
		IJ.log("Firing the update jlist method frin the new points");

		//update the jlist
		Make_sbs_update_jlist();

	}

	//////////////////////making a method to update the JList
	public void Make_sbs_update_jlist() {

		//if the roi list has no active rois, it is a new list. Update the model accordingly
		if (roi_list.size() == 0) {

			//then set up a new point and add it to the roi list
			roi_list.add(new PointRoi());

			//then update the JList with a new model
			//make a model for the jlist
			DefaultListModel<String> new_jlist_model = new DefaultListModel<String>();

			//setting the jlist with the new model
			Make_sbs_working_rois_list.setModel(new_jlist_model);

		}

		//if the roi list has active rois, it is a loaded or updating current list so find out if it is valid roi structure inside
		if (roi_list.size() > 0) {

			//get the first roi and get the type and properties
			Roi first_roi = roi_list.get(0);

			//get the name of the first roi
			String first_roi_name = first_roi.getName();

			//get the type of roi
			int first_roi_type = first_roi.getType();

			//if the name is not the x_y_coordinates string or the type is not a point roi
			if (!first_roi_name.equals("sbs_coordinates") || first_roi_type != Roi.POINT) {

				//get an error message string
				String error_message = "The roi list is not valid. Please load a valid roi list";

				//make an error window
				make_error_window(error_message);

				//unload the roi list
				roi_list = new ArrayList<Roi>();

				//then set up a new point and add it to the roi list
				roi_list.add(new PointRoi());

				//now get the new JList model
				//make a model for the jlist
				DefaultListModel<String> new_jlist_model = new DefaultListModel<String>();
				
				//update the JList with the new model
				Make_sbs_working_rois_list.setModel(new_jlist_model);

			} 
			
			//if the name is x_y_coordinates string and the type is point roi
			if (first_roi_name.equals(x_y_coordinates_roi_string) && first_roi_type == Roi.POINT) {
				
				//log something to make sure it passed
				IJ.log("passed the first roi check");
				
				//declare a new array list to hold the sbs coordinates names
				ArrayList<String> sbs_coordinates_names = new ArrayList<String>();

				//loop through the roi list after the first roi
				for (int a = 1; a < roi_list.size(); a++) {
					
					//get the roi
					Roi current_update_roi = roi_list.get(a);

					//validate the properties of the roi
					boolean roi_properties_valid = Make_sbs_validate_roi_properties(current_update_roi);

					//if the roi properties are valid
					if (roi_properties_valid) {

						//if it is valid, get the name without the "_coordinate" termination
						String current_update_roi_name = current_update_roi.getName();

						//get the length of the name
						int current_update_roi_name_length = current_update_roi_name.length();

						//get the name without the "_coordinate" termination
						String current_update_roi_name_without_coordinate_termination = current_update_roi_name.substring(0, current_update_roi_name_length - 11)+" / ";

						//run the method to get the roi properties as string array
						String[] current_update_roi_properties_as_strings = Make_sbs_get_current_update_roi_properties(current_update_roi);
						
						//loop through the array and get the name
						for (int b = 0; b < current_update_roi_properties_as_strings.length; b++) {						

							//get the current string
							String adding_string = current_update_roi_properties_as_strings[b];
							
							//add the previous string with the space and the current string
							current_update_roi_name_without_coordinate_termination = current_update_roi_name_without_coordinate_termination + "   " + adding_string;
							
						}

						//add it to the sbs coordinates names array list
						sbs_coordinates_names.add(current_update_roi_name_without_coordinate_termination);
					}

					//if the properties are not valid

				}

				//check right now the number of sbs coordinates points
				//get the roi 0 again
				Roi roi_0 = roi_list.get(0);

				//get the number of points
				Point[] number_of_points_array = roi_0.getContainedPoints();

				//get the number of points
				int number_of_points = number_of_points_array.length;

				//get the number of sbs coordinates names
				int number_of_sbs_coordinates_names = sbs_coordinates_names.size();

				//if the number of points is higher than 0 and the number of sbs coordinates names is 0, then ask they want to prepare the point sbs rois from the list
				if (number_of_points > 0 && number_of_sbs_coordinates_names == 0) {

					//get the message
					String message = "There are " + number_of_points + " points in the sbs_coordinates. There are no individual rois for this coordinates. Do you want to create them?";

					//make a yes no dialog
					YesNoCancelDialog yes_no_dialog = new YesNoCancelDialog(IJ.getInstance(), "Prepare point sbs rois from list?", message);

					//if they say yes
					if (yes_no_dialog.yesPressed()) {

						//run the method to prepare the point sbs rois from the list
						Make_sbs_prepare_point_sbs_rois_from_list();

					}

				}

				//if the number of points is more than one and the number of sbs coordinates names is more than one too
				if (number_of_points > 0 && number_of_sbs_coordinates_names > 0) {
					
					//make a model for the jlist
					DefaultListModel<String> new_jlist_model = new DefaultListModel<String>();

					//loop through the sbs coordinates names
					for (int index_sbs_coordinates_name = 0; index_sbs_coordinates_name < sbs_coordinates_names.size(); index_sbs_coordinates_name++) {

						//get the current sbs coordinates name
						String current_sbs_coordinates_name = sbs_coordinates_names.get(index_sbs_coordinates_name);

						//add it to the new jlist model
						new_jlist_model.addElement(current_sbs_coordinates_name);

					}

					//update the JList with the new model
					Make_sbs_working_rois_list.setModel(new_jlist_model);
				
				}

			}

		}

	}

	//////////////////////////reset the make sbs gui
	public void Make_sbs_menu_reset_gui() {

		//start with the text fields
		//set the text fields to empty
		make_sbs_roimanager_text_field.setText("");
		Make_sbs_cut_sbs_file_path_textfield.setText("");

		//set the buttons to disabled
		make_sbs_roimanager_save_button.setEnabled(false);
		make_sbs_roimanager_cut_selected_sbs_button.setEnabled(false);
		make_sbs_roimanager_cut_all_sbs_button.setEnabled(false);
		make_sbs_roimanager_delete_sbs_button.setEnabled(false);
		make_sbs_roimanager_select_button.setEnabled(true);
		Make_sbs_roimanager_make_square_roi_button.setEnabled(false);
		Make_sbs_roimanager_make_square_all_button.setEnabled(false);
		Make_sbs_roimanager_deselect_sbs_button.setEnabled(false);
		
		//checkboxes
		Make_sbs_use_projection_checkbox.setSelected(false);
		Make_sbs_use_projection_checkbox.setEnabled(true);
		Make_sbs_add_new_SBS_checkbox.setSelected(false);
		Make_sbs_add_new_SBS_checkbox.setEnabled(false);
		Make_sbs_cut_entire_z_checkbox.setSelected(false);
		Make_sbs_cut_entire_z_checkbox.setEnabled(false);
		Make_sbs_show_in_all_slices_checkbox.setEnabled(false);
		Make_sbs_show_in_all_slices_checkbox.setSelected(false);
		Make_sbs_show_all_points_as_overlay_checkbox.setEnabled(false);
		Make_sbs_show_all_points_as_overlay_checkbox.setSelected(false);

		//set the show all to false
		setShowAllSlices(false);

		//JSpinners
		Make_sbs_square_roi_size_spinner.setValue(100);
		Make_sbs_square_roi_size_spinner.setEnabled(false);

		//get the roi manager
		RoiManager roi_manager = RoiManager.getInstance();
		//reset the roi manager
		roi_manager.reset();

		//reset the windws rois
		//get the list of all the image titles
		String[] image_titles = WindowManager.getImageTitles();

		//loop through the image titles and reset the rois
		for (int index_image_title = 0; index_image_title < image_titles.length; index_image_title++) {

			//get the current image title
			String current_image_title = image_titles[index_image_title];

			//get the image
			ImagePlus current_image = WindowManager.getImage(current_image_title);

			//set the rois to null
			current_image.resetRoi();

		}

		//reset the JList
		//make a model for the jlist
		DefaultListModel<String> new_jlist_model = new DefaultListModel<String>();
		//update the JList with the new model
		Make_sbs_working_rois_list.setModel(new_jlist_model);	


	}

	//////////////////////////enable the make sbs gui
	public void Make_sbs_menu_enable_loaded_gui() {

		//set the buttons to enabled
		make_sbs_roimanager_save_button.setEnabled(true);
		make_sbs_roimanager_cut_selected_sbs_button.setEnabled(true);
		make_sbs_roimanager_cut_all_sbs_button.setEnabled(true);
		make_sbs_roimanager_delete_sbs_button.setEnabled(true);
		make_sbs_roimanager_select_button.setEnabled(true);
		Make_sbs_roimanager_make_square_roi_button.setEnabled(true);
		Make_sbs_roimanager_make_square_all_button.setEnabled(true);
		Make_sbs_show_all_points_as_overlay_checkbox.setEnabled(true);
		

		//set the checkboxes to enables
		Make_sbs_add_new_SBS_checkbox.setEnabled(true);
		Make_sbs_ask_for_zs_checkbox.setEnabled(false);
		Make_sbs_cut_entire_z_checkbox.setEnabled(true);
		Make_sbs_show_in_all_slices_checkbox.setEnabled(true);
		Make_sbs_show_all_points_as_overlay_checkbox.setSelected(false);

		//enable the JSpinners
		Make_sbs_square_roi_size_spinner.setEnabled(true);

		//disable the use projection checkbox
		Make_sbs_use_projection_checkbox.setEnabled(false);

	}

	//////////////////////////////get the make sbs working image id
	public int Make_sbs_get_working_image_id() {

		//get the variable to return
		int returnint_int = -1;

		//get the value from the use projection checkbox
		boolean get_use_projection = Make_sbs_use_projection_checkbox.isSelected();

		//if the use projection is enabled
		if (get_use_projection) {

			//check if the make_projection checkbox is enabled
			boolean make_projection_selected = Make_Projection_checkbox.isSelected();

			//if the make projection is selected
			if (make_projection_selected) {

				//get the projection image id
				returnint_int = projected_image_id;

			} else {

				//if the image is not projected, log that the image is not projected and return the main window id
				IJ.log("The image is not projected, using the main window image");

				returnint_int = main_image_id;

			}


		} else {

			//get the image id from the image list
			returnint_int = main_image_id;

		}

		return returnint_int;

	}

	//////////////////////////////display the rois on the image
	public void Make_sbs_display_rois_in_image() {

		//get the working image id
		int Make_sbs_window_id = Make_sbs_get_working_image_id();

		//get the roi to display
		int roi_list_index = Make_sbs_get_current_roi_selection_from_jlist();


		//get the first point list from the roi list
		Roi display_roi = roi_list.get(roi_list_index);

		
		//get the state of the overlay checkbox
		boolean show_overlay = Make_sbs_show_all_points_as_overlay_checkbox.isSelected();

		//display the roi on the image
		//select the image
		ImagePlus imp = WindowManager.getImage(Make_sbs_window_id);

		//reset the overlay of the image
		imp.setOverlay(null);
		
		//if the overlay checkbox is selected
		if (show_overlay) {

			//log that the overlay is being displayed
			IJ.log("Displaying the overlay");

			//get another roi to be the overlay roi that is a copy from the display roi
			PointRoi roi0 = (PointRoi) roi_list.get(0);

			//get the float polygon of the roi
			FloatPolygon fp = roi0.getFloatPolygon();

			//get the number of points in the roi
			int number_of_points = fp.npoints;

			//get a new point roi
			PointRoi overlay_roi = new PointRoi();

			//loop through the points in the roi
			for (int index_point = 0; index_point < number_of_points; index_point++) {

				//get the x and y coordinates of the point
				float x = fp.xpoints[index_point];
				float y = fp.ypoints[index_point];

				//add the point to the overlay roi
				overlay_roi.addPoint(x, y);

			}

			//set the color of the overlay_roi to red
			overlay_roi.setStrokeColor(Color.red);

			//make a new overlay
			Overlay overlay_adding = new Overlay();

			//add the roi to the overlay
			overlay_adding.add(overlay_roi);

			//add the overlay to the imp
			imp.setOverlay(overlay_adding);

			//deselect all current rois
			imp.resetRoi();

			//cast the roi to a point roi
			PointRoi display_point_roi = (PointRoi) display_roi;

			//set the display roi to be blue, a type of circle and a little bigger
			display_point_roi.setStrokeColor(Color.cyan);
			display_point_roi.setPointType(3);
			display_point_roi.setSize(5);
			
			imp.setRoi(display_point_roi);

		}

		//if there is no overlay
		if (!show_overlay) {

			//reset the overlay
			imp.setOverlay(null);

			//deselect all current rois
			imp.resetRoi();

			//cast the roi to a point roi
			PointRoi display_point_roi = (PointRoi) display_roi;

			//set the display roi to be yellow and a little smaller
			display_point_roi.setStrokeColor(Color.yellow);
			display_point_roi.setPointType(0);
			display_point_roi.setSize(2);

			imp.setRoi(display_point_roi);

		}



		//update the image
		imp.updateAndDraw();

	}

	///////////////////////////make the roi window interactable
	public void Make_sbs_roi_window_not_interactable(int imp_id) {

		//get the canvas of the image
		ImageCanvas imp_canvas = WindowManager.getImage(imp_id).getCanvas();

		//get the canvas mouse listeners
		MouseListener[] canvas_mouse_listeners = imp_canvas.getMouseListeners();

		//set the first mouse listener to the variable declared at the beginning
		sbs_coords_mouse_listener = canvas_mouse_listeners[0];

		//remove the mouse listeners from the canvas
		for (int index_canvas_mouse_listeners = 0; index_canvas_mouse_listeners < canvas_mouse_listeners.length; index_canvas_mouse_listeners++) {

			//remove the mouse listener
			imp_canvas.removeMouseListener(canvas_mouse_listeners[index_canvas_mouse_listeners]);

		}

		//add the new mouse listener to the canvas that does nothing
		imp_canvas.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

			}

		});
					
		//

		/*

		//get the image
		ImagePlus imp = WindowManager.getImage(imp_id);

		//get the roi to a copy
		Roi roi = imp.getRoi();

		//make a copy of the roi
		Roi roi_copy = (Roi) roi.clone();

		//deselect the imp roi
		imp.resetRoi();

		//add the copy roi to the image
		imp.setRoi(roi_copy);

		//get the image canvas
		ImageCanvas imp_canvas = imp.getCanvas();

		//get the canvas actions listeners
		//get the canvas actions listeners
		MouseListener[] canvas_action_listeners = imp_canvas.getMouseListeners();
		//remove the listeners
		for (int index_canvas_action_listeners = 0; index_canvas_action_listeners < canvas_action_listeners.length; index_canvas_action_listeners++) {

			//if the listener is more than 0
			if (index_canvas_action_listeners > 0) {

				//log the listener number
				IJ.log("Removing listener number: " + index_canvas_action_listeners);

				//remove the current listener
				imp_canvas.removeMouseListener(canvas_action_listeners[index_canvas_action_listeners]);

			}

		}

		//add a mouse listener to the canvas
		// Add a mouse listener to the canvas
        imp_canvas.addMouseListener(new MouseAdapter() {
            
			public void mouseClicked(MouseEvent e) {
				
				//get the source
				Object source = e.getSource();

				if (source instanceof ImageCanvas) {

					//log that the listener has been added
					IJ.log("Mouse listener added");

					ImageCanvas ic = (ImageCanvas) source;
					ImagePlus imp = ic.getImage();

					//get the imp id
					int imp_id = imp.getID();

					//remove the roi
					imp.resetRoi();

					//add the first roi of the list
					imp.setRoi(roi_list.get(0));



				}

				

            }
        });

		*/
	}

	/////////////////////////make the roi window interactable again
	public void Make_sbs_roi_window_interactable(int imp_id) {	
		
		
		//get the old mouse listener into the image canvas
		ImageCanvas imp_canvas = WindowManager.getImage(Make_sbs_window_id).getCanvas();

		//get the canvas mouse listeners
		MouseListener[] canvas_mouse_listeners = imp_canvas.getMouseListeners();

		//remove all the listeners
		for (int index_canvas_mouse_listeners = 0; index_canvas_mouse_listeners < canvas_mouse_listeners.length; index_canvas_mouse_listeners++) {

			//remove the mouse listener
			imp_canvas.removeMouseListener(canvas_mouse_listeners[index_canvas_mouse_listeners]);

		}

		//set the old one
		imp_canvas.addMouseListener(sbs_coords_mouse_listener);

	}

	///////////////////////////get the current roi selection
	public int Make_sbs_get_current_roi_selection_from_jlist() {

		int current_roi_selection = -2;

		//make sabs jlist selection index
		int sbs_selected_in_jlist = Make_sbs_working_rois_list.getSelectedIndex();

		//if the jlist has a -1
		if (sbs_selected_in_jlist == -1) {

			//set the current roi selection to -1
			current_roi_selection = 0;

		}

		//if it is more than -1
		if (sbs_selected_in_jlist > -1) {

			//get the value of the JList
			String sbs_selected_in_jlist_string = Make_sbs_working_rois_list.getSelectedValue();

			//get the string until the first space
			String sbs_selected_in_jlist_string_first_space = sbs_selected_in_jlist_string.substring(0, sbs_selected_in_jlist_string.indexOf(" "));

			//add the "_coordinate" string to the end
			String sbs_selected_in_jlist_string_first_space_coordinate = sbs_selected_in_jlist_string_first_space + "_coordinate";

			//loop through the roi list
			for (int index_roi_list = 0; index_roi_list < roi_list.size(); index_roi_list++) {

				//get the current roi
				Roi current_roi = roi_list.get(index_roi_list);

				//get the roi name
				String current_roi_name = current_roi.getName();

				//if the roi name is the same as the selected roi
				if (current_roi_name.equals(sbs_selected_in_jlist_string_first_space_coordinate)) {

					//set the current roi selection
					current_roi_selection = index_roi_list;

				}

			}

		}

		//log that the selection has been found
		IJ.log("Current roi selection: " + current_roi_selection);

		return current_roi_selection;

	}

	////////////////////////////////show al points method
	public void setShowAllSlices (boolean show_points) {

		//set the prefs show all points to enables
		Prefs.showAllPoints = show_points;

		//get all of the image titles
		String[] image_titles = WindowManager.getImageTitles();

		//loop through the image titles
		for (int index_image_titles = 0; index_image_titles < image_titles.length; index_image_titles++) {

			//get the current image
			ImagePlus current_image = WindowManager.getImage(image_titles[index_image_titles]);

			//update the image
			current_image.updateAndDraw();

		}

		//update the display

	}

	////////////////////////////add a window listenert to add the sbs
	public void AddWindowListenerNewSBS() {

		//get the image id
		int imp_id = Make_sbs_get_working_image_id();

		//get the image
		ImagePlus imp = WindowManager.getImage(imp_id);

		//get the canvas
		ImageCanvas imp_canvas = imp.getCanvas();

		//add the new mouse listener to the canvas
		imp_canvas.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				//get the source
				Object source = e.getSource();

				if (source instanceof ImageCanvas) {

					//get the working image
					ImagePlus imp = WindowManager.getImage(Make_sbs_get_working_image_id());

					//get the roi
					PointRoi roi = (PointRoi) imp.getRoi();

					//make sure that the roi is of type point
					if (roi.getType() != Roi.POINT) {

						//log that the roi is not a point
						IJ.log("Roi is not a point, reseting to the previous known");

						//reset the roi
						imp.resetRoi();

						//update and display 
						Make_sbs_display_rois_in_image();

					}

					//if it is of type point, update
					if (roi.getType() == Roi.POINT) {

						//update the roi list
						updateRoisList();

						//log that it has been updated
						IJ.log("Roi has been updated");

						//log the roi list length
						IJ.log("Roi list length: " + roi_list.size());

						//update and display
						Make_sbs_display_rois_in_image();

					}


				}

			}

		});



	
	}

	/////////////////////////////make a method to make the sbs new rois 
	public void updateRoisList() {

		//think carlos... update this friggin list....
		//create a new roi list to populate later
		ArrayList<Roi> new_roi_list = new ArrayList<Roi>();

		//get the image
		ImagePlus imp = WindowManager.getImage(Make_sbs_get_working_image_id());

		//get the point roi from the image
		PointRoi roi = (PointRoi) imp.getRoi();

		//add this roi to the new roi list
		new_roi_list.add(roi);
		
		//get the float polygon
		FloatPolygon fp = roi.getFloatPolygon();

		//get the x coordinates
		float[] x_coordinates = fp.xpoints;
		//get the y coordinates
		float[] y_coordinates = fp.ypoints;

		//loop through the number of coordinates
		for (int i = 0; i < y_coordinates.length; i++) {
			
			//get the roi name 
			String roi_name = new String("sbs"+(i+1)+"_coordinate");

			//get the adding x and adding y coordinates as floats
			float adding_x_coordinate = x_coordinates[i];
			float adding_y_coordinate = y_coordinates[i];

			//get the z coordinate
			int adding_z_coordinate = roi.getPointPosition(i);

			//now, loop through the remaining roi list after the 0th roi and get the coordinates
			for (int j = 1; j < y_coordinates.length; j++) {

				//get the roi
				PointRoi comparing_roi = (PointRoi) roi_list.get(j);

				//get the roi float polyg
				FloatPolygon comparing_fp = comparing_roi.getFloatPolygon();

				//get the x coordinates
				float[] comparing_x_coordinates = comparing_fp.xpoints;
				//get the y coordinates
				float[] comparing_y_coordinates = comparing_fp.ypoints;

				//get the z coordinate
				int comparing_z_coord = comparing_roi.getPointPosition(0);

				//compare the values to see if they are the same
				if (adding_x_coordinate == comparing_x_coordinates[0] && adding_y_coordinate == comparing_y_coordinates[0] && adding_z_coordinate == comparing_z_coord) {

					//if it was found, then change the name and add it to the new roi list
					comparing_roi.setName(roi_name);

					//set it to the new list
					new_roi_list.add(comparing_roi);

				}
				//if you cannot find the coordinates, then add the properties and set them to null
				else {

					//make a new roi and add the values
					PointRoi new_roi = new PointRoi();

					//set the values
					new_roi.addPoint(adding_x_coordinate, adding_y_coordinate, adding_z_coordinate);
					
					//set the name
					new_roi.setName(roi_name);

					//loop through the properties
					for (int k = 0; k < number_of_properties_per_sbs_roi_string_array.length; k++) {			
						
						//set the property to null
						new_roi.setProperty(number_of_properties_per_sbs_roi_string_array[k], "empty");

					}

					//add the new roi to the new roi list
					new_roi_list.add(new_roi);

				}
				
			}

		}

		//set the roi list to the new roi list
		roi_list = new_roi_list;

	}

	//#endregion


	/////////////////// run method
	public void run(String arg) {

		//remove the listeners
		remove_listeners();

		//remove the roi listeners
		//get the listeners from the roi class
		Vector roi_listeners = Roi.getListeners();

		//remove them
		for (int index_roi_listeners = 0; index_roi_listeners < roi_listeners.size(); index_roi_listeners++) {

			//remove the current listener
			Roi.removeRoiListener((RoiListener) roi_listeners.get(index_roi_listeners));

		}

		//add the action listeners
		ImagePlus.addImageListener(new_image_listener);

		//set the ingore flush to false
		new_image_listener.setIgnoreFlush(false);

		//get the image list
		//get all of the image titles
		//get the image lists
		
		String[] titles = WindowManager.getImageTitles();

		//close the images
		for (int i = 0; i < titles.length; i++) {
			ImagePlus imp = WindowManager.getImage(titles[i]);
			if (imp != null) {
				imp.close();
			}
		}
		
		//set up the gui
		make_gui();			

		//setup the not active components
		//buttons
		Make_Projection_checkbox.setEnabled(false);
		Save_Projection_button.setEnabled(false);
		//checkboxes
		Make_Projection_checkbox.setEnabled(false);
		Classify_checkbox.setEnabled(false);
		Make_sbs_checkbox.setEnabled(false);

		//set the text on the textfield
		file_path_text_field.setText(working_image_path);
		
	//end of run()
	}
}

























































































