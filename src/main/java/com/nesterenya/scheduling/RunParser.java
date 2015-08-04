package com.nesterenya.scheduling;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.nesterenya.modal.Ad;
import com.nesterenya.parsers.GohomeParser;
import com.nesterenya.parsers.ParsedResult;
import com.nesterenya.services.ImageService;
import com.nesterenya.services.TempStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RunParser {

    private Logger log = LoggerFactory.getLogger(RunParser.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000*3600)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(initialDelay=5000, fixedRate=1000*60*5)
    public void parseDataFrom() {

        log.info("Parsing start at : " + dateFormat.format(new Date()));
        // select all ads from gohome
        TreeSet<Ad> ads = new TreeSet<Ad>(new Comparator<Ad>() {
            @Override
            public int compare(Ad o1, Ad o2) {
                return o1.getAddress().trim().compareTo(o2.getAddress().trim());
            }
        });

        for(Ad ad : TempStorage.ads) {
            if(GohomeParser.BASE_URL.equals(ad.getSource())) {
                ads.add(ad);
            }
        }

        ImageService imageService = new ImageService();
        GohomeParser gohomeParser = new GohomeParser();

        ParsedResult result = gohomeParser.parse();

        for(Ad ad: result.getAds()) {
            if( ads.add(ad) ) {
                log.info("Add new record with address: : " + ad.getAddress());
                //save images {
                List<String> idInStorage = new ArrayList<>();
                for(String idImage : ad.getImages()) {
                    String newId = imageService.save(result.getImages().get(idImage));
                    idInStorage.add(newId);
                }

                ad.setImages(idInStorage);
                TempStorage.ads.add(ad);
            }
        }

        log.info("Parsing end at : " + dateFormat.format(new Date()));
        //System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
