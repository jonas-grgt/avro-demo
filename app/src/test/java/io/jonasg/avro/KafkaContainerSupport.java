package io.jonasg.avro;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

public interface KafkaContainerSupport {

    @ServiceConnection
    ConfluentKafkaContainer kafkaContainer = new ConfluentKafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.8.0"))
            .withReuse(true)
            .withExposedPorts(9092, 9093);

    @BeforeAll
    static void beforeAll() {
        if (!kafkaContainer.isRunning()) {
            kafkaContainer.start();
        }
    }
}
