package idempotent.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import utis.ConsumerSequential;
import utis.Producer;
import utis.Utils;

public class Ex1Problem {
    public static void main(String[] args) throws Exception {
        Utils.recreateTopics(1, 1, "topic1");

        try (var consumer = new ConsumerSequential("1", "topic1", Utils.consumerConfig);
             var producer = new Producer("topic1", Utils.createProducerConfig(b ->
                     b.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false)
             ), consumer.lastReceive)) {

            producer.start();

            // Несколько раз убиваем tcp-коннект на порт 9092 (например https://www.nirsoft.net/utils/cports.html)
            Thread.sleep(600000);

        }
    }
}
