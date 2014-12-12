package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class DuplicateRpmArtifactExceptionTest extends TestCase
{
    public void testException()
    {
        DuplicateRpmArtifactException exception = new DuplicateRpmArtifactException("artifact");
        assertEquals("The RPM artifact artifact already exists.", exception.getMessage());
    }
}
