package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class AbsoluteScanPathOutsideBuildPathExceptionTest
{
    @Test
    public void exception()
    {
        AbsoluteScanPathOutsideBuildPathException ex
                = new AbsoluteScanPathOutsideBuildPathException("scan", "build");
        assertEquals("Scan path scan outside of build directory build", ex.getMessage());
    }
}
