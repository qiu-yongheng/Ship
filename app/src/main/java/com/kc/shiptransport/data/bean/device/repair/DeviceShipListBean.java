package com.kc.shiptransport.data.bean.device.repair;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/6/16  22:14
 * @desc
 */

public class DeviceShipListBean {
    /**
     * AllRowCount : 20
     * AllPageCount : 1
     * Data : [{"rownumber":1,"ShipAccount":"yz105","ShipName":"永照105","ShipType":1},{"rownumber":2,"ShipAccount":"xj29","ShipName":"祥记29","ShipType":1},{"rownumber":3,"ShipAccount":"xj11","ShipName":"祥记11","ShipType":1},{"rownumber":4,"ShipAccount":"xj10","ShipName":"祥记10","ShipType":1},{"rownumber":5,"ShipAccount":"push1","ShipName":"铺沙1","ShipType":1},{"rownumber":6,"ShipAccount":"jx6","ShipName":"吉星6","ShipType":1},{"rownumber":7,"ShipAccount":"jx3","ShipName":"吉星3","ShipType":1},{"rownumber":8,"ShipAccount":"jx2","ShipName":"吉星2","ShipType":3},{"rownumber":9,"ShipAccount":"jx1","ShipName":"吉星1","ShipType":1},{"rownumber":10,"ShipAccount":"hy3228","ShipName":"辉裕3228","ShipType":1},{"rownumber":11,"ShipAccount":"hjd","ShipName":"横鸡趸","ShipType":1},{"rownumber":12,"ShipAccount":"gy1","ShipName":"冠亚1","ShipType":1},{"rownumber":13,"ShipAccount":"cssgcb","ShipName":"测试施工船舶","ShipType":1},{"rownumber":14,"ShipAccount":"t14","ShipName":"T14","ShipType":1},{"rownumber":15,"ShipAccount":"ftb25","ShipName":"FTB25","ShipType":1},{"rownumber":16,"ShipAccount":"ftb23","ShipName":"FTB23","ShipType":1},{"rownumber":17,"ShipAccount":"ftb21","ShipName":"FTB21","ShipType":1},{"rownumber":18,"ShipAccount":"fc155","ShipName":"FC155","ShipType":1},{"rownumber":19,"ShipAccount":"dcoc4","ShipName":"DCOC4","ShipType":1},{"rownumber":20,"ShipAccount":"dcoc3","ShipName":"DCOC3","ShipType":1}]
     */

    private int AllRowCount;
    private int AllPageCount;
    private List<DeviceShipBean> Data;

    public int getAllRowCount() { return AllRowCount;}

    public void setAllRowCount(int AllRowCount) { this.AllRowCount = AllRowCount;}

    public int getAllPageCount() { return AllPageCount;}

    public void setAllPageCount(int AllPageCount) { this.AllPageCount = AllPageCount;}

    public List<DeviceShipBean> getData() { return Data;}

    public void setData(List<DeviceShipBean> Data) { this.Data = Data;}

}
