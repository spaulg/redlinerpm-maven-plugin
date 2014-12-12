package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RpmPackageAssociationTest
{
    private RpmPackageAssociation association;

    @Before
    public void setUp()
    {
        association = new RpmPackageAssociation();
    }

    @Test
    public void nameAccessors()
    {
        assertEquals(null, this.association.getName());
        this.association.setName("testname");
        assertEquals("testname", this.association.getName());
    }

    @Test
    public void unassignedVersion()
    {
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    @Test
    public void latestVersion()
    {
        this.association.setVersion(null);
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());

        this.association.setVersion("");
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());

        this.association.setVersion("RELEASE");
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    @Test
    public void specificVersion()
    {
        this.association.setVersion("1.2.3");
        assertEquals("1.2.3", this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    @Test
    public void minVersionRange()
    {
        this.association.setVersion("[1.2.3,)");
        assertEquals(null, this.association.getVersion());
        assertEquals("1.2.3", this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    @Test
    public void maxVersionRange()
    {
        this.association.setVersion("[,1.2.3)");
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals("1.2.3", this.association.getMaxVersion());
    }

    @Test
    public void minMaxVersionRange()
    {
        this.association.setVersion("[1.2.3,1.2.5)");
        assertEquals(null, this.association.getVersion());
        assertEquals("1.2.3", this.association.getMinVersion());
        assertEquals("1.2.5", this.association.getMaxVersion());
    }
}
