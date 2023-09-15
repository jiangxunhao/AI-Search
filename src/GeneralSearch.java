abstract public class GeneralSearch {
    static public final int[][] tieBreaking = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static public final String[] directions = {"Right", "Down", "Left", "Up"};
    static public final double stepCost = 1.0;
    int[][] map;
    int n;
    boolean[][] isUpwards;
    boolean[][] isVisited;
    Coord start;
    Coord goal;
    int numberOfExpandedNode;

    public GeneralSearch(int[][] map, Coord start, Coord goal) {
        this.map = map;
        n = map.length;
        isUpwards = new boolean[n][n];
        isVisited = new boolean[n][n];
        this.start = start;
        this.goal = goal;
        initial();
    }

    private void initial() {
        numberOfExpandedNode = 0;
        for (int i = 0; i < n; i++) {
            boolean isUpward;
            if (i % 2 == 0) {
                isUpward = true;
            } else {
                isUpward = false;
            }

            for(int j = 0; j < n; j++) {
                isVisited[i][j] = false;

                isUpwards[i][j] = isUpward;
                isUpward = !isUpward;
            }
        }
    }

    abstract public void run();

    public void printSuccess(Node node) {
        System.out.println(node.getPathState());
        System.out.println(node.getPathString());
        System.out.println(node.getPathCost());
        System.out.println(numberOfExpandedNode);
    }

    public void printFail() {
        System.out.println();
        System.out.println("fail");
        System.out.println(numberOfExpandedNode);
    }
}
