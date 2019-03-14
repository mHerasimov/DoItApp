package com.mikeherasimov.doitapp.io.api

import com.google.gson.annotations.SerializedName
import com.mikeherasimov.doitapp.io.db.Task

data class TasksPage(
    @field:SerializedName("tasks") val tasks: List<Task>,
    @field:SerializedName("meta") val meta: Meta
) {

    data class Meta(
        @field:SerializedName("current") val current: Int,
        @field:SerializedName("limit") val limit: Int,
        @field:SerializedName("count") val count: Int
    )

}