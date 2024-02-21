package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class GameScreen implements Screen {

    private Stage stage;
    private Necromancer necromancer;
    private ScrollHandler scrollHandler;

    public GameScreen() {
        // Creem l'stage
        stage = new Stage();

        // Creem la nau i la resta d'objectes
        necromancer = new Necromancer();
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(necromancer);

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
