package com.surya.scheduler.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.surya.scheduler.fragments.all_classes_fragment;
import com.surya.scheduler.fragments.all_rooms_fragment;
import com.surya.scheduler.fragments.all_staffs_fragment;

public class fragmentAdapter extends FragmentStateAdapter {
    public fragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:{
                return new all_staffs_fragment();
            }
            case 2:{
                return new all_rooms_fragment();
            }
        }
        return new all_classes_fragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
