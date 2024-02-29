package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Juego;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.BackgroundMain;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class MainMenuScreen implements Screen {
    final Juego game;
    private Stage stage;

    private Skin skin;
    TextureRegion backgroundRegion;
    Table options,window,root;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();



    private Label title,titlePreferences,volumeMusicLabel,volumeSoundLabel,musicOnOffLabel,soundOnOffLabel;


    public MainMenuScreen(Juego game) {
        this.game=game;

        //Reproduim la musica desde l'assets manager si esta activada i li posem el volum que tenim a les opcions
        if(musicEnabled) {
            AssetManager.MainMenuMusic.setVolume(musicVolume);
            AssetManager.MainMenuMusic.play();
        }


        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        // Creem l'stage i assignem el viewport
        stage = new Stage(viewport);

        //batch = stage.getBatch();

        skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));

        // Carga el fondo desde AssetManager
        backgroundRegion = AssetManager.backgroundMain;
        // Configura el fondo de la pantalla principal
        BackgroundMain background = new BackgroundMain(backgroundRegion);
        // Añade el fondo al escenario
        stage.addActor(background);
        Gdx.input.setInputProcessor(stage);

        // Obtener la fuente de texto "title" de la skin
        BitmapFont font = skin.getFont("title");

        title =new Label("NECROMANCER", new Label.LabelStyle(font, Color.WHITE));
        title.setFontScale(1.5F);
        title.setPosition((Settings.GAME_WIDTH / 2 - title.getWidth() / 2)-180, Settings.GAME_HEIGHT - 200);
        stage.addActor(title);



        //Creamos una tabla(ventana) para tener ahi puesto los botones para iniciar
        window = new Table(skin);
        window.setBackground("window-special");
        window.setSize(500, 500);
        window.setPosition(Settings.GAME_WIDTH / 2 - window.getWidth() / 2, (Settings.GAME_HEIGHT / 2 - window.getHeight() / 2)-100);


        TextButton playButton = new TextButton("Jugar",skin);
        //playButton.setSize(600,700);
        //playButton.setDebug(true);
        playButton.getLabel().setFontScale(3f);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
                AssetManager.MainMenuMusic.stop();
            }
        });



        TextButton optionsButton = new TextButton("Opcions",skin);
        //optionsButton.setSize(200,100);
        //optionsButton.setDebug(true);
        optionsButton.getLabel().setFontScale(3f);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(false);
                options.setVisible(true);


            }
        });

        TextButton exitButton = new TextButton("Sortir",skin);
        //exitButton.setSize(200,100);
        //exitButton.setDebug(true);
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

        //root.add(window);


        //Segunda tabla para las opciones
        options = new Table(skin);
        options.setBackground("window-special");
        options.setVisible(false);
        options.setSize(800, 500);
        options.setPosition((Settings.GAME_WIDTH / 2 - options.getWidth() / 2), (Settings.GAME_HEIGHT / 2 - options.getHeight() / 2)-100);


        //Controles volumen musica
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( this.game.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                float volume = volumeMusicSlider.getValue();
                game.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                AssetManager.MainMenuMusic.setVolume(volume);
                return false;
            }
        });

        //Controles volumen sonidos
        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( this.game.getPreferences().getSoundVolume() );

        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                float volumeSounds = soundMusicSlider.getValue();
                game.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });


        //Controles desactivar musica
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( this.game.getPreferences().isMusicEnabled() );

        // Obtener el estilo actual del CheckBox
        CheckBox.CheckBoxStyle checkBoxStyle = musicCheckbox.getStyle();

        // Ajustar el tamaño del Drawable asociado al estilo del CheckBox
        checkBoxStyle.checkboxOn.setMinWidth(30); // Ajusta el ancho del CheckBox cliclado
        checkBoxStyle.checkboxOn.setMinHeight(30); // Ajusta la altura del CheckBox cliclado
        checkBoxStyle.checkboxOff.setMinWidth(30); // Ajusta el ancho del CheckBox vacio
        checkBoxStyle.checkboxOff.setMinHeight(30); // Ajusta la altura del CheckBox vacio
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled( enabled );
                if(enabled){
                    AssetManager.MainMenuMusic.play();
                }
                else
                    AssetManager.MainMenuMusic.pause();
                return false;
            }
        });

        //Controles desactivar sonidos
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( this.game.getPreferences().isSoundEffectsEnabled() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        TextButton returnButton = new TextButton("Menu principal",skin);
        returnButton.setSize(200,100);
        returnButton.getLabel().setFontScale(3f);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                options.setVisible(false);
                window.setVisible(true);
            }
        });


        titlePreferences = new Label( "Opcions", skin );
        titlePreferences.setFontScale(3f);
        volumeMusicLabel = new Label( "Volum musica", skin );
        volumeMusicLabel.setFontScale(2f);
        volumeSoundLabel = new Label( "Volum sons", skin );
        volumeSoundLabel.setFontScale(2f);
        musicOnOffLabel = new Label( "Musica", skin );
        musicOnOffLabel.setFontScale(2f);
        soundOnOffLabel = new Label( "Efectes de so", skin );
        soundOnOffLabel.setFontScale(2f);


        options.add(titlePreferences).colspan(2);
        options.row().pad(10,0,0,10);;
        options.add(volumeMusicLabel).left();
        options.add(volumeMusicSlider);
        options.row().pad(10,0,0,10);
        options.add(musicOnOffLabel);
        options.add(musicCheckbox);
        options.row().pad(10,0,0,10);;
        options.add(volumeSoundLabel);
        options.add(soundMusicSlider);
        options.row().pad(10,0,0,10);;
        options.add(soundOnOffLabel);
        options.add(soundEffectsCheckbox);
        options.row().pad(10,0,0,10);;
        options.add(returnButton).colspan(2);


        //root.add(options);

        stage.addActor(window);
        stage.addActor(options);

    }

    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualiza y dibuja el escenario
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);

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
