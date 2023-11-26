package JMT.lymdingdong.clubInfo.service;

import JMT.lymdingdong.clubInfo.dto.ClubInfoDTO;
import JMT.lymdingdong.clubInfo.entity.ClubInfoEntity;
import JMT.lymdingdong.clubInfo.repository.ClubInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubInfoService {

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    public ClubInfoDTO getClubInfoByClubName(String clubName) {
        ClubInfoEntity clubInfoEntity = clubInfoRepository.findByClubName(clubName);
        if (clubInfoEntity == null) {
            throw new IllegalArgumentException("클럽을 찾을 수 없습니다.");
        }
        return ClubInfoDTO.builder()
                .category(clubInfoEntity.getCategory())
                .clubName(clubInfoEntity.getClubName())
                .activityDescription(clubInfoEntity.getActivityDescription())
                .activitySchedule(clubInfoEntity.getActivitySchedule())
                .membershipFee(clubInfoEntity.getMembershipFee())
                .clubLocation(clubInfoEntity.getClubLocation())
                .photoUrl(clubInfoEntity.getPhotoUrl1())
                .photoUrl(clubInfoEntity.getPhotoUrl2())
                .photoUrl(clubInfoEntity.getPhotoUrl3())
                .build();
    }
}
