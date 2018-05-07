package prog03;

/**
 *
 * @author vjm
 */
public class PowerFib implements Fib {
    /** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
    */
    public double fib (int n) {
	    	//return Math.log(n);
    		return (pow(g1, n) - pow(g2, n)) / sqrt5;
    }

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double o (int n) {
	return n;
    }

    protected double sqrt5 = Math.sqrt(5);
    protected double g1 = (1 + sqrt5) / 2;
    protected double g2 = (1 - sqrt5) / 2;

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
		/*if (n<=0)
			return 1;
		double y = pow(x, n/2);
		if(n%2 == 0)
			return y*y;
		else
			return x*y*y;*/
	    	double y = 1;
		for (int i = 0; i < n; i++)
		    y *= x;
		return y;
    }
}
