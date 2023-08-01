---
slug: Tuan Blog Post
title: Tuan Blog Post!
authors:
  - name: Nguyễn Anh Tuấn 
    title: Co-creator of Docusaurus 1
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png
  
tags: [greetings]
---

## XIN CHÀO!

## Đây là blog của Tuấn tìm hiểu về thread 



Đầu tiên để hiểu về thread chúng ta nên biết qua về **process**

+ 1 process chạy độc lập với các process khác và không thể truy cập trực tiếp shared data với nhau được, mỗi process sẽ có dữ liệu riêng
+ 1 thread hiểu là 1 luồng thực hiện 1 số tác vụ trong 1 chương trình tức 1 chương trình thì có nhiều thread khác nhau
+ Các thread tồn tại trong process, mỗi process có ít nhất 1 thread bên trong, ứng dụng có ít nhất 1 thread để chạy và có thể share data giữa các process
+ Process thì không thể truy cập vào share data nhưng thread thì có thể share data được với nhau trong cùng 1 process

## Tiếp theo tìm hiểu về các method trong class Thread


```md title="method"
---
start(): khởi tạo thread đó
run(): chạy thread
setName(): Đặt tên cho các tiến trình
join(): join 2 thread lại với nhau
setPrioryti(): Đặt độ ưu tiên cho thread
interupt(): kết thúc giữa chừng
isAlive: kiểm tra xem hàm còn active không, thread còn sống không
---

Tiếp theo là ví dụ và khởi chạy 1 thread

```

## Ví dụ

```jsx title="1.ThreadSample.java"
public class ThreadSample {
    public static void main(String[] args) {
        CustomerThread thread1 = new CustomerThread();
        thread1.setName("A"); // set name cho thread
        CustomerThread thread2 = new CustomerThread();
        thread2.setName("B");
        thread1.start(); //chạy thread
        thread2.start();
        System.out.println(thread1.isAlive()); // kiểm tra xem thread còn sống không
    }
}

class CustomerThread extends Thread {
    @Override
    public void run() {
        int count = 0;
        while(count++ < 5){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.format("customer thread %s is running %d\n",Thread.currentThread().getName(), count);
        }
    }
}
```
```jsx title="kết quả"
true
customer thread B is running 1
customer thread A is running 1
customer thread B is running 2
customer thread A is running 2
customer thread B is running 3
customer thread A is running 3
customer thread B is running 4
customer thread A is running 4
customer thread B is running 5
customer thread A is running 5

 Như kết quả thấy thứ tự chạy các thread độc lập không cố định B A thay đổi , thread cố định thứ tự luôn đảm bảo 1 2 3 4 5
 và kết quả kiểm tra thấy true tức là thread1 đang live.
```

```jsx title="2. Ví dụ về interrupt"
public class Interrupt {
    public static void main(String[] args) {
        BankingThread bt = new BankingThread();
        bt.setName("A");
        bt.start();
    }
}
class BankingThread extends Thread{
    @Override
    public void run() {
        int count = 0;
        while(count++ < 5){
            try {
                sleep(2000);
                if (count == 3){
                    this.interrupt(); // thread ngắt tạm dùng để làm việc khác
                }
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                System.out.println(e.toString());
            }
            System.out.format("banking thread %s is running %d\n",Thread.currentThread().getName(), count);
        }
    }
}

```
```jsx title="kết quả"
banking thread A is running 1
banking thread A is running 2
banking thread A is running 3
java.lang.InterruptedException: sleep interrupted
banking thread A is running 4
banking thread A is running 5

Như kết quả thấy count = 3 thì lúc này thread sẽ ngắt tạm dừng thread hiện tại đó để chạy luôn thread với count = 4
```

```jsx title="Ví dụ 3: priority đặt độ ưu tiên cho thread"
public class PriorityThread {
    public static void main(String[] args) {
        StudentThread st1 = new StudentThread();
        st1.setName("A");
        st1.setPriority(Thread.MAX_PRIORITY);
        StudentThread st2 = new StudentThread();
        st2.setName("B");
        st2.setPriority(Thread.MIN_PRIORITY);
        st1.start();
        st2.start();
    }
}
class StudentThread extends Thread {
    public void run() {
        System.out.format("Thread %s is running %d \n", this.getName(), this.getPriority());
    }
}

**Kết quả**
lúc thì ra kết quả:
Thread B is running 1 
Thread A is running 10 

lúc khác thì ra kết quả:
Thread A is running 10 
Thread B is running 1 

Ta thấy thì trong một số trường hop nhất định sẽ thực hiện các chỉ số max_priority = 10, min_priority = 1, norm_priority = 5 cao sẽ ưu tiên hơn
nhưng không đảm bảo hoàn toàn thứ tự thread của chúng chỉ trong trường hop nhất định.
```

## Tiếp theo tìm hiểu về Concurency

+ Là khả năng chạy chương trình song song or một vài phần của chương trình song song voi nhau.
+ trong concurrency có 1 vài vấn đề như : **deadlock** và **uppredictable change**
+ Với **Deadlock** thì 2 hoặc nhiều hơn 2 thread bị block mãi mãi đợi nhau như vậy

ví dụ 1 : Thread A sử dụng tài nguyên của thread B và thread B sử dụng tài nguyên của thread A dẫn đến -> 2 thằng chờ nhau mãi vậy không kết thúc được dẫn tới gặp deadlock chỉ có 1 cách terminate nó

ví dụ 2: Thread A đang sử dụng tài nguyên. thread B đang sửa tài nguyên của thread A dẫn tới -> Thread A không biết thay đổi như thế nào? sử dụng giá trị nào khi bị thay đôit => gọi là **unpredictable change** =)) lúc này cần sử dụng synchonize đồng bộ để sử dụng cho việc đó.  

A new blog post is now available at [http://localhost:3000/blog/greetings](http://localhost:3000/blog/greetings).





