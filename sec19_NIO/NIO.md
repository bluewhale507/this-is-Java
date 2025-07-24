# NIO 패키지 기반 입출력 및 네트워킹
# NIO 소개
자바 4부터 새로운 입출력이라는 뜻에서 java.nio 패키지가 포함되었는데, 자바 7로 버전업하면서 자바 IO와 NIO 사이의 일관성 없는 클래스 설계를 바로잡고, 비동기 채널 등의 네트워크 지원을 대폭 강화한 NIO.2 API가 추가되었다. NIO.2는 java.nio2 패키지로 제공되지 않고, java.nio의 하위 패키지(java.nio.channels, java.nio.charset, java.nio.file)에 통합되어 있다. 이것이 자바다 교재는 NIO와 NIO.2를 구별하지 않고 NIO로 부르기로 한다.  

| NIO 패키지                 | 포함되어 있는 내용                            |
|-------------------------|---------------------------------------|
| java.nio                | 다양한 버퍼 클래스                            |
| java.nio.channels       | 파일 채널, TCP 채널 등의 클래스                  |
| java.nio.channels.spi   | java.nio.channels 패키지를 위한 서비스 제공자 클래스 |
| java.nio.charset        | 문자셋, 인코더, 디코더 API                     |
| java.nio.charset.spi    | java.nio.charset 패키지를 위한 서비스 제공자 클래스  |
| java.nio.file           | 파일 및 파일 시스템에 접근하기 위한 클래스              |
| java.nio.file.attribute | 파일 및 파일 시스템의 속성에 접근하기 위한 클래스          |
| java.nio.file.spi       | java.nio.file 패키지를 위한 서비스 제공자 클래스     |

## IO와 NIO의 차이점
IO와 NIO는 데이터를 입출력한다는 목적은 동일하지만, 방식에 있어서 크게 차이가 난다.  

| 구분            | IO              | NIO                 |
|---------------|-----------------|---------------------|
| 입출력 방식        | 스트림 방식          | 채널 방식               |
| 버퍼 방식         | 넌버퍼(non-buffer) | 버퍼(buffer)          |
| 비동기 방식        | 지원 안 함          | 지원                  |
| 블로킹 / 넌블로킹 방식 | 블로킹 방식만 지원      | 블로킹 / 넌블로킹 방식 모두 지원 |

### 스트림 vs. 채널
IO는 스트림 기반이다. 스트림은 입력 스트림과 출력 스트림으로 구분되어 있기 때문에 데이터를 읽기 위해서는 입력 스트림을 생성해야 하고, 데이터를 출력하기 위해서는 출력 스트림을 생성해야 한다. 예를 들어 하나의 파일에서 데이터를 읽고 저장하는 작업을 모두 해야 한다면 `FileInputStream`과 `FileOutputStream`을 별도로 생성해야 한다.  
NIO는 채널 기반이다.채널은 스트림과 달리 양방향으로 입력과 출력이 가능하다. 그렇기 때문에 입력과 출력을 위한 별도의 채널을 만들 필요가 없다. 예를 들어 하나의 파일에서 데이터를 읽고 저장하는 작업을 모두 해야 한다면 `FileChannel` 하나만 생성하면 된다.  

### 넌버퍼 vs. 버퍼
IO에서는 출력 스트림이 1바이틀르 쓰면 입력 스트림이 1받이트를 잃는다. 이런 시스템은 대체로 느리다. 이것보다는 버퍼를 사용해서 복수 개의 바이트를 한꺼번에 입력받고 출력하는 것이 빠른 성능을 낸다. 그래서 IO는 버퍼를 제공해주는 보조 스트림인 `BufferedInputStream`, `BufferedOutputStream`을 연결해서 사용하기도 한다. NIO는 기본적으로 버퍼를 사용해서 입출력을 하기 때문에 IO보다는 입출력 성능이 좋다. 채널은 버퍼에 저장된 데이터를 출력하고, 입력된 데이터를 버퍼에 저장한다.  

<img src=./img/NIO_01.png style="display: block; margin: 0 auto;">

IO는 스트림에서 읽은 데이터를 즉시 처리한다. 그렇기 때문에 스트림으로부터 입력된 전체 데이터를 별도로 저장하지 않으면, 입력된 데이터의 위치를 이동해 가면서 자유롭게 이용할 수 없다. NIO는 읽은 데이터를 무조건 버퍼에 저장하기 때문에 버퍼 내에서 데이터의 위치를 이동해 가면서 필요한 부분만 읽고 쓸 수 있다.  

### 블로킹 vs. 넌블로킹
IO는 블로킹(blocking)된다. 입력 스트림의 read() 메소드를 호출하면 데이터가 입력되기 전까지 스레드는 블로킹(대기상태)가 된다. 마찬가지로 출력 스트림의 write() 메소드를 호출하면 데이터가 출력되기 전까지는 스레드는 블로킹된다. IO스레드가 블로킹되면 다른 일을 할 수 없고 블로킹을 빠져나오기 위해 인터럽트(interrupt)도 할 수 없다. 블로킹을 빠져나오는 유일한 방법은 스트림을 닫는 것이다. NIO는 블로킹과 넌블로킹 특징을 모두 가지고 있다. IO 블로킹과의 차이점은 NIO 블로킹은 스레드를 인터럽트함으로써 빠져나올수가 있다는 것이다. 블로킹의 반대 개념이 넌블로킹인데, 입출력 작업 시 스레드가 블로킹되지 않는 것을 말한다. NIO의 넌블로킹은 입출력 작업 준비가 완료된 채널만 선택해서 작업 스레드가 처리하기 때문에 작업 스레드가 블로킹되지 않는다. 여기서 작업 준비가 완료되었따는 뜻은 지금 바로 읽고 쓸 수 있는 상태를 말한다. NIO 넌블로킹의 핵심 객체는 멀티플렉서(multiplexor)인 셀렉터(selector)이다. 셀럭터는 복수개의 채널 중에서 준비 완료된 채널을 선택하는 방법을 제공해준다.  

## IO와 NIO의 선택
네트워크 프로그램을 개발할 때 IO와 NIO 선택 기준에 대해 생각해보자. NIO는 불특정 다수의 클라이언트 연결 또는 멀티 파일들을 넌블로킹이나 비동기로 처리할 수 있기 때문에 과도한 운영체제의 버퍼(다이렉트 버퍼)를 이용한 입출력이 가능하기 때문에 입출력 성능이 향상된다.  
NIO는 연결 클라이언트 수가 많고, 하나의 입출력 처리 작업이 오래 걸리지 않는 경우에 사용하는 것이 좋다. 스레드에서 입출력 처리가 오래 걸린다면 대기하는 작업의 수가 늘어나기 때문에 제한된 스레드로 처리하는 것이 오히려 불리할 수 있다. 대용량 데이터를 처리할 경우에는 IO가 더 유리한데, NIO는 버퍼의 할당 크기도 문제가 되고, 모든 입출력 작업에 버퍼를 무조건 사용해야 하므로 받은 즉시 처리하는 IO보다는 좀 더 복잡하다. 연결 클라이언트 수가 적고, 전송되는 데이터가 대용량이면서 순차적으로 처리될 필요성이 있을 경우에는 IO로 서버를 구현하는 것이 좋다.  

# 파일과 디렉토리
IO는 파일의 속성 정보를 읽기 위해 File 클래스만 제공하지만, NIO는 좀 더 다양한 파일의 속성 정보를 제공해주는 클래스와 인터페이스를 `java.nio.fil`, `java.nio.file.attribute` 패키지에서 제공하고 있다.  

## 경로정의(Path)
NIO에서 제일 먼저 살펴봐야 할 API는 `java.nio.file.Path` 인터페이스이다. Path는 IO의 java.io.File 클래스에 대응되는 NIO 인터페이스이다. NIO의 API에서 파이르이 경로를 지정하기 위해 Path를 사용한다. Path 구현 객체를 얻기 위해서는 `java.nio.Paths` 클래스의 정적 메소드인 get() 메소드를 호출하면 된다.  

```java
Path path = Paths.get(String first, String... more);
Path path = Paths.get(URI uri);
```

get() 메소드의 매개값은 파일의 경로인데, 문자열로 지정할 수도 있고, URI 객체로 지정할 수도 있다. 문자열로 지정할 경우 전체 경로를 한꺼번에 지정해도 좋고, 상위 디렉토리와 하위 디렉토리를 나열해서 지정해도 좋다. 다음은 "C:\Temp\dir\file.txt" 경로를 이용해서 Path 객체를 얻는 방법을 보여준다.  

```java
Path path = Paths.get("C:\Temp\dir\file.txt");
Path path = Paths.get("C:\Temp\dir","file.txt");
Path path = Paths.get("C:","Temp","dir","file.txt");
```

파일의 경로는 절대경로와 상대경로 모두 사용가능하다.  

| 리턴 타입          | 메소드(매개변수)             | 설명                                                                                                                                |
|----------------|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| int            | compareTo(Path other) | 파일 경로가 동일하면 0을 리턴,<br/>상위 경로면 음수,<br/>하위 경로면 양수를 리턴,<br/>음수와 양수 값의 차이나는 문자열의 수                                                    |
| Path           | getFileName()         | 부모 경로를 제외한 파일 또는 디렉토리 이름만 가진 Path 리턴                                                                                              |
| FileSystem     | getFileSystem()       | FileSystem 객체 리턴                                                                                                                  |
| Path           | getName(int index)    | C:\Temp\dir\file.txt일 경우<br/>index가 0이면 "Temp"의 Path 객체 리턴<br/>index가 1이면 "dir"의 Path 객체 리턴<br/>index가 2이면 "file.txt"의 Path 객체 리턴 |
| int            | getNameCount()        | 중첩 경로 수, C:\Temp\dir\file.txt일 경우 3을 리턴                                                                                           |
| Path           | getParent()           | 바로 위 부모 폴더의 Path 리턴                                                                                                               |
| Path           | getRoot()             | 루트 디렉토리의 Path 리턴                                                                                                                  |
| Iterator<Path> | iterator()            | 경로에 있는 모든 디렉토리와 파일을 Path 객체로 생성하고 반복자를 리턴                                                                                         |
| Path           | normalize()           | 상대 경로로 표기할 때 불필요한 요소를 제거<br/>C:\Temp\dir1\..\dir2\file.txt -> C:\Temp\dir2\file.txt                                               |
| WatchKey       | register()            | WatchService를 등록(와치 서비스에서 설명함)                                                                                                    |
| File           | toFile()              | java.io.File 객체로 리턴                                                                                                               |
| String         | toString()            | 파일 경로를 문자열로 리턴                                                                                                                    |
| URI            | toUri()               | 파일 경로를 URI 객체로 리턴                                                                                                                 |

