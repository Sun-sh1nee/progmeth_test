package card;

import java.util.Random;
import logic.GameLogic;

public class HeavyHiterCard extends ActivateCard implements Activatable{
	
	private double damagePerHit;
	public HeavyHiterCard(String name , String image , CardTier tier) {
		super(name, image, tier , 15);
		randomizeAttributes();
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
			double damage = GameLogic.getMonsterStory().getMonsterHp() * (damagePerHit/100.0);
	        GameLogic.reduceMonsterHpStory(damage);
	    } else {
	    	double damage = GameLogic.getMonsterHome().getMonsterHp()  * (damagePerHit/100.0);
	        GameLogic.reduceMonsterHpHome(damage);
	    }


	}
	
	@Override
	public String toString() {
		
		return String.format("Card: %s [%s Tier]\n- Deals: %.2f%% of the monster's max HP \n-cooldown: 15sec",
	            name, tier, damagePerHit);
	}

}
