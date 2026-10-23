
package ai.novalens.free;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final int REQUEST_CAMERA = 101;
    private static final int REQUEST_GALLERY = 102;
    private ImageView preview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 36, 24, 24);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(0xFF101318);
        scroll.addView(root);

        TextView title = new TextView(this);
        title.setText("NovaLens AI");
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(32);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subtitle = new TextView(this);
        subtitle.setText("Free camera and photo preview");
        subtitle.setTextColor(0xFFB9C1CE);
        subtitle.setTextSize(15);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subtitleParams.setMargins(0, 8, 0, 24);
        root.addView(subtitle, subtitleParams);

        Button camera = new Button(this);
        camera.setText("Open Camera");
        root.addView(camera, fullWidth());
        camera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        });

        Button gallery = new Button(this);
        gallery.setText("Choose Photo");
        root.addView(gallery, fullWidth());
        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_GALLERY);
        });

        preview = new ImageView(this);
        preview.setAdjustViewBounds(true);
        preview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams previewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 700);
        previewParams.setMargins(0, 24, 0, 0);
        root.addView(preview, previewParams);

        TextView note = new TextView(this);
        note.setText("Starter version only. AI zoom and enhancement are not included yet.");
        note.setTextColor(0xFFB9C1CE);
        note.setTextSize(13);
        note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noteParams.setMargins(0, 20, 0, 0);
        root.addView(note, noteParams);

        setContentView(scroll);
    }

    private LinearLayout.LayoutParams fullWidth() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 6, 0, 6);
        return params;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        if (requestCode == REQUEST_CAMERA) {
            Object image = data.getExtras() != null ? data.getExtras().get("data") : null;
            if (image instanceof Bitmap) {
                preview.setImageBitmap((Bitmap) image);
            } else {
                Toast.makeText(this, "Camera returned no preview image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_GALLERY) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    Bitmap bitmap;
                    if (Build.VERSION.SDK_INT >= 28) {
                        bitmap = ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(getContentResolver(), uri));
                    } else {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    }
                    preview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Could not open this photo", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
