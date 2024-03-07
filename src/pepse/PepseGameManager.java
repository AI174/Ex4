package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Sky;

public class PepseGameManager extends GameManager {
    private Vector2 windowDimensions;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        // initialize fields
        this.windowDimensions = windowController.getWindowDimensions();
        // making sky
        GameObject sky = Sky.creat(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }
}
