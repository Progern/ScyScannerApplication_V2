package progernapplications.scyscannerapplication_v2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.models.Currencies;
import progernapplications.scyscannerapplication_v2.models.Currency;
import progernapplications.scyscannerapplication_v2.models.Locale;
import progernapplications.scyscannerapplication_v2.models.Locales;
import progernapplications.scyscannerapplication_v2.models.MarketCountry;
import progernapplications.scyscannerapplication_v2.models.Markets;
import progernapplications.scyscannerapplication_v2.network.Network;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class SettingsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    private AutoCompleteTextView localesText, marketCountryText, currencyText;
    private ArrayAdapter<Locale> localeArrayAdapter;
    private ArrayAdapter<MarketCountry> marketCountryArrayAdapter;
    private ArrayAdapter<Currency> currencyArrayAdapter;

    private List<MarketCountry> markets;
    private List<Currency> currencies;
    private List<Locale> locales;
    private Context context;

    private Snackbar successBar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");
        localesText = (AutoCompleteTextView) myView.findViewById(R.id.locale_autocomp);
        context = getActivity().getApplicationContext();
        marketCountryText = (AutoCompleteTextView) myView.findViewById(R.id.marketCountry_autocomp);
        currencyText = (AutoCompleteTextView) myView.findViewById(R.id.currency_autocomp);
        localesText.setOnItemClickListener(this);
        Button submitButton = (Button) myView.findViewById(R.id.settings_submit_button);
        submitButton.setOnClickListener(this);
        getLocalesRequest();
        getCurrenciesRequest();


        return myView;
    }

    private void getLocalesRequest() {
        Call<Locales> call = Network.API.getLocales(Config.ACCEPT);
        call.enqueue(new Callback<Locales>() {
            @Override
            public void onResponse(Response<Locales> response) {
                if (response.isSuccess()) {
                    locales = response.body().getLocales();
                    localeArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_layout, locales);
                    localesText.setAdapter(localeArrayAdapter);
                } else {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMarketCountriesRequest() {
        Call<Markets> call = Network.API.getMarketCountries(Config.ACCEPT, Config.LOCALE);
        call.enqueue(new Callback<Markets>() {
            @Override
            public void onResponse(Response<Markets> response) {
                if (response.isSuccess()) {
                    markets = response.body().getMarkets();
                    marketCountryArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item_layout, markets);
                    marketCountryText.setAdapter(marketCountryArrayAdapter);

                } else Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getCurrenciesRequest() {
        Call<Currencies> call = Network.API.getCurrencies(Config.ACCEPT);
        call.enqueue(new Callback<Currencies>() {
            @Override
            public void onResponse(Response<Currencies> response) {

                if (response.isSuccess()) {
                    currencies = response.body().getCurrencies();
                    currencyArrayAdapter = new ArrayAdapter<Currency>(context, R.layout.spinner_item_layout, currencies);
                    currencyText.setAdapter(currencyArrayAdapter);

                } else Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getLocaleCode(String localeName) {
        for (int i = 0; i < locales.size(); i++) {
            if (localeName.equals(locales.get(i).getName())) return locales.get(i).getCode();

        }
        return "en-GB";
    }

    private String getMarketCountryCode(String marketCountryName) {
        for (int i = 0; i < markets.size(); i++) {
            if (marketCountryName.equals(markets.get(i).getName())) return markets.get(i).getCode();
        }

        return "US";
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.row_item:
                Config.LOCALE = getLocaleCode(localesText.getText().toString());
                // We send a request when we have selected preferable locale
                getMarketCountriesRequest();
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_submit_button:
                try {
                    Config.MARKET_COUNTRY = getMarketCountryCode(marketCountryText.getText().toString());
                    Config.CURRENCY = currencyText.getText().toString();
                    successBar.show();
                } catch (NullPointerException ex) {
                }
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Settings");
        successBar = Snackbar.make(getView(), "Your settings have been saved", Snackbar.LENGTH_SHORT);

    }
}