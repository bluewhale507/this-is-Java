# IO 패키지 기반 입출력 및 네트워킹
# 입력 스트림과 출력 스트림
프로그램의 출발지냐 도착지냐에 따라서 스트림의 종류가 결정되는데, 프로그램이 데이터를 입력 받을 때에는 입력스트림이라 부르고, 프로그램이 데이터를 보낼 때에는 출력 스트림이라고 부른다. 입력 스트림의 출발지는 키보드, 파일, 네트워크상의 프로그램이 될 수 있고, 출력 스트림의 도착지는 모니터, 파일, 네트워크상의 프로그램이 될 수 있다.

<img src=./img/IO_01.png style="display: block; margin: 0 auto;">

항상 프로그램을 기준으로 데이터가 들어오면 입력 스트림이고, 데이터가 나가면 출력 스트림이라는 것을 명심해야 한다. 프로그램이 다른 프로그램과 데이터 교환을 하기 위해서는 양쪽 모두 입력 스트림과 출력 스트림이 따로 필요하다. 스트림의 특성이 단방향이므로 하나의 스트림으로 입출력을 모두 할 수가 없기 때문이다.

<img src=./img/IO_02.png style="display: block; margin: 0 auto;">

java의 기본적인 입출력 API는 java.io 패키지에서 제공하고 있다. java.io 패키지에는 파일 시스템의 정보를 얻기 위한 File 클래스와 데이터를 입출력 하기 위한 다양한 입출력 스트림 클래스를 제공하고 있다.

|java.io 패키지의 주요 클래스|설명|
|---|---|
|File|파일 시스템의 정보를 얻기 위한 클래스|
|Console|콘솔로부터 문자를 입출력 하기 위한 클래스|
|InputStream / OutputStream|바이트 단위 입출력을 위한 최상위 입출력 스트림 클래스|
|FileInputStream / FileOutputStream <br> DataInputStream / DataOutputStream <br> ObjectInputStream / ObjectOutputStream | 바이트 단위 입출력을 위한 스트림 클래스|
|Reader / Writer | 문자 단위 입출력을 위한 최상위 입출력 스트림 클래스|
|FileReader / FileWriter <br> InputStreamReader / OutputStreamWriter|문자 단위 입출력을 위한 하위 스트림 클래스|

스트림 클래스는 두 종류로 구분된다. 하나는 바이트 기반 스트림이고, 다른 하나는 문자(character)기반 스트림이다. 바이트 기반 스트림은 그림, 멀티미디어, 문자 등 모든 종류의 데이터를 받고 보낼 수 있으나, 문자 기반 스트림은 오직 문자만 받고 보낼 수 있도록 특화되어 있다.

## InputStream
InputStream은 바이트 기반 입력 스트림의 최상위 클래스로 추상 클래스이다. 모든 바이트 기반 입력 스트림은 이 클래스를 상속받아서 만들어진다. 다음과 같이 FileInputStream, BufferedInputStream, DataInputStream 클래스는 모두 InputStream 클래스를 상속하고 있다.

<img src=./img/IO_03.png style="display: block; margin: 0 auto;">

InputStream 클래스에는 바이트 기반 입력 스트림이 기본적으로 가져야 할 메소드가 정의되어 있다. 

| 리턴 타입 | 메소드                              | 설명                                                                                                                                    |
|-------|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| int   | read()                           | 입력 스트림으로부터 1바이트를 읽고 읽은 바이트를 리턴한다.                                                                                                     |
| int   | read(byte[] b)                   | 입력 스트림으로부터 읽은 바이트를 매개값으로 주어진 바이트 배열 b에 저장하고 실제로 읽은 바이트 수를 리턴한다.                                                                       |
| int   | read(byte[] b, int off, int len) | 입력 스트림으로부터 len개의 바이트만큼 읽고 매개값으로 주어진 바이트 배열 b[off]부터 len개 까지 저장한다. 그리고 실제로 읽은 바이트수인 len개를 리턴한다. 만약 len개를 모두 읽지 못하면 실제로 읽은 바이트 수를 리턴한다. |
| void  | close()                          | 사용한 시스템 자원을 반납하고 입력 스트림을 닫는다.                                                                                                         |

### read 메소드
read 메소드는 입력 스트림으로부터 1바이트를 읽고 4바이트 int 타입으로 리턴한다. 따라서 리턴된 4바이트 중 끝의 1바이트에만 데이터가 들어 있다. 예를 들어 입력 스트림에서 5개의 바이트가 들어온다면 다음과 같이 read()메소드로 1바이트씩 5번 읽을 수 있다.

<img src=./img/IO_04.png style="display: block; margin: 0 auto;">

더 이상 입력 스트림으로 부터 바이트를 읽을 수 없다면 read() 메소드는 -1을 리턴하는데, 이것을 이용하면 읽을 수 있는 마지막 바이트까지 루프를 돌며 한 바이트씩 읽을 수 있다.

```java
InputStream is = new FileInputStream("C:\\\test.jpg");   
int readByte;  
while((readByte=is.read()) !=s -1 ) {...}  
```

