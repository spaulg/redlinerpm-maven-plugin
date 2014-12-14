package uk.co.codezen.maven.redlinerpm.mocks;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import uk.co.codezen.maven.redlinerpm.mojo.AbstractRpmMojo;

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
}
