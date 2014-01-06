package kyle.endless_rpg;

import kyle.endless_rpg.StatsRoll.ClassEnum;
import kyle.endless_rpg.StatsRoll.RaceEnum;

public class Actor {
	private int strength, dexterity, intelligence, wisdom, constitution, charisma; //done
	private int strClassMod, dexClassMod, intClassMod, wisClassMod, chaClassMod, conClassMod; //done
	private int strRaceMod, dexRaceMod, intRaceMod, wisRaceMod, chaRaceMod, conRaceMod; //done
	private int maxHitpoints, currHitPoints, maxMana, currMana;
	private int baseHitpoints, baseMana, bonusHitpoints, bonusMana;
	
	private int defense, baseDefense;
	private int experienceCurr, experienceToNextLevel, charLevel;
	private double attackPerSecond, damagePerSecond;
	private double dodge, criticalChance, criticalMult, healthRegen, manaRegen;
	private String characterClass;
	private String characterRace;
	
	private enum alighnment{
		FRIENDLY, ENEMY, NEUTRAL;
	}

	
	public void CreateCharacter(String c, String r, int str, int dex, int intel, int wis, int con, int cha){
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
		
		
		LevelUp();
	}
	
	public void LevelUp(){
		charLevel++;
		baseHitpoints = baseHitpoints + ((int)Math.ceil((double)constitution/2) - 5) + (int)Math.ceil((double)charLevel/2);
		maxHitpoints = baseHitpoints + bonusHitpoints;
		
		baseMana = baseMana + ((int)Math.ceil((double)intelligence/2) - 5) + (int)Math.ceil((double)charLevel/2);
		maxMana = baseMana + bonusMana;
	
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
