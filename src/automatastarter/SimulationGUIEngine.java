/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatastarter;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

/**
 * Predator and Prey Simulation GUI
 * @author haydnyip
 */
public class SimulationGUIEngine {
    //Declare global arrayLists for positions of predator, prey, and food         
    ArrayList<Integer> preyRowPosition = new ArrayList<>();
    ArrayList<Integer> predRowPosition = new ArrayList<>();
    ArrayList<Integer> preyColumnPosition = new ArrayList<>();
    ArrayList<Integer> predColumnPosition = new ArrayList<>();
    ArrayList<Integer> foodRowPosition = new ArrayList<>();
    ArrayList<Integer> foodColumnPosition = new ArrayList<>();
    
    //Declare arrays for positioning
    boolean[] openSpaceArray = new boolean[4];
    boolean[] nearPredArray = new boolean[4];
    boolean[] nearPreyArray = new boolean[4];
    boolean[] nearFoodArray = new boolean[4];
    
    public String preySign = "@";
    public String predSign = "P";
    public String foodSign = "F";
    
    //Declare 2D array for grid
    String[][] globalGrid;
    public int inputPred, inputPrey, inputSize;
    
    public SimulationGUIEngine(){
        inputPred = 2;
        inputPrey = 10;
        inputSize = 20;
        globalGrid = new String[inputSize][inputSize];
    }
    
    /**
     * Starts program
     */
    public void run(){
        //Initialize variables and grid based on size and number of predators and prey inputted
        
        boolean quit = false;
        Scanner kb = new Scanner(System.in);
        
        //Set initial random positions for predator and prey
        initialPositionSet();
        
        //Loop and keep getting user input until user decides to quit
        do{
            //Get user input for next frame or leave
            System.out.print("Next(n) or Quit(q)? ");
            String input = kb.nextLine().toLowerCase();
            //If user types 'n' to go to next frame, then call on the 'next' method
            if(input.equals("n")){
                next(predSign, preySign, foodSign);
            }
            //If user inputs anything but 'n' for next
            else{
                //If user chooses to quit, exit program
                if(input.equals("q")){
                    quit = true;
                }
                //If bad input, ask user again 
                else{
                    System.out.println("Bad Input...");
                }
            } 
        } while(!quit);
        //Once loop is exited, then end program
        System.exit(0);
    }
    
    /**
     * //General method used to call on private methods that make the simulation 'move' or 'run'
     * @param predSign      The character used to represent a predator
     * @param preySign      The character used to represent a prey
     * @param foodSign      The character used to represent food
     */
    public void next (String predSign, String preySign, String foodSign){
        generateFood(foodSign);
        //predation(predSign, preySign, foodSign);
        //eatFood(predSign, preySign, foodSign);
        //reproduction(predSign, preySign, foodSign);
        movement(predSign, preySign, foodSign);
    }
    
    private void predDeath(){
        
    }
    
    private void preyDeath(){
        
    }
    
    
    ////Set initial random positions for predator and prey
    public void initialPositionSet(){    
        //Declare variables
        int pred = inputPred;
        int prey = inputPrey;
        
        //Goes through each row in the grid 
        for(int i = 0; i < globalGrid.length; i++){
            //Goes through each column in the grid
            for(int j = 0; j < globalGrid[0].length; j++){
                //If not all predators have been generated, and when the helper function called returns a predator
                if(pred!= 0 && generateInitialPredPrey(i) == 1){
                    //Add a predator to the grid
                    globalGrid[i][j] = predSign;
                    //Add the position of the predator to the arrays, stating its row and column
                    predRowPosition.add(i);
                    predColumnPosition.add(j);
                    //Subtract number of predators that still need to be added
                    pred -= 1;
                    continue;
                } 
                //If not all prey have been generated, and when the helper function called returns a prey
                else if(prey != 0 && generateInitialPredPrey(i) == -1){
                    //Add a prey to the grid
                    globalGrid[i][j] = preySign;
                    //Add the position of the prey to the arrays, stating its row and column
                    preyRowPosition.add(i);
                    preyColumnPosition.add(j);
                    //Subtract number of prey that still need to be added
                    prey -= 1;
                    continue;
                }
                //Otherwise print an empty space or a dot
                globalGrid[i][j] = ".";
            }
        }
    }
    
