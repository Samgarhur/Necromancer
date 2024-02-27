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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    Table options;
    Table window;

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


        //Creamos una tabla(ventana) para tener ahi puesto los botones para iniciar
        window = new Table(skin);
        window.setBackground("window-c");
        window.setSize(400, 400);
        window.setPosition(Settings.GAME_WIDTH / 2 - window.getWidth() / 2, Settings.GAME_HEIGHT / 2 - window.getHeight() / 2);

        TextButton playButton = new TextButton("Jugar",skin);
        playButton.setSize(200,100);
        playButton.setDebug(true);
        playButton.getLabel().setFontScale(3f);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });


        TextButton optionsButton = new TextButton("Opcions",skin);
        optionsButton.setSize(200,100);
        optionsButton.setDebug(true);
        optionsButton.getLabel().setFontScale(3f);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(false);
                options.setVisible(true);

            }
        });

        TextButton exitButton = new TextButton("Sortir",skin);
        exitButton.setSize(200,100);
        exitButton.setDebug(true);
        exitButton.getLabel().setFontScale(3f);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        // Agregar botones a la tabla
        window.add(playButton).padBottom(20).row(); // Agrega el botón de jugar con un espacio inferior de 20 y pasa a la siguiente fila
        window.add(optionsButton).padBottom(20).row();
        window.add(exitButton).padBottom(20).row();


        //Segunda tabla para las opciones
        options = new Table(skin);
        options.setBackground("window-c");
        options.setVisible(false);
        options.setSize(500, 500);
        options.setPosition((Settings.GAME_WIDTH / 2 - window.getWidth() / 2)-60, Settings.GAME_HEIGHT / 2 - window.getHeight() / 2);

        TextButton returnButton = new TextButton("Menu principal",skin);
        returnButton.setSize(200,100);
        returnButton.setDebug(true);
        returnButton.getLabel().setFontScale(3f);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                options.setVisible(false);
                window.setVisible(true);
            }
        });
        options.add(returnButton).padBottom(20).row();

        stage.addActor(window);
        stage.addActor(options);
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
