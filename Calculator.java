package school;

public class Calculator 
{
	/**	Method: SolveExpression
	 * 	Gets the result of a given expression
	 * 	@param givenExpression	Expression in string format
	 * 	@return	The result	*/
	public int SolveExpression(String givenExpression)
	{
		// Stack where the calculator will keep track of calculations
		SinglyLinkedStack<Integer> storage = new SinglyLinkedStack<>();
		
		// Final result
		int result = 0;
		
		// Convert expression from infix to postfix
		String postfix = infixToPostfix(givenExpression);
		
		// Go through the expression
		for (int i = 0; i < postfix.length(); i++)
		{
			// Get currently checked value
			char current = postfix.charAt(i);
			
			// If a "+" is found
			if (current == '+')
			{
				// Get sum value
				int a = storage.pop();
				int b = storage.pop();
				int sum = a + b;
				
				// Push it back into the stack
				storage.push(sum);
			}
			
			// If a "-" is found
			else if (current == '-')
			{
				// Get the difference value
				int a = storage.pop();
				int b = storage.pop();
				int dif = b - a;
				
				// Push it back into the stack
				storage.push(dif);
			}
			
			// If it is not an operator
			else
				// Push it into the stack
				storage.push(Character.getNumericValue(current));
			
		}
		
		// Get the result
		result = storage.pop();
		
		// Return the result
		return result;
	}
	
	/**	Helper Method: infixToPostfix
	 * 	Translates the given expression into postfix notation.
	 * 	Based on the function provided by the following website: https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
	 * 	@param expression	The given expression given in infix notation
	 * 	@return The given expression in postfix notation	*/
	public String infixToPostfix(String expression)
	{
		// Result string where the final postfix expression will be stored
		String result = "";
		
		// Operator stack where the operators will be stored until they are
		// pushed into the result string
		SinglyLinkedStack<Character> operatorStack = new SinglyLinkedStack<>();
		
		// Go through all the characters in the expression
		for (int i = 0; i < expression.length(); i++)
		{
			// Get the character being currently inspected
			char c = expression.charAt(i);
			
			// If it is a "("
			if (c == '(')
				// Push it to the Operator Stack
				operatorStack.push(c);
			
			// If it is a ")"
			else if (c == ')')
			{
				// Push all the operators in the stack into the result until
				// it finds a "(" (it does not push the "(" into the result)
				while (!operatorStack.isEmpty() && operatorStack.top() != '(')
					result += operatorStack.pop();
				
				// If something went wrong and the top of the stack is NOT a
				// "(", this is an invalid expression
				if (!operatorStack.isEmpty() && operatorStack.top() != '(')
					return "Invalid Expression";
				
				// If everything went well and the top of the stack is a "("
				// pop it out of the stack, without pushing it to the result
				else if (!operatorStack.isEmpty() && operatorStack.top() == '(')
					operatorStack.pop();
			}
			
			// If it is an operator
			else if (c == '+' || c == '-')
			{
				// Pop the following operators into the stack, unless it finds
				// a "(" in the operator stack
				while (!operatorStack.isEmpty() && operatorStack.top() != '(')
					result += operatorStack.pop();
				
				// Push the found operator into the stack
				operatorStack.push(c);
			}
			
			// If it is not an operator or parentheses, append it to the result string
			else
				result += c;
		}
		
		// Push the remaining operators into the result
		while (!operatorStack.isEmpty())
			result += operatorStack.pop();
		
		// Return the found postfix result
		return result;
	}
}
