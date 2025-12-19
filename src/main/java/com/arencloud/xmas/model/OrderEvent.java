package com.arencloud.xmas.model;

import java.time.Instant;

public class OrderEvent {
    private String id;
    private String giftId;
    private String giftName;
    private String buyer;
    private int quantity;
    private String note;
    private Instant createdAt;

    public OrderEvent() {
    }

    public OrderEvent(String id, String giftId, String giftName, String buyer, int quantity, String note, Instant createdAt) {
        this.id = id;
        this.giftId = giftId;
        this.giftName = giftName;
        this.buyer = buyer;
        this.quantity = quantity;
        this.note = note;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
