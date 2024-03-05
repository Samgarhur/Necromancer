package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.objects.Character;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;

public class InputHandler implements InputProcessor {
    private Character character;
    private GameScreen screen;
    private Stage stage;
    private TextButton returnMenuButton;

    // Enter per a la gestió del moviment d'arrossegament
    int previousY = 0;
    int previousX = 0;

    public InputHandler(GameScreen screen) {
        this.screen = screen;
        character = screen.getCharacter();
        stage= screen.getStage();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)||Gdx.input.isKeyPressed(Input.Keys.S)){
            this.character.goDown();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)||Gdx.input.isKeyPressed(Input.Keys.W)){
            this.character.goUp();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)||Gdx.input.isKeyPressed(Input.Keys.D)){
            this.character.goRight();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)||Gdx.input.isKeyPressed(Input.Keys.A)){
            this.character.goLeft();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            this.character.atack();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        character.goStraight();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        previousY = screenY;
        previousX = screenX;
        this.character.atack();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Quan deixem anar el dit acabem un moviment i posem el character a l'estat normal
        character.goStraight();

        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
        stage.getCamera().unproject(touchPoint);
        // Verifica si el punto de contacto está dentro de los límites del botón
        if (returnMenuButton != null && returnMenuButton.getX() < touchPoint.x && touchPoint.x < returnMenuButton.getX() + returnMenuButton.getWidth()
                && returnMenuButton.getY() < touchPoint.y && touchPoint.y < returnMenuButton.getY() + returnMenuButton.getHeight()) {
            // El botón ha sido presionado, realiza la acción correspondiente
            Gdx.app.log("Boton volver al menu principal", "pulsado");
            screen.getGame().setScreen(new MainMenuScreen(screen.getGame()));
        }

        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un llindar per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

        // Si la Y és major que la que tenim guardada és que va cap avall
            if (previousY < screenY) {
                character.goDown();
            } else {
            // En cas contrari cap amunt
                character.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;

        // Posem un llindar per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousX - screenX) > 2)

            // Si la X és major que la que tenim guardada és que va cap avall
            if (previousX < screenX) {
                character.goRight();
            } else {
                // En cas contrari cap amunt
                character.goLeft();
            }
        // Guardem la posició de la Y
        previousX = screenX;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    public void setReturnMenuButton(TextButton returnMenuButton) {
        this.returnMenuButton = returnMenuButton;
    }
}
