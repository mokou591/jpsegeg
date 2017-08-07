package util;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import model.Word;

import java.io.*;
import java.util.*;

public class MyUtil {

    private static final Set<String> SUBTITLE_FILE_EXTENSION_SET = new HashSet<String>();

    static {
        SUBTITLE_FILE_EXTENSION_SET.add("ass");
        SUBTITLE_FILE_EXTENSION_SET.add("srt");
    }

    /**
     * write word frequency to a .csv file
     *
     * @param wordList
     * @param path
     * @throws IOException
     */
    public static void writeAsCsv(List<Word> wordList, String path) throws IOException {
        //output as csv
        File file = new File(path);
        FileWriter output = new FileWriter(file);
        output.append("freq,word,pron,type1\r\n");
        for (int i = 0; i < wordList.size(); ++i) {
            Word word = wordList.get(i);
            Token token = word.getToken();
            String line = word.getFrequency() +
                    "," + token.getSurface() +
                    "," + token.getPronunciation() +
                    "," + token.getPartOfSpeechLevel1();
            output.append(line + "\r\n");
        }
        output.close();
    }

    /**
     * load script from a file
     *
     * @param path
     * @return String
     * @throws IOException
     */
    public static String loadSerifuByLine(String path, String charset) throws IOException {
        InputStream is = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(is, charset));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
//            if (line.indexOf("0000,,") == -1) {
//                continue;
//            }
//            line = line.substring(line.indexOf("0000,,") + 6);
            buffer.append(line);
        }
        return buffer.toString();
    }

    /**
     * analyze tokens list
     *
     * @param tokens list
     * @return map with word string as key more info and as value
     */
    public static HashMap<String, Word> getWordFreqMap(List<Token> tokens) {
        HashMap<String, Word> freqMap = new HashMap<String, Word>(100000);
        for (Token token : tokens) {
            if (token.getReading().equals("*")) {//filter meaningless word
                continue;
            }
            if (token.getSurface().length() < 2) {
                continue;
            }
            String wordStr = token.getSurface();
            if (freqMap.get(wordStr) == null) {//not found
                Word newWord = new Word();
                newWord.setToken(token);
                newWord.setFrequency(1);
                freqMap.put(wordStr, newWord);
            } else {//found
                freqMap.get(wordStr).addCount();
            }
        }
        return freqMap;
    }

    public static String getStringByFileDirectory(File fileDirectory,Set<String> acceptExtension) throws IOException {
        StringBuilder content = new StringBuilder();
        for (File file : fileDirectory.listFiles()) {
            //filter
            String fileExtension = getFileExtension(file);
            if(!acceptExtension.contains(fileExtension)){
                //reject the file
                continue;
            }
            //process
            content.append(MyUtil.loadSerifuByLine(file.getPath(), "UTF-8")+"\n");
        }
        return content.toString();
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        String extension = "";
        if(fileName.contains(".")){
            //has extension
            extension = fileName.substring(fileName.lastIndexOf('.')+1);
        }else{
            //has no extension
        }
        return extension.toLowerCase();
    }

    public static String getStatistic(List<Word> wordList) {
        StringBuilder statisticInfo = new StringBuilder();
        statisticInfo.append("total words: "+getWordCount(wordList)+"\r\n");
        statisticInfo.append("total unique words: "+wordList.size()+"\r\n");
        return statisticInfo.toString();
    }

    public static int getWordCount(List<Word> wordList) {
        int wordCount = 0;
        for(Word word:wordList){
            wordCount+=word.getFrequency();
        }
        return wordCount;
    }

    public static void processAllFiles(String directory) throws IOException {
        //get string by file directory
        File currentFileDirectory = new File(directory);
        String content = MyUtil.getStringByFileDirectory(currentFileDirectory,MyUtil.getSubtitleFileExtensionSet());
        System.out.println("string length: "+content.length());
        System.out.println(content.substring(0,100));

        //seg
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(content);
        System.out.println("tokens size: "+tokens.size());

        //freq map
        HashMap<String, Word> freqMap = MyUtil.getWordFreqMap(tokens);
        System.out.println("freqMap size: "+freqMap.size());

        //make word list
        List<Word> wordList = new ArrayList<Word>();
        wordList.addAll(freqMap.values());

        //sort
        Collections.sort(wordList, CompareUtil.getFrequencyDescWordComp());


        //output word frequency csv file
        MyUtil.writeAsCsv(wordList, directory + "/" +"["+currentFileDirectory.getName()+ "][word frequency].csv");

        //log
        String statisticInfo = MyUtil.getStatistic(wordList);
        System.out.println(statisticInfo);
    }

    public static Set<String> getSubtitleFileExtensionSet() {
        return SUBTITLE_FILE_EXTENSION_SET;
    }
}
