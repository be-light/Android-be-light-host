package com.example.a1117p.osam.host;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    
         new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    final String html = RequestHttpURLConnection.request("http://121.184.10.219/api/hoster",null,true,"GET");
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObj = (JSONObject) jsonParser.parse(html);
                    final JSONObject jsonObj2 = (JSONObject) jsonObj.get("user");
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            
                            ((TextView)findViewById(R.id.profile)).setText(html);
                            ((TextView)findViewById(R.id.id)).setText((String)jsonObj2.get("hostUserId"));
                            ((TextView)findViewById(R.id.name)).setText((String)jsonObj2.get("hostUserName"));
                            ((EditText)findViewById(R.id.email)).setText((String)jsonObj2.get("hostUserEmail"));
                            ((EditText)findViewById(R.id.phone)).setText((String)jsonObj2.get("hostUserPhoneNumber"));
                         }

                    });
                }
                catch(final Exception e){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {

                            Toast.makeText(ProfileActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            finish();
                        }

                    });
                }
            }
        }).start();
        findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
                String email = ((EditText)findViewById(R.id.email)).getText().toString();
                String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
                if(email.equals("")){
                    Toast.makeText(ProfileActivity.this,"이메일을 입력하세요",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(phone.equals("")){
                    Toast.makeText(ProfileActivity.this,"전화번호를 입력하세요",Toast.LENGTH_LONG).show();
                    return;
                }
                final HashMap params = new HashMap<String, String>(); 

                params.put("hostUserEmail",email);
                params.put("hostUserPhoneNumber",phone);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        final String html = RequestHttpURLConnection.request("http://121.184.10.219/api/hoster?_method=PUT",params,true,"POST");
                        runOnUiThread(new Runnable(){
                            
                            @Override
                            public void run() {
                                
                                Toast.makeText(ProfileActivity.this,html,Toast.LENGTH_LONG).show();
                            }
                            
                        });
                        
                    }
                }).start();
            }
        });
    }

    

}
