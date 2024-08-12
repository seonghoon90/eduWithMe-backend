package com.sparta.eduwithme.domain.user.service;

import com.sparta.eduwithme.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private int authNumber;

    //사용자가 입력한 인증코드와 실제 인증코드 비교
    public boolean CheckAuthNum(String email,String authNum){
        if(redisUtil.getData(authNum)==null){
            return false;
        }
        else if(redisUtil.getData(authNum).equals(email)){
            return true;
        }
        else{
            return false;
        }
    }

    //임의의 6자리 양수를 반환합니다.
    public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }

    //인증 번호를 html 형식으로 보냅니다.
    public String joinEmail(String email) {
        makeRandomNumber();
        String setFrom = "leesw1945@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
            "EduWithMe를 방문해주셔서 감사합니다." + 	//html 형식으로 작성
                "<br><br>" +
                "인증코드는 " + authNumber + " 입니다." +
                "<br>" +
                "5분내로 인증코드를 입력해주세요."; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    //이메일을 전송 메서드
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
        redisUtil.setDataExpire(Integer.toString(authNumber),toMail,60*5L);
    }

    // 임시 비밀번호 발급을 위한 이메일을 보내는 메서드
    public void sendTempPasswordEmail(String email) {
        makeRandomNumber();
        String setFrom = "leesw1945@gmail.com";
        String toMail = email;
        String title = "임시 비밀번호 발급을 위한 인증 코드입니다.";
        String content =
            "EduWithMe 임시 비밀번호 발급 인증 코드입니다." +
                "<br><br>" +
                "인증 코드는 " + authNumber + " 입니다." +
                "<br>" +
                "5분 내로 인증 코드를 입력해주세요.";

        mailSend(setFrom, toMail, title, content);
        redisUtil.setDataExpire(Integer.toString(authNumber), email, 60 * 5L);
    }

    public void sendTempPassword(String email, String tempPassword) {
        String setFrom = "leesw1945@gmail.com";
        String toMail = email;
        String title = "임시 비밀번호가 발급되었습니다.";
        String content =
            "EduWithMe 임시 비밀번호입니다." +
                "<br><br>" +
                "임시 비밀번호: " + tempPassword +
                "<br>" +
                "보안을 위해 로그인 후 비밀번호를 변경해주세요.";

        mailSend(setFrom, toMail, title, content);
    }

}