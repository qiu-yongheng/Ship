package com.kc.shiptransport.util.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @author 邱永恒
 * @time 2017/11/21  14:34
 * @desc 异常统一处理
 */

public class ExceptionHandle {
    public static String handleException(Throwable e) {
        e.printStackTrace();
        String errorInfo;

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case ApiCode.Http.UNAUTHORIZED:
                case ApiCode.Http.FORBIDDEN:
                case ApiCode.Http.NOT_FOUND:
                case ApiCode.Http.REQUEST_TIMEOUT:
                case ApiCode.Http.GATEWAY_TIMEOUT:
                case ApiCode.Http.INTERNAL_SERVER_ERROR:
                case ApiCode.Http.BAD_GATEWAY:
                case ApiCode.Http.SERVICE_UNAVAILABLE:
                default:
                    errorInfo = "网络连接不稳定, 请稍后重试";
                    break;
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            errorInfo = "数据解析异常";
        } else if (e instanceof ConnectException) {
            errorInfo = "网络异常";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            errorInfo = "SSL证书异常";
        } else if (e instanceof SocketTimeoutException) {
            errorInfo = "网络连接超时, 请稍后重试";
        } else {
            errorInfo = e.getMessage();
        }

        return errorInfo;
    }
}
