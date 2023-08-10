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
Thank for watching!! 





