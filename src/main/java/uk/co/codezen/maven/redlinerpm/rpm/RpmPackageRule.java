
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

import org.apache.maven.plugin.logging.Log;
import org.apache.tools.ant.DirectoryScanner;
import org.redline_rpm.Builder;
import org.redline_rpm.payload.Directive;

import uk.co.codezen.maven.redlinerpm.mojo.RpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.CanonicalScanPathOutsideBuildPathException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.AbstractRpmException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidPathException;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * RPM file
 * Represents an file to be included within the RPM
 */
final public class RpmPackageRule extends BaseOwnedObject
{
    /**
     * RPM package
     */
    private RpmPackage rpmPackage = null;

    /**
     * Rule base path, relative to plugin buildPath
     */
    private String base = File.separator;

    /**
     * Destination path of files within RPM
     */
    private String destination = null;

    /**
     * List of file include rules
     */
    private List<String> includes = new ArrayList<String>();

    /**
     * List of file exclude rules
     */
    private List<String> excludes = new ArrayList<String>();

    /**
     * File directives
     */
    private Directive directive = new Directive();

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
     * Set base path, relative to the buildPath
     *
     * @param base Base path
     */
    public void setBase(String base)
    {
        if (null == base || base.equals("")) {
            base = File.separator;
        }

        this.base = base;
    }

    /**
     * Get base path, relative to the buildPath
     *
     * @return Base path
     */
    public String getBase()
    {
        return this.base;
    }

    /**
     * Set file destination
     *
     * @param destination File destination
     */
    public void setDestination(String destination)
    {
        if (null != destination && destination.equals("")) {
            destination = null;
        }

        this.destination = destination;
    }

    /**
     * Get file destination
     *
     * @return File destination
     */
    public String getDestination()
    {
        return this.destination;
    }

    /**
     * Get the file destination, or the default setting if not set
     *
     * @return File destination
     */
    public String getDestinationOrDefault()
    {
        if (null == this.destination) {
            return this.getPackage().getMojo().getDefaultDestination();
        }
        else {
            return this.destination;
        }
    }

    /**
     * Set file inclusion rules
     *
     * @param includes File inclusion rules
     */
    public void setIncludes(List<String> includes)
    {
        this.includes = includes;
    }

    /**
     * Get file inclusion rules
     *
     * @return File inclusion rules
     */
    public List<String> getIncludes()
    {
        return this.includes;
    }

    /**
     * Set file exclusion rules
     *
     * @param excludes File exclusion rules
     */
    public void setExcludes(List<String> excludes)
    {
        this.excludes = excludes;
    }

    /**
     * Get file exclusion rules
     *
     * @return File exclusion rules
     */
    public List<String> getExcludes()
    {
        return this.excludes;
    }

    /**
     * Set file directives
     *
     * @param directives File directives
     * @throws InvalidRpmPackageRuleDirectiveException
     */
    public void setDirectives(List<String> directives) throws InvalidRpmPackageRuleDirectiveException
    {
        this.directive = RpmPackageRuleDirective.newDirective(directives);
    }

    /**
     * Get file directives
     *
     * @return File directives
     */
    public Directive getDirectives()
    {
        return this.directive;
    }

    /**
     * Get path used for scanning for files to be included by the rule
     *
     * @return Scan path
     * @throws InvalidPathException
     */
    public String getScanPath() throws InvalidPathException
    {
        String scanPath = String.format("%s%s%s",
                this.rpmPackage.getMojo().getBuildPath(), File.separator, this.getBase());

        try {
            return new File(scanPath).getCanonicalPath();
        }
        catch(IOException ex) {
            throw new InvalidPathException(scanPath, ex);
        }
    }

    /**
     * Get the Maven logger
     *
     * @return Maven logger
     */
    public Log getLog()
    {
        return this.getPackage().getMojo().getLog();
    }

    /**
     * List all files found by the rule to the package
     *
     * @return Matched file list
     * @throws AbstractRpmException
     */
    public String[] listFiles() throws AbstractRpmException
    {
        // Build base path
        RpmMojo mojo = this.rpmPackage.getMojo();

        // Get build path & scan paths
        String buildPath = mojo.getBuildPath();
        String scanPath = this.getScanPath();

        // Confirm the scan path is still within the build path
        if ( ! String.format("%s%s", scanPath, File.separator).startsWith(
                String.format("%s%s", buildPath, File.separator))) {
            throw new CanonicalScanPathOutsideBuildPathException(scanPath, buildPath);
        }

        // Perform scan
        DirectoryScanner ds = DirectoryScannerFactory.factory(
                scanPath,
                this.getIncludes().toArray(new String[0]),
                this.getExcludes().toArray(new String[0])
        );

        this.getLog().debug("Scanning for files for package rule");
        ds.scan();

        return ds.getIncludedFiles();
    }

    /**
     * Add all files found by the rule to the package
     *
     * @param builder RPM builder
     * @return Matched file list
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws AbstractRpmException
     */
    public String[] addFiles(Builder builder) throws IOException, NoSuchAlgorithmException, AbstractRpmException
    {
        String[] includedFiles = this.listFiles();
        String scanPath = this.getScanPath();

        // Add files to package at destination
        this.getLog().debug(String.format("Adding %d files found to package.", includedFiles.length));
        for (String includedFile : includedFiles) {
            String destinationPath = this.getDestinationOrDefault() + File.separator + includedFile;
            String sourcePath = String.format("%s%s%s", scanPath, File.separator, includedFile);

            String owner = this.getOwnerOrDefault();
            String group = this.getGroupOrDefault();
            int fileMode = this.getModeOrDefault();


            this.getLog().debug(String.format("Adding file: %s to path %s with owner '%s', " +
                    "group '%s', with file mode %o.",
                    sourcePath, destinationPath, owner, group, fileMode));

            builder.addFile(
                destinationPath,
                new File(sourcePath),
                fileMode,
                this.getDirectives(),
                owner,
                group
            );
        }

        // Return all results
        return includedFiles;
    }
}
