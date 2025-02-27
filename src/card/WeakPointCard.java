package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;

public class WeakPointCard extends ActivateCard implements Activatable{
	private double extraBoost;
	public WeakPointCard(String name , String image , CardTier tier) {
		super(name, image, tier ,7);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
        case COMMON: {  
        	extraBoost = random.nextDouble() * 5 + 5;       
            break;
        }case RARE: {     
        	extraBoost = random.nextDouble() * 5 + 10;     
            break;
        }case EPIC: {     
        	extraBoost = random.nextDouble() * 10 + 15;     
            break;
        }case LEGENDARY: {   
        	extraBoost = random.nextDouble() * 20 + 30;      
            break;
        }
        }
    }
	@Override
	public void activate() {
		if (isOnCooldown)return;
	    
	    isOnCooldown = true;
	    
	    GameLogic.ApplyextraDamage(extraBoost);
	    
	    Timeline buffTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
	      
	        GameLogic.CancelextraDamage(extraBoost);
	        
	        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(7), e -> {
	            
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
		
		return String.format("Card: %s [%s Tier]\n- Enemy get: %.2f%%  more damage \n-cooldown: 7sec",
				name , tier , extraBoost);
		
	}
	

}
