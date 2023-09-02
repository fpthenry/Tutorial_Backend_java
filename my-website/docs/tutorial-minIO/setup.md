---
sidebar_position: 2
slug: /tutorial-minIO/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt minIO trên windows](#hướng-dẫn-cài-đặt-redis-trên-windows)



## Overview


## Hướng dẫn cài đặt MinIO trên windows

**Bước 1**: Tải MinIO cho Windows:
+ Trước tiên, bạn cần tải bản cài đặt MinIO cho Windows từ trang chính thức của MInIO: 
+ Link : https://min.io/download#/windows
+ Mình cài sử dụng bản minio.exe cho windows
![download](https://stackjava.com/wp-content/uploads/2020/05/install-minio-windows-1.jpg)

**Bước 2**: Vào thư mục chứa minio.exe mở cmd
+ tạo Tài khoản và mật khẩu mà minio sử dụng sẽ đọc từ 2 biến môi trường là MINIO_ACCESS_KEY và MINIO_SECRET_KEY.
```bash
    $ set MINIO_ACCESS_KEY=minio_access_key
    $ set MINIO_SECRET_KEY=minio_secret_key
```

+ tạo 1 folder lưu trữ minio tôi tạo folder: minio_data
+ tiếp theo để bật minIO ta dùng lệnh:
```bash
    $ minio server minio_data --address localhost:9000
    với minio_data là folder bạn mới tạo ra trên
```

**Bước 3**: Sau khi hoàn thành ta truy cập vào localhost:9000 sẽ hiển thị ra UI của minio
+ Lúc này màn hình hiện ra thông tin đăng nhập 
 Nhập tài khoản : minio_access_key
 mật khẩu: minio_secret_key
 tk,mk là thông tin mình nãy setup cho min IO

 Sau khi đăng nhập thành công sẽ hiển thị giao diện UI của minIO và bạn có thể thực hành thử trên giao diện tạo 1 Buckets với tên bất kỳ và upload thử file ảnh lên đó.



## Hướng dẫn cài đặt MinIO trên Ubuntu sử dụng WSL trên Windows
**Bước 1**: Cài đặt Windows Subsystem for Linux (WSL)

+ Mở PowerShell với quyền quản trị (Run as Administrator).
+ Chạy lệnh sau để kích hoạt tính năng WSL:
```bash
$ dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```
+ restart PC

**Bước 2**: Cài đặt một bản phân phối Ubuntu trên WSL
+ Mở Microsoft Store và tìm kiếm "Ubuntu".
+ Chọn bản phân phối Ubuntu mà bạn muốn cài đặt (ví dụ: Ubuntu 20.04 LTS).
+ Nhấp vào "Install" để tải và cài đặt bản phân phối Ubuntu.

**Bước 3**: Mở Ubuntu trên WSL và cài đặt minIO

1. Mở Start Menu và tìm kiếm "Ubuntu" để mở bản phân phối Ubuntu mà bạn vừa cài đặt.
2. Theo hướng dẫn để thiết lập tài khoản người dùng và mật khẩu cho Ubuntu.
3. Mở terminal Ubuntu và cài đặt minIO bằng cách nhập các lệnh sau một cách tuần tự:

+ Cài đặt minIO:
```bash
    wget https://dl.min.io/server/minio/release/linux-amd64/minio
    chmod +x minio
    sudo mv minio /usr/local/bin/
```

+ Khởi động minIO:
```bash
    mkdir ~/minio-data
    minio server ~/minio-data
```

**Bước 4**: Truy cập giao diện quản lý minIO
1. Mở trình duyệt web trong Windows.
2. Trong thanh địa chỉ, nhập: http://localhost:9000 và nhấn Enter.

## Hướng dẫn cài đặt MinIO sử dụng centOS7 trên Windows

**Bước 1**: Cài đặt Phần mềm Ảo hóa VirtualBox
+ Tải xuống và cài đặt ứng dụng máy ảo như Oracle VirtualBox: https://www.virtualbox.org/
+ Tạo một máy ảo CentOS 7 trên VirtualBox bằng cách sử dụng file ISO của CentOS 7. Đảm bảo rằng máy ảo có kết nối internet.

**Bước 2**: Tạo máy ảo CentOS 7
**Bước 3**: Cài đặt CentOS 7 trên máy ảo
**Bước4**:  Cài đặt và chạy minIO trên CentOS 7 
+ Mở terminal trong máy ảo CentOS 7.
+ Sử dụng các lệnh cài đặt và chạy minIO tương tự như trong hướng dẫn cài đặt minIO trên Ubuntu.