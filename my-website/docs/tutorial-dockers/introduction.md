---
sidebar_position: 1
slug: /tutorial-dockers/introduction
title: Giới thiệu về Docker
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [greetings]
---

## In this article
* [Overview](#overview)
* [Docker là gì?](#docker-là-gì)
* [Lệnh cơ bản](#lệnh-cơ-bản)
* [Các thuật ngữ](#các-thuật-ngữ)
* [Các tiêu chuẩn về an toàn thông tin sử dụng Docker](#các-tiêu-chuẩn-về-an-toàn-thông-tin-sử-dụng-docker)
  1. [Docker có an toàn không?](#docker-có-an-toàn-không)
  2. [Tấn Công về bề mặt Docker Container](#tấn-công-về-bề-mặt-docker-container)
  3. [Thông tin về các lỗ hổng bảo mật](#thông-tin-về-các-lỗ-hổng-bảo-mật)
  5. [Các tiêu chuẩn về tối ưu, hiệu năng và kích cỡ](#các-tiêu-chuẩn-về-tối-ưu-hiệu-năng-và-kích-cỡ)
* [Các tiêu chuẩn về tối ưu và kích cỡ](#các-tiêu-chuẩn-về-tối-ưu-và-kích-cỡ)
* [Docker Compose](#docker-compose)


## Overview

**Docker**  là 1 phần mềm ảo hóa hệ điều hành OS virtualization được sử dụng để tạo, triển khai và chạy ứng dụng thông qua Docker container. nhẹ hơn các máy ảo hiện tại
 cùng với khả năng đóng gói ứng dụng vào các container kèm theo cả thư viện và các dependencies, Docker giúp giảm bớt sự phụ thuộc vào hệ thống đồng thời giúp cho quá trình làm việc trở lên đơn giản hơn.

## Docker là gì?
**Docker** là một công cụ giúp tạo ra và triển khai các container để phát triển chạy ứng dụng được dễ dàng.   
**Container** là môi trường mà ở đó các lập trình viên có thể đưa vào các thành phần cần thiết để ứng dụng chạy được bằng cách đóng gói ứng dụng bằng container.    
+ Với Docker đảm bảo rằng ứng dụng chạy được và giống nhau ở các máy khác nhau(linux, windows...)
## Lệnh cơ bản?
```bash
docker --version: Hiển thị phiên bản Docker đang được cài đặt.

docker pull <image-name>: Tải xuống một hình ảnh Docker từ Docker Hub hoặc một kho lưu trữ khác.

docker images: Liệt kê các hình ảnh Docker đã tải xuống hoặc đã tạo trên máy của bạn.

docker build -t <image-name> <path-to-dockerfile>: Xây dựng một hình ảnh Docker từ một Dockerfile có sẵn.

docker run <image-name>: Chạy một container từ một hình ảnh đã tải xuống hoặc đã xây dựng.

docker ps: Liệt kê các container đang chạy.

docker ps -a: Liệt kê tất cả các container, bao gồm cả các container đang dừng.

docker stop <container-id>: Dừng một container đang chạy.

docker start <container-id>: Khởi động lại một container đã dừng.

docker restart <container-id>: Khởi động lại một container đang chạy.

docker rm <container-id>: Xóa một container đã dừng.

docker rmi <image-name>: Xóa một hình ảnh Docker.

docker exec -it <container-id> <command>: Chạy một lệnh bên trong một container đang chạy.

docker logs <container-id>: Xem log của một container.

docker inspect <container-id>: Xem thông tin chi tiết về một container hoặc một hình ảnh.

docker-compose up: Chạy các dịch vụ được định nghĩa trong tệp docker-compose.yml.
```

## Các thuật ngữ?
**Container**: Một môi trường chạy cô lập, chứa tất cả các thành phần cần thiết để thực thi một ứng dụng. Container là phiên bản thực thi của một hình ảnh Docker.

**Image** : Một gói nhị phân chứa tất cả các thành phần cần thiết để thực thi một ứng dụng, bao gồm mã nguồn, thư viện, biến môi trường và các phụ thuộc. Images là cơ sở để tạo các container.

**Dockerfile**: Một tệp văn bản chứa các chỉ thị để xây dựng một hình ảnh Docker. Nó định nghĩa các bước để sao chép mã nguồn, cài đặt các phụ thuộc và cấu hình ứng dụng.

**Docker Hub**:  là một “github for docker images”. Trên DockerHub có hàng ngàn public images được tạo bởi cộng đồng cho phép bạn dễ dàng tìm thấy những image mà bạn cần. Và chỉ cần pull về và sử dụng với một số config mà bạn mong muốn.

**Docker Compose**: Công cụ cho phép bạn định nghĩa và quản lý các ứng dụng multi-container. Bằng cách sử dụng tệp docker-compose.yml, bạn có thể định nghĩa các dịch vụ, mạng và các cài đặt khác cần thiết cho việc triển khai một ứng dụng phức tạp với nhiều container liên quan đến nhau.


**Registry** : Trong Docker, "registry" là một dịch vụ lưu trữ cho các image Docker. Nó là nơi bạn có thể lưu trữ, quản lý và chia sẻ các image Docker được tạo ra. Mỗi image Docker chứa một phiên bản cụ thể của một ứng dụng hoặc môi trường, và registry cho phép bạn lưu trữ các image này để có thể sử dụng lại hoặc chia sẻ với người khác.

## Các tiêu chuẩn về an toàn thông tin sử dụng docker?
Có một số tiêu chuẩn và quy tắc tối ưu về hiệu năng và kích cỡ khi sử dụng Docker để đảm bảo ứng dụng hoạt động một cách hiệu quả và tiết kiệm tài nguyên. Dưới đây là một số hướng dẫn tối ưu thông thường:

**Cập nhật và Patching**: Luôn duy trì các hệ thống Docker và các container của bạn bằng cách cài đặt các bản vá và bản vá mới nhất để ngăn chặn các lỗ hổng bảo mật đã biết.

**Sử dụng Hình ảnh Tin cậy**: Chỉ sử dụng hình ảnh từ các nguồn tin cậy. Tránh sử dụng hình ảnh không rõ nguồn gốc hoặc không được xác minh.

**Cài đặt Security Best Practices**: Tuân theo các nguyên tắc và thực hành tốt nhất về an ninh Docker như tắt các dịch vụ không cần thiết trong container, hạn chế quyền truy cập, và sử dụng nguyên tắc "tạo ít quyền" (Principle of Least Privilege).

**Giới hạn Tài nguyên**: Hạn chế tài nguyên của container bằng cách sử dụng các giới hạn tài nguyên như CPU và bộ nhớ để ngăn container ảnh hưởng đến các container khác trên cùng một máy chủ.

**Kết nối mạng An toàn**: Sử dụng mạng riêng để kết nối các container và tạo các tường lửa ảo để ngăn container không cần thiết truy cập vào các dịch vụ khác.

**Kiểm tra Quyền truy cập**: Đảm bảo rằng các tài khoản và quyền truy cập trong container được cấu hình chính xác và an toàn.

**Sử dụng Docker Bench for Security**:  Docker Bench for Security là một công cụ tự động kiểm tra an toàn Docker và cung cấp các hướng dẫn về cách cải thiện an ninh.

**Giám sát và Log**: Sử dụng các công cụ giám sát và ghi log để theo dõi hoạt động của các container và phát hiện sớm các vấn đề an toàn.

**Chứng thực và Ủy quyền**: Xác minh và ủy quyền người dùng và quyền truy cập thông qua các cơ chế chứng thực và ủy quyền như OAuth, OpenID Connect, LDAP, và RBAC.

**Chứng thực và Ủy quyền**: Bảo vệ khỏi việc truy cập trái phép vào Docker Daemon và quản trị hệ thống.

### Docker có an toàn không?
**Docker có an toàn không?**  Hầu hêt đều cho rằng Docker tuyệt đối an toàn, họ có thể pull Docker image từ DockerHub và chạy nó mà không cần quan tâm rằng ai là người push image đó lên hoặc không quan tâm đến tính xác thực của image. Docker không bảo mật giống như máy ảo, bởi chúng ta không thể tương tác trực tiếp với kernel, không có quyền truy cập vào các file hệ thống như /sys và sys/fs,proc/*.
+ Việc cấu hình Docker dễ xảy ra sai sót hơn so với cấu hình một máy ảo. Tính linh hoạt trong việc chia sẻ tài nguyên của Docker cũng có thể gây ra các lỗi khi thực hiện cấu hình.

### Tấn Công về bề mặt Docker Container
+ Nếu kẻ tấn công là người có thể khởi động container (có quyền truy cập tới Docker API) thì ngay lập tức hắn ta có quyền của một root để truy cập tới host.
+ Nếu kẻ tấn công có được quyền quản lý bên trong container thì bạn sẽ gặp rắc rối, bởi hắn ta có thể thực hiện các lệnh làm ảnh hưởng đến host kernel. Nhưng thật không may là rất nhiều Docker image cho phép chạy dưới quyền root mà bỏ qua việc sử dụng User trong Dockerfile. Đây là một vấn đề bảo mật không thuộc về phía Docker mà thuộc về phía người dùng.
+ Các lỗ hổng bảo mật trên container máy chủ được chia sẻ cùng với kernel host, nó có thể khiến kẻ tấn công phá vỡ container đẻ xâm nhập vào kernel của host. 
+ Đôi khi cấu hình Container tệ cũng khiến bạn có được đặc quyền truy cập vào máy chủ bên dưới. 
+ Nếu bạn có một Container mount với hệ thống tệp trên máy chủ, bạn có thể sửa đổi những thứ trong hệ thống tệp đó để nâng cấp đặc quyền.

### Thông tin về các lỗ hổng bảo mật
+ Ngày nay, docker đang được sử dụng rộng rãi và hầu hết các developer làm việc với docker đều nghĩ rằng nó an toàn.

+ 10 image Docker hàng đầu với hơn 10 triệu lượt tải xuống, mỗi image chứa ít nhất 30 lỗ hổng.

+ Trong số 10 docker image miễn phí phổ biến, 50% có lỗ hổng bảo mật.

+ 80% developer không kiểm tra docker image của họ trong quá trình phát triển.

+ 50% developer hoàn toàn không quét Docker image để tìm các lỗ hổng.

+ 20% Docker image có lỗ hổng bảo mật có thể được giải quyết bằng cách xây dựng lại.
## Các tiêu chuẩn về tối ưu và kích cỡ
+ **Tối ưu Kích thước Hình ảnh**: Sử dụng các công cụ như Docker Slim, Multi-Stage Builds và các phương pháp khác để loại bỏ các thành phần không cần thiết và giảm kích thước hình ảnh.
+ **Cài đặt Tùy chỉnh Kernel**: Đối với các ứng dụng yêu cầu hiệu năng cao, bạn có thể cài đặt tùy chỉnh kernel để tối ưu hóa việc sử dụng tài nguyên.
........

## Docker compose
**Docker Compose** là một công cụ trong Docker giúp bạn định nghĩa và quản lý các ứng dụng multi-container một cách dễ dàng. Thay vì sử dụng nhiều lệnh Docker riêng lẻ để triển khai và quản lý các container, bạn có thể sử dụng Docker Compose để tổ chức các dịch vụ, mạng và các cài đặt khác liên quan đến nhau trong một tệp cấu hình duy nhất.

**Các ưu điểm của Docker Compose bao gồm:**

**Dễ sử dụng**: Docker Compose cho phép bạn sử dụng một tệp YAML đơn giản để định nghĩa cấu hình của các dịch vụ và các mối quan hệ giữa chúng.

**Quản lý các dịch vụ liên quan đến nhau**: Khi một ứng dụng bao gồm nhiều dịch vụ cần liên kết với nhau (ví dụ: máy chủ web và cơ sở dữ liệu), Docker Compose giúp bạn dễ dàng định nghĩa và triển khai chúng cùng nhau.

**Khởi động và dừng ứng dụng dễ dàng**: Với một lệnh duy nhất (docker-compose up), bạn có thể khởi động toàn bộ ứng dụng với các dịch vụ và mạng cần thiết.

**Tùy chỉnh môi trường**: Bạn có thể sử dụng biến môi trường và tệp .env để cung cấp các giá trị động cho các dịch vụ trong quá trình triển khai.

**Tái sử dụng và chia sẻ**: Tệp cấu hình Docker Compose có thể dễ dàng chia sẻ và tái sử dụng, giúp giảm đáng kể thời gian triển khai và khắc phục sự không đồng nhất giữa các môi trường.

Để sử dụng Docker Compose, bạn cần tạo một tệp YAML có tên docker-compose.yml trong thư mục dự án của mình. Trong tệp này, bạn định nghĩa các dịch vụ cần triển khai, các mạng và các cài đặt liên quan khác. Sau đó, bạn có thể sử dụng các lệnh Docker Compose như docker-compose up, docker-compose down, và docker-compose ps để quản lý ứng dụng của mình.