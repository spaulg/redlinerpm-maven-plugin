
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
import org.redline_rpm.Builder;
import org.redline_rpm.IntString;
import org.redline_rpm.header.Architecture;
import org.redline_rpm.header.Os;
import org.redline_rpm.header.RpmType;
import uk.co.codezen.maven.redlinerpm.mojo.RpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.exception.*;
import static org.redline_rpm.header.Flags.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * RPM package
 */
final public class RpmPackage
{
    /**
     * Plugin mojo currently in use
     */
    private RpmMojo mojo = null;

    /**
     * Package name
     */
    private String name = null;

    /**
     * Package version
     */
    private String version = null;

    /**
     * Project version
     */
    private String projectVersion = null;

    /**
     * Package release
     */
    private String release = null;

    /**
     * Final name of RPM artifact
     */
    private String finalName = null;

    /**
     * Package url
     */
    private String url = null;

    /**
     * Package group
     */
    private String group = null;

    /**
     * Package license
     */
    private String license = null;

    /**
     * Package summary
     */
    private String summary = null;

    /**
     * Package description
     */
    private String description = null;

    /**
     * Package distribution
     */
    private String distribution = null;

    /**
     * Build architecture
     * Defaults to detected architecture of build environment if non given
     */
    private Architecture architecture = null;

    /**
     * Build operating system
     * Defaults to detected operating system of build environment if non given
     */
    private Os operatingSystem = null;

    /**
     * Build host name
     * Defaults to hostname of build server provided by hostname service
     */
    private String buildHostName = null;

    /**
     * Packager of RPM
     */
    private String packager = null;

    /**
     * Attach the artifact
     */
    private boolean attach = true;

    /**
     * Artifact classifier
     */
    private String classifier = null;

    /**
     * Pre transaction event hook script file
     */
    private File preTransactionScriptFile = null;

    /**
     * Pre transaction event hook script program
     */
    private String preTransactionProgram = null;

    /**
     * Pre install event hook script file
     */
    private File preInstallScriptFile = null;

    /**
     * Pre install event hook program
     */
    private String preInstallProgram = null;

    /**
     * Post install event hook script file
     */
    private File postInstallScriptFile = null;

    /**
     * Post install event hook program
     */
    private String postInstallProgram = null;

    /**
     * Pre uninstall event hook script file
     */
    private File preUninstallScriptFile = null;

    /**
     * Pre uninstall event hook script program
     */
    private String preUninstallProgram = null;

    /**
     * Post uninstall event hook script file
     */
    private File postUninstallScriptFile = null;

    /**
     * Post uninstall event hook program
     */
    private String postUninstallProgram = null;

    /**
     * Post transaction event hook script file
     */
    private File postTransactionScriptFile = null;

    /**
     * Post transaction event hook program
     */
    private String postTransactionProgram = null;

    /**
     * List of triggers
     */
    private List<RpmTrigger> triggers = new ArrayList<RpmTrigger>();

    /**
     * Signing key
     */
    private String signingKey = null;

    /**
     * Signing key id
     */
    private String signingKeyId = null;

    /**
     * Signing key pass phrase
     */
    private String signingKeyPassPhrase = null;

    /**
     * Prefixes
     */
    private List<String> prefixes = new ArrayList<String>();

    /**
     * Dependencies
     */
    private List<RpmPackageAssociation> dependencies = new ArrayList<RpmPackageAssociation>();

    /**
     * Obsoletes
     */
    private List<RpmPackageAssociation> obsoletes = new ArrayList<RpmPackageAssociation>();

    /**
     * Conflicts
     */
    private List<RpmPackageAssociation> conflicts = new ArrayList<RpmPackageAssociation>();

    /**
     * Package file matching rules
     */
    private List<RpmPackageRule> rules = new ArrayList<RpmPackageRule>();



    /**
     * Set mojo in use by Maven
     *
     * @param mojo Current maven mojo
     */
    public void setMojo(RpmMojo mojo)
    {
        this.mojo = mojo;
    }

    /**
     * Get mojo in use by Maven
     *
     * @return Current maven mojo
     */
    public RpmMojo getMojo()
    {
        return this.mojo;
    }

    /**
     * Set package name
     *
     * @param name Package name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get package name
     *
     * @return Package name
     */
    public String getName()
    {
        if (null == this.name) {
            this.name = this.getMojo().getProjectArtifactId();
        }

        return this.name;
    }

