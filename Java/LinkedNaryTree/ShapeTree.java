/**
 * This class represents the shape tree.
 *
 * @author Hala Elewa
 */
public class ShapeTree {
    /**
     * Represents the whole tree.
     */
    private LinkedNaryTree<Shape> tree;

    /**
     * @return <b>LinkedNaryTree<Shape></b> the tree.
     */
    public LinkedNaryTree<Shape> getTree() {
        return tree;
    }

    /**
     * @return <b>NaryTreeNode<Shape></b> the tree root.
     */
    public NaryTreeNode<Shape> getRoot() {
        return tree.getRoot();
    }

    /**
     * @param shape to be added to tree.
     */
    public void addShapeNode(Shape shape) {
        NaryTreeNode<Shape> newNode = new NaryTreeNode<>(shape);
        // The tree is empty, so make this newNode with the given shape as root.
        if (tree == null)
            tree = new LinkedNaryTree<>(newNode);

        // Tree not empty
        else {
            // Initialize a node with the given shape to see if this shape could be added to the tree
            NaryTreeNode<Shape> suitableNode = addShapeNodeHelper(shape);

            // newNode could be added.
            if (suitableNode != null)
                tree.addNode(suitableNode, newNode);
        }
    }

    /**
     * This method work with addShapeNode() method to find the suitable node to add the new node to.
     *
     * @param shape to be added to tree.
     * @return <b>NaryTreeNode<Shape></b> the suitable node to add the new node to.
     */
    public NaryTreeNode<Shape> addShapeNodeHelper(Shape shape) {
        // Initialize the stack with tree nodes type.
        ArrayStack<NaryTreeNode<Shape>> st = new ArrayStack<>();
        st.push(getRoot()); // add the root node to stack (where we should start searching).

        // Start popping nodes from stack and check if the node could be added.
        while (!st.isEmpty()) {
            NaryTreeNode<Shape> suitableNode = st.pop();

            // Check if shape can be added to this node.
            if (checkNode(suitableNode, shape))
                return suitableNode;

            /* Push the popped nodes from stack into the temp stack,
                so we could look for other nodes from high as possible.
             */
            ArrayStack<NaryTreeNode<Shape>> tempSt = new ArrayStack<>();
            while (!st.isEmpty())
                tempSt.push(st.pop());

            // Add the children of that node to stack to check them from left-to-right.
            for (int i = suitableNode.getNumChildren() - 1; i >= 0; i--)
                st.push(suitableNode.getChild(i));

            // Push the nodes back to the stack so they are at the top.
            while (!tempSt.isEmpty())
                st.push(tempSt.pop());
        }

        // Return null if no such a valid node.
        return null;
    }

    public boolean checkNode(NaryTreeNode<Shape> node, Shape shape) {
        /* Rule 1: A node can contain up to x children, where x is the number of sides of that node's shape.
            this node has reach the maximum allowed limit.
         */
        if (node.getNumChildren() == node.getData().getNumSides())
            return false;

        /* Rule 2: A node cannot have a child whose colour is the same as the node's colour.
            this node's colour is the same as the shape colour.
         */
        if (node.getData().getColour().equals(shape.getColour()))
            return false;

        /* Rule 3: A node cannot contain siblings with the same colour.
            this node has siblings with the same colour.
         */
        for (int i = 0; i < node.getNumChildren(); i++)
            if (node.getChild(i).getData().getColour().equals(shape.getColour()))
                return false;

        return true;
    }

    /**
     * @return <b>String</b> the string representation of the shape tree.
     */
    @Override
    public String toString() {
        return tree.toString();
    }
}