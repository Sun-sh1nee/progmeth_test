package logic;

import java.util.ArrayList;
import java.util.Random;

import card.*;
import companion.Companion;
import enemy.Monster;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import player.Player;
import ui.BaseScene;
import ui.HomeScene;
import ui.RandomScene;
import ui.SceneManager;
import ui.StoryScene;
import ui.UpgradeScene;

public class GameLogic {
	private static SimpleLongProperty croissantCount = new SimpleLongProperty();
	private static SimpleLongProperty gemCount = new SimpleLongProperty();
	private static SimpleDoubleProperty monsterHpHome = new SimpleDoubleProperty();
	private static SimpleDoubleProperty monsterHpStory = new SimpleDoubleProperty();
	private static SimpleIntegerProperty attackPerClick = new SimpleIntegerProperty();
	private static SimpleIntegerProperty damagePerSec = new SimpleIntegerProperty();
	private static SimpleStringProperty musicSetting = new SimpleStringProperty();
//	!!!!!!!!!
	private static SimpleStringProperty effectSetting = new SimpleStringProperty();
//	================================================================================
	private static SimpleIntegerProperty storyState = new SimpleIntegerProperty();
	private static SimpleDoubleProperty storyTimerProgress = new SimpleDoubleProperty();
	private static boolean isStoryBattle;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	private static int stage;
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player = new Player();

	private static double damageCardBoost;
	private static double gemDropChanceCardBoost;
	private static double critChanceCardBoost;
	private static double critDamageCardBoost;
	private static double companionCardBoost;
	private static double extraDamage;

	private static SimpleBooleanProperty isMusic = new SimpleBooleanProperty(true);
	private static AudioClip backgroundSound;

	private static BaseCard[] equippedCards = new BaseCard[4];
	private static ArrayList<BaseCard> ownedCards = new ArrayList<>();

	public static void init() {
		setStage(1);
		initMonster();
		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		monsterHpStory.set(monsterStory.get(1).getMonsterHp());

		croissantCount.set(20000000);
		gemCount.set(11110);
		setAttackPerClick();
		setDamagePerSec();

		musicSetting.set("ON"); // not finish

		damageCardBoost = 0;
		gemDropChanceCardBoost = 0;
		critChanceCardBoost = 0;
		critDamageCardBoost = 0;
		companionCardBoost = 0;
		extraDamage = 0;
		isStoryBattle = false;

		setStoryState();

		startDpsHome();
	}

	public static BaseCard[] getEquippedCards() {
		return equippedCards;
	}

	public static ArrayList<BaseCard> getOwnedCards() {
		return ownedCards;
	}

	public static SimpleIntegerProperty getDamagePerSecProperty() {
		return damagePerSec;
	}

	public static void equipCard(BaseCard newCard, int slotIndex) {

		BaseCard oldCard = equippedCards[slotIndex];
		if (oldCard instanceof BuffStatCard) {
			((BuffStatCard) oldCard).CancelBuff();
		}

		equippedCards[slotIndex] = newCard;

		if (newCard instanceof BuffStatCard) {
			((BuffStatCard) newCard).applyBuff();
		}

	}

	public static void playBackgroundSound() {
		if (backgroundSound == null) {
			String backgroundURL = ClassLoader.getSystemResource("sounds/backgroundSound.mp3").toString();
			backgroundSound = new AudioClip(backgroundURL);
			backgroundSound.setCycleCount(AudioClip.INDEFINITE);
			backgroundSound.setVolume(0.1);
		}

		if (isMusic.get()) {
			backgroundSound.play();
		} else {
			backgroundSound.stop();
		}
	}

	public static SimpleBooleanProperty isMusicProperty() {
		return isMusic;
	}

	public static void setMusic(boolean value) {
		isMusic.set(value);
		playBackgroundSound();
	}

	public static void toggleMusic() {
		setMusic(!isMusic.get());
	}

