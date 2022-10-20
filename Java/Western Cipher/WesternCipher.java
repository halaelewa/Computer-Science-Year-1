import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is prompt the user the option to encode or decode, then apply
 * encode/decode at user's string.
 *
 * @author Hala Elewa
 */
public class WesternCipher {

	private CircularArrayQueue<Character> encodingQueue;
	private CircularArrayQueue<Character> decodingQueue;

	/**
	 * Construct the <b>encodingQueue</b> and <b>decodingQueue</b> with the given
	 * capacity.
	 *
	 * @param capacity default capacity to initialize the queues.
	 */
	public WesternCipher(int capacity) {
		encodingQueue = new CircularArrayQueue<>(capacity);
		decodingQueue = new CircularArrayQueue<>(capacity);
	}

	/**
	 * Construct the <b>encodingQueue</b> and <b>decodingQueue</b> with capacity of
	 * 10.
	 */
	public WesternCipher() {
		encodingQueue = new CircularArrayQueue<>(10);
		decodingQueue = new CircularArrayQueue<>(10);
	}

	/**
	 * This main method used to prompt the user the option to encode or decode, then
	 * prompt the user to type a string to apply the chosen process (encode/decode).
	 *
	 * @param args the command line arguments.
	 * @throws IOException if something went wrong with reading the user input.
	 */
	public static void main(String[] args) throws IOException {
		WesternCipher wc = new WesternCipher();

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Would you line to encode or decode? (e | d) "); // Prompt the user to choose an operation
		String userInput = input.readLine().toLowerCase(); // Take the user's input

		// The user option not valid. Prompt again...
		while (!userInput.equals("e") && !userInput.equals("d")) {
			System.out.print("Invalid userInput, choose again encode or decode? (e | d) ");
			userInput = input.readLine().toLowerCase();
		}

		// The user chose to encode
		if (userInput.equals("e")) {
			System.out.print("Input a string to encode: "); // Prompt the user to type the string
			userInput = input.readLine(); // Take the user's input

			// As long as the user typed a none empty string
			while (!userInput.isEmpty()) {
				String encodedString = wc.encode(userInput); // Encode the user's string
				System.out.printf("The encoded string of %s is %s\n", userInput, encodedString);

				System.out.print("Input a string to encode: "); // See the user wish to input another string
				userInput = input.readLine();
			}
		}

		// The user chose to decode
		else {
			System.out.print("Input a string to decode: ");
			userInput = input.readLine();

			while (!userInput.isEmpty()) {
				String decodedString = wc.decode(userInput);
				System.out.printf("The decoded string of %s is %s\n", userInput, decodedString);

				System.out.print("Input a string to decode: ");
				userInput = input.readLine();
			}
		}
	}

	/**
	 * Apply the Western Cipher to encode the string.
	 *
	 * @param input string to encode.
	 * @return <b>String</b> encoded string.
	 */
	public String encode(String input) {
		// Enqueue each letter to the encodingQueue
		for (int i = 0; i < input.length(); i++)
			encodingQueue.enqueue(input.charAt(i));

		String encodedString = "";

		boolean prevNumerical = false;
		int prevAmount = 0, i = 0;

		// Encoding each letter while dequeueing
		while (!encodingQueue.isEmpty()) {
			char ch = encodingQueue.dequeue(), encodedChar = 0;
			int steps = 5 + (i * 2); // Calculate the forwards steps for each letter

			// Character is a space, do nothing!
			if (ch == ' ') {
				encodedString += ch;
				i++;
				continue;
			}

			/*
			 * If the previous letter was converted to a numerical value 1. If this letter
			 * is to be converted to a numerical value, replace the letter with the
			 * corresponding value from the table.
			 * 
			 * 2. Otherwise, shift the letter forwards steps time, then shift it backwards
			 * twice the previous amount.
			 */
			if (prevNumerical) {
				prevNumerical = false;
				boolean q = false;

				// Check if the letter is to be converted to a numerical value
				if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U' || ch == 'Y') {
					// The letter from the table
					switch (ch) {
					case 'A': {
						encodedChar = '3';
						prevAmount = 3;
						prevNumerical = true;
						q = true;
						break;
					}
					case 'E': {
						encodedChar = '4';
						prevAmount = 4;
						prevNumerical = true;
						q = true;
						break;
					}
					case 'I': {
						encodedChar = '5';
						prevAmount = 5;
						prevNumerical = true;
						q = true;
						break;

					}
					case 'O': {
						encodedChar = '6';
						prevAmount = 6;
						prevNumerical = true;
						q = true;
						break;

					}
					case 'U': {
						encodedChar = '1';
						prevAmount = 1;
						prevNumerical = true;
						q = true;
					}
					case 'Y': {
						encodedChar = '2';
						prevAmount = 2;
						prevNumerical = true;
						q = true;
						break;

					}
					}
				}

				// This letter is not to be converted to a numerical value
				if (!q) {
					// Add steps to the letter and minus twice the previous amount
					steps = ((steps + ch - (2 * prevAmount)));

					/*
					 * If the letter passed the letter 'Z', then take the circular letter that match
					 * the steps. % 26: used to get the index/position of the letter. + 65: shift
					 * the letter by 65, so we get the real char as A is 65 in ascii.
					 */
					if (steps > 90)
						steps = ((steps % 90 - 1) % 26) + 65;

					encodedChar = (char) steps;
				}
			}

			// The previous letter is not converted to a numerical value
			else {

				// The letter from the table
				switch (ch) {
				case 'A': {
					encodedChar = '1';
					prevAmount = 1;
					prevNumerical = true;
					break;

				}
				case 'E': {
					encodedChar = '2';
					prevAmount = 2;
					prevNumerical = true;
					break;

				}
				case 'I': {
					encodedChar = '3';
					prevAmount = 3;
					prevNumerical = true;
					break;

				}
				case 'O': {
					encodedChar = '4';
					prevAmount = 4;
					prevNumerical = true;
					break;

				}
				case 'U': {
					encodedChar = '5';
					prevAmount = 5;
					prevNumerical = true;
					break;

				}
				case 'Y': {
					encodedChar = '6';
					prevAmount = 6;
					prevNumerical = true;
					break;

				}

				// The letter is not from the table
				default: {
					steps += ch;

					if (steps > 90)
						steps = ((steps % 90 - 1) % 26) + 65;

					encodedChar = (char) steps;
					break;

				}
				}
			}

			encodedString += encodedChar;
			i++;
		}

