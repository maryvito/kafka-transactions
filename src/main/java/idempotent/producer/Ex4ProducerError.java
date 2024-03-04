package idempotent.producer;


import utis.ConsumerSequential;
import utis.Producer;
import utis.Utils;

public class Ex4ProducerError {
    public static void main(String[] args) throws Exception {
        Utils.recreateTopics(1, 1, "topic1");

        try (var consumer = new ConsumerSequential("1", "topic1", Utils.consumerConfig);
             var producer1 = new Producer("topic1", Utils.producerConfig, consumer.lastReceive)) {

            producer1.start();
            Thread.sleep(500);
            producer1.close();

            try (var producer2 = new Producer("topic1", Utils.producerConfig, consumer.lastReceive, producer1.getLastSend() - 10)) {
                producer2.start();
                Thread.sleep(500);
            }

            Thread.sleep(5000);
        }
    }
}
