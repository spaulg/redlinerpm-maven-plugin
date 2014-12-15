package uk.co.codezen.maven.redlinerpm.mojo;

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

public class ListFilesRpmMojoTest
{
    String testOutputPath;

    private ListFilesRpmMojo mojo;
    private RpmPackageRule packageRule;

    @Before
    public void setUp()
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");

        Build projectBuild = new Build();
        projectBuild.setDirectory(this.testOutputPath);

        MavenProject project = new MavenProject();
        project.setGroupId("uk.co.codezen");
        project.setArtifactId("listfilesmojo-artifact");
        project.setName("test");
        project.setVersion("1.0-SNAPSHOT");
        project.setUrl("http://www.example.com");
        project.setBuild(projectBuild);

        this.mojo = new ListFilesRpmMojo();
        this.mojo.setProject(project);

        List<RpmPackageRule> packageRules = new ArrayList<RpmPackageRule>();
        this.packageRule = new RpmPackageRule();
        packageRules.add(this.packageRule);

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
        List<String> includes = new ArrayList<String>();
        includes.add("**");
        packageRule.setIncludes(includes);

        this.mojo.execute();
    }

    @Test
    public void packageRpmMissedFiles() throws MojoExecutionException
    {
        List<String> includes = new ArrayList<String>();
        packageRule.setIncludes(includes);

        this.mojo.execute();
    }
}
