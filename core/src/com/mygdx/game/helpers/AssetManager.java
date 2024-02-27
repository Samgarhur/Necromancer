package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.objects.Background;
import com.mygdx.game.utils.Settings;

import java.util.Set;

public class AssetManager {
    // Sprite Sheet
    public static Texture characterSheet;

    // Iconos de vida
    public static TextureRegion[] lifeIcons;
    public static int[] lifeStates;

    // Fons
    public static TextureRegion backgroundMain;
    public static TextureRegion background;

    public static TextureRegion liveIcon;

    // Movimiento derecha,quieto y ataque de necromancer
    public static TextureRegion[] characterStay;
    public static Animation<TextureRegion>  characterAnimationStay;

    public static TextureRegion[]  characterRight;
    public static Animation<TextureRegion> characterAnimationRight;

    public static TextureRegion[]  characterAtack;
    public static Animation<TextureRegion>  characterAnimationAtack;



    public static void load() {
        //Carreguem fons del menu principal y del joc
        backgroundMain=new TextureRegion(new Texture(Gdx.files.internal("Background/fondoNegro.jpg")));
        background=new TextureRegion(new Texture(Gdx.files.internal("Background/espacio.png")));
        background.flip(false,true);

        /*liveIcon=new TextureRegion(new Texture(Gdx.files.internal("Icons/vida.png")));
        liveIcon.flip(false,true);*/

        // Carga del icono de vida
        TextureRegion liveIcon = new TextureRegion(new Texture(Gdx.files.internal("Icons/vida.png")));
        liveIcon.flip(false, true);

        TextureRegion emptyLiveIcon = new TextureRegion(new Texture(Gdx.files.internal("Icons/vida_vacio.png")));
        emptyLiveIcon.flip(false, true);

        // Inicialización de los iconos de vida y sus estados
        lifeIcons = new TextureRegion[]{emptyLiveIcon, liveIcon};
        lifeStates = new int[]{1, 1, 1}; // Representa tres iconos de vida llenos al principio



        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        characterSheet = new Texture(Gdx.files.internal("Necromancer/Necromancer_Sheet.png"));
        characterSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Carreguem els 8 estats del character parat
        characterStay = new TextureRegion[8];
        for (int i = 0; i < characterStay.length; i++) {
            characterStay[i] = new TextureRegion(characterSheet, (i * 160)+55, 50, 60, 80);
            characterStay[i].flip(false,true);

        }
        characterAnimationStay = new Animation<>(0.05f,characterStay);

        // Carreguem els 8 estats del character cuan es mou de dreta o esquerra
        characterRight = new TextureRegion[8];
        for (int i = 0; i < characterRight.length; i++) {
            characterRight[i] = new TextureRegion(characterSheet, (i * 160)+55, 150, 60, 80);
            characterRight[i].flip(false,true);
        }
        characterAnimationRight= new Animation<>(0.08f,characterRight);

        // Carreguem els 13 estats del character cuan ataca
        characterAtack = new TextureRegion[13];
        for (int i = 0; i < characterAtack.length; i++) {
            characterAtack[i] = new TextureRegion(characterSheet, (i * 160)+60, 295, 60, 80);
            characterAtack[i].flip(false,true);

        }
        characterAnimationAtack= new Animation<>(0.03f,characterAtack);
    }

    public static void dispose() {
        characterSheet.dispose();

    }
}
