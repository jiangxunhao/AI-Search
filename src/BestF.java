public class BestF extends InformedSearch {
    public BestF(int[][] map, Coord start, Coord goal) {
        super(map, start, goal);
    }

    public void initialInformedSearch() {
        Node node = makeNode(null, start, "", true);
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
            Node nextNode = makeNode(node, nextState, action,true);
            add(nextNode);
        }
    }
}
