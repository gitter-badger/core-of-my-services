package com.nesterenya.scheduling;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import com.nesterenya.modal.Ad;
import com.nesterenya.services.GohomeParser;
import com.nesterenya.services.TempStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.BasicMarker;
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

    @Scheduled(initialDelay=5000, fixedRate=1000*3600)
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

        GohomeParser gohomeParser = new GohomeParser();
        List<Ad> parsedAds = gohomeParser.parse();

        for(Ad ad: parsedAds) {
            if( ads.add(ad) ) {
                log.info("Add new record with address: : " + ad.getAddress());
                TempStorage.ads.add(ad);
            }
        }

        log.info("Parsing end at : " + dateFormat.format(new Date()));
        //System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
