package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;
import org.apache.tools.ant.DirectoryScanner;

import org.junit.Test;

public class DirectoryScannerFactoryTest
{
    @Test
    public void factory()
    {
        String[] includes = {"foo", "bar"};
        String[] excludes = {"baz"};
        DirectoryScanner ds = DirectoryScannerFactory.factory(".", includes, excludes);

        assertEquals(DirectoryScanner.class, ds.getClass());
        assertEquals(true, ds.isCaseSensitive());
        assertEquals(false, ds.isFollowSymlinks());
    }
}
