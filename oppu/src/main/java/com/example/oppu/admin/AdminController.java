package com.example.oppu.admin;

import com.example.oppu.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/admin")
public class AdminController {

    private final AdminService adminService;

    private final MemberService memberService;

    public AdminController(AdminService adminService, MemberService memberService) {
        this.adminService = adminService;
        this.memberService = memberService;
    }

    //관리자페이지
    @RequestMapping("")
    public String adminMain() {
        return "/admin/adminMain";
    }

    //전체 회원목록
    @GetMapping("/allMember")
    public String allMember(Model model,
                            @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "searchCate", required = false, defaultValue = "")String searchCate,
                            @RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword) {

        model.addAttribute("searchCate", searchCate);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("paging", memberService.getList(searchCate, searchKeyword, pageable));

        return "/admin/allMember";
    }

    //관리자 목록
//    @GetMapping("/getAdminList")
//    public String getAdminList(Model model) {
//     model.addAttribute("adminList", adminService.getAdminList());
//     return "/admin/getAdminList";
//    }

    //관리자 상세보기
//    @GetMapping("/getAdmin")
//    public String getMagazine(Admin admin, Model model) {
//        model.addAttribute("admin", adminService.getAdmin(admin));
//        return "/admin/getAdmin";
//    }

    //관리자 수정
    @PostMapping("/updateAdmin")
    public String updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        return "redirect:/admin/getAdminList";
    }

    //관리자 삭제
    @GetMapping("/deleteAdmin")
    public String deleteAdmin(Admin admin) {
        adminService.deleteAdmin(admin);
        return "redirect:/admin/getAdminList";
    }


}