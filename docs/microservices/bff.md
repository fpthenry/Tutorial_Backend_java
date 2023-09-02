---
sidebar_position: 2
title: BFF - Backend For Frontend
---
# BFF
## 1. Giới thiệu về mô hình BFF
### BFF là gì?
**B**ackend **F**or **F**rontend (BFF) là mô hình tách các logic backend cho từng phiên bản client để đáp ứng tốt nhất các yêu cầu cụ thể cho từng nền tảng.

### Tại sao phải cần các backend riêng biệt?
Giả sử bạn có một ứng dụng triển khai trên web app cho máy tính và mobile app cho điện thoại. Khi sử dụng web app trên máy tính, ta có nhiều không gian để hiển thị thông tin cho các đối tượng. Tuy nhiên, trên mobile app lại hạn chế về không gian hiển thị nên ta buộc phải lược bỏ một vài trường thuộc tính cho đối tượng. Nếu dùng chung các api cho web app và mobile app thì sẽ bị lãng phí tài nguyên và hạn chế về hiệu suất khi dữ liệu trả về nhiều trường không cần dùng đến.

Lúc này, **BFF** sẽ là lựa chọn thích hợp cho hệ thống của bạn. **BFF** đóng vai trò như là một api gateway để điều phối các dữ liệu trả về từ backend của bạn. **BFF** sẽ gom các dữ liệu trả về từ backend rồi "chế biến" chúng và trả lại cho client.

## 2. Lợi ích khi triển khai BFF
Khi triển khai BFF cho ứng dụng microservice bạn sẽ đạt được một số lợi ích sau:
- **Tách biệt logic cho từng loại giao diện**: Khi bạn có nhiều giao diện khác nhau sử dụng cùng một tập hợp các dịch vụ microservices, việc triển khai các BFF cho các giao diện sẽ giúp tách biệt logic và yêu cầu của từng giao diện. Điều này giúp giảm thiểu sự ràng buộc giữa các giao diện và tạo sự linh hoạt trong việc phát triển và mở rộng.

- **Hiệu suất tối ưu hóa:** BFF có thể được tối ưu hóa riêng cho từng giao diện, đảm bảo rằng chỉ có những dịch vụ cần thiết được gọi và dữ liệu được trả về theo định dạng phù hợp với giao diện đó. Điều này giúp cải thiện hiệu suất và tốc độ phản hồi của ứng dụng.

- **Phân loại lỗi và theo dõi**: Khi xảy ra lỗi trong một BFF, bạn có khả năng phân loại lỗi theo giao diện cụ thể, giúp dễ dàng xác định và khắc phục vấn đề. Ngoài ra, việc phân tách giữa các BFF cũng giúp theo dõi và giám sát hiệu suất của từng giao diện một cách riêng biệt.

- **Tích hợp dịch vụ bên ngoài**: BFF cũng có thể được sử dụng để tích hợp các dịch vụ bên ngoài hoặc các API của bên thứ ba, và tạo ra một lớp trung gian giữa các dịch vụ đó và các giao diện của ứng dụng.
 
Tuy nhiên, việc triển khai BFF trong dự án của bạn cũng gây ra các thách thức về sự phức tạp hóa kiến trúc và khả năng xuất hiện lỗi cho hệ thống. Cần phải cân nhắc kỹ lưỡng về phạm vi và yêu cầu của dự án để quyết định sử dụng mô hình này.

## 3. Ví dụ minh họa và code triển khai
### 3.1. Ví dụ minh họa 

Để dễ hình dung, mình sẽ đưa ra một ví dụ về ứng dụng quản lý thư viện đơn giản, gồm có 2 microservice:
+ Student service (port 8082): Quản lý các học sinh, sinh viên.
+ Library service (port 8084): Quản lý sách cho thư viện.
+ BFF Gateway (port 8080): Là các BFF gateway như: Mobile BFF Gateway, Webapp BFF Gateway...

