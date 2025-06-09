package game.gui;

import game.engine.Battle;
import game.gui.util.EasyBattleUtil;
import game.gui.util.HardBattleUtil;
import game.gui.util.StageUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	 StageUtil.setStage(primaryStage);
    	FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
    	Parent mainroot = rootLoader.load();
        
        primaryStage.setTitle("Attack On Titans");
        primaryStage.setScene(new Scene(mainroot));
        // Make the stage maximized
     
        Battle myEasyBattle = new Battle(1, 0, 500, 3, 250) ;
        EasyBattleUtil.setBattle(myEasyBattle);
        //////////////////
//        Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,int initialResourcesPerLane)
        Battle myHardBattle = new Battle(1, 0, 500, 5, 125) ;
        HardBattleUtil.setBattle(myHardBattle);
        
        ////////////////////
       
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}