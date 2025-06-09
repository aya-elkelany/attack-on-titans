package game.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.gui.util.EasyBattleUtil;
import game.gui.util.StageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ShopController {

	@FXML
	private Button assignButton;
	
	@FXML
	private Button skipButton;
	
	 @FXML
	    private RadioButton lane1,lane2,lane3;
	 
	 @FXML
	    private RadioButton piercingCannon,sniperCannon,volleySpreadCannon,wallTrap;
	 
	 @FXML
	    private ToggleGroup lane,weapon;
	
	 @FXML
	    private Label laneResourceLabel;
	 
	 @FXML
	    private SplitPane shopPane;
	 
	Parent arenaRoot;
	
	Stage primaryStage;
	
	Battle myBattle;
	
	ArenaController arenaController;

    public void initialize() {
    	
    
    	
    	lane1.setToggleGroup(lane);
        lane2.setToggleGroup(lane);
        lane3.setToggleGroup(lane);
        
        piercingCannon.setToggleGroup(weapon);
        sniperCannon.setToggleGroup(weapon);
        volleySpreadCannon.setToggleGroup(weapon);
        wallTrap.setToggleGroup(weapon);
        
        // setRadioButtonsImages
        setRadioButtonsImages();
        
        // laneResourceLabel.setText(Integer.toString(EasyBattleUtil.getBattle().getResourcesGathered())); 
        
        // Get myBattle
        myBattle = EasyBattleUtil.getBattle();	
        
        // Set resources label
        laneResourceLabel.setText(Integer.toString( myBattle.getResourcesGathered()) );
        
        // Turn Scene initializer
        FXMLLoader arenaLoader = new FXMLLoader(getClass().getResource("arenaScene.fxml"));
        try {
			if(arenaRoot==null) {
				arenaRoot = arenaLoader.load();
				arenaController = arenaLoader.getController();
				
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
        	EasyBattleUtil.setSelectedLane(selectedLane);
        	EasyBattleUtil.setSelectedWeaponCode(selectedWeapon);
        	
        	try {
//				EasyBattleUtil.getBattle().purchaseWeapon(selectedWeapon, EasyBattleUtil.getSelectedLane());
				
				// Get lanes list
				ArrayList<Lane> laneList = new ArrayList<Lane>();
				while(! myBattle.getLanes().isEmpty()) {
					laneList.add(myBattle.getLanes().remove());	
				}
				myBattle.getLanes().addAll(laneList);
				
				myBattle.purchaseWeapon(selectedWeapon, laneList.get(selectedLane-1));

			
				arenaController.spawnTitans();
				
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
        	EasyBattleUtil.setSkipped(false);
        	myBattle.passTurn();
        	
        	arenaController.spawnTitans();
        	
        	primaryStage = StageUtil.getStage();
        	
        	if(arenaRoot!=null)primaryStage.setScene(new Scene(arenaRoot));
   
 	       
          
        });  
        
//        lane1.setOnAction(e ->{
//        	EasyBattleUtil.getBattle().getResourcesGathered();
//        });
      
    }
    
    private void setRadioButtonsImages() {
    	
    	Image gifImage = new Image(getClass().getResource("assets/weapon1.gif").toString());
        ImageView myImageView = new ImageView(gifImage);
        myImageView.setFitWidth(50);
        myImageView.setFitHeight(50);
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

	public int getLane() {
    	int selectedLane = 1;
    	if(lane1.isSelected()) 
    		selectedLane = 1;
    	if(lane2.isSelected()) 
    		selectedLane = 2;
    	if(lane3.isSelected()) 
    		selectedLane = 3;
    	return selectedLane;	
    }

    public int getWeapon() {
    	int selectedLane = 1;
    	if(piercingCannon.isSelected()) 
    		selectedLane = 1;
    	if(sniperCannon.isSelected()) 
    		selectedLane = 2;
    	if(volleySpreadCannon.isSelected()) 
    		selectedLane = 3;
    	if(wallTrap.isSelected())
    		selectedLane = 4;
    	return selectedLane;	
    }
    

}