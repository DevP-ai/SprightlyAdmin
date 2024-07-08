package com.developer.android.dev.technologia.androidapp.sprightlyadmin

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.adapter.CategoriesAdapter
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.databinding.FragmentCategoriesBinding
import com.developer.android.dev.technologia.androidapp.sprightlyadmin.model.Categories
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import java.util.ArrayList
import java.util.UUID

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    private lateinit var categoriesAdapter: CategoriesAdapter

    private var imageUri: Uri? = null
    private var catImageView: ImageView? = null

    private var launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data!!.data
            catImageView?.setImageURI(imageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        categoriesAdapter = CategoriesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAdd.setOnClickListener {
            showAddDialog()
        }

        getData()

    }

    private fun getData() {
        val list = ArrayList<Categories>()

        Firebase.firestore.collection("ProductsCategories")
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(Categories::class.java)
                    list.add(data)
                }
                categoriesAdapter.submitList(list)
                binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.productRecyclerView.adapter = categoriesAdapter

                if (list.isEmpty()) {
                    binding.productRecyclerView.visibility = View.GONE
                    binding.txtEmpty.visibility = View.VISIBLE
                } else {
                    binding.productRecyclerView.visibility = View.VISIBLE
                    binding.txtEmpty.visibility = View.GONE
                }
            }

    }

    private fun showAddDialog() {
        val addOption = Dialog(requireContext(),R.style.Dialog)
        addOption.setContentView(R.layout.popup_add_category)

        catImageView = addOption.findViewById(R.id.catImage)
        val catName = addOption.findViewById<EditText>(R.id.categoryName)
        val upload = addOption.findViewById<TextView>(R.id.txt_upload)
        val closeButton = addOption.findViewById<ImageView>(R.id.closeButton)
        val cancel = addOption.findViewById<TextView>(R.id.txt_cancel)

        closeButton.setOnClickListener {
            addOption.dismiss()
        }
        cancel.setOnClickListener {
            addOption.dismiss()
        }

        catImageView?.setOnClickListener {
            openGallery()
        }

        upload.setOnClickListener {
            validateData(catName.text.toString())
            catName.setText("")
            catImageView?.setImageURI(null)
            addOption.dismiss()
        }


        val params = WindowManager.LayoutParams()
        params.copyFrom(addOption.window?.attributes)
        params.gravity = Gravity.CENTER
        params.y = requireView().left
        addOption.window?.attributes = params
        addOption.show()

    }

    private fun validateData(catName: String) {
        if(catName.isEmpty()){
            Toast.makeText(requireContext(),"Please Enter Category Name",Toast.LENGTH_SHORT).show()
        }else if(imageUri==null){
            Toast.makeText(requireContext(),"upload image",Toast.LENGTH_SHORT).show()
        }else{
            uploadData(catName)
        }
    }

    private fun uploadData(name: String) {
        val fileName = UUID.randomUUID().toString() + ".jpg"

        val refStorage = FirebaseStorage
            .getInstance()
            .reference.child("Categories/$fileName")

        refStorage.putFile(imageUri!!)
            .addOnSuccessListener {
                it.storage.downloadUrl
                    .addOnSuccessListener { image->
                        storeData(name,image.toString())
                    }
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(),"Something Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(name: String, url: String){
        val db = Firebase.firestore
        val newCategory = db.collection("ProductsCategories").document()
        val data = Categories(
            catId =newCategory.id ,
            catName = name,
            catImage = url
        )
        newCategory
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Category update",Toast.LENGTH_SHORT).show()
                getData()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Something Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        launcherGallery.launch(intent)
    }

}