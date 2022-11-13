package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00

private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00
private const val PRICE_FOR_UP_SIZE = 1.00


class OrderViewModel : ViewModel() {

    // jumlah pesanan
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    // rasa pesanan
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    // ukuran pesanan
    private  val _size = MutableLiveData<String>()
    val size: LiveData<String> = _size

    // untuk membuat pilihan tangan
    val dateOptions: List<String> = getPickupOptions()

    // pemilihan tanggal
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // total harga sekarang sesuai peranan
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // Set initial values for the order
        resetOrder()
    }

   // jika pilihan memiliki harga yang berbada, maka akan diupdate harganya
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }


    // untuk set flavor
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }
    // jika pilihan memiliki harga yang berbada, maka akan diupdate harganya
    fun setSize(desiredSize: String) {
        _flavor.value = desiredSize
        updatePrice()
    }

    /**
     * untuk set tanggal

     */
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    /**
     * jika pada pilihan tidak memiliki pilihan, maka data nilainya akan null atau kosong
     */
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    fun hasNoSizeSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    /**
     * mengatur ulang pesanan
     */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
        _size.value = ""
    }

    /**
     * melakukan update terkait harga sesuai dengan pesanan
     */
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
            calculatedPrice += PRICE_FOR_UP_SIZE
        }
        _price.value = calculatedPrice
    }

    /**
     * mengembalikan nilai tanggal yang dipilih sesuai dengan 4 opsi yang ada
     */
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
}