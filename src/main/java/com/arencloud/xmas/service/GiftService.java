package com.arencloud.xmas.service;

import com.arencloud.xmas.model.Gift;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class GiftService {
    private final List<Gift> gifts = Collections.unmodifiableList(Arrays.asList(
            new Gift("candy-cane", "Candy Cane Bundle", "Minty fresh canes tied with a ribbon.", new BigDecimal("9.99"), "images/candy-cane.svg"),
            new Gift("gingerbread", "Gingerbread House Kit", "DIY kit with icing and gumdrops.", new BigDecimal("24.50"), "images/gingerbread.svg"),
            new Gift("sled", "Mini Sled", "Desk-sized sled for instant holiday vibes.", new BigDecimal("39.00"), "images/sled.svg"),
            new Gift("sweater", "Ugly Sweater Deluxe", "So ugly it’s beautiful. Limited glitter.", new BigDecimal("59.95"), "images/sweater.svg"),
            new Gift("lights", "Aurora Lights", "Smart fairy lights with northern glow presets.", new BigDecimal("34.75"), "images/lights.svg")
    ));

    public List<Gift> list() {
        return gifts;
    }

    public Gift find(String id) {
        return gifts.stream().filter(g -> g.getId().equals(id)).findFirst().orElse(null);
    }
}
