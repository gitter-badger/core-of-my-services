import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 27.02.15.
 *
 * Пока только умеет создавать вопроссы с одним правильным ответом
 * и с несколькими
 *
 */
public class MoodleXMLGenerator {

    final private String categoryName;
    final private List<Question> questions = new ArrayList<Question>();

    public MoodleXMLGenerator(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getMoodleXMLString() throws Exception {
        String result;

        StringBuilder body = new StringBuilder();
        for(Question question : questions) {
            body.append(XMLPaterns.getQuestion(question));
            body.append("\n\n");
        }

        result = XMLPaterns.getQuiz(categoryName,body.toString());

        return result;
    }
}
