package top.putileaf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.putileaf.mapper.VpnMapper;
import top.putileaf.pojo.Vpn;
import top.putileaf.service.VpnService;
import top.putileaf.utils.ThreadLocalUtil;

import java.util.List;
import java.util.Map;

@Service
public class VpnServiceImpl implements VpnService {

    @Autowired
    private VpnMapper vpnMapper;


    @Override
    public List<Vpn> list() {
        //获取当前登录的id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //获取是否成员身份
        Integer vip = (Integer) map.get("vip");
        if (vip == 1){
            List<Vpn> list = vpnMapper.listAll();
        //是会员显示所有节点
            return list;
        }else {
            return null;
        }






    }
}
