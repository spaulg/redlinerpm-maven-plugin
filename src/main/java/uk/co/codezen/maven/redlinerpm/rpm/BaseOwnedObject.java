
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

public abstract class BaseOwnedObject
{
    /**
     * Destination file mode
     */
    private int fileMode = 0;

    /**
     * Destination file owner
     */
    private String owner = null;

    /**
     * Destination file group
     */
    private String group = null;

    protected abstract RpmPackage getPackage();

    /**
     * Set file mode
     *
     * @param fileMode File mode
     */
    public void setFileMode(int fileMode)
    {
        this.fileMode = fileMode;
    }

    /**
     * Get file mode, or the default setting if not set
     *
     * @return File mode
     */
    public int getModeOrDefault()
    {
        if (0 == this.fileMode) {
            System.err.println(this.getPackage());
            System.err.println(this.getPackage().getMojo());
            System.err.println(this.getPackage().getMojo().getDefaultFileMode());
            return this.getPackage().getMojo().getDefaultFileMode();
        }
        else {
            return this.fileMode;
        }
    }

    /**
     * Set file owner
     *
     * @param owner File owner
     */
    public void setOwner(String owner)
    {
        if (null != owner && owner.equals("")) {
            owner = null;
        }

        this.owner = owner;
    }

    /**
     * Get file owner, or the default setting if not set
     *
     * @return File owner
     */
    public String getOwnerOrDefault()
    {
        if (null == this.owner)
        {
            return this.getPackage().getMojo().getDefaultOwner();
        }
        else {
            return this.owner;
        }
    }

    /**
     * Set file group
     *
     * @param group File group
     */
    public void setGroup(String group)
    {
        if (null != group && group.equals("")) {
            group = null;
        }

        this.group = group;
    }

    /**
     * Get file group, or the default setting if not set
     *
     * @return File group
     */
    public String getGroupOrDefault()
    {
        if (null == this.group) {
            return this.getPackage().getMojo().getDefaultGroup();
        }
        else {
            return this.group;
        }
    }
}