=> window OS에서의 경로 표시는 `\\\` 혹은 `/` 둘다 사용가능하다.

### read(byte[] b) 메소드
입력 스트림으로부터 매개값으로 주어진 바이트 배열의 길이만큼 바이트를 읽고 배열에 저장한다. 그리고 읽은 바이트 수를 리턴한다. 실제로 읽은 바이트 수가 배열의 길이보다 작을 경우 읽은 수만큼만 리턴한다. 예를 들어 입력 스트림에서 5개의 바이트가 들어온다면 다음과 같이 길이 3인 바이트 배열로 두 번 읽을 수 있다.  

<img src=./img/IO_05.png style="display: block; margin: 0 auto;">

read(byte[] b) 역시 입력 스트림으로부터 바이트를 더 이상 읽을 수 없다면 -1을 리턴하는데, 이것을 이용하면 읽을 수 있는 마지막 바이트까지 루프를 돌며 읽을 수 있다.


```java
InputStream is = new FileInputStream("C:/test.jpg");
int readByteNo;
byte[] readBytes = new byte[100];
while ((readByteNo = is.read(readBytes)) != -1) {...}  
```

입력 스트림으로부터 100개의 바이트가 들어온다면 read() 메소드는 100번을 루핑해서 읽어들여야 한다. 그러나 read(byte[] b) 메소드는 한 번 읽을 때 매개값으로 주어진 바이트 배열 길이만큼 읽기 때문에 루핑 횟수가 현저히 줄어든다. 그러므로 많은 양의 데이터를 읽을 때는 read(byte[] b) 메소드를 사용하는 것이 좋다.

### read(byte[] b, int off, int len) 메소드
입력 스트림으로 부터 len개의 바이트만큼 읽고, 매개값으로 주어진 바이트 배열 b[off]부터 len개까지 저장한 후, 읽은 바이트 수인 len개를 리턴한다. 실제로 읽은 바이트 수가 len개 보다 작을 경우 읽은 수만큼 리턴한다. 입력 스트림에서 전체 5개의 바이트가 들어오고, 여기서 3개만 읽어 b[2],b[3],b[4]에 각각 저장한다면 다음과 같이 할 수 있다.  

<img src=./img/IO_06.png width=80% style="display: block; margin: 0 auto;">

이 메서드 역시 입력 스트림으로부터 바이트를 더 이상 읽을 수 없다면 -1을 리턴한다. read(byte[] b)와의 차이점은 한 번에 읽어들이는 바이트 수를 len 매개값으로 조절할 수 있고, 배열에서 저장이 시작되는 인덱스를 지정할 수 있다는 점이다. 만약 off를 0으로, len을 배열의 길이로 준다면 read(byte[] b)와 동일하다.  

| read(byte[] b)                          | read(byte[] b, int off, int len)                            |
|-----------------------------------|-------------------------------------|
| InputStream is = ...;             | InputStream is = ...;              |
| byte[] readBytes = new byte[100]; | byte[] readBytes = new byte[100];  |
| int readByteNo=is.read(readBytes); | int readByteNo=is.read(readBytes, 0, 100); |

### close() 메소드
InputStream을 더 이상 사용하지 않을 경우에는 close() 메소드를 호출해서 InputStream에서 사용했던 시스템 자원을 풀어준다.
```java 
is.close();
```

## OutputStream         
OutputStream은 바이트 기반 출력 스트림의 최상위 클래스로 추상 클래스이다. 모든 바이트 기반 출력 스트림 클래스는 이 클래스를 상속받아서 만들어진다. FileOutputStream, PrintStream, BufferedOutputStream 클래스는 모두 OutputStream 클래스를 상속하고 있다.  

<img src=./img/IO_07.png style="display: block; margin: 0 auto;">

|리턴 타입| 메소드                               | 설명                                              |
|---|-----------------------------------|-------------------------------------------------|
|void| write(int b)                      | 출력 스트림으로 1바이트를 보낸다(b의 끝 1byte)                  |
|void| write(byte[] b)                   | 출려 스트림으로 주어진 바이트 배열 b의 모든 바이트를 보낸다.             |
|void| write(byte[] b, int off, int len) | 출력 스트림으로 주어진 바이트 배열 b[off]부터 len개 까지의 바이트를 보낸다. |
|void| flush()                           | 버퍼에 잔류하는 모든 바이트를 출력한다.                          |
|void| close()                           | 사용한 시스템 자원을 반납하고 출력 스트림을 닫는다.                   |

### write(int b) 메소드
write(int b) 메소드는 매개 변수로 주어진 int 값에서 끝에 있는 1byte만 출력 스트림으로 보낸다. 매개 변수가 int 타입이므로 4byte 모두를 보내는 것으로 오해할 수 있다.  

<img src=./img/IO_08.png style="display: block; margin: 0 auto;">
  
```java 
OutputStream os = new FileOutputStream("C:/test.txt");  
byte[] data = "ABC".getBytes();  
for(int i=0; i<data.length; i++) {  
    os.write(data[i]); // "A", "B", "C"를 하나씩 출력  
}
```  

### write(byte[] b) 메소드
매개값으로 주어진 바이트 배열의 모든 바이트를 출력 스트림으로 보낸다.

<img src=./img/IO_09.png style="display: block; margin: 0 auto;">

```java
OutputStream os = new FileOutputStream("C:/test.txt");
byte[] data = "ABC".getBytes();
os.write(data);
```

### write(byte[] b, int off, int len) 메소드
b[off]부터 len개의 바이트를 출력 스트림으로 보낸다. 

<img src=./img/IO_10.png style="display: block; margin: 0 auto;">

```java
OutputStream os = new FileOutputStream("C:/test.txt");
byte[] data = "ABC".getBytes();
os.write(data, 1, 2); //"BC"만 출력
```

### flush()와 close() 메소드
출력 스트림은 내부에 작은 버퍼가 있어 데이터가 출력되기 전에 버퍼에 쌓여있다가 순서대로 출력된다. flush() 메소드는 버퍼에 잔류하고 있는 데이터를 모두 출력시키고 버퍼를 비우는 역할을 한다. 프로그램에서 더 이상 출력할 데이터가 없다면 flush() 메소드를 마지막으로 호출하여 버퍼에 잔류하는 모든 데이터가 출력되도록 해야 한다. OutputStream을 더 이상 사용하지 않을 경우에는 close() 메소드를 호출해서 OutputStream에서 사용했던 시스템 자원을 풀어준다.

```java
OutputStream os = new FileOutputStream("C:/test.txt");
byte[] data = "ABC".getBytes();
os.write(data);
os.flusht();
os.close();
```

## Reader
Reader는 문자 기반 입력 스트림의 최상위 클래스로 추상 클래스이다. 모든 문자 기반 입력 스트림은 이 클래스를 상속 받아서 만들어진다. 다음과 같이 FileReader, BufferedReader, InputStreamReader 클래스는 모두 Reader 클래스를 상속하고 있다.

<img src=./img/IO_11.png style="display: block; margin: 0 auto;">

| 반환 타입 | 메소드                                 | 설명                                                                                            |
|-------|-------------------------------------|-----------------------------------------------------------------------------------------------|
| int   | read()                              | 입력 스트림으로부터 한 개의 문자를 읽고 리턴한다.                                                                  |
| int   | read(char[] cbuf)                   | 입력 스트림으로부터 읽은 문자들을 매개값으로 주어진 문자 배열 cbuf에 저장하고 실제로 읽은 문자 수를 리턴한다.                              |
| int   | read(char[] cbuf, int off, int len) | 입력 스트림으로부터 len개의 문자를 읽고 매개값으로 주어진 문자 배열 cbuf[off]부터 len개 까지 저장한다. 그리고 실제로 읽은 문자수인 len개를 리턴한다. |
| void  | close()                             | 사용한 시스템 자원을 반납하고 입력 스트림을 닫는다.                                                                 |

### read() 메소드
read() 메소드는 입력 스트림으로부터 한 개의 문자(2byte)를 읽고 4byte int 타입으로 리턴한다. 따라서 리턴된 4byte 중 끝에 있는 2byte에 문자 데이터가 들어 있다. 예를 들어 입력 스트림에서 2개의 문자(총 4byte)가 들어온다면 다음과 같이 read() 메소드로 한 문자씩 두 번 읽을 수 있다.  

read() 메소드가 리턴한 int 값을 char 타입으로 변환하면 읽은 문자를 얻을 수 있다.
```java
char charData = (char) read();
```
입력 스트림으로부터 문자를 더 이상 읽을 수 없다면 역시 read() 메소드는 -1을 리턴하는데 이것을 이용하면 읽을 수 있는 마지막 문자까지 루프를 돌며 한 문자씩 읽을 수 있다.
```java
Reader reader = new FileReader("C:/test.txt");
int readData;
while((readData=reader.read()) != -1) {
    char charData = (char) readData;
}
```

### read(char[] cbuf) 메소드
입력 스트림으로부터 매개값으로 주어진 문자 배열의 길이만큼 문자를 읽고 배열에 저장한다. 그리고 읽은 문자 수를 리턴한다.

```java
Reader reader = new FileReader("C:/test.txt");
int readCharNo;
char[] cbuf = new char[2];
while((readCharNo-reader.read(cbuf)) != -1) {...}
```
많은 양의 문자를 읽을 때 이용하면 루프 횟수가 현저히 줄어든다.

### read(char[] cbuf, int off, int len) 메소드
바이트 스트림과 동일하게 입력 배열 b와 배열 시작 인덱스 off, 읽을 문자 수 len를 인자로 받아 len을 반환한다. 실제 읽은 문자 수가 len개 보다 작을 경우 읽은 수 만큼 리턴한다. 만약 off를 0으로, len을 배열의 길이로 준다면 read(char[] cbuf)와 동일하다.
### close() 메소드
바이트 스트림과 마찬가지로 사용하지 않을 경우 close()를 호출하여 Reader에서 사용했던 시스템 자원을 풀어준다.

## Writer
Writer는 문자 기반 출력 스트림의 최상위 클래스로 추상 클래스이다. 모든 문자 기반 출력 스트림 클래스는 이 클래스르 상속 받아서 만들어진다. 다음과 같이 FileWriter, BufferedWriter, PrintWriter, OutputStreamWriter 클래스는 모두 Writer 클래스를 상속하고 있다.

<img src=./img/IO_12.png style="display: block; margin: 0 auto;">

|리턴 타입| 메소드                                  | 설명                                               |
|---|--------------------------------------|--------------------------------------------------|
|void| write(int c)                         | 출력 스트림으로 주어진 한 문자를 보낸다(b의 끝 2byte)               |
|void| write(char[] cbuf)                   | 출력 스트림으로 주어진 문자 배열 cbuf의 모든 문자를 보낸다.             |
|void| write(char[] cbuf, int off, int len) | 출력 스트림으로 주어진 바이트 배열 cbuf[off]부터 len개까지의 문자를 보낸다. |
|void| write(String str)                    | 출력 스트림으로 주어진 문자열을 전부 보낸다.                        |
|void| write(String str, int off, int len)  | 출력 스트림으로 주어진 문자열 off 순번부터 len개까지의 문자를 보낸다.       |
|void| flush()                              | 버퍼에 잔류하는 모든 문자열을 출력한다.                           |
|void| close()                              | 사용한 시스템 자원을 반납하고 출력 스트림을 닫는다.                    |

### write(int c) 메소드
write(int c) 메소드는 매개 변수로 주어진 int 값에서 끝에 있는 2바이트만 출력 스트림으로 보낸다. 매개 변수가 int 타입이므로 4바이트 모두 보내는 것으로 오해할 수 있다.

```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "홍길동".toCharArray();
for(int i=0; i<data.length; i++) {
    writer.write(data[i]); // "홍", "길", "동"을 하나씩 출력
}
```

### write(char[] cbuf) 메소드
매개값으로 주어진 char[] 배열의 모든 문자를 출력 스트림으로 보낸다.
```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "홍길동".toCharArray();
writer.write(data); // "홍길동" 모두 출력
```

### write(char[] c, int off, int len) 메소드
c[off]부터 len개의 문자를 출력 스트림으로 보낸다.
```java
Writer writer = new FileWriter("C:/test.txt");
char[] data = "홍길동".toCharArray();
writer.write(data, 1, 2); //"길동"만 출력
```

## write(String str)와 write(String str, int off, int len) 메소드
Writer는 문자열을 좀 더 쉽게 보내기 위해서 이 두 메소드를 제공한다. 각각 문자열 전체를 보내거나, 문자열 off 순번부터 len 개 까지의 문자를 출력스트림으로 보낸다. 바이트 출력 스트림과 마찬가지로 더 이상 출력할 문자가 없다면 flush()를 호출하고, Writer를 더 이상 사용하지 않을 경우 close()를 호출해서 시스템 자원을 해제한다.

```java
Writer writer = new FileWriter("C:/test.txt");
String data = "안녕 자바 프로그램";
writer.write(data);
writer.flush();
writer.close();
```

# 콘솔 입출력
콘솔은 시스템을 사용하기 위해 키보드로 입력을 받고 화면으로 출력하는 소프트 웨어를 말한다. 유닉스나 리눅스 운영체제에서는 터미널이 콘솔에 해당하고, Windows 운영체제는 명령 프롬프트가 콘솔에 해당한다. 각 IDE에도 Conosle 뷰가 있는 경우가 많은데, 자바는 콘솔로부터 데이터를 입력 받을 때 System.in을 사용하고, 콘솔에 데이터를 출력할 때 System.out을 사용한다. 그리고 에러를 출력할 때에는 System.err를 사용한다.  

## System.in 필드
자바는 프로그램이 콘솔로부터 데이터를 입력받을 수 있도록 System 클래스의 in 정적 필드를 제공하고 있다. System.in은 InputStream 타입의 필드이므로 다음과 같이 InputStream 변수로 참조가 가능하다.
```java
InputStream is = System.in;
```
키보드로부터 어떤 키가 입력되었는지 확인하려면 InputStream의 read() 메소드로 한 바이트를 읽으면 된다. 리턴된 int값에는 십진수 아스키 코드가 들어있다. 
```java
int asciiCode = is.read();
```
아스키 코드는 1byte로 표현되는 256가지의 숫자에 영어 알파벳, 아리비아 숫자, 특수 기호를 매칭하고 있다. 만약 키보드에서 a를 입력하고 엔터를 눌렀다면 a키의 97번과 Enter키의 13, 10번이 차례로 입력된다. Enter 키는 캐리지 리턴(13)과 라인 피드(10)이 결합된 키라고 볼 수 있다. 숫자로된 아스키 코드 대신에 입력한 문자를 직접 얻고 싶다면 read() 메소드로 읽은 아스키 코드를 char로 타입 변환하면 된다.
```java
char inputChar = (char) is.read();
```

> 다음은 현금 자동 입출금기와 비슷하게 사용자에게 메뉴를 제공하고 사용자가 어떤번호를 입력했는지 알아내는 예제이다.

참고 : [SystemInExample.java](./example/consoleIO/SystemInExample.java)

InputStream의 read() 메소드는 1byte만 읽기 때문에 1byte의 아스키 코드로 표현되는 숫자, 영어, 특수문자는 프로그램에서 잘 읽을 수 있지만, 한글과 같이 2byte를 필요로 하는 유니코드는 read() 메소드로 읽을 수 없다. 키보드로 입력된 한글을 얻기 위해서는 우선 read(byte[] b)나 read(byte[] b, int off, int len) 메소드로 전체 입력된 내용을 바이트로 받고 이 배열을 이용해서 String 객체를 생성하면 된다.  
프로그램에서 바이트 배열에 저장된 아스키 코드를 사용하려면 문자열로 변환해야 한다. 변환할 문자열은 바이트 배열의 0번 인덱스에서 시작해서 읽은 바이트 수-2만큼이다. 2를 빼는 이유는 Enter키에 해당하는 마지막 두 바이트를 제외하기 위해서이다. 바이트 배열을 문자열로 변환할 때에는 다음과 같이 String 클래스의 생성자를 이용한다.
```java
String strData = new String(byteData[바이트배열], 0[시작 인덱스], readByteNo-2[읽은 바이트 수-2]);
```

> 다음은 이름과 하고싶은 말을 입력받아 다시 출력하는 예제이다.

참고 : [SystemInExample2.java](./example/consoleIO/SystemInExample2.java)

## System.out 필드
콘솔에서 입력된 데이터를 System.in으로 읽었다면, 반대로 콘솔로 데이터를 출력하기 위해서는 System 클래스의 out 정적 필드를 사용한다. out은 PrintStream 타입의 필드이다. PrintStream은 OutputStream의 하위 클래스이므로 out 필드를 OutputSTream 타입으로 변환해서 사용할 수 있다.

```java
OutputStream os = System.out;
```
콘솔로 1개의 byte를 출력하려면 OutputStream의 write(int b) 메소드를 이용하면 된다. 이때 바이트 값은 아스키 코드인데, write() 메소드는 아스키 코드를 문자로 콘솔에 출력한다. 예를 들어 아스키 코드 97번을 write(int b)메소드로 출력하면 'a'가 출력된다.
```java
byte b = 97;
os.write(b);
os.flush();
```
OutputStream의 write(int b) 메소드는 1byte만 보낼 수 있기 때문에 1byte로 표현이 가능한 숫자, 영어, 특수문자는 출력이 가능하지만, 2byte로 표현되는 한글은 출력할 수 없다. 한글을 출력하기 위해서는 우선 한글을 바이트 배열로 얻은 다음, write(byte[] b)나, write(byte[] b, int off, int len)메소드로 콘솔에 출력하면 된다.
```java
String name = "홍길동";
byte[] nameBytes = name.getBytes();
os.write(nameBytes);
os.flush();
```
> 다음은 write(int b) 메소드를 사용해서 연속된 숫자, 영어를 출력하고 write(byte[] b) 메소드를 사용해서 한글을 출력한 예제이다.  

참고 : [SystemOutExample.java](./example/consoleIO/SystemOutExample.java)

System 클래스의 out 필드를 OutputStrema 타입으로 변환해서 콘솔에 출력하는 작업은 그리 편하지 않다. out은 원래 PrintStream 타입이 필드이므로 PrintStream의 print() 또는 println() 메서드를 사용하면 좀 더 쉬운 방법으로 다양한 타입의 데이터를 콘솔에 출력할 수 있다.
```java
PrintStream ps = System.out;
ps.println(...);
```

## Console 클래스
자바6부터는 콘솔에서 입력받은 문자열을 쉽게 읽을 수 있도록 java.io.Console 클래스를 제공한다. Console 객체를 얻기 위해서는 System의 정적 메소드인 console()을 다음과 같이 호출하면 된다.  
```java
Console console = System.console();
```
이클립스에서 실행하면 System.consoel() 메소드는 null을 리턴하기 때문에 반드시 명령 프롬프트에서 실행해야 한다. Console 클래스에서 읽기 메소드는 다음과 같다.

|리턴 타입|메소드|설명|
|---|---|---|
|String |readLine()|Enter 키를 입력하기 전의 모든 문자열을 읽음|
|char[]|readPassword()|키보드 입력 문자를 콘솔에 보여주지 않고 문자열을 읽음|

> 다음은 콘솔로부터 아이디와 패스워드를 입력받아 출력하는 예제이다.

참고 : [ConsoleExample.java](./example/consoleIO/ConsoleExample.java)

## Scanner 클래스
Console 클래스는 콘솔로부터 문자열을 읽을 수 있지만 기본 타입(정수, 실수) 값을 바로 읽을 수는 없다. java.io 패키지의 클래스는 아니지만, java.util 패키지의 Scanner 클래스를 이용하면 콘솔로부터 기본 타입의 값을 바로 읽을 수 있다. Scanner 객체를 생성하려면 다음과 같이 생성자에 System.in 매개값을 주면 된다.
```java
Scanner scanner = new Scanner(System.in);
```
Scanner가 콘솔에서만 사용되는 것은 아니고, 생성자 매개값에는 File, InputStream, Path 등과 같이 다양한 입력 소스를 지정할 수도 있다. Scanner는 기본 타입의 값을 읽기 위해 다음과 같은 메소드를 제공한다.  

| 리턴 타입 | 메소드            | 설명                                  |
|-----------|-------------------|---------------------------------------|
| boolean   | nextBoolean()     | boolean(true/false) 값을 읽는다.       |
| byte      | nextByte()        | byte 값을 읽는다.                      |
| short     | nextShort()       | short 값을 읽는다.                     |
| int       | nextInt()         | int 값을 읽는다.                       |
| long      | nextLong()        | long 값을 읽는다.                      |
| float     | nextFloat()       | float 값을 읽는다.                     |
| double    | nextDouble()      | double 값을 읽는다.                    |
| String    | next()            | 공백 이전까지의 문자열을 읽는다.       |
| String    | nextLine()        | 한 줄 전체 문자열을 읽는다. (개행까지) |

위 메소드들은 콘솔에서 데이터를 입력한 후 Enter키를 누르면 동작하도록 되어 있다.

> 다음 예제는 콘솔로부터 문자열, 정수, 실수를 직접 읽고 다시 콘솔로 출력한다.

참고 : [ScannerExample.java](./example/consoleIO/ScannerExample.java)

# 파일 입출력
## File 클래스

IO 패키지에서 제공하는 File 클래스는 파일 크기, 파일 속성, 파일 이름 등의 정보를 얻어내는 기능과 파일 생성 및 삭제 기능을 제공하고 있다. 그리고 디렉토리를 생성하고 디렉토리에 존재하는 파일 리스트를 얻어내는 기능도 있다. 그러나 파일의 데이터를 읽고 쓰는 기능은 지원하지 않는다. 파일의 입출력은 스트림을 사용해야 한다. 다음은 C:/Temp 디렉토리의 file.txt 파일을 File 객체로 생성하는 코드이다.  
```java
File file = new File("C:\\Temp\\file.txt");
File file = new File("C:/Temp/file.txt");
```

| 생성자 형태                               | 설명                                         |
|-------------------------------------------|----------------------------------------------|
| File(String pathname)                     | 지정된 경로 이름으로 File 객체 생성              |
| File(String parent, String child)         | 부모 경로와 자식 경로를 조합하여 File 객체 생성   |
| File(File parent, String child)           | 부모 File 객체와 자식 경로를 조합하여 File 객체 생성 |


디렉토리 구분자는 운영체제마다 조금씩 다르다. 윈도우에서는 \또는 /를 사용하나 리눅스에서는 /를 사용한다. File.separator 상수를 출력해보면 해당 운영체제에서 사용하는 디렉토리 구분자를 확인할 수 있다. 만약 \를 디렉토리 구분자로 사용한다면 이스케이프 문자(\\)로 기술해야 한다.  
File 객체를 생성했다고 해서 파일이나 디렉토리가 생성되는 것은 아니다. 생성자 매개값으로 주어진 경로가 유효하지 않더라도 컴파일 에러나 예외가 발생하지 않는다. File 객체를 통해 해당 경로에 실제로 파일이나 디렉토리가 있는지 확인하려면 exists() 메소드를 호출할 수 있다. 
```java
boolean isExist = file.exists();
```
exists() 메소드의 리턴값이 false라면 createNewFile(), mkdir(), mkdirs() 메소드로 파일 또는 디렉토리를 생성할 수 있다.  

| 리턴 타입 | 메소드             | 설명                                         |
|-----------|--------------------|----------------------------------------------|
| boolean   | createNewFile()    | 파일이 존재하지 않으면 새 파일을 생성함       |
| boolean   | mkdir()            | 디렉토리가 존재하지 않으면 새 디렉토리를 생성함 |
| boolean   | mkdirs()           | 경로상의 모든 디렉토리를 생성함               |
| boolean   | delete()           | 파일이나 디렉토리를 삭제함                    |
파일 또는 디렉토리가 존재하는 경우에는 다음 메소드를 통해 정보를 얻어낼 수 있다.

| 리턴 타입    | 메소드                              | 설명                         |
|--------------|--------------------------------------|------------------------------|
| boolean      | canExecute()                         | 실행할 수 있는 파일인지 여부  |
| boolean      | canRead()                            | 읽을 수 있는 파일인지 여부   |
| boolean      | canWrite()                           | 쓸 수 있는 파일인지 여부     |
| String       | getName()                            | 파일 또는 디렉토리의 이름 반환 |
| String       | getParent()                          | 부모 디렉토리의 경로 반환     |
| File         | getParentFile()                      | 부모 디렉토리를 File 객체로 반환 |
| String       | getPath()                            | 파일 또는 디렉토리의 경로 반환 |
| boolean      | isDirectory()                        | 디렉토리인지 여부             |
| boolean      | isFile()                             | 파일인지 여부                 |
| boolean      | isHidden()                           | 숨김 파일인지 여부            |
| long         | lastModified()                       | 마지막 수정 시간 반환         |
| long         | length()                             | 파일 크기(바이트) 반환        |
| String[]     | list()                               | 디렉토리 내 파일 이름 목록 반환 |
| String[]     | list(FilenameFilter filter)          | 필터에 맞는 파일 이름 목록 반환 |
| File[]       | listFiles()                          | 디렉토리 내 파일 목록 반환     |
| File[]       | listFiles(FilenameFilter filter)     | 필터에 맞는 파일 목록 반환     |

> 다음은 C:\Temp 디렉토리에 Dir 디렉토리와 file1.txt, file2.txt, file3.txt 파일을 생성하고, Temp 디렉토리에 있는 파일 목록을 출력하는 예제이다.

참고 : [FileExample.java](./example/fileIO/FileExample.java)

## FileInputStream
파일로부터 바이트 단위로 읽어들일 때 사용하는 바이트 기반 입력 스트림이다. 바이트 단위로 읽기 때문에 그림, 오디오, 비디오, 텍스트 파일 등 모든 종류의 파일을 읽을 수 있다. 다음은 FileInputStrea을 생성하는 두 가지 방법을 보여준다.

```java
import sec10_exception.example.TryWithResource.FileInputStream;
//1. 
FileInputStream fis = new FileInputStream("C:/Temp/image.gif");

