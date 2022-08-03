package com.hiphopheads.models

import com.google.gson.annotations.SerializedName
import com.hiphopheads.Link


data class RedditModel (

    @SerializedName("kind" ) var kind : String? = null,
    @SerializedName("data" ) var data : RequestData?   = RequestData()

)

data class Data (

    @SerializedName("subreddit"                     ) var subreddit                  : String?                        = null,
    @SerializedName("selftext"                      ) var selftext                   : String?                        = null,
    @SerializedName("author_fullname"               ) var authorFullname             : String?                        = null,
    @SerializedName("title"                         ) var title                      : String?                        = null,
    @SerializedName("name"                          ) var name                       : String?                        = null,
    @SerializedName("score"                         ) var score                      : Int?                           = null,
    @SerializedName("thumbnail"                     ) var thumbnail                  : String?                        = null,
    @SerializedName("subreddit_id"                  ) var subredditId                : String?                        = null,
    @SerializedName("id"                            ) var id                         : String?                        = null,
    @SerializedName("author"                        ) var author                     : String?                        = null,
    @SerializedName("permalink"                     ) var permalink                  : String?                        = null,
    @SerializedName("url"                           ) var url                        : String?                        = null,
    @SerializedName("created_utc"                   ) var createdUtc                 : Int?                           = null,
    @SerializedName("secure_media"                  ) var secureMedia                : SecureMedia?                   = SecureMedia(),
                                                           var links : Links? = Links()

    )

data class SecureMedia (
    @SerializedName("type"                          ) var type                       : String?                        = null,
    @SerializedName("oembed"                        ) var oembed                     : OEmbed?                        = OEmbed()
)

data class OEmbed (

    @SerializedName("thumbnail_url"                 ) var thumbnailUrl               : String?                        = null

)

data class Posts (

    @SerializedName("kind" ) var kind : String? = null,
    @SerializedName("data" ) var data : Data?   = Data()

)

data class RequestData (

    @SerializedName("after"      ) var after     : String?             = null,
    @SerializedName("dist"       ) var dist      : Int?                = null,
    @SerializedName("modhash"    ) var modhash   : String?             = null,
    @SerializedName("geo_filter" ) var geoFilter : String?             = null,
    @SerializedName("children"   ) var children  : ArrayList<Posts> = arrayListOf(),
    @SerializedName("before"     ) var before    : String?             = null

)

data class Links (
    var spotify : Link? = null,
    var apple : Link? = null,
    var amazon : Link? = null,
    var tidal : Link? = null,
    var deezer : Link? = null,
    var youtubeMusic : Link? = null,
    var youtube : Link? = null,
    var soundcloud: Link? = null,
    var bandcamp: Link? = null
)
