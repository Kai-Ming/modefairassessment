
import java.util.*;

enum Grid {
    A (0, 0),
    B (0, 1),
    C (0, 2),
    D (1, 0),
    E (1, 1),
    F (1, 2),
    G (2, 0),
    H (2, 1),
    I (2, 2);   

    private final int row;
    private final int col;

    Grid(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

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
        System.out.println("Input an alphabet between A to I");

        Scanner scan = new Scanner(System.in);
        String str1 = scan.next().toUpperCase();
        validateInputRange(str1);
        String str2 = scan.next().toUpperCase();
        validateInputRange(str2);
        String str3 = scan.next().toUpperCase();
        validateInputRange(str3);
        scan.close();

        //Validates if the input is unique
        if (str1.equals(str3) || str1.equals(str2) || str2.equals(str3)) {
            throw new java.lang.RuntimeException("Please input unique values");
        }

        Solver solve = new Solver(str1, str2, str3);
        solve.DFSSolve(Grid.valueOf(str1).getRow(), Grid.valueOf(str1).getCol(), new ArrayList<String>(), 0, false);
        solve.printRoutes();
    }

    private static void validateInputRange(String input) {
        // Validates if the input is within the alphabet range
        try {
            Grid validate = Grid.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new java.lang.RuntimeException("Please input a value between A to I");
        }
    }

    public Solver(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.routes = new ArrayList<ArrayList<String>>();
    }

    private void DFSSolve(int row, int col, ArrayList<String> currentRoute, int pointsAdded, boolean centreAdded) {
        ArrayList<String> newCurrentRoute = new ArrayList<String>();
        for (int i = 0; i < currentRoute.size(); i++) {
            newCurrentRoute.add(currentRoute.get(i));
        }
        currentRoute = newCurrentRoute;
        currentRoute.add(grid[row][col]);

        if (grid[row][col].equals(first) || grid[row][col].equals(second)) {
            // Added another point
            pointsAdded++;
        }

        if (grid[row][col].equals(third)) {
            // Reached end of pattern
            routes.add(currentRoute);
            return;
        }

        if (col == 1 && row == 1) {
            // Added the centre point
            centreAdded = true;
        }

        // Get all possible points reachable from current point
        HashSet<String> sides = getSides(row, col);
        HashSet<String> diagonals = getCorners(row, col, 1);
        HashSet<String> lCorners = getLCorners(row, col); 
        HashSet<String> corners = getCorners(row, col, 2);
        
        // Add the possible points from current point
        HashSet<String> successsor = new HashSet<String>();
        successsor.addAll(sides);
        successsor.addAll(diagonals);
        successsor.addAll(lCorners);
        if (centreAdded) {
            successsor.addAll(corners);
        }

        // Remove the final point if all the others are not added yet
        if (pointsAdded < 2) {
            successsor.remove(third);
        }

        // Remove any visited points
        for (int i = 0; i < currentRoute.size(); i++) {
            if (successsor.contains(currentRoute.get(i))) {
                successsor.remove(currentRoute.get(i));
            }
        }

        // Perform the DFS
        for (String point : successsor) {
            boolean previousCentreAdded = centreAdded;
            DFSSolve(Grid.valueOf(point).getRow(), Grid.valueOf(point).getCol(), currentRoute, pointsAdded, previousCentreAdded);
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

    private HashSet<String> getCorners(int row, int col, int dist) {
        HashSet<String> corners = new HashSet<String>();
        // Up right
        if(isInBounds(row - dist, col + dist)) {
            corners.add(grid[row - dist][col + dist]);
        }
        // Down right
        if(isInBounds(row + dist, col + dist)) {
            corners.add(grid[row + dist][col + dist]);
        }
        // Down left
        if(isInBounds(row + dist, col - dist)) {
            corners.add(grid[row + dist][col - dist]);
        }
        // Up left
        if(isInBounds(row - dist, col - dist)) {
            corners.add(grid[row - dist][col - dist]);
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
            int routeNumber = i + 1;
            System.out.print("Pattern " + routeNumber + ": ");
            for (int j = 0; j < routes.get(i).size(); j++) {
                System.out.print(routes.get(i).get(j));
            }
            System.out.print(", ");
        }
        System.out.println();
        System.out.println("Number of patterns: " + routes.size());
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
