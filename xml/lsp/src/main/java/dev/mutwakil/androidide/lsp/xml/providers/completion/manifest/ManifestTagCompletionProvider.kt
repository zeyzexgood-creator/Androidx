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

package dev.mutwakil.androidide.lsp.xml.providers.completion.manifest

import com.android.aaptcompiler.AaptResourceType.STYLEABLE
import com.android.aaptcompiler.ResourcePathData
import dev.mutwakil.androidide.lookup.Lookup
import dev.mutwakil.androidide.lsp.api.ICompletionProvider
import dev.mutwakil.androidide.lsp.models.CompletionItem
import dev.mutwakil.androidide.lsp.models.CompletionParams
import dev.mutwakil.androidide.lsp.models.CompletionResult
import dev.mutwakil.androidide.lsp.models.CompletionResult.Companion.EMPTY
import dev.mutwakil.androidide.lsp.models.MatchLevel.NO_MATCH
import dev.mutwakil.androidide.lsp.xml.providers.completion.IXmlCompletionProvider
import dev.mutwakil.androidide.lsp.xml.providers.completion.MANIFEST_TAG_PREFIX
import dev.mutwakil.androidide.lsp.xml.providers.completion.canCompleteManifest
import dev.mutwakil.androidide.lsp.xml.providers.completion.transformToTagName
import dev.mutwakil.androidide.lsp.xml.utils.XmlUtils.NodeType
import dev.mutwakil.androidide.lsp.xml.utils.XmlUtils.NodeType.TAG
import dev.mutwakil.androidide.xml.resources.ResourceTableRegistry
import org.eclipse.lemminx.dom.DOMDocument

/**
 * Provides tag completion in AndroidManifest.
 *
 * @author Akash Yadav
 */
class ManifestTagCompletionProvider(provider: ICompletionProvider) :
  IXmlCompletionProvider(provider) {

  override fun canProvideCompletions(pathData: ResourcePathData, type: NodeType): Boolean {
    return super.canProvideCompletions(pathData, type) &&
      canCompleteManifest(pathData, type) &&
      type == TAG
  }

  override fun doComplete(
    params: CompletionParams,
    pathData: ResourcePathData,
    document: DOMDocument,
    type: NodeType,
    prefix: String
  ): CompletionResult {
    val newPrefix =
      if (prefix.startsWith("<")) {
        prefix.substring(1)
      } else {
        prefix
      }

    val styleables =
      Lookup.getDefault().lookup(ResourceTableRegistry.COMPLETION_MANIFEST_ATTR_RES)
        ?.findPackage(ResourceTableRegistry.PCK_ANDROID)
        ?.findGroup(STYLEABLE)
        ?: run {
          log.warn("Cannot find manifest styleable entries")
          return EMPTY
        }

    val result = mutableListOf<CompletionItem>()

    styleables
      .findEntries { it.startsWith(MANIFEST_TAG_PREFIX) }
      .map { transformToTagName(it.name, MANIFEST_TAG_PREFIX) }
      .forEach {
        val match = matchLevel(it, newPrefix)
        if (match == NO_MATCH) {
          return@forEach
        }

        result.add(createTagCompletionItem(it, it, match))
      }

    return CompletionResult(result)
  }
}
