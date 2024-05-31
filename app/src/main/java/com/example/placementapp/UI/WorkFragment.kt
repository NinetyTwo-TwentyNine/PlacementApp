package com.example.placementapp.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.placementapp.MqttViewModel
import com.example.placementapp.data.Constants.MQTT_TOPIC_POWER
import com.example.placementapp.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private lateinit var binding: FragmentWorkBinding
    private val mqttClient: MqttViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mqttClient.mqttInitialize(requireContext())
        mqttClient.mqttConnect()

        mqttClient.tempText.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.temperatureText.text = it
                mqttClient.updateConnectionText(connectionText = binding.connectionStatusText)
            }
        }
        mqttClient.humidText.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.humidityText.text = it
                mqttClient.updateConnectionText(connectionText = binding.connectionStatusText)
            }
        }

        mqttClient.powerOn.observe(viewLifecycleOwner) {0
            binding.powerImage.isEnabled = true
            mqttClient.updateConnectionText(power = it, connectionText = binding.connectionStatusText)
        }

        binding.powerImage.setOnClickListener{
            it.isEnabled = false
            mqttClient.mqttPublish(MQTT_TOPIC_POWER, "${!mqttClient.powerOn.value!!}")
        }

    }
}