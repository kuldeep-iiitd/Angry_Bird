package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class Building_blocks
{
    private Texture texture;
    private Body body;
    private Sprite sprite;
    private int healthPoints;
    private static final float PPM = 50f;
    private boolean isDestroyed = false;
    private float width, height;
    private int type;  //1 for wood, 2 for rock, 3 for glass

    public Building_blocks(World world, Texture texture, float x, float y, float width, float height, float density)
    {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.healthPoints = 20;

        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        createBody(world, x, y, width, height,density);
    }

    private void createBody(World world, float x, float y, float width, float height, float density)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angularDamping = 0.1f;
        bodyDef.position.set((x + width/2) / PPM, (y + height/2) / PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / (2 * PPM), height / (2 * PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
    }

    public int getHealth()
    {
        return this.healthPoints;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setHealth(int n)
    {
        this.healthPoints= n;
    }

    public int getType() {
        return type;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void render(SpriteBatch batch)
    {
        jinda(batch);
    }

    public void jinda(SpriteBatch batch)
    {
        if (!isDestroyed)
        {
            Vector2 position = body.getPosition();

            sprite.setPosition(
                position.x * PPM - sprite.getWidth()/2,
                position.y * PPM - sprite.getHeight()/2
            );
            sprite.setOrigin(width/ 2, height/ 2);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
            sprite.draw(batch);
        }
    }

    public void handleCollision()
    {
        healthPoints--;
        if (healthPoints <= 0)
        {
            markForDestruction();
        }
    }


    public void markForDestruction() {
        isDestroyed = true;
    }

    public boolean isDestroyed()
    {
        return isDestroyed;
    }

    public Body getBody()
    {
        return body;
    }

    public void checkOutOfBounds()
    {
        Vector2 position = body.getPosition();
        if (position.x < -2 || position.x > 30 || position.y < -2 || position.y > 16)
        {
            markForDestruction();
        }
    }

    public void dispose()
    {
        texture.dispose();
    }
}
