package game.engine.titans;

public class ColossalTitan extends Titan
{
	public static final int TITAN_CODE = 4;
	
	public static final int TITAN_HP = 1000;
	
	
	public int getTitanCode() {
		return TITAN_CODE;
	}

	public int getTitanHP() {
		return TITAN_HP;
	}

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	@Override
	public boolean move()
	{
		boolean moveResult = super.move();
		this.setSpeed(this.getSpeed() + 1);
		return moveResult;
	}
}
