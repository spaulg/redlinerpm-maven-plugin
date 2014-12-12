package uk.co.codezen.maven.redlinerpm.rpm;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RpmTriggerTest extends TestCase
{
    public void testAccessors()
    {
        List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();
        RpmTrigger trigger = new RpmTrigger();

        trigger.setScriptFile("trigger-script");
        assertEquals("trigger-script", trigger.getScriptFile());

        trigger.setProgram("trigger-program");
        assertEquals("trigger-program", trigger.getProgram());

        trigger.setDependencies(dependencies);
        assertEquals(dependencies, trigger.getDependencies());
    }
}
