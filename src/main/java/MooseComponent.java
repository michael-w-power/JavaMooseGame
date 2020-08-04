import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class MooseComponent extends Component {

    @Override
    public void onAdded() {

    }

    @Override
    public void onUpdate(double tpf) {
        if ((this.getEntity().getY() >= 900)||(this.getEntity().getX() >= 600)||(this.getEntity().getX() <= -100)){
            FXGL.getWorldProperties().increment("score", 1000);
            this.getEntity().removeFromWorld();
        }
    }
}