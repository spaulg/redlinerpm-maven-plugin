package uk.co.codezen.maven.redlinerpm.mocks;

import org.redline_rpm.Builder;
import org.redline_rpm.payload.Contents;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;

final public class MockBuilder extends Builder
{
    /**
     * Get the contents
     * @return RPM package contents
     */
    public Contents getContents()
    {
        return this.contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String build( final File directory) throws NoSuchAlgorithmException, IOException
    {
        return "test-1.0.0-1.noarch.rpm";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build( final FileChannel original) throws NoSuchAlgorithmException, IOException
    {

    }
}
