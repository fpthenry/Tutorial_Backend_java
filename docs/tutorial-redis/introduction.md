---
sidebar_position: 1
slug: /tutorial-redis/introduction
title: Giới thiệu về Redis
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [greetings]
---

## In this article
* [Overview](#overview)
  1. [Redis là gì?](#redis-là-gì)
* [Ứng dụng của Redis?](#ứng-dụng-của-redis)
* [Các thuật ngữ](#các-thuật-ngữ)
* [Các kiểu dữ liệu Redis để lưu trữ Value?](#các-kiểu-dữ-liệu-redis-để-lưu-trữ-value)
* [Persistent redis là gì?](#persistent-redis-là-gì)
  1. [RDB(Redis database file)](#redis-database-file)
      + [Ưu điểm](#rdb-ưu-điểm)
      + [Nhược điểm](#rdb-nhược-điểm)
  2. [AOF(Append Only File)](#append-database-file)
      + [Ưu điểm](#append-only-file-ưu-điểm)
      + [Nhược điểm](#append-only-file-nhược-điểm) 
* [Các tiêu chuẩn về an toàn thông tin sử dụng Redis](#các-tiêu-chuẩn-về-an-toàn-thông-tin-sử-dụng-redis)
  1. [Redis có an toàn không?](#redis-có-an-toàn-không)
  2. [Thông tin về các lỗ hổng bảo mật](#thông-tin-về-các-lỗ-hổng-bảo-mật)
  3. [Các tiêu chuẩn về tối ưu, hiệu năng và kích cỡ](#các-tiêu-chuẩn-về-tối-ưu-hiệu-năng-và-kích-cỡ)
* [Các tiêu chuẩn về tối ưu và kích cỡ](#các-tiêu-chuẩn-về-tối-ưu-và-kích-cỡ)


## Overview
  ### **Redis là gì?**

  **Redis**(Remote Dictionary Server) là một mã nguồn mở được dùng để **lưu trữ dữ liệu có cấu trúc**, có thể sử dụng **như 1 database**, **bộ nhớ cache** hay **1 message broker**.
  Nó là hệ thống lưu trữ dữ liệu **key-value** lớn và mạnh mẽ nhất hiện nay. Redis nổi bật với việc hỗ trợ nhiều cấu trúc dữ liệu cơ bản như: HASH, LIST, SET, SORT SET, STRING...
  Tất cả dữ liệu được ghi và lưu trữ trên RAM -> do đó tốc độ đọc ghi dữ liệu rất là nhanh.


## Ứng dụng của Redis?
Ngoài khả năng lưu trữ dạng **Key-value** trên RAM thì Redis còn hỗ trợ tính năng **xắp xếp**, **query**, **backup** dữ liệu trên đĩa cứng cho phép phục hồi dữ liệu khi hệ thống gặp sự cố ... và có thể nhân bản(chạy nhiều server cùng lúc).

**Caching**: Sử dụng làm bộ nhớ đệm. Chính tốc độ đọc ghi nhanh mà Redis sử dụng làm bố nhớ đệm, nơi chia sẻ dữ liệu giữa các ứng dụng hoặc làm database tạm thời. Ngoài ra redis có thể sử dụng làm full page cache cho website. Vì tính nhất quán của Redis nên khi tải lại trang người dùng sẽ cảm nhận thấy sẽ không bị chậm.

**Counter**: Sử dụng làm **bộ đếm**. Với thuộc tính tăng giảm thông số rất nhanh trong khi dữ liệu được lưu trữ trên RAM, **Sets**, **set và sortsets** được sử dụng đếm lượt view của 1 website.
+ ví dụ: Các bảng xếp hạng game. **Redis** có hỗ trợ thread safe do đó nó có thể đồng bộ dữ liệu giữa các request.

**Queues**: Tạo hàng đợi để xử lý lần lượt các request. **Query** cho phép lưu trữ theo list và cung cấp rất nhiều thao tác với các phần tử trong list -> vì vậy sử dụng như 1 message queue.

**Publish/Suscribe (Pub/Sub)**: Tạo kênh chia sẻ dữ liệu. Redis hỗ trợ tạo các channel để trao đổi dữ liệu giữa publisher và subscriber giống như channel trong Socket Cluster hay topic trong Apache Kafka. Ví dụ: Pub/Sub được sử dụng theo dõi các kết nối trong mạng xã hội hoặc các hệ thống chat.


## Các thuật ngữ?
  + **Key-Value Store**: Redis là một hệ thống lưu trữ dữ liệu key-value, trong đó mỗi dữ liệu được lưu trữ dưới dạng một cặp key (khóa) và value (giá trị).
  + **String**: Redis hỗ trợ lưu trữ các giá trị dạng string. Các keys có thể liên quan đến các giá trị string.

  + **Hashes**: Hashes là một tập hợp các cặp key-value trong Redis. Các values có thể được lưu trữ dưới dạng hash, cho phép bạn truy cập nhanh chóng vào các phần cụ thể của dữ liệu.

  + **Lists**: Lists là danh sách dữ liệu có thứ tự trong Redis. Các phần tử có thể thêm vào hoặc loại bỏ ở đầu hoặc cuối danh sách.

  + **Sets**: Sets là một tập hợp các phần tử duy nhất, không có thứ tự cụ thể. Sets trong Redis cho phép thêm, xóa và thực hiện các phép toán tập hợp.

  + **Sorted Sets (ZSets)**: Sorted sets là một loại tập hợp trong đó mỗi phần tử được gắn với một điểm số (score). Các phần tử được sắp xếp dựa trên điểm số và cho phép bạn truy vấn các phần tử theo thứ tự.

  + **Pub/Sub (Publish/Subscribe)**: Redis hỗ trợ mô hình gửi thông điệp và đăng ký thông qua cơ chế Publish/Subscribe. Publishers gửi thông điệp tới các channels (kênh), và subscribers đăng ký để nhận thông điệp từ các channels mà họ quan tâm.

  + **Expire (TTL)**: Bạn có thể đặt một thời gian tồn tại (time-to-live - TTL) cho mỗi key trong Redis. Key sẽ tự động bị xóa sau khi thời gian tồn tại đã hết.

  + **Persistence**: Redis hỗ trợ các cơ chế để lưu trữ dữ liệu xuống đĩa để đảm bảo tính bền vững sau khi khởi động lại.

  + **Cache**: Redis thường được sử dụng như một cache (bộ nhớ đệm), giúp lưu trữ tạm thời các dữ liệu phổ biến để cải thiện hiệu suất truy vấn dữ liệu.

  + **Cluster**: Redis Cluster là một cách để mở rộng Redis trên nhiều máy chủ, cung cấp tính sẵn sàng và tăng cường hiệu suất.

  + **Lua Scripting**: Redis cho phép bạn thực thi các tập lệnh Lua trực tiếp trong máy chủ, giúp bạn thực hiện các tác vụ phức tạp mà không cần truyền nhiều lệnh tới máy chủ.

## Các kiểu dữ liệu Redis để lưu trữ Value?
Khác với các Database thông thường Redis  không có table(bảng). **Redis lưu trữ data dưới dạng key-value**
 ```bash
  + STRING : string, integer, float. Redis có thể làm việc với string, từng phần tử của string, cũng như việc tăng giảm giá trị trong integer và float.

  + LIST: là một danh sách của strings. xắp xếp theo thứ tự insert. Redis có thể thêm 1 phần tử vào đầu hoặc cuối list -> truy xuất cực nhanh(nhược điểm truy xuất phần tử ở giữa list rất chậm)

  + SET: tập hợp các string(không được sắp xếp). Redis hỗ trợ các thao tác thêm, đọc, sửa, xóa phần tử.

  + HASH: lưu trữ hash table của các cặp key-value(trong đó xắp xếp ngẫu nhiên không theo thứ tự các phần tử). redis hỗ trợ các thao tác thêm, đọc, sửa, xóa.

  + SORTED SET(ZSET):  là 1 danh sách, trong đó mỗi phần tử là map của 1 string (member) và 1 floating-point number (score), danh sách được sắp xếp theo score này. Các phần tử của zset được sắp xếp theo thứ tự từ score nhỏ tới lớn.  
 ```

## Persistent redis là gì?
Bên cạnh việc lưu key-value trên bộ nhớ RAM, **Redis có 2 background threads** chuyên làm nhiệm vụ định kỳ ghi dữ liệu lên đĩa cứng.
  + RDB(Redis database file)
  + AOF(Append Only File)

### RDB(Redis database file)
  + **RDB** thực hiện tạo và sao lưu snapshot của DB vào ổ cứng sau mỗi khoảng thời gian nhất định.
  #### Ưu điểm
    + RDB cho phép người dùng lưu các version khác nhau của DB, rất thuận tiện khi có sự cố xảy ra.
    + Bằng việc lưu trữ data vào 1 file cố định, người dùng có thể dễ dàng chuyển data đến các data centers, máy chủ khác nhau.

    **(RDB giúp tối ưu hóa hiệu năng của Redis. Tiến trình redis chính sẽ chỉ làm các công việc trên RAM bao gồm các thao tác cơ bản được yêu cầu từ phía client như thêm/đọc/xóa. Trong khi đó 1 tiến trình con sẽ đảm nhiệm các thao tác Disk I/O. cách tổ chức này giúp tối đa hiệu năng của redis.)**
  #### Nhược điểm
    + RDB không phải là lựa chọn tốt nếu bạn muốn giảm thiểu tối đa nguy cơ mất mát dữ liệu.
    + Thông thường người dùng sẽ set up để tạo RDB snapshot 5 phút 1 lần (hoặc nhiều hơn). Do vậy, trong trường hợp có sự cố, Redis không thể hoạt động, dữ liệu trong những phút cuối sẽ bị mất.
    + RDB cần dùng fork() để tạo tiến trình con phục vụ cho thao tác disk I/O. Trong trường hợp dữ liệu quá lớn, quá trình fork() có thể tốn thời gian và server sẽ không thể đáp ứng được request từ client trong vài milisecond hoặc thậm chí là 1 second tùy thuộc vào lượng data và hiệu năng CPU.

### AOF(Append Only File)
  + **AOF** lưu lại tất cả các thao tác write mà server nhận được, các thao tác này sẽ được chạy lại khi restart server hoặc tái thiết lập dataset ban đầu.
  #### Ưu điểm
    + Sử dụng AOF sẽ giúp đảm bảo dataset được bền vững hơn so với dùng RDB. Người dùng có thể config để Redis ghi log theo từng câu query hoặc mỗi giây 1 lần.
    + Redis ghi log AOF theo kiểu thêm vào cuối file sẵn có, do đó tiến trình seek trên file có sẵn là không cần thiết. Ngoài ra, kể cả khi chỉ 1 nửa câu lệnh được ghi trong file log (có thể do ổ đĩa bị full), Redis vẫn có cơ chế quản lý và sửa chữa lối đó (redis-check-aof).
    + Redis cung cấp tiến trình chạy nền, cho phép ghi lại file AOF khi dung lượng file quá lớn.
    
    **Trong khi server vẫn thực hiện thao tác trên file cũ, 1 file hoàn toàn mới được tạo ra với số lượng tối thiểu operation phục vụ cho việc tạo dataset hiện tại. Và 1 khi File moi được ghi xong , redis sẽ chuyển sang thực hiện thao tác ghi log trên file moi**

  #### Nhược điểm
    + File AOF thường lớn hơn file RDB với cùng 1 dataset.
    + AOF có thể chậm hơn RDB tùy theo cách thức thiết lập khoảng thời gian cho việc sao lưu vào ổ cứng. Tuy nhiên, nếu thiết lập log 1 giây 1 lần có thể đạt hiệu năng tương đương với RDB.
    + Developer của Redis đã từng gặp phải bug với AOF (mặc dù là rất hiếm), đó là lỗi AOF không thể tái tạo lại chính xác dataset khi restart Redis. Lỗi này chưa gặp phải khi làm việc với RDB bao giờ.

## Các tiêu chuẩn về an toàn thông tin sử dụng redis?
**Khi triển khai và sử dụng Redis, đảm bảo an toàn thông tin là rất quan trọng để bảo vệ dữ liệu và ngăn chặn các cuộc tấn công. Dưới đây là một số tiêu chuẩn và thực hành an toàn thông tin mà bạn nên áp dụng khi sử dụng Redis**:

+ **Mật khẩu bảo mật**: Đặt mật khẩu (password) cho máy chủ Redis để ngăn các người dùng trái phép truy cập. Đảm bảo mật khẩu được đặt mạnh và thường xuyên thay đổi.

+ **Cắt giảm bề ngoài**: Đảm bảo rằng Redis chỉ lắng nghe trên các giao diện mạng cần thiết và không lắng nghe trên giao diện mạng không cần thiết.

+ **Tường lửa (Firewall)**: Sử dụng tường lửa để hạn chế truy cập đến máy chủ Redis. Chỉ cho phép các địa chỉ IP cần thiết kết nối tới cổng Redis.

+ **Giao thức TLS/SSL**: Sử dụng giao thức SSL/TLS để mã hóa dữ liệu khi truyền tải giữa client và server. Điều này đảm bảo rằng thông tin được bảo vệ khỏi nguy cơ bị đánh cắp hoặc đọc trái phép.

+ **Quyền người dùng**: Đặt quyền truy cập cẩn thận cho người dùng và ứng dụng. Hạn chế quyền truy cập dựa trên nguyên tắc "nguyên tắc tối thiểu" (least privilege principle).

+ **Ngắt lệnh nguy hiểm**: Redis hỗ trợ một số lệnh có thể gây nguy hiểm như FLUSHALL (xóa tất cả dữ liệu) hoặc CONFIG (thay đổi cấu hình). Hạn chế sử dụng các lệnh này và chỉ cho phép người dùng có quyền cao sử dụng chúng.

+ **Monitor và Logging**: Theo dõi và ghi lại các hoạt động trong Redis để phát hiện sự cố và hoạt động bất thường.

+ **Cập nhật thường xuyên**: Đảm bảo rằng bạn đang sử dụng phiên bản Redis mới nhất và cập nhật các bản vá bảo mật khi có sẵn.

+ **Giới hạn tài nguyên**: Hạn chế tài nguyên (CPU, bộ nhớ) mà Redis có thể sử dụng để ngăn cản trở hoạt động bình thường của hệ thống.

+ **Xác thực bên thứ ba**: Tránh sử dụng các thư viện hoặc mã nguồn mở không đáng tin cậy để tương tác với Redis, để tránh lỗ hổng bảo mật.

### Redis có an toàn không?
Redis cung cấp các tính năng an toàn như mật khẩu bảo mật, hỗ trợ SSL/TLS để mã hóa dữ liệu truyền tải, và khả năng giới hạn quyền truy cập. Tuy nhiên, an toàn trong Redis phụ thuộc vào cách bạn cấu hình, triển khai và quản lý nó trong môi trường của mình và cần xem xét về vấn đề an toàn được nêu mục trên.


### Thông tin về các lỗ hổng bảo mật
Hiện tại chưa có thông tin về các lỗ hổng bảo mật Redis cụ thể. 

## Các tiêu chuẩn về tối ưu và kích cỡ
Khi triển khai và quản lý Redis, có một số tiêu chuẩn về tối ưu và kích cỡ bạn nên xem xét để đảm bảo hiệu suất tốt và sử dụng tài nguyên một cách hiệu quả. Dưới đây là một số tiêu chuẩn quan trọng:
+ **Kích thước máy chủ và tài nguyên**:
Chọn kích thước máy chủ (CPU, bộ nhớ, ổ cứng) phù hợp với khả năng sử dụng của ứng dụng và tải công việc.
Đảm bảo đủ bộ nhớ RAM để lưu trữ dữ liệu trong bộ nhớ (in-memory) mà không gây phải sử dụng ổ cứng làm bộ nhớ phụ (swap), vì điều này sẽ làm giảm hiệu suất.

+ **Cấu hình Redis:**
Tinh chỉnh cấu hình Redis phù hợp với ứng dụng của bạn. Các cài đặt như số kết nối đồng thời (maxclients), số luồng IO (io-threads), và quản lý bộ nhớ (maxmemory) cần được xem xét cẩn thận.
Sử dụng thời gian sống (TTL) cho các keys để giải phóng tài nguyên bộ nhớ.

+ **Phân loại dữ liệu:**
Sử dụng cẩn thận các kiểu dữ liệu Redis như strings, hashes, lists, sets và sorted sets để lưu trữ dữ liệu. Chọn kiểu dữ liệu phù hợp cho từng loại dữ liệu.

+ **Sử dụng Cache:**
Sử dụng Redis như một cache để lưu trữ dữ liệu tạm thời và giảm tải cho cơ sở dữ liệu chính.

+ **Replication (Sao chép dữ liệu)**:
Sử dụng sao chép dữ liệu để tạo bản sao của máy chủ Redis, cung cấp sự dự phòng và tăng khả năng sẵn sàng.

+ **Cluster:**
Sử dụng Redis Cluster để mở rộng hệ thống trên nhiều máy chủ, tăng khả năng chịu tải và sẵn sàng.

+ **Persistent Storage (Lưu trữ bền vững)**:
Sử dụng cơ chế RDB hoặc AOF để lưu trữ dữ liệu xuống đĩa, đảm bảo tính bền vững sau khi khởi động lại Redis.

+ **Monitoring và Scaling (Giám sát và Mở rộng)**:
Sử dụng công cụ giám sát và log để theo dõi hiệu suất và sự hoạt động của Redis. Tự động mở rộng hệ thống khi cần thiết.

+ **Bảo mật**:
Đảm bảo cấu hình bảo mật phù hợp, bao gồm mật khẩu, giao thức mã hóa và giới hạn quyền truy cập.


Tác giả: [Nguyễn Anh Tuấn](https://github.com/FPTooHenry)

![Hình ảnh tác giả](https://github.com/FPTooHenry.png)


