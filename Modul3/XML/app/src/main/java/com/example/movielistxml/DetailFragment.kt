package com.example.movielistxml

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movielistxml.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val title = arguments?.getString("EXTRA_NAME")
        val image = arguments?.getInt("EXTRA_PHOTO")
        val years = arguments?.getString("EXTRA_YEARS")
        val plot = arguments?.getString("EXTRA_PLOT")
        val desc = arguments?.getString("EXTRA_DESC")

        binding.tvItemTitle.text = title
        binding.tvItemPlot.text = years
        binding.tvItemYears.text = plot
        binding.tvItemDesc.text = desc
        image?.let {
            binding.imgPoster.setImageResource(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}