import com.almasb.fxgl.audio.Sound;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.paint.Color.GRAY;
import static javafx.scene.paint.Color.WHITE;

public class MooseButton extends StackPane {
    private static final Color SELECTED_COLOR = WHITE;
    private static final Color NOT_SELECTED = GRAY;
    private Sound selectSound = getAssetLoader().loadSound("selection.mp3");
    private String name;
    private Runnable action;

    private Text text;
    private Rectangle selector;

    public MooseButton(String name, Runnable action) {
        this.name = name;
        this.action = action;
        text = getUIFactoryService().newText(name, WHITE, 25.0);
        text.fillProperty().bind(Bindings.when(hoverProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED));
        text.strokeProperty().bind(Bindings.when(hoverProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED));
        text.setStrokeWidth(0.50);
        selector = new Rectangle(6,17, WHITE);
        selector.setTranslateX(-25);
        selector.setTranslateY(-2);
        selector.visibleProperty().bind(hoverProperty());
        setAlignment(Pos.CENTER_LEFT);
        setFocusTraversable(true);
        setOnMouseEntered(e-> {getAudioPlayer().playSound(selectSound);});
        setOnMouseClicked(e -> {
            action.run();
        });
        setMaxWidth(0);
        getChildren().addAll(selector, text);

    }
}