package com.example.bnn.kediri.network;

public class UtilsApi {
    public static final String BASE_URL = " https://bnn.elx.web.id/api/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }
}
