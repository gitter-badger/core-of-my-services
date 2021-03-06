package com.nesterenya.parsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nesterenya.services.ImageService;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nesterenya.modal.Ad;

public class GohomeParser implements Parser {
	private Logger log = LoggerFactory.getLogger(GohomeParser.class); 
	final public static String BASE_URL = "http://gohome.by";
	final static String SEARCH_URL = "http://gohome.by/rent/flat/gomel";
	

	@Override
	public ParsedResult parse() {

		ParsedResult result = new ParsedResult();
		try {
			
			Document document = Jsoup.connect(SEARCH_URL).get();

	        Element items = document.getElementById("list_items");
	        Elements rows = items.getElementsByClass("head");

	        List<String> links = new ArrayList<>();

	        for (Element e : rows) {
	            Element firstLink = e.getElementsByTag("a").first();
	            links.add(firstLink.attr("href"));
	        }

	        
	        for(String link: links) {
	           try {
	        	Ad ad = parseAd(Jsoup.connect(BASE_URL+link).get(), result);
				result.getAds().add(ad);

			   } catch(Exception e) {
	        	   log.error("Gohome parse ad: "+ link,e);
	           }
	        }

		} catch( Exception e) {
			log.error("Gohome connect error: ",e);
		}
		
		return result;
	}
	
	
	 private  Ad parseAd(Document adDoc, ParsedResult result) throws Exception {

	        //Element content = adDoc.getElementById("content");
	        Element adElement = adDoc.getElementById("new_ad");

	        Element views = adElement.getElementsByClass("views").first();
	        Elements bb = views.getElementsByTag("b");
	        Ad ad = new Ad();
	        ad.setDate(parseDate(bb));
	        ad.setViews(parseViews(bb));


	        // images
	        Element imagesBlock = adElement.getElementsByClass("block_images").first();
	        if(imagesBlock!=null) {
	            List<String> imgLinks = parseImageLinks(imagesBlock);
	            List<String> idImages = new ArrayList<>();
	            for(String link : imgLinks) {
	            	try {

						String newId = ObjectId.get().toHexString();
						result.getImages().put(newId, ImageService.imageToBytes(new URL(BASE_URL+link)) );
						idImages.add( newId );

					} catch (MalformedURLException e) {
						log.error("Image parse +" + link, e);
					}
	            }
	            ad.setImages(idImages);
	            
	            
	        } else {
	        	ad.setImages(new ArrayList<String>());
	        }
	        
	        

	        
	        Elements blocks = adElement.getElementsByClass("block");

	        String address = parseAddress(blocks.get(0));
	        ad.setAddress(address);

	        ad.setRoomCount(parseRooms(blocks.get(0)));
	        ad.setCost(parseCost(blocks.get(0)));

	        ad.setSource(BASE_URL);

	        ad.setContacts(parseContacts(blocks.get(3)));
	        ad.setDescription(parseDescription(blocks.get(2)));

	        return ad;
	    }

	    private  String parseContacts(Element block) {
	        Elements trs = block.getElementsByTag("tr");
	        String email = trs.get(0).getElementsByTag("td").get(1).html();

	        String[] parts = email.split("<a class=\"dog\"></a>");
	        email = parts[0] + "@"+ parts[1];

	        String phone = trs.get(2).getElementsByTag("td").get(1).text();

	        return phone+", " + email;
	    }

	    private  String parseDescription(Element block) {
	        Elements trs = block.getElementsByTag("tr");
			return trs.get(0).getElementsByTag("td").get(1).text();
	    }

	    private  String parseAddress(Element block) {
	        Elements trs = block.getElementsByTag("tr");

	        String town = trs.get(0).getElementsByTag("td").get(1).getElementsByTag("a").first().text();
	        String street = trs.get(1).getElementsByTag("td").get(1).text();
	        return town+", "+street;
	    }

	    private  int parseRooms(Element block) throws ParseException {
	        Elements trs = block.getElementsByTag("tr");
	        String rooms = trs.get(2).getElementsByTag("td").get(1).text();
	        return Integer.parseInt(rooms.split("-")[0]);
	    }

	    private  String parseCost(Element block) {
	        Elements trs = block.getElementsByTag("tr");
			return trs.get(3).getElementsByTag("td").get(1).text();
	    }

	    private  List<String> parseImageLinks(Element imagesBlock) {
	        List<String> imgs = new ArrayList<>();
	        Elements imgElements = imagesBlock.getElementsByTag("a");
	        if(imgElements!=null) {
	            for (Element e : imgElements) {
	                imgs.add(e.attr("href"));
	            }
	        }
	        return imgs;
	    }

	    private  int parseViews(Elements bb) {

	        int views;
	        try {
	            if(bb.size()==3) {
	                views = Integer.parseInt(bb.get(2).text());
	            } else {
	                views = Integer.parseInt(bb.get(1).text());
	            }
	        } catch (Exception ex) {
	            views = 0;
	        }

	        return views;
	    }

	    private  Date parseDate(Elements bb) throws ParseException {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        return (bb.size()==3)?sdf.parse(bb.get(1).text()):sdf.parse(bb.get(0).text());
	    }

}
