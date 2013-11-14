package test.com.gmail.sleepy771.console;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gmail.sleepy771.console.Command;
import com.gmail.sleepy771.console.Console;
import com.gmail.sleepy771.console.SyntaxException;

public class ConsoleTest {
    private Console c = new Console();
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
	c.put("lscmd", new Command(){
	    @Override
	    public String execute() throws SyntaxException {
		return c.commandSet().toString();
	    }

	    @Override
	    public void addArguments(LinkedList<String> stack)
		    throws SyntaxException {	
	    }
	});
	c.put("sub", new Command(){
	    private double a,b;
	    @Override
	    public String execute() throws SyntaxException {
		try{
		    return String.valueOf(a-b);
		} finally {
		    cleanup();
		}
	    }

	    @Override
	    public void addArguments(LinkedList<String> stack)
		    throws SyntaxException {
		if(stack.size()<2)
		    throw new SyntaxException();
		try {
		    this.a = Double.parseDouble(stack.removeLast());
		    this.b = Double.parseDouble(stack.removeLast());
		} catch(NumberFormatException nfe) {
		    throw new SyntaxException();
		}
	    }
	    
	    private void cleanup(){
		a=0.;
		b=0.;
	    }
	    
	});
    }

    @After
    public void tearDown() throws Exception {
	
    }

    @Test
    public void testRun() {
	try {
	    assertEquals(c.commandSet().toString(), c.executeLine("lscmd"));
	    assertEquals(String.valueOf(-1.), c.executeLine("sub 2 3"));
	    assertEquals(String.valueOf(500.-400.), c.executeLine("sub 500 400"));
	    
	} catch (SyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test(expected=SyntaxException.class)
    public void testException() throws SyntaxException {
	c.executeLine("sub");
    }

}
