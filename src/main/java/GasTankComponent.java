import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

/**
 * gasTank component class responsible for removing gasCans from the screen .
 */
public class GasTankComponent extends Component{

    @Override
    public void onAdded() {

    }

    /**
     * method responsible for removing gasCans from the game world and adds score appropriately.
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        if (this.getEntity().getY() >= 900){
            FXGL.getWorldProperties().increment("score", 250);
            this.getEntity().removeFromWorld();
        }
    }
}