package zx.learn.rbac_demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Integer userId;
    String userName;
    String userPassword;
    String userEmail;
    String userPhone;
    Double userBalance;

    Integer messageNum;

    String headerUrl;



}
