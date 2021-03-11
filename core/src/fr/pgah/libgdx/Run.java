package fr.pgah.libgdx;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.files.FileHandle;
public class Run extends ApplicationAdapter {
  private OrthographicCamera camera;
  private TiledMapRenderer tiledMapRenderer;
  private int cameraPositionX = 0;
  private int cameraPositionY = 0;
  private int screenHeight;
  private int screenWidth;
  private int tilePixelWidth=0;
  private int tilePixelHeight=0;

  private Map map01;
  private int groundHeight;
//---------------------------------------- affichage perso
  private SpriteBatch batch;
  private Perso mario;
  private Ennemi goomba;
  private Texture staticMario;
  private Texture deadMario;
//---------------------------------------- animation marche mario
  private Anim marioWalkAnimationToRight;
  private Anim marioWalkAnimationToLeft;
  private Anim marioJumpAnimationToRight;
  private Anim marioJumpAnimationToLeft;
  private Anim goombaRunAnimationToLeft;
  private float stateTime;
//---------------------------------------- JUMP
  private int initialYBeforeJump=0;
//---------------------------------------- HITBOX


    @Override
    public void create() {
      staticMario=new Texture("./mario/static/01.gif");
      deadMario = new Texture("./mario/dead/01.gif");
      genererFenetre();
      genererCamera();
      genererMap();
      genererPerso();
      creationMarioAnim();
    }

    public void creationMarioAnim(){
      // DEPLACEMENT ANIMATION VERS LA Droite
      FileHandle walkToRightTexture=Gdx.files.internal("./mario/walk/WalkToRight.png");
      marioWalkAnimationToRight = new Anim(walkToRightTexture,3,1);
      // DEPLACEMENT ANIMATION VERS LA GAUCHE
      FileHandle walkToLeftTexture=Gdx.files.internal("./mario/walk/WalkToLeft.png");
      marioWalkAnimationToLeft = new Anim(walkToLeftTexture,3,1);
      // SAUT ANIMATION VERS LA DROITE
      FileHandle jumpToRightTexture=Gdx.files.internal("./mario/jump/JumpToRight.png");
      marioJumpAnimationToRight = new Anim(jumpToRightTexture,2,1);
      // SAUT ANIMATION VERS LA GAUCHE
      FileHandle jumpToLeftTexture=Gdx.files.internal("./mario/jump/JumpToLeft.png");
      marioJumpAnimationToLeft = new Anim(jumpToLeftTexture,2,1);

      FileHandle goombaRunToLeftTexture=Gdx.files.internal("./goomba/run/goombaRunToLeft.png");
      goombaRunAnimationToLeft = new Anim(goombaRunToLeftTexture,1,8);
    }

    public void genererMap(){
      map01=new Map("mario01.tmx",192);
      tiledMapRenderer = map01.getTiledMapRenderer();
      tilePixelWidth= map01.getTilePixelWidth();
      tilePixelHeight= map01.getTilePixelHeight();
      groundHeight=map01.getGroundHeight();
    }

    public void genererFenetre(){
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      screenHeight = screenSize.height-100;
      screenWidth = screenSize.width-100;
      System.out.println(screenWidth + ":" + screenHeight);
      Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
      Gdx.graphics.setTitle("Mario"); 
    }

    public void genererCamera(){
      
      float w = Gdx.graphics.getWidth();
      float h = Gdx.graphics.getHeight();
      camera = new OrthographicCamera();
      camera.setToOrtho(false,w,h);
      camera.update();
    }

    public void genererPerso(){
      batch = new SpriteBatch();
      mario = new Perso(staticMario,50,50);
      goomba = new Ennemi(null,1300,192,100,100);

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        drawingMario();
        if(goomba!=null){
          goomba.dessineEnnemi(batch,goombaRunAnimationToLeft.getAnimation(stateTime));
          IaGoomba();
          collisionGoomba();
        }
        marioJump();
        keyPressed();
        batch.end();
    }

