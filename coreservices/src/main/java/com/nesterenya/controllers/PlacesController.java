package com.nesterenya.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.modal.Place;

@RestController
@RequestMapping("/journey/places")
public class PlacesController {

	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Place> getAll() {
		
		Place[] places = new Place[6];
		places[0] = new Place(1, "53.451281, 26.473303", "Мирский замок");
		places[1] = new Place(2, "53.222892, 26.691773", "Несвижский замок");
		places[2] = new Place(3, "52.083109, 23.658875", "Брестская крепость");
		places[3] = new Place(4, "54.251562, 26.020300", "Гольшанский замок");
		places[4] = new Place(5, "52.422152, 31.016734", "Дворец Румянцевых-Паскевичей");
		places[5] = new Place(6, "53.677282, 23.822971", "Старый замок Гродно");
		return Arrays.asList(places);
	}
}
