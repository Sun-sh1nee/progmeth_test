package card;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public abstract class ActivateCard extends BaseCard implements Activatable {
    protected int cooldown;
    protected SimpleDoubleProperty cooldownTimeLeft ;
    protected boolean isOnCooldown = false;

    public ActivateCard(String name, String image, CardTier tier , int cooldownTime) {
        super(name, image, tier);
        cooldown = cooldownTime;
        this.cooldownTimeLeft = new SimpleDoubleProperty(cooldown);
    }

    public abstract void activate();

    public int getCooldown() {
        return cooldown;
    }

    public boolean isOnCooldown() {
        return isOnCooldown;
    }

    public SimpleDoubleProperty cooldownTimeLeftProperty() {
        return cooldownTimeLeft;
    }

    public void startCooldown() {
        if (isOnCooldown) return;
        this.activate();
        isOnCooldown = true;
        cooldownTimeLeft.set(cooldown);

        Timeline cooldownTimer = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> {
                cooldownTimeLeft.set(cooldownTimeLeft.get() - 0.1);
//                System.out.println(cooldownTimeLeft.get());
                if (cooldownTimeLeft.get() <= 0) {
                    isOnCooldown = false;
                    cooldownTimeLeft.set(0);
                }
            })
        );
        cooldownTimer.setOnFinished(e -> {
        	cooldownTimeLeft.set(cooldown);
        });
        cooldownTimer.setCycleCount((int) (cooldown * 10)); 
        cooldownTimer.play();
        
    }
    
    public void resetCooldown() {
    	cooldownTimeLeft.set(cooldown);
    	isOnCooldown = false;
    }

}
