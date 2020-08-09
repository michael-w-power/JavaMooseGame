import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.core.math.FXGLMath;
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

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.paint.Color.*;

public class MooseGameMenu extends FXGLMenu {

    private Node mainMenuScreen;
    private Node leaderboardScreen;
    private StackPane carSelectionScreen;
    private Music bgm;
    private String car = "assets/textures/car0.png";

    public MooseGameMenu() {
        super(MenuType.MAIN_MENU);
        mainMenuScreen = createMainMenu();
        showMainMenu();
        carSelectionScreen = carSelectionMenu();
        bgm = getAssetLoader().loadMusic("Poisoncut_-_MenuMusic.mp3");
        getAudioPlayer().loopMusic(bgm);
    }

//    private Node leaderboardMenu(){
//
//    }

    private Node createMainMenu(){
        MooseButton playButton = new MooseButton("Play Game", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton leaderboardButton = new MooseButton("Leaderboard", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton carSelectionButton = new MooseButton("Car Selection", () -> showSelectionMenu());
        MooseButton creditButton = new MooseButton("Credits", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton quitButton = new MooseButton("Exit Game", () -> fireExit());

        MooseGameMenuTitle title = new MooseGameMenuTitle("Moose On The Loose");

        var box = new VBox(15, playButton, leaderboardButton, carSelectionButton, creditButton, quitButton);
        var titleBox = new VBox (title);


        titleBox.setTranslateX(30);
        titleBox.setTranslateY(200);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));

        box.setTranslateX(175);
        box.setTranslateY(400);
        box.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        box.setPadding(new Insets(50));
        box.setMaxWidth(0);


        var menuBox = new VBox(titleBox, box);
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

    private StackPane carSelectionMenu(){
        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setPrefSize(600, 900);
        Button car1Button = new Button("Prev Car");
        car1Button.setOnAction((event) -> {
            prevCar();});
        box.getChildren().add(car1Button);

        Button car2Button = new Button("Next Car");
        car2Button.setOnAction((event) -> {
            nextCar();});
        box.getChildren().add(car2Button);

        pane.getChildren().add(box);

        Image img = new Image(car);
        ImageView carView = new ImageView(img);
        pane.getChildren().add(carView);

        return pane;
    }

    private void nextCar(){
        int carNum = Character.getNumericValue(car.charAt(19));
        int nextCarNum = (carNum + 1)%10;

        System.out.println("car.charat:"+car.charAt(19));
        System.out.println("carnum:"+carNum);
        System.out.println("nextcarnum:"+nextCarNum);
        car = "assets/textures/car".concat(Integer.toString(nextCarNum)).concat(".png");
        carSelectionScreen.getChildren().remove(1);
        Image img = new Image(car);
        ImageView carView = new ImageView(img);
        carSelectionScreen.getChildren().add(carView);
    }

    private void prevCar(){
        int carNum = Character.getNumericValue(car.charAt(19));
        if (carNum == 0){
            carNum = 10;
        }
        int nextCarNum = (carNum - 1)%10;
        System.out.println("car.charat:"+car.charAt(19));
        System.out.println("carnum:"+carNum);
        System.out.println("nextcarnum:"+nextCarNum);
        car = "assets/textures/car".concat(Integer.toString(nextCarNum)).concat(".png");
        carSelectionScreen.getChildren().remove(1);
        Image img = new Image(car);
        ImageView carView = new ImageView(img);
        carSelectionScreen.getChildren().add(carView);
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
}