package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;


public class GlassCannonCard extends ActivateCard implements Activatable{

	private double critDamageBoost;
	public GlassCannonCard(String name , String image , CardTier tier) {
		super(name, image, tier, 6);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
	        case COMMON: {  
	        	critDamageBoost = random.nextDouble() * 20 + 30;       
	            break;
	        }case RARE: {     
	        	critDamageBoost = random.nextDouble() * 25 + 50;     
	            break;
	        }case EPIC: {     
	        	critDamageBoost = random.nextDouble() * 25 + 100;     
	            break;
	        }case LEGENDARY: {   
	        	critDamageBoost = random.nextDouble() * 50 + 150;      
	            break;
	        }
        }
    }
	
	@Override
	public void activate() {
		if (isOnCooldown)return;
	    
	    isOnCooldown = true;
	    
	    GameLogic.applyCritDamageCardBoost(critDamageBoost);
	    
	    Timeline buffTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
	      
	        GameLogic.cancelCritDamageCardBoost(critDamageBoost);
	        
	        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(cooldown), e -> {
	            
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
		
		return String.format("Card: %s [%s Tier]\n- Increase: %.2f%% critcal damage \n-cooldown: 6sec",
				name , tier , critDamageBoost);
		
	}

}
