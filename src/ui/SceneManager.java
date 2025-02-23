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
    public static String getSceneName() {
    	return sceneName;
    }
    public static void switchTo(String name) {
        if (scenes.containsKey(name)) {
        	sceneName = name;
            primaryStage.setScene(scenes.get(name));
            primaryStage.show();
 
            // Ensure DPS keeps running when switching scenes
            GameLogic.startDpsHome();

            if (name.equals("STORY")) {
                ((StoryScene) scenes.get("STORY").getRoot()).updateStoryUI(); // Reset story when switching
            }
        }
    }
    
    public static void updateHomeScene() {
    	((HomeScene) scenes.get("HOME").getRoot()).updateHpMonsterHome();
    }
}