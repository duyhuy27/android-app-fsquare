package vn.md18.fsquareapplication.di.module

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import vn.md18.fsquareapplication.features.main.adapter.MainPagerAdapter
import vn.md18.fsquareapplication.features.main.adapter.ProductBannerAdapter


@InstallIn(ActivityComponent::class)
@Module
class ViewModule {

    @Provides
    fun provideMainPagerAdapter(activity: FragmentActivity): MainPagerAdapter {
        return MainPagerAdapter(activity)
    }

    @Provides
    fun provideBannerProductAdapter() : ProductBannerAdapter {
        return ProductBannerAdapter()
    }

    @Provides
    fun providerLinearLayoutManager(fragmentActivity: FragmentActivity): LinearLayoutManager {
        return LinearLayoutManager(fragmentActivity)
    }


}