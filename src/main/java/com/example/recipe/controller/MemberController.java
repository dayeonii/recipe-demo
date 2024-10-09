package com.example.recipe.controller;

import com.example.recipe.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * 회원 관리 요청을 처리하기 위한 컨트롤러
 * */


@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemoryMemberRepository memoryMemberRepository;

    /********************
    * 회원가입 로직 처리
    * ******************/

    @GetMapping("/check-duplicate-id")
    @ResponseBody
    public boolean checkDuplicateId(@RequestParam String userID) {
        Member existingMember = memoryMemberRepository.findById(userID);
        return existingMember == null;  //아이디 사용 가능하면 true 반환 (findById가 null이면 true인거지)
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String userID,
                           @RequestParam String userPW,
                           @RequestParam String confirmuserPW,
                           @RequestParam String userName,
                           @RequestParam String userEmail,
                           @RequestParam String userPhone,
                           RedirectAttributes redirectAttributes) {

        //중복 아이디 확인
        Member existingMember = memoryMemberRepository.findById(userID);
        if (existingMember != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 사용 중인 아이디입니다.");
            return "redirect:/register";
        }

        //비밀번호 재확인
        if (!userPW.equals(confirmuserPW)) {
            redirectAttributes.addFlashAttribute("pwerrorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/register";
        }

        //폼에 입력된 정보로 멤버 객체 생성 후 저장 (게시글 수는 default 값인 0)
        Member newMember = new Member(userID, userPW, userName, userEmail, userPhone, Grade.BASIC, 0, 0);
        memberService.saveMember(newMember);

        return "redirect:/";
    }


    /********************
     * 로그인 로직 처리
     * ******************/

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userID, @RequestParam String userPW, RedirectAttributes redirectAttributes) {

        Member member = memoryMemberRepository.findById(userID);    //사용자 조회

        if (member != null && member.getPassword().equals(userPW)) {
            redirectAttributes.addFlashAttribute("currentUser", member);    //현재 사용자 정보 저장
            return "redirect:/myinfo";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 틀립니다.");
            return "redirect:/login";
        }

    }

    /***********************
     * 내 정보 확인 로직 처리
     * ***********************/

    @GetMapping("/myinfo")
    public String showMyInfoForm(Model model) {
        return "myinfo";
    }

    /********************
     * 로그아웃 로직 처리
     * ******************/

    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("logoutMessage", "로그아웃 되었습니다");
        return "redirect:/login";
    }
}
