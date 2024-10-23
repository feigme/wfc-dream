package cn.wmall.tp.enums;

import java.util.Arrays;

/**
 * 订单状态
 *
 * @author hui.guo
 * @since 2024/2/2 2:42 下午
 */
public enum OrderStatusEnum {

    WAIT_PAY(1, "待支付"),
    PAID_PART(103, "部分支付"),
    PAID(105, "已支付"),
    WAIT_DELIVERY(2, "待发货"),
    DELIVERED_PART(101, "部分发货"),
    DELIVERED(3, "已发货"),
    CLOSED(4, "交易关闭"),
    SIGNED(5, "已签收"),
    FINISHED(2, "交易完成");

//    21    发货前退款完结
//    22	发货后退款完结
//    39	收货后退款完结

    int code;
    String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(OrderStatusEnum.values()).filter(x -> code == x.getCode()).findFirst().orElse(null);
    }
}
