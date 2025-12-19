package com.arencloud.xmas.resource;

import com.arencloud.xmas.model.OrderEvent;
import com.arencloud.xmas.model.OrderRequest;
import com.arencloud.xmas.service.OrderService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Inject
    SecurityIdentity identity;

    @POST
    @RolesAllowed("user")
    public OrderEvent place(OrderRequest request) {
        String buyer = identity.getPrincipal().getName();
        return orderService.placeOrder(buyer, request);
    }

    @GET
    @RolesAllowed("user")
    public List<OrderEvent> events() {
        return orderService.events();
    }
}
