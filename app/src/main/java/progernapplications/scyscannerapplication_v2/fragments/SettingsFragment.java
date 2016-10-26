package progernapplications.scyscannerapplication_v2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.List;

import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.models.Locale;
import progernapplications.scyscannerapplication_v2.models.Locales;
import progernapplications.scyscannerapplication_v2.models.MarketCountry;
import progernapplications.scyscannerapplication_v2.models.Markets;
import progernapplications.scyscannerapplication_v2.network.Network;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class SettingsFragment extends Fragment {


    private AutoCompleteTextView localesText, marketCountryText;
    private ArrayAdapter<Locale> localeArrayAdapter;
    private ArrayAdapter<MarketCountry> marketCountryArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        localesText = (AutoCompleteTextView) myView.findViewById(R.id.locale_autocomp);
        marketCountryText = (AutoCompleteTextView) myView.findViewById(R.id.marketCountry_autocomp);

        getLocalesRequest();
        getMarketCountries();
        return myView;
    }

    public void getLocalesRequest() {
        Call<Locales> call = Network.API.getLocales(Config.ACCEPT);
        call.enqueue(new Callback<Locales>() {
            @Override
            public void onResponse(Response<Locales> response) {
                if (response.isSuccess()) {
                    List<Locale> locales = response.body().getLocales();
                    localeArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_layout, locales);
                    localesText.setAdapter(localeArrayAdapter);
                } else {
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMarketCountries() {
        Call<Markets> call = Network.API.getMarketCountries(Config.ACCEPT);
        call.enqueue(new Callback<Markets>() {
            @Override
            public void onResponse(Response<Markets> response) {
                if (response.isSuccess()) {
                    List<MarketCountry> markets = response.body().getMarkets();
                    marketCountryArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_layout, markets);
                    marketCountryText.setAdapter(marketCountryArrayAdapter);

                } else Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}