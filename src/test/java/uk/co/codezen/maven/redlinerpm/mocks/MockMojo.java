package uk.co.codezen.maven.redlinerpm.mocks;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import uk.co.codezen.maven.redlinerpm.mojo.AbstractRpmMojo;
import uk.co.codezen.maven.redlinerpm.rpm.RpmPackage;

import java.util.List;
import java.util.Set;

public class MockMojo extends AbstractRpmMojo
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanMasterFiles()
    {
        super.scanMasterFiles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws MojoExecutionException
    {
        super.validate();
    }

    /**
     * Get master file set
     *
     * @return Master file set
     */
    public Set<String> getMasterFiles()
    {
        return this.masterFiles;
    }

    /**
     * Get excludes
     *
     * @return Excludes
     */
    public List<String> getExcludes()
    {
        return this.excludes;
    }

    /**
     * Get packages
     *
     * @return Packages
     */
    public List<RpmPackage> getPackages()
    {
        return this.packages;
    }
}
