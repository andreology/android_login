package com.example.assignment_1;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_1.carListActivity;

import java.util.HashMap;

/**
 * A fragment representing a single car detail screen.
 * This fragment is either contained in a {@link carListActivity}
 * in two-pane mode (on tablets) or a {@link carDetailActivity}
 * on handsets.
 */
public class carDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "3200";

    /**
     * The car data content this fragment is presenting.
     */
    private HashMap<String, String> data;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public carDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            data = carListActivity.carInfoList.get(0);
            System.out.println("DATA: " + data);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(data.get("make") + " " + data.get("model"));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.car_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (data != null) {
            ((TextView) rootView.findViewById(R.id.car_detail)).setText(data.get("description"));
            ((TextView) rootView.findViewById(R.id.priceCar)).setText("$" + data.get("price"));
            ((TextView) rootView.findViewById(R.id.lastUpdate)).setText("Last Updated: " +
                    data.get("created"));
            ((TextView) rootView.findViewById(R.id.makeModel)).setText(data.get("make") +
                    " " + data.get("model"));

        }

        return rootView;
    }
}
