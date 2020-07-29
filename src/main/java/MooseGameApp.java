
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Main app for the Moose game.
 */
public class MooseGameApp extends GameApplication {
    private Entity player;

    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(600);
        settings.setHeight(900);
        settings.setTitle("MooseGame");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.S, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).moveRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).moveUp());

        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        onKey(KeyCode.DOWN, () -> player.getComponent(PlayerComponent.class).moveDown());
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());
        onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).moveUp());
    }

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("background");
        player = spawn("player",150,700);
        run(() -> spawn("potHole",random(50,400) ,0).rotateBy(90), Duration.seconds(random(1,2)));
    //run(() -> spawn("moose",900,random(-300,300)), Duration.seconds(10));
    }

    @Override
    protected void initPhysics(){
        onCollisionBegin(EntityType.PLAYER,EntityType.POTHOLE, (player, pothole) -> {
            pothole.removeFromWorld();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
