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
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.List;

public class ConverterUtils {
    public static void writeId(Element element, TextStringBuilder adoc) {
        Attribute id = element.attribute("id");
        if(id != null) {
            adoc.append("[#").append(id.getValue()).appendln("]");
        }
    }

    public static void convertChildren(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        List<Node> nodes = element.content();
        for(Node node : nodes) {
            if(node instanceof Text) {
                adoc.append(escapeVerticalLine(node.getText(), context));
            } else {
                ElementConverter.INSTANCE.convert((Element) node, adoc, context);
            }
        }
    }

    public static String getTitle(Element element) {
        Element title = element.element("title");
        if(title != null) {
            return title.getStringValue();
        }
        return null;
    }

    public static String escapeVerticalLine(String text, Context context) {
        if(context.tableLevel > 0) {
            return text.replace("|", "\\|");
        }
        return text;
    }

}
