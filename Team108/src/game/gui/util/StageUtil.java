package game.gui.util;

import javafx.stage.Stage;

public class StageUtil {

	  private static Stage stage;

	  public static void setStage(Stage stage) {
		  StageUtil.stage = stage;
	  }

	  public static Stage getStage() {
	    return stage;
	  }
		
}
