/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.mutwakil.androidide.templates.impl

import dev.mutwakil.androidide.templates.BooleanParameter
import dev.mutwakil.androidide.templates.EnumParameter
import dev.mutwakil.androidide.templates.Language
import dev.mutwakil.androidide.templates.ProjectTemplate
import dev.mutwakil.androidide.templates.ProjectVersionData
import dev.mutwakil.androidide.templates.Sdk
import dev.mutwakil.androidide.templates.StringParameter
import dev.mutwakil.androidide.templates.base.AndroidModuleTemplateBuilder
import dev.mutwakil.androidide.templates.base.ProjectTemplateBuilder
import dev.mutwakil.androidide.templates.base.baseProject
import dev.mutwakil.androidide.templates.impl.base.createRecipe
import dev.mutwakil.androidide.templates.minSdkParameter
import dev.mutwakil.androidide.templates.packageNameParameter
import dev.mutwakil.androidide.templates.projectLanguageParameter
import dev.mutwakil.androidide.templates.projectNameParameter
import dev.mutwakil.androidide.templates.useKtsParameter

/**
 * Indents the given string for the given [indentation level][level].
 */
fun String.indentToLevel(level: Int): String {
  val lines = split(Regex("[\r\n]"))
  return StringBuilder().apply {
    for (line in lines) {
      append(line)
      append(" ".repeat(level * 4))
    }
  }.toString()
}

@Suppress("UnusedReceiverParameter")
internal fun AndroidModuleTemplateBuilder.templateAsset(name: String,
                                                        path: String
): String {
  return "templates/${name}/${path}"
}

internal inline fun baseProjectImpl(
  projectName: StringParameter = projectNameParameter(),
  packageName: StringParameter = packageNameParameter(),
  useKts: BooleanParameter = useKtsParameter(),
  minSdk: EnumParameter<Sdk> = minSdkParameter(),
  language: EnumParameter<Language> = projectLanguageParameter(),
  projectVersionData: ProjectVersionData = ProjectVersionData(),
  crossinline block: ProjectTemplateBuilder.() -> Unit
): ProjectTemplate =
  baseProject(projectName = projectName, packageName = packageName,
    useKts = useKts, minSdk = minSdk, language = language,
    projectVersionData = projectVersionData) {
    block()

    // make sure we return a proper result
    if (!isRecipeSet) {
      recipe = createRecipe {}
    }
  }