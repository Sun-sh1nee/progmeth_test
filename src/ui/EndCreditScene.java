package ui;


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
    private Text spaceBarHint;

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
                "A long time ago in a galaxy far, far away...\n\n" +
                "🎉 Congratulations! 🎉\n\n" +
                "You have completed the game!\n\n" +
                "Game Developed by:\n" +
                "- Developer 1\n" +
                "- Developer 2\n\n" +
                "Special Thanks to:\n" +
                "- Community\n\n" +
                "Thanks for playing!\n\n" +
                "May the Force be with you!"
        );

    	creditsText.setFill(Color.WHITE);
        creditsText.setFont(new Font("Arial", 24));
        creditsText.setTextAlignment(TextAlignment.CENTER);

        
        creditContainer = new VBox(creditsText);
        creditContainer.setAlignment(Pos.CENTER);
        creditContainer.setTranslateY(600);




        
        spaceBarHint = new Text("Press SPACE BAR to Play Again");
        spaceBarHint.setFont(new Font("Arial", 18));
        spaceBarHint.setTextAlignment(TextAlignment.CENTER);
        spaceBarHint.setVisible(false);

        
        this.getChildren().addAll(creditContainer, spaceBarHint);
        StackPane.setAlignment(spaceBarHint, Pos.BOTTOM_CENTER);

        
        this.setFocusTraversable(true);
        this.setOnKeyPressed(e -> {
        	
            if (spaceBarHint.isVisible() && e.getCode() == KeyCode.SPACE) {
                GameLogic.init();
                SceneManager.switchTo("HOME");
            }
        });
        
        
        

        updateEndCreditUI(); 
    }

    public void updateEndCreditUI() {
        double scrollSpeed = 5; 
        double totalScrollDistance = 800; 
        double totalTime = totalScrollDistance / scrollSpeed * 25;

        timeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            double currentY = creditContainer.getTranslateY();
            creditContainer.setTranslateY(currentY - scrollSpeed);

        }));
        
        timeline.setCycleCount((int) (totalTime / 30));
        timeline.setOnFinished(e -> {
        	Platform.exit();
        	System.exit(0);
        });

        timeline.play();
    }

}
