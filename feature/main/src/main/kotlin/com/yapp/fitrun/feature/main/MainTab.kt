package com.yapp.fitrun.feature.main

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.feature.crew.CrewRoute
import com.yapp.fitrun.feature.home.HomeRoute
import com.yapp.fitrun.feature.mypage.MyPageRoute
import com.yapp.fitrun.feature.record.RecordRoute
import kotlin.reflect.KClass

enum class MainTab(
    val selectedColor: Color,
    val unselectedColor: Color,
    val iconResId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedColor = TextPrimary,
        unselectedColor = TextPrimary.copy(0.3f),
        iconResId = R.drawable.ic_home,
        titleTextId = com.yapp.fitrun.core.designsystem.R.string.ic_home,
        route = HomeRoute::class,
    ),
    RECORD(
        selectedColor = TextPrimary,
        unselectedColor = TextPrimary.copy(0.3f),
        iconResId = R.drawable.ic_record,
        titleTextId = com.yapp.fitrun.core.designsystem.R.string.ic_record,
        route = RecordRoute::class,
    ),
    CREW(
        selectedColor = TextPrimary,
        unselectedColor = TextPrimary.copy(0.3f),
        iconResId = R.drawable.ic_crew,
        titleTextId = com.yapp.fitrun.core.designsystem.R.string.ic_crew,
        route = CrewRoute::class,
    ),
    MY_PAGE(
        selectedColor = TextPrimary,
        unselectedColor = TextPrimary.copy(0.3f),
        iconResId = R.drawable.ic_my_page,
        titleTextId = com.yapp.fitrun.core.designsystem.R.string.ic_my_page,
        route = MyPageRoute::class,
    )
}