	public static void startStoryMode() {
		monsterHpStory.set(monsterStory.get(getStage()).getMonsterHp());
		for (BaseCard card : equippedCards) {
			if (card instanceof ActivateCard)
				((ActivateCard) card).resetCooldown();
		}
		startDpsStory();
		startTimer();
	}

	public static boolean isStoryBattle() {
		return isStoryBattle;
	}



	public static void applyExtraDamage(double extraDamage) {
		GameLogic.extraDamage += extraDamage;
	}

	public static void cancelExtraDamage(double decrease) {
		GameLogic.extraDamage -= decrease;
	}

	public static void applyDamageCardBoost(double damageCardBoost) {
		GameLogic.damageCardBoost += damageCardBoost;
	}

	public static void cancelDamageCardBoost(double decrease) {
		GameLogic.damageCardBoost -= decrease;
	}

	public static void applyGemDropChanceCardBoost(double gemDropChanceCardBoost) {
		GameLogic.gemDropChanceCardBoost += gemDropChanceCardBoost;
	}

	public static void cancelGemDropChanceCardBoost(double decrease) {
		GameLogic.gemDropChanceCardBoost -= decrease;
	}

	public static void applyCritChanceCardBoost(double critChanceCardBoost) {
		GameLogic.critChanceCardBoost += critChanceCardBoost;
	}

	public static void cancelCritChanceCardBoost(double decrease) {
		GameLogic.critChanceCardBoost -= decrease;
	}

	public static void applyCritDamageCardBoost(double critDamageCardBoost) {
		GameLogic.critDamageCardBoost += critDamageCardBoost;
	}

	public static void cancelCritDamageCardBoost(double decrease) {
		GameLogic.critDamageCardBoost -= decrease;
	}

	public static void applyCompanionCardBoost(double companionCardBoost) {
		GameLogic.companionCardBoost += companionCardBoost;
	}

	public static void cancelCompanionCardBoost(double decrease) {
		GameLogic.companionCardBoost -= decrease;
	}

	private static void startTimer() {
		int totalTime = 5;
		isStoryBattle = true;
		new Thread(() -> {
			double timeNow = totalTime;
			while (timeNow > 0) {
				try {
					if (!SceneManager.getSceneName().equals("STORY")) {
						isStoryBattle = false;
						return;
					}
					double progress = timeNow / totalTime;
					Platform.runLater(() -> storyTimerProgress.set(progress));

					timeNow -= 0.1;
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					e1.printStackTrace();
				}
			}
			isStoryBattle = false;
			Platform.runLater(() -> SceneManager.switchTo("HOME"));
		}).start();
	}

	private static void initMonster() {
		monsterStory = new ArrayList<Monster>();
//		ArrayList<String> images = new ArrayList<String>();
		monsterStory.add(new Monster(200, 50, 1, 1.0, 1.0, null));
		for (int i = 1; i <= 30; ++i) {
			int hpBase = i * 1000;
			int coinBase = i * 100;
			double coinScal = 1;
			double hpScal = 1.3;

			monsterStory.add(new Monster(hpBase, coinBase, i, hpScal, coinScal, null));

		}
	}

	public static SimpleLongProperty getCroissantCount() {
		return croissantCount;
	}

	public static void setCroissantCount(long croissant) {
		croissantCount.set(croissant);
	}

	public static SimpleLongProperty croissantCountProperty() {
		return croissantCount;
	}

	public static SimpleLongProperty gemCountProperty() {
		return gemCount;
	}

	public static void addCroissants(long amount) {
		croissantCount.set(croissantCount.get() + amount);
	}

	public static SimpleDoubleProperty monsterHpHomeProperty() {
		return monsterHpHome;
	}

	public static SimpleDoubleProperty monsterHpStoryProperty() {
		return monsterHpStory;
	}

	public static SimpleIntegerProperty attackPerClickProperty() {
		return attackPerClick;
	}

	public static SimpleIntegerProperty storyStateProperty() {
		return storyState;
	}

	public static SimpleDoubleProperty storyTimerProgressProperty() {
		return storyTimerProgress;
	}

