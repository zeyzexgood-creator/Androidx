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

import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableList
import dev.mutwakil.androidide.templates.ITemplateProvider
import dev.mutwakil.androidide.templates.Template
import dev.mutwakil.androidide.templates.impl.basicActivity.basicActivityProject
import dev.mutwakil.androidide.templates.impl.bottomNavActivity.bottomNavActivityProject
import dev.mutwakil.androidide.templates.impl.composeActivity.composeActivityProject
import dev.mutwakil.androidide.templates.impl.emptyActivity.emptyActivityProject
import dev.mutwakil.androidide.templates.impl.navDrawerActivity.navDrawerActivityProject
import dev.mutwakil.androidide.templates.impl.noActivity.noActivityProjectTemplate
import dev.mutwakil.androidide.templates.impl.noAndroidXActivity.noAndroidXActivityProject
import dev.mutwakil.androidide.templates.impl.tabbedActivity.tabbedActivityProject

/**
 * Default implementation of the [ITemplateProvider].
 *
 * @author Akash Yadav
 */
@Suppress("unused")
@AutoService(ITemplateProvider::class)
class TemplateProviderImpl : ITemplateProvider {

  private val templates = mutableMapOf<String, Template<*>>()

  init {
    initializeTemplates()
  }

  private fun templates() =
    //@formatter:off
    arrayOf(
      noActivityProjectTemplate(),
      emptyActivityProject(),
      basicActivityProject(),
      navDrawerActivityProject(),
      bottomNavActivityProject(),
      tabbedActivityProject(),
      noAndroidXActivityProject(),
      composeActivityProject()
    )

  private fun initializeTemplates() {
    templates().forEach { template ->
      templates[template.templateId] = template
    }
  }
  //@formatter:on

  override fun getTemplates(): List<Template<*>> {
    return ImmutableList.copyOf(templates.values)
  }

  override fun getTemplate(templateId: String): Template<*>? {
    return templates[templateId]
  }

  override fun reload() {
    release()
    initializeTemplates()
  }

  override fun release() {
    templates.forEach { it.value.release() }
    templates.clear()
  }
}