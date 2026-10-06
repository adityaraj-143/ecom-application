package com.ecommerce.notificaton;

import com.ecommerce.notificaton.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
//        System.out.println("Received order event: " + orderEvent);
//    }
    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            logger.info("Received order created event for order: {}", event.getOrderId());
            logger.info("Received order created event for user: {}", event.getUserId());
        };
    }
}
