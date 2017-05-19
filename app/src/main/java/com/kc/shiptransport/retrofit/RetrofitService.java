/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kc.shiptransport.retrofit;

import com.kc.shiptransport.data.bean.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author qiuyongheng
 * @time 2017/5/8  13:40
 * @desc
 */

public interface RetrofitService {

    /**
     * 登录
     * @return
     */
    @POST("AppService/cchk3WebService.asmx/")
    Observable<LoginResult> login (@Body String str);

    /**
     * 获取船数据
     * @param userinfo 分包商账号
     * @return
     */
    @FormUrlEncoded
    @POST("getShip")
    Observable<LoginResult> getShip (@Field("userInfo") String userinfo);

    /**
     * 获取分包商数据
     * @param SubcontractorAccount 分包商账号名
     * @return
     */
    @FormUrlEncoded
    @POST("getSubcontractor")
    Observable<LoginResult> getSubcontractor (@Field("SubcontractorAccount") String SubcontractorAccount);

    /**
     * 上传数据
     * @param SubmitInfo json数据
     * @return
     */
    @FormUrlEncoded
    @POST("getSubcontractorSubmitShipPlan")
    Observable<LoginResult> getSubcontractorSubmitShipPlan (@Field("SubmitInfo") String SubmitInfo);
}
