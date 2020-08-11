import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

/**
 * Pothole component class responsible for removing dodged potholes from the screen.
 */
public class PotHoleComponent extends Component {

    @Override
    public void onAdded() {

    }

    /**
     * Method runs on every frame to check if entity is out of bounds.
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        if (this.getEntity().getY() >= 900){
            this.getEntity().removeFromWorld();
        }
    }
}