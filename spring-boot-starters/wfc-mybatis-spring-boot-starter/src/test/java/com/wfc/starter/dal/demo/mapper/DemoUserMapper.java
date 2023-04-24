package com.wfc.starter.dal.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wfc.starter.dal.demo.entity.DemoUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 飞影
 * @create by 2019-08-30 10:51
 */
@Mapper
public interface DemoUserMapper extends BaseMapper<DemoUserDO> {

    /**
     * 通过xml定义sql查询
     *
     * @param name
     * @return
     */
    DemoUserDO getUserByName(String name);

    /**
     * like查询
     *
     * @param page
     * @param name
     * @return
     */
    IPage<DemoUserDO> selectLike(IPage<DemoUserDO> page, @Param("name") String name);

}
