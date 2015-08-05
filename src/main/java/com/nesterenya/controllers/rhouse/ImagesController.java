package com.nesterenya.controllers.rhouse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nesterenya.services.ImageService;

/**
 * Изображения хранятся в БД
 * из-за этого мы явно теряем по скорости, но выигрываем по гибкости,
 * при переносе не нужно думать о файлах
 *
 * Рассмотреть 2 возможные ситуации:
 * если скорости не будет хватать, хранение изображений узкое место, и нужно либо кешировать
 * либо хранить изображения по другому
 */
@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("/images/")
public class ImagesController {

	@Autowired
	ImageService service;
	
	@ResponseBody
	@RequestMapping(value="/test/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(@PathVariable(value="id") String id) throws IOException {
		return service.generateImage(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] get(@PathVariable(value="id") String id) throws IOException {
		byte[] image = service.getImage(id);
		return (image==null)?service.generateImage(id):image;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST, produces = "application/json")
    public @ResponseBody
	ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file){

		String answerPattern = "{\"status\":\"%s\", \"result\":\"%s\"}";
		String json;
		try {
			String id = service.save(file);
			json = String.format(answerPattern, "OK", id);
		} catch (Exception ex) {
			json = String.format(answerPattern, "ERROR", ex.getMessage());
			ex.printStackTrace();
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);

    }

}
