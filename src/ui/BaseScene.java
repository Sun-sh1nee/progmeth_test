package ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
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
        topBar = new HBox(20);
        topBar.setMinHeight(80);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-padding: 15px; -fx-background-color: lightgray;");

        
        HBox gemCounter = new HBox(10);
        gemCounter.setAlignment(Pos.CENTER_LEFT);
        gemCounter.setMaxWidth(90);
        

        String gemPath = ClassLoader.getSystemResource("objects/gem.png").toString();
        ImageView gemIcon = new ImageView(new Image(gemPath));
        gemIcon.setFitWidth(25);
        gemIcon.setFitHeight(25);

        Label gemCount = new Label();
        gemCount.textProperty().bind(GameLogic.getGemCountProperty().asString());
        gemCount.setFont(new Font(18));
        gemCount.setStyle("-fx-font-weight: bold;");
       
        
        gemCounter.getChildren().addAll(gemIcon, gemCount);
        VBox croissantContainer = new VBox(10);
        croissantContainer.setAlignment(Pos.CENTER);

        String croissantPath = ClassLoader.getSystemResource("objects/croissant.png").toString();
        ImageView croissantIcon = new ImageView(new Image(croissantPath));
        croissantIcon.setFitWidth(25);
        croissantIcon.setFitHeight(20);
        croissantIcon.setRotate(10);

        HBox croissantInfoPanel = new HBox(5);
        croissantInfoPanel.setAlignment(Pos.CENTER);
        croissantInfoPanel.setPrefWidth(200);

        croissantCountLabel = new Label();
        croissantCountLabel.textProperty().bind(GameLogic.croissantCountProperty().asString());
        croissantCountLabel.setFont(new Font(22));
        croissantCountLabel.setStyle("-fx-font-weight: bold;");
        croissantPerClickLabel = new Label();
        croissantPerClickLabel.textProperty().bind(GameLogic.attackPerClickProperty().asString());
        HBox perClickH = new HBox(5, croissantPerClickLabel, new Label("/ click"));
        perClickH.setAlignment(Pos.CENTER);

        croissantPerSecondLabel = new Label();
        croissantPerSecondLabel.textProperty().bind(GameLogic.getDamagePerSecProperty().asString());
        HBox perSecH = new HBox(5, croissantPerSecondLabel, new Label("/ sec"));
        perSecH.setAlignment(Pos.CENTER);

        croissantInfoPanel.getChildren().addAll(croissantCountLabel, croissantIcon);
        croissantContainer.getChildren().addAll(croissantInfoPanel, perClickH, perSecH);

    
        HBox centerContainer = new HBox();
        centerContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerContainer, Priority.ALWAYS); 
        centerContainer.getChildren().add(croissantContainer);

        String settingsPath = ClassLoader.getSystemResource("objects/setting.png").toString();
        ImageView settingsIcon = new ImageView(new Image(settingsPath));
        settingsIcon.setFitWidth(30);
        settingsIcon.setFitHeight(30);

        StackPane settingsButton = new StackPane(settingsIcon);
        settingsButton.setOnMouseClicked(e -> toggleSettingsPage());
        settingsButton.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(gemCounter, centerContainer, settingsButton);

        bodyContainer = new StackPane();
        bodyContainer.setMinHeight(400);
        bodyContainer.setMaxHeight(400);
        VBox.setVgrow(bodyContainer, Priority.ALWAYS);

        navBar = new HBox(20);
        navBar.setPrefHeight(80);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 15px; -fx-background-color: lightgray;");

        Label randomButton = new Label("⬆️");
        Label homeButton = new Label("🏠");
        Label storyButton = new Label("🎲");
        Label upgradeButton = new Label("⬆️");
        Label inventory = new Label("📦");

        inventory.setOnMouseClicked(e -> SceneManager.switchTo("CARD_EQUIPMENT"));
        randomButton.setOnMouseClicked(e -> SceneManager.switchTo("RANDOM"));
        homeButton.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
        storyButton.setOnMouseClicked(e -> SceneManager.switchTo("STORY"));
        upgradeButton.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

        navBar.getChildren().addAll(randomButton, homeButton, storyButton, upgradeButton, inventory);

        mainContainer = new VBox(topBar, bodyContainer, navBar);
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
        System.out.println(newContent);
        bodyContainer.getChildren().add(newContent);
    }

    
    
}