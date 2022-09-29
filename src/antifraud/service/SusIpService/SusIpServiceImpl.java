package antifraud.service.SusIpService;

import antifraud.model.DeleteResponse;
import antifraud.model.SusIp.SusIp;
import antifraud.repository.SusIpRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SusIpServiceImpl implements SusIpService {

    private SusIpRepo ipRepo;

    public SusIpServiceImpl(SusIpRepo ipRepo) {
        this.ipRepo = ipRepo;
    }

    @Override
    public SusIp saveIp(SusIp ip) {
        if (ipRepo.existsByIp(ip.getIp())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (!isValidIp(ip.getIp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ipRepo.save(ip);
    }

    @Override
    @Transactional
    public DeleteResponse deleteIp(String ip) {
        if (!ipRepo.existsByIp(ip)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!isValidIp(ip)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ipRepo.deleteByIp(ip);
        return new DeleteResponse("IP " + ip + " successfully removed!");
    }

    @Override
    public List<SusIp> showIps() {
        List<SusIp> allIps = new ArrayList<>();
        ipRepo.findAll().forEach(allIps::add);
        return allIps;
    }

    private boolean isValidIp(String ip) {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;
        return ip.matches(regex);
    }
}
