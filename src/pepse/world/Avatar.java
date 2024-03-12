package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Represents the avatar character in the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see AnimationRenderable
 * @see KeyEvent
 */
public class Avatar extends GameObject {
    /**
     * The height of the avatar.
     */
    public static final int AVATAR_HEIGHT = 60;
    /**
     * The width of the avatar.
     */
    public static final int AVATAR_WIDTH = 40 ;
    private static final float VELOCITY_X = 300;
    private static final float VELOCITY_Y = -450;
    private static final float GRAVITY = 500;
    private static final float CHANGING_TIME = .4f;
    private static final String AVATAR_TAG = "avatar";
    private static final String AVATAR_IMAGE = "assets/idle_0.png";
    private static final String AVATAR_ITH_IMAGE = "assets/idle_";
    private static final String PNG = ".png";
    private static final String AVATAR_JUMP_IMAGE = "assets/jump_";
    private static final String AVATAR_RAN_IMAGE = "assets/run_";
    private static Renderable imageRenderable;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private final ArrayList<AvatarObserver> observerList = new ArrayList<>();
    private float energy = 100;
    private AnimationRenderable notMovingAnimation;
    private AnimationRenderable upDownAnimation;
    private AnimationRenderable sideAnimation;

    /**
     * Constructs an avatar object with the specified position, input listener, and image reader.
     * @param pos The position of the avatar.
     * @param inputListener The input listener for handling user input.
     * @param imageReader The image reader for reading avatar images.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos.add(new Vector2(-AVATAR_WIDTH, -AVATAR_HEIGHT)),
                new Vector2(AVATAR_WIDTH, AVATAR_HEIGHT), imageRenderable);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        transform().setAccelerationY(GRAVITY);
        this.setTag(AVATAR_TAG);
        initializeAnimation();

    }
    /**
     * Creates an avatar object with the specified position, input listener, and image reader.
     * @param downRightCorner The bottom-right corner position of the avatar.
     * @param inputListener The input listener for handling user input.
     * @param imageReader The image reader for reading avatar images.
     * @return An avatar object.
     */
    public static Avatar create(Vector2 downRightCorner,UserInputListener inputListener,
                                ImageReader imageReader) {

        Avatar.imageRenderable = imageReader.readImage(AVATAR_IMAGE, true);
        Avatar avatar = new Avatar(downRightCorner, inputListener, imageReader);
        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        return avatar;

    }

    /**
     * Updates the avatar's state based on the elapsed time.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     * Used for determining new positions/velocities.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        move();
        createAnimation();
    }
    /**
     * Adds energy to the avatar.
     * @param n The amount of energy to add.
     */
    public void addEnergy(float n){
        energy = Math.min(100, energy + n);
    }
    /**
     * Gets the energy
     * @return the avatar's energy
     */
    public float getEnergy(){
        return energy;
    }
    /**
     * Registers an observer to observe the avatar's actions.
     * @param observer The observer to register.
     */
    public void registerObserver(AvatarObserver observer){
        observerList.add(observer);
    }
    private void move() {
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy >= .5f ) {
            xVel -= VELOCITY_X;
            renderer().setIsFlippedHorizontally(true);
            energy -= .5f;
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy >= .5f ) {
            xVel += VELOCITY_X;
            renderer().setIsFlippedHorizontally(false);
            energy -= .5f;

        }
        transform().setVelocityX(xVel);

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy >= 10){
            transform().setVelocityY(VELOCITY_Y);
            energy -= 10f;
            updateObservers();
        }

        if(getVelocity().isZero()){
            energy = Math.min(100f, energy + 1);
        }
    }
    private void initializeAnimation(){
        Renderable[] notMovingArr = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            notMovingArr[i] = imageReader.readImage(AVATAR_ITH_IMAGE +i+ PNG,
                    true);
        }

        Renderable[] upDownArr = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            upDownArr[i] = imageReader.readImage(AVATAR_JUMP_IMAGE +i+PNG, true);
        }

        Renderable[] sideArr= new Renderable[6];
        for (int i = 0; i < 6; i++) {
            sideArr[i] = imageReader.readImage(AVATAR_RAN_IMAGE +i+PNG, true);
        }

        notMovingAnimation = new AnimationRenderable(notMovingArr, CHANGING_TIME);
        upDownAnimation = new AnimationRenderable(upDownArr, CHANGING_TIME);
        sideAnimation = new AnimationRenderable(sideArr, CHANGING_TIME);
    }
    private void createAnimation(){
        if(getVelocity().isZero()){
            renderer().setRenderable(notMovingAnimation);
        }

        else if(getVelocity().y() != 0 && getVelocity().x() == 0 ){
            renderer().setRenderable(upDownAnimation);

        }
        else if(getVelocity().x() != 0){
            renderer().setRenderable(sideAnimation);
        }
    }
    private void updateObservers(){
        for (AvatarObserver avatarObserver : observerList) {
            if (avatarObserver != null) {
                avatarObserver.updateWhenAvatarJumps();
            }
        }
    }
}