> 다음 예제는 상대 경로를 이용해서 소스 파일에 대한 Path 객체를 얻고, 파일명, 부모 디렉토리명, 중첩 경로 수, 경로상에 있는 모든 디렉토리를 출력한다.  

```java
public class PathExample {
    public static void main(String[] args) {
        Path path = Paths.get("src/sec02/exam01_path/PathExample.java");
        System.out.println("[파일명]" + path.getFileName());
        System.out.println("[부모 디렉토리명]: " + path.getParent().getFileName());
        System.out.println("중첩 경로수: " + path.getNameCount());

        System.out.println();
        for(int i=0; i<path.getNameCount(); i++) {
            System.out.println(path.getName(i));
        }

        System.out.println();
        Iterator<Path> iterator = path.iterator();
        while(iterator.hasNext()) {
            Path temp = iterator.next();
            System.out.println(temp.getFileName());
        }
    }
}
```

## 파일 시스템 정보 (FileSystem)
운영체제의 파일 시스템은 FileSystem 인터페이스를 통해 접근할 수 있다. FileSystem 구현 객체는 FileSystems의 정적 메소드인 getDefault()로 얻을 수 있다.  

| 리턴 타입             | 메소드(매개 변수)           | 설명                            |
|-------------------|----------------------|-------------------------------|
| Iterable<FileStor> | getFielStores()      | 드라이버 정보를 가진 FileStore 객체들을 리턴 |
| Iterable<Path>    | getRootDirectories() | 루트 디렉토리 정보를 가진 Path 객체들을 리턴   |
| String            | getSeparator()       | 디록토리 구분자 리턴                   |

FileStor는 드라이버를 표현한 객체로 다음과 같은 메소드를 제공한다.  

| 리턴 타입   | 메소드(매개 변수)            | 설명                                         |
|---------|-----------------------|--------------------------------------------|
| long    | getTotalSpace()       | 드라이버 전체 공간 크기(단위: 바이트) 리턴                  |
| long    | getUnallocatedSpace() | 할당되지 않은 공간 크기(단위: 바이트) 리턴                  |
| long    | getUsableSpace()      | 사용 가능한 공간 크기, getUnallocatedSpace()와 동일한 값 |
| boolean | isReadOnly()          | 읽기 전용 여부                                   |
| String  | name()                | 드라이버명 리턴                                   |
| String  | type()                | 파일 시스템 종류                                  |

```java
/* FileSystemExample.java - 파일 시스템 정보 얻기 */
public class FileSystemExample{
    public static void main(String[] args) throws Exception {
        FileSystem fileSystem = FileSystems.getDefault();
        for(FileStore store : fileSystem.getFileStores()) {
            System.out.println("드라이버명: " + store.name());
            System.out.println("파일시스템: " + store.type());
            System.out.println("전체 공간: " + store.getTotalSpace() + " 바이트");
            System.out.println("사용 중인 공간: " + (store.getTotalSpace() - store.getUnallocatedSpace() + " 바이트"));
            System.out.println();
        }

        System.out.println("파일 구분자: " + fileSystem.getSeparator());
        System.out.println();

        for(Path path : fileSystem.getRootDirectories()) {
            System.out.println(path.toString());
        }
    }
}
```

<img src=./img/NIO_02.png style="display: block;">

## 파일 속성 읽기 및 파일, 디렉토리 생성/삭제
`java.nio.file.Files` 클래스는 파일과 디렉토리의 생성 및 삭제, 그리고 이들의 속성을 읽는 메소드를 제공하고 있다. 여기서 속성이란 파일이나 디렉토리가 숨김인지, 디렉토리인지, 크기가 어떻게 되는지, 소유자가 누구인지에 대한 정보를 말한다. 다음은 `java.nio.file.Files` 클래스가 제공하는 정적 메소드들이다.  

| 리턴 타입            | 메소드(매개 변수)                                         | 설명                                                     |
| ---------------- | -------------------------------------------------- | ------------------------------------------------------ |
| `long` 또는 `Path` | `copy(Path source, Path target, CopyOption...)`    | 파일이나 디렉토리를 복사함. 옵션으로 덮어쓰기 설정 가능 (`REPLACE_EXISTING` 등) |
| `Path`           | `createDirectories(Path dir, FileAttribute<?>...)` | 지정된 경로에 디렉토리를 생성함. 상위 디렉토리까지 함께 생성함                    |
| `Path`           | `createDirectory(Path dir, FileAttribute<?>...)`   | 디렉토리 1개만 생성함. 상위 디렉토리가 없으면 예외 발생                       |
| `Path`           | `createFile(Path path, FileAttribute<?>...)`       | 파일을 새로 생성함. 이미 존재하면 예외 발생                              |
| `boolean`        | `deleteIfExists(Path path)`                        | 파일 또는 디렉토리를 삭제함. 존재하지 않아도 예외 발생하지 않음                   |
| `void`           | `delete(Path path)`                                | 파일 또는 디렉토리를 삭제함. 존재하지 않으면 예외 발생                        |
| `boolean`        | `exists(Path path, LinkOption...)`                 | 파일 또는 디렉토리가 존재하는지 여부 반환                                |
| `boolean`        | `notExists(Path path, LinkOption...)`              | 파일 또는 디렉토리가 존재하지 않는지 여부 반환                             |
| `Path`           | `move(Path source, Path target, CopyOption...)`    | 파일이나 디렉토리를 이동하거나 이름 변경함                                |
| `long`           | `size(Path path)`                                  | 파일의 크기를 바이트 단위로 반환                                     |
| `boolean`        | `isDirectory(Path path, LinkOption...)`            | 경로가 디렉토리인지 여부 확인                                       |
| `boolean`        | `isRegularFile(Path path, LinkOption...)`          | 일반 파일인지 여부 확인                                          |
| `boolean`        | `isHidden(Path path)`                              | 파일이나 디렉토리가 숨김 파일인지 확인                                  |
| `UserPrincipal`  | `getOwner(Path path, LinkOption...)`               | 파일 또는 디렉토리의 소유자 정보 반환                                  |
| `FileTime`       | `getLastModifiedTime(Path path, LinkOption...)`    | 마지막 수정 시간을 반환                                          |
| `Stream<Path>`   | `list(Path dir)`                                   | 디렉토리 내부의 파일 목록을 스트림으로 반환                               |
| `byte[]`         | `readAllBytes(Path path)`                          | 파일의 전체 내용을 바이트 배열로 읽어옴                                 |
| `List<String>`   | `readAllLines(Path path, Charset cs)`              | 파일을 한 줄씩 읽어 리스트로 반환 (문자셋 지정 가능)                        |
| `Path`           | `write(Path path, byte[] bytes, OpenOption...)`    | 바이트 배열을 파일에 기록함 (옵션 지정 가능)                             |

> 다음 예제는 파일을 읽고 출력한다.  

```java
/* FileExample.java - 파일 속성 얻기 */
public class FileExample {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/sec02/exam03_file_directory/FileExample.java");
        System.out.println("디렉토리 여부: " + Files.isDirectory(path));
        System.out.println("파일 여부: " + Files.isRegularFile(path));
        System.out.println("마지막 수정 시간: " + Files.getLastModifiedTime(path));
        System.out.println("파일 크기: " + Files.size(path));
        System.out.println("소유자: " + Files.getOwner(path).getName());
        System.out.println("숨김 파일 여부: " + Files.isHidden(path));
        System.out.println("읽기 가능 여부: " + Files.isReadable(path));
        System.out.println("쓰기 가능 여부: " + Files.isWritable(path));
    }
}
```

> 다음 예제는 디렉토리와 파일을 생성하고, 디렉토리의 내용을 출력한다.  

```java
public class DirectoryExample {
    public static void main(String[] args) throws Exception {
        Path path1 = Paths.get("C:/Temp/dir/subdir");
        Path path2 = Paths.get("C:/Temp/file.txt");

        if(Files.notExists(path1)) {
            Files.createDirectories(path1);
        }

        if(Files.notExists(path2)) {
            Files.createFile(path2);
        }

        Path path3 = Paths.get("C:/Temp");
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path3);
        for(Path path : directoryStream) {
            if(Files.isDirectory(path)) {
                System.out.println("[디렉토리] " + path.getFileName());
            } else {
                System.out.println("[파일] " + path.getFileName() + " (크기:" + Files.size(path) + ")");
            }
        }
    }
}
```

## 와치 서비스
와치 서비스(WatchService)는 자바 7에서 처음 소개된 것으로 디렉퇼 내부에서 파일 생성, 삭제, 수정 드으이 내용 변화를 감시하는데 사용된다. 흔하게 볼 수 있는 와치 서비스의 적용 예는 에디터에서 파일으 ㄹ편집하고 있을 때, 에디터 바깥에서 파일 내용을 수정하게 되면 파일 내용이 변경됐으니 파일을 다시 불러올 것이닞 묻는 대화상자를 띄우는 것이다. 와치 서비스는 일반적으로 파일 변경 통지 메커니즘으로 알려져 있다. WatchService를 생성하려면 다음과 같이 FileSystem의 new WatchService() 메소드를 호출하면 된다.  

```java
WatchService watchService = FileSystems.getDefault().newWatchService();
```

WatchService를 생성했다면 감시가 필요한 디렉토리의 Path 객체에서 register() 메소드로 WatchService를 등록하면 된다. 이때 어떤 변화(생성, 삭제, 수정)를 감시할 것인지를 Standard WatchEventKinds 상수로 지정할 수 있다. 다음은 생성, 수정, 삭제를 감시하도록 WatchService를 등록한다.  

```java
path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.ENTRY_DELETE);
```

디렉토리(Path)에 WatchService를 등록한 순간부터 디렉토리 내부에서 변경이 발생되면 와치 이벤트(WatchEvent)가 발생하고, WatchService는 해당 이벤트 정보를 가진 와치키(WatchKey)를 생성하여 큐(Queue)에 넣어준다. 프로그램은 무한 루프를 돌면서 WatchService의 take() 메소드를 호출하여 WatchKey가 큐에 들어올 때까지 대기하고 있따가 WatchKey가 큐에 들어오면 WatchKey를 얻어 처리하면 된다.  

```java
while(true) {
    WatchKey watchKey = watchService.take(); //큐에 WatchKey가 들어올 때까지 대기
}
```

