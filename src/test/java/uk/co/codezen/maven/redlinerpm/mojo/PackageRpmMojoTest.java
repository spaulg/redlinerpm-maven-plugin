package uk.co.codezen.maven.redlinerpm.mojo;

import static org.junit.Assert.*;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackageRule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageRpmMojoTest
{
    String testOutputPath;

    private PackageRpmMojo mojo;
    private MavenProject project;

    @Before
    public void setUp()
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");

        Build projectBuild = new Build();
        projectBuild.setDirectory(this.testOutputPath);

        this.project = new MavenProject();
        this.project.setGroupId("uk.co.codezen");
        this.project.setArtifactId("packagerpmmojo-artifact");
        this.project.setName("test");
        this.project.setVersion("1.0-SNAPSHOT");
        this.project.setUrl("http://www.example.com");
        this.project.setBuild(projectBuild);

        this.mojo = new PackageRpmMojo();
        this.mojo.setProject(this.project);

        // Setup rules
        List<String> includes = new ArrayList<String>();
        includes.add("**");

        List<RpmPackageRule> packageRules = new ArrayList<RpmPackageRule>();
        RpmPackageRule packageRule = new RpmPackageRule();
        packageRule.setIncludes(includes);
        packageRules.add(packageRule);

        // Setup packages
        RpmPackage rpmPackage = new RpmPackage();
        rpmPackage.setRules(packageRules);

        List<RpmPackage> packages = new ArrayList<RpmPackage>();
        packages.add(rpmPackage);

        // Configure with mojo
        this.mojo.setPackages(packages);
        this.mojo.setBuildPath(String.format("%s%sbuild", this.testOutputPath, File.separator));
    }

    @Test
    public void packageRpm() throws MojoExecutionException
    {
        this.mojo.execute();
        assertEquals(true, this.project.getArtifact().getFile().exists());
    }
}
