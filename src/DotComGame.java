public class DotComGame {
    static int BOARD_SIZE = 7;
    static int DOTCOM_SIZE = 3;

    private BoardNode[][] board;
    private DotCom[] dotComs;

    DotComGame(int dotComCount) {
        this.board = new BoardNode[BOARD_SIZE][BOARD_SIZE];
        this.dotComs = new DotCom[dotComCount];

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new BoardNode();
            }
        }

        placeDotComsRandom();
    }

    public BoardNode[][] getBoard() {
        return this.board;
    }

    /**
     * Calculates all Positions that a DotCom with given parameters will be in.
     * @param startPos The Pivot-Point of the DotCom.
     * @param dir The Direction the DotCom is facing.
     * @param length The length of the DotCom.
     * @return All the Positions the DotCom will be inside of.
     */
    private Position[] calculatePositions(Position startPos, Direction dir, int length) {
        Position[] positions = new Position[length];
        for (int i = 0; i < length; i++) {
            Position curPos = new Position(
                    startPos.x + (dir.getDx() * i),
                    startPos.y + (dir.getDy() * i)
            );

            positions[i] = curPos;
        }

        return positions;
    }

    /**
     * Checks if nodes that the DotCom would be placed upon are LIVINGSTATE.EMPTY and if positions are inside of this.board.
     * @param nodePositions The Positions that the DotCom is inside.
     * @return True if every node is empty and all positions are valid; else false.
     */
    private boolean checkForSpace(Position[] nodePositions) {
        for (Position nodePos : nodePositions) {
            if (!(0 <= nodePos.x && nodePos.x < this.board.length &&
                    0 <= nodePos.y && nodePos.y < this.board[nodePos.x].length))
                return false;

            if (this.board[nodePos.x][nodePos.y].state != LivingState.EMPTY)
                return false;
        }

        return true;
    }

    /**
     * Calculates positions for DotCom's to be placed at and puts them on the Board.
     *
     * @throws IllegalStateException If there is no more room to place the next DotCom object.
     */
    public void placeDotComsRandom() {
        for (int i = 0; i < dotComs.length; i++) {
            boolean[][] tried = new boolean[BOARD_SIZE][BOARD_SIZE];
            int triedCount = 0;
            boolean notFound = true;
            int length = DOTCOM_SIZE; // optimized anyway - this is just for the
                                        // future to add variable DotCom lengths.
            while (notFound) { // search for available space
                Position pos = new Position(
                        (int) (Math.random() * BOARD_SIZE),
                        (int) (Math.random() * BOARD_SIZE));

                if (tried[pos.x][pos.y])
                    continue;

                tried[pos.x][pos.y] = true;
                triedCount++;

                Position[] positions = null;

                boolean found = false;
                for (Direction dir : Direction.values()) {
                    positions = calculatePositions(pos, dir, length);
                    if (checkForSpace(positions)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    dotComs[i] = new DotCom(
                            this,
                            positions,
                            "name.com");
                    notFound = false;
                }

                if (triedCount == BOARD_SIZE * BOARD_SIZE) {
                    throw new IllegalStateException("No available position found");
                }
            }
        }
    }
}
