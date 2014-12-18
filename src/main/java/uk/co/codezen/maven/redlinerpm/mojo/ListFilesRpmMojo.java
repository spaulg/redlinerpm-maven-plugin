
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
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;
import uk.co.codezen.maven.redlinerpm.rpm.exception.AbstractRpmException;

import java.util.Set;

@Mojo(name = "listfiles", defaultPhase = LifecyclePhase.PACKAGE)
final public class ListFilesRpmMojo extends AbstractRpmMojo
{
    /**
     * Execute goal
     *
     * @throws MojoExecutionException There was a problem running the Mojo.
     *          Further details are available in the message and cause properties.
     */
    @Override
    public void execute() throws MojoExecutionException
    {
        // Perform initialisation of the mojo
        this.validate();

        this.getLog().info("Declared packages:");

        // Scan files to create master list
        this.scanMasterFiles();

        // Create each package
        for (RpmPackage rpmPackage : this.packages) {
            Set<String> includedFiles;

            try {
                includedFiles = rpmPackage.listFiles();
            }
            catch (AbstractRpmException ex) {
                this.getLog().error(String.format("Unable to build package %s", rpmPackage.getName()), ex);
                throw new MojoExecutionException(String.format("Unable to build package %s", rpmPackage.getName()), ex);
            }

            // Remove included files from master file set
            this.masterFiles.removeAll(includedFiles);
        }

        // List any files in the master file set
        if (this.masterFiles.size() > 0) {
            this.getLog().info("Unmatched files:");

            for (String unmatchedFile : this.masterFiles) {
                this.getLog().info(String.format("    - %s", unmatchedFile));
            }
        }
    }
}
