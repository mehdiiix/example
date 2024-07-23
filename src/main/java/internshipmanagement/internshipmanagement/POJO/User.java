package internshipmanagement.internshipmanagement.POJO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser", query = "select new internshipmanagement.internshipmanagement.wrapper.UserWrapper(u.id,u.name,u.email,u.contactnumber) from User u where u.role='user'")
@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")
@NamedQuery(name = "User.getAllUser", query = "select u.email from User u where u.role='admin'")

 @Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Getter
    @Column(name = "name")
    private String name;

    @Column(name = "contactnumber")
    private String contactnumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;



}
