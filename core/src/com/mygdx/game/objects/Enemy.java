package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.Random;

public class Enemy extends Scrollable{

    private float runTime;
    private Circle collisionCircle;
    public Enemy(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        runTime = Methods.randomFloat(0,1);
        // Creem el cercle
        collisionCircle = new Circle();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;
        // Actualitzem el cercle de col·lisions (punt central de l'enemic i del radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.enemyAnimation.getKeyFrame(runTime,true), position.x, position.y, width, height);
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Character character) {

        if (position.x <= character.getX() + character.getWidth()) {
            return (Intersector.overlaps(collisionCircle, character.getCollisionRect()));
        }
        return false;
    }

    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número aleatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY);
        // Modificarem l'alçada i l'amplada segons l'aleatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatori entre 0 i l'alçada de l'aplicació menys l'alçada del enemic
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) height);
    }

    // Getter pel runTime
    public float getRunTime() {

        return runTime;
    }

    public Circle getCollisionCircle() {

        return collisionCircle;
    }
}
