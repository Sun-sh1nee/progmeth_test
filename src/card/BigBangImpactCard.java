package card;

import java.util.Random;
import logic.GameLogic;

public class BigBangImpactCard extends ActivateCard implements Activatable {

	private double damagePerHit;

	public BigBangImpactCard(String name, String image, CardTier tier) {
		super(name, image, tier, 20);
		randomizeAttributes();
	}

	private void randomizeAttributes() {
		Random random = new Random();

		switch (tier) {
		case COMMON: {
			damagePerHit = random.nextDouble() * 20 + 100;
			break;
		}
		case RARE: {
			damagePerHit = random.nextDouble() * 50 + 150;
			break;
		}
		case EPIC: {
			damagePerHit = random.nextDouble() * 50 + 200;
			break;
		}
		case LEGENDARY: {
			damagePerHit = random.nextDouble() * 100000 + 200;
			break;
		}
		}
	}

	@Override
	public void activate() {
		if (isOnCooldown)
			return;
		double damage = GameLogic.getPlayer().getAttackPerClick() * (damagePerHit / 100.0);
		if (GameLogic.isStoryBattle()) {

			GameLogic.reduceMonsterHpStory(damage);
		} else {

			GameLogic.reduceMonsterHpHome(damage);
		}

	}

	@Override
	public String toString() {

		return String.format("Card: %s [%s Tier]\n- Deals: %.2f%% of the damage per click to enemy \n-cooldown: 20sec",
				name, tier, damagePerHit);
	}

}
