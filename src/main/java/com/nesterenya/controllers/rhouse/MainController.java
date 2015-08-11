package com.nesterenya.controllers.rhouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("/")
public class MainController {


    @Autowired
    Environment env;

    @ResponseBody
    @RequestMapping(value="/version", method = RequestMethod.GET)
    public ResponseEntity<String> getVersion() {
        String answer = String.format("{\"status\":\"OK\", \"currentVersion\":\"%s\"}", env.getProperty("app.version"));
        return new ResponseEntity<String>(answer, HttpStatus.OK);
    }
}

