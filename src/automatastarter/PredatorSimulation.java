/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatastarter;

import java.util.*;

/**
 *
 * @author vince
 */
public class PredatorSimulation {

    /**
     * @param args the command line arguments
     */
        // get user input for grid dimensions, number of prey and number of predators
    int width;
    int height;
    int[][] grid;
    int numPreds;
    ArrayList<ArrayList<Integer>> predators;
    int numPreys;
    ArrayList<ArrayList<Integer>> preys;

//        fillGrid(grid, width, height, predators, preys);
//        printGrid(grid);
//
//        String in = s.nextLine();
//        while (!in.equals("x")) {
//            grid = movePrey(grid, preys);
//            grid = movePred(grid, preys, predators);
//            grid = preyRep(grid, preys);
//            grid = predRep(grid, predators);
//            grid = forestFire(grid, preys, predators);
//            printGrid(grid);
//            in = s.nextLine();
//        }

    public PredatorSimulation(int width, int height, int numPreds, int numPreys){
        this.width = width;
        this.height = height;
        this.numPreds = numPreds;
        this.numPreys = numPreys;
        grid = new int[width][height];
        predators = new ArrayList<>(numPreds);
        for (int i = 0; i < numPreds; i++){
            predators.add(new ArrayList());
        }
        preys = new ArrayList<>(numPreys);
        for (int i = 0; i < numPreys; i++){
            preys.add(new ArrayList());
        }
    }

    /**
     * fills the grid with predators and prey placed in random places and fills
     * in the remaining empty places predators are represented by 2, prey
     * represented by 1, and empty by 0
     *
     * @param grid the array to fill
     * @param width the width of the array
     * @param height the height of the array
     * @param predators the number of predators
     * @param preys the number of prey
     */
    public static void fillGrid(int[][] grid, int width, int height, ArrayList<ArrayList<Integer>> predators, ArrayList<ArrayList<Integer>> preys) {
        // fill the entire grid with "empty" spaces
        for (int i = 0; i < width; i++) {
            Arrays.fill(grid[i], 0);
        }
        // fill the grid with the number of predators, placed in random spaces
        for (int i = 0; i < predators.size(); i++) {
            int randHorizontal = new Random().nextInt(width);
            int randVertical = new Random().nextInt(height);
            // keep generating random spaces until the space is not occupied by another predator
            while (grid[randHorizontal][randVertical] != 0) {
                randHorizontal = new Random().nextInt(width);
                randVertical = new Random().nextInt(height);
            }
            grid[randHorizontal][randVertical] = 2;
            predators.get(i).add(randHorizontal);
            predators.get(i).add(randVertical);
            predators.get(i).add(0);
            predators.get(i).add(0);
        }

        // fill the grid with the number of prey, placed in random spaces
        for (int i = 0; i < preys.size(); i++) {
            int randHorizontal = new Random().nextInt(width);
            int randVertical = new Random().nextInt(height);
            // keep generating random spaces until the space is not occupied by another prey/predator
            while (grid[randHorizontal][randVertical] != 0) {
                randHorizontal = new Random().nextInt(width);
                randVertical = new Random().nextInt(height);
            }
            grid[randHorizontal][randVertical] = 1;
            preys.get(i).add(randHorizontal);
            preys.get(i).add(randVertical);
            preys.get(i).add(0);
        }
    }

