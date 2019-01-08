
//Joel Jacques
//Create Date: 9/16/2017
//Modified Date: 9/30/2017
//Chapter 20 - Question 20.14

/* 
 * Accepts an postfix expression entered by the user and returns the result to the console
 */

import java.util.Stack;

public class Jacques_20_14 {

	final static String parseStr = " ";

	public static void main(String[] args) throws Exception {

//		String expStr = "3 3 + 3 -";
	
	if (args.length > 0) {
		
		String expStr = args[0];		
		//check if every character in the expression is a valid character
		if (chkExpression(expStr)) {
		
			//create array of tokens delimited by whitespace character
			String tokens[] = expStr.split(parseStr);
			
			//Display postfix expression
			for (int i = 0; i < tokens.length; i++) {
				System.out.print(tokens[i] + " ");
			}
			//output result of calculating expression
			System.out.println("= " + processExp(tokens));
		} 
		else
			System.out.println(expStr + " is an invalid expression"
					+ "\nan example of a valid postfix expression ex. 2 3 - 45 + 7 -");

	}
	else
		System.out.println("an expression has no been passed in on the command line please pass in a valid posfix expression"
					+ "\nex. 3 3 + 3 -");
	}
	/*
	 * processExp - calculates result for a postfix expression passed in as an array of strings
	 */
	private static int processExp(String[] Strs) {
		Stack<Integer> stk = new Stack<Integer>();
		//check if postfix expression starts with two integers since the arithmetic operations used are binary 
		if (chkOperands(Strs[0].charAt(0)) && chkOperands(Strs[1].charAt(0))) {
			
			//check if element contains operands if so add to stack
			for (int i = 0; i < Strs.length; i++) {
				if (chkOperands(Strs[i].charAt(0))) {
					stk.push(Integer.parseInt(Strs[i]));
				} 
				//check if the element is an the operator if so perform operation
				else if (chkOperators(Strs[i].charAt(0))) {
					processAnOperator(stk, Strs[i].charAt(0));
				}
			}
		} 
		else {
			System.out.println("Postfix expression must start with two integers");
			System.exit(0);
		}
		return stk.pop();
	}
	/*
	 * processAnOperator - compute the result of the operation of the two operands on top of the stack
	 */
	private static void processAnOperator(Stack<Integer> stk, char op) {
		int op1 = stk.pop(); //remove first operand
		int op2 = stk.pop(); //remove second operand
		
		//switch to determine the type of operation to be performed
		if (op == '+') {
			stk.push(op1 + op2);
		} else if (op == '-') {
			stk.push(op2 - op1);
		} else if (op == '*') {
			stk.push(op1 * op2);
		} else if (op == '/') {
			stk.push(op1 / op2);
		}
	}
	/*
	 * chkExpression - check user input for invalid characters returns false if an invalid character is found
	 */
	private static boolean chkExpression(String s) {
		boolean status = true;
		for (int i = 0; i < s.length(); i++) {
			//condition to check for the presence of invalid characters in the expression string
			//if a invalid character is found the user is prompted with the invalid character and
			//the status is changed to false
			if (!chkOperators(s.charAt(i)) && !chkOperands(s.charAt(i))) {
				if (!chkOperators(s.charAt(i))) {
					System.out.println("The operator " + s.charAt(i) + " is not valid operator");					
				}
				else
					System.out.println("The operand " + s.charAt(i) + " is not valid operand");					
				status = false;}
		}
		return status;
	}
	/*
	 * chkOperators - check for valid operator if operator is valid method returns true
	 */
	private static boolean chkOperators(char c) {
		boolean status = false;

		if (c == '+' || c == '-' || c == '*' || c == '/' || c == ' ') {
			status = true; // Switch status to true
		} 
		else {

			status = false;
		}
		return status;
	}
	/*
	 * chkOperands - check for valid operands if operator is valid method returns true
	 */
	private static boolean chkOperands(char c) {
		boolean status = false;

		if (c >= '0' && c <= '9') {
			status = true; // Switch status to true
		} 
		else {
			status = false;
		}
		return status;
	}
}
