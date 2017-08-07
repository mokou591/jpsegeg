package executable;

import util.MyUtil;

import java.io.IOException;

public class OutputCSV {

    private static final String INSTRUCTION_ALL = "-a";
    private static final String INSTRUCTION_HELP = "-h";

    private static final String INFO_HELP = "options:\r\n" +
            "\t" + INSTRUCTION_HELP + "\tshow information for help\r\n" +
            "\t" + INSTRUCTION_ALL + "\tprocess all files in this directory";

    public static void main(String[] args) throws IOException {
        // check args
        if (null == args) {
            printHelp();
            System.exit(0);
        }
        //switch args
        String instruction = args[0];
        if (INSTRUCTION_ALL.equals(instruction)) {
            String currentDirectory = "";
            MyUtil.processAllFiles(currentDirectory);
        } else if (INSTRUCTION_HELP.equals(instruction)) {
            printHelp();
        }
    }

    public static void printHelp() {
        System.out.println(INFO_HELP);
    }

}
