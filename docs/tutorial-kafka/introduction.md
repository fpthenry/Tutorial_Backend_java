---
sidebar_position: 1
slug: /tutorial-kafka/introduction
title: Giới thiệu về kafka
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [greetings]
---


## Mục Lục
* [Overview](#overview)
  1. [kafka là gì?](#kafka-là-gì)
* [Các thuật ngữ](#các-thuật-ngữ)
* [Quản lý Topic và Partition](#quản-lý-topic-và-partition)
* [Quản lý Apache ZooKeeper và kafka cluster](#quản-lý-apache-zookeeper-và-kafka-cluster)
* [Cách Xử lý dữ liệu trong Kafka](#cách-xử-lý-dữ-liệu-trong-kafka)
* [Các tiêu chuẩn về an toàn thông tin sử dụng kafka](#các-tiêu-chuẩn-về-an-toàn-thông-tin-sử-dụng-kafka)
  1. [Kafka có an toàn không?](#kafka-có-an-toàn-không)
  2. [Thông tin về các lỗ hổng bảo mật](#thông-tin-về-các-lỗ-hổng-bảo-mật)



## Overview
  ### **kafka là gì?**

  **kafka** là hệ thống message pub/sub phân tán mã nguồn mở phổ biến nhất hiện nay và được xây dựng bởi mục đích xử lý dữ liệu stream real-item.
  Bên public dữ liệu gọi là producer, bên subscribe nhận dữ liệu theo topic gọi là consumer. Kafka có khả năng truyền 1 lượng lớn message theo thời gian thực, Trong trường hợp bên chưa nhận được message vẫn được sao lưu trên 1 hàng đợi và trên ổ đĩa đảm bảo an toàn. đồng thời nó cũng được replicate trong cluster phòng tránh mất dữ liệu.
  

<!-- ## Ứng dụng của Kafka? -->


## Các thuật ngữ?

**Broker**: Máy chủ Kafka, quản lý dữ liệu và xử lý thông điệp từ các producer đến các consumer.

**Topic**: Một loại kênh hoặc danh mục để phân loại các thông điệp. Các thông điệp được gửi bởi producer đến các topic và được consumer đọc từ các topic..

**Partition**: Mỗi topic có thể được chia thành nhiều partitions, đây là đơn vị cơ bản cho việc lưu trữ và xử lý dữ liệu. Mỗi partition có thể tồn tại trên nhiều broker.

**Replication**: Các partitions có thể được sao chép (replicate) trên nhiều broker khác nhau để đảm bảo khả năng sẵn sàng và sao lưu.

**Producer**: Là thành phần gửi dữ liệu vào Kafka cluster thông qua các topic.

**Consumer**: Là thành phần đọc dữ liệu từ Kafka cluster thông qua các topic.

**ZooKeeper**: Là một dịch vụ quản lý cụm được sử dụng để theo dõi và quản lý trạng thái của các broker, partitions và các metadata của cluster.

**Kafka Streams**: Thư viện xử lý và phân tích dữ liệu thời gian thực trong Kafka.

**Offset**: Một con số duy nhất đại diện cho vị trí của một thông điệp trong một partition. Các consumer sử dụng offset để theo dõi thông điệp mà họ đã đọc.
  
## Quản lý Topic và Partition
+. Hiểu cách tạo, xoá, và quản lý các Topic và Partition.
+. Tìm hiểu về cách quản lý các thuộc tính của Topic như retention policy, replication factor, 

**1: Tạo Topic**: Khi bạn tạo một topic mới, bạn cần xác định tên của topic và số lượng partition mà topic đó sẽ có. Số lượng partition ảnh hưởng đến khả năng mở rộng và hiệu suất của topic.

Ví dụ sử dụng Kafka CLI để tạo một topic:
```bash
  bin/kafka-topics.sh --create --topic my-topic --partitions 3 --replication-factor 2 --zookeeper localhost:2181
```
**2. Quản lý Partition:**
  + Số lượng partition trong một topic không thể thay đổi sau khi topic đã được tạo tuy nhiên có thể  mở rộng bằng cách tạo các topic mới với số lượng partition khác nhau.

**3. Sao chép (Replication) Partition**
+ Kafka hỗ trợ sao chép dữ liệu (replication) giữa các partition trên các broker khác nhau để đảm bảo tính khả dụng và bảo mật. Mỗi partition có một leader và một hoặc nhiều follower. Follower đảm bảo sao chép dữ liệu từ leader để đảm bảo dữ liệu không bị mất.

**4. Thay đổi số Partition:**
Có 1 số cách sau: 
```bash
 + Tạo một topic mới với số lượng partition mới.
 + Sử dụng Kafka Streams hoặc công cụ của bên thứ ba để sao chép dữ liệu từ topic cũ sang topic mới.
 + Cập nhật ứng dụng của bạn để sử dụng topic mới thay vì topic cũ.
```

## Quản lý Apache ZooKeeper và kafka cluster
 **1. Apache ZooKeeper:** Apache ZooKeeper là một dự án mã nguồn mở được phát triển để quản lý và giám sát các dịch vụ phân tán trong một hệ thống sử dụng để quản lý metadata, theo dõi trạng thái của các broker Kafka và giúp trong quá trình chuyển đổi leader partition.

 **Các chức năng** quan trọng của ZooKeeper trong Kafka bao gồm:
  + Lưu trữ metadata về các broker, topic, partition, consumer group, ...
  + Theo dõi trạng thái hoạt động của các broker và partition.
  + Giúp quá trình quản lý, mở rộng, và mục tiêu làm việc dễ dàng hơn.**

**2. Kafka Cluster:**  
Kafka cluster là tập hợp các máy chủ Kafka (broker) hoạt động cùng nhau để lưu trữ và xử lý dữ liệu. Mỗi broker trong cluster có thể chứa nhiều topic và partition, và chúng hoạt động cùng nhau để cung cấp tính khả dụng, hiệu suất và khả năng mở rộng cho hệ thống.

## Cách Xử lý dữ liệu trong Kafka
**1. Gửi Dữ liệu từ Producer:**
  + Sử dụng producer để tạo và gửi các thông điệp (messages) đến các topic trong Kafka.
  + Producer có thể gửi dữ liệu một cách đồng bộ hoặc không đồng bộ.

**2. Lưu trữ Dữ liệu trong Topic:**
  + Dữ liệu từ producer được lưu trữ trong các topic.
  + Mỗi topic có thể có nhiều partition để tăng hiệu suất và khả năng mở rộng.

**3. Xử lý Dữ liệu bằng Consumer:**
  + Sử dụng consumer để đọc và xử lý dữ liệu từ các topic.
  + Consumer có thể đọc dữ liệu một cách đồng bộ hoặc không đồng bộ.

**4. Sao chép (Replication) và Backup:**
  + Dữ liệu trong Kafka có thể được sao chép giữa các partition và broker để đảm bảo tính khả dụng và bảo mật.
  + Thực hiện sao lưu (backup) dữ liệu Kafka và cơ sở dữ liệu ZooKeeper thường xuyên. 

**5.  Xử lý Dữ liệu Thời gian thực (Real-time Processing):**
  + Sử dụng Kafka để xử lý và phản hồi dữ liệu thời gian thực từ các nguồn như IoT, trạng thái hệ thống, và các sự kiện khác.

## Các tiêu chuẩn về an toàn thông tin sử dụng kafka?
**Xác thực và Ủy quyền:**
Sử dụng xác thực SSL/TLS cho giao tiếp mật mã hóa giữa các broker, producer, consumer và ZooKeeper.
Sử dụng cơ chế ủy quyền để quản lý quyền truy cập vào các topic, partition và hành động khác.
Sử dụng SASL (Simple Authentication and Security Layer) để xác thực và ủy quyền các ứng dụng.

**Bảo mật mạng:**
Đảm bảo rằng các cổng mạng mà Kafka sử dụng được bảo mật và chặn truy cập không ổn định.
Cân nhắc triển khai Kafka trong mạng riêng ảo (VLAN) để cách ly dữ liệu.

**Bảo mật hệ thống:**
Thực hiện việc cập nhật và bảo mật hệ điều hành, cơ sở dữ liệu và các phần mềm liên quan khác trên các máy chủ Kafka.
Hạn chế quyền truy cập đến các máy chủ Kafka và ZooKeeper, chỉ cho phép các ứng dụng và người dùng cần thiết.

**Giám sát và Ghi nhật ký (Audit):**
Đảm bảo rằng bạn có các cơ chế giám sát để theo dõi hoạt động của Kafka cluster và phát hiện các hoạt động không bình thường.
Ghi lại các hoạt động quan trọng như xác thực, ủy quyền và truy cập vào các topic.

**Bảo vệ dữ liệu:**
Sử dụng mã hóa dữ liệu khi lưu trữ hoặc truyền tải thông điệp Kafka.
Cân nhắc sử dụng mã hóa end-to-end cho các thông điệp giữa producer và consumer.

**Bảo vệ dịch vụ ZooKeeper:**
Bảo vệ cụm ZooKeeper bằng cách sử dụng xác thực và ủy quyền.
Đảm bảo rằng các máy chủ ZooKeeper không bị lạc hướng và được bảo mật tốt.

<!-- 
### kafka có an toàn không?
+ Apache Kafka cung cấp nhiều tính năng và cơ chế để hỗ trợ bảo mật và an toàn thông tin. Tuy nhiên, việc đảm bảo an toàn cho hệ thống Kafka phụ thuộc vào cách triển khai và cấu hình của bạn. -->



Tác giả: [Nguyễn Anh Tuấn](https://github.com/FPTooHenry)

![Hình ảnh tác giả](https://github.com/FPTooHenry.png)

