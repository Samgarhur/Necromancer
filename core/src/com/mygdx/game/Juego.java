package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.screens.GameScreen;

public class Juego extends Game {

	@Override
	public void create () {
		// A l'iniciar el joc carreguem els recursos
		AssetManager.load();
		// I definim la pantalla principal com a la pantalla
		setScreen(new GameScreen());
	}

	@Override
	public void render () {

	}
	
	@Override
	public void dispose () {
		super.dispose();
		AssetManager.dispose();
	}
}
