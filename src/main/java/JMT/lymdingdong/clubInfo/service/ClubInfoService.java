package JMT.lymdingdong.clubInfo.service;

import JMT.lymdingdong.clubInfo.dto.ClubInfoDTO;
import JMT.lymdingdong.clubInfo.entity.ClubInfoEntity;
import JMT.lymdingdong.clubInfo.repository.ClubInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubInfoService {

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    public ClubInfoDTO getClubInfoByCategoryAndSlideKey(String category, int slideKey) {
        List<ClubInfoEntity> clubInfos = clubInfoRepository.findByCategory(category);

        if (slideKey < 0 || slideKey > clubInfos.size()) {
            throw new IllegalArgumentException("잘못된 슬라이드 번호입니다.");
        }

        ClubInfoEntity clubInfoEntity = clubInfos.get(slideKey);
        return ClubInfoDTO.builder()
                .category(clubInfoEntity.getCategory())
                .activityDescription(clubInfoEntity.getActivityDescription())
                .activitySchedule(clubInfoEntity.getActivitySchedule())
                .membershipFee(clubInfoEntity.getMembershipFee())
                .clubLocation((clubInfoEntity.getClubLocation()))
                .photoUrl(clubInfoEntity.getPhotoUrl())
                .build();
    }
}
