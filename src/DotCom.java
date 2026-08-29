public class DotCom {
    private BoardNode[] nodes;
    private LivingState state;
    private String name;

    public DotCom(DotComGame game, Position[] nodes, String name) {
        this.name = name;
        this.nodes = getGameNodes(game, nodes);
        this.state = LivingState.ALIVE;
    }

    private BoardNode[] getGameNodes(DotComGame game, Position[] nodePositions) {
        BoardNode[] myNodes = new BoardNode[nodePositions.length];

        for (int i = 0; i < nodePositions.length; i++) {
            Position curPos = nodePositions[i];
            myNodes[i] = game.getBoard()[curPos.x][curPos.y];

            myNodes[i].dotCom = this;
            myNodes[i].state = LivingState.ALIVE;
        }

        return myNodes;
    }

    // GETTERS

    public LivingState getState() {
        return this.state;
    }

    public String getName() {
        return this.name;
    }

    public BoardNode[] getNodes() {
        return this.nodes;
    }

    // SETTERS

    public void setState(LivingState state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodes(BoardNode[] nodes) {
        this.nodes = nodes;
    }

    // METHODS

    public void updateState() {
        int hits = 0;
        for (BoardNode node : nodes) {
            if (node.state != LivingState.ALIVE) {
                hits += 1;
            }
        }

        if (hits >= nodes.length) {
            this.state = LivingState.DEAD;
        } else if (hits > 0) {
            this.state = LivingState.WOUNDED;
        }
    }
}
