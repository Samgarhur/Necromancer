package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Juego;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.BackgroundMain;
import com.mygdx.game.utils.Settings;

import javax.swing.JWindow;

public class MainMenuScreen implements Screen {
    final Juego game;

    private Stage stage;

    OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    // Per obtenir el batch de l'stage
    private Batch batch;
    private Skin skin;
    TextureRegion backgroundRegion;

    public MainMenuScreen(Juego game) {
        this.game=game;
        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();


        // Creem la càmera de les dimensions del joc
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);


        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT , camera);

        // Creem l'stage i assignem el viewport
        stage = new Stage(viewport);

        batch = stage.getBatch();

        skin = new Skin(Gdx.files.internal("Skin/neon-ui.json"));

        // Carga el fondo desde AssetManager
        backgroundRegion = AssetManager.backgroundMain;
        // Configura el fondo de la pantalla principal
        BackgroundMain background = new BackgroundMain(backgroundRegion);
        // Añade el fondo al escenario
        stage.addActor(background);
        Gdx.input.setInputProcessor(stage);

        //Creamos una ventana para tener ahi puesto los botones para iniciar
        Window window = new Window("Título de la ventana", skin);
        window.setSize(200, 200);
        window.setPosition(Settings.GAME_WIDTH / 2 - window.getWidth() / 2, Settings.GAME_HEIGHT / 2 - window.getHeight() / 2);
        stage.addActor(window);


        TextButton playButton = new TextButton("Jugar",skin);
        playButton.setSize(70,40);
        playButton.setPosition(Settings.GAME_WIDTH / 2 - playButton.getWidth() / 2, Settings.GAME_HEIGHT / 2 - playButton.getHeight() / 2);
        playButton.getLabel().setFontScale(0.7f);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });
        stage.addActor(playButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Actualiza y dibuja el escenario
        stage.act(delta);
        stage.draw();

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
        stage.dispose();
        skin.dispose();

    }
}