    /**
     * Set package version
     *
     * @param version Package version
     */
    public void setVersion(String version)
    {
        if (null != version && version.equals("")) {
            version = null;
        }

        this.projectVersion = version;
        this.version = this.sanitiseVersion(version);
    }

    /**
     * Get package version
     *
     * @return Package version
     */
    public String getVersion()
    {
        if (null == this.version) {
            this.version = this.sanitiseVersion(this.getMojo().getProjectVersion());
        }

        return this.version;
    }

    /**
     * Get project version
     *
     * @return Project version
     */
    public String getProjectVersion()
    {
        if (null == this.projectVersion) {
            this.projectVersion = this.getMojo().getProjectVersion();
        }

        return this.projectVersion;
    }

    /**
     * Sanitise the version number for use in packaging.
     *
     * @param version Un-sanitised version
     * @return Sanitised version number
     */
    private String sanitiseVersion(String version)
    {
        if ( null != version && ! version.replaceAll("[a-zA-Z0-9\\.]", "").equals("")) {
            version = version.replaceAll("-", ".");
            version = version.replaceAll("[^a-zA-Z0-9\\.]", "");
        }

        return version;
    }

    /**
     * Set package release
     *
     * @param release Package release
     */
    public void setRelease(String release)
    {
        this.release = release;
    }

    /**
     * Get package release
     *
     * @return Package release
     */
    public String getRelease()
    {
        if (null == this.release) {
            this.release = Long.toString(System.currentTimeMillis() / 1000);
        }

        return this.release;
    }

    /**
     * Set final name of RPM artifact
     *
     * @param finalName Final name of RPM artifact
     */
    public void setFinalName(String finalName)
    {
        this.finalName = finalName;
    }

    /**
     * Get final name of the RPM artifact.
     * If a final name is not set, the final name will default to {name}-{version}-{release}.{architecture}.rpm
     *
     * @return Final name of RPM artifact
     */
    public String getFinalName()
    {
        if (null == this.finalName) {
            this.finalName = String.format("%s-%s-%s.%s.rpm",
                    this.getName(), this.getVersion(), this.getRelease(),
                    this.getArchitecture().toString().toLowerCase());
        }

        return finalName;
    }

    /**
     * Set package dependencies
     *
     * @param dependencies Package dependencies
     */
    public void setDependencies(List<RpmPackageAssociation> dependencies)
    {
        this.dependencies = dependencies;
    }

    /**
     * Get package dependencies
     *
     * @return Package dependencies
     */
    public List<RpmPackageAssociation> getDependencies()
    {
        return this.dependencies;
    }

    /**
     * Set package obsoletes
     *
     * @param obsoletes Package obsoletes
     */
    public void setObsoletes(List<RpmPackageAssociation> obsoletes)
    {
        this.obsoletes = obsoletes;
    }

    /**
     * Get package obsoletes
     *
     * @return Package obsoletes
     */
    public List<RpmPackageAssociation> getObsoletes()
    {
        return this.obsoletes;
    }

    /**
     * Set package conflicts
     *
     * @param conflicts Package conflicts
     */
    public void setConflicts(List<RpmPackageAssociation> conflicts)
    {
        this.conflicts = conflicts;
    }

    /**
     * Get package conflicts
     *
     * @return Package conflicts
     */
    public List<RpmPackageAssociation> getConflicts()
    {
        return this.conflicts;
    }

    /**
     * Set package url
     *
     * @param url Package url
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * Get package url
     *
     * @return Package url
     */
    public String getUrl()
    {
        if (null == this.url) {
            this.url = this.getMojo().getProjectUrl();
        }

        return this.url;
    }

    /**
     * Set package group
     *
     * @param group Package group
     */
    public void setGroup(String group)
    {
        this.group = group;
    }

    /**
     * Get package group
     *
     * @return Package group
     */
    public String getGroup()
    {
        return this.group;
    }

    /**
     * Set package license
     *
     * @param license Package license
     */
    public void setLicense(String license)
    {
        this.license = license;
    }

    /**
     * Get package license
     *
     * @return Package license
     */
    public String getLicense()
    {
        if (null == this.license) {
            this.license = this.getMojo().getCollapsedProjectLicense();
        }

        return this.license;
    }

