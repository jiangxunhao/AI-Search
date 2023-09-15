import java.util.ArrayList;
import java.util.LinkedList;

abstract public class UninformedSearch extends GeneralSearch {
    LinkedList<Node> frontier;

    public UninformedSearch(int[][] map, Coord start, Coord goal) {
        super(map, start, goal);
        frontier = new LinkedList<>();
    }

    public Node makeNode(Node parentNode, Coord currentState, String action) {
        PathNode pathNode = new PathNode(currentState, action);

        ArrayList<PathNode> path = parentNode != null ? new ArrayList<>(parentNode.getPath()) : new ArrayList<>();
        path.add(pathNode);

        double pathCost = parentNode != null ? parentNode.getPathCost() + stepCost : 0;
        return new Node(currentState, path, pathCost);
    }

    abstract void add(Node node);

    abstract void expand(Node node);

    void initialUninformedSearch() {
        Node node = makeNode(null, start, "");
        add(node);
    }

    void expandSingle(Node node, int i) {
        Coord state = node.getState();
        int r = state.getR(), c = state.getC();
        if ( (isUpwards[r][c] && i == 3) || (!isUpwards[r][c] && i == 1) ) {
            return;
        }

        int nextR = r + tieBreaking[i][0], nextC = c + tieBreaking[i][1];
        String action = directions[i];

        if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < n && map[nextR][nextC] == 0 && !isVisited[nextR][nextC]) {
            Coord nextState = new Coord(nextR, nextC);
            Node nextNode = makeNode(node, nextState, action);
            add(nextNode);
        }
    }

    abstract void printFrontier();

}