		return encodedString;
	}

	/**
	 * Apply the Western Cipher to decode the string.
	 *
	 * @param input string to decode
	 * @return <b>String</b> decoded string.
	 */
	public String decode(String input) {
		// Enqueue each character to decodingQueue
		for (int i = 0; i < input.length(); i++)
			decodingQueue.enqueue(input.charAt(i));

		String decodedString = "";

		int prevAmount = 0, i = 0;
		boolean prevNumerical = false;

		// Decoding each character while dequeueing
		while (!decodingQueue.isEmpty()) {
			char ch = decodingQueue.dequeue(), decodedChar = 0;
			int steps = -(5 + (i * 2)); // Calculate the backwards steps for each character

			// Character is a space, do nothing!
			if (ch == ' ') {
				decodedString += ch;
				i++;
				continue;
			}

			/*
			 * If the previous character is a numerical value 1. If this character is a
			 * numerical value, replace the character with the corresponding letter from the
			 * table.
			 * 
			 * 2. Otherwise, shift the character forwards steps time, then shift it
			 * backwards twice the previous amount.
			 */
			if (prevNumerical) {
				prevNumerical = false;
				boolean q = false;

				// Check if the character is a numerical value
				if (ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '1' || ch == '2') {
					// The character from the table
					switch (ch) {
					case '3': {
						decodedChar = 'A';
						prevAmount = 3;
						prevNumerical = true;
						q = true;
						break;

					}
					case '4': {
						decodedChar = 'E';
						prevAmount = 4;
						prevNumerical = true;
						q = true;
						break;

					}
					case '5': {
						decodedChar = 'I';
						prevAmount = 5;
						prevNumerical = true;
						q = true;
						break;

					}
					case '6': {
						decodedChar = 'O';
						prevAmount = 6;
						prevNumerical = true;
						q = true;
						break;

					}
					case '1': {
						decodedChar = 'U';
						prevAmount = 1;
						prevNumerical = true;
						q = true;
						break;

					}
					case '2': {
						decodedChar = 'Y';
						prevAmount = 2;
						prevNumerical = true;
						q = true;
						break;

					}
					}
				}

				// This character is not a numerical value
				if (!q) {
					steps += (2 * prevAmount);

					// shifted character is valid
					if (steps + ch >= 65)
						steps += ch;

					// shifted character is less then 65/'A', then take the circular letter
					else
						steps = 90 - (Math.abs(steps + (ch - 65) + 1) % 26);

					decodedChar = (char) steps;
					prevAmount = 0;
				}
			}

			// The previous character is not a numerical value
			else {
				// The character from the table
				switch (ch) {
				case '1': {
					decodedChar = 'A';
					prevAmount = 1;
					prevNumerical = true;
					break;

				}
				case '2': {
					decodedChar = 'E';
					prevAmount = 2;
					prevNumerical = true;
					break;

				}
				case '3': {
					decodedChar = 'I';
					prevAmount = 3;
					prevNumerical = true;
					break;

				}
				case '4': {
					decodedChar = 'O';
					prevAmount = 4;
					prevNumerical = true;
					break;

				}
				case '5': {
					decodedChar = 'U';
					prevAmount = 5;
					prevNumerical = true;
					break;

				}
				case '6': {
					decodedChar = 'Y';
					prevAmount = 6;
					prevNumerical = true;
					break;

				}

				// The character is not from the table
				default: {
					steps += ch;

					while (steps < 65)
						steps = 90 - (65 - steps) + 1;

					decodedChar = (char) steps;
					break;

				}
				}
			}

			decodedString += decodedChar;
			i++;
		}

		return decodedString;
	}
}
