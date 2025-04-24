## 확인문제
### 1. 람다식에 대한 설명 중 틀린것은 무엇입니까?
① 람다식은 함수적 인터페이스의 익명 구현 객체를 생성한다.  
② 매개 변수가 없을 경우 ( ) -> { ... } 형태로 작성한다.  
③ (x,y) -> { return x+y; } 는 (x,y) -> x+y 형태로 바꿀 수 있다.  
④ @FunctionalInterface가 기술된 인터페이스만 람다식으로 표현이 가능하다.  

답 : ④
> @FunctionalInterface 애너테이션은 함수형 인터페이스임을 명확하게 나타내기 위한 표시일 뿐, 람다식을 사용하기 위해 반드시 필요한 것은 아님. 즉, @FunctionalInterface가 없어도 단일 추상 메서드를 가진 인터페이스라면 람다식을 사용할 수 있다.

### 2. 메소드 참조에 대한 설명 중 틀린것은 무엇입니까?
① 메소드 참조는 함수적 인터페이스의 익명 구현 객체를 생성한다.  
② 인스턴스 메소드는 "참조변수::메소드"로 기술한다.  
③ 정적 메소드는 "클래스::메소드"로 기술한다.  
④ 생성자 참조인 "클래스::new"는 매개 변수가 없는 디폴트 생성자만 호출한다.  

답 : ④
> "클래스::new"는 반드시 디폴트 생성자만 호출하는 것이 아니다. 실제로는 여러 생성자 중에서 매개변수와 일치하는 생성자가 호출된다. 즉, Supplier<T>, Function<T, R>, BiFunction<T, U, R> 같은 함수형 인터페이스의 매개변수 개수에 따라 적절한 생성자가 선택된다.

### 3. 잘못 작성된 람다식은 무엇입니까?
① a -> a+3  
② a,b -> a*b  
③ x -> System.out.println(x/5)  
④ (x,y) -> Math.max(x,y)  

답 : ②, 매개변수가 하나이상인 경우 괄호로 묶어주어야 한다.

### 4. 다음 코드는 컴파일 에러가 발생합니다. 그 이유가 무엇입니까?
```java
import java.util.function.IntSupplier;

public class LambdaExample {
    public static int method(int x, int y) {
        IntSupplier supplier = () -> {
            x *= 10;
            int result = x + y;
            return result;
        };
        int result = supplier.getAsInt();
        return result;
    }

    public static void main(String[] args) {
        System.out.println(method(3, 5));
    }
}
```
답 : x *= 10;
> 람다식은 메소드처럼 보이지만 내부적으로 익명 구현 객체를 생성한다. 람다식이나 익명구현객체 내부에서 외부 변수를 참조할 때는 해당 변수의 값이 복사되어 사용된다. 하지만 자바는 복사된 변수의 값을 변경할 수 없도록 제한을 두는데, 이는 다음과 같은 문제를 방지하기 위한 조치이다.
> - 람다식 내부에서 변경된 값이 외부에서 변경되지 않는 문제
> - 멀티스레드 환경에서 변수 변경이 일관성이 없는 문제
>
> 이 때문에 컴파일러는 람다식 내부에서 참조하는 지역 변수를 `final`이나 `사실상의 final`로 제한한다. 

### 5. 다음은 배열 항목 중에 최대값 또는 최소값을 찾는 코드입니다. maxOrMin( ) 메소드의 매개값을 람다식으로 기술해보세요.  
```java
// LambdaExampl.java
import java.util.function.IntBinaryOperator;

public class LambdaExample {
    private static int[] scores = { 10, 50, 3 };
    
    public static maxOrMin(IntBinaryOperator operator) {
        int result = scores[0];
        for(int score : scores) {
            result = operator.applyAsInt(result, score);
        }
        return result;
    }

    public static void main(String[] args) {
        //최대값 얻기
        int max = maxOrMin(
                /* 답안 작성 */ 
        );
        System.out.println("최대값: " + max);
        
        //최소값 얻기
        int min = maxOrMin(
                /* 답안 작성 */
        );
        System.out.println("최소값: " + min);
    }
}
```

답 : 
```java
/* 최대값 */
(x, y) -> {
    if(x>=y) return x;
    else return y;
}

/* 최소값 */
(x, y) -> {
    if(x<=y) return x;
    else return y;
}
```

### 6. 다음은 학생의 영어 평균 점수와 수학 평균 점수를 계산하는 코드입니다. avg( ) 메소드를 선언해보세요.
```java
import java.util.function.ToIntFunction;

public class LambdaExample {
    private static Student[] students = {
        new Student("홍길동", 90, 96),
        new Student("신용권", 95, 93)
    };
    
    /* avg() 메소드 작성 */

    public static void main(String[] args) {
        double englishAvg = avg( s -> s.getEnglishScore() );
        System.out.println("영어 평균 점수: " + englishAvg);
        
        double mathAvg = avg( s -> s.getMathScore() );
        System.out.println("수학 평균 점수: " + mathAvg);
    }

    public class Student {
        private String name;
        private int englishScore;
        private int mathScore;

        public Student(String name, int englishScore, int mathScore) {
            this.name = name;
            this.englishScore = englishScore;
            this.mathScore = mathScore;
        }

        public String getName() {
            return name;
        }

        public int getEnglishScore() {
            return englishScore;
        }

        public int getMathScore() {
            return mathScore;
        }
    }
}
```

답 : 
```java
public static double avg(ToIntFunction<Student> function) {
    int sum = 0;
    for (Student student : students) {
        sum += function.applyAsInt(student);
    }
    double avg = (double) sum / students.size();
    result = avg;
}
```

### 7. 6번의 main() 메소드에서 avg()를 호출할 때 매개값으로 준 람다식을 메소드 참조로 변경해 보세요.

```java
import sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI.Student;

dobule englishAvg = avg(s -> s.getEnglishScore());

// =>
double englishAvg = avg(Student::getEnglishScore);

double mathAvg = avg(s -> s.getMathScore());
// =>
double mathAvg = avg(Student::getMathScore)
```