package com.vertice.teepop.leaveapp.util

import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * Created by VerDev06 on 1/22/2018.
 */
object NotificationUtil {

    val AUTH_KEY = "AAAALus4Ogw:APA91bFxWcYzxn66wwJhlqm-I6pZ1uYlJkKjURLs6_Te1O544GdxZsGspHIkGdxTBhzy6O8t8Cvj4izIOlmW5HdQExsWMipFWiUVNTS8misIPGVzcHivMD5cwuW0ACvm0Kfq7wme0k9n"

    fun pushNotification(role: String = "", title: String = "", body: String = "", data: Map<String, String>?): String {

        val jPayload = JSONObject()
        val jNotification = JSONObject()
        val jData = JSONObject()

        jNotification.apply {
            put("title", title)
            put("body", body)
        }

        data?.keys?.forEach { jData.put(it, data.getValue(it)) }

        jPayload.apply {
            put("to", "/topics/" + role)
            put("priority", "high")
            put("notification", jNotification)
            put("data", jData)
        }

        val url = URL("https://fcm.googleapis.com/fcm/send")
        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Authorization", "key=$AUTH_KEY")
            setRequestProperty("Content-Type", "application/json")
            doOutput = true
        }

        // Send FCM message content.
        val outputStream = conn.outputStream
        outputStream.write(jPayload.toString().toByteArray())

        // Read FCM response.
        val inputStream = conn.inputStream
        return convertStreamToString(inputStream)
    }

    private fun convertStreamToString(`is`: InputStream): String {
        val s = Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next().replace(",", ",\n") else ""
    }

}

//            put("sound", "default")
//            put("click_action", "OPEN_ACTIVITY_1")
//            put("icon", "ic_notification")

//        when (type) {
//            "tokens" -> {
//                val ja = JSONArray().apply {
//                    put("c5pBXXsuCN0:APA91bH8nLMt084KpzMrmSWRS2SnKZudyNjtFVxLRG7VFEFk_RgOm-Q5EQr_oOcLbVcCjFH6vIXIyWhST1jdhR8WMatujccY5uy1TE0hkppW_TSnSBiUsH_tRReutEgsmIMmq8fexTmL")
//                    put(FirebaseInstanceId.getInstance().token)
//                }
//                jPayload.put("registration_ids", ja)
//            }
//            "topic" -> jPayload.put("to", "/topics/news")
//            "condition" -> jPayload.put("condition", "'sport' in topics || 'news' in topics")
//            else -> jPayload.put("to", FirebaseInstanceId.getInstance().token)
//        }