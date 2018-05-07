package prog03;

public class LinearFib implements Fib{
	
	/** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
    */
    public double fib (int n) {
    		double a=0.0, b=1.0, total=0.0;
    		for(int i = 0; i < n; i++) {
    			total = b;
    			b = a + b;
    			a = total;
   
    		}
    		return total;
    }

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
   public double o (int n) {
    		return n;
    }
	
	
}//end LinearFib