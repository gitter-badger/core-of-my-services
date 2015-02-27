import java.util.List;
import java.util.Locale;

/**
 * Created by igor on 27.02.15.
 */
public class Question {
    final private String textOfQuestion;
    final private List<Answer> answers;
    private static int id=0;

    public Question(String textOfQuestion, List<Answer> answers) {
        this.textOfQuestion = textOfQuestion;
        this.answers = answers;
        this.id++;
    }

    public String getTextOfQuestion() {
        return textOfQuestion;
    }

    public String getNameOfQuestion() {
        return String.format(Locale.US,"question_%d",id);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isOneAnswerTrue() throws Exception {
        int count = countTrueAnswers();
        return (count==1)?true:false;
    }

    public int countTrueAnswers() throws Exception {
        int count = 0;
        for(Answer answer : answers) {
            if(answer.isTrue()) {count++;}
        }

        if(count<1)
            throw new Exception("В тесте не задано правильных ответов");

        return count;
    }

    private static float[] trueBalls = {
            5,
            10,
            11.11111f,
            12.5f,
            14.28571f,
            16.66667f,
            20,
            25,
            30,
            33.33333f,
            40,
            50,
            60,
            66.66667f,
            70,
            75,
            80,
            83.33333f,
            90,
            100
    };

    public float getPartOfTrueAnswer() throws Exception {

        if(this.isOneAnswerTrue()) {
            return 100;
        }
        return 100f/countTrueAnswers();
    }
}
