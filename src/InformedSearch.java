import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

abstract public class InformedSearch extends GeneralSearch {
    PriorityQueue<Node> frontier;

    public InformedSearch(int[][] map, Coord start, Coord goal) {
        super(map, start, goal);
        frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                double o1_fCost = o1.getF_cost();
                double o2_fCost = o2.getF_cost();
                if (o1_fCost == o2_fCost) {
                    int o1_dir = getIndexOfDirection(o1);
                    int o2_dir = getIndexOfDirection(o2);
                    if (o1_dir == o2_dir) {
                        int o1_depth = o1.getDepth();
                        int o2_depth = o2.getDepth();
                        return o1_depth - o2_depth;
                    }
                    return o1_dir-o2_dir;
                } else if (o1_fCost > o2_fCost) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    abstract void initialInformedSearch();

    public void run() {
        initialInformedSearch();
        while(!frontier.isEmpty()) {
            printFrontier();
            Node node = frontier.poll();
            numberOfExpandedNode++;
            if(node.isGoal(goal)) {
                printSuccess(node);
                return;
            }
            expand(node);
        }
        printFail();
    }

    private void expand(Node node) {
        for(int i = 0; i < 4; i++) {
            expandSingle(node, i);
        }
    }

    abstract void expandSingle(Node node, int i);

    void add(Node node) {
        Coord state = node.getState();
        frontier.offer(node);
        isVisited[state.getR()][state.getC()] = true;
    }

    public Node makeNode(Node parentNode, Coord currentState, String action, boolean isBestF) {
        PathNode pathNode = new PathNode(currentState, action);

        ArrayList<PathNode> path = parentNode != null ? new ArrayList<>(parentNode.getPath()) : new ArrayList<>();
        path.add(pathNode);

        int depth = parentNode != null ? parentNode.getDepth() + 1 : 0;

        double h_cost = calculateManhattanDistance(currentState, goal);

        double pathCost = parentNode != null ? parentNode.getPathCost() + stepCost : 0;

        double f_cost = getH_cost(h_cost, pathCost, isBestF);

        return new Node(currentState, path, depth, h_cost, f_cost, pathCost);
    }

    public double getH_cost(double h_cost, double pathCost, boolean isBestF) {
        if(isBestF) {
            return h_cost;
        }else {
            return h_cost + pathCost;
        }
    }

    public double calculateManhattanDistance(Coord from, Coord to) {
        int from_R = from.getR(), from_C = from.getC();
        int from_Dir = isUpwards[from_R][from_C] ? 0: 1;
        int to_R = to.getR(), to_C = to.getC();
        int to_Dir = isUpwards[to_R][to_C] ? 0: 1;

        double from_a = -from_R, from_b = (from_R + from_C - from_Dir) / 2, from_c = ((from_R + from_C - from_Dir) / 2) - from_R + from_Dir;
        double to_a = -to_R, to_b = (to_R + to_C - to_Dir) / 2, to_c = ((to_R + to_C - to_Dir) / 2) - to_R + to_Dir;

        return Math.abs(from_a - to_a) + Math.abs(from_b - to_b) + Math.abs(from_c - to_c);
    }

    private int getIndexOfDirection(Node node) {
        ArrayList<PathNode> path = node.getPath();
        PathNode pathNode = path.get(path.size()-1);
        String direction = pathNode.getAction();
        for(int i = 0; i < 4; i++) {
            if(direction.equals(directions[i])) {
                return i;
            }
        }
        return -1;
    }

    public void printFrontier() {
        ArrayList<Node> temp = new ArrayList<>();
        System.out.print("[");
        while(!frontier.isEmpty()) {
            Node node = frontier.poll();
            temp.add(node);
            System.out.print(node.getState().toString() + ":" + node.getF_cost());
            if(!frontier.isEmpty()) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        for (int j = 0; j < temp.size(); j++) {
            frontier.add(temp.get(j));
        }
        System.out.println();
    }
}
