package uk.co.codezen.maven.redlinerpm.rpm;

import junit.framework.TestCase;
import org.apache.maven.project.MavenProject;
import org.redline_rpm.header.Architecture;
import org.redline_rpm.header.Os;
import uk.co.codezen.maven.redlinerpm.mojo.PackageRpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.UnknownArchitectureException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.UnknownOperatingSystemException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RpmPackageTest extends TestCase
{
    private RpmPackage rpmPackage;

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

    public void testNameAccessors()
    {
        assertEquals("test-artifact", this.rpmPackage.getName());
        this.rpmPackage.setName("name");
        assertEquals("name", this.rpmPackage.getName());
    }

    public void testVersionAccessors()
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

    public void testReleaseAccessors()
    {
        assertTrue(this.rpmPackage.getRelease().matches("\\d+"));
        this.rpmPackage.setRelease("release");
        assertEquals("release", this.rpmPackage.getRelease());
    }

    public void testFinalNameAccessors()
    {
        this.rpmPackage.setName("name");
        this.rpmPackage.setVersion("1.0-SNAPSHOT");
        this.rpmPackage.setRelease("3");

        assertEquals("name-1.0.SNAPSHOT-3.noarch.rpm", this.rpmPackage.getFinalName());
        this.rpmPackage.setFinalName("finalname");
        assertEquals("finalname", this.rpmPackage.getFinalName());
    }

    public void testDependenciesAccessors()
    {
        List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getDependencies());
        this.rpmPackage.setDependencies(dependencies);
        assertEquals(dependencies, this.rpmPackage.getDependencies());
    }

    public void testObsoletesAccessors()
    {
        List<RpmPackageAssociation> obsoletes = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getObsoletes());
        this.rpmPackage.setObsoletes(obsoletes);
        assertEquals(obsoletes, this.rpmPackage.getObsoletes());
    }

    public void testConflictsAccessors()
    {
        List<RpmPackageAssociation> conflicts = new ArrayList<RpmPackageAssociation>();

        assertNotNull(this.rpmPackage.getConflicts());
        this.rpmPackage.setConflicts(conflicts);
        assertEquals(conflicts, this.rpmPackage.getConflicts());
    }

    public void testUrlAccessors()
    {
        assertEquals(null, this.rpmPackage.getUrl());
        this.rpmPackage.setUrl("http://www.example.com/foo");
        assertEquals("http://www.example.com/foo", this.rpmPackage.getUrl());
    }

    public void testGroupAccessors()
    {
        assertEquals(null, this.rpmPackage.getGroup());
        this.rpmPackage.setGroup("group/subgroup");
        assertEquals("group/subgroup", this.rpmPackage.getGroup());
    }

    public void testLicenseAccessors()
    {
        assertEquals(null, this.rpmPackage.getLicense());
        this.rpmPackage.setLicense("license");
        assertEquals("license", this.rpmPackage.getLicense());
    }

    public void testSummaryAccessors()
    {
        assertEquals(null, this.rpmPackage.getSummary());
        this.rpmPackage.setSummary("summary");
        assertEquals("summary", this.rpmPackage.getSummary());
    }

    public void testDescriptionAccessors()
    {
        assertEquals(null, this.rpmPackage.getDescription());
        this.rpmPackage.setDescription("description");
        assertEquals("description", this.rpmPackage.getDescription());
    }

    public void testDistributionAccessors()
    {
        assertEquals(null, this.rpmPackage.getDistribution());
        this.rpmPackage.setDistribution("distribution");
        assertEquals("distribution", this.rpmPackage.getDistribution());
    }

    public void testArchitectureAccessors() throws Exception
    {
        boolean exceptionRaised;
        assertEquals(Architecture.NOARCH, this.rpmPackage.getArchitecture());
        this.rpmPackage.setArchitecture("SPARC");
        assertEquals(Architecture.SPARC, this.rpmPackage.getArchitecture());

        try {
            exceptionRaised = false;
            this.rpmPackage.setArchitecture("NONEXISTENT");
        }
        catch(UnknownArchitectureException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);

        try {
            exceptionRaised = false;
            this.rpmPackage.setArchitecture("");
        }
        catch(UnknownArchitectureException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);

        try {
            exceptionRaised = false;
            this.rpmPackage.setArchitecture(null);
        }
        catch(UnknownArchitectureException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);
    }

    public void testOperatingSystemAccessors() throws Exception
    {
        boolean exceptionRaised;
        assertEquals(Os.LINUX, this.rpmPackage.getOperatingSystem());
        this.rpmPackage.setOperatingSystem("LINUX390");
        assertEquals(Os.LINUX390, this.rpmPackage.getOperatingSystem());

        try {
            exceptionRaised = false;
            this.rpmPackage.setOperatingSystem("NONEXISTENT");
        }
        catch(UnknownOperatingSystemException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);

        try {
            exceptionRaised = false;
            this.rpmPackage.setOperatingSystem("");
        }
        catch(UnknownOperatingSystemException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);

        try {
            exceptionRaised = false;
            this.rpmPackage.setOperatingSystem(null);
        }
        catch(UnknownOperatingSystemException ex) {
            exceptionRaised = true;
        }

        assertEquals(true, exceptionRaised);
    }

    public void testBuildHostNameAccessors() throws Exception
    {
        assertNotNull(this.rpmPackage.getBuildHostName());
        this.rpmPackage.setBuildHostName("buildhost");
        assertEquals("buildhost", this.rpmPackage.getBuildHostName());
    }

    public void testPackagerAccessors()
    {
        assertEquals(null, this.rpmPackage.getPackager());
        this.rpmPackage.setPackager("packager");
        assertEquals("packager", this.rpmPackage.getPackager());
    }

    public void testAttachAccessors()
    {
        assertEquals(true, this.rpmPackage.isAttach());
        this.rpmPackage.setAttach(false);
        assertEquals(false, this.rpmPackage.isAttach());
    }

    public void testClassifierAccessors()
    {
        assertEquals(null, this.rpmPackage.getClassifier());
        this.rpmPackage.setClassifier("classifier");
        assertEquals("classifier", this.rpmPackage.getClassifier());
    }

    public void testRulesAccessors()
    {
        List<RpmPackageRule> rules = new ArrayList<RpmPackageRule>();
        rules.add(new RpmPackageRule());
        rules.add(new RpmPackageRule());

        this.rpmPackage.setRules(rules);
        assertEquals(rules, this.rpmPackage.getRules());

        this.rpmPackage.setRules(null);
        assertNull(this.rpmPackage.getRules());
    }

    public void testEventHookAccessors()
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

    public void testTriggerAccessors()
    {
        List<RpmTrigger> triggers = new ArrayList<RpmTrigger>();

        assertNotNull(this.rpmPackage.getTriggers());
        this.rpmPackage.setTriggers(triggers);
        assertEquals(triggers, this.rpmPackage.getTriggers());
    }

    public void testSigningKeyAccessors()
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

    public void testPrefixesAccessors()
    {
        List<String> prefixes = new ArrayList<String>();

        assertNotNull(this.rpmPackage.getPrefixes());

        this.rpmPackage.setPrefixes(null);
        assertNotNull(this.rpmPackage.getPrefixes());

        this.rpmPackage.setPrefixes(prefixes);
        assertEquals(prefixes, this.rpmPackage.getPrefixes());
    }
}
