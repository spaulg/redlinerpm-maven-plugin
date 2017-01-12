
/*
    Copyright 2017 Teradata

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

public class RpmLink extends BaseOwnedObject
{
    /**
     * RPM package
     */
    private RpmPackage rpmPackage = null;

    private String path;
    private String target;

    /**
     * Set associated RPM package
     *
     * @param rpmPackage RPM package
     */
    public void setPackage(RpmPackage rpmPackage)
    {
        this.rpmPackage = rpmPackage;
    }

    /**
     * Get associated RPM package
     *
     * @return RPM package
     */
    @Override
    public RpmPackage getPackage()
    {
        return this.rpmPackage;
    }

    /**
     * Set symlink path
     *
     * @param path symlink path
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Get symlink path
     *
     * @return symlink path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Set symlink target
     *
     * @param target symlink target
     */
    public void setTarget(String target)
    {
        this.target = target;
    }

    /**
     * Get symlink target
     *
     * @return symlink target
     */
    public String getTarget()
    {
        return target;
    }
}
