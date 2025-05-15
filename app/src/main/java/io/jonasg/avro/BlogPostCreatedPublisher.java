package io.jonasg.avro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.jonasg.BlogPostCreated;

@Component
public class BlogPostCreatedPublisher {

    private final String ordersTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BlogPostCreatedPublisher(
            @Value("${app.topics.orders}") String ordersTopic,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.ordersTopic = ordersTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(BlogPostCreated blogPostCreated) {
        kafkaTemplate.send(ordersTopic, blogPostCreated.getId(), blogPostCreated);
    }
}
