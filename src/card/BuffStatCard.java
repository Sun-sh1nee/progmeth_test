package card;


import java.util.Random;

import logic.GameLogic;

public class BuffStatCard extends BaseCard {

	private double damagePerClick;
    private double gemDropChance;
    private double critChance;
    private double critDamage;
    private double companionBoost;
	
	public BuffStatCard(String name, String image, CardTier tier) {
		super(name, image, tier);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
	        case COMMON: {
	        	damagePerClick = random.nextDouble() * 5 + 1;  
	            gemDropChance = random.nextDouble() * 5 + 1;  
	            critChance = random.nextDouble() * 1 + 0.1;     
	            critDamage = random.nextDouble() * 5 + 1;      
	            companionBoost = random.nextDouble() * 1 + 0.1;  
	            break;
	        }case RARE: {
	        	damagePerClick = random.nextDouble() * 5 + 5;  
                gemDropChance = random.nextDouble() * 5 + 5;   
                critChance = random.nextDouble() * 1 + 1;      
                critDamage = random.nextDouble() * 5 + 5;     
                companionBoost = random.nextDouble() * 2 + 1; 
                break;
            }case EPIC: {
                damagePerClick = random.nextDouble() * 10 + 10;  
                gemDropChance = random.nextDouble() * 10 + 10;  
                critChance = random.nextDouble() * 2 + 2;        
                critDamage = random.nextDouble() * 10 + 10;     
                companionBoost = random.nextDouble() * 5 + 3;   
                break;
            }case LEGENDARY: {
                damagePerClick = random.nextDouble() * 20 + 20;  
                gemDropChance = random.nextDouble() * 20 + 20;   
                critChance = random.nextDouble() * 3 + 3;       
                critDamage = random.nextDouble() * 15 + 15;      
                companionBoost = random.nextDouble() * 10 + 5;   
                break;
            }
        }
    }
	
	public void applyBuff() {
		GameLogic.ApplyDamageCardBoost(damagePerClick);
		GameLogic.ApplyCritChanceCardBoost(critChance);
		GameLogic.ApplyCritDamageCardBoost(critDamage);
		GameLogic.ApplyGemDropChanceCardBoost(gemDropChance);
		GameLogic.ApplyCompanionCardBoost(companionBoost);
	}
	
	public void CancelBuff() {
		GameLogic.CancelDamageCardBoost(damagePerClick);
		GameLogic.CancelCritChanceCardBoost(critChance);
		GameLogic.CancelCritDamageCardBoost(critDamage);
		GameLogic.CancelGemDropChanceCardBoost(gemDropChance);
		GameLogic.CancelCompanionCardBoost(companionBoost);
	}

	@Override
	public String toString() {
		
		return String.format("Card: %s [%s Tier]\n"
                + "- Damage Per Click: +%.2f%%\n"
                + "- Gem Drop Chance: +%.2f%%\n"
                + "- Critical Chance: +%.2f%%\n"
                + "- Critical Damage: +%.2f%%\n"
                + "- Companion Boost: +%.2f%%\n",
                name, tier, damagePerClick, gemDropChance, critChance, critDamage, companionBoost);
	}

}
