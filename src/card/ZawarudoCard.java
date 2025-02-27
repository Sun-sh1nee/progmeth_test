package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;


public class ZawarudoCard extends ActivateCard implements Activatable{
	private int seconds;
	private boolean isOnCooldown = false;

	public ZawarudoCard(String name , String image , CardTier tier) {
		super(name, image, tier , 100);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
        case COMMON: {  
        	seconds = random.nextInt(2) + 1;       
            break;
        }case RARE: {     
        	seconds = random.nextInt(3) + 2;     
            break;
        }case EPIC: {     
        	seconds = random.nextInt(4) + 4;     
            break;
        }case LEGENDARY: {   
        	seconds = 10;      
            break;
        }
        }
    }
	@Override
	public void activate() {
		if (isOnCooldown) return;
		
		
		isOnCooldown = true;
		if (GameLogic.isStoryBattle()) {
			GameLogic.isTimeStop = true;
			
		}
		Timeline buffTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
	        
			GameLogic.isTimeStop = false;
	        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(7), e -> {
	            isOnCooldown = false;
	            
	        }));
	        cooldownTimeline.setCycleCount(1);
	        cooldownTimeline.play();
	    }));
	    buffTimeline.setCycleCount(1);
	    buffTimeline.play();
	
	}
	public boolean isOnCooldown() {
		return isOnCooldown;
	}
	@Override
	public String toString() {
		return String.format("Card: %s [%s Tier]\n- Stop time for: %d\n- Cooldown: %d sec",
                name, tier, seconds, cooldown);
	}
}