WatchKey를 얻고나서 해야 할 일은 pollEvents() 메소드를 호출해서 WatchEvent 리스트를 얻어내는 것이다. 한 개의 WatchEvent가 아니라 List<WatchEvent<?>>로 리턴하는 이유는 여러 개의 파일이 동시에 삭제, 수정, 생성될 수 있기 때문이다. 참고로 WatchEvent는 파일당 하나씩 발생한다.  

```java
List<WatchEvent<?>> list = watchKey.pollEvents();
```

프로그램은 WatchEvent 리스트에서 WatchEvent를 하나씩 꺼내어 이벤트의 종류와 Path 객체를 얻어낸 다음 적절히 처리하면 된다.

```java
for(WatchEvent watchEvent :list){
    // 이벤트 종류 얻기
    Kind kind = watchEvent.kind();
    // 감지된 Path 얻기
    Path path = (Path) watchEvent.context();
    // 이벤트 종류별로 처리
    if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
        // 생성되었을 경우, 실행할 코드
    } else if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
        // 삭제되었을 경우, 실행할 코드
    } else if(kind == StandardWatchEventKinds.ENTRY_MODIFY) {
        // 변경되었을 경우, 실행할 코드    
    } else if(kind == StandardWatchEventKinds.OVERFLOW) {}
}
```

OVERFLOW 이벤트는 운영체제에서 이벤트가 소실됐거나, 버려진 경우에 발생하므로 별도의 처리 코드가 필요없다. 따라서 CREATE, DELETE, MODIFY 이벤트만 처리하면 된다. 한 번 사용된 WatchKey는 reset() 메소드로 초기화해야 하는데, 새로운 WatchEvent가 발생하면 큐에 다시 들어가기 때문이다. 초기화에 성공하면 reset() 메소드는 true를 리턴하지마, 감시하는 디렉토리가 삭제되었거나 키가 더 이상 유효하지 않을 경우에는 false를 리턴한다. WatchKey가 더 이상 유효하지 않게되면 무한 루프를 빠져나와 WatchService의 close() 메소드를 호출하고 종료하면 된다.  

```java
while(true) {
    WatchKey watchKey = watchService.take();
    List<SatchEvent<?>> list = watchKey.pollEvents();
    for(WatchEvent watchEvent : lsit) {
        ...
    }
    boolean valid = watchKey.reset();
    if(!valid) { break; }
}
watchService.close();
```

> 다음 예제는 C:\Temp 디렉토리를 감시 디렉토리로 설정했다. 실행 후, C:\Temp\dir 디렉토리와 C:\Temp\file.txt 파일을 생성하고, file.txt 파일 내용을 수정한 다음 저장하였다. 그리고 dir, file.txt를 동시에 삭제했다. 이 모든 행위들이 TextArea에 기록되는 것을 볼 수 있다.  

