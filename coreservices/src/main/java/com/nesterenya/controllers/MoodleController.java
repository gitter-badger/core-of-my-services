package com.nesterenya.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.coreservices.parser.MDParser;
import com.nesterenya.coreservices.parser.MoodleXMLGenerator;
import com.nesterenya.coreservices.parser.Question;

@RestController
public class MoodleController {

	//static String xmlresult = "";
	
	@RequestMapping(value= "/moodle/parse",  method = RequestMethod.POST)
    public void  parse(@RequestParam(value="text", defaultValue="") String text, 
    		@RequestParam(value="category", defaultValue="None") String caterory,
    		Writer responseWriter/*, HttpServletResponse response*/) {
		
		MDParser parser = new MDParser(text);
	    List<Question> questions = parser.parse();

	    MoodleXMLGenerator generator = new MoodleXMLGenerator(caterory);
	    generator.setQuestions(questions);
	    
	    String xml = "";
	    try {
			xml = generator.getMoodleXMLString();
		} catch (Exception e) {
			xml = "";
			//TODO Добавить логгер
			//e.printStackTrace();
		}
	    	
	    
	    try {
			responseWriter.append(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
