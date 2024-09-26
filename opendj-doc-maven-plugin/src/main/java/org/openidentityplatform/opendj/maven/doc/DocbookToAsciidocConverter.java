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

import org.apache.commons.text.TextStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openidentityplatform.opendj.maven.doc.converter.Context;
import org.openidentityplatform.opendj.maven.doc.converter.ConversionException;
import org.openidentityplatform.opendj.maven.doc.converter.ElementConverter;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class DocbookToAsciidocConverter {


    public DocbookToAsciidocConverter() {

    }

    public String convert(String docbook) throws DocumentException, ConversionException {
        SAXReader xmlReader = new SAXReader();
        InputSource source = new InputSource(new StringReader(docbook));
        Document xmlDoc = xmlReader.read(source);
        TextStringBuilder adoc = new TextStringBuilder();
        appendCopyright(xmlDoc, adoc);
        Element element = xmlDoc.getRootElement();
        Context context = new Context();
        ElementConverter.INSTANCE.convert(element, adoc, context);
        return adoc.toString();
    }

    private void appendCopyright(Document xmlDoc, TextStringBuilder adoc) {
        if(!xmlDoc.nodeIterator().hasNext()) {
            return;
        }
        Node copyNode = xmlDoc.nodeIterator().next();
        String copyright = copyNode.getStringValue();
        adoc.appendln("////");
        adoc.appendln(copyright);
        adoc.appendln("////");

    }



}
