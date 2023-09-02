---
sidebar_position: 2
slug: /tutorial-dockers/setup
title: Hướng dẫn cài đặt
---

## In this article
* [Overview](#overview)
* [Hướng dẫn cài đặt docker trên windows](#hướng-dẫn-cài-đặt-docker-trên-windows)
* [Hướng dẫn cài đặt docker trên ubuntu](#hướng-dẫn-cài-đặt-docker-trên-ubuntu)
* [Hướng dẫn cài đặt docker trên centOS7](#hướng-dẫn-cài-đặt-docker-trên-centos7)


## Overview

Docker là một nền tảng ứng dụng được sử dụng để đóng gói, phân phối và chạy các ứng dụng trong các môi trường cô lập gọi là containers. Dưới đây là hướng dẫn cài đặt Docker trên hệ điều hành Windows, macOS và Linux.


## Hướng dẫn cài đặt docker trên windows
**Yêu cầu:**

+ Windows 10 64-bit: Pro, Enterprise hoặc Education (phiên bản 15063 trở lên)

+ Hyper-V được hỗ trợ và đã bật (kiểm tra trong "Control Panel" -> "Turn Windows features on or off" -> "Hyper-V")

+ CPU hỗ trợ SLAT

+ Bộ nhớ RAM ít nhất 4GB

**Bước 1**: Tải xuống Docker Desktop
+ Truy cập vào trang web chính thức của Docker và tải xuống Docker Desktop cho Windows: https://www.docker.com/products/docker-desktop

**Bước 2**: Cài đặt Docker Desktop
+ Chạy tệp cài đặt đã tải xuống và làm theo các hướng dẫn trong quá trình cài đặt. -> xong mở docker destop lên chạy từ desktop or tìm kiếm.

**Bước 3**: Đăng nhập vào Docker Hub (tùy chọn)
+ Nếu bạn đã có tài khoản Docker Hub, bạn có thể đăng nhập vào Docker Desktop để sử dụng các tính năng của Docker Hub, như tải xuống hình ảnh có chia sẻ hoặc đăng tải các hình ảnh của riêng bạn.
Đăng nhập vào Docker Hub bằng cách nhấp vào biểu tượng Docker Desktop trong khay hệ thống và chọn "Sign in / Create Docker ID".

**Bước 5**: Kiểm tra cài đặt
+ Sau khi Docker Desktop đã chạy, kiểm tra xem Docker đã hoạt động bằng cách mở "Command Prompt" hoặc "PowerShell" và chạy lệnh sau để kiểm tra phiên bản Docker:
```bash
docker --version
```
+ Kiểm tra xem Docker có thể chạy đúng container bằng cách chạy lệnh sau:
```bash
docker run hello-world
```

**chú ý 1 số lỗi có thể gặp phải trong quá trình cài đặt**:
+ Service: windows -> services -> Docker Desktop service -> properties -> running
+ kích hoạt Hyper-V: windows -> Apps and Features -> Programs and Features -> Turn Windows Features on or off -> Hyper-V -> restart máy
+ lỗi proxy: vào docker desktop -> setting -> resources -> proxies fix với http và https đổi thành : http://10.255.188.84:3128 còn với Bypass proxy... : đổi thành 10.*

## Hướng dẫn cài đặt docker bên trong ubuntu sử dụng máy ảo WSL  
**Bước 1**: Cài đặt WSL:
+ Mở PowerShell với quyền quản trị (Run as Administrator).
+ Chạy lệnh sau để bật tính năng WSL trên Windows:
```bash
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```
**Bước 2**: Cài đặt Ubuntu trên WSL:
+ Mở Microsoft Store từ menu Start.
+ Tìm kiếm "Ubuntu" và chọn một phiên bản Ubuntu để cài đặt (ví dụ: Ubuntu 20.04 LTS).
+ Nhấn "Install" để tải về và cài đặt hệ điều hành Ubuntu trên WSL.

**Bước 3**: Khởi động Ubuntu trên WSL:
+ Mở Ubuntu đã cài đặt từ menu Start hoặc tìm kiếm "Ubuntu".
+ Đợi quá trình khởi động hoàn tất và thực hiện các bước cấu hình như đặt mật khẩu cho tài khoản người dùng.

**Bước 4**: Cài đặt Docker trong Ubuntu trên WSL:
+ Trong cửa sổ Terminal của Ubuntu, chạy các lệnh sau để cài đặt Docker:
```bash
sudo apt update
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io

```
+ Chạy lệnh sau để kiểm tra xem Docker đã được cài đặt thành công:
```bash
sudo docker --version
```

**Bước 5**:Sử dụng Docker trong Ubuntu trên WSL:
+ Bây giờ bạn có thể sử dụng Docker trong Ubuntu trên WSL như trên bất kỳ môi trường Linux nào. Bạn có thể tạo và quản lý các container Docker thông qua Terminal của Ubuntu.

## Hướng dẫn cài đặt docker trên centOS7 sử dụng máy ảo VirtualBox
Để cài đặt Docker trên CentOS 7 sử dụng Windows, bạn cần cài đặt Docker trên máy ảo CentOS 7 chạy trên Windows (chẳng hạn, sử dụng ứng dụng Oracle VirtualBox). Dưới đây là hướng dẫn cài đặt Docker trên CentOS 7 trong môi trường máy ảo Windows:

**Bước 1**: Cài đặt Máy ảo CentOS 7 trên Windows:
+ Tải xuống và cài đặt ứng dụng máy ảo như Oracle VirtualBox: https://www.virtualbox.org/
+ Tạo một máy ảo CentOS 7 trên VirtualBox bằng cách sử dụng file ISO của CentOS 7. Đảm bảo rằng máy ảo có kết nối internet.

**Bước 2**: Cài đặt Docker trên CentOS 7:
+ Mở cửa sổ dòng lệnh (Terminal) trên máy ảo CentOS 7.
+ Cập nhật các gói hệ thống bằng lệnh:
```bash
sudo yum update
```
+ Cài đặt các gói cần thiết để hỗ trợ cài đặt Docker:
```bash
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```
+ Thêm kho lưu trữ Docker vào hệ thống:
```bash
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```
+ Cài đặt Docker:
```bash
sudo yum install docker-ce
```
+ sudo yum install docker-ce
```bash
sudo systemctl start docker
sudo systemctl enable docker
```
+ Thêm người dùng hiện tại vào nhóm docker để không cần sử dụng sudo khi chạy các lệnh Docker:
```bash
sudo usermod -aG docker $USER
```
+ Đăng xuất khỏi tài khoản người dùng hiện tại và đăng nhập lại để áp dụng thay đổi.

**Bước 3**: Kiểm tra cài đặt Docker:
+ Chạy lệnh sau để kiểm tra phiên bản Docker đã cài đặt:
```bash
docker --version
```

**Bước 4**: Sử dụng Docker:
+ Bạn đã cài đặt Docker thành công trên máy ảo CentOS 7. Bây giờ bạn có thể bắt đầu sử dụng Docker để tạo và quản lý các container.
