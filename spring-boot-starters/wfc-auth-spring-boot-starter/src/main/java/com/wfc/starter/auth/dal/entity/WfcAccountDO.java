package com.wfc.starter.auth.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wfc.starter.dal.BaseDO;
import lombok.Data;

/**
 * @author 飞影
 * @create by 2020-09-29 20:08
 */
@Data
@TableName("wfc_account")
public class WfcAccountDO extends BaseDO {

    private String loginName;
    private String phoneNum;
    private String password;
    private String slat;
    private Integer disabled;

}