//2.
File file = new File("C:/Temp/image.gif");
FileInputStream fis = new FileInputStream(file);
```
파일 경로를 가지고 FileInputStream을 생성하거나, 만약 읽어야 할 파일이 File 객체로 이미 생성되어 있다면 두 번째 방법으로 좀 더 쉽게 생성할 수 있다. FileInputStream 객체가 생성될 때 파일과 직접 연결되는데, 만약 파일이 존재하지 않으면 FileNotFoundException을 발생시키므로 try-catch 문으로 예외 처리를 해야 한다.  
FileInputStream 은 InputStream의 하위 클래스이기 때문에 사용 방법이 InputStream과 동일하다. read() 메서드를 이용해 -1이 나올때 까지 읽고, close()를 호출해 닫아주면 된다.

```java
FileInputStream fis = new FileInputStream("C:Temp/image.gif");
int readByteNo;
byte[] readBytes = new byte[100];
while((readByteNo = fis.read(readBytes)) != -1) {
    // 읽은 바이트 배열을 처리
}
fis.close();
```

>다음은 FileInputStreamExample.java 소스파일을 읽고 콘솔에 보여주는 예제이다.
 
참고 : [FileInputStreamExample.java](./example/fileIO/FileInputStreamExample.java)

## FileOutputStream
FileOutputStream은 바이트 단위로 데이터를 파일에 저장할 때 사용하는 바이트 기반 출력 스트림이다. FileInputStream과 대부분이 동일하다.

```java
//1. 
FileOutputStream fis = new FileOutputStream("C:/Temp/image.gif");

