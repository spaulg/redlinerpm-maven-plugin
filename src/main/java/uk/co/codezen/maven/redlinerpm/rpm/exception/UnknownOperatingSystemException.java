
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

package uk.co.codezen.maven.redlinerpm.rpm.exception;

/**
 * Unknown operating system exception
 */
final public class UnknownOperatingSystemException extends AbstractRpmException
{
    /**
     * Constructor
     *
     * @param unknownOperatingSystem Unknown operating system
     */
    public UnknownOperatingSystemException(String unknownOperatingSystem)
    {
        this.message = String.format("Unknown operating system '%s'", unknownOperatingSystem);
    }

    /**
     * Constructor
     *
     * @param unknownOperatingSystem Unknown operating system
     * @param cause Exception cause
     */
    public UnknownOperatingSystemException(String unknownOperatingSystem, Throwable cause)
    {
        this.message = String.format("Unknown operating system '%s'", unknownOperatingSystem);
        this.cause = cause;
    }
}
