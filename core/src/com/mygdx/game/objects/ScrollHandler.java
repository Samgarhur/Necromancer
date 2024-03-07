package com.mygdx.game.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.ArrayList;
import java.util.Random;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Enemics
    int numEnemy;
    ArrayList<Enemy> enemys;

    ArrayList<Skull> skulls;
    Skull skull;

    ArrayList<Shoot> shoots = new ArrayList<>();
    Shoot shoot;

    Random r;
    Random r1;
    private boolean isRunning = true; // Variable para controlar si el ScrollHandler está en funcionamiento

    private long tiempoEntreDisparos = 500000000; // Medio segundo (en milisegundos)
    private long tiempoUltimoDisparo;


    public ScrollHandler() {


        //Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        //Afegim els fons (actors) al grup
        addActor(bg);
        addActor(bg_back);
        tiempoUltimoDisparo = TimeUtils.nanoTime();


        // Creem l'objecte random
        r = new Random();
        r1 = new Random();


        // Comencem amb 3 asteroides
        numEnemy = 3;

        // Creem l'ArrayList
        enemys = new ArrayList<Enemy>();
        skulls = new ArrayList<Skull>();

        // Creem el skull
        skull = new Skull(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - Settings.SKULL_HEIGHT), Settings.SKULL_WIDTH, Settings.SKULL_HEIGHT, Settings.SKULL_VELOCITY);
        skulls.add(skull);
        addActor(skull);

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;

        // Afegim el primer enemic a l'array i al grup
        Enemy enemy = new Enemy(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Methods.randomFloat(Settings.ENEMY_SPEED_MIN, Settings.ENEMY_SPEED_MAX));
        enemys.add(enemy);
        addActor(enemy);

        // Des del segon fins l'últim enemy
        for (int i = 1; i < numEnemy; i++) {
            // Creem la mida aleatòria
            newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY) * 34;
            // Afegim l'enemic
            enemy = new Enemy(enemys.get(enemys.size() - 1).getTailX() + Settings.ENEMY_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Methods.randomFloat(Settings.ENEMY_SPEED_MIN, Settings.ENEMY_SPEED_MAX));
            // Afegim l'enemic a l'ArrayList
            enemys.add(enemy);
            // Afegim l'enemic al grup d'actors
            addActor(enemy);
        }


    }


    public void act(float delta) {
        super.act(delta);

        // Si el background es troba fora de la pantalla, fem un reset de l'element
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());
        }

        // Si el Skull es troba fora de la pantalla, fem un reset de l'element
        if (skull.isLeftOfScreen()) {
            skull.reset(Settings.GAME_WIDTH);
        }

        //Si un enemy es troba fora de la pantalla el tornem a construir
        for (int i = 0; i < enemys.size(); i++) {

            Enemy enemy = enemys.get(i);
            if (enemy.isLeftOfScreen()) {
                if (i == 0) {
                    enemy.reset(Settings.GAME_WIDTH + Settings.ENEMY_GAP);
                } else {
                    enemy.reset(Settings.GAME_WIDTH + Settings.ENEMY_GAP);
                }
            }
        }
    }

    public boolean collides(Character character) {
        // Comprovem les col·lisions entre cada enemy i el character
        for (Enemy enemy : enemys) {
            if (enemy.collides(character)) {
                removeEnemy(enemy);
                return true;
            }
        }
        return false;
    }

    public boolean collidesSkull(Character character) {
            if (skull.collides(character)) {
                removeSkull(skull);
                return true;
            }

        return false;
    }

    public boolean collidesEnemy() {

        // Comprovem les col·lisions entre cada shoot i el enemy
        for (Shoot shoot : shoots) {
            for (Enemy enemy : enemys) {
                if (shoot.collides(enemy)) {//Si colisiona el shoot amb el enemy
                    removeEnemy(enemy);//Esborrem el enemy
                    removeShoot(shoot);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeEnemy(Enemy enemy) {
        enemy.reset((Settings.GAME_WIDTH + 50) + Settings.ENEMY_RESET); // Eliminar el enemigo del escenario

    }

    public void removeSkull(Skull skull) {
        skull.reset((Settings.GAME_WIDTH + 50) + Settings.SKULL_RESET); // Eliminar el enemigo del escenario

    }

    public void removeShoot(Shoot shoot) {
        removeActor(shoot);
        shoots.remove(shoot);

    }


    public ArrayList<Enemy> getEnemys() {
        return enemys;
    }

    public Skull getSkull() {
        return skull;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public boolean puedeDisparar() {
        float tiempoActual = TimeUtils.nanoTime();
        return (tiempoActual - tiempoUltimoDisparo) > tiempoEntreDisparos;
    }


    public void createShoot(Character character) {
        if (puedeDisparar()) {
            tiempoUltimoDisparo = TimeUtils.nanoTime();
            float characterX = character.getX() + character.getWidth() / 2;
            float characterY = character.getY() + character.getHeight() / 2;
            shoot = new Shoot(characterX, characterY - 40, Settings.SHOOT_WIDTH, Settings.SHOOT_HEIGHT, Settings.SHOOT_VELOCITY);
            //Guardamos el disparo en una arralist para poder buscarlo despues para borrarlo
            shoots.add(shoot);
            addActor(shoot);
        }
    }


}