//2.
File file = new File("C:/Temp/image.gif");
FileOutputStream fis = new FileOutputStream(file);
```

주의할 점은 파일이 이미 존재할 경우, 데이터를 출력하면 파일을 덮어쓰게 되므로, 기존의 파일 내용은 사라지게 된다. 기존의 파일 내용 끝에 데이터를 추가할 경우에는 FileOutputStream 생성자의 두 번째 매개값을 true로 주면 된다.

```java
import java.io.FileOutputStream;

FileOutputStream fos = new FileOutputStream("C:Temp/data.txt", true);
FileOutputStream fos = new FileOutputStream(file, true);
```

FileOutputStream은 OutputStream의 하위 클래스이기 때문에 사용 방법이 OutputStream과 동일하다.

```java
FileOutputStream fos = new FileOutputStream("C:Temp/image.gif");
byte[] data = =...;
fos.write(data);
fos.flush();
fos.close();
```
write() 메소드를 호출한 이후에 flush() 메소드로 출력 버퍼의 잔류데이터를 완전히 출력하도록 하고, close() 메소드를 호출해서 파일을 닫아준다. 

> 다음은 원본 파일을 타겟 파일로 복사하는 예제이다. 원본 파일에서 읽은 바이트를 바로 타겟 파일로 저장하는 것이기 때문에 FileInputStream에서 읽은 데이터를 바로 FileOutputStream으로 저장하면 된다.  

참고 : [FileOutputStreamExample.java](./example/fileIO/FileOutputStreamExample.java)

## FileReader
FileReader 클래스는 텍스트 파일을 프로그램으로 읽어들일 때 사용하는 문자 기반 스트림이다. 문자 단위로 읽기 때문에 텍스트가 아닌 그림, 오디오, 비디오 등의 파일은 읽을 수 없다. 이외는 상술한 Reader와 대부분 동일하다.

```java
// 1.
FileReader fr = new FileReader("C:/Temp/file.txt");

