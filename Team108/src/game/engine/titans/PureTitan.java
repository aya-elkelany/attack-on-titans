package game.engine.titans;

public class PureTitan extends Titan
{
	public static final int TITAN_CODE = 1;
	
	public static final int TITAN_HP = 100;

	public int getTitanCode() {
		return TITAN_CODE;
	}

	public int getTitanHP() {
		return TITAN_HP;
	}

	public PureTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

}
