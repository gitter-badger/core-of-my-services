package com.nesterenya.services;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import com.nesterenya.modal.ImageEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageService {

    @Autowired
    Datastore storage;

    private Logger log = LoggerFactory.getLogger(ImageService.class);

    public byte[] getImage(String id) {
        ObjectId oid = new ObjectId(id);
        Query<ImageEntity> find = storage.createQuery(ImageEntity.class).field("_id").equal(oid);
        ImageEntity image = find.get();
        return image != null ? image.getBytes() : generateImage(id);
    }

    public static byte[] imageToBytes(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }


    public String save(byte[] imageInByte) {
        ImageEntity entity = dbSave(imageInByte);
        return entity.getId().toHexString();
    }

    public String save(URL url) throws Exception {
            byte[] imageInByte = ImageService.imageToBytes(url);
            ImageEntity entity = dbSave(imageInByte);
            return entity.getId().toHexString();
    }

    public String save(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            ImageEntity entity = dbSave(bytes);
            return entity.getId().toHexString();
        } else {
            throw new Exception("You failed to upload " + " because the file was empty.");
        }
    }

    private ImageEntity dbSave(byte[] bytes) {
        ImageEntity entity = new ImageEntity();
        entity.setBytes(bytes);
        storage.save(entity);

        return entity;
    }

    public byte[] generateImage(String id) {
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
        try {
            ImageIO.write(im2, "png", os);
        } catch (IOException e) {
            log.error("Image generation error", e);
            e.printStackTrace();
        }

        return os.toByteArray();
    }

}
