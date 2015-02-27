package converter;

import java.util.Locale;

/**
 * Created by igor on 27.02.15.
 */
public class XMLPaterns {

    final  static private String quiz_pattern  =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<quiz>\n" +
                "<question type=\"category\">\n" +
                    "    <category>\n" +
                    "        <text>$course$/%s</text>\n" +
                    "    </category>\n" +
                "</question>\n" +
                "%s" +
            "\n</quiz>";

    private XMLPaterns() {
        /* This is static class */
    }

    public static String getQuiz(String categoryName, String body) {
        return String.format(Locale.US,quiz_pattern,categoryName,body);
    }

    public static String getQuestion(Question question) throws Exception {
        String head = getHeaderQuestion(
                question.getNameOfQuestion(),
                question.getTextOfQuestion(),
                question.isOneAnswerTrue());

        StringBuilder body = new StringBuilder();
        for(Answer answer : question.getAnswers()) {
            body.append(getAnswer(answer,question.getPartOfTrueAnswer()));
        }

        return String.format(Locale.US,question_pattern, head, body);
    }

    /**
     *
     * @param answer Объект ответа
     * @param fraction Какой процент имеет данный ответ (0 если ответ не верный)
     * @return
     */
    private static String getAnswer(Answer answer, float fraction) {
        return String.format(Locale.US,
                answer_pattern,
                // Если ответ правильный то вернуть вес ответа в процентах
                (answer.isTrue())?fraction:0,
                answer.getTextOfAnswer());
    }

    final static String answer_pattern =
            "    <answer fraction=\"%f\" format=\"html\">\n" +
            "      <text><![CDATA[<p>%s</p>]]></text>\n" + // Текст ответа
            "      <feedback format=\"html\">\n" +
            "        <text><![CDATA[<p></p>]]></text>\n" +  // Можно добавить feedback
            "      </feedback>\n" +
            "    </answer>";

    final static String question_pattern =
            "  <question type=\"multichoice\">\n" +
            "    %s\n" +
            "    %s\n" +
            "  </question>";


    private static String getHeaderQuestion(String questionName, String questionText, boolean isSingle) {
        return String.format(Locale.US,header_question_pattern,questionName,questionText,isSingle);
    }

    final static String header_question_pattern =
            "    <name>\n" +
            "      <text>%s</text>\n" +
            "    </name>\n" +
            "    <questiontext format=\"html\">\n" +
            "      <text><![CDATA[<p>%s</p>]]></text>\n" +
            "    </questiontext>\n" +
            "    <generalfeedback format=\"html\">\n" +
            "      <text><![CDATA[<p> </p>]]></text>\n" + // Отзыв пока пустой
            "    </generalfeedback>\n" +
            "    <defaultgrade>1.0000000</defaultgrade>\n" +
            "    <penalty>0.3333333</penalty>\n" +
            "    <hidden>0</hidden>\n" +
            "    <single>%s</single>\n" + // Только одни ответ?
            "    <shuffleanswers>true</shuffleanswers>\n" +
            "    <answernumbering>none</answernumbering>\n" +
            "    <correctfeedback format=\"html\">\n" +
            "      <text><![CDATA[<p>Ваш ответ верный.</p>]]></text>\n" +
            "    </correctfeedback>\n" +
            "    <partiallycorrectfeedback format=\"html\">\n" +
            "      <text><![CDATA[<p>Ваш ответ частично правильный.</p>]]></text>\n" +
            "    </partiallycorrectfeedback>\n" +
            "    <incorrectfeedback format=\"html\">\n" +
            "      <text><![CDATA[<p>Ваш ответ неправильный.</p>]]></text>\n" +
            "    </incorrectfeedback>\n" +
            "    <shownumcorrect/>";
}
