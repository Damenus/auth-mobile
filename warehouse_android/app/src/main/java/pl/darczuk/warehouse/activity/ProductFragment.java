package pl.darczuk.warehouse.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.dummy.DummyContent;
import pl.darczuk.warehouse.activity.dummy.DummyContent.DummyItem;
import pl.darczuk.warehouse.activity.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductFragment newInstance(int columnCount) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        JSONArray productsJson = null;
        try {
            productsJson = new JSONArray(HttpClient.getRequest("/product"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products= new ArrayList<Product>();
        if (productsJson != null) {
            for (int i=0;i<productsJson.length();i++){
                try {
                    products.add(
                            new Product(
                                    Long.decode(productsJson.getJSONObject(i).getString("id")),
                                    productsJson.getJSONObject(i).getString("modelName"),
                                    productsJson.getJSONObject(i).getString("manufacturerName"),
                                    productsJson.getJSONObject(i).getDouble("price"),
                                    productsJson.getJSONObject(i).getInt("quantity")
                            )
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        recyclerView.setAdapter(new MyProductRecyclerViewAdapter(products, mListener));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            JSONArray productsJson = null;
            try {
                productsJson = new JSONArray(HttpClient.getRequest("/product"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Product> products= new ArrayList<Product>();
            if (productsJson != null) {
                for (int i=0;i<productsJson.length();i++){
                    try {
                        products.add(
                                new Product(
                                        Long.decode(productsJson.getJSONObject(i).getString("id")),
                                        productsJson.getJSONObject(i).getString("modelName"),
                                        productsJson.getJSONObject(i).getString("manufacturerName"),
                                        productsJson.getJSONObject(i).getDouble("price"),
                                        productsJson.getJSONObject(i).getInt("quantity")
                                )
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            recyclerView.setAdapter(new MyProductRecyclerViewAdapter(products, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Product item);

        void onCreate(Bundle savedInstanceState);
    }


}