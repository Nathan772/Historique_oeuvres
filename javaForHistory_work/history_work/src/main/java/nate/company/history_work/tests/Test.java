package nate.company.youtube_converter.tests;

import nate.company.youtube_converter.siteTools.VideoParsing;

import java.io.*;
import java.util.Objects;
import java.util.logging.Logger;


public class Test {
    /**
     * This method waits for python stdout and display the result of the command.
     */

    private static Logger logger = Logger.getLogger(VideoParsing.class.getName());

    private static void waitAndDisplay(Process process, StringBuilder terminalOutput){
        Objects.requireNonNull(process, "the process you're waiting for cannot be null");
        Objects.requireNonNull(terminalOutput);
        String line;
        String lineErr;

        //from stack : https://stackoverflow.com/questions/3403226/how-to-run-linux-commands-in-java
        //retrieve terminal output
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        //System.out.println("il y a des lignes à lire : ");
        try {
            //wait until the end
            while(process.isAlive()) {
                //display stdout
                while ((line = inputReader.readLine()) != null) {
                    //System.out.println("il y a une ligne : " + line + "+ à lire");
                    //add each output lines
                    System.out.println(line);
                    terminalOutput.append(line);
                }
                //display error (stderr)
                while ((lineErr = stdErrorReader.readLine()) != null) {
                    System.out.println(lineErr);
                    terminalOutput.append(line);
                }
            }
        }
        catch (IOException e) {
            throw new AssertionError("Use of incorrect command line " + e);
        }
        finally {
            //kills everything
            if(process.isAlive())
                process.destroy();
        }
    }
    /**
     * this method enables to launch a command expecting an output.
     *
     * @param terminalCommand
     * the command used.
     * @param isFile
     * precise if the command have to be handle as a python file.
     *
     * @return
     * the string output of the terminal.
     */
    //more than 20 lines but unavoidable.
    private static String launchCommandWithOutput(String terminalCommand, boolean isFile){
        Objects.requireNonNull(terminalCommand, "command used cannot be null.");
        Process process = null;
        var terminalOutput = new StringBuilder();
        String line;
        String lineErr;
        try {
            var command = new String[3];
            //shell call
            command[0] = "sh";
            command[1] = "-c";
            //commande expected

            command[2] = terminalCommand;//commandFileFind;
            process = Runtime.getRuntime().exec(command);

            //from stack : https://stackoverflow.com/questions/3403226/how-to-run-linux-commands-in-java
            //how to handle it ??
            //process.waitFor(); //seems useless and cause of errors (deprecated)
            waitAndDisplay(process, terminalOutput);

        } catch (IOException e) {
            throw new AssertionError("Use of incorrect command line " + e);
        }  finally {
            if(process != null && process.isAlive())
                process.destroy();
        }
        return terminalOutput.toString();

    }
    /*
    deprecated,old version
    private static String launchCommandWithOutput(String terminalCommand, boolean isFile){
        Objects.requireNonNull(terminalCommand, "command used cannot be null.");
        Process process = null;
        var terminalOutput = new StringBuilder();
        String line;
        String lineErr;
        try {
            var command = new String[3];
            //shell call
            command[0] = "sh";
            command[1] = "-c";
            //commande expected

            command[2] = terminalCommand;//commandFileFind;
            process = Runtime.getRuntime().exec(command);

            //from stack : https://stackoverflow.com/questions/3403226/how-to-run-linux-commands-in-java
            //how to handle it ??
            if(!isFile) {
                //should only wait for commands that don't wait
                // user input
                process.waitFor();
            }

            if(isFile) {
                //retrieve terminal output
//                BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                BufferedReader stdErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                System.out.println("il y a des lignes à lire : ");
//                //display stdout
//                while ((line = inputReader.readLine()) != null) {
//                    System.out.println("il y a une ligne : " + line + "+ à lire");
//                    //add each output lines
//                    System.out.println(line);
//                    terminalOutput.append(line);
//                }
//                //display error (stderr)
//                while ((lineErr = stdErrorReader.readLine()) != null) {
//                    System.out.println(lineErr);
//                }
                waitAndDisplay(process, terminalOutput);
            }
        } catch (IOException e) {
            throw new AssertionError("Use of incorrect command line " + e);
        } catch (InterruptedException e) {
            throw new AssertionError("Process waiter for terminal command interrupted");
        } finally {
            if(process != null && process.isAlive())
                process.destroy();
        }
        return terminalOutput.toString();

    }*/

