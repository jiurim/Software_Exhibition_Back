package JMT.lymdingdong.clubInfo.repository;
import JMT.lymdingdong.clubInfo.entity.ClubInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubInfoRepository extends JpaRepository<ClubInfoEntity, Integer> {
    ClubInfoEntity findByClubName(String clubName);
}