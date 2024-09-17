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

==

[#${name}-1]
==== ${name} — ${shortDesc}

==== Synopsis
`${name}` <#if args??>${args}</#if>

[#${name}-description]
==== ${descTitle}

${description?ensure_ends_with(".")}

<#if info??>${info}</#if>





<refentry xml:id="${name}-1"
          xmlns="http://docbook.org/ns/docbook" version="5.0" xml:lang="${locale}"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://docbook.org/ns/docbook
                              http://docbook.org/xml/5.0/xsd/docbook.xsd"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          xmlns:xinclude="http://www.w3.org/2001/XInclude">

 <info>
  <copyright>
   <year>${year}</year>
   <holder>2011-2017 ForgeRock AS. 2017-${year} Open Identity Platform Community</holder>
  </copyright>
 </info>

 <refmeta>
  <refentrytitle>${name}</refentrytitle><manvolnum>1</manvolnum>
  <refmiscinfo class="software">OpenDJ</refmiscinfo>
  <refmiscinfo class="version">${r"${project.version}"}</refmiscinfo>
 </refmeta>

 <refnamediv>
  <refname>${name}</refname>
  <refpurpose>${shortDesc}</refpurpose>
 </refnamediv>

 <refsynopsisdiv>
  <cmdsynopsis>
   <command>${name}</command>

  </cmdsynopsis>
 </refsynopsisdiv>

 <refsect1 xml:id="${name}-description">
   <title>${descTitle}</title>

   <para>

   </para>

   <#if info??>${info}</#if>
 </refsect1>

 <#if optionSection??>
   ${optionSection}
 </#if>

 <#if subcommands??>
   ${subcommands}
 </#if>

 <#if trailingSectionString??>
   ${trailingSectionString}
 </#if>
</refentry>
