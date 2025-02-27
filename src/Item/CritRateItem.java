package Item;

import logic.GameLogic;

public class CritRateItem extends Item {

	private double critChance;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CritRateItem(String itemURL) {
		super("CritRateItem", 500, itemURL);
		
		setcritChance(1.3);
		setScalFacCost(1.02);
		setScalFacStatus(0.7);
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
		setCostItem((int)(getCostItem().get() * (getScalFacCost())));
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
