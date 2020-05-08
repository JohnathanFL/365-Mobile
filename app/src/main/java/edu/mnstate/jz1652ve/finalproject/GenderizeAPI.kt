/**
 * A "Sphere of Influence" application that stores contacts and tells you how much pull ya got.
 * Author: Johnathan Lee
 * Date: 05/08/2020
 * MSUM CSIS-365 Mobile, Spring 2020
 *
 *
 * This project is under the public domain.
 */
package edu.mnstate.jz1652ve.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class GenderizeResult(
    val name: String,
    val gender: String,
    val probability: Double,
    val count: UInt
) {
    override fun toString(): String {
        return "GenderizeResult(name='$name', gender='$gender', probability=$probability, count=$count)"
    }
}

interface GenderizeAPI {
    @GET("/")
    fun checkName(@Query("name") n: String): Call<GenderizeResult>
}