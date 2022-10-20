import java.util.Iterator;

/**
 * This class represents the tree.
 *
 * @param <T> Generic class type.
 * @author Hala Elewa.
 */
public class LinkedNaryTree<T> {

    /**
     * Represents the root node of the tree.
     */
    private NaryTreeNode<T> root;

    /**
     * Create an empty tree.
     */
    public LinkedNaryTree() {
        root = null;
    }

    /**
     * Create a tree with the given node as root.
     *
     * @param node to initialize the root with.
     */
    public LinkedNaryTree(NaryTreeNode<T> node) {
        root = node;
    }

    /**
     * Add the given child node of the tree to the given parent node of the tree.
     *
     * @param parent The parent node.
     * @param child The child node.
     */
    public void addNode(NaryTreeNode<T> parent, NaryTreeNode<T> child) {
        parent.addChild(child);
    }

    /**
     * @return <b>NaryTreeNode<T></b> the root node.
     */
    public NaryTreeNode<T> getRoot() {
        return root;
    }

    /**
     * @return <b>T</b> root's data.
     */
    public T getRootElement() {
        return root.getData();
    }

    /**
     * @return <b>True</b> if the tree is empty, False otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Calculate the size of a node.
     * This method is using recursion to accomplish its function.
     *
     * @param node the node to calculate its size.
     * @return <b>int</b> the node's size.
     */
    public int size(NaryTreeNode<T> node) {
        // The base case where the node is null.
        if (node == null)
            return 0;

        int counter = 1; // count the node itself
        // loop through the node's children and calculate their size as well. so on..
        for (int i = 0; i < node.getNumChildren(); i++)
            counter += size(node.getChild(i));

        return counter;
    }

    /**
     * Travers in the tree in preorder.
     *
     * @param node The tree node.
     * @param list A list to store the nodes in.
     */
    public void preorder(NaryTreeNode<T> node, ArrayUnorderedList<T> list) {
        if (node != null) {
            // Add the node's data to the list
            list.addToRear(node.getData());
            // loop through the node's children to make the preorder traversal as well. so on..
            for (int i = 0; i < node.getNumChildren(); i++)
                preorder(node.getChild(i), list);
        }
    }

    /**
     * @return <b>Iterator<T></b> An iterator on the list that contains the data of preorder traversal tree.
     */
    public Iterator<T> iteratorPreorder() {
        ArrayUnorderedList<T> list = new ArrayUnorderedList<>();
        preorder(root, list);
        return list.iterator();
    }

    /**
     * @return <b>String</b> The string representation of the tree.
     */
    @Override
    public String toString() {
        if (isEmpty())
            return "Tree is empty.";

        String str = "";
        Iterator<T> list = iteratorPreorder();

        while (list.hasNext())
            str += list.next() + "\n";

        return str;
    }
}
