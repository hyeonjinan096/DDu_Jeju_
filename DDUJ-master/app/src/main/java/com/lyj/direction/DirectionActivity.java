package com.lyj.direction;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.gson.reflect.TypeToken;
import com.lyj.direction.R;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.gson.Gson;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyj.direction.Adapter.DirectionStepAdapter;
import com.lyj.direction.Model.DirectionLegModel;
import com.lyj.direction.Model.DirectionResponseModel;
import com.lyj.direction.Model.DirectionRouteModel;
import com.lyj.direction.Model.DirectionStepModel;
import com.lyj.direction.Model.GoogleResponseModel;
import com.lyj.direction.databinding.ActivityDirectionBinding;
import com.lyj.direction.databinding.BottomSheetLayoutBinding;
import com.lyj.direction.jeju_setplan2.ItemModel;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback,DurationCallback {
    @Override
    public void onDurationReceived(double duration) {
       // calculate(duration);
    }


    double mindur=1000000000;
    private Polyline[] p=new Polyline[5];
    List<Double>plz=new ArrayList<>();
    List<LatLng> plz2 = new ArrayList<>();
    private LatLng a=new LatLng(33.4868,126.3918);
    private LatLng b=new LatLng(33.2102,126.2569);
    private LatLng c=new LatLng(33.5054,	126.5384);
    private LatLng d=new LatLng(33.2534,	126.4288);
    List<com.google.android.gms.maps.model.LatLng> totalLtLg=new ArrayList<>();


   int index=0;
   private List<LatLng>optimal= new ArrayList<>();

    private double durationSum=0;
    private double distanceSum=0;
    private double testingMindur=0;
    private double temp=0;
    private ActivityDirectionBinding binding;
    private GoogleMap mGoogleMap;
    private AppPermissions appPermissions;
    private boolean isLocationPermissionOk=true,isTrafficEnable;    //원래 false
    private BottomSheetBehavior<RelativeLayout> bottomSheetBehavior;
    private BottomSheetLayoutBinding bottomSheetLayoutBinding;
    private RetrofitAPI retrofitAPI;
    private LoadingDialog loadingDialog;
    private Location currentLocation;
    private List<LatLng>totalPlaces=null ;

    private Double endLat,endLng;
    private Double startLat,startLng;
    private String placeId;
    private String currMode="transit";
    LatLng startltlg;
    LatLng endltlg;
    List<LatLng> totalstepList= new ArrayList<>(); ;
    SupportMapFragment mapFragment;
    SearchView searchView;
    SearchView searchView2;
    Button btn;
    AutocompleteSupportFragment autocompleteFragment;
    AutocompleteSupportFragment autocompleteFragment2;
    private LatLng recent=null;
    private DirectionStepAdapter adapter;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private PlacesClient placesClient;
    ArrayList<ItemModel> dataList;
    private SharedPreferences sharedPreferences;
    Polyline polyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("dataList", "defaultValue");
        Log.d("지도에서 확인",json);
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            dataList= gson.fromJson(json, new TypeToken<ArrayList<ItemModel>>(){}.getType());
            // 데이터 사용
        }

        for (ItemModel item : dataList) {
            Log.d("지도MainActivity", "Item: " + item.getName() + ", Quantity: " + item.getType());
            int a = item.getType();
            String b=item.getX();
            String c=item.getY();
            totalLtLg.add(new LatLng(Double.parseDouble(b),Double.parseDouble(c)));

        }





        binding = ActivityDirectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.directionMap);

       // totalLtLg.add(a);
      //  totalLtLg.add(b);
     //   totalLtLg.add(c);
     // totalLtLg.add(d);

        //------------자동완성 검색
        /*
         Places.initialize(getApplicationContext(), "AIzaSyByRMe8nJ5cp0bKSKcpQLM5URiu07U1DvQ");
        placesClient=Places.createClient(this);
        // AutocompleteSupportFragment 생성
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.txtStartLocation);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getLatLng());
                startLat=place.getLatLng().latitude;
                startLng=place.getLatLng().longitude;
                startltlg=new LatLng(startLat,startLng);
                mGoogleMap.addMarker(new MarkerOptions().position( startltlg).title(place.getName()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( startltlg,12));
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });


        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.txtEndLocation);
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getLatLng());
                endLat=place.getLatLng().latitude;
                endLng=place.getLatLng().longitude;
                endltlg=new LatLng(endLat,endLng);
                mGoogleMap.addMarker(new MarkerOptions().position( endltlg).title(place.getName()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( endltlg,12));
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        */


    //--------------길찾기 버튼
    /*    btn=findViewById(R.id.roadBtn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(startLat!=null&&startLng!=null&&endLat!=null&&endLng!=null) {
                    getDirection(currMode, startLat, startLng, endLat, endLng,Color.RED);

                }
            }
        });*/



        placeId = getIntent().getStringExtra("placeId");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appPermissions = new AppPermissions();
        loadingDialog = new LoadingDialog(this);

        retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);

        bottomSheetLayoutBinding = binding.bottomSheet;
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayoutBinding.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        adapter = new DirectionStepAdapter();

        bottomSheetLayoutBinding.stepRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomSheetLayoutBinding.stepRecyclerView.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.directionMap);

        mapFragment.getMapAsync(this);


        binding.enableTraffic.setOnClickListener(view -> {
            if (isTrafficEnable) {
                if (mGoogleMap != null) {
                    mGoogleMap.setTrafficEnabled(false);
                    isTrafficEnable = false;
                }
            } else {
                if (mGoogleMap != null) {
                    mGoogleMap.setTrafficEnabled(true);
                    isTrafficEnable = true;
                }
            }
        });

      /*  binding.travelMode.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {

            public void onCheckedChanged(ChipGroup group, List<Integer>checkedIds) {
                if (!checkedIds.isEmpty()) {
                    for(int checkedId:checkedIds) {
                        switch (checkedId) {
                            case R.id.btnChipDriving:
                                getDirection("driving", startLat, startLng, endLat, endLng,Color.RED);
                                currMode="driving";
                                break;
                            case R.id.btnChipWalking:
                                getDirection("walking", startLat, startLng, endLat, endLng,Color.RED);
                                currMode="walking";
                                break;
                            case R.id.btnChipBike:
                                getDirection("bicycling", startLat, startLng, endLat, endLng,Color.RED);
                                currMode="bicycling";
                                break;
                            case R.id.btnChipTrain:
                                getDirection("transit", startLat, startLng, endLat, endLng,Color.RED);
                                currMode="transit";
                                break;
                        }
                    }
                    }
                }

        });*/
        TEST();
    }
    private void getDirection(String mMode,Double startLat,Double startLng,Double endLat,Double endLng,int clr) {
        boolean alt=false;
        if (isLocationPermissionOk) {

            loadingDialog.startLoading();
         String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                // "alternatives="+alt+
                 "&origin=" + startLat+","+startLng+
                   "&destination=" +endLat+","+endLng+
               //  "&waypoints="+"optimize:"+true+"|"+d.latitude+","+d.longitude+
                    "&mode="+mMode+
                    "&key=" + "AIzaSyByRMe8nJ5cp0bKSKcpQLM5URiu07U1DvQ";
            //departuretime 설정 가능*/ //default는 now
             //transit_routing_preference = less_walking, fewer_transfers*/


            retrofitAPI.getDirection(url).enqueue(new Callback<DirectionResponseModel>() {
                @Override
                public void onResponse(Call<DirectionResponseModel> call, Response<DirectionResponseModel> response) {
                    Gson gson = new GsonBuilder().create();
                    String res = gson.toJson(response.body());
                    Log.d("TAG", "onResponse: " + res);

                    if (response.errorBody() == null) {
                        if (response.body() != null) {
                            clearUI();
                            Log.d("hi",String.valueOf(response.raw()));
                            if (response.body().getDirectionRouteModels().size() > 0) {
                                DirectionRouteModel routeModel = response.body().getDirectionRouteModels().get(0);
                                getSupportActionBar().setTitle(routeModel.getSummary());

                                DirectionLegModel legModel = routeModel.getLegs().get(0);
                                // binding.txtStartLocation.setText(legModel.getStartAddress());
                                // binding.txtEndLocation.setText(legModel.getEndAddress());


                                // bottomSheetLayoutBinding.txtSheetTime.setText(legModel.getDuration().getText());
                                //  bottomSheetLayoutBinding.txtSheetDistance.setText(legModel.getDistance().getText());

                                durationSum += legModel.getDuration().getValue();
                                distanceSum += legModel.getDistance().getValue();
                                double min = durationSum / 60 % 60;
                                durationSum /= 3600;
                                distanceSum /= 100;
                                bottomSheetLayoutBinding.txtSheetTime.setText(String.valueOf((int) durationSum) + "시간" + String.valueOf((int) min) + "분");
                                bottomSheetLayoutBinding.txtSheetDistance.setText(String.valueOf((int) distanceSum) + "km");
                               /* mGoogleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(legModel.getEndLocation().getLat(), legModel.getEndLocation().getLng()))
                                        .title("End Location"));

                                mGoogleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(legModel.getStartLocation().getLat(), legModel.getStartLocation().getLng()))
                                        .title("Start Location"));*/
                                /*좌표 찍기*/
                                for (int i = 0; i < totalLtLg.size(); i++) {
                                    mGoogleMap.addMarker(new MarkerOptions()
                                            .position(totalLtLg.get(i))
                                            .title(i + "번째"));
                                }

                                adapter.setDirectionStepModels(legModel.getSteps());


                                List<LatLng> stepList = new ArrayList<>();
                                ;

                                PolylineOptions options = new PolylineOptions()
                                        .width(15)
                                        // .addAll(totalLatLn)
                                        .color(clr)//R.color.line
                                        .geodesic(true)
                                        .clickable(true)
                                        .visible(true);

                                List<PatternItem> pattern;
                                if (mMode.equals("walking")) {
                                    pattern = Arrays.asList(
                                            new Dot(), new Gap(10));

                                    options.jointType(JointType.ROUND);
                                } else {
                                    pattern = Arrays.asList(
                                            new Dash(30));
                                }

                                options.pattern(pattern);

                                if (legModel.getSteps() != null) {
                                    for (DirectionStepModel stepModel : legModel.getSteps()) {

                                        List<com.google.maps.model.LatLng> decodedLatLng = decode(stepModel.getPolyline().getPoints());

                                        for (com.google.maps.model.LatLng latLng : decodedLatLng) {
                                            stepList.add(new LatLng(latLng.lat, latLng.lng));

                                                totalstepList.add(new LatLng(latLng.lat, latLng.lng));

                                        }
                                    }
                                }


                              //  options.addAll(stepList);
                                options.addAll(totalstepList);
                                if(polyline!=null){
                                    polyline.remove();
                                }
                                polyline=mGoogleMap.addPolyline(options);



                              // polyline = mGoogleMap.addPolyline(options.add(new LatLng(startLat,startLng), new LatLng(endLat,endLng)));
                               // mGoogleMap.addPolyline(options.add(new LatLng(totalLatLng[0][0], totalLatLng[0][1]), new LatLng(totalLatLng[1][0], totalLatLng[1][1])));
                               // mGoogleMap.addPolyline(options.add(new LatLng(totalLatLng[1][0], totalLatLng[1][1]), new LatLng(totalLatLng[2][0], totalLatLng[2][1])));
                                LatLng startLocation = new LatLng(legModel.getStartLocation().getLat(), legModel.getStartLocation().getLng());
                                LatLng endLocation = new LatLng(legModel.getStartLocation().getLat(), legModel.getStartLocation().getLng());


                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(b,c), 2));//startLocation, endLocation, 17


                            } else {
                                Toast.makeText(DirectionActivity.this, "경로가 없습니다1", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DirectionActivity.this, "경로가 없습니다2", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("TAG", "onNullResponse: " + response);
                    }

                    loadingDialog.stopLoading();
                }

                @Override
                public void onFailure(Call<DirectionResponseModel> call, Throwable t) {

                }
            });
        }


    }
private void clearUI(){
        mGoogleMap.clear();
       // binding.txtStartLocation.setText("");
       // binding.txtEndLocation.setText("");
        getSupportActionBar().setTitle("");
        bottomSheetLayoutBinding.txtSheetDistance.setText("");
        bottomSheetLayoutBinding.txtSheetTime.setText("");
}
@Override
public void onMapReady(GoogleMap googleMap){
    mGoogleMap=googleMap;
    if(appPermissions.isLocationOk(this)){
        if(startLng!=null&&startLat!=null&&endLng!=null&&endLat!=null) {
            setupGoogleMap();
        }
    }else{
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this).setTitle("Location Permission")
                    .setMessage("required permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            appPermissions.requestLocationPermission(DirectionActivity.this);
                        }
                    }).create().show();
        }
        else{
            appPermissions.requestLocationPermission(DirectionActivity.this);
        }
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==AllConstant.LOCATION_REQUEST_CODE){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                isLocationPermissionOk=true;
                setupGoogleMap();
            }else{
                isLocationPermissionOk=false;
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setupGoogleMap(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        //getCurrentLocation();
       // getDirection("driving",startLat.toString(),startLng.toString(),endLat.toString(),endLng.toString());
    }
    private void getCurrentLocation(){
        FusedLocationProviderClient fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    //getDirection("driving");
                }
                else{
                    Toast.makeText(DirectionActivity.this,"location not find",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed(){
        if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else{

        }
        super.onBackPressed();
    }
    private List<com.google.maps.model.LatLng> decode(String points){
        int len = points.length();

        final List<com.google.maps.model.LatLng> path = new ArrayList<>(len / 2);
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = points.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = points.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new com.google.maps.model.LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
    }
/*
void AutoSearch(AutocompleteSupportFragment autocompleteFragment,  Double lat, Double lng) {
    autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));
    autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(@NonNull Place place) {
            // TODO: Get info about the selected place.
            Log.i("TAG", "Place: " + place.getName() + ", " + place.getLatLng());
        }

        @Override
        public void onError(@NonNull Status status) {
            // TODO: Handle the error.
            Log.i("TAG", "An error occurred: " + status);
        }
    });

}*/
void TEST() {
     List<Double> plz = new ArrayList<>();
  // 최악의 루트
   // getDirection("transit",a.latitude,a.longitude,d.latitude,d.longitude,Color.RED);
 //   getDirection("transit", d.latitude, d.longitude, c.latitude, c.longitude,Color.RED);
  //  getDirection("transit", c.latitude, c.longitude, b.latitude, b.longitude,Color.RED);
   // getDirection("transit", b.latitude, b.longitude, a.latitude, a.longitude,Color.RED);


   //getDirection("transit",a.latitude,a.longitude,c.latitude,c.longitude,Color.RED);    //이상적
    //getDirection("transit", c.latitude, c.longitude, d.latitude, d.longitude,Color.RED);
    //getDirection("transit",d.latitude, d.longitude, b.latitude, b.longitude,Color.RED);
    //getDirection("transit",b.latitude, b.longitude, a.latitude, a.longitude,Color.RED);*/

    temp=0;


 //   getTime(a.latitude,a.longitude,d.latitude,d.longitude);
 //  getTime(d.latitude,d.longitude,c.latitude,c.longitude);
 //   getTime(c.latitude,c.longitude,b.latitude,b.longitude);//최장

    Log.i("으아","temp="+temp);

    List<List<LatLng>> permutations = new ArrayList<>();
    boolean[] visited = new boolean[totalLtLg.size()];
    List<LatLng> currentPermutation = new ArrayList<>();
    LatLng[] A=totalLtLg.toArray(new LatLng[0]);
    permute(A, visited, currentPermutation, permutations);  //permutations 안에 있나..
    //Log.i("per","per"+permutations);
    for(int i=0;i<permutations.size()-1;i++){
        for(int j=0;j<permutations.get(0).size()-1;j++){
          Log.i("I,J","="+i+"+"+j);
        getTime(permutations.get(i).get(j).latitude,permutations.get(i).get(j).longitude,permutations.get(i).get(j+1).latitude,permutations.get(i).get(j+1).longitude);
    }
    }
   // temp =0;
  //  plz.clear();

   // getTime(a.latitude,a.longitude,c.latitude,c.longitude);
   // getTime(c.latitude,c.longitude,d.latitude,d.longitude);
  //  getTime(d.latitude,d.longitude,b.latitude,b.longitude); //최단


    /*new DurationCallback() {
        @Override
        public void onDurationReceived(double duration) {
            plz.add(duration);
        }
    });*/


   /* for(int i=0;i<optimal.size()-1;i++){
        LatLng[] n=optimal.toArray(new LatLng[optimal.size()+1]);
        getDirection("transit",n[i].latitude,n[i].longitude,n[i+1].latitude,n[i+1].longitude,Color.RED );

    }*/

}
    void getTime(double aLat, double aLng, double bLat, double bLng){
    if (isLocationPermissionOk) {
        loadingDialog.startLoading();
        //final double[] duration = {0};
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                // "alternatives="+alt+
                "&origin=" + aLat + "," + aLng +
                "&destination=" + bLat + "," + bLng +
                //  "&waypoints="+"optimize:"+true+"|"+d.latitude+","+d.longitude+
                "&mode=" + "transit" +
                "&key=" + "AIzaSyByRMe8nJ5cp0bKSKcpQLM5URiu07U1DvQ";

        retrofitAPI.getDirection(url).enqueue(new Callback<DirectionResponseModel>() {
            @Override
            public void onResponse(Call<DirectionResponseModel> call, Response<DirectionResponseModel> response) {
                Gson gson = new GsonBuilder().create();
                String res = gson.toJson(response.body());
                Log.d("TAG", "onResponse: " + res);

                if (response.errorBody() == null) {
                    if (response.body() != null) {
                        clearUI();
                        Log.d("hi",String.valueOf(response.raw()));
                        if (response.body().getDirectionRouteModels().size() > 0) {
                            DirectionRouteModel routeModel = response.body().getDirectionRouteModels().get(0);
                            getSupportActionBar().setTitle(routeModel.getSummary());

                            DirectionLegModel legModel = routeModel.getLegs().get(0);
                            double duration = legModel.getDuration().getValue();
                           // callback.onDurationReceived(duration);


                            plz.add(duration);//시간
                            Log.i("시도","dur"+duration);
                            LatLng newnew=new LatLng(aLat,aLng);//출발지

                                plz2.add(newnew);

                            LatLng newnew2=new LatLng(bLat,bLng);//도착지

                                plz2.add(newnew2);



                            for(int i=0;i<plz.size()-2;i+=totalLtLg.size()-1){
                                double temp=plz.get(i)+plz.get(i+1)+plz.get(i+2);
                                if(temp<mindur){
                                    mindur=temp;
                                    optimal.clear();
                                    for(int j=i*2;j<(i+totalLtLg.size()-1)*2;j++) {
                                        if (!optimal.contains(plz2.get(j))) {
                                            optimal.add(plz2.get(j));
                                        }
                                    }
                                }
                            }

                            for(int i=0;i<optimal.size()-1;i++){
                               if(polyline!=null){
                                   polyline.remove();
                                }
                                LatLng[] n=optimal.toArray(new LatLng[optimal.size()+1]);
                                getDirection("transit",n[i].latitude,n[i].longitude,n[i+1].latitude,n[i+1].longitude,Color.RED );

                            }


                        } else {
                            Toast.makeText(DirectionActivity.this, "경로가 없습니다1", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DirectionActivity.this, "경로가 없습니다2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "onNullResponse: " + response);
                }

                loadingDialog.stopLoading();
            }

            @Override
            public void onFailure(Call<DirectionResponseModel> call, Throwable t) {
            }
        });
    }

}
    private static void permute(LatLng[] totalLtLng, boolean[] visited, List<LatLng> currentPermutation, List<List<LatLng>> permutations) {
        if (currentPermutation.size() == totalLtLng.length) {
            permutations.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (int i = 0; i < totalLtLng.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                currentPermutation.add(totalLtLng[i]);
                permute(totalLtLng, visited, currentPermutation, permutations);
                currentPermutation.remove(currentPermutation.size() - 1);
                visited[i] = false;
            }
        }
    }


}