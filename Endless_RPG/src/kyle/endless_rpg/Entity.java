package kyle.endless_rpg;

import kyle.endless_rpg.StatsRoll.ClassEnum;
import kyle.endless_rpg.StatsRoll.RaceEnum;

public class Entity {
	
	private String name;
	
	private long strength, dexterity, intelligence, wisdom, constitution, charisma; //done
	private long strClassMod, dexClassMod, intClassMod, wisClassMod, chaClassMod, conClassMod; //done
	private long strRaceMod, dexRaceMod, intRaceMod, wisRaceMod, chaRaceMod, conRaceMod; //done
	private long maxHitpoints, currHitPoints, maxMana, currMana;
	private long baseHitpoints, baseMana, bonusHitpoints, bonusMana;
	
	private long defense, baseDefense;
	private long experienceCurr, experienceToNextLevel, charLevel;
	private double attackPerSecond, damagePerSecond;
	private double dodge, criticalChance, criticalMult, healthRegen, manaRegen;
	private String characterClass;
	private String characterRace;
	
	private enum alighnment{
		FRIENDLY, ENEMY, NEUTRAL;
	}

	
	public String getCharacterClass(){
		return characterClass;
	}
	
	public String getCharacterRace(){
		return characterRace;
	}
	
	public long getStr(){
		return strength;
	}
	
	public long getInt(){
		return intelligence;
	}
	
	public long getDex(){
		return dexterity;
	}
	
	public long getWis(){
		return wisdom;
	}
	
	public long getCha(){
		return charisma;
	}
	
	public long getCon(){
		return constitution;
	}
	
	public void CreateCharacter(String n, String c, String r, long str, long dex, long intel, long wis, long con, long cha){
		name = n;
		strength = str;
		dexterity = dex;
		intelligence = intel;
		wisdom = wis;
		constitution = con;
		charisma = cha;
		setRaceSelection(r);
		setClassSelection(c);
		charLevel = 0;
		baseHitpoints = 10;
		baseMana = 20;
		bonusHitpoints = 0;
		bonusMana = 0;
		experienceToNextLevel = 10;
		experienceCurr = 0;
		
		
		LevelUp();
	}
	
	public void LevelUp(){
		charLevel++;
		baseHitpoints = baseHitpoints + ((int)Math.ceil((double)constitution/2) - 5) + (int)Math.ceil((double)charLevel/2);
		maxHitpoints = baseHitpoints + bonusHitpoints;
		
		baseMana = baseMana + ((int)Math.ceil((double)intelligence/2) - 5) + (int)Math.ceil((double)charLevel/2);
		maxMana = baseMana + bonusMana;
		
		experienceToNextLevel = experienceToNextLevel*experienceToNextLevel - experienceToNextLevel;
	
	}
	
	private void setRaceSelection(String r){
		RaceEnum e = RaceEnum.valueOf(r);
		switch(e){
		case Human:
			characterRace = "Human";
			break;
		case Dwarf:
			characterRace = "Dwarf";
			conRaceMod = 2;
			wisRaceMod = 2;
			chaRaceMod = -2;
  			break;
		case Elf:
			characterRace = "Elf";
			dexRaceMod = 2;
			intRaceMod = 2;
			conRaceMod = -2;
			break;
		case Gnome:
			characterRace = "Gnome";
			dexRaceMod = 2;
			intRaceMod = 2;
			chaRaceMod = -2;
			break;			
		case Ogre:
			characterRace = "Ogre";
			strRaceMod = 4;
			conRaceMod = -2;
			intRaceMod = -2;
			wisRaceMod = -2;
			break;				
		}
	}
	
	public void setClassSelection(String s){
		ClassEnum e = ClassEnum.valueOf(s);
		switch(e){
		case Warrior:
			characterClass = "Warrior";
			strClassMod = 2;
			break;
		case Cleric:
			characterClass = "Cleric";
			wisClassMod = 2;
  			break;
		case Wizard:
			characterClass = "Wizard";
			intClassMod = 2;
			break;
		case Rogue:
			characterClass = "Rogue";
			dexClassMod = 1;
			chaClassMod = 1;
			break;			
		case Ranger:
			characterClass = "Ranger";
			dexClassMod = 2;
			break;		
		default:
			break;
		}
	}
}
