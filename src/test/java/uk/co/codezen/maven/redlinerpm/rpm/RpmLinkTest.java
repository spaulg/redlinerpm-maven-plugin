
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

import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Before;
import uk.co.codezen.maven.redlinerpm.mojo.PackageRpmMojo;

import java.io.File;

public class RpmLinkTest extends BasedOwnedObjectTest
{
    String testOutputPath;

    private Log log;

    private RpmLink  rpmLink;
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
        this.rpmLink = new RpmLink();
        this.rpmLink.setPackage(rpmPackage);
    }

    @Override
    public BaseOwnedObject getBaseOwnedObject()
    {
        return rpmLink;
    }
}
