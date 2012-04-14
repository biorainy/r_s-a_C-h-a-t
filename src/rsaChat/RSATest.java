package rsaChat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class RSATest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCoprime() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGCD() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public void testMod_inverse() {
	fail("Not yet implemented"); // TODO
    }

    @Test
    public void testModulo() {
	fail("Not yet implemented"); // TODO
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
