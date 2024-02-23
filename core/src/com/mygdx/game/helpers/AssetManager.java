package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.objects.Background;

public class AssetManager {
    // Sprite Sheet
    public static Texture characterSheet;

    // Fons
    public static TextureRegion background;

    // Movimiento derecha,quieto y ataque de necromancer
    public static TextureRegion[] characterStay;
    public static Animation<TextureRegion>  characterAnimationStay;

    public static TextureRegion[]  characterRight;
    public static Animation<TextureRegion> characterAnimationRight;

    public static TextureRegion[]  characterAtack;
    public static Animation<TextureRegion>  characterAnimationAtack;



    public static void load() {
        background=new TextureRegion(new Texture(Gdx.files.internal("Background/espacio.png")));
        background.flip(false,true);


        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        characterSheet = new Texture(Gdx.files.internal("Necromancer/Necromancer_Sheet.png"));
        characterSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Carreguem els 8 estats del character parat
        characterStay = new TextureRegion[8];
        for (int i = 0; i < characterStay.length; i++) {
            characterStay[i] = new TextureRegion(characterSheet, i * 160, 0, 160, 160);
            characterStay[i].flip(false,true);

        }
        characterAnimationStay = new Animation<>(0.05f,characterStay);

        // Carreguem els 8 estats del character cuan es mou de dreta o esquerra
        characterRight = new TextureRegion[8];
        for (int i = 0; i < characterRight.length; i++) {
            characterRight[i] = new TextureRegion(characterSheet, i * 128, 160, 160, 160);
            characterRight[i].flip(false,true);
        }
        characterAnimationRight= new Animation<>(0.05f,characterRight);

        // Carreguem els 13 estats del character cuan ataca
        characterAtack = new TextureRegion[13];
        for (int i = 0; i < characterAtack.length; i++) {
            characterAtack[i] = new TextureRegion(characterSheet, i * 128, 384, 128, 128);
            characterAtack[i].flip(false,true);

        }
        characterAnimationAtack= new Animation<>(0.05f,characterAtack);



    }

    public static void dispose() {
        characterSheet.dispose();

    }
}
