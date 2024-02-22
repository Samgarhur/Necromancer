package com.mygdx.game.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.utils.Settings;

import java.util.Random;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;


    public ScrollHandler() {
        //Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        //Afegim els fons (actors) al grup
        addActor(bg);
        addActor(bg_back);
    }

    public void act(float delta) {
        super.act(delta);
        // Si algun element es troba fora de la pantalla, fem un reset de l'element
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());
        }
    }
}
