package com.nesterenya.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageService {

	final static Map<String, byte[]> images = new HashMap<>();

	public byte[] getImage(String id) {
		return images.get(id);
	}

	public String save(URL url) {
		// URL url = new URL("http://www.avajava.com/images/avajavalogo.jpg");
		try {
			BufferedImage image = ImageIO.read(url);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			ObjectId id = ObjectId.get();
			images.put(id.toHexString(), imageInByte);
			return id.toHexString();
			
		} catch (Exception e) {
			// TODO correct log
			return "Error saving photo from url";
		}

	}

	public String save(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				ObjectId id = ObjectId.get();
				images.put(id.toHexString(), bytes);
				return id.toHexString();
			} catch (Exception e) {
				return "You failed to upload " + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + " because the file was empty.";
		}
	}

	public byte[] generateImage(String id) throws IOException {
		Random r = new Random();
		BufferedImage im2 = new BufferedImage(400, 150,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = im2.getGraphics();
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		g.fillRect(0, 0, 400, 200);
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		g.setFont(new Font("Arial", Font.BOLD, 20));

		g.drawString("Image not found: " + id, 0, 75);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(im2, "png", os);

		return os.toByteArray();
	}

}
