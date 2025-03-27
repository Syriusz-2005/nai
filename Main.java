import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Main {
    private static Lang prepare(Lang in) {
        return new Lang(
                in.lang(),
                in.paragraphs().stream()
                    .map((line) -> line
                        .replaceAll("[^\\x00-\\x7F]|[;.,\\[\\]\\(\\)\\{\\}\\-\\:]", "")
                        .toLowerCase()
                    )
                    .toList()
        );
    }
    public static void main(String[] args) throws IOException {
        ArrayList<Lang> fileList = new ArrayList<>();
        Files.walkFileTree(Paths.get("input"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String lang = file.getFileName().toString().replace(".txt", "");
                fileList.add(new Lang(lang, Files.readAllLines(file)));
                return FileVisitResult.CONTINUE;
            }
        });
        var files = fileList.stream()
                .map(Main::prepare)
                .toList();
        System.out.println(files);
    }
}
