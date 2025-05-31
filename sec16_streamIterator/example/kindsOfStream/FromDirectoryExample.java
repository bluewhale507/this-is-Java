package sec16_streamIterator.example.kindsOfStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FromDirectoryExample {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("./src");
        Stream<Path> stream = Files.list(path);
        stream.map(Path::getFileName).forEach(System.out::println);
    }
}
