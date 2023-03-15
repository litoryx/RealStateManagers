package com.example.realstatemanagers;

import android.location.Location;

import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.model.google.Place;
import com.example.realstatemanagers.repository.AutoCompleteRepository;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;
import com.example.realstatemanagers.viewmodel.GeoViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GeoViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LocationRepository locationRepository = mock(LocationRepository.class);
    private final PossessionRepository possessionRepository = mock(PossessionRepository.class);
    private final AutoCompleteRepository autoRepository = mock(AutoCompleteRepository.class);
    private final PermissionChecker permRepository = mock(PermissionChecker.class);

    private GeoViewModel geoViewModel;

    @Before
    public void setup(){
        MutableLiveData<Location> locationLivedata = new MutableLiveData<>(createLocation(1.0, 0.0));
        when(locationRepository.getLocationLiveDataFormatLoc()).thenReturn(locationLivedata);

        List<Possession> possesionsTest = new ArrayList<>();
        Possession possession1 = new Possession("052",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025",350,"9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22",null,
                "045");
        possesionsTest.add(possession1);

        MutableLiveData<List<Possession>> possessionTest = new MutableLiveData<>(possesionsTest);
        when(possessionRepository.getPossession()).thenReturn(possessionTest);

        geoViewModel = new GeoViewModel(locationRepository,  permRepository, possessionRepository, autoRepository);

        verify(locationRepository).getLocationLiveData();
        verify(possessionRepository).getPossession();
    }

    @Test
    public void testGeoViewModel(){
        geoViewModel.getUserLocation();
    }


    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}