    /**
     * Set package summary
     *
     * @param summary Package summary
     */
    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    /**
     * Get package summary
     *
     * @return Package summary
     */
    public String getSummary()
    {
        return this.summary;
    }

    /**
     * Set package description
     *
     * @param description Package description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Get package description
     *
     * @return Package description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Set package distribution
     *
     * @param distribution Package distribution
     */
    public void setDistribution(String distribution)
    {
        this.distribution = distribution;
    }

    /**
     * Get package distribution
     *
     * @return Package distribution
     */
    public String getDistribution()
    {
        return this.distribution;
    }

    /**
     * Set package architecture
     *
     * @param architecture Package architecture
     * @throws UnknownArchitectureException The architecture supplied is not recognised.
     */
    public void setArchitecture(String architecture) throws UnknownArchitectureException
    {
        if (null == architecture || architecture.equals("")) {
            throw new UnknownArchitectureException(architecture);
        }

        architecture = architecture.toUpperCase();

        try {
            this.architecture = Architecture.valueOf(architecture);
        }
        catch (IllegalArgumentException ex) {
            // Unknown architecture
            throw new UnknownArchitectureException(architecture, ex);
        }
    }

    /**
     * Get package architecture
     *
     * @return Package architecture
     */
    public Architecture getArchitecture()
    {
        if (null == this.architecture) {
            this.architecture = Architecture.NOARCH;
        }

        return this.architecture;
    }

    /**
     * Set package operating system
     *
     * @param operatingSystem Package operating system
     * @throws UnknownOperatingSystemException The operating system supplied is not recognised.
     */
    public void setOperatingSystem(String operatingSystem) throws UnknownOperatingSystemException
    {
        if (null == operatingSystem || operatingSystem.equals("")) {
            throw new UnknownOperatingSystemException(operatingSystem);
        }

        operatingSystem = operatingSystem.toUpperCase();

        try {
            this.operatingSystem = Os.valueOf(operatingSystem);
        }
        catch(IllegalArgumentException ex) {
            // Unknown operating system
            throw new UnknownOperatingSystemException(operatingSystem, ex);
        }
    }

    /**
     * Get package operating system.
     * Defaults to LINUX if not set.
     *
     * @return Package operating system
     */
    public Os getOperatingSystem()
    {
        if (null == this.operatingSystem) {
            this.operatingSystem = Os.LINUX;
        }

        return this.operatingSystem;
    }

    /**
     * Set package build host name
     *
     * @param buildHostName Package build host name
     */
    public void setBuildHostName(String buildHostName)
    {
        this.buildHostName = buildHostName;
    }

    /**
     * Get package build host name.
     * If one is not supplied, the default hostname of the machine running the build is used.
     *
     * @return Package build host name
     * @throws UnknownHostException The build host could not be retrieved automatically.
     */
    public String getBuildHostName() throws UnknownHostException
    {
        if (null == this.buildHostName) {
            this.buildHostName = InetAddress.getLocalHost().getHostName();
        }

        return this.buildHostName;
    }

    /**
     * Set package packager
     *
     * @param packager Package packager
     */
    public void setPackager(String packager)
    {
        this.packager = packager;
    }

    /**
     * Get packager
     *
     * @return Packager
     */
    public String getPackager()
    {
        return this.packager;
    }

    /**
     * Set artifact attachment
     *
     * @param attach Artifact attachment
     */
    public void setAttach(boolean attach)
    {
        this.attach = attach;
    }

    /**
     * Get artifact attachment
     *
     * @return Artifact is attached
     */
    public boolean isAttach()
    {
        return this.attach;
    }

    /**
     * Set the artifact classifier
     *
     * @param classifier Artifact classifier
     */
    public void setClassifier(String classifier)
    {
        this.classifier = classifier;
    }

    /**
     * Get the artifact classifier
     *
     * @return Artifact classifier
     */
    public String getClassifier()
    {
        return this.classifier;
    }

    /**
     * Set pre transaction script file
     *
     * @param preTransactionScriptFile Pre transaction script file
     */
    public void setPreTransactionScriptFile(File preTransactionScriptFile)
    {
        this.preTransactionScriptFile = preTransactionScriptFile;
    }

    /**
     * Get pre transactions script file
     *
     * @return Pre transaction script file
     */
    public File getPreTransactionScriptFile()
    {
        return this.preTransactionScriptFile;
    }

