
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RPM package association (i.e. obsoletes, dependencies, conflicts)
 */
final public class RpmPackageAssociation
{
    /**
     * Association name
     */
    private String name = null;

    /**
     * Association maven style version
     */
    private String version = null;

    /**
     * Min version requirement
     */
    private String minVersion = null;

    /**
     * Max version requirement
     */
    private String maxVersion = null;

    /**
     * Version requirement has range
     */
    private boolean isRange = false;



    /**
     * Set name
     *
     * @param name Association name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get name
     *
     * @return Association name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set version
     *
     * @param version Maven style version
     */
    public void setVersion(String version)
    {
        if (version == null || version.equals("RELEASE") || version.equals("")) {
            // No version required, simplify things by setting to null
            this.isRange = false;
            this.version = null;
            this.minVersion = null;
            this.maxVersion = null;

            return;
        }

        Pattern versionPattern = Pattern.compile("\\[([0-9\\.]*),([0-9\\.]*)\\)");
        Matcher matcher = versionPattern.matcher(version);

        if (matcher.matches()) {
            // Min/max version number based on groups
            // [5.3.8,5.4) or [5.3.8,) or [,5.4)
            this.isRange = true;
            this.version = null;

            String minVersion = matcher.group(1);
            String maxVersion = matcher.group(2);

            if (minVersion.equals("")) {
                minVersion = null;
            }

            if (maxVersion.equals("")) {
                maxVersion = null;
            }

            this.minVersion = minVersion;
            this.maxVersion = maxVersion;
        }
        else {
            // Specific version number
            this.isRange = false;
            this.version = version;
            this.minVersion = null;
            this.maxVersion = null;
        }
    }

    /**
     * Is version range
     *
     * @return Version range, true or false
     */
    public boolean isVersionRange()
    {
        return this.isRange;
    }

    /**
     * Get version
     *
     * @return Maven style version
     */
    public String getVersion()
    {
        return this.version;
    }

    /**
     * Get min version requirement
     *
     * @return Min version requirement
     */
    public String getMinVersion()
    {
        return this.minVersion;
    }

    /**
     * Get max version requirement
     *
     * @return Max version requirement
     */
    public String getMaxVersion()
    {
        return this.maxVersion;
    }
}
