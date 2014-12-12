package uk.co.codezen.maven.redlinerpm.rpm;

import junit.framework.TestCase;

public class RpmPackageAssociationTest extends TestCase
{
    private RpmPackageAssociation association;

    public void setUp()
    {
        association = new RpmPackageAssociation();
    }

    public void testNameAccessors()
    {
        assertEquals(null, this.association.getName());
        this.association.setName("testname");
        assertEquals("testname", this.association.getName());
    }

    public void testUnassignedVersion()
    {
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    public void testLatestVersion()
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

    public void testSpecificVersion()
    {
        this.association.setVersion("1.2.3");
        assertEquals("1.2.3", this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    public void testMinVersionRange()
    {
        this.association.setVersion("[1.2.3,)");
        assertEquals(null, this.association.getVersion());
        assertEquals("1.2.3", this.association.getMinVersion());
        assertEquals(null, this.association.getMaxVersion());
    }

    public void testMaxVersionRange()
    {
        this.association.setVersion("[,1.2.3)");
        assertEquals(null, this.association.getVersion());
        assertEquals(null, this.association.getMinVersion());
        assertEquals("1.2.3", this.association.getMaxVersion());
    }

    public void testMinMaxVersionRange()
    {
        this.association.setVersion("[1.2.3,1.2.5)");
        assertEquals(null, this.association.getVersion());
        assertEquals("1.2.3", this.association.getMinVersion());
        assertEquals("1.2.5", this.association.getMaxVersion());
    }
}
