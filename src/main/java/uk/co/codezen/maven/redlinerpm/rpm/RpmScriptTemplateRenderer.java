
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

import org.mvel2.templates.TemplateRuntime;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RPM event hook template
 */
final public class RpmScriptTemplateRenderer
{
    /**
     * Template parameter map
     */
    private Map<String,Object> parameterMap = new HashMap<String,Object>();

    /**
     * Add parameter to parameter map
     *
     * @param name Parameter Name
     * @param value Parameter value
     */
    public void addParameter(String name, Object value)
    {
        this.parameterMap.put(name, value);
    }

    /**
     * Render a script template file
     *
     * @param templateFile Template file
     * @param renderedFile Rendered output file
     */
    public void render(File templateFile, File renderedFile) throws IOException
    {
        // Read the template file
        char[] buffer = new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        FileReader reader = null;

        try {
            reader = new FileReader(templateFile);

            int bytesRead;
            while (-1 != (bytesRead = reader.read(buffer))) {
                stringBuilder.append(buffer, 0, bytesRead);
            }
        }
        finally {
            if (null != reader) {
                reader.close();
            }
        }

        // Parse the template
        String renderedTemplate = (String) TemplateRuntime.eval(stringBuilder.toString(), this.parameterMap);

        // Write the generated script
        FileWriter writer = null;

        try {
            writer = new FileWriter(renderedFile);
            writer.write(renderedTemplate);
        }
        finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
