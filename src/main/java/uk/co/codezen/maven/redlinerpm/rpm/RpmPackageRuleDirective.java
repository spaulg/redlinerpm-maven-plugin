
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

import org.redline_rpm.payload.Directive;
import uk.co.codezen.maven.redlinerpm.rpm.exception.InvalidRpmPackageRuleDirectiveException;

import java.util.List;

/**
 * RPM directives
 */
final public class RpmPackageRuleDirective
{
    /**
     * Factory the directive object
     *
     * @param directiveList RPM directive list
     * @throws InvalidRpmPackageRuleDirectiveException
     */
    public static Directive newDirective(List<String> directiveList) throws InvalidRpmPackageRuleDirectiveException
    {
        Directive rpmDirective = new Directive();

        for (String directive : directiveList) {
            directive = directive.toLowerCase();

            if (directive.equals("config")) {
                rpmDirective.set(Directive.RPMFILE_CONFIG);
            }
            else if (directive.equals("doc")) {
                rpmDirective.set(Directive.RPMFILE_DOC);
            }
            else if (directive.equals("icon")) {
                rpmDirective.set(Directive.RPMFILE_ICON);
            }
            else if (directive.equals("missingok")) {
                rpmDirective.set(Directive.RPMFILE_MISSINGOK);
            }
            else if (directive.equals("noreplace")) {
                rpmDirective.set(Directive.RPMFILE_NOREPLACE);
            }
            else if (directive.equals("specfile")) {
                rpmDirective.set(Directive.RPMFILE_SPECFILE);
            }
            else if (directive.equals("ghost")) {
                rpmDirective.set(Directive.RPMFILE_GHOST);
            }
            else if (directive.equals("license")) {
                rpmDirective.set(Directive.RPMFILE_LICENSE);
            }
            else if (directive.equals("readme")) {
                rpmDirective.set(Directive.RPMFILE_README);
            }
            else if (directive.equals("unpatched")) {
                rpmDirective.set(Directive.RPMFILE_UNPATCHED);
            }
            else if (directive.equals("pubkey")) {
                rpmDirective.set(Directive.RPMFILE_PUBKEY);
            }
            else if (directive.equals("policy")) {
                rpmDirective.set(Directive.RPMFILE_POLICY);
            }
            else {
                // Invalid
                throw new InvalidRpmPackageRuleDirectiveException(directive);
            }
        }

        return rpmDirective;
    }

    /**
     * Static class. Prevent instantiation.
     */
    private RpmPackageRuleDirective()
    {

    }
}
