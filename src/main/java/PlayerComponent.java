import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGL.play;

/**
 * Player class
 */
public class PlayerComponent extends Component {

    @Override
    public void onAdded(){
    }

    @Override
    public void onUpdate(double tpf){

    }

    /**
     * Moves the player left.
     */
    public void moveLeft() {
        entity.translate(-2.5,0);
    }

    /**
     * Moves the player right.
     */
    public void moveRight() {
        entity.translate(2.5,0);
    }

    /**
     * Moves the player up.
     */
    public void moveUp(){
        entity.translate(0,-2.5);
    }

    /**
     * Moves the player down.
     */
    public void moveDown(){
        entity.translate(0,2.5);
    }

    /**
     * Sets the gamespeed to 75, or "Gear1"
     */
    public void gearOne(){
        FXGL.getWorldProperties().setValue("speed",75);
    }

    /**
     * Sets the gamespeed to 150, or "Gear2"
     */
    public void gearTwo(){
        FXGL.getWorldProperties().setValue("speed",150);
    }

    /**
     * Sets the gamespeed to 225, or "Gear3"
     */
    public void gearThree(){
        FXGL.getWorldProperties().setValue("speed",225);
    }
}
