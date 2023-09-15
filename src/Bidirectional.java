import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Bidirectional extends GeneralSearch {
    LinkedList<Node> forwardQueue;
    LinkedList<Node> visitedForward;
    LinkedList<Node> backwardQueue;
    LinkedList<Node> visitedBackward;


    public Bidirectional(int[][] map, Coord start, Coord goal) {
        super(map, start, goal);
        forwardQueue = new LinkedList<>();
        backwardQueue = new LinkedList<>();

        visitedForward = new LinkedList<>();
        visitedBackward = new LinkedList<>();
    }

    private void initialBidirectional() {
        Node nodeFromStart = makeNode(null, start, "");
        Node nodeFromGoal = makeNode(null, goal, "");

        add(nodeFromStart, forwardQueue, visitedForward);
        add(nodeFromGoal, backwardQueue, visitedBackward);
    }

    public void run() {
        initialBidirectional();
        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            printFrontier();
            var intersection = checkMeet();
            if (intersection != null) {
                printSuccess(intersection);
                return;
            }
            int forwardDepth = forwardQueue.peek().getDepth();
            int backwardDepth = backwardQueue.peek().getDepth();

            if (forwardDepth <= backwardDepth) {
                Node node = forwardQueue.poll();
                expand(node, forwardQueue, visitedForward);
            } else {
                Node node = backwardQueue.poll();
                expand(node, backwardQueue, visitedBackward);
            }
            numberOfExpandedNode++;

        }
        printFail();
    }

    public void expand(Node node, LinkedList<Node> queue, LinkedList<Node> visited) {
        for (int i = 0; i < 4; i++) {
            expandSingle(node, i, queue, visited);
        }
    }

    public void expandSingle(Node node, int i, LinkedList<Node> queue, LinkedList<Node> visited) {
        Coord state = node.getState();
        int r = state.getR(), c = state.getC();
        if ((isUpwards[r][c] && i == 3) || (!isUpwards[r][c] && i == 1)) {
            return;
        }

        int nextR = r + tieBreaking[i][0], nextC = c + tieBreaking[i][1];
        String action = directions[i];
        if (nextR >= 0 && nextR < n && nextC >= 0 && nextC < n && map[nextR][nextC] == 0) {
            Coord nextState = new Coord(nextR, nextC);
            Node nextNode = makeNode(node, nextState, action);
            if (!visited.contains(nextNode)) {
                add(nextNode, queue, visited);
            }
        }
    }

    private Node checkMeet() {
        Iterator<Node> iterator1 = visitedForward.iterator();
        while (iterator1.hasNext()) {
            Node node1 = iterator1.next();
            Iterator<Node> iterator2 = visitedBackward.iterator();
            while (iterator2.hasNext()) {
                Node node2 = iterator2.next();
                if (checkIsSame(node1, node2)) {
                    return node1;
                }
            }
        }
        return null;
    }

    private boolean checkIsSame(Node node1, Node node2) {
        Coord state1 = node1.getState();
        Coord state2 = node2.getState();
        return state1.equals(state2);
    }

    public Node makeNode(Node parentNode, Coord currentState, String action) {
        PathNode pathNode = new PathNode(currentState, action);

        ArrayList<PathNode> path = parentNode != null ? new ArrayList<>(parentNode.getPath()) : new ArrayList<>();
        path.add(pathNode);

        double pathCost = parentNode != null ? parentNode.getPathCost() + stepCost : 0.0;

        int depth = parentNode != null ? parentNode.getDepth() + 1 : 0;
        return new Node(currentState, path, pathCost, depth);
    }

    private void add(Node node, LinkedList<Node> queue, LinkedList<Node> visited) {
        queue.offer(node);
        visited.add(node);
    }

    public void printFrontier() {
        System.out.print("Forward Queue: [");
        for (int i = 0; i < forwardQueue.size(); i++) {
            System.out.print(forwardQueue.get(i).getState().toString());
            if (i != forwardQueue.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println();

        System.out.print("Backward Queue: [");
        for (int i = 0; i < backwardQueue.size(); i++) {
            System.out.print(backwardQueue.get(i).getState().toString());
            if (i != backwardQueue.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println();
    }

    public void printSuccess(Node intersectionNode) {
        var node1 = visitedForward.get(visitedForward.indexOf(intersectionNode));
        var node2 = visitedBackward.get(visitedBackward.indexOf(intersectionNode));
        System.out.println("Forward: ");
        System.out.println(node1.getPathState());
        System.out.println("Backward: ");
        System.out.println(node2.getPathState());
        System.out.println(node1.getPathCost() + node2.getPathCost());
        System.out.println(numberOfExpandedNode);
    }

    public void printFail() {
        System.out.println();
        System.out.println("fail");
        System.out.println(numberOfExpandedNode);
    }
}