```java
public class WatchServiceExample extends {
    class WatchServiceThread extends Thread {
        @Override
        public void run() {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path directory = Paths.get("C:/Temp");
                directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                                             StandardWatchEventKinds.ENTRY_DELETE,
                                             StandardWatchEventKinds.ENTRY_MODIFY);

                while(true) {
                    WatchKey watchKey = watchService.take();   // 블로킹(WatchKey가 큐에 들어올 때까지)
                    List<WatchEvent<?>> list = watchKey.pollEvents(); // WatchEvent 목록 얻기
                    for(WatchEvent watchEvent : list) {
                        // 이벤트 종류 얻기
                        Kind kind = watchEvent.kind();
                        // 감지된 Path 얻기
                        Path path = (Path)watchEvent.context();
                        if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            Platform.runLater(()->textArea.appendText("            -> " + path.getFileName() + "\n"));
                        } else if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            Platform.runLater(()->textArea.appendText("            -> " + path.getFileName() + "\n"));
                        } else if(kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Platform.runLater(()->textArea.appendText("           -> " + path.getFileName() + "\n"));
                        } else if(kind == StandardWatchEventKinds.OVERFLOW) {
                        }
                    }
                    boolean valid = watchKey.reset();
                    if(!valid) { break; }
                }
            } catch (Exception e) {}
        }
    }

    TextArea textArea;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefSize(500, 300);

        textArea = new TextArea();
        textArea.setEditable(false);
        root.setCenter(textArea);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WatchServiceExample");
        primaryStage.show();

        WatchServiceThread wst = new WatchServiceThread();
        wst.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

# 버퍼
NIO에서는 데이터를 입출력하기 위해 항상 버퍼를 사용해야 한다. 버퍼는 읽고 쓰기가 가능한 메모리 배열이다. 버퍼를 이해하고 잘 사용할 수 있어야 NIO에서 제공하는 API를 올바르게 활용할 수 있다.  

## Buffer 종류
Buffer는 저장되는 데이터 타입에 따라 분류할 수도 있고, 어떤 메모리를 사용하느냐에 따라 다이렉트와 넌다이렉트로 분류할 수도 있다.  

### 데이터 타입에 따른 버퍼

<img src=./img/NIO_03.png style="display: block; margin: 0 auto;">

버퍼 클래스으 ㅣ이름을 보면 어떤 데이터가 저장되는 버퍼인지 쉽게 알 수 있다. ByteBuffer는 byte 데이터가 저장되고, CharBuffer, ShortBuffer, IntBuffer, LongBuffer, FloatBuffer, DoubleBuffer는 각각 char, shor, int, long, float, double 데이터가 저장되는 버퍼이다. MappedByteBuffer는 ByteBuffer의 하위 클래스로 파일의 내용에 덤하게 접근하기 위해서 파일의 내용을 메모리와 매핑시킨다.  

### 넌다이렉트와 다이렉트 버퍼
버퍼가 사용하는 메모리의 위치에 따라서 넌다이렉트 버퍼와 다이렉트 버퍼로 분류된다. 넌 다이렉트 버퍼는 JVM이 관리하는 힙 메모리 공간을 이용하고, 다이렉트 버퍼는 운영체제가 관리하는 메모리 공간을 이용한다.  

| 구분          | 넌다이렉트버퍼    | 다이렉트 버퍼              |
|-------------|------------|----------------------|
| 사용하는 메모리 공간 | JVM의 힙 메모리 | 운영체제의 메모리            |
| 버퍼 생성 시간    | 버퍼 생성이 빠르다. | 버퍼 생성이 느리다.          |
| 버퍼의 크기      | 작다.        | 크다.(큰 데이터를 처리할 때 유리) |
| 입출력 성능      | 낮다.        | 높다.(입출력이 빈번할 때 유리)   |

넌 다이렉트 버퍼는 JVM 힙 메모리를 사용하므로 버퍼 생성 시간이 빠르지만, 다이렉트 버퍼는 운영체제의 메모리를 할당받기 위해 운영체제의 navive C 함수를 호출해야 하고 여러가지 잡다한 처리를 해야 하므로 상대적으로 버퍼 생성이 느리다. 그렇기 때문에 다이렉트 버퍼는 자주 생성하기 보다는 한번 생성해 놓고 재사용하는 것이 적합하다. 넌다이렉트 버퍼는 JVM의 제한된 힙 메모리를 사용하므로 버퍼의 크기를 크게 잡을 수가 없고, 반면 다이렉트 버퍼는 운영체제가 관리하는 메모리를 사용하므로 운영체제가 허용하는 범위 내에서 대용량 버퍼를 생성시킬 수 있다. 넌다이렉트 버퍼는 입출력을 하기 위해 임시 다이렉트 버퍼를 생성하고 넌다이렉트 버퍼에 있는 내용을 임시 다이렉트 버퍼에 복사한다. 그리고 나서 임시 다이렉트 버퍼를 사용해서 운영체제의 native I/O 기능을 수행한다. 그렇기 때문에 직접 다이렉트 버퍼를 사용하는 것보다는 입출력 성능이 낮다.  

> 다음 예제는 컴퓨터에서 200MB 크기의 버퍼를 생성하려고 했는데, 다이렉트 버퍼는 정상적으로 생성되는 반면, 넌다이렉트 버퍼는 OutOfMemoryError가 발생한다. 컴퓨터의 성능과 현재 상태에 따라서 버퍼의 크기가 유동적이기 때문에 크기를 조정하면서 테스트해보길 바란다. 예제에서 사용된 버퍼는 ByteBuffer이고, allocate() 메소드는 넌다이렉트 버퍼를 생성하는 메소드이다. 그리고 allocateDirect() 메소드는 다이렉트 버퍼를 생성하는 메소드이다.

```java
/* BufferSizeExample.java - 넌다이렉트와 다이렉트 크기 비교 */
public class BufferSizeExample {
    public static void main(String[] args) {
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(200 * 1024 * 1024);
        System.out.println("다이렉트 버퍼가 생성되었습니다.");

        ByteBuffer nonDirectBuffer = ByteBuffer.allocate(200 * 1024 * 1024);
        System.out.println("넌다이렉트 버퍼가 생성되었습니다.");
    }
}
```

> 다음 예제는 넌다이렉트 버퍼와 다이렉트 버퍼의 성능 테스트 결과를 출력한다. 이미지 파일을 100번 복사하는데 걸린 시간을 측정해본 결과 다이렉트 버퍼가 훨씬 빠른 것을 볼 수 있다.  

```java
/* PerformanceExample.java */
public class PerformanceExample {
    public static void main(String[] args) throws Exception {
        Path from = Paths.get("src/sec03/exam01_direct_buffer/house.jpg");
        Path to1 = Paths.get("src/sec03/exam01_direct_buffer/house2.jpg");
        Path to2 = Paths.get("src/sec03/exam01_direct_buffer/house3.jpg");

        long size = Files.size(from);

        FileChannel fileChannel_from = FileChannel.open(from);
        FileChannel fileChannel_to1 = FileChannel.open(to1, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));
        FileChannel fileChannel_to2 = FileChannel.open(to2, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE));

        ByteBuffer nonDirectBuffer = ByteBuffer.allocate((int) size);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect((int)size);

        long start, end;

        start = System.nanoTime();
        for(int i=0; i<100; i++) {
            fileChannel_from.read(nonDirectBuffer);
            nonDirectBuffer.flip();
            fileChannel_to1.write(nonDirectBuffer);
            nonDirectBuffer.clear();
        }
        end = System.nanoTime();
        System.out.println("넌다이렉트:\t" + (end-start) + " ns");

        fileChannel_from.position(0);

        start = System.nanoTime();
        for(int i=0; i<100; i++) {
            fileChannel_from.read(directBuffer);
            directBuffer.flip();
            fileChannel_to2.write(directBuffer);
            directBuffer.clear();
        }
        end = System.nanoTime();
        System.out.println("다이렉트:\t" + (end-start) + " ns");

        fileChannel_from.close();
        fileChannel_to1.close();
        fileChannel_to2.close();
    }
}
```

다이렉트 버퍼는 Channel을 사용해서 버퍼의 데이터를 읽고 저장할 경우에만 운영체제의 native I/O를 수행한다. 만약 채널을 사용하지 않고 ByteBuffer의 get()/put() 메소드를 사용해서 버퍼의 데이터를 읽고, 저장한다면 이 작업은 내부적으로 JNI(Java Native Interface - 자바코드에서 C 함수를 호출할 수 있도록 해주는 API)를 호출해서 native I/O를 수행하기 때문에 JNI 호출이라는 오버 헤드가 추가된다. 그렇기 때문에 오히려 넌다이렉트 버퍼의 get()/put() 메소드 성능이 더 좋게 나올 수 있다.  

## Buffer 생성
각 데이터 타입별로 넌다이렉트 버퍼를 생성하기 위해서는 각 Buffer 클래스의 allocate()와 wrap() 메소드를 호출하면 되고, 다이렉트 버퍼는 ByteBuffer의 allocateDirect() 메소드를 호출하면 된다.  

### allocate() 메소드
allocate() 메소드는 JVM 힙 메모리에 넌다이렉트 버퍼를 생성한다. 매개값은 해당 데이터 타입의 저장 개수를 말한다.  

| 버퍼 타입          | 생성 메서드                                | 설명            |
| -------------- | ------------------------------------- | ------------- |
| `ByteBuffer`   | `ByteBuffer.allocate(int capacity)`   | 바이트 버퍼 생성     |
| `CharBuffer`   | `CharBuffer.allocate(int capacity)`   | 문자 버퍼 생성      |
| `ShortBuffer`  | `ShortBuffer.allocate(int capacity)`  | short형 버퍼 생성  |
| `IntBuffer`    | `IntBuffer.allocate(int capacity)`    | int형 버퍼 생성    |
| `LongBuffer`   | `LongBuffer.allocate(int capacity)`   | long형 버퍼 생성   |
| `FloatBuffer`  | `FloatBuffer.allocate(int capacity)`  | float형 버퍼 생성  |
| `DoubleBuffer` | `DoubleBuffer.allocate(int capacity)` | double형 버퍼 생성 |

다음은 최대 100개의 바이트를 저장하는 ByteBuffer를 생성하고, 최대 100개의 문자를 저장하는 CharBuffer를 생성하는 코드이다.  

```java
ByteBuffer byteBuffer = ByteBuffer.allocate(100);
CharBuffer charBuffer = CharBuffer.allocate(100);
```

### wrap() 메소드
각 타입별 Buffer 클래스는 모두 wrap() 메서드를 가지고 있는데, wrap() 메소드는 이미 생성되어 있는 자바 배열을 래핑해서 Buffer객체를 생성한다. 자바 배열은 JVM 힙 메모리에 생성되므로 wrap()은 넌다이렉트 버퍼를 생성한다. 다음은 길이가 100인 byte[]를 시용해서 BYteBuffer를 생성하고, 길이가 100인 char[]를 이용해서 CharBuffer를 생성한다.  

```java
byte[] byteArray = new byte[100];
ByteBuffer byteBuffer = ByteBUffer.wrap(byteArray);
```

배열의 모든 데이터가 아니라 일부 데이터만 가지고 Buffer 객체를 생성할 수도 있다. 이 경우 시작 인덱스와 길이를 추가적으로 지정하면 된다. 다음은 0 인덱스부터 50개만 버퍼로 생성한다.  
```java
byte[] byteArray = new byte[100];
ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 0, 50);
```

CharBuffer는 추가적으로 CharSequence 타입의 매개값을 갖는 wrap() 메소드도 제공한다. String이 CharSequence 인터페이스를 구현했기 때문에 매개값으로 문자열을 제공해서 다음과 같이 CharBuffer를 생성할 수도 있다.  

```java
CharBuffer charBuffer = CharBuffer.wrap("NIO 입출력은 버퍼를 이용합니다.");
```

### allocateDirect() 메소드
ByteBuffer의 allocateDirect() 메소드는 JVM 힙 메모리 바깥쪽, 즉 운영체제가 관리하는 메모리에 다이렉트 버퍼를 생성한다. 이 메소드는 각 타입별 Buffer 클래스에는 없고, ByteBuffer에서만 제공된다. 각 타입별로 다이렉트 버퍼를 생성하고 싶다면 우선 ByteBuffer의 allocateDirect() 메소드로 버퍼를 생성한 다음 ByteBuffer의 asCharBuffer(), asShortBuffer, asIntBuffer(), asLongBuffer(), asFLoatBuffer(), asDoubleBuffer() 메소드를 이용해서 해당 타입별 Buffer를 얻으면 된다.  
다음은 100개의 바이트(byte)를 저장하는 다이렉트 ByteBuffer와 50개의 문자(char)를 저장하는 다이렉트 CharBUffer, 25개의 정수(int)를 저장하는 다이렉트 IntBuffer를 생성하는 코드이다. char는 2바이트 크기를 가지고, int는 4바이트 크기를 가지기 때문에 초기 다이렉트 ByteBuffer 생성 크기에 따라 저장 용량이 결정된다.  

```java
// 100개의 byte값 저장
ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
// 50개의 char값 저장
CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
// 25개의 int값 저장
CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asIntBuffer();
```

```java
/* DirectBufferCapacityExample.java - 다이렉트 버퍼 저장 용량 확인 */
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class DirectBufferCapacityExample {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        System.out.println("저장용량: " + byteBuffer.capacity() + " 바이트");


        CharBuffer charBuffer = ByteBuffer.allocateDirect(100).asCharBuffer();
        System.out.println("저장용량: " + charBuffer.capacity() + " 문자");

        IntBuffer intBuffer = ByteBuffer.allocateDirect(100).asIntBuffer();
        System.out.println("저장용량: " + intBuffer.capacity() + " 정수");
    }
}
```

## byte 해석순서(ByteOrder)
데이터를 처리할 때 바이트 처리 순서는 운영체제마다 차이가 있다. 이러한 차이는 데이터를 다른 운영체제로 보내거나 받을 때 영향을 미치기 때문에 데이터를 다루는 버퍼도 이를 고려해야 한다. 앞쪽 바이트부터 먼저 처리하는 것을 Big endian이라고 하고, 뒤쪽 바이트부터 먼저처리하는 것을 little endian이라고 한다.  

<img src=./img/NIO_04.png style="display: block; margin: 0 auto;">

Little endian으로 동작하는 운영체제에서 만든 데이터 파일을 Big endian으로 동작하는 운영체제에서 읽는다면 ByteOrder 클래스로 데이터 순서를 맞춰야 한다. ByteOrder 클래스의 nativeOrder() 메소드는 현재 동작하고 있는 운영체제가 Big endian인지 Little endian인지 알려준다. JVM도 일종의 독립된 운영체제이기 때문에 이런 문제를 취급하는데, JRE가 설치된 어떤 환경이든 JVM은 무조건 Big endian으로 동작하도록 되어 있다.  

> 다음 예제는 현재 컴퓨터의 운영체제 종류와 바이트를 해석하는 순서에 대해 출력한다.  

```java
/* ComputrByteOrderExample.java - 컴퓨터의 기본 바이트 순서 */
public class ComputerByteOrderExample {
    public static void main(String[] args) {
        System.out.println("운영체제의 종류: " + System.getProperty("os.name"));
        System.out.println("네이티브의 바이트 해석 순서: " + ByteOrder.nativeOrder());
    }
}
```

운영체제와 JVM의 바이트 해석 순서가 다를 경우에는 JVM이 운영체제와 데이터를 교환할 때 자동적으로 처리해주기 때문에 문제는 없지만, 다이렉트 버퍼일 경우 운영체제의 native I/O를 사용하므로 운영체제의 기본 해석 순서로 JVM의 해석 순서를 맞추는 것이 성능향상에 도움이 된다. 다음과 같이 allocateDirect()로 버퍼를 생성한 후, order() 메소드를 호출해서 nativeOrder()의 리턴값으로 세팅해주면 된다.  

```java
ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100).order(ByteOrder.nativeOrder());
```

## Buffer의 위치 속성
Buffer를 사용하려면 Buffer의 위치 속성 개념과 위치 속성이 언제 변경되는지 알고 있어야 한다. 다음은 Buffer의 네 가지 위치 속성을 정리한 것이다.  

| 속성           | 설명                                                                                                                                                                                                                    |
| ------------ |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **position** | 현재 데이터를 읽거나 쓰는 위치 값이다. 인덱스 값이기 때문에 0부터 시작하며, limit보다 큰 값을 가질 수 없다. position과 limit 값이 같아지면 더 이상 데이터를 읽거나 쓸 수 없다는 의미가 된다. 대부분의 버퍼 연산은 position을 기준으로 수행된다.                                                             |
| **limit**    | 버퍼에서 데이터를 읽거나 쓸 수 있는 한계 지점이다. 쓰기 모드에서는 일반적으로 limit이 capacity와 같고, `flip()`을 호출하여 읽기 모드로 전환하면 limit은 현재 position 값으로 설정된다. 이는 지금까지 쓴 양만큼만 읽을 수 있도록 범위를 고정하는 역할을 한다.                                                    |
| **capacity** | 버퍼가 생성될 때 정해지는 고정된 크기이다. position이나 limit보다 항상 크거나 같으며, 생성 이후에는 변경할 수 없다.                                                                                                                                             |
| **mark**     | `mark()` 메서드를 호출할 때 저장되는 position의 값이다. 이후 `reset()`을 호출하면 position을 mark가 설정된 위치로 되돌릴 수 있다. 단, position이나 limit값이 mark보다 작은경우, mark는 자동 제거된다. mark가 없는 상태에서 reset() apthemfmf ghcnfgkaus InvalidMarkException이 발생한다. |

position, limit, capacity, mark 속성의 크기 관계는 다음과 같다. mark는 position보다 클 수 없고, position은 limit보다 클 수 없으며, limit은 capacity보다 클 수 없다.  

> 0 <= mark <= position <= limit <= capacity

> 예를 들어 다음 그림처럼 7바이트 크기의 버퍼가 있다고 가정해보자. 처음에는 limit과 capacity가 모두 7이라는 값을 가지고 있고 position은 0을 가지고 있다. 버퍼의 크기가 7이므로 인덱스는 6까지이다. 
> 
> <img src=./img/NIO_05.png style="display: block; margin: 0 auto;">

> 먼저 2바이트를 버퍼에 저장해보자. 2바이트는 position이 위치한 0 인덱스에서 시작해서 버퍼에 저장된다. 따라서 다음 그림과 같이 처음 2바이트는 채워지고, position은 2번 인덱스가 된다.  
>
> <img src=./img/NIO_06.png style="display: block; margin: 0 auto;">

> 계속해서 3바이트르르 저장해보자, 3바이트는 position 2 인덱스에서 시작해서 버퍼에 저장된다. 따라서 다음 그림과 같이 5바이트가 채워지고 position은 5번 인덱스가 된다.  
>
> <img src=./img/NIO_07.png style="display: block; margin: 0 auto;">

> 이제 버퍼에 저장되어 있는 바이트를 읽어보자. 먼저 flip() 메소드를 호출해야 한다. flip() 메소드를 호출하면 limit을 현재 position 5 인덱스로 설정하고, position을 0번 인덱스로 설정한다.  
>
> <img src=./img/NIO_08.png style="display: block; margin: 0 auto;">

> 버퍼에서 3바이트를 읽는다고 가정해보자. position이 0번 인덱스이므로 처음 3바이트가 읽혀지고 position은 다음 그림처럼 3번 인덱스로 이동한다.  
>
> <img src=./img/NIO_09.png style="display: block; margin: 0 auto;">

> position이 3번 인덱스를 가르키고 있을 때 mark() 메소드를 호출해서 현재 위치를 기억시켜 놓는다. 따라서 mark는 3번 인덱스에 위치한다.  
>
> <img src=./img/NIO_10.png style="display: block; margin: 0 auto;">

> 이어서 2바이트를 더 읽어보자, 다음 그림처럼 position은 5번 인덱스로 이동한다.  
>
> <img src=./img/NIO_11.png style="display: block; margin: 0 auto;">

> 이번에는 position을 mark 위치로 다시 이동해야 한다고 가정해보자. reset() 메서드를 호출하면 position은 mark가 있는 3번 인덱스로 이동한다. 주의할 점은 mark가 없는 상태에서 reset() 메소드를 호출하면 InvalidMarkException 예외가 발생한다.  
>
> <img src=./img/NIO_12.png style="display: block; margin: 0 auto;">

> 이번에는 버퍼를 되감아 동일한 데이터를 한 번 더 읽고 싶다고 가정해보자. rewind() 메소드를 호출하면 limit은 변하지 않지만 position은 0번 인덱스로 다시 설정된다. mark는 position이나 limit이 mark보다 작은 값으로 조정되면 자동적으로 없어진다.  
> 
> <img src=./img/NIO_13.png style="display: block; margin: 0 auto;">

> 만약 rewind() 대신 clear() 메소드를 호출하면 Buffer의 세 가지 속성은 초기화된다. limit은 capacity로, position은 0으로 설정되고 mark는 자동적으로 없어진다. 하지만 데이터는 삭제되지 않는다. 
>
> <img src=./img/NIO_14.png style="display: block; margin: 0 auto;">

> 버퍼의 위치 속성을 변경하는 또 다른 메소드로 compact()가 있다. compact()를 호출하면 현재 position에서 limit 사이의 데이터가 0번 인덱스로 복사되고 현재 position은 복사된 데이터 다음 위치로 이동한다. 예를 들어 flip() 메소드 호출 후 세 개의 데이터를 읽고 다음과 같이 position이 3번 인덱스 위치에 있을 때 compact()가 호출되면 3번 인덱스와 4번 인덱스의 데이터는 0번 인덱스와 1번 인덱스로 복사되고 position은 2번 인덱스로 이동한다. 그리고 limit은 capacity로 이동한다. 주의할 점은 0번과 1번 인덱스를 제외한 나머지 인덱스의 데이터는 삭제되지 않고 남아있다.  
>
> <img src=./img/NIO_15.png style="display: block; margin: 0 auto;">

## Buffer 메소드
Buffer를 생성한 후 사용할 때에는 Buffer가 제공하는 메소드를 잘 활용해야 한다. Buffer마다 공통적으로 사용하는 메소드들도 있고, 데이터 타입별로 Buffer가 개별적으로 가지고 있는 메소드들도 있다. 

### 공통 메소드
각 타입별 버퍼 클래스는 Buffer 추상 클래스를 상속하고 있다. Buffer 추상 클래스에는 모든 버퍼가 공통적으로 가져야 할 메소드들이 정의되어 있는데, 위치 속성을 변경하는 flip(), rewind(), clear(), mark(), reset()도 모두 Buffer 추상 클래스에 있다.

| 리턴 타입     | 메소드(매개변수)                   | 설명                                             |
| --------- | --------------------------- | ---------------------------------------------- |
| `Object`  | `array()`                   | 버퍼가 래핑하고 있는 **배열 객체를 반환** (heap 버퍼에만 해당)       |
| `int`     | `position()`                | 현재 position 값을 반환                              |
| `Buffer`  | `position(int newPosition)` | position 값을 설정하고 this 반환                       |
| `int`     | `limit()`                   | 현재 limit 값을 반환                                 |
| `Buffer`  | `limit(int newLimit)`       | limit 값을 설정하고 this 반환                          |
| `int`     | `capacity()`                | 버퍼의 capacity (고정 크기) 반환                        |
| `Buffer`  | `flip()`                    | position을 0으로, limit을 현재 position으로 설정 (읽기 준비) |
| `Buffer`  | `clear()`                   | position을 0으로, limit을 capacity로 설정 (쓰기 준비)     |
| `Buffer`  | `rewind()`                  | position을 0으로 재설정 (다시 읽기용)                     |
| `Buffer`  | `mark()`                    | 현재 position을 mark로 저장                          |
| `Buffer`  | `reset()`                   | mark에 저장된 position으로 되돌림                       |
| `boolean` | `hasRemaining()`            | 읽거나 쓸 데이터가 남아 있으면 true 반환 (position < limit)   |
| `int`     | `remaining()`               | limit - position 값 반환 (남은 요소 수)                |
| `boolean` | `isReadOnly()`              | 버퍼가 읽기 전용인지 여부 반환                              |
| `boolean` | `hasArray()`                | 버퍼가 배열 기반(heap)인지 여부 반환                        |
| `int`     | `arrayOffset()`             | 버퍼가 내부 배열에서 시작되는 위치의 offset 반환                 |
| `Buffer`  | `duplicate()`               | 동일한 내용을 공유하는 새 버퍼 생성 (포지션 등은 독립적)              |
| `Buffer`  | `slice()`                   | 현재 position부터 limit까지의 슬라이스 버퍼 반환              |
| `Buffer`  | `compact()`                 | 읽지 않은 데이터만 앞쪽으로 복사 후 쓰기 모드로 전환                 |

### 데이터를 읽고 저장하는 메소드
버퍼에 데이터를 저장하는 메소드는 put()이고, 데이터를 읽는 메소드는 get()이다. 이 메소드들은 Buffer 추상 클래스에는 없고, 각 타입별 하위 Buffer 클래스가 가지고 있다. get()과 put() 메소드는 상대적과 절대적으로 구분된다. 버퍼내의 현재 위치 속성인 position에서 데이터를 읽고, 저장할 경우는 상대적이고, position과 상관없이 주어진 인덱스에서 데이터를 읽고 저장할 경우는 절대적이다. 상대적 get()과 put() 메소드를 호출하면 position의 값은 증가하지만, 절대적 get()과 position() 메소드를 호출하면 position의 값은 증가되지 않는다. 만약 positon 값이 limit 값까지 증가했는데도 상대적 get()을 사용하면 BufferUnderflowException 예외가 발생하고, put() 메소드를 사용하면 BufferOverflowException 예외가 발생한다.  

다음은 ByteBuffer와 CharBuffer에서 제공하는 get() 메소드와 put() 메소드를 나열한 표이다.  

| 구분        | 방식  | **ByteBuffer**                                                                                                                                                                                                                                                                 | **CharBuffer**                                                                                                                                                                     |
| --------- | --- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **get()** | 상대적 | `get()`<br>`get(byte[] dst)`<br>`get(byte[] dst, int offset, int length)`<br>**`getChar()`**<br>**`getShort()`**<br>**`getInt()`**<br>**`getLong()`**<br>**`getFloat()`**<br>**`getDouble()`**                                                                                 | `get()`<br>`get(char[] dst)`<br>`get(char[] dst, int offset, int length)`                                                                                                          |
|           | 절대적 | `get(int index)`<br>**`getChar(int index)`**<br>**`getShort(int index)`**<br>**`getInt(int index)`**<br>**`getLong(int index)`**<br>**`getFloat(int index)`**<br>**`getDouble(int index)`**                                                                                    | `get(int index)`                                                                                                                                                                   |
| **put()** | 상대적 | `put(byte b)`<br>`put(byte[] src)`<br>`put(byte[] src, int offset, int length)`<br>**`putChar(char value)`**<br>**`putShort(short value)`**<br>**`putInt(int value)`**<br>**`putLong(long value)`**<br>**`putFloat(float value)`**<br>**`putDouble(double value)`**            | `put(char c)`<br>`put(char[] src)`<br>`put(char[] src, int offset, int length)`<br>**`put(CharBuffer src)`**<br>**`put(String src)`**<br>**`put(String src, int start, int end)`** |
|           | 절대적 | `put(int index, byte b)`<br>**`putChar(int index, char value)`**<br>**`putShort(int index, short value)`**<br>**`putInt(int index, int value)`**<br>**`putLong(int index, long value)`**<br>**`putFloat(int index, float value)`**<br>**`putDouble(int index, double value)`** | `put(int index, char c)`                                                                                                                                                           |

상대적 메소드와 절대적 메소드를 쉽게 구분하는 방법은 index 매개변수가 없으면 상대적이고, index 매개변수가 있으면 절대적이다.  

### 버퍼 예외의 종류
주로 버퍼가 다 찼을 때 데이터를 저장하는 경우와 버퍼에서 더 이상 읽어올 데이터가 없을 때 읽으려는 경우 예외가 발생한다. 

| 예외 클래스                              | 설명                                                                                                                 |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| **`BufferOverflowException`**       | 버퍼에 데이터를 `put()`하려 했지만, **남은 공간이 부족한 경우** 발생. 주로 `position >= limit`인 상태에서 쓰기를 시도할 때 발생한다.                         |
| **`BufferUnderflowException`**      | 버퍼에서 데이터를 `get()`하려 했지만, **읽을 수 있는 데이터가 남아 있지 않은 경우** 발생. 주로 `position >= limit`일 때 읽기를 시도하면 발생한다.                 |
| **`ReadOnlyBufferException`**       | 읽기 전용 버퍼(`read-only buffer`)에 `put()`이나 `compact()` 등을 호출하려 할 때 발생.                                                |
| **`IndexOutOfBoundsException`**     | 절대적 접근(`get(int index)` 또는 `put(int index, ...)`)에서 **유효하지 않은 인덱스**를 지정했을 때 발생. limit 또는 capacity보다 크거나, 음수인 경우 등. |
| **`IllegalArgumentException`**      | 메소드 호출 시 **논리적으로 잘못된 인자값**을 넘긴 경우 발생. 예: offset과 length가 배열 범위를 벗어남.                                               |
| **`UnsupportedOperationException`** | 해당 버퍼 구현체가 특정 연산을 **지원하지 않을 경우** 발생. 예: `array()` 호출이 불가능한 다이렉트 버퍼에서 `array()`를 호출한 경우.                            |

> 다음 예제는 데이터를 버퍼에 쓰고, 읽을 때, 그리고 위치 속성을 변경하는 메소드를 호출할 때 버퍼의 위치 속성값의 변화를 보여준다.  

```java
/* BufferExample.java - Buffer의 위치 속성값 */
public class BufferExample {
    public static void main(String[] args) {
        System.out.println("[7바이트 크기로 버퍼 생성]");
        ByteBuffer buffer = ByteBuffer.allocateDirect(7);
        printState(buffer);

        buffer.put((byte)10);
        buffer.put((byte)11);
        System.out.println("[2바이트 저장후]");
        printState(buffer);

        buffer.put((byte)12);
        buffer.put((byte)13);
        buffer.put((byte)14);
        System.out.println("[3바이트 저장후]");
        printState(buffer);

        buffer.flip();
        System.out.println("[flip() 실행후]");
        printState(buffer);

        buffer.get(new byte[3]);
        System.out.println("[3바이트 읽은후]");
        printState(buffer);

        buffer.mark();
        System.out.println("--------[현재 위치를 마크 해놓음]");

        buffer.get(new byte[2]);
        System.out.println("[2바이트 읽은후]");
        printState(buffer);

        buffer.reset();
        System.out.println("--------[position을 마크 위치로 옮김]");
        printState(buffer);

        buffer.rewind();
        System.out.println("[rewind() 실행후]");
        printState(buffer);

        buffer.clear();
        System.out.println("[clear() 실행후]");
        printState(buffer);
    }

