import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static javafx.scene.paint.Color.*;

public class MooseGameMenu extends FXGLMenu {

    private Node mainMenuScreen;
    private Node leaderboardScreen;
    private Node carSelectionScreen;

    public MooseGameMenu() {
        super(MenuType.MAIN_MENU);
        mainMenuScreen = createMainMenu();
        showMainMenu();
        carSelectionScreen = carSelectionMenu();
    }

//    private Node leaderboardMenu(){
//
//    }

    private Node createMainMenu(){
        MooseButton playButton = new MooseButton("Play Game", () -> fireNewGame());
        MooseButton leaderboardButton = new MooseButton("Leaderboard", () -> fireNewGame());
        MooseButton carSelectionButton = new MooseButton("Car Selection", () -> showSelectionMenu());
        MooseButton creditButton = new MooseButton("Credits", () -> fireNewGame());
        MooseButton quitButton = new MooseButton("Exit Game", () -> fireExit());

        MooseGameMenuTitle title = new MooseGameMenuTitle("Moose On The Loose");

        var box = new VBox(15, playButton, leaderboardButton, carSelectionButton, creditButton, quitButton);
        var titleBox = new VBox (title);
        var menuBox = new VBox(titleBox, box);

        titleBox.setTranslateX(30);
        titleBox.setTranslateY(200);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));

        box.setTranslateX(175);
        box.setTranslateY(400);
        box.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        box.setPadding(new Insets(50));
        box.setMaxWidth(0);

        return menuBox;
    }

    private void showMainMenu(){
//        Image img = new Image("file:Roadway.png");
//        ImageView bg = new ImageView(img);
//        getContentRoot().getChildren().add(0, bg);
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(mainMenuScreen);
    }

    private void showSelectionMenu(){
//        Image img = new Image("file:Roadway.png");
//        ImageView bg = new ImageView(img);
//        getContentRoot().getChildren().add(0, bg);
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(carSelectionScreen);
    }

    private Node carSelectionMenu(){
        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setPrefSize(300, 450);
        Button car1Button = new Button("Car 1");
        car1Button.setOnAction(((event) -> {SelectCar1();}));
        box.getChildren().add(car1Button);

        Button car2Button = new Button("Car 2");
        car2Button.setOnAction(((event) -> {SelectCar2();}));
        box.getChildren().add(car2Button);

        pane.getChildren().add(box);

        //Image img = new Image("Car_AM_General_Hummer_98x164.png");
        ImageView carView = new ImageView("file:Roadway.png");
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-background-color: black");
        rect.setX(300);
        pane.getChildren().add(rect);

        return pane;
    }

    private void SelectCar1(){

    }

    private void SelectCar2(){

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