package util;

import model.Word;

import java.util.Comparator;

public class CompareUtil {
    private static final Comparator<Word> frequencyAscWordComp = new Comparator<Word>() {
        public int compare(Word o1, Word o2) {
            return o1.getFrequency() - o2.getFrequency();
        }
    };
    private static final Comparator<Word> frequencyDescWordComp = new Comparator<Word>() {
        public int compare(Word o1, Word o2) {
            return o2.getFrequency() - o1.getFrequency();
        }
    };

    public static Comparator<? super Word> getFrequencyAscWordComp() {
        return frequencyAscWordComp;
    }

    public static Comparator<? super Word> getFrequencyDescWordComp() {
        return frequencyDescWordComp;
    }
}
