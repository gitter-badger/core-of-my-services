package com.nesterenya.coreservices.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by igor on 27.02.15.
 *
 */
public class MDParser {

    private final String text;

    public MDParser(String text) {
        this.text = text;
    }
    
    
    // TODO Плохое решение. При возможности продумать все получше
    public List<Question> parse() {
        List<Question> questions = new ArrayList<Question>();
        
        //try(Scanner sc = new Scanner(new File(path))) {
        try(Scanner sc = new Scanner(text)) {
        String lastString = "";
            while (sc.hasNext()) {
                String line;
                if(lastString.length()>0) {
                    line = lastString;
                    lastString = "";
                } else {
                    line = sc.nextLine().trim();
                }

                if(line.length()>1) {
                    if (Character.isDigit(line.charAt(0))) {

                        Question question = parseQuestion(line);
                        if(question!=null) {

                            // Parse answers
                            while (sc.hasNext()) {
                                String otherline = sc.nextLine().trim();
                                if(otherline.length()>0) {
                                    if (otherline.charAt(0) == '+') {

                                        Answer answer = parseAnswer(otherline);
                                        if(answer!=null) {
                                            question.addAnswer(answer);
                                        }
                                    } else {
                                        lastString = otherline;
                                        break;
                                    }
                                }
                            }

                            if(question.getAnswers().size()>0) {
                                questions.add(question);
                            }
                        }
                    }


                }
            }

        } 

        return questions;
    }

    private Question parseQuestion(String stringQuestion) {

        Question question = null;
        String[] t = stringQuestion.split(". ",2);
        if(t.length>1) {
            question = new Question(t[1].replace("**", "").trim());
        }
        return question;
    }

    private Answer parseAnswer(String stringAnswer) {
        // Учет русских Ха и латинских Икс
        Pattern p = Pattern.compile("\\[[xXхХ]+\\]");
        Matcher m = p.matcher(stringAnswer);
        int count = 0;
        while (m.find())
            count++;

        boolean isTrue = (count>0);

        // Разбить по ответу
        String[] strings = stringAnswer.split("\\[[xXхХ ]*\\]");
        Answer answer = null;
        if(strings.length>1) {
            answer = new Answer(strings[1].trim(),isTrue);
        }

        return answer;
    }

}
