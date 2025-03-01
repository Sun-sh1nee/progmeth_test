package Item;

import logic.GameLogic;

public class AttackItem extends Item {
	private int attack;
	private double scalFacStatus;
	private double scalFacCost;

	public AttackItem(String itemURL) {
		super("AttackItem", 100, itemURL);

		setAttack(100);
		setScalFacCost(0.13);
		setScalFacStatus(1.15);
	}

	public void updateStat() {
		GameLogic.getPlayer().setAttackPerClick(attack);
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get() + 1);
		setAttack((int) (getAttack() * getScalFacStatus()));
		setCostItem((int) (getCostItem().get() * (1 + getScalFacCost())));
		updateStat();
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack < 1 ? 1 : attack;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus < 0 ? 0.01 : scalFacStatus;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost < 0 ? 0.01 : scalFacCost;
	}

	public String toString() {
		return "Attack";
	}
}
