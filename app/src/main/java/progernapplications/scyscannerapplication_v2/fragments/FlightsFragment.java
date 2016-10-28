package progernapplications.scyscannerapplication_v2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import progernapplications.scyscannerapplication_v2.R;
import progernapplications.scyscannerapplication_v2.config.Config;


public class FlightsFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_flights, container, false);
        Button searchButton = (Button) myView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        getActivity().setTitle("Flights booking");

        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                Toast.makeText(getContext(), Config.LOCALE + ", " + Config.CURRENCY +
                        ", " + Config.MARKET_COUNTRY, Toast.LENGTH_SHORT).show();
        }
    }
}
