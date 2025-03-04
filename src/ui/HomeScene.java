
package ui;

import java.util.Random;
import card.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;

public class HomeScene extends BaseScene {
	private Label hpLabel;
	private ProgressBar hpBar;
	private StackPane monsterArea;
	private HBox equippedCardsBar;
	private ImageView monsterImage;

	public HomeScene() {
		super();

		hpLabel = new Label();
		hpBar = new ProgressBar(1.0);
		hpBar.setStyle("-fx-accent: red;");
		hpBar.setPrefWidth(200);

		hpLabel.textProperty().bind(GameLogic.monsterHpHomeProperty().asString("%.0f"));
		hpBar.progressProperty()
				.bind(Bindings.createDoubleBinding(
						() -> GameLogic.monsterHpHomeProperty().get() / GameLogic.getMonsterHome().getMonsterHp(),
						GameLogic.monsterHpHomeProperty()));

		monsterImage = new ImageView(new Image(GameLogic.getMonsterHome().getMonsterURL()));
		monsterImage.setFitHeight(120);
		monsterImage.setFitWidth(120);

		monsterArea = new StackPane();
		monsterArea.setPrefSize(120, 120);
		monsterArea.setStyle("-fx-background-color: #FBEDBE;");
		monsterArea.getChildren().add(monsterImage);
		monsterArea.setOnMouseClicked(e -> attackMonster());

		equippedCardsBar = new HBox(10);
		equippedCardsBar.setPadding(new Insets(10));
		equippedCardsBar.setSpacing(20);
		equippedCardsBar.setAlignment(Pos.CENTER);
		updateEquippedCardsBar();

		VBox homeLayout = new VBox(10, hpLabel, hpBar, monsterArea, equippedCardsBar);
		homeLayout.setAlignment(Pos.CENTER);
		switchBody(homeLayout);

		GameLogic.startDpsHome();
	}

	private void attackMonster() {
		int damage = (int) GameLogic.clickHandle();
		GameLogic.reduceMonsterHpHome(damage);

		Random rand = new Random();
		double randomX = rand.nextDouble() * 150 - 75;
		double randomY = rand.nextDouble() * 150 - 100;
		double randomSize = rand.nextDouble() * 10 + 20;
		double randomRotation = rand.nextDouble() * 30 * (rand.nextDouble() > 0.5 ? -1 : 1);

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

	public void monsterIsDead() {

		monsterImage.setImage(new Image(GameLogic.getMonsterHome().getMonsterDeadURL()));
		monsterArea.setOnMouseClicked(e -> {
		});
		Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {

			monsterImage.setImage(new Image(GameLogic.getMonsterHome().getMonsterURL()));

		}));
		cooldownTimer.setOnFinished(e -> {
			GameLogic.startDpsHome();
			monsterArea.setOnMouseClicked(event -> attackMonster());

		});
		cooldownTimer.setCycleCount(1);
		cooldownTimer.play();
	}

	public void updateMonsterHome() {

		hpLabel.textProperty().bind(GameLogic.monsterHpHomeProperty().asString("%.0f"));
		hpBar.progressProperty()
				.bind(Bindings.createDoubleBinding(
						() -> GameLogic.monsterHpHomeProperty().get() / GameLogic.getMonsterHome().getMonsterHp(),
						GameLogic.monsterHpHomeProperty()));

		monsterImage.setImage(new Image(GameLogic.getMonsterHome().getMonsterURL()));

	}

	public void updateEquippedCardsBar() {

		equippedCardsBar.getChildren().clear();

		BaseCard[] equipped = GameLogic.getEquippedCards();

		for (BaseCard card : equipped) {
			VBox cardPane = new VBox();
			cardPane.setPrefSize(80, 125);
			cardPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; "
					+ "-fx-alignment: top_center; -fx-background-color: white;");
			cardPane.setSpacing(2);

			if (card != null) {
				cardPane.setStyle("-fx-border-width: 2px; " + "-fx-alignment: top_center; -fx-background-color: white;"
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
						} else {
							cooldownBar.setVisible(true);
						}
					}));
					cooldownBarCheck.setCycleCount(Timeline.INDEFINITE);
					cooldownBarCheck.play();

					cooldownBar.progressProperty()
							.bind(Bindings.createDoubleBinding(
									() -> ((ActivateCard) card).cooldownTimeLeftProperty().get()
											/ ((ActivateCard) card).getCooldown(),
									((ActivateCard) card).cooldownTimeLeftProperty()));
					cardPane.setOnMouseClicked(e -> {

						ActivateCard activatableCard = (ActivateCard) card;

						if (!((ActivateCard) card).isOnCooldown()) {
							activatableCard.startCooldown();
						}

					});

				} else {
					cooldownBar.setVisible(false);
				}

				cardPane.getChildren().addAll(cardLabel, imgView, cooldownBar);

			}

			equippedCardsBar.getChildren().add(cardPane);
		}
	}

}