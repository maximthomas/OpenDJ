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

import java.net.URI;
import java.net.URISyntaxException;

public class LinkConverter implements Converter {

    private LinkConverter() {}

    public static LinkConverter INSTANCE = new LinkConverter();
    @Override
    public void convert(Element element, TextStringBuilder adoc, Context context) throws ConversionException {
        String href = element.attribute("href").getStringValue();

        String window = "";
        if("new".equals(element.attributeValue("show"))) {
            window = ", window=_blank";
        }

        String text = element.getStringValue();
        if("http://docbook.org/xlink/role/olink".equals(element.attributeValue("role"))) {
            adoc.append("xref:");
            try {
                href = convertXRef(href, context);
            } catch (URISyntaxException e) {
                throw new ConversionException(e);
            }
        } else {
            adoc.append("link:");
        }

        adoc.append(href).append("[").append(text).append(window).append("]");

    }

    private String convertXRef(String href, Context context) throws URISyntaxException {
        URI uri = new URI(href);
        if(uri.getFragment() != null && uri.getFragment().startsWith("dsconfig")) {
            href = "dsconfig-subcommands-ref.adoc#" + uri.getFragment();
        }
        return href;
    }
}
