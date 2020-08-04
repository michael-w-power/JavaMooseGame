import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity roadBackGround(SpawnData data){
        return entityBuilder()
                .at(0,0)
                .view("road_with_patches.png")
                .build();
    }
    @Spawns("background2")
    public Entity roadBackGround2(SpawnData data) {
        return entityBuilder()
                .at(0, -1350)
                .view("road_with_patches.png")
                .build();
    }
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        return entityBuilder()
                .type(EntityType.PLAYER)
                .from(data)
                .viewWithBBox("Car_Audi_Sport_Quattro_86x145.png")
                .with(new PlayerComponent())
                .collidable()
                .build();
    }

    @Spawns("potHole")
    public static Entity newPotHole(SpawnData data) {
        return entityBuilder()
                .type(EntityType.POTHOLE)
                .at(FXGLMath.random(75,getAppWidth()-150),-100)
                .viewWithBBox("potHole.png")
                .scale(.8,.8)
                .with(new PotHoleComponent())
                .collidable()
                .build();
    }

    @Spawns("moose")
    public static Entity newMoose(SpawnData data) {
        AnimatedTexture view = texture("mooseWalkAnimation.png").toAnimatedTexture(10, Duration.seconds(0.5));
        return entityBuilder()
                .type(EntityType.MOOSE)
                .at(FXGLMath.random(getAppWidth()/2,getAppWidth()),-100)
                .viewWithBBox(view.loop())
                .with(new MooseComponent())
                .collidable()
                .build();
    }
}
