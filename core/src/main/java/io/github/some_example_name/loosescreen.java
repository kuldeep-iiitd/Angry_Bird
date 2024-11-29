package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class loosescreen extends ScreenAdapter {
    AngryBirdGame game;
    private SpriteBatch batch;
    private Texture myTexture;
    private float displayTime = 3f;
    private float timer = 0f;
    StretchViewport viewport;

    public loosescreen(AngryBirdGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        myTexture = new Texture("gameover.jpg");
        viewport = new StretchViewport(16, 10);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        timer += delta;


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        if (timer < displayTime) {

            float screenWidth = viewport.getWorldWidth();
            float screenHeight = viewport.getWorldHeight();


            batch.draw(myTexture, 0, 0, screenWidth, screenHeight);
        }

        batch.end();


        if (timer > displayTime) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void hide() {
        batch.dispose();
        myTexture.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        myTexture.dispose();
    }
}
