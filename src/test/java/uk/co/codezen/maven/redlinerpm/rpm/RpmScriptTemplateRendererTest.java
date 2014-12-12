package uk.co.codezen.maven.redlinerpm.rpm;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.junit.Test;

public class RpmScriptTemplateRendererTest
{
    @Test
    public void render() throws Exception
    {
        ClassLoader cl = this.getClass().getClassLoader();
        URL templateResource = cl.getResource("unit/uk/co/codezen/maven/redlinerpm/rpm/RpmScriptTemplateRenderer-template");
        URL expectedResource = cl.getResource("unit/uk/co/codezen/maven/redlinerpm/rpm/RpmScriptTemplateRenderer-template-expected");

        if (null == templateResource || null == expectedResource) {
            throw new Exception("Failed to find template or expected template resources");
        }

        File templateScriptFile = new File(templateResource.getPath());
        File expectedScriptFile = new File(expectedResource.getPath());
        File actualScriptFile = new File(templateScriptFile.getParent() + File.separator + "RpmScriptTemplateRenderer-template-actual");

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
