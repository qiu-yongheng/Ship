package com.kc.shiptransport.data.source.remote;

import com.kc.shiptransport.util.BaseUrl;
import com.kc.shiptransport.util.FakeX509TrustManager;
import com.kc.shiptransport.util.LogUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * @author qiuyongheng
 * @time 2017/5/17  10:29
 * @desc ${TODD}
 */
public class RemoteDataSource {
    private final String EndPoint = BaseUrl.EndPoint;
    private final int timeout = 10000;
    private final String nameSpace = "http://tempuri.org/";

    /**
     * 发送网络请求
     *
     * @param endPoint
     * @param soapAction
     * @param rpc
     * @return
     */
    private String getCallResult(String endPoint, String soapAction, SoapObject rpc) throws Exception {
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

        if (envelope.bodyIn.toString().contains("SoapFault")) {
            LogUtil.e("envelope.bodyIn: \n" + envelope.bodyIn.toString());
        }
        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        String result = object.getProperty(0).toString();
        return result;
    }

    /**
     * 登录
     *
     * @return
     */
    public String getLoginInfo(String username, String password) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取供应商信息
     *
     * @param subcontractorAccount 用户名
     * @return
     */
    public String getSubcontractorInfo(String subcontractorAccount) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取船信息
     *
     * @param subcontractorAccount 用户名
     * @return
     */
    public String getShipInfo(String subcontractorAccount) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取任务计划
     *
     * @param SubcontractorAccount 用户名
     * @param StartDay             开始时间 2017-05-18
     * @param EndDay               结束时间 2017-05-18
     * @return
     */
    public String getWeekTaskInfo(String SubcontractorAccount, String StartDay, String EndDay) throws Exception {
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

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交一周计划
     *
     * @param jsonData json数据
     * @return
     */
    public String commitWeekTask(String jsonData) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 根据ItemID, 返回验收明细
     *
     * @param itemID
     */
    public String getAcceptanceByItemID(int itemID) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交验沙结果
     * InsertReceptionSandRecord
     *
     * @return
     */
    public String InsertReceptionSandRecord(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertReceptionSandRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertReceptionSandRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("json", json);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交验收结果
     *
     * @param itemID
     * @param PassReceptionSandTime
     * @return
     */
    public String UpdateForPassReceptionSandTime(int itemID, String PassReceptionSandTime) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 供应商预验收评价
     *
     * @param json
     * @return
     */
    public String InsertPreAcceptanceEvaluation(String json) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取供应商预供砂计划
     *
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String PublicSubcontractorSandPlanList(String SubcontractorAccount, String StartDate, String EndDate) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "PublicSubcontractorSandPlanList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/PublicSubcontractorSandPlanList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 根据账号进行权限管理
     *
     * @param SubcontractorAccount
     * @return
     */
    public String getAppList(String SubcontractorAccount) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 更新量方数据
     *
     * @param json
     * @return
     */
    public String InsertTheAmountOfSideRecord(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertTheAmountOfSideRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertTheAmountOfSideRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 信息完善
     *
     * @param json
     * @return
     */
    public String InsertPerfectBoatRecord(String json) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取施工船舶
     *
     * @return
     */
    public String GetConstructionBoat() throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取过砂记录数据
     *
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String GetOverSandRecordList(String SubcontractorAccount, String StartDate, String EndDate) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.13获取验砂取样信息
     *
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @param ExitTime
     * @return
     */
    public String GetSandSamplingList(String SubcontractorAccount, String StartDate, String EndDate, String ExitTime) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param ItemID
     * @return
     */
    public String GetPerfectBoatRecordByItemID(String ItemID) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.21 上传图片接口
     *
     * @param ByteDataStr
     * @param SuffixName
     * @param FileName
     * @return
     */
    public String UploadFile(String ByteDataStr, String SuffixName, String FileName) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取供应商航次完善扫描件类型数据
     *
     * @return
     */
    public String GetSubcontractorPerfectBoatScannerAttachmentTypeList() throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交过砂记录
     *
     * @param json
     * @return
     */
    public String InsertOverSandRecord(String json) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.19根据进场计划ID获取过砂记录明细（多条）
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetOverSandRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取考勤类型
     *
     * @return
     */
    public String GetAttendanceTypeList() throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交考勤数据
     *
     * @param json
     * @return
     */
    public String InsertAttendanceRecord(String json) throws Exception {
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
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.28	 获取考勤数据
     *
     * @param ItemID
     * @param Creator
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String GetAttendanceRecords(int ItemID, String Creator, String StartDate, String EndDate, String Auditor) throws Exception {
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
        rpc.addProperty("Auditor", Auditor);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.23根据进场计划ID获取验砂取样信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetSandSamplingBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSandSamplingBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSandSamplingBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 提交验砂取样数据
     *
     * @param json
     * @return
     */
    public String InsertSandSampling(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertSandSampling";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertSandSampling";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.29 提交图片到供应商航次完善扫描件表(图片信息)
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    public String InsertSubcontractorPerfectBoatScannerAttachment(String json, String ByteDataStr) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertSubcontractorPerfectBoatScannerAttachment";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertSubcontractorPerfectBoatScannerAttachment";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        rpc.addProperty("ByteDataStr", ByteDataStr);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.31	 根据进场计划ID，获取包商航次完善扫描件
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetSubcontractorPerfectBoatScannerAttachmentRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorPerfectBoatScannerAttachmentRecordBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorPerfectBoatScannerAttachmentRecordBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.32	 获取扫描件明细（具体扫描件类型对应的图片信息）
     *
     * @param SubcontractorInterimApproachPlanID
     * @param SubcontractorPerfectBoatScannerAttachmentTypeID
     * @return
     */
    public String GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID(int SubcontractorInterimApproachPlanID, int SubcontractorPerfectBoatScannerAttachmentTypeID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorPerfectBoatScannerAttachmentRecordByAttachmentTypeID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);
        rpc.addProperty("SubcontractorPerfectBoatScannerAttachmentTypeID", SubcontractorPerfectBoatScannerAttachmentTypeID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.30	 删除供应商航次完善扫描件表(图片信息)
     *
     * @param ItemID
     * @return
     */
    public String DeleteSubcontractorPerfectBoatScannerAttachmentByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "DeleteSubcontractorPerfectBoatScannerAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/DeleteSubcontractorPerfectBoatScannerAttachmentByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.33	 获取料源石场数据
     *
     * @return
     */
    public String GetStoneSource() throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetStoneSource";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetStoneSource";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.35 获取量方信息数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetTheAmountOfSideRecordBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.27 提交考勤审核数据
     *
     * @param json
     * @return
     */
    public String InsertAttendanceCheckRecord(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertAttendanceCheckRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertAttendanceCheckRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.36 提交量方图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    public String InsertTheAmountOfSideAttachment(String json, String ByteDataStr) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertTheAmountOfSideAttachment";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertTheAmountOfSideAttachment";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        rpc.addProperty("ByteDataStr", ByteDataStr);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.34 获取洗石场所在地数据
     *
     * @return
     */
    public String GetWashStoreAddressOptions() throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetWashStoreAddressOptions";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetWashStoreAddressOptions";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.37 删除量方图片数据
     *
     * @param ItemID
     * @return
     */
    public String DeleteTheAmountOfSideAttachmentByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "DeleteTheAmountOfSideAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/DeleteTheAmountOfSideAttachmentByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.37提交验砂图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    public String InsertReceptionSandAttachment(String json, String ByteDataStr) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertReceptionSandAttachment";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertReceptionSandAttachment";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        rpc.addProperty("ByteDataStr", ByteDataStr);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.39 删除验砂图片数据
     *
     * @param ItemID
     * @return
     */
    public String DeleteReceptionSandAttachmentByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "DeleteReceptionSandAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/DeleteReceptionSandAttachmentByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.40 根据进场计划ID获取验砂数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetReceptionSandBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetReceptionSandBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetReceptionSandBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 删除过砂取样
     *
     * @param ItemID
     * @return
     */
    public String DeleteSandSamplingNumRecordByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "DeleteSandSamplingNumRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/DeleteSandSamplingNumRecordByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.17获取对应的航次完善信息明细
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetPerfectBoatRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetPerfectBoatRecordBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetPerfectBoatRecordBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.42 获取停工因素选项数据
     *
     * @return
     */
    public String GetStopOptions() throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetStopOptions";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetStopOptions";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.43 提交施工日志（停工）数据
     *
     * @param json
     * @return
     */
    public String InsertConstructionBoatStopDaily(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertConstructionBoatStopDaily";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertConstructionBoatStopDaily";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.45 获取当天施工日志（停工,开始时间默认值）
     *
     * @param CurrentDate
     * @param CurrentBoatAccount
     * @return
     */
    public String GetConstructionBoatDefaultStartTime(String CurrentDate, String CurrentBoatAccount) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetConstructionBoatDefaultStartTime";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetConstructionBoatDefaultStartTime";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("CurrentDate", CurrentDate);
        rpc.addProperty("CurrentBoatAccount", CurrentBoatAccount);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.46 提交施工日志（抛砂）数据
     *
     * @param json
     * @return
     */
    public String InsertConstructionBoatThrowingSandRecord(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertConstructionBoatThrowingSandRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertConstructionBoatThrowingSandRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 获取抛砂分层
     *
     * @return
     */
    public String GetConstructionLayerOptions() throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetConstructionLayerOptions";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetConstructionLayerOptions";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);


        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.44 获取施工日志（停工）数据
     *
     * @param ItemID
     * @param ShipAccount
     * @param StartTime
     * @param EndTime
     * @param StopTypeID
     * @param Creator
     * @return
     */
    public String GetConstructionBoatStopDaily(int ItemID, String ShipAccount, String StartTime, String EndTime, String StopTypeID, String Creator) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetConstructionBoatStopDaily";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetConstructionBoatStopDaily";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);
        rpc.addProperty("ShipAccount", ShipAccount);
        rpc.addProperty("StartTime", StartTime);
        rpc.addProperty("EndTime", EndTime);
        rpc.addProperty("StopTypeID", StopTypeID);
        rpc.addProperty("Creator", Creator);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.48	 获取施工日志（抛砂）数据
     *
     * @param ItemID
     * @param ShipAccount
     * @param StartTime
     * @param EndTime
     * @param Creator
     * @return
     */
    public String GetConstructionBoatThrowingSandList(int ItemID, String ShipAccount, String StartTime, String EndTime, String Creator) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetConstructionBoatThrowingSandList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetConstructionBoatThrowingSandList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);
        rpc.addProperty("ShipAccount", ShipAccount);
        rpc.addProperty("StartTime", StartTime);
        rpc.addProperty("EndTime", EndTime);
        rpc.addProperty("Creator", Creator);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.49 获取可以进行退场申请的数据
     *
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     */
    public String GetExitApplyPendingApplicationList(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetExitApplyPendingApplicationList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetExitApplyPendingApplicationList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 3.1 修改密码
     *
     * @return
     */
    public String ChangeUserPassword(String LoginName, String OldPassword, String NewPassword) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "ChangeUserPassword";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/ChangeUserPassword";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("LoginName", LoginName);
        rpc.addProperty("OldPassword", OldPassword);
        rpc.addProperty("NewPassword", NewPassword);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.51	 根据退场ItemID,获取退场申请的数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     */
    public String GetExitApplicationRecordBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetExitApplicationRecordBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetExitApplicationRecordBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.53 删除退场申请图片数据
     *
     * @param ItemID
     * @return
     */
    public String DeleteExitApplicationAttachmentByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "DeleteExitApplicationAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/DeleteExitApplicationAttachmentByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.52 提交退场申请图片数据
     *
     * @param json
     * @param ByteDataStr
     * @return
     */
    public String InsertExitApplicationAttachment(String json, String ByteDataStr) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertExitApplicationAttachment";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertExitApplicationAttachment";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        rpc.addProperty("ByteDataStr", ByteDataStr);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.50 提交退场申请数据
     *
     * @param json
     * @return
     */
    public String InsertExitApplicationRecord(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "InsertExitApplicationRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/InsertExitApplicationRecord";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 3.2 修改用户信息
     *
     * @param json
     * @return
     */
    public String ChangeUserData(String json) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "ChangeUserData";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/ChangeUserData";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        return getCallResult(endPoint, soapAction, rpc);


    }

    /**
     * 3.3 获取用户信息
     *
     * @param LoginName
     * @return
     */
    public String GetUserDataByLoginName(String LoginName) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetUserDataByLoginName";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetUserDataByLoginName";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("LoginName", LoginName);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 3.4 获取部门信息
     *
     * @return
     * @throws Exception
     */
    public String GetDepartmentsOptions() throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetDepartmentsOptions";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetDepartmentsOptions";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.18 获取对应的过砂记录信息明细
     *
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String GetOverSandRecordByItemID(int ItemID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetOverSandRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetOverSandRecordByItemID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 2.5 获取供应商进场计划进度跟踪
     *
     * @param SubcontractorAccount
     * @param ShipName
     * @param StartDate
     * @param EndDate
     * @return
     * @throws Exception
     */
    public String GetSubcontractorInterimApproachPlanProgressTracking(String SubcontractorAccount, String ShipName, String StartDate, String EndDate) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorInterimApproachPlanProgressTracking";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorInterimApproachPlanProgressTracking";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("ShipName", ShipName);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 2.4 根据进场计划ID，获取供砂过程总表
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     * @throws Exception
     */
    public String GetAllDetailBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetAllDetailBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetAllDetailBySubcontractorInterimApproachPlanID";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.54 获取供应商预验收评价数据
     *
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetPreAcceptanceEvaluationList(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetPreAcceptanceEvaluationList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetPreAcceptanceEvaluationList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.55 获取供应商评分排行榜
     *
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetSubcontractorPreAcceptanceEvaluationRanking(String ConditionJson) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetSubcontractorPreAcceptanceEvaluationRanking";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetSubcontractorPreAcceptanceEvaluationRanking";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 4.1 获取当前app最新版本
     *
     * @param Version 本地版本
     * @return
     * @throws Exception
     */
    public String GetNewVersion(String Version) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetLastAppVersion ";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetLastAppVersion ";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("Version", Version);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 3.5 获取所有用户信息(通讯录数据源)
     *
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetMembers(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetMembers";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.56 提交分包商航次完善扫描件(用于确认提交)
     *
     * @return
     * @throws Exception
     */
    public String InsertSubcontractorPerfectBoatScannerRecord(String json) throws Exception {
        // 调用的方法名称
        String methodName = "InsertSubcontractorPerfectBoatScannerRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.61 判断是否允许新增或者修改进场计划数据
     *
     * @param Date
     * @return
     * @throws Exception
     */
    public String IsAllowEditPlanData(String Date) throws Exception {
        // 调用的方法名称
        String methodName = "IsAllowEditPlanData";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("Date", Date);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.57 根据进场计划ID获取分包商预验收评价数据
     *
     * @param SubcontractorInterimApproachPlanID
     * @return
     * @throws Exception
     */
    public String GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 调用的方法名称
        String methodName = "GetSandSubcontractorPreAcceptanceEvaluationBySubcontractorInterimApproachPlanID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorInterimApproachPlanID", SubcontractorInterimApproachPlanID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.60 获取量方人员信息数据
     *
     * @return
     * @throws Exception
     */
    public String GetTheAmountOfPersonnelOptions() throws Exception {
        // 调用的方法名称
        String methodName = "GetTheAmountOfPersonnelOptions";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.58 提交验砂管理船名照片(图片信息)
     *
     * @param json
     * @param ByteDataStr
     * @return
     * @throws Exception
     */
    public String InsertReceptionSandBoatNameAttachment(String json, String ByteDataStr) throws Exception {
        // 调用的方法名称
        String methodName = "InsertReceptionSandBoatNameAttachment";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);
        rpc.addProperty("ByteDataStr", ByteDataStr);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.59 删除验砂管理船名照片(图片信息)
     *
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteReceptionSandBoatNameAttachmentByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteReceptionSandBoatNameAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.63 获取可以进行退场审核的数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetExitAuditPendingApplicationRecords(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetExitAuditPendingApplicationRecords";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.65 获取退场离场反馈信息
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetExitAuditedApplicationRecords(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetExitAuditedApplicationRecords";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.66 获取用户待办信息
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetPendingTaskList(int PageSize, int PageCount, String ConditionJson, String CurrentUserAccount) throws Exception {
        // 调用的方法名称
        String methodName = "GetPendingTaskList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);
        rpc.addProperty("CurrentUserAccount", CurrentUserAccount);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.67根据ItemID删除过砂记录信息
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteOverSandRecordByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteOverSandRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.68 获取施工船舶明细数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetConstructionBoatDailyList(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetConstructionBoatDailyList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.69 删除船舶日志停工数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteConstructionBoatStopDailyByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteConstructionBoatStopDailyByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.70 删除船舶抛砂数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteConstructionBoatThrowingSandRecordsByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteConstructionBoatThrowingSandRecordsByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.49 获取退场数据(退场申请，退场审核列表)
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetExitApplicationList(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetExitApplicationList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.68 获取供砂船航次信息数据(近7天)
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetBoatShipItemNum(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetBoatShipItemNum";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.24 删除验砂取样图片数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteSandSamplingAttachmentByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteSandSamplingAttachmentByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.69 提交BCF供砂来船数据
     * @param json
     * @return
     * @throws Exception
     */
    public String InsertBCFToShipRecord(String json) throws Exception {
        // 调用的方法名称
        String methodName = "InsertBCFToShipRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.70 提交BCF抛砂数据（施工日志抛砂）
     * @param json
     * @return
     * @throws Exception
     */
    public String InsertBCFBoatThrowingSandRecord(String json) throws Exception {
        // 调用的方法名称
        String methodName = "InsertBCFBoatThrowingSandRecord";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("json", json);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.71 获取BCF来砂船舶的明细数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetBCFToShipRecords(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetBCFToShipRecords";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.72 获取BCF来砂船舶（抛砂）的明细数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetBCFBoatList(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetBCFBoatList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.73 获取BCF来砂船舶数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetBCFToShipInfo(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetBCFToShipInfo";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.73 删除BCF来砂船舶日志数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteBCFToShipRecordsByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteBCFToShipRecordsByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.74 删除BCF船舶日志(抛砂日志)数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String DeleteBCFBoatThrowingSandRecordsByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "DeleteBCFBoatThrowingSandRecordsByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.75 根据ItemID获取施工日志（抛砂）数据 (BCF来砂)
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String GetBCFBoatThrowingSandRecordByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "GetBCFBoatThrowingSandRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.76 根据ItemID获取施工日志（抛砂）数据
     * @param ItemID
     * @return
     * @throws Exception
     */
    public String GetConstructionBoatThrowingSandRecordByItemID(int ItemID) throws Exception {
        // 调用的方法名称
        String methodName = "GetConstructionBoatThrowingSandRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("ItemID", ItemID);

        return getCallResult(endPoint, soapAction, rpc);
    }

    /**
     * 1.77 获取泵砂船数据
     * @param PageSize
     * @param PageCount
     * @param ConditionJson
     * @return
     * @throws Exception
     */
    public String GetPumpShipInfo(int PageSize, int PageCount, String ConditionJson) throws Exception {
        // 调用的方法名称
        String methodName = "GetPumpShipInfo";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = nameSpace + methodName;

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("PageSize", PageSize);
        rpc.addProperty("PageCount", PageCount);
        rpc.addProperty("ConditionJson", ConditionJson);

        return getCallResult(endPoint, soapAction, rpc);
    }
}