package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

// this is the bird class , contains all data related to birds
public class Bird
{
    protected Body body;
    protected Sprite sprite;
    protected float width;
    protected float height;
    protected static final float PPM=50f;

    int type;

    public Body getBody()
    {
        return this.body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bird(World world, float x, float y, float width, float height, String texturePath)
    {
        this.width = width;
        this.height = height;

        Texture texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        sprite.setSize(width * PPM, height * PPM);
        sprite.setOriginCenter();

        this.body = createBody(world, x/PPM, y/PPM, width, height);
        updateSpritePosition();
    }

    public Sprite getSprite()
    {
        return this.sprite;
    }

    private Body createBody(World world, float x, float y, float width, float height)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public void setBodyType(BodyDef.BodyType bodyType)
    {
        body.setType(bodyType);
    }

    //this is used to update the poosition of sprite along wit the position of body
    public void updateSpritePosition()
    {
        float x = body.getPosition().x * PPM;
        float y = body.getPosition().y * PPM;
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void dispose()
    {
        this.dispose();
    }
}
