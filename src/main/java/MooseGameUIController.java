import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getip;

/**
 * UI Controller for the main game. Handles putting the score and time remaining into moosegame_ui.fxgml
 */
public class MooseGameUIController implements UIController {
    @FXML
    private Pane root;

    @FXML
    private Rectangle scoreDisplayRectangle;

    @FXML
    private Text scoreLabel;

    @FXML
    private Text scoreText;

    @FXML
    private Text remainingLabel;

    @FXML
    private Text remainingTime;

    /**
     * Puts the score and time in the correct place in moosegame_ui.fxml
     */
    @Override
    public void init(){
        scoreText.textProperty().bind(getip("score").asString());
        remainingTime.textProperty().bind(getip("time").asString());
    }
}
