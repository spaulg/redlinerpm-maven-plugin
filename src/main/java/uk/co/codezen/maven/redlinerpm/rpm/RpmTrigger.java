
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

import java.util.ArrayList;
import java.util.List;

/**
 * RPM trigger
 */
final public class RpmTrigger
{
    /**
     * Trigger script file
     */
    private String scriptFile = null;

    /**
     * Trigger script program
     */
    private String program = null;

    /**
     * Trigger package associations
     */
    private List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();


    /**
     * Set trigger script file
     *
     * @param scriptFile Trigger script file
     */
    public void setScriptFile(String scriptFile)
    {
        this.scriptFile = scriptFile;
    }

    /**
     * Get trigger script file
     *
     * @return Trigger script file
     */
    public String getScriptFile()
    {
        return scriptFile;
    }

    /**
     * Set trigger program
     *
     * @param program Trigger program
     */
    public void setProgram(String program)
    {
        this.program = program;
    }

    /**
     * Get trigger program
     *
     * @return Trigger program
     */
    public String getProgram()
    {
        return program;
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
