package rsaChat;

/**
 * @author Baichuan Li
 * @author Hui Jia
 * @version Apr. 12 2012
 */
public class RSA {
    //   public static int coprime(int x) {
    
	return 0;
	
    }
    
    public static int GCD(int a, int b) {
	int r;
	int x = a;
	int y = b;
	while(y != 0) {
	    r = x % y;
	    x = y;
	    y = r;
	}
	return x;
	
    }
}
