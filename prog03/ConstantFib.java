package prog03;

/**
 *
 * @author IEG
 */
public class ConstantFib extends PowerFib {
    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double o (int n) {
        //return Math.log(n);
    		return 1;
    }

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
    		double answer = Math.pow(x, n);
    		return answer;
    	/*if (n <= 0)
	    return 1;
	double y = pow(x, n / 2);
	if (n % 2 == 0)
	    return y * y;
	else
	    return x * y * y;*/
    }
}
