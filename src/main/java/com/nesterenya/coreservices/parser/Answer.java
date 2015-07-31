package com.nesterenya.coreservices.parser;

/**
 * Created by igor on 27.02.15.
 */
public class Answer {
    final private String textOfAnswer;
    final private boolean isTrue;

    public Answer(String textOfAnswer, boolean isTrue) {
        this.textOfAnswer = textOfAnswer;
        this.isTrue = isTrue;
    }

    public String getTextOfAnswer() {
        return textOfAnswer;
    }

    public boolean isTrue() {
        return isTrue;
    }
}
