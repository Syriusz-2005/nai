import Vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record Lang(String lang, List<String> paragraphs) {
    public static String chars = "abcdefghijklmnopqrstuwxyz";

    public List<Vector> getAsVectors() {
        return paragraphs
                .stream()
                .filter(para -> para.length() > 10)
                .map(Lang::getParaAsVector)
                .toList();
    }

    public static Vector getParaAsVector(String para) {
        var paragraphCharacters = para.chars()
                .mapToObj((charCode) -> (char) charCode)
                .collect(Collectors.groupingBy(
                        character -> character,
                        Collectors.toList()
                ));
        ArrayList<Double> characters = new ArrayList<>();
        for (var c : chars.toCharArray()) {
            var groupedCharacters = paragraphCharacters.get(c);
            characters.add(((double) (groupedCharacters != null ? groupedCharacters.size() : 0)) / para.length());
        }
        return new Vector(
                characters
        );
    }
}
