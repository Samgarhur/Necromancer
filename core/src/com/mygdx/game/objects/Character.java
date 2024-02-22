package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.utils.Settings;

public class Character extends Actor {
    // Diferents posicions del actor principal: recta, pujant i baixant
    public static final int CHARACTER_STRAIGHT = 0;
    public static final int CHARACTER_UP = 1;
    public static final int CHARACTER_DOWN = 2;

    // Paràmetres del character
    private Vector2 position;
    private int width, height;
    private int direction;

    public Character() {


    }

    public Character(float x, float y, int width, int height) {

        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        direction = CHARACTER_STRAIGHT;
    }

    public void act(float delta) {

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

    // Posem el Character al seu estat original
    public void goStraight() {
        direction = CHARACTER_STRAIGHT;
    }


}
