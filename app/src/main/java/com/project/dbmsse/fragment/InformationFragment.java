package com.project.dbmsse.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.dbmsse.R;
import com.project.dbmsse.WebActivity;

import static com.project.dbmsse.R.id.faq;
import static com.project.dbmsse.R.id.policy;
import static com.project.dbmsse.R.id.terms;


public class InformationFragment extends Fragment {


    public InformationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        getActivity().setTitle("");

        final Button faqButton = (Button) view.findViewById(faq);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faqIntent = new Intent(getActivity(), WebActivity.class);
                faqIntent.putExtra("INFORMATION", "faq");
                getActivity().startActivity(faqIntent);
            }
        });

        Button termsButton = (Button)view.findViewById(terms);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent termsIntent = new Intent(getActivity(), WebActivity.class);
               termsIntent.putExtra("INFORMATION", "terms");
                getActivity().startActivity(termsIntent);
            }
        });

        final Button policyButton = (Button)view.findViewById(policy);
        policyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policyIntent = new Intent(getActivity(),  WebActivity.class);
                policyIntent.putExtra("INFORMATION", "policy");
                getActivity().startActivity(policyIntent);
            }
        });

        final Button thirdPartyButton = (Button)view.findViewById(R.id.third_party);
        thirdPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thirdPartyIntent = new Intent(getActivity(), WebActivity.class);
                thirdPartyIntent.putExtra("INFORMATION", "thirdparty");
                getActivity().startActivity(thirdPartyIntent);
            }
        });

        return view;
    }

}
