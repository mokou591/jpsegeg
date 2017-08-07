package model;

import com.atilika.kuromoji.ipadic.Token;

public class Word{
    private Token token;
    private Integer frequency=0;

    public void addCount(){
        ++frequency;
    }


    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
