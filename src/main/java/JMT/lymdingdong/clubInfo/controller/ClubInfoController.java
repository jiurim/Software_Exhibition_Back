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

    @GetMapping("/getSlideContent/{category}/{slideKey}")
    public ResponseEntity<?> getSlideContent(@PathVariable String category, @PathVariable String slideKey) {
        try {
            int key = Integer.parseInt(slideKey);
            ClubInfoDTO clubInfo = clubInfoService.getClubInfoByCategoryAndSlideKey(category, key);
            return new ResponseEntity<>(clubInfo, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid slide key format", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}