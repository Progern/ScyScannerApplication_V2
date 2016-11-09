package progernapplications.scyscannerapplication_v2.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.config.Other;
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

    @BindView(R.id.locale_autocomp)
    AutoCompleteTextView localesText;
    @BindView(R.id.marketCountry_autocomp)
    AutoCompleteTextView marketCountryText;
    @BindView(R.id.currency_autocomp)
    AutoCompleteTextView currencyText;
    @BindView(R.id.settings_submit_button)
    Button submitButton;
    @BindView(R.id.clear_country)
    ImageView clear_country_field;
    @BindView(R.id.clear_currency)
    ImageView clear_currency_field;
    @BindView(R.id.clear_language)
    ImageView clear_language_field;
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
        getActivity().setTitle("Settings");
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, myView);
        context = getActivity().getApplicationContext();
        localesText.setOnItemClickListener(this);
        submitButton.setOnClickListener(this);
        clear_country_field.setOnClickListener(this);
        clear_currency_field.setOnClickListener(this);
        clear_language_field.setOnClickListener(this);

        // Perform requests when user enters the fragment
        getLocalesRequest();
        getCurrenciesRequest();

        if (Other.ALERT_COUNTER < 1) createHelpDialog().show();

        return myView;
    }

    // HTTP-Requests block  ******************************
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


    // ****************************************************

    // Gets the locale code for API tickets request
    private String getLocaleCode(String localeName) {
        for (int i = 0; i < locales.size(); i++) {
            if (localeName.equals(locales.get(i).getName())) return locales.get(i).getCode();

        }
        return "en-GB";
    }

    // Gets the market country code for API tickets request
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
                if(!(localesText.getText().toString().equals(""))) Config.LOCALE = getLocaleCode(localesText.getText().toString());
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
                    if (!(marketCountryText.getText().toString().equals(""))) Config.MARKET_COUNTRY = getMarketCountryCode(marketCountryText.getText().toString());
                    if(!(currencyText.getText().toString().equals(""))) Config.CURRENCY = currencyText.getText().toString();
                    successBar.show();
                } catch (NullPointerException ex) {
                }
                break;

            case R.id.clear_country:
                marketCountryText.setText("");
                break;

            case R.id.clear_currency:
                currencyText.setText("");
                break;

            case R.id.clear_language:
                localesText.setText("");
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Settings");
        successBar = Snackbar.make(getView(), "Your settings have been saved", Snackbar.LENGTH_SHORT);

    }

    public AlertDialog createHelpDialog()
    {
        AlertDialog.Builder helpDialogBuilder = new AlertDialog.Builder(getContext());
        helpDialogBuilder.setTitle("Please notice")
                .setMessage(R.string.settings_dialog)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Other.ALERT_COUNTER = 1;
                    }
                });
        AlertDialog helpAlert = helpDialogBuilder.create();
        return helpAlert;

    }
}