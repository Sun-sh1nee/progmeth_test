package ui;

import card.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;

public class CardEquipmentScene extends BaseScene {

	private HBox slotsContainer;
	private Tooltip arrayTooltips[] = new Tooltip[4];
	private VBox arraySlotPane[] = new VBox[4];

	public CardEquipmentScene() {
		super();

		Label title = new Label("Equipment");
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		slotsContainer = new HBox(20);
		slotsContainer.setAlignment(Pos.CENTER);
		slotsContainer.setPadding(new Insets(20));

		for (int i = 0; i < 4; i++) {
			VBox slotPane = createSlotPane(i);
			Button removeButton = createRemoveButton(i);
			arraySlotPane[i] = slotPane;

			VBox oneSlot = new VBox(10);
			oneSlot.getChildren().addAll(slotPane, removeButton);
			oneSlot.setAlignment(Pos.CENTER);

			slotsContainer.getChildren().add(oneSlot);
		}

		VBox layout = new VBox(20, title, slotsContainer);
		layout.setAlignment(Pos.CENTER);

		switchBody(layout);
	}

	private Button createRemoveButton(int slotIndex) {
		Button removeButton = new Button();
		removeButton.setText("remove");
		removeButton.setAlignment(Pos.CENTER);
		removeButton.setStyle("-fx-background-color: FF4E01;");
		removeButton.setOnMouseClicked(e -> {
			GameLogic.equipCard(null, slotIndex);
			updateSlotPane(arraySlotPane[slotIndex], slotIndex);
			SceneManager.switchTo("CARD_EQUIPMENT");

		});

		return removeButton;
	}

	private VBox createSlotPane(int slotIndex) {
		VBox slotPane = new VBox();
		slotPane.setPrefSize(100, 140);
		slotPane.setStyle("-fx-border-color: black; -fx-border-width: 2; "
				+ "-fx-alignment: top_center; -fx-background-color: white;");

		updateSlotPane(slotPane, slotIndex);

		slotPane.setOnMouseClicked(e -> {
			CardInventoryScene.setTargetSlotIndex(slotIndex);
			SceneManager.switchTo("CARD_INVENTORY");
		});

		arrayTooltips[slotIndex] = new Tooltip("No card equipped");
		Tooltip.install(slotPane, arrayTooltips[slotIndex]);

		return slotPane;
	}

	private void updateSlotPane(VBox slotPane, int slotIndex) {
		BaseCard card = GameLogic.getEquippedCards()[slotIndex];
		slotPane.getChildren().clear();

		if (card == null) {

			Label plusLabel = new Label("+");
			plusLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: gray;");
			StackPane stackPlus = new StackPane(plusLabel);
			slotPane.setStyle("-fx-border-color: black;");
			stackPlus.setAlignment(Pos.CENTER);

			slotPane.getChildren().add(stackPlus);
		} else {

			Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
			cardLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 8;");
			ImageView imgView = new ImageView(new Image(card.getCardURL()));
			imgView.setFitWidth(80);
			imgView.setFitHeight(100);
			imgView.setPreserveRatio(true);
			slotPane.setStyle("-fx-border-color: black; -fx-border-width: 2; "
					+ "-fx-alignment: top_center; -fx-background-color: white;" + "-fx-border-color: "
					+ card.getTierStyle() + ";");

			slotPane.getChildren().addAll(cardLabel, imgView);

			arrayTooltips[slotIndex]
					.setText("Name: " + card.getName() + "\nTier: " + card.getTier() + "\nDetail: " + card.toString());

		}
	}

	public void refreshSlots() {
		for (int i = 0; i < slotsContainer.getChildren().size(); i++) {

			updateSlotPane(arraySlotPane[i], i);
		}
	}
}