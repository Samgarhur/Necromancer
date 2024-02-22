package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.ScrollHandler;
import com.mygdx.game.utils.Settings;


public class GameScreen implements Screen {

    private Stage stage;
    private Character character;
    private ScrollHandler scrollHandler;

    public GameScreen() {
        // Creem l'stage
        stage = new Stage();

        // Creem la nau i la resta d'objectes
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

    public Character getCharacter() {
        return character;
    }

    public Stage getStage() {
        return stage;
    }


    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

}
