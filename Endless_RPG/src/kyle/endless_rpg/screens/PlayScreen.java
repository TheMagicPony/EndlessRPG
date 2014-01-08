package kyle.endless_rpg.screens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kyle.endless_rpg.Entity;
import kyle.endless_rpg.Quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PlayScreen implements Screen {
	
	private Entity playerEntity;
	
	private Stage stage;
	private SpriteBatch batch;
	private Table table;
	private Skin skin;
	private TextureAtlas atlas;
	
	private TextField progressBar;
	//private Label log;
	private Stack stack;
	private float delay = 1; //seconds
	private ScrollPane scroll;
	private List log;
	private String[] logText;
	int counter = 0;
	
	private float wait_time = 5f;
	private float time = 0;
	
	private float questDuration = 0;
	private float questTime = 0;
	
	
	public void update(float deltaTime){
		time += deltaTime;
		if(time >= wait_time){
			System.out.print("Waited\n");
			time -= wait_time;
		}
	}
	
	public void questUpdate(float deltaTime){
		questTime += deltaTime;
		if(questTime >= questDuration){
			
			questTime -= questDuration;
			playerEntity.questComplete(true);
			
		}

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!playerEntity.onQuest()){
			Quest q = new Quest();
			q.Create(playerEntity);
			playerEntity.setQuest(q);
			playerEntity.setQuestBool(true);
			questDuration = playerEntity.getQuestDuration();
			System.out.print("Quest duration " + playerEntity.getQuestDuration()+ "\n");
		}
		
		questUpdate(delta);
		//update(delta);
		batch.begin();
		batch.end();

	
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
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


//		logText = new String[]{"0"};
//		log = new List(logText, skin.get("black", ListStyle.class));
//		scroll = new ScrollPane(log, skin);
//
//		Timer.schedule(new Task(){
//			@Override
//			public void run(){
//				String[] tmp  = new String[logText.length+1];
//				tmp[0] = Integer.toString(counter + 1);
//				for(int i = 0; i < logText.length; i++){
//					tmp[i+1] = logText[i];
//
//				}
//				counter++;
//				logText = new String[tmp.length];
//				logText = tmp;
//				log.setItems(logText);
//			}
//		}, delay, 1);	
//
//		table = new Table(skin);
//		table.setFillParent(true);
//		table.add(scroll);
//		table.getCell(scroll).size(Gdx.graphics.getWidth()/4, 100).padBottom(20);	
//		table.top().left();
//
//		stage.addActor(table);

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
			System.out.print("Character Loaded\n");
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
