public class BoardNode {
    DotCom dotCom;
    LivingState state;

    BoardNode() {
        this.dotCom = null;
        this.state = LivingState.EMPTY;
    }

    public HitInfo hit() {
        if (this.dotCom == null)
            return HitInfo.MISS;

        if (this.state == LivingState.DEAD)
            return HitInfo.REHIT;

        this.state = LivingState.DEAD;
        this.dotCom.updateState();

        return switch (this.dotCom.getState()) {
            case WOUNDED -> HitInfo.HIT;
            case DEAD -> HitInfo.SINKED;
            default -> throw new IllegalStateException("Unexpected state");
        };
    }
}
