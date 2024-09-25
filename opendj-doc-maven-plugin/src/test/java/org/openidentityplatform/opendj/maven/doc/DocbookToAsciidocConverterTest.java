package org.openidentityplatform.opendj.maven.doc;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class DocbookToAsciidocConverterTest {
    @Test
    public void testConvert() throws Exception {
        File testFile = new File(getClass().getResource("/docbook/man-pages/man-dsconfig.xml").toURI());
        String docbook = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        DocbookToAsciidocConverter converter = new DocbookToAsciidocConverter();
        String asciidoc = converter.convert(docbook);
        System.out.println(asciidoc.toString());
    }

    @Test
    public void multipleFilesConverter() throws Exception {


        List<String> manFiles = new ArrayList<>();
        manFiles.add("addrate");
        manFiles.add("authrate");
        manFiles.add("base64");
        manFiles.add("ldapcompare");
        manFiles.add("ldapdelete");
        manFiles.add("ldapmodify");
        manFiles.add("ldappasswordmodify");
        manFiles.add("ldapsearch");
        manFiles.add("ldifdiff");
        manFiles.add("ldifmodify");
        manFiles.add("ldifsearch");
        manFiles.add("makeldif");
        manFiles.add("modrate");
        manFiles.add("searchrate");

        manFiles.add("backendstat");
        manFiles.add("backup");
        manFiles.add("control-panel");
        manFiles.add("create-rc-script");
        manFiles.add("dsconfig");
        manFiles.add("dsreplication");
        manFiles.add("encode-password");
        manFiles.add("export-ldif");
        manFiles.add("import-ldif");
        manFiles.add("list-backends");
        manFiles.add("manage-account");
        manFiles.add("manage-tasks");
        manFiles.add("rebuild-index");
        manFiles.add("restore");
        manFiles.add("setup");
        manFiles.add("start-ds");
        manFiles.add("status");
        manFiles.add("stop-ds");
        manFiles.add("uninstall");
        manFiles.add("upgrade");
        manFiles.add("verify-index");

        List<String> commonFiles = new ArrayList<>();
        commonFiles.add("filters.xml");
        commonFiles.add("attributes.xml");
        commonFiles.add("files.xml");
        commonFiles.add("description-resource-path.xml");


        URI manPagesUri = getClass().getResource("/docbook/man-pages").toURI();
        //File manPages = new File();
        Path mpPath = Paths.get(manPagesUri);

        for(String manFile : manFiles) {
            System.out.println("" + manFile);
//            try (DirectoryStream<Path> dir = Files.newDirectoryStream(mpPath, "man-" + manFile + "*.xml")) {
//
//                for (Path entry : dir) {
//                    System.out.println("--" + entry);
//                    String docbook = FileUtils.readFileToString(entry.toFile(), StandardCharsets.UTF_8);
//                    DocbookToAsciidocConverter converter = new DocbookToAsciidocConverter();
//                    String asciidoc = converter.convert(docbook);
//                    System.out.println(asciidoc.toString());
//                }
//            }
//            try (DirectoryStream<Path> dir = Files.newDirectoryStream(mpPath, manFile + "-examples.xml")) {
//
//                for (Path entry : dir) {
//                    System.out.println("--" + entry);
//                    String docbook = FileUtils.readFileToString(entry.toFile(), StandardCharsets.UTF_8);
//                    DocbookToAsciidocConverter converter = new DocbookToAsciidocConverter();
//                    String asciidoc = converter.convert(docbook);
//                    System.out.println(asciidoc.toString());
//                }
//            }
        }
        try (DirectoryStream<Path> dir = Files.newDirectoryStream(mpPath, "exit-codes-*.xml")) {

            for (Path entry : dir) {
                System.out.println("--" + entry);
                String docbook = FileUtils.readFileToString(entry.toFile(), StandardCharsets.UTF_8);
                DocbookToAsciidocConverter converter = new DocbookToAsciidocConverter();
                String asciidoc = converter.convert(docbook);
                System.out.println(asciidoc.toString());
            }
        }
    }
}