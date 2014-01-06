package kyle.endless_rpg.screens;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kyle.endless_rpg.Entity;
import kyle.endless_rpg.StatsRoll;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreateScreen implements Screen {
	
	private Stage stage;
	private SpriteBatch batch;
	private Table table;
	private Table nameTable;
	private Skin skin;
	private TextButton buttonBack, buttonRoll, buttonPlay;
	
	private TextField characterNameInput;
	private TextureAtlas atlas;
	private SelectBox classDropDown, raceDropDown;
	private Label classLabel, raceLabel, nameLabel;
	private Label strLabel, dexLabel, intLabel, wisLabel, chaLabel, conLabel;
	
	private StatsRoll charOptions;

	private Sprite sprite;
	private Texture raceSprite;
	private TextureRegion region;
	
	private String classSelection = "", raceSelection = "";
	
	private ClassSelectListener classSelectListener;
	private RaceSelectListener raceSelectListener;
	private RollButtonListener rollButtonListener;
	private PlayButtonListener playButtonListener;
	
	private Texture backgroundTexture;
	private Sprite backgroundSprite;
	
	private String raceFile = "";

	private Entity playerEntity;
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			backgroundSprite.draw(batch);
			sprite.draw(batch);
		batch.end();
		
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		stage = new Stage();
		
		charOptions = new StatsRoll();
		classSelectListener = new ClassSelectListener();
		raceSelectListener = new RaceSelectListener();
		rollButtonListener = new RollButtonListener();
		playButtonListener = new PlayButtonListener();
		
		backgroundTexture = new Texture("img/temp_background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("ui/uiskin.atlas");
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
		
		buttonRoll = new TextButton("Roll!", skin);
		buttonRoll.addListener(rollButtonListener);
		
		buttonPlay = new TextButton("Play!", skin);
		buttonPlay.addListener(playButtonListener);
		
		buttonBack = new TextButton("Back", skin);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});	


		characterNameInput = new TextField("", skin);
		classLabel = new Label("Class: ", skin);
		raceLabel = new Label("Race: ", skin);
		
		nameLabel = new Label("Name: ", skin);
		strLabel = new Label("STR: ", skin);
		dexLabel = new Label("DEX: ", skin);
		conLabel = new Label("CON: ", skin);
		intLabel = new Label("INT: ", skin);
		wisLabel = new Label("WIS: ", skin);
		chaLabel = new Label("CHA: ", skin);	
		
		classDropDown = new SelectBox(new String[] {"Warrior", "Cleric", "Wizard", "Rogue", "Ranger" }, skin);
		classDropDown.addListener(classSelectListener);
		
		raceDropDown = new SelectBox(new String[] {"Human", "Dwarf", "Elf", "Gnome", "Ogre" }, skin);
		raceDropDown.addListener(raceSelectListener);

		charOptions.setClassSelection(classDropDown.getSelection());
		charOptions.setRaceSelection(raceDropDown.getSelection());
		charOptions.rollStats();
		
		strLabel.setText("STR: " + charOptions.getStrString());
		dexLabel.setText("DEX: " + charOptions.getDexString());
		wisLabel.setText("WIS: " + charOptions.getWisString());
		intLabel.setText("INT: " + charOptions.getIntString());
		conLabel.setText("CON: " + charOptions.getConString());
		chaLabel.setText("CHA: " + charOptions.getChaString());	
		
		raceSelection = raceDropDown.getSelection();
		raceFile = "data/ActorSprites/" + raceSelection + "512.png";
		raceSprite = new Texture(Gdx.files.internal(raceFile));
		sprite = new Sprite(raceSprite);
		sprite.setPosition((Gdx.graphics.getWidth() *.75f - raceSprite.getWidth()/2), 150);

		
		table = new Table(skin);
		table.setFillParent(true);
		table.top().left();
		table.add(raceLabel);
		table.add(raceDropDown);
		table.getCell(raceLabel).padBottom(10).minHeight(50).top().left();
		table.getCell(raceDropDown).padBottom(10).minHeight(50).minWidth(300).top().left();
		table.row();
		table.add(classLabel);
		table.add(classDropDown);
		table.getCell(classLabel).minHeight(50).top().left();
		table.getCell(classDropDown).padBottom(100).minHeight(50).minWidth(300);
		table.row();
		table.add(strLabel).padBottom(100).left();
		table.add(dexLabel).padBottom(100).left().padLeft(20);
		table.row();		
		table.add(conLabel).padBottom(100).left();
		table.add(intLabel).padBottom(100).left().padLeft(20);
		table.row();
		table.add(wisLabel).padBottom(50).left();
		table.add(chaLabel).padBottom(50).left().padLeft(20);
		table.row();
		table.add(buttonRoll).padBottom(20);
		table.row();
		table.add(buttonBack);
		table.row();
		table.add(buttonPlay);
		
		nameTable = new Table(skin);
		nameTable.setFillParent(true);		
		nameTable.bottom();
		nameTable.add(nameLabel);
		nameTable.add(characterNameInput).padBottom(20);
		nameTable.getCell(characterNameInput).size(Gdx.graphics.getWidth()/4, 100).padBottom(20);	

		stage.addActor(nameTable);
		stage.addActor(table);
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
		backgroundSprite.getTexture().dispose();
		sprite.getTexture().dispose();
		
	}

	
	private class RollButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			charOptions.rollStats();
			strLabel.setText("STR: " + charOptions.getStrString());
			dexLabel.setText("DEX: " + charOptions.getDexString());
			wisLabel.setText("WIS: " + charOptions.getWisString());
			intLabel.setText("INT: " + charOptions.getIntString());
			conLabel.setText("CON: " + charOptions.getConString());
			chaLabel.setText("CHA: " + charOptions.getChaString());
		}
	}

	private class ClassSelectListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			charOptions.setClassSelection(classDropDown.getSelection());
			
			strLabel.setText("STR: " + charOptions.getStrString());
			dexLabel.setText("DEX: " + charOptions.getDexString());
			wisLabel.setText("WIS: " + charOptions.getWisString());
			intLabel.setText("INT: " + charOptions.getIntString());
			conLabel.setText("CON: " + charOptions.getConString());
			chaLabel.setText("CHA: " + charOptions.getChaString());
		}
		
	}
	
	private class PlayButtonListener extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			playerEntity = new Entity();
			// TODO: Change that temp name
			playerEntity.CreateCharacter("Kyle", classDropDown.getSelection(), raceDropDown.getSelection(), charOptions.getStr(), charOptions.getDex(),
					charOptions.getInt(), charOptions.getWis(), charOptions.getCon(), charOptions.getCha());
			
			JSONObject obj = new JSONObject();
			//JSONArray statList = new JSONArray();
			
			obj.put("name", "Kyle");
			obj.put("class", playerEntity.getCharacterClass());
			obj.put("race", playerEntity.getCharacterRace());
			obj.put("strength", playerEntity.getStr());
			obj.put("dexterity", playerEntity.getDex());
			obj.put("intelligence", playerEntity.getInt());
			obj.put("wisdom", playerEntity.getWis());
			obj.put("charisma", playerEntity.getCha());
			obj.put("constitution", playerEntity.getCon());
			
			try{
				FileWriter file = new FileWriter("../Endless_RPG-android/assets/testPlayer.json");
				file.write(obj.toJSONString());
				file.flush();
				file.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			System.out.print(obj);
			
			dispose();
			((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
		}		
	}
	
	private class RaceSelectListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			charOptions.setRaceSelection(raceDropDown.getSelection());
			
			
			strLabel.setText("STR: " + charOptions.getStrString());
			dexLabel.setText("DEX: " + charOptions.getDexString());
			wisLabel.setText("WIS: " + charOptions.getWisString());
			intLabel.setText("INT: " + charOptions.getIntString());
			conLabel.setText("CON: " + charOptions.getConString());
			chaLabel.setText("CHA: " + charOptions.getChaString());
			
			
			raceSelection = raceDropDown.getSelection();
			sprite.getTexture().dispose();
	
			
			raceFile = "data/ActorSprites/" + raceSelection + "512.png";
			raceSprite = new Texture(Gdx.files.internal(raceFile));

			sprite = new Sprite(raceSprite);
			sprite.setPosition((Gdx.graphics.getWidth() *.75f - raceSprite.getWidth()/2), 150);
		}
		
	}
	
}
