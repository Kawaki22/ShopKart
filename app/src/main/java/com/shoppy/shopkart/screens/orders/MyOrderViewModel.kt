package com.shoppy.shopkart.screens.orders

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppy.shopkart.data.DataOrException
import com.shoppy.shopkart.models.MCart
import com.shoppy.shopkart.repository.FireOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor(private val fireOrderRepository: FireOrderRepository): ViewModel() {

    val fireOrder: MutableState<DataOrException<List<MCart>, Boolean, Exception>> = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getOrdersFromFirebase()
    }

    fun getOrdersFromFirebase(){

        viewModelScope.launch {
            fireOrder.value.loading = true

            fireOrder.value = fireOrderRepository.getOrdersFromFirebase()

            if (!fireOrder.value.data.isNullOrEmpty()) fireOrder.value.loading = false
        }
    }
}