package uk.co.codezen.maven.redlinerpm.rpm.exception;

import static org.junit.Assert.*;
import org.junit.Test;

public class InvalidRpmPackageRuleDirectiveExceptionTest
{
    @Test
    public void exception()
    {
        InvalidRpmPackageRuleDirectiveException ex
                = new InvalidRpmPackageRuleDirectiveException("directive");
        assertEquals("RPM directive 'directive' invalid", ex.getMessage());
    }
}
