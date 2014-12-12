package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import org.redline_rpm.payload.Directive;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RpmPackageRuleDirectiveTest
{
    private ArrayList<String> directiveList;

    @Before
    public void setUp()
    {
        this.directiveList = new ArrayList<String>();
    }

    @Test
    public void configDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("config");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_CONFIG, directive.flag() & Directive.RPMFILE_CONFIG);
        assertEquals(0, directive.flag() & Directive.RPMFILE_DOC);
    }

    @Test
    public void docDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("doc");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_DOC, directive.flag() & Directive.RPMFILE_DOC);
        assertEquals(0, directive.flag() & Directive.RPMFILE_ICON);
    }

    @Test
    public void iconDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("icon");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_ICON, directive.flag() & Directive.RPMFILE_ICON);
        assertEquals(0, directive.flag() & Directive.RPMFILE_MISSINGOK);
    }

    @Test
    public void missingOkDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("missingok");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_MISSINGOK, directive.flag() & Directive.RPMFILE_MISSINGOK);
        assertEquals(0, directive.flag() & Directive.RPMFILE_NOREPLACE);
    }

    @Test
    public void noReplaceDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("noreplace");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_NOREPLACE, directive.flag() & Directive.RPMFILE_NOREPLACE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_SPECFILE);
    }

    @Test
    public void specFileDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("specfile");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_SPECFILE, directive.flag() & Directive.RPMFILE_SPECFILE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_GHOST);
    }

    @Test
    public void ghostDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("ghost");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_GHOST, directive.flag() & Directive.RPMFILE_GHOST);
        assertEquals(0, directive.flag() & Directive.RPMFILE_LICENSE);
    }

    @Test
    public void licenseDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("license");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_LICENSE, directive.flag() & Directive.RPMFILE_LICENSE);
        assertEquals(0, directive.flag() & Directive.RPMFILE_README);
    }

    @Test
    public void readmeDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("readme");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_README, directive.flag() & Directive.RPMFILE_README);
        assertEquals(0, directive.flag() & Directive.RPMFILE_UNPATCHED);
    }

    @Test
    public void unpatchedDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("unpatched");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_UNPATCHED, directive.flag() & Directive.RPMFILE_UNPATCHED);
        assertEquals(0, directive.flag() & Directive.RPMFILE_PUBKEY);
    }

    @Test
    public void pubkeyDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("pubkey");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_PUBKEY, directive.flag() & Directive.RPMFILE_PUBKEY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_POLICY);
    }

    @Test
    public void policyDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("policy");
        Directive directive = RpmPackageRuleDirective.newDirective(this.directiveList);
        assertEquals(Directive.RPMFILE_POLICY, directive.flag() & Directive.RPMFILE_POLICY);
        assertEquals(0, directive.flag() & Directive.RPMFILE_DOC);
    }

    @Test
    public void multipleDirectives() throws InvalidRpmPackageRuleDirectiveException
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

    @Test(expected = InvalidRpmPackageRuleDirectiveException.class)
    public void invalidDirective() throws InvalidRpmPackageRuleDirectiveException
    {
        this.directiveList.add("invalid");
        RpmPackageRuleDirective.newDirective(this.directiveList);
    }
}
