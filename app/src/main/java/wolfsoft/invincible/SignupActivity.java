package wolfsoft.invincible;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SignupActivity extends AppCompatActivity {

private ImageView banar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Context context;


     //   banar1 = (ImageView)findViewById(R.id.banar1);

        forCircleImage(banar1, R.drawable.white);
        //Bitmap bitmap = StringToBitMap(imgv);



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
