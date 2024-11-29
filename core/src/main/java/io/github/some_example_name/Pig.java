package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class Pig
{
    public Body body;
    public Sprite sprite;
    public float width;
    public float height;
    public int healthPoints;
    public boolean isDestroyed = false;
    public static final float PPM = 50f;
    public Texture texture;



    public Pig(World world, float x, float y, float width, float height, String texturePath){
        this.width = width;
        this.height = height;
        this.healthPoints = 30;

        texture = new Texture(texturePath);
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(width * PPM, height * PPM);
        createBody(world, x, y, width, height);
    }


    private void createBody(World world, float x, float y, float width, float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angularDamping = 0.5f;
        bodyDef.position.set((x + width*PPM/2) / PPM, (y + height*PPM/2) / PPM);

        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(width/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 5.0f;
        fixtureDef.friction = 1.0f;
        fixtureDef.restitution = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
    }

    public int getHealth()
    {
        return this.healthPoints;
    }

    public void setHealth(int n)
    {
        this.healthPoints= n;
    }

    public void updateSpritePosition()
    {
        chiii();
    }

    public void chiii()
    {
        if (!isDestroyed) {
            float x = body.getPosition().x * PPM;
            float y = body.getPosition().y * PPM;
            sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        }
    }

    public void handleCollision(){
        healthPoints--;
        if (healthPoints <= 0) {
            markForDestruction();
        }
    }

    public void markForDestruction() {
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void checkOutOfBounds()
    {
        Vector2 position = body.getPosition();
        if (position.x < -2 || position.x > 30 || position.y < -2 || position.y > 16)
        {
            markForDestruction();
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
