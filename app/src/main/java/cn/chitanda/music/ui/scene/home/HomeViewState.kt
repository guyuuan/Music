package cn.chitanda.music.ui.scene.home

import cn.chitanda.music.http.bean.HomeBanner
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIcon
import cn.chitanda.music.http.bean.MLogExtInfo
import cn.chitanda.music.ui.scene.PageState

/**
 * @author: Chen
 * @createTime: 2022/1/5 15:17
 * @description:
 **/
data class HomeViewState(
    val banner: List<HomeBanner.Banner> = emptyList(),
    val icons: List<HomeRoundIcon> = emptyList(),
    val playlist: Pair<HomeData.Data.Block.UiElement?, List<HomeData.Data.Block.Creative.Resource>> = Pair(
        HomeData.Data.Block.UiElement(),
        emptyList()
    ),
    val mLog: Pair<HomeData.Data.Block.UiElement?, List<MLogExtInfo>> = Pair(
        HomeData.Data.Block.UiElement(), emptyList()
    ),
    val songList: Pair<HomeData.Data.Block.UiElement?, List<HomeData.Data.Block.Creative>> = Pair(
        HomeData.Data.Block.UiElement(), emptyList()
    ),
    val state: PageState = PageState.Empty
)