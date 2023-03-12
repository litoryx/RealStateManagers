package com.example.realstatemanagers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    ImageView possPhto;
    TextView typeBien;
    TextView place;
    TextView surface;
    TextView nbre_piece;
    TextView nbre_bathroom;
    TextView nbre_bed;
    TextView detailVal;
    TextView description;
    AppCompatButton mButtonMod;
    private static final String ARG_PARAM1 = "param1";
    private GoogleMap map;
    private Possession mPossession;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Possession possession) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, possession);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPossession = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeBien = view.findViewById(R.id.detail_type);
        place = view.findViewById(R.id.detail_adr);
        description = view.findViewById(R.id.desc);
        possPhto = view.findViewById(R.id.imageView5);
        detailVal = view.findViewById(R.id.detail_val);
        surface = view.findViewById(R.id.surface);
        nbre_piece = view.findViewById(R.id.nbre_piece);
        nbre_bathroom = view.findViewById(R.id.nbre_bathroom);
        nbre_bed = view.findViewById(R.id.nbre_bed);
        mButtonMod = view.findViewById(R.id.buttonMod);
        String Package_Name = getContext().getPackageName();

        Uri uri = Uri.parse("android.resource://" + Package_Name + "/" + R.raw.videoclip);
        VideoView simpleVideoView = (VideoView) view.findViewById(R.id.simpleVideoView); // initiate a video view
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();

        if(mPossession != null) {
            typeBien.setText(mPossession.getType_bien());
            place.setText(mPossession.getAdr());
            description.setText(mPossession.getDesc());
            detailVal.setText(mPossession.getVal_bien());
            surface.setText(mPossession.getSurface());
            nbre_piece.setText(mPossession.getNbre_piece());
            nbre_bathroom.setText(mPossession.getStatut());
            nbre_bed.setText(mPossession.getId());

            Glide.with(possPhto.getContext()).load(mPossession.getPhto()).into(possPhto);

            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent intent = result.getData();
                                Possession possession = intent.getParcelableExtra("mPossMod");
                                typeBien.setText(possession.getType_bien());
                                place.setText(possession.getAdr());
                                description.setText(possession.getDesc());
                                detailVal.setText(possession.getVal_bien());
                                surface.setText(possession.getSurface());
                                nbre_piece.setText(possession.getNbre_piece());
                                nbre_bathroom.setText(possession.getStatut());
                                nbre_bed.setText(possession.getId());
                            }
                        }
                    });

            mButtonMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityIntent = new Intent(getContext(), UpdateActivity.class);
                    activityIntent.putExtra("mId", mPossession.getId());
                    someActivityResultLauncher.launch(activityIntent);
                }
            });
        }
    }
}