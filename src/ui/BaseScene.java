package ui;

import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.GameLogic;

public class BaseScene extends VBox {
    protected HBox topBar;
    protected StackPane bodyContainer;
    protected HBox navBar;
    protected Label croissantCountLabel;
    protected Label croissantPerSecondLabel;
    protected Label croissantPerClickLabel;
    
    
    protected StackPane rootContainer; 
    protected VBox mainContainer; 
    protected StackPane settingsContainer;
    protected boolean isSettingsOpen = false;
   
    

    public BaseScene() {
    	
        topBar = new HBox(10);
        topBar.setMinHeight(80);

        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

        HBox gemCounter = new HBox(5);
        gemCounter.setAlignment(Pos.CENTER_LEFT);
        Label gemIcon = new Label("💎");
        Label gemCount = new Label();
        gemCount.textProperty().bind(GameLogic.getGemCountProperty().asString());
        gemCount.setFont(new Font(18));
        gemCounter.getChildren().addAll(gemIcon, gemCount);

        VBox croissantInfoPanel = new VBox(2);
        croissantInfoPanel.setAlignment(Pos.CENTER);
        croissantInfoPanel.setPrefWidth(150);
        croissantCountLabel = new Label();
        croissantCountLabel.textProperty().bind(GameLogic.croissantCountProperty().asString());
        croissantCountLabel.setFont(new Font(22));
        
        croissantPerClickLabel = new Label();
        croissantPerClickLabel.textProperty().bind(GameLogic.attackPerClickProperty().asString());
        HBox perClickH = new HBox(croissantPerClickLabel , new Label("/clicked"));
        perClickH.setSpacing(2);
        perClickH.setAlignment(Pos.CENTER);
        croissantPerSecondLabel = new Label();
        croissantPerSecondLabel.textProperty().bind(GameLogic.getDamagePerSecProperty().asString());
        HBox perSecH = new HBox(croissantPerSecondLabel , new Label("/sec"));
        perSecH.setSpacing(2);
        perSecH.setAlignment(Pos.CENTER);
        croissantInfoPanel.getChildren().addAll(croissantCountLabel, perClickH, perSecH);

        Label settingsButton = new Label("⚙️");
        //settingsButton.setOnMouseClicked(e -> System.out.println("open setting"));
        settingsButton.setOnMouseClicked(e -> {
        	toggleSettingsPage();
        });
        
        topBar.getChildren().addAll(gemCounter, croissantInfoPanel, settingsButton);

        bodyContainer = new StackPane();
        bodyContainer.setMinHeight(400);
        bodyContainer.setMaxHeight(400);
        VBox.setVgrow(bodyContainer, Priority.ALWAYS);

        navBar = new HBox(30);
        navBar.setPrefHeight(80);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

       
        
        
        ImageView homeIcon = new ImageView(new Image(loadImagePath("icon/HomeIcon.png")));
        homeIcon.setFitWidth(50); 
        homeIcon.setFitHeight(50);
        
        ImageView randomIcon = new ImageView(new Image(loadImagePath("icon/diceIcon.png")));
        randomIcon.setFitWidth(30);
        randomIcon.setFitHeight(30);
        
        ImageView storyIcon = new ImageView(new Image(loadImagePath("icon/crossedSwordsIcon.png")));
        storyIcon.setFitWidth(40);
        storyIcon.setFitHeight(40);
        
        ImageView upgradeIcon = new ImageView(new Image(loadImagePath("icon/arrowUpIcon.png")));
        upgradeIcon.setFitWidth(30);
        upgradeIcon.setFitHeight(30);

        ImageView inventoryIcon = new ImageView(new Image(loadImagePath("icon/backpackIcon.png")));
        inventoryIcon.setFitWidth(40);
        inventoryIcon.setFitHeight(30);
        
        
        
        inventoryIcon.setOnMouseClicked(e -> SceneManager.switchTo("CARD_EQUIPMENT"));
        randomIcon.setOnMouseClicked(e -> SceneManager.switchTo("RANDOM"));
        homeIcon.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
        storyIcon.setOnMouseClicked(e -> SceneManager.switchTo("STORY"));
        upgradeIcon.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

        
        navBar.getChildren().addAll(randomIcon, homeIcon, storyIcon, upgradeIcon,inventoryIcon);


        
        mainContainer = new VBox(topBar, bodyContainer, navBar);
        mainContainer.setStyle("-fx-background-color: #FBEDBE;");
        mainContainer.setAlignment(Pos.CENTER);
        
        createSettingsPage();
        
        rootContainer = new StackPane(mainContainer, settingsContainer);
        rootContainer.setAlignment(Pos.CENTER);

        this.getChildren().add(rootContainer);
        
    }
    
    private void createSettingsPage() {

        Region background = new Region();
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        background.setPrefSize(800, 600);
        background.setOnMouseClicked(e -> toggleSettingsPage()); // คลิกข้างนอกเพื่อปิด

        VBox settingsBox = new VBox(15);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setStyle(
        	    "-fx-background-color: white; " +
        	    "-fx-padding: 15px; " +
        	    "-fx-background-radius: 10px;"
        );
        settingsBox.setMaxSize(250, 150); // ลดขนาดลง

        Label title = new Label("Settings");
        title.setFont(new Font(20)); // ลดขนาดตัวอักษร

        Button toggleMusicButton = new Button();
        toggleMusicButton.textProperty().bind(GameLogic.isMusicProperty().map(value -> value ? "Music: ON" : "Music: OFF"));
        toggleMusicButton.setOnAction(e -> toggleMusic());

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> toggleSettingsPage());

        settingsBox.getChildren().addAll(title, toggleMusicButton, closeButton);

        // 🟢 StackPane จัดหน้า Settings ให้อยู่ตรงกลาง
        StackPane settingsContent = new StackPane(settingsBox);
        settingsContent.setAlignment(Pos.CENTER);

        // 🟢 StackPane ครอบหน้า Settings + Background	
        settingsContainer = new StackPane(background, settingsContent);
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.setVisible(false); // ซ่อนตอนเริ่มเกม
    }

    private void toggleSettingsPage() {
        isSettingsOpen = !isSettingsOpen;
        settingsContainer.setVisible(isSettingsOpen);
    }

    private void toggleMusic() {
        GameLogic.toggleMusic();
    }
    
    
    protected String formatNumber(long number) {
        return String.format("%,d", number);
    }
    
    
    public void switchBody(javafx.scene.Node newContent) {
        bodyContainer.getChildren().clear();
        bodyContainer.getChildren().add(newContent);
    }
    
    
    private String loadImagePath (String path) {
    	return ClassLoader.getSystemResource(path).toString();
    }
    
    
}