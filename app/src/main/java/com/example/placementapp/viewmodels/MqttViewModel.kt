package com.example.placementapp.viewmodels

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placementapp.data.Constants.MQTT_CLIENT_ID
import com.example.placementapp.data.Constants.MQTT_SERVER_PORT
import com.example.placementapp.data.Constants.MQTT_SERVER_URI
import com.example.placementapp.data.Constants.MQTT_TOPIC_HUMIDITY
import com.example.placementapp.data.Constants.MQTT_TOPIC_LIST
import com.example.placementapp.data.Constants.MQTT_TOPIC_POWER
import com.example.placementapp.data.Constants.MQTT_TOPIC_TEMPERATURE
import com.example.placementapp.data.Constants.MQTT_USER_NAME
import com.example.placementapp.data.Constants.MQTT_USER_PASSWORD
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MqttViewModel(): ViewModel() {
    private lateinit var mqttServer: MqttRepository
    var tempText: MutableLiveData<String> = MutableLiveData()
    var humidText: MutableLiveData<String> = MutableLiveData()
    var powerOn: MutableLiveData<Boolean> = MutableLiveData(false)

    fun mqttInitialize(context: Context) {
        mqttServer = MqttRepository(context, "${MQTT_SERVER_URI}:${MQTT_SERVER_PORT}", MQTT_CLIENT_ID)
    }

    fun mqttConnect() {
        mqttServer.connect(MQTT_USER_NAME, MQTT_USER_PASSWORD,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    MQTT_TOPIC_LIST.forEach {
                        mqttSubscribe(it)
                    }
                }
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    updateLiveData(powerOn, false)
                    Log.d("MQTT_DEBUGGER", "MQTT connection was failed.")
                    exception?.printStackTrace()

                    try {
                        mqttConnect()
                    } catch (e: Exception) {
                        Log.d("MQTT_DEBUGGER", "MQTT connection function call failed.")
                        e.printStackTrace()
                    }
                }
            },
            object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    when (topic) {
                        MQTT_TOPIC_POWER -> {
                            when(message.toString()) {
                                "0", "false" -> updateLiveData(powerOn, false)
                                "1", "true" -> updateLiveData(powerOn, true)
                            }
                        }
                        MQTT_TOPIC_TEMPERATURE -> {
                            powerOn.value = true
                            updateLiveData(tempText, "$message°C")
                        }
                        MQTT_TOPIC_HUMIDITY -> {
                            powerOn.value = true
                            updateLiveData(humidText, "$message%")
                        }
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    Log.d("MQTT_DEBUGGER", "MQTT connection was lost.")
                    cause?.printStackTrace()
                    try {
                        mqttConnect()
                    } catch (e: Exception) {
                        Log.d("MQTT_DEBUGGER", "MQTT connection function call failed.")
                        e.printStackTrace()
                    }
                }
                override fun deliveryComplete(token: IMqttDeliveryToken?) {}
            })
    }

    private fun <T> updateLiveData(liveData: MutableLiveData<T>, message: T) {
        liveData.value = message
        liveData.postValue(message)
    }


    fun mqttSubscribe(topic: String) {
        if (mqttServer.isConnected()) {
            mqttServer.subscribe(topic,
                1,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {}
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {}
                })
        }
    }

    fun mqttPublish(topic: String, message: String) {
        if (mqttServer.isConnected()) {
            mqttServer.publish(topic,
                message,
                1,
                false,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {}
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {}
                })
        }
    }

    fun mqttUnsubscribe(topic: String) {
        if (mqttServer.isConnected()) {
            mqttServer.unsubscribe( topic,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {}
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {}
                })
        }
    }

    fun mqttDisconnect() {
        if (mqttServer.isConnected()) {
            mqttServer.disconnect(object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {}
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {}
            })
        }
    }

    fun updateConnectionText(power: Boolean = powerOn.value!!, connectionText: TextView) {
        if (power) {
            connectionText.text = "Подключено"
        } else {
            connectionText.text = "Отключено"
        }
    }
}