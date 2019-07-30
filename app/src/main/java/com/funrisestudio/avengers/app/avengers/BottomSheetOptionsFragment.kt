package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import com.funrisestudio.avengers.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_options.*

class BottomSheetOptionsFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_options, container, false)
    }

    override fun onResume() {
        super.onResume()
        view?.doOnLayout {
            BottomSheetBehavior.from(it.parent as View).apply {
                setPeekHeight(vHidden.top)
            }
        }
    }

}