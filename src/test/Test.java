package test;

import util.MyUtil;

import java.io.IOException;

public class Test {
    public static final String PATH_RESOURCE = "res";

    @org.junit.Test
    public void testOutputCSC_1() throws IOException {
        MyUtil.processAllFiles(PATH_RESOURCE+"/clannad");
    }

    @org.junit.Test
    public void testOutputCSC_2() throws IOException {
        MyUtil.processAllFiles(PATH_RESOURCE+"/gintama 1-300/1-10");
    }

    @org.junit.Test
    public void testOutputCSC_3() throws IOException {
        MyUtil.processAllFiles(PATH_RESOURCE+"/Jinrui wa Suitai Shimashita");
    }

    @org.junit.Test
    public void testOutputCSC_4() throws IOException {
        MyUtil.processAllFiles(PATH_RESOURCE+"/nonnonbiyori");
    }

    @org.junit.Test
    public void testOutputCSC_5() throws IOException {
        MyUtil.processAllFiles(PATH_RESOURCE+"/nonnonbiyori repeat");
    }
}
