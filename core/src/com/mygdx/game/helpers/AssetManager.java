package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.objects.Background;
import com.mygdx.game.utils.Settings;

import java.util.Set;

public class AssetManager {
    // Sprite Sheet
    public static Texture characterSheet;

    public static Texture enemySheet;

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

    public static TextureRegion[]  characterDead;
    public static Animation<TextureRegion>  characterAnimationDead;

    public static TextureRegion[] enemy;
    public static Animation<TextureRegion> enemyAnimation;
    public static Music MainMenuMusic;
    public static Music GameMusic;
    public static Sound Impact;
    public static Sound Dead;




    public static void load() {

        //Carreguem fons del menu principal y del joc
        backgroundMain=new TextureRegion(new Texture(Gdx.files.internal("Background/fondoNegro.jpg")));
        background=new TextureRegion(new Texture(Gdx.files.internal("Background/espacio.png")));
        background.flip(false,true);


        // Carga del icono de vida
        Texture liveIconTexture = new Texture(Gdx.files.internal("Icons/calavera.png"));
        //liveIconTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion liveIcon = new TextureRegion(liveIconTexture);
        liveIcon.flip(false, true);

        TextureRegion emptyLiveIcon = new TextureRegion(new Texture(Gdx.files.internal("Icons/calavera_vacia.png")));
        emptyLiveIcon.flip(false, true);

        // Inicialización de los iconos de vida y sus estados
        lifeIcons = new TextureRegion[]{emptyLiveIcon, liveIcon};
        lifeStates = new int[]{1, 1, 1}; // Representa tres iconos de vida llenos al principio


        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        enemySheet = new Texture(Gdx.files.internal("Enemy/Cacodaemon_Sheet.png"));
        enemySheet .setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Carreguem els estats del enemy
        enemy = new TextureRegion[6];
        for (int i = 0; i < enemy.length; i++) {

            enemy[i] = new TextureRegion(enemySheet, i * 64, 64, 64, 64);
            enemy[i].flip(true, true);

        }
        enemyAnimation = new Animation<>(0.07f, enemy);


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

        // Carreguem els 10 estats del character cuan es mor
        characterDead = new TextureRegion[10];
        for (int i = 0; i < characterDead.length; i++) {
            characterDead[i] = new TextureRegion(characterSheet, (i * 160)+60, 800, 60, 80);
            characterDead[i].flip(false,true);

        }
        characterAnimationDead= new Animation<>(0.1f,characterDead);

/////////////////////////////////////////////Musica i sons //////////////////////////////////////////////////////////////////////////////////////////
        //Carreguem la musica del menu principal
        MainMenuMusic = Gdx.audio.newMusic((Gdx.files.internal("Music/metal.mp3")));
        MainMenuMusic.setLooping(true);

        //Carreguem la musica del joc
        GameMusic = Gdx.audio.newMusic((Gdx.files.internal("Music/Points.mp3")));
        GameMusic.setLooping(true);

        //Carreguem el so cuan impactem
        Impact= Gdx.audio.newSound((Gdx.files.internal("Sounds/Impact.wav")));

        //Carreguem el so cuan el character mor
        Dead= Gdx.audio.newSound((Gdx.files.internal("Sounds/Dead.wav")));


    }

    public static void dispose() {
        characterSheet.dispose();
        enemySheet.dispose();
        GameMusic.dispose();
        MainMenuMusic.dispose();
        Impact.dispose();
        Dead.dispose();


    }
}
