
import java.util.*;

public class Solver {

    private static final int gridSize = 3;
    private static final String[][] grid = {{"A", "B", "C"},
                                            {"D", "E", "F"},
                                            {"G", "H", "I"}};
    private ArrayList<ArrayList<String>> routes;
    private String first;
    private String second;
    private String third;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String str1 = scan.next().toUpperCase();
        String str2 = scan.next().toUpperCase();
        String str3 = scan.next().toUpperCase();

        Solver solve = new Solver(str1, str2, str3);
        solve.DFSSolve(Grid.valueOf(str1).getRow(), Grid.valueOf(str1).getCol(), new ArrayList<String>(), 0);
        solve.printRoutes();
    }

    public Solver(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.routes = new ArrayList<ArrayList<String>>();
    }

    private void DFSSolve(int row, int col, ArrayList<String> currentRoute, int pointsAdded) {
        currentRoute.add(grid[row][col]);
        if (grid[row][col].equals(first) || grid[row][col].equals(second)) {
            pointsAdded++;
        }
        if (grid[row][col].equals(third)) {
            System.out.println("terminate route");
            printRoute(currentRoute);
            routes.add(currentRoute);
            return;
        }

        HashSet<String> sides = getSides(row, col);
        HashSet<String> corners = getCorners(row, col);
        HashSet<String> lCorners = getLCorners(row, col); 
        
        HashSet<String> successsor = new HashSet<String>();
        successsor.addAll(sides);
        successsor.addAll(corners);
        successsor.addAll(lCorners);

        if (pointsAdded < 2) {
            successsor.remove(third);
        }

        for (int i = 0; i < currentRoute.size(); i++) {
            if (successsor.contains(currentRoute.get(i))) {
                successsor.remove(currentRoute.get(i));
            }
        }

        for (String point : successsor) {
            System.out.println("loop");
            System.out.println(point);
            printRoute(currentRoute);
            DFSSolve(Grid.valueOf(point).getRow(), Grid.valueOf(point).getCol(), currentRoute, pointsAdded);
        }
    }
    
    private HashSet<String> getSides(int row, int col) {
        HashSet<String> sides = new HashSet<String>();
        // Up
        if(isInBounds(row - 1, col)) {
            sides.add(grid[row - 1][col]);
        }
        // Right
        if(isInBounds(row, col + 1)) {
            sides.add(grid[row][col + 1]);
        }
        // Down
        if(isInBounds(row + 1, col)) {
            sides.add(grid[row + 1][col]);
        }
        // Left
        if(isInBounds(row, col - 1)) {
            sides.add(grid[row][col - 1]);
        }
        return sides;
    }

    private HashSet<String> getCorners(int row, int col) {
        HashSet<String> corners = new HashSet<String>();
        // Up right
        if(isInBounds(row - 1, col + 1)) {
            corners.add(grid[row - 1][col + 1]);
        }
        // Down right
        if(isInBounds(row + 1, col + 1)) {
            corners.add(grid[row + 1][col + 1]);
        }
        // Down left
        if(isInBounds(row + 1, col - 1)) {
            corners.add(grid[row + 1][col - 1]);
        }
        // Up left
        if(isInBounds(row - 1, col - 1)) {
            corners.add(grid[row - 1][col - 1]);
        }
        return corners;
    }

    private HashSet<String> getLCorners(int row, int col) {
        HashSet<String> lCorners = new HashSet<String>();
        // Up up right
        if(isInBounds(row - 2, col + 1)) {
            lCorners.add(grid[row][col + 1]);
        }
        // Up right right
        if(isInBounds(row - 1, col + 2)) {
            lCorners.add(grid[row - 1][col + 2]);
        }
        // Down right right
        if(isInBounds(row + 1, col + 2)) {
            lCorners.add(grid[row + 1][col + 2]);
        }
        // Down down right
        if(isInBounds(row + 2, col + 1)) {
            lCorners.add(grid[row + 2][col + 1]);
        }
        // Down down left
        if(isInBounds(row + 2, col - 1)) {
            lCorners.add(grid[row + 2][col - 1]);
        }
        // Down left left
        if(isInBounds(row + 1, col - 2)) {
            lCorners.add(grid[row + 1][col - 2]);
        }
        // Up left left
        if(isInBounds(row - 1, col - 2)) {
            lCorners.add(grid[row - 1][col - 2]);
        }
        // Up up left
        if(isInBounds(row - 2, col - 1)) {
            lCorners.add(grid[row - 2][col - 1]);
        }
        return lCorners;
    }

    private void printRoutes() {
        for (int i = 0; i < routes.size(); i++) {
            for (int j = 0; j < routes.get(i).size(); j++) {
                System.out.print(routes.get(i).get(j));
            }
            System.out.println();
        }
        System.out.println(routes.size());
    }

    private void printRoute(ArrayList<String> route) {
        for (int i = 0; i < route.size(); i++){
            System.out.print(route.get(i));
        }
        System.out.println();
    }

    private boolean isInBounds(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }
        else if (row >= gridSize || col >= gridSize) {
            return false;
        }
        else {
            return true;
        }
    }
}
