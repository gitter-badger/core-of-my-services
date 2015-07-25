package com.nesterenya.controllers.rhouse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images/")
public class ImagesController {

	@ResponseBody
	@RequestMapping(value="/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(@PathVariable(value="id") String id) throws IOException {
		
		Random r = new Random();
		BufferedImage im2 = new BufferedImage(400,200, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = im2.getGraphics();
        g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
        g.fillRect(0,0,400,200);
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g.setFont(new Font("Arial",Font.BOLD,20));
        
        g.drawString("This is image: "+id,20,100);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write( im2,"png",os);
        
	    return os.toByteArray();
	}
}
