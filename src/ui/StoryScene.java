package ui;

import java.util.Random;

import card.Activatable;
import card.ActivateCard;
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
        	    () -> GameLogic.monsterHpStoryProperty().get() / GameLogic.getMonsterStory().getMonsterHp(),
        	    GameLogic.monsterHpStoryProperty()
        ));

     
        ImageView monsterImage = new ImageView(new Image(GameLogic.getMonsterStory().getMonsterURL()));
        monsterImage.setFitHeight(120);
        monsterImage.setFitWidth(120);
        monsterArea = new StackPane();
        monsterArea.setPrefSize(120, 120);
        monsterArea.setStyle("-fx-background-color: #FBEDBE;");
        monsterArea.getChildren().add(monsterImage);
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
                
                  
                cooldownBar.setStyle("-fx-accent: green;");
                
                
                if (card instanceof ActivateCard) {
                	
                	
                	Timeline cooldownBarCheck = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            			if (!((ActivateCard) card).isOnCooldown()) { 
            				cooldownBar.setVisible(false);
	                    }else {
	                    	cooldownBar.setVisible(true);
	                    }
            		}));
                	cooldownBarCheck.setCycleCount(Timeline.INDEFINITE);
                	cooldownBarCheck.play();
                	
                	
                	
                	cooldownBar.progressProperty().bind(Bindings.createDoubleBinding(
                    	    () -> ((ActivateCard) card).cooldownTimeLeftProperty().get() / ((ActivateCard) card).getCooldown(),
                    	    ((ActivateCard) card).cooldownTimeLeftProperty()
                    ));
                	cardPane.setOnMouseClicked(e -> {
                    
                		ActivateCard activatableCard = (ActivateCard) card;
                        
                        if (!((ActivateCard) card).isOnCooldown()) { 
                        	activatableCard.startCooldown();
                        }
                    
                	});
                	
                	
                }else {
                	cooldownBar.setVisible(false);
                }
                
                cardPane.getChildren().addAll(cardLabel, imgView, cooldownBar);
                
                
            }

            equippedCardsBar.getChildren().add(cardPane);
        }
    }

}