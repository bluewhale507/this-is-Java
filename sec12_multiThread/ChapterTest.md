## 확인문제
### 1. 스레드에 대한 설명 중 틀린것은 무엇입니까?
① 자바 애플리케이션은 메인(main) 스레드가 main() 메소드를 실행시킨다.    
② 작업 스레드 클래스는 Thread 클래스를 상속해서 만들 수 있다.  
③ Runnable 객체는 스레드가 실행해야 할 코드를 가지고 있는 객체라고 볼 수 있다.   
④ 스레드 실행을 시작하려면 run() 메소드를 호출해야 한다.

답 : ④ start() 메소드를 호출해야 한다.

### 2. 동영상과 음악을 재생하기 위해 두 가지 스레드를 실행하려고 합니다. 비어 있는 부분에 적당한 코드를 넣어 보세요.  

```java
/* ThreadExample.java */
public class ThreadExample {
    public static void main(String[] args) {
        Thread thread1 = new MovieThread();
        thread1.start();
        
        Thread thread2 = new Thread([빈칸]);
        thread2.start();
    }
}

/* MovieThread.java */
public class MovieThread [빈칸] {
    @Override
    public void run() {
        for(int i=0; i<3; i++) {
            System.out.println("동영상을 재생합니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }
}

/* MusicRunnable.java */
public calss MusicRunnable [빈칸] {
    @Override
    public void run() {
        for(int i=0; i<3; i++) {
            System.out.println("음악을 재생합니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }
}
```

정답 : 차례로, new MusicRunnable(), extends Thread, implements Runnable

### 3. 스레드의 우선순위에 대한 설명 중 틀린 것은 무엇입니까?
① 우선순위가 높은 스레드가 실행 기회를 더 많이 가질 수 있다.    
② 우선순위는 1부터 10까지 줄 수 있는데, 디폴트는 5이다.  
③ Thread 클래스는 NORM_PRIORITY, MIN_PRIORITY, MAX_PRIORITY 상수를 제공한다.   
④ 1은 가장 높은 우선순위이기 때문에 다른 스레드보다 실행 기회를 더 많이 갖는다.  

답 : ④ 숫자가 클 수록 더 높은 우선순위를 갖는다.

### 4. 동기화 메소드와 동기화 블록에 대한 설명 중 틀린것은 무엇입니까?  
① 동기화 메소드와 동기화 블록은 싱글(단일) 스레드 환경에서는 필요 없다.    
② 스레드가 동기화 메소드를 실행할 때 다른 스레드는 일반 메소드를 호출할 수 없다.  
③ 스레드가 동기화 메소드를 실행할 때 다른 스레드는 다른 동기화 메소드를 호출할 수 없다.   
④ 스레드가 동기화 블록을 실행할 때 다른 스레드는 다른 동기화 메소드를 호출할 수 없다.

답 : ② 일반 메소드는 자유롭게 호출가능하다.

### 5. 스레드 일시 정지 상태에 대한 설명 중 틀린 것은 무엇입니까?  
① 일시 정지 상태는 BLOCKED, WAITING, TIMED_WAITING이 있다.    
② 스레드가 동기화 메소드를 실행할 때 다른 스레드가 동기화 메소드를 호출하게 되면 BLOCKED 일시 정지 상태가 된다.  
③ 동기화 메소드 내에서 wait()를 호출하면 WAITING 일시 정지 상태가 된다.   
④ yield() 메소드를 호출하면 TIMED_WAITING 일시 정지 상태가 된다.

답 : ④ yield() 호출 시 RUNNABLE 상태가 된다.

### 6. 스레드 상태 제어를 하는 메소드에 대한 설명 중 틀린 것은 무엇입니까?
① 일시 정지 상태는 BLOCKED, WAITING, TIMED_WAITING이 있다.    
② 스레드가 동기화 메소드를 실행할 때 다른 스레드가 동기화 메소드를 호출하게 되면 BLOCKED 일시 정지 상태가 된다.  
③ 동기화 메소드 내에서 wait()를 호출하면 WAITING 일시 정지 상태가 된다.   
④ yield() 메소드를 호출하면 TIMED_WAITING 일시 정지 상태가 된다.

답 : ④ yield() 호출 시 RUNNABLE 상태가 된다.  