// 2.
File file = new File("C:/Temp/file.txt");
FileReader fr = new FileReader(file);
```

```java
FileReader fr = new FileReader("C:/Temp/file.txt");
int readCharNo;
char[] cbuf = new char[100];
while ((readCharNo=fr.read(cbuf)) != -1) {
    // 읽은 문자 배열(cbuf)를 처리    
}
fr.close();
```

> 다음은 FileReaderExample.java 소스 파일을 읽고 콘솔에 출력하는 예제이다.

참고 : [FileReaderExample.java](./example/fileIO/FileReaderExample.java)

## FileWriter
FileWriter는 텍스트 데이터를 파일에 저장할 때 사용하는 문자 기반 스트림이다. 문자 단위로 저장하기 때문에 텍스트가 아닌 그림, 오디오, 비디오 등의 데이터를 파일로 저장할 수 없다. 대부분 Writer와 유사하다.
```java
// 1.
FileWriter fr = new FileWriter("C:/Temp/file.txt");

// 2.
File file = new File("C:/Temp/file.txt");
FileWriter fr = new FileWriter(file);
```

위와 같이 FileWriter를 생성하게 되면 지정된 파일이 이미 존재하는 경우 그 파일을 덮어쓰게 되므로, 기존의 파일 내용은 사라지게 된다. 기존의 파일 내용 끝에 추가할 경우에는 FileWriter 생성자에 두 번째 매개값으로 true를 주면 된다.

```java
FileWriter fr = new FileWriter("C:/Temp/file.txt", true);
FileWriter fr = new FileWriter(file, true);
```

```java
FileWriter fw = new FileWriter("C:/Temp/file.txt");
String data = "저장할 문자열";
fw.write(data);
fw.flush();
fw.close();
```

> 다음 예제는 문자열 데이터를 해당 경로에 저장한다. \r\n은 캐리지리턴과 라인피드로 Enter키와 동일한 역할을 한다.

참고 : [FileWriterExample.java](./example/fileIO/FileWriterExample.java)

# 보조 스트림
보조 스트림이란 다른 스트림과 연결되어 여러 가지 편리한 기능을 제공해주는 스트림을 말한다. 보조 스트림을 필터 스트림이라고도 하는데, 이는 보조 스트림의 일부가 FilterInputStream, FilterOutputStream의 하위 클래스이기 때문이다. 보조 스트림은 자체적으로 입출력을 수행할 수 없기 때문에 입력 소스와 바로 연결되는 InputStream, FileInputStream, Reader, FileReader 출력 소스와 바로 연결되는 OutputStream, FileOutputStream, Writer, FileWriter 등에 연결해서 입출력을 수행한다. 보조 스트림은 문자 변환, 입출력 성능 향상, 기본 데이터 타입 출력, 객체 입출력 등의 기능을 제공한다.  

<img src=./img/IO_13.png style="display: block; margin: 0 auto;">

보조 스트림을 생성할 때에는 자신이 연결될 스트림을 생성자의 매개값으로 갖는다. 예를 들어 콘솔 입력 스트림을 문자 변환 보조 스트림인 InputStreamReader에 연결하고, 다시 성능향상 보조 스트림인 BufferedReader에 연결하는 코드는 다음과 같다.

<img src=img/IO_14.png style="display: block; margin: 0 auto;">

```java
InputStream is = System.in;
InputStreamReader reader = new InputStreamRedaer(is);
BufferedReader br = new BufferedReader(reader);
```

## 문자 변환 보조 스트림
소스 스트림이 바이트 기반 스트림이면서 입출력 데이터가 문자라면 Reader와 Writer로 변환해서 사용하는 것을 고려해야 한다. 그 이유는 Reader와 Writer는 문자 단위로 입출력하기 때문에 바이트 기반 스트림보다는 편리하고, 문자셋의 종류를 지정할 수 있기 때문에 다양한 문자를 입출력할 수 있다. 

### InputStreamReader
바이트 입력 스트림에 연결되어 문자 입력 스트림인 Reader로 변환시키는 보조 스트림이다.  

<img src=img/IO_15.png style="display: block; margin: 0 auto;">

```java
Reader reader = new InputStreamReader(바이트입력스트림);
```
예를 들어 콘솔 입력을 위한 InputStream을 다음과 같이 Reader 타입으로 변환할 수 있다.
```java
InputStream is = System.in;
Reader reader = new InputStreamReader(is);
```
파일 입력을 위한 FileInputStream도 다음과 같이 Reader 타입으로 변환시킬 수 있다.
```java
FileInputStream fis = new FileInputStream("C:/Temp/file.txt");
Reader reader = new InputStreamReader(fis);
```
FileInputStream에 InputStreamReader를 연결하지 않고 FileReader를 직접 생성할 수도 있다. FileReader는 InputStreamReader의 하위 클래스이다. 이것은 FileReader가 내부적으로 FileInputStream에 InputStreamReader 보조 스트림을 연결한 것이라고 볼 수 있다. 

> 다음은 콘솔에서 입력한 한글을 Reader를 이용해서 읽고, 다시 콘솔로 출력하는 예제이다.

참고 : [InputStreamReaderExample.java](./example/subStream/InputStreamReaderExample.java)

### OutputStreamReader
바이트 입력 스트림에 연결되어 문자 입력 스트림인 Reader로 변환시키는 보조 스트림이다.

<img src=./img/IO_16.png style="display: block; margin: 0 auto;">

```java
Reader reader = new OutputStreamReader(바이트출력스트림);
```
예를 들어 파일 출력을 위한 FileOutputStream을 다음과 같이 Writer 타입으로 변환할 수 있다.
```java
FileOutputStream fos = new FileOutputStream("C:/Temp/file.txt");
Writer writer = new OutputStreamWriter(fos);
```
파일 입력을 위한 FileInputStream도 다음과 같이 Reader 타입으로 변환시킬 수 있다.

FileOutputStream에 OutputStreamWriter를 연결하지 않고 FileWriter를 직접 생성할 수도 있다. FileWriter는 OutputStreamWriter의 하위 클래스이다. 이것은 FileWriter가 내부적으로 FileOutputStream에 OutputStreamWriter의 보조 스트림을 연결한 것이라고 볼 수 있다.

> 다음은 FileOutputStream을 Writer로 변환해서 문자열을 파일에 저장한다.

참고 : [OutputStreamWriterExample.java](./example/subStream/OutputStreamWriterExample.java)

## 성능 향상 보조 스트림
프로그램의 실행 성능은 입출력이 가장 낮은 장치를 따라간다. CPU와 메모리가 아무리 뛰어나도 하드 디스크의 입출력이 늦어지면 프로그램의 실행 성능은 하드 디스크의 처리 속도에 맞춰진다. 실제 사용자의 입력속도와 컴퓨터 혹은 네트워크의 전송/처리 속도는 현격한 차이가 있기 때문에 속도를 높이기 위해 사용자와 컴퓨터 사이에 버퍼를 두어 데이터가 쌓이면 한꺼번에 전송/처리 하도록 함으로써 출력 횟수를 줄여준다.  

<img src=./img/IO_17.png style="display: block; margin: 0 auto;">

보조 스트림 중에는 위와 같이 메모리 버퍼를 제공하여 프로그램의 실행 성능을 향상 시키는 것들이 있다. 바이트 기반 스트림에는 BufferedInputStream, BufferedOutputStream이 있고, 문자 기반 스트림에는 BufferedReader, BufferedWriter가 있다.  

### BufferedInputStream과 BufferedReader
버퍼를 제공해주는 보조 스트림으로 입력 소스로부터 자신의 내부 버퍼 크기만큼 데이터를 미리 읽고 버퍼에 저장해 둔다. 프로그램은 외부의 입력 소스로부터 직접 읽는 대신 버퍼로부터 읽음으로써 읽기 성능이 향상된다.  

각 보조 스트림은 생성자의 매개값으로 준 입력 스트림과 연결되어 BufferedInputStream은 8912byte(8KB), BufferedReader는 8192개의 공간을 갖는 char[] (16KB) 의 기본 사이즈를 가진 내부 버퍼를 갖게된다.
```java
BufferedInputStream bis = new BufferedInputStream(바이트입력스트림);
BufferedReader br = new BufferedReader(문자입력스트림);
```
데이터를 읽어 들이는 방법은 InputStrea 또는 Reader와 동일하다.

> 다음 예제는 BufferedInputStream을 사용했을 때와 사용하지 않았을 때의 성능 차이를 보여주는 예제이다.

참고 : [BufferedInputStreamExample.java](./example/subStream/BufferedInputStreamExample.java)

BufferedReader는 readLine() 메소드를 추가적으로 더 가지고 있는데, 이 메소드를 이용하면 캐리지리턴(\r) 라인피드(\n)로 구분된 행 단위의 문자열을 한꺼번에 읽을 수 있다. 다음 코드는 Enter키를 입력하기 전까지 콘솔에서 입력한 모든 문자열을 한꺼번에 얻는다.

```java
Reader reader = new InputStreamReader(System.in);
BufferedReader br = new BufferedReader();
String inputStr = br.readLine();
```

참고 : [BufferedReaderExample.java](./example/subStream/BufferedReaderExample.java)

### BufferedOutputStream과 BufferedWriter
프로그램에서 전송한 데이터를 내부 버퍼에 쌓아 두었다가 꽉차면 버퍼의 모든 데이터를 한꺼번에 보낸다. 입력 보조스트림과 같이 8192byte, 8192char를 기본값으로 갖는다.

```java
BufferedOutputStream bos = new BufferedOutputStream(바이트출력스트림);
BufferedWriter bw = new BufferedWriter(문자출력스트림);
```
데이터 출력 방법은 기본 출력 스트림과 동일하다. 주의할 점은 버퍼가 가득찼을 때만 출력을 하기 때문에 마지막 자투리 데이터 부분이 목적지로 가지 못하고 버퍼에 남아있을 수 있다. 그래서 마지막 출력을 마친 후에는 flush()를 호출하여 버퍼의 잔류 데이터를 모두 보내도록 해야한다.

> 다음은 파일 복사 예제이다. BufferedOutputStream을 사용했을 때와 사용하지 않았을 때의 프로그램 실행 성능 차이를 보여준다.

참고 : [BufferedOutputStreamExample.java](./example/subStream/BufferedOutputStreamExample.java)

## 기본 타입 입출력 보조 스트림
바이트 스트림은 바이트 단위로 입출력 하기 때문에 자바의 기본 데이터 타입인 boolean, char, short, int, long, float, double 단위로 입출력할 수 없다. 그러나 DataInputStream과 DataOutputStream 보조스트림을 연결하면 기본 데이터 타입으로 입출력 가능하다.  

<img src=./img/IO_18.png style="display: block; margin: 0 auto;">

다음은 DataInputStream과 DataOutputStream이 제공하는 메소드들을 보여준다.

| 리턴 타입 | DataInputStream 메소드     | 리턴 타입 | DataOutputStream 메소드        |
|-----------|----------------------------|-----------|---------------------------------|
| boolean   | readBoolean()              | void      | writeBoolean(boolean v)        |
| byte      | readByte()                 | void      | writeByte(int v)               |
| char      | readChar()                 | void      | writeChar(int v)               |
| short     | readShort()                | void      | writeShort(int v)              |
| int       | readInt()                  | void      | writeInt(int v)                |
| long      | readLong()                 | void      | writeLong(long v)              |
| float     | readFloat()                | void      | writeFloat(float v)            |
| double    | readDouble()               | void      | writeDouble(double v)          |
| String    | readUTF()                  | void      | writeUTF(String s)             |

이 메소드로 출력 시 주의할 점이 있는데, 데이터 타입의 크기가 모두 다르므로 DataOutputStream으로 출력한 데이터를 다시 DataInputStream으로 읽어올 때는 출력한 순서와 동일한 순서로 읽어야 한다. 예를 들어 출력할 때의 순서가 int->boolean->double 이라면 읽을 때의 순서도 int->boolean->double 이어야 한다.

> 다음은 이름, 성적, 순위 순으로 파일에 출력하고 다시 이름, 성적, 순위 순으로 파일로부터 입력받는 예제이다.

참고 : [DataInputOutputStreamExample.java](./example/subStream/DataInputOutputStreamExample.java)

## 프린터 보조 스트림
PrintStream과 PrintWriter는 프린터와 유사하게 출력하는 print(), println() 메소드를 가지고 있는 보조 스트림이다. 콘솔 출력 스트림 System.out이 바로 PrintStream 타입이기 때문에 print(), println() 메소드를 사용할 수 있었다. PrintStream은 바이트 출력 스트림과 연결되고, PrintWriter는 문자 출력 스트림과 연결된다.  

<img src=./img/IO_19.png style="display: block; margin: 0 auto;">

pirntln() 메소드는 출력할 데이터 끝에 개행 문자인 '\n'를 더 추가시켜주기 때문에 콘솔이나 파일에서 줄 바꿈이 일어난다. 그러나 print()는 개행없이 계속해서 문자를 출력시킨다. 두 메소드는 출력할 데이터 타입에 따라 다음과 같이 오버 로딩이 되어 있다.  

| 반환 타입 | print 메서드                | 반환 타입 | println 메서드              |
|-----------|-----------------------------|-----------|-----------------------------|
| void      | print(boolean b)            | void      | println(boolean b)          |
| void      | print(char c)               | void      | println(char c)             |
| void      | print(int i)                | void      | println(int i)              |
| void      | print(long l)               | void      | println(long l)             |
| void      | print(float f)              | void      | println(float f)            |
| void      | print(double d)             | void      | println(double d)           |
| void      | print(char[] s)             | void      | println(char[] s)           |
| void      | print(String s)             | void      | println(String s)           |
| void      | print(Object obj)           | void      | println(Object obj)         |
|       |                             | void      | println()                   |

> 다음 예제는 FileOutputStream에 보조 스트림으로 PrintStream을 연결해서 print()와 println() 메소드로 문자열을 출력시킨다.  

참고 : [PrintStreamExample.java](./example/subStream/PrintStreamExample.java)

PrintStream과 printWriter는 println()과 print() 이외에 printf()도 제공한다. printf() 메소드는 형식화된 문자열(format string)을 출력할 수 있도록 하기 위해 java5부터 추가된 메소드이다. 첫 번째 매개값으로 형식화된 문자열을 지정하고 두 번째 매개값 부터 형식화된 문자열에 들어갈 값을 나열해주면 된다.  

> printf( **String format**, Object...args )  
> 
> **String format** : %[argument_index$] [flags] [width] [.precision] conversation

| 요소                | 설명                           |
| ----------------- | ---------------------------- |
| `%`               | 형식 지정자의 시작                   |
| `argument_index$` | 전달 인자 순서를 지정 (옵션)            |
| `flags`           | 출력 형식을 제어하는 플래그 (정렬, 채움 등)   |
| `width`           | 최소 출력 너비 지정                  |
| `.precision`      | 소수점 자릿수 또는 문자열 자릿수 제한        |
| `conversion`      | 변환 타입 (`d`, `f`, `s`, `c` 등) |

형식화된 문자열에서 %와 conversation(변환문자)은 필수적으로 작성하고 그 이외의 항목은 생략할 수 있다.  
argument_index\$는 적용할 매개값의 순번으로 1\$는 첫 번째 매개값, 2$는 두 번째 매개값을 말한다.  
flags는 빈공간을 채우는 방법이다. 생략되면 왼쪽이 공백으로 채워지고, -는 오른쪽이 공백으로 채워진다. 0은 왼쪽이 0으로 채워진다.  
width는 전체 자릿수이며 .precision은 소수자릿수를 뜻한다.  
변환 문자에는 정수(d), 실수(f), 문자열(s)과 시간과 관련된 문자가 와서 매개값을 해당 타입으로 출력한다.

| 타입   | 형식화된 문자 | 설명                            | 출력형태 예시            |
|--------|----------------|----------------------------------|---------------------------|
| 정수   | %d             | 정수 출력                        | 42                        |
|        | %6d            | 최소 6자리, 오른쪽 정렬          | "    42"                  |
|        | %-6d           | 최소 6자리, 왼쪽 정렬            | "42    "                  |
|        | %06d           | 최소 6자리, 빈자리는 0으로 채움   | "000042"                  |
| 실수   | %f             | 기본 소수점 6자리까지 출력       | 3.141593                  |
|        | %10.2f         | 전체 10자리, 소수점 2자리        | "      3.14"              |
|        | %-10.2f        | 왼쪽 정렬                        | "3.14      "              |
|        | %010.2f        | 0으로 채우며 전체 10자리         | "0000003.14"              |
| 문자열 | %s             | 문자열 그대로 출력               | "Hello"                   |
|        | %6s            | 최소 6자리, 오른쪽 정렬          | " Hello"                  |
|        | %-6s           | 최소 6자리, 왼쪽 정렬            | "Hello "                  |
| 날짜   | %tF            | 날짜 전체 (yyyy-MM-dd)           | 2024-12-31                |
|        | %tY            | 연도 4자리                       | 2024                      |
|        | %ty            | 연도 2자리                       | 24                        |
|        | %tm            | 월 (2자리)                      | 09                        |
|        | %td            | 일 (2자리)                      | 05                        |
|        | %tH            | 시 (24시간 형식)                 | 14                        |
|        | %tI            | 시 (12시간 형식)                 | 02                        |
|        | %tM            | 분                               | 07                        |
|        | %tS            | 초                               | 45                        |
| 특수문자 | \t            | 탭 문자                          | (탭 간격)                 |
|        | \n             | 줄바꿈                           | 다음 줄로 이동            |
|        | %%             | % 기호 출력                      | %                         |

> 다음은 printf() 메소드를 사용해서 다양한 형식으로 출력하는 예제이다.

참고 : [PrintfExample.java](./example/subStream/PrintfExample.java)

## 객체 입출력 보조 스트림
자바는 메모리에 생성된 객체를 파일 또는 네트워크로 출력할 수가 있다. 객체는 문자가 아니기 때문에 바이트 기반 스트림으로 출력해야 한다. 객체를 출력하기 위해서는 객체의 데이터(필드값)를 일렬로 늘어선 연속적인 바이트로 변경해야 하는데, 이것을 객체 직렬화(serialization)이라고 한다. 반대로 파일에 저장되어 있거나 네트워크에서 전송된 객체를 읽을수도 있는데, 입력 스트림으로부터 읽어들인 연속적인 바이트를 객체로 복원하는것을 역직렬화라고 한다.  

### ObjectInputStream, ObjectOutputStream
자바는 객체를 입출력할 수 있는 두 개의 보조 스트림인 ObjectInputStream과 ObjectOutputStream을 제공한다. 각 보조 스트림은 객체를 직렬화, 역직렬화 하는 역할을 한다.  

<img src=./img/IO_20.png style="display: block; margin: 0 auto;">

다른 보조 스트림들과 마찬가지로 연결할 바이트 입출력 스트림을 생성자의 매개값으로 받는다
```java
ObjectInputStream ois = new ObjectInputStream(바이트입력스트림);
ObjectOutputStream oos = new ObjectOutputStream(바이트출력스트림);
```
ObjectOutputStream으로 객체를 직렬화하기 위해서는 writeObject() 메소드를 사용한다.
```java
oos.writeObject();
```
반대로 ObjectInputStream으로 객체를 역직렬화하기 위해서는 ois.readObject()를 사용한다. 역직렬화한 후 객체 원래의 타입으로 변환해야 한다.
```java
객체타입 변수 = (객체타입) ois.readObject();
```

> 다음은 다양한 객체를 파일에 저장하고, 다시 파일로부터 읽어 객체로 복원하는 예제이다. 복수의 객체를 저장할 경우, 출력된 객체 순서와 동일한 순서로 객체를 읽어야 한다.

참고 : [ObjectInputOutputStreamExample.java](./example/subStream/ObjectInputOutputStreamExample.java)

### 직렬화가 가능한 클래스(Serializable)
자바는 Serializable 인터페이스를 구현한 클래스만 직렬화할 수 있도록 제한하고 있다. Serializable 인터페이스는 필드나 메소드가 없는 빈 인터페이스지만, 객체를 직렬화할 때 private 필드를 포함한 모든 필드를 바이트로 변환해도 좋다는 표시 역할을 한다.  
```java
public class XXX implements  Serializable { }
```
객체를 직렬화하면 바이트로 변환되는것은 필드들이고, 생성자 및 메소드는 직렬화에 포함되지 않는다. 따라서 역직렬화할 때에는 필드의 값만 포함된다. 하지만 모든 필드가 직렬화 대상이 되는것은 아니다. 필드 선언에 static 또는 transient가 붙어있을 경우에는 직렬화가 되지 않는다.  

<img src=./img/IO_21.png style="display: block; margin: 0 auto;">

> 다음은 직렬화되는 필드와 그렇지 못한 필드가 어떤것이 있는지 확인해주는 예제이다.

```java
import java.io.FileOutputStream;
import java.io.Serializable;