    /**
     * Set pre transaction program
     *
     * @param preTransactionProgram Pre transaction program
     */
    public void setPreTransactionProgram(String preTransactionProgram)
    {
        this.preTransactionProgram = preTransactionProgram;
    }

    /**
     * Get pre transaction program
     *
     * @return Pre transaction program
     */
    public String getPreTransactionProgram()
    {
        return this.preTransactionProgram;
    }

    /**
     * Set pre install script file
     *
     * @param preInstallScriptFile Pre install script file
     */
    public void setPreInstallScriptFile(File preInstallScriptFile)
    {
        this.preInstallScriptFile = preInstallScriptFile;
    }

    /**
     * Get pre install script file
     *
     * @return Pre install script file
     */
    public File getPreInstallScriptFile()
    {
        return this.preInstallScriptFile;
    }

    /**
     * Set pre install program
     *
     * @param preInstallProgram Pre install program
     */
    public void setPreInstallProgram(String preInstallProgram)
    {
        this.preInstallProgram = preInstallProgram;
    }

    /**
     * Get pre install program
     *
     * @return Pre install program
     */
    public String getPreInstallProgram()
    {
        return this.preInstallProgram;
    }

    /**
     * Set post install script file
     *
     * @param postInstallScriptFile Post install script file
     */
    public void setPostInstallScriptFile(File postInstallScriptFile)
    {
        this.postInstallScriptFile = postInstallScriptFile;
    }

    /**
     * Get post install script file
     *
     * @return Post install script file
     */
    public File getPostInstallScriptFile()
    {
        return this.postInstallScriptFile;
    }

    /**
     * Set post install program
     *
     * @param postInstallProgram Post install program
     */
    public void setPostInstallProgram(String postInstallProgram)
    {
        this.postInstallProgram = postInstallProgram;
    }

    /**
     * Get post install program
     *
     * @return Post install program
     */
    public String getPostInstallProgram()
    {
        return this.postInstallProgram;
    }

    /**
     * Set pre uninstall script file
     *
     * @param preUninstallScriptFile Pre uninstall script file
     */
    public void setPreUninstallScriptFile(File preUninstallScriptFile)
    {
        this.preUninstallScriptFile = preUninstallScriptFile;
    }

    /**
     * Get pre uninstall script file
     *
     * @return Pre uninstall script file
     */
    public File getPreUninstallScriptFile()
    {
        return this.preUninstallScriptFile;
    }

    /**
     * Set pre uninstall program
     *
     * @param preUninstallProgram Pre uninstall program
     */
    public void setPreUninstallProgram(String preUninstallProgram)
    {
        this.preUninstallProgram = preUninstallProgram;
    }

    /**
     * Get pre uninstall program
     *
     * @return Pre uninstall program
     */
    public String getPreUninstallProgram()
    {
        return this.preUninstallProgram;
    }

    /**
     * Set post uninstall script file
     *
     * @param postUninstallScriptFile Post uninstall script file
     */
    public void setPostUninstallScriptFile(File postUninstallScriptFile)
    {
        this.postUninstallScriptFile = postUninstallScriptFile;
    }

    /**
     * Get post uninstall script file
     *
     * @return Post uninstall script file
     */
    public File getPostUninstallScriptFile()
    {
        return this.postUninstallScriptFile;
    }

    /**
     * Set post uninstall program
     *
     * @param postUninstallProgram Post uninstall program
     */
    public void setPostUninstallProgram(String postUninstallProgram)
    {
        this.postUninstallProgram = postUninstallProgram;
    }

    /**
     * Get post uninstall program
     *
     * @return Post uninstall program
     */
    public String getPostUninstallProgram()
    {
        return this.postUninstallProgram;
    }

    /**
     * Set post transaction script file
     *
     * @param postTransactionScriptFile Post transaction script file
     */
    public void setPostTransactionScriptFile(File postTransactionScriptFile)
    {
        this.postTransactionScriptFile = postTransactionScriptFile;
    }

    /**
     * Get post transaction script file
     *
     * @return Post transaction script file
     */
    public File getPostTransactionScriptFile()
    {
        return this.postTransactionScriptFile;
    }

    /**
     * Set post transaction program
     *
     * @param postTransactionProgram Post transaction program
     */
    public void setPostTransactionProgram(String postTransactionProgram)
    {
        this.postTransactionProgram = postTransactionProgram;
    }

