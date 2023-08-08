---
sidebar_position: 3
slug: /tutorial-dockers/Application
title: Bài Tập Thực Hành
---

## Có bao nhiêu cách đóng gói ứng dụng springboot?

**+ Có 3 cách :**
```bash
1- Dockerfile

2- Sử dụng thư viện GoogleContainerTools/jib

3- Sử dụng Graalvm và Spring Boot native
```

**Đầu tiên chúng ta nên hiểu qua về luồng đi của docker** 
+ thứ nhất sau khi chúng ta đã tạo xong project 
+ chúng ta tạm gọi là context bên trong context sẽ chứa tất cả sourcecode, package, thư viện, môi trường... để cài đặt chạy lên code
+ lúc này từ context ta sẽ build -> thành image -> run thành con tainer docker
như vậy sau khi build xong chúng ta đã có container nhưng là build ở local máy cá nhân ví dụ muốn sử dụng cho bên abc server nào đó thì làm sao ?
- Lúc này ta sẽ đưa container lên server gọi là B bằng cách sử dụng trung gian thông qua cách phổ biến nhất là registry
 Từ image - > ta push lên registry, lúc này dockerhub sẽ pull từ registry về 

 **Tiếp theo là 3 Cách đóng gói ứng dụng spring boot bằng dọcker thực hành theo các bước**

## Cách 1: Đóng gói bằng Docker file
 + Đối với dockerfile có các bước chính sau:

 ```bash
   1+ Biên dịch tạo ra file *.jar.
   2+ Viết dockerfile để đóng gói *.jar vào docker image
   3+ Viết file Makefile để tự động hóa bước 1 và 2
 ```

 **cụ thể như sau**
 ```bash
   + tạo docker file trong project cùng cấp với tên project  bên trong cấu hình như sau :
   # Sử dụng hình ảnh chứa Java 11 trên Alpine Linux như là hình ảnh cơ sở

   FROM openjdk:11

   # Tạo thư mục để lưu trữ ứng dụng
   WORKDIR /app

   # Sao chép các tệp .jar của ứng dụng vào thư mục /app trong hình ảnh
   COPY target/*.jar /app/app.jar

   # Expose cổng mà ứng dụng sẽ lắng nghe (thay thế 8080 bằng cổng mà ứng dụng của bạn lắng nghe)

   EXPOSE 8080

  # Lệnh để chạy ứng dụng khi container được khởi động
   CMD ["java", "-jar", "app.jar"]
```

**Sau khi tạo dockerfile xong** ta di chuyển vào thư mục gốc của project mở cmd lên và chạy
```bash
- kiểm tra lênh '$docker images' xem đã có tên image mình tạo chưa (chưa có thì bỏ qua bước này)
- tiếp lệnh $ docker build -t ten-docker-image:latest . (với dấu chấm là thư mục cùng cấp)
- kiểm tra thử lúc này xem đã có docker image chưa sd : $ docker images ten-docker-image hoặc là $docker images -a để xem chi tiết image thời gian tạo..
- sau khi có docker image rồi chúng ta sẽ build thành container sd: $docker run -p 8081:8080 ten-docker 
 ```



## Cách 2: Đóng gói bằng Jib Google
 + Đối với jib googke có các bước chính sau:

 ```bash
   1+ cài đặt Maven và Docker Desktop.
   2+ Cấu hình Maven pom.xml
   3+ Chạy lệnh sau để build Docker image và đẩy nó lên container
 ```

**cụ thể như sau**

 ```bash
            <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <from>
                        <image>docker://openjdk:17-alpine3.12</image>  // đối với docker:// là chúng ta sẽ lấy jdk về khi đã pull openjdk về nếu ko có sẽ lỗi 
                    </from>
                    <to>
                        <image>tuantu2k/webdockerjib:1.0</image>
                    </to>
                    <container>
                        <creationTime>USE_CURRENT_TIMESTAMP</creationTime>
                    </container>
                </configuration>
            </plugin>
```

**Sau khi tạo Cấu hình Maven pom.xml xong** ta di chuyển vào thư mục gốc của project mở cmd lên và chạy
```bash
- kiểm tra lênh '$docker images' xem đã có tên image mình tạo chưa (chưa có thì bỏ qua bước này)
- Build Docker image rồi đăng ký local Docker Daemon '$ mvn compile jib:dockerBuild '
- Khi tạo Docker Image thành công, Tiếp đến tạo Docker container $ docker run -d -p 8081:8080 --name webdocker tuantu2k/webdocker:latest
- Khi đã tạo xong muốn cho lên registy ta sd $ docker push tuantu2k/webdockerjib -> khi thành công lúc này ta đã có repository lên dockerhub để kiểm tra
 ```

 **chú ý 1 số lỗi có thể gặp phải trong quá trình cài đặt**:
+ Lỗi maven khi chạy mvn ta phải Cài đặt Maven và thêm vào biến môi trường PATH(ta có thể tải trực tiếp từ trang chủ như sau: https://maven.apache.org/download.cgi 
tiếp đến tìm đến file bin xong copy nguồn vào eviroment variable trong local edit file path thêm mới vào ) 
+ khi chạy lệnh $mvn compile jib:dockerBuild có thể sinh ra thêm lỗi về WARNING: 'mainClass' configured in 'maven-jar-plugin' is not a valid Java class: ${start-class} 
-> lúc này do trong file chưa thêm ta thêm start-class vào file porm.xml

```bash
<properties>
    <start-class>com.example.SpringbootMYSQLDocker.MyApplication</start-class>
</properties>
```
+ lỗi vấn đề về xác thực -> vao thư mục gốc project run cmd -> $docker login -> đăng nhập username và password đăng ký trên dockerhub

+ lỗi proxy: vào docker khi chạy mvn jib không kéo được jdk về -> thêm docker:// vào file setup pom.xml như trên trước khi chạy mvn jib thì ta nên pull jdk về trước là được.

## Cách 3: Ít tạm thời chưa sử dụng


## Đối Với Project

+ Project tạo ra kết hop springboot + mysql rồi đóng gói bằng dockerfile và jib google
+ khi tạo chạy có 1 vài lỗi ta nên set proxy cho inteliji, mysql
+ project CRUD Student lưu vào trong db.

Source code: http://10.60.156.11/khcp/daotao/tuansv/docker/-/tree/master/src/main/java/com/example/springbootmysqldocker