![BFF](https://datasecurity.viettel.vn/apps/files_pictureviewer/public_display?token=fFFhVHJukwDsd7I&file=BFF+diagram.jpg)

<center><i>Mô hình Backend For Frontend (BFF)</i></center>

Ứng dụng của mình sẽ hoạt động như sau: Đối với mỗi service sẽ có các api (CRUD) trả về đầy đủ các trường thông tin cho mỗi bảng. BFF Gateway chỉ đơn giản là định tuyến các yêu cầu từ client đến các dịch vụ backend tương ứng và có thể gom nhiều api để tạo thành một api mới tùy theo yêu cầu của client. 
Mình có một request gửi tới ```http://localhost:8080/api/library/borrow-book/1```, phương thức ```GET``` để xem thông tin mượn sách. API đối với web app, API sẽ trả dữ liệu như sau:
```
{
    "bookId": 1,
    "bookName": "Design pattern",
    "bookDescription": "Học về kiến trúc phần mềm",
    "bookAuthor": "Fullstack",
    "bookPrice": 1000.0,
    "bookStudentId": 1,
    "studentFirstName": "Nguyen",
    "studentLastName": "Thinh",
    "studentAge": 22,
    "studentAddress": "Ha Noi"
}
``` 
Còn đối với mobile app, API sẽ cho kết quả:
```
{
    "bookId": 1,
    "bookName": "Design pattern",
    "bookAuthor": "Fullstack",
    "bookPrice": 1000.0,
    "bookStudentId": 1,
    "studentName": "Nguyen Thinh"
}
```
Trong ví dụ trên, khi request gửi tới ```http://localhost:8080/api/library/borrow-book/1```, phương thức ```GET``` minh thực hiện ở hàm controller của BFF gateway 3 việc sau:
1. Gửi request tới địa chỉ ```http://localhost:8084/api/library/borrow-book/1``` để nhận thông tin về một cuốn sách mà sinh viên mượn có id = 1. Dữ liệu trả về như sau:
```
{
    "id": 1,
    "name": "Design pattern",
    "description": "Học về kiến trúc phần mềm",
    "author": "Fullstack",
    "price": 1000.0,
    "studentId": 1
}
```
2. Sau khi có studentId, mình tiếp tục gửi request tới địa chỉ ```http://localhost:8082/api/student/1``` để lấy thống tin của học sinh có studentId = 1. Dữ liệu trả về như sau:
```
{
    "id": 1,
    "firstName": "Nguyen",
    "lastName": "Thinh",
    "age": 22,
    "address": "Ha Noi"
}
```
3. Cuối cùng, dựa và Header của client để xác định type của client là `mobile` hay `web` rồi thực hiện gộp, lọc bớt các trường thông tin và trả về cho client kết quả như trên.

### 3.2. Code triển khai BFF
Dưới đây mình có chuẩn bị một project mẫu để minh họa cho mô hình BFF theo ví dụ nêu trên.

Link gitlab của project xem [tại đây!](http://gitlab.gpdn.net/khcp/daotao/thinhnq7/bff-sample.git)

**Cài đặt môi trường và chạy ứng dụng:**
***BFF Sample*** sử dụng Java 17 cho các service và dùng Docker để chạy MySQL. Làm theo hướng dẫn sau để chạy code:
1. Mở lần lượt các service trong IntelliJ
2. Cài đặt cho IntelliJ sử dụng Java 17 và load Maven
3. Mở một cửa sổ terminal tại thư mục ```bff-sample/docker``` và chạy câu lệnh ```docker-compose up -d``` để khởi động MySQL
4. Chạy lần lượt từng microservice với IntelliJ
5. Import file ```postman-api.json``` vào **Postman**.
6. Thực hiện thêm các sinh viên và sách được mượn với API ```Add new student``` và ```Add new borrow book``` vừa được import vào Postman.
7. Quan sát dữ liệu trả về từ API ```[MOBILE] get borrow book by id``` và API ```[WEB] get borrow book by id```

***Lưu ý***

1. Project sử dụng các cổng sau:
    - 8082: student-service
    - 8084: library-service
    - 8080: bff-gateway
    - 3306: MySQL

Đảm bảo các cổng trên chưa được sử dụng. Nếu muốn thay đổi cổng lắng nghe các dịch vụ thì sửa ở file ```application.properties``` đối với các ứng dụng Springboot và file ```docker-compose.yml``` cho các dịch vụ chạy bằng docker.

2. Nếu không import được file json vào **Postman** thì hạ phiên bản của **Postman** từ v9. trở xuống

3. Các Request cần gửi kèm theo Header "X-App-Type" có giá trị là "web" hoặc "mobile"
