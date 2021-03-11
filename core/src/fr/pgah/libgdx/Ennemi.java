package fr.pgah.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.Math;

public class Ennemi{
    private Texture texture;
    private int posx=0;
    private int posy=0;
    private boolean isDead=false;
    private int height=0;
    private int width=0;
    private Hitbox hitboxEnnemi;

    public Ennemi(Texture texture, int posX, int posY, int width, int height){
        this.texture = texture;
        this.posx=posX;
        this.posy=posY;
        this.width=width;
        this.height=height;
        hitboxEnnemi = new Hitbox(posx,posy,width,height);
        
    }

    public void dessineEnnemi(SpriteBatch batch){
        if(!isDead)
        batch.draw(texture, this.posx, this.posy, this.width, this.height);
    }

    public void dessineEnnemi(SpriteBatch batch, TextureRegion currentFrame){
        if(!isDead)
        batch.draw(currentFrame, this.posx, this.posy, this.width, this.height);
    }

    public void dessineEnnemi(SpriteBatch batch, Texture texture, int width, int height){
        if(!isDead)
        batch.draw(texture, this.posx, this.posy, this.width, this.height);
    }

    public void decrementPosX(){
        posx-=8;
    }   

    public void decrementPosXByTwo(){
        posx-=2;
    }   
    
    public void incrementPosX(){
        posx+=8;
    }
    
    public void incrementPosY(){
        posy+=8;
    }
        
    public void decrementPosY(){
        posy-=8;
    }

    public int getX(){
        return posx;
    }

    public void setDead(){
        this.isDead=true;
    }

    public Hitbox getHitbox(){
        hitboxEnnemi.refreshHitbox(posx,posy);
        return hitboxEnnemi;
    }

}