
import ij.IJ;

public class chromosomeTracing_menu {
    
    //#region ///////////////////////////////// Variables ////////////////////////////

    //#region //-------------------- Other Variables Initiation ---------------------------//

    //Strings
    public static String mainMenu_button_string = "Chromosome Tracing";

    //#endregion





    //#endregion


    //#region ///////////////////////////////// Methods ////////////////////////////

    //method to return the button string if it is a main menu button
    public static String isMainMenuButton() {

        //return the button string
        return mainMenu_button_string;

    }



    //method to initialize the frame
    public static void initializeFrame() {

        //log anything in IJ
        IJ.log("Initializing the chromosome tracing menu frame");



    }
    

    //#endregion 

}
