package com.example.realstatemanagers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.realstatemanagers.viewmodel.DetailViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener{

    ImageView possPhto;
    TextView typeBien;
    TextView place;
    TextView surface;
    TextView nbre_piece;
    TextView nbre_bathroom;
    TextView nbre_bed;
    TextView detailVal;
    TextView description;
    Button mButtonGal;
    AppCompatButton mButtonMod;
    FloatingActionButton mButtonBack;
    private static final String ARG_PARAM1 = "param1";
    private GoogleMap map;
    private Possession mPossession;
    DetailViewModel viewModel;
    String Package_Name = null;

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
        mButtonGal = view.findViewById(R.id.buttonGal);
        mButtonBack = view.findViewById(R.id.buttonBack);

        viewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(DetailViewModel.class);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapDetail);

        assert fragment != null;
        fragment.getMapAsync( this);

        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );

        if(getContext() != null) {
            Package_Name = getContext().getPackageName();
        }

        Uri uri = Uri.parse("android.resource://" + Package_Name + "/" + R.raw.videoclip);
        VideoView simpleVideoView = (VideoView) view.findViewById(R.id.simpleVideoView); // initiate a video view
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();

        if(mPossession != null) {
            typeBien.setText(mPossession.getType_bien());
            place.setText(mPossession.getAdr());
            description.setText(mPossession.getDesc());
            detailVal.setText(mPossession.getVal_bien());
            String sur = String.valueOf(mPossession.getSurface());
            surface.setText(sur);
            nbre_piece.setText(mPossession.getNbre_piece());
            nbre_bathroom.setText(mPossession.getStatut());
            nbre_bed.setText(mPossession.getId());

            Uri selectedImage = Uri.parse(mPossession.getPhto());

            Glide.with(possPhto.getContext()).load(selectedImage).into(possPhto);

            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
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
                    });

            mButtonGal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Créer un Intent avec une action  ACTION_PICK
                    Intent i =new Intent();
                    //Définissez le type comme image/*.
                    //Cela garantit que seuls les composants de type image sont sélectionnés
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    //Nous passons un tableau supplémentaire avec les types MIME acceptés.
                    //Cela garantira que seuls les composants avec ces types MIME sont ciblés.
                    String[] mimeTypes = {"image/jpeg", "image/png","image/jpg"};
                    i.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                    //Lancer l'Intent
                    startActivityForResult(Intent.createChooser(i,"Select Picture"),1);
                }
            });

            mButtonMod.setOnClickListener(v -> {
                Intent activityIntent = new Intent(getContext(), UpdateActivity.class);
                activityIntent.putExtra("mId", mPossession.getId());
                someActivityResultLauncher.launch(activityIntent);
            });
        }
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        //Le code de résultat est RESULT_OK uniquement si l'utilisateur sélectionne une image.
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == 1) {//data.getData renvoie l'URI de contenu pour l'image sélectionnée
                Uri selectedImage = data.getData();
                String selectimageString = selectedImage.toString();

                Log.d("verfiImg", "" + selectimageString);
                mPossession.setPhto(selectimageString);
                viewModel.updatePhoto(mPossession);
                possPhto.setImageURI(selectedImage);
                //Définir l'image dans ImageView après le décodage de la chaîne
            }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if(clickCount != null){marker.getTitle();}

        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        float zoomLevel = 15f;
        String adr;
        adr = mPossession.getAdr();
        if (adr == null) {
            adr = "6 rue pelleport";
        }
        LatLng mCoord = getLocationFromAddress(getContext(), adr);

            map.addMarker(new MarkerOptions()
                    .position(mCoord)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mCoord, zoomLevel));
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.refresh();
    }
}