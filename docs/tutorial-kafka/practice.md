---
sidebar_position: 3
slug: /tutorial-kafka/Application
title: Bài Tập Thực Hành
---
## Các công cụ phần mềm sử dụng trong project:
  + Inteliji
  + postman
  + mysql
  + docker desktop

### Các bước cấu hình sử dụng và chạy project kafka
** Ứng dụng docker-compose vào để chạy Kafka, mysql


### Bước 1: Cấu hình pom.xml và application.properties 

+ Với pom.xml thì ta thêm thư viện sử dụng cho kafka
```bash
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.baeldung.spring.kafka.KafkaApplication</mainClass>
                </configuration>
            </plugin>
```
+ Tiếp theo cấu hình kafka trong application.properties
```bash
# C?u hình Kafka
spring.kafka.bootstrap-servers=localhost:9092

# C?u hình KafkaConsumer
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

# C?u hình Producer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

```

### Bước 2: Thiết lập tạo file docker-compose để chạy và triển khai các container
+ Tạo file docker-compose
```bash
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - 2181:2181



  kafka:
    image: wurstmeister/kafka
    container_name: kafka
    environment:
      KAFKA_ADVERTISED_HOST_NAME: kafka
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    ports:
      - 9092:9092
    depends_on:
      - zookeeper
  
  
  #ui
  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    platform: linux/amd64
    depends_on:
      - kafka
    ports:
      - 9000:9000
    environment:
      KAFKA_BROKERCONNECT: kafka:9092
```
+ Sau khi tạo xong docker-compose sử dụng terminal trong thư mục đó chạy container
```bash
  gõ lệnh: $docker-compose up
  (lưu ý: $docker-compose down để xóa hết container cũ)
```

### Bước 3: ÁP dụng và kiểm tra tương tác kafka 

+ Đầu tiên cấu hình configuration cho kafka
```bash
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
        config.put("key.deserializer", StringDeserializer.class);
        config.put("value.deserializer", StringDeserializer.class);
        config.put("key.serializer", StringSerializer.class);
        config.put("value.serializer", StringSerializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put("key.deserializer", StringDeserializer.class);
        config.put("value.deserializer", StringDeserializer.class);
        config.put("key.serializer", StringSerializer.class);
        config.put("value.serializer", StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

+ Tiếp theo như project cũ làm về CRUD student tương tác với MySQL chúng ta sẽ ứng dụng thêm kafka vào insert và get dữ liệu và xem hiển thị trên UI kafdrop.
+ trong class Controller sử dụng để insert student vào sql thì ta tạo thêm 2 class producer và consumer

```bash
  + Class: StudentController

      @PostMapping("/insert")
      ResponseEntity<String> insertStudent(
            @RequestBody @Validated Student student) {
        kafkaProducer.sendStudent(new Gson().toJson(student));
        return ResponseEntity.ok("Object student sent successfully!");
    }


    @GetMapping("/hello")
    public String sendMessage() {
        kafkaProducer.sendMessage("test");
        return "Message sent: " + "test";
    }

```

+ Class KafkaProducer để send dữ liệu
```bash
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topicStudent = "student-topic"; // Tên của Kafka topic
    private final String topicMessage = "message-topic"; // Tên của Kafka topic

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, StudentConverter studentConverter1) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStudent(String student) {
        kafkaTemplate.send(topicStudent, student);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topicMessage, message);
    }
}
```

+ Class KafkaConsumer để nhận message
```bash
@Service
public class KafkaConsumer {

    private final StudentRepository studentRepository;
    private final StudentConverter studentConverter;
    private final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaConsumer(StudentRepository studentRepository, StudentConverter studentConverter) {
        this.studentRepository = studentRepository;
        this.studentConverter = studentConverter;
    }

    @KafkaListener(topics = "student-topic", groupId = "group-id")
    public void consumeMessage(String gsonStudent) {
        Gson gson = new Gson();
        Student student = gson.fromJson(String.valueOf(gsonStudent), Student.class);

        StudentEntity studentEntity = studentConverter.toEntity(student);
        studentRepository.save(studentEntity);
        log.info("student event : " + studentEntity.toString());
        System.out.println("Received student: " + studentEntity.getId());
        System.out.println("gsonStudent : " + gsonStudent);
    }


    @KafkaListener(topics = "message-topic", groupId = "group-id")
    public void consumeMessage1(String messages) {
        log.info("student event : " + messages);
        System.out.println("Received student: " + messages);
    }
}
```

+ Bước cuối là kiểm tra dữ liệu chạy test trên postman và kiểm tra data procucer pulic lên thấy được các topic 

+ Với postman:

+ Với UI kafdrop kết quả như sau


Thank you for watch: source code: http://10.60.156.11/khcp/daotao/tuansv/kafka

  