import com.almasb.fxgl.entity.component.Component;

/**
 * Player class
 */
public class PlayerComponent extends Component {

    @Override
    public void onAdded(){
        ;
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

}
