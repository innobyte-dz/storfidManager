package wolfsoft.invincible;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import wolfsoft.invincible.model.User;

public class LoginActivity extends AppCompatActivity {

private ImageView banar1;
private Button btn_login;
private customfonts.MyTextView btn_new_user;
    private TextView mTextResult;

    private EditText mTextUsername;
    private EditText mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextResult = (TextView)findViewById(R.id.txt_result);
        mTextUsername = (EditText) findViewById(R.id.username);
        mTextPassword = (EditText) findViewById(R.id.password);


        Bundle extras = getIntent().getExtras();
        String userName;
        boolean saved = false;

        if (extras != null) {
            if(saved = extras.getBoolean("saved")) {
                userName = extras.getString("username");
                mTextResult.setText("Nouveau utilisateur enregistré." +
                        "" +
                        ""+userName);
            }else {
                mTextResult.setText("");
            }
            // and get whatever type user account id is
        }

        btn_new_user = (customfonts.MyTextView) findViewById(R.id.btn_new_user);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        btn_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewUserActivity.class);
                startActivity(intent);
            }
        });
        final Context context;
     //   banar1 = (ImageView)findViewById(R.id.banar1);

        forCircleImage(banar1, R.drawable.white);
        //Bitmap bitmap = StringToBitMap(imgv);

}

    private void performLogin() {


        boolean cancel = false;
        String username = mTextUsername.getText().toString();
        String password = mTextPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            mTextUsername.setError(getString(R.string.error_field_required));
            cancel = true;
            return;
        }
        if(TextUtils.isEmpty(password)){
            mTextPassword.setError(getString(R.string.error_field_required));
            cancel = true;
            return;
        }

        // TREMP METHOD
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        /*

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.


        } else {

            // Perform login here
            User u = new User(  );
            for (User user:u.listAll(u.getClass()))
            {
               if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                   Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(intent);
               }
            }

            mTextResult.setText("Utilisateur ou mot de passe incorrecte !");

        }

*/

    }

    private void forCircleImage(ImageView imageView, int image){

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                image);

        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

     //   imageView.setImageBitmap(circleBitmap);

    }
}
