package com.example.movielistxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielistxml.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: ListMovieAdapter
    private val list = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        list.clear()
        list.addAll(getListMovie())
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView(){
        movieAdapter = ListMovieAdapter(
            list,
            onImdbClick = {url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            },
            onDetailClick = {title, image, years, plot, desc ->
                val detailFragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("EXTRA_NAME", title)
                        putInt("EXTRA_PHOTO", image)
                        putString("EXTRA_YEARS", years)
                        putString("EXTRA_PLOT", plot)
                        putString("EXTRA_DESC", desc)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.rvMovie.apply {
            layoutManager =LinearLayoutManager(context)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    private fun getListMovie(): ArrayList<Movie>{
        val dataMovie = resources.getStringArray(R.array.data_title)
        val dataLink = resources.getStringArray(R.array.data_link)
        val dataImage = resources.obtainTypedArray(R.array.data_image)
        val dataPlot = resources.getStringArray(R.array.plot)
        val dataYear = resources.getStringArray(R.array.data_years)
        val dataDesc = resources.getStringArray(R.array.data_description)
        val listMovie = ArrayList<Movie>()
        for (i in dataMovie.indices){
            val movie = Movie(dataMovie[i], dataLink[i], dataImage.getResourceId(i, -1), dataPlot[i], dataYear[i], dataDesc[i])
            listMovie.add(movie)
        }
        dataImage.recycle()
        return listMovie
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}