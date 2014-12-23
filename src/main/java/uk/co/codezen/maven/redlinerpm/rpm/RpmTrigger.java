
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

package uk.co.codezen.maven.redlinerpm.rpm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * RPM trigger
 */
final public class RpmTrigger
{
    /**
     * Pre install event hook script file
     */
    private File preInstallScriptFile = null;

    /**
     * Pre install event hook program
     */
    private String preInstallProgram = null;

    /**
     * Post install event hook script file
     */
    private File postInstallScriptFile = null;

    /**
     * Post install event hook program
     */
    private String postInstallProgram = null;

    /**
     * Pre uninstall event hook script file
     */
    private File preUninstallScriptFile = null;

    /**
     * Pre uninstall event hook script program
     */
    private String preUninstallProgram = null;

    /**
     * Post uninstall event hook script file
     */
    private File postUninstallScriptFile = null;

    /**
     * Post uninstall event hook program
     */
    private String postUninstallProgram = null;

    /**
     * Trigger package associations
     */
    private List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();


    /**
     * Set pre install script file
     *
     * @param preInstallScriptFile Pre install script file
     */
    public void setPreInstallScriptFile(File preInstallScriptFile)
    {
        this.preInstallScriptFile = preInstallScriptFile;
    }

    /**
     * Get pre install script file
     *
     * @return Pre install script file
     */
    public File getPreInstallScriptFile()
    {
        return this.preInstallScriptFile;
    }

    /**
     * Set pre install program
     *
     * @param preInstallProgram Pre install program
     */
    public void setPreInstallProgram(String preInstallProgram)
    {
        this.preInstallProgram = preInstallProgram;
    }

    /**
     * Get pre install program
     *
     * @return Pre install program
     */
    public String getPreInstallProgram()
    {
        return this.preInstallProgram;
    }

    /**
     * Set post install script file
     *
     * @param postInstallScriptFile Post install script file
     */
    public void setPostInstallScriptFile(File postInstallScriptFile)
    {
        this.postInstallScriptFile = postInstallScriptFile;
    }

    /**
     * Get post install script file
     *
     * @return Post install script file
     */
    public File getPostInstallScriptFile()
    {
        return this.postInstallScriptFile;
    }

    /**
     * Set post install program
     *
     * @param postInstallProgram Post install program
     */
    public void setPostInstallProgram(String postInstallProgram)
    {
        this.postInstallProgram = postInstallProgram;
    }

    /**
     * Get post install program
     *
     * @return Post install program
     */
    public String getPostInstallProgram()
    {
        return this.postInstallProgram;
    }

    /**
     * Set pre uninstall script file
     *
     * @param preUninstallScriptFile Pre uninstall script file
     */
    public void setPreUninstallScriptFile(File preUninstallScriptFile)
    {
        this.preUninstallScriptFile = preUninstallScriptFile;
    }

    /**
     * Get pre uninstall script file
     *
     * @return Pre uninstall script file
     */
    public File getPreUninstallScriptFile()
    {
        return this.preUninstallScriptFile;
    }

    /**
     * Set pre uninstall program
     *
     * @param preUninstallProgram Pre uninstall program
     */
    public void setPreUninstallProgram(String preUninstallProgram)
    {
        this.preUninstallProgram = preUninstallProgram;
    }

    /**
     * Get pre uninstall program
     *
     * @return Pre uninstall program
     */
    public String getPreUninstallProgram()
    {
        return this.preUninstallProgram;
    }

    /**
     * Set post uninstall script file
     *
     * @param postUninstallScriptFile Post uninstall script file
     */
    public void setPostUninstallScriptFile(File postUninstallScriptFile)
    {
        this.postUninstallScriptFile = postUninstallScriptFile;
    }

    /**
     * Get post uninstall script file
     *
     * @return Post uninstall script file
     */
    public File getPostUninstallScriptFile()
    {
        return this.postUninstallScriptFile;
    }

    /**
     * Set post uninstall program
     *
     * @param postUninstallProgram Post uninstall program
     */
    public void setPostUninstallProgram(String postUninstallProgram)
    {
        this.postUninstallProgram = postUninstallProgram;
    }

    /**
     * Get post uninstall program
     *
     * @return Post uninstall program
     */
    public String getPostUninstallProgram()
    {
        return this.postUninstallProgram;
    }


    /**
     * Set trigger packages
     *
     * @param dependencies Trigger packages
     */
    public void setDependencies(List<RpmPackageAssociation> dependencies)
    {
        this.dependencies = dependencies;
    }

    /**
     * Get trigger packages
     *
     * @return Trigger packages
     */
    public List<RpmPackageAssociation> getDependencies()
    {
        return dependencies;
    }
}
