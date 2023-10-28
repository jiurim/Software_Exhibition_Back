package JMT.lymdingdong.clubInfo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "clubInfo")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubInfoEntity {

    @Id
    private int Id;

    @Column(name = "분과")
    private String category;

    @Column(name = "동아리명")
    private String clubName;

    @Column(name = "활동내용")
    private String activityDescription;

    @Column(name = "활동시기")
    private String activitySchedule;

    @Column(name = "회비")
    private String membershipFee;

    @Column(name = "동아리방위치")
    private String clubLocation;

    @Column(name = "활동사진링크")
    private String photoUrl;

}
