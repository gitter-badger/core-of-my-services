import java.util.List;

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
        return String.format("question_%d",id);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    // TODO пока всегда возращает один ответ
    public boolean isOneAnswerTrue() {
        return true;
    }

    // TODO пока всегда возращает 100%
    public float getPartOfTrueAnswer() {
        return 100;
    }
}
