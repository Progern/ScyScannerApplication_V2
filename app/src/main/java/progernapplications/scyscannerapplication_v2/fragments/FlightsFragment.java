package progernapplications.scyscannerapplication_v2.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
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


public class FlightsFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private AutoCompleteTextView outboundText, inboundText;
    private List<Place> placesList;
    private PlacesAdapter mAdapter;
    private TextView outboundDate, inboundDate;
    private Switch inboundSwitch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Flights booking");
        View myView = inflater.inflate(R.layout.fragment_flights, container, false);
        Button searchButton = (Button) myView.findViewById(R.id.search_button);
        inboundSwitch = (Switch) myView.findViewById(R.id.inbound_switch);
        // *****************************************************************
        inboundDate = (TextView) myView.findViewById(R.id.inbound_date);
        outboundDate = (TextView) myView.findViewById(R.id.outbound_date);
        // ******************************************************************

        // User will change the visibility of inboundDate TextView(by using switch) if he would need this option
        inboundDate.setVisibility(View.INVISIBLE);

        outboundText = (AutoCompleteTextView) myView.findViewById(R.id.outbound_text);
        outboundText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                getAutoSuggestLocation(editable.toString());
            }
        });

        inboundText = (AutoCompleteTextView) myView.findViewById(R.id.inbound_text);
        inboundText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                getAutoSuggestLocation(editable.toString());
            }
        });


        inboundSwitch.setOnCheckedChangeListener(this);
        searchButton.setOnClickListener(this);
        outboundDate.setOnClickListener(this);
        inboundDate.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                if (!(outboundText.getText().toString().equals("")))
                    Config.OUTBOUND_LOCATION = getLocationId(outboundText.getText().toString());
                if (!(inboundText.getText().toString().equals("")))
                    Config.INBOUND_LOCATION = getLocationId(inboundText.getText().toString());
                Toast.makeText(getContext(), Config.OUTBOUND_LOCATION + " --- " + Config.INBOUND_LOCATION, Toast.LENGTH_SHORT).show();
                break;
            case R.id.outbound_date:
                Other.setDateSetChecker(0);
                onCreateDialog().show();
                break;
            case R.id.inbound_date:
                Other.setDateSetChecker(1);
                onCreateDialog().show();
                break;


        }
    }

    // GET Request to suit the user's input with prediction, using ScyScanner AutoSuggestionPlace API
    public void getAutoSuggestLocation(String currentQuery) {
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
    public String getLocationId(String fullString) {
        StringBuffer returnString = new StringBuffer();
        for (int i = 0; i < fullString.length(); i++) {
            if (fullString.charAt(i) == '(') {
                i++;
                while (fullString.charAt(i) != ')') {
                    returnString.append(fullString.charAt(i));
                    i++;


                }
            }
        }

        return returnString.toString();
    }

    private DatePickerDialog.OnDateSetListener mCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            // ***************************************************************************************
            // This is made, because Android month counter starts from 0, and ScyScanners starts from 1
            // Plus ScyScanners requests require month formath, if month is < 10 : 01,02,03... etc
            StringBuffer monthBuf = new StringBuffer();
            if(i1 >= 10) monthBuf.append((i1+1));
            else monthBuf.append("0" + (i1+1));
            // ***************************************************************************************


            if (Other.DATE_SET_CHECKER == 0) outboundDate.setText(i + "-" + monthBuf.toString() + "-" + i2);
            else if (Other.DATE_SET_CHECKER == 1) inboundDate.setText(i + "-" + monthBuf.toString() + "-" + i2);
        }
    };

    protected Dialog onCreateDialog() {
        DatePickerDialog mDateDialog = new DatePickerDialog(getContext(), mCallBack, 2016, 0, 1);
        return mDateDialog;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (inboundSwitch.isChecked()) {
            inboundDate.setVisibility(View.VISIBLE);
        } else inboundDate.setVisibility(View.INVISIBLE);
    }


}
