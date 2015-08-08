package com.nesterenya.scheduling;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.nesterenya.modal.Ad;
import com.nesterenya.parsers.GohomeParser;
import com.nesterenya.parsers.ParsedResult;
import com.nesterenya.services.ImageService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RunParser {

    @Autowired
    private Datastore storage;

    @Autowired
    ImageService imageService;

    private Logger log = LoggerFactory.getLogger(RunParser.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000*3600)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(initialDelay=1000*60*10, fixedRate=1000*60*60)
    public void parseDataFrom() {

        log.info("Parsing start at : " + dateFormat.format(new Date()));
        // select all ads from gohome
        TreeSet<Ad> ads = new TreeSet<Ad>(new Comparator<Ad>() {
            @Override
            public int compare(Ad o1, Ad o2) {
                return o1.getAddress().trim().compareTo(o2.getAddress().trim());
            }
        });


        Query<Ad> query = storage.createQuery(Ad.class).field("source").equal(GohomeParser.BASE_URL);

        for(Ad ad : query.asList()) {
            if(GohomeParser.BASE_URL.equals(ad.getSource())) {
                ads.add(ad);
            }
        }


        GohomeParser gohomeParser = new GohomeParser();

        ParsedResult result = gohomeParser.parse();

        for(Ad ad: result.getAds()) {
            if( ads.add(ad) ) {
                log.info("Add new record with address: : " + ad.getAddress());
                //save images {
                List<String> idInStorage = new ArrayList<>();
                for(String idImage : ad.getImages()) {

                    byte[] img = result.getImages().get(idImage);
                    if(img != null) {
                        String newId = imageService.save(img);
                        idInStorage.add(newId);
                    }
                }
                ad.setImages(idInStorage);

                storage.save(ad);
            }
        }

        log.info("Parsing end at : " + dateFormat.format(new Date()));
        //System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
