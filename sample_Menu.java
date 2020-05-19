package sample;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Klasa tworząca menu gry
 * @author Jakub Polejowski
 */
public class Menu {
    enum Difficulty {EASY,NORMAL,HARD}

    /**
     * Metoda klasy Group wyświetlająca menu
     * @return zwraca obiekt root
     */
    public static Group GetMenu(){
        Group root = new Group();

        Text txt = new Text();
        txt.setText("Znajdź różnicę!");
        txt.setX(260);
        txt.setY(284);
        txt.setFont(new Font(80));
        txt.setFill(Color.WHITE);
        root.getChildren().add(txt);

        Button ranking = new Button("RANKING");
        ranking.setLayoutX(430);
        ranking.setLayoutY(650);
        ranking.setMinSize(160, 80);
        ranking.setFont(new Font(20));
        ranking.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> Main.ShowRanking()
        );
        ranking.setStyle("-fx-background-color: #ffc4ff;-fx-background-radius: 10;-fx-border-color: #000000; -fx-border-width: 0px");
        root.getChildren().add(ranking);

        for (Difficulty i : Difficulty.values()) {
            Button btn = new Button();
            btn.setText(""+i);
            int width = 1024/(Difficulty.values().length+1);
            btn.setMinWidth(width);
            btn.setMinHeight(768/4);
            btn.setLayoutX(width * (i.ordinal()+1) - width/2);
            btn.setLayoutY(768/2);
            btn.setFont(new Font(20));
            switch (i){
                case EASY: {
                    btn.setText("ŁATWY");
                    btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> Main.StartLevel(5,3, Difficulty.EASY));
                    btn.setStyle("-fx-background-color: #0f0;-fx-background-radius: 10;-fx-border-color: #000000; -fx-border-width: 0px");
                    break;
                }
                case NORMAL: {
                    btn.setText("NORMALNY");
                    btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> Main.StartLevel(10,6, Difficulty.NORMAL));
                    btn.setStyle("-fx-background-color: #ff0;-fx-background-radius: 10;-fx-border-color: #000000; -fx-border-width: 0px");
                    break;
                }
                case HARD: {
                    btn.setText("TRUDNY");
                    btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> Main.StartLevel(15,12, Difficulty.HARD));
                    btn.setStyle("-fx-background-color: #f00;-fx-background-radius: 10;-fx-border-color: #000000; -fx-border-width: 0px; -fx-spacing: 100px");
                    break;
                }
            }
            root.getChildren().add(btn);
        }
        return root;
    }
}
