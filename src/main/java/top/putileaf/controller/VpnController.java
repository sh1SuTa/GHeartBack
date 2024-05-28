package top.putileaf.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.putileaf.pojo.Result;
import top.putileaf.pojo.Vpn;
import top.putileaf.service.VpnService;

import java.util.List;

@RestController
public class VpnController {

    @Autowired
    private VpnService vpnService;

    @GetMapping("/vpn")
    public Result list(){
        List<Vpn> vpnNode= vpnService.list();
        if( vpnNode != null && !vpnNode.isEmpty()){
            return Result.success(vpnNode);
        }else {
            return Result.error("你不是会员");
        }

    }







}
