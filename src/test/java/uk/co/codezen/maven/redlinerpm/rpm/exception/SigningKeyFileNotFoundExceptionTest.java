package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class SigningKeyFileNotFoundExceptionTest
{
    @Test
    public void testException()
    {
        SigningKeyFileNotFoundException exception = new SigningKeyFileNotFoundException("keyfile");
        assertEquals("Signing key keyfile could not be found", exception.getMessage());
    }
}
