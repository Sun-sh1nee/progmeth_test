package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;

public class BullsEyeCard extends ActivateCard implements Activatable{
	private double critChanceBoost;
	public BullsEyeCard(String name , String image , CardTier tier) {
		super(name, image, tier , 6);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
        case COMMON: {  
        	critChanceBoost = random.nextDouble() * 5 + 10;       
            break;
        }case RARE: {     
        	critChanceBoost = random.nextDouble() * 5 + 20;     
            break;
        }case EPIC: {     
        	critChanceBoost = random.nextDouble() * 15 + 50;     
            break;
        }case LEGENDARY: {   
        	critChanceBoost = 100;      
            break;
        }
        }
    }
	@Override
	public void activate() {
		if (isOnCooldown) return;
	    
	    isOnCooldown = true;
	    
	    GameLogic.ApplyCritChanceCardBoost(critChanceBoost);
	    
	    Timeline buffTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
	      
	        GameLogic.ApplyCritChanceCardBoost(critChanceBoost);
	        
	        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(6), e -> {
	            
	            isOnCooldown = false;
	            
	        }));
	        cooldownTimeline.setCycleCount(1);
	        cooldownTimeline.play();
	    }));
	    buffTimeline.setCycleCount(1);
	    buffTimeline.play();
	}
	
	@Override
	public String toString() {
		
		return String.format("Card: %s [%s Tier]\n- Increase: %.2f%% critcal chance \n-cooldown: 6sec",
				name , tier , critChanceBoost);
		
	}

}
