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

package org.openidentityplatform.opendj.maven.doc.converter;

import org.apache.commons.text.TextStringBuilder;
import org.dom4j.Element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RefEntryConverter implements Converter {

    private RefEntryConverter() {}

    public static RefEntryConverter INSTANCE = new RefEntryConverter();

    @Override
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        adoc.appendNewLine();
        ConverterUtils.writeId(element, adoc);
        List<Element> children = element.elements();
        for(Element child : children) {
            if(ignoreElement(child)) {
                continue;
            }
            if(child.getName().equals("refnamediv")) {
                convertRefName(child, adoc, context);
            } else if(child.getName().equals("refsynopsisdiv")) {
                convertRefSynopsis(child, adoc, context);
            } else if(child.getName().equals("refsect1")) {
                convertRefSect1(child, adoc, context);
            } else {
                ElementConverter.INSTANCE.convert(child, adoc, context);
            }
        }
    }

    private void convertRefName(Element element, TextStringBuilder adoc, Context context) {
         String refName = element.element("refname").getStringValue();
         String refPurpose = element.element("refpurpose").getStringValue();
         adoc.append("=== ").append(refName).append(" - ").appendln(refPurpose);
    }

    private void convertRefSynopsis(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        adoc.appendNewLine();
        adoc.appendln("==== Synopsis");
        Element cmdSynopsis = element.element("cmdsynopsis");
        Element synopsis = element.element("synopsis");
        if(cmdSynopsis != null) {
            String cmd = cmdSynopsis.element("command").getStringValue();
            adoc.append("`").append(cmd).append("`");
            Element arg = cmdSynopsis.element("arg");
            if(arg != null) {
                adoc.append(" ").append(arg.getStringValue());
            }
        } else if (synopsis != null) {
            ProgramListingConverter.INSTANCE.convert(synopsis, adoc, context);
        } else {
            throw new ConversionException("bad sysnoposis type");
        }
        adoc.appendNewLine();
    }

    public void convertRefSect1(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        adoc.appendNewLine();
        ConverterUtils.writeId(element, adoc);
        for(Element child : element.elements()) {
            if(child.getName().equals("title")) {
                adoc.append("==== ").append(child.getStringValue()).appendNewLine();
            } else if(child.getName().equals("refsect2")) {
                convertRefSect2(child, adoc, context);
            } else {
                ElementConverter.INSTANCE.convert(child, adoc, context);
            }
        }
    }

    private void convertRefSect2(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        adoc.appendNewLine();
        ConverterUtils.writeId(element, adoc);
        for(Element child : element.elements()) {
            if(child.getName().equals("title")) {
                adoc.append("===== ").append(child.getStringValue()).appendNewLine();
            } else if(child.getName().equals("refsect3")) {
                convertRefSect3(child, adoc, context);
            } else {
                ElementConverter.INSTANCE.convert(child, adoc, context);
            }
        }
    }

    private void convertRefSect3(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        adoc.appendNewLine();
        ConverterUtils.writeId(element, adoc);
        for(Element child : element.elements()) {
            if(child.getName().equals("title")) {
                adoc.append("====== ").append(child.getStringValue()).appendNewLine();
            } else {
                ElementConverter.INSTANCE.convert(child, adoc, context);
            }
        }
    }

    private boolean ignoreElement(Element element) {
        Set<String> ignoreElements = new HashSet<>();
        ignoreElements.add("info");
        ignoreElements.add("refmeta");
        return ignoreElements.contains(element.getName());
    }
}
