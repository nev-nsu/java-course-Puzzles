import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

interface CellUpdateListener {
    void cellIsUpdated(int X, int Y);
}

interface ValueUpdateListener {
    void valueIsUpdated();
}

class PuzzlesModel {

    private static final Random random = new Random();
    private final ArrayList<BufferedImage> images = new ArrayList<>();
    private final List<CellUpdateListener> cellUpdateListeners = new ArrayList<>();
    private final List<ValueUpdateListener> totalScoresUpdateListeners = new ArrayList<>();
    private final List<ValueUpdateListener> currentScoresUpdateListeners = new ArrayList<>();
    private final List<ValueUpdateListener> fieldSizeUpdateListeners = new ArrayList<>();
    private final List<ValueUpdateListener> winListeners = new ArrayList<>();
    private final String filename = "resources/";
    private final int count_of_pictures = 9;
    private final int usingPictures = 6;
    private final int image_size = 200;
    private Puzzle[][] cells;
    private int height = 0;
    private int width = 0;
    private int totalScores = 0;
    private int currentScores = 0;
    private int incorrect;

    PuzzlesModel() throws IOException {
        for (int i = 0; i < count_of_pictures; i++) {
            File file = new File(filename + i + ".jpg");
            FileInputStream in = new FileInputStream(file);
            images.add(ImageIO.read(in));
        }
    }

    void loadNewPuzzle(int height, int width) {
        Collections.shuffle(images);
        currentScores = usingPictures * height * width;
        this.height = height;
        this.width = width;
        incorrect = 0;
        for (ValueUpdateListener i : fieldSizeUpdateListeners)
            i.valueIsUpdated();
        cells = new Puzzle[height][width];
        int answer = random.nextInt(usingPictures);
        for (int x = 0; x < height; x++)
            for (int y = 0; y < width; y++)
                cells[x][y] = new Puzzle();
        for (int i = 0; i < usingPictures; i++) {

            int numbers_of_one;
            if (i == answer)
                numbers_of_one = height * width;
            else
                numbers_of_one = random.nextInt(height * width);
            ArrayList<Boolean> marks = new ArrayList<>();
            for (int j = 0; j < height * width; j++)
                marks.add(j < numbers_of_one);
            Collections.shuffle(marks);

            BufferedImage newImage = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(images.get(i), 0, 0, image_size, image_size, null);
            g.dispose();

            int chunkWidth = newImage.getWidth() / width;
            int chunkHeight = newImage.getHeight() / height;
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    if (marks.get(x * width + y)) {
                        BufferedImage chunk = new BufferedImage(chunkWidth, chunkHeight, newImage.getType());
                        Graphics2D gr = chunk.createGraphics();
                        gr.drawImage(newImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y,
                                chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                        gr.dispose();
                        cells[x][y].addImage(new ImageIcon(chunk), i == answer);
                    }
                }
            }
        }
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (!cells[x][y].isRightState())
                    incorrect++;
                for (CellUpdateListener i : cellUpdateListeners)
                    i.cellIsUpdated(x, y);
            }
        }
        if (incorrect == 0)
            loadNewPuzzle(height, width);
        for (ValueUpdateListener i : winListeners)
            i.valueIsUpdated();
    }

    int getTotalScores() {
        return totalScores;
    }

    private void setTotalScores(int value) {
        totalScores = value;
        for (ValueUpdateListener i : totalScoresUpdateListeners)
            i.valueIsUpdated();
    }

    int getCurrentLevelScores() {
        return currentScores;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    Puzzle getCell(int X, int Y) {
        return cells[X][Y];
    }

    void changeCellState(int X, int Y) {
        if (cells[X][Y].isRightState())
            incorrect++;
        cells[X][Y].changeState();
        if (cells[X][Y].isRightState())
            incorrect--;
        if (currentScores > 0)
            setCurrentScores(currentScores - 1);
        if (isWin()) {
            setTotalScores(totalScores + currentScores);
            setCurrentScores(0);
            for (ValueUpdateListener i : winListeners)
                i.valueIsUpdated();
        }
        for (CellUpdateListener i : cellUpdateListeners)
            i.cellIsUpdated(X, Y);
    }

    private void setCurrentScores(int value) {
        currentScores = value;
        for (ValueUpdateListener i : currentScoresUpdateListeners)
            i.valueIsUpdated();
    }

    boolean isWin() {
        return incorrect == 0;
    }

    void addCellUpdateListener(CellUpdateListener listener) {
        cellUpdateListeners.add(listener);
    }

    void addTotalScoresUpdateListener(ValueUpdateListener listener) {
        totalScoresUpdateListeners.add(listener);
    }

    void addCurrentScoresUpdateListener(ValueUpdateListener listener) {
        currentScoresUpdateListeners.add(listener);
    }

    void addFieldSizeUpdateListener(ValueUpdateListener listener) {
        fieldSizeUpdateListeners.add(listener);
    }

    void addWinListener(ValueUpdateListener listener) {
        winListeners.add(listener);
    }

}
