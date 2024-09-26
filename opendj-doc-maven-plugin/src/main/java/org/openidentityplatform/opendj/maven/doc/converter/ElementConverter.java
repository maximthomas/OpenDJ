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

import java.util.HashMap;
import java.util.Map;

public class ElementConverter implements Converter {
    private final Map<String, Converter> converterMap;

    public static ElementConverter INSTANCE = new ElementConverter();

    private ElementConverter() {
        converterMap = new HashMap<>();
        converterMap.put("table", TableConverter.INSTANCE);
        converterMap.put("para", ParagraphConverter.INSTANCE);

        converterMap.put("literal", LiteralConverter.INSTANCE);
        converterMap.put("command", LiteralConverter.INSTANCE);
        converterMap.put("option", LiteralConverter.INSTANCE);
        converterMap.put("filename", LiteralConverter.INSTANCE);

        converterMap.put("section", SectionConverter.INSTANCE);
        converterMap.put("variablelist", VariableListConverter.INSTANCE);
        converterMap.put("itemizedlist", ItemizedListConverter.INSTANCE);
        converterMap.put("refentry", RefEntryConverter.INSTANCE);
        converterMap.put("include", XIncludeConverter.INSTANCE);
        converterMap.put("replaceable", EmphasisConverter.INSTANCE);
        converterMap.put("firstterm", EmphasisConverter.INSTANCE);
        converterMap.put("emphasis", EmphasisConverter.INSTANCE);
        converterMap.put("programlisting", ProgramListingConverter.INSTANCE);
        converterMap.put("xref", XRefConverter.INSTANCE);
        converterMap.put("reference", ReferenceConverter.INSTANCE);
        converterMap.put("partintro", PartIntroConverter.INSTANCE);
        converterMap.put("link", LinkConverter.INSTANCE);
        converterMap.put("olink", OLinkConverter.INSTANCE);
        converterMap.put("refsect1", RefEntryConverter.INSTANCE::convertRefSect1);
        converterMap.put("screen", ProgramListingConverter.INSTANCE);
        converterMap.put("citetitle", ChildrenConverter.INSTANCE);
        converterMap.put("orderedlist", OrderedListConverter.INSTANCE);
        converterMap.put("informalexample", InformalExampleConverter.INSTANCE);
        converterMap.put("important", ImportantConverter.INSTANCE);
        converterMap.put("trademark", ImportantConverter.INSTANCE);
    }

    @Override
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        Converter converter = converterMap.get(element.getName());
        if(converter == null) {
            throw new ConversionException("unknown element " + element.asXML());
        }
        converter.convert(element, adoc, context);
    }
}
