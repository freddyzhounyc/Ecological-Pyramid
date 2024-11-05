/**
 * The OrganismTree class represents the tree that will contain the OrganismNodes and have the methods that perform different functions on the tree.
 *
 * @author Freddy Zhou
 */

public class OrganismTree{
    private OrganismNode root; // the reference to the root of the tree.
    private OrganismNode cursor; // reference to the cursor of the tree.

    /**
     * Creates an instance of OrganismTree with no arguments.
     * @custom.postcondition
     *  An object of OrganismTree is instantiated.
     */
    public OrganismTree(){
    }

    /**
     * Creates an instance of OrganismTree with an OrganismNode argument that represents the root of the tree.
     * @param apexPredator
     *  The root of the ternary tree of the ecological pyramid.
     * @custom.precondition
     *  apexPredator is of OrganismNode type.
     * @custom.postcondition
     *  An OrganismTree object is instantiated.
     */
    public OrganismTree(OrganismNode apexPredator){
        root = apexPredator;
        cursor = apexPredator;
    }

    /**
     * Resets the cursor to the root of the tree.
     * @custom.postcondition
     *  The cursor will be moved to the root of the tree.
     */
    public void cursorReset(){
        cursor = root;
    }

    /*
    public void moveCursor(String name, OrganismNode root) throws IllegalArgumentException{
        if (root.getName().equals(name)){
            cursor = root;
            return;
        }
        if (root.getLeft() != null)
            moveCursor(name, root.getLeft());
        if (root.getMiddle() != null)
            moveCursor(name, root.getMiddle());
        if (root.getRight() != null)
            moveCursor(name, root.getRight());

        if (!cursor.getName().equals(name))
            throw new IllegalArgumentException();
    }
     */

    /**
     * Moves the cursor to a specific node with a specific name provided in the parameter.
     * @param name
     *  The name of the organism to be moved to.
     * @throws IllegalArgumentException
     *  Thrown if the name is not in the tree.
     * @custom.precondition
     *  Name is of String type.
     * @custom.postcondition
     *  The cursor will be moved to the node with the name specified.
     */
    public void moveCursor(String name) throws IllegalArgumentException{
        OrganismNode answer = findNode(name, cursor);
        if (answer == null)
            throw new IllegalArgumentException();
        cursor = answer;
    }

    /**
     * Finds the node that has the target name string in a recursive manner.
     * @param target
     *  The name that is to be found in the tree.
     * @param node
     *  The node that is currently being evaluated.
     * @return
     *  Returns the result of the recursive call and the OrganismNode.
     * @custom.precondition
     *  Target is of string type.
     *  Node is of OrganismNode type.
     * @custom.postcondition
     *  The node with the target string will be returned.
     */
    public OrganismNode findNode(String target, OrganismNode node){
        if (node.getName().equals(target))
            return node;
        OrganismNode result;
        if (node.getLeft() != null)
            result = findNode(target, node.getLeft());
        else
            result = null;
        if (result == null) { // not in left subtree, so search middle
            if (node.getMiddle() != null)
                result = findNode(target, node.getMiddle());
            else
                result = null;
        }
        if (result == null){ // not in middle subtree, so search right
            if (node.getRight() != null)
                result = findNode(target, node.getRight());
            else
                result = null;
        }
        return result;
    }

