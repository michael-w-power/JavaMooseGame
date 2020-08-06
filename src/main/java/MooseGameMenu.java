import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static javafx.scene.paint.Color.*;

public class MooseGameMenu extends FXGLMenu {

    public MooseGameMenu() {
        super(MenuType.MAIN_MENU);

        MooseButton playButton = new MooseButton("Play Game", () -> fireNewGame());
        MooseButton leaderboardButton = new MooseButton("Leaderboard", () -> fireNewGame());
        MooseButton creditButton = new MooseButton("Credits", () -> fireNewGame());
        MooseButton quitButton = new MooseButton("Exit Game", () -> fireExit());

        MooseGameMenuTitle title = new MooseGameMenuTitle("Moose On The Loose");

        var box = new VBox(15, playButton, creditButton, quitButton, leaderboardButton);
        var titleBox = new VBox (title);

        titleBox.setTranslateX(30);
        titleBox.setTranslateY(200);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));

        box.setTranslateX(175);
        box.setTranslateY(400);
        box.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        box.setPadding(new Insets(50,50,50,50));


        getContentRoot().getChildren().addAll(box, titleBox);
    }

    @Override
    protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Button createActionButton(String s, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Node createBackground(double w, double h) {
        return FXGL.texture("Roadway.png", 600, 900);
    }

    @Override
    protected Node createProfileView(String s) {
        return new Rectangle();
    }

    @Override
    protected Node createTitleView(String s) {
        return new Rectangle();
    }

    @Override
    protected Node createVersionView(String s) {
        return new Rectangle();
    }

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

    private static class MooseButton extends StackPane {
        private static final Color SELECTED_COLOR = WHITE;
        private static final Color NOT_SELECTED = GRAY;

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

            setOnMouseClicked(e -> {
                action.run();
            });

            getChildren().addAll(selector, text);
        }
    }
}