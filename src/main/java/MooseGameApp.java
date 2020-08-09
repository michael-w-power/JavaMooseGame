
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.collection.Array;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UI;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Main game loop app for Moose on the Loose.
 */
public class MooseGameApp extends GameApplication {
    private Entity player;
    private Entity background1;
    private Entity background2;
    private Entity signpost;
    private Music bgm;
    private SaveData savedData = null;
    private int highScore;
    private String highScoreName;
    private ArrayList<HighScore> highScoreList;


    /**
     * Initializes the game settings
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(600);
        settings.setHeight(900);
        settings.setTitle("MooseGame");
        settings.setVersion("0.3");
        settings.setMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory(){
            @Override
            public FXGLMenu newMainMenu() {
                return new MooseGameMenu();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("time",60);
        vars.put("speed",150);
    }

    @Override
    protected void initUI(){
        UI ui = getAssetLoader().loadUI("moosegame_ui.fxml", new MooseGameUIController());
        getGameScene().addUI(ui);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.S, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).moveRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).moveUp());

        onKey(KeyCode.DIGIT1, () -> player.getComponent(PlayerComponent.class).gearOne());
        onKey(KeyCode.DIGIT2, () -> player.getComponent(PlayerComponent.class).gearTwo());
        onKey(KeyCode.DIGIT3, () -> player.getComponent(PlayerComponent.class).gearThree());

        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
    }

    @Override
    protected void initGame(){

        getGameWorld().addEntityFactory(new GameEntityFactory());


        getFileSystemService().<SaveData>readDataTask("./highscores.dat")
                .onSuccess(data -> savedData = data)
                .onFailure(error->{error.printStackTrace();})
                .run();
        if (savedData == null){
            savedData = new SaveData(new ArrayList<HighScore>());
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            savedData.getHighScoreList().add(new HighScore("CPU",0));
            System.out.println("new scorelist created.");
        }
        initGame(savedData);
    }


    protected void initGame(SaveData saveData){
        bgm = getAssetLoader().loadMusic("Poisoncut_GameLoop.mp3");
        getAudioPlayer().loopMusic(bgm);

        highScoreList = saveData.getHighScoreList();

        background1 = spawn("background");
        background2 = spawn("background2");
        player = spawn("player",350,700);
        signpost = spawn("signPost");

        run(()->spawn("potHole"),Duration.seconds(random(5,10)));
        run(()->spawn("leftMoose"),Duration.seconds(random(3,10)));
        run(()->spawn("rightMoose"),Duration.seconds(random(3,10)));
        run(()->spawn("gasTank"),Duration.seconds(random(10,15)));

        run(()->FXGL.getWorldProperties().setValue("time",FXGL.getWorldProperties().getInt("time")-1),Duration.seconds(2));
    }

    protected void initPhysics(){

        onCollisionBegin(EntityType.PLAYER,EntityType.GASTANK, (player, gastank) -> {
            gastank.removeFromWorld();
            if (getWorldProperties().getInt("speed")==75){
                FXGL.getWorldProperties().increment("score", 125);
            }
            else if (getWorldProperties().getInt("speed")==150){
                FXGL.getWorldProperties().increment("score", 250);
            }
            else if (getWorldProperties().getInt("speed")==225){
                FXGL.getWorldProperties().increment("score", 375);
            }
        });

        onCollisionBegin(EntityType.PLAYER,EntityType.POTHOLE, (player, pothole) -> {
            pothole.removeFromWorld();
            if (getWorldProperties().getInt("speed")==75){
                FXGL.getWorldProperties().increment("score", -50);
            }
            else if (getWorldProperties().getInt("speed")==150){
                FXGL.getWorldProperties().increment("score", -100);
            }
            else if (getWorldProperties().getInt("speed")==225){
                FXGL.getWorldProperties().increment("score", -150);
            }
        });

        onCollisionBegin(EntityType.PLAYER,EntityType.LEFTMOOSE, (player, moose) -> {
            moose.removeFromWorld();
            //FXGL.getWorldProperties().increment("score", -1000);
            gameOver();
        });

        onCollisionBegin(EntityType.PLAYER,EntityType.RIGHTMOOSE, (player, moose) -> {
            moose.removeFromWorld();
            //FXGL.getWorldProperties().increment("score", -1000);
            gameOver();

        });

        onCollisionBegin(EntityType.PLAYER,EntityType.SIGNPOST, (player, signpost) -> {
            //FXGL.getWorldProperties().increment("score", -1000);
            gameOver();

        });
    }

    @Override
    protected void onUpdate(double tpf){
        getGameWorld().getEntitiesByType(EntityType.GASTANK).forEach(gastank -> gastank.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));

        getGameWorld().getEntitiesByType(EntityType.POTHOLE).forEach(pothole -> pothole.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));

        getGameWorld().getEntitiesByType(EntityType.SIGNPOST).forEach(pothole -> pothole.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));

        getGameWorld().getEntitiesByType(EntityType.LEFTMOOSE).forEach(moose -> moose.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));
        getGameWorld().getEntitiesByType(EntityType.LEFTMOOSE).forEach(moose -> moose.translateX(-2));

        getGameWorld().getEntitiesByType(EntityType.RIGHTMOOSE).forEach(moose -> moose.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));
        getGameWorld().getEntitiesByType(EntityType.RIGHTMOOSE).forEach(moose -> moose.translateX(2));

        background1.translateY(FXGL.getWorldProperties().getInt("speed") * tpf);
        background2.translateY(FXGL.getWorldProperties().getInt("speed") * tpf);


        if (background1.getY() >= 1350){
            background1.setY(background1.getY()-2700);
        }

        if (background2.getY() >= 1350){
            background2.setY(background2.getY()-2700);
        }

        if (FXGL.getWorldProperties().getInt("time")<=0){
            getDialogService().showMessageBox("You win!! Press OK to return to menu", getGameController()::gotoMainMenu);
        }
    }

    private void gameOver() {


        Point2D explosionSpawnPoint = player.getPosition().subtract(30, 0);
        spawn("explosion", explosionSpawnPoint);


        getAudioPlayer().stopMusic(bgm);
        String[] mooseFacts = {"The province of NL experiences over 700 \nmoose vehicle accidents per year on average.",
                "On average there is approximately 40000 \nmoose calves born in the province of NL each year.",
                "Motor-vehicle accidents involving moose are \n13 times more likely to result in death than crashes with deer.",
                "The majority of moose vehicle accidents \noccur between dusk and dawn, \nwhen moose are most active and visibility is low",
                "This is the time when driver visibility \nis severely limited by darkness, and when moose are most active.",
                "Moose crossing signs mark High-Risk areas. \nSlow down and watch for moose."};

        String randomMessage = mooseFacts[FXGLMath.random(0,3)];
        getDialogService().showConfirmationBox("Did you know?\n"+randomMessage+"\nPlay Again?", yes -> {
            if (yes) {
                getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
                getGameController().startNewGame();
            } else {

                int score = FXGL.getWorldProperties().getInt("score");
                if (score > highScoreList.get(highScoreList.size()-1).getScore()) {
                    getDialogService().showInputBox("High Score! Enter your name", playerName -> {
                        for (int i = 0; i < highScoreList.size(); i++) {
                            if (score > highScoreList.get(i).getScore()){
                                highScoreList.add(i,new HighScore(playerName,score));
                                highScoreList.sort(new sortHighScores());
                                highScoreList.remove(highScoreList.size()-1);
                                break;
                            }
                            System.out.println(highScoreList.get(i).getName());
                        }
                        getFileSystemService().writeDataTask(new SaveData(highScoreList), "./highscores.dat").run();
                        getGameController().gotoMainMenu();
                    });
                } else {
                    getGameController().gotoMainMenu();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
