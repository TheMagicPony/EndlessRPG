package kyle.endless_rpg;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

public class StatsRoll {
	private String classSelection;
	private String raceSelection;
	
	private int str = -1, dex = -1, intel = -1, wis = -1, cha = -1, con = -1;
	
	private int strClassMod = -1, dexClassMod = -1, intClassMod = -1, wisClassMod = -1, chaClassMod = -1, conClassMod = -1;
	private int strRaceMod = -1, dexRaceMod = -1, intRaceMod = -1, wisRaceMod = -1, chaRaceMod = -1, conRaceMod = -1;
	private int strRoll, dexRoll, intRoll, wisRoll, chaRoll, conRoll;

	
	private int roll(){
		Random ranRoll = new Random();
		int roll = (ranRoll.nextInt(7-1)+1) + (ranRoll.nextInt(7-1)+1) + (ranRoll.nextInt(7-1)+1);
		return roll;		
	}
	
    public String modifierString(int cMod, int rMod)
    {

    	String modStr = "";
    	if(cMod + rMod < 0)
    	{
    		modStr = String.valueOf(cMod + rMod);
    	}
    	else
    	{
    		modStr = "+" + String.valueOf(cMod + rMod);
    	}
    	
    	return modStr;
    }	
   
    
    public int getStr(){
    	return str;
    }
    public int getDex(){
    	return dex;
    }
    public int getInt(){
    	return intel;
    }
    public int getWis(){
    	return wis;
    }
    public int getCon(){
    	return con;
    }
    public int getCha(){
    	return cha;
    }
    
	public String getStrString(){
		int r = strRoll + strClassMod + strRaceMod;
		String s = r + "(" + modifierString(strClassMod, strRaceMod) + ")";
		return s;
	}
	
	public String getDexString(){
		int r = dexRoll + dexClassMod + dexRaceMod;
		String s = r + "(" + modifierString(dexClassMod, dexRaceMod) + ")";
		return s;
	}
	
	public String getIntString(){
		int r = intRoll + intClassMod + intRaceMod;
		String s = r + "(" + modifierString(intClassMod, intRaceMod) + ")";
		return s;
	}
	
	public String getWisString(){
		int r = wisRoll + wisClassMod + wisRaceMod;
		String s = r + "(" + modifierString(wisClassMod, wisRaceMod) + ")";
		return s;
	}
	
	public String getChaString(){
		int r = chaRoll + chaClassMod + chaRaceMod;
		String s = r + "(" + modifierString(chaClassMod, chaRaceMod) + ")";
		return s;
	}
	
	public String getConString(){
		int r = conRoll + conClassMod + conRaceMod;
		String s = r + "(" + modifierString(conClassMod, conRaceMod) + ")";
		return s;
	}
    
    public void rollStats(){
    	strRoll = roll();
    	dexRoll = roll();
    	intRoll = roll();
    	wisRoll = roll();
    	chaRoll = roll();
    	conRoll = roll();
    	
    	str = strRoll + strClassMod + strRaceMod;
    	dex = dexRoll + dexClassMod + dexRaceMod;
    	intel = intRoll + intClassMod + intRaceMod;
    	wis = wisRoll + wisClassMod + wisRaceMod;
    	cha = chaRoll + chaClassMod + chaRaceMod;
    	con = conRoll + conClassMod + conRaceMod;
    }
	
	public enum ClassEnum{
		Warrior("Warrior", 0),
		Cleric("Cleric", 1),
		Wizard("Wizard", 2),
		Rogue("Rogue", 3),
		Ranger("Ranger", 4);
		
		private String stringValue;
		private int intValue;
		
		
		private ClassEnum(String toString, int value){
			stringValue = toString;
			intValue = value;
		}
		@Override
		public String toString(){
			return stringValue;
		}

	}
	
	public enum RaceEnum{
		Human("Human", 0),
		Dwarf("Dwarf", 1),
		Elf("Elf", 2),
		Gnome("Gnome", 3),
		Ogre("Ogre", 4);
		
		private String stringValue;
		private int intValue;
		
		private RaceEnum(String toString, int value){
			stringValue = toString;
			intValue = value;
		}
		@Override
		public String toString(){
			return stringValue;
		}
	}
	
	
	private void resetClassMod(){
		strClassMod = 0;
		dexClassMod = 0;
		intClassMod = 0;
		wisClassMod = 0;
		chaClassMod = 0;
		conClassMod = 0;
	}
	
	private void resetRaceMod(){
		strRaceMod = 0;
		dexRaceMod = 0;
		intRaceMod = 0;
		wisRaceMod = 0;
		chaRaceMod = 0;
		conRaceMod = 0;
	}
	
	public void setRaceSelection(String r){
		resetRaceMod();
		RaceEnum e = RaceEnum.valueOf(r);
		switch(e){
		case Human:
			raceSelection = "Human";
			break;
		case Dwarf:
			raceSelection = "Dwarf";
			conRaceMod = 2;
			wisRaceMod = 2;
			chaRaceMod = -2;
  			break;
		case Elf:
			raceSelection = "Elf";
			dexRaceMod = 2;
			intRaceMod = 2;
			conRaceMod = -2;
			break;
		case Gnome:
			raceSelection = "Gnome";
			dexRaceMod = 2;
			intRaceMod = 2;
			chaRaceMod = -2;
			break;			
		case Ogre:
			raceSelection = "Ogre";
			strRaceMod = 4;
			conRaceMod = -2;
			intRaceMod = -2;
			wisRaceMod = -2;
			break;				
		}
	}
	
	
	public void setClassSelection(String s){
		resetClassMod();
		ClassEnum e = ClassEnum.valueOf(s);
		switch(e){
		case Warrior:
			classSelection = "Warrior";
			strClassMod = 2;
			break;
		case Cleric:
			classSelection = "Cleric";
			wisClassMod = 2;
  			break;
		case Wizard:
			classSelection = "Wizard";
			intClassMod = 2;
			break;
		case Rogue:
			classSelection = "Rogue";
			dexClassMod = 1;
			chaClassMod = 1;
			break;			
		case Ranger:
			classSelection = "Ranger";
			dexClassMod = 2;
			break;		
		default:
			break;
		}
	}


}
