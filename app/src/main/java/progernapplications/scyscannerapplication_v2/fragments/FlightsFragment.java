package progernapplications.scyscannerapplication_v2.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.outbound_text)
    AutoCompleteTextView outboundText;
    @BindView(R.id.inbound_text)
    AutoCompleteTextView inboundText;
    @BindView(R.id.outbound_date)
    TextView outboundDate;
    @BindView(R.id.inbound_date)
    TextView inboundDate;
    @BindView(R.id.inbound_switch)
    Switch inboundSwitch;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.clear_inbound)
    ImageView clear_inbound_field;
    @BindView(R.id.clear_outbound)
    ImageView clear_outbound_field;
    @BindView(R.id.change_routes_imgbut)
    ImageView change_routes;

    private List<Place> placesList;
    private PlacesAdapter mAdapter;
    private Animation anim;
    private Calendar mCalendar;
    private DatePickerDialog.OnDateSetListener mCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            if (i < Other.CURRENT_YEAR || i1 < Other.CURRENT_MONTH || i2 < Other.CURRENT_DAY)
                Toast.makeText(getContext(), "The date is in the past.", Toast.LENGTH_SHORT).show();
            else {
                // This is made, because Android month counter starts from 0, and ScyScanners starts from 1
                // Plus ScyScanners requests require month formath, if month is < 10 : 01,02,03... etc
                StringBuffer monthBuf = new StringBuffer();
                if (i1 >= 10) monthBuf.append((i1 + 1));
                else monthBuf.append("0" + (i1 + 1));

                if (Other.DATE_SET_CHECKER == 0)
                    outboundDate.setText(i + "-" + monthBuf.toString() + "-" + i2);
                else if (Other.DATE_SET_CHECKER == 1)
                    inboundDate.setText(i + "-" + monthBuf.toString() + "-" + i2);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Flights booking");
        View myView = inflater.inflate(R.layout.fragment_flights, container, false);
        ButterKnife.bind(this, myView);

        mCalendar = Calendar.getInstance();
        setCurrentDate();


        anim = AnimationUtils.loadAnimation(getContext(), R.anim.routes_change_rotation);

        // User will change the visibility of inboundDate TextView(by using switch) if he would need this option
        inboundDate.setVisibility(View.INVISIBLE);

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

        inboundSwitch.setOnCheckedChangeListener(this);
        searchButton.setOnClickListener(this);
        outboundDate.setOnClickListener(this);
        inboundDate.setOnClickListener(this);
        clear_inbound_field.setOnClickListener(this);
        clear_outbound_field.setOnClickListener(this);
        change_routes.setOnClickListener(this);

        if (Other.FLIGHTS_COUNTER < 1) {
            createHelpDialog().show();
        }


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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(Config.REFERAL_URL + Config.MARKET_COUNTRY + "/"
                        + Config.CURRENCY + "/"
                        + Config.LOCALE + "/" +
                        Config.OUTBOUND_LOCATION +
                        "/" + Config.INBOUND_LOCATION + "/"
                        + outboundDate.getText() +
                        "/" + inboundDate.getText() + "?" + Config.API_KEY));
                startActivity(browserIntent);
                break;
            case R.id.outbound_date:
                Other.setDateSetChecker(0);
                onCreateDialog().show();
                break;
            case R.id.inbound_date:
                Other.setDateSetChecker(1);
                onCreateDialog().show();
                break;

            case R.id.clear_inbound:
                inboundText.setText("");
                break;

            case R.id.clear_outbound:
                outboundText.setText("");
                break;

            case R.id.change_routes_imgbut:
                change_routes.startAnimation(anim);
                changeRoutes();
                break;


        }
    }

    // GET Request to suit the user's input with prediction, using ScyScanner AutoSuggestionPlace API
    public void getAutoSuggestLocation(String currentQuery) {
        if ((!(outboundText.getText().toString().equals(""))) || (!(inboundText.getText().toString().equals("")))) {
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

    protected Dialog onCreateDialog() {
        DatePickerDialog mDateDialog = new DatePickerDialog(getContext(), mCallBack, Other.CURRENT_YEAR, Other.CURRENT_MONTH, Other.CURRENT_DAY);
        return mDateDialog;
    }


    // To set the Inbound field visible/invisible
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (inboundSwitch.isChecked()) {
            inboundDate.setVisibility(View.VISIBLE);
        } else inboundDate.setVisibility(View.INVISIBLE);
    }


    public void changeRoutes() {
        String tmp = inboundText.getText().toString();
        inboundText.setText(outboundText.getText().toString());
        outboundText.setText(tmp);
    }


    // Temporary dialog, cause ScyScanner doesn't supports Live Pricing API right now
    public AlertDialog createHelpDialog() {
        AlertDialog.Builder helpDialogBuilder = new AlertDialog.Builder(getContext());
        helpDialogBuilder.setTitle(" Some inconvenience")
                .setMessage(R.string.unsupport_dialog)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Other.FLIGHTS_COUNTER = 1;
                    }
                });
        AlertDialog helpAlert = helpDialogBuilder.create();
        return helpAlert;

    }


    // A method to synchronize the current date of device with the date, that will be displayed in DatePicker
    private void setCurrentDate() {
        Other.CURRENT_YEAR = mCalendar.get(Calendar.YEAR);
        Other.CURRENT_MONTH = mCalendar.get(Calendar.MONTH);
        Other.CURRENT_DAY = mCalendar.get(Calendar.DAY_OF_MONTH);
    }
}
