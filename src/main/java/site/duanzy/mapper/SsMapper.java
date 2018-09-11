package site.duanzy.mapper;

import site.duanzy.entity.Ss;

import java.util.List;
import java.util.Map;

public interface SsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ss record);

    int insertSelective(Ss record);

    Ss selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ss record);

    int updateByPrimaryKey(Ss record);

    /**
     * 根据条件查询账号信息
     * @param param
     * @return
     */
    List<Ss> getAccounts(Map<String, Object> param);

    /**
     * 根据端口删除记录
     * @param port
     * @return
     */
    int deleteByPort(int port);
}