    /**
     * Get post transaction program
     *
     * @return Post transaction program
     */
    public String getPostTransactionProgram()
    {
        return this.postTransactionProgram;
    }

    /**
     * Set triggers
     *
     * @param triggers Triggers
     */
    public void setTriggers(List<RpmTrigger> triggers)
    {
        this.triggers = triggers;
    }

    /**
     * Get triggers
     *
     * @return Triggers
     */
    public List<RpmTrigger> getTriggers()
    {
        return triggers;
    }

    /**
     * Set signing key
     *
     * @param signingKey Signing key
     */
    public void setSigningKey(String signingKey)
    {
        this.signingKey = signingKey;
    }

    /**
     * Get signing key
     *
     * @return Signing key
     */
    public String getSigningKey()
    {
        return this.signingKey;
    }

    /**
     * Set signing key id
     *
     * @param signingKeyId Signing key id
     */
    public void setSigningKeyId(String signingKeyId)
    {
        this.signingKeyId = signingKeyId;
    }

    /**
     * Get signing key id
     *
     * @return Signing key id
     */
    public String getSigningKeyId()
    {
        return this.signingKeyId;
    }

    /**
     * Set signing key pass phrase
     *
     * @param signingKeyPassPhrase Signing key pass phrase
     */
    public void setSigningKeyPassPhrase(String signingKeyPassPhrase)
    {
        this.signingKeyPassPhrase = signingKeyPassPhrase;
    }

    /**
     * Get signing key pass phrase
     *
     * @return Signing key pass phrase
     */
    public String getSigningKeyPassPhrase()
    {
        return this.signingKeyPassPhrase;
    }

    /**
     * Set list of prefixes
     *
     * @param prefixes List of Prefixes
     */
    public void setPrefixes(List<String> prefixes)
    {
        if (null == prefixes) {
            prefixes = new ArrayList<String>();
        }

        this.prefixes = prefixes;
    }

    /**
     * Get list of prefixes
     *
     * @return List of prefixes
     */
    public List<String> getPrefixes()
    {
        return this.prefixes;
    }

    /**
     * Set package rules
     *
     * @param rules Package rules
     */
    public void setRules(List<RpmPackageRule> rules)
    {
        if (null != rules) {
            for (RpmPackageRule rpmPackageRule : rules) {
                rpmPackageRule.setPackage(this);
            }
        }

        this.rules = rules;
    }

    /**
     * Get package rules
     *
     * @return Package rules
     */
    public List<RpmPackageRule> getRules()
    {
        return this.rules;
    }

    /**
     * Get the Maven logger
     *
     * @return Maven logger
     */
    public Log getLog()
    {
        return this.getMojo().getLog();
    }

