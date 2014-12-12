package uk.co.codezen.maven.redlinerpm.rpm.exception;

import junit.framework.TestCase;

public class InvalidRpmPackageRuleDirectiveExceptionTest extends TestCase
{
    public void testException()
    {
        InvalidRpmPackageRuleDirectiveException ex
                = new InvalidRpmPackageRuleDirectiveException("directive");
        assertEquals("RPM directive 'directive' invalid", ex.getMessage());
    }
}
