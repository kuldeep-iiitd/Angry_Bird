package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class random_level extends ScreenAdapter
{
    World world;
    AngryBirdGame game;
    private Texture Background;
    private Texture Pause_Button;
    StretchViewport viewport;

    private Catapult catapult;
    private List<Building_blocks> blocks;
    private List<Pig> pigs;
    private List<Bird> birds;
    private Bird birdusing;

    private static int BIRD_KI_GINTI = 0;
    private static float GROUND_Y = 180;
    private static float BIRD_SPACING = 50;
    private static float DANDA_X = 280;
    private static float DANDA_Y = 280;
    private static float SCREEN_WIDTH = 1280;
    private static float SCREEN_HEIGHT = 720;
    private static float PPM = 50f;
    private Music backgroundMusic;

    private Vector2 dragStart;
    private boolean kheech_rakha_h = false;
    private boolean birdLaunched = false;
    private Vector2 initialBirdPosition;
    private boolean isSettling = false;
    private float settlingTimer = 0;
    private static final float SETTLING_TIME = 2f;

    private int state;

    public random_level(AngryBirdGame game,int state)
    {
        this.game = game;
        this.state = state;
    }

    public void loadState(String filePath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {

            String line = reader.readLine().trim();
            String[] birdie = line.split(" ");
            BIRD_KI_GINTI = birdie.length;
            float startX = 50;
            for (int i = 0; i < BIRD_KI_GINTI; i++)
            {
                int type1=Integer.parseInt(birdie[i]);
                String path1 ;

                switch (type1)
                {
                    case 1:
                        path1 = "Red_Bird.png";
                        break;
                    case 2:
                        path1 = "Black_Bird.png";
                        break;
                    default:
                        path1 = "Yellow_Bird.png";

                }



                Bird bird = new Bird(world, startX + (i * BIRD_SPACING), GROUND_Y,1,1, path1);
                bird.setType(type1);
                bird.setBodyType(BodyDef.BodyType.StaticBody);
                bird.getBody().setGravityScale(0);
                birds.add(bird);
            }

            reader.readLine();

            while (!(line = reader.readLine()).isEmpty())
            {
                String[] parts = line.split(" ");
                float x = Float.parseFloat(parts[0]) / PPM;
                float y = Float.parseFloat(parts[1]) / PPM;
                float health = Float.parseFloat(parts[2]);
                float angle = Float.parseFloat(parts[5]);
                float width = Float.parseFloat(parts[4]);
                float height = Float.parseFloat(parts[3]);
                int type = Integer.parseInt(parts[6]);
                String path;
                float density;
                if(type ==1)
                {
                    path = "wood.png";
                    density = 2.0f;
                }
                else if (type==2)
                {
                    path = "rock.png";
                    density = 2.0f;
                }
                else
                {
                    path = "glass.png";
                    density = 1.0f;
                }

                Building_blocks block = new Building_blocks(world, new Texture(path), x, y, width,height,density);
                block.setHealth((int)health);
                block.getBody().setTransform(x, y, angle);
                block.setType(type);
                blocks.add(block);

            }

            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(" ");
                float x = Float.parseFloat(parts[0]) / PPM;
                float y = Float.parseFloat(parts[1]) / PPM;
                float health = Float.parseFloat(parts[2]);
                float angle = Float.parseFloat(parts[3]);
                float w = Float.parseFloat(parts[4]);

                Pig pig = new Pig(world, x, y,w,w,"Small_Pig.png");
                pig.setHealth((int)health);
                pig.getBody().setTransform(x, y, angle);
                pigs.add(pig);
            }

        }
        catch (IOException e)
        {
            System.err.println("ERROR ERROR- " + e.getMessage());
        }
    }

    public void chalu_kardo()
    {
        jameen();
        makaan_banado();
        createRightWall();
        chdiya_banadu();
    }

    @Override
    public void show()
    {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(createContactListener());

        Background = new Texture("Level1_Background.jpg");
        Pause_Button = new Texture("Pause_Button_Level_Screen_Icon.png");
        viewport = new StretchViewport(1280, 720);
        catapult = new Catapult();

        blocks = new ArrayList<>();
        pigs = new ArrayList<>();
        birds = new ArrayList<>();
        chalu_kardo();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("chirp.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        initialBirdPosition = new Vector2(DANDA_X, DANDA_Y);
    }

    private void jameen()
    {
        BodyDef groundBodyDef = new BodyDef();

        groundBodyDef.position.set(640 / PPM, 0 / PPM);
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(640 / PPM, 145/ PPM);
        groundBody.createFixture(groundBox, 100.0f);
        groundBody.setType(BodyDef.BodyType.StaticBody);
        groundBox.dispose();

    }

    private void createRightWall()
    {
        BodyDef rightWallBodyDef = new BodyDef();
        rightWallBodyDef.position.set((SCREEN_WIDTH - 10) / PPM, (SCREEN_HEIGHT / 2) / PPM);
        Body rightWallBody = world.createBody(rightWallBodyDef);

        PolygonShape rightWallShape = new PolygonShape();
        rightWallShape.setAsBox(2 / PPM, (SCREEN_HEIGHT / 2) / PPM);

        FixtureDef rightWallFixtureDef = new FixtureDef();
        rightWallFixtureDef.shape = rightWallShape;
        rightWallFixtureDef.density = 100.0f;

        rightWallBody.createFixture(rightWallFixtureDef);
        rightWallBody.setType(BodyDef.BodyType.StaticBody);

        rightWallShape.dispose();
    }

    private void makaan_banado()
    {
        String udta_panjab = "jatin1_state.txt";
        int n=LoadingScreen.randomloopnumber;
        if(n==0)
        {
            udta_panjab = "jatin1_state.txt";

        }
        else if (n == 1)
        {
            udta_panjab = "jatin2_state.txt";


        }
        else if (n == 2)
        {
            udta_panjab = "jatin3_state.txt";

        }
        else if (n == 3)
        {
            udta_panjab = "jatin4_state.txt";

        }
        LoadingScreen.randomloopnumber++;

        if(state == -1)
        {
            udta_panjab = "randomlevel_savestate.txt";
        }

        FileHandle fileHandle = Gdx.files.internal(udta_panjab);

        if (fileHandle.exists())
        {
            loadState(fileHandle.path());
        }
        else
        {

            System.out.println("Level state file not found: " + udta_panjab);
        }

        for (Building_blocks block : blocks)
        {
            block.getBody().setType(BodyDef.BodyType.DynamicBody);
            block.getBody().setGravityScale(1.0f);
        }

        for (Pig a : pigs)
        {
            a.getBody().setType(BodyDef.BodyType.DynamicBody);
            a.getBody().setGravityScale(1.0f);
        }
    }

    private void chdiya_banadu()
    {
        if (!birds.isEmpty())
        {
            birdusing = birds.get(0);
            panchikobethado(birdusing);
        }
    }

    @Override
    public void render(float delta)
    {
        try
        {
            update(delta);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);


        game.batch.begin();
        drawBackground();
        drawCatapult();
        drawBlocks();
        drawPigs();
        drawBirds();
        drawButton();
        game.batch.end();

        cleanupDestroyedObjects();
        bird_bahar_chali_gyi_kya();
        if(pigs.isEmpty())
        {
            backgroundMusic.stop();
            game.setScreen(new winscreen(game));
        }
        else if (birds.isEmpty())
        {
            backgroundMusic.stop();
            game.setScreen((new loosescreen(game)));
        }

    }

    public void drawBirds()
    {
        for (Bird bird : birds)
        {
            bird.updateSpritePosition();
            bird.getSprite().draw(game.batch);
        }
    }

    public void drawBackground()
    {
        float worldheight = viewport.getWorldHeight();
        float worldwidth = viewport.getWorldWidth();
        game.batch.draw(Background, 0, 0, worldwidth, worldheight);
    }

    public void drawCatapult()
    {
        catapult.render(game.batch, 240, 146, 250, 170);
    }

    private void drawBlocks()
    {
        for (Building_blocks block : blocks)
        {
            block.render(game.batch);
        }
    }

    private void drawPigs()
    {
        for (Pig pig : pigs)
        {
            pig.updateSpritePosition();
            pig.getSprite().draw(game.batch);
        }
    }

    private ContactListener createContactListener()
    {
        return new ContactListener()
        {
            @Override
            public void beginContact(Contact contact)
            {
                Body banda1 = contact.getFixtureA().getBody();
                Body banda2 = contact.getFixtureB().getBody();

                Object bandekijaat1 = contact.getFixtureA().getUserData();
                Object bandekijaat2 = contact.getFixtureB().getUserData();

                handleCollision(bandekijaat1);
                handleCollision(bandekijaat2);

                if (birdusing != null && (banda1 == birdusing.getBody() || banda2 == birdusing.getBody()))
                {
                    isSettling = true;
                    settlingTimer = 0;
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    }

    private void handleCollision(Object userData)
    {
        if (userData instanceof Building_blocks)
        {
            Building_blocks block = (Building_blocks) userData;
            block.handleCollision();
        }
        else if (userData instanceof Pig)
        {
            Pig pig = (Pig) userData;
            pig.handleCollision();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    private void panchikobethado(Bird bird)
    {
        bird.setBodyType(BodyDef.BodyType.KinematicBody);
        bird.getBody().setTransform(DANDA_X/PPM, DANDA_Y/PPM, 0);
        birdLaunched = false;
        Music music = Gdx.audio.newMusic(Gdx.files.internal("push.mp3"));
        music.setVolume(0.5f);
        music.play();
    }

    private void removeBird(Bird bird)
    {
        world.destroyBody(bird.getBody());
        birds.remove(bird);

        if (!birds.isEmpty())
        {
            birdusing = birds.get(0);
            panchikobethado(birdusing);
        }
    }

    public void building_hatha_bhai()
    {
        Iterator<Building_blocks> blockIterator = blocks.iterator();
        while (blockIterator.hasNext())
        {
            Building_blocks block = blockIterator.next();
            if (block.isDestroyed())
            {
                world.destroyBody(block.getBody());
                blockIterator.remove();
            }
        }
    }

    public  void  chidiya_tmkc()
    {
        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext())
        {
            Pig pig = pigIterator.next();
            if (pig.isDestroyed())
            {
                world.destroyBody(pig.getBody());
                pigIterator.remove();
            }
        }
    }

    private void cleanupDestroyedObjects()
    {
        building_hatha_bhai();
        chidiya_tmkc();

    }

    private void update(float delta) throws IOException
    {
        world.step(1/100f, 8, 5);

        if (isSettling)
        {
            settlingTimer += delta;
            if (settlingTimer >= SETTLING_TIME)
            {
                isSettling = false;
                removeBird(birdusing);
            }
        }

        if (!isSettling)
        {
            input();
        }

    }

    private void bird_bahar_chali_gyi_kya()
    {
        Iterator<Bird> iterator = birds.iterator();
        while (iterator.hasNext())
        {
            Bird bird = iterator.next();
            Vector2 position = bird.getBody().getPosition();
            if (position.x * PPM < -50 || position.x * PPM > SCREEN_WIDTH + 50 ||
                position.y * PPM < -50 || position.y * PPM > SCREEN_HEIGHT + 50)
            {
                if (bird == birdusing)
                {
                    world.destroyBody(bird.getBody());
                    iterator.remove();

                    if (!birds.isEmpty())
                    {
                        birdusing = birds.get(0);
                        panchikobethado(birdusing);
                    }
                }
            }
        }
    }

    public void input() throws IOException
    {
        if(birdusing == null || birdLaunched) return;

        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(touchPos);

            if (touchPos.x >= 1200 && touchPos.x <= (1200 + 50) &&
                touchPos.y >= 650 && touchPos.y <= (650 + 50))
            {
                saveGameStateToFile();
                game.setScreen(new PauseScreen(game,1));
                return;
            }

            Vector2 birdPos = new Vector2(birdusing.getBody().getPosition().x * PPM,
                birdusing.getBody().getPosition().y * PPM);
            float touchDistance = new Vector2(touchPos.x - birdPos.x,
                touchPos.y - birdPos.y).len();

            if (!kheech_rakha_h && touchDistance < 100)
            {
                dragStart = new Vector2(touchPos.x, touchPos.y);
                kheech_rakha_h = true;
            }

            if (kheech_rakha_h)
            {
                float dragDistance = new Vector2(touchPos.x - initialBirdPosition.x,
                    touchPos.y - initialBirdPosition.y).len();

                if (dragDistance > 50f)
                {
                    Vector2 dragDir = new Vector2(touchPos.x - initialBirdPosition.x,
                        touchPos.y - initialBirdPosition.y).nor();
                    touchPos.x = initialBirdPosition.x + dragDir.x * 50f;
                    touchPos.y = initialBirdPosition.y + dragDir.y * 50f;
                }

                birdusing.getBody().setTransform(touchPos.x/PPM, touchPos.y/PPM, 0);
            }

        }

        else if (kheech_rakha_h)
        {
            Vector2 dragEnd = new Vector2(birdusing.getBody().getPosition().x * PPM,
                birdusing.getBody().getPosition().y * PPM);
            Vector2 impulse = calculateImpulse(initialBirdPosition, dragEnd);

            birdusing.setBodyType(BodyDef.BodyType.DynamicBody);

            birdusing.getBody().setGravityScale(1.0f);

            birdusing.getBody().applyLinearImpulse(impulse,
                birdusing.getBody().getWorldCenter(), true);

            kheech_rakha_h = false;
            birdLaunched = true;
        }

    }

    private Vector2 calculateImpulse(Vector2 anchor, Vector2 dragEnd)
    {
        Vector2 impulse = new Vector2(anchor).sub(dragEnd);
        impulse.scl(1.0f);

        float maxImpulse = 15f;
        if (impulse.len() > maxImpulse)
        {
            impulse.nor().scl(maxImpulse);
        }
        return impulse;
    }

    public void drawButton()
    {
        game.batch.draw(Pause_Button, 1200, 650, 50, 50);
    }

    @Override
    public void dispose()
    {
        Background.dispose();
        Pause_Button.dispose();
        world.dispose();
    }

    private void saveGameStateToFile() throws IOException
    {

        String fileName = "randomlevel_savestate.txt";

        try (FileWriter writer = new FileWriter(fileName, false))
        {
            for(Bird b:birds)
            {
                writer.write(b.getType() + " ");
            }

            writer.write("\n");
            writer.write("\n");

            for (Building_blocks block : blocks)
            {
                Vector2 position = block.getBody().getPosition();
                float health = block.getHealth();
                float angle = block.getBody().getAngle();

                writer.write(position.x * PPM + " " + position.y * PPM + " " + health +" "+ block.getHeight() +" "+ block.getWidth() + " " + angle +" "+ block.getType()+ "\n");
            }
            writer.write("\n");

            for (Pig pig : pigs)
            {
                Vector2 position = pig.getBody().getPosition();
                float health = pig.getHealth();
                float angle = pig.getBody().getAngle();
                writer.write(position.x * PPM + " " + position.y * PPM + " " + health + " " + angle+" "+ pig.width + "\n");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