// ClassA.java
public class ClassA implements Serializable {
    int field1;
    ClassB field2 = new ClassB();
    static int field3;
    transient int field4;
}

//ClassB.java
public class ClassB implements Serializable {
    int field1;
}

//SerializableWriter.java
public class SerializableWriter {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        ClassA classA = new ClassA();
        classA.field1 = 1;
        classA.field2.field1 = 2;
        classA.field3 = 3;
        classA.field4 = 4;
        oos.writeObject(classA);
        oos.flush(); oos.close(); fos.close();
    }
}

//SerializableReader.java
public class SerializableReader {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ClassA v = (ClassA) ois.readObject();
        System.out.println("field1: " + v.field1);
        System.out.println("field2.field1: " + v.field2.field1);
        System.out.println("field3: " + v.field3);
        System.out.println("field4: " + v.field4);
    }
}
```
결과  
![deserializationResult.png](img%2FdeserializationResult.png)

실행 결과를 보면 field1과 field2는 값이 복원되는 것을 알 수 있으나, static 필드인 field3과 transient 필드인 field4는 값이 복원되지 않는다.

### serialVersionUID 필드
직렬화된 객체를 역직렬화할 때는 직렬화했을 때와 같은 클래스를 사용해야 한다. 클래스의 이름이 같더라도 클래스의 내용이 변경되면, 역직렬화는 실패하며 다음과 같은 예외가 발생한다.  

```java
java.io.InvalidClassException: XXX; local class incompatible: stream classdesc
serialVersionUID = -9130799490637378756, local class serialVersionUID = -117472785765765746546465
```
위 예외의 내용은 직렬화할 때와 역직렬화할 때 사용된 클래스의 serialVersionUID가 다르다는 것이다. serialVersionUID는 같은 클래스임을 알려주는 식별자 역할을 하는데, Serializable 인터페이스를 구현한 클래스를 컴파일 하면 자동적으로 serialVersionUID 정적 필드가 추가된다. 문제는 클래스를 재컴파일 하면 serialVersionUID 값이 달라진다는 것이다. 네트워크로 객체를 직렬화 하여 전송하는 경우, 보내는 쪽과 받는 쪽 모두 같은 serialVersionUID를 갖는 클래스를 가지고 있어야 하는데, 한 쪽에서 클래스를 변경해서 재컴파일 하면 다른 serialVersionUID를 가지게 되므로 역직렬화에 실패하게 된다.  

> 다음 예제는 직렬화할 때 사용했던 클래스를 재컴파일한 후 역직렬화 하면 예외가 발생한다는 것을 보여준다. 우선 ClassC 클래스를 다음과 같이 작성한다. ClassC 클래스를 작성할 때 serialVersionUID 필드를 선언하지 않았기 때문에 컴파일 시 자동으로 생성된다.    

```java
// ClassC.java
public class ClassC implements Serializable {
    int field1;
}

