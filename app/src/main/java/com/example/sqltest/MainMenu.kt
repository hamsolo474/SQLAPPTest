package com.example.sqltest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sqltest.databinding.MainMenuBinding

class MainMenu : Fragment() {
    private var _binding: MainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.game.setOnClickListener {
            Log.d("MainMenu", "Game button clicked")
            findNavController().navigate(R.id.action_main_menu_to_game)
        }
        binding.viewList.setOnClickListener {
            Log.d("MainMenu", "ViewList button clicked")
            findNavController().navigate(R.id.action_main_menu_to_view_list)
        }
        binding.settings.setOnClickListener {
            Log.d("MainMenu", "Setting button clicked")
            findNavController().navigate(R.id.action_main_menu_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}