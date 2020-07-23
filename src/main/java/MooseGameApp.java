
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

/**
 * Main app for the Moose game.
 */
public class MooseGameApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings){
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setTitle("MooseGame");
        settings.setVersion("0.1");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
