import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.features.main.ui.fragment.ProfileFragment

class BottomDialogLoggoutFragment : BottomSheetDialogFragment() {

    interface LogoutListener {
        fun onLogoutConfirmed()
    }

    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Đảm bảo context là ProfileFragment
        if (context is LogoutListener) {
            logoutListener = context
        } else {
            throw ClassCastException("$context must implement LogoutListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_dialog_loggout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCancel = view.findViewById<View>(R.id.btnCancel)
        val btnLogout = view.findViewById<View>(R.id.btnConfirm)

        btnCancel.setOnClickListener {
            dismiss() // Đóng Bottom Dialog
        }

        btnLogout.setOnClickListener {
            // Khi người dùng chọn logout, gọi lại callback
            logoutListener?.onLogoutConfirmed()
            dismiss() // Đóng Bottom Dialog
        }
    }

    override fun onDetach() {
        super.onDetach()
        logoutListener = null // Xoá callback khi fragment bị detach
    }
}
