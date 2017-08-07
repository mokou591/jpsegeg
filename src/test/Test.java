package test;

import javable.OutputCSV;

import java.io.IOException;

public class Test {
    public static final String PATH_RESOURCE = "res";

    @org.junit.Test
    public void testOutputCSC() throws IOException {
        OutputCSV.processAllFiles(PATH_RESOURCE+"/clannad");
    }

}
