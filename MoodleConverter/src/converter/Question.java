package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by igor on 27.02.15.
 *
 */
public class Question {
    final private String textOfQuestion;
    final private List<Answer> answers;
    final private int id;
    private static int globalId =0;

    public Question(String textOfQuestion, List<Answer> answers) {
        this.textOfQuestion = textOfQuestion;
        this.answers = answers;
        this.id = globalId++;
    }

    public Question(String textOfQuestion) {
        this.textOfQuestion = textOfQuestion;
        this.answers = new ArrayList<>();
        this.id = globalId++;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }



    public String getTextOfQuestion() {
        return textOfQuestion;
    }

    public String getNameOfQuestion() {
        return String.format(Locale.US,"question_%d", id);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isOneAnswerTrue() throws Exception {
        int count = countTrueAnswers();
        return (count==1);
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

    public float getPartOfTrueAnswer() throws Exception {

        if(this.isOneAnswerTrue()) {
            return 100;
        }
        return 100f/countTrueAnswers();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(textOfQuestion);
        sb.append("  [ ");
        for (Answer answer: answers)  {
            sb.append("{ ");
            sb.append(answer.getTextOfAnswer());
            sb.append(" : ");
            sb.append(answer.isTrue());
            sb.append(" };");
        }

        sb.append("  ] ");

        return sb.toString();
    }
}
