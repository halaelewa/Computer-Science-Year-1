/**
 * This class represents a <b>Stack Data Structure</b>.
 *
 * @author hala elewa
 */
public class ArrayStack<T> implements ArrayStackADT<T> {

    /**
     * Used to track the arrow path.
     */
    public static String sequence;
    /**
     * Used to store the data items of the stack.
     */
    private T[] stack;
    /**
     * Used to store the position of the last data item in the stack (initial -1).
     */
    private int top;

    /**
     * Initialize Stack with default size of 14.
     */
    public ArrayStack() {
        stack = (T[]) new Object[14];
        top = -1;
        sequence = "";
    }

    /**
     * Initialize the stack with the given capacity.
     *
     * @param initialCapacity The initial capacity of the stack.
     */
    public ArrayStack(int initialCapacity) {
        stack = (T[]) (new Object[initialCapacity]);
        top = -1;
        sequence = "";
    }

    /**
     * Adds one element to the top of this stack.
     *
     * @param dataItem data item to be pushed onto stack.
     */
    @Override
    public void push(T dataItem) {
        int length = stack.length;

        // stack is full, increase the capacity
        if (++top == length) {
            int newLength;
            if (length < 50) // if stack size < 50, then increase the capacity by 10.
                newLength = length + 10;

            else
                newLength = length * 2;

            T[] temp = (T[]) new Object[newLength]; // create a copy of the stack with the new length
            System.arraycopy(stack, 0, temp, 0, length);
            stack = temp;
        }

        stack[top] = dataItem; // push the data item at top

        // update the arrow path sequence
        if (dataItem instanceof MapCell)
            sequence += "push" + ((MapCell) dataItem).getIdentifier();

        else
            sequence += "push" + dataItem.toString();
    }

    /**
     * Removes and returns the top element from this stack.
     *
     * @return <b>T</b> data item removed from the top of the stack.
     * @throws EmptyStackException if stack is empty.
     */
    @Override
    public T pop() throws EmptyStackException {
        // stack is empty, throw an EmptyStackException exception
        if (isEmpty())
            throw new EmptyStackException("Stack");

        T result = stack[top--]; // retrieve the last data item

        // create a copy of the stack
        int length = stack.length;
        T[] temp = (T[]) (new Object[length]);
        System.arraycopy(stack, 0, temp, 0, length);
        stack = temp;

        /* check if the number of data items in stack is less than the fourth of the total size,
           shrink the stack size to half with minimum of 14.
         */
        if ((top + 1) < length / 4) {
            T[] reducedArray = (T[]) (new Object[Math.max(14, length / 2)]);
            System.arraycopy(stack, 0, reducedArray, 0, top + 1);
            stack = reducedArray;
        }

        // update the arrow path sequence
        if (result instanceof MapCell)
            sequence += "pop" + ((MapCell) result).getIdentifier();

        else
            sequence += "pop" + result.toString();

        return result;

    }

    /**
     * Returns without removing the top element of this stack.
     *
     * @return <b>T</b> data item at top of stack without removing it.
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public T peek() throws EmptyStackException {
        // stack is empty, throw an EmptyStackException exception
        if (isEmpty())
            throw new EmptyStackException("Stack");

        return stack[top];
    }

    /**
     * Returns true if this stack contains no elements.
     *
     * @return <b>true</b> if the stack is empty, <b>false</b> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Returns the number of data items in this stack.
     *
     * @return <b>int</b> number of data items in this stack.
     */
    @Override
    public int size() {
        return top + 1;
    }

    /**
     * Returns the total size of the stack.
     *
     * @return <b>int</b> number of stack capacity.
     */
    public int length() {
        return stack.length;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return <b>String</b> representation of this stack.
     */
    @Override
    public String toString() {
        String res = "Stack: ";

        for (int i = 0; i <= top; i++) {
            res += stack[i].toString();

            if (i != top) res += ", "; // as long as we're not at last element
        }

        return res;
    }
}