    /**
     * Build the package
     *
     * @return Files included within the package
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws AbstractRpmException
     */
    public Set<String> build() throws IOException, NoSuchAlgorithmException, AbstractRpmException
    {
        Set<String> fileList = new HashSet<String>();
        String buildDirectory = this.getMojo().getBuildDirectory();

        this.getLog().debug("Creating RPM builder");
        Builder builder = new Builder();
        builder.setType(RpmType.BINARY);

        // Main package
        this.getLog().debug("Setting package information");
        builder.setPackage(this.getName(), this.getVersion(), this.getRelease());
        builder.setPlatform(this.getArchitecture(), this.getOperatingSystem());
        builder.setGroup(this.getGroup());
        builder.setLicense(this.getLicense());
        builder.setSummary(this.getSummary());
        builder.setDescription(this.getDescription());
        builder.setDistribution(this.getDistribution());
        builder.setBuildHost(this.getBuildHostName());
        builder.setPackager(this.getPackager());
        builder.setUrl(this.getUrl());
        builder.setPrefixes(this.getPrefixes().toArray(new String[0]));

        // Process dependencies
        for (RpmPackageAssociation dependency : this.getDependencies()) {
            if (null != dependency.getName()) {
                if (dependency.isVersionRange()) {
                    if (null != dependency.getMinVersion()) {
                        builder.addDependency(dependency.getName(), GREATER | EQUAL, dependency.getMinVersion());
                    }

                    if (null != dependency.getMaxVersion()) {
                        builder.addDependency(dependency.getName(), LESS, dependency.getMaxVersion());
                    }
                } else {
                    if (null != dependency.getVersion()) {
                        builder.addDependency(dependency.getName(), EQUAL, dependency.getVersion());
                    } else {
                        builder.addDependency(dependency.getName(), 0, "");
                    }
                }
            }
        }

        // Process obsoletes
        for (RpmPackageAssociation obsolete : this.getObsoletes()) {
            if (null != obsolete.getName()) {
                if (obsolete.isVersionRange()) {
                    if (null != obsolete.getMinVersion()) {
                        builder.addObsoletes(obsolete.getName(), GREATER | EQUAL, obsolete.getMinVersion());
                    }

                    if (null != obsolete.getMaxVersion()) {
                        builder.addObsoletes(obsolete.getName(), LESS, obsolete.getMaxVersion());
                    }
                } else {
                    if (null != obsolete.getVersion()) {
                        builder.addObsoletes(obsolete.getName(), EQUAL, obsolete.getVersion());
                    } else {
                        builder.addObsoletes(obsolete.getName(), 0, "");
                    }
                }
            }
        }

        // Process conflicts
        for (RpmPackageAssociation conflict : this.getConflicts()) {
            if (null != conflict.getName()) {
                if (conflict.isVersionRange()) {
                    if (null != conflict.getMinVersion()) {
                        builder.addConflicts(conflict.getName(), GREATER | EQUAL, conflict.getMinVersion());
                    }

                    if (null != conflict.getMaxVersion()) {
                        builder.addConflicts(conflict.getName(), LESS, conflict.getMaxVersion());
                    }
                } else {
                    if (null != conflict.getVersion()) {
                        builder.addConflicts(conflict.getName(), EQUAL, conflict.getVersion());
                    } else {
                        builder.addConflicts(conflict.getName(), 0, "");
                    }
                }
            }
        }


        // Event scripting
        this.getLog().debug("Setting event hook scripts");

        RpmScriptTemplateRenderer scriptTemplateRenderer = this.getMojo().getTemplateRenderer();
        String scriptPath = String.format("%s%s%s-%s", buildDirectory, File.separator, this.getName(), this.getProjectVersion());
        File scriptTemplate;

        scriptTemplate = this.getPreTransactionScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-pretrans-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPreTransScript(scriptFile);
            builder.setPreTransProgram(this.getPreTransactionProgram());
        }

