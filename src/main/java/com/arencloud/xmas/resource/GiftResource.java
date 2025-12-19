package com.arencloud.xmas.resource;

import com.arencloud.xmas.model.Gift;
import com.arencloud.xmas.service.GiftService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/gifts")
@Produces(MediaType.APPLICATION_JSON)
public class GiftResource {

    @Inject
    GiftService giftService;

    @GET
    @PermitAll
    public List<Gift> list() {
        return giftService.list();
    }
}
