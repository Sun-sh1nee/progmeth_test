package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;

public class HeavyHiterCard extends BaseCard implements Activatable{
	
	private double damagePerHit;
	public HeavyHiterCard(String name , String image , CardTier tier) {
		super(name, image, tier);
		randomizeAttributes();
		cooldown = 15;
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
	        case COMMON: {
	        	damagePerHit = random.nextInt(5)  ;
	            break;
	        }case RARE: {
	        	damagePerHit = random.nextInt(5) + 5;
                break;
            }case EPIC: {
            	damagePerHit = random.nextInt(5) + 10;
                break;
            }case LEGENDARY: {
            	damagePerHit = random.nextInt(5) + 15; 
                break;
            }
        }
    }
	@Override
	public void activate() {
		if (isOnCooldown) return;

		if (GameLogic.isStoryBattle()) {
			double damage = GameLogic.getMonster().getMonsterHp() * (damagePerHit/100.0);
	        GameLogic.reduceMonsterHpStory(damage);
	    } else {
	    	double damage = GameLogic.getMonster().getMonsterHp()  * (damagePerHit/100.0);
	        GameLogic.reduceMonsterHpHome(damage);
	    }

	    isOnCooldown = true;
	    
	    Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
	        isOnCooldown = false;
	    }));
	    cooldownTimeline.setCycleCount(1);
	    cooldownTimeline.play();
	}
	
	@Override
	public String toString() {
		
		return String.format("Card: %s [%s Tier]\n- Deals: %.2f%% of the monster's max HP \n-cooldown: 15sec",
	            name, tier, damagePerHit);
	}

}
