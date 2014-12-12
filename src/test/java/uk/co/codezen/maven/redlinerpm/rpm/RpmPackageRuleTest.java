
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

import junit.framework.TestCase;
import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.redline_rpm.payload.Directive;
import uk.co.codezen.maven.redlinerpm.mojo.PackageRpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RpmPackageRuleTest extends TestCase
{
    private RpmPackageRule rpmFileRule;
    private RpmPackage rpmPackage;
    private List<String> includes;
    private List<String> excludes;
    private List<String> directives;
    private Log log;

    public void setUp()
    {
        this.log = new DefaultLog(new ConsoleLogger());

        MavenProject mavenProject = new MavenProject();
        PackageRpmMojo mojo = new PackageRpmMojo();
        mojo.setProject(mavenProject);
        mojo.setDefaultFileMode(0644);
        mojo.setDefaultOwner("root");
        mojo.setDefaultGroup("root");
        mojo.setDefaultDestination("/var/www/test");
        mojo.setLog(this.log);
        mojo.setBuildPath("target");

        this.rpmPackage = new RpmPackage();
        this.rpmPackage.setMojo(mojo);

        this.rpmFileRule = new RpmPackageRule();
        this.rpmFileRule.setPackage(rpmPackage);

        this.includes = new ArrayList<String>();
        this.excludes = new ArrayList<String>();
        this.directives = new ArrayList<String>();

        directives.add("noreplace");
    }

    public void testDirectiveAccessors() throws InvalidRpmPackageRuleDirectiveException
    {
        this.rpmFileRule.setDirectives(directives);
        assertEquals(Directive.class, this.rpmFileRule.getDirectives().getClass());
    }

    public void testPackageAccessors()
    {
        assertEquals(rpmPackage, this.rpmFileRule.getPackage());
    }

    public void testBaseAccessors()
    {
        this.rpmFileRule.setBase("");
        assertEquals("/", this.rpmFileRule.getBase());

        this.rpmFileRule.setBase(null);
        assertEquals("/", this.rpmFileRule.getBase());

        this.rpmFileRule.setBase("/foo");
        assertEquals("/foo/", this.rpmFileRule.getBase());

        this.rpmFileRule.setBase("/bar/");
        assertEquals("/bar/", this.rpmFileRule.getBase());
    }

    public void testDestinationAccessors()
    {
        this.rpmFileRule.setDestination("");
        assertEquals(null, this.rpmFileRule.getDestination());

        this.rpmFileRule.setDestination(null);
        assertEquals(null, this.rpmFileRule.getDestination());

        assertEquals("/var/www/test/", this.rpmFileRule.getDestinationOrDefault());

        this.rpmFileRule.setDestination("/foo");
        assertEquals("/foo/", this.rpmFileRule.getDestination());
        assertEquals("/foo/", this.rpmFileRule.getDestinationOrDefault());

        this.rpmFileRule.setDestination("/bar/");
        assertEquals("/bar/", this.rpmFileRule.getDestination());
        assertEquals("/bar/", this.rpmFileRule.getDestinationOrDefault());
    }

    public void testModeAccessors()
    {
        assertEquals(0644, this.rpmFileRule.getModeOrDefault());
        this.rpmFileRule.setFileMode(0755);
        assertEquals(0755, this.rpmFileRule.getModeOrDefault());
    }

    public void testOwnerAccessors()
    {
        this.rpmFileRule.setOwner("");
        assertEquals("root", this.rpmFileRule.getOwnerOrDefault());

        this.rpmFileRule.setOwner(null);
        assertEquals("root", this.rpmFileRule.getOwnerOrDefault());

        assertEquals("root", this.rpmFileRule.getOwnerOrDefault());
        this.rpmFileRule.setOwner("owner");
        assertEquals("owner", this.rpmFileRule.getOwnerOrDefault());

        this.rpmFileRule.setOwner("");
        assertEquals("root", this.rpmFileRule.getOwnerOrDefault());
    }

    public void testGroupAccessors()
    {
        this.rpmFileRule.setGroup("");
        assertEquals("root", this.rpmFileRule.getGroupOrDefault());

        this.rpmFileRule.setGroup(null);
        assertEquals("root", this.rpmFileRule.getGroupOrDefault());

        assertEquals("root", this.rpmFileRule.getGroupOrDefault());
        this.rpmFileRule.setGroup("group");
        assertEquals("group", this.rpmFileRule.getGroupOrDefault());

        this.rpmFileRule.setGroup("");
        assertEquals("root", this.rpmFileRule.getGroupOrDefault());
    }

    public void testIncludeAccessors()
    {
        this.rpmFileRule.setIncludes(includes);
        assertEquals(includes, this.rpmFileRule.getIncludes());
    }

    public void testExcludeAccessors()
    {
        this.rpmFileRule.setExcludes(excludes);
        assertEquals(excludes, this.rpmFileRule.getExcludes());
    }

    public void testLogAccessor()
    {
        assertEquals(this.log, this.rpmFileRule.getLog());
    }

    public void testScanPathAccessor()
    {
        String scanPath = new File("target/classes").getAbsolutePath() + "/";
        this.rpmFileRule.setBase("classes");
        assertEquals(scanPath, this.rpmFileRule.getScanPath());
    }

    // todo: add something for addFiles
    // todo: add something for listFiles
}
