package aeonlabs.common.libraries.Helper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;

import aeonlabs.common.libraries.activities.FragmentBase;


public class FragmentManagement {
    private HashMap<String, FragmentBase> fragments=new HashMap<String, FragmentBase>();
    private final Activity activity;
    private final int containerViewId;
    private String defaultFragmentTag="";

    public String currentLoadedFragmentTAG="";

    public static final int NOTIFY_ALL_FRRAGMENTS=-1;

    public FragmentManagement(Activity _activity, int _containerViewId) {
        this.fragments=new HashMap<>();
        this.activity=_activity;
        this.containerViewId=_containerViewId;
    }

    // *********************************************************************************************
    public Boolean AddFragment(FragmentBase fragment, Bundle bundle, boolean setAsDefault){
        if(bundle != null)
            fragment.setArguments(bundle);

        this.fragments.put(fragment.getTAG(), fragment);
        if(setAsDefault){
            defaultFragmentTag=fragment.getTAG();
        }
        return true;
    }
    // *********************************************************************************************
    public Boolean emptyFragemntsList(){
            this.fragments= new HashMap<String, FragmentBase>();
            return true;
    }

    // *********************************************************************************************
    public Boolean RemoveFragment(String tag){
        this.fragments.remove(tag);
        return true;
    }

    // *********************************************************************************************
    public void StartDefaultFragment(){
        if (defaultFragmentTag.equals("")){
            Toast.makeText(activity,"Default fragment not set!", Toast.LENGTH_LONG).show();
            return;
        }
        if(fragments.get(defaultFragmentTag) == null){
            Toast.makeText(activity,"Fragment "+ defaultFragmentTag+ "not found!", Toast.LENGTH_LONG).show();
            return;
        }
        setCurrentFragment(defaultFragmentTag);
    }
    // *********************************************************************************************
    public void AddAndStartFragment(FragmentBase fragment, Bundle bundle){
        AddFragment(fragment, bundle, false);
        setCurrentFragment(fragment.getTAG());
    }

    // *********************************************************************************************
    public void removeCurrentLoadedFragment(){
        if (!defaultFragmentTag.equals("")){
            if(!currentLoadedFragmentTAG.equals(defaultFragmentTag))
                this.fragments.remove(currentLoadedFragmentTAG);
        }
    }

    // *********************************************************************************************
    public int getFragmentsCount() {
        return fragments.size();
    }

    // *********************************************************************************************
    public FragmentBase getFragment(String TAG){
        return this.fragments.get(TAG);
    }

    // *********************************************************************************************
    public void setCurrentFragment(String tag){
        currentLoadedFragmentTAG=tag;
        FragmentManager fragmentManager =  ((AppCompatActivity)activity).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(this.containerViewId, fragments.get(tag))
                .addToBackStack(null);
        transaction.commit();
        fragmentManager.executePendingTransactions();
    }
}