import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
/**
 * Pothole component class responsible for removing dodged potholes from the screen.
 */

public class MooseComponent extends Component {

    @Override
    public void onAdded() {

    }

    /**
     * method responsible for removing Moose from the game world and adds score appropriately for missing moose.
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        if ((this.getEntity().getY() >= 900)||(this.getEntity().getX() >= 650)||(this.getEntity().getX() <= -101)){
            FXGL.getWorldProperties().increment("score", 100);
            this.getEntity().removeFromWorld();
        }
    }
}