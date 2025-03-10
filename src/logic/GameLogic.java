package logic;

import java.util.ArrayList;
import java.util.Random;

import card.*;
import enemy.Monster;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import player.Player;
import ui.EndCreditScene;
import ui.HomeScene;
import ui.SceneManager;

public class GameLogic {
	private static SimpleLongProperty croissantCount = new SimpleLongProperty();
	private static SimpleLongProperty gemCount = new SimpleLongProperty();
	private static SimpleDoubleProperty monsterHpHome = new SimpleDoubleProperty();
	private static SimpleDoubleProperty monsterHpStory = new SimpleDoubleProperty();
	private static SimpleIntegerProperty attackPerClick = new SimpleIntegerProperty();
	private static SimpleIntegerProperty damagePerSec = new SimpleIntegerProperty();
	private static SimpleStringProperty musicSetting = new SimpleStringProperty();
	private static SimpleIntegerProperty storyState = new SimpleIntegerProperty();
	private static SimpleDoubleProperty storyTimerProgress = new SimpleDoubleProperty();

	private static boolean isStoryBattle;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player;
	private static double damageCardBoost;
	private static double gemDropChanceCardBoost;
	private static double critChanceCardBoost;
	private static double critDamageCardBoost;
	private static double companionCardBoost;
	private static double extraDamage;
	private static SimpleBooleanProperty isMusic = new SimpleBooleanProperty(true);
	private static AudioClip backgroundSound;
	private static AudioClip endCreditBgSound;
	private static BaseCard[] equippedCards;
	private static ArrayList<BaseCard> ownedCards;

	public static void init() {
		setstoryState(1);
		initMonster();
		player = new Player();

		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		monsterHpStory.set(monsterStory.get(1).getMonsterHp());
		croissantCount.set(0);
		gemCount.set(0);
		setAttackPerClick();
		setDamagePerSec();
		musicSetting.set("ON");
		damageCardBoost = 0;
		gemDropChanceCardBoost = 0;
		critChanceCardBoost = 0;
		critDamageCardBoost = 0;
		companionCardBoost = 0;
		extraDamage = 0;
		isStoryBattle = false;
		equippedCards = new BaseCard[4];
		ownedCards = new ArrayList<>();
		setStoryState();
		startDpsHome();
		playBackgroundSound();
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
			((BuffStatCard) oldCard).cancelBuff();
		}
		equippedCards[slotIndex] = newCard;
		if (newCard instanceof BuffStatCard) {
			((BuffStatCard) newCard).applyBuff();
		}
	}

	public static void playBackgroundSound() {
		if (endCreditBgSound != null)
			endCreditBgSound.stop();

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

	public static void endCreditBackgroundSound() {
		if (backgroundSound != null) {
			isMusic.set(true);
			backgroundSound.stop();
		}
		if (endCreditBgSound == null) {
			String endCreditBgURL = ClassLoader.getSystemResource("sounds/endCreditBgSound.mp3").toString();
			endCreditBgSound = new AudioClip(endCreditBgURL);
			endCreditBgSound.setCycleCount(AudioClip.INDEFINITE);
			endCreditBgSound.setVolume(0.5);
		}

		endCreditBgSound.play();
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
		monsterHpStory.set(monsterStory.get(getstoryState()).getMonsterHp());
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
		int totalTime = 30;
		isStoryBattle = true;
		new Thread(() -> {
			double timeNow = totalTime;
			while (timeNow > 0) {
				try {
					if (!SceneManager.getSceneName().equals("STORY")) {
						isStoryBattle = false;
						break;
					}
					double progress = timeNow / totalTime;
					Platform.runLater(() -> storyTimerProgress.set(progress));
					timeNow -= 0.1;
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();

					return;
				}
			}
			isStoryBattle = false;
		}).start();
	}

	private static void initMonster() {
		monsterStory = new ArrayList<Monster>();

		monsterStory.add(new Monster(200, 50, 1, 1.0, 1.0, "croissant"));

		for (int i = 1; i <= 30; ++i) {
			int hpBase = i * 1000;
			int coinBase = i * 100;
			double coinScal = 0.4;
			double hpScal = 1.3;

			Random rand = new Random();
			int random = rand.nextInt(3);
			Monster newMonster;
			if (random == 0) {
				newMonster = new Monster(hpBase, coinBase, i, hpScal, coinScal, "croissant");
			} else if (random == 1) {
				newMonster = new Monster(hpBase, coinBase, i, hpScal, coinScal, "croissantKing");
			} else {
				newMonster = new Monster(hpBase, coinBase, i, hpScal, coinScal, "cabbage");
			}
			monsterStory.add(newMonster);

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

	public static SimpleLongProperty getGemCountProperty() {
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

	public static void monsterStoryIsDead() {
		if (storyState.get() >= 30) {
			SceneManager.addScene("END_CREDIT", new Scene(new EndCreditScene(), 500, 600));
			SceneManager.switchTo("END_CREDIT");
			return;
		}
		monsterHome = monsterStory.get(storyState.get());
		monsterHpHome.set(monsterHome.getMonsterHp());

		storyState.set(getstoryState() + 1);
		;
		setStoryState();
		monsterHpStory.set(monsterStory.get(storyState.get()).getMonsterHp());

		for (BaseCard card : equippedCards) {
			if (card instanceof ActivateCard)
				((ActivateCard) card).resetCooldown();
		}
		gemCount.set(gemCount.get() + 2);
		SceneManager.switchTo("HOME");
	}

	public static void monsterHomeIsDead() {

		addCroissants(monsterHome.getCoinDrop());
		Random random = new Random();
		if (random.nextDouble() < (player.getChanceToDropGem() + gemDropChanceCardBoost)) {
			gemCount.set(gemCount.get() + 1);
		}
		dpsHomeThread.stop();

		HomeScene homeScene = (HomeScene) SceneManager.getSceneNode("HOME");
		homeScene.monsterIsDead();
		Timeline deadTimer = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {

			monsterHpHome.set(monsterHome.getMonsterHp());
		}));

		deadTimer.setCycleCount(1);
		deadTimer.play();

	}

	public static void reduceMonsterHpHome(double amount) {
		monsterHpHome.set(monsterHpHome.get() - amount <= 0 ? 0 : monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterHomeIsDead();
		}
	}

	public static void reduceMonsterHpStory(double amount) {
		monsterHpStory.set(monsterHpStory.get() - amount <= 0 ? 0 : monsterHpStory.get() - amount);
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

	public static int getstoryState() {
		return storyState.get();
	}

	public static void setstoryState(int storyStateNow) {
		storyState.set(storyStateNow);
	}

	public static void setAttackPerClick() {
		attackPerClick.set(player.getAttackPerClick());
	}

	public static void addGemCount(int amount) {
		gemCount.set(gemCount.get() + amount);
	}

	public static boolean reduceGemCount(int amount) {
		if (getGemCountProperty().get() < amount)
			return false;
		gemCount.set(getGemCountProperty().get() - amount);
		return true;
	}

	public static void setDamagePerSec() {
		damagePerSec.set(player.getDamagePerSec());
	}

	public static Monster getMonsterHome() {
		return monsterHome;
	}

	public static Monster getMonsterStory() {
		return monsterStory.get(storyState.get());
	}

	public static void setStoryState() {
		storyState.set(storyState.get());
	}
}