    /**
     * Lists the prey of the cursor node.
     * @return
     *  Returns a string representation of the cursor's prey.
     * @throws IsPlantException
     *  Thrown if the cursor is a plant.
     * @custom.precondition
     *  The cursor is not a plant.
     * @custom.postcondition
     *  The string representation of the prey of the cursor will be returned.
     */
    public String listPrey() throws IsPlantException{
        if (cursor.isPlant())
            throw new IsPlantException();
        String result = cursor.getName() + " -> ";
        int animalCounter = 0;
        int counter = 0; // for commas
        if (cursor.getLeft() != null)
            animalCounter++;
        if (cursor.getMiddle() != null)
            animalCounter++;
        if (cursor.getRight() != null)
            animalCounter++;

        if (cursor.getLeft() != null && counter < animalCounter-1){
            result += cursor.getLeft().getName() + ", ";
            counter++;
        } else if (cursor.getLeft() != null && counter >= animalCounter-1){
            result += cursor.getLeft().getName();
            counter++;
        }
        if (cursor.getMiddle() != null && counter < animalCounter-1){
            result += cursor.getMiddle().getName() + ", ";
            counter++;
        } else if (cursor.getMiddle() != null && counter >= animalCounter-1){
            result += cursor.getMiddle().getName();
            counter++;
        }
        if (cursor.getRight() != null && counter < animalCounter-1){
            result += cursor.getRight().getName() + ", ";
            counter++;
        } else if (cursor.getRight() != null && counter >= animalCounter-1){
            result += cursor.getRight().getName();
            counter++;
        }
        return result;
    }

    /**
     * Lists the food chain from the root of the tree to the cursor node.
     * @return
     *  Returns the string representation of the food chain from root to cursor.
     * @custom.precondition
     *  The tree is not empty.
     * @custom.postcondition
     *  The string representation of the food chain from root to cursor will be returned.
     */
    public String listFoodChain(){
        return findPath(cursor.getName(), root);
    }

    /**
     * Finds the path of nodes that go from the root to the cursor.
     * @param target
     *  The target string that is to be carved a path to from the root.
     * @param node
     *  The node that is currently being assessed.
     * @return
     *  Returns the path in a string form from the root to that node.
     * @custom.precondition
     *  Target is of string type.
     *  Node is of OrganismNode type.
     * @custom.postcondition
     *  The path representation from root to the node with the target string is returned.
     */
    public String findPath(String target, OrganismNode node) {
        if (node.getName().equals(target))
            return node.getName();

        String leftStr = "";
        if (node.getLeft() != null){
            leftStr = findPath(target, node.getLeft());
            if (!leftStr.isEmpty())
                return node.getName() + " -> " + leftStr;
        } else
            leftStr = "";

        String middleStr = "";
        if (leftStr.isEmpty()){    // not in left subtree
            if (node.getMiddle() != null) {
                middleStr = findPath(target, node.getMiddle());
                if (!middleStr.isEmpty())
                    return node.getName() + " -> " + middleStr;
            } else
                middleStr = "";
        }

        String rightStr = "";
        if (middleStr.isEmpty()){    // not in middle subtree
            if (node.getRight() != null) {
                rightStr = findPath(target, node.getRight());
                if (!rightStr.isEmpty())
                    return node.getName() + " -> " + rightStr;
            } else
                rightStr = "";
        }

        return rightStr;
    }

    /**
     * Prints the whole organism tree in a specific format.
     * @custom.precondition
     *  The tree has nodes a part of it.
     * @custom.postcondition
     *  The tree will be printed out to the console.
     */
    public void printOrganismTree(){
        preorderTraverse(cursor, 0);
    }

    /**
     * Performs a preorder traversal on the tree.
     * @param root
     *  The root of the tree and when called recursively, the node being assessed.
     * @param indentCounter
     *  The counter that counts the indentation for each depth in the tree.
     * @custom.precondition
     *  Root is of OrganismNode type.
     *  IndentCounter is of int type.
     * @custom.postcondition
     *  The tree will be traversed in a preorder manner and the organism tree will be printed out to the console.
     */
    public void preorderTraverse(OrganismNode root, int indentCounter){
        if (!root.isPlant()){
            for (int i = 0; i < indentCounter; i++) {
                System.out.print("\t");
            }
            System.out.println("|- " + root.getName());
        } else{
            for (int i = 0; i < indentCounter; i++) {
                System.out.print("\t");
            }
            System.out.println("- " + root.getName());
        }

        if (root.getLeft() != null)
            preorderTraverse(root.getLeft(), indentCounter +1);
        if (root.getMiddle() != null)
            preorderTraverse(root.getMiddle(), indentCounter +1);
        if (root.getRight() != null)
            preorderTraverse(root.getRight(), indentCounter +1);
    }

