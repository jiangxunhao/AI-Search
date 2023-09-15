public class PathNode {
    private Coord state;
    private String action;

    public PathNode(Coord state, String action) {
        this.state = state;
        this.action = action;
    }

    public Coord getState() {
        return state;
    }

    public String getAction() {
        return action;
    }
}
