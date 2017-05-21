import javax.swing.*;
import java.util.ArrayList;

class Puzzle {

    private final ArrayList<ImageIcon> images = new ArrayList<>();
    private int currentState;
    private int rightState;

    void addImage(ImageIcon image, boolean isRight) {
        if (isRight)
            rightState = images.size();
        images.add(image);
    }

    void changeState() {
        currentState = (currentState + 1) % images.size();
    }

    boolean isRightState() {
        return currentState == rightState;
    }

    ImageIcon getImage() {
        return images.get(currentState);
    }

}
