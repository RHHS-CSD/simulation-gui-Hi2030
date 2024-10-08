/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatastarter;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

/**
 * Predator and Prey Simulation GUI Engine
 * Controls the background functions/methods/running of the program
 * @author Haydn Yip
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
    
    //Signs signifying the predators, prey and food
    public String preySign = "@";
    public String predSign = "P";
    public String foodSign = "F";
    
    //Declare 2D array for grid
    String[][] globalGrid;
    
    //Public variables for the number of prey, predator and size of the grid
    public int inputPred, inputPrey, inputSize;
    
    //Constructor for the engine's object
    public SimulationGUIEngine(){
        //By default there will be 2 predators, 10 prey, and the grid size is 20
        inputPred = 2;
        inputPrey = 10;
        inputSize = 20;
        //Set grid size
        globalGrid = new String[inputSize][inputSize];
    }
    
    /**
     * //General method used to call on private methods that make the simulation 'move' or 'run'
     * @param predSign      The character used to represent a predator
     * @param preySign      The character used to represent a prey
     * @param foodSign      The character used to represent food
     */
    public void next (String predSign, String preySign, String foodSign){
        //Method that generates food on the grid
        generateFood(foodSign);
        //Method that allows predators to eat prey
        predation(predSign, preySign, foodSign);
        //Method that allows prey to eat food
        eatFood(predSign, preySign, foodSign);
        //Method that moves the predators and prey after all the eating has been done
        movement(predSign, preySign, foodSign);
        //Method to ensure that if a predator or prey moves out of bounds that they get deleted
        checkOutOfBounds();
    }
    
    //Makes sure there are no predators or prey that moved outside of the grid
    private void checkOutOfBounds(){
        //For all predators
        for(int i = 0; i < predRowPosition.size(); i++){
            //If a predator is outside of the grid then remove them
            if(predRowPosition.get(i) >= globalGrid.length || predColumnPosition.get(i) >= globalGrid.length){
                predRowPosition.remove(i);
                predColumnPosition.remove(i);
            } else if(predRowPosition.get(i) < 0 || predColumnPosition.get(i) < 0){
                predRowPosition.remove(i);
                predColumnPosition.remove(i);
            }
        }
        //If a prey is outside of the grid then remove them
        for(int j = 0; j < preyRowPosition.size(); j++){
            if(preyRowPosition.get(j) >= globalGrid.length || preyColumnPosition.get(j) >= globalGrid.length){
                preyRowPosition.remove(j);
                preyColumnPosition.remove(j);
            } else if(preyRowPosition.get(j) < 0 || preyColumnPosition.get(j) < 0){
                preyRowPosition.remove(j);
                preyColumnPosition.remove(j);
            }
        }
    }
    
    /**
     * Set initial random positions for predator and prey
     */
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
                //Otherwise add an empty space/print a dot
                globalGrid[i][j] = ".";
            }
        }
    }
    
    //Helper method to initially generate predators and prey at random spots in the grid
    private int generateInitialPredPrey(int row){
        int printPredPrey = 0;
        //Default chances of generating predator and prey before half way point of the grid
        if(row <= (globalGrid.length)/2){
            printPredPrey = generateChances(65,20);
        }
        
        //Ensures all predators and prey get printed by the end of the
        //grid through increasing chances of generating after half way of the grid
        //and before 3/4 of the grid
        if(row > (globalGrid.length)/2 && row <= 3*(globalGrid.length)/4){
            printPredPrey = generateChances(30,15);
        }
        
        //Ensures all predators and prey get printed by the end of the
        //grid through increasing chances of generating after 3/4 of the grid
        if(row > 3*(globalGrid.length)/4){
            printPredPrey = generateChances(5,2);
        }
        //Returns a number to state if a predator, prey or nothing gets added/created
        return printPredPrey;
    }
    
    //Helper method that further breaks down the method used to generate predator or prey in random positions
    private int generateChances (int predChance, int preyChance){
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
        //Returns a number to state if a predator, prey or nothing gets added/created
        return printPredPrey;
    }
    
    //Used to check if prey is near predator, and if so, the predator eats the prey and reproduces
    private void predation(String predSign, String preySign, String foodSign){
        //Declare variables
        Random rand = new Random();

        //For each predator
        for(int i = 0; i < predRowPosition.size(); i++){
            //For each prey
            for(int k = 0; k < preyRowPosition.size();k++){
                //If the predator is on top of the prey
                if(preyRowPosition.get(k) == predRowPosition.get(i) && preyColumnPosition.get(k) == predColumnPosition.get(i)){
                    //Remove the prey
                    preyRowPosition.remove(k);
                    preyColumnPosition.remove(k);
                    //1/5 chance for a predator to reproduce after eating food
                    if(rand.nextInt(5) == 1){
                        try{
                            predRowPosition.add(predRowPosition.get(i));
                            predColumnPosition.add(predColumnPosition.get(i));
                        } catch(ArrayIndexOutOfBoundsException e){

                        }
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
            predCheckSurroundings(predRowPosition.get(i), predColumnPosition.get(i), predSign, preySign, foodSign);
            //Create arraylist for each predator on possible ways it can move
            ArrayList<Integer> possibleDirections = new ArrayList<>();
            //Checks the spaces around the predator
            for(int k = 0; k < openSpaceArray.length; k++){
                //If there is an open space, add it to the possible directions that the predator can go to
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
            preyCheckSurroundings(preyRowPosition.get(j), preyColumnPosition.get(j), predSign, preySign, foodSign);
            //Create arraylist for each prey on possible ways it can move
            ArrayList<Integer> possibleDirections = new ArrayList<>();
            //Checks the open spaces around the prey
            for(int k = 0; k < openSpaceArray.length; k++){
                //If there is an open space, add it to the possible directions that the predator can go to
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
        //Declare variables
        Random r = new Random();
        
        //Iterate through each row
        for(int i = 0; i < globalGrid.length; i++){
            //Iterate through each column
            for(int j = 0; j < globalGrid[0].length;j++){
                //If there is an empty space
                if(globalGrid[i][j].equals(".")){
                    //There is a 1/250 or 0.004% chance of food appearing in an empty spot
                    if(r.nextInt(250)+1 == 1){
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
        //Declare variables
        Random rand = new Random();

        //For each prey
        for(int i = 0; i < preyRowPosition.size(); i++){
            //For each food
            for(int k = 0; k < foodRowPosition.size();k++){
                //If the prey is on top of the food
                if(foodRowPosition.get(k) == preyRowPosition.get(i) && foodColumnPosition.get(k) == preyColumnPosition.get(i)){
                    //Remove the food
                    foodRowPosition.remove(k);
                    foodColumnPosition.remove(k);
                    //1/5 chance for a prey to reproduce after eating food
                    if(rand.nextInt(5) == 1){
                        try{
                            preyRowPosition.add(preyRowPosition.get(i));
                            preyColumnPosition.add(preyColumnPosition.get(i));
                        } catch(ArrayIndexOutOfBoundsException e){

                        }
                    }
                }
            } 
        }
    }
    
    //Helper method to determine what is in the spaces around the selected positions
    //In this program, it is used to determine what is next to the prey
   private void preyCheckSurroundings(int rowPosition, int columnPosition, String predSign, String preySign, String foodSign){
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
        try{
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
                }
            }
        } 
        //A failsafe to ensure that if there is an index out of bounds, that the prey
        //doesn't go out of bounds
        catch(ArrayIndexOutOfBoundsException e){
            openSpaceArray[0] = false;
        }
    }

    //Helper method to determine what is in the spaces around the selected positions
    //In this program, it is used to determine what is next to the prey
    private void predCheckSurroundings(int rowPosition, int columnPosition, String predSign, String preySign, String foodSign){
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
        try{
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
                }
                //Check if there is food nearby
                else if((globalGrid[up][columnPosition]).equals(foodSign)){
                    openSpaceArray[0] = false;
                    nearFoodArray[0] = true;
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
                }
                //Check if there is food nearby
                else if((globalGrid[down][columnPosition]).equals(foodSign)){
                    openSpaceArray[1] = false;
                    nearFoodArray[1] = true;
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
                }
                //Check if there is food nearby
                else if((globalGrid[rowPosition][left]).equals(foodSign)){
                    openSpaceArray[2] = false;
                    nearFoodArray[2] = true;
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
                }
                //Check if there is food nearby
                else if((globalGrid[rowPosition][right]).equals(foodSign)){
                    openSpaceArray[3] = false;
                    nearFoodArray[3] = true;
                }
            }
        } 
        //A failsafe to ensure that if there is an index out of bounds, that the predator
        //doesn't go out of bounds
        catch(ArrayIndexOutOfBoundsException e){
            openSpaceArray[0] = false;
        }
    }

}