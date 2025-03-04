package Item;

import logic.GameLogic;

public class ChanceToDropGemItem extends Item {
	private double chanceToDropGem;
	private double scalFacCost;
	private double scalFacStatus;

	public ChanceToDropGemItem(String itemURL) {
		super("ChanceToDropGemItem", 1000, itemURL);

		setChanceToDropGem(0.01);
		setScalFacCost(0.28);
		setScalFacStatus(0.005);
	}

	@Override
	public void updateStat() {
		GameLogic.getPlayer().setChanceToDropGem(chanceToDropGem);
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get() + 1);
		setChanceToDropGem(getChanceToDropGem() + getScalFacStatus());
		setCostItem((int) (getCostItem().get() * (1 + getScalFacCost())));
		updateStat();
	}

	public double getChanceToDropGem() {
		return chanceToDropGem;
	}

	public void setChanceToDropGem(double chanceToDropGem) {
		this.chanceToDropGem = chanceToDropGem;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus;
	}

	public String toString() {
		return "Chance To Drop Gem";
	}

}
