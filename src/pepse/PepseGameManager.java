package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;

import java.util.Random;

public class PepseGameManager extends GameManager {
    public static final int MIN_X = 0;
    private Vector2 windowDimensions;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        // initialize fields
        this.windowDimensions = windowController.getWindowDimensions();
        // making sky
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        // making ground
        Terrain terrain = new Terrain(windowDimensions,(int)new Random().nextGaussian());
        for (Block block: terrain.createInRange(MIN_X,(int)windowDimensions.x())){
            gameObjects().addGameObject(block,Layer.STATIC_OBJECTS);
        }
        // making night
        GameObject night = Night.create(windowDimensions,30.0f);
        gameObjects().addGameObject(night, Layer.FOREGROUND);  // appear in front of the main gameplay or background elements
    }
}
