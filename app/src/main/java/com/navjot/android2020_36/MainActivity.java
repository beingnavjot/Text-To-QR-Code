package com.navjot.android2020_36;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;
    Bitmap bitmap;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        imageView=findViewById(R.id.imageView);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = editText.getText().toString();

                try {
                    bitmap = TextToImageEncode(url);

                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }

            private Bitmap TextToImageEncode(String url) throws WriterException
            {
                BitMatrix bitMatrix;
                try {
                    bitMatrix = new MultiFormatWriter().encode(
                            url,
                            BarcodeFormat.DATA_MATRIX.QR_CODE,
                            QRcodeWidth, QRcodeWidth, null
                    );

                } catch (IllegalArgumentException Illegalargumentexception) {

                    return null;
                }

                int bitMatrixWidth = bitMatrix.getWidth();

                int bitMatrixHeight = bitMatrix.getHeight();

                int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

                for (int y = 0; y < bitMatrixHeight; y++) {
                    int offset = y * bitMatrixWidth;

                    for (int x = 0; x < bitMatrixWidth; x++) {

                        pixels[offset + x] = bitMatrix.get(x, y) ?
                                getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.colorW);
                    }
                }


                Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

                bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
                return bitmap;

            }
        });


    }


}
