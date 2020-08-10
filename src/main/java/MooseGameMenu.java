import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
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
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.paint.Color.*;

public class MooseGameMenu extends FXGLMenu {

    private Node mainMenuScreen;
    private Node leaderboardScreen;
    private StackPane carSelectionScreen;
    private Music bgm;
    private static String car = "assets/textures/car0.png";
    private SaveData savedData = null;

    public MooseGameMenu() {
        super(MenuType.MAIN_MENU);
        mainMenuScreen = createMainMenu();
        showMainMenu();
        carSelectionScreen = carSelectionMenu();
        leaderboardScreen = leaderboardMenu();
        bgm = getAssetLoader().loadMusic("Poisoncut_-_MenuMusic.mp3");
        getAudioPlayer().loopMusic(bgm);
    }

    private StackPane leaderboardMenu(){

        getFileSystemService().<SaveData>readDataTask("./highscores.dat")
                .onSuccess(data -> savedData = data)
                .onFailure(ignore->{})
                .run();
        if (savedData == null){
            savedData = new SaveData(new ArrayList<HighScore>());
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            System.out.println("new save data");
        }

        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);
        ArrayList<HighScore> highScores = savedData.getHighScoreList();
        int y = 0;
        for (int i = 0; i < highScores.size(); i++) { ;
            Text text = new Text(i+".\t" + highScores.get(i).getName() +"\t\t\t" + Integer.toString(highScores.get(i).getScore()));
            text.setTranslateY(y);
            y+=10;
            pane.getChildren().add(text);
        }
        return pane;
    }

    private Node createMainMenu(){
        MooseButton playButton = new MooseButton("Play Game", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton leaderboardButton = new MooseButton("Leaderboard", () -> showLeaderBoardMenu());
        MooseButton carSelectionButton = new MooseButton("Car Selection", () -> showSelectionMenu());
        MooseButton creditButton = new MooseButton("Credits", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton quitButton = new MooseButton("Exit Game", this::fireExit);

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


        return new VBox(titleBox, box);
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

    private void showLeaderBoardMenu(){
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(leaderboardScreen);
    }

    private StackPane carSelectionMenu(){
        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setPrefSize(600, 900);

        MooseButton backButton = new MooseButton("Back", this::showMainMenu);
        //backButton.setOnAction((event) -> {showMainMenu();});
        backButton.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        backButton.setPadding(new Insets(25));
        backButton.setMaxWidth(0);
        box.getChildren().add(backButton);

        MooseGameMenuTitle carSelectionTitle = new MooseGameMenuTitle("Car Selection");
        var titleBox = new VBox(carSelectionTitle);
        titleBox.setTranslateX(125);
        titleBox.setTranslateY(75);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));
        titleBox.setMaxWidth(0);
        box.getChildren().add(titleBox);


        MooseButton prevCarButton = new MooseButton("Prev Car", this::prevCar);
        //prevCarButton.setOnAction((event) -> { prevCar();});
        var prevCarBox = new VBox(15, prevCarButton);
        prevCarBox.setTranslateX(50);
        prevCarBox.setTranslateY(260);
        prevCarBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        prevCarBox.setPadding(new Insets(25));
        prevCarBox.setMaxWidth(0);
        box.getChildren().add(prevCarBox);


        MooseButton nextCarButton = new MooseButton("Next Car", this::nextCar);
        //nextCarButton.setOnAction((event) -> { nextCar();});
        var nextCarBox = new VBox(0, nextCarButton);
        nextCarBox.setTranslateX(400);
        nextCarBox.setTranslateY(180);
        nextCarBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        nextCarBox.setPadding(new Insets(25));
        nextCarBox.setMaxWidth(0);
        box.getChildren().add(nextCarBox);

        pane.getChildren().add(box);

        Rectangle carViewBackground = new Rectangle(200,150,150,200);
        carViewBackground.setFill(GRAY);
        Image img = new Image(car);
        ImageView carView = new ImageView(img);

        pane.getChildren().add(carViewBackground);
        pane.getChildren().add(carView);
        //pane.getChildren().add(carViewBackground);

        return pane;
    }

    private void nextCar(){
        int carNum = Character.getNumericValue(car.charAt(19));
        int nextCarNum = (carNum + 1)%10;
        car = "assets/textures/car".concat(Integer.toString(nextCarNum)).concat(".png");
        carSelectionScreen.getChildren().remove(2);
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
        car = "assets/textures/car".concat(Integer.toString(nextCarNum)).concat(".png");
        carSelectionScreen.getChildren().remove(2);
        Image img = new Image(car);
        ImageView carView = new ImageView(img);
        carSelectionScreen.getChildren().add(carView);
    }


    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding stringBinding, @NotNull Runnable runnable) {
        return new Button();
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull String s, @NotNull Runnable runnable) {
        return new Button();
    }

    @NotNull
    @Override
    protected Node createBackground(double w, double h) {
        return FXGL.texture("Roadway.png", 600, 900);
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String s) {
        return new Rectangle();
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String s) {
        return new Rectangle();
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String s) {
        return new Rectangle();
    }

    public static String playerCar(){
        return car;
    }
}