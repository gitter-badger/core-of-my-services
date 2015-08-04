package com.nesterenya.parsers;

import com.nesterenya.modal.Ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedResult {

    final private List<Ad> ads = new ArrayList<>();
    final private Map<String, byte[]> images = new HashMap<>();

    public List<Ad> getAds() {
        return ads;
    }

    public Map<String, byte[]> getImages() {
        return images;
    }
}
