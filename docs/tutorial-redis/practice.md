---
sidebar_position: 3
slug: /tutorial-redis/Application
title: Bài Tập Thực Hành
---
## Các công cụ phần mềm sử dụng trong project:
  + Inteliji
  + postman
  + mysql
  + cmd 

## Cách ứng dụng redis trong project?

### Bước 1: Cấu hình pom.xml và application.properties

+ để sd redis cần thêm thư viện vào sd trong pom.xml
```bash
  <!-- Spring Data Redis -->
<!--        Cấu hình, thao tác kết nối redis với springboot-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

<!--        Cung cấp api tạo, kết nối, thao tác với Redis Server-->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>
        
<!--        Hiệu xuất cao hơn đa phần xử lý về synchronize-->
        <dependency>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </dependency>
```


+ cấu hình redis trong application.properties
```bash
# C?u hình Redis
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.cache-names=cache1,cache2
spring.cache.redis.time-to-live=60000
spring.cache.redis.cache-null-values=true

# C?u hình ghi log Spring Boot
logging.level.org.springframework.data.redis=DEBUG
```

### Bước 2: Setup file config cho redis
```bash
  + tạo folder config
  + tạo class RedisConfig 

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));
        return configuration;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .build();
        return cacheManager;
    }
    @Bean
    public RedisTemplate<String, Session> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Session> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
```

### Bước 3: ÁP dụng và kiểm tra tương tác Redis server
  + Đối với code

  + đối với cache
  ```bash
  1. $redis-cli ping
    + Ta mở cmd lên và kiểm tra: $ redis-cli ping -> trả ra kêt quả như của tôi setup trả về là pong (Tức là kiểm tra máy chủ Redis có hoạt động không)

  2. $redis-cli
    + khi chạy dòng lệnh trên sẽ mở ra 1 prompt  để tương tác với redis server và  đó là 127.0.0.1>
    + trong đó 127.0.0.1 là địa của ip của máy chủ redis và port 6379 mà bạn kết nối đến.  
  3. $config get port 
    + sẽ trả về port 6379 nếu như bạn muốn kiểm tra port
  4 $keys *
    + với lệnh này sẽ kiểm tra được tất cả key-value mà máy chủ redis ghi lại được ví dụ khi mình getAllStudent với value = "cache1", key = "'#getAls'") thì lúc này sẽ có giá trị hiển thị ra 1) "cache1::#getAls" 
  5 $monitor 
    + khi chạy lệnh trên sẽ ra OK lúc này để nguyên khi ta bắt đầu tương tác với project chạy 
    các api CRUD lúc này sẽ nhận được thông tin được ghi lại tự động generate ra và bạn có thể kiểm tra như ví vụ tôi làm như sau :

```
```bash
        127.0.0.1:6379> monitor
OK
1691638730.849950 [0 127.0.0.1:54017] "PING"
1691638730.861188 [0 127.0.0.1:54017] "GET" "cache1::#getAls"
1691638804.811105 [0 127.0.0.1:54224] "PING"
1691638804.822538 [0 127.0.0.1:54224] "GET" "cache1::#save"
1691638804.904845 [0 127.0.0.1:54224] "SET" "cache1::#save" "{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":11,\"name\":\"tuannnn1\",\"age\":22}"
1691638807.928735 [0 127.0.0.1:54224] "GET" "cache2::finALl"
1691638808.006247 [0 127.0.0.1:54224] "SET" "cache2::finALl" "[\"java.util.ArrayList\",[{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":1,\"name\":\"tuannnn\",\"age\":23},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":2,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":3,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":4,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":5,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":6,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":7,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":8,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":9,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":10,\"name\":\"tuannnn1\",\"age\":22},{\"@class\":\"com.example.springbootmysqldocker.entity.StudentEntity\",\"id\":11,\"name\":\"tuannnn1\",\"age\":22}]]"
1691638808.841135 [0 127.0.0.1:54224] "GET" "cache2::finALl"
1691638810.658377 [0 127.0.0.1:54224] "GET" "cache1::#getAls"
1691638811.928258 [0 127.0.0.1:54224] "GET" "cache1::#getAls"
1691638813.291193 [0 127.0.0.1:54224] "GET" "cache1::#save"
1691638813.935221 [0 127.0.0.1:54224] "GET" "cache1::#save"
```
```bash
 Dưới đây là 1 vài anotation sử dụng với redis

@CacheEvict: Được sử dụng để xóa dữ liệu từ cache sau khi thực hiện một hành động cập nhật hoặc xóa dữ liệu.

@CachePut: Được sử dụng để lưu trữ kết quả mới của một phương thức vào cache, thường được sử dụng trong trường hợp cập nhật dữ liệu.

@CacheConfig: Annotation đặt ở mức class để cấu hình chung cho caching trong các phương thức của lớp đó.

@Cacheable, @CacheEvict, @CachePut, @Caching: Các annotation này cùng với @CacheConfig là các annotation chính để quản lý caching trong Spring.

@EnableCaching: Annotation được sử dụng ở mức class hoặc configuration để bật tích hợp caching trong ứng dụng.

RedisTemplate và RedisConnectionFactory: Các lớp và giao diện trong Spring Data Redis để tương tác với Redis server.

Lettuce và Jedis: Hai thư viện được sử dụng để kết nối và tương tác với Redis server từ ứng dụng Java.
```

