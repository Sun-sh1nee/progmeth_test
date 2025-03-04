package ui;


import javax.swing.plaf.InsetsUIResource;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.util.Duration;
import logic.GameLogic;

public class EndCreditScene extends StackPane {
    private Timeline timeline;
    private Text creditsText;
    private VBox creditContainer;

    public EndCreditScene() {
    	GameLogic.endCreditBackgroundSound();
    	
    	String backgroundImagePath = ClassLoader.getSystemResource("endCredit/bgEnd.png").toString();
    	Image backgroundImage = new Image(backgroundImagePath);

    	this.setBackground(new Background(new BackgroundImage(
    	        backgroundImage, 
    	        BackgroundRepeat.NO_REPEAT,
    	        BackgroundRepeat.NO_REPEAT,
    	        BackgroundPosition.CENTER,
    	        new BackgroundSize(100, 100, true, true, true, true)
    	)));

    	creditsText = new Text(
                "The Great Croissant \n" +
                "Uprising is over! \n\n" + 
                "For generations, \n" +
                "the monstrous Croissants, \n" + 
                "born from a freak bakery \n" +
                "accident involving \n" + 
                "a forgotten sourdough \n" +
                "starter and a rogue meteor, 1\n" + 
                "have terrorized the land.\n" +
                "Their flaky, \n "  + 
                "buttery bodies and \n" +
                "jam-filled eyes struck fear \n" +  
                "into the hearts of all. \n\n"
                + "But you, brave clicker, \n" +
                "you stood against \n" +
                "the doughy tide! \n" +
                "With your tireless clicking \n" +
                "and strategic upgrades, \n" +
                "you've vanquished the Croissant King \n" +
                "and restored peace to the kingdom \n" +
                "(and ensured a steady supply \n" +
                "of non-sentient pastries for all). \n\n"
                
                + "Congratulations, hero! \n" +
                "You kneaded the competition \n"+
                "and rose to the occasion! \n" +
                "Enjoy your well-deserved rest... \n\n " + 
                "and maybe a real croissant \n" +
                "(they're safe now, we promise!). \n\n" +
                "You have completed the game!\n\n\n\n\n" +
                "Game Developed by:\n" +
                "- Wiritpol Poonnak \n" +
                "- Worapob Pongpanich \n" +
                "- Pokpong Sukjai\n\n" +
                "Special Thanks to:\n" +
                "- Vishnu Kotrajaras \n" +
                "- Peerapon Vateekul \n" +
                "- Chate Patanothai \n\n" +
                "Thanks for playing!\n\n" +
                "May the Force be with you!"
        );

    	creditsText.setFill(Color.WHITE);
        creditsText.setFont(new Font("Arial", 24));
        creditsText.setTextAlignment(TextAlignment.CENTER);
        
        creditContainer = new VBox(creditsText);
        creditContainer.setAlignment(Pos.CENTER);
        creditContainer.setTranslateY(1100);
        
        this.getChildren().add(creditContainer);

        
        this.setFocusTraversable(true);

        updateEndCreditUI(); 
    }

    public void updateEndCreditUI() {

        double scrollSpeed = 0.3; 
        double totalScrollDistance = 980; 
        double totalTime = totalScrollDistance / scrollSpeed * 25;

        timeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            double currentY = creditContainer.getTranslateY();
            creditContainer.setTranslateY(currentY - scrollSpeed);

        }));
        
        timeline.setCycleCount((int) (totalTime / 30));
        timeline.setOnFinished(e -> {
        	new Thread(()->{
        		try {
					Thread.sleep(5000);
					Platform.exit();
		        	System.exit(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}).start();
        });

        timeline.play();
    }

}
