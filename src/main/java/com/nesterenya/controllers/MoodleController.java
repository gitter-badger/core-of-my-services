package com.nesterenya.controllers;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.nesterenya.coreservices.parser.MDParser;
import com.nesterenya.coreservices.parser.MoodleXMLGenerator;
import com.nesterenya.coreservices.parser.Question;

@RestController
@RequestMapping("/moodle")
public class MoodleController {

	final static Map<UUID, String> results = new HashMap<UUID, String>();

	@RequestMapping(value = "/parse", method = RequestMethod.POST)
	public ModelAndView parse(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "category", defaultValue = "None") String caterory) {

		MDParser parser = new MDParser(text);
		List<Question> questions = parser.parse();

		MoodleXMLGenerator generator = new MoodleXMLGenerator(caterory);
		generator.setQuestions(questions);

		String xml = "";
		try {
			xml = generator.getMoodleXMLString();
		} catch (Exception e) {
			xml = "";
			// TODO Добавить логгер
			// e.printStackTrace();
		}
		
		
		UUID uuid = UUID.randomUUID();
		results.put(uuid, xml);
		
		String url = String.format("/moodle/result/%s", uuid);
		return new ModelAndView(new RedirectView(url, true));
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public void result(@PathVariable(value="id") String id, HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setHeader("Content-disposition", "attachment; filename=" + "yourquestions.xml" );
		
		String xml = results.get(UUID.fromString(id));
		
		try {
			response.getWriter().append(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
