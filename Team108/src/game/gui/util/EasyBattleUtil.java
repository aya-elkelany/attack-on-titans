package game.gui.util;

import java.util.ArrayList;

import game.engine.Battle;
import game.engine.lanes.Lane;

public class EasyBattleUtil {
	private static Battle battle;
	private static Lane selectedLane;
	private static int selectedWeaponCode;
	private static boolean isSkipped = false;
	
	
	public static boolean isSkipped() {
		return isSkipped;
	}

	public static void setSkipped(boolean isSkipped) {
		EasyBattleUtil.isSkipped = isSkipped;
	}

	public static Lane getSelectedLane() {
		return selectedLane;
	}

	public static void setSelectedLane(int selectedLane) {
		ArrayList<Lane> lanelist = new ArrayList<Lane>();
		while(! battle.getLanes().isEmpty()) {
			lanelist.add(battle.getLanes().remove());
			
		}
		battle.getLanes().addAll(lanelist);
		EasyBattleUtil.selectedLane = lanelist.get(selectedLane-1);
	}

	public static int getSelectedWeaponCode() {
		return selectedWeaponCode;
	}

	public static void setSelectedWeaponCode(int selectedWeaponCode) {
		EasyBattleUtil.selectedWeaponCode = selectedWeaponCode;
	}
	

	public static Battle getBattle() {
		return battle;
	}

	public static void setBattle(Battle battle) {
		EasyBattleUtil.battle = battle;
	}
	
	

}
