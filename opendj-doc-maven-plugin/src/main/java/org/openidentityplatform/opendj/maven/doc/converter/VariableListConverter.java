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

import java.util.List;

public class VariableListConverter implements Converter {

    private VariableListConverter() {}
    public static VariableListConverter INSTANCE = new VariableListConverter();
    @Override
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        ConverterUtils.writeId(element, adoc);
        String title = ConverterUtils.getTitle(element);
        if(title != null) {
            adoc.append(".").appendln(title);
        }
        adoc.appendln("--");
        List<Element> children = element.elements("varlistentry");
        for(Element child: children) {
            convertVarListEntry(child, adoc, context);
        }
        adoc.appendln("--");
        adoc.appendNewLine();
    }

    private void convertVarListEntry(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        List<Element> children = element.elements();
        for(Element child : children) {
            if(child.getName().equals("term")) {
                ConverterUtils.convertChildren(child, adoc, context);
                adoc.appendln("::");
            } else if(child.getName().equals("listitem")) {
                convertListItem(child, adoc, context);
            }
        }
        adoc.appendNewLine();
    }

    private void convertListItem(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        List<Element> children = element.elements();
        for (int i = 0; i < children.size(); i++) {
            if(i > 0) {
                adoc.appendln("+");
            }
            ElementConverter.INSTANCE.convert(children.get(i), adoc, context);
        }
    }
}
