package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;

public class Small_Pig extends Pig
{
    public Small_Pig(World world, float x, float y) {
        super(world, x, y, 0.6f, 0.6f, "small_pig.png");
    }

    @Override
    public void updateSpritePosition() {
        if (!isDestroyed) {
            float x = body.getPosition().x * PPM;
            float y = body.getPosition().y * PPM;
            sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        }
    }
}
