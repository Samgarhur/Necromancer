package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

public class Character extends Actor {
    // Diferents posicions del actor principal: recta, pujant i baixant
    public static final int CHARACTER_STRAIGHT = 0;
    public static final int CHARACTER_UP = 1;
    public static final int CHARACTER_DOWN = 2;
    public static final int CHARACTER_RIGHT = 3;
    public static final int CHARACTER_LEFT = 4;

    public boolean isAttack,isFacingRight;
    TextureRegion currentFrame;

    // Paràmetres del character
    private Vector2 position;
    private int width, height;
    private int direction;
    private Animation<TextureRegion> stayAnimation,atackAnimation;

    private float stateTime;
    private Rectangle collisionRect;

    public Character() {


    }

    public Character(float x, float y, int width, int height) {

        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        direction = CHARACTER_STRAIGHT;

        stayAnimation= AssetManager.characterAnimationStay;
        atackAnimation= AssetManager.characterAnimationAtack;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(isAttack){
            currentFrame=atackAnimation.getKeyFrame(stateTime,false);
            if(atackAnimation.isAnimationFinished(stateTime)){
                isAttack=false;
            }
        }
        else {
            currentFrame = stayAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void act(float delta) {

        super.act(delta);
        stateTime +=delta;

        // Movem l'Spacecraft depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case CHARACTER_UP:
                if (this.position.y - Settings.CHARACTER_VELOCITY  * delta >= 0) {
                    this.position.y -= Settings.CHARACTER_VELOCITY  * delta;
                }
                break;
            case CHARACTER_DOWN:
                if (this.position.y + height + Settings.CHARACTER_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.CHARACTER_VELOCITY  * delta;
                }
                break;
            case CHARACTER_RIGHT:
                if (this.position.x + width + Settings.CHARACTER_VELOCITY * delta <= Settings.GAME_WIDTH) {
                    this.position.x += Settings.CHARACTER_VELOCITY  * delta;

                }
                break;
            case CHARACTER_LEFT:
                if (this.position.x + Settings.CHARACTER_VELOCITY * delta >= 0) {
                    this.position.x -= Settings.CHARACTER_VELOCITY  * delta;
                }
                break;
            case CHARACTER_STRAIGHT:
                break;
        }
    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció del Character: Puja
    public void goUp() {
        direction = CHARACTER_UP;
    }

    // Canviem la direcció del Character: Baixa
    public void goDown() {
        direction = CHARACTER_DOWN;
    }
    // Canviem la direcció del Character: Dreta
    public void goRight() {
        direction = CHARACTER_RIGHT;
    }
    // Canviem la direcció del Character: Esquerra
    public void goLeft() {
        direction = CHARACTER_LEFT;
    }

    // Posem el Character al seu estat original
    public void goStraight() {
        direction = CHARACTER_STRAIGHT;
    }

    public void atack(){
        if(!isAttack){
            this.isAttack=true;
            this.stateTime=0;
        }

    }
    public Rectangle getCollisionRect() {

        return collisionRect;
    }


}
