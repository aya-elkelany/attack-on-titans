package game.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;
import game.gui.util.EasyBattleUtil;
import game.gui.util.StageUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
//
//import java.io.IOException;
//import game.engine.Battle;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ArenaController {
	
//	@FXML
//	private Button startArenaButton;
	private Battle myBattle;

	@FXML
    private Button purchaseWeaponButton;
	@FXML
    private Button passTurnButton;
	
	@FXML
    private Pane lanePane1;
	@FXML
    private Pane lanePane2;
	@FXML
    private Pane lanePane3;
	
	private ProgressBar pbStart;
	
	@FXML
    private ProgressBar wall1PB;
	@FXML
    private ProgressBar wall2PB;
	@FXML
    private ProgressBar wall3PB;
	
	@FXML
	private Label l1w1Label;
	@FXML
	private Label l1w2Label;
	@FXML
	private Label l1w3Label;
	@FXML
	private Label l1w4Label;
	@FXML
	private Label l2w1Label;
	@FXML
	private Label l2w2Label;
	@FXML
	private Label l2w3Label;
	@FXML
	private Label l2w4Label;
	@FXML
	private Label l3w1Label;
	@FXML
	private Label l3w2Label;
	@FXML
	private Label l3w3Label;
	@FXML
	private Label l3w4Label;
	
	@FXML
	private Label scoreL;
	@FXML
	private Label turnL;
	@FXML
	private Label phaseL;
	@FXML
	private Label resourceL;
	@FXML
	private Label laneL;
	
	@FXML
	private Label wallHealth1,  wallHealth2, wallHealth3;
	
	 @FXML
	    private RadioButton lane1,lane2,lane3;
	 
	 @FXML
	    private RadioButton piercingCannon,sniperCannon,volleySpreadCannon,wallTrap;
	 
	 @FXML
	    private ToggleGroup lane,weapon;
	 
	 @FXML
	 private Label dangerLevel1,dangerLevel2,dangerLevel3;
	
	 @FXML
	 private ImageView laneImage1,laneImage2, laneImage3;
	 
	 @FXML
	 private Pane shopPane;
	 
	Parent shopRoot;
	Parent welcomeRoot;
	
	Stage primaryStage;
	
	private List<ImageView> laneImageViews;

    public void initialize() {
    	

    	
    laneImageViews = Arrays.asList(laneImage1, laneImage2, laneImage3);
    	
    	setStatsImages();
    	lane1.setToggleGroup(lane);
        lane2.setToggleGroup(lane);
        lane3.setToggleGroup(lane);
        
        piercingCannon.setToggleGroup(weapon);
        sniperCannon.setToggleGroup(weapon);
        volleySpreadCannon.setToggleGroup(weapon);
        wallTrap.setToggleGroup(weapon);
		myBattle = EasyBattleUtil.getBattle();	
		
		purchaseWeaponButton.setOnAction(e -> {
        	//TURN 2
			
			int selectedLane = getLane();
        	int selectedWeapon = getWeapon();
        	
        	
        	
        	EasyBattleUtil.setSelectedLane(selectedLane);
        	EasyBattleUtil.setSelectedWeaponCode(selectedWeapon);
        	
        	try {

				// Get lanes list
				ArrayList<Lane> laneList = new ArrayList<Lane>();
				while(! myBattle.getLanes().isEmpty()) {
					laneList.add(myBattle.getLanes().remove());	
				}
				myBattle.getLanes().addAll(laneList);
				
				if(selectedLane>=1) myBattle.purchaseWeapon(selectedWeapon, laneList.get(selectedLane -1));

			
				spawnTitans();
				
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
        	
        });
		
		passTurnButton.setOnAction(e -> {
			
			EasyBattleUtil.setSkipped(false);
        	
        	myBattle.passTurn();
        	
    		// Spawn titans
        	spawnTitans();
        });
        
		
    }



	public void spawnTitans() {
		// Get lanes list
		ArrayList<Lane> laneList = new ArrayList<>(myBattle.getLanes());  // Make a copy for safe iteration

		

		int  dist = 0;
		double  prog = 0;
		for (int l = 0; l < laneList.size(); l++) {
			
//			// Clear Pane
			getLanePane(l).getChildren().clear();
			
			//Set Pane with graphics
			
			Lane lane =laneList.get(l);
			
			// Get titans list
			ArrayList<Titan> titanList = new ArrayList<>(lane.getTitans());
			
			
			// Set Danger Level
			setLaneDangerLevel(l,lane.getDangerLevel() , lane.getTitans().size());
			
			
			
			for (int t = 0; t < titanList.size(); t++) {
				
				
				Titan titan = titanList.get(t);
			
				dist = titan.getDistance();
				prog = titan.getCurrentHealth();
				
				// 1- Progress bar
				pbStart = new ProgressBar((double)prog/titan.getTitanHP()); 
		        // Add the image to the lane VBox
		        getLanePane(l).getChildren().add(pbStart);
		        pbStart.setLayoutX(getTitanImageXPosition(titan.getTitanCode())); // X-coordinate
		        pbStart.setLayoutY(-10 + getTitanTransitionYPosition(dist)); // Y-coordinate
		        // Create a Timeline animation
		        Timeline timeline = new Timeline();
                        KeyValue keyValue = new KeyValue(pbStart.progressProperty(), (double) prog / titan.getTitanHP());
		        KeyFrame keyFrame = new KeyFrame(new Duration(1000), keyValue);
		        timeline.getKeyFrames().add(keyFrame);
		        timeline.play();
		        // Set animation
//				TranslateTransition progressTransition = new TranslateTransition(Duration.seconds(3), pbStart);
//				progressTransition.setToY(getTitanTransitionYPosition(dist));
//				progressTransition.setCycleCount(0);
//				progressTransition.play();
		        
		        // Add label
		        Label progressLabel = new Label();
		        progressLabel.setLayoutX(getTitanImageXPosition(titan.getTitanCode()) + 55 );
		        progressLabel.setLayoutY(-10 + getTitanTransitionYPosition(dist)); 
                        progressLabel.setText(String.format("%.0f%%", (double) prog / titan.getTitanHP() * 100));
		        getLanePane(l).getChildren().add(progressLabel);
		        
		        // 2- ImageView
				ImageView titanImage = getTitanImage(titan.getTitanCode());
				// Set the image size
				titanImage.setFitHeight(40);
				titanImage.setPreserveRatio(true);
				// Add the image to the lane VBox
				if(titanImage != null) {
					getLanePane(l).getChildren().add(titanImage);
				}
				// Set the image position
				titanImage.setLayoutX(getTitanImageXPosition(titan.getTitanCode())); // X-coordinate
				titanImage.setLayoutY(10 + getTitanTransitionYPosition(dist)); // Y-coordinate
				
				// Set animation
//				TranslateTransition transition = new TranslateTransition(Duration.seconds(3), titanImage);
//		        transition.setToY(getTitanTransitionYPosition(dist));
//		        transition.setCycleCount(0);
//		        transition.play();
		        
			}
			
			// Get weapons list
			ArrayList<Weapon> weaponList = lane.getWeapons();
			
			// Set lane weapons numbers
			updateLaneWeapons(l,weaponList);
			
			// Update lane wall health number
			updateLaneWallHealth(l,lane.getLaneWall().getCurrentHealth());
			
			// Update lane image if lost
			if(lane.isLaneLost()) {
				
				Image pngImage = new Image(getClass().getResource("assets/disabledLane1.png").toString());
		        ImageView myImageView = getLaneImageView(l);
		        myImageView.setImage(pngImage);
		        
			}
			
		}
						
		// Set application stats
		setStats();
//		Pane welcomeRoot = new Pane(); // Simple pane, or more complex setup as needed
//
//		// Example of loading from FXML
//		try {
//		    welcomeRoot = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		    System.out.println("Failed to load Welcome Scene FXML.");
//		}
		//check if game is over
		if(myBattle.isGameOver()) {
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Game Over");
		    alert.setHeaderText("GAME OVER!!");
		    alert.setContentText("SCORE: " + myBattle.getScore());

		    ImageView gifView = new ImageView(new Image(getClass().getResourceAsStream("assets/clownboba.gif")));
		    gifView.setFitWidth(300);  // Set width as per your requirement
		    gifView.setFitHeight(200); // Set height as per your requirement
		    
		    alert.setGraphic(gifView); // Set the ImageView as the graphic of the alert
		    alert.showAndWait();

		    // Reload the main scene to reset its state
		    reloadMainScene();
		}}

		private void reloadMainScene() {
		    try {
		        Stage primaryStage = StageUtil.getStage();
		        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
		        Parent mainroot = mainLoader.load();
		        primaryStage.setScene(new Scene(mainroot));
		        primaryStage.show();
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.out.println("Error reloading the main scene.");
		    }
		}

	



	
	
	private void updateLaneWallHealth(int laneIndex, int health) 
	{
		int baseHealth = 10000;
		switch(laneIndex) {
		case 0:
//			wallHealth1.setText(Integer.toString(health));
			wall1PB.setProgress((double)health/baseHealth);
			break;
		case 1:
//			wallHealth2.setText(Integer.toString(health));
			wall2PB.setProgress((double)health/baseHealth);
			break;
		case 2:
		//	wallHealth3.setText(Integer.toString(health));
			wall3PB.setProgress((double)health/baseHealth);
			break;
		default:
			wall1PB.setProgress((double)health/baseHealth);
			break;
		}
	}
	
	private void setLaneDangerLevel(int laneIndex, int dangerLevel, int titansCount )
	{
		switch(laneIndex) {
		case 0:
			dangerLevel1.setText("Danger Level: "+ dangerLevel );
			break;
		case 1:
			dangerLevel2.setText("Danger Level: "+ dangerLevel );
			break;
		case 2:
			dangerLevel3.setText("Danger Level: "+ dangerLevel );
			break;
		default:
			dangerLevel1.setText("Danger Level: "+ dangerLevel );
			break;
		}
	}
	
	private void updateLaneWeapons(int laneIndex, ArrayList<Weapon> weaponList) {
	    int[] weaponCounters = new int[4];  // Array to hold counts for 4 types of weapons

	    // Count each weapon based on its code
	    for (Weapon weapon : weaponList) {
	        int code = weapon.weaponCode();
	        if (code >= 1 && code <= weaponCounters.length) {
	            weaponCounters[code - 1]++;  // Subtract 1 to match zero-based index
	        }
	    }

	    // Update the UI labels only once per function call, after all counts are updated
	    switch (laneIndex) {
	        case 0:
	            l1w1Label.setText(Integer.toString(weaponCounters[0]));
	            l1w2Label.setText(Integer.toString(weaponCounters[1]));
	            l1w3Label.setText(Integer.toString(weaponCounters[2]));
	            l1w4Label.setText(Integer.toString(weaponCounters[3]));
	            break;
	        case 1:
	            l2w1Label.setText(Integer.toString(weaponCounters[0]));
	            l2w2Label.setText(Integer.toString(weaponCounters[1]));
	            l2w3Label.setText(Integer.toString(weaponCounters[2]));
	            l2w4Label.setText(Integer.toString(weaponCounters[3]));
	            break;
	        case 2:
	            l3w1Label.setText(Integer.toString(weaponCounters[0]));
	            l3w2Label.setText(Integer.toString(weaponCounters[1]));
	            l3w3Label.setText(Integer.toString(weaponCounters[2]));
	            l3w4Label.setText(Integer.toString(weaponCounters[3]));
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid lane index: " + laneIndex);
	    }
	}


    private void setStats() {
    	
    	scoreL.setText("Score: "+ myBattle.getScore());
    	turnL.setText("Turn: " + myBattle.getNumberOfTurns());
    	phaseL.setText("Phase: "+ myBattle.getBattlePhase().name());
    	resourceL.setText("Resources: "+ myBattle.getResourcesGathered());
    	laneL.setText("Lanes: "+ myBattle.getLanes().size());
    	
    }
    
    private void setStatsImages() {
    	
    	Image gifImage = new Image(getClass().getResource("assets/score.gif").toString());
        ImageView myImageView = new ImageView(gifImage);
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        scoreL.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/turn.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        turnL.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/phase.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        phaseL.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/resource.gif").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        resourceL.setGraphic(myImageView);
        
        gifImage = new Image(getClass().getResource("assets/lane01.jpeg").toString());
        myImageView = new ImageView(gifImage);	
        myImageView.setFitWidth(40);
        myImageView.setFitHeight(40);
        laneL.setGraphic(myImageView);
		
	}
    
    
    private ImageView getTitanImage(int index) {
    	
    	ImageView myImage;
    	Image gifImage;
    	
    	switch(index) {
    		case 1:
    			gifImage = new Image(getClass().getResource("assets/titan.png").toString());
    			myImage = new ImageView(gifImage);	//PureTitan
    			break;
    		case 2:
    			gifImage = new Image(getClass().getResource("assets/titan2.png").toString());
    			myImage = new ImageView(gifImage);	//AbnormalTitan
    			break;
    		case 3:
    			gifImage = new Image(getClass().getResource("assets/titan3.png").toString());
    			myImage = new ImageView(gifImage);	//ArmoredTitan
    			break;
    		case 4:
    			gifImage = new Image(getClass().getResource("assets/titan4.png").toString());
    			myImage = new ImageView(gifImage);	//ColossalTitan
    			break;
    		default:
    			gifImage = new Image(getClass().getResource("assets/titan.gif").toString());
    			myImage = new ImageView(gifImage);	//PureTitan
    			break;	
    	}
    	
    	return myImage;
    }
    
    private double getTitanTransitionYPosition(int dist) {
    	
    
    	return (-3.65 *(100-dist))-40;
    } 
    
    private int getTitanImageXPosition(int index) {
    	
    	return (index -1) *100 ;
    }

    private Pane getLanePane(int index) {
    	Pane lane;
		switch(index) {
			case 0:
				lane = lanePane1;
				break;
			case 1:
				lane = lanePane2;
				break;
			case 2:
				lane = lanePane3;
				break;
			default: throw new IllegalArgumentException("Invalid lane index: " + index);
		}
	
		return lane;
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
    	int selectedWeapon = 1;
    	if(piercingCannon.isSelected()) 
    		selectedWeapon = 1;
    	if(sniperCannon.isSelected()) 
    		selectedWeapon = 2;
    	if(volleySpreadCannon.isSelected()) 
    		selectedWeapon = 3;
    	if(wallTrap.isSelected())
    		selectedWeapon = 4;
    	return selectedWeapon;	
    }
    
    private ImageView getLaneImageView(int laneIndex)
    {
    	ImageView imageView;
		switch(laneIndex) {
			case 0:
				imageView = laneImage1;
				break;
			case 1:
				imageView = laneImage2;
				break;
			case 2:
				imageView = laneImage3;
				break;
			default:
				imageView = laneImage1;
				break;	
		}
	
		return imageView;
    }
    
    
}