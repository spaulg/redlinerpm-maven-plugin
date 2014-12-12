
/*
    Copyright 2014 Simon Paulger <spaulger@codezen.co.uk>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package uk.co.codezen.maven.redlinerpm.mojo;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.model.License;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.DirectoryScanner;
import uk.co.codezen.maven.redlinerpm.rpm.DirectoryScannerFactory;
import uk.co.codezen.maven.redlinerpm.rpm.RpmScriptTemplateRenderer;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;

import java.io.File;
import java.util.*;

/**
 * Abstract RPM.
 * Contains the properties needed for customising
 * the build within the pom.xml.
 */
abstract public class AbstractRpmMojo extends AbstractMojo implements RpmMojo
{
    /**
     * Set of master files (all files in build path)
     */
    protected Set<String> masterFiles = new HashSet<String>();

    /**
     * Event hook template renderer
     */
    protected RpmScriptTemplateRenderer templateRenderer = null;

    /**
     * Maven project
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project = null;

    /**
     * Build path
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}")
    protected String buildPath = null;

    /**
     * RPM package declarations from configuration
     */
    @Parameter
    protected List<RpmPackage> packages = new ArrayList<RpmPackage>();

    /**
     * Default mode
     */
    @Parameter
    protected int defaultFileMode = 0644;

    /**
     * Default owner
     */
    @Parameter
    protected String defaultOwner = "root";

    /**
     * Default group
     */
    @Parameter
    protected String defaultGroup = "root";

    /**
     * Default installation destination
     */
    @Parameter
    protected String defaultDestination = File.separator;

    /**
     * List of file exclude patterns
     */
    @Parameter
    protected List<String> excludes = new ArrayList<String>();

    /**
     * Perform checking for extra files not included within any packages,
     * or excluded from all packages
     */
    @Parameter
    protected boolean performCheckingForExtraFiles = true;

    /**
     * Set Maven project
     *
     * @param project Maven project
     */
    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    /**
     * Get Maven project
     *
     * @return Maven project
     */
    protected MavenProject getProject()
    {
        return project;
    }

    /**
     * Get event hook template renderer
     *
     * @return Event hook template renderer
     */
    @Override
    public RpmScriptTemplateRenderer getTemplateRenderer()
    {
        if (null == this.templateRenderer) {
            this.templateRenderer = new RpmScriptTemplateRenderer();

            // Configure template renderer with objects typically found in maven
            this.templateRenderer.addParameter("project", this.getProject());
            this.templateRenderer.addParameter("env", System.getenv());

            Properties systemProperties = System.getProperties();
            for (String propertyName : systemProperties.stringPropertyNames()) {
                this.templateRenderer.addParameter(propertyName, systemProperties.getProperty(propertyName));
            }

            Properties projectProperties = this.getProject().getProperties();
            for (String propertyName : projectProperties.stringPropertyNames()) {
                this.templateRenderer.addParameter(propertyName, projectProperties.getProperty(propertyName));
            }
        }

        return this.templateRenderer;
    }

    /**
     * Get the project artifact id
     *
     * @return Artifact id
     */
    @Override
    public String getProjectArtifactId()
    {
        return this.getProject().getArtifactId();
    }

    /**
     * Get the project version
     *
     * @return Project version
     */
    @Override
    public String getProjectVersion()
    {
        return this.getProject().getVersion();
    }

    /**
     * Get the project url
     *
     * @return Project url
     */
    @Override
    public String getProjectUrl()
    {
        return this.getProject().getUrl();
    }

    /**
     * Get collapsed project licensing
     *
     * @return Project licenses, collapsed in to a single line, separated by commas.
     */
    @Override
    public String getCollapsedProjectLicense()
    {
        String collapsedLicenseList = "";

        for (License license : this.getProject().getLicenses()) {
            if (collapsedLicenseList.equals("")) {
                collapsedLicenseList = license.getName();
            }
            else {
                collapsedLicenseList += ", " + collapsedLicenseList;
            }
        }

        return (collapsedLicenseList.length() > 0 ? collapsedLicenseList : null);
    }

    /**
     * Get build output directory
     *
     * @return Build output directory
     */
    @Override
    public String getBuildDirectory()
    {
        String directory = this.project.getBuild().getDirectory();

        if (null == directory) {
            // Directory is not set, default to target
            directory = "target";
            this.project.getBuild().setDirectory(directory);
        }

        return directory;
    }

    /**
     * Set the primary artifact
     *
     * @param artifactFile Primary artifact
     * @param classifier Artifact classifier
     */
    @Override
    public void setPrimaryArtifact(File artifactFile, String classifier)
    {
        DefaultArtifactHandler handler = new DefaultArtifactHandler();
        handler.setExtension("rpm");

        Artifact artifact = new DefaultArtifact(
                this.getProject().getGroupId(),
                this.getProject().getArtifactId(),
                this.getProject().getVersion(),
                null,
                "rpm",
                classifier,
                handler
        );

        artifact.setFile(artifactFile);

        this.getProject().setArtifact(artifact);
    }

