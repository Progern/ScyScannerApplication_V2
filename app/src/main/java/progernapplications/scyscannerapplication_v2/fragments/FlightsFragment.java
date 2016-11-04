package progernapplications.scyscannerapplication_v2.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.adapters.PlacesAdapter;
import progernapplications.scyscannerapplication_v2.config.Config;
import progernapplications.scyscannerapplication_v2.config.Other;
import progernapplications.scyscannerapplication_v2.models.Place;
import progernapplications.scyscannerapplication_v2.models.Places;
import progernapplications.scyscannerapplication_v2.network.Network;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class FlightsFragment extends Fragment implements View.OnClickListener{


    private AutoCompleteTextView outboundText, inboundText;
    private List<Place> placesList;
    private PlacesAdapter mAdapter;
    private AutoCompleteTextView outboundDate, inboundDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Flights booking");
        View myView = inflater.inflate(R.layout.fragment_flights, container, false);
        Button searchButton = (Button) myView.findViewById(R.id.search_button);
        final Switch inboundSwitch = (Switch) myView.findViewById(R.id.inbound_switch);
        AutoCompleteTextView outboundDate = (AutoCompleteTextView) myView.findViewById(R.id.outbound_date);
        final AutoCompleteTextView inboundDate = (AutoCompleteTextView) myView.findViewById(R.id.inbound_date);
        outboundDate = (AutoCompleteTextView) myView.findViewById(R.id.outbound_date);



        inboundDate.setVisibility(View.INVISIBLE);

        outboundText = (AutoCompleteTextView) myView.findViewById(R.id.outbound_text);
        outboundText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getAutoSuggestLocation(editable.toString());
            }
        });

        inboundText = (AutoCompleteTextView) myView.findViewById(R.id.inbound_text);
        inboundText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getAutoSuggestLocation(editable.toString());
            }
        });

        inboundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (inboundSwitch.isChecked())
                {
                    inboundDate.setVisibility(View.VISIBLE);
                }
                else inboundDate.setVisibility(View.INVISIBLE);
            }
        });
        searchButton.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                if (!(outboundText.getText().toString().equals(""))) Config.OUTBOUND_LOCATION = getLocationId(outboundText.getText().toString());
                if (!(inboundText.getText().toString().equals(""))) Config.INBOUND_LOCATION = getLocationId(inboundText.getText().toString());
                Toast.makeText(getContext(), Config.OUTBOUND_LOCATION + " --- " + Config.INBOUND_LOCATION, Toast.LENGTH_SHORT).show();
//
        }
    }


    // GET Request to suit the user's input with prediction, using ScyScanner AutoSuggestionPlace API
    private void getAutoSuggestLocation(String currentQuery) {
        if (!(outboundText.getText().toString().equals(""))) {
            Call<Places> call = Network.API.getAutoSuggestPlaces(Config.ACCEPT, Config.MARKET_COUNTRY, Config.CURRENCY, Config.LOCALE, currentQuery);
            call.enqueue(new Callback<Places>() {
                @Override
                public void onResponse(Response<Places> response) {
                    if (response.isSuccess()) {
                        placesList = response.body().getPlaces();
                        mAdapter = new PlacesAdapter(getContext(), R.layout.place_row, placesList);
                        outboundText.setAdapter(mAdapter);
                        inboundText.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // Transforms the string, that is suitable for user, to string for ScyScanner API Call. Doesn't changes the UI outbound/inbound string
    public String getLocationId(String fullString)
    {
        StringBuffer returnString = new StringBuffer();
        for(int i = 0; i < fullString.length(); i++)
        {
            if( fullString.charAt(i) == '(')
            {
                i++;
                while(fullString.charAt(i) != ')')
                {
                    returnString.append(fullString.charAt(i));
                    i++;


                }
            }
        }

        return returnString.toString();
    }



}
