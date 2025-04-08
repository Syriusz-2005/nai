import Vector.Vector;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
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
        var langs = fileList.stream()
                .map(Main::prepare)
                .toList();
        System.out.println(langs);
        Layer layer = new Layer();
        layer.train(langs, 20_000);
        System.out.println("Layer trained!");
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a paragraph to check:");
            String para = s.nextLine();
            Vector paraVector = Lang.getParaAsVector(preprocessPara(para));
            List<Double> output = layer.layerCompute(paraVector);
            showReadableOutput(langs, output);
        }
    }

    private static String preprocessPara(String para) {
        return para
                .replaceAll("[^\\x00-\\x7F]|[;.,\\[\\](){}\\-:]", "")
                .toLowerCase();
    }

    private static Lang prepare(Lang in) {
        return new Lang(
                in.lang(),
                in.paragraphs().stream()
                        .map(Main::preprocessPara)
                        .toList()
        );
    }

    private static void showReadableOutput(List<Lang> langs, List<Double> output) {
        for (int i = 0; i < output.size(); i++) {
            var outputValue = output.get(i);
            var lang = langs.get(i);
            System.out.printf("Lang: %s %.8f\n", lang.lang(), outputValue);
        }
    }
}