    /**
     * Add a secondary artifact
     *
     * @param artifactFile Secondary artifact file
     * @param artifactId Artifact Id
     * @param version Artifact version
     * @param classifier Artifact classifier
     */
    @Override
    public void addSecondaryArtifact(File artifactFile, String artifactId, String version, String classifier)
    {
        DefaultArtifactHandler handler = new DefaultArtifactHandler();
        handler.setExtension("rpm");

        Artifact artifact = new DefaultArtifact(
                this.getProject().getGroupId(),
                artifactId,
                version,
                null,
                "rpm",
                classifier,
                handler
        );

        artifact.setFile(artifactFile);

        this.getProject().addAttachedArtifact(artifact);
    }

    /**
     * Set the build root path
     *
     * @param buildPath Build root path
     */
    public void setBuildPath(String buildPath)
    {
        if (null != buildPath && ! buildPath.endsWith(File.separator)) {
            buildPath += File.separator;
        }

        this.buildPath = buildPath;
    }

    /**
     * Get the build root path
     *
     * @return Build root path
     */
    @Override
    public String getBuildPath()
    {
        return this.buildPath;
    }

    /**
     * Set the RPM packages defined by the configuration
     *
     * @param packages List of RPM packages
     */
    public void setPackages(List<RpmPackage> packages)
    {
        // Push mojo in to each package
        for (RpmPackage rpmPackage : packages) {
            rpmPackage.setMojo(this);
        }

        this.packages = packages;
    }

    /**
     * Set default mode
     *
     * @param defaultFileMode Default mode
     */
    public void setDefaultFileMode(int defaultFileMode)
    {
        this.defaultFileMode = defaultFileMode;
    }

    /**
     * Get default mode
     *
     * @return Default mode
     */
    @Override
    public int getDefaultFileMode()
    {
        return this.defaultFileMode;
    }

    /**
     * Set default owner
     *
     * @param defaultOwner Default owner
     */
    public void setDefaultOwner(String defaultOwner)
    {
        this.defaultOwner = defaultOwner;
    }

    /**
     * Get default owner
     *
     * @return Default owner
     */
    @Override
    public String getDefaultOwner()
    {
        return this.defaultOwner;
    }

    /**
     * Set default group
     *
     * @param defaultGroup Default group
     */
    public void setDefaultGroup(String defaultGroup)
    {
        this.defaultGroup = defaultGroup;
    }

    /**
     * Get default group
     *
     * @return Default group
     */
    @Override
    public String getDefaultGroup()
    {
        return this.defaultGroup;
    }

    /**
     * Set default destination
     *
     * @param defaultDestination Default destination
     */
    public void setDefaultDestination(String defaultDestination)
    {
        // Sanity check to always end with a /
        if ( ! defaultDestination.endsWith(File.separator)) {
            defaultDestination += File.separator;
        }

        this.defaultDestination = defaultDestination;
    }

    /**
     * Get default destination
     *
     * @return Default destination
     */
    @Override
    public String getDefaultDestination()
    {
        return this.defaultDestination;
    }

    /**
     * Set the list of file exclude patterns
     *
     * @param excludes List of file exclude patterns
     */
    public void setExcludes(List<String> excludes)
    {
        this.excludes = excludes;
    }

    /**
     * Set ignore extra files
     *
     * @param performCheckingForExtraFiles Ignore extra files
     */
    public void setPerformCheckingForExtraFiles(boolean performCheckingForExtraFiles)
    {
        this.performCheckingForExtraFiles = performCheckingForExtraFiles;
    }

    /**
     * Get ignore extra files
     *
     * @return Ignore extra files
     */
    public boolean isPerformCheckingForExtraFiles()
    {
        return this.performCheckingForExtraFiles;
    }

    /**
     * Scan the build path for all files for inclusion in an RPM.
     *
     * Excludes are applied also. This is because it doesn't matter
     * if a file ends up being included within an RPM as the master list
     * is only for us to know which files have been missed by a packaging
     * rule.
     */
    protected void scanMasterFiles()
    {
        DirectoryScanner ds = DirectoryScannerFactory.factory(
                this.buildPath, null, this.excludes.toArray(new String[0]));
        ds.scan();

        // Place results in to master list
        String[] fileMatches = ds.getIncludedFiles();
        this.masterFiles = new HashSet<String>(fileMatches.length);

        Collections.addAll(this.masterFiles, fileMatches);
    }

    /**
     * Validate the Mojo parameters
     *
     * @throws MojoExecutionException
     */
    protected void validate() throws MojoExecutionException
    {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<AbstractRpmMojo>> constraintViolations = validator.validate(this);
//
//        if (constraintViolations.size() > 0) {
//            // Validation errors
//            String message = "";
//
//            for (ConstraintViolation<AbstractRpmMojo> violation : constraintViolations) {
//                message += String.format("'%s' %s.", violation.getPropertyPath(), violation.getMessage());
//            }
//
//            throw new MojoExecutionException(String.format("Invalid parameter(s): %s", message));
//        }
    }
}
