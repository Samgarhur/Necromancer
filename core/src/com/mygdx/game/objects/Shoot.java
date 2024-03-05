package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Methods;

public class Shoot extends Scrollable{
    private float runTime;
    private Circle collisionCircle;

    public Shoot(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        runTime = Methods.randomFloat(0,1);
        collisionCircle = new Circle();
    }

    public void act(float delta) {
        super.act(delta);
        runTime += delta;
        // Actualitzem el cercle de col·lisions (punt central de l'enemic i del radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.shootAnimation.getKeyFrame(runTime,true), position.x, position.y, width, height);
    }

    public boolean collides(Enemy enemy) {

        if (position.x <= enemy.getX() + enemy.getWidth()) {
            return (Intersector.overlaps(collisionCircle, enemy.getCollisionCircle()));
        }
        return false;
    }
}
