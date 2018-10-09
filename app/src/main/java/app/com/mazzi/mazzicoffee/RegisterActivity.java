package app.com.mazzi.mazzicoffee;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText UNresbox,PWresbox;
    String user_name,user_pass;
    Button resbutton;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UNresbox=(EditText)findViewById(R.id.UNresbox);
        PWresbox=(EditText)findViewById(R.id.PWresbox);
        resbutton=(Button)findViewById(R.id.resbutton);
        resbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_name = UNresbox.getText().toString().trim();
                        user_pass = PWresbox.getText().toString().trim();
                        DatabaseOperations dop = new DatabaseOperations(RegisterActivity.this);
                        SQLiteDatabase SQL=dop.getWritableDatabase();
                        String[] columns={DatabaseContract.Info.USER_NAME,DatabaseContract.Info.USER_PASS};
                        Cursor cs=SQL.query(DatabaseContract.Info.TABLE_NAME,columns,null,null,null,null,null);
                        cs.moveToFirst();
                        try {
                            int user_nameColumnIndex = cs.getColumnIndex(DatabaseContract.Info.USER_NAME);
                            int user_passColumnIndex = cs.getColumnIndex(DatabaseContract.Info.USER_PASS);
                            while (cs.moveToNext()) {
                                String currentuser_name = cs.getString(user_nameColumnIndex);
                                String currentuser_pass = cs.getString(user_passColumnIndex);
                                if (user_name!=currentuser_name) {
                                    if (user_name.length()!=0&&user_pass.length()!=0) {
                                        dop.putInformation(dop,user_name,user_pass);
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else if(user_name.length()==0&&user_pass.length()==0){
                                        Toast.makeText(RegisterActivity.this, "Vui lòng điền tài khoản và mật khẩu", Toast.LENGTH_LONG).show();
                                    }else if(user_name.length()==0){
                                        Toast.makeText(RegisterActivity.this, "Vui lòng điền tài khoản", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Vui lòng điền mật khẩu", Toast.LENGTH_LONG).show();
                                    }
                                } else if(user_name.equals(currentuser_name)){
                                    dop.putInformation(dop,user_name,user_pass);
                                    Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                    UNresbox.setText("");
                                    PWresbox.setText("");
                                }
                            }
                        }finally {
                            cs.close();
                        }
                }
            });
        }
}
