package com.sacdev.swipeassignment.fragment


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sacdev.swipeassignment.R
import com.sacdev.swipeassignment.adapter.ProductsAdapter
import com.sacdev.swipeassignment.databinding.FragmentProductListBinding
import com.sacdev.swipeassignment.model.ProductModel
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private val productViewModel: ProductViewModel by viewModel()
    private lateinit var adapters: ProductsAdapter
    private lateinit var progressBar: CardView
    private lateinit var recyclerView: RecyclerView
    private lateinit var productList: List<ProductModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater, container, false)
        progressBar = binding.progressView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addProductBtn.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.addProductFragment)
        }
        binding.crossImageView.setOnClickListener {
            hideKeyboard(context, it)
            binding.searchEdtText.text =null
            binding.searchEdtText.clearFocus()
        }
        recyclerView = binding.rvProduct
        adapters = ProductsAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapters
        binding.searchEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s.toString().trim()
                val filteredList = if (searchQuery.isEmpty()) {
                    productList // Show all products when search query is empty
                } else {
                    productList.filter { product ->
                        // Filter products based on search query
                        product.productName.contains(searchQuery, true)
                    }
                }
                adapters.updateList(filteredList)
            }
        })
        productViewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    progressBar.visibility = View.GONE
                    productList = response.data
                    Log.d("sachin", response.toString())
                    adapters.updateList(productList)
                }

                is ApiResponse.Error -> {

                    val errorMessage = response.errorMessage
                    Log.d("sachin", errorMessage)
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()

                }

                ApiResponse.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    // Show loading indicator
                }
            }
        }

        productViewModel.fetchProducts()

    }

    private fun hideKeyboard(context: Context?, target: View?) {
        if (context == null || target == null) {
            return
        }
        val imm = getInputMethodManager(context)
        imm.hideSoftInputFromWindow(target.windowToken, 0)
    }

    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }


}