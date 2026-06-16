package dev.mutwakil.androidide.utils

import dev.mutwakil.androidide.buildinfo.BuildInfo

/**
 * Provides various information about the IDE build.
 *
 * @author Akash Yadav
 */
object BasicBuildInfo {

    /**
     * Basic info, includes internal app name and version name.
     */
    const val BASIC_INFO = "${BuildInfo.INTERNAL_NAME} (${BuildInfo.VERSION_NAME_SIMPLE})"
}