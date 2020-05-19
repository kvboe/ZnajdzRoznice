package sample;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

/**
 * Klasa tworząca dwie plansze
 * @author Jakub Polejowski
 */
public class Level {
    /** zmienna opisująca stan gry */
    public static boolean isGameStarted = false;
    /** tworzenie lewej planszy */
    static Square[][] left;
    /** tworzenie prawej planszy */
    static Square[][] right;
    /** odwołanie do przenoszonego kwadratu */
    public static Square draggedSquare;
    /** zmienna opisująca czy zaistniała zmiana */
    private static int changed;
    /** nowy Text */
    private static Text txt = new Text();
    /** przycisk powrotu do menu */
    private static Button back = new Button("WRÓĆ");
    /** zmienna zawierająca czas startu rozgrywki */
    private static long time_start;
    /** odwołanie do poziomu trudności rozgrywki */
    private static Menu.Difficulty difficulty;

    /**
     * Konstruktor uzupełniający plansze kwadratami
     * @param scale wielkość przycisku
     * @param changes liczba zmian kolorów
     * @param diff trudność poziomu
     * @return zwraca obiekt typu Group
     */
    public static Group start(int scale, int changes, Menu.Difficulty diff){
        difficulty = diff;
        Group root = new Group();
        changed = changes;
        Square.setSize(400/scale);
        left = new Square[scale][scale];
        right = new Square[scale][scale];
        Random rnd = new Random();

        for ( int i=0; i<scale; i++){
            for (int j=0; j<scale; j++){
                Color clr = new Color(rnd.nextDouble(), rnd.nextDouble(),rnd.nextDouble(), 1);
                left[i][j] = new Square(clr, i, j,false);
                root.getChildren().add(left[i][j]);
                right[i][j] = new Square(clr, i, j,true);
                root.getChildren().add(right[i][j]);
            }
        }

        int i = 0;
        changes = Math.min(changes,scale^2); // idiotoochrona
        while ( i < changes ){
            int x = rnd.nextInt(scale),
                y = rnd.nextInt(scale);
            if (right[x][y].changed) continue;
            right[x][y].randomizeColor();
            i++;
        }

        txt.setText("Pozostało różnic: " + changed);
        txt.setX(340);
        txt.setY(100);
        txt.setFont(new Font(40));
        txt.setFill(Color.WHITE);
        root.getChildren().add(txt);

        back.setLayoutX(460);
        back.setLayoutY(650);
        back.setMinSize(100, 60);
        back.setFont(new Font(20));
        back.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> Main.GoToMenu()
        );
        back.setStyle("-fx-background-color: #d32f2f;-fx-background-radius: 10;-fx-border-color: #000000; -fx-border-width: 0px");
        root.getChildren().add(back);

        Level.isGameStarted = true;
        time_start = System.currentTimeMillis();

        return root;
    }

    /**
     * Metoda wyświetlająca czas gry po odnalezieniu wszystkich różnic
     */
    public static void changeFound(){
        if (--changed <= 0) {
            long time = System.currentTimeMillis() - time_start;
            txt.setText("Czas gry: " + formatTime(time));
            Ranking.AddRecord(Level.difficulty.toString(), time);
            Level.isGameStarted = false;
        }
        else
            txt.setText("Pozostało różnic: " + changed);
    }

    /**
     * Metoda formatująca czas na sekundy
     * @param time czas gry
     * @return zwraca czas w [s]
     */
    public static String formatTime(long time){
        time = time / 1000;
        String s = "";
        if (time >= 60) {
            s += time / 60;
            s += " min ";
        }
        if (time % 60 != 0) {
            s += time % 60;
            s += " s";
        }
        return s;
    }
}
