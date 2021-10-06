package com.example.safodel.model

import com.example.safodel.R

class AnalysisGenericInfo(
    var heading_id: Int,
    var description_id: Int
) {
    companion object {
        fun init(): MutableList<AnalysisGenericInfo> {
            val genericInfoList: MutableList<AnalysisGenericInfo> = ArrayList()

            genericInfoList.add(
                AnalysisGenericInfo(
                    R.string.analysis_heading1,
                    R.string.analysis_description1
                )
            )
            genericInfoList.add(
                AnalysisGenericInfo(
                    R.string.analysis_heading2,
                    R.string.analysis_description2
                )
            )
            genericInfoList.add(
                AnalysisGenericInfo(
                    R.string.analysis_heading3,
                    R.string.analysis_description3
                )
            )
            genericInfoList.add(
                AnalysisGenericInfo(
                    R.string.analysis_heading4,
                    R.string.analysis_description4
                )
            )
            genericInfoList.add(
                AnalysisGenericInfo(
                    R.string.analysis_heading5,
                    R.string.analysis_description5
                )
            )

            return genericInfoList
        }
    }
}