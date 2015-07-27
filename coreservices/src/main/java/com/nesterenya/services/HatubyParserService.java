package com.nesterenya.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.*;

import com.nesterenya.modal.Ad;

public class HatubyParserService implements Parser {

	private static List<Ad> cachedAds;
	
	@Override
	public List<Ad> parse() {

		if(cachedAds!=null)
			return cachedAds;
		
		try {
			List<Ad> ads = new ArrayList<>();
			
			Random r = new Random();
			
			for (int i = 0; i < 3; i++) {

				String url = "http://www.hatu.by/ad/search/minsk/" + i + "/list.do";
				Document document = Jsoup.connect(url).get();
				Element element = document.getElementById("list");
				Elements elements = element.getElementsByClass("box-container");

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"dd.MM.yyyy");

				for (Element e : elements) {
					Ad ad = new Ad();
					Element a = e.getElementsByTag("h2").first()
							.getElementsByTag("a").first();
					ad.setAddress(a.text());

					String price = e.getElementsByClass("price").first().text();
					ad.setCost(price);

					Element note = e.getElementsByClass("note").first();
					Elements strongs = note.getElementsByTag("strong");

					String stringDate = strongs.get(0).text();
					Date date = simpleDateFormat.parse(stringDate);
					ad.setDate(date);

					String stringCnt = strongs.get(1).text();
					int roomCnt = Integer.parseInt(stringCnt);
					ad.setRoomCount(roomCnt);

					String discr = e.getElementsByAttributeValue("itemprop",
							"description").text();
					ad.setDescription(discr);

					String contact = e.getElementsByClass("psevdo").get(1)
							.text();
					ad.setContacts(contact);
					ads.add(ad);

					ad.setSource("http://www.hatu.by/");
					
					
					// TODO warning this is images for test UI generated random 
					switch(r.nextInt(4)) {
						case 0:
							break;
							
						case 1:
							ad.setImages(Arrays.asList("55b3634e8025742ac0d88"+r.nextInt(999)));
							break;
						case 2:
							ad.setImages(Arrays.asList("55b3634e8025742ac0d88"+r.nextInt(999),"55b3634e8025742ac0d88"+r.nextInt(999)));
							break;
						case 3:
							ad.setImages(Arrays.asList("55b3634e8025742ac0d88"+r.nextInt(999),"55b3634e8025742ac0d88"+r.nextInt(999),"55b3634e8025742ac0d88"+r.nextInt(999)));
							break;
					}
					ad.setViews(r.nextInt(1000));
					
					// cache
					cachedAds = ads;
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cachedAds;
	}

}
