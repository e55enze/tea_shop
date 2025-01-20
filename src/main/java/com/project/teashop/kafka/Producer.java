package com.project.teashop.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.teashop.entity.OrderData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

//    public void sendMessage(String message) {
//        kafkaTemplate.send("orders", message);
//    }

    public void sendMessage(OrderData orderData) {
        try {
            String message = objectMapper.writeValueAsString(orderData);
            kafkaTemplate.send("orders", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
