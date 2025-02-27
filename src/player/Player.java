package player;

import java.util.ArrayList;
import java.util.List;
import Item.AttackItem;
import Item.ChanceToDropGemItem;
import Item.CompanionItem;
import Item.CritDamageItem;
import Item.CritRateItem;
import Item.Item;

public class Player {
  private int attackPerClick;
  private double critRate;
  private double critDamage;
  private double chanceToDropGem;
  private int damagePerSec;
  private List<Item> items;

  public Player() {
    this.items = new ArrayList<>();
    AttackItem attackItem = new AttackItem("123");
    CritRateItem critRateItem = new CritRateItem("123");
    CompanionItem companionItem = new CompanionItem("123");
    CritDamageItem critDamageItem = new CritDamageItem("123");
    ChanceToDropGemItem chanceToDropGemItem = new ChanceToDropGemItem("123");
    items.add(attackItem);
    items.add(critRateItem);
    items.add(critDamageItem);
    items.add(chanceToDropGemItem);
    items.add(companionItem);
    this.setAttackPerClick(attackItem.getAttack());
    this.setChanceToDropGem(chanceToDropGemItem.getChanceToDropGem());;
    this.setCritDamage(critDamageItem.getCritDamage());
    this.setCritRate(critRateItem.getCritChance());
    this.setDamagePerSec(companionItem.getDamagePerSec());

  }
  
  public int getDamagePerSec() {
	return damagePerSec;
}

  public void setDamagePerSec(int damagePerSec) {
	this.damagePerSec = damagePerSec;
  }

	public int getAttackPerClick() {
    return attackPerClick;
  }

  public void setAttackPerClick(int attackPerClick) {
    this.attackPerClick = attackPerClick;
  }

  public double getCritRate() {
    return critRate;
  }

  public void setCritRate(double critRate) {
    this.critRate = critRate;
  }

  public double getCritDamage() {
    return critDamage;
  }

  public void setCritDamage(double critDamage) {
    this.critDamage = critDamage;
  }

  public double getChanceToDropGem() {
    return chanceToDropGem;
  }

  public void setChanceToDropGem(double chanceToDropGem) {
    this.chanceToDropGem = chanceToDropGem;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void updateAllItems() {
	  for(Item item : items) {
		  item.updateStat();
	  }
  }
}