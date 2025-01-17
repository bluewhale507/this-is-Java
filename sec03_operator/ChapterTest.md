## 확인문제
### 1. 연산자와 연산식에 대한 설명 중 틀린것은 무엇입니까?
① 연산자는 피연산자의 수에 따라 단항, 이항, 삼항 연산자로 구분된다.
② 비교 연산자와 논리 연산자의 산출 타입은 boolean(true/false)이다.
③ 연산식은 하나 이상의 값을 산출할 수도 있다. 
④ 하나의 값이 올 수 있는 자리라면 연산식도 올 수 있다.

답 : 3. 연산식은 기본적으로 하나의 값을 산출한다.

### 2. 다음 코드를 실행했을 때 출력 결과는 무엇입니까?
```java
public class Exercise02 {
    public static void main(String[] args) {
        int x = 10;
        int y = 20;
        int z = (++x) + (y--);
        System.out.println(z);
    }
}
```
답 : 31

### 3. 다음 코드를 실행했을 때 출력 결과는 무엇입니까?
```java
public class Exercise03 {
    int score = 85;
    String result = (!(score>90)) ? "가" :"나";
    System.out.println(result);
}
```
답 : 가

### 4. 534자루의 연필을 30명의 학생들에게 똑같은 개수로 나누어 줄 때 학생당 몇 개를 가질 수 있고, 최종적으로 몇 개가 남는지를 구하는 코드입니다. ( #1 )과 ( #2 )에 들어갈 코드를 작성하세요.
```java
public class Exercise04 {
    public static void main(String[] args) {
        int pencils = 534;
        int students = 30;
        
        //학생 한 명이 가지는 연필 수
        int pencilsPerStudent = ( #1 );
        System.out.println(pencilsPerStudent);
        
        //남은 연필 수
        int pencilsLeft = ( #2 );
        System.out.println(pencilsLeft);
    }
}
```
답 :
```java
// ( #1 )
pencils / students
// ( #2 )
pencils % students
```

### 5. 다음은 십의 자리 이하를 버리는 코드입니다. 변수 value의 값이 356이라면 300이 나올 수 있도록 ( #1 )에 알맞은 코드를 작성하세요(산술연산자만 사용하세요.)
```java
public class Exercise05 {
    public static void main(String[] args) {
        int value = 356;
        System.out.println( #1 );
    }
}
```
답 : value / 100 * 100

### 6. 다음 코드는 사다리꼴의 넓이를 구하는 코드입니다. 정확히 소수자릿수가 나올 수 있도록 ( #1 )에 알맞은 코드를 작성하세요.
```java
public class Exercise06 {
    public static void main(String[] args) {
        int lengthTop = 5;
        int lengthBottom = 10;
        int height = 7;
        double area = ( #1 );
        System.out.println(area);
    }
}
```
답 : (lengthBottom + lengthTop) * height / 2.0

### 7. 다음 코드는 비교 연산자와 논리 연산자의 복합 연산식입니다. 연산식의 출력 결과를 괄호( )에 넣으세요.
```java
public class Exercise07 {
    public static void main(String[] args) {
        int x = 10;
        int y = 5;

        System.out.println( (x>7) && (y<=5));               // ( ① )
        System.out.println( (x%3 == 2) || (y%2 != 1));      // ( ② )
    }
}
```
답 : ① true ② false

### 8. 다음은 % 연산을 수행한 결과값에 10을 더하는 코드입니다. NaN값을 검사해서 올바른 결과가 출력될 수 있도록 ( #1 )에 들어갈 NaN을 검사하는 코드를 작성하세요.
```java
public class Exercise08 {
    public static void main(String[] args) {
        double x = 5.0;
        double y = 0.0;
        
        double z = x % y;
        
        if( #1 ) {
            System.out.println("0.0으로 나눌 수 없습니다.");
        } else {
            double result = z + 10;
            System.out.println("결과 : " + result);
        }
    }
}
```
답 : Double.isNan(z)

