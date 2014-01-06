package kyle.endless_rpg.screens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kyle.endless_rpg.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PlayScreen implements Screen {
	
	private Entity playerEntity;
	
	private Stage stage;
	private SpriteBatch batch;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	private Slider experienceSlider;
	
	private ShapeRenderer experienceBar;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.end();
		
		experienceBar.begin(ShapeType.Filled);
		experienceBar.identity();
		experienceBar.translate(20, 12, 2);
		experienceBar.rotate(0, 0, 1, 90);
		experienceBar.rect(50, 50, 50, 50);
		experienceBar.end();
	
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		playerEntity = new Entity();
		batch = new SpriteBatch();
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("ui/uiskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
		experienceBar = new ShapeRenderer();
		

		
		JSONParser parser = new JSONParser();
		try{
			Object obj = parser.parse(new FileReader("../Endless_RPG-android/assets/testPlayer.json"));
			
			JSONObject jsonObject = (JSONObject) obj;
			
			String name = (String) jsonObject.get("name");
			String charRace = (String) jsonObject.get("race");
			String charClass = (String) jsonObject.get("class");

			long str = (Long) jsonObject.get("strength");
			long dex = (Long) jsonObject.get("dexterity");
			long intel = (Long) jsonObject.get("intelligence");
			long wis = (Long) jsonObject.get("wisdom");
			long con = (Long) jsonObject.get("constitution");
			long cha = (Long) jsonObject.get("charisma");
		
			playerEntity.CreateCharacter(name, charClass, charRace, str, dex, intel, wis, con, cha);
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
