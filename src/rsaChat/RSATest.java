package rsaChat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

public class RSATest {

    @Test
    public void testCoprime() {
	// since it is randomly generate number, it is hard to test
	// so shall we delete this test?
    }

    @Test
    public void testGCD() {
	assertEquals(1, RSA.GCD(3, 8));
	assertEquals(3, RSA.GCD(3, 9));
	assertEquals(1, RSA.GCD(1, 0));
	assertEquals(RSA.GCD(100, 10), 10);
	assertEquals(RSA.GCD(10, 100), 10);
	assertEquals(RSA.GCD(100, 13), 1);
	assertEquals(RSA.GCD(100, 100), 100);
	assertEquals(RSA.GCD(13, 39), 13);
	assertEquals(RSA.GCD(120, 35), 5);
	assertEquals(RSA.GCD(1001, 77), 77);
	assertEquals(RSA.GCD(1001, 33), 11);
    }

    @Test
    public void testMod_inverse() {
	assertEquals(
		RSA.mod_inverse(BigInteger.valueOf(3), BigInteger.valueOf(17)),
		BigInteger.valueOf(6));
	assertEquals(RSA.mod_inverse(BigInteger.valueOf(55),
		BigInteger.valueOf(123)), BigInteger.valueOf(85));
	assertEquals(RSA.mod_inverse(BigInteger.valueOf(45),
		BigInteger.valueOf(223)), BigInteger.valueOf(114));
	assertEquals(
		RSA.mod_inverse(BigInteger.valueOf(2342),
			BigInteger.valueOf(92830457)),
		BigInteger.valueOf(75588250));
	assertEquals(
		RSA.mod_inverse(BigInteger.valueOf(3), BigInteger.valueOf(30)),
		BigInteger.valueOf(0));
    }

    @Test
    public void testTotient() {
	RSA rsa = new RSA();
	assertEquals(6, rsa.totient(9));
    }

    @Test
    public void testEndecrypt() {
	fail("Not yet implemented"); // TODO
    }

}