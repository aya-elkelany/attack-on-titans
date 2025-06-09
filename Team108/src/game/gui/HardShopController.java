package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;

import game.gui.util.HardBattleUtil;
import game.gui.util.StageUtil;


public class HardShopController {
	

	 @FXML 
	   private Button assignButton, skipButton;
	@FXML
	private RadioButton lane1, lane2, lane3, lane4, lane5;
	@FXML 
	private RadioButton piercingCannon, sniperCannon, volleySpreadCannon, wallTrap;
	@FXML
	private Label laneResourceLabel;
	@FXML 
	private Parent arenaRoot;
	 @FXML
	    private ToggleGroup lane,weapon;
	Stage primaryStage;
    private Battle myBattle;
    private HardArenaController hardArenaController;

 public void initialize() {
    	
    
    	
	 lane1.setToggleGroup(lane);
     lane2.setToggleGroup(lane);
     lane3.setToggleGroup(lane);
     lane4.setToggleGroup(lane);
     lane5.setToggleGroup(lane);
     
     
     piercingCannon.setToggleGroup(weapon);
     sniperCannon.setToggleGroup(weapon);
     volleySpreadCannon.setToggleGroup(weapon);
     wallTrap.setToggleGroup(weapon);
		myBattle = HardBattleUtil.getBattle();
        
        
        // setRadioButtonsImages
        setRadioButtonsImages();
        
        // laneResourceLabel.setText(Integer.toString(EasyBattleUtil.getBattle().getResourcesGathered())); 
        
        // Get myBattle
        myBattle = HardBattleUtil.getBattle();	
        
        // Set resources label
        laneResourceLabel.setText(Integer.toString( myBattle.getResourcesGathered()) );
        
        // Turn Scene initializer
        FXMLLoader arenaLoader = new FXMLLoader(getClass().getResource("HardArenaScene.fxml"));
        try {
			if(arenaRoot==null) {
				arenaRoot = arenaLoader.load();
				hardArenaController = arenaLoader.getController();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        // Handle button action
        assignButton.setOnAction(e -> {
        	
         	// Access the Stage via the root node
        	int selectedLane = getLane();
        	int selectedWeapon = getWeapon();
        	HardBattleUtil.setSelectedLane(selectedLane);
        	HardBattleUtil.setSelectedWeaponCode(selectedWeapon);
        	
        	try {
//				EasyBattleUtil.getBattle().purchaseWeapon(selectedWeapon, EasyBattleUtil.getSelectedLane());
				
				// Get lanes list
				ArrayList<Lane> laneList = new ArrayList<Lane>();
				while(! myBattle.getLanes().isEmpty()) {
					laneList.add(myBattle.getLanes().remove());	
				}
				myBattle.getLanes().addAll(laneList);
				
				myBattle.purchaseWeapon(selectedWeapon, laneList.get(selectedLane-1));

			
				hardArenaController.spawnTitans();
				
			} catch (InsufficientResourcesException | InvalidLaneException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("An error has occurred");
				alert.setContentText(e1.getMessage());

				// Show the alert dialog
				alert.showAndWait();
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	primaryStage = StageUtil.getStage();
        	primaryStage = StageUtil.getStage();
           
        	if(arenaRoot!=null)primaryStage.setScene(new Scene(arenaRoot));
        	 
 	       
          
        });
        
        skipButton.setOnAction(e -> {
        	
         	// Access the Stage via the root node
        	HardBattleUtil.setSkipped(false);
        	myBattle.passTurn();
        	
        	hardArenaController.spawnTitans();
        	
        	primaryStage = StageUtil.getStage();
        	
        	if(arenaRoot!=null)primaryStage.setScene(new Scene(arenaRoot));
   
 	       
          
        });  
        

      
    }


    private void setRadioButtonsImages() {
        // Implementation remains similar, set images for radio buttons
    	Image gifImage = new Image(getClass().getResource("assets/weapon1.gif").toString());
        ImageView myImageView = new ImageView(gifImage);
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        piercingCannon.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/weapon2.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        sniperCannon.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/weapon3.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        volleySpreadCannon.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/weapon4.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        wallTrap.setGraphic(myImageView);
    }

    private int getLane() {
        if (lane1.isSelected()) return 1;
        if (lane2.isSelected()) return 2;
        if (lane3.isSelected()) return 3;
        if (lane4.isSelected()) return 4;
        if (lane5.isSelected()) return 5;
        return 1; // Default to lane 1 if none selected
    }

    private int getWeapon() {
        if (piercingCannon.isSelected()) return 1;
        if (sniperCannon.isSelected()) return 2;
        if (volleySpreadCannon.isSelected()) return 3;
        if (wallTrap.isSelected()) return 4;
        return 1; // Default to weapon 1 if none selected
    }
}
