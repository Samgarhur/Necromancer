package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.Random;

public class Power extends Scrollable{

    private float runTime;
    private Circle collisionCircle;


    public Power(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        runTime = Methods.randomFloat(0,1);
        collisionCircle = new Circle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;
        // Actualitzem el cercle de col·lisions (punt central del skull i del radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + height, (width / 2.0f)+80);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.powerAnimation.getKeyFrame(runTime,true), position.x, position.y, width, height);
    }

    // Retorna true si hi ha col·lisió amb el character
    public boolean collides(Character character) {

        if (position.x <= character.getX() + character.getWidth()) {
            return (Intersector.overlaps(collisionCircle, character.getCollisionRect()));
        }
        return false;
    }

    public void reset(float newX) {
        super.reset(newX+700);
        // La posició serà un valor aleatori entre 0 i l'alçada de l'aplicació menys l'alçada del power
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - Settings.SKULL_HEIGHT);

    }
}
