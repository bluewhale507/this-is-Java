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
버퍼가 사용하는 메모리의 위치에 따라서 넌다이렉트 버퍼와 다이렉트 버퍼로 분류된다.  