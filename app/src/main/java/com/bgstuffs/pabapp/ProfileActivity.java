package com.bgstuffs.pabapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends Fragment {

    // declaration of the views and others
    ImageView iv;
    TextView openWebpage;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;

    FirebaseUser user;
    String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile,container,false);

        // initialization of views
        iv = view.findViewById(R.id.ivProfile);
        openWebpage = view.findViewById(R.id.tvCommonProblems);
        openWebpage.setMovementMethod(LinkMovementMethod.getInstance());

        // getting information of a specific user
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        // when iv is clicked
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        openWebpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.drivespark.com/four-wheelers/2017/common-car-problems-and-solutions-simplified/articlecontent-pf79135-024390.html"));
                startActivity(browserIntent);
            }
        });



        return view;
    }

    private void selectImage(){
        final CharSequence[] items = {"Gallery","Cancel"};

        // in fragment we use getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"), SELECT_FILE);

                }
                else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }

            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if(requestCode==SELECT_FILE){
                Uri selectedImageUri = data.getData();
                iv.setImageURI(selectedImageUri);
            }
        }
    }
}
