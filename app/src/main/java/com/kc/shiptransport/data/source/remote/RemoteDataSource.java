package com.kc.shiptransport.data.source.remote;

import com.kc.shiptransport.util.BaseUrl;
import com.kc.shiptransport.util.FakeX509TrustManager;

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
    private final int timeout = 15000;

    /**
     * 发送网络请求
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
     * 获取分包商信息
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
     * UpdateForReceptionSandTime
     *
     * @return
     */
    public String UpdateForReceptionSandTime(int itemID, String ReceptionSandTime) throws Exception {
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
     * 分包商预验收评价
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
     * 获取分包商预供砂计划
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
     * 获取分包商航次完善扫描件类型数据
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
     * 1.29 提交图片到分包商航次完善扫描件表(图片信息)
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
     * 1.30	 删除分包商航次完善扫描件表(图片信息)
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
     * @param SubcontractorAccount
     * @param StartDate
     * @param EndDate
     * @return
     */
    public String GetExitApplicationList(String SubcontractorAccount, String StartDate, String EndDate) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetExitApplicationList";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetExitApplicationList";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        rpc.addProperty("SubcontractorAccount", SubcontractorAccount);
        rpc.addProperty("StartDate", StartDate);
        rpc.addProperty("EndDate", EndDate);

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
    public String GetExitApplicationRecordByItemID(int SubcontractorInterimApproachPlanID) throws Exception {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "GetExitApplicationRecordByItemID";
        // EndPoint
        String endPoint = EndPoint;
        // SOAP Action
        String soapAction = "http://tempuri.org/GetExitApplicationRecordByItemID";

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
}