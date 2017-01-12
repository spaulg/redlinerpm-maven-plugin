
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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class BasedOwnedObjectTest
{
    protected abstract BaseOwnedObject getBaseOwnedObject();

    @Test
    public void modeAccessors()
    {
        assertEquals(0644, this.getBaseOwnedObject().getModeOrDefault());
        this.getBaseOwnedObject().setFileMode(0755);
        assertEquals(0755, this.getBaseOwnedObject().getModeOrDefault());
    }

    @Test
    public void ownerAccessors()
    {
        this.getBaseOwnedObject().setOwner("");
        assertEquals("root", this.getBaseOwnedObject().getOwnerOrDefault());

        this.getBaseOwnedObject().setOwner(null);
        assertEquals("root", this.getBaseOwnedObject().getOwnerOrDefault());

        assertEquals("root", this.getBaseOwnedObject().getOwnerOrDefault());
        this.getBaseOwnedObject().setOwner("owner");
        assertEquals("owner", this.getBaseOwnedObject().getOwnerOrDefault());

        this.getBaseOwnedObject().setOwner("");
        assertEquals("root", this.getBaseOwnedObject().getOwnerOrDefault());
    }

    @Test
    public void groupAccessors()
    {
        this.getBaseOwnedObject().setGroup("");
        assertEquals("root", this.getBaseOwnedObject().getGroupOrDefault());

        this.getBaseOwnedObject().setGroup(null);
        assertEquals("root", this.getBaseOwnedObject().getGroupOrDefault());

        assertEquals("root", this.getBaseOwnedObject().getGroupOrDefault());
        this.getBaseOwnedObject().setGroup("group");
        assertEquals("group", this.getBaseOwnedObject().getGroupOrDefault());

        this.getBaseOwnedObject().setGroup("");
        assertEquals("root", this.getBaseOwnedObject().getGroupOrDefault());
    }

}
