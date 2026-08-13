package com.example.write_cqrs;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC = "cqrs-topic";

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void sendMessage(BookDTO bookDTO){
        String message = "{\"bid\":"+"\"" + bookDTO.getBid() +"\"}";
        this.kafkaTemplate.send(TOPIC, message);
    }
}
