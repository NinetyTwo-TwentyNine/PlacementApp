package com.example.placementapp.data

object Constants {
    const val APP_MIN_PASSWORD_LENGTH = 8

    const val APP_PREFERENCES = "APP_PREFERENCES"
    const val APP_PREFERENCES_STAY = "APP_PREFERENCES_STAY_BOOL"

    const val APP_TOAST_WEAK_CONNECTION = "Looks like there are some problems with connection..."
    const val APP_TOAST_NOT_SIGNED_IN = "You aren't signed in yet."
    const val APP_TOAST_LOGIN_FAILED = "Failed to log in"
    const val APP_TOAST_LOGIN_SUCCESS = "Logged in successfully"
    const val APP_TOAST_SIGNUP_FAILED = "Failed to sign up"
    const val APP_TOAST_SIGNUP_SUCCESS = "Registered successfully"
    const val APP_TOAST_PASSWORD_TOO_SHORT = "Your password should be at least $APP_MIN_PASSWORD_LENGTH characters long."
    const val APP_TOAST_PASSWORDS_DONT_MATCH = "Your passwords don't match. Please confirm your password."

    const val MQTT_SERVER_URI = "tcp://wqtt.ru"
    const val MQTT_SERVER_PORT = "5031"

    const val MQTT_CLIENT_ID = "xdtcgfvgyjuhijokcfghjkj323"

    const val MQTT_USER_NAME = "123"
    const val MQTT_USER_PASSWORD = "123"

    private const val MQTT_TOPIC_MAIN = "airchecker"

    const val MQTT_TOPIC_POWER = "$MQTT_TOPIC_MAIN/power/mode"

    const val MQTT_TOPIC_TEMPERATURE = "$MQTT_TOPIC_MAIN/sensor_t_h/temp"
    const val MQTT_TOPIC_HUMIDITY = "$MQTT_TOPIC_MAIN/sensor_t_h/hum"

    val MQTT_TOPIC_LIST = listOf(MQTT_TOPIC_POWER, MQTT_TOPIC_TEMPERATURE, MQTT_TOPIC_HUMIDITY)
}
