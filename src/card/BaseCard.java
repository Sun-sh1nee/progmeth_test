package card;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class BaseCard implements Comparable<BaseCard>{
	protected String name;
	protected String CardURL;
	protected CardTier tier;
	 protected int cooldown;
	 protected boolean isOnCooldown = false;
	 protected double cooldownTimeLeft = 0;

	public BaseCard(String name , String image , CardTier tier) {
		this.setName(name);
		this.setCardURL(image);
		this.setTier(tier);
		String path = ClassLoader.getSystemResource(image).toString();
		this.setCardURL(path);
	}

	public boolean isOnCooldown() {
        return isOnCooldown;
    }
	
	public abstract String toString();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardURL() {
		return CardURL;
	}

	public void setCardURL(String cardURL) {
		CardURL = cardURL;
	}

	public CardTier getTier() {
		return tier;
	}

	public void setTier(CardTier tier) {
		this.tier = tier;
	}
	
	

	public String getTierStyle() {
	    switch (this.tier) {
	        case COMMON:
	            return "#808080";
	        case RARE:
	            return "#0000FF";
	        case EPIC:
	            return "#800080"; 
	        case LEGENDARY:
	            return "#FFA500"; 
	        default:
	            return "#000000"; 
	    }
	}
	
	@Override
    public int compareTo(BaseCard other) {
        return Integer.compare(other.tier.ordinal(), this.tier.ordinal());
    }

	 public double getCooldownTimeLeft() {
	        return cooldownTimeLeft;
	 }

	  public void startCooldown() {
	        if (isOnCooldown) return;

	        isOnCooldown = true;
	        cooldownTimeLeft = cooldown;

	        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
	            cooldownTimeLeft--;
	            if (cooldownTimeLeft <= 0) {
	                isOnCooldown = false;
	                cooldownTimeLeft = 0;
	            }
	        }));

	        cooldownTimer.setCycleCount(cooldown);
	        cooldownTimer.play();
	   }
	
		public int getCooldown() {
			return cooldown;
		}

}
