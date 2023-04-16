package aeonlabs.barcodescanner.common.libraries.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import aeonlabs.barcodescanner.common.libraries.R;
import aeonlabs.barcodescanner.common.libraries.adapter.BarCodeAdapter;
import aeonlabs.barcodescanner.common.libraries.adapter.SwipeRVTouchHelper;
import aeonlabs.barcodescanner.common.libraries.database.DatabaseHelper;
import aeonlabs.barcodescanner.common.libraries.model.BarCode;
import aeonlabs.common.libraries.activities.ActivityBase;
import aeonlabs.common.libraries.activities.FragmentBase;
import aeonlabs.common.libraries.activities.FragmentBaseObserver;

public class BarCodeListFragment extends FragmentBase implements FragmentBaseObserver,  SwipeRVTouchHelper.SwipeRVTouchHelperListener {

    private RecyclerView mRecyclerView;
    private BarCodeAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    ArrayList<Object> barCodeArrayList;
    private RelativeLayout mainLayout , emptyLayout ;
    DatabaseHelper db ;
    private ActivityBase activity;

    public BarCodeListFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_prduct_list,container,false);
        activity=(ActivityBase) getActivity();

        mRecyclerView = view.findViewById(R.id.barcode_list_recycler_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh_layout);
        mainLayout = view.findViewById(R.id.main_layout);
        emptyLayout = view.findViewById(R.id.empty_layout);
        swipeRefresh.setColorSchemeColors(activity.getResources().getColor(R.color.green),activity.getResources().getColor(R.color.blue),activity.getResources().getColor(R.color.orange));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBarCodeDB();
            }
        });

        loadBarCodeDB();
        return view;
    }

/**************************************************************************************************/

    private void loadBarCodeDB() {
        db= new DatabaseHelper(activity);
        barCodeArrayList = db.getAllBarCodes();


        if(!barCodeArrayList.isEmpty()){
            mAdapter = new BarCodeAdapter(activity, barCodeArrayList);
            //mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            swipeRefresh.setRefreshing(false);
            emptyLayout.setVisibility(View.GONE);

            // Enable both left and right swipe
            // Pass 0 to prevent drag
            ItemTouchHelper.SimpleCallback simpleCallback = new SwipeRVTouchHelper(this,0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);
        }
        else{
            emptyLayout.setVisibility(View.VISIBLE);
            swipeRefresh.setRefreshing(false);
        }

    }


/*******************************************************************************************/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadBarCodeDB();
    }

    /**
     * Determine what happens after the item in recyclerview is swiped off
     * @param direction
     * @param position
     */
    @Override
    public void onSwiped( int direction, final int position) {
        // Temporary store the swiped off item
        final BarCode barCode = (BarCode) barCodeArrayList.get(position);
        //Remove the item
        mAdapter.removeSwipeItem(position);
        // If swipe left - delete the item
        if(direction == ItemTouchHelper.LEFT){
            Snackbar.make(mRecyclerView, "Contact deleted", Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(activity, R.color.colorIconTintSelected))
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAdapter.addSwipeItem(position, barCode);
                        }
                    }).show();
        } // If swipe left - archive the item
        else if (direction == ItemTouchHelper.RIGHT){
            Snackbar.make(mRecyclerView, "Contact archive", Snackbar.LENGTH_LONG)
                    .setActionTextColor(ContextCompat.getColor(activity, R.color.colorIconTintSelected))
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAdapter.addSwipeItem(position, barCode);
                        }
                    }).show();
        }
    }

/********************************************************************************/
    @Override
    public void notifyObserversFragment(String... params) {
        loadBarCodeDB();
    }
}
