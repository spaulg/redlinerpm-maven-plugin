package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class UnknownArchitectureExceptionTest
{
    @Test
    public void exception()
    {
        UnknownArchitectureException ex
                = new UnknownArchitectureException("unknown");
        assertEquals("Unknown architecture 'unknown'", ex.getMessage());
    }

    @Test
    public void exceptionWithCause()
    {
        Exception cause = new Exception("cause");
        UnknownArchitectureException ex
                = new UnknownArchitectureException("unknown", cause);
        assertEquals("Unknown architecture 'unknown'", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
