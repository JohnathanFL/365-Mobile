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
