package kyle.endless_rpg.screens;

import kyle.endless_rpg.tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
			splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture splashTexture = new Texture("img/splash.png");
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash,  SpriteAccessor.ALPHA,  2).target(1).repeatYoyo(1,0.5f).setCallback(new TweenCallback(){
			@Override
			public void onEvent(int type, BaseTween<?> source){
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);
		
	}

	@Override
	public void hide() {
	
	}

	@Override
	public void pause() {
	
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

}
