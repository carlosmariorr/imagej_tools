import java.awt.Dimension;

import javax.swing.JFrame;

import ij.IJ;

public class straighten_menu {
    
        //explain the class
    //#region ///////////////////////////////// Class explanation //////////////////////////////////
    /*

    
    */
    //#endregion


    //get the region for the variables
    //#region ///////////////////////////////// Variables declaration //////////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//
    
    //Strings
        
    //Booleans
    private static boolean straighten_menu_exists = false;

    //Class<?>
    private static Class<?> straighten_menu_class = straighten_menu.class;

    //end of the region
    //#endregion



    //#region //-------------------- javax.swing elements --------------------//
    //JFrames
    public static JFrame straigthen_menu_frame = new JFrame("Straighten Menu");

    //Spring layouts


    //JButtons


    //end of the swing elements region
    //#endregion


    //end of the region
    //#endregion


    //region to hold the methods
    //#region ///////////////////////////////// Methods declaration //////////////////////////////////
    







    //method to initizalize the frame
    public static void initializeFrame() {

        //add the components to the frame
        addComponents();

        //set the frame to initialized
        straighten_menu_exists = true;

    }

    //method to add the components
    public static void addComponents() {

        //add the components to the frame

    }

    //method to know if the frame is initialized
    public static boolean isInitialized() {
        return straighten_menu_exists;
    }

    //method to close the menu
    public static void closeMenu() {
        straigthen_menu_frame.dispose();
        straighten_menu_exists = false;
    }

    //method to reset the button on the mainMenu
    public static void resetMainMenuButton() {
        mainMenu.resetOpenedMenuButton(straighten_menu_class);
    }









}
