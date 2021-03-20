package sample;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Character extends Pane {

    ImageView imageView;
      int COUNT = 10; // сколько спрайтов возьмём
      int COLUMN = 10; // сколько колонок
      int OFSETX = 0;
      int OFSETY = 0; // откуда начинать
      int WIDTH = 90; // размеры
      int HEIGHT = 90;
      int score =0;

      Rectangle removeRectangle = null;
      SpriteAnimated animated;

      // -------------------------------------------------конструктор игрока
      public Character (ImageView imageView){
          this.imageView = imageView; // приняли картинку
          this.imageView.setViewport(new Rectangle2D(OFSETX, OFSETY, WIDTH, HEIGHT)); // прорисовываем, но не всю, а толкьо по заданным значениям

          // передаём наши данные в анимацию
          animated = new SpriteAnimated(
                  imageView,
                  Duration.millis(500), // анимация
                  COUNT, COLUMN,
                  OFSETX, OFSETY,
                  WIDTH, HEIGHT
          );

          Main.root.getChildren().addAll(imageView); // добавляем нашего игрока(картинку) на панель
      }

      //----------------------------------------------------------------------- Действия по оси X -----------------
    public void moveX(int x){ // сюда приходят данные из Main
        boolean move = x>0?true:false; // есть действие?
        for (int i= 0; i < Math.abs(x); i++){
            //в зависимости от действия, туда и перемещаем объект
            if(move) this.imageView.setTranslateX(this.imageView.getTranslateX()+1);
            else this.imageView.setTranslateX(this.imageView.getTranslateX()-1);

            // блокировка выхода за край панели
            if (this.imageView.getTranslateX()<0) this.imageView.setTranslateX(0);
            if (this.imageView.getTranslateX()>Main.rootWidth-WIDTH) this.imageView.setTranslateX(Main.rootWidth-WIDTH);

            isBonuseEat(); // рядом есть красный квадрат?
        }
    }

    //----------------------------------------------------------------------- Действия по оси Y -----------------
    public void moveY(int y){
        boolean move = y>0?true:false; // есть действие?
        for (int i= 0; i < Math.abs(y); i++){
            //в зависимости от действия, туда и перемещаем объект
            if(move) this.imageView.setTranslateY(this.imageView.getTranslateY()+1);
            else this.imageView.setTranslateY(this.imageView.getTranslateY()-1);

            // блокировка выхода за край панели
            if (this.imageView.getTranslateY()<0) this.imageView.setTranslateY(0);
            if (this.imageView.getTranslateY()>Main.rootHeight-HEIGHT) this.imageView.setTranslateY(Main.rootHeight-HEIGHT);

            isBonuseEat(); // рядом есть красный квадрат?
        }
    }

    //----------------------------------------------------------------------- Действия при возможном сталкновении с красным квадратом -----------------
    public void isBonuseEat(){ // действие, рядом есть красный квадрат?
          Main.ArrayListRectangleS.forEach((rectangle -> { // проходим по коллекции из квадратов
              if (this.imageView.getBoundsInParent().intersects(rectangle.getBoundsInParent())){ // если наша картинка соприкасается размерами с квадратом, то
                  removeRectangle = rectangle; // добавляем квадрат на удаление
                  score++; // прибавляем счёт
              }
          }));
          Main.ArrayListRectangleS.remove(removeRectangle); // удаляем его из коллекции
          Main.root.getChildren().remove(removeRectangle); // и с панели
    }

}
