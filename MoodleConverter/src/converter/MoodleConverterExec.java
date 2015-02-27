package converter;

import markdown.MDParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by igor on 27.02.15.
 */
public class MoodleConverterExec {

    public static void main(String[] args) throws Exception {

        MDParser parser = new MDParser("/home/igor/Git/md_tmp.md");
        List<Question> questions =  parser.parse();

        for(Question question: questions) {
            System.out.println(question);
        }

        /*
        converter.MoodleXMLGenerator generator = new converter.MoodleXMLGenerator("Test My Generator T2");

        // This test
        converter.Answer[] answers1 = {new converter.Answer("A1",false),new converter.Answer("A2",false),new converter.Answer("A3",true)};
        converter.Question q1 = new converter.Question("Who are you?", Arrays.asList(answers1));

        // Multi
        converter.Answer[] answers2 = {new converter.Answer("AA1", true),new converter.Answer("AA2",false),new converter.Answer("AA3",true)};
        converter.Question q2 = new converter.Question("What are you?", Arrays.asList(answers2));

        // Three
        converter.Answer[] answers3 = {new converter.Answer("AAA1", true),new converter.Answer("AAA2",false),new converter.Answer("AAA3",true),new converter.Answer("AAA4",false),new converter.Answer("AAA5",true)};
        converter.Question q3 = new converter.Question("are you right?", Arrays.asList(answers3));

        generator.addQuestion(q1);
        generator.addQuestion(q2);
        generator.addQuestion(q3);

        String xml = generator.getMoodleXMLString();
        System.out.println(xml);*/
    }
}
