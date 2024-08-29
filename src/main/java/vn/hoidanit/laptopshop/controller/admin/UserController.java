package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    // user information list page
    @GetMapping("/admin/user")
    public String getHomePage(Model model) {
        List<User> arrUser = this.userService.getAllUser();
        model.addAttribute("listUser", arrUser);
        return "/admin/user/show";
    }

    // user creation page form
    @GetMapping("/admin/user/create")
    public String getCreateUser(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @PostMapping(value = "/admin/user/create-user")
    public String createUser(
            @ModelAttribute("newUser") User user,
            @RequestParam("getImgFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashCode = this.passwordEncoder.encode(user.getPassword());
        user.setAvatar(avatar);
        user.setPassword(hashCode);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    // detele user by id
    @GetMapping(value = "/admin/user/view-deteleId/{id}")
    public String viewDeteleUser(@PathVariable("id") Long id, Model model) {
        Optional<User> getUserOp = this.userService.findByIDUser(id);
        User getUser = getUserOp.get();
        model.addAttribute("idUser", getUser.getId());
        return "/admin/user/delete";
    }

    @GetMapping(value = "/admin/user/deteleId/{id}")
    public String deteleUser(@PathVariable("id") Long id) {
        this.userService.deleteByIdUser(id);
        return "redirect:/admin/user";
    }

    // page updates users by id
    @GetMapping(value = "/admin/user/view-updateId/{id}")
    public String viewUpdateUser(Model model, @PathVariable("id") Long id) {
        Optional<User> userOptional = this.userService.findByIDUser(id);
        User user = userOptional.get();
        model.addAttribute("updateUser", user);
        return "/admin/user/update";
    }

    @PostMapping(value = "/admin/user/updateId")
    public String updateUser(@ModelAttribute("updateUser") User user,
            @RequestParam("getImgFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        Optional<User> currentUser = this.userService.findByIDUser(user.getId());
        User currentUserUnWrap = currentUser.get();
        if (currentUserUnWrap != null) {
            currentUserUnWrap.setFullName(user.getFullName());
            currentUserUnWrap.setAddress(user.getAddress());
            currentUserUnWrap.setPassword(user.getPassword());
            currentUserUnWrap.setPhone(user.getPhone());
            currentUserUnWrap.setRole(this.userService.getRoleByName(user.getRole().getName()));
            currentUserUnWrap.setAvatar(avatar);
            this.userService.handleSaveUser(currentUserUnWrap);
        }
        return "redirect:/admin/user";
    }

    // page displays user details by id
    @GetMapping(value = "/admin/user/user-detail/{id}")
    public String userDetail(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional = this.userService.findByIDUser(id);
        User user = userOptional.get();
        model.addAttribute("userName", user.getFullName());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userAddress", user.getAddress());
        model.addAttribute("userPhone", user.getPhone());
        model.addAttribute("userPass", user.getPassword());
        return "/admin/user/detail";
    }

}
