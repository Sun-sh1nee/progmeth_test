package ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;

import java.util.HashMap;

public class SceneManager {
    private static Stage primaryStage;
    private static final HashMap<String, Scene> scenes = new HashMap<>();
    private static String sceneName = "HOME";
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }
    public static void changeScene(String name, Scene scene) {
        scenes.replace(name, scene);
    }
    public static String getSceneName() {
    	return sceneName;
    }
    public static void switchTo(String name) {
        if (scenes.containsKey(name)) {
        	sceneName = name;
            primaryStage.setScene(scenes.get(name));
            primaryStage.show();
            System.out.println(name);
            // Ensure DPS keeps running when switching scenes
            GameLogic.startDpsHome();

            if (name.equals("STORY")) {
            	StoryScene storyScene = (StoryScene) scenes.get("STORY").getRoot();
            	storyScene.updateStoryUI();
                storyScene.updateEquippedCardsBar();
            }
            if (name.equals("HOME")) {
                HomeScene homeScene = (HomeScene) scenes.get("HOME").getRoot();
                homeScene.updateEquippedCardsBar();
                homeScene.updateMonsterHome();
//                GameLogic.startDpsHome();
            }
            if (name.equals("CARD_EQUIPMENT")) {
            	CardEquipmentScene equipmentScene = (CardEquipmentScene) scenes.get("CARD_EQUIPMENT").getRoot();
            	equipmentScene.refreshSlots();
                CardInventoryScene inventoryScene = (CardInventoryScene) scenes.get("CARD_INVENTORY").getRoot();
                inventoryScene.updateEquippedCardsInventory();
            }
            
            if(name.equals("END_CREDIT")) {
            	EndCreditScene endCreditScene = (EndCreditScene) scenes.get("END_CREDIT").getRoot();
            	endCreditScene.updateEndCreditUI();
            }
            
            
        }
    }
    
    public static BaseScene getSceneNode(String name) {
        if (scenes.containsKey(name)) {
            return (BaseScene) scenes.get(name).getRoot();
        }
        return null;
    }
    
}