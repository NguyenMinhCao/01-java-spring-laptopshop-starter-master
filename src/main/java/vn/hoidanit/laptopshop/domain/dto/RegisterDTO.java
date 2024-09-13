package vn.hoidanit.laptopshop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.laptopshop.service.validator.RegisterChecked;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RegisterChecked
public class RegisterDTO {
    // các trường ứng với form đăng ký tài khoản
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
