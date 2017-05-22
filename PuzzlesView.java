import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

class PuzzlesView extends JFrame {
    private JPanel MainPanel;
    private JButton startNewGameButton;
    private JComboBox SizeComboBox;
    private JPanel GameField;
    private JLabel elements[][];
    private JPanel StartGamePanel;
    private JPanel ScoresPanel;
    private JLabel ScoresLabel;
    private JLabel RewardLabel;
    private JLabel winMark;

    PuzzlesView() {
        setContentPane(MainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 300);
        setLocation(300, 200);
        setResizable(false);
        setTitle("Puzzles");
        setVisible(true);
    }

    void resizeGameField(int x, int y) {
        GameField.removeAll();
        GridLayout grid = new GridLayout(x, y, 0, 0);
        GameField.setLayout(grid);
        elements = new JLabel[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++) {
                elements[i][j] = new JLabel();
                GameField.add(elements[i][j]);
            }
    }

    void updateLabelImage(int x, int y, ImageIcon newImage) {
        elements[x][y].setIcon(newImage);
    }

    void updateScoresLabel(int newValue) {
        ScoresLabel.setText(Integer.toString(newValue));
    }

    void updateRewardLabel(Integer newValue) {
        RewardLabel.setText(newValue.toString());
    }

    int getSizeValue() {
        return SizeComboBox.getSelectedIndex() + 2;
    }


    void setNewGameButtonHandler(ActionListener listener) {
        startNewGameButton.addActionListener(listener);
    }

    void setLeftClickHandler(int X, int Y, MouseListener listener) {
        elements[X][Y].addMouseListener(listener);
    }

    void updateWinMark(String newValue) {
        winMark.setText(newValue);
    }
}
