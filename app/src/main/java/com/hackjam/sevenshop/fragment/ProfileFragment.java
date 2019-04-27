package com.hackjam.sevenshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackjam.sevenshop.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private CircleImageView iv_foto;
    private TextView nama, editProfil, editPassword, syaratKetentuan, keluar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        iv_foto = rootView.findViewById(R.id.iv_profile_foto);
        nama = rootView.findViewById(R.id.tv_profile_nama);
        editProfil = rootView.findViewById(R.id.tv_profile_edit);
        editPassword = rootView.findViewById(R.id.tv_profile_ganti_pass);
        syaratKetentuan = rootView.findViewById(R.id.tv_profile_syarat_dan_ketentuan);
        keluar = rootView.findViewById(R.id.tv_profile_keluar);

        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_profile_edit :
                break;
            case R.id.tv_profile_ganti_pass :
                break;
            case R.id.tv_profile_syarat_dan_ketentuan :
                break;
            case R.id.tv_profile_keluar :
                break;
        }
    }
}
