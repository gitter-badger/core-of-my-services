package converter;

import markdown.MDParser;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by igor on 27.02.15.
 */
public class MoodleConverterExec {

    public static void main(String[] args) throws Exception {

        MDParser parser = new MDParser("D:\\PSPTest.md");
        List<Question> questions =  parser.parse();

        converter.MoodleXMLGenerator generator = new converter.MoodleXMLGenerator("Test My Generator T3");
        generator.setQuestions(questions);

        String xml = generator.getMoodleXMLString();

        //Создаём объект файла
        File flt = new File("output.xml");
        //Объект, позволяющий осуществить запись в файл
        FileWriter wrt = new FileWriter(flt);
        wrt.append(xml);
        //Собственно здесь и происходит сама запись в файл
        wrt.flush();
        wrt.close();

        System.out.println("Done!");
    }
}
