package kevinbaguisa.tracker.Controller;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/*
jb/strol-tracker/public/track
 */

public interface API {

    @FormUrlEncoded
    @POST("jb/strol-tracker/public/track")
    Call<ResponseBody> updateCoordinates(
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("bus_id") String bus_id
            );
    /*
    http://dev7.htechcorp.net/jb/strol-tracker/public/track?bus_id=test321&lat=12.3&lng=32.1
     */

//    @FormUrlEncoded
//    @POST("locations/")
//    Call<ResponseBody> updateCoordinates(
//            @Field("bus_id") String bus_id,
//            @Field("lat") double lat,
//            @Field("lng") double lng
//    );
}
