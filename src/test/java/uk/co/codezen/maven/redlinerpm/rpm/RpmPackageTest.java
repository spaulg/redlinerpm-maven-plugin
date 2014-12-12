package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import org.apache.maven.project.MavenProject;
import org.redline_rpm.header.Architecture;
import org.redline_rpm.header.Os;
import uk.co.codezen.maven.redlinerpm.mojo.PackageRpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.UnknownArchitectureException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.UnknownOperatingSystemException;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RpmPackageTest
{
    private RpmPackage rpmPackage;

    @Before
    public void setUp()
    {
        this.rpmPackage = new RpmPackage();

        MavenProject project = new MavenProject();
        project.setArtifactId("test-artifact");
        project.setName("test");
        project.setVersion("1.0");

        PackageRpmMojo mojo = new PackageRpmMojo();
        mojo.setProject(project);
        this.rpmPackage.setMojo(mojo);
    }

    @Test
    public void nameAccessors()
    {
        assertEquals("test-artifact", this.rpmPackage.getName());
        this.rpmPackage.setName("name");
        assertEquals("name", this.rpmPackage.getName());
    }

    @Test
    public void versionAccessors()
    {
        assertEquals("1.0", this.rpmPackage.getVersion());
        assertEquals("1.0", this.rpmPackage.getProjectVersion());
        this.rpmPackage.setVersion("2.0");
        assertEquals("2.0", this.rpmPackage.getVersion());
        assertEquals("2.0", this.rpmPackage.getProjectVersion());

        this.rpmPackage.setVersion("2.0-SNAPSHOT");
        assertEquals("2.0.SNAPSHOT", this.rpmPackage.getVersion());
        assertEquals("2.0-SNAPSHOT", this.rpmPackage.getProjectVersion());

        this.rpmPackage.setVersion(null);
        assertEquals("1.0", this.rpmPackage.getVersion());
        assertEquals("1.0", this.rpmPackage.getProjectVersion());

        this.rpmPackage.setVersion("");
        assertEquals("1.0", this.rpmPackage.getVersion());
        assertEquals("1.0", this.rpmPackage.getProjectVersion());
    }

    @Test
    public void releaseAccessors()
    {
        assertTrue(this.rpmPackage.getRelease().matches("\\d+"));
        this.rpmPackage.setRelease("release");
        assertEquals("release", this.rpmPackage.getRelease());
    }

    @Test
    public void finalNameAccessors()
    {
        this.rpmPackage.setName("name");
        this.rpmPackage.setVersion("1.0-SNAPSHOT");
        this.rpmPackage.setRelease("3");

        assertEquals("name-1.0.SNAPSHOT-3.noarch.rpm", this.rpmPackage.getFinalName());
        this.rpmPackage.setFinalName("finalname");
        assertEquals("finalname", this.rpmPackage.getFinalName());
    }

    @Test
    public void dependenciesAccessors()
    {
        List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getDependencies());
        this.rpmPackage.setDependencies(dependencies);
        assertEquals(dependencies, this.rpmPackage.getDependencies());
    }

    @Test
    public void obsoletesAccessors()
    {
        List<RpmPackageAssociation> obsoletes = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getObsoletes());
        this.rpmPackage.setObsoletes(obsoletes);
        assertEquals(obsoletes, this.rpmPackage.getObsoletes());
    }

    @Test
    public void conflictsAccessors()
    {
        List<RpmPackageAssociation> conflicts = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getConflicts());
        this.rpmPackage.setConflicts(conflicts);
        assertEquals(conflicts, this.rpmPackage.getConflicts());
    }

    @Test
    public void urlAccessors()
    {
        assertEquals(null, this.rpmPackage.getUrl());
        this.rpmPackage.setUrl("http://www.example.com/foo");
        assertEquals("http://www.example.com/foo", this.rpmPackage.getUrl());
    }

    @Test
    public void groupAccessors()
    {
        assertEquals(null, this.rpmPackage.getGroup());
        this.rpmPackage.setGroup("group/subgroup");
        assertEquals("group/subgroup", this.rpmPackage.getGroup());
    }

    @Test
    public void licenseAccessors()
    {
        assertEquals(null, this.rpmPackage.getLicense());
        this.rpmPackage.setLicense("license");
        assertEquals("license", this.rpmPackage.getLicense());
    }

    @Test
    public void summaryAccessors()
    {
        assertEquals(null, this.rpmPackage.getSummary());
        this.rpmPackage.setSummary("summary");
        assertEquals("summary", this.rpmPackage.getSummary());
    }

    @Test
    public void descriptionAccessors()
    {
        assertEquals(null, this.rpmPackage.getDescription());
        this.rpmPackage.setDescription("description");
        assertEquals("description", this.rpmPackage.getDescription());
    }

    @Test
    public void distributionAccessors()
    {
        assertEquals(null, this.rpmPackage.getDistribution());
        this.rpmPackage.setDistribution("distribution");
        assertEquals("distribution", this.rpmPackage.getDistribution());
    }

    @Test
    public void architectureAccessors() throws UnknownArchitectureException
    {
        assertEquals(Architecture.NOARCH, this.rpmPackage.getArchitecture());
        this.rpmPackage.setArchitecture("SPARC");
        assertEquals(Architecture.SPARC, this.rpmPackage.getArchitecture());
    }

    @Test(expected = UnknownArchitectureException.class)
    public void architectureInvalidException() throws UnknownArchitectureException
    {
        this.rpmPackage.setArchitecture("NONEXISTENT");
    }

    @Test(expected = UnknownArchitectureException.class)
    public void architectureBlankException() throws UnknownArchitectureException
    {
        this.rpmPackage.setArchitecture("");
    }

    @Test(expected = UnknownArchitectureException.class)
    public void architectureNullException() throws UnknownArchitectureException
    {
        this.rpmPackage.setArchitecture(null);
    }

    @Test
    public void operatingSystemAccessors() throws UnknownOperatingSystemException
    {
        assertEquals(Os.LINUX, this.rpmPackage.getOperatingSystem());
        this.rpmPackage.setOperatingSystem("LINUX390");
        assertEquals(Os.LINUX390, this.rpmPackage.getOperatingSystem());
    }

    @Test(expected = UnknownOperatingSystemException.class)
    public void operatingSystemInvalidException() throws UnknownOperatingSystemException
    {
        this.rpmPackage.setOperatingSystem("NONEXISTENT");
    }

    @Test(expected = UnknownOperatingSystemException.class)
    public void operatingSystemBlankException() throws UnknownOperatingSystemException
    {
        this.rpmPackage.setOperatingSystem("");
    }

    @Test(expected = UnknownOperatingSystemException.class)
    public void operatingSystemNullException() throws UnknownOperatingSystemException
    {
        this.rpmPackage.setOperatingSystem(null);
    }

    @Test
    public void buildHostNameAccessors() throws Exception
    {
        assertNotNull(this.rpmPackage.getBuildHostName());
        this.rpmPackage.setBuildHostName("buildhost");
        assertEquals("buildhost", this.rpmPackage.getBuildHostName());
    }

    @Test
    public void packagerAccessors()
    {
        assertEquals(null, this.rpmPackage.getPackager());
        this.rpmPackage.setPackager("packager");
        assertEquals("packager", this.rpmPackage.getPackager());
    }

    @Test
    public void attachAccessors()
    {
        assertEquals(true, this.rpmPackage.isAttach());
        this.rpmPackage.setAttach(false);
        assertEquals(false, this.rpmPackage.isAttach());
    }

    @Test
    public void classifierAccessors()
    {
        assertEquals(null, this.rpmPackage.getClassifier());
        this.rpmPackage.setClassifier("classifier");
        assertEquals("classifier", this.rpmPackage.getClassifier());
    }

    @Test
    public void rulesAccessors()
    {
        List<RpmPackageRule> rules = new ArrayList<RpmPackageRule>();
        rules.add(new RpmPackageRule());
        rules.add(new RpmPackageRule());

        this.rpmPackage.setRules(rules);
        assertEquals(rules, this.rpmPackage.getRules());

        this.rpmPackage.setRules(null);
        assertNull(this.rpmPackage.getRules());
    }

    @Test
    public void eventHookAccessors()
    {
        File scriptFile = new File("samplescript.sh");


        // pre transaction
        assertEquals(null, this.rpmPackage.getPreTransactionScriptFile());
        this.rpmPackage.setPreTransactionScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPreTransactionScriptFile());

        assertEquals(null, this.rpmPackage.getPreTransactionProgram());
        this.rpmPackage.setPreTransactionProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPreTransactionProgram());


        // pre install
        assertEquals(null, this.rpmPackage.getPreInstallScriptFile());
        this.rpmPackage.setPreInstallScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPreInstallScriptFile());

        assertEquals(null, this.rpmPackage.getPreInstallProgram());
        this.rpmPackage.setPreInstallProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPreInstallProgram());


        // post install
        assertEquals(null, this.rpmPackage.getPostInstallScriptFile());
        this.rpmPackage.setPostInstallScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPostInstallScriptFile());

        assertEquals(null, this.rpmPackage.getPostInstallProgram());
        this.rpmPackage.setPostInstallProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPostInstallProgram());


        // pre uninstall
        assertEquals(null, this.rpmPackage.getPreUninstallScriptFile());
        this.rpmPackage.setPreUninstallScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPreUninstallScriptFile());

        assertEquals(null, this.rpmPackage.getPreUninstallProgram());
        this.rpmPackage.setPreUninstallProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPreUninstallProgram());


        // post uninstall
        assertEquals(null, this.rpmPackage.getPostUninstallScriptFile());
        this.rpmPackage.setPostUninstallScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPostUninstallScriptFile());

        assertEquals(null, this.rpmPackage.getPostUninstallProgram());
        this.rpmPackage.setPostUninstallProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPostUninstallProgram());


        // post transaction
        assertEquals(null, this.rpmPackage.getPostTransactionScriptFile());
        this.rpmPackage.setPostTransactionScriptFile(scriptFile);
        assertEquals(scriptFile, this.rpmPackage.getPostTransactionScriptFile());

        assertEquals(null, this.rpmPackage.getPostTransactionProgram());
        this.rpmPackage.setPostTransactionProgram("/bin/sh");
        assertEquals("/bin/sh", this.rpmPackage.getPostTransactionProgram());
    }

    @Test
    public void triggerAccessors()
    {
        List<RpmTrigger> triggers = new ArrayList<RpmTrigger>();

        assertNotNull(this.rpmPackage.getTriggers());
        this.rpmPackage.setTriggers(triggers);
        assertEquals(triggers, this.rpmPackage.getTriggers());
    }

    @Test
    public void signingKeyAccessors()
    {
        assertEquals(null, this.rpmPackage.getSigningKey());
        this.rpmPackage.setSigningKey("key");
        assertEquals("key", this.rpmPackage.getSigningKey());

        assertEquals(null, this.rpmPackage.getSigningKeyId());
        this.rpmPackage.setSigningKeyId("id");
        assertEquals("id", this.rpmPackage.getSigningKeyId());

        assertEquals(null, this.rpmPackage.getSigningKeyPassPhrase());
        this.rpmPackage.setSigningKeyPassPhrase("passphrase");
        assertEquals("passphrase", this.rpmPackage.getSigningKeyPassPhrase());
    }

    @Test
    public void prefixesAccessors()
    {
        List<String> prefixes = new ArrayList<String>();

        assertNotNull(this.rpmPackage.getPrefixes());

        this.rpmPackage.setPrefixes(null);
        assertNotNull(this.rpmPackage.getPrefixes());

        this.rpmPackage.setPrefixes(prefixes);
        assertEquals(prefixes, this.rpmPackage.getPrefixes());
    }
}
