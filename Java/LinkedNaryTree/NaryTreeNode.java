/**
 * This class represents each of the N-Tree node.
 *
 * @param <T> Generic class type.
 * @author Hala Elewa.
 */
public class NaryTreeNode<T> {
    /**
     * Generic data type.
     */
    private T data;
    /**
     * Represents the number of children of this node.
     */
    private int numChildren;
    /**
     * An array represents the children of this node.
     */
    private NaryTreeNode<T>[] children;

    /**
     * Constructor to initialize the data of the node.
     *
     * @param data to attach it to this node.
     */
    public NaryTreeNode(T data) {
        this.data = data;
        children = null;
        numChildren = 0;
    }

    /**
     * Add a child to the node.
     *
     * @param child to add to this node.
     */
    public void addChild(NaryTreeNode<T> child) {
        // The node has no children, initialize the children array with 3 slots.
        if (children == null)
            children = new NaryTreeNode[3];

        // The children array is full, expand it with 3 more slots.
        if (numChildren == children.length - 1)
            expandCapacity();

        // Add the child to children array.
        children[numChildren++] = child;
    }

    /**
     * @return <b>int</b> the number of the children of the node.
     */
    public int getNumChildren() {
        return numChildren;
    }

    /**
     * @param index the index of child to retrieve.
     * @return <b>NaryTreeNode<T></b> the child at a specified index.
     */
    public NaryTreeNode<T> getChild(int index) {
        // The node has no children, return null.
        if (children == null)
            return null;

        // Return the children at this index.
        return children[index];
    }

    /**
     * @return <b>T</b> get the data of the node.
     */
    public T getData() {
        return data;
    }

    /**
     * Expand the capacity of the children array 3 more slots.
     */
    public void expandCapacity() {
        NaryTreeNode<T>[] temp = new NaryTreeNode[children.length + 3];

        // Copy the element to a temp expanded array.
        for (int i = 0; i < children.length; i++)
            temp[i] = children[i];

        children = temp;
    }

    /**
     * @return <b>String</b> String representation of the node.
     */
    @Override
    public String toString() {
        return "Node containing " + getData();
    }
}
