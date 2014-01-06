package kyle.endless_rpg.screens;

import kyle.endless_rpg.Endless_RPG;
import kyle.endless_rpg.tween.ActorAccessor;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonExit, buttonPlay, buttonCreate;
	private Label heading;
	private TweenManager tweenManager;
	
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		backgroundSprite.draw(batch);
		batch.end();
		
		tweenManager.update(delta);
		
		stage.act(delta);
		stage.draw();
		
		
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		table.setClip(true);
		table.setSize(width, height);
	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		backgroundTexture = new Texture("img/temp_background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Creating heading
//		heading = new Label(Endless_RPG.TITLE, new LabelStyle(white, Color.BLACK));
		heading = new Label(Endless_RPG.TITLE, skin);
		heading.setFontScale(3);
		
		// Play button implementation
		buttonPlay = new TextButton("PLAY", skin);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
			}
		});
		buttonPlay.pad(15);
		
		// New player button implementation
		buttonCreate = new TextButton("NEW", skin);
		buttonCreate.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new CreateScreen());
			}
		});
		buttonCreate.pad(15);	
		
		// Exit button implementation
		buttonExit = new TextButton("EXIT", skin);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		buttonExit.pad(15);
		
		
		
		
		
		table.add(heading);
		table.getCell(heading).spaceBottom(150);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(15);
		table.getCell(buttonPlay).size(Gdx.graphics.getWidth()/4, 100);
		table.row();
		table.add(buttonCreate);
		table.getCell(buttonCreate).spaceBottom(15);
		table.getCell(buttonCreate).size(Gdx.graphics.getWidth()/4, 100);
		table.row();
		table.add(buttonExit);
		table.getCell(buttonExit).size(Gdx.graphics.getWidth()/4, 100);
		//table.debug();
		stage.addActor(table);
		
		// Creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		// heading and button fade-in
		Timeline.createSequence().beginSequence()
			.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonCreate, ActorAccessor.ALPHA).target(0))
			.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
			.push(Tween.from(heading, ActorAccessor.ALPHA, .5f).target(0))
			.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonCreate, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonExit,  ActorAccessor.ALPHA, .5f).target(1))
			.end().start(tweenManager);
		
		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
		
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		backgroundSprite.getTexture().dispose();
		batch.dispose();
	}

}
