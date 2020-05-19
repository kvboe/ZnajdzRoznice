package sample;

import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Klasa tworząca poszczególne elementy plansz oraz zmieniająca kolory przycisków
 * dziedzicząca po klasie Button
 * @author Jakub Polejowski
 */
public class Square extends Button {
    /** wielkość przycisku */
    private static int s = 50;
    /** zmienna opisująca kolor kwadratu */
    private Color color;
    /** zmienna określająca czy zaszła zmiana koloru */
    public boolean changed = false;
    /** pozycja kwadratu */
    public int x,y;

    /**
     * Metoda ustalająca kolor kwadratu
     * @param c parametr opisujący kolor
     */
    public void setColor(Color c) {
        color = c;
        setStyle("-fx-background-color: "+toRGB(c)+";-fx-background-radius: 0;-fx-border-color: #000000; -fx-border-width: 1px");
    }

    /**
     * Kontruktor tworzący kolorowe przyciski
     * @param c color przycisku
     * @param x wspłółrzędna x
     * @param y współrzędna y
     * @param right zmienna sprawdzająca czy zmiana zachodzi na prawej planszy
     */
    public Square(Color c, int x, int y, boolean right){
        this.x = x;
        this.y = y;
        setColor(c);

        setMinSize(s, s);
        setLayoutX(87 + x*s + (right ? 450 : 0));
        setLayoutY(184+y*s);

        Square that = this;
        if (!right) {
            setOnDragDetected(event -> {
                if (!Level.isGameStarted) return;
                if (Level.draggedSquare == null) {
                    Level.draggedSquare = that;
                    Dragboard db = that.startDragAndDrop(TransferMode.ANY);
                      ClipboardContent content = new ClipboardContent();
                      content.putString("");
                      db.setContent(content);
                    that.setVisible(false);
                }
                event.consume();
            });
        }
        else {
            setOnDragOver(event -> {
                if (!Level.isGameStarted) return;
                if (Level.draggedSquare != null)
                    event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            });

            setOnDragDropped(e -> {
                boolean success = false;
                Square ds = Level.draggedSquare;
                if (ds != null && that.x == ds.x && that.y == ds.y && that.changed) {
                    setColor(ds.color);
                    changed = false;
                    Level.changeFound();
                    success = true;
                }
                ds.setVisible(true);
                Level.draggedSquare = null;
                e.setDropCompleted(success);
                e.consume();
            });

            setOnDragEntered(e -> {
                if (!Level.isGameStarted) return;
                setStyle("-fx-background-color: " + toRGB(that.color.brighter().brighter()) + ";-fx-background-radius: 0;-fx-border-color: #000000; -fx-border-width: 1px");
                e.consume();
            });

            setOnDragExited(e -> {
                setStyle("-fx-background-color: " + toRGB(that.color) + ";-fx-background-radius: 0;-fx-border-color: #000000; -fx-border-width: 1px");
                e.consume();
            });
        }
        setOnDragDone(e -> {
            Level.draggedSquare.setVisible(true);
            Level.draggedSquare = null;

            e.consume();
        });
    }

    /**
     * metoda zmieniająca kolor na wartości RGB
     * @param c wybrany kolor
     * @return zwraca kolor w wartości RGB
     */
    private String toRGB(Color c) {
        return "rgb("+c.getRed()*255+","+c.getGreen()*255+","+c.getBlue()*255+")";
    }

    /**
     * metoda ustalająca rozmiar przycisku
     * @param s wielkoś przycisku
     */
    public static void setSize( int s ){
        Square.s = s;
    }

    /**
     * metoda zmieniająca kolor na losowy
     */
    public void randomizeColor(){
        Random rnd = new Random();
        double r = color.getRed(), g = color.getGreen(),b = color.getBlue();
        r += (rnd.nextDouble()+.3)*(rnd.nextBoolean()?1:-1);
        g += (rnd.nextDouble()+.3)*(rnd.nextBoolean()?1:-1);
        b += (rnd.nextDouble()+.3)*(rnd.nextBoolean()?1:-1);
        r = Math.max(0, Math.min(1, r));
        g = Math.max(0, Math.min(1, g));
        b = Math.max(0, Math.min(1, b));
        setColor( new Color(r,g,b,color.getOpacity()));
        changed = true;
    }

}
