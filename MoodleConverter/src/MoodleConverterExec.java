import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by igor on 27.02.15.
 */
public class MoodleConverterExec {

    public static void main(String[] args) throws Exception {

        MoodleXMLGenerator generator = new MoodleXMLGenerator("Test My Generator");

        // This test
        Answer[] answers1 = {new Answer("A1",false),new Answer("A2",false),new Answer("A3",true)};
        Question q1 = new Question("Who are you?", Arrays.asList(answers1));

        // Multi
        Answer[] answers2 = {new Answer("AA1", true),new Answer("AA2",false),new Answer("AA3",true)};
        Question q2 = new Question("What are you?", Arrays.asList(answers2));

        // Three
        Answer[] answers3 = {new Answer("AAA1", true),new Answer("AAA2",false),new Answer("AAA3",true),new Answer("AAA4",false),new Answer("AAA5",true)};
        Question q3 = new Question("are you right?", Arrays.asList(answers3));

        generator.addQuestion(q1);
        generator.addQuestion(q2);
        generator.addQuestion(q3);

        String xml = generator.getMoodleXMLString();
        System.out.println(xml);
    }
}
