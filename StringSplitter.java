
import java.util.LinkedList;
import java.util.Queue;

/**
 * StringSplitter class is a supporting class which helps us to tokenize a
 * String the way we want.
 * 
 * @author emmaka
 * @since
 */

public class StringSplitter {
	// ----------------Fields-----------------------------
	private Queue<Character> characters;
	private String token;
	public static final String SPECIAL_CHARACTERS = "()+-*/^";

	// -------------------Constructor--------------------------
	/**
	 * This is the constrcor of StringSplitter class. It takes a {@code String} from
	 * the class that uses this class and tokenizes the {@code String}. First It
	 * puts each {@code char} of the String into a Queue.
	 * 
	 * @param line A {@code String}
	 */
	public StringSplitter(String line) {
		characters = new LinkedList<Character>();
		// put all each char into a queue
		for (int i = 0; i < line.length(); i++) {
			characters.add(line.charAt(i));
		}
	}

	/**
	 * Checks if there's any more token i.e. if the Queue is empty.
	 * 
	 * @return If no more token, returns false. Otherwise true.
	 */
	public boolean hasNext() {
		if (characters.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * This method returns the next token.
	 * 
	 * @return findNextToken() A method.
	 */
	public String next() {
		return findNextToken();
	}

	/**
	 * This method creates the next token.
	 * 
	 * @return token A {@code String}
	 */
	private String findNextToken() {
		// peek ahead and see if the next elem is a white space
		// If it's, remove it.
		while (!characters.isEmpty() && Character.isWhitespace(characters.peek())) {
			characters.remove();
		}

		if (characters.isEmpty()) {
			token = null;
		} else {
			char ch = characters.remove();
			// if ch is either a digit or a dot or a SPECIAL_CHARACTER
			// initialize token as ch
			if (Character.isDigit(ch) || ch == '.' || SPECIAL_CHARACTERS.indexOf(ch) >= 0)
				token = "" + ch;
			// else throw IllegalArgumentException
			else {
				System.out.println(ch + " is not legal!!");
				throw new IllegalArgumentException("Invalid Input!!");
			}

			if (!SPECIAL_CHARACTERS.contains(token)) {
				boolean done = false;
				while (!characters.isEmpty() && !done) {
					char ch2 = characters.peek();
					if (Character.isWhitespace(ch2) || SPECIAL_CHARACTERS.indexOf(ch2) >= 0) {
						done = true;
					} else if (Character.isDigit(ch2) || ch2 == '.') {
						token = token + characters.remove();
					} else {
						System.out.println(ch2 + " is not legal!!\n");
						// done = true;
						throw new IllegalArgumentException("Invalid Input!!");
					}

				}

			}

		}
		return token;

	}

}