    public static void printState(Buffer buffer) {
        System.out.print("\tposition:" + buffer.position() + ", ");
        System.out.print("\tlimit:" + buffer.limit() + ", ");
        System.out.println("\tcapacity:" + buffer.capacity());
    }
}
```

> 다음 예제는 compact() 메소드 호출 후, 변경된 버퍼의 내용과 position, limit의 위치를 보여준다.  

```java
/* CompacteExample.java - compact() 메소드의 역할 */
public class CompactExample {
    public static void main(String[] args) {
        System.out.println("[7바이트 크기로 버퍼 생성]");
        ByteBuffer buffer = ByteBuffer.allocateDirect(7);
        buffer.put((byte)10);
        buffer.put((byte)11);
        buffer.put((byte)12);
        buffer.put((byte)13);
        buffer.put((byte)14);
        buffer.flip();
        printState(buffer);

        buffer.get(new byte[3]);
        System.out.println("[3바이트 읽음]");

        buffer.compact();
        System.out.println("[compact() 실행후]");
        printState(buffer);
    }

    public static void printState(ByteBuffer buffer) {
        System.out.print(buffer.get(0) + ", ");
        System.out.print(buffer.get(1) + ", ");
        System.out.print(buffer.get(2) + ", ");
        System.out.print(buffer.get(3) + ", ");
        System.out.println(buffer.get(4));
        System.out.print("position:" + buffer.position() + ", ");
        System.out.print("limit:" + buffer.limit() + ", ");
        System.out.println("capacity:" + buffer.capacity());
    }
}
```

## Buffer 변환
채널이 데이터를 읽고 쓰는 버퍼는 모두 ByteBuffer이다. 그렇기 때문에 채널을 통해 읽은 데이터를 복원하려면 ByteBuffer를 문자열 또는 다른 타입 버퍼(CharBuffer, SHortBuffer, IntBuffer, LongBuffer, FloatBuffer, DoubleBuffer)로 변환해야 한다. 반대로 문자열 또는 다른 타입 버퍼의 내용을 채널을 통해 쓰고 싶다면 ByteBuffer로 변환해야 한다.  

### ByteBuffer <-> String
프로그램에서 가장 많이 처리되는 데이터는 String 타입, 즉 문자열이다. 채널을 통해 문자열을 파일이나 네트워크로 전송하려면 특정 문자셋(UTF-8, EUC-KR)으로 인코딩해서 ByteBuffer로 변환해야 한다. 먼저 문자셋을 표현하는 java.nio.charset.Charset 객체가 필요한데, 다음 두 가지 방법으로 얻을 수 있다.  

```java
Charset charset = Charset.forName("UTF-8");     // 매개값으로 주어진 문자셋
Charset charset = Charset.defaultCharset();     // 운영체제가 사용하는 디폴트 문자셋
```

Charset을 이용해서 문자열을 ByteBuffer로 변환하려면 다음과 같이 encode() 메소드를 호출하면된다.  

```java
String data = "";
ByteBuffer byteBuffer = charset.encode(data);
```

반대로 파일이나 네트워크로부터 읽은 ByteBuffer가 특정 문자셋(UTF-8, EUC-KR)으로 인코딩되어 있을 경우, 해당 문자셋으로 디코딩 해야만 문자열로 복원할 수 있다.  

```java
ByteBuffer byteBuffer = "";
String data = charset.decode(byteBuffer).toString();
```

```java
/* ByteBufferToStringExample.java - ByteBuffer <-> String */
public class ByteBufferToStringExample {
    public static void main(String[] args) {
        Charset charset = Charset.forName("UTF-8");

        //문자열 -> 인코딩 -> ByteBuffer
        String data = "안녕하세요";
        ByteBuffer byteBuffer = charset.encode(data);

        //ByteBuffer -> 디코딩 -> CharBuffer -> 문자열
        data = charset.decode(byteBuffer).toString();
        System.out.println("문자열 복원: " + data);
    }
}
```

### ByteBuffer <-> IntBuffer
int[] 배열을 생성하고 이것을 네트워크로 출력하기 위해서는 int[] 배열 또는 IntBuffer로부터 ByteBuffer를 생성해야 한다. int 타입은 4byte 크기를 가지므로 int[] 배열 크기 또는 IntBuffer의 capacity보다 4배 큰 capacity를 가진 ByteBuffer를 생성하고, ByteBuffer의 putInt() 메소드로 정수값을 하나씩 저장하면 된다. 다음은 int[] 배열을 IntBuffer로 래핑하고 다시 IntBuffer로 래핑할 필요는 없다. 4배 큰 ByteBuffer를 생성한 후 정수를 저장한다. 주의할 점은 putInt() 메소드는 position을 이동시키기 때문에 모두 저장한 후에는 position=0으로 되돌려 놓는 flip() 메소드를 호출해야 한다.

```java
int[] data = new int[] { 10, 20 };
IntBuffer intBuffer = IntBuffer.wrap(data);
ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
for (int i = 0; i < intBuffer.capacity(); i++) {
byteBuffer.putInt(intBuffer.get(i));
}
byteBuffer.flip();
```

반대로 파일이나 네트워크로부터 입력된 ByteBuffer에 4바이트씩 연속된 int 데이터가 저장되어 있을 경우, int[] 배열로 복원이 가능하다. ByteBuffer의 asIntBuffer() 메소드로 IntBuffer를 얻고, IntBuffer의 capacity와 동일한 크기의 int[] 배열을 생성한다. 그리고 IntBuffer의 get() 메소드로 int 값을 배열에 저장하면 된다.

```java
ByteBuffer byteBuffer = ...;
IntBuffer intBuffer = byteBuffer.asIntBuffer();
int[] data = new int[intBuffer.capacity()];
intBuffer.get(data);
```

참고로 ByteBuffer에서 asIntBuffer()로 얻은 IntBuffer에서는 array() 메소드를 사용해서 int[] 배열을 얻을 수 없다. array() 메소드는 래핑한 배열만 리턴하기 때문에 int[] 배열은 wrap() 메소드로 래핑한 IntBuffer에서만 사용할 수 있음을 기억하기 바란다. 다음 예제에서는 int[] 배열로부터 얻은 ByteBuffer를 이용해서 다시 int[] 배열로 복원한다.

```java
public class ByteBufferToIntBufferExample {
    public static void main(String[] args) throws Exception {
        //int[] -> IntBuffer -> ByteBuffer
        int[] writeData = { 10, 20 };
        IntBuffer writeIntBuffer = IntBuffer.wrap(writeData);
        ByteBuffer writeByteBuffer= ByteBuffer.allocate(writeIntBuffer.capacity()*4);
        for(int i=0; i<writeIntBuffer.capacity(); i++) {
            writeByteBuffer.putInt(writeIntBuffer.get(i));
        }
        writeByteBuffer.flip();

        //ByteBuffer -> IntBuffer -> int[]
        ByteBuffer readByteBuffer = writeByteBuffer;
        IntBuffer readIntBuffer = readByteBuffer.asIntBuffer();
        int[] readData = new int[readIntBuffer.capacity()];
        readIntBuffer.get(readData);
        System.out.println("배열 복원: " + Arrays.toString(readData));
    }
}
```

ByteBuffer와 IntBuffer 간의 변환을 이해하면 ByteBuffer와 ShortBuffer, LongBuffer, FloatBuffer, DoubleBuffer 간의 변환도 비슷한 방법으로 하면 된다. 다음은 ByteBuffer가 가지고 있는 기본 타입 버퍼로 변환하는 asXXXBuffer() 메소드들이다.

| 리턴 타입          | 변환 메소드             | 설명                                      |
| -------------- | ------------------ | --------------------------------------- |
| `ShortBuffer`  | `asShortBuffer()`  | 2바이트씩 연속된 short 데이터를 가진 ByteBuffer일 경우  |
| `IntBuffer`    | `asIntBuffer()`    | 4바이트씩 연속된 int 데이터를 가진 ByteBuffer일 경우    |
| `LongBuffer`   | `asLongBuffer()`   | 8바이트씩 연속된 long 데이터를 가진 ByteBuffer일 경우   |
| `FloatBuffer`  | `asFloatBuffer()`  | 4바이트씩 연속된 float 데이터를 가진 ByteBuffer일 경우  |
| `DoubleBuffer` | `asDoubleBuffer()` | 8바이트씩 연속된 double 데이터를 가진 ByteBuffer일 경우 |

# 파일 채널
java.nio.channels.FileChannel을 이용하면 파일의 읽기와 쓰기를 할 수 있다. FileChannel은 동기식 처리기 때문에 멀티 스레드 환경에서 사용해도 안정적이다.

<img src=./img/NIO_16.png style="display: block; margin: 0 auto;">

## FileChannel 생성과 닫기
FileChannel은 정적 메소드인 open()을 호출해서 얻을 수도 있지만, IO의 FileInputStream, FileOutputStream의 getChannel() 메소드를 호출해서 얻을 수도 있다. 다음은 open() 메소드로 FileChannel을 얻는 방법을 보여준다.  

```java
FileChannel fileChannel = FileChannel.open(Path path, OpenOption... options);
```

첫 번째 path 매개변수는 열거나, 생성하고자 하는 파일의 경로를 Path 객체로 생성해서 지정하면 되고, 두 번째 options 매개변수는 열기 옵션 값인데 StandardOpenOption의 다음 열기 상수를 나열해주면 된다.  

| 열기 상수               | 설명                                             |
| ------------------- | ---------------------------------------------- |
| `READ`              | 읽기용으로 파일을 연다.                                  |
| `WRITE`             | 쓰기용으로 파일을 연다.                                  |
| `CREATE`            | 파일이 없으면 파일을 생성한다.                              |
| `CREATE_NEW`        | 파일이 없으면 새 파일을 생성한다. <br>이미 파일이 있을 경우 예외가 발생한다. |
| `APPEND`            | 파일 끝에 데이터를 추가한다. (`WRITE`, `CREATE`와 함께 사용)    |
| `DELETE_ON_CLOSE`   | 채널을 닫을 때 파일을 삭제한다 (임시 파일을 사용할 때 유용함)           |
| `TRUNCATE_EXISTING` | 파일을 0바이트로 잘라낸다. (`WRITE` 옵션과 함께 사용)            |

예를 들어 "C:/Temp/file.txt" 파일을 생성하고, 어떤 내용을 쓰고 싶다면 다음과 같이 매개값을 지정하면 된다.

```java
    FileChannel fileChannel = FileChannel.open(
    Paths.get("C:/Temp/file.txt"),
    StandardOpenOption.CREATE_NEW,
    StandardOpenOption.WRITE
    );
