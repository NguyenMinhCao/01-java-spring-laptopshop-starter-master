package vn.hoidanit.laptopshop.domain.dto;

import jakarta.validation.constraints.Size;
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
    @Size(min = 3, message = "First name tối thiểu 3 ký tự")
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Size(min = 3, message = "Confirm ít nhất 3 ký tự")
    private String confirmPassword;
}
