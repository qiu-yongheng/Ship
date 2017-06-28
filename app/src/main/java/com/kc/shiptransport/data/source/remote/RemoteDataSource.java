package com.kc.shiptransport.data.source.remote;

import com.kc.shiptransport.util.BaseUrl;
import com.kc.shiptransport.util.FakeX509TrustManager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author qiuyongheng
 * @time 2017/5/17  10:29
 * @desc ${TODD}
 */
public class RemoteDataSource {
    private final String EndPoint = BaseUrl.EndPoint;
    private final int timeout = 15000;

    /**
     * 登录
     *
     * @return
     */
    public String getLoginInfo(String username, String password) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "getLogin";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/getLogin";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("userName", username);
        rpc.addProperty("password", password);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取分包商信息
     *
     * @param subcontractorAccount 用户名
     * @return
     */
    public String getSubcontractorInfo(String subcontractorAccount) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "getSubcontractor";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/getSubcontractor";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", subcontractorAccount);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取船信息
     *
     * @param subcontractorAccount 用户名
     * @return
     */
    public String getShipInfo(String subcontractorAccount) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "getShip";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/getShip";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", subcontractorAccount);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取任务计划
     *
     * @param SubcontractorAccount 用户名
     * @param StartDay             开始时间 2017-05-18
     * @param EndDay               结束时间 2017-05-18
     * @return
     */
    public String getWeekTaskInfo(String SubcontractorAccount, String StartDay, String EndDay) throws IOException, XmlPullParserException {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "getSubcontractorPlanList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/getSubcontractorPlanList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDay);
        rpc.addProperty("EndDate", EndDay);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        // 调用WebService
        transport.call(soapAction, envelope);

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 提交一周计划
     *
     * @param jsonData json数据
     * @return
     */
    public String commitWeekTask(String jsonData) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "getSubcontractorSubmitShipPlan";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/getSubcontractorSubmitShipPlan";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubmitInfo", jsonData);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 根据ItemID, 返回验收明细
     *
     * @param itemID
     */
    public String getAcceptanceByItemID(int itemID) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorPlanByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorPlanByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ItemID", itemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 提交验沙结果
     * UpdateForReceptionSandTime
     *
     * @return
     */
    public String UpdateForReceptionSandTime(int itemID, String ReceptionSandTime, String Batch) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "UpdateForReceptionSandTime";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/UpdateForReceptionSandTime";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ItemID", itemID);
        rpc.addProperty("ReceptionSandTime", ReceptionSandTime);
        rpc.addProperty("Batch", Batch);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 提交验收结果
     *
     * @param itemID
     * @param PassReceptionSandTime
     * @return
     */
    public String UpdateForPassReceptionSandTime(int itemID, String PassReceptionSandTime) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "UpdateForPassReceptionSandTime";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/UpdateForPassReceptionSandTime";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ItemID", itemID);
        rpc.addProperty("PassReceptionSandTime", PassReceptionSandTime);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 分包商预验收评价
     *
     * @param json
     * @return
     */
    public String InsertPreAcceptanceEvaluation(String json) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertPreAcceptanceEvaluation";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertPreAcceptanceEvaluation";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取分包商预供砂计划
     *
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String PublicSubcontractorSandPlanList(String SubcontractorAccount, String StartDate, String EndDate) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "PublicSubcontractorSandPlanList";
        // EndPoint
        String endPoint = "https://cchk3.kingwi.org/AppService/cchk3WebService.asmx";
        // SOAP Action
        String soapAction = "http://tempuri.org/PublicSubcontractorSandPlanList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 根据账号进行权限管理
     *
     * @param SubcontractorAccount
     * @return
     */
    public String getAppList(String SubcontractorAccount) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetAppList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetAppList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("UserID", SubcontractorAccount);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 更新量方数据
     * @param itemID
     * @param TheAmountOfTime
     * @param Capacity
     * @param DeckGauge
     * @param Deduction
     * @return
     */
    public String UpdateTheAmountOfSideData(int itemID, String TheAmountOfTime, String Capacity, String DeckGauge, String Deduction) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "UpdateTheAmountOfSideData";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/UpdateTheAmountOfSideData";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ItemID", itemID);
        rpc.addProperty("TheAmountOfTime", TheAmountOfTime);
        rpc.addProperty("Capacity", Capacity);
        rpc.addProperty("DeckGauge", DeckGauge);
        rpc.addProperty("Deduction", Deduction);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 信息完善
     * @param json
     * @return
     */
    public String InsertPerfectBoatRecord(String json) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertPerfectBoatRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertPerfectBoatRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取施工船舶
     * @return
     */
    public String GetConstructionBoat() {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetConstructionBoat";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetConstructionBoat";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取过砂记录数据
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String GetOverSandRecordList(String SubcontractorAccount, String StartDate, String EndDate) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetOverSandRecordList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetOverSandRecordList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);



        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 1.13获取验砂取样信息
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @param ExitTime
     * @return
     */
    public String GetSandSamplingList(String SubcontractorAccount, String StartDate, String EndDate, String ExitTime) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSandSamplingList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSandSamplingList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);



        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);
        rpc.addProperty("ExitTime", ExitTime);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 1.17获取对应的航次完善信息明细
     * @param ItemID
     * @return
     */
    public String GetPerfectBoatRecordByItemID(String ItemID) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetPerfectBoatRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetPerfectBoatRecordByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 1.21 上传图片接口
     * @param ByteDataStr
     * @param SuffixName
     * @param FileName
     * @return
     */
    public String UploadFile(String ByteDataStr, String SuffixName, String FileName) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "UploadFile";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/UploadFile";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ByteDataStr", ByteDataStr);
        rpc.addProperty("SuffixName", SuffixName);
        rpc.addProperty("FileName", FileName);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取分包商航次完善扫描件类型数据
     * @return
     */
    public String GetSubcontractorPerfectBoatScannerAttachmentTypeList() {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorPerfectBoatScannerAttachmentTypeList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorPerfectBoatScannerAttachmentTypeList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 提交过砂记录
     * @param json
     * @return
     */
    public String InsertOverSandRecord(String json) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertOverSandRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertOverSandRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 1.19根据进场计划ID获取过砂记录明细（多条）
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetOverSandRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetOverSandRecordBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetOverSandRecordBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 获取考勤类型
     * @return
     */
    public String GetAttendanceTypeList() {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetAttendanceTypeList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetAttendanceTypeList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 提交考勤数据
     * @param json
     * @return
     */
    public String InsertAttendanceRecord(String json) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertAttendanceRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertAttendanceRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 1.28	 获取考勤数据
     * @param ItemID
     * @param Creator
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String GetAttendanceRecords(int ItemID, String Creator, String StartDate, String EndDate) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetAttendanceRecords";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetAttendanceRecords";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);
        rpc.addProperty("Creator", Creator);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout);

        FakeX509TrustManager.allowAllSSL();

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }
}