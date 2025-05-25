package io.jonasg.avro;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import io.jonasg.BlogPostCreated;
import io.jonasg.avro.eventmothers.BlogPostCreatedMother;

@SpringBootTest
class BlogPostCreatedPublisherTest implements KafkaContainerSupport {

    @Autowired
    BlogPostCreatedPublisher blogPostCreatedPublisher;

    @Autowired
    ConsumerFactory<String, BlogPostCreated> consumerFactory;

    @Value("${app.topics.orders}")
    String ordersTopic;

    @Test
    void publishBlogPostCreated() {
        String id = UUID.randomUUID().toString();
        blogPostCreatedPublisher.publish(
                BlogPostCreatedMother
                        .aBlogPostCreated()
                        .setId(id)
                        .build()
        );

        var consumer = consumerFactory.createConsumer("test-group", "test-client");
        consumer.subscribe(List.of(ordersTopic));
        ConsumerRecords<String, BlogPostCreated> records = KafkaTestUtils
                .getRecords(consumer);

        assertThat(records)
                .satisfiesOnlyOnce(record -> {
                    assertThat(record.key()).isEqualTo(id);
                    assertThat(record.value().getId()).isEqualTo(id);
                });

        consumer.close();
    }
}