    public void keyPressed() {
      if(Gdx.input.isKeyPressed(Keys.LEFT)){
        if(cameraPositionX>0){
          camera.translate(-8,0);
          cameraPositionX=cameraPositionX-8;
          resetAnim();
          mario.setWalkingToLeft(true);
          if(goomba!=null)
          goomba.incrementPosX();

        }
      }
      if(Gdx.input.isKeyPressed(Keys.RIGHT)){
        if(cameraPositionX<(tilePixelWidth-screenWidth)-10){
          camera.translate(8,0);
          cameraPositionX=cameraPositionX+8;
          resetAnim();
          mario.setWalkingToRight(true);
          if(goomba!=null)
          goomba.decrementPosX();
        }
      }
      if(Gdx.input.isKeyPressed(Keys.UP) && mario.isJumping()==false && mario.isFalling()==false){
        mario.setJumping(true);
        initialYBeforeJump=cameraPositionY;
      } 
      if(Gdx.input.isKeyPressed(Keys.D)){
        mario.setDead(true);
      }
      if(!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT) && Gdx.input.isKeyPressed(Keys.LEFT)){
        resetAnim();
        mario.setStatic(true);
      }
  }

  public void resetAnim(){
    mario.setWalkingToLeft(false);
    mario.setWalkingToRight(false);
    mario.setStatic(false);
    mario.setJumpingToLeft(false);
    mario.setJumpingToRight(false);
  }

  public void setMarioAnimationToWalkToRight(){
    mario.dessine(batch,marioWalkAnimationToRight.getAnimation(stateTime),(screenWidth/2)-25,groundHeight);
  }
  public void setMarioAnimationToWalkToLeft(){
    mario.dessine(batch,marioWalkAnimationToLeft.getAnimation(stateTime),(screenWidth/2)-25,groundHeight);
  }
  public void setMarioAnimationToJumpToLeft(){
    mario.dessine(batch,marioJumpAnimationToLeft.getAnimation(stateTime),(screenWidth/2)-25,groundHeight);
  }
  public void setMarioAnimationToJumpToRight(){
    mario.dessine(batch,marioJumpAnimationToRight.getAnimation(stateTime),(screenWidth/2)-25,groundHeight);
  }
  public void setMarioAnimationToStatic(){
    mario.dessine(batch,(screenWidth/2)-25,groundHeight);
  }
  public void setMarioAnimationToDead(){
    mario.dessine(batch,deadMario,(screenWidth/2)-25,groundHeight);
  }

  public void drawingMario(){
    if(!mario.isDead()){
      if(mario.isStatic()){
        setMarioAnimationToStatic();
      } else if(mario.isWalkingToRight() && !mario.isJumping()){
        setMarioAnimationToWalkToRight();
      } else if(mario.isWalkingToLeft() && !mario.isJumping()){
        setMarioAnimationToWalkToLeft();
      } else if(mario.isJumping() && mario.isWalkingToLeft()){
        setMarioAnimationToJumpToLeft();
      } else if(mario.isJumping() && mario.isWalkingToRight()){
        setMarioAnimationToJumpToRight();
      } 
    } else {
      setMarioAnimationToDead();
    }
  }

  public void marioJump(){
    if(mario.isJumping()==true && cameraPositionY<=initialYBeforeJump+4.5*32 && mario.isFalling()==false){
      if(cameraPositionY==initialYBeforeJump+4.5*32){
        mario.setJumping(false);
        mario.setFalling(true);
      }
      camera.translate(0,8);
      cameraPositionY=cameraPositionY+8;
      if(goomba!=null)
      goomba.decrementPosY();
    } else if(cameraPositionY>=0 && mario.isFalling()==true){
      camera.translate(0,-8);
      cameraPositionY=cameraPositionY-8;
      if(goomba!=null)
      goomba.incrementPosY();
      if(cameraPositionY==0){
        mario.setJumping(false);
        mario.setFalling(false);
        mario.setJumpingToLeft(false);
      }
    } 
  }

  public void IaGoomba(){
    if(goomba.getX()<1000){
      goomba.decrementPosXByTwo();
    }
    if(goomba.getX()==-2000){
      goomba.setDead();
      goomba=null;
    }
  }

  public void collisionGoomba(){
    Hitbox hitboxGoomba = goomba.getHitbox();
    int coinBasGaucheXGoomba=hitboxGoomba.getCoinBasGaucheX()-((screenWidth/2)-25);
    int coinBasGaucheYGoomba=hitboxGoomba.getCoinBasGaucheY();
    int coinBasDroitXGoomba=hitboxGoomba.getCoinBasDroitX()-((screenWidth/2)-25);
    int coinBasDroitYGoomba=hitboxGoomba.getCoinBasDroitY();
    int coinHautGaucheXGoomba=hitboxGoomba.getCoinHautGaucheX()-((screenWidth/2)-25);
    int coinHautGaucheYGoomba=hitboxGoomba.getCoinHautGaucheY();
    int coinHautDroitXGoomba=hitboxGoomba.getCoinHautDroitX()-((screenWidth/2)-25);
    int coinHautDroitYGoomba=hitboxGoomba.getCoinHautDroitY();

    Hitbox hitboxMario = mario.getHitbox();
    int coinBasGaucheXMario=hitboxMario.getCoinBasGaucheX();
    int coinBasGaucheYMario=hitboxMario.getCoinBasGaucheY();
    int coinBasDroitXMario=hitboxMario.getCoinBasDroitX();
    int coinBasDroitYMario=hitboxMario.getCoinBasDroitY();
    int coinHautGaucheXMario=hitboxMario.getCoinHautGaucheX();
    int coinHautGaucheYMario=hitboxMario.getCoinHautGaucheY();
    int coinHautDroitXMario=hitboxMario.getCoinHautDroitX();
    int coinHautDroitYMario=hitboxMario.getCoinHautDroitY();


    if (
      (coinBasDroitXGoomba >= coinHautGaucheXMario &&
      coinBasDroitXGoomba <= coinHautDroitXMario &&
      coinBasDroitYGoomba <= coinHautGaucheYMario &&
      coinBasDroitYGoomba >= coinBasGaucheYMario ) ||
     (coinBasGaucheXGoomba <= coinHautDroitXMario &&
      coinBasGaucheXGoomba >= coinHautGaucheXMario && 
      coinBasGaucheYGoomba <= coinHautDroitYMario && 
      coinBasGaucheYGoomba >= coinBasDroitYMario ) || 
     (coinHautGaucheXGoomba >= coinHautGaucheXMario && 
      coinHautGaucheXGoomba <= coinHautDroitXMario && 
      coinHautGaucheYGoomba >= coinBasDroitYMario && 
      coinHautGaucheYGoomba <= coinHautDroitYMario ) || 
     (coinHautDroitXGoomba <= coinHautDroitXMario && 
      coinHautDroitXGoomba >= coinHautGaucheXMario && 
      coinHautDroitYGoomba <= coinHautGaucheXMario && 
      coinHautDroitYGoomba >= coinBasDroitYMario)
    )
    {
      goomba.setDead();
      goomba=null;
    }
    //if ((coinHautDroitXMario/2 - coinHautDroitXGoomba/2) < 0 &&
    //    (coinHautDroitYMario/2 - coinHautDroitYGoomba/2) < -110 && 
    //    (coinHautDroitXMario/2 - coinHautDroitXGoomba/2) > -40 && 
    //    (coinHautDroitYMario/2 - coinHautDroitYGoomba/2) > -130){
  }
}