```

다음은 "C:/Temp/file.txt" 파일을 읽고, 쓸 수 있도록 FileChannel을 생성하는 예제이다.  

```java
FileChannel fileChannel = FileChannel.open(
    Paths.get("C:/Temp/file.txt"),
    StandardOpenOption.READ,
    StandardOpenOption.WRITE
);
```

FileChannel을 더 이상 이용하지 않을 경우에는 다음과 같이 close() 메서드를 호출해서 닫아주어야 한다.  

```java
fileChannel.close();
```

## 파일 쓰기와 읽기  
파일에 바이트를 쓰려면 다음과 같이 FileChannel의 write() 메소드를 호출하면 된다. 매개값으로 ByteBuffer 객체를 주면 되는데, 파일에 쓰여지는 바이트는 ByteBuffer의 position부터 limit까지이다. position이 0이고, limit()이 capacity()와 동일하다면 ByteBuffer의 모든 바이트가 파일에 쓰여진다. write() 메소드의 리턴값은 ByteBuffer에서 파일로 쓰여진 바이트 수이다.  

```java
int bytesCount = fileChannel.write(ByteBuffer src);
```

> 다음 예제는 FileChannel을 이용하여 문자열을 C:\Temp\file.txt에 저장한다.

```java
public class FileChannelWriteExample {
02      public static void main(String[] args) throws IOException {
03          Path path = Paths.get("C:/Temp/file.txt");
04          Files.createDirectories(path.getParent());  // 디렉토리 생성
05  
06          FileChannel fileChannel = FileChannel.open(
07              path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);  // FileChannel 생성
08  
09          String data = "안녕하세요";
10          Charset charset = Charset.defaultCharset();
11          ByteBuffer byteBuffer = charset.encode(data);  // 파일에 데이터 쓰기
12  
13          int byteCount = fileChannel.write(byteBuffer);  // 파일에 쓰기
14          System.out.println("file.txt : " + byteCount + " bytes written");  // 실행결과 출력
15  
16          fileChannel.close();  // 채널 닫기
17      }
18  }
```

파일로부터 바이트를 읽기 위해서는 다음과 같이 FileChannel의 read() 메소드를 호출하면 된다. 매개값으로 ByteBuffer 객체를 주면 되는데, 파일에서 읽혀지는 바이트는 ByteBuffer의 position부터 저장된다. position()이 0이면 ByteBuffer의 첫 바이트부터 저장된다.  read() 메소드의 리턴값은 파일에서 ByteBuffer로 읽혀진 바이트 수이다. 한 번 읽을 수 있는 최대 바이트 수는 ByteBuffer의 capacity값이고, 최댓값은 capacity가 된다. 파일에서 더 이상 읽을 바이트가 없다면 read() 메소드는 -1을 리턴한다.

```java
int bytesCount = fileChannel.read(ByteBuffer dst);
```

버퍼에 한 바이트를 저장할 때마다 position이 1씩 증가하게 되는데, read() 메소드가 -1을 리턴할 때까지 버퍼에 저장한 마지막 바이트의 위치는 position - 1 인덱스이다.  

> 다음 예제는 이전 예제에서 생성한 FileChannelReadExample.java 파일을 읽고 콘솔에 출력한다.  

```java
public class FileChannelReadExample {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:/Temp/file.txt");