        scriptTemplate = this.getPreInstallScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-preinstall-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPreInstallScript(scriptFile);
            builder.setPreInstallProgram(this.getPreInstallProgram());
        }

        scriptTemplate = this.getPostInstallScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-postinstall-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPostInstallScript(scriptFile);
            builder.setPostInstallProgram(this.getPostInstallProgram());
        }

        scriptTemplate = this.getPreUninstallScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-preuninstall-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPreUninstallScript(scriptFile);
            builder.setPreUninstallProgram(this.getPreUninstallProgram());
        }

        scriptTemplate = this.getPostUninstallScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-postuninstall-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPostUninstallScript(scriptFile);
            builder.setPostUninstallProgram(this.getPostUninstallProgram());
        }

        scriptTemplate = this.getPostTransactionScriptFile();
        if (null != scriptTemplate) {
            File scriptFile = new File(String.format("%s-posttrans-hook", scriptPath));
            scriptTemplateRenderer.render(scriptTemplate, scriptFile);
            builder.setPostTransScript(scriptFile);
            builder.setPostTransProgram(this.getPostTransactionProgram());
        }


        // Triggers
        for (RpmTrigger trigger : this.getTriggers()) {
            // Build trigger mapping
            Map<String,IntString> depends = new HashMap<String,IntString>();

            for (RpmPackageAssociation dependency : trigger.getDependencies()) {
                int flags = 0;
                String version = "";

                if (null != dependency.getVersion()) {
                    version = dependency.getVersion();
                }
                else if (null != dependency.getMinVersion()) {
                    flags = GREATER | EQUAL;
                    version = dependency.getMinVersion();
                }
                else if (null != dependency.getMaxVersion()) {
                    flags = LESS;
                    version = dependency.getMaxVersion();
                }

                depends.put(dependency.getName(), new IntString(flags, version));
            }

            scriptTemplate = trigger.getPreInstallScriptFile();
            if (null != scriptTemplate) {
                File scriptFile = new File(String.format("%s-preinstall-trigger", scriptPath));
                scriptTemplateRenderer.render(scriptTemplate, scriptFile);
                builder.addTrigger(scriptFile, trigger.getPreInstallProgram(), depends, SCRIPT_TRIGGERPREIN);
            }

            scriptTemplate = trigger.getPostInstallScriptFile();
            if (null != scriptTemplate) {
                File scriptFile = new File(String.format("%s-postinstall-trigger", scriptPath));
                scriptTemplateRenderer.render(scriptTemplate, scriptFile);
                builder.addTrigger(scriptFile, trigger.getPostInstallProgram(), depends, SCRIPT_TRIGGERIN);
            }

            scriptTemplate = trigger.getPreUninstallScriptFile();
            if (null != scriptTemplate) {
                File scriptFile = new File(String.format("%s-preuninstall-trigger", scriptPath));
                scriptTemplateRenderer.render(scriptTemplate, scriptFile);
                builder.addTrigger(scriptFile, trigger.getPreUninstallProgram(), depends, SCRIPT_TRIGGERUN);
            }

            scriptTemplate = trigger.getPostUninstallScriptFile();
            if (null != scriptTemplate) {
                File scriptFile = new File(String.format("%s-postuninstall-trigger", scriptPath));
                scriptTemplateRenderer.render(scriptTemplate, scriptFile);
                builder.addTrigger(scriptFile, trigger.getPostUninstallProgram(), depends, SCRIPT_TRIGGERPOSTUN);
            }
        }


        // Signing
        String keyFileName = this.getSigningKey();
        if (null != keyFileName) {
            File keyFile = new File(keyFileName);

            if ( ! keyFile.exists()) {
                // Key file not found, throw exception
                throw new SigningKeyFileNotFoundException(keyFileName);
            }

            String keyPassPhrase = this.getSigningKeyPassPhrase();
            if (null != keyPassPhrase && ! keyPassPhrase.equals("")) {
                builder.setPrivateKeyPassphrase(keyPassPhrase);
            }

            builder.setPrivateKeyId(this.getSigningKeyId());
            builder.setPrivateKeyRingFile(keyFile);
        }

        // Build file list
        this.getLog().debug("Adding files matched from each rule.");
        for (RpmPackageRule packageRule : this.getRules()) {
            Collections.addAll(fileList, packageRule.addFiles(builder));
        }


        // Build RPM
        String rpmFileName = String.format("%s%s%s", buildDirectory, File.separator, this.getFinalName());

        this.getLog().info(String.format("Generating RPM file %s", rpmFileName));
        File packageFile = new File(rpmFileName);

        FileOutputStream packageOutputStream = new FileOutputStream(packageFile);
        builder.build(packageOutputStream.getChannel());

        RpmMojo mojo = this.getMojo();

        if (mojo.getProjectPackagingType().equals("rpm")
                && mojo.getProjectArtifactId().equals(this.getName())
                && mojo.getProjectVersion().equals(this.getProjectVersion())) {
            // Add primary artifacts, only if the packaging type is rpm. attach flag is ignored for
            // primary artifacts
            this.getLog().info(String.format("Attaching %s as primary artifact", packageFile.getCanonicalPath()));
            mojo.setPrimaryArtifact(packageFile, this.getClassifier());
        }
        else if (this.isAttach()) {
            // Add secondary artifacts, but only if instructed to attach them
            this.getLog().info(String.format("Attaching %s as secondary artifact", packageFile.getCanonicalPath()));
            mojo.addSecondaryArtifact(packageFile, this.getName(), this.getProjectVersion(), this.getClassifier());
        }

        return fileList;
    }

    /**
     * List files matched for the package
     *
     * @return Files included within the package
     * @throws AbstractRpmException
     */
    public Set<String> listFiles() throws AbstractRpmException
    {
        int counter = 1;
        Set<String> fileList = new HashSet<String>();
        this.getMojo().getLog().info(String.format("    Package: %s", this.getFinalName()));

        // Build file list
        for (RpmPackageRule packageRule : this.getRules()) {
            this.getMojo().getLog().info(String.format("        \\ Rule: %d", counter++));

            String[] packageRuleFileList = packageRule.listFiles();
            String scanPath = packageRule.getScanPath();

            for (String packageRulefileName : packageRuleFileList) {
                this.getMojo().getLog().info(String.format("            - %s/%s", scanPath, packageRulefileName));
            }

            Collections.addAll(fileList, packageRuleFileList);
        }
        this.getMojo().getLog().info("");

        return fileList;
    }
}
