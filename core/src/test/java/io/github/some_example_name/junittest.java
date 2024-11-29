package io.github.some_example_name;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class junittest
{
    private static HeadlessApplication a;
    private static GL20 gl20;

    @BeforeClass
    public static void setUp()
    {
        gl20 = mock(GL20.class);

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        a = new HeadlessApplication(new ApplicationAdapter()
        {
            @Override
            public void create()
            {
                Gdx.gl = gl20;
                Gdx.gl20 = gl20;
            }
        }, config);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @AfterClass
    public static void tearDown()
    {
        if (a != null)
        {
            a.exit();
            a = null;
        }
    }

    public void test3()
    {
        World world = new World(new Vector2(0, -9.8f), true);

        Texture texture = mock(Texture.class);

        Building_blocks block = new Building_blocks(world, texture, 100f, 200f, 2f, 2f, 2.0f);

        int initialHealth = block.getHealth();
        block.handleCollision();

        assertEquals(initialHealth - 1, block.getHealth());

        world.dispose();
    }

    @Test
    public void collisiondamage_block()
    {
        test3();
    }

    public void test_1()
    {
        World world = new World(new Vector2(0, -9.8f), true);
        Texture texture = mock(Texture.class);
        Building_blocks block = new Building_blocks(world, texture, 100f, 200f, 2f, 2f, 2.0f);

        block.setHealth(50);
        assertEquals(50, block.getHealth());

        world.dispose();
    }

    @Test
    public void blockhealth_test()
    {
        test_1();
    }

    public void test()
    {
        World world = new World(new Vector2(0, -9.8f), true);
        Texture texture = mock(Texture.class);
        Building_blocks block = new Building_blocks(world, texture, -10f, 200f, 2f, 2f, 2.0f);

        block.checkOutOfBounds();

        assertTrue(!block.isDestroyed());
        world.dispose();
    }

    @Test
    public void blockoutofbouund_test()
    {
        test();
    }


}
