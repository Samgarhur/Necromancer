package com.mygdx.game.utils;

public class Settings {

    ////////////////////////////////////////GAME//////////////////////////////////////////
    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 960;

    ////////////////////////////////////////CHARACTER//////////////////////////////////////////

    // Propietats del character
    public static final float CHARACTER_VELOCITY = 300;
    public static final int CHARACTER_WIDTH =150;
    public static final int CHARACTER_HEIGHT = 200;
    public static final float CHARACTER_STARTX = 5;
    public static final float CHARACTER_STARTY = GAME_HEIGHT/2 - CHARACTER_HEIGHT/2;

    ////////////////////////////////////////ICONS//////////////////////////////////////////
    public static final int ICON_WIDTH =60;
    public static final int ICON_HEIGHT = 80;
    public static final float ICON_STARTX = 20;
    public static final float ICON_STARTY = 10;
    public static final float ICON_PADDING_X = 5;


    ////////////////////////////////////////ENEMIES//////////////////////////////////////////

    // Rang de valors per canviar la mida de l'asteroide
    public static final float MAX_ENEMY = 8f;
    public static final float MIN_ENEMY = 4f;

    // Configuració scrollable
    public static final int ENEMY_SPEED = -150;
    public static final int ENEMY_GAP = 75;


    public static final int BG_SPEED = -400;
}
