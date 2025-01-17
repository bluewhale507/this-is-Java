## 확인문제
### 1. 조건문과 반복문의 종류를 괄호 ( ) 속에 넣어보세요.
조건문 : ( ), ( )
반복문 : ( ), ( ), ( )

답 : 차례로 if, switch, for, while, do-while

### 2. 조건문과 반복문을 설명한 것 중 틀린것은 무엇입니까?
① if문은 조건식의 결과에 따라 실행 흐름을 달리할 수 있다.  
② switch문에서 사용할 수 있는 변수의 타입은 int, double이 될 수 있다.  
③ for문은 카운터 변수로 지정한 횟수만큼 반복시킬 때 사용할 수 있다.  
④ break문은 switch문, for문, while문을 종료할 때에 사용할 수 있다.

답 : ②

### 3. for문을 이용해서 1부터 100까지의 정수 중에서 3의 배수의 총합을 구하는 코드를 작성해보세요.
```java
public class Exercise03 {
    public static void main(String[] args) {
        // 답
        int result = 0;
        for(int i=1; i<=100; i++) {
            if(i%3 == 0) result += i;
        }
    }
}
```

### 4. while문과 Math.random() 메소드를 이용해서 두 개의 주사위를 던졌을 때 나오는 눈을 (눈1, 눈2) 형태로 출력하고, 눈의 합이 5가 아니면 계속 주사위를 던지고 눈의 합이 5이면 실행을 멈추는 코드를 작성해보세요.  
```java
public class Exercise04 {
    public static public static void main(String[] args){
        // 답
        do {
            dice_num1 = (Math.random() * 6) + 1;
            dice_num2 = (Math.random() * 6) + 1;
            System.out.println("("+dice_num1+","+dice_num2+")");
        } while ((dice_nume1 + dice_num2) == 6);
    }
}
```
### 5. 중첩 for문을 이용하여 방정식 4x + 5y = 60의 모든 해를 구해서 (x,y) 형태로 출력해 보세요.(단, x와 y는 10 이하의 자연수)
```java
public class Exercise05 {
    public static void main(String[] args) {
        // 답
        for(int i=0; i<11; i++) {
            for(int j=0; j<11; j++) {
                if (4*i + 5*j == 60) System.out.println("("+i+","+j+")");
            }
        }
    }
}
```

### 6. for문을 이용해서 실행 결과와 같은 삼각형을 출력하는 코드를 작성해보세요.
```java
public class Exercise06 {
    public static void main(String[] args) {
        // 답
        for(int i=1; i<6; i++) {
            for(int j=0; j<i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
```

### 7. while문과 Scanner를 이용해서 키보드로부터 입력된 데이터로 예금, 출금, 조회, 종료 기능을 제공하는 코드를 작성해보세요.
```java
import java.util.Scanner;

public class Exercise07 {
    public static void main(String[] args) {
        boolean run = true;

        int balance = 0;
        int input = 0;
        Scanner scanner = new Scanner(System.in);

        while(run) {
            System.out.println("-----------------------------");
            System.out.println("1.예금 | 2.출금 | 3.잔고 | 4.종료");
            System.out.println("-----------------------------");
            System.out.print("선택 > ");

            input = scanner.nextInt();
            // 답
            switch(input) {
                case 1:
                    System.out.print("예금액>");
                    balance += scanner.nextInt();
                    break;
                case 2:
                    System.out.print("출금액>");
                    balance -= scanner.nextInt();
                    break;
                case 3:
                    System.out.println("잔고>"+balance);
                    break;
                case 4:
                    run = false;
            }
        }
        System.out.println("프로그램 종료");

    }
}
```