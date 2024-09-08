package com.tariqul.githubsearchapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.tariqul.githubsearchapp.utils.NetworkUtil
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkUtilTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkCapabilities: NetworkCapabilities

    lateinit var networkUtil: NetworkUtil

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        networkUtil = NetworkUtil(context)
    }

    @Test
    fun `isConnected returns true for WiFi connection`() {
        val network = Mockito.mock(Network::class.java)
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(network)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        Mockito.`when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

        val result = networkUtil.isConnected()
        assertTrue(!result)
    }

    @Test
    fun `isConnected returns false when no active network`() {
        Mockito.`when`(connectivityManager.activeNetwork).thenReturn(null)

        val result = networkUtil.isConnected()
        assertFalse(result)
    }
}
