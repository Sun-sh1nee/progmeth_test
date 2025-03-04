package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;
import ui.CardEquipmentScene;
import ui.CardInventoryScene;
import ui.HomeScene;
import ui.RandomScene;
import ui.SceneManager;
import ui.StoryScene;
import ui.UpgradeScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
    	GameLogic.init();
        SceneManager.setStage(primaryStage);

        
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        
        
        
        SceneManager.addScene("HOME", new Scene(new HomeScene(), 500, 600));
        SceneManager.addScene("RANDOM", new Scene(new RandomScene(), 500, 600));
        SceneManager.addScene("STORY", new Scene(new StoryScene(), 500, 600));
        SceneManager.addScene("UPGRADE", new Scene(new UpgradeScene(), 500, 600));
        SceneManager.addScene("CARD_EQUIPMENT", new Scene(new CardEquipmentScene(), 500, 600));
        SceneManager.addScene("CARD_INVENTORY", new Scene(new CardInventoryScene(), 500, 600));
     
        
        SceneManager.switchTo("HOME");
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//hello