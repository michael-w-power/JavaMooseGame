import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.WHITE;

public class MooseGameMenuTitle extends StackPane {
    private Text text;

    public MooseGameMenuTitle(String name) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        text = new Text(spread);
        text.setFill(WHITE);
        text.setFont(Font.font("deriveFont", FontWeight.EXTRA_BOLD, 35));
        getChildren().addAll(text);
    }


}