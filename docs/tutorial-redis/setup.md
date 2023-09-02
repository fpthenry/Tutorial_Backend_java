---
sidebar_position: 2
slug: /tutorial-redis/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt Redis trên windows](#hướng-dẫn-cài-đặt-redis-trên-windows)



## Overview




## Hướng dẫn cài đặt Redis trên windows
**Yêu cầu:**

+ Windows 10 64-bit: Pro, Enterprise hoặc Education (phiên bản 15063 trở lên)

+ Hyper-V được hỗ trợ và đã bật (kiểm tra trong "Control Panel" -> "Turn Windows features on or off" -> "Hyper-V")

+ CPU hỗ trợ SLAT

+ Bộ nhớ RAM ít nhất 4GB

**Bước 1**: Tải Redis cho Windows:
+ Trước tiên, bạn cần tải bản cài đặt Redis cho Windows từ trang chính thức của Redis: 
+ Link : https://github.com/MicrosoftArchive/redis/releases
+ Mình cài sử dụng bản cũ hỗ trợ là : Redis-x64-3.0.504.msi năm 2016

**Bước 2**: Cài đặt Redis:
Di chuyển đến thư mục bạn vừa tải run with admin rồi cài đặt các bước rồi tới finish

**Bước 3**: Chạy lệnh cmd $redis-cli để connect tới redis server và thực hiện thêm, lấy dữ liệu
```bash
    Tiếp theo
$set name kai: lưu chuỗi text tuantu2k vào biến name
    Tiếp theo 
$get name: lấy giá trị trong biến name

Okay, Done! $Exit để thoát
```

