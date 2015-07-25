package com.nesterenya.controllers.rhouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("/images/")
public class ImagesController {

	@Resource
    private Environment env;
	
	@ResponseBody
	@RequestMapping(value="/test/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(@PathVariable(value="id") String id) throws IOException {
		return getRandomImage(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] get(@PathVariable(value="id") String id) throws IOException {
		byte[] image = images.get(id);
		if(image!=null)
			return image;
		else 
			return getRandomImage(id);
	}
	
	private byte[] getRandomImage(String id) throws IOException {
		Random r = new Random();
		BufferedImage im2 = new BufferedImage(400,200, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = im2.getGraphics();
        g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
        g.fillRect(0,0,400,200);
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g.setFont(new Font("Arial",Font.BOLD,20));
        
        g.drawString("Image not found: "+id,20,100);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write( im2,"png",os);
        
	    return os.toByteArray();
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

	final static Map<String, byte[]> images = new HashMap<>();
	
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                
                
                //BufferedOutputStream stream =
                //        new BufferedOutputStream(new FileOutputStream(new File(name)));
                //stream.write(bytes);
                //stream.close();
                
                ObjectId id = ObjectId.get();
                images.put(id.toHexString(), bytes);
                
                return id.toHexString();
            } catch (Exception e) {
                return "You failed to upload "  + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " +  " because the file was empty.";
        }
    }
	
}
