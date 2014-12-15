
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
 * Unknown architecture exception
 */
final public class UnknownArchitectureException extends AbstractRpmException
{
    /**
     * Constructor
     *
     * @param unknownArchitecture Unknown architecture name
     */
    public UnknownArchitectureException(String unknownArchitecture)
    {
        this.message = String.format("Unknown architecture '%s'", unknownArchitecture);
    }

    /**
     * Constructor
     *
     * @param unknownArchitecture Unknown architecture name
     * @param cause Exception cause
     */
    public UnknownArchitectureException(String unknownArchitecture, Throwable cause)
    {
        this.message = String.format("Unknown architecture '%s'", unknownArchitecture);
        this.cause = cause;
    }
}
