package top.putileaf.service;

public interface MailCodeService {

    boolean codeIsHave(String username);

    void  sendCodeMail( String username,String email);

    boolean checkCode(String username, String code);
}