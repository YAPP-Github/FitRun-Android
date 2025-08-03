package com.yapp.fitrun.feature.main

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.yapp.fitrun.feature.crew.CrewRoute
import com.yapp.fitrun.feature.home.HomeRoute
import com.yapp.fitrun.feature.mypage.navigation.MyPageRoute
import com.yapp.fitrun.feature.record.RecordRoute
import kotlin.reflect.KClass
import com.yapp.fitrun.core.designsystem.R

enum class MainTab(
    @ColorRes val selectedColorRes: Int,
    @ColorRes val unselectedColorRes: Int,
    val iconResId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedColorRes = R.color.fg_text_primary,
        unselectedColorRes = R.color.fg_text_primary,
        iconResId = R.drawable.ic_home,
        titleTextId = R.string.ic_home,
        route = HomeRoute::class,
    ),
    RECORD(
        selectedColorRes = R.color.fg_text_primary,
        unselectedColorRes = R.color.fg_text_primary,
        iconResId = R.drawable.ic_record,
        titleTextId = R.string.ic_record,
        route = RecordRoute::class,
    ),
    CREW(
        selectedColorRes = R.color.fg_text_primary,
        unselectedColorRes = R.color.fg_text_primary,
        iconResId = R.drawable.ic_crew,
        titleTextId = R.string.ic_crew,
        route = CrewRoute::class,
    ),
    MY_PAGE(
        selectedColorRes = R.color.fg_text_primary,
        unselectedColorRes = R.color.fg_text_primary,
        iconResId = R.drawable.ic_my_page,
        titleTextId = R.string.ic_my_page,
        route = MyPageRoute::class,
    ),
}
