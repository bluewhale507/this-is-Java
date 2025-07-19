## 확인문제
### 1. 스트림에 대한 설명으로 틀린 것은 무엇입니까?
① 스트림은 내부 반복자를 사용하기 때문에 코드가 간결해진다.  
② 스트림은 요소를 분리해서 병렬 처리시킬 수 있다.  
③ 스트림은 람다식을 사용해서 요소 처리 내용을 기술한다.  
④ 스트림은 요소를 모두 처리하고 나서 처음부터 요소를 다시 반복시킬 수 있다.  

답 : ④ 스트림은 한 번만 소비(consumed) 가능한 일회성 객체이다. 요소를 모두 처리한 후에는 재사용할 수 없으며, 다시 사용하려면 새 스트림을 생성해야 한다.

### 2. 스트림을 얻을 수 있는 소스가 아닌 것은 무엇입니까?
① 컬렉션(List)  
② int, long, double 범위  
③ 디렉토리  
④ 배열  

답 : ② int, long은 range(),rangeClose()를 이용해 범위 스트림을 얻을 수 있지만, double은 없음.

### 3. 스트림에 파이프라인 대한 설명으로 틀린 것은 무엇입니까?
① 스트림을 연결해서 중간 처리와 최종 처리를 할 수 있다.  
② 중간 처리 단계에서는 필터링, 매핑, 정렬, 그룹핑을 한다.  
③ 최종 처리 단계에서는 합계, 평균, 카운팅, 최대값, 최소값 등을 얻을 수 있다.  
④ 최종 처리가 시작되기 전에 중간 처리 단계부터 시작시킬 수 있다.  

답 : ④ 스트림의 핵심원리는 지연평가(lazy evaluation)으로 중간연산만 기술했을때는 실행되지 않고, 최종 연산이 호출되어야 데이터 처리가 이루어짐

### 4. 스트림 병렬 처리에 대한 설명으로 틀린 것은 무엇입니까?
① 멀티 코어의 수에 따라 요소를 분배하고 스레드를 생성시킨다.  
② 내부적으로 포크조인 프레임워크를 이용한다.  
③ 병렬 처리는 순차적 처리보다 항상 빠른 처리를 한다.  
④ 내부적으로 스레드풀을 이용해서 스레드를 관리한다.  

답 : ③ 병렬 처리는 스레드 풀과 스레드를 생성하는 비용이 추가로 들기 때문에 요소당 수와 처리시간을 고려하여 선택적으로 적용해야 한다.

### 5. List에 저장되어 있는 String 요소에서 대소문자와 상관없이 "java"라는 단어가 포함된 문자열만 필터링해서 출력하려고 합니다. 빈칸에 알맞은 코드를 작성해보세요.  
```java
public class StreamExample {
	public static void main(String[] args) {
		List<String> list = Arrays.asList(
			"This is a java book",
			"Lambda Expressions",
			"Java8 supports lambda expressions"
		);
		list.stream()
            // 빈 줄    
			.forEach(a -> System.out.println(a));
	}
}
```

답 : 
```java
.filter(s->s.toLowerCase().contains("java"))
```

### 6. List에 저장되어 있는 Member의 평균 나이를 출력하려고 합니다. 빈칸에 알맞은 코드를 작성해 보세요.  
```java
import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<Member> list = Arrays.asList(
                new Member("홍길동", 30),
                new Member("신용권", 40),
                new Member("감자바", 26)
        );

        double avg = list.stream()
        //빈 블럭
        // ...

        System.out.println("평균 나이: " + avg);
    }

    static class Member {
        private String name;
        private int age;

        public Member(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }
}
```

답 : 
```java
.mapToInt(Member::getAge)
.average()
.getAsDouble();
```

### 7. List에 저장되어 있는 Member 중에서 직업이 "개발자"인 사람만 별도의 List에 수집하려고 합니다. 빈칸에 알맞은 코드를 작성해보세요.  
```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
	public static void main(String[] args) {
		List<Member> list = Arrays.asList(
			new Member("홍길동", "개발자"),
			new Member("김나리", "디자이너"),
			new Member("신용권", "개발자")
		);
		
		List<Member> developers = list.stream()
        // 빈 블록
        // ...
		
		developers.stream().forEach(m -> System.out.println(m.getName()));
	}
	
	static class Member {
		private String name;
		private String job;
		
		
		public Member(String name, String job) {
			this.name = name;
			this.job = job;
		}
		
		public String getName() { return name; }
		public String getJob() { return job; }
	}
}
```

답:
```java
.filter(m -> m.getJob().equals("개발자"))
.collect(Collectors.toList());
```

### 8. List에 저장되어 있는 Member를 직업별로 그룹핑해서 Map<String,List<String>> 객체로 생성하려고 합니다. 키는 Member의 직업이고, 값은 Member의 이름으로 구성된 List<String>입니다. 빈칸에 알맞은 코드를 작성해보세요.  
```java
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamExample {
	public static void main(String[] args) {
		List<Member> list = Arrays.asList(
			new Member("홍길동", "개발자"),
			new Member("김나리", "디자이너"),
			new Member("신용권", "개발자")
		);
		
		Map<String, List<String>> groupingMap = list.stream()
        // 빈 블록
        // ...

		System.out.print("[개발자] ");
		groupingMap.get("개발자").stream().forEach(s -> System.out.print(s + " "));
		System.out.print("\n[디자이너] ");
		groupingMap.get("디자이너").stream().forEach(s -> System.out.print(s + " "));
	}
	
	static class Member {
		private String name;
		private String job;
		
		
		public Member(String name, String job) {
			this.name = name;
			this.job = job;
		}
		
		public String getName() { return name; }
		public String getJob() { return job; }
	}
}
```

답:
```java
.collect(
    Collectors.groupingBy(
        Member::getJob, 
        Collectors.mapping(Member::getName, Collectors.toList())
    )
); 
```