// SerialVersionUIDExample1.java
public class SerialVersionUIDExample1 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        ClassC classC = new ClassC();
        classC.field1 = 1;
        oos.writeObject(classC);
        oos.close(); oos.close(); fos.close();
    }
}

// SerialVersionUIDExample2.java
public class SerialVersionUIDExample2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("//C:/Users/Eunchan/IdeaProjects/this_is_java/src/sec15_IOAndNetworking/example/subStream/Object.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ClassC classC = (ClassC) ois.readObject();
        System.out.println("field1: " + classC.field1);
    }
}
```
> SerialVersionUIDExample1을 실행해서 객체를 파일에 저장한 후, SerialVersionUIDExample2를 실행하면 정상적으로 객체가 복원되는것을 확인할 수 있다.

결과  
![deserializationResult2.png](img%2FdeserializationResult2.png)  

이번에는 Classc 클래스에 다음과 같이 field2를 추가하고 저장(컴파일)한다. field2가 추가 되었기 때문에 serialVersionUID 필드값이 변경된다.

```java
// ClassC.java
public class ClassC implements Serializable {
    int field1;
    int field2;
}
```
파일에 저장된 ClassC 객체를 복원하기 위해 SerialVersionUIDExample2를 실행하면 다음과 같은 예외가 발생한다.  
![deserializationResult3.png](img%2FdeserializationResult3.png)

만약 불가피하게 클래스의 수정이 필요하다면 클래스 작성 시 다음과 같이 serialVersionUID를 명시적으로 선언하면 된다.  
```java
public class XXX implements Serializable {
    static final long serialVersionUID = 정수값;
    ...
}
```
클래스에 serialVersionUID가 명시적으로 선언되어 있으면 컴파일 시에 serialVersioUID 필드가 추가되지 않기 때문에 동일한 serialVersionUID값을 유지할 수 있다. 이 값은 개발자가 임의로 줄 수 있지만 가능하다면 클래스마다 다른 값을 갖도록 하는 것이 좋다. 그래서 자바는 <JDK 설치 경로>\bin 폴더에 serialVersionUID 값을 자동으로 생성시켜 주는 serialver.exe 명령어를 제공하고 있다.

### writeObject()와 readObject() 필드
두 클래스가 상속 관계에 있다고 가정해보자. 부모 클래스가 Serializable 인터페이스를 구현하고 있으면 두 자식 클래스는 Serializable 인터페이스를 구현하지 않아도 자식 객체를 직렬화하면 부모필드 및 자식 필드가 모두 직렬화 된다. 하지만 그 반대의 경우에는 부모 필드가 직렬화에서 제외된다. 이 경우 부모 필드를 직렬화하고 싶다면 다음 두 가지 방법 중 하나를 선택해야 한다.  
- 부모 클래스가 Serializable 인터페이스를 구현한다.
- 자식 클래스에서 writeObject()와 readObject() 메소드를 선언해서 부모 객체의 필드를 직접 출력시킨다.  

첫 번째 방법이 제일 좋은 방법이지만, 부모 클래스의 소스를 수정할 수 없는 경우에는 두 번째 방법을 사용해야 한다. writeObject() 메소드는 직렬화될 때 자동으로 호출되고, readObject() 메소드는 역직렬화될 때 자동적으로 호출된다. 
```java
private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeXXX(부모필드);         // 부모 객체의 필드값을 출력
    ...
    out.defaultWriteObject();     // 자식 객체의 필드값을 직렬화
}
```
```java
private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    부모필드 = in.readXXX();        // 부모 객체의 필드값을 읽어옴
    ...
    in.defaultReadObject();        // 자식 객체의 필드값을 역직렬화
}
```
두 메소드를 작성할 때 주의할 점은 접근 제한자가 private이 아니면 자동 호출되지 않기 때문에 반드시 private을 붙여주어야 한다. writeObject()와 readObject() 메소드의 매개값인 ObjectOutputStream과 ObjectInputStream은 다양한 종류의 writeXXX(), readXXX() 메소드를 제공하므로 부모 필드 타입에 맞는 것을 선택해서 사용하면 된다. defaultWrtieObject()와 defaultReadObject()는 자식 클래스에 정의된 필드들을 모두 직렬화하고 역직렬화한다.  

```java
// Parent.java
public class Parent {
    public String field1;
}

// Child.java
public class Child extends Parent implements Serializable {
    public String field2;

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeUTF(field1);
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        field1 = in.readUTF();
        in.defaultReadObject();
    }
}

// NonSerializableParentExample.java
```