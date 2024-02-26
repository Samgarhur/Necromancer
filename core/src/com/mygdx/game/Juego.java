package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;

public class Juego extends Game {

	@Override
	public void create () {
		// A l'iniciar el joc carreguem els recursos
		AssetManager.load();
		// I definim la pantalla principal com a la pantalla
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void resize( int width,int height) {
		super.resize(width,height);

	}

	@Override
	public void pause() {
		super.pause();

	}
	@Override
	public void resume() {
		super.resume();

	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetManager.dispose();
	}
}
