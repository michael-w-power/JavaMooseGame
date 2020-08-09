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

    public void moveLeft() {
        entity.translate(-2,0);
    }

    public void moveRight() {
        entity.translate(2,0);
    }

    public void moveUp(){
        entity.translate(0,-2);
    }

    public void moveDown(){
        entity.translate(0,2);
    }

    public void gearOne(){
        FXGL.getWorldProperties().setValue("speed",75);
    }

    public void gearTwo(){
        FXGL.getWorldProperties().setValue("speed",150);
    }

    public void gearThree(){
        FXGL.getWorldProperties().setValue("speed",225);
    }
}
