package Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.Tabs.NutrionFragment;
import Fragments.Tabs.ProgressFragment;
import Fragments.Tabs.TrainingFragment;

public class TabsAdapter extends FragmentStateAdapter {

    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TrainingFragment();
            case 1:
                return new NutrionFragment();
            default:
                return new ProgressFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
