package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class UnknownOperatingSystemExceptionTest extends TestCase
{
    public void testException()
    {
        UnknownOperatingSystemException ex
                = new UnknownOperatingSystemException("unknown");
        assertEquals("Unknown operating system 'unknown'", ex.getMessage());
    }
}
