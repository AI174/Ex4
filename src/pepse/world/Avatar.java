package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    public static final int AVATAR_HEIGHT = 60; //don't change
    public static final int AVATAR_WIDTH = 30 ; //don't change
    private static final float VELOCITY_X = 300; //don't change
    private static final float VELOCITY_Y = -450; //don't change
    private static final float GRAVITY = 500; //don't change
    private static final float CHANGING_TIME = .4f; //don't change
    private float energy = 100;
    private static Renderable imageRenderable;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private static final String AVATAR_TAG = "avatar";
    private boolean inTheAir = false;
    private AnimationRenderable notMovingAnimation;
    private AnimationRenderable upDownAnimation;
    private AnimationRenderable sideAnimation;

    /**
     * hhh
     * @param pos down right
     * @param inputListener input
     * @param imageReader imageReader
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos.add(new Vector2(-AVATAR_WIDTH, -AVATAR_HEIGHT)),
                new Vector2(AVATAR_WIDTH, AVATAR_HEIGHT), imageRenderable); // must change wrong parameters
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        transform().setAccelerationY(GRAVITY);
        this.setTag(AVATAR_TAG);
        initializeAnimation();
    }

    public static Avatar create(Vector2 downRightCorner,UserInputListener inputListener,
                                ImageReader imageReader) {

        Avatar.imageRenderable = imageReader.readImage("assets/idle_0.png", true);
        Avatar avatar = new Avatar(downRightCorner, inputListener, imageReader);
        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        return avatar;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        move();
        createAnimation();
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
        }

        if(getVelocity().isZero()){
            energy = Math.min(100f, energy + 1);
        }
    }


    public void addEnergy(float n){
        energy = Math.min(100, energy + n);
    }
    public float getEnergy(){
        return energy;
    }


    /**
     * tomorowwwwwwwwwwwwwwwwww job
     */
    private void initializeAnimation(){
        Renderable[] notMovingArr = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            notMovingArr[i] = imageReader.readImage("assets/idle_"+i+".png", true);
        }

        Renderable[] upDownArr = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            upDownArr[i] = imageReader.readImage("assets/jump_"+i+".png", true);
        }

        Renderable[] sideArr= new Renderable[6];
        for (int i = 0; i < 6; i++) {
            sideArr[i] = imageReader.readImage("assets/run_"+i+".png", true);
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
}
