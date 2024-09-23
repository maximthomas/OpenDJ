////
The contents of this file are subject to the terms of the Common Development and
Distribution License (the License). You may not use this file except in compliance with the
License.
You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
specific language governing permission and limitations under the License.

When distributing Covered Software, include this CDDL Header Notice in each file and include
the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
Header, with the fields enclosed by brackets [] replaced by your own identifying
information: "Portions Copyright [year] [name of copyright owner]".

Copyright 2024 3A Systems LLC
////

[#${name}-options]

====== ${title}

${intro}


<refsect1 xml:id="">
    <title></title>

    <para>

    </para>

    <#list groups as group>
        <variablelist>
            <#if group.description??>
                <para>
                    ${group.description}
                </para>
            </#if>

            <#list group.options as option>
                <varlistentry>
                    <term><option>${option.synopsis?xml}</option></term>
                    <listitem>
                        <para>
                            ${option.description?ensure_ends_with(".")}
                        </para>

                        <#if option.default??>
                            <para>
                                ${option.default}
                            </para>
                        </#if>

                        <#if option.info??>${option.info}</#if>
                    </listitem>
                </varlistentry>
            </#list>
        </variablelist>
    </#list>
</refsect1>