	public static Monster getMonsterStage(int index) {
		if ((index >= 30 || index < 0)) {
			System.out.println("monster stage index out of bound");
			return null;
		}
		return monsterStory.get(index);
	}

	public static void monsterStoryIsDead() {

		if (stage >= monsterStory.size()) {
			System.out.println("🎉 Story Completed! Returning to Home...");
			SceneManager.switchTo("HOME");
			return;
		}

		// Proceed to the next stage
		monsterHome = monsterStory.get(stage);
		monsterHpHome.set(monsterHome.getMonsterHp());
		stage++;
		setStoryState();
		// monsterHpStory.set(monsterStory.get(stage).getMonsterHp());
		monsterHpStory.set(getMonsterStage(stage).getMonsterHp());

		for (BaseCard card : equippedCards) {
			if (card instanceof ActivateCard)
				((ActivateCard) card).resetCooldown();
		}
		// monsterHpHome.set(monsterStory.get(stage - 1).getMonsterHp());
		gemCount.set(gemCount.get() + 2);
		SceneManager.switchTo("HOME");
//			((HomeScene) SceneManager.getSceneNode("HOME")).updateHpMonsterHome();

	}

	public static void monsterHomeIsDead() {
		monsterHpHome.set(monsterHome.getMonsterHp());
		addCroissants(monsterHome.getCoinDrop());

		Random random = new Random();
		if (random.nextDouble() < player.getChanceToDropGem()) {
			gemCount.set(gemCount.get() + 1);
		}
//			((HomeScene) SceneManager.getSceneNode("HOME")).updateHpMonsterHome();
	}

	public static void reduceMonsterHpHome(double amount) {

		monsterHpHome.set(monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterHomeIsDead();
		}

	}

	public static void reduceMonsterHpStory(double amount) {
		monsterHpStory.set(monsterHpStory.get() - amount);
		if (monsterHpStory.get() <= 0) {
			monsterStoryIsDead();
		}

	}

	public static double clickHandle() {
		int amount = player.getAttackPerClick();
		if (damageCardBoost > 0)
			amount *= (1 + (damageCardBoost / 100.0));
		Random random = new Random();
		double critRate = player.getCritRate() + (critChanceCardBoost / 100.0);
		if (random.nextDouble() < critRate) {
			amount *= (player.getCritDamage() + (critDamageCardBoost / 100.0));
		}
		if (extraDamage > 0)
			amount *= (1 + (extraDamage / 100.0));
		return amount;
	}

	public static void startDpsHome() {
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}
		if (dpsStoryThread != null) {
			dpsStoryThread.stop();

		}

		dpsHomeThread = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			double dam = damagePerSec.get() * (1 + (companionCardBoost / 100.0));
			reduceMonsterHpHome(dam);
		}));
		dpsHomeThread.setCycleCount(Timeline.INDEFINITE);
		dpsHomeThread.play();
	}

	public static void startDpsStory() {
		if (dpsStoryThread != null) {
			dpsStoryThread.stop();

		}
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}
		dpsStoryThread = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			double dam = damagePerSec.get() * (1 + (companionCardBoost / 100.0));
			reduceMonsterHpStory(dam);
		}));
		dpsStoryThread.setCycleCount(Timeline.INDEFINITE);
		dpsStoryThread.play();

	}

	public static Player getPlayer() {
		return player;
	}

	public static int getStage() {
		return stage;
	}

	public static void setStage(int stageNow) {
		stage = stageNow;
	}

	public static void setAttackPerClick() {
		attackPerClick.set(player.getAttackPerClick());
	}

	public static boolean reduceGemCount(int amount) {
		if (gemCountProperty().get() < amount)
			return false;
		gemCount.set(gemCountProperty().get() - amount);
		return true;
	}

	public static void setDamagePerSec() {
		damagePerSec.set(player.getDamagePerSec());

	}

	public static Monster getMonsterHome() {
		return monsterHome;
	}

	public static Monster getMonsterStory() {
		return monsterStory.get(stage);
	}

	public static void setStoryState() {
		storyState.set(stage);
	}
}