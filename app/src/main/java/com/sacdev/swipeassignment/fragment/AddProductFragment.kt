package com.sacdev.swipeassignment.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sacdev.swipeassignment.databinding.FragmentAddProductBinding
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.viewmodel.AddProductViewModel
import com.yalantis.ucrop.UCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: AddProductViewModel by viewModel()
    private  var  imageUri :Uri = Uri.parse("")
    private lateinit var progressBar: CardView

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {

                    try {
                        val options = UCrop.Options()
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
                        options.setCompressionQuality(100)
                        options.withAspectRatio(1f, 1f)

                        val imageFile = File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "ProductImage.jpg"
                        )
                        val destinationUri = Uri.fromFile(imageFile)
                        UCrop.of(selectedImageUri, destinationUri)
                            .withOptions(options)
                            .start(requireContext(),this, UCrop.REQUEST_CROP)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater, container, false)
        val productTypes = arrayListOf("Type1", "Type2", "Type3")
        val adapter =
            ArrayAdapter(requireContext(), org.koin.android.R.layout.support_simple_spinner_dropdown_item, productTypes)
        val autoCompleteTextView = binding.productTypeAutoComplete
        autoCompleteTextView.setAdapter(adapter)
        progressBar = binding.progressView

        return binding.root
    }


    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageLayout.setOnClickListener{
            getImage()
        }
        binding.btnSubmit.setOnClickListener{
            val productName = binding.etProductName.text.toString()
            val productType = binding.productTypeAutoComplete.text.toString()
            val productPrice = binding.etProductPrice.text.toString()
            val productTax = binding.etProductTax.text.toString()
            if (productName.isEmpty()){
                binding.etProductName.error = "Enter Product Name"
                binding.etProductName.requestFocus()
                return@setOnClickListener
            }
            if (productType.isEmpty()){
                binding.productTypeDropdown.error = "Select Product Type"
                binding.productTypeDropdown.requestFocus()
                return@setOnClickListener
            }
            if (productPrice.isEmpty()){
                binding.etProductPrice.error = "Enter Product Price"
                binding.etProductPrice.requestFocus()
                return@setOnClickListener
            }
            if (productTax.isEmpty()){
                binding.etProductTax.error = "Enter Product Name"
                binding.etProductTax.requestFocus()
                return@setOnClickListener
            }
            if (imageUri.equals(Uri.parse(""))){
                getImage()
                return@setOnClickListener
            }

            val file = imageUri.path?.let { it1 -> File(it1) }
            viewModel.submitProduct(productName,productType,productPrice,productTax,
                listOf(file) as List<File>
            )

        }
      viewModel.addProductResult.observe(viewLifecycleOwner){response->
          when (response) {
              is ApiResponse.Success -> {
                  progressBar.visibility = View.GONE
                  Log.d("sachin",response.toString())
                  Toast.makeText(context,"Product Added SuccessFully",Toast.LENGTH_LONG).show()
              }

              is ApiResponse.Error -> {

                  val errorMessage = response.errorMessage
                  Log.d("sachin", errorMessage)
                  progressBar.visibility = View.GONE
                  Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()

              }

              ApiResponse.Loading -> {
                  progressBar.visibility = View.VISIBLE

              }
          }
      }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) { }
        else
        {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                100
            )
        }
    }
    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP) {
            try {
                imageUri = data?.let { UCrop.getOutput(it) }!!
                binding.ivProductImage.setImageURI(imageUri)
            }catch (_:Exception){

            }

        }
    }

}