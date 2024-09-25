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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.TextStringBuilder;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class TableConverter implements Converter {
    private TableConverter() {}
    public static TableConverter INSTANCE = new TableConverter();
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        context.tableLevel++;

        ConverterUtils.writeId(element, adoc);

        List<Element> children = element.elements();
        for (Element child : children) {
            if(child.getName().equals("title")) {
                adoc.append(".").appendln(child.getStringValue());
            } else if(child.getName().equals("textobject")) {
                continue;
            } else if (child.getName().equals("tgroup")) {
                List<Element> colSpecs = child.elements("colspec");
                int sumWidth = 0;
                for(Element colSpec : colSpecs) {
                    sumWidth += Integer.parseInt(colSpec.attribute("colwidth").getValue().replace("*", ""));
                }
                int widthCoeff = 100 / sumWidth;
                adoc.append("[cols=\"");
                List<String> adocCols = new ArrayList<>();
                for(Element colSpec : colSpecs) {
                    int widthPercent = Integer.parseInt(colSpec.attribute("colwidth").getValue().replace("*", "")) * widthCoeff;
                    adocCols.add("" + widthPercent +  "%");
                }
                adoc.append(StringUtils.join(adocCols, ",")).appendln("\"]");

                adoc.appendln("|===");

                List<Element> headers = child.element("thead").element("row").elements();
                for(Element header : headers) {
                    adoc.append("|").append(header.getStringValue()).append(" ");
                }
                adoc.appendNewLine();
                List<Element> rows = child.element("tbody").elements("row");
                for(Element row : rows) {
                    List<Element> cols = row.elements("entry");
                    for(Element col : cols) {
                        adoc.append("a|");
                        List<Element> els = col.elements();
                        for(Element el : els) {
                            ElementConverter.INSTANCE.convert(el, adoc, context);
                        }
                    }
                    adoc.appendNewLine();
                }

            } else {
                throw new ConversionException("unknown element: " + element);
            }
        }
        adoc.appendln("|===");
        context.tableLevel--;
    }
}
