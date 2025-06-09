package game.gui;

import java.io.IOException;

import game.engine.Battle;
import game.gui.util.EasyBattleUtil;
import game.gui.util.HardBattleUtil;
import game.gui.util.StageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
	
    @FXML
    private Label label;
    @FXML
    private Button startArenaButton;
    @FXML
    private TextArea gameinstrTextBox;
    @FXML
    private Pane welcomePane;
    @FXML
    private ImageView backgroundImageView; // Make sure this is correctly referenced in the FXML
    @FXML private RadioButton easyRB;
    @FXML private RadioButton hardRB;
    @FXML private ToggleGroup mode;

    private Parent shopRoot;
    private Battle myBattle;

    public void initialize() {
        // Set background image and its opacity
//        Image image = new Image(getClass().getResource("assets/wecomeBackground.png").toString());
//        backgroundImageView.setImage(image);
//        backgroundImageView.autosize();
//        backgroundImageView.setOpacity(0.5); // Set the desired opacity (0.0 - fully transparent, 1.0 - fully opaque)

        // Set Labels
      
        gameinstrTextBox.setText("Game Instructions:\n\t1- Set the difficulty of the game.\n\t2- Setup each turn by assigning weapons to each lane.\n\t3- Start the game.\n\t4- Have fun :)");

       //////////////////////////////////////// 
       
    
    
    startArenaButton.setOnAction(e -> {
        try {
            if (mode.getSelectedToggle() == easyRB) {
                // Set up easy mode battle and load its scene
                myBattle = new Battle(1, 0, 100, 3, 250);
                EasyBattleUtil.setBattle(myBattle);
                switchScene("shopScene.fxml");
            } else {
                // Set up hard mode battle and load its scene
                myBattle = new Battle(1, 0, 100, 5, 125);
                HardBattleUtil.setBattle(myBattle);
                switchScene("HardShop.fxml");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
}

private void switchScene(String fxmlFile) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent root = loader.load();
    Stage stage = (Stage) startArenaButton.getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
}
}
