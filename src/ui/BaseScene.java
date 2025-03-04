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
		topBar = new HBox(0);
		topBar.setMinHeight(90);
		topBar.setAlignment(Pos.CENTER);
		topBar.setStyle("-fx-padding: 15px; -fx-background-color: F7D07A;");

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
		croissantContainer.setMaxWidth(150);

		String croissantPath = ClassLoader.getSystemResource("objects/croissant.png").toString();
		ImageView croissantIcon = new ImageView(new Image(croissantPath));
		croissantIcon.setFitWidth(25);
		croissantIcon.setFitHeight(20);
		croissantIcon.setRotate(10);

		HBox croissantInfoPanel = new HBox(5);
		croissantInfoPanel.setAlignment(Pos.CENTER);
		croissantInfoPanel.setPrefWidth(150);

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

		String settingsPath = ClassLoader.getSystemResource("objects/setting.png").toString();
		ImageView settingsIcon = new ImageView(new Image(settingsPath));
		settingsIcon.setFitWidth(30);
		settingsIcon.setFitHeight(30);

		StackPane settingsButton = new StackPane(settingsIcon);
		settingsButton.setOnMouseClicked(e -> toggleSettingsPage());
		settingsButton.setAlignment(Pos.CENTER_RIGHT);

		StackPane leftContainer = new StackPane();
		leftContainer.setAlignment(Pos.CENTER_LEFT);
		leftContainer.getChildren().add(gemCounter);
		leftContainer.setStyle("-fx-background-color: F7D07A;");
		leftContainer.setPrefWidth(150);

		StackPane centerContainer = new StackPane();
		centerContainer.setAlignment(Pos.CENTER);
		centerContainer.getChildren().add(croissantContainer);
		centerContainer.setStyle("-fx-background-color: F7D07A;");
		centerContainer.setPrefWidth(150);

		StackPane rightContainer = new StackPane();
		rightContainer.setAlignment(Pos.CENTER);
		rightContainer.getChildren().add(settingsButton);
		rightContainer.setStyle("-fx-background-color: F7D07A;");
		rightContainer.setPrefWidth(150);

		topBar.getChildren().addAll(leftContainer, centerContainer, rightContainer);

		bodyContainer = new StackPane();
		bodyContainer.setMinHeight(400);
		bodyContainer.setMaxHeight(400);
		VBox.setVgrow(bodyContainer, Priority.ALWAYS);

		navBar = new HBox(30);
		navBar.setPrefHeight(65);
		navBar.setAlignment(Pos.CENTER);
		navBar.setStyle("-fx-padding: 15px; -fx-background-color: F7D07A;");

		StackPane homeIcon = new StackPane(
				new ImageView(new Image(loadImagePath("icon/HomeIcon.png"), 50, 50, false, false)));
		homeIcon.setPrefSize(50, 70);
		homeIcon.setStyle("-fx-background-color: F7D07A;");
		StackPane randomIcon = new StackPane(
				new ImageView(new Image(loadImagePath("icon/diceIcon.png"), 30, 30, false, false)));
		randomIcon.setPrefSize(50, 70);
		randomIcon.setStyle("-fx-background-color: F7D07A;");
		StackPane storyIcon = new StackPane(
				new ImageView(new Image(loadImagePath("icon/crossedSwordsIcon.png"), 40, 40, false, false)));
		storyIcon.setPrefSize(50, 70);
		storyIcon.setStyle("-fx-background-color: F7D07A;");
		StackPane upgradeIcon = new StackPane(
				new ImageView(new Image(loadImagePath("icon/arrowUpIcon.png"), 30, 30, false, false)));
		upgradeIcon.setPrefSize(50, 70);
		upgradeIcon.setStyle("-fx-background-color: F7D07A;");
		StackPane inventoryIcon = new StackPane(
				new ImageView(new Image(loadImagePath("icon/backpackIcon.png"), 40, 30, false, false)));
		inventoryIcon.setPrefSize(50, 70);
		inventoryIcon.setStyle("-fx-background-color: F7D07A;");

		inventoryIcon.setOnMouseClicked(e -> SceneManager.switchTo("CARD_EQUIPMENT"));
		randomIcon.setOnMouseClicked(e -> SceneManager.switchTo("RANDOM"));
		homeIcon.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
		storyIcon.setOnMouseClicked(e -> SceneManager.switchTo("STORY"));
		upgradeIcon.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

		navBar.getChildren().addAll(randomIcon, homeIcon, storyIcon, upgradeIcon, inventoryIcon);

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
		background.setOnMouseClicked(e -> toggleSettingsPage());

		VBox settingsBox = new VBox(15);
		settingsBox.setAlignment(Pos.CENTER);
		settingsBox.setStyle("-fx-background-color: white; " + "-fx-padding: 15px; " + "-fx-background-radius: 10px;");
		settingsBox.setMaxSize(250, 150);

		Label title = new Label("Settings");
		title.setFont(new Font(20));

		Button toggleMusicButton = new Button();
		toggleMusicButton.textProperty()
				.bind(GameLogic.isMusicProperty().map(value -> value ? "Music: ON" : "Music: OFF"));
		toggleMusicButton.setOnAction(e -> toggleMusic());

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> toggleSettingsPage());

		settingsBox.getChildren().addAll(title, toggleMusicButton, closeButton);

		StackPane settingsContent = new StackPane(settingsBox);
		settingsContent.setAlignment(Pos.CENTER);

		settingsContainer = new StackPane(background, settingsContent);
		settingsContainer.setAlignment(Pos.CENTER);
		settingsContainer.setVisible(false);
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

	private String loadImagePath(String path) {
		return ClassLoader.getSystemResource(path).toString();
	}

}