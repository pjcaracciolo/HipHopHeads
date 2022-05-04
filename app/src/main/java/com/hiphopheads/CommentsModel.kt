package com.hiphopheads

import com.google.gson.annotations.SerializedName

data class CommentsModel(
    @SerializedName("kind" ) var kind: String? = null,
    @SerializedName("data" ) var data: CommentsRequestData? = CommentsRequestData()
)

data class CommentsRequestData(
    @SerializedName("after" ) var after: String? = null,
    @SerializedName("children" ) var children: ArrayList<CommentChildren> = arrayListOf()
)

data class CommentChildren(
    @SerializedName("kind" ) var kind: String? = null,
    @SerializedName("data" ) var data: CommentData = CommentData()
)

data class CommentData(
    @SerializedName("body" ) var body: String? = null
)