        FileChannel fileChannel = FileChannel.open(
                path, StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        Charset charset = Charset.defaultCharset();
        String data = "";
        int byteCount;

        while(true) {
            byteCount = fileChannel.read(byteBuffer);
            if(byteCount == -1) break;
            byteBuffer.flip();
            data += charset.decode(byteBuffer).toString();
            byteBuffer.clear();
        }

        fileChannel.close();

        System.out.println("file.txt : " + data);
    }
}
```

파일의 크기가 100바이트보다 작지만, 예제에서는 ByteBuffer의 크기를 100으로 주었다.
15라인은 FileChannel의 read() 메소드를 호출해서 최대 100바이트를 읽는다.
그러나 파일의 크기가 100바이트보다 작으므로 byteCount에는 100보다 작은 값이 저장된다.

- 17라인에서 flip()을 호출한 이유는 limit을 현재 position으로 설정하고, position을 0으로 설정하기 위해서이다.
- 18라인은 position이 0에서 limit까지 읽기 위해 문자열로 변환한다.
- 19라인에서 clear()를 호출한 이유는 position을 0번 인덱스로, limit을 capacity로 설정해서 ByteBuffer를 초기화하기 위해서이다.  

## 파일 복사
파일 복사를 구현하기 위해서는 하나의 ByteBuffer를 사이에 두고, 파일 읽기용 FileChannel과 파일 쓰기용 FileChannel이 읽기와 쓰기를 교대로 번갈아 수행하도록 하면 된다.  

<img src=./img/NIO_17.png style="display: block; margin: 0 auto;">

다음 예제는 FileChannel을 이용해서 이미지 파일을 복사한다. 12라인에서는 크기 100인 다이렉트 버퍼를 생성했다. 채널에서 읽고 다시 채널로 쓰는 경우 다이렉트 버퍼가 좋은 성능을 내기 때문이다.  

```java
public class FileCopyExample {
    public static void main(String[] args) throws IOException {
        Path from = Paths.get("src/sec04/exam02_file_copy/house1.jpg");
        Path to = Paths.get("src/sec04/exam02_file_copy/house2.jpg");

        FileChannel fileChannel_from = FileChannel.open(
                from, StandardOpenOption.READ);

        FileChannel fileChannel_to = FileChannel.open(
                to, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocateDirect(100);
        int byteCount;
        while(true) {
            buffer.clear();
            byteCount = fileChannel_from.read(buffer);
            if(byteCount == -1) break;
            buffer.flip();
            fileChannel_to.write(buffer);
        }

        fileChannel_from.close();
        fileChannel_to.close();
        System.out.println("파일 복사 성공");
    }
}
```

이번 예제처럼 ByteBuffer와 FileChannel 두 개를 직접 생성해서 복사를 구현해도 좋지만, 단순히 파일을 복사할 목적이라면 NIO의 Files 클래스의 copy() 메소드를 사용하는 것이 더 편리하다.  

```java
Path targetPath = Files.copy(Path source, Path target, CopyOption... options);
```

> 🧾 매개변수 설명  
> source : 원본 파일의 Path 객체를 지정  
> target : 복사될 대상 파일의 Path 객체를 지정    
> options : 선택적 매개변수로, StandardCopyOption의 세 가지 열거 상수를 목적에 맞게 나열할 수 있음

| 열거 상수              | 설명                                       |
| ------------------ | ---------------------------------------- |
| `REPLACE_EXISTING` | 타겟 파일이 존재하면 **덮어쓴다**.                    |
| `COPY_ATTRIBUTES`  | 파일의 속성(시간 등)을 **복사**한다.                  |
| `NOFOLLOW_LINKS`   | 링크 파일일 경우 링크만 복사하고 **링크된 파일은 복사하지 않는다**. |

> 다음 예제 Files 클래스의 copy() 메소드를 이용한 파일 복사한다.  

```java
public class FilesCopyMethodExample {
    public static void main(String[] args) throws IOException {
        Path from = Paths.get("src/sec04/exam02_file_copy/house1.jpg");
        Path to = Paths.get("src/sec04/exam02_file_copy/house2.jpg");

        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("파일 복사 성공");
    }
}
```

# 파일 비동기 채널
FileChannel의 read()와 write() 메소드는 파일 입출력 작업 동안 블로킹된다. 만약 UI 및 이벤트를 처리하는 스레드에서 이 메소드를 호출하면 블로킹되는 동안에 UI 갱신이나 이벤트 처리를 할 수 없다. 따라서 별도의 작업 스레드를 생성해서 이 메소드들을 호출해야 한다. 만약 동시에 처리해야 할 파일 수가 많다면 스레드의 수도 증가하기 때문에 문제가 될 수 있다. 그래서 자바 NIO는 블로킹되지 않고 파일 및 내용량과 파일의 입출력 작업을 위해서 비동기 파일 채널(AsynchronousFileChannel)을 별도로 제공하고 있다.

AsynchronousFileChannel의 특징은 파일의 데이터 입출력을 위해 read()와 write() 메소드를 호출하면 스레드풀에서 작업 처리를 요청하고 이 메소드들은 즉시 리턴시킨다. 실제적인 입출력 작업 처리는 스레드풀의 작업 스레드가 담당한다. 작업 스레드가 파일 입출력을 완료하게 되면 콜백(callback) 메소드가 자동 호출되기 때문에 작업 완료 후 실행해야 할 코드가 있다면 콜백 메소드에 작성하면 된다.  

<img src=./img/NIO_18.png style="display: block; margin: 0 auto;">

## AsynchronousFileChannel 생성과 닫기
AsynchronousFileChannel은 두 가지 정적 메소드인 open()을 호출해서 얻을 수 있다. 첫 번째 open() 메소드는 다음과 같이 파일의 Path 객체와 열기 옵션 값을 매개값으로 받는다.

```java
AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
Path file,
OpenOption... options
);
```

이렇게 생성된 AsynchronousFileChannel은 내부적으로 생성되는 기본 스레드풀을 이용해서 스레드를 관리한다. 기본 스레드풀의 최대 스레드 개수는 개발자가 지정할 수 있기 때문에 다음과 같이 두 번째 open() 메소드로 AsynchronousFileChannel을 만들 수도 있다.

```java
AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
Path file,
Set<? extends OpenOption> options,
    ExecutorService executor,
    FileAttribute<?>... attrs
);
```

file 매개값은 파일의 Path 객체이고, options 매개값은 파일 열기 옵션 정보를 저장한 Set 객체이며, executor 매개값은 스레드풀 객체인 ExecutorService를 지정하고, attrs 매개값은 생성할 파일의 속성정보인 FileAttribute를 지정하면 된다. 아래 예제는 “C:/Temp/file.txt” 파일을 생성할 AsynchronousFileChannel을 다음과 같이 생성한 것이다.  

```java
ExecutorService executorService = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
);

AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
    Paths.get("C:/Temp/file.txt"),
    EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE),
    executorService
);
```

Runtime.getRuntime().availableProcessors()는 CPU의 코어 수를 리턴한다. 쿼드 코어 CPU일 경우는 4를 리턴, 하이퍼 스레딩일 경우는 논을 리턴한다. EnumSet.of() 메소드는 매개값으로 나열된 열거 상수를 Set 객체에 담아 리턴한다. AsynchronousFileChannel을 더 이상 사용하지 않으면 다음과 같이 close() 메소드를 호출해서 닫아준다.  

```java
fileChannel.close();
```

## 파일 읽기와 쓰기  
AsynchronousFileChannel이 생성되었다면 read(), write() 메소드를 이용해서 입출력할 수 있다.
```java
read(ByteBuffer dst, long position, A attachment, CompletionHandler<Integer, A> handler);
write(ByteBuffer src, long position, A attachment, CompletionHandler<Integer, A> handler);
```

이 메소드들을 호출하면 즉시 리턴되고, 스레드풀의 스레드가 입출력 작업을 진행한다. dst와 src 매개값은 읽거나 쓰기 위한 ByteBuffer이고, position 매개값은 파일에서 읽을 위치이거나 쓸 위치이다. 파일의 첫 번째 바이트부터 읽거나 첫 번째 위치에 바이트를 쓰고 싶다면 position을 0으로 주면 된다. attachment 매개값은 콜백 메소드로 전달할 첨부 객체이다. 첨부 객체는 콜백 메소드에서 결과와 연관이 있는 추가적인 정보를 알고자 할 때 사용하는 객체를 말한다. 만약 첨부 객체가 필요 없다면 null을 대입해도 된다.

handler 매개값은 CompletionHandler<Integer, A> 구현 객체를 지정한다. Integer는 입출력 작업의 결과 타입으로, read()와 write()가 읽거나 쓴 바이트 수이다. A는 첨부 객체 타입이다. 만약 첨부 객체가 필요 없다면 CompletionHandler 구현 객체를 작성할 때 명시로 지정이 가능하다. 첨부 객체가 필요 없다면 A는 Void가 된다. CompletionHandler<Integer, A> 구현 객체는 비동기 작업이 정상적으로 완료되었을 경우와 예외 발생으로 실패된 경우에 자동 호출되는 다음 두 가지 메소드를 가지고 있어야 한다.  

| 리턴 타입 | 메소드명(매개 변수)                             | 설명                   |
| ----- | --------------------------------------- | -------------------- |
| void  | completed(Integer result, A attachment) | 작업이 정상적으로 완료된 경우 호출  |
| void  | failed(Throwable exc, A attachment)     | 예외 때문에 작업이 실패된 경우 호출 |

completed() 메소드의 result 매개값은 작업 결과가 대입되는데, read()와 write() 작업 결과는 읽거나 쓴 바이트 수이다. attachment 매개값은 read()와 write() 메소드 호출 시 제공된 첨부 객체를 전달받는다. failed() 메소드의 첫 번째 매개값은 예외 정보를 가진 Throwable 객체이고, 두 번째 매개값은 첨부 객체이다. 그렇기 때문에 JavaFX 애플리케이션일 경우 UI 생성 및 변경 작업을 이 메소드에서 직접할 수 없고 Platform.runLater()를 이용해야 한다. 다음은 CompletionHandler 구현 클래스 작성하는 방법을 보여준다.  

```java
new CompletionHandler<Integer, A>() {
    @Override
    public void completed(Integer result, A attachment) { ... }

    @Override
    public void failed(Throwable exc, A attachment) { ... }
};
```

다음은 AsynchronousFileChannel을 이용해서 비동기적으로 "C:\Temp" 디렉토리에 file0.txt ~ file9.txt까지 총 10개의 파일을 생성한 후 "안녕하세요"라는 내용을 쓴다. 그리고 비동기 작업이 완료되었을 때 사용된 바이트 수와 처리를 담당했던 스레드 이름을 콘솔에 출력한다.  

```java
/* AsynchronousFileChannelWriteExample.java - 비동기로 파일 생성 및 쓰기 */
public class AsynchronousFileChannelWriteExample {
    public static void main(String[] args) throws Exception {
        //스레드풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        for(int i=0; i<10; i++) {
            Path path = Paths.get("C:/Temp/file" + i + ".txt");
            Files.createDirectories(path.getParent());

            //비동기 파일 채널 생성
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
                    path,
                    EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                    executorService
            );

            Charset charset = Charset.defaultCharset();
            ByteBuffer byteBuffer = charset.encode("안녕하세요");

            //첨부 객체 생성
            class Attachment {
                Path path;
                AsynchronousFileChannel fileChannel;
            }
            Attachment attachment = new Attachment();
            attachment.path = path;
            attachment.fileChannel = fileChannel;

            //CompletionHandler 객체 생성
            CompletionHandler<Integer, Attachment> completionHandler =
                    new CompletionHandler<Integer, Attachment>() {
                        @Override
                        public void completed(Integer result, Attachment attachment) {
                            System.out.println(attachment.path.getFileName() + " : " + result + " bytes written : " + Thread.currentThread().getName());
                            try { attachment.fileChannel.close(); } catch (IOException e) {}
                        }
                        @Override
                        public void failed(Throwable exc, Attachment attachment) {
                            exc.printStackTrace();
                            try { attachment.fileChannel.close(); } catch (IOException e) {}
                        }
                    };

            fileChannel.write(byteBuffer, 0, attachment, completionHandler);
        }

        //스레드풀 종료
        executorService.shutdown();
    }
}
```

이 예제에서 주의할 점은 48라인에서 write() 메소드가 즉시 리턴되더라도 뒤에서는 작업 스레드가 파일 쓰기 작업을 하고 있기 때문에 바로 AsynchronousFileChannel을 닫으면 안 된다. 작업이 정상적으로 완료되었거나, 실패일 경우 채널을 닫아야 하므로 completed()와 failed() 메소드에서 AsynchronousFileChannel의 close()를 호출해야 한다. 다음 예제는 이전 예제에서 생성한 file0.txt~file9.txt를 읽고 콘솔에 출력한다.  

```java
/* AsynchronousFileChannelReadExample.java - 비동기로 파일 생성 및 쓰기 */
public class AsynchronousFileChannelReadExample {
    public static void main(String[] args) throws Exception {
        //스레드풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        for(int i=0; i<10; i++) {
            Path path = Paths.get("C:/Temp/file" + i + ".txt");

            //비동기 파일 채널 생성
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
                    path,
                    EnumSet.of(StandardOpenOption.READ),
                    executorService
            );

            ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());

            //첨부 객체 생성
            class Attachment {
                Path path;
                AsynchronousFileChannel fileChannel;
                ByteBuffer byteBuffer;
            }
            Attachment attachment = new Attachment();
            attachment.path = path;
            attachment.fileChannel = fileChannel;
            attachment.byteBuffer = byteBuffer;

            //CompletionHandler 객체 생성
            CompletionHandler<Integer, Attachment> completionHandlernew =
                    new CompletionHandler<Integer, Attachment>() {
                        @Override
                        public void completed(Integer result, Attachment attachment) {
                            attachment.byteBuffer.flip();

                            Charset charset = Charset.defaultCharset();
                            String data = charset.decode(attachment.byteBuffer).toString();

                            System.out.println(attachment.path.getFileName() + " : " + data + " : " + Thread.currentThread().getName());
                            try { fileChannel.close(); } catch (IOException e) {
                                //e.printStackTrace();
                            }
                        }
                        @Override
                        public void failed(Throwable exc, Attachment attachment) {
                            exc.printStackTrace();
                            try { fileChannel.close(); } catch (IOException e) {}
                        }
                    };

            //파일 읽기
            fileChannel.read(byteBuffer, 0, attachment, completionHandlernew);
        }

        //스레드풀 종료
        //Thread.sleep(1000);
        executorService.shutdown();
    }
}
```

이 예제에서도 역시 54라인의 read() 메소드가 즉시 리턴되더라도 뒤에서는 작업 스레드가 파일 읽기 작업을 하고 있기 때문에 바로 AsynchronousFileChannel을 닫으면 안된다. 작업이 정상적으로 완료되었거나, 실패일 경우 채널을 닫아야 하므로 completed()와 failed() 메소드에서 AsynchronousFileChannel의 close()를 호출해야 한다.  

## [연습문제 풀이](./ChapterTest.md)