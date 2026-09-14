import java.util.Date;
import java.text.SimpleDateFormat;
import ij.IJ;

public class logManager {
    
    /* 
     This class will have the log manager functions that will be used
     to create the log files in other classes.
     */

    /////////////////////////// global variables ///////////////////////////
    //#region for the global variables

    


    //#endregion end of global variables region


    /////////////////////////// Methods ///////////////////////////
    //#region for Methods

    //method to start a log file
    public static void startLog(String mainDir, String logName) {

        //get the file_path
        String saving_path = mainDir + logName;

        //get the string to return
        String current_log = getTimeStamp() + " Starting log file.";

        //log the string
        IJ.log(current_log);

        //write the string to the file
        IJ.saveString(current_log, saving_path);

    }

    //method to get the time stamp
    public static String getTimeStamp() {

        //initialize the time stamp string
        String timeStamp = ""; 

        //get the current date
        Date date = new Date();

        //get the format for the date
        SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

        //add the date to the time stamp
        timeStamp = formatter.format(date);

        //return the time stamp
        return timeStamp;

    }




    //#endregion for Methods





}
