package hr.fer.zemris.ppj.finite.automaton;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class BasicInputTest {

    @Test
    public void testEqualsStringString() {
        Assert.assertTrue(new BasicInput(" ").equals(new BasicInput(" ")));
    }

    @Test
    public void testEqualsStringChar() {
        Assert.assertTrue(new BasicInput(" ").equals(new BasicInput(' ')));
    }

    @Test
    public void testEqualsCharString() {
        Assert.assertTrue(new BasicInput(' ').equals(new BasicInput(" ")));
    }

    @Test
    public void testEqualsStringInt() {
        Assert.assertTrue(new BasicInput("1").equals(new BasicInput(1)));
    }

    @Test
    public void testEqualsIntString() {
        Assert.assertTrue(new BasicInput(1).equals(new BasicInput("1")));
    }

    @Test
    public void testEqualsIntChar() {
        Assert.assertTrue(new BasicInput(1).equals(new BasicInput('1')));
    }

    @Test
    public void testEqualsCharInt() {
        Assert.assertTrue(new BasicInput('1').equals(new BasicInput(1)));
    }

    @Test
    public void testNotEqualsCharInt() {
        Assert.assertFalse(new BasicInput('\0').equals(new BasicInput(0)));
    }

    @Test
    public void testNotEqualsStringNull() {
        Assert.assertFalse(new BasicInput(" ").equals(new BasicInput(null)));
    }
}
