package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.ScrollHandler;
import com.mygdx.game.utils.Settings;


public class GameScreen implements Screen {

    private Stage stage;
    private Character character;
    private ScrollHandler scrollHandler;

    OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    // Per obtenir el batch de l'stage
    private Batch batch;

    public GameScreen() {

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem la càmera de les dimensions del joc
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera perque faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT , camera);

        // Creem l'stage i assignem el viewport
        stage = new Stage(viewport);

        batch = stage.getBatch();

        // Creem el character i la resta d'objectes
        character = new Character(Settings.CHARACTER_STARTX, Settings.CHARACTER_STARTY, Settings.CHARACTER_WIDTH, Settings.CHARACTER_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(character);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Dibuixem i actualitzem tots els actors de l'stage
        stage.draw();
        stage.act(delta);


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
    @Override
    public void dispose() {

    }



}
