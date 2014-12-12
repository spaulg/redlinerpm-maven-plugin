package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class SigningKeyFileNotFoundExceptionTest extends TestCase
{
    public void testException()
    {
        SigningKeyFileNotFoundException exception = new SigningKeyFileNotFoundException("keyfile");
        assertEquals("Signing key keyfile could not be found", exception.getMessage());
    }
}
