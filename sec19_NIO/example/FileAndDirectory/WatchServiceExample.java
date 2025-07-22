package sec19_NIO.example.FileAndDirectory;

import java.awt.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

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

















