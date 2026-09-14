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
    import java.awt.Container;

    import ij.IJ;
    import ij.ImagePlus;
    import ij.WindowManager;
    import ij.process.ImageProcessor;
    import ij.process.ImageStatistics;


public class pointRegistration_menu {
  
    
    //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*
    

    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //flags to check if the frame has been initialized
    public static boolean pointRegistration_menu_Initialized = false;

    //the current class
    static Class<?> currentClass = pointRegistration_menu.class;

    //end of the region
    //#endregion

    //#region //-------------------- javax.swing elements --------------------//


    //JFrames
    public static JFrame pointRegistration_frame = new JFrame("Point Registration Menu");

    //Containers
    public static Container pointRegistration_frame_contentPane = pointRegistration_frame.getContentPane();

    ///////////// region for the single section ///////////////////
        //end of the region
    //#endregion


    //end of the swing elements region
    //#endregion

    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    
    //method to initialize the frame
    public static void initializeFrame() {

        //log that it has been initialized
        IJ.log("Initializing the pointRegistration_menu frame");
        
        //add the components to the frame
        addComponentsToFrame();

        //pack the frame
        pointRegistration_frame.pack();

        //set the frame visible
        pointRegistration_frame.setVisible(true);

        //set the frame listeners
        setFrameListeners();

        //set the frame flag to initialized
        pointRegistration_menu_Initialized = true;

    }
    
    //method to add the components to the frame
    public static void addComponentsToFrame() {

        

    }

    //method to retrieve if the frame is initialized
    public static boolean isInitialized() {

        //return the boolean
        return pointRegistration_menu_Initialized;

    }

    //method to set the frame's listeners
    public static void setFrameListeners() {

        //get the frame's listeners into a list
        WindowListener[] frameListeners = pointRegistration_frame.getWindowListeners();

        //loop through the list and remove them
        for (WindowListener windowListener : frameListeners) {

            //remove the listener
            pointRegistration_frame.removeWindowListener(windowListener);

        }

        //add the new listener
        pointRegistration_frame.addWindowListener(new WindowAdapter() {

            //method to close the frame
            public void windowClosing(WindowEvent windowEvent) {

                //close the frame
                closeMenu();

            }

        });

    }

    //method to reset the menu
    public static void resetMenu() {

        //log that the menu is being reset
        IJ.log("Resetting the pointRegistration_menu frame");


    }

    //method to close the menu
    public static void closeMenu() {

        //close the frame
        pointRegistration_frame.dispose();      

        //ressetting the menu
        resetMenu();

        //run the method in the mainMenu to reset the button
        mainMenu.resetOpenedMenuButton(currentClass);

        //set the boolean to false
        pointRegistration_menu_Initialized = false;

    }

    //#endregion end of methods region

    
    
    
    
    













}
