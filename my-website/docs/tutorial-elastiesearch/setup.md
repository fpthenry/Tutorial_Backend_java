---
sidebar_position: 2
slug: /tutorial-elasticsearch/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt Redis trên windows](#hướng-dẫn-cài-đặt-redis-trên-windows)



## Overview


## Hướng dẫn cài đặt Elasticserch, kibana và Logstash trên windows
**Cài đặt Elasticsearch**:

Bước 1: Download elasticsearch trên windows : https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.9.1-windows-x86_64.zip
Bước 2: giải nén truy cập vào thư mục bin mở cmd 
Bước 3: trước khi run vào config của elasticsearch.yml thêm Enable automatic creation of system indices
```bash
action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml* 
```
+ Bước cuối là Elasticsearch cmd
```bash
 vào bin của elasticsearch run cmd : $ elasticsearch.bat
```
**Cài đặt Kibana**:

Bước 1: download kibana trên windows: https://artifacts.elastic.co/downloads/kibana/kibana-8.9.1-windows-x86_64.zip
Bước 2: giải nén truy cập vào thư mục bin mở cmd 
Bước 3: vào bin mở cmd của kibana chạy : $ kibana.bat
Nhưng khi kiểm tra giao diện qua http://localhost:5601 thì lúc này sẽ yêu cầu generate an enrollment token for Kibana

+ lúc này ta phải tạo elasticsearch-create-enrollment-token
```bash
 vào bin elasticsearch mở cmd: $ elasticsearch-create-enrollment-token -s kibana --url "https://172.0.0.3:9200"

 sau khi chạy xong sẽ cho ta 1 token vào trên giao diện

 tiếp theo vào lại bin của kibana mở cmd và chạy lại kibana: $ kibana.bat 

 lúc này sau khi chạy xong sẽ cho ta 1 mã verification code is:  681 721 (như trên máy tôi là mã vậy)

 tiếp tục apply mã code trên Ui là thành công
```

**Cài đặt Logstash**:
Bước 1: download Logstash trên windows: https://www.elastic.co/downloads/logstash
Bước 2: tạo file config trog thư mục giải nén `logstash.conf` và mở notepad++ thêm 
```bash
input {
  stdin { }
}

output {
  stdout { codec => rubydebug }
}

 Logstash sẽ đọc dữ liệu từ đầu vào chuẩn (stdin) và đẩy dữ liệu đã xử lý ra đầu ra chuẩn (stdout).
```
Bước 3: vào bin mở cmd của logstash chạy : $ logstash -f config\logstash.conf -> done!

