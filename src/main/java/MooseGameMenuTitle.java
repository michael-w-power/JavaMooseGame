import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.WHITE;

/**
 * Class responsible for providing main menu title.
 */
public class MooseGameMenuTitle extends StackPane {
    private Text text;

    /**
     * Constructor that creates and formats the title on the main menu screen.
     * @param name
     */
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