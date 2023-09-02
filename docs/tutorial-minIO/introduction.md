---
sidebar_position: 1
slug: /tutorial-minIO/introduction
title: Giới thiệu về minIO
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [greetings]
---

## In this article
* [Overview](#overview)
  1. [MinIO là gì?](#minio-là-gì)
* [Các thuật ngữ](#các-thuật-ngữ)
* [Kiến thức Cơ bản về Lưu Trữ Đối Tượng (Object Storage):](#kiến-thức-cơ-bản-về-lưu-trữ-đối-tượng-object-storage)
* [Bảo Mật và Quyền Truy Cập](#bảo-mật-và-quyền-truy-cập)
* [Tích Hợp với Các Ứng Dụng và Dịch Vụ:](#tích-hợp-với-các-ứng-dụng-và-dịch-vụ)


## Overview
### **MinIO là gì?**

  **MinIO** là một server lưu trữ đối tượng dạng phân tán với hiệu năng cao.
  + Trình bày dễ hiểu thì MinIO là một file server giúp bạn dễ dàng upload file, download file như amazon, google drive, mediafire…
  + MinIO cung cấp các api làm việc giống như Amazon S3, do đó bạn có thể upload, download file, lấy link… qua api một cách đơn giản mà không phải tự cài đặt.


## Các thuật ngữ?

**MinIO Server**: Là phần chính của MinIO, là dịch vụ lưu trữ đối tượng đáp ứng giao thức S3, hỗ trợ việc lưu trữ và truy xuất các đối tượng (objects) thông qua các giao thức như HTTP, HTTPS.

**Bucket**: Là một không gian lưu trữ ảo trong MinIO. Bạn có thể coi nó như một thư mục lưu trữ đối tượng, nơi bạn có thể lưu trữ và tổ chức các đối tượng. Tên của bucket là duy nhất trong một máy chủ MinIO.

**Object**: Là dữ liệu thực sự bạn lưu trữ trong MinIO, có thể là bất kỳ loại dữ liệu nào (văn bản, hình ảnh, video, v.v.). Mỗi object trong MinIO sẽ có một tên duy nhất trong một bucket.

**Access Key và Secret Key**: Là cặp thông tin xác thực cung cấp bởi MinIO để cho phép các ứng dụng và dịch vụ truy cập vào dữ liệu trên MinIO Server thông qua API. Chúng cần được bảo mật cẩn thận.
  
### Kiến thức Cơ bản về Lưu Trữ Đối Tượng (Object Storage):
1. **Lưu trữ đối tượng (Object Storage)**:
+ Lưu trữ đối tượng là một phương pháp lưu trữ dữ liệu trong định dạng không cấu trúc gọi là "đối tượng".
+ Đối tượng là một đơn vị lưu trữ dữ liệu độc lập bao gồm dữ liệu thực tế và thông tin về siêu dữ liệu (metadata) mô tả đối tượng đó.

2. **Dữ liệu và Siêu Dữ Liệu (Metadata):**
+ Dữ liệu thực sự của đối tượng có thể là bất kỳ loại dữ liệu nào, chẳng hạn như hình ảnh, video, văn bản, file âm thanh, dữ liệu JSON, ...
+ Siêu dữ liệu là thông tin mô tả về đối tượng, ví dụ như tên tệp, loại tệp, kích thước, ngày tạo, người tạo, v.v.

3. **Giao Thức API Lưu Trữ Đối Tượng:**
+ Giao thức API lưu trữ đối tượng là giao thức mà các ứng dụng và dịch vụ sử dụng để tương tác với hệ thống lưu trữ đối tượng.
+ Amazon S3 API là một trong những giao thức phổ biến, và MinIO hỗ trợ giao thức này, cho phép bạn sử dụng thư viện và công cụ đã tồn tại để làm việc với MinIO.

### Bảo Mật và Quyền Truy Cập
+ MinIO hỗ trợ các tính năng bảo mật như mã hóa dữ liệu và quyền truy cập dựa trên chính sách.
+ Bạn có thể quản lý quyền truy cập đối với từng đối tượng hoặc thư mục, đảm bảo rằng chỉ những người dùng có quyền mới có thể truy cập dữ liệu.

### Tích Hợp với Các Ứng Dụng và Dịch Vụ:
+ MinIO có thể tích hợp dễ dàng với các ứng dụng và dịch vụ khác thông qua các giao thức như REST API và Amazon S3 API.
+ Điều này cho phép bạn sử dụng MinIO như một phần của kiến trúc ứng dụng phức tạp.













  Tác giả: [Nguyễn Anh Tuấn](https://github.com/FPTooHenry)

![Hình ảnh tác giả](https://github.com/FPTooHenry.png)