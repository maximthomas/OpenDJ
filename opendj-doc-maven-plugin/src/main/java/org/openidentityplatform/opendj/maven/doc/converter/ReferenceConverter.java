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

public class ReferenceConverter implements Converter {

    private ReferenceConverter() {}

    public static ReferenceConverter INSTANCE = new ReferenceConverter();

    @Override
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        ConverterUtils.writeId(element, adoc);
        for(Element child : element.elements()) {
            if(child.getName().equals("title")) {
                adoc.append("== ").appendln(child.getStringValue());
            } else {
                ElementConverter.INSTANCE.convert(child, adoc, context);
            }
        }
    }
}
