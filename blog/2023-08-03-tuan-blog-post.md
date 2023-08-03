---
slug: tuan-blog-post
title: Tuan Blog Post
authors:
  - name: Nguyễn Anh Tuấn
    title: Maintainer of Docusaurus
    url: https://github.com/FPTooHenry
    image_url: https://github.com/FPTooHenry.png

tags: [hola, docusaurus]
---

## XIN CHÀO!

## Đây là blog của Tuấn tìm hiểu về thread

Đầu tiên để hiểu về thread chúng ta nên biết qua về **process**

- 1 process chạy độc lập với các process khác và không thể truy cập trực tiếp shared data với nhau được, mỗi process sẽ có dữ liệu riêng
- 1 thread hiểu là 1 luồng thực hiện 1 số tác vụ trong 1 chương trình tức 1 chương trình thì có nhiều thread khác nhau
- Các thread tồn tại trong process, mỗi process có ít nhất 1 thread bên trong, ứng dụng có ít nhất 1 thread để chạy và có thể share data giữa các process
- Process thì không thể truy cập vào share data nhưng thread thì có thể share data được với nhau trong cùng 1 process

## Tìm hiểu về các method trong class Thread

```md title="method"
---
start(): khởi tạo thread đó
run(): chạy thread
setName(): Đặt tên cho các tiến trình
join(): chạy chờ thread chết xong moi toi thread khác
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

```jsx title="Ví dụ 4: Sử dụng join() cho thread"
Đối với phương thức Join() chờ 1 thread chết hay nói cách khác là làm cho thread
đang chạy làm xong nhiệm vụ ngừng hoạt động thì tới tiếp tục các thread khác

public class JoinThread {
    public static void main(String[] args) {
        testJoinMethod t1 = new testJoinMethod();
        t1.setName(" t1 ");
        testJoinMethod t2 = new testJoinMethod();
        t2.setName(" t2 ");
        testJoinMethod t3 = new testJoinMethod();
        t3.setName(" t3 ");
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t2.start();
        t3.start();
    }

}

class testJoinMethod extends Thread {
    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this.getName() + i);
        }
    }
}


**Kết quả**
 t1 1
 t1 2
 t1 3
 t1 4
 t2 1
 t3 1
 t2 2
 t3 2
 t3 3
 t2 3
 t3 4
 t2 4

Ta thấy thì kết quả sau khi sử dụng method join() của t1 thì thread sẽ chạy xong hoàn toàn thread t1 rồi mới tiếp tục các thread t2,3
```

## Tìm hiểu về Concurency

- Là khả năng chạy chương trình song song or một vài phần của chương trình song song voi nhau.
- trong concurrency có 1 vài vấn đề như : **deadlock** và **uppredictable change**
- Với **Deadlock** thì 2 hoặc nhiều hơn 2 thread bị block mãi mãi đợi nhau như vậy

ví dụ 1 : Thread A sử dụng tài nguyên của thread B và thread B sử dụng tài nguyên của thread A dẫn đến -> 2 thằng chờ nhau mãi vậy không kết thúc được dẫn tới gặp deadlock chỉ có 1 cách terminate nó

ví dụ 2: Thread A đang sử dụng tài nguyên. thread B đang sửa tài nguyên của thread A dẫn tới -> Thread A không biết thay đổi như thế nào? sử dụng giá trị nào khi bị thay đôit => gọi là **unpredictable change** =)) lúc này cần sử dụng synchronize đồng bộ để sử dụng cho việc đó.

## Runable

- Runnable là 1 interface sử dụng để tạo new thread trong java application.
- Các sub-class của runnble buộc phải implement phương thức run của nó

```jsx title="Ví dụ runnable"
------------
public class StudentRunnable implements Runnable {
    private List<ModelMBean> student;

    public StudentRunnable(List<ModelMBean> student) {
        this.student = student;
    }

    @Override
    public void run() {
        if (student != null){
            System.out.println(Thread.currentThread().getName());
            System.out.println(student.size());
        }
    }
}

-------------
public class RunnalbleSample  {
    public static void main(String[] args) {
        StudentRunnable st = new StudentRunnable(new ArrayList<>());
        Thread thread = new Thread(st,"thread runnable");
        thread.start();
    }
}
------------
**Kết quả**
thread runnable
0
------------

