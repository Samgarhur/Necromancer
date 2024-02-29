package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.InputHandler;
import com.mygdx.game.objects.Character;
import com.mygdx.game.objects.Enemy;
import com.mygdx.game.objects.ScrollHandler;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

import java.util.ArrayList;


public class GameScreen implements Screen {

    private Stage stage;
    private Character character;
    private ScrollHandler scrollHandler;

    OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    // Per obtenir el batch de l'stage
    private Batch batch;
    private TextureRegion liveIcon;



    private Skin skin;
    Music music;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();
    public int vidas = 3;


    private Boolean gameOver = false;

    public GameScreen() {

        //Reproduim la musica desde l'assets manager si esta activada i li posem el volum que tenim a les opcions
        if(musicEnabled) {
            AssetManager.GameMusic.setVolume(musicVolume);
            AssetManager.GameMusic.play();
        };
        skin = new Skin(Gdx.files.internal("Skin/star-soldier-ui.json"));

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
                    shapeRenderer.setColor(1,0,0,1);
                    break;
                case 1:
                    shapeRenderer.setColor(0,0,1,1);
                    break;
                case 2:
                    shapeRenderer.setColor(1,1,0,1);
                    break;
                default:
                    shapeRenderer.setColor(1,1,1,1);
                    break;
            }
            shapeRenderer.circle(enemy.getX() + enemy.getWidth()/2, enemy.getY() + enemy.getWidth()/2, enemy.getWidth()/2);
        }


        shapeRenderer.end();
    }

    private void drawLife() {
        liveIcon= AssetManager.liveIcon;

        // Itera sobre los estados de los íconos de vida
        for (int i = 0; i < AssetManager.lifeStates.length; i++) {
            // Obtén el ícono correspondiente según el estado actual
            TextureRegion lifeIcon = AssetManager.lifeIcons[AssetManager.lifeStates[i]];

            // Calcula la posición en la pantalla para cada ícono de vida
            float iconX = Settings.ICON_STARTX + i * (Settings.ICON_WIDTH + Settings.ICON_PADDING_X);
            float iconY = Settings.ICON_STARTY;

            // Dibuja el ícono de vida en la posición calculada
            batch.begin();
            batch.draw(lifeIcon, iconX, iconY, Settings.ICON_WIDTH, Settings.ICON_HEIGHT);
            batch.end();
        }

    }





    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);

        if (vidas > 0) {
            // Dibuixem i actualitzem tots els actors de l'stage
            drawElements();
            drawLife();
            if (scrollHandler.collides(character)) {

                // Si hi ha hagut col·lisió Reproduïm el so de impacte
                AssetManager.Impact.play();
                ArrayList<Enemy> enemys = scrollHandler.getEnemys();
                for (int i = 0; i < enemys.size(); i++) {
                    Enemy enemy = enemys.get(i);
                    if (enemy.collides(character)) {
                        // Eliminar el enemigo de la lista y del escenario
                        scrollHandler.removeEnemy(i);
                        break; // Salir del bucle una vez eliminado el enemigo
                    }
                }
                vidas--;
                Gdx.app.log("VIDAS", ""+vidas);
            }
        } else {

            // Cargar la fuente desde la skin
            BitmapFont font = skin.get("font", BitmapFont.class);

            batch.begin();
            // Dibujar el texto "Game Over" utilizando la fuente de la skin
            font.draw(batch, "Game Over", Settings.GAME_WIDTH/2,Settings.GAME_HEIGHT/2);


            batch.end();
            //Aturem la musica
            AssetManager.GameMusic.stop();

            //Reproduim la animacio de cuan el caracter es mor
            character.death();

            //Si hi mort reporduim el so de mort
            AssetManager.Dead.play();



        }


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

    public Stage getStage() {
        return stage;
    }

    public Character getCharacter() {
        return character;
    }
}
