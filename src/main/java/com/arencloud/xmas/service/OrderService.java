package com.arencloud.xmas.service;

import com.arencloud.xmas.model.Gift;
import com.arencloud.xmas.model.OrderEvent;
import com.arencloud.xmas.model.OrderRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    @Inject
    GiftService giftService;

    @Inject
    ProducerTemplate producerTemplate;

    private final List<OrderEvent> events = Collections.synchronizedList(new LinkedList<>());

    public OrderEvent placeOrder(String buyer, OrderRequest request) {
        Gift gift = giftService.find(request.getGiftId());
        if (gift == null) {
            throw new IllegalArgumentException("Gift not found: " + request.getGiftId());
        }

        OrderEvent event = new OrderEvent(
                UUID.randomUUID().toString(),
                gift.getId(),
                gift.getName(),
                buyer,
                Math.max(1, request.getQuantity()),
                request.getNote(),
                Instant.now()
        );

        events.add(0, event);
        producerTemplate.asyncSendBody("direct:order-events", event);
        return event;
    }

    public List<OrderEvent> events() {
        return events;
    }
}
