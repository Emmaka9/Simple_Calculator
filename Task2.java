
import java.util.Scanner;
import java.util.Stack;

/**
 * This program takes in a mathematical expression as input from the keyboard.
 * It uses StringSplitter class to tokenize the input. Then evaluates it. For
 * this program to evaluate the expression, the expression needs to be fully
 * parenthesized. The mathematical operations that it can calculate are
 * +,-,*,/,^ . With invalid input, it might throw
 * {@code IllegalArgumentexception} or simply print invalid expression. This
 * program implements a variation of the Shunting-Yard algorithm by Edsgar
 * Dijkstra.
 * 
 * @author Emmaka
 * @since 8-July-2019
 * 
 */

public class Task2 {
	// -----------main method------------
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		// take the input from keyboard using scanner
		// put it into a Strng var.
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Your Input:");
		String input = sc.nextLine();
		// declare a StringSpliter varible to tokenize the input
		StringSplitter splitter = new StringSplitter(input);

		// declare two stacks- numStack to put the numeric tokens
		// and symbolstack to put the symbolic tokens.
		Stack<Double> numStack = new Stack<Double>();
		Stack<String> symbolStack = new Stack<String>();
		// declare a boolean var error
		// the method evaluates the tokens
		// if it encounters any error it returns true, else false.
		boolean error = evaluate(splitter, numStack, symbolStack);

		if (error || numStack.size() != 1 || !symbolStack.isEmpty()) {
			System.out.println("Illegal Expression!!");
		} else {
			System.out.println("Result = " + numStack.pop());
		}

		// System.out.println(numStack);
		// System.out.println(symbolStack);

	}

	public static boolean isNumeric(String strNum) {
		return strNum.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * This method implements the Shunting-yard algorithm We keep taking tokens from
	 * the splitter as long as there's token to process. We use two stacks- numStack
	 * to store the numeric tokens and symboleStack to store symbolic tokens. When
	 * we find a right parenthesis(')'), that means we have all the information for
	 * that sub-expression, thus we evaluate it.
	 * 
	 * We assume the input has no error. We use a boolean err_flag to recognize it.
	 * If an error if an error is encountered, we set it true.
	 * 
	 * @param splitter    A {@code StringSplitter} to get tokens.
	 * @param numStack    A {@code Stack} to store the numeric tokens.
	 * @param symbolStack A {@code Stack} to store the symbols.
	 * @return err_flag A {@code boolean}, true if there's an error.
	 */
	static boolean evaluate(StringSplitter splitter, Stack<Double> numStack, Stack<String> symbolStack) {
		// a boolean flag to recognize any error
		boolean err_flag = false;

		// As long as there's token and no error:
		while (!err_flag && splitter.hasNext()) {
			// take the next token into a String var str.
			String str = splitter.next();
			// System.out.println("TOken: " + str);
			/**
			 * Check if it's numeric if so,turn it into a double and put it into numStack,
			 * else check if it's left parenthesis or any of the legal operator else if it's
			 * a right parenthesis, which means we can evaluate this sub-expression.
			 */
			if (isNumeric(str)) {
				numStack.push(Double.parseDouble(str));
			} else {
				if (str.equals("(") || "+-*/^".contains(str)) {
					symbolStack.push(str);
				} else if (str.equals(")")) {
					// To evaluate this sub-expression, we pop last two numbers
					// pass it as formal parameters of the calculate method
					double num2 = numStack.pop();
					double num1 = numStack.pop();
					String operator = symbolStack.pop();
					if (!"+-*/^".contains(operator)) {
						System.out.println("'" + operator + "' in the " + "place of an operator!!");
						System.out.println("Error in parenthesis!" + "!\nCheck Your Input!!");
						return true;
					}
					// take the calculated value from the last sub-expr into
					// a double var result.
					double result = calculate(num1, num2, operator);
					// push result into numStack
					numStack.push(result);
					// if symbolStack not empty
					// pop from symbolStack
					if (!symbolStack.isEmpty())
						symbolStack.pop();
				} else {
					System.out.println("The number '" + str + "' is not valid!!");
					err_flag = true;
					break;
				}
			}
//			System.out.println("Num Stack: " + numStack);
//			System.out.println("Symbol Stack: " + symbolStack);
//			System.out.println("err_flag = " + err_flag);
		}
		return err_flag;
	}

	/**
	 * This method carries out some certain mathematical operation for given
	 * numerics.
	 * 
	 * @param num1     A {@code double}
	 * @param num2     A {@code double}
	 * @param operator A {@code String}
	 * @param err_flag A {@code boolean}
	 * @return result A {@code double}
	 */
	static double calculate(double num1, double num2, String operator) {
		double result = 0;
		if (operator.equals("+")) {
			result = num1 + num2;
		} else if (operator.equals("-")) {
			result = num1 - num2;
		} else if (operator.equals("*")) {
			result = num1 * num2;
		} else if (operator.equals("^")) {
			result = Math.pow(num1, num2);
		} else if (operator.equals("/")) {
			result = num1 / num2;
		}

		return result;
	}

}
