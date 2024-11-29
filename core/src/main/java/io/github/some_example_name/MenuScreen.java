package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;


// this is the screen which supports menu, here you can choose random level, or map or can go back to the loading screen
public class MenuScreen extends ScreenAdapter
{
    AngryBirdGame game;
    private Texture MenuBackground;
    StretchViewport viewport;
    private Texture Map;
    private Texture Setting;
    private Texture Random_level;
    private Texture BackArrow;
    private Texture Logo;


    public MenuScreen(AngryBirdGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        //taking images
        MenuBackground = new Texture("Menu_Background.png");
        Logo = new Texture("Game_Icon_Menu.png");
        Map = new Texture("Map_Icon_Menu.png");
        Random_level = new Texture("RL_Icon_Menu.png");
        Setting = new Texture("Settings_Icon_Menu.png");
        BackArrow = new Texture("Exit_Icon_Menu.png");
        viewport = new StretchViewport(32, 20);
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    private void input()
    {
        //to check where it is touched
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.getCamera().unproject(touchPos);

            int worldX = (int) touchPos.x;
            int worldY = (int) touchPos.y;

            float settingX = 20, settingY = 4, settingWidth = 12, settingHeight = 2;
            float Random_levelX = 18, Random_levelY = 7, Random_levelWidth = 11, Random_levelHeight = 2;
            float MapX = 21, MapY = 9, MapWidth = 6, MapHeight = 2;

            if (worldX >= settingX && worldX <= (settingX + settingWidth) && worldY >= settingY && worldY <= (settingY + settingHeight))
            {
                System.out.println("Settings");
            }

            if (worldX >= Random_levelX && worldX <= (Random_levelX + Random_levelWidth) && worldY >= Random_levelY && worldY <= (Random_levelY + Random_levelHeight))
            {

                game.setScreen(new random_level(game,0));
            }

            if (worldX >= MapX && worldX <= (MapX + MapWidth) && worldY >= MapY && worldY <= (MapY + MapHeight))
            {
                game.setScreen(new LevelScreen(game));
            }

            if (worldX >= 0 && worldX<=5 &&  worldY <= 1 && worldY>=0)
            {
                game.setScreen(new LoadingScreen(game));
            }
        }
    }


    public void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //drawing images , buttons on screen
        game.batch.draw(MenuBackground, 0, 0, worldWidth, worldHeight);
        game.batch.draw(Logo, 14, 13, 16, 8);
        game.batch.draw(Map, 20, 9, 10, 4);
        game.batch.draw(Random_level, 16, 6, 15, 4);
        game.batch.draw(Setting, 16, 3, 15, 4);
        game.batch.draw(BackArrow, -3,-1, 12, 4);

        game.batch.end();
    }

    @Override
    public void render(float delta)
    {
        gamechaule();
    }

    public void gamechaule()
    {
        input();
        draw();
    }

    public void marjao()
    {
        MenuBackground.dispose();
        Random_level.dispose();
        Setting.dispose();
        Map.dispose();
        Logo.dispose();
        BackArrow.dispose();
    }

    @Override
    public void hide()
    {
        marjao();
    }
}
