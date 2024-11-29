package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class LoadingScreen extends ScreenAdapter
{
    AngryBirdGame game;
    private Texture Background;
    StretchViewport viewport;
    private float timer = 0f;
    public static int randomloopnumber=0;
    public LoadingScreen(AngryBirdGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Background = new Texture("Splash_Screen.png ");
        viewport = new StretchViewport(8, 5);

        Gdx.input.setInputProcessor(new InputAdapter()
        {
            @Override
            public boolean keyDown(int keyCode)
            {
                if (keyCode == Input.Keys.SPACE)
                {
                        game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        });

    }

    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void drawBackground()
    {

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        game.batch.draw(Background , 0 , 0 , worldWidth , worldHeight);

    }

    @Override
    public void render(float delta)
    {
        timer+=delta;
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        game.batch.begin();
        drawBackground();

        float displayTime = 3f;
        if (timer > displayTime)
        {
            game.setScreen(new MenuScreen(game));
        }

        game.batch.end();
    }

    public void marjao()
    {
        Background.dispose();
    }

    @Override
    public void hide()
    {
        marjao();
    }
}
