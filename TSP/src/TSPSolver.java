import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TSPSolver {
    private static final float crossoverProbability = 0.9f;
    private static final float mutationProbability = 0.01f;
    private static final int populationSize = 50;
    private static final int maxGeneration = 1000;

    private static int mutationTimes;
    private static int currentGeneration;

    private static int cityNum;
    private static int[][] population;
    private static float[][] dist;

    private static int[] bestIndividual;
    private static float bestDist;
    private static int currentBestPosition;
    private static float currentBestDist;

    private static float[] sumDistances;
    private static double[] fitnessValues;
    private static double[] surival;

    private static Random ran;

    public static ArrayList<City> readFile(String filename) {
        ArrayList<City> cities = new ArrayList<>();
        File file = new File(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String [] s = tempString.split("\t");
                City c = new City(Integer.valueOf(s[0]), Double.valueOf(s[1]), Double.valueOf(s[2]));
                cities.add(c);
//                System.out.println(s[0] + "," + s[1] + ","  + s[2]);
//                System.out.println(c.num + ": " + c.x);
            }
            cityNum = cities.size();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        };
        return cities;
    }

    public static ArrayList<City> solveProblem(ArrayList<City> citiesToVisit) {
        dist = getDist(citiesToVisit);
        initGeneration();
        while (currentGeneration < maxGeneration) {
            nextGeneration();
            System.out.println("generation: " + currentGeneration+"  bestDistance: " + bestDist + "  current distance: " +currentBestDist);
        }
        int [] result = getBestIndividual();
        ArrayList<City> routine = new ArrayList<City>();
        for(int i = 0;i<dist.length;i++)
        {
            routine.add(citiesToVisit.get(result[i]));
        }
        return routine;
    }

    public static double printSolution(ArrayList<City> routine) {
        double totalDistance = 0.0;
        for(int i = 0;i < routine.size();i++)
        {
            System.out.print(routine.get(i).num + " -> ");
            totalDistance+=routine.get(i).distance(routine.get((i + 1) % routine.size()));
        }
        System.out.println(routine.get(0).num);
        return totalDistance;
    }

    public static float[][] getDist(ArrayList<City> cities) {
        float[][] dist = new float[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i; j < cities.size(); j++) {
                dist[i][j] = (float)cities.get(i).distance(cities.get(j));
                dist[j][i] = dist[i][j];
            }
        }
        return dist;
    }

    private static void initGeneration() {
        mutationTimes = 0;
        currentGeneration = 0;
        bestIndividual = null;
        bestDist = 0;
        currentBestPosition = 0;
        currentBestDist = 0;

        sumDistances = new float[populationSize];
        fitnessValues = new double[populationSize];
        surival = new double[populationSize];
        population = new int[populationSize][cityNum];

        for (int i = 0; i < populationSize; i++) {
            population[i] = getNewIndividual();
        }
        evaluateBestIndividual();
    }

    public static void nextGeneration() {
        currentGeneration++;
        selection();

        crossover();

        mutation();

        evaluateBestIndividual();
    }

    private static void selection() {
        int[][] parents = new int[populationSize][cityNum];

        parents[0] = population[currentBestPosition];
        parents[1] = exchangeMutate(bestIndividual.clone());
        parents[2] = insertMutate(bestIndividual.clone());
        parents[3] = bestIndividual.clone();

        setSurvival();
        for (int i = 4; i < populationSize; i++) {
            int j = Math.random() > surival[i] ? i : 0;
            parents[i] = population[j];
        }
        population = parents;
    }


    private static int[] exchangeMutate(int[] seq) {
        mutationTimes++;
        int m, n;
        do {
            m = random(seq.length - 2);
            n = random(seq.length);
        } while (m >= n);

        int j = (n - m + 1) >> 1;
        for (int i = 0; i < j; i++) {
            int tmp = seq[m + i];
            seq[m + i] = seq[n - i];
            seq[n - i] = tmp;
        }
        return seq;
    }


    private static int[] insertMutate(int[] seq) {
        mutationTimes++;
        int m, n;
        do {
            m = random(seq.length >> 1);
            n = random(seq.length);
        } while (m >= n);

        int[] s1 = Arrays.copyOfRange(seq, 0, m);
        int[] s2 = Arrays.copyOfRange(seq, m, n);

        for (int i = 0; i < m; i++) {
            seq[i + n - m] = s1[i];
        }
        for (int i = 0; i < n - m; i++) {
            seq[i] = s2[i];
        }
        return seq;
    }

    private static void crossover() {
        int[] queue = new int[populationSize];
        int num = 0;
        for (int i = 0; i < populationSize; i++) {
            if (Math.random() < crossoverProbability) {
                queue[num] = i;
                num++;
            }
        }
        queue = Arrays.copyOfRange(queue, 0, num);
        queue = shuffle(queue);
        for (int i = 0; i < num - 1; i += 2) {
            population[queue[i]] = getChild(queue[i], queue[i+1], 0);
            population[queue[i+1]] = getChild(queue[1], queue[i+1], 1);
        }
    }

    private static int[] getChild(int x, int y, int pos) {
        int[] solution = new int[cityNum];
        int[] px = population[x].clone();
        int[] py = population[y].clone();

        int dx = 0, dy = 0;
        int c = px[random(px.length)];
        solution[0] = c;

        for (int i = 1; i < cityNum; i++) {
            int posX = indexOf(px, c);
            int posY = indexOf(py, c);

            if (pos == 0) {
                dx = px[(posX + px.length - 1) % px.length];
                dy = py[(posY + py.length - 1) % py.length];
            } else if (pos == 1) {
                dx = px[(posX + px.length + 1) % px.length];
                dy = py[(posY + py.length + 1) % py.length];
            }

            for (int j = posX; j < px.length - 1; j++) {
                px[j] = px[j + 1];
            }
            px = Arrays.copyOfRange(px, 0, px.length - 1);
            for (int j = posY; j < py.length - 1; j++) {
                py[j] = py[j + 1];
            }
            py = Arrays.copyOfRange(py, 0, py.length - 1);

            c = dist[c][dx] < dist[c][dy] ? dx : dy;

            solution[i] = c;
        }
        return solution;
    }

    private static void mutation() {
        for (int i = 0; i < populationSize; i++) {
            if (Math.random() < mutationProbability) {
                if (Math.random() > 0.5) {
                    population[i] = insertMutate(population[i]);
                } else {
                    population[i] = exchangeMutate(population[i]);
                }
                if (Math.random() > 0.5) {
                    population[i] = insertMutate(population[i]);
                } else {
                    population[i] = exchangeMutate(population[i]);
                }
            }
        }
    }

    private static void evaluateBestIndividual() {
        for (int i = 0; i < population.length; i++) {
            sumDistances[i] = calculateIndividualDist(population[i]);
        }
        evaluateBestCurrentDist();
        if (bestDist == 0 || bestDist > currentBestDist) {
            bestDist = currentBestDist;
            bestIndividual = population[currentBestPosition].clone();
        }
    }

    public static float calculateIndividualDist(int[] indivial) {
        float sum = dist[indivial[0]][indivial[indivial.length - 1]];
        for (int i = 1; i < indivial.length; i++) {
            sum += dist[indivial[i]][indivial[i - 1]];
        }
        return sum;
    }

    private static void evaluateBestCurrentDist() {
        currentBestDist = sumDistances[0];
        currentBestPosition = 0;
        for (int i = 1; i < populationSize; i++) {
            if (sumDistances[i] < currentBestDist) {
                currentBestDist = sumDistances[i];
                currentBestPosition = i;
            }
        }
    }

    private static void setSurvival() {
        //set fitness
        for (int i = 0; i < sumDistances.length; i++) {
            fitnessValues[i] = 100.0 * cityNum / sumDistances[i];
        }
        //set the survival
        double min = fitnessValues[0], max = fitnessValues[0];
        for (int i = 0; i < fitnessValues.length; i++) {
            if(fitnessValues[i] < min)
                min = fitnessValues[i];
            if(fitnessValues[i] > max)
                max = fitnessValues[i];
        }
        for (int i = 0; i < surival.length; i++) {
            surival[i] = (fitnessValues[i] - min) / (max - min);
        }
    }

    private static int [] getNewIndividual() {
        int [] a = new int[cityNum];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        a = shuffle(a);
        return a;
    }

    private static int [] shuffle(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int p = random(a.length);
            int tmp = a[i];
            a[i] = a[p];
            a[p] = tmp;
        }
        for (int i = 0; i < a.length; i++) {
            int p = random(a.length);
            int tmp = a[i];
            a[i] = a[p];
            a[p] = tmp;
        }
        return a;
    }

    private static int random(int n) {
        if (ran == null) {
            ran = new Random();
        }
        return ran.nextInt(n);
    }

    private static int indexOf(int[] a, int index) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == index) {
                return i;
            }
        }
        return 0;
    }

    private static int[] getBestIndividual() {
        int[] best = new int[bestIndividual.length];
        int pos = indexOf(bestIndividual, 0);
        for (int i = 0; i < best.length; i++) {
            best[i] = bestIndividual[(i + pos) % bestIndividual.length];
        }
        return best;
    }
}
