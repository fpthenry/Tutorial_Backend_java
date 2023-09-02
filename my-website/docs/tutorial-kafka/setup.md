---
sidebar_position: 2
slug: /tutorial-kafka/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt kafka trên windows](#hướng-dẫn-cài-đặt-kafka-trên-windows)



## Overview




## Hướng dẫn cài đặt kafka trên windows
**Yêu cầu:**

+ Windows 10 64-bit: Pro, Enterprise hoặc Education (phiên bản 15063 trở lên)

+ Hyper-V được hỗ trợ và đã bật (kiểm tra trong "Control Panel" -> "Turn Windows features on or off" -> "Hyper-V")

+ CPU hỗ trợ SLAT

+ Bộ nhớ RAM ít nhất 4GB

**Bước 1**: Tải cài đặtkafka cho Windows:
 download: https://kafka.apache.org/downloads
 mình cài bản recomment kafka_2.13-3.5.1.tgz (asc, sha512) sau đó cài về và giải nén folder ra

**Bước 2**: Kiểm kafka đã cài đặt thành công chưa:
1. Kiểm tra 
```bash
 $kafka-topics.sh --version
```

2. Đầu tiên đổi tên folder giải nén hơi dài thành kafka
- vào folder kafka mở cmd
 ```bash
 gõ lệnh : $ bin\windows\kafka-server-start.bat config\zookeeper.properties
 khởi chạy zookeeper để quản lý kafka
 ```

 Khởi tạo kafka server -> vào folder kafka

 ```bash
 gõ lệnh: $bin\windows\kafka-server-start.bat config\server.properties
```

sau khi thành công tạo topic 

```bash
 $ kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

```
Liệt kê danh sách các topic:

```bash
$ kafka-topics.sh --list --bootstrap-server localhost:9092

```
Cuối Gửi và nhận tin nhắn:

+ Producer:
```bash
$ kafka-console-producer.sh --topic test-topic --bootstrap-server localhost:9092
```
+ Consumer:

```bash
$ kafka-console-consumer.sh --topic test-topic --bootstrap-server localhost:9092 --from-beginning
```

Done!
