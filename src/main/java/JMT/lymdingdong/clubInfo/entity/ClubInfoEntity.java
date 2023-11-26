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

    @Column(name = "department")
    private String category;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "substance")
    private String activityDescription;

    @Column(name = "period")
    private String activitySchedule;

    @Column(name = "membership_fee")
    private String membershipFee;

    @Column(name = "location")
    private String clubLocation;

    @Column(name = "club_image1")
    private String photoUrl1;

    @Column(name = "club_image2")
    private String photoUrl2;

    @Column(name = "club_image3")
    private String photoUrl3;

}
