/**
 * The FoodPyramid class represents the main way to run the ecological pyramid program with the ternary tree represented by OrganismTree and OrganismNode.
 *
 * @author Freddy Zhou
 *      Email: freddy.zhou@stonybrook.edu
 *      Stony Brook ID: 116580337
 *      HW #5 - Ecological Pyramid
 *      CSE 214
 *      Recitation Number: R04 | TA Names: Veronica Oreshko, Anuj Sureshbhai, Alex Zuzow
 */
import java.util.Scanner;

public class FoodPyramid{
    private OrganismTree tree; // The tree that will be used by the user.

    /**
     * Creates an instance of the FoodPyramid class with no arguments.
     * @custom.postcondition
     *  A FoodPyramid object will have been instantiated with the tree being instantiated as well.
     */
    public FoodPyramid(){
        tree = new OrganismTree();
    }

    /**
     * Used to run the main program to be able to operate the tree.
     * @param args - Not used.
     * @throws PositionNotAvailableException
     *  Thrown if a method call throws it.
     * @throws IsPlantException
     *  Thrown if a method call throws it.
     * @throws DietMismatchException
     *  Thrown if a method call throws it.
     * @throws IllegalArgumentException
     *  Thrown if a method call throws it.
     */
    public static void main(String[] args) throws PositionNotAvailableException, IsPlantException, DietMismatchException, IllegalArgumentException{
        Scanner cin = new Scanner(System.in);

        // Start the tree program out by asking for the root and constructs the tree.
        System.out.print("What is the name of the apex predator?: ");
        String apexPredator = cin.nextLine();
        System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
        String animalType = cin.nextLine();
        System.out.println();
        System.out.println("Constructing food pyramid. . .\n");

        // Creates a node to be placed in the tree.
        OrganismNode apexNode = new OrganismNode();
        apexNode.setName(apexPredator);
        if (animalType.equals("h")) {
            apexNode.setHerbivore(true);
            apexNode.setCarnivore(false);
            apexNode.setPlant(false);
        } else if (animalType.equals("c")){
            apexNode.setHerbivore(false);
            apexNode.setCarnivore(true);
            apexNode.setPlant(false);
        } else if (animalType.equals("o")){
            apexNode.setHerbivore(true);
            apexNode.setCarnivore(true);
            apexNode.setPlant(false);
        }

        // Creates the tree.
        OrganismTree programTree = new OrganismTree(apexNode);

        // Running of the program by displaying menu options and performing them.
        boolean quit = false;
        String menuInput = "";
        while(!quit){
            // Menu output
            System.out.println("Menu:\n\n(PC) - Create New Plant Child\n(AC) - Create New Animal Child\n(RC) - Remove Child\n(P) - Print Out Cursor’s Prey\n" +
                    "(C) - Print Out Food Chain\n(F) - Print Out Food Pyramid at Cursor\n(LP) - List All Plants Supporting Cursor\n(R) - Reset Cursor to Root\n" +
                    "(M) - Move Cursor to Child\n(Q) - Quit\n");

            System.out.print("Please select an option: ");
            menuInput = cin.nextLine().toLowerCase();
            System.out.println();

            // Menu option to create a new plant child.
            if (menuInput.equals("pc")){
                // Try-catch used to catch problems with program with the throws.
                try {
                    // Used to check if there is an available spot before displaying prompts to create a new plant child.
                    programTree.addPlantChild("test");
                    programTree.removeChild("test");

                    System.out.print("What is the name of the organism?: ");
                    String name = cin.nextLine().toLowerCase();
                    System.out.println();

                    // Adds plant child to the tree.
                    programTree.addPlantChild(name);

                    String firstLetterOfName = name.substring(0, 1);
                    firstLetterOfName = firstLetterOfName.toUpperCase();
                    name = firstLetterOfName + name.substring(1);
                    System.out.println(name + " has successfully been added as prey for the " + programTree.getCursor().getName().toLowerCase() + "!\n");
                } catch (DietMismatchException e){
                    System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator\n");
                } catch (IsPlantException e){
                    System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators\n");
                } catch (IllegalArgumentException e){
                    System.out.println("ERROR: This prey already exists for this predator\n");
                } catch (PositionNotAvailableException e){
                    System.out.println("ERROR: There is no more room for more prey for this predator\n");
                }
            }
            // Menu option to create a new animal child.
            if (menuInput.equals("ac")){
                // Try-catch used to catch throws and to prevent from shutting down program.
                try {
                    // Used to check for if there is an available spot.
                    programTree.addAnimalChild("test", false, false);
                    programTree.removeChild("test");

                    System.out.print("What is the name of the organism?: ");

                    String name = cin.nextLine().toLowerCase();

                    System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
                    String animalTypeForAdd = cin.nextLine().toLowerCase();
                    System.out.println();

                    if (animalTypeForAdd.equals("h"))
                        programTree.addAnimalChild(name, true, false);
                    else if (animalTypeForAdd.equals("c"))
                        programTree.addAnimalChild(name, false, true);
                    else if (animalTypeForAdd.equals("o"))
                        programTree.addAnimalChild(name, true, true);

                    System.out.println("A(n) " + name.toLowerCase() + " has successfully been added as prey for the " + programTree.getCursor().getName().toLowerCase() + "!\n");
                } catch (DietMismatchException e){
                    System.out.print("ERROR: This prey cannot be added as it does not match the diet of the predator\n");
                } catch (IllegalArgumentException e){
                    System.out.println("ERROR: This prey already exists for this predator\n");
                } catch (PositionNotAvailableException e){
                    System.out.println("ERROR: There is no more room for more prey for this predator\n");
                } catch (IsPlantException e){
                    System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators\n");
                }
            }
            // Menu option to remove a specific child from the tree.
            if (menuInput.equals("rc")){
                System.out.print("What is the name of the organism to be removed?: ");
                String animalToRemove = cin.nextLine().toLowerCase();

                programTree.removeChild(animalToRemove);

                System.out.println("A(n) " + animalToRemove + " has been successfully removed as prey for the " + programTree.getCursor().getName() + "!\n");
            }
            // Menu option to list the prey of the cursor.
            if (menuInput.equals("p")) {
                System.out.println(programTree.listPrey());
                System.out.println();
            }
            // Menu option to list the food chain from the root of the tree to the cursor.
            if (menuInput.equals("c")) {
                System.out.println(programTree.listFoodChain());
                System.out.println();
            }
            // Menu option to print the entire organism tree.
            if (menuInput.equals("f")) {
                programTree.printOrganismTree();
                System.out.println();
            }
            // Menu option to list all the plants after the cursor.
            if (menuInput.equals("lp")){
                System.out.println(programTree.listAllPlants());
                System.out.println();
            }
            // Menu option to reset the cursor to the root.
            if (menuInput.equals("r")) {
                programTree.cursorReset();
                System.out.println("Cursor successfully reset to root!\n");
            }
            // Menu option to move the cursor to a specific node.
            if (menuInput.equals("m")){
                System.out.print("Move to?: ");
                String moveCursorToAnimal = cin.nextLine();

                // Try-catch used to catch an exception to prevent it from shutting down program.
                try {
                    programTree.moveCursor(moveCursorToAnimal);

                    System.out.println();
                    System.out.println("Cursor successfully moved to " + moveCursorToAnimal + "!\n");
                } catch (IllegalArgumentException e){
                    System.out.println("ERROR: This prey does not exist for this predator\n");
                }
            }
            // Menu option to quit the program.
            if (menuInput.equals("q"))
                quit = true;
        }
    }
}
