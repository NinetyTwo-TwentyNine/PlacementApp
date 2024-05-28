package com.example.diplomasecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diplomasecond.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private lateinit var binding: FragmentWorkBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}