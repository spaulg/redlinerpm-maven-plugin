package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class DuplicateRpmArtifactExceptionTest
{
    @Test
    public void exception()
    {
        DuplicateRpmArtifactException exception = new DuplicateRpmArtifactException("artifact");
        assertEquals("The RPM artifact artifact already exists.", exception.getMessage());
    }
}
