
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

import org.apache.maven.plugin.logging.Log;
import uk.co.codezen.maven.redlinerpm.rpm.RpmScriptTemplateRenderer;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidPathException;

import java.io.File;

/**
 * Plugin mojo implementation
 */
public interface RpmMojo
{
    /**
     * Get event hook template renderer
     *
     * @return Event hook template renderer
     */
    public RpmScriptTemplateRenderer getTemplateRenderer();

    /**
     * Set the primary artifact
     *
     * @param artifactFile Primary artifact
     * @param classifier Artifact classifier
     */
    public void setPrimaryArtifact(File artifactFile, String classifier);

    /**
     * Add a secondary artifact
     *
     * @param artifactFile Secondary artifact file
     * @param artifactId Artifact Id
     * @param version Artifact version
     * @param classifier Artifact classifier
     */
    public void addSecondaryArtifact(File artifactFile, String artifactId, String version, String classifier);

    /**
     * Get build output directory
     *
     * @return Build output directory
     */
    public String getBuildDirectory();

    /**
     * Get the project artifact id
     *
     * @return Artifact id
     */
    public String getProjectArtifactId();

    /**
     * Get the project version
     *
     * @return Project version
     */
    public String getProjectVersion();

    /**
     * Get the project url
     *
     * @return Project url
     */
    public String getProjectUrl();

    /**
     * Get project packaging type
     *
     * @return Packaging type
     */
    public String getProjectPackagingType();

    /**
     * Get collapsed project licensing
     *
     * @return Project licenses, collapsed in to a single line, separated by commas.
     */
    public String getCollapsedProjectLicense();

    /**
     * Get the build root path
     *
     * @return Build root path
     * @throws InvalidPathException Build path is invalid and could not be retrieved
     */
    public String getBuildPath() throws InvalidPathException;

    /**
     * Get default mode
     *
     * @return Default mode
     */
    public int getDefaultFileMode();

    /**
     * Get default owner
     *
     * @return Default owner
     */
    public String getDefaultOwner();

    /**
     * Get default group
     *
     * @return Default group
     */
    public String getDefaultGroup();

    /**
     * Get default destination
     *
     * @return Default destination
     */
    public String getDefaultDestination();

    /**
     * Get logger
     *
     * @return Logger
     */
    public Log getLog();
}
