package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.MainGame;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;

public class LoseScreen implements Screen {

    /*
    LoseScreen is the class that renders the mission failed screen
    and either transfers over to the PlayScreen or the MainMenuScreen
     */

    //Main Game Reference
    MainGame game;

    //Menu buttons
    ImageButton playButton;
    ImageButton quitButton;

    Stage stage;

    Texture title;

    //Menu positions
    Vector2 playPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 60);
    Vector2 quitPos = new Vector2(Constants.CAMERA_WIDTH/2-48, 20);
    Vector2 titlePos = new Vector2(Constants.CAMERA_WIDTH/2-96, 100);

    //Colliders
    private World world;
    private Box2DDebugRenderer b2dr;

    //Reference
    private CustomCamera camera;

    public LoseScreen (final MainGame game){

        this.game = game;
        title = new Texture("Sprites/Menu/MissionFailed.png");

        //Collisions
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        //Camera
        camera = new CustomCamera();

        //Buttons
        setupButtons();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, new Matrix4(camera.cam.combined));

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        game.batch.draw(title, titlePos.x, titlePos.y);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }

    //Custom functions from here

    public void update(float delta){
        world.step(1/60f, 6, 2);

        //Updates Camera
        camera.update();
    }

    void setupButtons(){
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);

        playButton = new ImageButton(new Image(new Texture("Sprites/Menu/PlayOff.png")).getDrawable());
        quitButton = new ImageButton(new Image(new Texture("Sprites/Menu/ExitOff.png")).getDrawable());

        playButton.setPosition(playPos.x, playPos.y);
        quitButton.setPosition(quitPos.x, quitPos.y);

        playButton.setSize(96, 32);
        quitButton.setSize(96, 32);

        playButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/PlayOn.png")).getDrawable();
        quitButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/ExitOn.png")).getDrawable();

        stage.addActor(playButton);
        stage.addActor(quitButton);

        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new PlayScreen(game));
            }
        });

        quitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

}