Source code: 


## Thực Hành Redis sử dụng docker-compose xử lý Redis + hibernate query
1. Cấu hình docker-compose redis như trên là mình sử dụng có kết hợp với mysql như sau: 

Bước 1: Cấu hình application.properties
```bash
# C?u hình Redis
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.cache-names=cache1,cache2
spring.cache.redis.time-to-live=60000
spring.cache.redis.cache-null-values=true
# C?u hình ghi log Spring Boot
logging.level.org.springframework.data.redis=DEBUG
```

+ cấu hình tạo docker-compose kết hợp mysql và redis:
```bash
services:
  #    Zookeeper - Kafka
  #  MySQL
  mysql:
    container_name: mysql
    image: mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_USER: user
      MYSQL_PASSWORD: user
    ports:
      - '3307:3306'
    volumes:
      - ./mysql-data:/var/lib/mysql

  redis:
    image: redis:latest
    container_name: my-redis
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
volumes:
  redis_data:      
```

+ cấu hình RedisConfig như trong project mình cấu hình để là cacheConfig

```bash
 @Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));
        return configuration;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration())
                .build();
        return cacheManager;
    }
    @Bean
    public RedisTemplate<String, Session> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Session> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }



    @Bean
    public RedisTemplate<String, StudentEntity> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, StudentEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Set value serializer
        RedisSerializer<StudentEntity> valueSerializer = new Jackson2JsonRedisSerializer<>(StudentEntity.class);
        template.setValueSerializer(valueSerializer);

        return template;
    }
}
```

