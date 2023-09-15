import java.util.ArrayList;

public class Node {
    private Coord state;
    private ArrayList<PathNode> path;
    private int depth;
    private double h_cost;
    private double f_cost;
    private double pathCost;

    /*
    Node for the uninformed search
     */
    public Node(Coord state, ArrayList<PathNode> path, double pathCost) {
        this.state = state;
        this.path = path;
        this.pathCost = pathCost;
    }

    /*
    Node for the informed search
    */
    public Node(Coord state, ArrayList<PathNode> path, int depth, double h_cost, double f_cost, double pathCost) {
        this(state, path, pathCost);
        this.depth = depth;
        this.h_cost = h_cost;
        this.f_cost = f_cost;
    }

    public Node(Coord state, ArrayList<PathNode> path, double pathCost, int depth) {
        this(state, path, pathCost);
        this.depth = depth;
    }

    public boolean isGoal(Coord goal) {
        return state.equals(goal);
    }

    public Coord getState() {
        return state;
    }

    public ArrayList<PathNode> getPath() {
        return path;
    }

    public PathNode getPathNode(int i) {
        if (i >= path.size()) {
            return null;
        }
        return path.get(i);
    }

    public String getPathState() {
        StringBuffer pathState = new StringBuffer();
        for (int i = 0; i < path.size(); i++) {
            PathNode pathNode = getPathNode(i);
            pathState.append(pathNode.getState().toString());
        }
        return new String(pathState.toString());
    }

    public String getPathString() {
        StringBuffer pathString = new StringBuffer();
        for (int i = 1; i < path.size(); i++) {
            PathNode pathNode = getPathNode(i);
            if (i == path.size() - 1) {
                pathString.append(pathNode.getAction());
                break;
            }
            pathString.append(pathNode.getAction() + " ");
        }
        return new String(pathString.toString());
    }

    public int getDepth() {
        return depth;
    }

    public double getH_cost() {
        return h_cost;
    }

    public double getF_cost() {
        return f_cost;
    }

    public double getPathCost() {
        return pathCost;
    }


    @Override
    public boolean equals(Object obj) {
        var node = (Node) obj;
        return this.getState().equals(node.getState());
    }
}
