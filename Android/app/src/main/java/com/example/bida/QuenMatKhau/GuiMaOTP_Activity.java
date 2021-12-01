package com.example.bida.QuenMatKhau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.bida.DangKy.GuiMaOTP_DangKyActivity;
import com.example.bida.DangKy.NhapThongTinDK_Activity;
import com.example.bida.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class GuiMaOTP_Activity extends AppCompatActivity {
    public String Number_entered_by_user,code_by_system;
    Button verify;
    TextView resend;
    PinView otp;
    public FirebaseAuth mAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guima_otp);
        Intent intent = getIntent();
        Number_entered_by_user = intent.getStringExtra("number");


        verify = (Button) findViewById(R.id.verifybutton);
        resend= (TextView) findViewById(R.id.resend);
        send_code_to_user(Number_entered_by_user);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend_otp(Number_entered_by_user);
            }
        });

        otp= (PinView) findViewById(R.id.pinview);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_code();
            }
        });
    }

    private void resend_otp(String number_entered_by_user) {
        send_code_to_user(number_entered_by_user);
    }

    private void check_code() {
        String user_entered_otp = otp.getText().toString().trim();
        if (user_entered_otp.isEmpty() || user_entered_otp.length()<6){
            Toast.makeText(GuiMaOTP_Activity.this, "Sai OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        finish_everything(user_entered_otp);
    }
    private void send_code_to_user(String number_entered_by_user) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number_entered_by_user,
                60,
                TimeUnit.SECONDS,
                this,
                mCallback
        );
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            code_by_system=s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code !=null){
                finish_everything(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(GuiMaOTP_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };



    private void finish_everything(String code) {
        otp.setText(code);
        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(code_by_system,code);
        sign_in(credential);
    }

    private void sign_in(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(GuiMaOTP_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(GuiMaOTP_Activity.this, "Xác thực thành công !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuiMaOTP_Activity.this, ThayDoiMK_Activity.class);
//                                                            String s = edt_nhapSDT.getText().toString().trim();
//                                                            String s1 = s.substring(1);
//                                                            String sdt = "+84"+s1;
                    intent.putExtra("number",Number_entered_by_user);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(GuiMaOTP_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}
