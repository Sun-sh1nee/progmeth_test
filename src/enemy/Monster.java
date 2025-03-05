package enemy;

public class Monster {
	private int monsterHp;
	private int stageMonster;
	private int coinDrop;
	private String monsterURL;
	private String monsterDeadURL;

	public Monster(int baseHealth, int baseCoin, int stage, double scalFactorHp, double scalFactorCoin, String name) {
		this.setStageMonster(stage);
		int monsterHealth = (int) Math.pow(scalFactorHp, this.stageMonster - 1) * Math.max(1, baseHealth);
		int monsterCoin = (int) Math.pow(1 + scalFactorCoin, this.stageMonster - 1) * Math.max(1, baseCoin);
		this.setMonsterHp(monsterHealth);
		this.setCoinDrop(monsterCoin);
		if (name.equals("croissant")) {
			this.monsterURL = ClassLoader.getSystemResource("monster/croissant.png").toString();
			this.monsterDeadURL = ClassLoader.getSystemResource("monster/croissantDead.png").toString();
		} else if (name.equals("croissantKing")) {
			this.monsterURL = ClassLoader.getSystemResource("monster/croissantKing.png").toString();
			this.monsterDeadURL = ClassLoader.getSystemResource("monster/croissantKingDead.png").toString();
		} else {
			this.monsterURL = ClassLoader.getSystemResource("monster/cabbage.png").toString();
			this.monsterDeadURL = ClassLoader.getSystemResource("monster/cabbageDead.png").toString();
		}

	}

	public int getCoinDrop() {
		return coinDrop;
	}

	public void setCoinDrop(int coinDrop) {
		this.coinDrop = coinDrop;
	}

	public int getMonsterHp() {

		return monsterHp;
	}

	public void setMonsterHp(int monsterHp) {

		this.monsterHp = Math.max(monsterHp, 0);
	}

	public int getStageMonster() {
		return stageMonster;
	}

	public void setStageMonster(int stageMonster) {
		this.stageMonster = Math.max(stageMonster, 0);
	}

	public String getMonsterURL() {
		return monsterURL;
	}

	public String getMonsterDeadURL() {
		return monsterDeadURL;
	}

}