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
                .viewWithBBox("car1.png")
                .with(new PlayerComponent())
                .collidable()
                .build();
    }

    @Spawns("signPost")
    public static Entity newSignPost(SpawnData data){
        return entityBuilder()
                .type(EntityType.SIGNPOST)
                .at(getAppWidth()-125,100)
                .viewWithBBox("signpost.png")
                .collidable()
                .build();
    }

    @Spawns("potHole")
    public static Entity newPotHole(SpawnData data) {
        return entityBuilder()
                .type(EntityType.POTHOLE)
                .at(FXGLMath.random(75,getAppWidth()-150),-100)
                .viewWithBBox("potHole.png")
                .with(new PotHoleComponent())
                .collidable()
                .build();
    }

    @Spawns("gasTank")
    public static Entity newGasTank(SpawnData data){
        return entityBuilder()
                .type(EntityType.GASTANK)
                .at(FXGLMath.random(75,getAppWidth()-300),-150)
                .viewWithBBox("gasTank.PNG")
                .scale(.8,.8)
                .with(new GasTankComponent())
                .collidable()
                .build();
    }

    @Spawns("leftMoose")
    public static Entity newMoose(SpawnData data) {
        AnimatedTexture view = texture("mooseWalkAnimation.png").toAnimatedTexture(10, Duration.seconds(1));

        int spawnX;
        int spawnY = random(-100,300);

        if (spawnY > -50){
            spawnX = getAppWidth();
        }
        else{
            spawnX = random(getAppWidth()/2,getAppWidth());
        }

        return entityBuilder()
                .type(EntityType.LEFTMOOSE)
                .at(spawnX,spawnY)
                .viewWithBBox(view.loop())
                .with(new MooseComponent())
                .collidable()
                .build();
    }
    @Spawns("rightMoose")
    public static Entity newRightMoose(SpawnData data) {
        AnimatedTexture view = texture("mooseWalkAnimation.png").toAnimatedTexture(10, Duration.seconds(1));

        int spawnX;
        int spawnY = random(-100,300);

        if (spawnY > -50){
            spawnX = -100;
        }
        else{
            spawnX = random(-100,(getAppWidth()/2)-45);
        }

        return entityBuilder()
                .type(EntityType.RIGHTMOOSE)
                .at(spawnX,spawnY)
                .viewWithBBox(view.loop())
                .scale(-1,1)
                .with(new MooseComponent())
                .collidable()
                .build();
    }
}