Bước 2: Áp dụng
1. Triển khai 1 ví dụ tìm list student theo tuổi của họ trong class controller và save 1 sutudent vào trong db lưu trữ trong redis
```bash
    @PostMapping("/insert1")
    StudentEntity insertStudent1(
            @RequestBody @Validated StudentEntity student) {
        return studentService.insertStudent1(student);
    }

     @GetMapping("/byAge/{age}")
    public ResponseEntity<List<StudentEntity>> getStudentsByAge(@PathVariable int age) {
        List<StudentEntity> students = studentService.findStudentsByAge(age);
        return ResponseEntity.ok(students);
    }
```
2. triển khai class service
```bash
-------------- insert new 1 student lưu vào db-------------
    @Cacheable(value = "cache3", key = "'#insertStudent1'+ #student.id")
    public StudentEntity  insertStudent1(StudentEntity student) {
        StudentEntity savedStudent = studentRepository.save(student);
        redisTemplate.opsForValue().set("studentSave1:" + savedStudent.getId(), savedStudent);
        return savedStudent;
    }
--------------------------- tìm student theo tuổi----------

 public List<StudentEntity> findStudentsByAge(int age) {
        List<StudentEntity> students = new ArrayList<>();

        // Kiểm tra xem dữ liệu có trong Redis không
        ValueOperations<String, StudentEntity> ops = redisTemplate.opsForValue();
        String redisKey = "age:" + age;
        boolean hasKey = redisTemplate.hasKey(redisKey);

        if (hasKey) {
            StudentEntity cachedStudent = ops.get(redisKey);
            students.add(cachedStudent);
        } else {
            // Nếu không có trong Redis, thực hiện truy vấn Hibernate
            students = studentRepository.findStudentsByAge(age);

            // Lưu dữ liệu vào Redis để sử dụng cho lần sau
            if (!students.isEmpty()) {
                StudentEntity firstStudent = students.get(0);
                ops.set(redisKey, firstStudent);
            }
        }
        return students;
    }

```

3. lớp cuối truy vấn db trong repository

```bash
          @Query(value = "select * from student s where s.age = ?1", nativeQuery = true)
        List<StudentEntity> findStudentsByAge (int age);
```

4. Sau khi code xong chạy kết quả trên post man theo api sau 
ví dự tôi muốn tìm list student trong db với age bằng 22
```bash
 localhost:8080/api/v1/students/byAge/22
```

5. kiểm tra redis bằng cmd
```bash
 1. gõ lệnh: $redis-cli  sử dụng Command Line Interface (CLI) của Redis để kiểm tra dữ liệu đã lưu trữ:. redis 127.0.0.1:6379>
 2. $KEYS * với lệnh này để để liệt kê tất cả các khóa hiện có trong Redis.
 3. $Get age:22 để tìm kiếm kết quả với tuổi bằng 22 thì trong redis đã lưu dữ liệu của tôi chưa
```
Kết quả sau khi tôi chạy ra như sau:

```bash
 PS F:\1.sourcecode\1.road_map\5.minIO\SpringbootMYSQLDocker> redis-cli
127.0.0.1:6379> KEYS *
1) "rollno"
2) "cache2::finALl"
3) "\xac\xed\x00\x05t\x00\x04USER"
4) "cache2::#update"
5) "cache1::#getAls"
6) "name"
7) "age:22"
8) "cache1::#save"
127.0.0.1:6379> GET tuannnn1:22
(nil)
127.0.0.1:6379> GET age:22
"{\"id\":2,\"name\":\"tuannnn1\",\"age\":22}"
127.0.0.1:6379>
```
  Tiếp theo là save 1 student

```bash
  save 1 student sử dụng postman localhost:8080/api/v1/students/insert1
  {
    "name": "tuananhtu2k",
    "age":23
  }

  sau khi tôi save 1 student vào trong db lúc này đồng thời lưu student vào trong bộ nhớ redis nữa
  tôi muốn kiểm tra nhhư sau :
  đầu tiên tìm liệt kê ra xem có chưa :
  127.0.0.1:6379> KEYS *
 1) "cache2::finALl"
 2) "\xac\xed\x00\x05t\x00\x04USER"
 3) "studentSave:19"
 4) "studentSave1:22"
 5) "cache1::#getAls"
 6) "cache2::#update"
 7) "studentSave:18"
 8) "studentSave:21"
 9) "cache3::#insertStudent10"
10) "studentSave:20"
11) "age:22"
12) "rollno"
13) "age:23"
14) "name"
15) "cache3::#insertStudent1"
16) "cache1::#save"

sau đó tìm value mà tôi mới save student vào với id là 22
127.0.0.1:6379> get studentSave1:22
"{\"id\":22,\"name\":\"tuananhtu2k\",\"age\":23}"
127.0.0.1:6379>

```

Done! thank for watch My source code: http://10.60.156.11/khcp/daotao/tuansv/redis_update

Thank for watching!! 





