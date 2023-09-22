package com.example.guessing_game_demo_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessing_game_demo_app.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

       // updateScreen()

        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer { newValue ->
            binding.incorrectGuesses.text = "Incorrect guesses: $newValue"
        })

        viewModel.livesLeft.observe(viewLifecycleOwner, Observer { newValue ->
            binding.lives.text = "You have $newValue lives left"
        })
        viewModel.secretWordDisplay.observe(viewLifecycleOwner, Observer { newValue ->
            binding.word.text = newValue
        })
        viewModel.gameOver.observe(viewLifecycleOwner, Observer { newValue ->
            if (newValue){
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        })

        binding.guessButton.setOnClickListener {
            viewModel.makeGuesses(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            //updateScreen()

//            if (viewModel.isWon() || viewModel.isLost()) {
//                val action = GameFragmentDirections
//                    .actionGameFragmentToResultFragment(viewModel.wonLostMessage())
//                view.findNavController().navigate(action)
//            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun updateScreen() {
//        binding.word.text = viewModel.secretWordDisplay
//        binding.lives.text = "You have ${viewModel.livesLeft} lives left."
//        binding.incorrectGuesses.text = "Incorrect guesses: ${viewModel.incorrectGuesses}"
//    }


}