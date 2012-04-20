package rsaChat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Baichuan Li
 * @author Hui Jia
 * @version Apr. 12 2012
 */
class RSA {
    Scanner scan;

    public static void main(String[] args) {

	new RSA().run();
    }

    private void run() {
	scan = new Scanner(System.in);
	System.out.println("Enter the nth prime and the mth prime to compute:");
	int nth;
	int mth;
	try {
	    nth = scan.nextInt();
	    mth = scan.nextInt();
	} catch (Exception e) {
	    System.out.println("Input not valid integers. Program quit....");
	    return;
	}
	long nthPrime = 0;
	long mthPrime = 0;
	// already consider 2 as a prime, so count starts from 1
	int primeCount = 1;
	if (nth == 1) {
	    nthPrime = 2;
	}
	if (mth == 1) {
	    mthPrime = 2;
	}
	int i = 3;
	// computer the nth and mth prime number
	while (true) {
	    boolean prime = true;
	    for (int j = 2; j < Math.sqrt(i) + 1; j++) {
		if (i % j == 0) {
		    prime = false;
		    break;
		}
	    }
	    if (prime) {
		primeCount++;
		if (primeCount == nth) {
		    nthPrime = i;
		} else if (primeCount == mth) {
		    mthPrime = i;
		}
	    }
	    if (nthPrime != 0 && mthPrime != 0) {
		break;
	    }
	    i++;
	}

	BigInteger c = BigInteger.valueOf(nthPrime * mthPrime);
	BigInteger m = BigInteger.valueOf((nthPrime - 1) * (mthPrime - 1));

	BigInteger e = coprime(m);
	BigInteger d = mod_inverse(e, m);
	System.out.println(nth + "th prime = " + nthPrime + ", " + mth
		+ "th prime = " + mthPrime + ", c = " + c + ", m = " + m
		+ ", e = " + e + ", d = " + d + ", Public Key = (" + e + ", "
		+ c + "), Private Key = (" + d + ", " + c + ")");

	promptForEncrypt();
	promptForDecrypt();
    }

    /**
     * Get the "index"th prime number.
     * 
     * @param index
     * @return
     */
    public long getPrime(int index) {
	return 0;
    }

    /**
     * Prompt the user for encrypt.
     */
    public void promptForEncrypt() {
	System.out
		.println("Please enter the public key (e, c): first e, then c");
	BigInteger pubKey = scan.nextBigInteger();
	BigInteger c_key = scan.nextBigInteger();
	System.out.println("Please enter a sentence to encrypt");
	scan.nextLine();
	String input = scan.nextLine();
	for (int k = 0; k < input.length(); k++) {
	    // System.out.println("Input length is:" + input.length());
	    System.out.print(input.charAt(k) + " " + (int) input.charAt(k)
		    + " encrypt to: ");
	    BigInteger cipher = endecrypt(
		    BigInteger.valueOf((long) input.charAt(k)), pubKey, c_key);
	    System.out.println(cipher);
	}
    }

    public void promptForDecrypt() {
	System.out
		.println("Please enter the private key (d, c): first d, then c");
	BigInteger privateKey = scan.nextBigInteger();
	BigInteger c_key = scan.nextBigInteger();
	System.out
		.println("Enter next char cipher value as an int, type quit to quit");

	String input = scan.next();
	while (!input.equals("quit")) {
	    try {
		BigInteger cipher = new BigInteger(input);
		cipher = endecrypt(cipher, privateKey, c_key);
		System.out.println((char) cipher.longValue() + " " + cipher);
	    } catch (Exception e) {
		System.out
			.println("Input is not a valid int number. Program quit...");
		return;
	    }
	    input = scan.next();

	}

    }

    public static BigInteger coprime(BigInteger x) {

	Random rand = new Random();
	long y = rand.nextLong();
	while (GCD(x.longValue(), y) != 1) {
	    y = rand.nextInt();
	}
	return BigInteger.valueOf(y);

    }

