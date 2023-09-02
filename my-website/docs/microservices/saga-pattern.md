---
sidebar_position: 1
title: SAGA Pattern
---

# SAGA PATTERN TRONG MICROSERVICES
## 1) Mở đầu
Hãy tưởng tượng bạn vừa chốt đơn một chiếc màn hình máy tính trên Shopee. Sau khi thanh toán qua ví ShopeePay thành công, chiếc màn hình máy tính của bạn được đóng gói và gửi đến địa chỉ nhà bạn. Tuy nhiên, địa chỉ mà bạn cung cấp bị sai. Đến hôm giao hàng shipper đã cố tìm đến địa chỉ đó nhưng không tìm thấy và gọi điện cho bạn. Bạn nhận ra đơn hàng đã sai địa chỉ và không thể nhận nên quyết định hủy bỏ đơn hàng. Lúc này thì chiếc màn hình sẽ trở về nơi sản xuất, nhưng tiền bạn đã bỏ ra có về túi của bạn không? Tất nhiên là có và về mặt logic nghiệp vụ thì sẽ hoàn tiền bằng cách nào? Có phải người quản trị hệ thống sẽ chuyển khoản lại cho bạn số tiền ấy và vào danh mục sản phẩm trên Shopee và cộng lại số lượng 1 cho sản phẩm cho màn hình sao? Nếu như làm theo cách này thì nghe có vẻ khá củ chuối nhỉ? 

Vậy có mô hình nào để giải quyết bài toán trên không? Hãy cùng mình tìm hiểu về SAGA pattern trong bài viết này nhé!
## 2) Khái niệm
**SAGA pattern** là một mô hình hoạt động dựa trên một chuỗi các transaction cục bộ trên từng service khác nhau để thực hiện một business logic trong kiến trúc **Microservices**

Ý tưởng của SAGA đó là: *"Thắng ăn cả - ngã về không"*. Điều đó có nghĩa hoặc là tất cả transaction được thực hiện thành công hoặc không có transaction nào được thực hiện.

SAGA có 2 cơ chế để rollback:
- *Backward*: Nếu một transaction thực hiện thất bại thì cần phải quay lại và hủy bỏ bất kì transaction thành công trước đó trong chuỗi
- *Forward*: Thực hiện retry transaction thất bại đến khi nó thành công.

Có 2 cách thức phổ biến để triển khai SAGA đó là **Orchestration** và **Choreography**
## 3) Orchestration - Command based
**SAGA Orchestration** là cách điều phối tập trung. Các transaction cục bộ được điều phối bởi một **Orchestrator service** (hay còn gọi là điều phối viên). Orchestrator sẽ yêu cầu các service thực hiện một transaction và phản hồi lại là thành công hay thất bại. Orchestrator dựa vào thông báo và đưa ra quyết định tiếp theo cho các service khác.

Tiếp tục xem xét ví dụ trên trong sơ đồ sau:
- *Order service*: Tiếp nhận đơn hàng và thông tin của người đặt hàng
- *Stock service*: Kiểm tra và cập nhật số lượng của các mặt hàng
- *Payment service*: Thực hiện các giao dịch với ví tiền của người dùng
- *Delivery service*: Giao hàng tới địa chỉ cá nhân cho người đặt hàng

