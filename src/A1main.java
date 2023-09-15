public class A1main {

	public static void main(String[] args) {
		Conf conf = Conf.valueOf(args[1]);
		runSearch(args[0],conf.getMap(),conf.getS(),conf.getG());

	}

	private static void runSearch(String algo, Map map, Coord start, Coord goal) {
		switch(algo) {
			case "BFS" -> { //run BFS
				BFS bfs = new BFS(map.getMap(), start, goal);
				bfs.run();
			}
			case "DFS" -> { //run DFS
				DFS dfs = new DFS(map.getMap(), start, goal);
				dfs.run();
			}
			case "BestF" -> { //run BestF
				BestF bestF = new BestF(map.getMap(), start, goal);
				bestF.run();
			}
			case "AStar" -> { //run AStar
				AStar aStar = new AStar(map.getMap(), start, goal);
				aStar.run();
			}
			case "Bidirectional" -> {
				Bidirectional bidirectional = new Bidirectional(map.getMap(), start, goal);
				bidirectional.run();
			}
		}
	}


	private static void printMap(Map m, Coord init, Coord goal) {

		int[][] map=m.getMap();

		System.out.println();
		int rows=map.length;
		int columns=map[0].length;

		//top row
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" "+c);
		}
		System.out.println();
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" -");
		}
		System.out.println();

		//print rows 
		for(int r=0;r<rows;r++) {
			boolean right;
			System.out.print(r+"|");
			if(r%2==0) { //even row, starts right [=starts left & flip right]
				right=false;
			}else { //odd row, starts left [=starts right & flip left]
				right=true;
			}
			for(int c=0;c<columns;c++) {
				System.out.print(flip(right));
				if(isCoord(init,r,c)) {
					System.out.print("S");
				}else {
					if(isCoord(goal,r,c)) {
						System.out.print("G");
					}else {
						if(map[r][c]==0){
							System.out.print(".");
						}else{
							System.out.print(map[r][c]);
						}
					}
				}
				right=!right;
			}
			System.out.println(flip(right));
		}
		System.out.println();


	}

	private static boolean isCoord(Coord coord, int r, int c) {
		//check if coordinates are the same as current (r,c)
		if(coord.getR()==r && coord.getC()==c) {
			return true;
		}
		return false;
	}



	public static String flip(boolean right) {
        //prints triangle edges
		if(right) {
			return "\\"; //right return left
		}else {
			return "/"; //left return right
		}

	}

}
