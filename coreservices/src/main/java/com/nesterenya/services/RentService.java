package com.nesterenya.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nesterenya.modal.Ad;

@Component
public class RentService {
	
	private List<Ad> testAds = new ArrayList<>();
	
	{
		Ad ad1 = new Ad();
		ad1.setAddress("Минск, Платонова, 33");
		ad1.setContacts("80259088713");
		ad1.setRoomCount(1);
		ad1.setCost("400");
		ad1.setDate( new Date(1437339600000L) );
		ad1.setDescription("Сдам квартиру по адресу Минск, Платонова, 33 на длительный срок без посредников. ул.Платонова д.33 к.3 Большая светлая квартира, отличный вид из окна. Новый монолитный дом, современные коммуникации. Современный ремонт. Новая мебель. Холодильник, стиральная машина, телевизор, микроволновка. Метро 850м. По договору. Предоплата за 1-й и последний месяцы. Посредников не беспокоить :) НА ДЛИТЕЛЬНЫЙ СРОК для работающих, без вредных привычек, без детей, без животных Получите больше чем ожидаете :)");
		ad1.setImages( Arrays.asList("55b3634e8025742ac0d88ec5","55b3634e8025742ac0d88ec6") );
		
		Ad ad2 = new Ad();
		ad2.setAddress("Минск, Чкалова, 22");
		ad2.setContacts("80297648133");
		ad2.setRoomCount(2);
		ad2.setCost("450");
		ad2.setDate( new Date(1437426000000L) );
		ad2.setDescription("Сдам квартиру по адресу Минск, Чкалова, 22 на длительный срок без посредников. Сдам двухкомнатную квартиру по ул.Чкалова на длительный срок.Свежий ремонт.Стеклопакеты,ламинат,двери межкомнатные- шпон дуба,входная- металлическая,новая сантехника.Полностью меблирована.Есть вся бытовая техника.Отличный район.Одна остановка до метро Институт Культуры на автобусе № 100.Магазины,аптеки,фитнес клуб и бассейн в шаговой доступности.Договор.Коммунальные,интернет и электроэнергия оплачиваются отдельно.Сдают хозяева.");
		
		
		Ad ad3 = new Ad();
		ad3.setAddress("Минск, Чкалова, 17");
		ad3.setContacts("80293934665");
		ad3.setRoomCount(2);
		ad3.setCost("350");
		ad3.setDate( new Date(1437339600000L) );
		ad3.setDescription("Сдам квартиру по адресу Минск, Чкалова, 17 на длительный срок без посредников. Сдается 2-х комнатная квартира не далеко от центра, по улице Чкалова д.17 (ст.м. Институт культуры) на длительный срок. Квартира на 4 этаже 5-ти этажного дома. В хорошем состоянии, полностью обустроена для проживания. Во дворе свободная парковка, большой двор с детской площадкой и зеленой зоной. Квартира сдается по договору аренды, на длительный срок. Оплата ежемесячно + коммунальные услуги.");
		ad3.setImages(Arrays.asList("55b3634e8025742ac0d88e45"));
		
		testAds.add(ad1);
		testAds.add(ad2);
		testAds.add(ad2);
	}
	
	
	public List<Ad> getAll() {
		return testAds;
	}
	
	public List<Ad> getTestParsedData() {
		Parser parser = new HatubyParserService();
		return parser.parse();
	}
	
	final int DEFAULT_CARD_OF_PAGE = 5;
	public int getCountPages(int cardOnPage) {
		if(cardOnPage<=0)
			cardOnPage = DEFAULT_CARD_OF_PAGE;
		
		Parser parser = new HatubyParserService();
		return (int) Math.ceil((double)parser.parse().size()/cardOnPage);
	}
	
	
	public List<Ad> getPage(int pageNumber, int cardOnPage) {
		// TODO warning
		if(cardOnPage<=0)
			cardOnPage = DEFAULT_CARD_OF_PAGE;
		Parser parser = new HatubyParserService();
		
		List<Ad> ads = parser.parse();
		Collections.sort(ads, new Comparator<Ad>() {
			@Override
			public int compare(Ad o1, Ad o2) {
				if(o1.getDate()!=null)
					return -o1.getDate().compareTo(o2.getDate());
				else 
					return 0;
			}
		});
		
		List<Ad> pageList = new ArrayList<Ad>();
		
		int first = (pageNumber-1)*cardOnPage;
		
		if(first<0)
			first = 0;
		
		for(int i = first; i < first+cardOnPage&&i<ads.size(); i++) {
			pageList.add(ads.get(i));
		}
		
		return pageList;
	}
	
}