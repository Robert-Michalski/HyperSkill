package antifraud.service.SusIpService;

import antifraud.model.DeleteResponse;
import antifraud.model.SusIp.SusIp;

import java.util.List;

public interface SusIpService {
    SusIp saveIp(SusIp ip);

    DeleteResponse deleteIp(String ip);

    List<SusIp> showIps();
}
