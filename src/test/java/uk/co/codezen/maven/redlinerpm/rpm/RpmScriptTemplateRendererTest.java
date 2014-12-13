package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

public class RpmScriptTemplateRendererTest
{
    String testOutputPath;

    @Before
    public void setUp()
    {
        // Test output path
        this.testOutputPath = System.getProperty("project.build.testOutputDirectory");
    }

    @Test
    public void render() throws Exception
    {
        File templateScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/rpm/RpmScriptTemplateRenderer-template", this.testOutputPath));
        File expectedScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/rpm/RpmScriptTemplateRenderer-template-expected", this.testOutputPath));
        File actualScriptFile = new File(
                String.format("%s/unit/uk/co/codezen/maven/redlinerpm/rpm/RpmScriptTemplateRenderer-template-actual", this.testOutputPath));

        RpmScriptTemplateRenderer renderer = new RpmScriptTemplateRenderer();
        renderer.addParameter("testdata1", true);
        renderer.addParameter("testdata2", "test");
        renderer.addParameter("testdata3", 123);


        // Confirm the file has been generated first
        assertEquals(false, actualScriptFile.exists());
        renderer.render(templateScriptFile, actualScriptFile);
        assertEquals(true, actualScriptFile.exists());

        FileReader reader;
        char[] buff = new char[1024];
        StringBuilder stringBuilder;
        int bytesRead;


        // Read the actual template
        stringBuilder = new StringBuilder();
        reader = new FileReader(actualScriptFile);

        while (-1 != (bytesRead = reader.read(buff))) {
            stringBuilder.append(buff, 0, bytesRead);
        }

        reader.close();

        String actualTemplateContents = stringBuilder.toString();


        // Read the expected template
        stringBuilder = new StringBuilder();
        reader = new FileReader(expectedScriptFile);

        while (-1 != (bytesRead = reader.read(buff))) {
            stringBuilder.append(buff, 0, bytesRead);
        }

        reader.close();

        String expectedTemplateContents = stringBuilder.toString();

        assertEquals(expectedTemplateContents, actualTemplateContents);
    }
}
