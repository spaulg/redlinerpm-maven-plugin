package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RpmTriggerTest
{
    @Test
    public void accessors()
    {
        List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();
        RpmTrigger trigger = new RpmTrigger();
        File triggerScript = new File("/path/to/file");

        assertNull(trigger.getPreInstallScriptFile());
        trigger.setPreInstallScriptFile(triggerScript);
        assertEquals(triggerScript, trigger.getPreInstallScriptFile());

        assertNull(trigger.getPreInstallProgram());
        trigger.setPreInstallProgram("/bin/sh");
        assertEquals("/bin/sh", trigger.getPreInstallProgram());

        assertNull(trigger.getPostInstallScriptFile());
        trigger.setPostInstallScriptFile(triggerScript);
        assertEquals(triggerScript, trigger.getPostInstallScriptFile());

        assertNull(trigger.getPostInstallProgram());
        trigger.setPostInstallProgram("/bin/sh");
        assertEquals("/bin/sh", trigger.getPostInstallProgram());

        assertNull(trigger.getPreUninstallScriptFile());
        trigger.setPreUninstallScriptFile(triggerScript);
        assertEquals(triggerScript, trigger.getPreUninstallScriptFile());

        assertNull(trigger.getPreUninstallProgram());
        trigger.setPreUninstallProgram("/bin/sh");
        assertEquals("/bin/sh", trigger.getPreUninstallProgram());

        assertNull(trigger.getPostUninstallScriptFile());
        trigger.setPostUninstallScriptFile(triggerScript);
        assertEquals(triggerScript, trigger.getPostUninstallScriptFile());

        assertNull(trigger.getPostUninstallProgram());
        trigger.setPostUninstallProgram("/bin/sh");
        assertEquals("/bin/sh", trigger.getPostUninstallProgram());

        trigger.setDependencies(dependencies);
        assertEquals(dependencies, trigger.getDependencies());
    }
}
