package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


// this is the catapult class, means the slingshot, just to draw
public class Catapult
{
    private Texture texture;
    private Sprite sprite;

    Catapult()
    {
        texture = new Texture("Catapult.png");
        sprite = new Sprite(texture);
        Float PPM = 50f;
        sprite.setSize(5f*PPM,4f*PPM);
        sprite.setPosition(280,145);

    }

    public void render(SpriteBatch batch , int x , int y , int width , int height)
    {
        sprite.draw(batch);
    }

    public void dispose()
    {
        this.dispose();
    }
}
