package io.github.pirateducks.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pirateducks.PirateDucks;


public class PauseScreen implements Screen  {

    private final Array<Sprite> buttons = new Array<>();

    private Texture pauseTextTexture;
    private Sprite pauseTextSprite;

    private Texture continueButtonTexture;
    private Sprite continueButtonSprite;

    private Texture settingsButtonTexture;
    private Sprite settingsButtonSprite;

    private Texture quitButtonTexture;
    private Sprite quitButtonSprite;

    private PirateDucks mainClass;
    private Screen prevScreen;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public PauseScreen(PirateDucks mainClass, Screen prevScreen){
        this.mainClass = mainClass;
        this.prevScreen = prevScreen;
    }
    /**
     * Called to draw the screen
     * @param batch
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        // set background as blurred map
        backgroundTexture = new Texture("map_blurred.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);

        if (backgroundSprite != null){
            backgroundSprite.draw(batch);
        }


        if (pauseTextSprite != null){
            pauseTextSprite.draw(batch);
        }

        for (Sprite button : buttons){
            if (button != null){
                button.draw(batch);
            }
        }
    }

    /**
     * Called to update the screen
     * @param delta The delta time since the last update
     */
    public void update(float delta){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            // We need to ensure multiple buttons are not pressed at once
            boolean buttonPressed = false;

            for (int i=0;i<buttons.size;i++){
                Sprite button = buttons.get(i);
                // As mouse position coordinates start in top left whereas game coordinates start in bottom left
                // we need to inverse them
                int x = Gdx.graphics.getWidth() - Gdx.input.getX();
                int y = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Check if mouse position is inside button when clicked
                float buttonX = button.getX();
                float buttonY = button.getY();
                float buttonW = button.getWidth();
                float buttonH = button.getHeight();

                if (x >= buttonX && x<= (buttonX + buttonW) && y >= buttonY && y <= (buttonY + buttonH)){
                    if (i == 0){
                        System.out.println("Continue button pressed");
                        buttonPressed = true;
                        //MyGdxGame.setCurrentScreen(new LevelManager());
                        mainClass.setCurrentScreen(prevScreen);

                    } else if (i==1) {
                        System.out.println("Settings button pressed");
                        buttonPressed = true;
                    } else if (i == 2){
                        System.out.println("Quit button pressed");
                        Gdx.app.exit();
                    }
                }
            }
        }
    }

    /**
     * Called when this screen becomes the active screen
     */
    public void startDisplaying(OrthographicCamera camera){
        // Create game logo sprite
//        gameLogoTexture = new Texture("logo.png");
//        gameLogoSprite = new Sprite(gameLogoTexture);
//        // Scale logo depending on window size
//        float scaleRatio = (gameLogoSprite .getWidth() / camera.viewportWidth) * 1.5f;
//        gameLogoSprite.setSize(gameLogoSprite.getWidth() / scaleRatio, gameLogoSprite.getHeight()/scaleRatio);
//
//        //Centre the logo
//        gameLogoSprite.setPosition(camera.viewportWidth/2 - gameLogoSprite.getWidth()/2,(camera.viewportHeight/2 - gameLogoSprite.getHeight()/2) * 2.5f);

        pauseTextTexture = new Texture("pauseScreen/pausedText.png");
        pauseTextSprite = new Sprite(pauseTextTexture);

        float scaleRatio = (pauseTextSprite.getWidth() / camera.viewportWidth * 1.8f);
        pauseTextSprite.setSize(pauseTextSprite.getWidth() / scaleRatio, pauseTextSprite.getHeight()/scaleRatio);
        pauseTextSprite.setPosition(camera.viewportWidth/2 - pauseTextSprite.getWidth()/2,(camera.viewportHeight/2 - pauseTextSprite.getHeight()/2) * 1.5f);

        //Buttons

        // Start Game Button
        continueButtonTexture = new Texture("pauseScreen/continue.png");
        continueButtonSprite = new Sprite(continueButtonTexture);
        scaleRatio = buttonScaleRatio(continueButtonSprite, camera);
        continueButtonSprite.setSize(continueButtonSprite.getWidth()/scaleRatio,continueButtonSprite.getHeight()/scaleRatio);

        int offset = -10;

        continueButtonSprite.setPosition(camera.viewportWidth/2 - continueButtonSprite.getWidth()/2,(camera.viewportHeight/2-continueButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(continueButtonSprite);

        // Settings Button
        settingsButtonTexture = new Texture("pauseScreen/settings.png");
        settingsButtonSprite = new Sprite(settingsButtonTexture);
        scaleRatio = buttonScaleRatio(settingsButtonSprite, camera);
        settingsButtonSprite.setSize(settingsButtonSprite.getWidth()/scaleRatio,settingsButtonSprite.getHeight()/scaleRatio);

        offset -= 20;

        settingsButtonSprite.setPosition(camera.viewportWidth/2 - settingsButtonSprite.getWidth()/2,(camera.viewportHeight/2-settingsButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(settingsButtonSprite);

        // Quit button

        quitButtonTexture = new Texture("pauseScreen/quit.png");
        quitButtonSprite = new Sprite(quitButtonTexture);
        scaleRatio = buttonScaleRatio(quitButtonSprite, camera);
        quitButtonSprite.setSize(quitButtonSprite.getWidth()/scaleRatio,quitButtonSprite.getHeight()/scaleRatio);

        offset -= 20;
        quitButtonSprite.setPosition(camera.viewportWidth/2 - quitButtonSprite.getWidth()/2,(camera.viewportHeight/2-quitButtonSprite.getHeight()/2) + (offset/scaleRatio));
        buttons.add(quitButtonSprite);
    }

    private float buttonScaleRatio(Sprite button, OrthographicCamera camera){
        float scaleRatio = (button.getWidth()/camera.viewportWidth) * 3.5f;
        return scaleRatio;
    }


    /**
     * Called when this screen is no longer the active screen
     * Use this method to dispose of everything
     */
    public void stopDisplaying(){
        continueButtonTexture.dispose();
        settingsButtonTexture.dispose();
        quitButtonTexture.dispose();
    }

}
