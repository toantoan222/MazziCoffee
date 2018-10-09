package app.com.mazzi.mazzicoffee;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.com.mazzi.mazzicoffee.DatabaseContract.Info;
public class LoginActivity extends AppCompatActivity {
    TextView res;
    Button loginbutton;
    String username,password;
    EditText UNbox,PWbox;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        res=(TextView)findViewById(R.id.res);
        loginbutton=(Button)findViewById(R.id.loginbutton);
        UNbox=(EditText)findViewById(R.id.UNbox);
        PWbox=(EditText)findViewById(R.id.PWbox);
        res.setClickable(true);
        res.setMovementMethod(LinkMovementMethod.getInstance());
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = UNbox.getText().toString().trim();
                password = PWbox.getText().toString().trim();
                DatabaseOperations dop = new DatabaseOperations(LoginActivity.this);
                SQLiteDatabase SQL=dop.getReadableDatabase();
                String[] columns={Info.USER_NAME,Info.USER_PASS};
                Cursor cs=SQL.query(Info.TABLE_NAME,columns,null,null,null,null,null);
                cs.moveToFirst();
                String NAME = "";
                boolean login_status=false;
                try {
                    int usernameColumnIndex = cs.getColumnIndex(Info.USER_NAME);
                    int passwordColumnIndex = cs.getColumnIndex(Info.USER_PASS);
                    while (cs.moveToNext()) {
                        String currentusername = cs.getString(usernameColumnIndex);
                        String currentpassword = cs.getString(passwordColumnIndex);
                        if (username.length() != 0 && password.length() != 0) {
                            if (username.equals(currentusername) && password.equals(currentpassword)) {
                                NAME = cs.getString(0);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                login_status=true;
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else if (username.length() == 0 && password.length() == 0) {
                            Toast.makeText(LoginActivity.this, "Vui lòng điền tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                        } else if (username.length() == 0) {
                            Toast.makeText(LoginActivity.this, "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Vui lòng điền mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }finally {
                    cs.close();
                }
            }
        });
    }
}
