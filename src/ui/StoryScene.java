package ui;

import java.util.Random;

import card.Activatable;
import card.BaseCard;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;

public class StoryScene extends BaseScene {
    private Label hpLabel;
    private Label stageNowLabel;
    private ProgressBar hpBar;
    private StackPane monsterArea;
    private ProgressBar timerProgress;
    private HBox equippedCardsBar;
    
    private final int marginLayout = 10;

    public StoryScene() {
        super();

        updateStoryUI();
    }
    
    private void letStart() {
    	stageNowLabel = new Label();
    	stageNowLabel.textProperty().bind(GameLogic.storyStateProperty().asString());
    	
    	
        hpLabel = new Label();
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);

        hpLabel.setText(getAccessibleHelp());
        hpLabel.textProperty().bind(GameLogic.monsterHpStoryProperty().asString("%.0f"));
        hpBar.progressProperty().bind(Bindings.createDoubleBinding(
        	    () -> GameLogic.monsterHpStoryProperty().get() / GameLogic.getMonster().getMonsterHp(),
        	    GameLogic.monsterHpStoryProperty()
        ));

     
        monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());

   
        timerProgress = new ProgressBar(1.0);
        timerProgress.setStyle("-fx-accent: yellow;");
        timerProgress.setPrefWidth(200);
        timerProgress.setPrefHeight(12);
        timerProgress.progressProperty().bind(GameLogic.storyTimerProgressProperty());
        
        equippedCardsBar = new HBox(10);
        equippedCardsBar.setPadding(new Insets(10));
        equippedCardsBar.setSpacing(20);
        equippedCardsBar.setAlignment(Pos.CENTER);
        updateEquippedCardsBar();

    	
        VBox storyLayout = new VBox(marginLayout, stageNowLabel, hpLabel, hpBar, timerProgress, monsterArea , equippedCardsBar);
        storyLayout.setAlignment(Pos.CENTER);
        VBox.setMargin(hpBar, new Insets(0, 0, marginLayout * (-1), 0));  // Reduce space below hpBar
        VBox.setMargin(timerProgress, new Insets(0, 0, 0, 0)); // No extra space above timerProgress
        switchBody(storyLayout);
        
        
    }


    private void attackMonster() {
    	int damage = (int) GameLogic.clickHandle(); // Use actual damage value here
        GameLogic.reduceMonsterHpStory(damage);

        // Random damage animation
        Random rand = new Random();
        double randomX = rand.nextDouble() * 150 - 75; 
        double randomY = rand.nextDouble() * 150 - 100; 
        double randomSize = rand.nextDouble() * 10 + 20; 
        double randomRotation = rand.nextDouble() * 30 * (rand.nextBoolean() ? -1 : 1);

        Text damageText = new Text("-" + damage);
        damageText.setFill(Color.RED);
        damageText.setStyle("-fx-font-weight: bold;");
        damageText.setTranslateX(randomX);
        damageText.setTranslateY(randomY);
        damageText.setRotate(randomRotation);
        damageText.setScaleX(randomSize / 20);
        damageText.setScaleY(randomSize / 20);
        damageText.setOnMouseClicked(e -> attackMonster());
        bodyContainer.getChildren().add(damageText);
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(600), damageText);
        moveUp.setByY(-30);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), damageText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> bodyContainer.getChildren().remove(damageText));

        moveUp.play();
        fadeOut.play();
    }

    
    public void updateStoryUI() {
        
        Label stageLabel = new Label("Stage: " + GameLogic.getStage());
        Button startButton = new Button("Start");
        startButton.setOnMouseClicked(e -> {
        	letStart();
        	GameLogic.startStoryMode();
        });

        VBox storyLayout = new VBox(10, stageLabel, startButton);
        storyLayout.setAlignment(Pos.CENTER);

        switchBody(storyLayout); // ✅ Now we always pass a valid Node
    }
    
    
    public void updateEquippedCardsBar() {
//        equippedCardsBar.getChildren().clear();
        equippedCardsBar = new HBox(10);
        equippedCardsBar.setPadding(new Insets(10));
        equippedCardsBar.setSpacing(20);
        equippedCardsBar.setAlignment(Pos.CENTER);
        BaseCard[] equipped = GameLogic.getEquippedCards();
        
        for (BaseCard card : equipped) {
            VBox cardPane = new VBox();
            cardPane.setPrefSize(80, 125); 
            cardPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; "
                            + "-fx-alignment: top_center; -fx-background-color: #eeeeee;");
            cardPane.setSpacing(2);

            if (card != null) {
                cardPane.setStyle("-fx-border-width: 2px; "
                        + "-fx-alignment: top_center; -fx-background-color: #eeeeee;"
                        + "-fx-border-color: " + card.getTierStyle() + ";");

                ImageView imgView = new ImageView(new Image(card.getCardURL()));
                imgView.setFitWidth(60);
                imgView.setFitHeight(80);
                imgView.setPreserveRatio(true);

                Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
                cardLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 8;");
                
               
                ProgressBar cooldownBar = new ProgressBar(1);
                cooldownBar.setPrefWidth(60);
                cooldownBar.setVisible(false);  
                cooldownBar.setStyle("-fx-accent: green;");
                
                cardPane.getChildren().addAll(cardLabel, imgView, cooldownBar);
                if (card instanceof Activatable) {
                	cardPane.setOnMouseClicked(e -> {
                    
                        Activatable activatableCard = (Activatable) card;
                        
                        if (!((Activatable) card).isOnCooldown()) { 
                            activatableCard.activate();
                            startCooldownAnimation(cooldownBar, ((BaseCard) card).getCooldown());
                        }
                    
                	});
                }
            }

            equippedCardsBar.getChildren().add(cardPane);
        }
    }

    private void startCooldownAnimation(ProgressBar cooldownBar, int cooldownSeconds) {
        cooldownBar.setVisible(true);
        cooldownBar.setProgress(1.0);

        Timeline cooldownTimeline = new Timeline(
            new KeyFrame(Duration.seconds(cooldownSeconds), new KeyValue(cooldownBar.progressProperty(), 0))
        );

        cooldownTimeline.setOnFinished(e -> cooldownBar.setVisible(false)); // Hide after cooldown
        cooldownTimeline.play();
    }
}