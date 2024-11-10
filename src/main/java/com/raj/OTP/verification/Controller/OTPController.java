package com.raj.OTP.verification.Controller;

import com.raj.OTP.verification.Service.EmailService;
import com.raj.OTP.verification.Service.OTPService;
import com.raj.OTP.verification.TempStore.OTPStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/generate")
    public String generateAndSendOTP(@RequestParam("email") String mail){
        String otp = otpService.generateOTP();
        System.out.println(otp);
        emailService.sendOtpEmail(mail,otp);
        OTPStorage.storeOtp(mail,otp);
        return "OTP sent Successfully to "+mail;
    }

    @PostMapping("/verify")
    public String verification(@RequestParam("email") String mail,@RequestParam("otp") String otp){
        boolean isValid = OTPStorage.verifyOTP(mail,otp);
        return isValid ? "OTP verified successfully!" : "Invalid OTP, please try again.";
    }

}
