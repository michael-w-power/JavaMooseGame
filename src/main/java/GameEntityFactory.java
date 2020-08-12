import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Entity factory that handles the spawn calls for each entity.
 */
public class GameEntityFactory implements EntityFactory {
    /**
     * Spawns the first part of the background, the first one you see when you load up the game.
     * @param data unused, could be passed information about where to spawn.
     * @return background entity
     */
    @Spawns("background")
    public Entity roadBackGround(SpawnData data){
        return entityBuilder()
                .at(0,0)
                .view("road_with_patches.png")
                .build();
    }
    /**
     * Spawns the second part of the background, that spawns offscreen. Used for looping the background.
     * @param data unused, could be passed information about where to spawn.
     * @return background2 entity
     */
    @Spawns("background2")
    public Entity roadBackGround2(SpawnData data) {
        return entityBuilder()
                .at(0, -1350)
                .view("road_with_patches.png")
                .build();
    }

    /**
     * Spawns the player.
     * @param data used to set spawn location
     * @return player entity
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        return entityBuilder()
                .type(EntityType.PLAYER)
                .from(data)
                .viewWithBBox(MooseGameMenu.playerCar().substring(16))
                .with(new PlayerComponent())
                .collidable()
                .build();
    }

    /**
     * Spawns a signpost, seen at the beginning of the game
     * @param data used to set spawn location
     * @return signPost entity
     */
    @Spawns("signPost")
    public static Entity newSignPost(SpawnData data){
        return entityBuilder()
                .type(EntityType.SIGNPOST)
                .at(getAppWidth()-125,100)
                .viewWithBBox("signpost.png")
                .collidable()
                .build();
    }

    /**
     * Spawns a pothole
     * @param data used to set spawn location
     * @return pothole entity
     */
    @Spawns("potHole")
    public static Entity newPotHole(SpawnData data) {
        return entityBuilder()
                .type(EntityType.POTHOLE)
                .at(FXGLMath.random(75,getAppWidth()-150),-100)
                .viewWithBBox("pothole.png")
                .scale(0.6,0.6)
                .with(new PotHoleComponent())
                .collidable()
                .build();
    }

    /**
     * Spawns a gas tank
     * @param data used to set spawn location
     * @return gas tank entity
     */
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

    /**
     * Spawns a moose on the Right side of the screen that faces Left
     * @param data unused, could be used to set spawn location
     * @return left moose entity
     */
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

    /**
     * Spawns a moose on the left side of the screen that faces right
     * @param data unused, could be used to set spawn location
     * @return right moose entity
     */
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

    /**
     * Spawns an explosion, used when the player crashes.
     * @param data used to set spawn location
     * @return explosion entity
     */
    @Spawns("explosion")
    public static Entity newExplosion(SpawnData data){

        play("explosion.wav");

        var emitter = ParticleEmitters.newExplosionEmitter(350);
        emitter.setMaxEmissions(1);
        emitter.setSize(2, 10);
        emitter.setStartColor(Color.YELLOW);
        emitter.setEndColor(Color.RED);
        emitter.setSpawnPointFunction(i -> new Point2D(64, 64));

        return entityBuilder()
                .from(data)
                .view(texture("explosion.png").toAnimatedTexture(16, Duration.seconds(0.66)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .with(new ParticleComponent(emitter))
                .scale(1.4,1.4)
                .build();
    }
}
