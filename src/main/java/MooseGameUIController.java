import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.getip;

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

    @Override
    public void init() {
        scoreText.textProperty().bind(getip("score").asString());
        remainingTime.textProperty().bind(getip("time").asString());
    }
}
