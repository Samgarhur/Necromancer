package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Necromancer extends Actor {

    // Diferents posicions del necromancer: recta, pujant i baixant
    public static final int NECROMANCER_STRAIGHT = 0;
    public static final int NECROMANCER_UP = 1;
    public static final int NECROMANCER_DOWN = 2;

    // Paràmetres del necromancer
    private Vector2 position;
    private int width, height;
    private int direction;

    public Necromancer(float x, float y, int width, int height) {

        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        direction = NECROMANCER_STRAIGHT;
    }

}
