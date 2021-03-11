   
package fr.pgah.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.Math;
 
public class Perso extends ApplicationAdapter {
    private Texture texture;
    private boolean Static=true;
    private boolean WalkingToLeft=false;
    private boolean WalkingToRight=false;
    private boolean JumpingToLeft=false;
    private boolean JumpingToRight=false;
    private boolean Dead=false;
    private boolean Jumping=false;
    private boolean Falling=false;
    private Hitbox hitboxPerso;
    private int width;
    private int height;
    
    public Perso(Texture texture, int width, int height) { 
        this.texture = texture;
        hitboxPerso=new Hitbox(0,0,width,height);
        this.height=height;
        this.width=width;
    }

    public void dessine(SpriteBatch batch, int posx, int posy){
        batch.draw(texture, posx, posy, this.width, this.height);
    }

    public void dessine(SpriteBatch batch, TextureRegion currentFrame, int posx, int posy){
        batch.draw(currentFrame, posx, posy, this.width, this.height);
    }

    public void dessine(SpriteBatch batch, Texture texture, int posx, int posy){
        batch.draw(texture, posx, posy, this.width, this.height);
    }


    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture t){
        this.texture=t;
    }

    public boolean isStatic() {
        return Static;
    }

    public void setStatic(boolean static1) {
        Static = static1;
    }

    public boolean isWalkingToLeft() {
        return WalkingToLeft;
    }

    public void setWalkingToLeft(boolean walkingToLeft) {
        WalkingToLeft = walkingToLeft;
    }

    public boolean isWalkingToRight() {
        return WalkingToRight;
    }

    public void setWalkingToRight(boolean walkingToRight) {
        WalkingToRight = walkingToRight;
    }

    public boolean isJumpingToLeft() {
        return JumpingToLeft;
    }

    public void setJumpingToLeft(boolean jumpingToLeft) {
        JumpingToLeft = jumpingToLeft;
    }

    public boolean isJumpingToRight() {
        return JumpingToRight;
    }

    public void setJumpingToRight(boolean jumpingToRight) {
        JumpingToRight = jumpingToRight;
    }

    public boolean isDead() {
        return Dead;
    }

    public void setDead(boolean dead) {
        Dead = dead;
    }

    public boolean isJumping() {
        return Jumping;
    }

    public void setJumping(boolean jumping) {
        Jumping = jumping;
    }

    public boolean isFalling() {
        return Falling;
    }

    public void setFalling(boolean falling) {
        Falling = falling;
    }

    public Hitbox getHitbox(){
        return hitboxPerso;
    }
}