import java.util.Date;
import java.text.SimpleDateFormat;
import ij.IJ;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class logObject {
    
    //#region ######################## VARIABLES ########################

    //the parent directory
    private String logDir;

    //the name of the log file
    private String logName;

    //the script name
    private String scriptName;

    //the log flag
    private boolean logFlag;

    //the print flag 
    private boolean printFlag;

    //#endregion  ################## VARIABLES ########################

    //#region ###################### CONSTRUCTOR ######################

    //The class constructor
    public logObject(String logDir, String logName, String scriptName, boolean logFlag, boolean printFlag) {

        //set the log directory
        this.logDir = logDir;

        //set the log file
        this.logName = logName;

        //set the script name
        this.scriptName = scriptName;

        //set the log flag
        this.logFlag = logFlag;

        //set the print flag
        this.printFlag = printFlag;

    }

    //#endregion ################# CONSTRUCTOR ######################

    //#region ###################### METHODS ##########################

    //method to get the date and time for files
    public String getDateTimeForFiles() {

        //get the string to return
        String returning_date_time = getPromptStart();

        //return the string
        return returning_date_time;

    }

    //method to get the prompt start
    public String getPromptStart() {

        //initialize the prompt start string
        String promptStart = "";

        //get the current date
        Date date = new Date();

        //get the format for the date
        SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

        //add the date to the time stamp
        promptStart = formatter.format(date);

        //return the prompt start
        return promptStart;

    }

    //method to get the log path
    public String getLogPath() {

        //make sure that the directory ends with /
        if (!logDir.endsWith("/")) {

            //add the / to the end of the directory
            logDir = logDir + "/";

        }

        //make sure the directory is not just /
        if (logDir.equals("/")) {

            //set the directory to the current directory
            logDir = IJ.getDirectory("current");

            //return an empty string
            return "";

        }

        //get the log path
        String log_path = logDir + logName;

        //return the log path
        return log_path;

    }

    //method to start the log
    public void startLog() {

        //try to make the file
        try {

            //if any of the print flag or the log flag are true
            if (printFlag || logFlag) {

                //get the log path
                String log_path = getLogPath();

                //get the prompt start
                String prompt_start = getPromptStart();

                //get the string to return
                String messageString = prompt_start + " " + scriptName + " started.";

                //if the print flag is true
                if (printFlag) {

                    //print the string
                    IJ.log(messageString);

                }

                //if the log flag is true
                if (logFlag) {

                    //make the file writer
                    FileWriter file_writer = new FileWriter(log_path);

                    //make the buffered writer
                    BufferedWriter buffered_writer = new BufferedWriter(file_writer);

                    //write the string to the file
                    buffered_writer.write(messageString);

                    //add the new line
                    buffered_writer.newLine();

                    //close the buffered writer
                    buffered_writer.close();

                }

            }
        
        } catch (IOException e) {

            //print the error
            IJ.log("Error: " + e);

        }

    }

    //method to add a log
    public void addLog(String message) {

        //if the print flag is true or the log flag is true
        if (printFlag || logFlag) {

            //get the log path
            String log_path = getLogPath();

            //get the prompt start
            String prompt_start = getPromptStart();

            //get the string to return
            String messageString = prompt_start + " " + message;

            //if the print flag is true
            if (printFlag) {

                //print the string
                IJ.log(messageString);

            }

            //if the log flag is true
            if (logFlag) {

                //try to make the file
                try {

                    //make the file writer
                    FileWriter file_writer = new FileWriter(log_path, true);

                    //make the buffered writer
                    BufferedWriter buffered_writer = new BufferedWriter(file_writer);

                    //write the string to the file
                    buffered_writer.write(messageString);

                    //add the new line
                    buffered_writer.newLine();

                    //close the buffered writer
                    buffered_writer.close();

                } catch (IOException e) {

                    //print the error
                    IJ.log("Error: " + e);

                }

            }

        }

    }

    //method to end the log
    public void endLog() {

        //if the print flag is true or the log flag is true
        if (printFlag || logFlag) {

            //get the log path
            String log_path = getLogPath();

            //get the prompt start
            String prompt_start = getPromptStart();

            //get the string to return
            String messageString = prompt_start + " " + scriptName + " ended.";

            //if the print flag is true
            if (printFlag) {

                //print the string
                IJ.log(messageString);

            }

            //if the log flag is true
            if (logFlag) {

                //try to make the file
                try {

                    //make the file writer
                    FileWriter file_writer = new FileWriter(log_path, true);

                    //make the buffered writer
                    BufferedWriter buffered_writer = new BufferedWriter(file_writer);

                    //write the string to the file
                    buffered_writer.write(messageString);

                    //add the new line
                    buffered_writer.newLine();

                    //close the buffered writer
                    buffered_writer.close();

                } catch (IOException e) {

                    //print the error
                    IJ.log("Error: " + e);

                }

            }

        }

    }

    //#endregion ################### METHODS ##########################

}
