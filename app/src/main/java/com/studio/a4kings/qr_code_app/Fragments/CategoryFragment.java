package com.studio.a4kings.qr_code_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.a4kings.qr_code_app.Activities.FriendsOrSubscribersListActivity;
import com.studio.a4kings.qr_code_app.Activities.MainActivity;
import com.studio.a4kings.qr_code_app.Adapters.Category.CategoryListAdapter;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.CategoryModel;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.UserEnum;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.R;

import java.util.ArrayList;

/**
 * Created by DUX on 10.02.2016.
 */
public class CategoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private CategoryListAdapter mAdapter;
    ArrayList<CategoryModel> cat;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        cat = new ArrayList<>();

        CategoryModel myFriends = new CategoryModel(R.drawable.ic_relation, "My friends", 0xFF43a047);

        Intent intent;
        intent = new Intent(this.getActivity(), FriendsOrSubscribersListActivity.class);
        intent.putExtra(this.getString(R.string.sub_enum), UserEnum.MY);
        myFriends.setIntent(intent);

        cat.add(myFriends);
        CategoryModel allPeople = new CategoryModel(R.drawable.ic_company, "All people", 0xFFffc107);

        intent = new Intent(this.getActivity(), FriendsOrSubscribersListActivity.class);
        allPeople.setIntent(intent);
        cat.add(allPeople);


        CategoryModel myEvents = new CategoryModel(R.drawable.ic_events, "My events", 0xFF7e57c2);
        Fragment fragment = new EventsListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(this.getString(R.string.sub_enum), UserEnum.MY);
        fragment.setArguments(bundle);
        myEvents.setFragment(fragment);
        cat.add(myEvents);


        CategoryModel allEvents = new CategoryModel(R.drawable.ic_journey, "All events", 0xFF00695c);

        fragment = new EventsListFragment();
        bundle = new Bundle();
        bundle.putSerializable(this.getString(R.string.sub_enum), UserEnum.NOT_MY);
        fragment.setArguments(bundle);
        allEvents.setFragment(fragment);
        cat.add(allEvents);


        CategoryModel messager = new CategoryModel(R.drawable.ic_places, "Messager", 0xFF880e4f);
        cat.add(messager);


        mAdapter = new CategoryListAdapter(this.getContext(), cat);
        mRecyclerView.setAdapter(mAdapter);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mAdapter.setOnItemClickListener(onItemClickListener);
        return view;
    }

    CategoryListAdapter.OnItemClickListener onItemClickListener = new CategoryListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (position == 4) {
                Member member = PrefsManager.getInstance(getActivity()).getMember();
                String user_id = PrefsManager.getInstance(getActivity()).get(PrefsParam.USER_ID);
                if (member != null && !user_id.isEmpty()) {
                    openApp(getActivity(), "com.a4kings.dmitrypavlenko.smapme_mess", 1, user_id);
                }
                return;
            }
            CategoryModel categoryModel = cat.get(position);
            if (categoryModel.getIntent() != null)
                startActivity(categoryModel.getIntent());
            else if (categoryModel.getFragment() != null)
                ((MainActivity) getActivity()).changeFragment(categoryModel.getFragment(), null);
        }
    };


    public boolean openApp(Context context, String packageName, int runMode, String userId) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            i.putExtra("runMode", runMode);
            i.putExtra("user_id", userId);
            if (i == null) {
                return false;
                //throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