    /**
     * this method enables to find the absolute position for a specific file.
     * It just retrieves the first occurence of this file name.
     * @param fileName
     * the name of the file you want to find.
     * @return
     * the file path for the absolute position.
     */
    private static String fileAbsolutePathPosition(String fileName) {
        Objects.requireNonNull(fileName, "File name cannot be null");
        //use to find the path for the file
        var commandFileFind = "find ~ -name " + fileName + " -print -quit";
        return launchCommandWithOutput(commandFileFind, false);
    }

    /**
     * this method enables to find the absolute position for a specific file.
     * It just retrieves the first occurence of this file name.
     * @param filePathBeginning
     * the name of the file you want to find with the beginning of the path.
     * @return
     * the file path for the absolute position.
     */
    private static String fileAbsolutePathPositionWithBeginning(String filePathBeginning) {
        Objects.requireNonNull(filePathBeginning, "File name cannot be null");
        //use to find the path for the file
        var commandFileFind = "find ~ -path \\*" + filePathBeginning + " -print -quit";
        return launchCommandWithOutput(commandFileFind, false);
    }


    /**
     *
     * this method runs the file "filePath" with their arguments.
     * @param filePath
     * path to the file you want to run.
     * @param arguments
     * the argument you want to use with the file as an argument
     */
    private static void launchFile(String filePath, String[] arguments){
        Objects.requireNonNull(filePath, "File's path name cannot be null");
        Objects.requireNonNull(arguments);
        if(!filePath.startsWith("./") && !filePath.startsWith("/")){
            throw new IllegalStateException("File's path must start with either '/' or './' to be executed !");
        }
        //launch the file
        var strBuilder = new StringBuilder();
        strBuilder.append(filePath);
        strBuilder.append(" ");
        for (String argument : arguments) {
            strBuilder.append(argument);
            strBuilder.append(" ");
        }
        System.out.println("la commande à lancer : "+strBuilder.toString()+"\n");
        launchCommandWithOutput(strBuilder.toString(), true);

    }

    /**
     *
     * this method retrieve a video name on the directory based on its
     * youtube url.
     *
     * @param logBook
     * the file where you retrieve file that contains path name
     * @param videoLink
     * the link for the video you're interested in.
     *
     * @return
     *
     * the new path where the file is in.
     */
    public static String retrieveVideoFileName(String logBook, String videoLink){
        Objects.requireNonNull(logBook);
        Objects.requireNonNull(videoLink);
        RandomAccessFile logBookFile;

        try {
            logBookFile = new RandomAccessFile("VideoConverterPersonalFiles/journal_de_bord", "r");
        } catch (FileNotFoundException e) {
            throw new AssertionError("Path for logbook, is wrong");
        }

        /*can be improved by using user's name to look for their first occurence + can use a hashMap. in order to keep.
        file's position in the hierarchy (not sure cause the data are written by python, not java...).
        may use the same method as scala tp3 : each file is written on a fixed number of character, let's
        say 150. If one file is under this amount, you just add padding that will be overlooked later.
         */
        int maxLinkFileSize = 150;
        byte[] bytesForFileReader = new byte[maxLinkFileSize];

        String videoEncoded;
        /*
        read the byte for the file
         */
        var linkIsFound = false;

        String videoName = null;
        do {
            try {
                var endOfFile = logBookFile.read(bytesForFileReader);
                //retrive the string read

                videoEncoded = new String(bytesForFileReader);
                var LinkIsFound = false;

                //video found
                if(videoEncoded.contains(videoLink)){
                    linkIsFound = true;
                    //retrieve only the title of the video
                    var videoAndName = videoEncoded.split(":");

                    //remove offset element
                    videoName = videoAndName[1].split("\\*")[0];
                }

                // file not found but end of file
                if(endOfFile == -1 && !linkIsFound) {
                    logger.info("Vidéo non trouvée parmi les vidéos téléchargées du log");
                    break;
                }
            } catch (IOException e) {
                throw new AssertionError("lack of bytes to read by byteReader");
            }
        }while(!linkIsFound);

        if(videoName != null) {

            logger.info("Vidéo trouvée parmi les vidéos téléchargées du log : "+videoName);
            return videoName;

        }

        logger.info("Vidéo non trouvée parmi les vidéos téléchargées du log");
        return "NomParDefautFichierNonTrouve.mp3";


    }

    /*public static void main(String args[]){
        var filePath = fileAbsolutePathPositionWithBeginning("VideoConverter/python_script_download/yDownloaderForJava.py");
        launchFile(filePath, args);
    }*/




}
