package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Calculator {
	Object[] tokenize (String s) {
		input = s;
		index = 0;
		Object[] out = new Object[s.length()];
		int n = 0;

		while (!atEndOfInput ()) {
			if (isNumber())
				out[n++] = getNumber();
			else
				out[n++] = s.charAt(index++);
		}

		Object[] out2 = new Object[n];
		System.arraycopy(out, 0, out2, 0, n);
		return out2;
	}

	String input = null;
	int index = 0;

	boolean atEndOfInput () {
		while (index < input.length() &&
				Character.isWhitespace(input.charAt(index)))
			index++;
		return index == input.length();
	}

	boolean isNumber () {
		return Character.isDigit(input.charAt(index));
	}

	double getNumber () {
		int index2 = index;
		while (index2 < input.length() &&
				(Character.isDigit(input.charAt(index2)) ||
						input.charAt(index2) == '.'))
			index2++;
		double d = 0;
		try {
			d = Double.parseDouble(input.substring(index, index2));
		} catch (Exception e) {
			System.out.println(e);
		}
		index = index2;
		return d;
	}

	char getOperator () {
		char op = input.charAt(index++);
		if (OPERATORS.indexOf(op) == -1)
			System.out.println("Operator " + op + " not recognized.");
		return op;
	}

	Stack<Double> numberStack = new Stack<Double>();
	Stack<Character> operatorStack = new Stack<Character>();

	String numberStackToString () {
		String s = "numberStack: ";
		Stack<Double> helperStack = new Stack<Double>();
		// EXERCISE
		// Put every element of numberStack into helperStack
		// You will need to use a loop.  What kind?
		// What condition? When can you stop moving elements out of numberStack?
		// What method do you use to take an element out of numberStack?
		// What method do you use to put that element into helperStack.
		while(!numberStack.empty()){
			helperStack.push(numberStack.pop());
		}

		while(!helperStack.empty()) {
			s = s + " " + numberStack.push(helperStack.pop());
		}

		return s;
	}

	String operatorStackToString () {
		String s = "operatorStack: ";
		Stack<Character> helperStack = new Stack<Character>();
		// EXERCISE
		while(!operatorStack.empty()) {
			helperStack.push(operatorStack.pop());
		}
		while(!helperStack.empty()) {    	
			s = s + " " + operatorStack.push(helperStack.pop());
		}

		return s;
	}

	static UserInterface ui = new GUI();

	void displayStacks () {
		ui.sendMessage(numberStackToString() + "\n" +
				operatorStackToString());
	}

	double evaluate (String expr) {
		// Clean up any previous bad input.
		while (!operatorStack.empty()) operatorStack.pop();
		while (!numberStack.empty()) numberStack.pop();

		// Read the new input.
		Object[] inputs = tokenize(expr);

		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] instanceof Double) {
				double x = (Double) inputs[i];
				numberStack.push(x);
				displayStacks();
			}
			else {
				char o = (Character) inputs[i];
				if(o == '-' && (i == 0 || ((inputs[i-1] instanceof Double) == false && (inputs[i-1].equals(')')) == false))) {
						o = 'u';
				}
				processOperator(o);
				//operatorStack.push(o);
				displayStacks();
			}
		}

		while(!operatorStack.empty()) {
			evaluateOperator();
		}
		

		return numberStack.pop();
		//return 0;
	}//end evaluate

	static final String OPERATORS = "()+-*/u^";
	static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3 ,4};
	int precedence (char op) {
		return PRECEDENCE[OPERATORS.indexOf(op)];
	}//end precedence

	double evaluateOperator(double a, char op, double b) {
		switch(op) {
		case '+': return a + b;
		case '-': return a - b;
		case '*': return a * b;
		case '/': return a / b;
		case '^': return Math.pow(a, b);
		}
		/*if(op == '+')
		 return a + b;
	 if(op == '-')
		 return a-b;
	 else if(op == '*')
		 return a * b;
	 else if(op == '/')
		 return a/b;
	 else if(op == '^')
		 return Math.pow(a, b);
	 else if(op == '%')
		 return a % b;
	 //what other functions?
	 else*/
		return 0.0; 
		//any other values
	}//end double evaluateOperator(a, op, b)


	void evaluateOperator() {
		char op;
		double a, b;
		if(operatorStack.peek() == 'u') {
			op = operatorStack.pop();
			a = numberStack.pop();
			numberStack.push(a * (-1));
		}
		else {
			op = operatorStack.pop();
			b = numberStack.pop();
			a = numberStack.pop();
			numberStack.push(evaluateOperator(a, op, b));
		}
		displayStacks();
	}//end evaluateOperator()


	void processOperator(char op) {
		if(op == '(' || op == 'u') {
			operatorStack.push(op);
		}
		else if(op == ')') {
			while(operatorStack.peek() != '(') 
				evaluateOperator();
			operatorStack.pop();

		}
	
		else {
			while(!operatorStack.empty() && precedence(operatorStack.peek()) >= precedence(op)) {
					evaluateOperator();  
			}
			operatorStack.push(op);
		}
	}//end processOperator



	public static void main (String[] args) {
		Calculator parser = new Calculator();

		while (true) {
			String line = ui.getInfo("Enter arithmetic expression or cancel.");
			if (line == null)
				return;

			try {
				double result = parser.evaluate(line);
				ui.sendMessage(line + " = " + result);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}



//if we see open parentheses, wepush and we push until we see closed parentheses, when we see a closed
//paretheses we pop and calculate until we see the open parentheses again and then its good








/*
 double evaluate (String expr) {
    // Clean up any previous bad input.
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();

    // Read the new input.
    Object[] inputs = tokenize(expr);

    for (int i = 0; i < inputs.length; i++) {
      if (inputs[i] instanceof Double) {
        double x = (Double) inputs[i];
        numberStack.push(x);
        displayStacks();
      }
      else {
        char o = (Character) inputs[i];
        processOperator(o);
        //operatorStack.push(o);
        displayStacks();
      }
    }

   while(!operatorStack.empty()) {
	   evaluateOperator();
   }

    return numberStack.pop();
    //return 0;
  }

  static final String OPERATORS = "()+-*/			/*			^";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3 };
  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }

  double evaluateOperator(double a, char op, double b) {
	 switch(op) {
	 case '+': return a + b;
	 case '-': return a - b;
	 case '*': return a * b;
	 case '/': return a / b;
	 case '^': return Math.pow(a, b);
	 }
	  /*if(op == '+')
		 return a + b;
	 if(op == '-')
		 return a-b;
	 else if(op == '*')
		 return a * b;
	 else if(op == '/')
		 return a/b;
	 else if(op == '^')
		 return Math.pow(a, b);
	 else if(op == '%')
		 return a % b;
	 //what other functions?
	 else*/						/*
		 return 0.0; 
		 //any other values
  }//end double evaluateOperator


  void evaluateOperator() {
	  char op = operatorStack.pop();
	  double b = numberStack.pop();
	  double a = numberStack.pop();
	  numberStack.push(evaluateOperator(a, op, b));
	  displayStacks();
  }


  void processOperator(char op) {
	  while(!operatorStack.empty() && precedence(operatorStack.peek()) >= precedence(op)) {
		  evaluateOperator();  
	  }
	  operatorStack.push(op);
  }
	  */
