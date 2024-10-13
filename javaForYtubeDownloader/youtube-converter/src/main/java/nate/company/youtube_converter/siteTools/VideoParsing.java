package nate.company.youtube_converter.siteTools;

import java.io.*;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VideoParsing {

    /*

       USEFUL for java you may transform the methos used as simple class in order to add
       "multi threading" support for multi-user trying to download

     */


    private static Logger logger = Logger.getLogger(VideoParsing.class.getName());
    private static Handler fileHandler;

    //retrieve the log file
    {
        try {
            fileHandler = new FileHandler("logVideoParsing.txt");
            Logger.getLogger("").addHandler(fileHandler);
            Logger.getLogger("nate.company.youtube_converter.logger").setLevel(Level.INFO);
        } catch (IOException e) {
            throw new AssertionError("The log file doesn't exist");
        }
    }



    public static String videoLogFilePath = "VideoConverter/outputfiles/journal_de_bord.txt";





    /**
     * This method waits for python stdout and display the result of the command.
     */
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
        //System.out.println("la commande à lancer : "+strBuilder.toString()+"\n");
        launchCommandWithOutput(strBuilder.toString(), true);

        //need to retrieve the path with the fileName and return it for downloadLink in frontend
        //maybe I can write it in a specific .txt file, and parse it to retrieve. It would be a way
        // of interacting java with python

    }

    /**
     *
     * this method retrieve a video name on the directory based on its
     * youtube url.
     *
     * @param videoLink
     * the link for the video you're interested in.
     *
     * @return
     *
     * the new name of the video associated to the video Link.
     */
    public static String retrieveVideoFileName(String videoLink){
        Objects.requireNonNull(videoLink);
        RandomAccessFile logBookFile;
        //retrive position in desktop
        var logBookPathPrecise = fileAbsolutePathPositionWithBeginning(videoLogFilePath);

        /* retrieve log file*/
        try {
            logBookFile = new RandomAccessFile(logBookPathPrecise, "r");
        } catch (FileNotFoundException e) {
            //System.out.println("le fichier de log est introuvable");
            throw new AssertionError("Path for logbook, is wrong");
        }
        System.out.println(" le fichier de log a été trouvé ! ");

        /*can be improved by using user's name to look for their first occurence + can use a hashMap. in order to keep.
        file's position in the hierarchy (not sure cause the data are written by python, not java...).
        may use the same method as scala tp3 : each file is written on a fixed number of character, let's
        say 400. If one file is under this amount, you just add padding that will be overlooked later.
        Padding is represented by "*" symbole.
        Warning: it has to match with the python value in the function "startSave()"
         */
        int maxLinkFileSize = 400;
        byte[] bytesForFileReader = new byte[maxLinkFileSize];

        String videoEncoded;
        /*
        read the byte for the file
         */
        var linkIsFound = false;

        String videoName = null;
        do {
            try {
                System.out.println(" on lit le fichier ...");
                var endOfFile = logBookFile.read(bytesForFileReader);
                //retrive the string read

                videoEncoded = new String(bytesForFileReader);
                var LinkIsFound = false;

                //video found
                if(videoEncoded.contains(videoLink)){
                    linkIsFound = true;
                    //retrieve only the title of the video
                    var videoAndName = videoEncoded.split("::");

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
        return null;


    }

    public static void startingPointForDownload(String[] args) {
        var filePath = fileAbsolutePathPositionWithBeginning("VideoConverter/python_script_download/yDownloaderForJava.py");
        launchFile(filePath, args);
//        System.out.println("La vidéo retrouvée a un fichier qui s'intitule :"+retrieveVideoFileName(
//                "https://www.youtube.com/watch?v=sML_1FMTn6c"));
    }

    public static void main(String args[]){
        var filePath = retrieveVideoFileName("https://www.youtube.com/watch?v=sML_1FMTn6c");
    }
}
