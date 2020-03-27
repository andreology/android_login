package com.example.assignment_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An activity representing a list of cars. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link carDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class carListActivity extends AppCompatActivity implements OnItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private String TAG = carListActivity.class.getSimpleName();
    private Spinner makeSpinner;
    // Will hold id & car make
    private ArrayList<HashMap<String, String>> carMakeList;
    // Will hold vehicle make id, then model id (unique) & model name
    // (id:4, model:Roadster, makeID:3), (id:5, model:Model X, makeID:3)
    private ArrayList<HashMap<String, String>> carModelList;
    // This will hold the data for the specific make/model retrieved
    private ArrayList<HashMap<String, String>> carInfoList;

    private ArrayList<String> makeList;
    // Will hold model name & make ID#
    private ArrayList<String> modelList;
    private String[] arrMakes;
    private String[] arrModels;

    // These will be set by the results of the spinners, default is first to show
    private String carMakeNum = "10";
    private String carModelNum = "21";

    private ProgressDialog pDialog;
    // URL to get all of the car makes in JSON
    private static String makeUrl = "https://thawing-beach-68207.herokuapp.com/carmakes";
    // Base URL, append the id to end to retrieve models of that particular make (e.g. 3 for Tesla)
    private static String modelUrl = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";
    // Base URL
    private static String carUrl = "https://thawing-beach-68207.herokuapp.com/cars/ / /92603";
    private ArrayAdapter<String> spinnerAdapter2;
    private Spinner modelSpinner;
    // to avoid duplicate calls, see if user or program changed the spinner from previous position
    private int lastSpinnerPositionMake = 0;
    private int lastSpinnerPositionModel = 0;
    // Stores the results of the spinners for the car detail call
    private String lastSpinnerMake;
    private String lastSpinnerModel;
    private String makeID;

    public ArrayList<HashMap<String, String>> getCarInfoList(){
        return carInfoList;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carMakeList = new ArrayList<>();
        carModelList = new ArrayList<>();
        carInfoList = new ArrayList<>();
        makeList = new ArrayList<>();
        modelList = new ArrayList<>();
        new GetMakeData().execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);



        // Creating the spinners and loading them with data
        makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        // Setting values of makeSpinner after onPostExecute
        makeSpinner.setSelected(false);
        makeSpinner.setSelection(0, true);
        makeSpinner.setOnItemSelectedListener(this);


        // Loading the model spinner
        modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        // Setting values of modelSpinner after onPostExecute
        modelSpinner.setSelected(false);
        modelSpinner.setSelection(0, true);
        modelSpinner.setOnItemSelectedListener(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.car_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }




        View recyclerView = findViewById(R.id.car_list);
        assert recyclerView != null;
        // This is done after the post execute of the third async task
        setupRecyclerView((RecyclerView) recyclerView);

    }

    /**
     * Handles the logic for when a spinner is used to select a value
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Update the model spinner based on the make spinner
        // If the spinner has changed it's answer:
        int id = adapterView.getId();
        // Checking if it's the first (make) spinner
        if (id == R.id.makeSpinner){
            // Check if the spinner value has been updated, proceed if it has, else return
            if (lastSpinnerPositionMake != i) {
                lastSpinnerPositionMake = i;
            }
            else{
                // Do nothing if the spinner result hasn't changed
                return;
            }
            // Put our case switching logic in here:
            String result = adapterView.getItemAtPosition(i).toString().trim();
            System.out.println("RESULT of Spinner 1: " + result);
            lastSpinnerMake = result;
            lastSpinnerPositionModel = 0;
            Toast.makeText(adapterView.getContext(), "Selected: " + result, Toast.LENGTH_SHORT).show();
            /*Depending on the result of the first spinner, updates the contents of the second
            spinner to the models available of the make chosen (e.g. if Tesla is chosen in the
            first spinner, Model X and Model S will be options in the second spinner*/
            // go through and find models with make_id of 10

            // Set makeID to the makeID of the car selected by result
            modelList.clear();
            for (HashMap m : carMakeList){
                if (m.get("make").toString().equals(result)){
                    makeID = m.get("id").toString();
                }
            }
            // Iterating through the models to get all models with the matched makeID
            for (HashMap a : carModelList){
                // Replace the 3 with tesla.key & get it out of switch
                if (a.get("makeID").toString().equals(makeID)){
                    modelList.add(a.get("model").toString());
                }
            }
            System.out.println("MODEL LIST" + modelList);
            // Create the String[]:
            arrModels = new String[modelList.size()];
            int j = 0;
            for (String s : modelList){
                arrModels[j] = s;
                j++;
            }


            spinnerAdapter2 = new ArrayAdapter<String>(carListActivity.this,
                    android.R.layout.simple_list_item_1, arrModels);
            spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modelSpinner.setAdapter(spinnerAdapter2);
        }
        // else it will be the second (model) spinner
        else{
            if (lastSpinnerPositionModel != i) {
                lastSpinnerPositionModel = i;
            }
            else{
                // Do nothing if the spinner result hasn't changed
                return;
            }
            String result = adapterView.getItemAtPosition(i).toString().trim();
            System.out.println("RESULT of Spinner 2: " + result);
            lastSpinnerModel = result;

        }
        // Update the carMakeNum and carModelNum for URL:
        for (HashMap m : carMakeList){
            if (m.get("make").toString().equals(lastSpinnerMake)){
                carMakeNum = m.get("id").toString();
                break;
            }
        }
        for (HashMap n : carModelList){
            if (n.get("model").toString().equals(lastSpinnerModel)){
                carModelNum = n.get("id").toString();
                break;
            }
        }
        // On any change of spinner, need to get the new car data
        // Then show the available results in the recyclerview based on the selected make/model
        new GetCarData().execute();
        View recyclerView = findViewById(R.id.car_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }

    /**
     * Async task class to get json by making HTTP call
     * This one gets the Makes of all available cars
     * @param
     */
    private class GetMakeData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(carListActivity.this);
            pDialog.setMessage("Fetching manufacturer data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStrMake = sh.makeServicesCall(makeUrl);


            Log.e(TAG, "Response from url: " + jsonStrMake);

            // If we get a response from the url
            if (jsonStrMake != null){
                try{
                    // Getting JSON Array node
                    JSONArray carMakes = new JSONArray(jsonStrMake);
                    // Looping through the makes
                    for (int i = 0; i < carMakes.length(); i++) {
                        // Getting each individual object (each make)
                        JSONObject temp = carMakes.getJSONObject(i);
                        String id = temp.getString("id");
                        String make = temp.getString("vehicle_make");


                        // Creating a temp HashMap for this entry
                        HashMap<String, String> entry = new HashMap<>();
                        // Adding this entry into the HashMap
                        entry.put("id", id);
                        entry.put("make", make);
                        // Adding the make into the ArrayList
                        carMakeList.add(entry);
                        System.out.println(entry);
                    }
                    // Print carmakelist once it is done being created
                    System.out.println("CARMAKELIST : " + carMakeList);

                    // Creating model list of strings
                    for (HashMap a : carMakeList){
                        makeList.add(a.get("make").toString());
                    }
                    arrMakes = new String[makeList.size()];
                    int i = 0;
                    // Converting it into a string array instead of array list
                    for (String s : makeList){
                        arrMakes[i] = s;
                        System.out.println(arrMakes[i]);
                        i++;
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Couldn't get JSON from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get JSON from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            // Setting the spinner data for the makes
            ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(carListActivity.this,
                    android.R.layout.simple_list_item_1, arrMakes);
            spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            makeSpinner.setAdapter(spinnerAdapter1);

            // When all of the makes have been retrieved, retrieve all of the models
            new GetModelData().execute();

        }

    }

    /**
     * Async task class to get json by making HTTP call
     * This gets the models of all available makes
     * @param
     */
    private class GetModelData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(carListActivity.this);
            pDialog.setMessage("Fetching model data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            // Loop through the available ID's and call sh.makeServicesCall for each appended URL (each car make)
            for (HashMap make : carMakeList){
                String urlOffset = make.get("id").toString().trim();
                // appending the id to call the correct url
                String jsonStrMake = sh.makeServicesCall(modelUrl + urlOffset);

                Log.e(TAG, "Response from url: " + jsonStrMake);

                // If we get a response from the url
                if (jsonStrMake != null){
                    try{
                        // Getting JSON Array node
                        JSONArray carModels = new JSONArray(jsonStrMake);

                        // Looping through each model
                        for (int i = 0; i < carModels.length(); i++) {
                            // Getting each individual object (each model)
                            JSONObject temp = carModels.getJSONObject(i);
                            String id = temp.getString("id");
                            String model = temp.getString("model");
                            String makeId = temp.getString("vehicle_make_id");

                            // Creating a temp HashMap for this entry
                            HashMap<String, String> mEntry = new HashMap<>();
                            mEntry.put("id", id);
                            mEntry.put("model", model);
                            mEntry.put("makeID", makeId);
                            carModelList.add(mEntry);
                            System.out.println("Model Entry: " + mEntry);
                        }
                        System.out.println("CARMODELLIST: " + carModelList);

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                }
                else {
                    Log.e(TAG, "Couldn't get JSON from server");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get JSON from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }

            // Initialization for first load:
            for (HashMap m : carMakeList){
                if (m.get("make").toString().equals("Aston Martin")){
                    makeID = m.get("id").toString();
                }
            }
            // Iterating through the models to get all models with the matched makeID
            for (HashMap a : carModelList){
                if (a.get("makeID").toString().equals(makeID)){
                    modelList.add(a.get("model").toString());
                }
            }
            System.out.println("MODEL LIST" + modelList);
            // Create the String[]:
            arrModels = new String[modelList.size()];
            int j = 0;
            for (String s : modelList){
                arrModels[j] = s;
                j++;
            }
            // Need initialization of the recyclerView
            View recyclerView = findViewById(R.id.car_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

            // Setting the spinner data for the models
            ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(carListActivity.this,
                    android.R.layout.simple_list_item_1, arrModels);
            spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            modelSpinner.setAdapter(spinnerAdapter2);
        }

    }

    /**
     * Async task class to get json by making HTTP call
     * This gets the info of a specific make/model based on the spinner's selection
     * @param
     */
    private class GetCarData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(carListActivity.this);
            pDialog.setMessage("Fetching car data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh = new HttpHandler();

            // Have to get the make and model to create the URL to make the call
            // Replacing the make and model number in the carUrl to make the correct search
            carUrl = carUrl.replaceFirst(" ", carMakeNum);
            carUrl = carUrl.replaceFirst(" ", carModelNum);
            // appending the id to call the correct url
            String jsonStrMake = sh.makeServicesCall(carUrl);
            System.out.println("CAR URL: " + carUrl);

            Log.e(TAG, "Response from url: " + jsonStrMake);
            String test = jsonStrMake.replaceAll("/", ".");



            // If we get a response from the url
            if (jsonStrMake != null){
                try{
                    JSONObject lists = new JSONObject(test);
                    JSONArray cars = (JSONArray) lists.get("lists");


                    // Looping through each model
                    for (int i = 0; i < cars.length(); i++) {
                        // Getting each individual object (each model)
                        JSONObject temp = cars.getJSONObject(i);
                        String carId = temp.getString("id");
                        String price = temp.getString("price");
                        String created = temp.getString("created_at");
                        String description = temp.getString("veh_description");

                        // Creating a temp HashMap for this entry
                        HashMap<String, String> mEntry = new HashMap<>();
                        mEntry.put("id", carId);
                        mEntry.put("price", price);
                        mEntry.put("created", created);
                        mEntry.put("description", description);

                        carInfoList.add(mEntry);
                        System.out.println("Model Entry: " + mEntry);
                    }
                    System.out.println("CARINFOLIST: " + carInfoList);
                    for (int i = 0; i < carInfoList.size(); i++){
                        System.out.println("INDEX " + i + ": " + carInfoList.get(i));
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
            else {
                Log.e(TAG, "Couldn't get JSON from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get JSON from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }

            View recyclerView = findViewById(R.id.car_list);
            assert recyclerView != null;
            ((RecyclerView) recyclerView).getAdapter().notifyDataSetChanged();

        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, carInfoList, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final carListActivity mParentActivity;
        private final ArrayList<HashMap<String, String>> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                ArrayList<HashMap<String, String>> item = (ArrayList<HashMap<String, String>>) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    // ???
                    arguments.putString(carDetailFragment.ARG_ITEM_ID, item.toString());
                    carDetailFragment fragment = new carDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.car_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, carDetailActivity.class);
                    intent.putExtra(carDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(carListActivity parent,
                                      ArrayList<HashMap<String, String>> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.car_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).get("id"));
            holder.mContentView.setText(mValues.get(position).get("price"));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
