package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class AngryBirdGame extends Game
{

    SpriteBatch batch;
    ShapeRenderer sher;
    BitmapFont font;

    @Override
    public void create ()
    {
        batch = new SpriteBatch();
        sher = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose ()
    {
        batch.dispose();
        sher.dispose();
        font.dispose();
    }

}
