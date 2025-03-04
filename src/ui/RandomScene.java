package ui;

import card.BaseCard;

import card.BigBangImpactCard;
import card.BuffStatCard;
import card.BullsEyeCard;
import card.CardTier;
import card.FriendshipIsMagicCard;
import card.GlassCannonCard;
import card.HeavyHiterCard;
import card.PoisonCard;
import card.WeakPointCard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;

import java.util.Random;

public class RandomScene extends BaseScene {
	private HBox cardDisplay;
	private static final CardTier[] TIERS = { CardTier.COMMON, CardTier.RARE, CardTier.EPIC, CardTier.LEGENDARY };
	private Label notEnoughGemsLabel;
	private String[] specialCard = { "BigBangImpact", "Poison", "BullsEye", "FriendshipIsMagic", "GlassCannon",
			"WeakPoint", "HeavyHiter" };

	public RandomScene() {
		super();

		Label chestLabel = new Label("CHEST");
		String chestPath = ClassLoader.getSystemResource("objects/chest.png").toString();
		ImageView chest = new ImageView(new Image(chestPath));
		chest.setFitWidth(100);
		chest.setFitHeight(80);

		HBox buttonBox = new HBox(20);
		Button buyOne = new Button("X1 💎 20");
		Button buyTen = new Button("X10 💎 180");

		buyOne.setOnMouseEntered(e -> buyOne.setStyle(buyOne.getStyle() + "-fx-opacity: 0.8;"));
		buyOne.setOnMouseExited(e -> buyOne.setStyle(buyOne.getStyle().replace("-fx-opacity: 0.8;", "")));
		buyOne.setOnAction(e -> openChest(1, 20));

		buyTen.setOnMouseEntered(e -> buyTen.setStyle(buyTen.getStyle() + "-fx-opacity: 0.8;"));
		buyTen.setOnMouseExited(e -> buyTen.setStyle(buyTen.getStyle().replace("-fx-opacity: 0.8;", "")));
		buyTen.setOnAction(e -> openChest(10, 180));

		buttonBox.getChildren().addAll(buyOne, buyTen);
		buttonBox.setAlignment(Pos.CENTER);

		cardDisplay = new HBox(10);
		cardDisplay.setAlignment(Pos.CENTER);
		cardDisplay.setMaxWidth(Double.MAX_VALUE);

		ScrollPane scrollPane = new ScrollPane(cardDisplay);
		cardDisplay.setPrefWidth(scrollPane.getWidth());
		cardDisplay.setDisable(false);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefHeight(180);
		scrollPane.setStyle("-fx-background: EACBA8;");
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		VBox randomLayout = new VBox(10, chestLabel, chest, buttonBox, scrollPane);
		randomLayout.setStyle("-fx-padding: 20px; " + "-fx-border-radius: 15px;");

		randomLayout.setAlignment(Pos.CENTER);

		switchBody(randomLayout);
	}

	private void openChest(int times, int costGem) {
		Random random = new Random();
		cardDisplay.getChildren().clear();

		if (!GameLogic.reduceGemCount(costGem)) {
			notEnoughGemsLabel = new Label("❌ Not Enough Gems!");
			notEnoughGemsLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
			cardDisplay.getChildren().add(notEnoughGemsLabel);
			return;
		}

		for (int i = 0; i < times; i++) {

			double tier = random.nextDouble();
			int indexTier;

			if (tier >= 0.97)
				indexTier = 3;
			else if (tier >= 0.85)
				indexTier = 2;
			else if (tier >= 0.60)
				indexTier = 1;
			else
				indexTier = 0;

			CardTier randomTier = TIERS[indexTier];

			Boolean isSpecial = random.nextDouble() >= 0.8;
			BaseCard card;

			if (!isSpecial) {

				card = new BuffStatCard("Buff Overall Card", "cards/buffCard/default.png", randomTier);
			} else {

				String chosenCard = specialCard[random.nextInt(specialCard.length)];
				card = createCard(chosenCard, randomTier);
			}

			VBox cardBlock = new VBox(5);
			cardBlock.setAlignment(Pos.CENTER);
			cardBlock.setStyle("-fx-padding: 10px; " + "-fx-border-width: 5px; " + "-fx-border-radius: 10px; "
					+ "-fx-background-radius: 10px; " + "-fx-background-color: white; " + "-fx-border-color: "
					+ card.getTierStyle() + "; ");

			cardBlock.setMinWidth(100);
			cardBlock.setMaxWidth(100);
			cardBlock.setPrefWidth(100);

			ImageView cardImage = new ImageView();
			cardImage.setPreserveRatio(true);
			cardImage.setSmooth(true);
			cardImage.setFitWidth(70);
			cardImage.setFitHeight(90);

			cardImage.setImage(new Image(card.getCardURL()));
			GameLogic.getOwnedCards().add(card);

			Label cardNameLabel = new Label(card.getName());
			cardNameLabel.setWrapText(true);
			cardNameLabel.setTextAlignment(TextAlignment.CENTER);
			cardNameLabel.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-text-fill: black;"
					+ "-fx-border-color: black;" + "-fx-border-width: 1px;" + "-fx-padding: 3;"
					+ "-fx-background-color: white;");

			Label tierLabel = new Label(card.getTier().toString());
			tierLabel.setStyle("-fx-font-size: 9px;" + "-fx-text-fill: " + card.getTierStyle() + ";");

			cardBlock.getChildren().addAll(cardImage, cardNameLabel, tierLabel);

			cardDisplay.getChildren().add(cardBlock);
		}
	}

	private BaseCard createCard(String type, CardTier tier) {
		switch (type) {
		case "BigBangImpact":
			return new BigBangImpactCard("Big Bang Impact", "cards/specialCard/bigBangImpact.png", tier);
		case "Poison":
			return new PoisonCard("Poison", "cards/specialCard/poison.png", tier);
		case "BullsEye":
			return new BullsEyeCard("Bulls Eye", "cards/specialCard/bullsEye.png", tier);
		case "FriendshipIsMagic":
			return new FriendshipIsMagicCard("Friend Ship", "cards/specialCard/friendshipIsMagicCard.png", tier);
		case "GlassCannon":
			return new GlassCannonCard("Glass Cannon", "cards/specialCard/glassCannonCard.png", tier);
		case "WeakPoint":
			return new WeakPointCard("Weak Point", "cards/specialCard/weakPoint.png", tier);
		case "HeavyHiter":
			return new HeavyHiterCard("Heavy Hiter", "cards/specialCard/heavyHiter.png", tier);
		default:
			throw new IllegalArgumentException("Unknown special card type: " + type);
		}
	}

}