    /**
     * Lists all the plants after the cursor.
     * @return
     *  Returns the string representation of all the plants after the cursor.
     * @custom.precondition
     *  Plant nodes are in the tree.
     * @custom.postcondition
     *  String representation of the plants after the cursor is returned.
     */
    public String listAllPlants(){
        String answer = findAllPlants(cursor);
        answer = answer.substring(0,answer.length()-2);
        return answer;
    }

    /**
     * Finds all the plants in a tree recursively and returns it in a string format.
     * @param node
     *  The current node that is being assessed.
     * @return
     *  Returns the string representation of all the plants after the initial given node.
     * @custom.precondition
     *  Node is of OrganismNode type.
     * @custom.postcondition
     *  The string representation of all the plants after the initial given node is returned.
     */
    public String findAllPlants(OrganismNode node){
        String result = "";
        if (node.isPlant())
            result += node.getName() + ", ";

        if (node.getLeft() != null)
            result += findAllPlants(node.getLeft());
        if (node.getMiddle() != null)
            result += findAllPlants(node.getMiddle());
        if (node.getRight() != null)
            result += findAllPlants(node.getRight());

        return result;
    }

    /**
     * Adds an animal node to the tree with the specified requirements after the cursor reference.
     * @param name
     *  The name of the animal node that is to be added to the tree.
     * @param isHerbivore
     *  Whether the animal node being added is a herbivore.
     * @param isCarnivore
     *  Whether the animal node being added is a carnivore.
     * @throws IllegalArgumentException
     *  Thrown when the animal already exists in the tree.
     * @throws PositionNotAvailableException
     *  Thrown if there are no available spots for this new animal node.
     * @throws DietMismatchException
     *  Thrown if the diet does not make sense with the addition of the new animal node.
     * @throws IsPlantException
     *  Thrown if the cursor is a plant.
     * @custom.precondition
     *  The animal does not already exist.
     *  There is an open spot.
     *  The diet will make sense after the addition of the animal.
     *  The cursor is not a plant.
     * @custom.postcondition
     *  The animal node will be added to the tree.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException {
        if (cursor.getLeft() != null)
            if (cursor.getLeft().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getMiddle() != null)
            if (cursor.getMiddle().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getRight() != null)
            if (cursor.getRight().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null)
            throw new PositionNotAvailableException();
        if (cursor.isHerbivore() && !cursor.isCarnivore())
            throw new DietMismatchException();

        OrganismNode newNode = new OrganismNode();
        newNode.setName(name);
        newNode.setHerbivore(isHerbivore);
        newNode.setCarnivore(isCarnivore);
        newNode.setPlant(false);

        cursor.addPrey(newNode);
    }

    /**
     * Adds a plant child to the tree with the specified requirements after the cursor reference.
     * @param name
     *  The name of the plant that is added to the tree.
     * @throws IllegalArgumentException
     *  Thrown if the plant already exists in the tree.
     * @throws PositionNotAvailableException
     *  Thrown if there are no open spots for the plant.
     * @throws DietMismatchException
     *  Thrown if the diet does not make sense with the addition of this plant.
     * @throws IsPlantException
     *  Thrown if the cursor is a plant.
     * @custom.precondition
     *  Name is of string type.
     *  Cursor is not a plant.
     *  There is an open spot.
     *  The diets will make sense.
     *  The plant does not already exist.
     * @custom.postcondition
     *  The plant child will be added to the tree.
     */
    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException, DietMismatchException, IsPlantException{
        if (cursor.getLeft() != null)
            if (cursor.getLeft().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getMiddle() != null)
            if (cursor.getMiddle().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getRight() != null)
            if (cursor.getRight().getName().equals(name))
                throw new IllegalArgumentException();
        if (cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null)
            throw new PositionNotAvailableException();
        if (cursor.isCarnivore() && !cursor.isHerbivore())
            throw new DietMismatchException();
        if (cursor.isPlant())
            throw new IsPlantException();

        OrganismNode newNode = new OrganismNode();
        newNode.setName(name);
        newNode.setPlant(true);
        newNode.setCarnivore(false);
        newNode.setHerbivore(false);

        boolean inserted = false;
        if (cursor.getLeft() == null){
            cursor.setLeft(newNode);
            inserted = true;
        }
        if (!inserted && cursor.getMiddle() == null){
            cursor.setMiddle(newNode);
            inserted = true;
        }
        if (!inserted && cursor.getRight() == null){
            cursor.setRight(newNode);
            inserted = true;
        }
    }

