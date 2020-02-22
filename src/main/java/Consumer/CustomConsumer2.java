package Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 消费者 2、手动-同步提交offset
 */
public class CustomConsumer2 {
    public static void main(String[] args) {
        String topic = "004";

        Properties props = new Properties();
        // kafka集群，broker-list
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.159.132:9092");
        // 消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        // 关闭自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 自动提交等待时间（自动提交开启才有效）
        // props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // key序列化类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // value序列化类
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value()
            );
            //同步提交，当前线程会阻塞直到 offset 提交成功
            consumer.commitSync();
        }

    }

}
