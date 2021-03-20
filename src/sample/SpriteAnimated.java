package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

// ------------------------------------------- наш класс с наследием от анимации
public class SpriteAnimated extends Transition {

    private ImageView imageView; // картинка
    private  int count; // сколько спрайтов возьмём
    private  int column;// сколько колонок
    private  int ofsetX;// откуда начинать
    private  int ofsetY;
    private  int width;// размеры
    private  int height;

    public SpriteAnimated( // конструктор
                           ImageView imageVie,
                           Duration duration, // анимация длительность
                           int count, int column,
                           int ofsetX, int ofsetY,
                           int width, int height
    )
    {
        this.imageView = imageVie;
        this.count=count;
        this.column=column;
        this.ofsetX=ofsetX;
        this.ofsetY=ofsetY;
        this.width=width;
        this.height=height;
        setCycleDuration(duration); // анимация, перелистывания спрайта
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR); // то что анимация будет линейной, без ускорений
        this.imageView.setViewport(new Rectangle2D(ofsetX, ofsetY, width, height));
    }

    public void setOfsetX(int x){ // откуда начинать анимацию по оси Х, но он не нужен (Т.Т), он потеряшка, но мы его оставим. Пусть будет талисманом на удачу =D
        this.ofsetX=x;
    }

    public void setOfsetY(int y){ // откуда начинать анимацию по оси Y
        this.ofsetY=y;
    }

    // наша анимация
    @Override
    protected void interpolate(double frac) {
        int index = Math.min((int)Math.floor(frac*count), count-1); // позиция, в зависимости от COUNT
        int x = (index % column) * width + ofsetX; // по Х
        int y = (index / column) * height + ofsetY; // по Y
        imageView.setViewport(new Rectangle2D(x,y,width,height)); // отрисовываем новый эл.
    }
}