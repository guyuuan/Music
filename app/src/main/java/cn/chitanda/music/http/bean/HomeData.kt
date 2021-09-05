package  cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeData(
    @Json(name = "code")
    override val code: Int = 0,
    @Json(name = "data")
    val `data`: Data? = Data(),
    @Json(name = "message")
    override val message: String? = "",
    @Json(name = "msg")
    override val msg: String? = "",
) : BaseJson() {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "blockUUIDs")
        val blockUUIDs: Any? = Any(),
        @Json(name = "blocks")
        val blocks: List<Block?>? = listOf(),
        @Json(name = "cursor")
        val cursor: String? = "",
        @Json(name = "guideToast")
        val guideToast: GuideToast? = GuideToast(),
        @Json(name = "hasMore")
        val hasMore: Boolean? = false,
        @Json(name = "pageConfig")
        val pageConfig: PageConfig? = PageConfig()
    ) {
        @JsonClass(generateAdapter = true)
        data class Block(
            @Json(name = "action")
            val action: String? = "",
            @Json(name = "actionType")
            val actionType: String? = "",
            @Json(name = "blockCode")
            val blockCode: String? = "",
            @Json(name = "canClose")
            val canClose: Boolean? = false,
            @Json(name = "creatives")
            val creatives: List<Creative?>? = listOf(),
            @Json(name = "extInfo")
            val extInfo: Any? = Any(),
            @Json(name = "showType")
            val showType: String? = "",
            @Json(name = "uiElement")
            val uiElement: UiElement? = UiElement()
        ) {
            @JsonClass(generateAdapter = true)
            data class Creative(
                @Json(name = "action")
                val action: String? = "",
                @Json(name = "actionType")
                val actionType: String? = "",
                @Json(name = "alg")
                val alg: String? = "",
                @Json(name = "creativeId")
                val creativeId: String? = "",
                @Json(name = "creativeType")
                val creativeType: String? = "",
                @Json(name = "logInfo")
                val logInfo: String? = "",
                @Json(name = "position")
                val position: Int? = 0,
                @Json(name = "resources")
                val resources: List<Resource?>? = listOf(),
                @Json(name = "uiElement")
                val uiElement: UiElement? = UiElement()
            ) {
                @JsonClass(generateAdapter = true)
                data class Resource(
                    @Json(name = "action")
                    val action: String? = "",
                    @Json(name = "actionType")
                    val actionType: String? = "",
                    @Json(name = "alg")
                    val alg: String? = "",
                    @Json(name = "logInfo")
                    val logInfo: String? = "",
                    @Json(name = "resourceExtInfo")
                    val resourceExtInfo: ResourceExtInfo? = ResourceExtInfo(),
                    @Json(name = "resourceId")
                    val resourceId: String? = "",
                    @Json(name = "resourceType")
                    val resourceType: String? = "",
                    @Json(name = "resourceUrl")
                    val resourceUrl: Any? = Any(),
                    @Json(name = "uiElement")
                    val uiElement: UiElement? = UiElement(),
                    @Json(name = "valid")
                    val valid: Boolean? = false
                ) {
                    @JsonClass(generateAdapter = true)
                    data class ResourceExtInfo(
                        @Json(name = "artists")
                        val artists: List<Artist?>? = listOf(),
                        @Json(name = "commentSimpleData")
                        val commentSimpleData: CommentSimpleData? = CommentSimpleData(),
                        @Json(name = "highQuality")
                        val highQuality: Boolean? = false,
                        @Json(name = "playCount")
                        val playCount: Long? = 0,
                        @Json(name = "songData")
                        val songData: SongData? = SongData(),
                        @Json(name = "songPrivilege")
                        val songPrivilege: SongPrivilege? = SongPrivilege(),
                        @Json(name = "specialCover")
                        val specialCover: Int? = 0,
                        @Json(name = "specialType")
                        val specialType: Int? = 0,
                        @Json(name = "users")
                        val users: List<User?>? = listOf()
                    ) {
                        @JsonClass(generateAdapter = true)
                        data class Artist(
                            @Json(name = "albumSize")
                            val albumSize: Int? = 0,
                            @Json(name = "alias")
                            val alias: List<Any?>? = listOf(),
                            @Json(name = "briefDesc")
                            val briefDesc: String? = "",
                            @Json(name = "id")
                            val id: Int? = 0,
                            @Json(name = "img1v1Id")
                            val img1v1Id: Int? = 0,
                            @Json(name = "img1v1Url")
                            val img1v1Url: String? = "",
                            @Json(name = "musicSize")
                            val musicSize: Int? = 0,
                            @Json(name = "name")
                            val name: String? = "",
                            @Json(name = "picId")
                            val picId: Int? = 0,
                            @Json(name = "picUrl")
                            val picUrl: String? = "",
                            @Json(name = "topicPerson")
                            val topicPerson: Int? = 0,
                            @Json(name = "trans")
                            val trans: String? = ""
                        )

                        @JsonClass(generateAdapter = true)
                        data class CommentSimpleData(
                            @Json(name = "commentId")
                            val commentId: Long? = 0,
                            @Json(name = "content")
                            val content: String? = "",
                            @Json(name = "threadId")
                            val threadId: String? = "",
                            @Json(name = "userId")
                            val userId: Int? = 0,
                            @Json(name = "userName")
                            val userName: String? = ""
                        )

                        @JsonClass(generateAdapter = true)
                        data class SongData(
                            @Json(name = "album")
                            val album: Album? = Album(),
                            @Json(name = "alias")
                            val alias: List<Any?>? = listOf(),
                            @Json(name = "artists")
                            val artists: List<Artist?>? = listOf(),
                            @Json(name = "audition")
                            val audition: Any? = Any(),
                            @Json(name = "bMusic")
                            val bMusic: BMusic? = BMusic(),
                            @Json(name = "commentThreadId")
                            val commentThreadId: String? = "",
                            @Json(name = "copyFrom")
                            val copyFrom: String? = "",
                            @Json(name = "copyright")
                            val copyright: Int? = 0,
                            @Json(name = "copyrightId")
                            val copyrightId: Int? = 0,
                            @Json(name = "crbt")
                            val crbt: Any? = Any(),
                            @Json(name = "dayPlays")
                            val dayPlays: Int? = 0,
                            @Json(name = "disc")
                            val disc: String? = "",
                            @Json(name = "duration")
                            val duration: Int? = 0,
                            @Json(name = "fee")
                            val fee: Int? = 0,
                            @Json(name = "ftype")
                            val ftype: Int? = 0,
                            @Json(name = "hMusic")
                            val hMusic: Any? = Any(),
                            @Json(name = "hearTime")
                            val hearTime: Int? = 0,
                            @Json(name = "id")
                            val id: Int? = 0,
                            @Json(name = "lMusic")
                            val lMusic: LMusic? = LMusic(),
                            @Json(name = "mMusic")
                            val mMusic: MMusic? = MMusic(),
                            @Json(name = "mark")
                            val mark: Int? = 0,
                            @Json(name = "mp3Url")
                            val mp3Url: Any? = Any(),
                            @Json(name = "mvid")
                            val mvid: Int? = 0,
                            @Json(name = "name")
                            val name: String? = "",
                            @Json(name = "no")
                            val no: Int? = 0,
                            @Json(name = "noCopyrightRcmd")
                            val noCopyrightRcmd: Any? = Any(),
                            @Json(name = "originCoverType")
                            val originCoverType: Int? = 0,
                            @Json(name = "originSongSimpleData")
                            val originSongSimpleData: Any? = Any(),
                            @Json(name = "playedNum")
                            val playedNum: Int? = 0,
                            @Json(name = "popularity")
                            val popularity: Int? = 0,
                            @Json(name = "position")
                            val position: Int? = 0,
                            @Json(name = "ringtone")
                            val ringtone: Any? = Any(),
                            @Json(name = "rtUrl")
                            val rtUrl: Any? = Any(),
                            @Json(name = "rtUrls")
                            val rtUrls: List<Any?>? = listOf(),
                            @Json(name = "rtype")
                            val rtype: Int? = 0,
                            @Json(name = "rurl")
                            val rurl: Any? = Any(),
                            @Json(name = "score")
                            val score: Int? = 0,
                            @Json(name = "sign")
                            val sign: Any? = Any(),
                            @Json(name = "single")
                            val single: Int? = 0,
                            @Json(name = "starred")
                            val starred: Boolean? = false,
                            @Json(name = "starredNum")
                            val starredNum: Int? = 0,
                            @Json(name = "status")
                            val status: Int? = 0,
                            @Json(name = "transName")
                            val transName: Any? = Any(),
                            @Json(name = "videoInfo")
                            val videoInfo: Any? = Any()
                        ) {
                            @JsonClass(generateAdapter = true)
                            data class Album(
                                @Json(name = "alias")
                                val alias: List<Any?>? = listOf(),
                                @Json(name = "artist")
                                val artist: Artist? = Artist(),
                                @Json(name = "artists")
                                val artists: List<Artist?>? = listOf(),
                                @Json(name = "blurPicUrl")
                                val blurPicUrl: String? = "",
                                @Json(name = "briefDesc")
                                val briefDesc: String? = "",
                                @Json(name = "commentThreadId")
                                val commentThreadId: String? = "",
                                @Json(name = "company")
                                val company: String? = "",
                                @Json(name = "companyId")
                                val companyId: Int? = 0,
                                @Json(name = "copyrightId")
                                val copyrightId: Int? = 0,
                                @Json(name = "description")
                                val description: String? = "",
                                @Json(name = "id")
                                val id: Int? = 0,
                                @Json(name = "mark")
                                val mark: Int? = 0,
                                @Json(name = "name")
                                val name: String? = "",
                                @Json(name = "onSale")
                                val onSale: Boolean? = false,
                                @Json(name = "pic")
                                val pic: Long? = 0,
                                @Json(name = "picId")
                                val picId: Long? = 0,
                                @Json(name = "picId_str")
                                val picIdStr: String? = "",
                                @Json(name = "picUrl")
                                val picUrl: String? = "",
                                @Json(name = "publishTime")
                                val publishTime: Long? = 0,
                                @Json(name = "size")
                                val size: Int? = 0,
                                @Json(name = "songs")
                                val songs: List<Any?>? = listOf(),
                                @Json(name = "status")
                                val status: Int? = 0,
                                @Json(name = "subType")
                                val subType: String? = "",
                                @Json(name = "tags")
                                val tags: String? = "",
                                @Json(name = "transName")
                                val transName: Any? = Any(),
                                @Json(name = "type")
                                val type: String? = ""
                            ) {
                                @JsonClass(generateAdapter = true)
                                data class Artist(
                                    @Json(name = "albumSize")
                                    val albumSize: Int? = 0,
                                    @Json(name = "alias")
                                    val alias: List<Any?>? = listOf(),
                                    @Json(name = "briefDesc")
                                    val briefDesc: String? = "",
                                    @Json(name = "id")
                                    val id: Int? = 0,
                                    @Json(name = "img1v1Id")
                                    val img1v1Id: Int? = 0,
                                    @Json(name = "img1v1Url")
                                    val img1v1Url: String? = "",
                                    @Json(name = "musicSize")
                                    val musicSize: Int? = 0,
                                    @Json(name = "name")
                                    val name: String? = "",
                                    @Json(name = "picId")
                                    val picId: Int? = 0,
                                    @Json(name = "picUrl")
                                    val picUrl: String? = "",
                                    @Json(name = "topicPerson")
                                    val topicPerson: Int? = 0,
                                    @Json(name = "trans")
                                    val trans: String? = ""
                                )

                            }

                            @JsonClass(generateAdapter = true)
                            data class Artist(
                                @Json(name = "albumSize")
                                val albumSize: Int? = 0,
                                @Json(name = "alias")
                                val alias: List<Any?>? = listOf(),
                                @Json(name = "briefDesc")
                                val briefDesc: String? = "",
                                @Json(name = "id")
                                val id: Int? = 0,
                                @Json(name = "img1v1Id")
                                val img1v1Id: Int? = 0,
                                @Json(name = "img1v1Url")
                                val img1v1Url: String? = "",
                                @Json(name = "musicSize")
                                val musicSize: Int? = 0,
                                @Json(name = "name")
                                val name: String? = "",
                                @Json(name = "picId")
                                val picId: Int? = 0,
                                @Json(name = "picUrl")
                                val picUrl: String? = "",
                                @Json(name = "topicPerson")
                                val topicPerson: Int? = 0,
                                @Json(name = "trans")
                                val trans: String? = ""
                            )

                            @JsonClass(generateAdapter = true)
                            data class BMusic(
                                @Json(name = "bitrate")
                                val bitrate: Int? = 0,
                                @Json(name = "dfsId")
                                val dfsId: Int? = 0,
                                @Json(name = "extension")
                                val extension: String? = "",
                                @Json(name = "id")
                                val id: Long? = 0,
                                @Json(name = "name")
                                val name: Any? = Any(),
                                @Json(name = "playTime")
                                val playTime: Int? = 0,
                                @Json(name = "size")
                                val size: Int? = 0,
                                @Json(name = "sr")
                                val sr: Int? = 0,
                                @Json(name = "volumeDelta")
                                val volumeDelta: Any? = Any()
                            )

                            @JsonClass(generateAdapter = true)
                            data class LMusic(
                                @Json(name = "bitrate")
                                val bitrate: Int? = 0,
                                @Json(name = "dfsId")
                                val dfsId: Int? = 0,
                                @Json(name = "extension")
                                val extension: String? = "",
                                @Json(name = "id")
                                val id: Long? = 0,
                                @Json(name = "name")
                                val name: Any? = Any(),
                                @Json(name = "playTime")
                                val playTime: Int? = 0,
                                @Json(name = "size")
                                val size: Int? = 0,
                                @Json(name = "sr")
                                val sr: Int? = 0,
                                @Json(name = "volumeDelta")
                                val volumeDelta: Any? = Any()
                            )

                            @JsonClass(generateAdapter = true)
                            data class MMusic(
                                @Json(name = "bitrate")
                                val bitrate: Int? = 0,
                                @Json(name = "dfsId")
                                val dfsId: Int? = 0,
                                @Json(name = "extension")
                                val extension: String? = "",
                                @Json(name = "id")
                                val id: Long? = 0,
                                @Json(name = "name")
                                val name: Any? = Any(),
                                @Json(name = "playTime")
                                val playTime: Int? = 0,
                                @Json(name = "size")
                                val size: Int? = 0,
                                @Json(name = "sr")
                                val sr: Int? = 0,
                                @Json(name = "volumeDelta")
                                val volumeDelta: Any? = Any()
                            )
                        }

                        @JsonClass(generateAdapter = true)
                        data class SongPrivilege(
                            @Json(name = "chargeInfoList")
                            val chargeInfoList: List<ChargeInfo?>? = listOf(),
                            @Json(name = "cp")
                            val cp: Int? = 0,
                            @Json(name = "cs")
                            val cs: Boolean? = false,
                            @Json(name = "dl")
                            val dl: Int? = 0,
                            @Json(name = "downloadMaxbr")
                            val downloadMaxbr: Int? = 0,
                            @Json(name = "fee")
                            val fee: Int? = 0,
                            @Json(name = "fl")
                            val fl: Int? = 0,
                            @Json(name = "flag")
                            val flag: Int? = 0,
                            @Json(name = "freeTrialPrivilege")
                            val freeTrialPrivilege: FreeTrialPrivilege? = FreeTrialPrivilege(),
                            @Json(name = "id")
                            val id: Int? = 0,
                            @Json(name = "maxbr")
                            val maxbr: Int? = 0,
                            @Json(name = "paidBigBang")
                            val paidBigBang: Boolean? = false,
                            @Json(name = "payed")
                            val payed: Int? = 0,
                            @Json(name = "pc")
                            val pc: Any? = Any(),
                            @Json(name = "pl")
                            val pl: Int? = 0,
                            @Json(name = "playMaxbr")
                            val playMaxbr: Int? = 0,
                            @Json(name = "preSell")
                            val preSell: Boolean? = false,
                            @Json(name = "realPayed")
                            val realPayed: Int? = 0,
                            @Json(name = "rscl")
                            val rscl: Any? = Any(),
                            @Json(name = "sp")
                            val sp: Int? = 0,
                            @Json(name = "st")
                            val st: Int? = 0,
                            @Json(name = "subp")
                            val subp: Int? = 0,
                            @Json(name = "toast")
                            val toast: Boolean? = false
                        ) {
                            @JsonClass(generateAdapter = true)
                            data class ChargeInfo(
                                @Json(name = "chargeMessage")
                                val chargeMessage: Any? = Any(),
                                @Json(name = "chargeType")
                                val chargeType: Int? = 0,
                                @Json(name = "chargeUrl")
                                val chargeUrl: Any? = Any(),
                                @Json(name = "rate")
                                val rate: Int? = 0
                            )

                            @JsonClass(generateAdapter = true)
                            data class FreeTrialPrivilege(
                                @Json(name = "resConsumable")
                                val resConsumable: Boolean? = false,
                                @Json(name = "userConsumable")
                                val userConsumable: Boolean? = false
                            )
                        }

                        @JsonClass(generateAdapter = true)
                        data class User(
                            @Json(name = "accountStatus")
                            val accountStatus: Int? = 0,
                            @Json(name = "authStatus")
                            val authStatus: Int? = 0,
                            @Json(name = "authority")
                            val authority: Int? = 0,
                            @Json(name = "avatarImgId")
                            val avatarImgId: Int? = 0,
                            @Json(name = "avatarImgIdStr")
                            val avatarImgIdStr: String? = "",
                            @Json(name = "avatarUrl")
                            val avatarUrl: String? = "",
                            @Json(name = "backgroundImgId")
                            val backgroundImgId: Int? = 0,
                            @Json(name = "backgroundImgIdStr")
                            val backgroundImgIdStr: String? = "",
                            @Json(name = "backgroundUrl")
                            val backgroundUrl: Any? = Any(),
                            @Json(name = "birthday")
                            val birthday: Int? = 0,
                            @Json(name = "city")
                            val city: Int? = 0,
                            @Json(name = "defaultAvatar")
                            val defaultAvatar: Boolean? = false,
                            @Json(name = "description")
                            val description: Any? = Any(),
                            @Json(name = "detailDescription")
                            val detailDescription: Any? = Any(),
                            @Json(name = "djStatus")
                            val djStatus: Int? = 0,
                            @Json(name = "expertTags")
                            val expertTags: Any? = Any(),
                            @Json(name = "experts")
                            val experts: Any? = Any(),
                            @Json(name = "extProperties")
                            val extProperties: Any? = Any(),
                            @Json(name = "followed")
                            val followed: Boolean? = false,
                            @Json(name = "gender")
                            val gender: Int? = 0,
                            @Json(name = "mutual")
                            val mutual: Boolean? = false,
                            @Json(name = "nickname")
                            val nickname: String? = "",
                            @Json(name = "province")
                            val province: Int? = 0,
                            @Json(name = "remarkName")
                            val remarkName: Any? = Any(),
                            @Json(name = "signature")
                            val signature: Any? = Any(),
                            @Json(name = "userId")
                            val userId: Int? = 0,
                            @Json(name = "userType")
                            val userType: Int? = 0,
                            @Json(name = "vipType")
                            val vipType: Int? = 0,
                            @Json(name = "xInfo")
                            val xInfo: Any? = Any()
                        )
                    }

                    @JsonClass(generateAdapter = true)
                    data class UiElement(
                        @Json(name = "image")
                        val image: Image? = Image(),
                        @Json(name = "labelTexts")
                        val labelTexts: List<String?>? = listOf(),
                        @Json(name = "mainTitle")
                        val mainTitle: MainTitle? = MainTitle(),
                        @Json(name = "rcmdShowType")
                        val rcmdShowType: String? = "",
                        @Json(name = "subTitle")
                        val subTitle: SubTitle? = SubTitle()
                    ) {
                        @JsonClass(generateAdapter = true)
                        data class Image(
                            @Json(name = "imageUrl")
                            val imageUrl: String? = ""
                        )

                        @JsonClass(generateAdapter = true)
                        data class MainTitle(
                            @Json(name = "title")
                            val title: String? = ""
                        )

                        @JsonClass(generateAdapter = true)
                        data class SubTitle(
                            @Json(name = "title")
                            val title: String? = "",
                            @Json(name = "titleType")
                            val titleType: String? = ""
                        )
                    }
                }

                @JsonClass(generateAdapter = true)
                data class UiElement(
                    @Json(name = "image")
                    val image: Image? = Image(),
                    @Json(name = "labelTexts")
                    val labelTexts: List<String?>? = listOf(),
                    @Json(name = "mainTitle")
                    val mainTitle: MainTitle? = MainTitle(),
                    @Json(name = "rcmdShowType")
                    val rcmdShowType: String? = "",
                    @Json(name = "subTitle")
                    val subTitle: SubTitle? = SubTitle()
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Image(
                        @Json(name = "imageUrl")
                        val imageUrl: String? = ""
                    )

                    @JsonClass(generateAdapter = true)
                    data class MainTitle(
                        @Json(name = "title")
                        val title: String? = ""
                    )

                    @JsonClass(generateAdapter = true)
                    class SubTitle
                }
            }

            @JsonClass(generateAdapter = true)
            data class UiElement(
                @Json(name = "button")
                val button: Button? = Button(),
                @Json(name = "rcmdShowType")
                val rcmdShowType: String? = "",
                @Json(name = "subTitle")
                val subTitle: SubTitle? = SubTitle()
            ) {
                @JsonClass(generateAdapter = true)
                data class Button(
                    @Json(name = "action")
                    val action: String? = "",
                    @Json(name = "actionType")
                    val actionType: String? = "",
                    @Json(name = "iconUrl")
                    val iconUrl: Any? = Any(),
                    @Json(name = "text")
                    val text: String? = ""
                )

                @JsonClass(generateAdapter = true)
                data class SubTitle(
                    @Json(name = "title")
                    val title: String? = "",
                    @Json(name = "titleImgUrl")
                    val titleImgUrl: String? = ""
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class GuideToast(
            @Json(name = "hasGuideToast")
            val hasGuideToast: Boolean? = false,
            @Json(name = "toastList")
            val toastList: List<Any?>? = listOf()
        )

        @JsonClass(generateAdapter = true)
        data class PageConfig(
            @Json(name = "abtest")
            val abtest: List<String?>? = listOf(),
            @Json(name = "fullscreen")
            val fullscreen: Boolean? = false,
            @Json(name = "homepageMode")
            val homepageMode: String? = "",
            @Json(name = "nodataToast")
            val nodataToast: String? = "",
            @Json(name = "orderInfo")
            val orderInfo: String? = "",
            @Json(name = "refreshInterval")
            val refreshInterval: Int? = 0,
            @Json(name = "refreshToast")
            val refreshToast: String? = "",
            @Json(name = "showModeEntry")
            val showModeEntry: Boolean? = false,
            @Json(name = "songLabelMarkLimit")
            val songLabelMarkLimit: Int? = 0,
            @Json(name = "songLabelMarkPriority")
            val songLabelMarkPriority: List<String?>? = listOf(),
            @Json(name = "title")
            val title: Any? = Any()
        )
    }
}