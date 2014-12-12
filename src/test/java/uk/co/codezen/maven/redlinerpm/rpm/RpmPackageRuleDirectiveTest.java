package uk.co.codezen.maven.redlinerpm.rpm;

import junit.framework.TestCase;
import org.redline_rpm.payload.Directive;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import java.util.ArrayList;

public class RpmPackageRuleDirectiveTest extends TestCase
{
    private ArrayList<String> directiveList;

    public void setUp()
    {
        this.directiveList = new ArrayList<String>();
    }

    public void testConfigDirective() throws Exception
    {
        this.directiveList.add("config");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_CONFIG, directive.flag() & Directive.RPMFILE_CONFIG);
        assertEquals(0, directive.flag() & Directive.RPMFILE_DOC);
    }

    public void testDocDirective() throws Exception
    {
        this.directiveList.add("doc");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_DOC, directive.flag() & Directive.RPMFILE_DOC);
        assertEquals(0, directive.flag() & Directive.RPMFILE_ICON);
    }

    public void testIconDirective() throws Exception
    {
        this.directiveList.add("icon");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_ICON, directive.flag() & Directive.RPMFILE_ICON);
        assertEquals(0, directive.flag() & Directive.RPMFILE_MISSINGOK);
    }

    public void testMissingOkDirective() throws Exception
    {
        this.directiveList.add("missingok");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_MISSINGOK, directive.flag() & Directive.RPMFILE_MISSINGOK);
        assertEquals(0, directive.flag() & Directive.RPMFILE_NOREPLACE);
    }

    public void testNoReplaceDirective() throws Exception
    {
        this.directiveList.add("noreplace");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_NOREPLACE, directive.flag() & Directive.RPMFILE_NOREPLACE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_SPECFILE);
    }

    public void testSpecFileDirective() throws Exception
    {
        this.directiveList.add("specfile");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_SPECFILE, directive.flag() & Directive.RPMFILE_SPECFILE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_GHOST);
    }

    public void testGhostDirective() throws Exception
    {
        this.directiveList.add("ghost");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_GHOST, directive.flag() & Directive.RPMFILE_GHOST);
        assertEquals(0, directive.flag() & Directive.RPMFILE_LICENSE);
    }

    public void testLicenseDirective() throws Exception
    {
        this.directiveList.add("license");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_LICENSE, directive.flag() & Directive.RPMFILE_LICENSE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_README);
    }

    public void testReadmeDirective() throws Exception
    {
        this.directiveList.add("readme");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_README, directive.flag() & Directive.RPMFILE_README);
        assertEquals(0, directive.flag() & Directive.RPMFILE_UNPATCHED);
    }

    public void testUnpatchedDirective() throws Exception
    {
        this.directiveList.add("unpatched");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_UNPATCHED, directive.flag() & Directive.RPMFILE_UNPATCHED);
        assertEquals(0, directive.flag() & Directive.RPMFILE_PUBKEY);
    }

    public void testPubkeyDirective() throws Exception
    {
        this.directiveList.add("pubkey");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_PUBKEY, directive.flag() & Directive.RPMFILE_PUBKEY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_POLICY);
    }

    public void testPolicyDirective() throws Exception
    {
        this.directiveList.add("policy");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_POLICY, directive.flag() & Directive.RPMFILE_POLICY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_DOC);
    }

    public void testMultipleDirectives() throws Exception
    {
        this.directiveList.add("config");
        this.directiveList.add("noreplace");
        this.directiveList.add("license");
        this.directiveList.add("readme");

        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_CONFIG, directive.flag() & Directive.RPMFILE_CONFIG);
        assertEquals(Directive.RPMFILE_NOREPLACE, directive.flag() & Directive.RPMFILE_NOREPLACE);
        assertEquals(Directive.RPMFILE_LICENSE, directive.flag() & Directive.RPMFILE_LICENSE);
        assertEquals(Directive.RPMFILE_README, directive.flag() & Directive.RPMFILE_README);

        assertEquals(0, directive.flag() & Directive.RPMFILE_UNPATCHED);
        assertEquals(0, directive.flag() & Directive.RPMFILE_PUBKEY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_POLICY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_DOC);
    }

    public void testInvalidDirective() throws Exception
    {
        this.directiveList.add("invalid");
        boolean exceptionThrown = false;

        try {
            RpmPackageRuleDirective.newDirective(this.directiveList);
        }
        catch(InvalidRpmPackageRuleDirectiveException ex)
        {
            exceptionThrown = true;
        }

        assertEquals(true, exceptionThrown);
    }
}
