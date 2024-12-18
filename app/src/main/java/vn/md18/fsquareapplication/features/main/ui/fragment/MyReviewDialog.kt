package vn.md18.fsquareapplication.features.main.ui.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.features.main.viewmodel.ReviewViewmodel
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import vn.md18.fsquareapplication.utils.view.CustomEditText
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MyBottomSheetDialog : BottomSheetDialogFragment() {

    private val mainViewModel: ReviewViewmodel by viewModels()

    private lateinit var stars: List<ImageView>
    private val REQUEST_CODE_PERMISSION = 1001
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private var orderId: String? = null
    private val selectedFiles: MutableList<File> = mutableListOf() // Danh sách file đã chọn
    private lateinit var img1: ImageView

    private var onReviewSubmitListener: ((rating: Int, comment: String, files: List<File>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getString(ARG_ORDER_ID)
        }
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUris: MutableList<Uri> = mutableListOf()

                result.data?.clipData?.let { clipData ->
                    val count = clipData.itemCount
                    for (i in 0 until count) {
                        val imageUri = clipData.getItemAt(i).uri
                        selectedImageUris.add(imageUri)
                    }
                } ?: run {
                    result.data?.data?.let { selectedImageUris.add(it) }
                }

                if (selectedImageUris.isNotEmpty()) {
                    handleImageSelected(selectedImageUris)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_my_order_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnStar1 = view.findViewById<ImageView>(R.id.btnStar1)
        val btnStar2 = view.findViewById<ImageView>(R.id.btnStar2)
        val btnStar3 = view.findViewById<ImageView>(R.id.btnStar3)
        val btnStar4 = view.findViewById<ImageView>(R.id.btnStar4)
        val btnStar5 = view.findViewById<ImageView>(R.id.btnStar5)
        img1 = view.findViewById<ImageView>(R.id.img1)

        stars = listOf(btnStar1, btnStar2, btnStar3, btnStar4, btnStar5)

        var selectedRating = 0

        for (i in stars.indices) {
            stars[i].setOnClickListener {
                updateStars(i)
                selectedRating = i + 1
            }
        }

        val buttonAction = view.findViewById<Button>(R.id.btnApply)
        val buttonClose = view.findViewById<Button>(R.id.btnCancel)

        val btnAddImage = view.findViewById<Button>(R.id.btnAddImage)
        val txtComment = view.findViewById<CustomEditText>(R.id.txtComment)

        btnAddImage.setOnClickListener {
            openGallery()
        }

        buttonAction.setOnClickListener {
            val content = txtComment.getText().toString()

            if (selectedRating == 0) {
                FSLogger.d("phuc", "Chưa chọn số sao")
                return@setOnClickListener
            }

            onReviewSubmitListener?.invoke(selectedRating, content, selectedFiles)
            dismiss()
        }

        buttonClose.setOnClickListener {
            dismiss()
        }
    }

    private fun updateStars(selectedIndex: Int) {
        for (i in stars.indices) {
            if (i <= selectedIndex) {
                stars[i].setImageResource(R.drawable.star)
            } else {
                stars[i].setImageResource(R.drawable.outline_star)
            }
        }
    }

    private fun handleImageSelected(imageUris: List<Uri>) {
        imageUris.forEach { uri ->
            val file = createFileFromUri(uri)
            if (file != null) {
                selectedFiles.add(file) // Lưu file vào danh sách
            }
        }

        // Hiển thị ảnh đầu tiên vào ImageView
        if (imageUris.isNotEmpty()) {
            val selectedUri = imageUris[0]
            val bitmap = getBitmapFromUri(selectedUri)
            img1.setImageBitmap(bitmap)
        }

        FSLogger.d("phuc", "Selected files: ${selectedFiles.map { it.name }}")
    }

    private fun createFileFromUri(uri: Uri): File? {
        return try {
            val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(uri, "r", null) ?: return null
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(requireContext().cacheDir, System.currentTimeMillis().toString() + ".jpg")
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            file
        } catch (e: Exception) {
            FSLogger.e("phuc", "Error creating file from URI")
            null
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            FSLogger.e("phuc", "Error while getting bitmap from uri")
            null
        }
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {

            // Nếu đã có quyền, mở thư viện ảnh
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            pickImageLauncher.launch(intent)

        } else {
            // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )
        }
    }

    companion object {
        private const val ARG_ORDER_ID = "orderId"

        fun newInstance(orderId: String): MyBottomSheetDialog {
            val dialog = MyBottomSheetDialog()
            val args = Bundle()
            args.putString(ARG_ORDER_ID, orderId)
            dialog.arguments = args
            return dialog
        }
    }

    fun setOnReviewSubmitListener(listener: (rating: Int, comment: String, files: List<File>) -> Unit) {
        onReviewSubmitListener = listener
    }
}
