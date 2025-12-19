package com.arencloud.xmas.camel;

import com.arencloud.xmas.model.OrderEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class OrderRoutes extends RouteBuilder {

    @Override
    public void configure() {
        // simple telemetry/logging route ready to be opened and extended in Kaoto
        from("direct:order-events")
                .routeId("order-events-route")
                .log(LoggingLevel.INFO, "🎄 Order ${body.id} for ${body.giftName} by ${body.buyer}")
                .marshal().json(OrderEvent.class)
                .to("log:com.arencloud.xmas.order?level=INFO&showHeaders=false&multiline=true");
    }
}
