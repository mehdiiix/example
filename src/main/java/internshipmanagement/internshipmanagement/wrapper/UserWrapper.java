package internshipmanagement.internshipmanagement.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {
    private Integer id;
    private String name;
    private String contactnumber;
    private String email;
    public UserWrapper(Integer id, String name, String contactNumber, String email) {
        this.id = id;
        this.name = name;
        this.contactnumber = contactNumber;
        this.email = email;
    }
}
