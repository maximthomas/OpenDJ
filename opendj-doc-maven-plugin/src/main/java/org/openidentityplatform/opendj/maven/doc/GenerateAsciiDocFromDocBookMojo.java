/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2024 3A Systems,LLC.
 */

package org.openidentityplatform.opendj.maven.doc;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Mojo(name = "generate-asciidoc", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateAsciiDocFromDocBookMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/docbkx-sources/man-pages")
    protected File buildDirectory;

    @Parameter
    private List<DocBookFileToConvert> files;

    @Parameter
    private List<String> manPages;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DocbookToAsciidocConverter docbookToAsciidocConverter = new DocbookToAsciidocConverter();
        if(files != null) {
            for (DocBookFileToConvert file : files) {
                File input = new File(file.getInputDocBookFile());
                File output = new File(file.getOutputAsciiDocFile());
                try {
                    String docbook = FileUtils.readFileToString(input, StandardCharsets.UTF_8);
                    String adoc = docbookToAsciidocConverter.convert(docbook);
                    FileUtils.write(output, adoc, StandardCharsets.UTF_8, false);
                } catch (Exception e) {
                    throw new MojoExecutionException("error converting file " + input, e);
                }
            }
        }

        if(manPages != null) {
            for (String manPage : manPages) {

            }
        }
    }
}
