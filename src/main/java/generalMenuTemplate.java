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

import javax.swing.JFrame;

import ij.IJ;


//#endregion ############################################ IMPORTS #####################################################


//start of the class
public class generalMenuTemplate {

    
//#region ############################################### GLOBALS #####################################################

//the current class
static Class<?> currentClass = generalMenuTemplate.class;

//the frame name
public static String frameName = "generalMenuTemplate";

//the boolean to know if the frame is initialized
public static boolean isInitialized = false;


//#region ############################################### JAVA SWING ELEMENTS #########################################

//the frame
public static JFrame generalMenuTemplate_frame = new JFrame(frameName);


//#endregion ############################################ JAVA SWING ELEMENTS #########################################

//#endregion ############################################ GLOBALS #####################################################


//#region ############################################### METHODS #####################################################

//method to initialize the frame
public static void initializeFrame() {

    //log anything in IJ
    IJ.log("Initializing the "+frameName);

    //set the boolean to true
    isInitialized = true;

}

//method to close the menu
public static void closeMenu() {

    //initialize the frame
    initializeFrame();

    //close the frame
    generalMenuTemplate_frame.dispose();

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

