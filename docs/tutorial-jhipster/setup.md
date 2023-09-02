---
sidebar_position: 2
slug: /tutorial-jhipster/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt Jhipster](#hướng-dẫn-cài-đặt-jhipster)



## Overview




## Hướng dẫn cài đặt Jhipster 
**Yêu cầu:**
+ Cài đặt Java 11 recommend có thể tải và cài ở đây : https://adoptium.net/temurin/releases/?version=11
+ Cài đặt nodeJs qua link: https://nodejs.org/en/download  mình cài bản moi nhất
+ Cài đặt JHipster:
  - sau khi cài đặt xong nodejs thành công thì mình sẽ cài đặt jhipster nhưng lưu ý là xem proxy nếu khi cày đặt lỗi với dòng lệnh sau:
  ```bash
        $ npm install -g generator-jhipster
  ```
  - nếu khi cài mà lỗi proxy thì có thể setup thêm lại như sau:
  ```bash
       + npm config set proxy http://host:port
        ví dụ mình setup: npm config set proxy http://10.61.11.42:3128
       + npm config set https-proxy http://10.61.11.42:3128 
  ```
  - cài đặt thành công xong mình mở cmd kiểm tra version: jhipster --version có là done!

Minh họa như hình sau:

![set-up](./img/41.jhipster%20env.png)

Tiếp theo tạo project và sử dụng check bài tập thực hành.
