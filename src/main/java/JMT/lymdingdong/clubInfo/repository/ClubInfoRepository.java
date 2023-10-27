package JMT.lymdingdong.clubInfo.repository;
import JMT.lymdingdong.clubInfo.entity.ClubInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClubInfoRepository extends JpaRepository<ClubInfoEntity, Integer> {
    List<ClubInfoEntity> findByCategory(String category);
}