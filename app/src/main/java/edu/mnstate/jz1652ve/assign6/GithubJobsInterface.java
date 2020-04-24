/**
 * Author: Johnathan Lee
 * Class: CSIS365 Mobile, MSUM
 * Due: 04/24/20
 * Assign 6 - A simple Github Jobs client that searches jobs and lets you open them in a browser.

 Copyright 2020 Johnathan Lee

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package edu.mnstate.jz1652ve.assign6;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubJobsInterface {

    class Position {
        public String id;
        public String type;
        public String url;
        @SerializedName("created_at")
        public String createdAt;
        public String company;
        @SerializedName("company_url")
        public String companyUrl;
        public String location;
        public String title;
        public String description;
        @SerializedName("how_to_apply")
        public String howToApply;
        @SerializedName("company_logo")
        public String companyLogo;
    }

    @GET("positions.json")
    Call<List<Position>> getPositions(@Query("description") String jobDesc);

}
