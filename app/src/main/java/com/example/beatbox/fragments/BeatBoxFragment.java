package com.example.beatbox.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beatbox.R;
import com.example.beatbox.databinding.FragmentBeatBoxBinding;
import com.example.beatbox.databinding.ListItemSoundBinding;
import com.example.beatbox.model.Sound;
import com.example.beatbox.repository.BeatBoxRepository;

import java.util.List;


public class BeatBoxFragment extends Fragment {
    //private RecyclerView mRecyclerView;
    private BeatBoxRepository mRepository;
    private FragmentBeatBoxBinding mBinding;

    public BeatBoxFragment() {
        // Required empty public constructor
    }

    public static BeatBoxFragment newInstance(String param1, String param2) {
        BeatBoxFragment fragment = new BeatBoxFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = BeatBoxRepository.getInstance(getContext());
        setRetainInstance(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRepository.releaseSoundPool();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_beat_box,
                container,
                false);
        initViews();
        setUpAdapter();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.recyclerViewBeatBox.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    private void setUpAdapter(){
        List<Sound> sounds = mRepository.getSounds();
        SoundAdapter adapter = new SoundAdapter(sounds);
        mBinding.recyclerViewBeatBox.setAdapter(adapter);

    }

    private class SoundHolder extends RecyclerView.ViewHolder{
        //private Button mBtn_beatbox;
        private Sound mSound;
        private ListItemSoundBinding mListItemSoundBinding;


        public SoundHolder(ListItemSoundBinding listItemSoundBinding) {
            super(listItemSoundBinding.getRoot());
            mListItemSoundBinding = listItemSoundBinding;

            //mBtn_beatbox = itemView.findViewById(R.id.btn_beat_box);
            mListItemSoundBinding.btnBeatBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRepository.play(mSound);
                }
            });

        }

        public void bindSound(Sound sound) {
            mSound = sound;
            mListItemSoundBinding.btnBeatBox.setText(mSound.getName());
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{

        private List<Sound> mSounds;

        public List<Sound> getSounds() {
            return mSounds;
        }

        public void setSounds(List<Sound> sounds) {
            mSounds = sounds;
        }

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }


        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            /*View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_sound,parent,false);*/

            ListItemSoundBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.list_item_sound,
                    parent,
                    false);

            //return new SoundHolder(view);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();

        }
    }
}