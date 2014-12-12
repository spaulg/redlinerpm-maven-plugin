
# redlinerpm-maven-plugin

http://redline-rpm.org/ based RPM Maven packaging plugin.

Add this plugin as a plugin dependency to your Maven projects to package your software
as an RPM file. Uses the http://redline-rpm.org/ RPM packaging library to provide
operating system agnostic RPM builds.  

## Usage

Add the plugin to the <plugins> section within your pom.xml file.

```xml
<plugins>
    <plugin>
        <groupId>uk.co.codezen</groupId>
        <artifactId>redlinerpm-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <extensions>true</extensions>
    
        <configuration>
            <buildPath>test</buildPath>
            <defaultOwner>root</defaultOwner>
            <defaultGroup>root</defaultGroup>
            <defaultFileMode>0644</defaultFileMode>
            <performCheckingForExtraFiles>true</performCheckingForExtraFiles>
    
            <excludes>
                <source>**/*.bak</source>
            </excludes>
    
            <packages>
                <package>
                    <name>mypackage</name>
                    <version>1.0-SNAPSHOT</version>
                    <url>http://www.codezen.co.uk</url>
    
                    <rules>
                        <rule>
                            <base>/</base>
                            <destination>/var/www</destination>
                            <owner>root</owner>
                            <group>root</group>
                            <fileMode>0644</fileMode>
    
                            <includes>
                                <include>**/*.ini</include>
                            </includes>
    
                            <directives>
                                <directive>config</directive>
                                <directive>noreplace</directive>
                            </directives>
                        </rule>
    
                        <rule>
                            <base>/</base>
                            <destination>/var/www</destination>
    
                            <includes>
                                <include>**/*.php</include>
                            </includes>
    
                            <excludes>
                                <exclude>cache/*.php</exclude>
                            </excludes>
                        </rule>
                    </rules>
                </package>
            </packages>
        </configuration>
    </plugin>
</plugins>
```

## Motivations

Why create another RPM maven plugin, when there is already rpm-maven-plugin
and riot-redline-plugin?

Both plugins have their merits, I wanted a plugin that combined the merits of
both, without the weaknesses.

redlinerpm-maven-plugin provides OS agnostic builds, the rpm packaging type, 
ant based flexible file inclusion/exclusion globs, building and deploying 
multiple RPM packages, sane defaults, and adding directives to files.   

## License

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
