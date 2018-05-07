package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibn;

	/** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n)
	 */
	public static double time (Fib fib, int n) {
		// Get the current time in nanoseconds.
		long start = System.nanoTime();

		// Calculate the n'th Fibonacci number.  Store the
		// result in fibn.
		fibn = fib.fib(n);

		// Get the current time in nanoseconds.
		long end = System.nanoTime();

		// Return the difference between the end time and the
		// start time divided by 1000.0 to convert to microseconds.
		return (end-start)/1000.0;
	}

	/** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
	 */
	public static double averageTime (Fib fib, int n, int ncalls) {
		double totalTime = 0;

		// Add up the total call time for ncalls calls to time (above).
		if(ncalls == 0)
			ncalls = 1;

		for(int i = 0; i < ncalls; i++) {
			totalTime += time(fib, n);
		}

		// Return the average time.
		return totalTime/ncalls;
	}

	/** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double accurateTime (Fib fib, int n) {
		// Get the time in microseconds using the time method above.
		double t = time(fib, n);

		// If the time is (equivalent to) more than a second, return it.
		if (t > 1e6)
			return t;

		// Estimate the number of calls that would add up to one second.
		// Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
		int numcalls = (int)(1e6 / t);

		// Get the average time using averageTime above and that many
		// calls and return it.
		return averageTime(fib, n, numcalls);
	}

	private static UserInterface ui = new GUI();

	public static void doExperiments (Fib fib) {
		boolean isNum = true;
		int n = 0; 
		double c = 0;

		while(true) {
			String num = ui.getInfo("Enter an Integer: ");
			if(num == null)
				break;
			else {
				try{
					n = Integer.parseInt(num);
					if (n < 0) {
						ui.sendMessage(n + " is a negative number... invalid");
						isNum = false;
						continue;
					}
					else 
						isNum = true;
				}
				catch (NumberFormatException e) {
					isNum = false;
				}
				
				
				/*if (n < 0) {
					ui.sendMessage(n + " is a negative number... invalid");
					//break;
				}*/
				/*else if(num.equals("") || isNum == false) {
					ui.sendMessage(num + " is not an integer");
					continue;
					//break;
				}*/
				
				
				if(num.equals("") || isNum == false) {
					ui.sendMessage(num + " is not an integer");
					continue;
					//break;
				}
				
				//else{
				if(isNum == true) {
					//ask if there should only be one message pop up or 2 like in his(i have 1 currently)
					double runTime = accurateTime(fib, n);
					
					//should the time i return be accurate time or just time?

					if (c != 0.0) {
						double estimate = c * fib.o(n);
						c = runTime/fib.o(n);
						double error = 100.0 * (estimate - runTime) / runTime;
						ui.sendMessage("n = " + n +
								"\nfib("+ n + ") = " + fib.fib(n) +
								"\nrunning time = " + runTime + "ms" +
								"\nestimated time = " + estimate + "ms" +
								"\nconstant = " + c +
								"\n" + error + "% error");
						
						//should calculate the estimated time and print, then have another screen which outputs the real time
					}
					else {
						c = runTime/fib.o(n); 
						ui.sendMessage("n = " + n +
								"\nfib("+ n + ") = " + fib.fib(n) +
								"\nrunning time = " + runTime + "ms" +
								"\nconstant = " + c);
					}

				}
			}
		}
	}//end doExperi(fib)	 



	public static void doExperiments () {
		String[] commands = {
				"MysteryFib",
				"ConstantFib",
				"LogFib",
				"LinearFib",
				"ExponentialFib",
		"EXIT"};

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:	//clicking the red x
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0://mysteryfib
				doExperiments(new MysteryFib());
				break;
			case 1://constantfib
				doExperiments(new ConstantFib());
				break;
			case 2://log fib
				doExperiments(new LogFib());
				break;
			case 3://linearfin
				doExperiments(new LinearFib());
				break;
			case 4://exponential fib
				doExperiments(new ExponentialFib());
				break;
			//case 5://hit exit button
			default:
				return;
			}
		}
	}

	static void labExperiments () {
		// Create (Exponential time) Fib object and test it.
		//Fib efib = new ExponentialFib();
		Fib efib = new LinearFib();
		//Fib efib = new LogFib();
		//Fib efib = new ConstantFib();
		//Fib efib = new PowerFib();
		System.out.println(efib);
		for (int i = 0; i < 11; i++)
			System.out.println(i + " " + efib.fib(i));

		// Determine running time for n1 = 20 and print it out.
		int n1 = 20;
		//int n1 = 1000;
		double time1 = averageTime(efib, n1, 1000);
		System.out.println("n1 " + n1 + " time1 " + time1);

		int ncalls = (int) (1e6/ time1 * time1);
		time1 = averageTime(efib, n1, ncalls);
		System.out.println("n1 " + n1 + " average time " + time1);
		time1 = accurateTime(efib, n1);
		System.out.println("n1 " + n1 + " accurate time " + time1);


		// Calculate constant:  time = constant times O(n).
		double c = time1 / efib.o(n1);
		System.out.println("c " + c);

		// Estimate running time for n2=30.
		int n2 = 30;
		double time2est = c * efib.o(n2);
		System.out.println("n2 " + n2 + " estimated time " + time2est);


		// Calculate actual running time for n2=30.
		double time2 = averageTime(efib, n2, 100);
		System.out.println("n2 " + n2 + " actual time " + time2);

		time2 = averageTime(efib, n2, ncalls);
		System.out.println("n2 " + n2 + "average time " + time2);
		time2 = accurateTime(efib, n2);
		System.out.println("n2 " + n2 + " accurate time " + time2);

		//estimate running time for n3 = 100
		int n3 = 100;
		double time3est = c *efib.o(n3);
		double years = time3est / 1e6/ 3600/ 24/365.25;
		System.out.println("n3 " + n3 + " estimated time " + time3est + " ms");
		System.out.println("n3 " + n3 + " estimated time " + years + "  years");

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main (String[] args) {
		doExperiments();

		labExperiments();
	}
}
