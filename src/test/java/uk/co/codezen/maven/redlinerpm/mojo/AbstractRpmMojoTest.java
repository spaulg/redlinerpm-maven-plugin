package uk.co.codezen.maven.redlinerpm.mojo;

import static org.junit.Assert.*;

import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import uk.co.codezen.maven.redlinerpm.mocks.MockMojo;

public class AbstractRpmMojoTest
{
    private MockMojo mojo;
    private MavenProject project;

    @Before
    public void setUp()
    {
        this.project = new MavenProject();
        this.project.setArtifactId("test-artifact");
        this.project.setName("test");
        this.project.setVersion("1.0");

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
    }

    /*

    setProject
    getProject

    getTemplateRenderer

    getProjectArtifactId
    getProjectVersion
    getProjectUrl
    getCollapsedProjectLicense

    getBuildDirectory

    setPrimaryArtifact
    addSecondaryArtifact

    setBuildPath
    getBuildPath

    setPackages

    setDefaultFileMode
    getDefaultFileMode

    setDefaultOwner
    getDefaultOwner

    setDefaultGroup
    getDefaultGroup

    setDefaultDestination
    getDefaultDestination

    setExcludes

    setPerformCheckingForExtraFiles
    isPerformCheckingForExtraFiles

    scanMasterFiles

     */
}
