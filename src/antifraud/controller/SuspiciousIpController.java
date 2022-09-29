package antifraud.controller;

import antifraud.model.DeleteResponse;
import antifraud.model.SusIp.SusIp;
import antifraud.service.SusIpService.SusIpService;
import antifraud.validation.ValidIp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class SuspiciousIpController {

    private SusIpService ipService;
    public SuspiciousIpController(SusIpService ipService){
        this.ipService = ipService;
    }

    @PostMapping("/suspicious-ip")
    @ResponseStatus(HttpStatus.OK)
    public SusIp saveSuspiciousIp(@RequestBody SusIp ip){
        return ipService.saveIp(ip);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse deleteIp(@PathVariable String ip){
        return ipService.deleteIp(ip);
    }

    @GetMapping("/suspicious-ip")
    @ResponseStatus(HttpStatus.OK)
    public List<SusIp> ShowSuspiciousIps(){
        return ipService.showIps();
    }
}
