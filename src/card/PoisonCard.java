package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;

public class PoisonCard extends ActivateCard implements Activatable{
	
	private double dotDamage;
	
	public PoisonCard(String name , String image , CardTier tier) {
		super(name, image, tier , 7);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
        case COMMON: {  
        	dotDamage = random.nextDouble() * 20 + 90;       
            break;
        }case RARE: {     
        	dotDamage = random.nextDouble() * 25 + 125;     
            break;
        }case EPIC: {     
        	dotDamage = random.nextDouble() * 25 + 150;     
            break;
        }case LEGENDARY: {   
        	dotDamage = random.nextDouble() * 50 + 200;      
            break;
        }
        }
    }
	@Override
	public void activate() {
		if (isOnCooldown) return;

	    isOnCooldown = true;
	    
	    double poison = GameLogic.getPlayer().getAttackPerClick() * dotDamage;
	    
	    
	    Timeline poisonTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	        
	        if (GameLogic.isStoryBattle()) {
	            GameLogic.reduceMonsterHpStory(poison);
	        } else {
	            GameLogic.reduceMonsterHpHome(poison);
	        }
	    }));
	    poisonTimeline.setCycleCount(10);
	    poisonTimeline.play();
	    
	    Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(7), e -> {
	        isOnCooldown = false;
	    }));
	    cooldownTimeline.setCycleCount(1);
	    cooldownTimeline.play();
	}
	
	@Override
	public String toString() {
		return String.format("Card: %s [%s Tier]\n- deal: %.2f%% of damage per click every seconds to enemy for 10 seconds \n-cooldown: 7sec",
				name , tier , dotDamage);
	}
}
