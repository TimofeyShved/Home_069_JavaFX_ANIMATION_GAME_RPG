package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends Application {

    // <код кнопки которую мы нажали> и <активна> ли она?
    private HashMap<KeyCode, Boolean> Key = new HashMap<>();
    // список красных квадратов
    public static ArrayList<Rectangle> ArrayListRectangleS = new ArrayList<>();

    Image IMG = new Image(getClass().getResourceAsStream("sprite_man.png"));
    ImageView imageView = new ImageView(IMG); // передаём нашу картинку

    // создание игрока из картинки
    Character player = new Character(imageView);

    // наша панель
    static Pane root = new Pane();
    static final int rootWidth = 800;
    static final int rootHeight = 800;

    // ----------------------------------- рандомное появление краснах квадратов
    public  void  bonus(){
        int random = (int)Math.floor(Math.random()*100); // рандом
        int x = (int)Math.floor(Math.random()*rootWidth); // по оси x
        int y = (int)Math.floor(Math.random()*rootHeight); // по оси y
        if(random==5){ // создание квадрата
            Rectangle rectangle =new Rectangle(20,20,Color.RED);
            rectangle.setX(x);
            rectangle.setY(y); // его координаты
            ArrayListRectangleS.add(rectangle); // запихиваем в список
            Main.root.getChildren().addAll(rectangle); // добавляем на панель
        }
    }

    // ---------------------------------------------- Прорисовка движений
    public void update(){
        if (isPressed(KeyCode.UP)){ // при нажатии клавиши вверх
            player.animated.play(); // запускает анимацию
            player.animated.setOfsetY(190); // место оси по спрайту
            player.moveY(-5); // передвижение фигуры
        } else
        if (isPressed(KeyCode.DOWN)){
            player.animated.play();
            player.animated.setOfsetY(0);
            player.moveY(5);
        } else
        if (isPressed(KeyCode.RIGHT)){
            player.animated.play();
            player.animated.setOfsetY(285);
            player.moveX(5);
        } else
        if (isPressed(KeyCode.LEFT)){
            player.animated.play();
            player.animated.setOfsetY(95);
            player.moveX(-5);
        }
        else {
            player.animated.stop();
        }
    }

    // обработка нажатия на кнопку
    public boolean isPressed(KeyCode keyCode){
        return Key.getOrDefault(keyCode, false);
    }

    // ------------------------------------------------------- запуск программы ----------------------------------
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // добавление на сцены | на форму
        Scene scene = new Scene(root, rootWidth, rootHeight);

        // запись в список, нажатие на кнопки
        scene.setOnKeyPressed(event -> Key.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> { // и её отпускание
            Key.put(event.getCode(), false); // код и нажата ли она?
        });

        // -------------------------------------- запуск таймера
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(); // было вше - обновление наших действий
                bonus(); // появление красных квадратов
                primaryStage.setTitle("Game (Elfo vs Apple) "+player.score+" - "+ ArrayListRectangleS.size()); // заголовок формы
            }
        };
        timer.start();

        primaryStage.setScene(scene);  // размер формы и сцена
        primaryStage.show(); // отобразить
    }

    public static void main(String[] args) {
        launch(args);
    }
}
