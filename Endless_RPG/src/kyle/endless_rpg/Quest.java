package kyle.endless_rpg;

import java.util.Random;

public class Quest 
{
	private int duration;
	private int zoneCount;
	private String[] zones;
	private double experienceReward;
	private Entity player;
	private static final double base_xp = 20f;
	private static final double factor_xp = 2.6f;
	private double charLevel;
	private double charExpToLevel;
	
	private static final double COMMON_RARITY = 90;
	private static final double UNCOMMON_RARITY = 75;
	private static final double RARE_RARITY = 50;
	private static final double EPIC_RARITY = 10;
	private static final double LEGENDARY_RARITY = 1;

	
	public void Create(Entity p)
	{
		player = p;
		charLevel = player.getLevel();
		charExpToLevel = player.getExpTolevel();
		double min, max;
		
		min = Math.sqrt(player.getLevel());
		max = min + player.getLevel();
		min = min * 60;
		max = max * 60;
		
		Random r = new Random();
		duration = r.nextInt((int)max - (int)min +1) + (int)min;
		//10 * (lvl ^ 1.5) = amount of common mobs per level
		double mobKillsToLevel = (Math.pow(charLevel, 1.5f));
		double rarity = rarity();
		experienceReward = (charExpToLevel / mobKillsToLevel) + (charExpToLevel / mobKillsToLevel) * rarity;
		
		//experienceReward = (base_xp * (Math.pow(player.getLevel(), duration/60f)))/10;
		System.out.print(rarity + " rarity!\n");
		System.out.print(mobKillsToLevel + " mobKillsToLevel!\n");
		System.out.print(experienceReward + " experience for this quest!\n");
	}
	
	public int getDuration(){
		return duration;
	}
	
	public double getExperience(){
		return experienceReward;
	}
	
	private double rarity(){
		double mod = 0;
		int ran;
		Random r = new Random();
		ran = r.nextInt(10000 - 1) + 1;
		
		
		
		
		
		if(ran <= LEGENDARY_RARITY * 100)
			mod = 1f;
		else if(ran <= EPIC_RARITY * 100)
			mod = .75f;
		else if(ran <= RARE_RARITY * 100)
			mod = .5f;
		else if(ran <= UNCOMMON_RARITY * 100)
			mod = .25f;
		else if(ran <= COMMON_RARITY * 100)
			mod = 0f;

	
		return mod;
	}
}