    /**
     * moves prey randomly to any empty adjacent cell if prey cannot move, it
     * dies
     *
     * @param grid the grid in which they move around in
     * @return grid with the new locations of prey
     */
    public static int[][] movePrey(int[][] grid, ArrayList<ArrayList<Integer>> prey) {
        // move prey
        for (int i = 0; i < prey.size(); i++) {
            // only move prey if it's alive
            if (prey.get(i).get(0) >= 0) {
                prey.get(i).set(2, prey.get(i).get(2) + 1);
                int up = prey.get(i).get(0) - 1;
                int down = prey.get(i).get(0) + 1;
                int left = prey.get(i).get(1) - 1;
                int right = prey.get(i).get(1) + 1;
                boolean canUp = false;
                boolean canDown = false;
                boolean canLeft = false;
                boolean canRight = false;
                // check if prey can move to each adjacent cell
                if (up >= 0) {
                    if (grid[up][prey.get(i).get(1)] == 0) {
                        canUp = true;
                    }
                }
                if (down < grid.length) {
                    if (grid[down][prey.get(i).get(1)] == 0) {
                        canDown = true;
                    }
                }
                if (left >= 0) {
                    if (grid[prey.get(i).get(0)][left] == 0) {
                        canLeft = true;
                    }
                }
                if (right < grid[0].length) {
                    if (grid[prey.get(i).get(0)][right] == 0) {
                        canRight = true;
                    }
                }
                boolean movable = false;
                int random = -1;
                // generate a random number: 1 is up, 2 is down, 3 is left, 4 is right
                // if a number is generated and prey cannot move in that direction, generate until it can
                // only generate numbers if prey can move in at least one adjacent cell

                grid[prey.get(i).get(0)][prey.get(i).get(1)] = 0;
                if (canUp || canDown || canLeft || canRight) {
                    while (!movable) {
                        random = new Random().nextInt(4);
                        if (random == 0 && canUp) {
                            movable = true;
                            grid[up][prey.get(i).get(1)] = 1;
                            System.out.println("prey up from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                            prey.get(i).set(0, up);
                        } else if (random == 1 && canDown) {
                            movable = true;
                            grid[down][prey.get(i).get(1)] = 1;
                            System.out.println("prey down from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                            prey.get(i).set(0, down);
                        } else if (random == 2 && canLeft) {
                            movable = true;
                            grid[prey.get(i).get(0)][left] = 1;
                            System.out.println("prey left from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                            prey.get(i).set(1, left);
                        } else if (random == 3 && canRight) {
                            movable = true;
                            grid[prey.get(i).get(0)][right] = 1;
                            System.out.println("prey right from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                            prey.get(i).set(1, right);
                        }
                    }

                } else {
                    // prey dies
                    System.out.println("can't move");
                    prey.get(i).set(0, -1);
                    prey.get(i).set(1, -1);
                }
            }
        }
        return grid;
    }

    /**
     * moves predators to any adjacent empty cell or prey prioritizes prey over
     * empty cells if it eats prey, prey dies
     *
     * @param grid
     * @param prey
     * @param predator
     * @return the grid with new predator locations
     */
    public static int[][] movePred(int[][] grid, ArrayList<ArrayList<Integer>> prey, ArrayList<ArrayList<Integer>> predator) {
        // move predators
        for (int i = 0; i < predator.size(); i++) {
            // only move predator if it's alive
            if (predator.get(i).get(0) >= 0) {
                //increase the number of steps since last prey eaten
                predator.get(i).set(3, predator.get(i).get(3) + 1);

                int up = predator.get(i).get(0) - 1;
                int down = predator.get(i).get(0) + 1;
                int left = predator.get(i).get(1) - 1;
                int right = predator.get(i).get(1) + 1;
                boolean canUp = false;
                boolean canDown = false;
                boolean canLeft = false;
                boolean canRight = false;
                boolean preyUp = false;
                boolean preyDown = false;
                boolean preyLeft = false;
                boolean preyRight = false;

                // check if predator can move to each adjacent cell
                if (up >= 0) {
                    if (grid[up][predator.get(i).get(1)] == 1) {
                        preyUp = true;
                    } else if (grid[up][predator.get(i).get(1)] == 0) {
                        canUp = true;
                    }
                }
                if (down < grid.length) {
                    if (grid[down][predator.get(i).get(1)] == 1) {
                        preyDown = true;
                    } else if (grid[down][predator.get(i).get(1)] == 0) {
                        canDown = true;
                    }
                }
                if (left >= 0) {
                    if (grid[predator.get(i).get(0)][left] == 1) {
                        preyLeft = true;
                    } else if (grid[predator.get(i).get(0)][left] == 0) {
                        canLeft = true;
                    }
                }
                if (right < grid[0].length) {
                    if (grid[predator.get(i).get(0)][right] == 1) {
                        preyRight = true;
                    } else if (grid[predator.get(i).get(0)][right] == 0) {
                        canRight = true;
                    }
                }

                // if there is a prey in an adjacent cell, reset all booleans to false and only the prey cells to true
                if (preyUp || preyDown || preyLeft || preyRight) {
                    canUp = false;
                    canDown = false;
                    canLeft = false;
                    canRight = false;
                    if (preyUp) {
                        canUp = true;
                    }
                    if (preyDown) {
                        canDown = true;
                    }
                    if (preyLeft) {
                        canLeft = true;
                    }
                    if (preyRight) {
                        canRight = true;
                    }
                }
                boolean movable = false;
                int random = -1;
                // generate a random number: 1 is up, 2 is down, 3 is left, 4 is right
                // if a number is generated and predator cannot move in that direction, generate until it can
                // only generate numbers if predator can move in at least one adjacent cell
                if (canUp || canDown || canLeft || canRight) {
                    while (!movable) {
                        random = new Random().nextInt(4);
                        if (random == 0 && canUp) {
                            movable = true;
                            grid[predator.get(i).get(0)][predator.get(i).get(1)] = 0;
                            // if the cell contains a prey, eat the prey
                            if (preyUp) {
                                for (int j = 0; j < prey.size(); j++) {
                                    if (predator.get(i).get(0) - 1 == prey.get(j).get(0) && predator.get(i).get(1) == prey.get(j).get(1)) {
                                        System.out.println("eaten" + prey.get(j).get(0) + ", " + prey.get(j).get(1));
                                        prey.get(j).set(0, -1);
                                        prey.get(j).set(1, -1);
                                        predator.get(i).set(2, predator.get(i).get(2) + 1);
                                        predator.get(i).set(3, 0);
                                        break;
                                    }
                                }
                            }
                            grid[up][predator.get(i).get(1)] = 2;
                            System.out.println("pred up from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                            predator.get(i).set(0, up);
                        } else if (random == 1 && canDown) {
                            movable = true;
                            grid[predator.get(i).get(0)][predator.get(i).get(1)] = 0;
                            if (preyDown) {
                                for (int j = 0; j < prey.size(); j++) {
                                    if (predator.get(i).get(0) + 1 == prey.get(j).get(0) && predator.get(i).get(1) == prey.get(j).get(1)) {
                                        System.out.println("eaten" + prey.get(j).get(0) + ", " + prey.get(j).get(1));
                                        prey.get(j).set(0, -1);
                                        prey.get(j).set(1, -1);
                                        predator.get(i).set(2, predator.get(i).get(2) + 1);
                                        predator.get(i).set(3, 0);
                                        break;
                                    }
                                }
                            }
                            grid[down][predator.get(i).get(1)] = 2;
                            System.out.println("pred down from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                            predator.get(i).set(0, down);

                        } else if (random == 2 && canLeft) {
                            movable = true;
                            grid[predator.get(i).get(0)][predator.get(i).get(1)] = 0;
                            if (preyLeft) {
                                for (int j = 0; j < prey.size(); j++) {
                                    if (predator.get(i).get(0) == prey.get(j).get(0) && predator.get(i).get(1) - 1 == prey.get(j).get(1)) {
                                        System.out.println("eaten" + prey.get(j).get(0) + ", " + prey.get(j).get(1));
                                        prey.get(j).set(0, -1);
                                        prey.get(j).set(1, -1);
                                        predator.get(i).set(2, predator.get(i).get(2) + 1);
                                        predator.get(i).set(3, 0);
                                        break;
                                    }
                                }
                            }
                            grid[predator.get(i).get(0)][left] = 2;
                            System.out.println("pred left from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                            predator.get(i).set(1, left);

                        } else if (random == 3 && canRight) {
                            movable = true;
                            grid[predator.get(i).get(0)][predator.get(i).get(1)] = 0;
                            if (preyRight) {
                                for (int j = 0; j < prey.size(); j++) {
                                    if (predator.get(i).get(0) == prey.get(j).get(0) && predator.get(i).get(1) + 1 == prey.get(j).get(1)) {
                                        System.out.println("eaten" + prey.get(j).get(0) + ", " + prey.get(j).get(1));
                                        prey.get(j).set(0, -1);
                                        prey.get(j).set(1, -1);
                                        predator.get(i).set(2, predator.get(i).get(2) + 1);
                                        predator.get(i).set(3, 0);
                                        break;
                                    }
                                }
                            }
                            grid[predator.get(i).get(0)][right] = 2;
                            System.out.println("pred right from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                            predator.get(i).set(1, right);

                        }
                    }
                }
                // the predator dies if it hasn't eaten a prey in more than 20 steps
                if (predator.get(i).get(3) > 20){
                    predator.get(i).set(0, -1);
                    predator.get(i).set(1, -1);
                }
            }
        }
        return grid;
    }

    /**
     * reproduces prey if there is at least one empty adjacent cell around them
     * has at least a 5% chance to reproduce for every direction
     * @param grid  the grid in which the prey reproduce
     * @param prey  the arraylist of prey to reproduce
     * @return grid with reproduced prey
     */
    public static int[][] preyRep(int[][] grid, ArrayList<ArrayList<Integer>> prey) {
        int reproduced = 0;
        // loop through prey in the arraylist
        for (int i = 0; i < prey.size() - reproduced; i++) {
            // only go through if prey are alive
            if (prey.get(i).get(0) >= 0) {
                int up = prey.get(i).get(0) - 1;
                int down = prey.get(i).get(0) + 1;
                int left = prey.get(i).get(1) - 1;
                int right = prey.get(i).get(1) + 1;
                boolean canUp = false;
                boolean canDown = false;
                boolean canLeft = false;
                boolean canRight = false;
                // check if each direction is empty
                if (up >= 0) {
                    if (grid[up][prey.get(i).get(1)] == 0) {
                        canUp = true;

                    }
                }
                if (down < grid.length) {
                    if (grid[down][prey.get(i).get(1)] == 0) {
                        canDown = true;
                    }
                }
                if (left >= 0) {
                    if (grid[prey.get(i).get(0)][left] == 0) {
                        canLeft = true;
                    }
                }
                if (right < grid[0].length) {
                    if (grid[prey.get(i).get(0)][right] == 0) {
                        canRight = true;
                    }
                }
                int random = -1;
                // if there is at least one empty adjacent cell, generate a random number
                if (canUp || canDown || canLeft || canRight) {
                    random = new Random().nextInt(100);
                    // 5% chance to reproduce up, increases for every step alive
                    if (random < 5 + Math.min(prey.get(i).get(2), 20) && canUp) {
                        // add prey to arraylist as well as its location
                        // add new prey to grid
                        prey.add(new ArrayList<>());
                        prey.get(prey.size() - 1).add(up);
                        prey.get(prey.size() - 1).add(prey.get(i).get(1));
                        prey.get(prey.size() - 1).add(0);
                        grid[up][prey.get(i).get(1)] = 1;
                        System.out.println("prey rep up from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                        reproduced++;
                    } else if (random >= 5 + Math.min(prey.get(i).get(2), 20) && random < 10 + Math.min(prey.get(i).get(2) * 2, 40) && canDown){
                        prey.add(new ArrayList<>());
                        prey.get(prey.size() - 1).add(down);
                        prey.get(prey.size() - 1).add(prey.get(i).get(1));
                        prey.get(prey.size() - 1).add(0);
                        grid[down][prey.get(i).get(1)] = 1;
                        System.out.println("prey rep down from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                        reproduced++;
                    } else if (random >= 10 + Math.min(prey.get(i).get(2) * 2, 40) && random < 15 + Math.min(prey.get(i).get(2) * 3, 60) && canLeft){
                        prey.add(new ArrayList<>());
                        prey.get(prey.size() - 1).add(prey.get(i).get(0));
                        prey.get(prey.size() - 1).add(left);
                        prey.get(prey.size() - 1).add(0);
                        grid[prey.get(i).get(0)][left] = 1;
                        System.out.println("prey rep left from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                        reproduced++;
                    } else if (random >= 15 + Math.min(prey.get(i).get(2) * 3, 60)  && random < 20 + Math.min(prey.get(i).get(2) * 4, 80) && canRight) {
                        prey.add(new ArrayList<>());
                        prey.get(prey.size() - 1).add(prey.get(i).get(0));
                        prey.get(prey.size() - 1).add(right);
                        prey.get(prey.size() - 1).add(0);
                        grid[prey.get(i).get(0)][right] = 1;
                        System.out.println("prey rep right from " + prey.get(i).get(0) + ", " + prey.get(i).get(1));
                        reproduced++;
                    }
                }
            }
        }
        return grid;
    }
    
    /**
     * reproduces predators if there is at least one empty cell around them and the predator has eaten a prey in the last 10 steps
     * has a 2% chance to reproduce for every direction
     * @param grid  the grid in which predators are reproduced
     * @param predator  the arraylist of predators to reproduce
     * @return  the grid with newly reproduced predators
     */
    public static int[][] predRep(int[][] grid, ArrayList<ArrayList<Integer>> predator){
        // loop through predators in the arraylist
        for (int i = 0; i < predator.size(); i++){
            // only go through with the process if predators are alive and have eaten a prey in the last 10 steps
            if (predator.get(i).get(3) <= 10 && predator.get(i).get(2) > 0 && predator.get(i).get(0) >= 0){
                int up = predator.get(i).get(0) - 1;
                int down = predator.get(i).get(0) + 1;
                int left = predator.get(i).get(1) - 1;
                int right = predator.get(i).get(1) + 1;
                boolean canUp = false;
                boolean canDown = false;
                boolean canLeft = false;
                boolean canRight = false;
                // check if each direction is empty
                if (up >= 0) {
                    if (grid[up][predator.get(i).get(1)] == 0) {
                        canUp = true;
                    }
                }
                if (down < grid.length) {
                    if (grid[down][predator.get(i).get(1)] == 0) {
                        canDown = true;
                    }
                }
                if (left >= 0) {
                    if (grid[predator.get(i).get(0)][left] == 0) {
                        canLeft = true;
                    }
                }
                if (right < grid[0].length) {
                    if (grid[predator.get(i).get(0)][right] == 0) {
                        canRight = true;
                    }
                }
                int random = -1;
                if (canUp || canDown || canLeft || canRight){
                    random = new Random().nextInt(100);
                    if (random < 2 && canUp) {
                        // add predator to arraylist as well as its location
                        // add new predator to grid
                        predator.add(new ArrayList<>());
                        predator.get(predator.size() - 1).add(up);
                        predator.get(predator.size() - 1).add(predator.get(i).get(1));
                        predator.get(predator.size() - 1).add(0);
                        predator.get(predator.size() - 1).add(0);
                        grid[up][predator.get(i).get(1)] = 2;
                        System.out.println("pred rep up from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                    } else if (random >= 2 && random < 4 && canDown){
                        predator.add(new ArrayList<>());
                        predator.get(predator.size() - 1).add(down);
                        predator.get(predator.size() - 1).add(predator.get(i).get(1));
                        predator.get(predator.size() - 1).add(0);
                        predator.get(predator.size() - 1).add(0);
                        grid[down][predator.get(i).get(1)] = 2;
                        System.out.println("pred rep down from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                    } else if (random >= 4 && random < 6 && canLeft){
                        predator.add(new ArrayList<>());
                        predator.get(predator.size() - 1).add(predator.get(i).get(0));
                        predator.get(predator.size() - 1).add(left);
                        predator.get(predator.size() - 1).add(0);
                        predator.get(predator.size() - 1).add(0);
                        grid[predator.get(i).get(0)][left] = 2;
                        System.out.println("pred rep left from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                    } else if (random >= 6 && random < 8 && canRight) {
                        predator.add(new ArrayList<>());
                        predator.get(predator.size() - 1).add(predator.get(i).get(0));
                        predator.get(predator.size() - 1).add(right);
                        predator.get(predator.size() - 1).add(0);
                        predator.get(predator.size() - 1).add(0);
                        grid[predator.get(i).get(0)][right] = 2;
                        System.out.println("pred rep right from " + predator.get(i).get(0) + ", " + predator.get(i).get(1));
                    }
                }
            }
        }
        return grid;
    }
    
    /**
     * adds forest fires that occur at a low chance every step
     * if prey and predator are in the area of the forest fire, they die
     * @param grid  the grid in which the fires occur
     * @param prey  the prey arraylist that gets affected by the fires
     * @param predator  the predator arraylist that gets affected by the fires
     * @return 
     */
    public static int[][] forestFire(int[][] grid, ArrayList<ArrayList<Integer>> prey, ArrayList<ArrayList<Integer>> predator){
        // 5% chance to summon a forest fire, generate a random number, a random size, and a random location
        int random = new Random().nextInt(100);
        int activate = 5;
        // if the 5% is met, check if the prey and predator are in the same place as the forest fire
        if (random < activate){
            int size = new Random().nextInt(grid.length -2) + 1;
            int location = new Random().nextInt(grid.length-size);
            System.out.println("forest fire from " + location + ", " + location + " to " + (location+size) + ", " + (location+size));
            for (int i = location; i <= location + size; i++){
                for (int j = location; j <= location + size; j++){
                    for (int k = 0; k < prey.size(); k++){
                        // if prey is in the forest fire, kill it
                        if (i == prey.get(k).get(0) && j == prey.get(k).get(1)){
                            prey.get(k).set(0, -1);
                            prey.get(k).set(1, -1);
                            grid[i][j] = 0;
                        }
                    }
                    for (int k = 0; k < predator.size(); k++){
                        // if predator is in forest fire, kill it
                        if (i == predator.get(k).get(0) && j == predator.get(k).get(1)){
                            predator.get(k).set(0, -1);
                            predator.get(k).set(0, -1);
                            grid[i][j] = 0;
                        }
                    }
                }
            }
        }
        
        return grid;
    }
    
    /**
     * prints the grid
     *
     * @param grid the grid to print
     */
    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("");
        }
    }

}
