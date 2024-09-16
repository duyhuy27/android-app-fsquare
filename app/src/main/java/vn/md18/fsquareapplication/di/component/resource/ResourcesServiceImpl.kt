package vn.md18.fsquareapplication.di.component.resource

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ResourcesServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    ResourcesService {
    override fun getString(key: String): String {
        val identifier = context.resources.getIdentifier(key, "string", context.packageName)
        return if (identifier == 0) key else context.resources.getString(identifier)
    }

    override fun getString(key: String, vararg args: Any): String {
        val identifier = context.resources.getIdentifier(key, "string", context.packageName)
        return if (identifier == 0) key else context.resources.getString(identifier, *args)
    }

    override fun getString(key: Int, vararg args: Any): String {
        return context.resources.getString(key, *args)
    }

    override fun getColor(colorId: Int): Int {
        return ResourcesCompat.getColor(context.resources, colorId, context.resources.newTheme())
    }
}