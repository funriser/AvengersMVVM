package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOptionsFragment : BottomSheetDialogFragment() {

    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_options, container, false)
    }

    override fun onResume() {
        super.onResume()
        behavior = BottomSheetBehavior.from(view?.parent as View)
    }

}