![Orchestration - Command based](https://datasecurity.viettel.vn/apps/files_pictureviewer/public_display?token=KFOLFlLbQ2wgHID&file=Orchestration.jpg)
<center><i>Sơ đồ hoạt động của Orchestration.</i></center>


**Giải thích:** 
- Đầu tiên **Orchestrator** gửi yêu cầu cho **Order service** để tạo thông tin cho một order mới. Sau khi tạo thành công, **Order service** phản hồi lại cho **Orchestrator** rằng một order mới đã được tạo với status là *"CREATED"*.
- **Orchestrator** nhận thông tin về đơn hàng và yêu cầu **Stock service** kiểm tra xem trong kho còn loại hàng đó không. **Stock service** tiến hành kiểm tra tồn kho và trừ đi số lượng sản phẩm sau đó thông báo cho **Orchestrator**.
- **Orchestrator** yêu cầu **Payment service** kiểm tra số dư tài khoản của bạn. Sau khi kiểm tra số dư hợp lệ, Payment service tiến hành trừ tiền và phản hồi cho **Orchestrator**.
- Cuối cùng, **Delivery service** nhận yêu cầu của **Orchestrator** và giao hàng cho bạn. Tuy nhiên shipper gọi cho bạn không được và đã thực hiện lại vài lần sau đó. Kết quả là vẫn thể liên lạc với bạn và cuối ngày thì đơn hàng đó bị hủy. **Delivery service** phản hồi tới **Orchestrator** rằng việc giao hàng thất bại. Lúc này **Orchestrator** sẽ phải gửi đi yêu cầu tới các service trước để hoàn lại số hàng trong kho, hoàn lại số tiền cho bạn và cập nhật trạng thái đơn hàng là *"FAILED"*.

## 4) Choreography - Event based
SAGA Choreography là cách điều phối phi tập trung. Các service tương tác trực tiếp với nhau thông qua các event. Nếu có một transaction nào thất bại thì các transaction thành công trước đó sẽ lần lượt thực hiện rollback.

Vẫn là ví dụ trên nhưng triển khai theo Choreography:
- *Order service*: Tiếp nhận đơn hàng và thông tin của người đặt hàng
- *Stock service*: Kiểm tra và cập nhật số lượng của các mặt hàng
- *Payment service*: Thực hiện các giao dịch với ví tiền của người dùng
- *Delivery service*: Giao hàng tới địa chỉ cá nhân cho người đặt hàng

![Choreography - Event based](https://datasecurity.viettel.vn/apps/files_pictureviewer/public_display?token=xpz0XydMZ2Y2BKv&file=Choreography+Saga.jpg)
<center><i>Sơ đồ hoạt động của Choreography.</i></center>

**Giải thích:** 
- Đầu tiên **Order service** tạo một đơn hàng và gửi đi sự kiện là **Order event** mang thông tin về đơn hàng đó
- **Stock service** lắng nghe và nhận sự kiện **Order event** từ **Order service** và thực hiện kiểm tra số lượng tồn kho của sản phẩm đó để trừ số đi số lượng đặt hàng. Sau đó **Stock service** gửi đi sự kiện **Stock event**
- **Payment service** lắng nghe và nhận sự nhiên **Stock event** tự **Stock service** và thực hiện kiểm tra số dư trong ví của bạn để trừ đi số tiền của đơn hàng. Sau đó **Payment service** gửi đi sự kiện **Payment event**.
- Cuối cùng, **Delivery service** lắng nghe và nhận sự kiện **Payment event** từ **Payment service** và thực hiện giao hàng đến địa chỉ nhà bạn.

Đối với mỗi transaction trên, khi xảy ra lỗi thì service đó sẽ thực hiện retry sau một số lần. Nếu thành công, các transaction phía sau tiếp tục được thực hiện. Nếu thất bại, service đó sẽ gọi đến hàm *revoke* riêng của service. Hàm này sẽ lần lượt gọi tiếp các hàm *revoke* của các service trước đó để rollback dữ liệu mà transaction đó thực hiện.

## 5) Code minh họa
Dưới đây mình có chuẩn bị một project mẫu để minh họa cho mô hình SAGA Choreography

Link github của project xem [tại đây!](http://gitlab.gpdn.net/khcp/daotao/thinhnq7/saga-pattern-sample.git)

### 5.1. Cài đặt môi trường và chạy ứng dụng
**SAGA pattern sample** sử dụng Java 17 cho các service và dùng Docker để chạy Kafka và MySQL. Làm theo hướng dẫn sau để chạy code:
1. Mở lần lượt các service trong IntelliJ
2. Cài đặt cho IntelliJ sử dụng Java 17 và load Maven
3. Mở một cửa sổ terminal tại thư mục ```saga-pattern-sample/docker``` và chạy câu lệnh ```docker-compose up -d``` để khởi động MySQL và Kafka
4. Chạy lần lượt từng microservice với IntelliJ
5. Import file ```postman-api.json``` vào **Postman** để test các api.
### 5.2. Một vài lưu ý
**1.** Project sử dụng các cổng sau:
- 8080: order-service
- 8081: payment-service
- 8082: stock-service
- 8083: delivery-service
- 9092: Kafka
- 2181: Zookeeper
- 3307: MySQL

    Đảm bảo các cổng trên chưa được sử dụng. Nếu muốn thay đổi cổng lắng nghe các dịch vụ thì sửa ở file ```application.properties``` đối với các ứng dụng Springboot và file ```docker-compose.yml``` cho các dịch vụ chạy bằng docker.

**2.** Nếu không import được file json vào **Postman** thì hạ phiên bản của **Postman** từ v9. trở xuống

**3.** Để minh họa lỗi của các service có thể tắt một trong số các service hoặc api ```/api/order``` làm như sau:
- **Order service**: Đơn hàng thiếu 1 trong các trường có trong mẫu **Postman**
- **Stock service**: Trường ```quantity``` lớn hơn số lượng sản phẩm hoặc trường ```item``` là sản phẩm không trong database của **Stock service**
- **Payment service**: Trường ```amount``` > 1000000
- **Delivery service**: Trường ```address``` để trống

**4.** Để các transaction có thể rollback chính xác ta cần lưu ý:
- Nắm rõ thứ tự chuỗi transaction
- Hàm revoke của 1 service nên nhận sự kiện của transaction liền trước nó và gửi thêm sự kiện revoke tới transaction liền sau nó. Khi lỗi ở đâu thì chuỗi sẽ được rollback hoàn toàn
- Event phải mang đủ và chính xác các thông tin mà hàm revoke cần để thực hiện rollback

## 6. Khi nào nên sử dụng SAGA Pattern và sử dụng mô hình nào?
**Saga pattern** thường phù hợp trong các hệ thống có quy mô lớn và phức tạp, trong đó các giao transacton phân tán cần được quản lý một cách linh hoạt và có khả năng phục hồi. Tùy vào từng trường hợp với các ưu nhược điểm dưới đây, ta có thể cân nhắc sử dụng **Orchestration** hoặc **Choreography**

### Orchestration
- **Ưu điểm**: Orchestration giúp dễ dàng quản lý và điều phối các bước trong quy trình. Nó cũng cho phép thực hiện các thay đổi trong quy trình một cách tương đối dễ dàng.
- **Nhược điểm**: Orchestration có thể trở nên điểm độc cô lớn và điểm yếu trong hệ thống. Nếu điểm điều khiển chính gặp sự cố, toàn bộ quy trình có thể bị ảnh hưởng.
### Choreography
- **Ưu điểm**: Choreography cho phép sự phản hồi nhanh chóng trong hệ thống. Mỗi dịch vụ có thể tự quyết định khi nào cần thực hiện một hành động.
- **Nhược điểm**: Việc theo dõi và hiểu rõ các luồng tương tác có thể trở nên khó khăn khi hệ thống trở nên phức tạp.

***Happy coding! ~.~***


