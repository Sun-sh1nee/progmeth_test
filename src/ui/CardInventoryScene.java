package ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;
import card.BaseCard;

public class CardInventoryScene extends BaseScene {

    private static int targetSlotIndex = 0; 
    private VBox inventoryContainer;
    private Label hoverInfoLabel; 
    private FlowPane cardsPane;

    public static void setTargetSlotIndex(int slotIndex) {
        targetSlotIndex = slotIndex;
    }

    public CardInventoryScene() {
        super();

        Label title = new Label("Inventory");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // FlowPane for displaying owned cards
        cardsPane = new FlowPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10));
        cardsPane.setAlignment(Pos.CENTER);

        // If you want the cards to wrap at a certain width, you can do:
        // cardsPane.setPrefWrapLength(500);

        // Wrap the FlowPane in a ScrollPane so it becomes scrollable
        ScrollPane scrollPane = new ScrollPane(cardsPane);
        scrollPane.setFitToWidth(true);  
        // scrollPane.setFitToHeight(true); // if you want it to expand vertically as well
        scrollPane.setPadding(new Insets(10));

        // The “yellow box” for showing toString() on hover
        hoverInfoLabel = new Label("Hover a card to see details");
        hoverInfoLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5; -fx-wrap-text: true;");
        hoverInfoLabel.setWrapText(true);
        hoverInfoLabel.setMaxWidth(600);
        hoverInfoLabel.setMaxHeight(150); // Increase max height
        hoverInfoLabel.setPrefHeight(150); // Set preferred height
        hoverInfoLabel.setMinHeight(120);


        ArrayList<BaseCard> ownedCards = GameLogic.getOwnedCards();
        BaseCard[] equippedCardsAry = GameLogic.getEquippedCards(); 

        ArrayList<BaseCard> equippedCards = new ArrayList<>();
        for (BaseCard equip : equippedCardsAry) {
            equippedCards.add(equip);
        }

        for (BaseCard card : ownedCards) {
  
            if (!equippedCards.contains(card)) { 
            	VBox cardView = createCardView(card);
                cardsPane.getChildren().add(cardView);
            }
        }

       
        Label closeButton = new Label("Close");
        closeButton.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-background-color: lightgray;");
        closeButton.setOnMouseClicked(e -> SceneManager.switchTo("CARD_EQUIPMENT"));

        inventoryContainer = new VBox(15, closeButton, scrollPane, hoverInfoLabel);
        inventoryContainer.setAlignment(Pos.BOTTOM_CENTER);
        inventoryContainer.setPadding(new Insets(20));

        switchBody(inventoryContainer);
    }

    private VBox createCardView(BaseCard card) {
    	VBox cardPane = new VBox();
        cardPane.setPrefSize(80, 110);
        cardPane.setSpacing(2);
        cardPane.setStyle("-fx-background-color: transparent; " +
                "-fx-alignment: top_center; " +
                "-fx-border-width: 3px; " +
                "-fx-border-color: " + card.getTierStyle()  + ";"+
                "-fx-text-fill: black;");
        ImageView imgView = new ImageView(new Image(card.getCardURL()));
        imgView.setFitWidth(60);
        imgView.setFitHeight(75);
        imgView.setPreserveRatio(true);
        Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");  
        cardLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 8;");
        cardPane.getChildren().addAll(cardLabel,imgView);
        
        
        cardPane.setOnMouseEntered(e -> hoverInfoLabel.setText(card.toString()));
        cardPane.setOnMouseExited(e -> hoverInfoLabel.setText("Hover a card to see details"));

        
        cardPane.setOnMouseClicked(e -> {
            GameLogic.equipCard(card, targetSlotIndex);
            SceneManager.switchTo("CARD_EQUIPMENT");

            
            CardEquipmentScene equipScene = 
                (CardEquipmentScene) SceneManager.getSceneNode("CARD_EQUIPMENT");
            if (equipScene != null) {
                equipScene.refreshSlots();
            }
        });

        return cardPane;
    }
    
    public void updateEquippedCardsInventory() {
    	
		cardsPane.getChildren().clear();
		ArrayList<BaseCard> ownedCards = GameLogic.getOwnedCards();
	    BaseCard[] equippedCardsAry = GameLogic.getEquippedCards(); 
	    Thread th = new Thread(() -> {
	    	Collections.sort(ownedCards);
	        ArrayList<BaseCard> equippedCards = new ArrayList<>();
	        for (BaseCard equip : equippedCardsAry) {
	            equippedCards.add(equip);
	        }
	
	        for (BaseCard card : ownedCards) {
	   
	            if (!equippedCards.contains(card)) { 
	            	VBox cardView = createCardView(card);
	                cardsPane.getChildren().add(cardView);
	             }
	         }
	    });
	    th.start();
    }
    
    
    
    
}
