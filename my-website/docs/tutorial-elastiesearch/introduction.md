---
sidebar_position: 1
slug: /tutorial-elasticsearch/introduction
title: Giới thiệu về elasticsearch
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [greetings]
---

## In this article
* [Overview](#overview)
  1. [Elasticsearch là gì ?](#elasticsearch-là-gì)
* [Các thuật ngữ](#các-thuật-ngữ)
* [Các khái niệm quan trọng trong Elasticsearch](#các-khái-niệm-quan-trọng-trong-elasticsearch)
* [Hiểu rõ hơn về Primary Shard và Replica Shard](#hiểu-rõ-hơn-về-primary-shard-và-replica-shard)
* [Ưu và nhược điểm của Elasticsearch(ES)](#ưu-và-nhược-điểm-của-elasticsearches)
* [Tìm hiểu và cài đặt (ELK) Elasticsearch Logstash Kibana](#tìm-hiểu-và-cài-đặt-elk-elasticsearch-logstash-kibana)

## Overview

+ **Elasticsearch** là một search engine.
+ **Elasticsearch** được kế thừa từ Lucene Apache
+ **Elasticsearch** thực chất hoặt động như 1 web server, có khả năng tìm kiếm nhanh chóng (near realtime) thông qua giao thức RESTful
+ **Elasticsearch** có khả năng phân tích và thống kê dữ liệu
+ **Elasticsearch** chạy trên server riêng và đồng thời giao tiếp thông qua RESTful do vậy nên nó không phụ thuộc vào client viết bằng gì hay hệ thống hiện tại của bạn viết bằng gì. Nên việc tích hợp nó vào hệ thống bạn là dễ dàng, bạn chỉ cần gửi request http lên là nó trả về kết quả.
+ **Elasticsearch** là 1 hệ thống phân tán và có khả năng mở rộng tuyệt vời (horizontal scalability). Lắp thêm node cho nó là nó tự động auto mở rộng cho bạn.
+ **Elasticsearch** là 1 open source được phát triển bằng Java
## Elasticsearch là gì ?
**Elasticsearch** là một công cụ tìm kiếm dựa trên nền tảng Apache Lucene. Nó cung cấp một bộ máy tìm kiếm dạng phân tán, có đầy đủ công cụ với một giao diện web HTTP có hỗ trợ dữ liệu JSON. Elasticsearch được phát triển bằng Java và được phát hành dạng nguồn mở theo giấy phép Apache. (Apache Lucene hiểu là core của Elasticsearch)

+ Cách hoạt động của elasticsearch:
![cách hoạt động](https://topdev.vn/blog/wp-content/uploads/2020/05/elasticsearch-la-gi.png)
 

## Các khái niệm quan trọng trong Elasticsearch

 + Các khái niệm quan trọng trong Elasticsearch: cluster, node, index, document, shards.
 
 + đầu tiên chúng ta có thể hiểu DB như là 1 elasticsearch nhưng replace 1 chút khác nhau

như ảnh sau:
![images](https://wirehaired-windscreen-260.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F77470828-c645-4aa0-a594-6f8553460aba%2FUntitled.png?table=block&id=aa3272af-fe08-4987-b0f9-73dc2fbe44df&spaceId=0e04a265-f5da-4612-9871-75e47e1ec043&width=2000&userId=&cache=v2)

+ ta có thể thấy ánh xạ tương tự như với db trog db table thì tương đương với index
 Row thì tương đương với document
 column với file và schema với mapping

 ![tổng quan](./img/6.Annotation%202023-08-18%20143839.png)

## Các thuật ngữ
**Document** là một JSON object với một số dữ liệu. Đây là basic information unit trong ES. Hiểu 1 cách cơ bản thì đây là đơn vị nhỏ nhất để lưu trữ dữ liệu trong Elasticsearch. 

**Index** : (index không giống trong mySQL) Trong Elasticsearch , sử dụng một cấu trúc được gọi là inverted index . Nó được thiết kế để cho phép tìm kiếm full-text search. Cách thức của nó khá đơn giản, các văn bản được phân tách ra thành từng từ có nghĩa sau đó sẽ đk map xem thuộc văn bản nào. Khi search tùy thuộc vào loại search sẽ đưa ra kết quả cụ thể.

**Shard**: 
  + Shard là đối tượng của Lucene , là tập con các documents của 1 Index. Một Index có thể được chia thành nhiều shard.
  + Mỗi node bao gồm nhiều Shard . Chính vì thế Shard mà là đối tượng nhỏ nhất, hoạt động ở mức thấp nhất, đóng vai trò lưu trữ dữ liệu.
  + Chúng ta gần như không bao giờ làm việc trực tiếp với các Shard vì Elasticsearch đã support toàn bộ việc giao tiếp cũng như tự động thay đổi các Shard khi cần thiết.
  + Có 2 loại Shard là : primary shard và replica shard.

**Node** : 
  + Là trung tâm hoạt động của Elasticsearch. Là nơi lưu trữ dữ liễu ,tham gia thực hiện đánh index cúa cluster cũng như thực hiện các thao tác tìm kiếm
  + Mỗi node được định danh bằng 1 unique name

**Cluster**: 
  + Tập hợp các nodes hoạt động cùng với nhau, chia sẽ cùng thuộc tính cluster.name.
  + Mỗi cluster có một node chính (master), được lựa chọn một cách tự động và có thể thay thế nếu sự cố xảy ra. Một cluster có thể gồm 1 hoặc nhiều nodes. Các nodes có thể hoạt động trên cùng 1 server



## Hiểu rõ hơn về Primary Shard và Replica Shard
 1. Primary Shard
    + Primary Shard là sẽ lưu trữ dữ liệu và đánh index . Sau khi đánh xong dữ liệu sẽ được vận chuyển tới các Replica Shard.
    + Mặc định của Elasticsearch là mỗi index sẽ có 5 Primary shard và với mỗi Primary shard thì sẽ đi kèm với 1 Replica Shard.
 2. Replica Shard
    + Replica Shard đúng như cái tên của nó, nó là nơi lưu trữ dữ liệu nhân bản của Primary Shard
    + Replica Shard có vai trò đảm bảo tính toàn vẹn của dữ liệu khi Primary Shard xảy ra vấn đề.
    + Ngoài ra Replica Shard có thể giúp tăng cường tốc độ tìm kiếm vì chúng ta có thể setup lượng Replica Shard nhiều hơn mặc định của ES

## Ưu và nhược điểm của Elasticsearch(ES)

1. **Ưu Điểm**
+ Tìm kiếm dữ liệu rất nhanh chóng, mạnh mẽ dựa trên Apache Lucene ( near-realtime searching)
+ Có khả năng phân tích dữ liệu (Analysis data)
+ Khả năng mở rộng theo chiều ngang tuyệt “vòi”
+ Hỗ trợ tìm kiếm mờ (fuzzy), tức là từ khóa tìm kiếm có thể bị sai lỗi chính tả hay không đúng cú pháp thì vẫn có khả năng elasticsearch trả về kết quả tốt.
+ Hỗ trợ Structured Query DSL (Domain-Specific Language ), cung cấp việc đặc tả những câu truy vấn phức tạp một cách cụ thể và rõ ràng bằng JSON
+ Hỗ trợ nhiều Elasticsearc client như Java, PhP, Javascript, Ruby, .NET, Python

2. **Nhược Điểm**
+ lasticsearch được thiết kế cho mục đích search, do vậy với những nhiệm vụ khác ngoài search như CRUD thì elastic kém thế hơn so với những database khác như Mongodb, Mysql …. Do vậy người ta ít khi dùng elasticsearch làm database chính, mà thường kết hợp nó với 1 database khác.
+ Trong elasticsearch không có khái niệm database transaction , tức là nó sẽ không đảm bảo được toàn vẹn dữ liệu trong các hoạt động Insert, Update, Delete.Tức khi chúng ta thực hiện thay đổi nhiều bản ghi nếu xảy ra lỗi thì sẽ làm cho logic của mình bị sai hay dẫn tới mất mát dữ liệu. Đây cũng là 1 phần khiến elasticsearch không nên là database chính.
+ Không thích hợp với những hệ thống thường xuyên cập nhật dữ liệu. Sẽ rất tốn kém cho việc đánh index dữ liệu.





 



## Tìm hiểu và cài đặt (ELK) Elasticsearch Logstash Kibana
+ **Elastic Stack (ELK Stack)** - là một nhóm phần mềm nguồn mở, dựa trên Elastic nó cho phép tìm kiếm, phân tích, thể hiện trực quan các log thu thập được từ các nguồn, các log này là bất kỳ định dạng nào
+  ELK là trung tâm phân tích log, trợ giúp xác định các vấn đề phát sinh trên các server, các ứng dụng mà bạn không cần truy cập trực tiếp vào log của từng server, từng ứng dụng.
+ Thường để xây dựng nên trung tâm này dùng đến ELK với các thành phần chính gồm:
![ELK](https://raw.githubusercontent.com/xuanthulabnet/linux-centos/master/docs/beats-platform.png)


 + **Elasticsearch** - máy chủ lưu trữ và tìm kiếm dữ liệu
 + **Logstash** - thành phần xử lý dữ liệu, sau đó nó gửi dữ liệu nhận được cho Elasticsearch để lưu trữ
 + **Kibana** - ứng dụng nền web để tìm kiếm và xem trực quan các logs
 + **Beats** - gửi dữ liệu thu thập từ log của máy đến Logstash


