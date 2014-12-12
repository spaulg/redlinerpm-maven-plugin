package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class UnknownArchitectureExceptionTest extends TestCase
{
    public void testException()
    {
        UnknownArchitectureException ex
                = new UnknownArchitectureException("unknown");
        assertEquals("Unknown architecture 'unknown'", ex.getMessage());
    }
}
