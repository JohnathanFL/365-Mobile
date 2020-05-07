package edu.mnstate.jz1652ve.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class GenderizeResult(val name: String, val gender: String, val probability: Double, val count: UInt) {
    override fun toString(): String {
        return "GenderizeResult(name='$name', gender='$gender', probability=$probability, count=$count)"
    }
}

interface GenderizeAPI {
    @GET("/")
    fun checkName(@Query("name") n: String): Call<GenderizeResult>
}