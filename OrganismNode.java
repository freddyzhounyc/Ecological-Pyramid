/**
 * The OrganismNode class represents a node in a tree. The tree being represented is a ternary tree that represents an organism in an ecological pyramid.
 *
 * @author Freddy Zhou
 */
public class OrganismNode{
    private OrganismNode left; // left child reference.
    private OrganismNode middle; // middle child reference.
    private OrganismNode right; // right child reference.
    private String name; // name of the organism.
    private boolean isPlant; // whether the organism is a plant.
    private boolean isHerbivore; // whether the organism is a herbivore.
    private boolean isCarnivore; // whether the organism is a carnivore.

    /**
     * Creates an instance of the OrganismNode class with no arguments.
     * @custom.postcondition
     *  An object of OrganismNode is instantiated with no arguments.
     */
    public OrganismNode(){
    }

    /**
     * Creates an instance of the OrganismNode class with all instance variable arguments.
     * @param left
     *  Left child reference.
     * @param middle
     *  Middle child reference.
     * @param right
     *  Right child reference.
     * @param name
     *  Name of the organism.
     * @param isPlant
     *  Whether the organism is a plant.
     * @param isHerbivore
     *  Whether the organism is a herbivore.
     * @param isCarnivore
     *  Whether the organism is a carnivore.
     * @custom.precondition
     *  Left, middle, and right are of OrganismNode type.
     *  Name is of string type.
     *  IsPlant, isHerbivore, and isCarnivore are of boolean type.
     * @custom.postcondition
     *  An object of OrganismNode is instantiated and filled with parameter values.
     *
     */
    public OrganismNode(OrganismNode left, OrganismNode middle, OrganismNode right, String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore){
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    /**
     * Adds a child node to the current node if it meets the requirements of diet and has an available slot.
     * @param preyNode
     *  The OrganismNode that is to be added to the tree.
     * @throws PositionNotAvailableException
     *  Thrown if there is no open spot.
     * @throws IsPlantException
     *  Thrown if the preyNode is a plant.
     * @throws DietMismatchException
     *  Thrown if the diet of the current node will not make sense with the preyNode.
     * @custom.precondition
     *  preyNode is of OrganismNode type.
     *  preyNode is not a plant.
     *  preyNode fits the diet of the current node.
     * @custom.postcondition
     *  preyNode will be added to the tree.
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException{
        if (left != null && middle != null && right != null)
            throw new PositionNotAvailableException();
        if (isPlant)
            throw new IsPlantException();
        if ((isHerbivore && !isCarnivore) && (preyNode.isCarnivore || preyNode.isHerbivore))
            throw new DietMismatchException();
        if ((isCarnivore && !isHerbivore) && preyNode.isPlant)
            throw new DietMismatchException();

        // valid prey
        if (left == null)
            left = preyNode;
        else if (middle == null)
            middle = preyNode;
        else if (right == null)
            right = preyNode;
    }

    // Getters

    /**
     * Gets the left child.
     * @return
     *  Returns the left child.
     */
    public OrganismNode getLeft(){
        return left;
    }

    /**
     * Gets the middle child.
     * @return
     *  Returns the middle child.
     */
    public OrganismNode getMiddle(){
        return middle;
    }

    /**
     * Gets the right child.
     * @return
     *  Returns the right child.
     */
    public OrganismNode getRight(){
        return right;
    }

    /**
     * Gets the name of the current node.
     * @return
     *  Returns the name of the current node.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets whether the node is a plant.
     * @return
     *  Returns whether the node is a plant.
     */
    public boolean isPlant(){
        return isPlant;
    }

    /**
     * Gets whether the node is a herbivore.
     * @return
     *  Returns whether the node is a herbivore.
     */
    public boolean isHerbivore(){
        return isHerbivore;
    }

    /**
     * Gets whether the node is a carnivore.
     * @return
     *  Returns whether the node is a carnivore.
     */
    public boolean isCarnivore(){
        return isCarnivore;
    }

    // Setters
    /**
     * Sets the left child.
     * @param left
     *  Left child.
     */
    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    /**
     * Sets the middle child.
     * @param middle
     *  Middle child.
     */
    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    /**
     * Sets the right child.
     * @param right
     *  Right child.
     */
    public void setRight(OrganismNode right) {
        this.right = right;
    }

    /**
     * Sets the name.
     * @param name
     *  Name of the organism.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets whether the node is a plant.
     * @param plant
     *  Whether the node is a plant.
     */
    public void setPlant(boolean plant) {
        isPlant = plant;
    }

    /**
     * Sets whether the node is a herbivore.
     * @param herbivore
     *  Whether the node is a herbivore.
     */
    public void setHerbivore(boolean herbivore) {
        isHerbivore = herbivore;
    }

    /**
     * Sets whether the node is a carnivore.
     * @param carnivore
     *  Whether the node is a carnivore.
     */
    public void setCarnivore(boolean carnivore) {
        isCarnivore = carnivore;
    }
}
