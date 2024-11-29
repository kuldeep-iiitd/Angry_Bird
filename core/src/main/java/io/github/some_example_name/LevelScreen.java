package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.awt.*;

public class LevelScreen extends ScreenAdapter
{
    AngryBirdGame game;
    private Texture Background;
    StretchViewport viewport;


    public LevelScreen(AngryBirdGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        Background = new Texture("Level_Selector_Background.png");
        viewport = new StretchViewport(1280, 720);
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void mujhechuliya()
    {
        if (Gdx.input.isTouched())
        {

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(touchPos);


            int worldX = (int) touchPos.x;
            int worldY = (int) touchPos.y;

            int exitX = 560, exitY = 80, exitWidth = 160, exitHeight = 60;


            if (worldX >= 252+30 && worldX <= 432+30 &&
                worldY >= 310 && worldY <= 380)
            {
                game.setScreen(new Level_1(game,0));
            }

            if (worldX >= 252+30 && worldX <= 432+30 &&
                worldY >= 310-20-70 && worldY <= 310-20)
            {
                game.setScreen(new Level_1(game,-1));
            }

            if (worldX >= 432+30+90  && worldX <= 432+30+90+180 &&
                worldY >= 310 && worldY <= 380)
            {
                game.setScreen(new Level_2(game,0));
            }

            if (worldX >= 432+30+90  && worldX <= 432+30+90+180 &&
                worldY >= 310-20-70 && worldY <= 310-20)
            {
                game.setScreen(new Level_2(game,-1));
            }

            if (worldX >= 432+30+90+180 +90  && worldX <= 432+30+90+180 +90 + 180 &&
                worldY >= 310 && worldY <= 380)
            {
                game.setScreen(new Level_3(game,0));
            }

            if (worldX >= 432+30+90+180 +90  && worldX <= 432+30+90+180 +90 + 180 &&
                worldY >= 310-20-70 && worldY <= 310-20)
            {
                game.setScreen(new Level_3(game,-1));
            }

            if (worldX >= exitX && worldX <= (exitX + exitWidth) &&
                worldY >= exitY && worldY <= (exitY + exitHeight))
            {
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    private void input()
    {
        mujhechuliya();
    }

    public void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        game.batch.draw(Background, 0, 0, worldWidth, worldHeight);

        game.batch.end();
    }

    @Override
    public void render(float delta)
    {
        hallabol();
    }

    public void hallabol()
    {
        input();
        draw();
    }

    @Override
    public void hide()
    {
        Background.dispose();
    }
}
