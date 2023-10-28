package JMT.lymdingdong.clubInfo.controller;

import JMT.lymdingdong.clubInfo.dto.ClubInfoDTO;
import JMT.lymdingdong.clubInfo.service.ClubInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClubInfoController {
    @Autowired
    private ClubInfoService clubInfoService;

    @GetMapping("/getSlideContent/{clubName}")
    public ResponseEntity<ClubInfoDTO> getSlideContent(@PathVariable String clubName) {
        try {
            ClubInfoDTO clubInfo = clubInfoService.getClubInfoByClubName(clubName);
            return new ResponseEntity<>(clubInfo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}