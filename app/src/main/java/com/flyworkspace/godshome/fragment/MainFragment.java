package com.flyworkspace.godshome.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyworkspace.godshome.R;
import com.flyworkspace.godshome.bean.Section;
import com.flyworkspace.godshome.util.PublicFunction;

/**
 * Created by jinpengfei on 15/1/25.
 */
public class MainFragment extends Fragment {
    private TextView tv;
    public static MainFragment mainFragment;
    public Section section;

    public static MainFragment newInstance(Section section) {
//        if (mainFragment == null){
        mainFragment = new MainFragment();
        mainFragment.section = section;
//            }
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        tv = (TextView) view.findViewById(R.id.txt_content);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(section.getSectionName());
        tv.setText(PublicFunction.readFromAsset(getActivity(), section.getPath()));
        refreshFontTextSize();
    }

    public void refreshFontTextSize(){
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tv.setTextSize((sp.getInt("content_font_size", 1) + 2) * 6);
    }
}
