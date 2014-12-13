package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class CanonicalScanPathOutsideBuildPathExceptionTest
{
    @Test
    public void exception()
    {
        CanonicalScanPathOutsideBuildPathException ex
                = new CanonicalScanPathOutsideBuildPathException("scan", "build");
        assertEquals("Scan path scan outside of build directory build", ex.getMessage());
    }
}
