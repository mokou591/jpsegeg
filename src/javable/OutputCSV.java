package javable;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import model.Word;
import util.CompareUtil;
import util.MyUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
            processAllFiles(currentDirectory);
        } else if (INSTRUCTION_HELP.equals(instruction)) {
            printHelp();
        }
    }



    public static void processAllFiles(String directory) throws IOException {
        //get string by file directory
        File currentFileDirectory = new File(directory);
        String content = MyUtil.getStringByFileDirectory(currentFileDirectory);

        //seg
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(content);

        //freq map
        HashMap<String, Word> freqMap = MyUtil.getWordFreqMap(tokens);

        //make word list
        List<Word> wordList = new ArrayList<Word>();
        wordList.addAll(freqMap.values());

        //sort
        Collections.sort(wordList, CompareUtil.getFrequencyDescWordComp());


        //output word frequency csv file
        MyUtil.writeAsCsv(wordList, directory + "/" +currentFileDirectory.getName()+ "_output.csv");

        //log
        String statisticInfo = MyUtil.getStatistic(wordList);
        System.out.println(statisticInfo);
    }

    public static void printHelp() {
        System.out.println(INFO_HELP);
    }

}
