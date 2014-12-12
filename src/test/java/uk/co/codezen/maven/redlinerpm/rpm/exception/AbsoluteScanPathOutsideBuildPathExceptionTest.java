package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class AbsoluteScanPathOutsideBuildPathExceptionTest extends TestCase
{
    public void testException()
    {
        AbsoluteScanPathOutsideBuildPathException ex
                = new AbsoluteScanPathOutsideBuildPathException("scan", "build");
        assertEquals("Scan path scan outside of build directory build", ex.getMessage());
    }
}
