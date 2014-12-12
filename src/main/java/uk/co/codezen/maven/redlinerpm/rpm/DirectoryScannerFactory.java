
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

import org.apache.tools.ant.DirectoryScanner;

/**
 * Factory a directory scanner
 */
final public class DirectoryScannerFactory
{
    /**
     * Construct a directory scanner using sane default
     *
     * @param baseDir Base directory of scan
     * @param includes Included files rules
     * @param excludes Excluded files rules
     * @return Constructed Directory scanner
     */
    public static DirectoryScanner factory(String baseDir, String[] includes, String[] excludes)
    {
        DirectoryScanner ds = new DirectoryScanner();
        ds.setIncludes(includes);
        ds.setExcludes(excludes);
        ds.setBasedir(baseDir);
        ds.setFollowSymlinks(false);
        ds.setCaseSensitive(true);

        return ds;
    }

    /**
     * Static class
     */
    private DirectoryScannerFactory()
    {

    }
}