```

## Thread và Runnable

- Extend thread không cho phép extend class nào khác cả
- **Thread** có rất nhiều operator các tác vụ khác nhau để quản lý các thread ví dụ start(), interupt(), setpriority()..
- Với interface **runnable** chỉ có 1 hàm run duy nhất tuy nhiên runnable không phải là 1 thread, để sử dụng tạo ra thread
  -> thì sử dụng instance của thread mà tạo class kế thừa lại runnable đấy

## Start() và run()

- **start()** tạo ra 1 thread mới hoặc là thread đó bản thân đang trong thread khác mà thread đó start() thì cũng sẽ tạo ra 1 thread mới.
- **run()** không tạo thread mới mà join current thread ví dụ đang chạy trên main thì chạy tiếp trên hàm main mà không cần phải khởi tạo mới.
- **đặc biệt** gọi **start** chỉ 1 lần và **run** được nhiều lần

## Synchronous và Asynchronous

1. Với **Synchronous** sẽ block thread hiện tại đợi cho đến khi kết thúc túc là thực hiện tuần tự các task với nhau A xong mới tới B.
2. Vói **Asynchronous** chỉ làm 1 lệnh gọi và để cho nó tự chạy và nó sẽ làm việc khác túc là bất đồng bộ các task có thể thực hiện chạy song song, đồng thời với nhau.

vd: khi gọi điện thoại **Synchronous** cần đợi ngta nghe và trả lời nói chuyện kết thúc cuộc trò chuyện đó mới tiếp tục cuộc gọi khác được
còn với **Asynchronous** gửi thư điện tử hay gui mail tay ấn nút send thì nó có gửi hay báo lỗi không xong nó tự chạy tức là không quan tâm đến kết quả trả về là khi nào mà nó sẽ thực hiện các thread tiếp theo.

## Cơ chế Synchronization

- là cơ chế kiểm xoát truy cập nhiều multiple thread vào shared resource(data, object....)
- ngăn ngừa tương tác với các thread không mong muốn và tránh vấn đề liên quan đến consistent(nhất quán)
- **Synchronization** types có 3 loại:
    - Synchronized method 
    - Synchronized object 
    - static Synchronized 
 + Method: wait, notify, notifyall

**a. Synchronize method và Synchronize object**
```jsx title="Ví dụ Synchronize method và Synchronize object"
ví dụ về sử dụng như rút thẻ ngân hàng account thì trong thẻ còn số dư bao nhiêu, customer rút bao nhiêu tiền.
--------class account--------
public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

     void withDraw(String name, int money) { // sd synchronize với method ta chỉ cần thêm synchronized vào trước method của mình
        int count = 0;
        while (count++ < 3) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " withDrawing ");
        }
        if (balance >= money) {
            balance -= money;
            System.out.println(name + " success " + balance);
        } else {
            System.out.println(name + " not enough money " + balance);
        }
    }
}
------------class customer --------------
public class Customer implements Runnable {
    private String name;
    private Account account;

    public Customer(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        synchronized (account) { // sử dụng synchronize with object
            account.withDraw(name, 50);
        }
    }
}
------------Class BankingApplication----------
public class BankingApplication {
    public static void main(String[] args) {
        Account account = new Account(90);
        Customer customer1 = new Customer("customer 1", account);
        Customer customer2 = new Customer("customer 2", account);
        Thread t1 = new Thread(customer1);
        Thread t2 = new Thread(customer2);
        t1.start();
        t2.start();
    }
}
---------------------------------------------
**Kết quả** :
customer 1 withDrawing
customer 1 withDrawing
customer 1 withDrawing
customer 1 success 40
customer 2 withDrawing
customer 2 withDrawing
customer 2 withDrawing
customer 2 not enough money 40

sau khi sử dụng synchronize lúc này đã thấy thread chạy rõ ràng customer 1 chạy xong rồi tới customer 2
```
**b. Static Synchronize method**

```jsx title="Ví dụ static Synchronize method"
ví dụ về sử dụng như rút thẻ ngân hàng account thì trong thẻ còn số dư bao nhiêu, customer rút bao nhiêu tiền.
- các lớp trên vẫn như cũ

public synchronized static void print(String name){ // sd static method
        int count = 0;
        while (count++ < 5) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " printing ");
        }
    }

    @Override
    public void run() {
//        synchronized (account) {
//            account.withDraw(name, 50);
//        }
        print(name);
    }

---------kết quả-----------------
customer 1 printing 
customer 1 printing 
customer 1 printing 
customer 1 printing 
customer 1 printing 
customer 2 printing 
customer 2 printing 
customer 2 printing 
customer 2 printing 
customer 2 printing 
    
```

## Method

**1. wait method:**

- là 1 khối bloking(not busy) wait
- liên quan đến object
- sử dụng trong vòng lặp để check lại điều kiện
- wait(long millis)

**2. notify và notifyAll method**

- Stop a process from waiting– wakes it up
- liên quan đến object
- Wakes up a blocked thread (notify) or all blocked
  threads (notifyAll)
- để notify, nếu nhiều hơn 1 thread bị đánh thức thì sẽ được pick

```jsx title="Ví dụ sử dụng method trong synchronized method"
    while (condition not true) {
        try {
            wait(); // this.wait();
        } catch {
            System.out.println(“Interrupted!”);
        }
    }
    ví dụ :
synchronized (object) {
// object.wait()
// object.notify()
// object.notifyAll()
}
```

Thank you for watch ^^
