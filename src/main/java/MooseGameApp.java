
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UI;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Main app for the Moose game.
 */
public class MooseGameApp extends GameApplication {
    private Entity player;
    private Entity background1;
    private Entity background2;


    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(600);
        settings.setHeight(900);
        settings.setTitle("MooseGame");
        settings.setVersion("0.2");
        settings.setMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
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

        onKey(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).slowDown());
        //onKey(KeyCode.C, () -> player.getComponent(PlayerComponent.class).regularSpeed());

        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
    }

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new GameEntityFactory());
        background1 = spawn("background");
        background2 = spawn("background2");
        player = spawn("player",350,700);
        run(()->spawn("potHole"),Duration.seconds(5));
        run(()->spawn("moose"),Duration.seconds(10));
    //run(() -> spawn("moose",900,random(-300,300)), Duration.seconds(10));
    }

    @Override
    protected void initPhysics(){
        onCollisionBegin(EntityType.PLAYER,EntityType.POTHOLE, (player, pothole) -> {
            pothole.removeFromWorld();
            FXGL.getWorldProperties().increment("score", -100);
        });

        onCollisionBegin(EntityType.PLAYER,EntityType.MOOSE, (player, moose) -> {
            moose.removeFromWorld();
            FXGL.getWorldProperties().increment("score", -1000);
            getDialogService().showMessageBox("Game Over. Press OK to exit", getGameController()::exit);

        });
    }

    @Override
    protected void onUpdate(double tpf){


        getGameWorld().getEntitiesByType(EntityType.POTHOLE).forEach(pothole -> pothole.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));
        getGameWorld().getEntitiesByType(EntityType.MOOSE).forEach(moose -> moose.translateY(FXGL.getWorldProperties().getInt("speed")*tpf));
        getGameWorld().getEntitiesByType(EntityType.MOOSE).forEach(moose -> moose.translateX(-2));

        background1.translateY(FXGL.getWorldProperties().getInt("speed") * tpf);
        background2.translateY(FXGL.getWorldProperties().getInt("speed") * tpf);



        if (background1.getY() >= 1350){
            background1.setY(background1.getY()-2700);
        }

        if (background2.getY() >= 1350){
            background2.setY(background2.getY()-2700);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
