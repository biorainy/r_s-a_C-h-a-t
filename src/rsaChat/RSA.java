package rsaChat;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Baichuan Li
 * @author Hui Jia
 * @version Apr. 12 2012
 */
class RSA {

    public static int coprime(int x) {
	Random rand = new Random();
	int y = rand.nextInt();
	while (GCD(x, y) != 1) {
	    y = rand.nextInt();
	}
	return y;

    }

    public static int GCD(int a, int b) {
	int r;
	int x = a;
	int y = b;
	while (y != 0) {
	    r = x % y;
	    x = y;
	    y = r;
	}
	return x;
    }

    // helper method for mod_inverse
    private static ArrayList<Integer> extendedGCD(int a, int b) {
	ArrayList<Integer> k = new ArrayList<Integer>();
	ArrayList<Integer> j = new ArrayList<Integer>();
	ArrayList<Integer> r = new ArrayList<Integer>();
	ArrayList<Integer> x = new ArrayList<Integer>();
	ArrayList<Integer> y = new ArrayList<Integer>();
	ArrayList<Integer> q = new ArrayList<Integer>();
	ArrayList<Integer> result = new ArrayList<Integer>();
	int gcd = 0;
	int i = 0;
	int m = 0;
	k.add(b);
	j.add(a);
	r.add(a);

	if (a == b) {
	    gcd = a;
	    x.add(1);
	    y.add(0);
	} else {
	    while (r.get(i) != 0) {
		q.add(k.get(i) / j.get(i));
		r.add(k.get(i) - q.get(i) * j.get(i));
		k.add(j.get(i));
		j.add(r.get(i + 1));
		i++;
		gcd = j.get(i - 1);
	    }
	    x.add(1);
	    y.add(0);
	    i--;

	    while (i > 0) {
		y.add(x.get(m));
		x.add(y.get(m) - q.get(i - 1) * x.get(m));
		i--;
		m++;
		// int x1 = x.get(m);
		// int y1 = y.get(m);
	    }
	    result.add(gcd);
	    result.add(x.get(x.size() - 1));
	    result.add(y.get(y.size() - 1));

	}
	return result;

    }

    public static int mod_inverse(int base, int m) {
	int x = 0;
	if (GCD(base, m) == 1) {
	    x = extendedGCD(base, m).get(1);
	}
	return x % m;

    }

    // helper method for modulo
    private static ArrayList<Integer> int2baseTwo(int x) {
	int q = x;
	int k = 0;
	// what is the function of this k??? Seems to me it's not necessary at
	// all???
	ArrayList<Integer> a = new ArrayList<Integer>();
	while (q != 0) {
	    a.add(q % 2);
	    q = q / 2;
	    k++;
	}
	return a;

    }

    public static int modulo(int a, int b, int c) {
	ArrayList<Integer> baseA = int2baseTwo(b);
	int x = 1;
	int power = a % c;
	for (int i = 0; i < baseA.size(); i++) {
	    if (baseA.get(i) == 1)
		x = (x * power) % c;
	    power = (power * power) % c;
	}
	return x;

    }

    /**
     * Computer Euler's Totient. Euler's totient or phi function is an
     * arithmetic function that counts the number of positive integers less than
     * or equal to n that are relatively prime to n.
     * 
     * @param n
     *            The number n to be computed.
     * @return The number of positive integers relatively prime to n.
     */
    int totient(int n) {
	int count = 0;
	for (int i = 1; i < n; i++) {
	    if (GCD(i, n) == 1) {
		count++;
	    }
	}
	return count;
    }

    /**
     * Given an integer representing an ASCII character value, encrypt it via
     * the RSA crypto algorithm.
     * 
     * @param msg_or_cipher
     * @param key
     * @param c
     * @return
     */
    int endecrypt(int msg_or_cipher, int key, int c) {
	return 0;
    }
}
