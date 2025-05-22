package com.example.movielistxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielistxml.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: ListMovieAdapter

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("Akmal")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val movies = getListMovie()
        viewModel.setMovieList(movies)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieList.collectLatest { list ->
                Timber.i("Movie List updated with ${list.size} items")
                movieAdapter.updateData(ArrayList(list))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedMovie.collectLatest { movie ->
                movie?.let {
                    Timber.i("Movie selected: ${movie.title}")
                    val detailFragment = DetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("EXTRA_NAME", movie.title)
                            putInt("EXTRA_PHOTO", movie.image)
                            putString("EXTRA_YEARS", movie.years)
                            putString("EXTRA_PLOT", movie.plot)
                            putString("EXTRA_DESC", movie.desc)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, detailFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun setupRecyclerView(){
        movieAdapter = ListMovieAdapter(
            arrayListOf(),
            onImdbClick = {url ->
                Timber.i("IMDB clicked with URL: $url")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            },
            onDetailClick = {title, _, _, _, _ ->
                Timber.i("Detail clicked with title: $title")
                val movie = viewModel.movieList.value.find { it.title == title }
                movie?.let {
                    viewModel.selectMovie(it)
                }
            }
        )
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
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