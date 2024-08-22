package vn.hoidanit.laptopshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin/user")
    public String getHomePage(Model model) {
        List<User> arrUser = this.userService.getAllUser();
        model.addAttribute("listUser", arrUser);
        return "/admin/user/home";
    }

    @RequestMapping("/admin/user/create")
    public String getCreateUser(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create-user", method = RequestMethod.POST)
    public String createUser(Model model, @ModelAttribute("newUser") User user) {
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/deteleId/{id}")
    public String deteleUser(@PathVariable("id") Long id) {
        this.userService.deleteByIdUser(id);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/view-updateId/{id}")
    public String viewUpdateUser(Model model, @PathVariable("id") Long id) {
        Optional<User> userOptional = this.userService.findByIDUser(id);
        User user = userOptional.get();
        model.addAttribute("updateUser", user);
        return "/admin/user/update";
    }

    @RequestMapping(value = "/admin/user/updateId/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id);
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

}
