import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class PotHoleComponent extends Component {

    @Override
    public void onAdded() {

    }

    @Override
    public void onUpdate(double tpf) {
        if (this.getEntity().getY() >= 900){
            FXGL.getWorldProperties().increment("score", 100);
            this.getEntity().removeFromWorld();
        }
    }
}