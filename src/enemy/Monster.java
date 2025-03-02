package enemy;

public class Monster {
	private int monsterHp;
	private int stageMonster;
	private int coinDrop;
	private String monsterURL;

	public Monster(int baseHealth, int baseCoin, int stage, double scalFactorHp, double scalFactorCoin,
			String imageMonster) {
		this.setStageMonster(stage);
		int monsterHealth = (int) Math.pow(scalFactorHp, this.stageMonster - 1) * Math.max(1, baseHealth);
		int monsterCoin = (int) Math.pow(1 + scalFactorCoin, this.stageMonster - 1) * Math.max(1, baseCoin);
		this.setMonsterHp(monsterHealth);
		this.setCoinDrop(monsterCoin);
		this.monsterURL =  ClassLoader.getSystemResource(imageMonster).toString();

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

}