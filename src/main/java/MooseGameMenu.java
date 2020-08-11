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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.paint.Color.*;

/**
 * Class responsible for switching between all the created game menus.
 */
public class MooseGameMenu extends FXGLMenu {

    private Node mainMenuScreen;
    private Node leaderboardScreen;
    private StackPane carSelectionScreen;
    private Node creditsScreen;
    private Music bgm;
    private static String car = "assets/textures/car0.png";
    private SaveData savedData = null;

    /**
     * Constructor responsible for loading the menu when the game is first loaded.
     */
    public MooseGameMenu() {
        super(MenuType.MAIN_MENU);
        mainMenuScreen = createMainMenu();
        showMainMenu();
        carSelectionScreen = carSelectionMenu();
        creditsScreen = createCredits();
        bgm = getAssetLoader().loadMusic("Poisoncut_-_MenuMusic.mp3");
        getAudioPlayer().loopMusic(bgm);
    }

    /**
     * This node is created to display credits on the main menu.
     * @return
     */
    private Node createCredits(){
        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);

        MooseButton backButton = new MooseButton("Back", this::showMainMenu);
        backButton.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        backButton.setPadding(new Insets(25));
        backButton.setMaxWidth(0);

        MooseGameMenuTitle title = new MooseGameMenuTitle("Credits");

        Text credits = new Text("Micheal Power\n\nJoshua Newman\n\nTyler Tobin");
        credits.setFill(GRAY);
        credits.setFont(Font.font("deriveFont", FontWeight.EXTRA_BOLD, 35));

        VBox backButtonBox = new VBox (backButton);
        VBox titleBox = new VBox(title);
        VBox creditsBox = new VBox(credits);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));
        titleBox.setMaxWidth(0);
        titleBox.setMaxHeight(0);
        titleBox.setTranslateX(0);
        titleBox.setTranslateY(-250);

        creditsBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        creditsBox.setPadding(new Insets(75,25,75,25));
        creditsBox.setTranslateY(50);
        creditsBox.setTranslateX(15);
        creditsBox.setMaxWidth(0);
        creditsBox.setMaxHeight(0);

        pane.getChildren().add(backButtonBox);
        pane.getChildren().add(titleBox);
        pane.getChildren().add(creditsBox);
        return pane;
    }

    /**
     * responsible for initializing leaderoard from main menu.
     * @return
     */
    private StackPane leaderboardMenu(){

        getFileSystemService().<SaveData>readDataTask("./highscores.dat")
                .onSuccess(data -> savedData = data)
                .onFailure(ignore->{})
                .run();
        if (savedData == null){
            savedData = new SaveData(new ArrayList<HighScore>());
            savedData.getHighScoreList().add(new HighScore("Eric",100));
            savedData.getHighScoreList().add(new HighScore("Josh",50));
            savedData.getHighScoreList().add(new HighScore("Pranjal",0));
            System.out.println("new save data");
        }

        VBox scoreBox = new VBox();
        StackPane pane = new StackPane();
        pane.setPrefSize(600,900);
        pane.setAlignment(Pos.CENTER);
        VBox box = new VBox();
        box.setPrefSize(600, 900);
        MooseButton backButton = new MooseButton("Back", this::showMainMenu);
        backButton.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        backButton.setPadding(new Insets(25));
        backButton.setMaxWidth(0);
        box.getChildren().add(backButton);
        ArrayList<HighScore> highScores = savedData.getHighScoreList();
        int y = 0;
        for (int i = 0; i < highScores.size(); i++) { ;
            Text textName = new Text((i+1)+". " + highScores.get(i).getName());
            Text textHighScore = new Text("Score: " + Integer.toString(highScores.get(i).getScore()) + "\n" +"--------------------");
            textName.setFill(GRAY);
            textHighScore.setFill(GRAY);
            textName.setFont(Font.font("deriveFont", FontWeight.EXTRA_BOLD, 35));
            textHighScore.setFont(Font.font("deriveFont", FontWeight.EXTRA_BOLD, 35));
            VBox textNameBox = new VBox(textName);
            VBox textHighScoreBox = new VBox(textHighScore);
            y+=10;
            scoreBox.setAlignment(Pos.CENTER_LEFT);
            scoreBox.getChildren().add(textNameBox);
            scoreBox.getChildren().add(textHighScoreBox);
        }

        MooseGameMenuTitle title = new MooseGameMenuTitle("Leaderboard");
        VBox titleBox = new VBox(title);
        titleBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        titleBox.setPadding(new Insets(10));
        titleBox.setMaxWidth(0);
        titleBox.setMaxHeight(0);
        titleBox.setTranslateX(0);
        titleBox.setTranslateY(-300);

        scoreBox.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
        scoreBox.setPadding(new Insets(0,10,0,10));
        scoreBox.setMaxWidth(0);
        scoreBox.setMaxHeight(500);
        pane.getChildren().add(box);
        pane.getChildren().add(titleBox);
        pane.getChildren().add(scoreBox);
        return pane;
    }

    private Node createMainMenu(){
        MooseButton playButton = new MooseButton("Play Game", () -> {fireNewGame();getAudioPlayer().stopMusic(bgm);});
        MooseButton leaderboardButton = new MooseButton("Leaderboard", () -> showLeaderBoardMenu());
        MooseButton carSelectionButton = new MooseButton("Car Selection", () -> showSelectionMenu());
        MooseButton creditButton = new MooseButton("Credits", () -> {showCredits();getAudioPlayer().stopMusic(bgm);});
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
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(mainMenuScreen);
    }

    private void showCredits(){
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(creditsScreen);
    }

    private void showSelectionMenu(){
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(carSelectionScreen);
    }

    /***
     * Class that shows leaderboard menu.
     */
    private void showLeaderBoardMenu(){
        leaderboardScreen = leaderboardMenu();
        getMenuContentRoot().getChildren().clear();
        getMenuContentRoot().getChildren().addAll(leaderboardScreen);
    }

    /***
     * Class that shows car selection menu.
     */
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

    /***
     * Class that gets the car the next car present in the menu.
     */
    private void nextCar(){
        int carNum = Character.getNumericValue(car.charAt(19));
        int nextCarNum = (carNum + 1)%10;
        car = "assets/textures/car".concat(Integer.toString(nextCarNum)).concat(".png");
        carSelectionScreen.getChildren().remove(2);
        Image img = new Image(car);
        ImageView carView = new ImageView(img);
        carSelectionScreen.getChildren().add(carView);
    }

    /***
     * Class that gets the car the previous car present in the menu.
     */
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

    /***
     * Class that returns the car selected by the player.
     */
    public static String playerCar(){
        return car;
    }
}