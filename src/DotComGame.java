import java.util.Scanner;

public class DotComGame {
    static int BOARD_SIZE = 7;
    static int DOTCOM_SIZE = 3;

    private BoardNode[][] board;
    private DotCom[] dotComs;
    private String[] names;

    DotComGame(int dotComCount, String[] names) {
        this.board = new BoardNode[BOARD_SIZE][BOARD_SIZE];
        this.dotComs = new DotCom[dotComCount];
        this.names = names;

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new BoardNode();
            }
        }

        placeDotComsRandom();
    }

    // GETTERS

    public BoardNode[][] getBoard() {
        return this.board;
    }

    // MAIN

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DotComGame game = new DotComGame(3, new String[]{"name.com", "dotcom.com", "arcane.com"});
        int tries = 0;
        while (!game.hasWon()) {
            System.out.print("Enter target cell: ");
            String input = scanner.next();
            Position pos = new Position(input);
            HitInfo hitInfo = game.guess(pos);

            tries++;

            System.out.println(hitInfo.name());

            if (hitInfo == HitInfo.SINKED) {
                System.out.println("You sunk \"" + game.getBoard()[pos.x][pos.y].dotCom.getName() + "\".");
            }
        }

        System.out.println("You won! You finished in " + Integer.toString(tries) + " tries.");
    }

    // METHODS

    /**
     * Checks if an alive DotCom's still exist.
     * @return true if no living DotCom exists.
     */
    public boolean hasWon() {
        for (DotCom dotCom : this.dotComs) {
            if (dotCom.getState() != LivingState.DEAD) {
                return false;
            }
        }
        return true;
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
        for (int i = 0; i < this.dotComs.length; i++) {
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
                    this.dotComs[i] = new DotCom(
                            this,
                            positions,
                            this.names[i]);
                    notFound = false;
                }

                if (triedCount == BOARD_SIZE * BOARD_SIZE) {
                    throw new IllegalStateException("No available position found");
                }
            }
        }
    }

    public HitInfo guess(Position pos) {
        if (!(0 <= pos.x && pos.x < this.board.length &&
                0 <= pos.y && pos.y < this.board[pos.x].length))
            return HitInfo.MISS;
        return this.board[pos.x][pos.y].hit();
    }
}
