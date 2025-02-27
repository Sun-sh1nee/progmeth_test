package Item;

import logic.GameLogic;

public class CompanionItem extends Item {
	private int damagePerSec;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CompanionItem(String itemURL) {
		super("CompanionItem", 750, itemURL);
		
		setDamagePerSec(75);
		setScalFacCost(0.18);
		setScalFacStatus(1.35);
	}

	@Override
	public void updateStat() {
		// GameLogic update player status 
		GameLogic.getPlayer().setDamagePerSec(damagePerSec);;
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get()+1);
		setDamagePerSec((int) (getDamagePerSec() * getScalFacStatus()));
		setCostItem((int)(getCostItem().get() * (1 + getScalFacCost())));
		updateStat();
	}


	public int getDamagePerSec() {
		return damagePerSec;
	}

	public void setDamagePerSec(int damagePerSec) {
		this.damagePerSec = damagePerSec;
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
		return "CompanionItem";
	}
}
