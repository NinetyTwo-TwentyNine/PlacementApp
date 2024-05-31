package com.example.placementapp.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mqtt.MqttViewModel
import com.example.placementapp.data.Constants.MQTT_TOPIC_POWER
import com.example.placementapp.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private lateinit var binding: FragmentWorkBinding
    private val viewModelClient: MqttViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelClient.tempText.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.temperatureText.text = it
                viewModelClient.updateConnectionText(connectionText = binding.connectionStatusText)
            }
        }
        viewModelClient.humidText.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.humidityText.text = it
                viewModelClient.updateConnectionText(connectionText = binding.connectionStatusText)
            }
        }

        viewModelClient.powerOn.observe(viewLifecycleOwner) {0
            binding.powerImage.isEnabled = true
            viewModelClient.updateConnectionText(power = it, connectionText = binding.connectionStatusText)
        }

        binding.powerImage.setOnClickListener{
            it.isEnabled = false
            viewModelClient.mqttPublish(MQTT_TOPIC_POWER, "${!viewModelClient.powerOn.value!!}")
        }

    }
}