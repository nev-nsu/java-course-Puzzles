class Puzzles {

    public static void main(String[] args) {
        try {
            PuzzlesView view = new PuzzlesView();
            PuzzlesModel model = new PuzzlesModel();
            PuzzlesController controller = new PuzzlesController(view, model);
            controller.startNewGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
