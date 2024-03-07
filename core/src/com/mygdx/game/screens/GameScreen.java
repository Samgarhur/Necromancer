package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Juego;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.InputHandler;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.ScrollHandler;
import com.mygdx.game.objects.Shoot;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

import java.util.ArrayList;


public class GameScreen implements Screen {
    final Juego game;

    FileHandle fuente;

    private Stage stage;
    private Character character;
    private ScrollHandler scrollHandler;
    private BitmapFont font;

    OrthographicCamera camera;
    public ArrayList<Enemy> enemys;
    public Enemy enemy;

    private ShapeRenderer shapeRenderer;

    public TextButton returnMenuButton;

    // Per obtenir el batch de l'stage
    private Batch batch;
    private TextureRegion liveIcon;
    boolean isGameOver = false;
    private Skin skin;
    Music music;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();

    boolean soundsEnabled = preferences.isSoundEffectsEnabled();
    float soundsVolume = preferences.getSoundVolume();
    public int vidas = 3;
    public int puntuacion= 0;
    public int mejorPuntuacion;


    public GameScreen(Juego game) {
        this.game = game;
        AssetManager.load();

        //Reproduim la musica desde l'assets manager si esta activada i li posem el volum que tenim a les opcions
        if (musicEnabled) {
            AssetManager.GameMusic.setVolume(musicVolume);
            AssetManager.GameMusic.play();
        }

        //Inizializamos la skin
        skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));

        //Inizializamos la mejor puntuacion
        mejorPuntuacion=Gdx.app.getPreferences("data").getInteger("score");

        //Inicializamos la fuente para los textos
        fuente = AssetManager.fuente;

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem la càmera de les dimensions del joc
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera perque faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assignem el viewport
        stage = new Stage(viewport);

        batch = stage.getBatch();

        // Creem el character i la resta d'objectes
        character = new Character(Settings.CHARACTER_STARTX, Settings.CHARACTER_STARTY, Settings.CHARACTER_WIDTH, Settings.CHARACTER_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(character);

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

    }

    private void drawElements() {

        // Recollim les propietats del batch de l'stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem el character
        shapeRenderer.rect(character.getX(), character.getY(), character.getWidth(), character.getHeight());



        // Recollim tots els  enemics
        ArrayList<Enemy> enemys = scrollHandler.getEnemys();
        Enemy enemy;

        for (int i = 0; i < enemys.size(); i++) {

            enemy = enemys.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getWidth() / 2, enemy.getWidth() / 2);
        }


        shapeRenderer.end();
    }

    private void drawLife() {
        liveIcon = AssetManager.liveIcon;

        // Itera sobre los íconos de vida en el asset manager
        for (int i = 0; i < AssetManager.lifeIcons.length; i++) {
            // Obtén el ícono correspondiente según el índice actual
            TextureRegion lifeIcon = AssetManager.lifeIcons[i];

            // Si el ícono no es nulo, dibújalo
            if (lifeIcon != null) {

                // Calcula la posición en la pantalla para cada ícono de vida
                float iconX = Settings.ICON_STARTX + i * (Settings.ICON_WIDTH + Settings.ICON_PADDING_X);
                float iconY = Settings.ICON_STARTY;

                // Dibuja el ícono de vida en la posición calculada
                batch.begin();
                batch.draw(lifeIcon, iconX, iconY, Settings.ICON_WIDTH, Settings.ICON_HEIGHT);
                batch.end();
            }
        }

    }

    private void drawScore() {
        // Obtener la fuente de texto de la skin
        BitmapFont fontScore = new BitmapFont(fuente, true);
        fontScore.getData().scale(1f);

        //Asignamos la posicion del texto "BestScore"
        float textBestScoreY = Settings.GAME_HEIGHT-50;

        batch.begin();
        fontScore.draw(batch, "Score: "+puntuacion, Settings.SCORE_STARTX, Settings.SCORE_STARTY);
        fontScore.draw(batch, "Best Score: "+mejorPuntuacion, Settings.SCORE_STARTX, textBestScoreY);
        batch.end();

    }


    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        if (!isGameOver) {
            if (vidas > 0) {
                // Dibuixem i actualitzem tots els actors de l'stage
                //drawElements();
                drawLife();
                drawScore();
                // Verificar si el el personaje esta atacando
                if (character.isAttack) {
                    // Crear un disparo desde el ScrollHandler
                    scrollHandler.createShoot(character);


                    // Actualizar el estado de ataque del personaje
                    character.isAttack = false;
                }

                if (scrollHandler.collides(character)) {
                    // Si hi ha hagut col·lisió Reproduïm el so de impacte
                    if (soundsEnabled) {
                        Long impactSound = AssetManager.Impact.play();
                        AssetManager.Impact.setVolume(impactSound, soundsVolume);
                    }
                    character.hurt();

                    vidas--;
                    // Eliminar el último ícono de vida si aún quedan vidas
                    if (vidas >= 0 && vidas < AssetManager.lifeIcons.length) {
                        AssetManager.lifeIcons[vidas] = null;
                    }
                    Gdx.app.log("VIDAS", "" + vidas);
                }
                if (scrollHandler.collidesEnemy()) {
                    if (soundsEnabled) {
                        Long impactSound = AssetManager.ImpactShoot.play();
                        AssetManager.ImpactShoot.setVolume(impactSound, soundsVolume);
                    }
                    puntuacion+=10;
                    Gdx.app.log("Puntuacion", "" + puntuacion);


                }
            } else {
                isGameOver = true; // Establecemos isGameOver como verdadero cuando el juego termina

                //Aturem la musica
                AssetManager.GameMusic.stop();

                //Reproduim la animacio de cuan el caracter es mor
                character.death();

                // Si hi ha hagut col·lisió Reproduïm el so de impacte
                if (soundsEnabled) {
                    Long impactSound = AssetManager.Dead.play();
                    AssetManager.Dead.setVolume(impactSound, soundsVolume);
                }
            }
        } else {

            //Si la puntuacion final es superior a la mejor puntuacion la guardamos en preferences
            if(puntuacion>mejorPuntuacion) {
                //Guardem la puntuacio final a les preferences de gdx
                Gdx.app.getPreferences("data").putInteger("score", puntuacion).flush();
            }

            // Obtener la fuente de texto de la skin
            BitmapFont font = new BitmapFont(fuente, true);
            font.getData().scale(3f);

            // Obtener la fuente de texto de la skin
            BitmapFont font1 = new BitmapFont(fuente, true);
            font1.getData().scale(2f);

            //Asignamos la posicion del texto "Game Over"
            float gameOverX = (Settings.GAME_WIDTH / 2) - 300;
            float gameOverY = (Settings.GAME_HEIGHT / 2) - 100;

            //Asignamos la posicion del texto "Score"
            float textScoreX = gameOverX-50;
            float textScoreY = gameOverY+100;


            batch.begin();
            font.draw(batch, "Game Over", gameOverX, gameOverY);
            font1.draw(batch, "Final Score: "+puntuacion, textScoreX, textScoreY);
            batch.end();

            //Asignamos la posicion del botón debajo del texto
            float buttonX = gameOverX + 50; //
            float buttonY = textScoreY + 100; //


            // Obtener el estilo de la skin del botón
            TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);

            // Cambiar la fuente del estilo del botón por el que hemos creado antes
            buttonStyle.font = font;

            //Cambiamos el control para que no lo gestione el inputhandler y no se pueda usar el personaje
            Gdx.input.setInputProcessor(stage);


            returnMenuButton = new TextButton("Menu principal", skin);
            returnMenuButton.getLabel().setFontScale(2f);
            returnMenuButton.setSize(500, 100);
            returnMenuButton.setPosition(buttonX, buttonY);
            //returnMenuButton.setDisabled(false);
            //returnMenuButton.debug();
            returnMenuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MainMenuScreen(game));


                }
            });

            stage.addActor(returnMenuButton);
        }


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);

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

    public Stage getStage() {
        return stage;
    }

    public Character getCharacter() {
        return character;
    }

    public TextButton getReturnMenuButton() {
        return returnMenuButton;
    }

    public Juego getGame() {
        return this.game;
    }
}
