
# redlinerpm-maven-plugin

http://redline-rpm.org/ based RPM Maven packaging plugin.

Add this plugin as a plugin dependency to your Maven projects to package your software
as an RPM file. Uses the http://redline-rpm.org/ RPM packaging library to provide
operating system agnostic RPM builds and requires Maven v3+ and Java 1.6+.

## Usage

Add the plugin to the <plugins> section within your pom.xml file.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>my-project</artifactId>
    <version>1.1</version>
    <packaging>rpm</packaging>
    
    <build>
        <plugins>
            <plugin>
                <groupId>uk.co.codezen</groupId>
                <artifactId>redlinerpm-maven-plugin</artifactId>
                <version>2.1</version>
                <extensions>true</extensions>
             
                <configuration>
                    <defaultUser>apache</defaultUser>
                    <defaultGroup>apache</defaultGroup>
                    <defaultDestination>/var/www/${project.build.name}</defaultDestination>
         
                    <excludes>
                        <source>**/*.bak</source>
                    </excludes>
             
                    <packages>
                        <package>
                            <rules>
                                <rule>
                                    <includes>
                                        <include>**/*.ini</include>
                                    </includes>
             
                                    <directives>
                                        <directive>config</directive>
                                        <directive>noreplace</directive>
                                    </directives>
                                </rule>
             
                                <rule>
                                    <includes>
                                        <include>**/*</include>
                                    </includes>
             
                                    <excludes>
                                        <exclude>**/*.bak</exclude>
                                        <exclude>**/*.ini</exclude>
                                        <exclude>cache/*.php</exclude>
                                    </excludes>
                                </rule>
                            </rules>
                        </package>
                    </packages>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## Motivations

Why create another RPM maven plugin, when there is already rpm-maven-plugin
and riot-redline-plugin?

Both plugins have their merits, I wanted a plugin that combined the merits of
both, without the weaknesses.

redlinerpm-maven-plugin provides OS agnostic builds, the rpm packaging type, 
ant based flexible file inclusion/exclusion globs, building and deploying 
multiple RPM packages, sane defaults, adding directives to files, and template
triggers and event hook scripts using Java expression language and Maven EL
variables.

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
