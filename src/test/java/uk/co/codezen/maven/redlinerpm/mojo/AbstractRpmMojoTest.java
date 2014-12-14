package uk.co.codezen.maven.redlinerpm.mojo;

import static org.junit.Assert.*;

import org.apache.maven.model.Build;
import org.apache.maven.model.License;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import uk.co.codezen.maven.redlinerpm.mocks.MockMojo;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;
import uk.co.codezen.maven.redlinerpm.rpm.RpmScriptTemplateRenderer;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidPathException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AbstractRpmMojoTest
{
    String testOutputPath;

    private MockMojo mojo;
    private MavenProject project;

    @Before
    public void setUp()
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");

        Build projectBuild = new Build();
        projectBuild.setDirectory("target");

        this.project = new MavenProject();
        this.project.setGroupId("uk.co.codezen");
        this.project.setArtifactId("test-artifact");
        this.project.setName("test");
        this.project.setVersion("1.0-SNAPSHOT");
        this.project.setUrl("http://www.example.com");
        this.project.setBuild(projectBuild);

        List<License> licenses = new ArrayList<License>();
        License license1 = new License();
        license1.setName("GPL");
        licenses.add(license1);

        License license2 = new License();
        license2.setName("LGPL");
        licenses.add(license2);

        this.project.setLicenses(licenses);

        this.mojo = new MockMojo();
        this.mojo.setProject(this.project);
    }

    @Test
    public void projectAccessors()
    {
        this.mojo.setProject(null);
        assertNull(this.mojo.getProject());

        this.mojo.setProject(this.project);
        assertEquals(this.project, this.mojo.getProject());

        assertEquals("test-artifact", this.mojo.getProjectArtifactId());
        assertEquals("1.0-SNAPSHOT", this.mojo.getProjectVersion());
        assertEquals("http://www.example.com", this.mojo.getProjectUrl());
        assertEquals("GPL, LGPL", this.mojo.getCollapsedProjectLicense());
        assertEquals("target", this.mojo.getBuildDirectory());
    }

    @Test
    public void templateRenderer() throws IOException
    {
        RpmScriptTemplateRenderer renderer = this.mojo.getTemplateRenderer();
        assertNotNull(renderer);

        // Render a template
        File templateScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/mojo/AbstractRpmMojo-template", this.testOutputPath));
        File expectedScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/mojo/AbstractRpmMojo-template-expected", this.testOutputPath));
        File actualScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/mojo/AbstractRpmMojo-template-actual", this.testOutputPath));

        assertEquals(false, actualScriptFile.exists());
        renderer.render(templateScriptFile, actualScriptFile);
        assertEquals(true, actualScriptFile.exists());

        FileReader reader;
        char[] buff = new char[1024];
        StringBuilder stringBuilder;
        int bytesRead;


        // Read the actual template
        stringBuilder = new StringBuilder();
        reader = new FileReader(actualScriptFile);

        while (-1 != (bytesRead = reader.read(buff))) {
            stringBuilder.append(buff, 0, bytesRead);
        }

        reader.close();

        String actualTemplateContents = stringBuilder.toString();


        // Read the expected template
        stringBuilder = new StringBuilder();
        reader = new FileReader(expectedScriptFile);

        while (-1 != (bytesRead = reader.read(buff))) {
            stringBuilder.append(buff, 0, bytesRead);
        }

        reader.close();

        String expectedTemplateContents = stringBuilder.toString();

        assertEquals(expectedTemplateContents, actualTemplateContents);

        // Confirm the same object is returned on subsequent calls
        assertEquals(renderer, this.mojo.getTemplateRenderer());
    }

    @Test
    public void projectArtifacts()
    {
        File artifact = new File(String.format(
            "%s/artifact.rpm",
            this.testOutputPath)
        );

        this.mojo.setPrimaryArtifact(artifact, "test");

        this.mojo.addSecondaryArtifact(artifact, "secondary-artifact", "1.0", "test");
        this.mojo.addSecondaryArtifact(artifact, "secondary-artifact", "1.0", null);

        assertNotNull(this.project.getArtifact());
        assertEquals(2, this.project.getAttachedArtifacts().size());
    }

    @Test
    public void buildPath() throws InvalidPathException
    {
        this.mojo.setBuildPath(this.testOutputPath);
        assertEquals(this.testOutputPath, this.mojo.getBuildPath());
    }

    @Test
    public void packages()
    {
        List<RpmPackage> packages = new ArrayList<RpmPackage>();
        packages.add(new RpmPackage());

        this.mojo.setPackages(packages);
        assertEquals(packages, this.mojo.getPackages());
    }

    @Test
    public void defaults()
    {
        assertEquals(0644, this.mojo.getDefaultFileMode());
        this.mojo.setDefaultFileMode(0755);
        assertEquals(0755, this.mojo.getDefaultFileMode());

        assertEquals("root", this.mojo.getDefaultOwner());
        this.mojo.setDefaultOwner("nobody");
        assertEquals("nobody", this.mojo.getDefaultOwner());

        assertEquals("root", this.mojo.getDefaultGroup());
        this.mojo.setDefaultGroup("nobody");
        assertEquals("nobody", this.mojo.getDefaultGroup());

        assertEquals(File.separator, this.mojo.getDefaultDestination());
        this.mojo.setDefaultDestination(String.format("%sdestination", File.separator));
        assertEquals(String.format("%sdestination", File.separator), this.mojo.getDefaultDestination());
    }

    @Test
    public void excludes()
    {
        List<String> excludes = new ArrayList<String>();
        this.mojo.setExcludes(excludes);
        assertEquals(excludes, this.mojo.getExcludes());
    }

    @Test
    public void checkingForExtraFiles()
    {
        this.mojo.setPerformCheckingForExtraFiles(true);
        assertTrue(this.mojo.isPerformCheckingForExtraFiles());

        this.mojo.setPerformCheckingForExtraFiles(false);
        assertFalse(this.mojo.isPerformCheckingForExtraFiles());
    }

    @Test
    public void scanMasterFiles()
    {
        this.mojo.setBuildPath(String.format("%s%sbuild", this.testOutputPath, File.separator));
        this.mojo.scanMasterFiles();

        Set<String> masterFiles = this.mojo.getMasterFiles();
        assertEquals(67, masterFiles.size());
    }

    @Test
    public void validate() throws MojoExecutionException
    {
        this.mojo.validate();
    }
}
