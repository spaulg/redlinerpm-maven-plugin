
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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;
import uk.co.codezen.maven.redlinerpm.rpm.exception.AbstractRpmException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Build an RPM using Maven and the Redline Java library, allowing
 * for operating system agnostic RPM builds
 */
@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
final public class PackageRpmMojo extends AbstractRpmMojo
{
    /**
     * Execute goal
     *
     * @throws MojoExecutionException There was a problem running the Mojo.
     *          Further details are available in the message and cause properties.
     */
    public void execute() throws MojoExecutionException
    {
        // Perform initialisation of the mojo
        this.validate();

        // Scan files to create master list
        this.scanMasterFiles();
        long totalFilesPackaged = 0;

        // Create each package
        for (RpmPackage rpmPackage : this.packages) {
            Set<String> includedFiles;

            // Build package
            try {
                includedFiles = rpmPackage.build();
            }
            catch (IOException ex) {
                this.getLog().error(String.format("Unable to build package %s", rpmPackage.getName()), ex);
                throw new MojoExecutionException(String.format("Unable to build package %s", rpmPackage.getName()), ex);
            }
            catch (NoSuchAlgorithmException ex) {
                this.getLog().error(String.format("Unable to build package %s", rpmPackage.getName()), ex);
                throw new MojoExecutionException(String.format("Unable to build package %s", rpmPackage.getName()), ex);
            }
            catch (AbstractRpmException ex) {
                this.getLog().error(String.format("Unable to build package %s", rpmPackage.getName()), ex);
                throw new MojoExecutionException(String.format("Unable to build package %s", rpmPackage.getName()), ex);
            }

            // Remove included files from master file set
            this.masterFiles.removeAll(includedFiles);
            totalFilesPackaged += includedFiles.size();
        }

        // Error if the master file set still contains files
        if (this.isPerformCheckingForExtraFiles() && this.masterFiles.size() > 0) {
            // Files not included or excluded explicitly. Flag as error
            this.getLog().error(String.format("%d file(s) listed below were found in the build path that have not been " +
                    "included in any package or explicitly excluded. Maybe you need to exclude them?", this.masterFiles.size()));

            for (String missedFile : this.masterFiles) {
                this.getLog().error(String.format(" - %s", missedFile));
            }

            throw new MojoExecutionException(String.format("%d file(s) were found in the build path that have not been " +
                    "included or explicitly excluded. Maybe you need to exclude them? See the error report for more details.",
                    this.masterFiles.size()));
        }

        // Error if no files were included within the RPM packages.
        if (0 < this.packages.size() && 0 == totalFilesPackaged) {
            // No files were actually packaged. Perhaps something got missed.
            this.getLog().error(String.format("No files were included when packaging RPM artifacts. " +
                    "Did you specify the correct output path?"));

            throw new MojoExecutionException("No files were included when packaging RPM artifacts. " +
                    "Did you specify the correct output path?");
        }
    }
}
