import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity roadBackGround(SpawnData data){
        return entityBuilder()
                .from(data)
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
    public Entity newPotHole(SpawnData data) {
        return entityBuilder()
                .type(EntityType.POTHOLE)
                .from(data)
                .viewWithBBox("potHole.png")
                .with(new ProjectileComponent(new Point2D(0, 1.0), 50.0))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }

    @Spawns("moose")
    public Entity newMoose(SpawnData data) {
        return entityBuilder()
                .type(EntityType.MOOSE)
                .from(data)
                .viewWithBBox("moose.png")
                .with(new ProjectileComponent(new Point2D(-.8, 1), 80.0))
                .collidable()
                .build();
    }
}
