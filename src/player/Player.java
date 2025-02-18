package player;

import java.util.ArrayList;
import java.util.List;

import Item.AttackItem;
import Item.ChanceToDropGemItem;
import Item.CritDamageItem;
import Item.CritRateItem;
import Item.Item;
import companion.Companion;

public class Player {

  private int attackPerClick;
  private double critRate;
  private double critDamage;
  private double chanceToDropGem;
  private List<Item> items;
  // private List<Card> cards;
  private List<Companion> companions;

  public Player() {
    this.items = new ArrayList<>();
    Item attackItem = new AttackItem("123");
    Item critRateItem = new CritRateItem("123");
    Item critDamageItem = new CritDamageItem("123");
    Item chanceToDropGemItem = new ChanceToDropGemItem("123");
    items.add(attackItem);
    items.add(critRateItem);
    items.add(critDamageItem);
    items.add(chanceToDropGemItem);
    updateAllItems();
    // this.cards = new ArrayList<>();
    this.companions = new ArrayList<>();
  }

//  public void removeCard(int index) {
//      this.cards.remove(index);
//  }
//
//  public void changeCard(int index, Card card) {
//      this.cards.set(index, card);
//  }

  public void addCompanion(Companion companion) {
    this.companions.add(companion);
  }

  // Getters and setters for each field
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

//  public List<Card> getCards() {
//    return cards;
//  }
//
//  public void setCards(List<Card> cards) {
//    this.cards = cards;
//  }

  public List<Companion> getCompanions() {
    return companions;
  }

  public void setCompanions(List<Companion> companions) {
    this.companions = companions;
  }
  public void updateAllItems() {
	  for(Item item : items) {
		  item.updateStat();
	  }
  }
}
