---
sidebar_position: 3
title: CDC - Capture Data Change
---
# CDC - Capture data change
Làm việc với hệ thống database từ trước đến nay vẫn luôn là công việc khó khăn về hiệu suất và duy trì đồng bộ. Một trong các cách truyền thống đó là các hệ thống sẽ xử lý dữ liệu từng batch (batch-processing) để chuyển dữ liệu sang Data Warehouse một hoặc vài lần trong ngày. Tuy nhiên cách làm này sẽ tạo ra độ trễ và gây ra nguy cơ mất dữ liệu khi hệ thống sập. Với một số business cụ thể, giá trị của dữ liệu sẽ giảm dần theo thời gian, ví dụ như dữ liệu tài chính, cổ phiếu…
Từ nhu cầu trên, một phương pháp mới được ra đời đem đến giải pháp mạnh mẽ cho việc migrate và replicate dữ liệu giữa các hệ thống có tên là **Capture data change**
## 1) Khái niệm về CDC
**C**apture **D**ata **C**hange (CDC) là một kỹ thuật theo dõi và bắt giữ những thay đổi của dữ liệu xảy ra trong source database và liên tục cập nhật những thay đổi ấy cho các hệ thống khác.

### Các khái niệm liên quan
- **Source database**: Cơ sở dữ liệu gốc mà chúng ta muốn theo dõi thay đổi.
- **Source connector**: Connector đảm nhận nhiệm vụ đọc dữ liệu từ source database và đẩy chúng vào Kafka
- **External database**: Hệ thống nhận và xử lý sự kiện thay đổi để đảm bảo đồng bộ dữ liệu
- **Sink connector**: Connector để truyền dữ liệu từ Kafka đến External database
- **Change event**: Các sự kiện thông báo về các thay đổi dữ liệu như thêm, sửa hoặc xóa...

Một trong những công cụ để triển khai CDC mạnh mẽ và phổ biến trong cộng đồng phát triển là **Debezium**
## 2) Triển khai CDC với Debezium
### Debezium là gì?
**Debezium** là một dự án mã nguồn mở được phát triển bởi Red Hat. **Debezium** có khả năng theo dõi thay đổi dữ liệu trong các cơ sở dữ liệu phổ biến như MySQL, PostgreSQL, MongoDB và nhiều loại cơ sở dữ liệu khác. 

Thông thường, mỗi một transaction xảy ra ở database đều được lưu lại tại transaction log và các database khác nhau sẽ có cách ghi transaction log khác nhau.
**Debezium** sẽ sử dụng những connector đọc transaction log để phát hiện những thay đổi đã xảy ra với source database, nhờ đó mà **Debezium** có thể phát hiện thay đổi với từng dòng (row-level), từng record. Sau đó, những change-event tương ứng với từng transaction sẽ được tạo ra và gửi đến những streaming service như Kafka, RabbitMQ, AWS Kinesis, …

### Cách triển khai CDC với Debezium 
![CDC](https://datasecurity.viettel.vn/apps/files_pictureviewer/public_display?token=PltsrzOR48wkS20&file=CDC+diagram.jpg)
<center><i>Capture Data Change work flow</i></center>

### Source code minh họa
Dưới đây mình có chuẩn bị một project mẫu để minh họa cho việc triển khai CDC với Debezium

Link gitlab của project xem [tại đây!](http://gitlab.gpdn.net/khcp/daotao/thinhnq7/cdc-sample)

***Happy coding! ~.~***