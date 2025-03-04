package Item;

import logic.GameLogic;

public class CritDamageItem extends Item {
	private double critDamage;
	private double scalFacCost;
	private double scalFacStatus;

	public CritDamageItem(String itemURL) {
		super("CritDamageItem", 750, itemURL);

		setCritDamage(1.5);
		setScalFacCost(0.19);
		setScalFacStatus(0.05);
	}

	@Override
	public void updateStat() {
		GameLogic.getPlayer().setCritDamage(critDamage);
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get() + 1);
		setCritDamage(getCritDamage() + getScalFacStatus());
		setCostItem((int) (getCostItem().get() * (1 + getScalFacCost())));
		updateStat();
	}

	public double getCritDamage() {
		return critDamage;
	}

	public void setCritDamage(double critDamage) {
		this.critDamage = critDamage;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost;
	}

	public String toString() {
		return "Critical Damage";
	}

}
