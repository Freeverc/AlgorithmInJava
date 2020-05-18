import java.time.format.TextStyle;
import java.util.ArrayList;

/*
    Feel free to change this file, but:
	!!!!!  ANY modification to this file WILL BE INGNORED  !!!!!
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<City> cities = TSPSolver.readFile("instances/RC11010.TXT");
        double totalDistance = TSPSolver.printSolution(cities);
        System.out.printf("Distances: %f\n", totalDistance);
        float[][] dist = TSPSolver.getDist(cities);
        System.out.printf("len : %d\n", cities.size());
        System.out.printf("size: %d %d\n", dist.length, dist[0].length);

        cities = TSPSolver.solveProblem(cities);
        totalDistance = TSPSolver.printSolution(cities);
        System.out.printf("Distances: %f\n", totalDistance);
		// Your program should not crash after running the code above!!!
		// It should print out a correct result
    }
}
