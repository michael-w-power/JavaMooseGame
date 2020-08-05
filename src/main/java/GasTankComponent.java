import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
public class GasTankComponent extends Component{

    @Override
    public void onAdded() {

    }

    @Override
    public void onUpdate(double tpf) {
        if (this.getEntity().getY() >= 900){
            FXGL.getWorldProperties().increment("score", 250);
            this.getEntity().removeFromWorld();
        }
    }
}