package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

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

        trigger.setScriptFile("trigger-script");
        assertEquals("trigger-script", trigger.getScriptFile());

        trigger.setProgram("trigger-program");
        assertEquals("trigger-program", trigger.getProgram());

        trigger.setDependencies(dependencies);
        assertEquals(dependencies, trigger.getDependencies());
    }
}