    public static long GCD(long a, long b) {
	long r;
	long x = a;
	long y = b;
	while (y != 0) {
	    r = x % y;
	    x = y;
	    y = r;
	}
	return x;
    }

    // helper method for mod_inverse
    private static ArrayList<Long> extendedGCD(long a, long b) {
	ArrayList<Long> k = new ArrayList<Long>();
	ArrayList<Long> j = new ArrayList<Long>();
	ArrayList<Long> r = new ArrayList<Long>();
	ArrayList<Long> x = new ArrayList<Long>();
	ArrayList<Long> y = new ArrayList<Long>();
	ArrayList<Long> q = new ArrayList<Long>();
	ArrayList<Long> result = new ArrayList<Long>();
	long gcd = 0;
	int i = 0;
	int m = 0;
	k.add(b);
	j.add(a);
	r.add(a);

	if (a == b) {
	    gcd = a;
	    x.add(1l);
	    y.add(0l);
	} else {
	    while (r.get(i) != 0) {
		q.add(k.get(i) / j.get(i));
		r.add(k.get(i) - q.get(i) * j.get(i));
		k.add(j.get(i));
		j.add(r.get(i + 1));
		i++;
		gcd = j.get(i - 1);
	    }
	    x.add(1l);
	    y.add(0l);
	    i--;

	    while (i > 0) {
		y.add(x.get(m));
		x.add(y.get(m) - q.get(i - 1) * x.get(m));
		i--;
		m++;
	    }
	    result.add(gcd);
	    result.add(x.get(x.size() - 1));
	    result.add(y.get(y.size() - 1));

	}
	return result;

    }

    /**
     * @param base
     * @param m
     * @return
     */
    public static BigInteger mod_inverse(BigInteger base, BigInteger m) {
	// Mod inverse is getting negative numbers? I think it's better to
	// change it to positive number in the range of (0, m)?
	// changed this into positive number
	long x = 0;
	long result = 0;
	if (GCD(base.longValue(), m.longValue()) == 1) {
	    x = extendedGCD(base.longValue(), m.longValue()).get(1);

	    result = x % m.longValue();
	}

	return BigInteger.valueOf(result);
    }

    /*
        // helper method for modulo
        private static ArrayList<Integer> int2baseTwo(int x) {
    	int q = x;
    	// what is the function of this k? Seems to me it's not necessary at
    	// all?
    	ArrayList<Integer> a = new ArrayList<Integer>();
    	while (q != 0) {
    	    a.add(q % 2);
    	    q = q / 2;
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
        */

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
    public static BigInteger endecrypt(BigInteger msg_or_cipher,
	    BigInteger key, BigInteger c) {
	return msg_or_cipher.modPow(key, c);
    }

    /**
     * Given public key and c_key, calculate and return the private key.
     * 
     * @param pubKey
     * @param cKey
     * @return Private Key.
     */
    public static long bruteDecrpt(long pubKey, long cKey) {
	int i = 2;

	while (true) {
	    boolean prime = true;
	    for (int j = 2; j < Math.sqrt(i) + 1; j++) {
		if (i % j == 0) {
		    prime = false;
		    break;
		}
	    }

	    if (prime) {
		if ((cKey % i) == 0) {
		    boolean found = true;
		    for (int k = 2; k < Math.sqrt(i) + 1; k++) {
			if (i % k == 0) {
			    found = false;
			    break;
			}
		    }
		    if (found) {
			long onePrime = i;
			long anotherPrime = cKey / i;
			long m = (onePrime - 1) * (anotherPrime - 1);
			// BigInteger e = coprime(BigInteger.valueOf(m));
			// System.out.println("one primer is:" + onePrime
			// + " and another prime is:" + anotherPrime);
			/*
			while (e.longValue() != pubKey) {
			    e = coprime(BigInteger.valueOf(m));
			}
			*/

			BigInteger d = mod_inverse(BigInteger.valueOf(pubKey),
				BigInteger.valueOf(m));
			return d.longValue();

		    }
		}
	    }

	    i++;
	}
    }
}