    /**
     * The child with the specified name will be removed from the tree and the children will be adjusted properly.
     * @param name
     *  The child that is to be removed from the tree.
     * @throws IllegalArgumentException
     *  Thrown if the node with the name specified does not exist.
     * @custom.precondition
     *  Name is of string type.
     * @custom.postcondition
     *  The child with the name specified will be removed from the tree.
     */
    public void removeChild(String name) throws IllegalArgumentException{
        OrganismNode nodeRemove = findNode(name, cursor);
        if (nodeRemove == null)
            throw new IllegalArgumentException();

        if (cursor.getLeft().getName().equals(name)){
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (cursor.getMiddle().getName().equals(name)){
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        } else if (cursor.getRight().getName().equals(name))
            cursor.setRight(null);
    }

    // Getters
    /**
     * Gets the root of the tree.
     * @return
     *  Returns the root of the tree.
     */
    public OrganismNode getRoot() {
        return root;
    }

    /**
     * Gets the cursor of the tree.
     * @return
     *  Returns the cursor of the tree.
     */
    public OrganismNode getCursor() {
        return cursor;
    }

    // used to test
    /**
     * Used to test the different methods in the OrganismTree class.
     * @param args - Not used
     * @throws IsPlantException
     *  Thrown if a method throws it.
     * @throws PositionNotAvailableException
     *  Thrown if a method throws it.
     * @throws DietMismatchException
     *  Thrown if a method throws it.
     */
    public static void main(String[] args) throws IsPlantException, PositionNotAvailableException, DietMismatchException {
        OrganismNode testNode1 = new OrganismNode();
        testNode1.setName("Cat");
        OrganismNode testNode2 = new OrganismNode();
        testNode2.setName("Rat");
        OrganismNode testNode3 = new OrganismNode();
        testNode3.setName("Bird");
        OrganismNode testNode4 = new OrganismNode();
        testNode4.setName("Bug");
        testNode1.setLeft(testNode2);
        testNode1.setMiddle(testNode3);
        testNode1.setRight(testNode4);

        OrganismTree testTree1 = new OrganismTree(testNode1);
        System.out.println(testTree1.listPrey());

        OrganismNode testNode5 = new OrganismNode();
        testNode5.setName("Worm");
        testNode3.setLeft(testNode5);

        testTree1.moveCursor("Bird");
        System.out.println(testTree1.listPrey());
        System.out.println("Food Chain: " + testTree1.listFoodChain());
        System.out.println();

        testTree1.cursorReset();
        System.out.println(testTree1.cursor.getName() + "\n");

        testTree1.printOrganismTree();

        testNode3.setPlant(true);
        testNode5.setPlant(true);
        System.out.println(testTree1.listAllPlants());
        testTree1.moveCursor("Bird");
        testTree1.addAnimalChild("Insect", true, false);
        testTree1.cursorReset();
        testTree1.moveCursor("Bug");
        testTree1.addAnimalChild("Bug2", true, false);
        System.out.println();
        testTree1.cursorReset();
        testTree1.printOrganismTree();
        System.out.println();
        testTree1.removeChild("Bird");
        testTree1.printOrganismTree();
    }
}
