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

package dev.mutwakil.androidide.templates.impl.base

import dev.mutwakil.androidide.templates.ModuleTemplateData
import dev.mutwakil.androidide.templates.ModuleTemplateRecipeResult
import dev.mutwakil.androidide.templates.ProjectTemplateData
import dev.mutwakil.androidide.templates.ProjectTemplateRecipeResult
import dev.mutwakil.androidide.templates.base.ModuleTemplateBuilder
import dev.mutwakil.androidide.templates.base.ProjectTemplateBuilder

data class ProjectTemplateRecipeResultImpl(
  override val data: ProjectTemplateData
) : ProjectTemplateRecipeResult

data class ModuleTemplateRecipeResultImpl(override val data: ModuleTemplateData
) : ModuleTemplateRecipeResult


internal fun ProjectTemplateBuilder.recipeResult(): ProjectTemplateRecipeResult {
  return ProjectTemplateRecipeResultImpl(data)
}

internal fun ModuleTemplateBuilder.recipeResult(): ModuleTemplateRecipeResult {
  return ModuleTemplateRecipeResultImpl(data)
}