
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

import static org.junit.Assert.*;

import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.redline_rpm.payload.Directive;
import uk.co.codezen.maven.redlinerpm.mocks.MockBuilder;
import uk.co.codezen.maven.redlinerpm.mojo.PackageRpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.AbstractRpmException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.CanonicalScanPathOutsideBuildPathException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidPathException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RpmPackageRuleTest extends BasedOwnedObjectTest
{
    String testOutputPath;

    private Log log;

    private RpmPackageRule rpmFileRule;
    private RpmPackage rpmPackage;

    @Before
    public void setUp() throws Exception
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");

        // Create mojo
        PackageRpmMojo mojo = new PackageRpmMojo();
        mojo.setDefaultFileMode(0644);
        mojo.setDefaultOwner("root");
        mojo.setDefaultGroup("root");
        mojo.setDefaultDestination(String.format("%svar%swww%stest", File.separator, File.separator, File.separator));
        mojo.setBuildPath(testOutputPath);

        // Empty maven project is required
        MavenProject mavenProject = new MavenProject();
        mojo.setProject(mavenProject);

        // Mojo logger
        this.log = new DefaultLog(new ConsoleLogger());
        mojo.setLog(this.log);

        // RPM package
        this.rpmPackage = new RpmPackage();
        this.rpmPackage.setMojo(mojo);

        // RPM package rule
        this.rpmFileRule = new RpmPackageRule();
        this.rpmFileRule.setPackage(rpmPackage);
        this.rpmFileRule.setBase("build");
    }

    @Override
    public BaseOwnedObject getBaseOwnedObject()
    {
        return rpmFileRule;
    }

    @Test
    public void directiveAccessors() throws InvalidRpmPackageRuleDirectiveException
    {
        List<String> directives = new ArrayList<String>();
        directives.add("noreplace");

        this.rpmFileRule.setDirectives(directives);
        assertEquals(Directive.class, this.rpmFileRule.getDirectives().getClass());
    }

    @Test
    public void packageAccessors()
    {
        assertEquals(rpmPackage, this.rpmFileRule.getPackage());
    }

    @Test
    public void baseAccessors()
    {
        this.rpmFileRule.setBase("");
        assertEquals(File.separator, this.rpmFileRule.getBase());

        this.rpmFileRule.setBase(null);
        assertEquals(File.separator, this.rpmFileRule.getBase());

        this.rpmFileRule.setBase(String.format("%sfoo", File.separator));
        assertEquals(String.format("%sfoo", File.separator), this.rpmFileRule.getBase());
    }

    @Test
    public void destinationAccessors()
    {
        this.rpmFileRule.setDestination("");
        assertEquals(null, this.rpmFileRule.getDestination());

        this.rpmFileRule.setDestination(null);
        assertEquals(null, this.rpmFileRule.getDestination());

        assertEquals(String.format("%svar%swww%stest", File.separator, File.separator, File.separator),
                this.rpmFileRule.getDestinationOrDefault());

        this.rpmFileRule.setDestination(String.format("%sfoo", File.separator));
        assertEquals(String.format("%sfoo", File.separator), this.rpmFileRule.getDestination());
        assertEquals(String.format("%sfoo", File.separator), this.rpmFileRule.getDestinationOrDefault());
    }

    @Test
    public void includeAccessors()
    {
        List<String> includes = new ArrayList<String>();
        this.rpmFileRule.setIncludes(includes);
        assertEquals(includes, this.rpmFileRule.getIncludes());
    }

    @Test
    public void excludeAccessors()
    {
        List<String> excludes = new ArrayList<String>();
        this.rpmFileRule.setExcludes(excludes);
        assertEquals(excludes, this.rpmFileRule.getExcludes());
    }

    @Test
    public void logAccessor()
    {
        assertEquals(this.log, this.rpmFileRule.getLog());
    }

    @Test
    public void scanPathAccessor() throws InvalidPathException
    {
        String scanPath = String.format("%s%sbuild", new File(this.testOutputPath).getAbsolutePath(), File.separator);
        assertEquals(scanPath, this.rpmFileRule.getScanPath());
    }

    @Test
    public void testListFiles() throws AbstractRpmException
    {
        List<String> includes = new ArrayList<String>();
        includes.add("**");

        List<String> excludes = new ArrayList<String>();
        excludes.add("composer.*");

        this.rpmFileRule.setIncludes(includes);
        this.rpmFileRule.setExcludes(excludes);

        String[] files = this.rpmFileRule.listFiles();
        assertEquals(65, files.length);
    }

    @Test(expected = CanonicalScanPathOutsideBuildPathException.class)
    public void testListFilesOutsideBuildPath() throws AbstractRpmException
    {
        this.rpmFileRule.setBase(String.format("..%s", File.separator));
        System.out.println(this.rpmFileRule.getScanPath());
        this.rpmFileRule.listFiles();
    }

    @Test
    public void testAddFiles() throws NoSuchAlgorithmException, IOException, AbstractRpmException
    {
        MockBuilder builder = new MockBuilder();

        List<String> includes = new ArrayList<String>();
        includes.add("**");

        List<String> excludes = new ArrayList<String>();
        excludes.add("composer.*");

        this.rpmFileRule.setIncludes(includes);
        this.rpmFileRule.setExcludes(excludes);

        String[] files = this.rpmFileRule.addFiles(builder);
        assertEquals(65, files.length);
        assertEquals(96, builder.getContents().size()); // includes directories
    }
}
