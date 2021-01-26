package com.example.coolweather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coolweather.db.City;
import com.example.coolweather.db.Country;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
  private static final String TAG = "ChooseAreaFragment";
  public static final int LEVEL_PROVINCE = 0;
  public static final int LEVEL_CITY = 1;
  public static final int LEVEL_COUNTRY = 2;
  private ProgressDialog mProgressDialog;
  private TextView mTitleView;
  private Button mBackButton;
  private ListView mAreaListView;
  private ArrayAdapter<String> mAdapter;
  private List<String> dataList = new ArrayList<>();

  private List<Province> mProvinceList;
  private List<City> mCityList;
  private List<Country> mCountryList;
  private Province selectedProvince;
  private City selectedCity;
  private int currentLevel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.choose_area, container, false);
    mBackButton = view.findViewById(R.id.back_button);
    mTitleView = view.findViewById(R.id.title_text);
    mAreaListView = view.findViewById(R.id.list_view);
    mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
    mAreaListView.setAdapter(mAdapter);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
          selectedProvince = mProvinceList.get(position);
          queryCites();
        } else if (currentLevel == LEVEL_CITY) {
          selectedCity = mCityList.get(position);
          queryCountries();
        }else if(currentLevel ==LEVEL_COUNTRY){
          String weatherId = mCountryList.get(position).getWeatherId();
          if(getActivity() instanceof MainActivity){
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            intent.putExtra("weather_id",weatherId);
            startActivity(intent);
            getActivity().finish();
          }else if(getActivity() instanceof WeatherActivity){
            WeatherActivity activity = (WeatherActivity) getActivity();
            activity.mDrawerLayout.closeDrawers();
            activity.mSwipeRefreshLayout.setRefreshing(true);
            activity.requestWeatherInfo(weatherId);
          }
        }
      }
    });
    mBackButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentLevel == LEVEL_COUNTRY) {
          queryCites();
        } else if (currentLevel == LEVEL_CITY) {
          queryProvinces();
        }

      }
    });
    queryProvinces(); //初始状态下首先加载全国省份数据
  }

  /**
   * 查询全国各省的数据
   */
  private void queryProvinces() {
    mTitleView.setText("中国");
    mBackButton.setVisibility(View.GONE);
    mProvinceList = LitePal.findAll(Province.class);
    if (mProvinceList.size() > 0) {
      dataList.clear();
      for (Province province : mProvinceList) {
        dataList.add(province.getProvinceName());
      }
      mAdapter.notifyDataSetChanged();
      mAreaListView.setSelection(0);
      currentLevel = LEVEL_PROVINCE;
    } else {
      String url = "http://guolin.tech/api/china";
      queryFromServer(url, "province");
    }
  }

  private void queryCites(){
    mTitleView.setText(selectedProvince.getProvinceName());
    mBackButton.setVisibility(View.VISIBLE);
    mCityList=LitePal.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
    if(mCityList.size()>0){
      dataList.clear();
      for(City city:mCityList){
        dataList.add(city.getCityName());
      }
      mAdapter.notifyDataSetChanged();
      mAreaListView.setSelection(0);
      currentLevel=LEVEL_CITY;
    }
    else {
      int provinceCode=selectedProvince.getProvinceCode();
      String address="http://guolin.tech/api/china/"+provinceCode;
      queryFromServer(address,"city");
    }
  }

  private void queryCountries() {
    mTitleView.setText(selectedCity.getCityName());
    mBackButton.setVisibility(View.VISIBLE);
    mCountryList =
        LitePal.where("cityid = ?", String.valueOf(selectedCity.getId())).find(Country.class);
    if (mCountryList.size() > 0) {
      dataList.clear();
      for (Country country : mCountryList) {
        dataList.add(country.getCountryName());
      }
      mAdapter.notifyDataSetChanged();
      mAreaListView.setSelection(0);
      currentLevel = LEVEL_COUNTRY;
    } else {
      int provinceCode = selectedProvince.getProvinceCode();
      int cityCode = selectedCity.getCityCode();
      String url = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
      queryFromServer(url, "country");
    }
  }

  private void queryFromServer(String url, final String type) {
    showDialog();
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            closeDialog();
            Toast.makeText(getContext(), "load fail", Toast.LENGTH_SHORT).show();
          }
        });
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();

        boolean result = false;
        if ("province".equals(type)) {
          result = Utility.handleProvinceData(responseText);
        } else if ("city".equals(type)) {
          result = Utility.handleCityData(responseText, selectedProvince.getId());
        } else if ("country".equals(type)) {
          result = Utility.handleCountryData(responseText, selectedCity.getId());
        }
        if (result) {
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              closeDialog();
              if ("province".equals(type)) {
                queryProvinces();
              } else if ("city".equals(type)) {
                queryCites();
              } else if ("country".equals(type)) {
                queryCountries();
              }
            }
          });
        }
      }
    });
  }

  private void showDialog() {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(getContext());
      mProgressDialog.setMessage("loading");
      mProgressDialog.setCancelable(false);
    }
    mProgressDialog.show();
  }

  private void closeDialog() {
    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
    }
  }


}
