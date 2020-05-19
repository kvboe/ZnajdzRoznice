package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Klasa główna programu Znajdź różnicę!
 * @author Jakub Polejowski
 */
public class Main extends Application {
    /** Scena okna */
    private static Scene scene;

    /**
     * Metoda wyświetlająca stage oraz ustawiająca jego parametry, ustala wielkość stage na 1024x768 oraz nie pozwala na zmiane wielkości okna
     * @param primaryStage początkowy stage
     */
    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Znajdź różnicę!");

        scene = new Scene(Menu.GetMenu(),1024,768,Color.DARKSLATEGRAY);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Główna metoda
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metoda tworząca levele według ustalonych parametrów gry
     * @param scale ilość kwadratów na planszy np 5x5
     * @param changes ilość zmian w jednej z plansz
     * @param diff poziom trudności do późniejszego zapisu gry
     */
    public static void StartLevel(int scale, int changes, Menu.Difficulty diff){
        scene.setRoot(Level.start(scale, changes, diff));
    }

    /**
     * Metoda pozwalająca na powrót do menu gry
     */
    public static void GoToMenu(){
        scene.setRoot(Menu.GetMenu());
    }

    /**
     * Metoda przenosząca do scene z rankingiem
     */
    public static void ShowRanking() { scene.setRoot(Ranking.GetRanking());}
}
