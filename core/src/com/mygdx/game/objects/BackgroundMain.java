package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;


public class BackgroundMain extends Actor {
    TextureRegion background;

    public BackgroundMain(TextureRegion backgroundMain) {
        this.background = backgroundMain;
        setSize(Settings.GAME_WIDTH,Settings.GAME_HEIGHT);
    }
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(background, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
