public class DFS extends UninformedSearch {
    /**
     * LinkedList as stack.
     */
    public DFS(int[][] map, Coord start, Coord goal) {
        super(map, start, goal);
    }

    public void run() {
        initialUninformedSearch();
        while(!frontier.isEmpty()) {
            printFrontier();
            Node node = frontier.pop();
            numberOfExpandedNode++;
            if (node.isGoal(goal)) {
                printSuccess(node);
                return;
            }
            expand(node);
        }
        printFail();
    }

    @Override
     void add(Node node) {
        Coord state = node.getState();
        frontier.push(node);
        isVisited[state.getR()][state.getC()] = true;
    }

    void expand(Node node) {
        for (int i = 3; i >= 0; i--) {
            expandSingle(node, i);
        }
    }

    @Override
    public void printFrontier() {
        System.out.print("[");
        int i;
        for (i = frontier.size() - 1; i >= 1 ; i--) {
            System.out.print(frontier.get(i).getState().toString());
            System.out.print(",");
        }
        System.out.print(frontier.get(i).getState().toString());
        System.out.print("]");
        System.out.println();
    }
}
