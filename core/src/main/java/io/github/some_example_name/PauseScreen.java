package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

//pause screen , it opens when called in between a game
//from here yu can either go back to menu
//or u can resume to the game where you left
public class PauseScreen extends ScreenAdapter
{
    AngryBirdGame game;
    private Texture Background;
    StretchViewport viewport;
    private Texture MainMenu;
    private Texture ResumeMenu;


    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private int level;

    public PauseScreen(AngryBirdGame game , int level )
    {
        this.game = game;
        this.level = level;
    }

    @Override
    public void show()
    {
        Background = new Texture("Pause_Screen_Background.png");
        MainMenu = new Texture("Menu_Icon_PauseScreen.png");
        ResumeMenu = new Texture("Resume_Icon_PauseScreen.png");

        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
        viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    private void input()
    {
        kyabaath();
    }

    public void kyabaath()
    {
        if(Gdx.input.isTouched())
        {

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(touchPos);

            float MenuX = 100, MenuY = 100, MenuWidth = 300, MenuHeight = 100;
            float PauseX = 700, PauseY = 100, PauseWidth = 400, PauseHeight = 100;

            if (touchPos.x >= MenuX && touchPos.x <= (MenuX + MenuWidth) &&
                touchPos.y >= MenuY && touchPos.y <= (MenuY + MenuHeight))
            {
                game.setScreen(new MenuScreen(game));
            }
            if (touchPos.x >= PauseX && touchPos.x <= (PauseX + PauseWidth) &&
                touchPos.y >= PauseY && touchPos.y <= (PauseY + PauseHeight))
            {

                if(level == 1 )
                {
                    game.setScreen(new Level_1(game , -1));
                }
                if(level == 2 )
                {
                    game.setScreen(new Level_2(game , -1));
                }
                if(level == 3 )
                {
                    game.setScreen(new Level_3(game , -1));
                }

            }
        }
    }

    public void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        drawkardoooo();

        game.batch.end();
    }

    public void drawkardoooo()
    {
        game.batch.draw(Background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        game.batch.draw(MainMenu, 100, 100, 300, 100);
        game.batch.draw(ResumeMenu, 700, 100, 400, 100);
    }

    @Override
    public void render(float delta)
    {
        input();
        draw();
    }

    @Override
    public void dispose()
    {
        Background.dispose();
        MainMenu.dispose();
        ResumeMenu.dispose();
    }
}
