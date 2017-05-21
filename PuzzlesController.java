import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PuzzlesController {

    private final PuzzlesView theView;
    private final PuzzlesModel theModel;

    PuzzlesController(PuzzlesView theView, PuzzlesModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.theView.setNewGameButtonHandler(e -> startNewGame());
        this.theModel.addCellUpdateListener((X, Y) -> theView.updateLabelImage(X, Y, theModel.getCell(X, Y).getImage()));
        this.theModel.addFieldSizeUpdateListener(() -> theView.resizeGameField(theModel.getWidth(), theModel.getHeight()));

        this.theModel.addCurrentScoresUpdateListener(() -> {
            int value = theModel.getCurrentLevelScores();
            theView.updateTimerLabel(value);
        });
        this.theModel.addTotalScoresUpdateListener(() -> {
            int value = theModel.getTotalScores();
            theView.updateScoresLabel(value);
        });
        this.theModel.addWinListener(() -> {
            if (theModel.isWin())
                theView.updateWinMark("YOU WON!");
            else
                theView.updateWinMark("Game started");
        });
    }

    void startNewGame() {
        int newFieldSize = theView.getSizeValue();
        theModel.loadNewPuzzle(newFieldSize, newFieldSize);
        for (int i = 0; i < theModel.getHeight(); i++)
            for (int j = 0; j < theModel.getWidth(); j++)
                this.theView.setLeftClickHandler(i, j, new ClickListener(i, j));
    }

    private void onLeftClick(int X, int Y) {
        theModel.changeCellState(X, Y);
    }

    class ClickListener implements MouseListener {
        private final int x;
        private final int y;

        ClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            onLeftClick(x, y);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

