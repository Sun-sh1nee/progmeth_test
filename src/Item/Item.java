package Item;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Item {

    protected String nameItem;
    protected IntegerProperty levelItem;
    protected IntegerProperty costItem;
    protected String itemURL;

    public Item(String nameItem, int costItem, String itemURL) {
    	this.setNameItem(nameItem);
    	this.costItem = new SimpleIntegerProperty(costItem);
        this.setItemURL(itemURL);
        this.levelItem = new SimpleIntegerProperty(1);
    }

    public abstract void updateStat();
    public abstract void upgrade();

	public String getNameItem() {
		return nameItem;
	}

	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}

	public int getLevelItem() {
		return levelItem.get();
	}

	public void setLevelItem(int levelItem) {
		this.levelItem.set(levelItem);
	}

	public IntegerProperty getCostItem() {
		return costItem;
	}

	public void setCostItem(int costItem) {
		this.costItem.set(costItem);
	}

	public String getItemURL() {
		return itemURL;
	}

	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}
	
	public IntegerProperty levelProperty() {
		return levelItem;
	}
	
    
}
