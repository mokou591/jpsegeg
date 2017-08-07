package util;

import com.atilika.kuromoji.ipadic.Token;
import model.Word;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class MyUtil {

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
            if (token.getSurface().length() < 2) {
                continue;
            }
            if (token.getReading().equals("*")) {//filter meaningless word
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

    public static String getStringByFileDirectory(File fileDirectory) throws IOException {
        StringBuilder content = new StringBuilder();
        for (File file : fileDirectory.listFiles()) {
            content.append(MyUtil.loadSerifuByLine(file.getPath(), "Unicode"));
        }
        return content.toString();
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
}
