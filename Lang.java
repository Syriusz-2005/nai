import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Lang(String lang, List<String> paragraphs) {
    public int getMaxSize() {
        return paragraphs().stream().mapToInt(String::length).max().getAsInt();
    }

    public List<Vector> getAsVectors() {
        return paragraphs
                .stream()
                .filter(para -> para.length() > 10)
                .map(para -> {
                    List<Double> charCodes = para.chars().asDoubleStream().boxed().toList();
                    return new Vector(
                            charCodes
                    );
                })
                .toList();
    }
}
