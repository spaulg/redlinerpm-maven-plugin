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
}
