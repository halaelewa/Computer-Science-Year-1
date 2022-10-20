/**
 * The main purpose of this class is to represents the CircularArrayQueue data
 * structure.
 *
 * @param <T> Generic type to represents the data type of the queue.
 * @author Hala Elewa
 */
public class CircularArrayQueue<T> implements QueueADT<T> {

	/**
	 * Default capacity of the queue.
	 */
	private final int DEFAULT_CAPACITY = 20;
	/**
	 * Array that represents the queue.
	 */
	private T[] queue;
	/**
	 * Integer represents the front index of the queue.
	 */
	private int front;
	/**
	 * Integer represents the rear index of the queue.
	 */
	private int rear;
	/**
	 * Integer represents the number of elements in the queue.
	 */
	private int count;

	/**
	 * Construct a queue with default capacity of 20.
	 */
	public CircularArrayQueue() {
		front = 1;
		rear = DEFAULT_CAPACITY;
		count = 0;
		queue = (T[]) new Object[DEFAULT_CAPACITY];
	}

	/**
	 * Construct queue with capacity of initialCapacity.
	 *
	 * @param initialCapacity the initial capacity of the queue.
	 */
	public CircularArrayQueue(int initialCapacity) {
		front = 1;
		rear = initialCapacity;
		count = 0;
		queue = (T[]) new Object[initialCapacity];
	}

	/**
	 * Adds one element to the rear of this queue.
	 *
	 * @param element the element to be added to the rear of this queue.
	 */
	@Override
	public void enqueue(T element) {
		// queue is full, expand the capacity of the queue
		if (count == getLength() - 1) {
			expandCapacity();
		}

		/*
		 * increase the rear as circular increment, e.g. let's assume the length is 5
		 * and rear is now 3 so 3 % 5 = 3, then rear will be 4 => 4 % 5 = 4 then 4 will
		 * be 5 => 5 % 5 == 0, then 5 will be 6 => 6 % 5 = 1 and so on...
		 */
		rear = (rear + 1) % getLength();
		queue[rear] = element;

		count++; // increase the elements counter
	}

	/**
	 * Removes and returns the element at the front of this queue.
	 *
	 * @return <b>T</b> the element at the front of this queue.
	 */
	@Override
	public T dequeue() {
		// queue is empty
		if (isEmpty())
			throw new EmptyCollectionException("CircularQueue");

		T result = queue[front]; // get the front element
		front = (front + 1) % getLength(); // explained in enqueue() above

		count--; // decrease the elements counter
		return result;
	}

	/**
	 * Returns without removing the element at the front of this queue.
	 *
	 * @return <b>T</b> the first element in this queue.
	 */
	@Override
	public T first() {
		// queue is empty
		if (isEmpty())
			throw new EmptyCollectionException("CircularQueue");

		return queue[front];
	}

	/**
	 * Returns true if this queue contains no elements.
	 *
	 * @return <b>true</b> if this queue is empty, <b>false</b> otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	/**
	 * Returns the number of elements in this queue.
	 *
	 * @return <b>int</b> the number of elements in this queue.
	 */
	@Override
	public int size() {
		return count;
	}

	/**
	 * Returns the front index value.
	 *
	 * @return <b>int</b> front index value
	 */
	public int getFront() {
		return front;
	}

	/**
	 * Returns the rear index value.
	 *
	 * @return <b>int</b> rear index value
	 */
	public int getRear() {
		return rear;
	}

	/**
	 * Returns the length (capacity) of the queue.
	 *
	 * @return <b>int</b> length (capacity) of the queue.
	 */
	public int getLength() {
		return queue.length;
	}

	/**
	 * Expand the capacity of the queue by 20 additional slots.
	 */
	private void expandCapacity() {
		// create a temp queue with the additional slots
		T[] temp = (T[]) new Object[queue.length + 20];

		// copy the elements in queue to the temp queue
		for (int i = front; i <= rear; i++)
			temp[i] = queue[i];

		queue = temp; // assign the temp queue to the original queue
	}

	/**
	 * Returns a string representation of this queue.
	 *
	 * @return <b>String</b> the representation of this queue
	 */
	@Override
	public String toString() {
		// queue is empty
		if (isEmpty())
			return "The queue is empty";

		String res = "QUEUE: ";
		for (int i = front; i <= rear; i++)
			res += queue[i] + (i < rear ? ", " : ".");

		return res;
	}
}
