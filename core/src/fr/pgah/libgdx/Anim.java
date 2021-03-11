package fr.pgah.libgdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;

public class Anim{
    private Animation<TextureRegion> Animation;
    private Texture sheet;
    private float stateTime;
    private TextureRegion[] frames;

    public Anim(FileHandle pathToTexture, int nbImagesLargeur, int nbImagesHauteur){
        sheet = new Texture(pathToTexture);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / nbImagesLargeur, sheet.getHeight() / nbImagesHauteur);
        frames = new TextureRegion[nbImagesLargeur * nbImagesHauteur];
        int index = 0;
        for (int i = 0; i < nbImagesHauteur; i++) {
            for (int j = 0; j < nbImagesLargeur; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        Animation = new Animation<TextureRegion>(0.15f, frames);
    }

    public TextureRegion getAnimation(float stateTime){
        return Animation.getKeyFrame(stateTime, true);
    }


}