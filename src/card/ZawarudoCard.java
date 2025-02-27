package card;

import java.util.Random;

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
        	seconds = random.nextInt(2) ;       
            break;
        }case RARE: {     
        	seconds = random.nextInt(3) + 1;     
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
		if (isOnCooldown)return;
//		zawarudooooo
	    
	}
	public boolean isOnCooldown() {
		return isOnCooldown;
	}
	@Override
	public String toString() {
		return String.format("Card: %s [%s Tier]\n- stop time for : %.2f%%  \n-cooldown: 15sec",
	            name, tier, seconds);
	}
}
