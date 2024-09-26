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
import org.dom4j.DocumentException;
import org.openidentityplatform.opendj.maven.doc.converter.ConversionException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Mojo(name = "generate-asciidoc", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateAsciiDocFromDocBookMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/docbkx-sources/man-pages")
    protected File mpPath;

    @Parameter(defaultValue = "${project.build.directory}/asciidoc/source/partials")
    protected File adocPath;

    @Parameter
    private List<DocBookDirToConvert> dirs;

    @Parameter
    private List<DocBookFileToConvert> files;

    @Parameter
    private List<String> manPages;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DocbookToAsciidocConverter docbookToAsciidocConverter = new DocbookToAsciidocConverter();
        if (dirs != null) {

            for(DocBookDirToConvert dir : dirs) {
                getLog().info("convert directory: " + dir );
                File inputDir = new File(dir.getInput());
                File outputDir = new File(dir.getOutput());

                for(File input: inputDir.listFiles(pathname -> {
                    if(!pathname.isFile()) {
                        return false;
                    } else if(!pathname.getName().endsWith(".xml")) {
                        return false;
                    } else if(dir.getIgnoreFiles().contains(pathname.getName())) {
                        return false;
                    }
                    return true;
                })) {
                    String outputFileName = input.getName().replace(".xml", ".adoc");
                    File output = new File(outputDir, outputFileName);
                    try {
                        String docbook = FileUtils.readFileToString(input, StandardCharsets.UTF_8);
                        String adoc = docbookToAsciidocConverter.convert(docbook);
                        FileUtils.write(output, adoc, StandardCharsets.UTF_8, false);
                    } catch (Exception e) {
                        throw new MojoExecutionException("error converting file " + input, e);
                    }
                }
            }
        }

        if (files != null) {
            for (DocBookFileToConvert file : files) {
                getLog().info("convert file: " + file);
                File input = new File(file.getInput());
                File output = new File(file.getOutput());
                try {
                    String docbook = FileUtils.readFileToString(input, StandardCharsets.UTF_8);
                    String adoc = docbookToAsciidocConverter.convert(docbook);
                    FileUtils.write(output, adoc, StandardCharsets.UTF_8, false);
                } catch (Exception e) {
                    throw new MojoExecutionException("error converting file " + input, e);
                }
            }
        }

        if (manPages != null) {
            for (String manPage : manPages) {
                convertManPagesDirectoryStream("*" + manPage + "*.xml");
            }
            convertManPagesDirectoryStream("exit-codes-*.xml");
        }
    }

    private void convertManPagesDirectoryStream(String glob) throws MojoExecutionException {
        try (DirectoryStream<Path> dir = Files.newDirectoryStream(mpPath.toPath(), glob)) {
            for (Path entry : dir) {
                convertManPage(entry);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("error converting exit code", e);
        }
    }

    private void convertManPage(Path entry) throws IOException, DocumentException, ConversionException {
        getLog().info("processing file " + entry.toFile().getName());
        String docbook = FileUtils.readFileToString(entry.toFile(), StandardCharsets.UTF_8);
        DocbookToAsciidocConverter converter = new DocbookToAsciidocConverter();
        String adoc = converter.convert(docbook);
        File output = new File(adocPath, entry.toFile().getName().replace(".xml", ".adoc"));
        FileUtils.write(output, adoc, StandardCharsets.UTF_8, false);
    }
}
