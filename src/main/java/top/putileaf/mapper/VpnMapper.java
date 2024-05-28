package top.putileaf.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.putileaf.pojo.Vpn;

import java.util.List;

@Mapper
public interface VpnMapper {


    @Select("select * from vpnTab ")
    List<Vpn> listAll();








}
