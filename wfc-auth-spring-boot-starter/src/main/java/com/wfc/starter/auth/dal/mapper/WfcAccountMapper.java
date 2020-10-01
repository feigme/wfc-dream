package com.wfc.starter.auth.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfc.starter.auth.dal.entity.WfcAccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 飞影
 * @create by 2020-09-29 20:29
 */
@Mapper
public interface WfcAccountMapper extends BaseMapper<WfcAccountDO> {

    /**
     * 根据登陆名称获取账号信息
     *
     * @param loginName
     * @return
     */
    WfcAccountDO getAccountByLoginName(String loginName);

    /**
     * 根据手机号码获取账号信息
     *
     * @param phoneNum
     * @return
     */
    WfcAccountDO getAccountByPhoneNum(String phoneNum);

}
