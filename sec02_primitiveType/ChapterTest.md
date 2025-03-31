## 확인문제
### 1. 자바에서 변수에 대한 설명 중 틀린것은 무엇입니까?
① 변수는 하나의 값만 저장할 수 있다.  
② 변수는 선언 시에 사용한 타입의 값만 저장할 수 있다.  
③ 변수는 변수가 선언된 중괄호({ })안에서만 사용할 수 있다.  
④ 변수는 초기값이 저장되지 않은 상태에서 읽을 수 있다.  

답 : 4. 자바에서 초기화되지 않은 변수를 읽으려고 하면 컴파일 오류가 발생한다. 그러나 멤버변수는 초기값을 설정하지 않고  
사용해도 기본값으로 자동 초기화 된다.

### 2. 변수 이름으로 사용 가능한 것을 모두 선택하세요.
① modelName  
② class  
③ 6hour  
④ $value  
⑤ _age  
⑥ int

답 : 1, 4, 5

### 3. 다음 표의 빈칸에 알맞은 기본타입(primitive type) 8개를 적어보세요.
| 크기 / 타입 | 1byte | 2byte  | 4byte | 8byte |
|---|:---|:-------|:------|:---|
|정수타입|( ① )| ( ② ),( ③ ) | ( ④ ) |( ⑤ )|
|실수타입|||( ⑥ )|( ⑦ )|
|논리타입|( ⑧ )|||

답 : ①byte, ②char , ③short, ④int, ⑤long, ⑥float, ⑦double, ⑧boolean

### 4. 다음 코드에서 타입, 변수이름, 리터럴에 해당하는것을 적어 보세요.
```java
int age;
age = 10;
double price = 3.14;
```
타입 : ( ), ( )  
변수이름 : ( ), ( )  
리터럴 : ( ), ( )

답 : int, double, age, price, 10, 3.14

### 5. 자동 타입 변환에 대한 내용입니다. 컴파일 에러가 발생하는 것은 무엇입니까?
```java
byte byteValue = 10;
char charValue = 'A';
```
① int intValue = byteValue;  
② int intValue = charValue;  
③ short shortValue = charValue;  
④ double doubleValue = byteValue;

답 : char는 2byte지만 음수범위가 없으므로, 1 ~ 2^16-1 이고, short는 -2^15-1 ~ 2^15-1으로 범위가 다르다.  
범위 내에 있더라도 자바는 char와 short간 자동형변환을 허용하지 않으므로,Casting해 주지 않는다면 컴파일 오류가 발생한다.

### 6. 강제 타입 변환(Casting)에 대한 내용입니다. 컴파일 에러가 발생하는것은 무엇입니까?
```java
int intValue = 10;
char charValue = 'A';
double doubleValue = 5.7;
String strValue = "A";
```
① double var = (double) intValue; => 자동형변환이 가능한 경우에도 강제형변환이 가능하다.  
② byte var = (byte) intValue;  
③ int var = (int) doubleValue;  
④ char var = (char) strValue;  

답 : 4. 문자열은 참조타입이므로, Casting으로 char타입으로 타입변환이 불가능하다.

### 7. 변수를 잘못 초기화 한것은 무엇입니까?
① int var1 = 10;  
② long var2 = 10000000000L;  
③ char var3 = '';  
④ double var4 = 10;  
⑤ float var5 = 10;  

답 : 3. char 변수는 ''와 같은 빈 문자로 초기화할 수 없다.

### 8. 연산식에서의 타입 변환 내용입니다. 컴파일 에러가 생기는 것은 무엇입니까?
```java
byte byteValue = 10;
float floatValue = 2.5F;
double doubleValue = 2.5;
```
① byte result = byteValue + byteValue;  
② int result = 5 + byteValue;  
③ float result = 5 + floatValue;  
④ double result = 5 + doubleValue;  

답 : 1. 정수연산의 기본단위는 int