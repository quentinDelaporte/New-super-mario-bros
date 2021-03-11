package fr.pgah.libgdx;

public class Hitbox{
    int coinBasGaucheX;
    int coinBasGaucheY;
    int coinBasDroitX;
    int coinBasDroitY;
    int coinHautGaucheX;
    int coinHautGaucheY;
    int coinHautDroitX;
    int coinHautDroitY;
    int width;
    int height;

    public Hitbox(int x, int y, int width, int height){
        this.coinBasGaucheX=x;
        this.coinBasGaucheY=y;
        this.coinBasDroitX=x+width;
        this.coinBasDroitY=y;
        this.coinHautGaucheX=x;
        this.coinHautGaucheY=y+height;
        this.coinHautDroitX=x+width;
        this.coinHautDroitY=y+height;
        this.width=width;
        this.height=height;
    }

    public int getCoinBasGaucheX() {
        return coinBasGaucheX;
    }

    public int getCoinBasGaucheY() {
        return coinBasGaucheY;
    }

    public int getCoinBasDroitX() {
        return coinBasDroitX;
    }

    public int getCoinBasDroitY() {
        return coinBasDroitY;
    }

    public int getCoinHautGaucheX() {
        return coinHautGaucheX;
    }

    public int getCoinHautGaucheY() {
        return coinHautGaucheY;
    }

    public int getCoinHautDroitX() {
        return coinHautDroitX;
    }

    public int getCoinHautDroitY() {
        return coinHautDroitY;
    }


    public void refreshHitbox(int x, int y){
        this.coinBasGaucheX=x;
        this.coinBasGaucheY=y;
        this.coinBasDroitX=x+width;
        this.coinBasDroitY=y;
        this.coinHautGaucheX=x;
        this.coinHautGaucheY=y+height;
        this.coinHautDroitX=x+width;
        this.coinHautDroitY=y+height;
    }

}