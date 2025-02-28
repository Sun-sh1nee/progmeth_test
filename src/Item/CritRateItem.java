package Item;

import logic.GameLogic;

public class CritRateItem extends Item {

	private double critChance;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CritRateItem(String itemURL) {
		super("CritRateItem", 500, itemURL);

		setcritChance(0.20);
		setScalFacCost(0.17);
		setScalFacStatus(0.01);


	}

	@Override
	public void updateStat() {
		// TODO Auto-generated method stub
		GameLogic.getPlayer().setCritRate(critChance);
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get()+1);
		setcritChance(getCritChance() + getScalFacStatus());

		setCostItem((int)(getCostItem().get() * (1 + getScalFacCost())));	
		updateStat();
	}

	public double getCritChance() {
		return critChance;
	}

	public void setcritChance(double critChance) {
		this.critChance= critChance;
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

	@Override
	public String toString() {
		return "CritRate";
	}
	
	

}
