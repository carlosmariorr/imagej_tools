import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import ij.IJ;


public class mainMenu {

    //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    This class will manage the main menu. In this menu I'm planning two implementations.
    
    The first for the general creation of rois. This includes the cutting of sbs, setting the points to keep track of 
    elements such the nuclei in gonads or foci in the nuclei, etc. 

    The second for specifically tracing and stringhtening of chromosomes. 

    How am i going to implement the UI? I have no idea.....

    I'll start simple by just making a list with the implementations. If later I think it is viable, I'll add 
    a split pane to hold the implementations access and the options on the right.
    
    

    Here's some instructions on how to create a new class and add it to the menu:

    1) Create the buttons and add them to the populateButtonList() method.
    2) Create the class file and add it to the populateMenuClassList() method.
    3) Create the new class in the same "java" directory
    4) Add the method "initializeFrame" to the new java class that you just made because it will be called from the action listener in the mainMenu button. The method's title should be: "initializeFrame"
    5) Add the method to know if your class is active in the class you just created.The name of the method should be "isInitialized".
    6) Add the method to close the subMenu that you just created. The name should be: "closeMenu".
    7) Add a method in the subMenu that you just created to reset the button in this class. The name method must invoke the "resetOpenedMenuButton(Class<?> reset_button_class)" in this script so make sure to pass the Class<?>.
    8) In the subMenu class file add the window listeners so that they call the closeMenu() method when you close the window.
    
    
    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //Strings
        
    //Booleans
    public static boolean mainMenu_exists = false;

    //Functions
    //list of functions
    public static ArrayList<Class<?>> subMenus_class_list = new ArrayList<Class<?>>();

    //current directory
    public static String initial_directory = System.getProperty("user.dir") + System.getProperty("file.separator");
    
    //end of the region
    //#endregion



    //#region //-------------------- javax.swing elements --------------------//
    //JFrames
    //the main frame which will hold the splitpane
    public static JFrame mainMenu_frame = new JFrame("Carlton Lab Tools");

    //add the Panels
    //the panel that will hold the buttons
    public static JPanel mainMenu_button_panel = new JPanel();

    //Spring layouts
    //the layout of the main menu frame.
    public static SpringLayout mainMenu_spring_layout = new SpringLayout();
    public static SpringLayout mainMenu_button_panel_spring_layout = new SpringLayout();


    //JButtons
    //here, I'll take the button array approach to programatically add the buttons to the layout
    public static ArrayList<JButton> mainMenu_button_list = new ArrayList<JButton>();
    //the general roi creation button
    public static JButton mainMenu_general_roi_creation_button = new JButton("General ROI Creation");
    //the button for the projection management
    public static JButton mainMenu_projection_button = new JButton("Make Projection");
    //the chromosome tracing button
    public static JButton mainMenu_chromosome_tracing_button = new JButton("Chromosome Tracing");    
    //for the weka background substraction
    public static JButton mainMenu_weka_background_substraction_button = new JButton("Weka Background");
    //for the zBackground substraction
    public static JButton mainMenu_zBackground_substraction_button = new JButton("Z Background");
    //for the straighthen menu
    public static JButton mainMenu_straighten_menu_button = new JButton("Straighten");
    //for the trace menu
    public static JButton mainMenu_trace_menu_button = new JButton("Tracing");
    //for the point registration menu
    public static JButton mainMenu_point_registraction_menu_button = new JButton("Point Registration");
    //for the creates sbs menu
    public static JButton mainMenu_cut_sbs_menu_button = new JButton("Cut nuclei");
    //for the cut polygon mask menu
    public static JButton mainMenu_cut_polygon_mask_menu_button = new JButton("Cut Polygon Mask");
    //for the align moving nuclei menu
    public static JButton mainMenu_align_mobing_nuclei = new JButton("Align Moving Nuclei");

    //end of the swing elements region
    //#endregion


    //end of the region
    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    

    //method to populate the array list of buttons
    public static void populateButtonList(){
        //add the buttons to the list
        mainMenu_button_list.add(mainMenu_general_roi_creation_button);
        mainMenu_button_list.add(mainMenu_projection_button);
        mainMenu_button_list.add(mainMenu_chromosome_tracing_button);
        mainMenu_button_list.add(mainMenu_weka_background_substraction_button);
        mainMenu_button_list.add(mainMenu_zBackground_substraction_button);
        mainMenu_button_list.add(mainMenu_straighten_menu_button);
        mainMenu_button_list.add(mainMenu_trace_menu_button);
        mainMenu_button_list.add(mainMenu_point_registraction_menu_button);
        mainMenu_button_list.add(mainMenu_cut_sbs_menu_button);
        mainMenu_button_list.add(mainMenu_cut_polygon_mask_menu_button);
        mainMenu_button_list.add(mainMenu_align_mobing_nuclei);
    }

    //populate the function list
    public static void populateMenuClassList(){        

        //get the list of the classes
        subMenus_class_list.add(generalRoiCreation_menu.class);
        subMenus_class_list.add(projectionMenu.class);
        subMenus_class_list.add(chromosomeTracing_menu.class);
        subMenus_class_list.add(wekaBackgroundSubstraction_menu.class);
        subMenus_class_list.add(zBackgroundSubstraction_menu.class);
        subMenus_class_list.add(straighten_menu.class);
        subMenus_class_list.add(trace_menu.class);
        subMenus_class_list.add(pointRegistration_menu.class);
        subMenus_class_list.add(cutSbs_menu.class);
        subMenus_class_list.add(cutPolygonMask_menu.class);
        subMenus_class_list.add(alignMovingNuclei_menu.class);
    
    }

    //initialize the frame
    public static void initializeFrame(){

        //loging that the frame is being initialized
        IJ.log("Initializing the Main Menu");

        //populate the button list
        populateButtonList();

        //reset them 
        mainMenu_reset_components();

        //set the main frame
        //set the minimum size
        mainMenu_frame.setMinimumSize(new Dimension(300,500));
        
        //set the size of the JFrame
        mainMenu_frame.setSize(300, 500);
        
        //set the location
        mainMenu_frame.setLocation(100, 100);
        
        //set the default close operation
        mainMenu_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //get the content pane
        //get the main menu content pane
        Container mainMenu_content_pane = mainMenu_frame.getContentPane();

        //set the layout of the content pane
        mainMenu_content_pane.setLayout(mainMenu_spring_layout);

        //add the panel to the content pane
        mainMenu_content_pane.add(mainMenu_button_panel);

        //set the layout for the panel
        mainMenu_button_panel.setLayout(mainMenu_button_panel_spring_layout);
                
        //add the buttons
        //loop through the buttons
        for(int i = 0; i < mainMenu_button_list.size(); i++){
            //add the button
            mainMenu_button_panel.add(mainMenu_button_list.get(i));
        }

        //set the layout constraints for the panes
        //looop through the buttons
        for(int i = 0; i < mainMenu_button_list.size(); i++){
            
            //if it is 0
            if(i == 0){
                //set the constraints
                mainMenu_button_panel_spring_layout.putConstraint(SpringLayout.NORTH, 
                                                                mainMenu_button_list.get(i),
                                                                5,
                                                                SpringLayout.NORTH,
                                                                mainMenu_button_panel);
            }
            else{
                //set the constraints
                mainMenu_button_panel_spring_layout.putConstraint(SpringLayout.NORTH, 
                                                                mainMenu_button_list.get(i),
                                                                5,
                                                                SpringLayout.SOUTH,
                                                                mainMenu_button_list.get(i-1));
            }
            mainMenu_button_panel_spring_layout.putConstraint(SpringLayout.WEST,
                                                            mainMenu_button_list.get(i),
                                                            10,
                                                            SpringLayout.WEST,
                                                            mainMenu_button_panel);
            mainMenu_button_panel_spring_layout.putConstraint(SpringLayout.EAST,
                                                            mainMenu_button_list.get(i),
                                                            -10,
                                                            SpringLayout.EAST,
                                                            mainMenu_button_panel);
        }

        //run the populate method
        populateMenuClassList();

        //run the add action listeners to buttons method
        addListenersToButtons();

        //set the color of the button panel to light blue
        mainMenu_button_panel.setBackground(Color.LIGHT_GRAY);

        //set the layout constraints
        //for the scroll pane
        mainMenu_spring_layout.putConstraint(SpringLayout.NORTH, 
                                            mainMenu_button_panel,
                                            5,
                                            SpringLayout.NORTH,
                                            mainMenu_content_pane);
        mainMenu_spring_layout.putConstraint(SpringLayout.WEST,
                                            mainMenu_button_panel,
                                            5,
                                            SpringLayout.WEST,
                                            mainMenu_content_pane);
        mainMenu_spring_layout.putConstraint(SpringLayout.EAST,
                                            mainMenu_button_panel,
                                            -5,
                                            SpringLayout.EAST,
                                            mainMenu_content_pane);
        mainMenu_spring_layout.putConstraint(SpringLayout.SOUTH,
                                            mainMenu_button_panel,
                                            -5,
                                            SpringLayout.SOUTH,
                                            mainMenu_content_pane);

        //add the listener to the frame
        addMainMenuFrameListener();

        //pack the frame
        mainMenu_frame.pack();
        
        //set the visibility
        mainMenu_frame.setVisible(true);

        //set the boolean
        mainMenu_exists = true;

        //log that the frame has been initialized
        IJ.log("Main Menu initialized");

    }

    //method to add the action listeners to the buttons
    public static void addListenersToButtons() {
        
        //add the action listeners
        //loop through the buttons
        for(int i = 0; i < mainMenu_button_list.size(); i++){
            
            //get the button's action listeners
            ActionListener[] button_action_listeners = mainMenu_button_list.get(i).getActionListeners();
            //remove the action listeners
            for(int j = 0; j < button_action_listeners.length; j++){
                //remove the action listener
                mainMenu_button_list.get(i).removeActionListener(button_action_listeners[j]);
            }
            //add the new action listener
            mainMenu_button_list.get(i).addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    //get the index of the button in the button list
                    int button_index = mainMenu_button_list.indexOf(e.getSource());

                    //get the class corresponding to the button from the class list
                    Class<?> subMenu_class = subMenus_class_list.get(button_index);

                    try {
                        Method subMenu_initialize_method = subMenu_class.getMethod("initializeFrame");
                        //run the method
                        subMenu_initialize_method.invoke(null);

                    } catch (NoSuchMethodException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (SecurityException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
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

                    //set the button to disables
                    mainMenu_button_list.get(button_index).setEnabled(false);
                        
                }
            });

        }

    }
    
    //method to reset the main menu components
    public static void mainMenu_reset_components() {

        //reset the buttons
        //loop through the list
        for(int i = 0; i < mainMenu_button_list.size(); i++){
            //get the button
            JButton button = mainMenu_button_list.get(i);
            //set the button to enabled
            button.setEnabled(true);
        }
        //reset the main frame
        mainMenu_frame = new JFrame("Main Menu");

    }

    //method to reset the general roi creation button
    public static void resetGeneralRoiCreationMenuButton() {

        //get the button
        JButton button = mainMenu_button_list.get(0);
        //set the button to enabled
        button.setEnabled(true);

        //reset the listeners
        addListenersToButtons();

    }

    //method to reset the specified button
    public static void resetButton (JButton reseted_button) {

        //set the button to enabled
        reseted_button.setEnabled(true);

        //reset the listeners to the buttons
        //get the listeners
        ActionListener[] button_action_listeners = reseted_button.getActionListeners();

        //remove the listeners
        for(int i = 0; i < button_action_listeners.length; i++){
            //remove the action listener
            reseted_button.removeActionListener(button_action_listeners[i]);
        }

        //add the new action listener
        //add the new action listener
        reseted_button.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                
                //get the index of the button in the button list
                int button_index = mainMenu_button_list.indexOf(e.getSource());

                //get the class corresponding to the button from the class list
                Class<?> subMenu_class = subMenus_class_list.get(button_index);

                try {
                    Method subMenu_initialize_method = subMenu_class.getMethod("initializeFrame");
                    //run the method
                    subMenu_initialize_method.invoke(null);

                } catch (NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
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

                //set the button to disables
                mainMenu_button_list.get(button_index).setEnabled(false);
                    
            }
        });

    }

    //method to reset the opened menu button
    public static void resetOpenedMenuButton(Class<?> reset_button_class) {

        //get the index of the class in the class list
        int class_index = subMenus_class_list.indexOf(reset_button_class);

        //get the button
        JButton button = mainMenu_button_list.get(class_index);

        //run the reset button method
        resetButton(button);

    }

    //set button to disabled method
    public static void setButtonToDisabledFromClass(Class<?> disabled_button_class) {

        //get the idnex of the class form the array list
        int class_index = subMenus_class_list.indexOf(disabled_button_class);

        //get the button
        JButton button = mainMenu_button_list.get(class_index);

        //set the button to disabled
        button.setEnabled(false);

    }

    //method to check if any of the menus are open
    public static ArrayList<Class<?>> getOpenMenus() {

        //declare the variable to return
        ArrayList<Class<?>> openMenus = new ArrayList<Class<?>>();

        //loop through the classes list 
        for (int i = 0; i < subMenus_class_list.size(); i++) {
            
            //run the method to check if the menu is open
            try {
                Method subMenu_isInitialized_method = subMenus_class_list.get(i).getMethod("isInitialized");
                //run the method
                boolean isOpen = (Boolean) subMenu_isInitialized_method.invoke(null);                
                //if the menu is open
                if(isOpen){
                    //add the class to the list
                    openMenus.add(subMenus_class_list.get(i));
                }

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

        //return the class
        return openMenus;

    }

    //method to close the main menu
    public static void closeInitializedMenus(ArrayList<Class<?>> openMenus) {

        //loop through the menus
        for (int i = 0; i < openMenus.size(); i++) {
            
            //get the method to close the menu
            try {
                Method subMenu_close_method = openMenus.get(i).getMethod("closeMenu");
                //run the method
                subMenu_close_method.invoke(null);

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

    //method to add a listener to the main menu frame
    public static void addMainMenuFrameListener() {

        //get the main frame listeners
        WindowListener[] mainMenu_frame_listeners = mainMenu_frame.getWindowListeners();

        //loop through the listeners
        for (int i = 0; i < mainMenu_frame_listeners.length; i++) {
            //remove the listener
            mainMenu_frame.removeWindowListener(mainMenu_frame_listeners[i]);
        }

        //add the listener
        mainMenu_frame.addWindowListener(new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                


                //get the open menus
                ArrayList<Class<?>> openMenus = getOpenMenus();
                //close the menus
                closeInitializedMenus(openMenus);
                //reset the main menu components
                mainMenu_reset_components();
                //reset the general roi creation menu button
                resetGeneralRoiCreationMenuButton();
                //set the boolean
                mainMenu_exists = false;
            
            }
        
        });

    }

    //method to modify the current directory
    public static int setInitialDirectory(String new_directory) {

        //make sure that it ends with the separator
        if(!new_directory.endsWith(System.getProperty("file.separator"))){
            //add the separator
            new_directory = new_directory + System.getProperty("file.separator");
        }

        //if the directory exists, set the initial directory to the new directory
        if(new java.io.File(new_directory).exists()){
            
            //set the new directory
            initial_directory = new_directory;
            
            //return 1
            return 1;
            
        }
        else{
            
            //return -1
            return -1;

        }

    }

    //method to get the initial directory
    public static String getInitialDirectory() {

        //return the directory
        return initial_directory;

    }

    //method to get a file 
    public static String getFileFromChooser(String type) {

        //initialize the returning path
        String returningPath = "";

        //make the file chooser
        JFileChooser fileChooser = new JFileChooser();

        //set the file chooser to open files
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //if the type is image:
        if(type.equals("image")){
            //set the file filter
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "tif", "tiff", "jpg", "jpeg", "png", "gif", "bmp", "dv", "czi", "decon", "deconzs"));
        }

        //if the type is polygon
        if(type.equals("polygon")){
            //set the file filter
            fileChooser.setFileFilter(new FileNameExtensionFilter("Polygon Files", "roi", "zip"));
        }

        //set the initial directory
        fileChooser.setCurrentDirectory(new java.io.File(initial_directory));

        //open the file chooser
        int returnVal = fileChooser.showOpenDialog(null);

        //if the user selected a file
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            //get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            //get the selected file path
            String selectedFilePath = selectedFile.getAbsolutePath();
            
            //get the parent directory from the selected file path
            String ParentDir = selectedFilePath.substring(0, selectedFilePath.lastIndexOf(File.separator) + 1);

            //set the initial directory to the parent directory
            initial_directory = ParentDir;

            //set the returning path
            returningPath = selectedFilePath;

        }

        //return the variable
        return returningPath;

    }

    //end of the region
    //#endregion








}
