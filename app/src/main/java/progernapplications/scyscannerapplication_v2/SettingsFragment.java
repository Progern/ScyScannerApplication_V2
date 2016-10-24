package progernapplications.scyscannerapplication_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static final String LOCALE_REQUEST = "http://partners.api.skyscanner.net/apiservices/reference/v1.0/locales?apiKey=zd325407587237928226567311928563";
    private static final String CURRENCIES_REQUEST = "http://partners.api.skyscanner.net/apiservices/reference/v1.0/currencies?apiKey=zd325407587237928226567311928563";


    private AutoCompleteTextView localesText, currensiesText, countriesText;
    private String resultLocale, resultCurrency, resultContry;

    private AsyncHttpClient mClient;
    private RequestParams params;

    // Array Lists for handling our responses
    private ArrayList<MarketCountry> countriesResponseList;
    private ArrayList<Currency> currenciesResponseList;
    private ArrayList<Locale> localesResponseList;

    private ArrayAdapter<MarketCountry> marketCountryArrayAdapter;
    private ArrayAdapter<Currency> currencyArrayAdapter;
    private ArrayAdapter<Locale> localeArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_settings, container, false);
        localesText = (AutoCompleteTextView) myView.findViewById(R.id.locale_autocomp);
        currensiesText = (AutoCompleteTextView) myView.findViewById(R.id.currency_autocomp);
        countriesText = (AutoCompleteTextView) myView.findViewById(R.id.marketCountry_autocomp);

        mClient = new AsyncHttpClient();
        params = new RequestParams();
        params.add("Accept", "application/json");

        countriesResponseList = new ArrayList<>();
        currenciesResponseList = new ArrayList<>();
        localesResponseList = new ArrayList<>();


        marketCountryArrayAdapter = new ArrayAdapter<MarketCountry>(getContext(), R.layout.support_simple_spinner_dropdown_item, countriesResponseList);
        currencyArrayAdapter = new ArrayAdapter<Currency>(getContext(), R.layout.support_simple_spinner_dropdown_item, currenciesResponseList);
        localeArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, localesResponseList);

        localesText.setOnClickListener(this);
        currensiesText.setOnClickListener(this);
        countriesText.setOnClickListener(this);

        localesText.setAdapter(localeArrayAdapter);
        currensiesText.setAdapter(currencyArrayAdapter);
        countriesText.setAdapter(marketCountryArrayAdapter);


        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locale_autocomp:
                getLocale();
                break;
            case R.id.currency_autocomp:
                // TODO
                break;
            case R.id.marketCountry_autocomp:
                // TODO
                break;
        }
    }


    // TODO BD cashing later!
    private void getLocale() {
        mClient.get(LOCALE_REQUEST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    try {
                        JSONArray locales = response.getJSONArray("Locales");

                        for (int i = 0; i < locales.length(); i++) {
                            localesResponseList.add(new Locale(locales.getJSONObject(i).getString("Code"), locales.getJSONObject(i).getString("Name")));
                        }

                        localeArrayAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {

                    }
                }
            }
        });
    }
}
