package uk.co.codezen.maven.redlinerpm.rpm;

import junit.framework.TestCase;
import org.apache.tools.ant.DirectoryScanner;

public class DirectoryScannerFactoryTest extends TestCase
{
    public void testFactory()
    {
        String[] includes = {"foo", "bar"};
        String[] excludes = {"baz"};
        DirectoryScanner ds = DirectoryScannerFactory.factory(".", includes, excludes);

        assertEquals(DirectoryScanner.class, ds.getClass());
        assertEquals(true, ds.isCaseSensitive());
        assertEquals(false, ds.isFollowSymlinks());
    }
}