    //Helper method to initially generate predators and prey at random spots in the grid
    private int generateInitialPredPrey(int row){
        int printPredPrey = 0;
        //Default chances of generating predator and prey before half way point of the grid
        if(row <= (globalGrid.length)/2){
            printPredPrey = generateHelper(65,30);
        }
        
        //Ensures all predators and prey get printed by the end of the
        //grid through increasing chances of generating after half way of the grid
        //and before 3/4 of the grid
        if(row > (globalGrid.length)/2 && row <= 3*(globalGrid.length)/4){
            printPredPrey = generateHelper(30,15);
        }
        
        //Ensures all predators and prey get printed by the end of the
        //grid through increasing chances of generating after 3/4 of the grid
        if(row > 3*(globalGrid.length)/4){
            printPredPrey = generateHelper(5,5);
        }
        return printPredPrey;
    }
    
    //Helper method that further breaks down the method used to generate predator or prey in random positions
    private int generateHelper (int predChance, int preyChance){
        //Declare variables
        Random rand = new Random();
        int printPredPrey = 0;
        //Randomly choose between predator and prey
        int choose = rand.nextInt(2)+1;
        
        //If the random number is 1, randomly generate a predator
        if(choose == 1){
            //If predator doesn't get generated, return to initialPosition function and add an empty spot
            //and insert empty space
            if(rand.nextInt(predChance) + 1 == 1){
                //If predator does get generated, it will be added to the grid
                printPredPrey = 1;
            }
        } 
        //Otherwise randomly generate a prey
        else{
            //If prey doesn't get generated, return to initialPosition function and add an empty spot
            //and insert empty space
            if(rand.nextInt(preyChance) + 1 == 1){
                //If prey does get generated, it will be added to the grid
                printPredPrey = -1;
            }
        }
        return printPredPrey;
    }
    
    
    //Used to check if prey is near predator, and if so, the predator eats the prey and reproduces
    private void predation(String predSign, String preySign, String foodSign){
        //Declare arraylist of prey that needs to be removed
        ArrayList<Integer> preyRowRemove = new ArrayList<>();
        ArrayList<Integer> preyColumnRemove = new ArrayList<>();

        //For each predator
        for(int i = 0; i < predRowPosition.size(); i++){
            //Checks positions around the predator
            checkSurroundings(predRowPosition.get(i), predColumnPosition.get(i), predSign, preySign, foodSign);
            //Check if any prey are next to the predator
            for(int j = 0; j < nearPreyArray.length; j++){
                //If predator is next to prey, remove prey (predator kills prey before it can move away)
                //If predator is next to multiple prey, kill the above first, else below, else left, else the one on the right
                if(nearPreyArray[j] == true){
                    switch (j) {
                        case 1:
                            //Replaces the dead prey by reproducing and creating another predator
                            globalGrid[predRowPosition.get(i)-1][predColumnPosition.get(i)] = predSign;
                            predRowPosition.add(predRowPosition.get(i)-1);
                            predColumnPosition.add(predColumnPosition.get(i));
                            break;
                        case 2:
                            //Replaces the dead prey by reproducing and creating another predator
                            globalGrid[predRowPosition.get(i)+1][predColumnPosition.get(i)] = predSign;
                            predRowPosition.add(predRowPosition.get(i)+1);
                            predColumnPosition.add(predColumnPosition.get(i));
                            break;
                        case 3:
                            //Replaces the dead prey by reproducing and creating another predator
                            globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)-1] = predSign;
                            predRowPosition.add(predRowPosition.get(i));
                            predColumnPosition.add(predColumnPosition.get(i)-1);
                            break;
                        default:
                            //Replaces the dead prey by reproducing and creating another predator
                            globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)+1] = predSign;
                            predRowPosition.add(predRowPosition.get(i));
                            predColumnPosition.add(predColumnPosition.get(i)+1);
                            break;
                    }
                    //Removes the foods from the array outside of the iteration to prevent exception
                    preyRowPosition.removeAll(preyRowRemove);
                    preyColumnPosition.removeAll(preyColumnRemove);
                    break;
                }
            }
        }
    }
    
    //Used for prey to randomly reproduce if they have space next to them
    private void reproduction(String predSign, String preySign, String foodSign){
        Random r = new Random();
        //Iterate through each prey
        for(int i = 0; i < preyRowPosition.size(); i++){
            //Check the spaces around the prey
            checkSurroundings(preyRowPosition.get(i), preyColumnPosition.get(i), predSign, preySign, foodSign);
            //Iterate through the open spaces available
            for(int j = 0; j < openSpaceArray.length; j++){
                //Have 1% chance for prey to reproduce if empty space next to it
                int rand = r.nextInt(100)+1;
                //If there is an open space and if the 1% chance is achieved
                if(openSpaceArray[j] == true && rand == 1){
                    switch (j) {
                        //If the open space is above, then add a prey above the current one
                        case 1:
                            /*preyRowPosition.add(preyRowPosition.get(i)-1);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[preyRowPosition.get(i)-1][preyRowPosition.get(i)] = preySign;*/
                            
                            try{
                                preyRowPosition.add(preyRowPosition.get(i)-1);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[preyRowPosition.get(i)-1][preyRowPosition.get(i)] = preySign;
                            } catch (ArrayIndexOutOfBoundsException e){
                                preyRowPosition.add(0);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[0][preyRowPosition.get(i)] = preySign;
                            }
                            break;
                        //If the open space is below, then add a prey below the current one
                        case 2:
                           /* preyRowPosition.add(preyRowPosition.get(i)+1);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[preyRowPosition.get(i)+1][preyRowPosition.get(i)] = preySign;*/
                            try{
                                preyRowPosition.add(preyRowPosition.get(i)+1);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[preyRowPosition.get(i)+1][preyRowPosition.get(i)] = preySign;
                            } catch (ArrayIndexOutOfBoundsException e){
                                preyRowPosition.add(19);
                                preyColumnPosition.add(preyRowPosition.get(i));
                                globalGrid[19][preyRowPosition.get(i)] = preySign;
                            }
                            break;
                        //If the open space is to the left, then add a prey to the left of the current one
                        case 3:
                            /*preyRowPosition.add(preyRowPosition.get(i));
                            preyColumnPosition.add(preyRowPosition.get(i)-1);
                            globalGrid[preyRowPosition.get(i)][preyRowPosition.get(i)-1] = preySign;*/
                            try{
                                preyRowPosition.add(preyRowPosition.get(i));
                                preyColumnPosition.add(preyRowPosition.get(i)-1);
                                globalGrid[preyRowPosition.get(i)][preyRowPosition.get(i)-1] = preySign;
                            } catch (ArrayIndexOutOfBoundsException e){
                                preyRowPosition.add(preyRowPosition.get(i));
                                preyColumnPosition.add(0);
                                globalGrid[preyRowPosition.get(i)][0] = preySign;
                            }
                            break;
                        //If the open space is to the right, then add a prey to the right of the current one
                        case 4:
                            /*preyRowPosition.add(preyRowPosition.get(i));
                            preyColumnPosition.add(preyRowPosition.get(i)+1);
                            globalGrid[preyRowPosition.get(i)][preyRowPosition.get(i)+1] = preySign;*/
                            try{
                                preyRowPosition.add(preyRowPosition.get(i));
                                preyColumnPosition.add(preyRowPosition.get(i)+1);
                                globalGrid[preyRowPosition.get(i)][preyRowPosition.get(i)+1] = preySign;
                            } catch (ArrayIndexOutOfBoundsException e){
                                preyRowPosition.add(preyRowPosition.get(i));
                                preyColumnPosition.add(19);
                                globalGrid[preyRowPosition.get(i)][19] = preySign;
                            }
                            break;
                        default: break;
                    }
                                
                }
            }
        }
    }
    
    //Allows the predators and prey to move around the grid
    private void movement(String predSign, String preySign, String foodSign){
        //Declare variables
        int chosenDirection;
        Random r = new Random();    

        //For each predator
        for(int i = 0; i < predRowPosition.size(); i++){
            //Checks space around each predator
            checkSurroundings(predRowPosition.get(i), predColumnPosition.get(i), predSign, preySign, foodSign);
            ArrayList<Integer> possibleDirections = new ArrayList<>();
            //Checks the open spaces around the predator
            for(int k = 0; k < openSpaceArray.length; k++){
                if(openSpaceArray[k] == true){
                    possibleDirections.add(k);
                }
            }
            //Used to make sure the prey does not move if there is no where to go
            if(possibleDirections.isEmpty()){
                continue;
            } else {  
                //If there is an open space, then randomly choose a number/direction based on open spaces
                chosenDirection = possibleDirections.get(r.nextInt(possibleDirections.size()));
            }
            //Move up and replace the spot with an empty space
            if(chosenDirection == 0){
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = ".";
                predRowPosition.set(i,predRowPosition.get(i)-1);
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = predSign;
            } 
            //Move down and replace the spot with an empty space
            else if (chosenDirection == 1){
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = ".";
                predRowPosition.set(i,predRowPosition.get(i)+1);
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = predSign;
            } 
            //Move left and replace the spot with an empty space
            else if (chosenDirection == 2){                
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = ".";
                predColumnPosition.set(i,predColumnPosition.get(i)-1);
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = predSign;
            } 
            //Move right and replace the spot with an empty space
            else if (chosenDirection == 3){
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = ".";
                predColumnPosition.set(i,predColumnPosition.get(i)+1);
                globalGrid[predRowPosition.get(i)][predColumnPosition.get(i)] = predSign;
            }
        }
        
        //For each prey
        for(int j = 0; j < preyRowPosition.size(); j++){
            //Checks space around each prey
            checkSurroundings(preyRowPosition.get(j), preyColumnPosition.get(j), predSign, preySign, foodSign);
            ArrayList<Integer> possibleDirections = new ArrayList<>();
            //Checks the open spaces around the prey
            for(int k = 0; k < openSpaceArray.length; k++){
                if(openSpaceArray[k] == true){
                    possibleDirections.add(k);
                }
            }
            //Used to make sure the prey does not move if there is no where to go
            if(possibleDirections.isEmpty()){
                continue;
            } else {
                //If there is an open space, then randomly choose a number/direction based on open spaces
                chosenDirection = possibleDirections.get(r.nextInt(possibleDirections.size()));
            }
            //Move up and replace the spot with an empty space
            if(chosenDirection == 0){
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = ".";
                preyRowPosition.set(j,preyRowPosition.get(j)-1);
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = preySign;
            } 
            //Move down and replace the spot with an empty space
            else if (chosenDirection == 1){
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = ".";
                preyRowPosition.set(j,preyRowPosition.get(j)+1);
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = preySign;
            } 
            //Move left and replace the spot with an empty space
            else if (chosenDirection == 2){
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = ".";
                preyColumnPosition.set(j,preyColumnPosition.get(j)-1);
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = preySign;
            } 
            //Move right and replace the spot with an empty space
            else if (chosenDirection == 3){
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = ".";
                preyColumnPosition.set(j,preyColumnPosition.get(j)+1);
                globalGrid[preyRowPosition.get(j)][preyColumnPosition.get(j)] = preySign; 
            }
        }
    }
    
    //Method used to randomly generate food items for the prey to eat
    private void generateFood(String foodSign){
        Random r = new Random();
        //Iterate through each row
        for(int i = 0; i < globalGrid.length; i++){
            //Iterate through each column
            for(int j = 0; j < globalGrid[0].length;j++){
                //If there is an empty space
                if(globalGrid[i][j].equals(".")){
                    //There is a 1/500 or 0.002% chance of food appearing in an empty spot
                    if(r.nextInt(500)+1 == 1){
                        //If food does spawn, add its position to the arrays and add to the grid
                        globalGrid[i][j] = foodSign;
                        foodRowPosition.add(i);
                        foodColumnPosition.add(j);
                    }
                }
            }
        }
    }
    
    //Checks the spaces around every prey, and if there is food to eat
    private void eatFood(String predSign, String preySign, String foodSign){
        ArrayList<Integer> foodRowRemove = new ArrayList<>();
        ArrayList<Integer> foodColumnRemove = new ArrayList<>();

        //For each prey
        for(int i = 0; i < preyRowPosition.size(); i++){
            //Checks spaces around the prey
            checkSurroundings(preyRowPosition.get(i), preyColumnPosition.get(i), predSign, preySign, foodSign);
            //Check if any food are next to the prey
            for(int j = 0; j < nearPreyArray.length; j++){
                //If prey is next to food, eat/remove food
                //If prey is next to multiple food, eat the one above first, else below, else left, else the one on the right
                if(nearFoodArray[j] == true){
                    switch (j) {
                        case 1:
                            /*//For each food
                            for(int each: foodRowPosition){
                                //Remove the food from the grid
                                //Conditions are used to prevent removing the wrong food from the array after being eaten
                                if(foodRowPosition.indexOf(preyRowPosition.get(i)-1) == foodColumnPosition.indexOf(preyColumnPosition.get(i))){
                                    //Set the space where to food was, to empty
                                    globalGrid[preyRowPosition.get(i)-1][preyColumnPosition.get(i)] = ".";
                                    foodRowRemove.add(foodRowPosition.get(each));
                                    foodColumnRemove.add(foodColumnPosition.get(each));
                                }
                            }*/
                            globalGrid[preyRowPosition.get(i)-1][preyColumnPosition.get(i)] = ".";
                            break;
                        case 2:
                            /*for(int each: foodRowPosition){
                                //Remove the food from the grid
                                //Conditions are used to prevent removing the wrong food from the array after being eaten
                                if(foodRowPosition.indexOf(preyRowPosition.get(i)+1) == foodColumnPosition.indexOf(preyColumnPosition.get(i))){
                                    //Set the space where to food was, to empty
                                    globalGrid[preyRowPosition.get(i)+1][preyColumnPosition.get(i)] = ".";
                                    foodRowRemove.add(foodRowPosition.get(each));
                                    foodColumnRemove.add(foodColumnPosition.get(each));
                                }
                            }*/
                            globalGrid[preyRowPosition.get(i)+1][preyColumnPosition.get(i)] = ".";
                            break;
                        case 3:
                            /*for(int each: preyRowPosition){
                                //Remove the food from the grid
                                //Conditions are used to prevent removing the wrong food from the array after being eaten
                                if(foodRowPosition.indexOf(preyRowPosition.get(i)) == foodColumnPosition.indexOf(preyColumnPosition.get(i))-1){
                                    //Set the space where to food was, to empty
                                    globalGrid[preyRowPosition.get(i)][preyColumnPosition.get(i)-1] = ".";
                                    foodRowRemove.add(foodRowPosition.get(each));
                                    foodColumnRemove.add(foodColumnPosition.get(each));
                                }
                            }*/
                            globalGrid[preyRowPosition.get(i)][preyColumnPosition.get(i)-1] = ".";
                            break;
                        default:
                            /*for(int each: preyRowPosition){
                                //Remove the food from the grid
                                //Conditions are used to prevent removing the wrong food from the array after being eaten
                                if(foodRowPosition.indexOf(preyRowPosition.get(i)) == foodColumnPosition.indexOf(preyColumnPosition.get(i))+1){
                                    //Set the space where to food was, to empty
                                    globalGrid[preyRowPosition.get(i)][preyColumnPosition.get(i)+1] = ".";
                                    foodRowRemove.add(foodRowPosition.get(each));
                                    foodColumnRemove.add(foodColumnPosition.get(each));
                                }
                            }*/
                            globalGrid[preyRowPosition.get(i)][preyColumnPosition.get(i)+1] = ".";
                            break;
                    }
                    //Removes the foods from the array outside of the iteration to prevent exception
                    foodRowPosition.removeAll(foodRowRemove);
                    foodColumnPosition.removeAll(foodColumnRemove);
                    break;
                }
            }
        }
    }
    
    //Helper method to determine what is in the spaces around the selected positions
    //In this program, it is used to determine what is next to the predator or prey
    private void checkSurroundings(int rowPosition, int columnPosition, String predSign, String preySign, String foodSign){
        //Declare the positions around the predator/prey
        int up = rowPosition - 1;
        int down = rowPosition + 1;
        int left = columnPosition - 1;
        int right = columnPosition + 1;
        
        //Reset the arrays
        for(int i = 0; i < openSpaceArray.length; i++){
            openSpaceArray[i] = true;
            nearPredArray [i] = false;
            nearPreyArray [i] = false;
            nearFoodArray [i] = false;
        }
        
        //Check up for out of bounds
        if(up < 0){
            openSpaceArray[0] = false;
        } else {
            //Otherwise, check if there is a predator nearby
            if((globalGrid[up][columnPosition]).equals(predSign)){
                nearPredArray[0] = true;
                openSpaceArray[0] = false;
            }           
            //Check if there is a prey nearby
            else if((globalGrid[up][columnPosition]).equals(preySign)){
                nearPreyArray[0] = true;
                openSpaceArray[0] = false;
            }
            //Check if there is food nearby
            else if((globalGrid[up][columnPosition]).equals(foodSign)){
                nearFoodArray[0] = true;
                openSpaceArray[0] = false;
            }
        }
        //Check down for out of bounds
        if(down > globalGrid.length-1){
            openSpaceArray[1] = false;
        } else {
            //Otherwise, check if there is a predator nearby
            if((globalGrid[down][columnPosition]).equals(predSign)){
                nearPredArray[1] = true;
                openSpaceArray[1] = false;
            }             
            //Check if there is prey nearby
            else if((globalGrid[down][columnPosition]).equals(preySign)){
                nearPreyArray[1] = true;
                openSpaceArray[1] = false;
            }
            //Check if there is food nearby
            else if((globalGrid[down][columnPosition]).equals(foodSign)){
                nearFoodArray[1] = true;
                openSpaceArray[1] = false;
            }
        }
        //Check left for out of bounds
        if(left < 0){
            openSpaceArray[2] = false;
        } else{
            //Otherwise, check if there is a predator nearby
            if((globalGrid[rowPosition][left]).equals(predSign)){
                nearPredArray[2] = true;
                openSpaceArray[2] = false;
            }
            //Check if there is prey nearby
            else if((globalGrid[rowPosition][left]).equals(preySign)){
                nearPreyArray[2] = true;
                openSpaceArray[2] = false;
            }
            //Check if there is food nearby
            else if((globalGrid[rowPosition][left]).equals(foodSign)){
                nearFoodArray[2] = true;
                openSpaceArray[2] = false;
            }
        }
        //Check right for out of bounds
        if(right > globalGrid[0].length-1){
            openSpaceArray[3] = false;
        } else{
            //Otherwise, check if there is a predator nearby
            if((globalGrid[rowPosition][right]).equals(predSign)){
                nearPredArray[3] = true;
                openSpaceArray[3] = false;
            }            
            //Check if there is prey nearby
            else if((globalGrid[rowPosition][right]).equals(preySign)){
                nearPreyArray[3] = true;
                openSpaceArray[3] = false;
            }
            //Check if there is food nearby
            else if((globalGrid[rowPosition][right]).equals(foodSign)){
                nearFoodArray[3] = true;
                openSpaceArray[3] = false;
            }
        }
    }
    
    /**
     * The method used to output whatever is on the grid to the console
     * Prints every element in the 2D array called globalGrid
     */
    public void reprint(){
        //Iterates through each row
        for(int row = 0; row < globalGrid.length; row++){
            //Iterates through each column
            for(int column = 0; column < globalGrid[0].length; column++){
                //Print element stored at the specified index
                System.out.print(globalGrid[row][column]);
            }
            //Print new line/row when each column in a row is done
            System.out.println("");
        }
    }
}