### 7. interrupt() 메소드를 호출한 효과에 대한 설명 중 틀린것은 무엇입니까?
① 일시 정지 상태에서 InterruptedException을 발생 시킨다.    
② 스레드는 즉시 종료한다.  
③ 실행 대기 상태에서 호출되면 일시 정지 상태가 될 때까지 InterruptedException이 발생하지 않는다.   
④ 아직 InterruptedException이 발생하지 않았따면 interrupted(), isInterrupted() 메소드는 true를 리턴한다.  

답 : ② 스레드는 즉시 종료하지 않고, InturruptedException 처리를 통해 실행준비나 종료 상태로 자연스레 전이된다.  

### 8. 메인 스레드에서 1초 후 MovieThread의 interrupt() 메소드를 호출해서 MovieThread를 안전하게 종료하고 싶습니다. 비어 있는 부분에 적당한 코드를 작성해보세요.  

```java
/* ThreadExample.java */
public static void main(String[] args) {
    Thread thread = new MovieThread();
    thread.start();
    
    try { Thread.sleep(1000); } catch (InterruptedException e) { }
    
    thread.interrupt();
}

/* MovieThread.java */
public class MoiveThread extends Thread {
    @Override
    public void run() {
        while(ture) {
            System.out.println("동영상을 재생합니다.");
            [빈 블럭]
        }
    }
}
```

정답 : 
```java
try {
    Thread.sleep(1);    
} catch (InterruptedException e) {
    break;
}
```
또는
```java
if(Thread.interrupted()) {
    break;
}
```

### 9. wait()와 notify() 메소드에 대한 설명 중 틀린 것은 무엇입니까?  
① 스레드가 wait()를 호출하면 일시 정지 상태가 된다.    
② 스레드가 notify()를 호출하면 wait()로 일시 정지 상태에 있던 다른 스레드가 실행 대기 상태가 된다.    
③ wait()와 notify()는 동기화 메소드 또는 블록에서 호출할 필요가 없다.  
④ 스레드가 wait(long millis)를 호출하면 notify()가 호출되지 않아도 주어진 시간이 지나면 자동으로 실행 대기 상태가 된다.  

정답 : ③

### 10. 메인 스레드가 종료하면 MovieTHread도 같이 종료되기 만들고 싶습니다. 비어 있는 부분에 적당한 코드를 넣어 보세요.  

```java
/* ThreadExample.java */
public class ThreadExample {
    public static void main(String[] args) {
        Thread thread = new MovieThread();
        [빈칸]
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) { }
    }
}
```

정답 : thread.setDaemon(true);

### 11. while문으로 반복적인 작업을 하는 스레들르 종료시키는 방법에 대한 설명 중 최선의 방법이 아닌것은?
① stop() 메소드를 호출해서 즉시 종료시킨다.    
② 조건식에 boolean 타입의 stop 플래그를 이용해서 while문을 빠져나간다.    
③ 스레드가 반복적으로 일시 정지 상태가 된다면 InterruptedException을 발생시켜 예외처리 코드에서 breadk문으로 빠져나가게 한다.  
④ 스레드가 일시 정지 상태로 가지 않는다면 isInterrupted()나 interrupted() 메소드의 리턴값을 조사해서 true일 경우 break문으로 빠져나가게 한다.  

정답 : ① stop()으로 종료 시 스레드가 점유하고 있던 자원들의 상태가 불안정해지는 문제가 있어서 deprecated 되었다.

### 12. 스레드풀에 대한 설명 중 틀린 것은 무엇입니까?
① 갑작스러운 작업의 증가로 스레드의 폭증을 막기 위해 사용된다.    
② ExecutorService 객체가 스레드풀이며 newFixedThreadPool() 메소드로 얻을 수 있다.      
③ 작업은 Runnable 또는 Callable 인터페이스를 구현해서 정의한다.  
④ submit() 메소드로 작업 처리 요청을 하면 작업이 완료될 때까지 블로킹된다.  

정답 : ④ submit() 메서드는 즉시 Future 객체를 반환하며 블로킹되지 않음, Future.get()을 호출할 때 비로소 블로킹이 발생

### 13. Future 객체에 대한 설명 중 틀린것은 무엇입니까?  
① Future는 스레드가 처리한 작업의 결과값을 가지고 있는 객체이다.    
② submit() 메소드를 호출하면 즉시 리턴되는 객체이다.      
③ Future의 get() 메소드는 스레드가 작업을 완료하기 전까지 블로킹된다.  
④ CompletionService를 이용하면 작업 완료된 순으로 Future를 얻을 수 있다.

정답 : ② 작업이 끝난 후 리턴된다.